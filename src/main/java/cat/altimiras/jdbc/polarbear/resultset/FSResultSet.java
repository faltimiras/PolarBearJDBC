package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.statement.DirsIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.stream.Stream;

public class FSResultSet extends PolarBearResultSet {

	private final static Logger log = LoggerFactory.getLogger(FSResultSet.class);

	private final DirsIterator itDirs;
	private Stream<Path> filesInDir;
	private Iterator<Path> itFilesInDir;
	private Stream<String> rows; //TODO byte[]
	private Iterator<String> itRows;
	private String rowRaw;
	private String[] row;

	public FSResultSet(TableDefinition tableDefinition, DirsIterator itDirs) throws PolarBearException {
		super(tableDefinition);
		try {
			this.itDirs = itDirs;
		} catch (Exception e) {
			throw new PolarBearException("Error starting to read files", e);
		}
	}

	@Override
	public boolean next() throws PolarBearException {
		try {
			if (nextLine()) {
				return true;
			} else {
				if (nextFile()) {
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

	@Override
	public String getString(int columnIndex) throws SQLException {
		return row[columnIndex - 1];
	}

	@Override
	public String getString(String columnLabel) throws SQLException {
		return row[tableDefinition.getPosition(columnLabel)];
	}

	private boolean nextLine() {
		if (itRows == null || !itRows.hasNext()) {
			return false;
		} else {
			rowRaw = itRows.next();
			if (rowRaw == null || rowRaw.isEmpty()) { //remove empty lines from file
				log.debug("Empty line detected. Ignoring it");
				return nextLine();
			} else {
				row = (String[]) rowFormatter.parse(rowRaw);
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

	String getCurrentLineRaw() {
		return rowRaw;
	}
}