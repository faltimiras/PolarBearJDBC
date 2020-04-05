package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class RowFormatterFactory {

	private static Map<String, Class> formatters = new HashMap<>(1);

	static {
		formatters.put("csv", CSVFormatter.class); //TODO: lib in classpath should be able to add formats
		formatters.put("json", JsonlFormatter.class);
		formatters.put("jsonl", JsonlFormatter.class);
	}

	public static RowFormatter get(String format, TableDefinition tableDefinition) throws PolarBearException {

		try {
			Class formatterClass = formatters.get(format);
			if (formatterClass == null) {
				throw new PolarBearException("Row line format " + format + " not supported");
			}

			Constructor c = formatterClass.getConstructor(TableDefinition.class);
			return (RowFormatter) c.newInstance(tableDefinition);

		} catch (Exception e) {
			throw new PolarBearException("Formatter can not initialized", e);
		}
	}
}