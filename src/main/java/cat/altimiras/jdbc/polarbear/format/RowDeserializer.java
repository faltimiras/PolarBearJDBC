package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;

import java.util.Map;

public abstract class RowDeserializer {

	final protected TableDefinition tableDefinition;

	public RowDeserializer(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	/**
	 * Converts raw to array of elements
	 * @param raw
	 * @return
	 * @throws PolarBearException
	 */
	public abstract String[] parse(byte[] raw) throws PolarBearException;

}
