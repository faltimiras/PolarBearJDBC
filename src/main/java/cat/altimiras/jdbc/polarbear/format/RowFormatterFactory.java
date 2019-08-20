package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;

import java.util.HashMap;
import java.util.Map;

public class RowFormatterFactory {

	private static Map<String, RowFormatter> formatters = new HashMap<>(1);
	static {
		formatters.put("csv", new CSVFormatter()); //TODO: lib in classpath should be able to add formats
	}

	public static RowFormatter get(String format) throws PolarBearException {

		RowFormatter formatter = formatters.get(format);
		if (formatter == null) {
			throw new PolarBearException("Row line format " + format + " not supported");
		}
		return formatter;
	}
}