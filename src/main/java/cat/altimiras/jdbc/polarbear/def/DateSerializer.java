package cat.altimiras.jdbc.polarbear.def;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class DateSerializer extends /*StdSerializer<LocalDateTime>,*/ StdDeserializer<LocalDateTime> {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

	public DateSerializer() {
		this(null);
	}

	public DateSerializer(Class<LocalDateTime> t) {
		super(t);
	}

	@Override
	public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
		String date = jsonParser.getText();
		return LocalDateTime.from(formatter.parse(date));
	}
/*
	@Override
	public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider arg2) throws IOException, JsonProcessingException {
		gen.writeString(formatter.format(value));
	}
*/

}
