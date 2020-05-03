package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.query.antlr4.SqlBaseListener;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlParser;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Query extends SqlBaseListener {
	private final List<Table> tables = new ArrayList<>(); //extra tables to join, star schema

	private List<Field> fields = new ArrayList<>(); //null means *

	private String currentTableAlias = null;

	private String currentTableName = null;

	private String currentColumnName = null;

	private String currentColumnAlias = null;

	//Filters stored in pre-order
	private final Deque<Expr> contextExpr = new ArrayDeque<>();

	@Override
	public void exitTable(SqlParser.TableContext ctx) {
		Table table = new Table(this.currentTableName, this.currentTableAlias);
		this.tables.add(table);

		this.currentTableName = null;
		this.currentTableAlias = null;
	}

	@Override
	public void exitResult_column(SqlParser.Result_columnContext ctx) {
		if ("*".equals(ctx.getText())) {
			this.fields = null;
		} else {
			fields.add(new Field(currentTableName, currentColumnName, currentColumnAlias));
		}
		this.currentTableName = null;
		this.currentColumnName = null;
		this.currentColumnAlias = null;
	}

	@Override
	public void exitTable_name(SqlParser.Table_nameContext ctx) {
		this.currentTableName = ctx.getText();
	}

	@Override
	public void exitTable_alias(SqlParser.Table_aliasContext ctx) {
		this.currentTableAlias = ctx.getText();
	}

	@Override
	public void exitColumn_name(SqlParser.Column_nameContext ctx) {
		this.currentColumnName = ctx.getText();
	}

	@Override
	public void exitColumn_alias(SqlParser.Column_aliasContext ctx) {
		this.currentColumnAlias = ctx.getText();
	}

	@Override
	public void exitExpr_literal(SqlParser.Expr_literalContext ctx) {
		Expr currentContext = this.contextExpr.peek();
		if (currentContext.getOperand1() == null) {
			currentContext.setOperand1(unquote(ctx.getText()));
		} else {
			currentContext.setOperand2(unquote(ctx.getText()));
		}
	}

	@Override
	public void exitExpr_column(SqlParser.Expr_columnContext ctx) {
		String tableName = ctx.table_name() == null ? null : ctx.table_name().getText();
		Field field = new Field(tableName, ctx.column_name().getText(), null);
		Expr currentContext = this.contextExpr.peek();
		if (currentContext.getOperand1() == null) {
			currentContext.setOperand1(field);
		} else {
			currentContext.setOperand2(field);
		}
	}

	@Override
	public void exitComparator(SqlParser.ComparatorContext ctx) {
		this.contextExpr.peek().setOperation(ctx.getText());
	}

	@Override
	public void exitExpr_binary(SqlParser.Expr_binaryContext ctx) {
		//if more than element on the stack, must be compacted:
		// lower level there is the OR/AND op
		// at the peek the last expression processed
		if (contextExpr.size() != 1) {
			Expr lastExpr = contextExpr.pop();
			contextExpr.peek().setOperand2(lastExpr);
		}
	}

	@Override
	public void exitBinary(SqlParser.BinaryContext ctx) { //exit OP (o OR o AND)
		//AND/OP put itself at the peek, putting current expression as a first child
		Expr expr = new Expr();
		expr.setOperation(ctx.getText());
		expr.setOperand1(this.contextExpr.pop());
		this.contextExpr.push(expr);
	}

	@Override
	public void enterExpr_filter(SqlParser.Expr_filterContext ctx) {
		this.contextExpr.push(new Expr());
	}

	@Override
	public void exitExpr_parenthesis(SqlParser.Expr_parenthesisContext ctx) {
		if (contextExpr.size() != 1) {
			Expr lastExpr = contextExpr.pop();
			contextExpr.peek().setOperand2(lastExpr);
		}
	}

	@Override
	public void exitExpr_not(SqlParser.Expr_notContext ctx) {
		Expr not = new Expr();
		not.setOperation("NOT");
		not.setOperand1(contextExpr.pop());
		contextExpr.push(not);
	}

	public List<Table> getTables() {
		return tables;
	}

	public List<Field> getFields() {
		return fields;
	}

	public Expr getWhere() {
		return contextExpr.peek();
	}

	private String unquote(String value) {
		if (value.charAt(0) == '\'') {
			return value.substring(1, value.length() - 1);
		} else {
			return value;
		}
	}
}