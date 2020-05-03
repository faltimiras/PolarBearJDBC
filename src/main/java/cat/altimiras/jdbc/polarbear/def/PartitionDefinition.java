package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.time.LocalDateTime;

public class PartitionDefinition {
	private String columnName;

	private String partitionsFormat;

	@JsonDeserialize(using = DateDeserializer.class)
	private LocalDateTime since;

	private int step;

	public PartitionDefinition() {
	}

	public PartitionDefinition(String columnName, String partitionsFormat, LocalDateTime since, int step) {
		this.columnName = columnName;
		this.partitionsFormat = partitionsFormat;
		this.since = since;
		this.step = step;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getPartitionsFormat() {
		return partitionsFormat;
	}

	public void setPartitionsFormat(String partitionsFormat) {
		this.partitionsFormat = partitionsFormat;
	}

	public LocalDateTime getSince() {
		return since;
	}

	public void setSince(LocalDateTime since) {
		this.since = since;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}
}
