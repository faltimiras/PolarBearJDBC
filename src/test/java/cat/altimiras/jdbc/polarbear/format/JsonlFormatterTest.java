package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.def.Column;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class JsonlFormatterTest {

	@Test
	public void ok() throws Exception {
		JsonlFormatter formatter = new JsonlFormatter(tableDefTest(false));
		Object[] parsed = formatter.parse("{\"first\" : \"a\", \"second\" : \"b\", \"third\" : 1 }", null);
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

		Column c1 = new Column("first", "string");
		Column c2 = new Column("second", "sstring");
		Column c3 = new Column("third", "int");

		tableDefinition.setColumns(Arrays.asList(c1, c2, c3));

		return tableDefinition;
	}

	@Test
	public void filter() throws Exception {

		Map byName = new HashMap();
		byName.put("first", 0);

		JsonlFormatter formatter = new JsonlFormatter(tableDefTest(false));
		Object[] parsed = formatter.parse("{\"first\" : \"\", \"second\" : \"b\", \"third\" : 1 }", byName);
		assertEquals(1, parsed.length);
		assertEquals("", parsed[0]);
	}

	@Test
	public void filter2() throws Exception {

		Map byName = new LinkedHashMap();
		byName.put("first", 0);
		byName.put("third", 2);

		JsonlFormatter formatter = new JsonlFormatter(tableDefTest(false));
		Object[] parsed = formatter.parse("{\"first\" : \"a\", \"second\" : \"b\", \"third\" : 1 }", byName);
		assertEquals(2, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("1", parsed[1]);
	}
}
