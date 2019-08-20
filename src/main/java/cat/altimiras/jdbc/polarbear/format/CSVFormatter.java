package cat.altimiras.jdbc.polarbear.format;

/**
 * Just split raw row into an array of Strings. It do not convert to the proper type (available in table definition) to avoid to convert values that are not needed.
 */
public class CSVFormatter implements RowFormatter {

	private String separator = ",";

	public CSVFormatter(){
	}

	public CSVFormatter(String separator) {
		this.separator = separator;
	}

	@Override
	public Object[] parse(String raw) {
		return raw == null ? null : raw.split(separator);
	}
}
