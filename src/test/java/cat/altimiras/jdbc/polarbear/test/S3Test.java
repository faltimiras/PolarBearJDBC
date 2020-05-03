package cat.altimiras.jdbc.polarbear.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class S3Test {
	@Test
	public void selectAll() throws Exception {
		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager
			.getConnection("jdbc:polarbear:s3//s3.eu-west-1.amazonaws.com/alti-test/", "", "");
		Statement stmt = conn.createStatement();
		String sql = "SELECT f1 FROM table1 where ts < '2019-01-01 13:00'";
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
}