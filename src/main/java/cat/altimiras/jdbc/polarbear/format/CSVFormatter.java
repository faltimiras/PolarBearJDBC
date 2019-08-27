package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;

import java.util.List;
import java.util.Map;

/**
 * Just split raw row into an array of Strings. It do not convert to the proper type (available in table definition) to avoid to convert values that are not needed.
 */
public class CSVFormatter extends RowFormatter {

	private final String separator;

	private final int columns;

	private final boolean ignoreWrongRow;

	public CSVFormatter(TableDefinition tableDefinition) {
		super(tableDefinition);
		this.columns = tableDefinition.getColumns().size();
		this.separator = tableDefinition.getSeparator();
		this.ignoreWrongRow = tableDefinition.isIgnoreWrongRowData();
	}

	@Override
	public Object[] parse(String raw,  Map<String, Integer> queryFieldsByName) throws PolarBearException {
		if (raw == null) {
			return null;
		}
		String[] values = raw.split(separator, -1);
		if (values.length == columns) {
			if (queryFieldsByName == null) { // null means *, return all fields
				return values;
			} else {
				return filter(values, queryFieldsByName);
			}
		} else {
			if (ignoreWrongRow) {
				return null;
			} else {
				throw new PolarBearException("Bad formed row");
			}
		}
	}

	private String[] filter(String[] values, Map<String, Integer> queryFieldsByName) {

		String[] filter = new String[queryFieldsByName.size()];
		int i = 0;
		for(Map.Entry<String, Integer> e : queryFieldsByName.entrySet()){
			int pos = e.getValue();
			filter[i] = values[pos];
			i++;
		}
		return filter;
	}
}
