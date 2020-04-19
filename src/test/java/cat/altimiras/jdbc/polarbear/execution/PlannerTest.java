package cat.altimiras.jdbc.polarbear.execution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.PartitionDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.execution.Planner;
import cat.altimiras.jdbc.polarbear.query.Parser;
import cat.altimiras.jdbc.polarbear.query.Query;
import org.junit.Before;
import org.junit.Test;

public class PlannerTest {
	private TableManager tableManager = mock(TableManager.class);

	private Parser parser = new Parser();

	private TableDefinition mainTable;

	@Before
	public void setUp() throws Exception {
		mainTable = new TableDefinition();
		mainTable.setName("table");
		PartitionDefinition partitionDefinition = new PartitionDefinition();
		partitionDefinition.setColumnName("ts");
		mainTable.setPartition(partitionDefinition);

		when(tableManager.getTable("table")).thenReturn(mainTable);
	}

	@Test
	public void lowerLimit() throws Exception {

		Query query = parser.parse("select * from table where ts >'2020/12/12 12:12' and a=12");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:13", planner.getTsLowerLimit().toString());
	}

	@Test
	public void lowerEqualLimit() throws Exception {

		Query query = parser.parse("select * from table where a=12 and ts >='2020/12/12 12:12' ");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsLowerLimit().toString());
	}

	@Test
	public void lowerEqualLimitOrder() throws Exception {

		Query query = parser.parse("select * from table where a=12 and '2020/12/12 12:12' <= ts ");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsLowerLimit().toString());
	}

	@Test
	public void upperLimit() throws Exception {

		Query query = parser.parse("select * from table where a=12 and ts <'2020/12/12 12:12' ");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:11", planner.getTsUpperLimit().toString());
	}

	@Test
	public void upperEqualLimit() throws Exception {

		Query query = parser.parse("select * from table where a=12 and ts <='2020/12/12 12:12' ");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void upperEqualLimitOrder() throws Exception {

		Query query = parser.parse("select * from table where a=12 and '2020/12/12 12:12' >= ts ");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void limits() throws Exception {

		Query query = parser
			.parse("select * from table where a=12 and '2020/12/12 12:12' >= ts AND  ts > '2000/12/12'");
		Planner planner = new Planner(tableManager, query);

		assertEquals("2000-12-12T00:01", planner.getTsLowerLimit().toString());
		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void notLimit() throws Exception {
		try {
			Query query = parser.parse("select * from table");
			new Planner(tableManager, query);
			fail("Must fail. No window defined");
		} catch (PolarBearException e) {
			assertEquals("Not time window defined, full scan not allowed", e.getMessage());
		}
	}
}