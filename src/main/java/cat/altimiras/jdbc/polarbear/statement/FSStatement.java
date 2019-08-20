package cat.altimiras.jdbc.polarbear.statement;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.QueryParser;
import cat.altimiras.jdbc.polarbear.resultset.FSResultSet;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class FSStatement extends PolarBearStatement {

	private final Path base;

	public FSStatement(String target, TableManager tableManager, QueryParser queryParser) {
		super(target, tableManager, queryParser);
		this.base = Paths.get(target);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		Query query = this.queryParser.parse(sql);

		TableDefinition tableDefinition = this.tableManager.getTable(query.getTable());
		LocalDateTime from = query.getTsLowerLimit() == null ? tableDefinition.getSince() : query.getTsLowerLimit();
		LocalDateTime to = query.getTsUpLimit() == null ? LocalDateTime.now() : query.getTsUpLimit();

		if (from.isAfter(to)) {
			throw new PolarBearException("Time range is not valid");
		}

		return new FSResultSet(tableDefinition, new DirsIterator(base.resolve(query.getTable()), from, to, tableDefinition.getStep(), tableDefinition.getNotFoundMaxLimit()));
	}
}