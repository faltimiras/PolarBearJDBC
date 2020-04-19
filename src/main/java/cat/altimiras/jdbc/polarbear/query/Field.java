package cat.altimiras.jdbc.polarbear.query;

public class Field {

	private String table;
	private String name;
	private String alias;

	public Field(String table, String name, String alias) {
		this.table = table;
		this.name = name;
		this.alias = alias;
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
}
