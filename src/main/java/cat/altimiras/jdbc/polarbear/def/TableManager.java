package cat.altimiras.jdbc.polarbear.def;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.io.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public abstract class TableManager {
	protected Map<String, TableDefinition> definitions = new HashMap<>();

	private final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * Gets table definition from the source
	 * @param name
	 * @return
	 * @throws PolarBearException
	 */
	public TableDefinition getTable(String name) throws PolarBearException {

		try {
			TableDefinition definition = definitions.get(name);
			if (definition == null) {
				byte[] json = readDefinition(name);
				definition = objectMapper.readValue(json, TableDefinition.class);
				definitions.put(name, definition);
			}
			return definition;
		} catch (IOException e) {
			throw new PolarBearException("Error retrieving table metadata", e);
		}
	}

	protected abstract byte[] readDefinition(String name) throws PolarBearException;
	protected abstract void writeDefinition(String name, byte[] json) throws PolarBearException;
	/**
	 * Reads the content of the table
	 * @return
	 * @throws PolarBearException
	 */
	public abstract Reader read(String name, LocalDateTime from, LocalDateTime to, long maxRows)
		throws PolarBearException;
}
