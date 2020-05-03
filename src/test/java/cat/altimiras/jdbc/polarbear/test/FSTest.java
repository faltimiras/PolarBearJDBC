package cat.altimiras.jdbc.polarbear.test;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FSTest {

	@Test
	public void selectNoResult() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f1 FROM test_table1 where ts > '1970-01-01'";
		ResultSet rs = stmt.executeQuery(sql);

		//Do not find any data because more than 5 steps without data (step 5 defined in test_table1.json)
		assertFalse(rs.next());
	}

	@Test
	public void selectLower() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f1 FROM test_table1 where ts > '2019-01-01 12:59'";
		ResultSet rs = stmt.executeQuery(sql);

		assertTrue(rs.next());
		assertEquals("1301-1-1", rs.getString(1));
		assertFalse(rs.next());
	}

	@Test
	public void selectUpper() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f1 FROM test_table1 where ts < '2019-01-01 13:00'";
		ResultSet rs = stmt.executeQuery(sql);

		assertTrue(rs.next());
		assertEquals("1259-1-1", rs.getString(1));
		assertTrue(rs.next());
		assertEquals("1259-1-2", rs.getString(1));
		assertTrue(rs.next());
		assertEquals("1259-1-3", rs.getString(1));
		assertTrue(rs.next());
		assertEquals("1259-2-1", rs.getString(1));
		assertTrue(rs.next());
		assertEquals("1259-2-2", rs.getString(1));
		assertTrue(rs.next());
		assertEquals("1259-2-3", rs.getString(1));
		assertFalse(rs.next());
	}

	@Test
	public void selectRange() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f1 FROM test_table1 where ts >= '2019-01-01 13:00' and  ts < '2019-01-01 13:01'";
		ResultSet rs = stmt.executeQuery(sql);

		assertFalse(rs.next());
	}

	@Test
	public void selectAll2() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f2, f1, f3 as alias3 FROM test_table2 WHERE ts > '2019-01-01'";
		ResultSet rs = stmt.executeQuery(sql);

		assertTrue(rs.next());
		assertEquals("111111111", rs.getString(1));
		assertEquals("111111111", rs.getString("f2"));
		assertEquals("aaa1", rs.getString(2));
		assertEquals("aaa1", rs.getString("f1"));
		assertEquals("11.11", rs.getString(3));
		assertEquals("11.11", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("222222222", rs.getString(1));
		assertEquals("222222222", rs.getString("f2"));
		assertEquals("bbb1", rs.getString(2));
		assertEquals("bbb1", rs.getString("f1"));
		assertEquals("22.22", rs.getString(3));
		assertEquals("22.22", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("333333333", rs.getString(1));
		assertEquals("333333333", rs.getString("f2"));
		assertEquals("ccc1", rs.getString(2));
		assertEquals("ccc1", rs.getString("f1"));
		assertEquals("33.33", rs.getString(3));
		assertEquals("33.33", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("444444444", rs.getString(1));
		assertEquals("444444444", rs.getString("f2"));
		assertEquals("ddd1", rs.getString(2));
		assertEquals("ddd1", rs.getString("f1"));
		assertEquals("44.44", rs.getString(3));
		assertEquals("44.44", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("555555555", rs.getString(1));
		assertEquals("555555555", rs.getString("f2"));
		assertEquals("eee1", rs.getString(2));
		assertEquals("eee1", rs.getString("f1"));
		assertEquals("55.55", rs.getString(3));
		assertEquals("55.55", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("666666666", rs.getString(1));
		assertEquals("666666666", rs.getString("f2"));
		assertEquals("fff1", rs.getString(2));
		assertEquals("fff1", rs.getString("f1"));
		assertEquals("66.66", rs.getString(3));
		assertEquals("66.66", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("777777777", rs.getString(1));
		assertEquals("777777777", rs.getString("f2"));
		assertEquals("ggg1", rs.getString(2));
		assertEquals("ggg1", rs.getString("f1"));
		assertEquals("77.77", rs.getString(3));
		assertEquals("77.77", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("888888888", rs.getString(1));
		assertEquals("888888888", rs.getString("f2"));
		assertEquals("hhh1", rs.getString(2));
		assertEquals("hhh1", rs.getString("f1"));
		assertEquals("88.88", rs.getString(3));
		assertEquals("88.88", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("999999999", rs.getString(1));
		assertEquals("999999999", rs.getString("f2"));
		assertEquals("iii1", rs.getString(2));
		assertEquals("iii1", rs.getString("f1"));
		assertEquals("99.99", rs.getString(3));
		assertEquals("99.99", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("101010101", rs.getString(1));
		assertEquals("101010101", rs.getString("f2"));
		assertEquals("jjj1", rs.getString(2));
		assertEquals("jjj1", rs.getString("f1"));
		assertEquals("10.10", rs.getString(3));
		assertEquals("10.10", rs.getString("alias3"));
		assertTrue(rs.next());
		assertEquals("111111111", rs.getString(1));
		assertEquals("111111111", rs.getString("f2"));
		assertEquals("kkk1", rs.getString(2));
		assertEquals("kkk1", rs.getString("f1"));
		assertEquals("11.11", rs.getString(3));
		assertEquals("11.11", rs.getString("alias3"));
		assertFalse(rs.next());
	}

	@Test
	public void selectWithStar() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT t3.b, s1.f2 FROM test_table3 t3, test_star_table1 s1 WHERE t3.a=s1.f1 AND ts > '2020-03-15'";
		ResultSet rs = stmt.executeQuery(sql);

		assertTrue(rs.next());
		assertEquals("b", rs.getString(1));
		assertEquals("1.1", rs.getString(2));
		assertTrue(rs.next());
		assertEquals("c", rs.getString(1));
		assertEquals("1.1", rs.getString(2));
		assertTrue(rs.next());
		assertEquals("a", rs.getString(1));
		assertEquals("1.1", rs.getString(2));
		assertTrue(rs.next());
		assertEquals("a", rs.getString(1));
		assertEquals("2", rs.getString(2));
		assertTrue(rs.next());
		assertEquals("e", rs.getString(1));
		assertEquals("15", rs.getString(2));
		assertFalse(rs.next());
	}
}