package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlBaseListener;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlParser;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.MINUTES;

public class Query extends SqlBaseListener {

	private TableManager tableManager;
	private TableDefinition tableDefinition;
	private Map<String, Integer> tableColumns;

	private String table;
	private List<Field> fields = new ArrayList<>(); //null means *
	private LocalDateTime tsLowerLimit; //null means no limit. Including it
	private LocalDateTime tsUpLimit; //null means no limit. Including it

	//private List<>

	private String currentColumn;
	private String currentComparator;
	private boolean currentColumnFirst; //true means column is first operand
	private String currentValue;

	public Query(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	@Override
	public void exitTable(SqlParser.TableContext ctx) {
		this.table = ctx.table_alias() == null ? ctx.table_name().getText() : ctx.table_alias().getText();
	}

	@Override
	public void exitResult_column(SqlParser.Result_columnContext ctx) {
		if ("*".equals(ctx.getText())) {
			this.fields = null;
		} else {
			String alias = ctx.column_alias() == null ? null : ctx.column_alias().getText();
			Field field = new Field(ctx.column_name().getText(), alias);
			fields.add(field);
		}
	}

	@Override
	public void exitFilter(SqlParser.FilterContext ctx) {

		try {
			if (currentColumn.equals(tableDefinition.getTsColumnName())) {
				if (currentComparator.equals(">")) {
					if (currentColumnFirst) {
						tsLowerLimit = DateFormatter.parse(currentValue).plus(1, MINUTES);
					} else {
						tsUpLimit = DateFormatter.parse(currentValue).minus(1, MINUTES);
					}
				}
				else if ( currentComparator.equals(">=")){
					if (currentColumnFirst) {
						tsLowerLimit = DateFormatter.parse(currentValue);
					} else {
						tsUpLimit = DateFormatter.parse(currentValue);
					}
				}
				else if (currentComparator.equals("<")) {
					if (currentColumnFirst) {
						tsUpLimit = DateFormatter.parse(currentValue).minus(1, MINUTES);
					} else {
						tsLowerLimit = DateFormatter.parse(currentValue).plus(1, MINUTES);
					}
				}
				else if (currentComparator.equals("<=")){
					if (currentColumnFirst) {
						tsUpLimit = DateFormatter.parse(currentValue);
					} else {
						tsLowerLimit = DateFormatter.parse(currentValue);
					}
				}
			} else {
				//other filters
				//TODO
			}
		} catch (Exception e) {
			//TODO
		}

		currentColumn = null;
		currentComparator = null;
		currentValue = null;
	}

	@Override
	public void exitTo_compare(SqlParser.To_compareContext ctx) {

		try {
			loadTableDef();

			String txt = ctx.getText();

			if (tableColumns.containsKey(txt) || txt.equals(tableDefinition.getTsColumnName())) {
				currentColumn = txt;
				currentColumnFirst = currentValue == null;
			} else {
				//column not exist
				currentValue = txt;
			}

		} catch (Exception e) {
			//TODO
		}
	}

	@Override
	public void exitComparator(SqlParser.ComparatorContext ctx) {
		this.currentComparator = ctx.getText();
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public LocalDateTime getTsLowerLimit() {
		return tsLowerLimit;
	}

	public void setTsLowerLimit(LocalDateTime tsLowerLimit) {
		this.tsLowerLimit = tsLowerLimit;
	}

	public LocalDateTime getTsUpLimit() {
		return tsUpLimit;
	}

	public void setTsUpLimit(LocalDateTime tsUpLimit) {
		this.tsUpLimit = tsUpLimit;
	}

	private void loadTableDef() throws PolarBearException {
		if (tableColumns == null) {
			tableDefinition = tableManager.getTable(table);
			tableColumns = tableDefinition.getColumnsByName();
		}
	}
}
