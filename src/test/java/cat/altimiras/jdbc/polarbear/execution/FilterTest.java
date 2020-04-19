package cat.altimiras.jdbc.polarbear.execution;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cat.altimiras.jdbc.polarbear.def.FSTableManager;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import java.nio.file.Paths;
import org.junit.Before;
import org.junit.Test;

public class FilterTest {
	private TableDefinition tableDefinition;

	private Filter filter;

	@Before
	public void setUp() throws Exception {
		TableManager tableManager = new FSTableManager(Paths.get("src/test/resources/fs"));
		tableDefinition = tableManager.getTable("test_table2");
		filter = new Filter(tableDefinition);
	}

	@Test
	public void filterNullExpression() throws Exception {
		assertTrue(new Filter(tableDefinition).meets(new String[]{"a"}, null));
	}

	@Test
	public void textEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f1", null));
		expr.setOperation("=");
		expr.setOperand2("lolo");

		assertTrue(filter.meets(new String[]{"lolo"}, expr));
		assertFalse(filter.meets(new String[]{"lala"}, expr));
	}

	@Test
	public void textEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("=");
		expr.setOperand2(new Field("test_table2", "f1", null));

		assertTrue(filter.meets(new String[]{"lolo"}, expr));
		assertFalse(filter.meets(new String[]{"lala"}, expr));
	}

	@Test
	public void textEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("==");
		expr.setOperand2(new Field("test_table2", "f1", null));

		assertTrue(filter.meets(new String[]{"lolo"}, expr));
		assertFalse(filter.meets(new String[]{"lala"}, expr));
	}

	@Test
	public void textNotEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f1", null));
		expr.setOperation("!=");
		expr.setOperand2("lolo");

		assertTrue(filter.meets(new String[]{"lala"}, expr));
		assertFalse(filter.meets(new String[]{"lolo"}, expr));
	}

	@Test
	public void textNotEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("!=");
		expr.setOperand2(new Field("test_table2", "f1", null));

		assertTrue(filter.meets(new String[]{"lala"}, expr));
		assertFalse(filter.meets(new String[]{"lolo"}, expr));
	}

	@Test
	public void textNotEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("<>");
		expr.setOperand2(new Field("test_table2", "f1", null));

		assertTrue(filter.meets(new String[]{"lala"}, expr));
		assertFalse(filter.meets(new String[]{"lolo"}, expr));
	}

	@Test
	public void longEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("=");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("==");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f2", null));
		expr.setOperation("==");
		expr.setOperand2("1");

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void floatEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1.1");
		expr.setOperation("=");
		expr.setOperand2(new Field("test_table2", "f3", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void floatEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1.1");
		expr.setOperation("==");
		expr.setOperand2(new Field("test_table2", "f3", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void floatEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f3", null));
		expr.setOperation("==");
		expr.setOperand2("1.1");

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longBigger() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f2", null));
		expr.setOperation(">");
		expr.setOperand2("1");

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longBigger2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation(">");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longBiggerEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f2", null));
		expr.setOperation(">=");
		expr.setOperand2("1");

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longBiggerEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation(">=");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLower() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f2", null));
		expr.setOperation("<");
		expr.setOperand2("1");

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLower2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("<");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLowerEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_table2", "f2", null));
		expr.setOperation("<=");
		expr.setOperand2("1");

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLowerEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("<=");
		expr.setOperand2(new Field("test_table2", "f2", null));

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void and() throws Exception {
		Expr expr1 = new Expr("1", "==", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr and = new Expr(expr1, "and", expr2);

		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and));
	}

	@Test
	public void or() throws Exception {
		Expr expr1 = new Expr("1", "==", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr and = new Expr(expr1, "OR", expr2);

		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, and));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, and));
		assertTrue(filter.meets(new String[]{"lolo", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and));
	}

	@Test
	public void ands() throws Exception {
		Expr expr1 = new Expr("10", ">", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr expr3 = new Expr("0", ">", new Field("test_table2", "f2", null));
		Expr and1 = new Expr(expr1, "and", expr2);
		Expr and2 = new Expr(expr3, "and", and1);

		assertTrue(filter.meets(new String[]{"lala", "-1", "2.2"}, and2));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, and2));
		assertFalse(filter.meets(new String[]{"lala", "11", "2.2"}, and2));
		assertFalse(filter.meets(new String[]{"lolo", "-1", "2.2"}, and2));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and2));
		assertFalse(filter.meets(new String[]{"lolo", "11", "2.2"}, and2));
	}

	@Test
	public void ors() throws Exception {
		Expr expr1 = new Expr("10", ">", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr expr3 = new Expr("0", ">", new Field("test_table2", "f2", null));
		Expr or1 = new Expr(expr1, "or", expr2);
		Expr or2 = new Expr(expr3, "or", or1);

		assertTrue(filter.meets(new String[]{"lala", "-1", "2.2"}, or2));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, or2));
		assertTrue(filter.meets(new String[]{"lala", "11", "2.2"}, or2));
		assertTrue(filter.meets(new String[]{"lolo", "-1", "2.2"}, or2));
		assertTrue(filter.meets(new String[]{"lolo", "2", "2.2"}, or2));
		assertFalse(filter.meets(new String[]{"lolo", "11", "2.2"}, or2));
	}

	@Test
	public void orAnd() throws Exception {
		Expr expr1 = new Expr("10", ">", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr expr3 = new Expr("0", ">", new Field("test_table2", "f2", null));
		Expr and = new Expr(expr1, "and", expr2);
		Expr or = new Expr(expr3, "or", and);

		assertTrue(filter.meets(new String[]{"lala", "-1", "2.2"}, or));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, or));
		assertFalse(filter.meets(new String[]{"lala", "11", "2.2"}, or));
		assertTrue(filter.meets(new String[]{"lolo", "-1", "2.2"}, or));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, or));
		assertFalse(filter.meets(new String[]{"lolo", "11", "2.2"}, or));
	}

	@Test
	public void andOr() throws Exception {
		Expr expr1 = new Expr("10", ">", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr expr3 = new Expr("0", ">", new Field("test_table2", "f2", null));
		Expr or = new Expr(expr1, "or", expr2);
		Expr and = new Expr(expr3, "and", or);

		assertTrue(filter.meets(new String[]{"lala", "-1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lala", "11", "2.2"}, and));
		assertTrue(filter.meets(new String[]{"lolo", "-1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "11", "2.2"}, and));
	}

	@Test
	public void not() throws Exception {
		Expr expr1 = new Expr("1", "==", new Field("test_table2", "f2", null));
		Expr expr2 = new Expr("lala", "=", new Field("test_table2", "f1", null));
		Expr and = new Expr(expr1, "and", expr2);
		Expr not = new Expr(and, "not", null);

		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lolo", "1", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lolo", "2", "2.2"}, not));
	}
}
