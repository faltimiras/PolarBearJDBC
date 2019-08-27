package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.statement.S3FilesIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class S3ResultSet extends PolarBearResultSet {

	private final static Logger log = LoggerFactory.getLogger(FSResultSet.class);

	private final S3FilesIterator s3FilesIterator;
	private BufferedReader reader;
	private String rowRaw;
	private int rowsFetched = 0;


	public S3ResultSet(List<Field> queryFields, TableDefinition tableDefinition, S3FilesIterator s3FilesIterator, Statement statement) throws PolarBearException {
		super(queryFields, tableDefinition, statement);
		this.s3FilesIterator = s3FilesIterator;
	}

	@Override
	public boolean next() throws SQLException {
		try {
			if (statement.getMaxRows() > 0 && rowsFetched >= statement.getMaxRows()) {
				return false;
			}

			if (nextLine()) {
				rowsFetched++;
				return true;
			} else {
				if (nextFile()) {
					rowsFetched++;
					return true;
				} else {
					return false;
				}
			}

		} catch (Exception e) {
			log.error("Error getting next value", e);
			throw new PolarBearException("Error getting next value", e);
		}
	}

	@Override
	public void close() throws SQLException {
		if (reader != null) {
			try {
				reader.close();
			} catch (IOException e) {
				throw new PolarBearException("Error closing resources", e);
			}
		}
	}

	private boolean nextLine() throws PolarBearException {
		try {
			if (reader != null) {
				rowRaw = reader.readLine();
				if (rowRaw == null) {
					return false;
				} else if (rowRaw.isEmpty()) { //remove empty lines from file
					log.debug("Empty line detected. Ignoring it");
					return nextLine();
				} else {
					row = (String[]) rowFormatter.parse(rowRaw, this.queryFieldsByNameInFile);
					return true;
				}
			}
			return false;

		} catch (Exception e) {
			log.error("Error getting next value", e);
			throw new PolarBearException("Error getting next value", e);
		}
	}

	private boolean nextFile() throws Exception {
		if (s3FilesIterator == null || !s3FilesIterator.hasNext()) {
			return false;
		} else {
			InputStream inputStream = s3FilesIterator.next();
			if (inputStream == null) {
				return nextFile();
			} else {
				reader = new BufferedReader(new InputStreamReader(inputStream));
				return nextLine();
			}
		}
	}
}
