package cat.altimiras.jdbc.polarbear.execution;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.ColumnDefinition.Type;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FilterTest {
	private Filter filter;

	@Before
	public void setUp() {
		TableDefinition tableDefinition = mock(TableDefinition.class);
		Map<String, ColumnDefinition> columnDefs = new HashMap<>();
		columnDefs.put("field", new ColumnDefinition("field", Type.TEXT, 0));
		columnDefs.put("another-field", new ColumnDefinition("another-field", Type.LONG, 1));
		columnDefs.put("other-one-field", new ColumnDefinition("other-one-field", Type.FLOAT, 2));

		when(tableDefinition.getColumnsByName()).thenReturn(columnDefs);
		filter = new Filter(tableDefinition);
	}

	@Test
	public void filterNullExpression() throws Exception {
		assertTrue(filter.meets(new String[]{"a"}, null));
	}

	@Test
	public void textEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "field", null));
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
		expr.setOperand2(new Field("table-name", "field", null));

		assertTrue(filter.meets(new String[]{"lolo"}, expr));
		assertFalse(filter.meets(new String[]{"lala"}, expr));
	}

	@Test
	public void textEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("==");
		expr.setOperand2(new Field("table-name", "field", null));

		assertTrue(filter.meets(new String[]{"lolo"}, expr));
		assertFalse(filter.meets(new String[]{"lala"}, expr));
	}

	@Test
	public void textNotEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "field", null));
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
		expr.setOperand2(new Field("table-name", "field", null));

		assertTrue(filter.meets(new String[]{"lala"}, expr));
		assertFalse(filter.meets(new String[]{"lolo"}, expr));
	}

	@Test
	public void textNotEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("lolo");
		expr.setOperation("<>");
		expr.setOperand2(new Field("table-name", "field", null));

		assertTrue(filter.meets(new String[]{"lala"}, expr));
		assertFalse(filter.meets(new String[]{"lolo"}, expr));
	}

	@Test
	public void longEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("=");
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1");
		expr.setOperation("==");
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "another-field", null));
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
		expr.setOperand2(new Field("table-name", "other-one-field", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void floatEqual2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1("1.1");
		expr.setOperation("==");
		expr.setOperand2(new Field("table-name", "other-one-field", null));

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void floatEqual3() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "other-one-field", null));
		expr.setOperation("==");
		expr.setOperand2("1.1");

		assertTrue(filter.meets(new String[]{"lala", "1", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}

	@Test
	public void longBigger() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "another-field", null));
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
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longBiggerEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "another-field", null));
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
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertFalse(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLower() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "another-field", null));
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
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void longLowerEqual() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("table-name", "another-field", null));
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
		expr.setOperand2(new Field("table-name", "another-field", null));

		assertTrue(filter.meets(new String[]{"lala", "2", "1.1"}, expr));
		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, expr));
		assertFalse(filter.meets(new String[]{"lala", "0", "2.2"}, expr));
	}

	@Test
	public void and() throws Exception {
		Expr expr1 = new Expr("1", "==", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr and = new Expr(expr1, "and", expr2);

		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lala", "2", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and));
	}

	@Test
	public void or() throws Exception {
		Expr expr1 = new Expr("1", "==", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr and = new Expr(expr1, "OR", expr2);

		assertTrue(filter.meets(new String[]{"lala", "1", "2.2"}, and));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, and));
		assertTrue(filter.meets(new String[]{"lolo", "1", "2.2"}, and));
		assertFalse(filter.meets(new String[]{"lolo", "2", "2.2"}, and));
	}

	@Test
	public void ands() throws Exception {
		Expr expr1 = new Expr("10", ">", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr expr3 = new Expr("0", ">", new Field("table-name", "another-field", null));
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
		Expr expr1 = new Expr("10", ">", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr expr3 = new Expr("0", ">", new Field("table-name", "another-field", null));
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
		Expr expr1 = new Expr("10", ">", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr expr3 = new Expr("0", ">", new Field("table-name", "another-field", null));
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
		Expr expr1 = new Expr("10", ">", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr expr3 = new Expr("0", ">", new Field("table-name", "another-field", null));
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
		Expr expr1 = new Expr("1", "==", new Field("table-name", "another-field", null));
		Expr expr2 = new Expr("lala", "=", new Field("table-name", "field", null));
		Expr and = new Expr(expr1, "and", expr2);
		Expr not = new Expr(and, "not", null);

		assertFalse(filter.meets(new String[]{"lala", "1", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lolo", "1", "2.2"}, not));
		assertTrue(filter.meets(new String[]{"lolo", "2", "2.2"}, not));
	}

	@Test
	public void fields() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("test_tableA", "field", null));
		expr.setOperation("=");
		expr.setOperand2(new Field("test_tableB", "another-field", null));

		assertTrue(filter.meets(new String[]{"lala", "2", "2.2"}, expr));
	}
}
