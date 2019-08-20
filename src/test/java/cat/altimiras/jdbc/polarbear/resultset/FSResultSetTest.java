package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.def.Column;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.format.RowFormatter;
import cat.altimiras.jdbc.polarbear.statement.DirsIterator;
import org.junit.Test;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;


public class FSResultSetTest {

	//This is not a real unit test. But mocking Files it is "impossible" to test it properly and understandable
	@Test
	public void realTest() throws Exception {

		TableDefinition tableDefinition = new TableDefinition();

		DirsIterator dirsIterator = new DirsIterator(Paths.get("src/test/resources/fs/test-table1"),
				LocalDateTime.of(2019, 01, 01, 12, 58),
				LocalDateTime.of(2019, 01, 01, 13, 05), 1, 5);

		FSResultSet fsResultSet = new FSResultSet(tableDefTest(), dirsIterator);

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

		Column column = new Column();
		column.setName("first");
		column.setType("string");

		tableDefinition.setColumns(Arrays.asList(column));

		return tableDefinition;
	}

}

