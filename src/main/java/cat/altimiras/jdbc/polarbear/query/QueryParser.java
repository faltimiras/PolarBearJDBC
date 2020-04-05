package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlLexer;
import cat.altimiras.jdbc.polarbear.query.antlr4.SqlParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

public class QueryParser {

	private TableManager tableManager;

	public QueryParser(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	public Query parse(String sql) throws PolarBearException {

		SqlLexer l = new SqlLexer(CharStreams.fromString(sql));
		SqlParser p = new SqlParser(new CommonTokenStream(l));

		//TODO
		p.addErrorListener(new BaseErrorListener() {
			@Override
			public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
				throw new IllegalStateException("Error at " + line + " due to " + msg, e);
			}
		});

		Query query = new Query(tableManager);
		ParseTree tree = p.select();
		ParseTreeWalker walker = new ParseTreeWalker();
		walker.walk(query,tree);

		return query;
	}
}