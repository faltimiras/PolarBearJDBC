package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.query.Field;
import cat.altimiras.jdbc.polarbear.statement.DirsIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class FSResultSet extends PolarBearResultSet {

	private final static Logger log = LoggerFactory.getLogger(FSResultSet.class);

	private final DirsIterator itDirs;
	private Stream<Path> filesInDir;
	private Iterator<Path> itFilesInDir;
	private Stream<String> rows; //TODO byte[]
	private Iterator<String> itRows;
	private String rowRaw;
	private int rowsFetched = 0;

	public FSResultSet(List<Field> fields, TableDefinition tableDefinition, DirsIterator itDirs, Statement statement) throws PolarBearException {
		super(fields, tableDefinition, statement);
		this.itDirs = itDirs;
	}

	@Override
	public boolean next() throws PolarBearException {
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
					return nextDir();
				}
			}

		} catch (Exception e) {
			log.error("Error getting next value", e);
			throw new PolarBearException("Error getting next value", e);
		}
	}



	private boolean nextLine() throws PolarBearException {
		if (itRows == null || !itRows.hasNext()) {
			return false;
		} else {
			rowRaw = itRows.next();
			if (rowRaw == null || rowRaw.isEmpty()) { //remove empty lines from file
				log.debug("Empty line detected. Ignoring it");
				return nextLine();
			} else {
				row = (String[]) rowFormatter.parse(rowRaw, this.fields);
				return true;
			}
		}
	}

	private boolean nextFile() throws Exception {
		if (itFilesInDir == null || !itFilesInDir.hasNext()) {
			return false;
		} else {
			Path nextFile = itFilesInDir.next();
			log.debug("New file: {}", nextFile);
			rows = Files.lines(nextFile);
			itRows = rows.iterator();
			return nextLine();
		}
	}

	private boolean nextDir() throws Exception {
		while (itDirs.hasNext()) {
			Path nextDir = itDirs.next();
			log.debug("Next dir:{}", nextDir);
			filesInDir = Files.walk(nextDir, 1).filter(Files::isRegularFile);
			itFilesInDir = filesInDir.iterator();
			if (nextFile()) {
				rowsFetched++;
				return true;
			}
		}
		return false;
	}

	@Override
	public void close() throws SQLException {
		if (filesInDir != null) {
			filesInDir.close();
		}
		if (rows != null) {
			rows.close();
		}
	}

	void setCurrentRow(String[] row) {
		this.row = row;
	}

	@Override
	public Statement getStatement() throws SQLException {
		return super.getStatement();
	}
}