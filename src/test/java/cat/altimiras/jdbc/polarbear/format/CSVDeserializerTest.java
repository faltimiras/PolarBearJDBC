package cat.altimiras.jdbc.polarbear.format;

import static org.junit.Assert.assertEquals;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import java.util.Arrays;
import org.junit.Test;

public class CSVDeserializerTest {
	@Test
	public void ok() throws Exception {
		CSVDeserializer deserializer = new CSVDeserializer(tableDefTest(",", false));
		Object[] parsed = deserializer.parse("a,b,1".getBytes());
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

		ColumnDefinition c1 = new ColumnDefinition("first", ColumnDefinition.Type.TEXT);
		ColumnDefinition c2 = new ColumnDefinition("second", ColumnDefinition.Type.TEXT);
		ColumnDefinition c3 = new ColumnDefinition("third", ColumnDefinition.Type.TEXT);

		tableDefinition.setColumnDefinitions(Arrays.asList(c1, c2, c3));

		return tableDefinition;
	}

	@Test
	public void emptyMiddle() throws Exception {
		CSVDeserializer deserializer = new CSVDeserializer(tableDefTest(",", false));
		Object[] parsed = deserializer.parse("a,,1".getBytes());
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyBeginning() throws Exception {
		CSVDeserializer deserializer = new CSVDeserializer(tableDefTest(",", false));
		Object[] parsed = deserializer.parse(",b,1".getBytes());
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyEnd() throws Exception {
		CSVDeserializer deserializer = new CSVDeserializer(tableDefTest(",", false));
		Object[] parsed = deserializer.parse("a,b,".getBytes());
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("", parsed[2]);
	}

	@Test
	public void nulls() throws Exception {
		CSVDeserializer deserializer = new CSVDeserializer(tableDefTest(",", false));
		Object[] parsed = deserializer.parse(",,".getBytes());
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("", parsed[2]);
	}
}