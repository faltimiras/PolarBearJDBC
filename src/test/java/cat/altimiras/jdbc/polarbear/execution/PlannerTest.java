package cat.altimiras.jdbc.polarbear.execution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.PartitionDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;

public class PlannerTest {
	private TableManager tableManager = mock(TableManager.class);

	private TableDefinition tableDef;

	@Before
	public void setUp() throws Exception {
		tableDef = new TableDefinition("table");
		tableDef.setPartition(new PartitionDefinition("ts", null, null, 1));
		tableDef.setColumnDefinitions(Arrays.asList());

		when(tableManager.getTable("table")).thenReturn(tableDef);
	}

	@Test
	public void lowerLimit() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table", "ts", null, tableDef));
		expr.setOperation(">");
		expr.setOperand2("2020/12/12 12:12");

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(expr);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:13", planner.getTsLowerLimit().toString());
	}

	@Test
	public void lowerEqualLimit() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table", "ts", null, tableDef));
		expr.setOperation(">=");
		expr.setOperand2("2020/12/12 12:12");

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(expr);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsLowerLimit().toString());
	}

	@Test
	public void lowerEqualLimitOrder() throws Exception {
		Expr comparator = new Expr();
		comparator.setOperand1("12");
		comparator.setOperation("=");
		comparator.setOperand2(new Field("table", "field", null, tableDef));

		Expr tsExpr = new Expr();
		tsExpr.setOperand1("2020/12/12 12:12");
		tsExpr.setOperation("<=");
		tsExpr.setOperand2(new Field("table", "ts", null, tableDef));

		Expr and = new Expr();
		and.setOperand1(comparator);
		and.setOperation("AND");
		and.setOperand2(tsExpr);

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(and);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsLowerLimit().toString());
	}

	@Test
	public void upperLimit() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table", "ts", null, tableDef));
		expr.setOperation("<");
		expr.setOperand2("2020/12/12 12:12");

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(expr);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:11", planner.getTsUpperLimit().toString());
	}

	@Test
	public void upperEqualLimit() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table", "ts", null, tableDef));
		expr.setOperation("<=");
		expr.setOperand2("2020/12/12 12:12");

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(expr);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void upperEqualLimitOrder() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("2020/12/12 12:12");
		expr.setOperation(">=");
		expr.setOperand2(new Field("table", "ts", null, tableDef));

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(expr);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void limits() throws Exception {
		Expr tsExpr1 = new Expr();
		tsExpr1.setOperand1("2020/12/12 12:12");
		tsExpr1.setOperation(">=");
		tsExpr1.setOperand2(new Field("table", "ts", null, tableDef));

		Expr tsExpr2 = new Expr();
		tsExpr2.setOperand1(new Field("table", "ts", null, tableDef));
		tsExpr2.setOperation(">");
		tsExpr2.setOperand2("2000/12/12");

		Expr and = new Expr();
		and.setOperand1(tsExpr1);
		and.setOperation("AND");
		and.setOperand2(tsExpr2);

		Query query = mock(Query.class);
		when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
		when(query.getWhere()).thenReturn(and);

		Planner planner = new Planner(tableManager, query);

		assertEquals("2000-12-12T00:01", planner.getTsLowerLimit().toString());
		assertEquals("2020-12-12T12:12", planner.getTsUpperLimit().toString());
	}

	@Test
	public void notLimit() {
		try {
			Expr expr = new Expr();
			expr.setOperand1("12");
			expr.setOperation(">=");
			expr.setOperand2(new Field("table", "field", null, tableDef));

			Query query = mock(Query.class);
			when(query.getTables()).thenReturn(Arrays.asList(new Table("table", null, tableDef)));
			when(query.getWhere()).thenReturn(expr);

			new Planner(tableManager, query);
			fail("Must fail. No window defined");
		} catch (PolarBearException e) {
			assertEquals("Not time window defined, full scan not allowed", e.getMessage());
		}
	}
}