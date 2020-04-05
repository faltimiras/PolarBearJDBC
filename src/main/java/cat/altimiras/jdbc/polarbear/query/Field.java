package cat.altimiras.jdbc.polarbear.query;

public class Field {

	private String name;
	private String alias;

	public Field(String name, String alias) {
		this.name = name;
		this.alias = alias;
	}

	public String getName() {
		return name;
	}

	public String getAlias() {
		return alias;
	}
}
