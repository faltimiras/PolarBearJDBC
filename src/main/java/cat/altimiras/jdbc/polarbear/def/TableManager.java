package cat.altimiras.jdbc.polarbear.def;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public abstract class TableManager {

	private ObjectMapper objectMapper = new ObjectMapper();

	private Map<String, TableDefinition> definitions = new HashMap<>();

	public TableDefinition getTable(String name) throws PolarBearException {

		try {
			TableDefinition definition = definitions.get(name);
			if (definition == null) {
				byte[] json = read(name);
				definition = objectMapper.readValue(json, TableDefinition.class);
				definitions.put(name, definition);
			}
			return definition;
		} catch (Exception e) {
			throw new PolarBearException("Error retrieving table metadata", e);
		}
	}

	protected abstract byte[] read(String name) throws PolarBearException;

	protected abstract void write(String name, byte[] json) throws PolarBearException;

}
