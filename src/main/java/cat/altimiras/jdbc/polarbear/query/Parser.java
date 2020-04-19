package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.query.antlr4.SqlLexer;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class Parser {

	public Query parse(String sql) throws QueryException {

		SqlLexer l = new SqlLexer(CharStreams.fromString(sql));
		SqlParser p = new SqlParser(new CommonTokenStream(l));

		p.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
				throw new QueryException("Error close to [" + charPositionInLine + "] due to: " + msg, e);
			}
		});

		Query query = new Query();
		ParseTree tree = p.select();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(query, tree);

		return query;
	}
}