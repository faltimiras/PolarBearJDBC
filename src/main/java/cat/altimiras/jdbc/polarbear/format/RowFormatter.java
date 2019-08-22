package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;

import java.util.List;

public abstract class RowFormatter {

	final protected TableDefinition tableDefinition;

	public RowFormatter(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public abstract Object[] parse (String raw, List<Field> fields) throws PolarBearException;

}
