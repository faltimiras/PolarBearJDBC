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
	public void selectAll() throws Exception {

		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT field FROM test-table1";
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
		assertTrue(rs.next());
		assertEquals("1301-1-1", rs.getString(1));
		assertFalse(rs.next());
	}

	@Test
	public void selectLower() throws Exception {

		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection("jdbc:polarbear:fs//src/test/resources/fs/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT field FROM test-table1 where ts > '2019-01-01 12:59'";
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
		String sql = "SELECT field FROM test-table1 where ts < '2019-01-01 13:00'";
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
		String sql = "SELECT field FROM test-table1 where ts => '2019-01-01 13:00' and  ts < '2019-01-01 13:01'";
		ResultSet rs = stmt.executeQuery(sql);

		assertFalse(rs.next());
	}

}
