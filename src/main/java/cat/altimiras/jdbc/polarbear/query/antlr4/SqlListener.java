// Generated from Sql.g4 by ANTLR 4.7.2
package cat.altimiras.jdbc.polarbear.query.antlr4;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link SqlParser}.
 */
public interface SqlListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link SqlParser#select}.
	 * @param ctx the parse tree
	 */
	void enterSelect(SqlParser.SelectContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#select}.
	 * @param ctx the parse tree
	 */
	void exitSelect(SqlParser.SelectContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#table}.
	 * @param ctx the parse tree
	 */
	void enterTable(SqlParser.TableContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#table}.
	 * @param ctx the parse tree
	 */
	void exitTable(SqlParser.TableContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#result_column}.
	 * @param ctx the parse tree
	 */
	void enterResult_column(SqlParser.Result_columnContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#result_column}.
	 * @param ctx the parse tree
	 */
	void exitResult_column(SqlParser.Result_columnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_literal}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_literal(SqlParser.Expr_literalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_literal}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_literal(SqlParser.Expr_literalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_parenthesis}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_parenthesis(SqlParser.Expr_parenthesisContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_parenthesis}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_parenthesis(SqlParser.Expr_parenthesisContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_filter}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_filter(SqlParser.Expr_filterContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_filter}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_filter(SqlParser.Expr_filterContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_column}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_column(SqlParser.Expr_columnContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_column}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_column(SqlParser.Expr_columnContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_not}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_not(SqlParser.Expr_notContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_not}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_not(SqlParser.Expr_notContext ctx);
	/**
	 * Enter a parse tree produced by the {@code expr_binary}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr_binary(SqlParser.Expr_binaryContext ctx);
	/**
	 * Exit a parse tree produced by the {@code expr_binary}
	 * labeled alternative in {@link SqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr_binary(SqlParser.Expr_binaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(SqlParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(SqlParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#binary}.
	 * @param ctx the parse tree
	 */
	void enterBinary(SqlParser.BinaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#binary}.
	 * @param ctx the parse tree
	 */
	void exitBinary(SqlParser.BinaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void enterLiteral_value(SqlParser.Literal_valueContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#literal_value}.
	 * @param ctx the parse tree
	 */
	void exitLiteral_value(SqlParser.Literal_valueContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#column_alias}.
	 * @param ctx the parse tree
	 */
	void enterColumn_alias(SqlParser.Column_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#column_alias}.
	 * @param ctx the parse tree
	 */
	void exitColumn_alias(SqlParser.Column_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#database_name}.
	 * @param ctx the parse tree
	 */
	void enterDatabase_name(SqlParser.Database_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#database_name}.
	 * @param ctx the parse tree
	 */
	void exitDatabase_name(SqlParser.Database_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#table_name}.
	 * @param ctx the parse tree
	 */
	void enterTable_name(SqlParser.Table_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#table_name}.
	 * @param ctx the parse tree
	 */
	void exitTable_name(SqlParser.Table_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#table_alias}.
	 * @param ctx the parse tree
	 */
	void enterTable_alias(SqlParser.Table_aliasContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#table_alias}.
	 * @param ctx the parse tree
	 */
	void exitTable_alias(SqlParser.Table_aliasContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#column_name}.
	 * @param ctx the parse tree
	 */
	void enterColumn_name(SqlParser.Column_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#column_name}.
	 * @param ctx the parse tree
	 */
	void exitColumn_name(SqlParser.Column_nameContext ctx);
	/**
	 * Enter a parse tree produced by {@link SqlParser#any_name}.
	 * @param ctx the parse tree
	 */
	void enterAny_name(SqlParser.Any_nameContext ctx);
	/**
	 * Exit a parse tree produced by {@link SqlParser#any_name}.
	 * @param ctx the parse tree
	 */
	void exitAny_name(SqlParser.Any_nameContext ctx);
}