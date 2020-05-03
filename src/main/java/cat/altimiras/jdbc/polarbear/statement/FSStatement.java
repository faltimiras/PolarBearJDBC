package cat.altimiras.jdbc.polarbear.statement;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.execution.Planner;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.QueryManager;
import cat.altimiras.jdbc.polarbear.resultset.FSResultSet;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FSStatement extends PolarBearStatement {
	private final Path base;

	public FSStatement(String target, TableManager tableManager, QueryManager queryManager, Connection connection) {
		super(target, tableManager, queryManager, connection);
		this.base = Paths.get(target);
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		Query query = this.queryManager.parse(sql);
		Planner planner = new Planner(tableManager, query);

		if (planner.getTsLowerLimit().isAfter(planner.getTsUpperLimit())) {
			throw new PolarBearException("Time range is not valid");
		}

		DirsIterator dirsIterator = new DirsIterator(
			base.resolve(planner.getMainTable().getName()),
			planner.getTsLowerLimit(),
			planner.getTsUpperLimit(),
			planner.getMainTable().getPartition().getPartitionsFormat(),
			planner.getMainTable().getPartition().getStep(),
			planner.getMainTable().getNotFoundMaxLimit());

		resultSet = new FSResultSet(query.getFields(), planner.getMainTable(), dirsIterator, planner, this);
		return resultSet;
	}
}