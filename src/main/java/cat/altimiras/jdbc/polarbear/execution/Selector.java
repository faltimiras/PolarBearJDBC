package cat.altimiras.jdbc.polarbear.execution;

import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.util.HashMap;
import java.util.Map;

public class Selector {
	final int[] realPositions;

	public Selector(TableDefinition mainTable, Query query) {
		realPositions = new int[query.getFields().size()];

		Map<String, Integer> tableFirstColumnPosition = new HashMap<>();
		int relativePosition = 0;
		for (Table table : query.getTables()) {
			String name = table.getName();
			if (table.getDefinition().isStarTable()) {
				tableFirstColumnPosition.put(name, relativePosition);
				relativePosition += table.getDefinition().numOfColumns();
			}
		}
		//main table is always at the end
		tableFirstColumnPosition.put(mainTable.getName(), relativePosition);

		for (int i = 0; i < query.getFields().size(); i++) {
			Field field = query.getFields().get(i);
			int fieldPositionInTable = field.getTableDefinition().getColumnsByName().get(field.getName())
				.getPosition();
			realPositions[i] =
				tableFirstColumnPosition.get(field.getTableDefinition().getName()) + fieldPositionInTable;
		}
	}

	public int[] getRealPositions() {
		return realPositions;
	}
}
