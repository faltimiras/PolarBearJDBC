package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.def.Column;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CSVFormatterTest {

	@Test
	public void ok() throws Exception{
		CSVFormatter formatter = new CSVFormatter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,b,1");
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyMiddle() throws Exception{
		CSVFormatter formatter = new CSVFormatter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,,1");
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyBeginning() throws Exception{
		CSVFormatter formatter = new CSVFormatter(tableDefTest(",", false));
		Object[] parsed = formatter.parse(",b,1");
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("1", parsed[2]);
	}

	@Test
	public void emptyEnd() throws Exception{
		CSVFormatter formatter = new CSVFormatter(tableDefTest(",", false));
		Object[] parsed = formatter.parse("a,b,");
		assertEquals(3, parsed.length);
		assertEquals("a", parsed[0]);
		assertEquals("b", parsed[1]);
		assertEquals("", parsed[2]);
	}

	@Test
	public void nulls() throws Exception{
		CSVFormatter formatter = new CSVFormatter(tableDefTest(",", false));
		Object[] parsed = formatter.parse(",,");
		assertEquals(3, parsed.length);
		assertEquals("", parsed[0]);
		assertEquals("", parsed[1]);
		assertEquals("", parsed[2]);
	}

	private TableDefinition tableDefTest(String separator, boolean ignore) {

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setName("test");
		tableDefinition.setFormat("csv");
		tableDefinition.setSeparator(separator);
		tableDefinition.setIgnoreWrongRowData(ignore);

		Column c1 = new Column("first", "string");
		Column c2 = new Column("second", "string");
		Column c3 = new Column("third", "string");

		tableDefinition.setColumns(Arrays.asList(c1,c2,c3));

		return tableDefinition;
	}

}
