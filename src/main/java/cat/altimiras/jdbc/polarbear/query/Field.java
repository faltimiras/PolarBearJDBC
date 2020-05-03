package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.def.TableDefinition;

public class Field {
	private String table;

	private String name;

	private String alias;

	private boolean isTs = false;

	private TableDefinition tableDefinition;

	public Field(String table, String name, String alias) {
		this.table = table;
		this.name = name;
		this.alias = alias;
	}

	public Field(String table, String name, String alias, TableDefinition tableDefinition) {
		this.table = table;
		this.name = name;
		this.alias = alias;
		this.tableDefinition = tableDefinition;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public boolean isTs() {
		return isTs;
	}

	public void setTs(boolean ts) {
		isTs = ts;
	}

	public TableDefinition getTableDefinition() {
		return tableDefinition;
	}

	public void setTableDefinition(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}
}
