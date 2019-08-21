package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;

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
	public Object[] parse(String raw) throws PolarBearException {
		if (raw == null) {
			return null;
		}
		Object[] values = raw.split(separator, -1);
		if (values.length == columns) {
			return values;
		} else {
			if (ignoreWrongRow) {
				return null;
			} else {
				throw new PolarBearException("Bad formed row");
			}
		}
	}
}
