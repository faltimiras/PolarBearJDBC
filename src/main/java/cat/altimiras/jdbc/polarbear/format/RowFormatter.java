package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;

import java.util.Map;

public abstract class RowFormatter {

	final protected TableDefinition tableDefinition;

	public RowFormatter(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public abstract Object[] parse(String raw, Map<String, Integer> fieldsByName) throws PolarBearException;

}
