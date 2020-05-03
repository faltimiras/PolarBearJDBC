package cat.altimiras.jdbc.polarbear.execution;

import static org.junit.Assert.assertArrayEquals;

import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.ColumnDefinition.Type;
import cat.altimiras.jdbc.polarbear.def.PartitionDefinition;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class JoinerTest {
	private Table mainTable;

	private Table starTable;

	@Before
	public void setUp() throws Exception {
		TableDefinition mainTableDef = new TableDefinition("main-table");
		mainTableDef.setPartition(new PartitionDefinition("ts", "YYYY/MM/", LocalDateTime.now(), 44640));
		mainTableDef.setColumnDefinitions(Arrays.asList(
			new ColumnDefinition("f1", Type.TEXT, 0),
			new ColumnDefinition("f2", Type.LONG, 1),
			new ColumnDefinition("f3", Type.FLOAT, 2),
			new ColumnDefinition("f4", Type.TEXT, 3)
		));
		mainTable = new Table("main-table", null, mainTableDef);

		TableDefinition starTableDef = new TableDefinition("start-table");
		starTableDef.setColumnDefinitions(Arrays.asList(
			new ColumnDefinition("f1", Type.TEXT, 0, true),
			new ColumnDefinition("f2", Type.LONG, 1),
			new ColumnDefinition("f3", Type.TEXT, 2)
		));
		starTable = new Table("start-table", null, starTableDef);
	}

	@Test
	public void withoutStarTable() throws Exception {
		Joiner joiner = new Joiner(mainTable, Arrays.asList(mainTable), null, null);
		String[] result = joiner.join(new String[]{"aaa", "12", "1.0", "bbb"});

		assertArrayEquals(new String[]{"aaa", "12", "1.0", "bbb"}, result);
	}

	@Test
	public void joinHitStarTable() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("main-table", "f1", null, mainTable.getDefinition()));
		expr.setOperation("=");
		expr.setOperand2(new Field("start-table", "f1", null, starTable.getDefinition()));
		joinHitStarTable(expr);
	}

	@Test
	public void joinHitStarTable2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("start-table", "f1", null, starTable.getDefinition()));
		expr.setOperation("=");
		expr.setOperand2(new Field("main-table", "f1", null, mainTable.getDefinition()));

		joinHitStarTable(expr);
	}

	public void joinHitStarTable(Expr expr) throws Exception {
		Map<String, String[]> testStarTable1 = new HashMap<>();
		testStarTable1.put("aaa", new String[]{"aaa", "123", "ccc"});
		testStarTable1.put("ddd", new String[]{"ddd", "456", "iii"});
		Map<String, Map<String, String[]>> starTales = new HashMap<>();
		starTales.put("start-table", testStarTable1);

		Joiner joiner = new Joiner(mainTable, Arrays.asList(mainTable, starTable), expr, starTales);
		String[] result = joiner.join(new String[]{"aaa", "12", "1.0", "bbb"});

		assertArrayEquals(new String[]{"aaa", "123", "ccc", "aaa", "12", "1.0", "bbb"}, result);
	}

	@Test
	public void joinMissStarTable() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("main-table", "f1", null, mainTable.getDefinition()));
		expr.setOperation("=");
		expr.setOperand2(new Field("start-table", "f1", null, starTable.getDefinition()));
		joinHitStarTable(expr);
	}

	@Test
	public void joinMissStarTable2() throws Exception {
		Expr expr = new Expr();
		expr.setOperand1(new Field("start-table", "f1", null, starTable.getDefinition()));
		expr.setOperation("=");
		expr.setOperand2(new Field("main-table", "f1", null, mainTable.getDefinition()));
		joinHitStarTable(expr);
	}

	public void joinMissStarTable(Expr expr) throws Exception {
		Map<String, String[]> testStarTable1 = new HashMap<>();
		testStarTable1.put("aaa", new String[]{"aaa", "123", "ccc"});
		Map<String, Map<String, String[]>> starTales = new HashMap<>();
		starTales.put("start-table", testStarTable1);

		Joiner joiner = new Joiner(mainTable, Arrays.asList(mainTable, starTable), expr, starTales);
		String[] result = joiner.join(new String[]{"XXX", "12", "1.0", "bbb"});

		assertArrayEquals(new String[]{null, null, null, "XXX", "12", "1.0", "bbb"}, result);
	}
}