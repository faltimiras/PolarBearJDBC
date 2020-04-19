package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDateTime;

public class PartitionDefinition {

	private String columnName;
	private String partitionsFormat;
	@JsonDeserialize(using = DateSerializer.class)
	private LocalDateTime since;
	private int step;

/*
	private String dateFormat = "yyyy/MM/dd";
	private String timeFormat = "HH:mm:ss";
	private String timestampFormat = "yyyy/MM/dd HH:mm:ss";
	private Boolean isUnixtime = false;

	@JsonIgnore
	private SimpleDateFormat dateFormatter;
	@JsonIgnore
	private SimpleDateFormat timeFormatter;
	@JsonIgnore
	private SimpleDateFormat timestampFormatter;

	public SimpleDateFormat getDateFormat() {
		if (dateFormatter == null) {
			dateFormatter = new SimpleDateFormat(dateFormat);
		}
		return dateFormatter;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public SimpleDateFormat getTimeFormat() {
		if (timeFormatter == null) {
			timeFormatter = new SimpleDateFormat(timeFormat);
		}
		return timeFormatter;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public SimpleDateFormat getTimestampFormat() {
		if (timestampFormatter == null) {
			timestampFormatter = new SimpleDateFormat(timestampFormat);
		}
		return timestampFormatter;
	}

	public void setTimestampFormat(String timestampFormat) {
		this.timestampFormat = timestampFormat;
	}

	public Boolean getUnixtime() {
		return isUnixtime;
	}

	public void setUnixtime(Boolean unixtime) {
		isUnixtime = unixtime;
	}
	*/

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
