package cat.altimiras.jdbc.polarbear.statement;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.execution.Planner;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.QueryManager;
import cat.altimiras.jdbc.polarbear.resultset.S3ResultSet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import software.amazon.awssdk.services.s3.S3Client;

public class S3Statement extends PolarBearStatement {
	private final S3Client s3Client;

	private final String bucket;

	public S3Statement(String target, TableManager tableManager, QueryManager queryManager, Connection connection,
		S3Client s3Client, String bucket) {
		super(target, tableManager, queryManager, connection);
		this.s3Client = s3Client;
		this.bucket = bucket;
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {

		Query query = this.queryManager.parse(sql);
		Planner planner = new Planner(tableManager, query);

		if (planner.getTsLowerLimit().isAfter(planner.getTsUpperLimit())) {
			throw new PolarBearException("Time range is not valid");
		}

		S3FilesIterator s3FilesIterator = new S3FilesIterator(
			s3Client,
			bucket,
			planner.getMainTable().getName(),
			planner.getTsLowerLimit(),
			planner.getTsUpperLimit(),
			planner.getMainTable().getPartition().getPartitionsFormat()
		);

		return new S3ResultSet(query.getFields(), planner.getMainTable(), s3FilesIterator, planner, this);
	}
}