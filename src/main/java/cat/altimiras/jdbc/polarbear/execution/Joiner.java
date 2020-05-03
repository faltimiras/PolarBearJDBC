package cat.altimiras.jdbc.polarbear.execution;

import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Joiner {
	final private Map<String, Map<String, String[]>> starTables;

	final private int joinedRowLength;

	final private Collection<FK> fks;

	public Joiner(Table mainTable, List<Table> tables, Expr where,
		Map<String, Map<String, String[]>> starTables) {

		this.starTables = starTables;

		Map<String, FK> indexedFKs = findJoins(where, mainTable);

		int tablePosition = 0;
		int mainTableNumColumns = 0;
		for (Table table : tables) {
			if (table.getDefinition().isStarTable()) {
				FK fk = indexedFKs.get(table.getName());
				fk.position = tablePosition;
				fk.length = table.getDefinition().numOfColumns();
				tablePosition += table.getDefinition().numOfColumns();
			} else {
				mainTableNumColumns = table.getDefinition().numOfColumns();
			}
		}

		this.joinedRowLength = mainTableNumColumns + tablePosition;
		this.fks = indexedFKs.values();
	}

	/**
	 * Result row is the join of all star tables in order of appearance in the FROM clause + main table
	 * @param row
	 * @return
	 */
	public String[] join(String[] row) {
		String[] newRow = new String[joinedRowLength];
		for (FK fk : fks) {
			String[] starRow = starTables.get(fk.starTableName).get(row[fk.mainTableFKColumnIndex]);
			if (starRow != null) {
				System.arraycopy(starRow, 0, newRow, fk.position, fk.length);
			}
		}
		System.arraycopy(row, 0, newRow, newRow.length - row.length, row.length);
		return newRow;
	}

	private Map<String, FK> findJoins(Expr expr, Table mainTable) {
		if (expr == null) {
			return new HashMap<>();
		}
		Object operand1 = expr.getOperand1();
		Object operand2 = expr.getOperand2();

		if (operand1 instanceof Field && operand2 instanceof Field) {
			String starTable;
			int fkIndex;
			if (((Field) operand1).getTable().equalsIgnoreCase(mainTable.getName())
				|| ((Field) operand1).getTable().equalsIgnoreCase(mainTable.getAlias())) {
				starTable = ((Field) operand2).getTableDefinition().getName();
				String fkName = ((Field) operand1).getName();
				fkIndex = ((Field) operand1).getTableDefinition().getColumnsByName().get(fkName).getPosition();
			} else {
				starTable = ((Field) operand1).getTableDefinition().getName();
				String fkName = ((Field) operand2).getName();
				fkIndex = ((Field) operand2).getTableDefinition().getColumnsByName().get(fkName).getPosition();
			}
			Map<String, FK> fks = new HashMap<>();
			fks.put(starTable, new FK(starTable, fkIndex));
			return fks;
		} else if (operand1 instanceof Expr && operand2 instanceof Expr) {
			Map<String, FK> fks = new HashMap<>();
			fks.putAll(findJoins((Expr) operand1, mainTable));
			fks.putAll(findJoins((Expr) operand2, mainTable));
			return fks;
		}
		return new HashMap<>();
	}

	private class FK {
		private final String starTableName;

		private final int mainTableFKColumnIndex;

		private int position;

		private int length;

		public FK(String starTableName, int mainTableFKColumnIndex) {
			this.starTableName = starTableName;
			this.mainTableFKColumnIndex = mainTableFKColumnIndex;
		}
	}
}
