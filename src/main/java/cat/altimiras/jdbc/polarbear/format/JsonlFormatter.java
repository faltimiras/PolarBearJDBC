package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.Column;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.Map;


public class JsonlFormatter extends RowFormatter {

	private final int columns;

	private final boolean ignoreWrongRow;

	private final ObjectMapper objectMapper;

	public JsonlFormatter(TableDefinition tableDefinition) {
		super(tableDefinition);
		this.columns = tableDefinition.getColumns().size();
		this.ignoreWrongRow = tableDefinition.isIgnoreWrongRowData();

		this.objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(String[].class, new Deserializer());
		objectMapper.registerModule(module);
	}

	@Override
	public Object[] parse(String raw, Map<String, Integer> queryFieldsByName) throws PolarBearException {
		if (raw == null) {
			return null;
		}

		try {
			String[] values = objectMapper.readValue(raw, String[].class);
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
		} catch (Exception e) {
			throw new PolarBearException("Bad formed row", e);
		}
	}

	private String[] filter(String[] values, Map<String, Integer> queryFieldsByName) {

		String[] filter = new String[queryFieldsByName.size()];
		int i = 0;
		for (Map.Entry<String, Integer> e : queryFieldsByName.entrySet()) {
			int pos = e.getValue();
			filter[i] = values[pos];
			i++;
		}
		return filter;
	}

	class Deserializer extends StdDeserializer<String[]> {

		public Deserializer() {
			this(null);
		}

		public Deserializer(Class<?> vc) {
			super(vc);
		}

		@Override
		public String[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {

			JsonNode node = jsonParser.getCodec().readTree(jsonParser);

			String[] values = new String[tableDefinition.getColumns().size()];
			for (int i = 0; i < tableDefinition.getColumns().size(); i++) {
				Column column = tableDefinition.getColumns().get(i);
				values[i] = node.get(column.getName()).asText();
			}
			return values;
		}
	}
}
