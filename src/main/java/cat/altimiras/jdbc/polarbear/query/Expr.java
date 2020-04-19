package cat.altimiras.jdbc.polarbear.query;

public class Expr {

	private String operation;
	private Object operand1;
	private Object operand2;

	public Expr() {
	}

	public Expr(Object operand1, String operation, Object operand2) {
		this.operation = operation;
		this.operand1 = operand1;
		this.operand2 = operand2;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Object getOperand1() {
		return operand1;
	}

	public boolean isOperand1Field() {
		return operand1 instanceof Field;
	}

	public boolean isOperand1Expr() {
		return operand1 instanceof Expr;
	}

	public boolean isOperand1Value() {
		return !isOperand1Expr() && !isOperand1Field();
	}

	public Field getOperand1AsField() {
		return (Field) operand1;
	}

	public Expr getOperand1AsExpr() {
		return (Expr) operand1;
	}

	public void setOperand1(Object operand1) {
		this.operand1 = operand1;
	}

	public Object getOperand2() {
		return operand2;
	}

	public boolean isOperand2Field() {
		return operand2 instanceof Field;
	}

	public boolean isOperand2Expr() {
		return operand2 instanceof Expr;
	}

	public boolean isOperand2Value() {
		return !isOperand2Expr() && !isOperand2Field();
	}

	public Field getOperand2AsField() {
		return (Field) operand2;
	}

	public Expr getOperand2AsExpr() {
		return (Expr) operand2;
	}

	public void setOperand2(Object operand2) {
		this.operand2 = operand2;
	}
}
