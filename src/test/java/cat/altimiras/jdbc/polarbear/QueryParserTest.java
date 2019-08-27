package cat.altimiras.jdbc.polarbear;

import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.QueryParser;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class QueryParserTest {

	@Test
	public void validStar() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("select     * from    table");
		assertEquals("table", query.getTable());
		assertNull(query.getFields());
		assertNull(query.getTsLowerLimit());
		assertNull(query.getTsUpLimit());
	}

	@Test
	public void alias() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("Select field1 AS Alias1 from table");
		assertEquals("table", query.getTable());
		assertEquals("field1", query.getFields().get(0).getName());
		assertEquals("Alias1", query.getFields().get(0).getAlias());
		assertNull(query.getTsLowerLimit());
		assertNull(query.getTsUpLimit());
	}

	@Test
	public void multipleAlias() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("Select field1 AS     Alias1, field2 , field3     as alias3 from table");
		assertEquals("table", query.getTable());
		assertEquals("field1", query.getFields().get(0).getName());
		assertEquals("Alias1", query.getFields().get(0).getAlias());
		assertEquals("field2", query.getFields().get(1).getName());
		assertNull(query.getFields().get(1).getAlias());
		assertEquals("field3", query.getFields().get(2).getName());
		assertEquals("alias3", query.getFields().get(2).getAlias());
		assertNull(query.getTsLowerLimit());
		assertNull(query.getTsUpLimit());
	}

	@Test
	public void upperLimit() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("Select * from table Where ts <    '2019-01-01 13:00' ");
		assertEquals("table", query.getTable());
		assertNull(query.getFields());
		assertNull(query.getTsLowerLimit());
		assertEquals("2019-01-01T12:59", query.getTsUpLimit().toString());
	}

	@Test
	public void lowerLimit() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("Select * from table Where ts    > '2019-01-01' ");
		assertEquals("table", query.getTable());
		assertNull(query.getFields());
		assertNull(query.getTsUpLimit());
		assertEquals("2019-01-01T00:01", query.getTsLowerLimit().toString());
	}

	@Test
	public void limits() throws Exception {

		QueryParser queryParser = new QueryParser();
		Query query = queryParser.parse("Select * from table Where ts => '2019-01-01 00:01' and    ts < '2019-12-01' ");
		assertEquals("table", query.getTable());
		assertNull(query.getFields());
		assertEquals("2019-01-01T00:01", query.getTsLowerLimit().toString());
		assertEquals("2019-11-30T23:59", query.getTsUpLimit().toString());
	}
}
