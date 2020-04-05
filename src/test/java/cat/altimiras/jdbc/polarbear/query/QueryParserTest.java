package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.def.FSTableManager;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryParserTest {

	private TableManager tableManager;

	@Before
	public void setUp() {
		//step = 1
		tableManager = new FSTableManager(Paths.get("src/test/resources/fs"));
	}

	@Test
	public void selectStar() throws Exception {

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from test_table2");

		assertEquals("test_table2", query.getTable());
		assertNull(query.getFields());
	}

	@Test
	public void selectFields() throws Exception {
//TODO hauria de validar fields
		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select f1, f2 AS a2, f3 from test_table2");

		assertEquals(3, query.getFields().size());
		assertEquals("test_table2", query.getTable());
		assertEquals("f1", query.getFields().get(0).getName());
		assertNull(query.getFields().get(0).getAlias());
		assertEquals("f2", query.getFields().get(1).getName());
		assertEquals("a2", query.getFields().get(1).getAlias());
		assertEquals("f3", query.getFields().get(2).getName());
		assertNull(query.getFields().get(2).getAlias());
	}

	@Test
	public void dbTableAndAlias() throws Exception {

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from db.theTable as test_table2");

		assertEquals("test_table2", query.getTable());
		assertNull(query.getFields());
	}

	@Test
	public void withUpperLimit() throws Exception{

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from db.test_table2 where ts < '2019-01-01 13:00'");

		assertEquals("test_table2", query.getTable());
		assertEquals("2019-01-01T12:59", query.getTsUpLimit().toString());
	}

	@Test
	public void withUpperLimitOtherOrder() throws Exception{

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from db.test_table2 where '2019-01-01 13:00' > ts");

		assertEquals("test_table2", query.getTable());
		assertEquals("2019-01-01T12:59", query.getTsUpLimit().toString());
	}

	@Test
	public void withUpperAndLowerLimit() throws Exception{

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from db.test_table2 where ts <= '2019-01-01 13:00' AND ts >= '2019-01-01 12:00' ");

		assertEquals("test_table2", query.getTable());
		assertEquals("2019-01-01T13:00", query.getTsUpLimit().toString());
		assertEquals("2019-01-01T12:00", query.getTsLowerLimit().toString());
	}

	@Test
	public void withLowerLimit() throws Exception{

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select * from db.test_table2 where ts > '2019-01-01 12:00' ");

		assertEquals("test_table2", query.getTable());
		assertEquals("2019-01-01T12:01", query.getTsLowerLimit().toString());
	}

	@Test
	public void otherFilters() throws Exception{

		QueryParser parser = new QueryParser(tableManager);
		Query query = parser.parse("select field3 from db.test_table2 where field = \"lolo\" AND field2=3 ");

		assertEquals("test_table2", query.getTable());
		assertNull(query.getTsUpLimit());
		assertNull(query.getTsLowerLimit());
		assertEquals(1, query.getFields().size());
		assertEquals("field3", query.getFields().get(0).getName());
		assertNull(query.getFields().get(0).getAlias());
	}

}
