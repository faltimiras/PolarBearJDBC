package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonlDeserializer extends RowDeserializer {
	private final ObjectMapper objectMapper;

	public JsonlDeserializer(TableDefinition tableDefinition) {
		super(tableDefinition);

		objectMapper = new ObjectMapper();
		SimpleModule module = new SimpleModule();
		module.addDeserializer(String[].class, new RowDeserializer(tableDefinition));
		objectMapper.registerModule(module);
	}

	@Override
	public String[] parse(byte[] raw) throws PolarBearException {
		try {
			return objectMapper.readValue(raw, String[].class);
		} catch (Exception e) {
			return null;
		}
	}

	private static class RowDeserializer extends StdDeserializer<String[]> {
		private final List<ColumnDefinition> columnDefinitions;

		protected RowDeserializer(TableDefinition tableDefinition) {
			super(String[].class);
			this.columnDefinitions = new ArrayList<>(tableDefinition.getColumnsByName().values());
		}

		@Override
		public String[] deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
			throws IOException {
			String[] row = new String[columnDefinitions.size()];
			JsonNode node = jsonParser.getCodec().readTree(jsonParser);
			for (int i = 0; i < row.length; i++) {
				row[i] = node.get(columnDefinitions.get(i).getName()).asText();
			}
			return row;
		}
	}
}