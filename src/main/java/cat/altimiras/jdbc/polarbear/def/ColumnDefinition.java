package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ColumnDefinition {
	private String name;

	private Type type;

	private boolean pk = false;

	@JsonIgnore
	private int position;

	public String format;

	public ColumnDefinition() {
	}

	public ColumnDefinition(String name, Type type) {
		this.name = name;
		this.type = type;
	}

	public ColumnDefinition(String name, Type type, int position) {
		this.name = name;
		this.type = type;
		this.position = position;
	}

	public ColumnDefinition(String name, Type type, int position, boolean pk) {
		this.name = name;
		this.type = type;
		this.pk = pk;
		this.position = position;
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
		return pk;
	}

	public void setPK(boolean PK) {
		pk = PK;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public enum Type {TEXT, LONG, FLOAT, DATE, DATETIME, TIME, BOOLEAN}
}
