package cat.altimiras.jdbc.polarbear.query;

import java.time.LocalDateTime;
import java.util.List;

public class Query {

	private String table;
	private List<Field> fields; //null means *
	private LocalDateTime tsLowerLimit; //null means no limit
	private LocalDateTime tsUpLimit; //null means no limit

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
}
