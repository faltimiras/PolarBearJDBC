package cat.altimiras.jdbc.polarbear.execution;

import static java.time.temporal.ChronoUnit.MINUTES;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.io.Reader;
import cat.altimiras.jdbc.polarbear.query.DateFormatter;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.Table;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Planner {
	private final static Logger log = LoggerFactory.getLogger(Planner.class);

	private final static String SEPARATOR = "-";

	//star tables cache
	//TODO ehcache??
	private static final Map<String, Map<String, String[]>> starTables = new HashMap<>();

	private final Query query;

	private final Selector selector;

	private final Joiner joiner;

	private final Filter filter;

	private Table mainTable;

	private LocalDateTime tsUpperLimit;

	private LocalDateTime tsLowerLimit;

	public Planner(TableManager tableManager, Query query) throws PolarBearException {
		this.query = query;

		for (Table table : query.getTables()) {
			if (table.getDefinition().isStarTable()) { //load tables into memory
				log.debug("Loading star table {}", table.getName());
				List<Integer> pkPositions = table.getDefinition().getPKPositions();
				Reader reader = tableManager.read(table.getName(), null, null, -1);
				Map<String, String[]> tableInMemory = new HashMap<>();
				while (reader.hasNext()) {
					String[] row = reader.next();
					String pk = createPK(row, pkPositions);
					tableInMemory.put(pk, row);
				}
				starTables.put(table.getName(), tableInMemory);
			} else {
				mainTable = table;
			}
		}

		//set query limits
		setLimits(query.getWhere(), mainTable.getDefinition().getPartition().getColumnName());

		if (tsLowerLimit == null && tsUpperLimit == null) {
			log.error("Not time window defined, full scan not allowed!");
			throw new PolarBearException("Not time window defined, full scan not allowed");
		}

		filter = new Filter(mainTable.getDefinition());
		joiner = new Joiner(mainTable, query.getTables(), query.getWhere(), starTables);
		selector = new Selector(mainTable.getDefinition(), query);
	}

	/**
	 * @param row row from time partitioned table
	 * @return null if row do not meet query filters, String[] with the results with fields properly ordered, adding fields from star tables, and removing not selected ones.
	 */
	public Row process(String[] row) throws PolarBearException {
		//filter. Discard rows from main table that do not met conditions
		if (filter.meets(row, query.getWhere())) {
			return new Row(selector.getRealPositions(), joiner.join(row));
		} else {
			log.debug("Row {} discarded, where not met", row);
			return null;
		}
	}

	private String createPK(String[] row, List<Integer> pkPositions) {
		return pkPositions.stream().map(p -> row[p]).collect(Collectors.joining(SEPARATOR));
	}

	public LocalDateTime getTsLowerLimit() {
		if (tsLowerLimit == null) {
			return mainTable.getDefinition().getPartition().getSince();
		} else {
			return tsLowerLimit;
		}
	}

	public LocalDateTime getTsUpperLimit() {
		if (tsUpperLimit == null) {
			return LocalDateTime.now();
		} else {
			return tsUpperLimit;
		}
	}

	public TableDefinition getMainTable() {
		return mainTable.getDefinition();
	}

	private void setLimits(Expr expr, String tsColumnName) throws PolarBearException {

		if (expr != null) {
			Object operand1 = expr.getOperand1();
			if (operand1 != null) {
				if (operand1 instanceof Field) {
					if (((Field) operand1).getName().equals(tsColumnName)) {
						setLimit((String) expr.getOperand2(), expr.getOperation(), true);
					}
				}
				if (operand1 instanceof Expr) {
					setLimits(expr.getOperand1AsExpr(), tsColumnName);
				}
			}
			Object operand2 = expr.getOperand2();
			if (operand2 != null) {
				if (operand2 instanceof Field) {
					if (((Field) operand2).getName().equals(tsColumnName)) {
						setLimit((String) expr.getOperand1(), expr.getOperation(), false);
					}
				}
				if (operand2 instanceof Expr) {
					setLimits(expr.getOperand2AsExpr(), tsColumnName);
				}
			}
		}
	}

	private void setLimit(String value, String comparator, boolean fieldFirst) throws PolarBearException {

		if (comparator.equals(">")) {
			if (fieldFirst) {
				tsLowerLimit = DateFormatter.parse(value).plus(1, MINUTES);
			} else {
				tsUpperLimit = DateFormatter.parse(value).minus(1, MINUTES);
			}
		} else if (comparator.equals(">=")) {
			if (fieldFirst) {
				tsLowerLimit = DateFormatter.parse(value);
			} else {
				tsUpperLimit = DateFormatter.parse(value);
			}
		} else if (comparator.equals("<")) {
			if (fieldFirst) {
				tsUpperLimit = DateFormatter.parse(value).minus(1, MINUTES);
			} else {
				tsLowerLimit = DateFormatter.parse(value).plus(1, MINUTES);
			}
		} else if (comparator.equals("<=")) {
			if (fieldFirst) {
				tsUpperLimit = DateFormatter.parse(value);
			} else {
				tsLowerLimit = DateFormatter.parse(value);
			}
		} else {
			log.error("Comparator {} not supported", comparator);
			throw new PolarBearException("Time partition is not correctly defined");
		}
	}
}
