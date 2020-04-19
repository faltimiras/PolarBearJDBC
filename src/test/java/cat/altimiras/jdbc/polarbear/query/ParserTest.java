package cat.altimiras.jdbc.polarbear.query;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserTest {

	@Test
	public void selectStar() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select * from test_table2");

		assertEquals("test_table2", query.getTables().get(0).getName());
		assertNull(query.getTables().get(0).getAlias());
		assertNull(query.getFields());
	}

	@Test
	public void selectFields() throws Exception {
		Parser parser = new Parser();
		Query query = parser.parse("select field, test_table2.field2 AS a2, table.field3 from test_table2 AS table");

		assertEquals(3, query.getFields().size());
		assertEquals("test_table2", query.getTables().get(0).getName());
		assertEquals("table", query.getTables().get(0).getAlias());

		assertEquals("field", query.getFields().get(0).getName());
		assertNull(query.getFields().get(0).getAlias());
		//TODO quan estigui fet validacio
		//assertEquals("test_table2", query.getFields().get(0).getTable());

		assertEquals("field2", query.getFields().get(1).getName());
		assertEquals("a2", query.getFields().get(1).getAlias());
		assertEquals("test_table2", query.getFields().get(1).getTable());

		assertEquals("field3", query.getFields().get(2).getName());
		assertNull(query.getFields().get(2).getAlias());
		//TODO quan estigui fet validacio
		//assertEquals("test_table2", query.getFields().get(2).getTable());
	}

	@Test
	public void dbTableAndAlias() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select * from db.test_table2 as theTable");

		assertEquals("test_table2", query.getTables().get(0).getName());
		assertEquals("theTable", query.getTables().get(0).getAlias());
		assertNull(query.getFields());
	}

	@Test
	public void multipleTables() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select * from db.test_table2, test_star_table1 AS t1");

		assertEquals("test_table2", query.getTables().get(0).getName());
		assertNull(query.getTables().get(0).getAlias());
		assertEquals("test_star_table1", query.getTables().get(1).getName());
		assertEquals("t1", query.getTables().get(1).getAlias());
	}

	@Test
	public void singleFilters() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where f1 = 'lolo' ");

		assertEquals("test_table2", query.getTables().get(0).getName());
		assertEquals(1, query.getFields().size());
		assertEquals("field3", query.getFields().get(0).getName());
		assertEquals("test_table2", query.getFields().get(0).getTable());
		assertNull(query.getFields().get(0).getAlias());
		assertEquals("f1", ((Field) query.getWhere().getOperand1()).getName());
		assertNull(((Field) query.getWhere().getOperand1()).getTable());
		assertEquals("=", query.getWhere().getOperation());
		assertEquals("lolo", query.getWhere().getOperand2());
	}

	@Test
	public void multipleFilters() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where f1 = 'lolo' AND test_table2.f2=3 OR test_table2.f2=f3");

		/**
		 * 						OR
		 * 			AND				f2=f3
		 * f1 =lolo 	f2=3
		 */

		assertEquals("test_table2", query.getTables().get(0).getName());
		assertEquals(1, query.getFields().size());
		assertEquals("field3", query.getFields().get(0).getName());
		assertEquals("test_table2", query.getFields().get(0).getTable());
		assertNull(query.getFields().get(0).getAlias());

		assertEquals("OR", query.getWhere().getOperation());

		Expr and = query.getWhere().getOperand1AsExpr();
		assertEquals("AND", and.getOperation());
		assertEquals("f1", and.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("lolo", and.getOperand1AsExpr().getOperand2());
		assertEquals("=", and.getOperand1AsExpr().getOperation());

		assertEquals("f2", and.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", and.getOperand2AsExpr().getOperand1AsField().getTable());
		assertEquals("3", and.getOperand2AsExpr().getOperand2());
		assertEquals("=", and.getOperand2AsExpr().getOperation());

		assertEquals("f2", query.getWhere().getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", query.getWhere().getOperand2AsExpr().getOperand1AsField().getTable());
		assertEquals("f3", query.getWhere().getOperand2AsExpr().getOperand2AsField().getName());
		assertEquals("=", query.getWhere().getOperand2AsExpr().getOperation());
	}

	@Test
	public void parenthesisFilters() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where f1 = 'lolo' AND (test_table2.f2=3 OR test_table2.f2=f3)");

		/**
		 * 				AND
		 * f1 =lolo 			OR
		 * 					f2=3	f2=f3
		 */

		assertEquals("AND", query.getWhere().getOperation());
		assertEquals("f1", query.getWhere().getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("lolo", query.getWhere().getOperand1AsExpr().getOperand2());
		assertEquals("=", query.getWhere().getOperand1AsExpr().getOperation());

		Expr or = query.getWhere().getOperand2AsExpr();
		assertEquals("OR", or.getOperation());
		assertEquals("f2", or.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", or.getOperand1AsExpr().getOperand1AsField().getTable());
		assertEquals("3", or.getOperand1AsExpr().getOperand2());
		assertEquals("=", or.getOperand1AsExpr().getOperation());

		assertEquals("f2", or.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", or.getOperand2AsExpr().getOperand1AsField().getTable());
		assertEquals("f3", or.getOperand2AsExpr().getOperand2AsField().getName());
		assertEquals("=", or.getOperand2AsExpr().getOperation());
	}

	@Test
	public void parenthesisChangeOrderFilters() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where (test_table2.f2=3 OR test_table2.f2=f3) AND f1 = 'lolo'");

		/**
		 * 				AND
		 * 		OR			f1 =lolo
		 * 	f2=3	f2=f3
		 */

		assertEquals("AND", query.getWhere().getOperation());

		Expr or = query.getWhere().getOperand1AsExpr();
		assertEquals("OR", or.getOperation());
		assertEquals("f2", or.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", or.getOperand1AsExpr().getOperand1AsField().getTable());
		assertEquals("3", or.getOperand1AsExpr().getOperand2());
		assertEquals("=", or.getOperand1AsExpr().getOperation());

		assertEquals("f2", or.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("test_table2", or.getOperand2AsExpr().getOperand1AsField().getTable());
		assertEquals("f3", or.getOperand2AsExpr().getOperand2AsField().getName());
		assertEquals("=", or.getOperand2AsExpr().getOperation());

		assertEquals("f1", query.getWhere().getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("lolo", query.getWhere().getOperand2AsExpr().getOperand2());
		assertEquals("=", query.getWhere().getOperand2AsExpr().getOperation());
	}

	@Test
	public void nestedParenthesisFilters() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where (f1 = 'lolo' OR f1='lele') AND (test_table2.f2=3 OR (test_table2.f2=f3 OR f3>4))");

		/**
		 * 						AND
		 * 			OR(1)					OR(2)
		 * f1=lolo		f1=lele		f2=3			OR(3)
		 * 									f2=f3		f3>4
		 *
		 */

		assertEquals("AND", query.getWhere().getOperation());

		Expr or1 = query.getWhere().getOperand1AsExpr();
		assertEquals("OR", or1.getOperation());
		assertEquals("f1", or1.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("lolo", or1.getOperand1AsExpr().getOperand2());
		assertEquals("=", or1.getOperand1AsExpr().getOperation());
		assertEquals("f1", or1.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("lele", or1.getOperand2AsExpr().getOperand2());
		assertEquals("=", or1.getOperand2AsExpr().getOperation());

		Expr or2 = query.getWhere().getOperand2AsExpr();
		assertEquals("f2", or2.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("3", or2.getOperand1AsExpr().getOperand2());
		assertEquals("=", or2.getOperand1AsExpr().getOperation());

		Expr or3 = or2.getOperand2AsExpr();
		assertEquals("OR", or3.getOperation());
		assertEquals("f2", or3.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("f3", or3.getOperand1AsExpr().getOperand2AsField().getName());
		assertEquals("=", or3.getOperand1AsExpr().getOperation());
		assertEquals("f3", or3.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("4", or3.getOperand2AsExpr().getOperand2());
		assertEquals(">", or3.getOperand2AsExpr().getOperation());
	}

	@Test
	public void not() throws Exception {

		Parser parser = new Parser();
		Query query = parser.parse("select test_table2.field3 from db.test_table2 where  NOT (f2=99 OR f1 > 8)"); //probably use of != it is more natural, but this is also supported

		/**
		 *
		 * 					NOT
		 * 			OR				(null)
		 * f2=99		f1>8
		 *
		 */

		assertEquals("NOT", query.getWhere().getOperation());

		Expr or = query.getWhere().getOperand1AsExpr();
		assertEquals("f2", or.getOperand1AsExpr().getOperand1AsField().getName());
		assertEquals("99", or.getOperand1AsExpr().getOperand2());
		assertEquals("=", or.getOperand1AsExpr().getOperation());

		assertEquals("f1", or.getOperand2AsExpr().getOperand1AsField().getName());
		assertEquals("8", or.getOperand2AsExpr().getOperand2());
		assertEquals(">", or.getOperand2AsExpr().getOperation());

		assertNull(query.getWhere().getOperand2());
	}
}