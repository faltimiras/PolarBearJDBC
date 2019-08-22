package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;

import java.util.List;

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
	public Object[] parse(String raw, List<Field> fields) throws PolarBearException {
		if (raw == null) {
			return null;
		}
		Object[] values = raw.split(separator, -1);
		if (values.length == columns) {
			if (fields == null) { // null means *, return all fields
				return values;
			} else {
				return filter(values, fields);
			}
		} else {
			if (ignoreWrongRow) {
				return null;
			} else {
				throw new PolarBearException("Bad formed row");
			}
		}
	}

	private Object[] filter(Object[] values, List<Field> fields) {

		Object[] filter = new Object[fields.size()];
		for (int i = 0; i < fields.size(); i++) {
			int pos = this.tableDefinition.getPosition(fields.get(i).getName());
			filter[i] = values[pos];
		}
		return filter;
	}
}
