package cat.altimiras.jdbc.polarbear.execution;

import static java.time.temporal.ChronoUnit.MINUTES;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.io.Reader;
import cat.altimiras.jdbc.polarbear.query.DateFormatter;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Planner {
	private final static Logger log = LoggerFactory.getLogger(Planner.class);

	private final static String SEPARATOR = "-";

	//star tables cache
	//TODO ehcache??
	private static Map<String, Map<String, String[]>> starTablesCache = new HashMap<>();

	private Query query;

	private TableDefinition mainTable;

	private LocalDateTime tsUpperLimit;

	private LocalDateTime tsLowerLimit;

	public Planner(TableManager tableManager, Query query) throws PolarBearException {
		this.query = query;

		for (Table table : query.getTables()) {
			TableDefinition tableDef = tableManager.getTable(table.getName());
			if (tableDef.isStarTable()) { //load tables into memory
				log.debug("Loading star table {}", table.getName());
				List<Integer> pkPositions = tableDef.getPKPositions();
				Reader reader = tableManager.read(table.getName(), null, null, -1);
				Map<String, String[]> tableInMemory = new HashMap<>();
				while (reader.hasNext()) {
					String[] row = reader.next();
					String pk = createPK(row, pkPositions);
					tableInMemory.put(pk, row);
				}
				starTablesCache.put(table.getName(), tableInMemory);
			} else {
				mainTable = tableDef;
			}
		}

		//set query limits
		setLimits(query.getWhere(), mainTable.getPartition().getColumnName());

		if (tsLowerLimit == null && tsUpperLimit == null) {
			log.error("Not time window defined, full scan not allowed!");
			throw new PolarBearException("Not time window defined, full scan not allowed");
		}
	}

	/**
	 * @param row row from time partitioned table
	 * @return null if row do not meet query filters, String[] with the results with fields properly ordered, adding fields from star tables, and removing not selected ones.
	 */
	public String[] process(String[] row) throws PolarBearException {

		//filter. Discard rows from main table that do not met conditions
		Filter filter = new Filter(mainTable);
		if (filter.meets(row, query.getWhere())){

			//complete with start tables
			Joiner joiner = new Joiner();
			String[] fatRow = joiner.join(row);

			//select fields
			Selector selector = new Selector();
			return selector.select(fatRow);
		}
		else {
			log.debug("Row {} discarded, where not met", row);
			return null;
		}
	}

	private String createPK(String[] row, List<Integer> pkPositions) {
		StringBuffer sb = new StringBuffer();
		for (Integer pos : pkPositions) {
			sb.append(row[pos]);
			sb.append(SEPARATOR);
		}
		return sb.toString();
	}

	public LocalDateTime getTsLowerLimit() {
		if (tsLowerLimit == null) {
			return mainTable.getPartition().getSince();
		} else {
			return tsLowerLimit;
		}
	}

	public LocalDateTime getTsUpperLimit() {
		if (tsUpperLimit == null) {
			return LocalDateTime.now();
		} else {
			return tsUpperLimit;
		}
	}

	public TableDefinition getMainTable() {
		return mainTable;
	}

	private void setLimits(Expr expr, String tsColumnName) throws PolarBearException {

		if (expr != null) {
			Object operand1 = expr.getOperand1();
			if (operand1 != null) {
				if (operand1 instanceof Field) {
					if (((Field) operand1).getName().equals(tsColumnName)) {
						setLimit((String) expr.getOperand2(), expr.getOperation(), true);
					}
				}
				if (operand1 instanceof Expr) {
					setLimits(expr.getOperand1AsExpr(), tsColumnName);
				}
			}
			Object operand2 = expr.getOperand2();
			if (operand2 != null) {
				if (operand2 instanceof Field) {
					if (((Field) operand2).getName().equals(tsColumnName)) {
						setLimit((String) expr.getOperand1(), expr.getOperation(), false);
					}
				}
				if (operand2 instanceof Expr) {
					setLimits(expr.getOperand2AsExpr(), tsColumnName);
				}
			}
		}
	}

	private void setLimit(String value, String comparator, boolean fieldFirst) throws PolarBearException {

		if (comparator.equals(">")) {
			if (fieldFirst) {
				tsLowerLimit = DateFormatter.parse(value).plus(1, MINUTES);
			} else {
				tsUpperLimit = DateFormatter.parse(value).minus(1, MINUTES);
			}
		} else if (comparator.equals(">=")) {
			if (fieldFirst) {
				tsLowerLimit = DateFormatter.parse(value);
			} else {
				tsUpperLimit = DateFormatter.parse(value);
			}
		} else if (comparator.equals("<")) {
			if (fieldFirst) {
				tsUpperLimit = DateFormatter.parse(value).minus(1, MINUTES);
			} else {
				tsLowerLimit = DateFormatter.parse(value).plus(1, MINUTES);
			}
		} else if (comparator.equals("<=")) {
			if (fieldFirst) {
				tsUpperLimit = DateFormatter.parse(value);
			} else {
				tsLowerLimit = DateFormatter.parse(value);
			}
		} else {
			log.error("Comparator {} not supported", comparator);
			throw new PolarBearException("Time partition is not correctly defined");
		}
	}
}
