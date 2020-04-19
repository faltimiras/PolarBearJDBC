package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.statement.DirsIterator;
import org.junit.Test;

import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FSResultSetTest {
/*
	//This is not a real unit test. But mocking Files it is "impossible" to test it properly and understandable
	@Test
	public void realTest() throws Exception {

		Statement statement = mock(Statement.class);

		DirsIterator dirsIterator = new DirsIterator(Paths.get("src/test/resources/fs/test_table1"),
				LocalDateTime.of(2019, 01, 01, 12, 58),
				LocalDateTime.of(2019, 01, 01, 13, 05),
				"YYYY/MM/dd/HH/mm",
				1,
				5);

		FSResultSet fsResultSet = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), dirsIterator, statement);

		assertTrue(fsResultSet.next());
		assertEquals("1259-1-1", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-1-2", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-1-3", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-2-1", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-2-2", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-2-3", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1301-1-1", fsResultSet.getString(1));
		assertFalse(fsResultSet.next());
	}

	private TableDefinition tableDefTest() {

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setName("test");
		tableDefinition.setFormat("csv");

		ColumnDefinition columnDefinition = new ColumnDefinition("first", ColumnDefinition.Type.TEXT );
		tableDefinition.setColumnDefinitions(Arrays.asList(columnDefinition));

		return tableDefinition;
	}

	@Test
	public void types() throws Exception {
/*
		ColumnDefinition c1 = new ColumnDefinition("first", "string");
		ColumnDefinition c2 = new ColumnDefinition("second", "int");
		ColumnDefinition c3 = new ColumnDefinition("third", "long");
		ColumnDefinition c4 = new ColumnDefinition("4th", "double");
		ColumnDefinition c5 = new ColumnDefinition("5th", "date");
		ColumnDefinition c6 = new ColumnDefinition("6th", "time");
		ColumnDefinition c7 = new ColumnDefinition("7th", "timestamp");
		ColumnDefinition c8 = new ColumnDefinition("8th", "float");
		ColumnDefinition c9 = new ColumnDefinition("9th", "boolean");
		ColumnDefinition c10 = new ColumnDefinition("10th", "bigdecimal");
		ColumnDefinition c11 = new ColumnDefinition("11th", "bytes");
		ColumnDefinition c12 = new ColumnDefinition("12th", "short");
		ColumnDefinition c13 = new ColumnDefinition("13th", "byte");
		ColumnDefinition c14 = new ColumnDefinition("14th", "date");
		c14.setUnixtime(true);
		ColumnDefinition c15 = new ColumnDefinition("15th", "time");
		c15.setUnixtime(true);
		ColumnDefinition c16 = new ColumnDefinition("16th", "timestamp");
		c16.setUnixtime(true);
		ColumnDefinition c17 = new ColumnDefinition("17th", "date");
		c17.setDateFormat("yyyy#MM#dd");
		ColumnDefinition c18 = new ColumnDefinition("18th", "time");
		c18.setTimeFormat("mm@HH");
		ColumnDefinition c19 = new ColumnDefinition("19th", "timestamp");
		c19.setTimestampFormat("ss::mm::HH-yyyy#MM#dd");

		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setName("test");
		tableDefinition.setFormat("csv");
		tableDefinition.setColumnDefinitions(Arrays.asList(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, c17, c18, c19));

		FSResultSet rs = new FSResultSet(
				Arrays.asList(
						new Field("test_table1","first", null),
						new Field("test_table1","second", null),
						new Field("test_table1","third", null),
						new Field("test_table1","4th", null),
						new Field("test_table1","5th", null),
						new Field("test_table1","6th", null),
						new Field("test_table1","7th", null),
						new Field("test_table1","8th", null),
						new Field("test_table1","9th", null),
						new Field("test_table1","10th", null),
						new Field("test_table1","11th", null),
						new Field("test_table1","12th", null),
						new Field("test_table1","13th", null),
						new Field("test_table1","14th", null),
						new Field("test_table1","15th", null),
						new Field("test_table1","16th", null),
						new Field("test_table1","17th", null),
						new Field("test_table1","18th", null),
						new Field("test_table1","19th", null)
				),
				tableDefinition,
				null,
				null);
		rs.setCurrentRow(new String[]{"string", "1", "2", "1.1", "2020/10/10", "12:30:02", "2019/01/01 05:23:55", "2.2", "true", "3.141592653589793238462643383279502884197169399375105820974944592307816406286", "bytes", "32", "6", "1566415210208", "100000", "1566415210208", "2020#05#16", "23@45", "22::33::23-1980#05#01"});


		assertEquals("string", rs.getString(1));
		assertEquals("string", rs.getString("first"));
		assertEquals("1", rs.getString(2));
		assertEquals(1, rs.getInt(2));
		assertEquals(1, rs.getInt("second"));
		assertEquals("2", rs.getString(3));
		assertEquals(2l, rs.getLong(3));
		assertEquals(2l, rs.getLong("third"));
		assertEquals("1.1", rs.getString(4));
		assertEquals(1.1d, rs.getDouble(4), 0);
		assertEquals(1.1d, rs.getDouble("4th"), 0);
		assertEquals("2020/10/10", rs.getString(5));
		assertEquals("2020-10-10", rs.getDate(5).toString());
		assertEquals("2020-10-10", rs.getDate("5th").toString());
		assertEquals("12:30:02", rs.getString(6));
		assertEquals("12:30:02", rs.getTime(6).toString());
		assertEquals("12:30:02", rs.getTime("6th").toString());
		assertEquals("2019/01/01 05:23:55", rs.getString(7));
		assertEquals("2019-01-01 05:23:55.0", rs.getTimestamp(7).toString());
		assertEquals("2019-01-01 05:23:55.0", rs.getTimestamp("7th").toString());
		assertEquals("2.2", rs.getString(8));
		assertEquals(2.2f, rs.getFloat(8), 0);
		assertEquals(2.2f, rs.getFloat("8th"), 0);
		assertEquals("true", rs.getString(9));
		assertTrue(rs.getBoolean(9));
		assertTrue(rs.getBoolean("9th"));
		assertEquals("3.141592653589793238462643383279502884197169399375105820974944592307816406286", rs.getString(10));
		assertEquals("3.141592653589793238462643383279502884197169399375105820974944592307816406286", rs.getBigDecimal(10).toString());
		assertEquals("3.141592653589793238462643383279502884197169399375105820974944592307816406286", rs.getBigDecimal("10th").toString());
		assertEquals("bytes", rs.getString(11));
		assertEquals("bytes", new String(rs.getBytes(11)));
		assertEquals("bytes", new String(rs.getBytes("11th")));
		assertEquals("32", rs.getString(12));
		assertEquals(32, rs.getShort(12));
		assertEquals(32, rs.getShort("12th"));
		assertEquals("6", rs.getString(13));
		assertEquals(6, rs.getByte(13));
		assertEquals(6, rs.getByte("13th"));
		assertEquals("2019-08-21", rs.getDate(14).toString());
		assertEquals("2019-08-21", rs.getDate("14th").toString());
		assertEquals("01:01:40", rs.getTime(15).toString());
		assertEquals("01:01:40", rs.getTime("15th").toString());
		assertEquals("2019-08-21 21:20:10.208", rs.getTimestamp(16).toString());
		assertEquals("2019-08-21 21:20:10.208", rs.getTimestamp("16th").toString());
		assertEquals("2020-05-16", rs.getDate(17).toString());
		assertEquals("2020-05-16", rs.getDate("17th").toString());
		assertEquals("21:23:00", rs.getTime(18).toString());
		assertEquals("21:23:00", rs.getTime("18th").toString());
		assertEquals("1980-05-01 23:33:22.0", rs.getTimestamp(19).toString());
		assertEquals("1980-05-01 23:33:22.0", rs.getTimestamp("19th").toString());

 */
	/*
	}

	@Test(expected = SQLException.class)
	public void notExistPosition() throws Exception {

		FSResultSet rs = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), null, null);
		rs.setCurrentRow(new String[]{"string"});

		rs.getLong(2);
	}

	@Test(expected = SQLException.class)
	public void notExistLabel() throws Exception {


		FSResultSet rs = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), null, null);
		rs.setCurrentRow(new String[]{"string"});

		rs.getString("notexist");
	}

	@Test(expected = SQLException.class)
	public void wrongType() throws Exception {

		FSResultSet rs = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), null, null);
		rs.setCurrentRow(new String[]{"string"});
		rs.getByte("first");
	}

	@Test
	public void empty() throws Exception {

		FSResultSet rs = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), null, null);
		rs.setCurrentRow(new String[]{""});

		assertEquals("", rs.getString("first"));
	}

	@Test
	public void maxRows() throws Exception {

		Statement statement = mock(Statement.class);
		when(statement.getMaxRows()).thenReturn(2);

		DirsIterator dirsIterator = new DirsIterator(Paths.get("src/test/resources/fs/test_table1"),
				LocalDateTime.of(2019, 01, 01, 12, 58),
				LocalDateTime.of(2019, 01, 01, 13, 05),
				"YYYY/MM/dd/HH/mm",
				1,
				5);

		FSResultSet fsResultSet = new FSResultSet(Arrays.asList(new Field("test_table1","first", null)), tableDefTest(), dirsIterator, statement);

		assertTrue(fsResultSet.next());
		assertEquals("1259-1-1", fsResultSet.getString(1));
		assertTrue(fsResultSet.next());
		assertEquals("1259-1-2", fsResultSet.getString(1));
		assertFalse(fsResultSet.next());
	}

	@Test
	public void alias() throws Exception {

		Statement statement = mock(Statement.class);

		DirsIterator dirsIterator = new DirsIterator(Paths.get("src/test/resources/fs/test_table1"),
				LocalDateTime.of(2019, 01, 01, 12, 58),
				LocalDateTime.of(2019, 01, 01, 13, 05),
				"YYYY/MM/dd/HH/mm",
				1,
				5);

		FSResultSet fsResultSet = new FSResultSet(Arrays.asList(new Field("test_table1","first", "alias")), tableDefTest(), dirsIterator, statement);

		assertTrue(fsResultSet.next());
		assertEquals("1259-1-1", fsResultSet.getString("alias"));
	}


	 */
}

