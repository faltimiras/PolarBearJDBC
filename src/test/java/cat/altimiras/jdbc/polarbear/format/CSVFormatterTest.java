package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class CSVFormatterTest {
/*
	@Test
	public void ok() throws Exception {
		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,b,1", null);
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	private TableDefinition tableDefTest(String separator, boolean ignore) {

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setName("test");
		tableDefinition.setFormat("csv");
		tableDefinition.setSeparator(separator);
		tableDefinition.setIgnoreWrongRowData(ignore);

		ColumnDefinition c1 = new ColumnDefinition("first", ColumnDefinition.Type.TEXT );
		ColumnDefinition c2 = new ColumnDefinition("second", ColumnDefinition.Type.TEXT );
		ColumnDefinition c3 = new ColumnDefinition("third", ColumnDefinition.Type.TEXT );

		tableDefinition.setColumnDefinitions(Arrays.asList(c1, c2, c3));

		return tableDefinition;
	}

	@Test
	public void emptyMiddle() throws Exception {
		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,,1", null);
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyBeginning() throws Exception {
		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse(",b,1", null);
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyEnd() throws Exception {
		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,b,", null);
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("", parsed[2]);
	}

	@Test
	public void nulls() throws Exception {
		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse(",,", null);
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("", parsed[2]);
	}

	@Test
	public void filter() throws Exception {

		Map byName = new HashMap();
		byName.put("first", 0);

		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse(",,", byName);
		assertEquals(1, parsed.length);
		assertEquals("", parsed[0]);
	}

	@Test
	public void filter2() throws Exception {

		Map byName = new LinkedHashMap();
		byName.put("first", 0);
		byName.put("third", 2);

		CSVConverter formatter = new CSVConverter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,b,c", byName);
		assertEquals(2, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("c", parsed[1]);
	}

 */
}
