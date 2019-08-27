package cat.altimiras.jdbc.polarbear.statement;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import cat.altimiras.jdbc.polarbear.query.Query;
import cat.altimiras.jdbc.polarbear.query.QueryParser;
import cat.altimiras.jdbc.polarbear.resultset.S3ResultSet;
import software.amazon.awssdk.services.s3.S3Client;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class S3Statement extends PolarBearStatement {

	private final S3Client s3Client;
	private final String bucket;

	public S3Statement(String target, TableManager tableManager, QueryParser queryParser, Connection connection, S3Client s3Client, String bucket) {
		super(target, tableManager, queryParser, connection);
		this.s3Client = s3Client;
		this.bucket = bucket;
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

		S3FilesIterator s3FilesIterator = new S3FilesIterator(
				s3Client,
				bucket,
				tableDefinition.getName(),
				from,
				to,
				tableDefinition.getPartitionsFormat()
		);
		return new S3ResultSet(query.getFields(), tableDefinition, s3FilesIterator, this);
	}
}
