package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ColumnDefinition {

	public enum Type {TEXT, LONG, FLOAT, DATE, DATETIME, TIME, BOOLEAN}

	private String name;
	private Type type;
	private boolean isPK = false;
	@JsonIgnore
	private int position;

	public ColumnDefinition() {
	}

	public ColumnDefinition(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public boolean isPK() {
		return isPK;
	}

	public void setPK(boolean PK) {
		isPK = PK;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
