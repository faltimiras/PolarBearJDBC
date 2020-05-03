package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.def.TableDefinition;

public class Table {
	private String name;

	private String alias;

	private TableDefinition definition;

	public Table(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public Table(String name, String alias, TableDefinition definition) {
		this.name = name;
		this.alias = alias;
		this.definition = definition;
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

	public TableDefinition getDefinition() {
		return definition;
	}

	public void setDefinition(TableDefinition definition) {
		this.definition = definition;
	}
}
