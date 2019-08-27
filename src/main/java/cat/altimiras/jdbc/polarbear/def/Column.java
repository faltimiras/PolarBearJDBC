package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.text.SimpleDateFormat;

public class Column {

	private String name;
	private String type;

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


	public Column() {
	}

	public Column(String name, String type) {
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

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

}
