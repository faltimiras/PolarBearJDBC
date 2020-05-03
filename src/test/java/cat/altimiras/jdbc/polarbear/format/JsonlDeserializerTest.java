package cat.altimiras.jdbc.polarbear.format;

import static org.junit.Assert.assertEquals;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import java.util.Arrays;
import org.junit.Test;

public class JsonlDeserializerTest {
	@Test
	public void ok() throws Exception {
		JsonlDeserializer deserializer = new JsonlDeserializer(tableDefTest(false));
		String[] parsed = deserializer.parse("{\"first\" : \"a\", \"second\" : \"b\", \"third\" : 1 }".getBytes());
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	private TableDefinition tableDefTest(boolean ignore) {

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setName("test");
		tableDefinition.setFormat("json");
		tableDefinition.setIgnoreWrongRowData(ignore);

		ColumnDefinition c1 = new ColumnDefinition("first", ColumnDefinition.Type.TEXT);
		ColumnDefinition c2 = new ColumnDefinition("second", ColumnDefinition.Type.TEXT);
		ColumnDefinition c3 = new ColumnDefinition("third", ColumnDefinition.Type.LONG);

		tableDefinition.setColumnDefinitions(Arrays.asList(c1, c2, c3));

		return tableDefinition;
	}
}
