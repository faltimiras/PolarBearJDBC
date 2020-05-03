package cat.altimiras.jdbc.polarbear.execution;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.ColumnDefinition;
import cat.altimiras.jdbc.polarbear.def.ColumnDefinition.Type;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Expr;
import cat.altimiras.jdbc.polarbear.query.Field;

class Filter {
	private final TableDefinition tableDefinition;

	public Filter(TableDefinition tableDefinition) {
		this.tableDefinition = tableDefinition;
	}

	public boolean meets(String[] row, Expr expr) throws PolarBearException {

		if (expr == null) {
			return true;
		} else {
			Object operand1 = expr.getOperand1();
			Object operand2 = expr.getOperand2();

			if (operand1 instanceof Field && operand2 instanceof Field) {
				//it is a join between to fields, not a filter
				return true;
			} else if (!(operand1 instanceof Expr) && !(operand2 instanceof Expr)) {
				return evaluate(row, operand1, operand2, expr.getOperation());
			} else { //and | or | not
				if (expr.getOperation().equalsIgnoreCase("AND")) {
					return meets(row, expr.getOperand1AsExpr()) && meets(row, expr.getOperand2AsExpr());
				} else if (expr.getOperation().equalsIgnoreCase("OR")) {
					return meets(row, expr.getOperand1AsExpr()) || meets(row, expr.getOperand2AsExpr());
				} else if (expr.getOperation().equalsIgnoreCase("NOT")) {
					return !meets(row, expr.getOperand1AsExpr());
				} else {
					throw new PolarBearException("Comparation not supported");
				}
			}
		}
	}

	private boolean evaluate(String[] row, Object operand1, Object operand2, String operation)
		throws PolarBearException {

		Type type = null;
		Object val1 = null;
		Object val2 = null;
		if (operand1 instanceof Field) {
			Field field = (Field) operand1;
			if (field.isTs()) {
				return true; //it is not a filter
			} else {
				ColumnDefinition columnDefinition = tableDefinition.getColumnsByName().get(field.getName());
				type = columnDefinition.getType();
				val1 = convert(row[columnDefinition.getPosition()], columnDefinition.getType());
			}
		}
		if (operand2 instanceof Field) {
			Field field = (Field) operand2;
			if (field.isTs()) {
				return true;
			}
			ColumnDefinition columnDefinition = tableDefinition.getColumnsByName().get((field.getName()));
			//validate types compatible
			if (val1 != null && !val1.equals(val2)) {
				throw new PolarBearException("Comparing incompatible types");
			} else {
				type = columnDefinition.getType();
			}
			val2 = convert(row[columnDefinition.getPosition()], columnDefinition.getType());
		}
		if (val1 == null) { //operand1 is a value
			val1 = convert(operand1.toString(), type);
		}
		if (val2 == null) { //operand2 is a value
			val2 = convert(operand2.toString(), type);
		}

		return compare(val1, val2, operation);
	}

	private boolean compare(Object operand1, Object operand2, String operation) throws PolarBearException {
		switch (operation) {
			case "=":
			case "==":
				return operand1.equals(operand2);
			case ">":
				if (operand1 instanceof String) {
					throw new PolarBearException("incompatible comparation '>' ");
				} else if (operand1 instanceof Number) {
					return ((Number) operand1).floatValue() > ((Number) operand2).floatValue();
				} else {
					throw new RuntimeException("//TODO");
				}
				//TODO dates formats

			case ">=":
				if (operand1 instanceof String) {
					throw new PolarBearException("incompatible comparation '>=' ");
				} else if (operand1 instanceof Number) {
					return ((Number) operand1).floatValue() >= ((Number) operand2).floatValue();
				} else {
					throw new RuntimeException("//TODO");
				}
				//TODO dates formats
			case "<":
				if (operand1 instanceof String) {
					throw new PolarBearException("incompatible comparation '<' ");
				} else if (operand1 instanceof Number) {
					return ((Number) operand1).floatValue() < ((Number) operand2).floatValue();
				} else {
					throw new RuntimeException("//TODO");
				}
				//TODO dates formats
			case "<=":
				if (operand1 instanceof String) {
					throw new PolarBearException("incompatible comparation '<=' ");
				} else if (operand1 instanceof Number) {
					return ((Number) operand1).floatValue() <= ((Number) operand2).floatValue();
				} else {
					throw new RuntimeException("//TODO");
				}
			case "!=":
			case "<>":
				return !operand1.equals(operand2);

			default:
				throw new PolarBearException("Comparation not supported");
		}
	}

	private Object convert(String value, ColumnDefinition.Type type) {
		switch (type) {
			case TEXT:
				return value;
			case LONG:
				return Long.valueOf(value);
			case FLOAT:
				return Float.valueOf(value);
			case BOOLEAN:
				return Boolean.valueOf(value);

			default:
				//TODO
				throw new RuntimeException("TODO");
		}
	}
}
