package cat.altimiras.jdbc.polarbear.io;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.format.RowDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.stream.Stream;

public class FSBaseReader implements Reader{

	private final static Logger log = LoggerFactory.getLogger(FSPartitionedReader.class);

	private final Iterator<Path> itDirs;
	private final long maxRows;
	private final RowDeserializer rowDeserializer;
	private Stream<Path> filesInDir;
	private Iterator<Path> itFilesInDir;
	private Stream<String> rows;
	private Iterator<String> itRows;
	private String rowRaw;
	private int rowsFetched = 0;

	private String[] row;

	public FSBaseReader(Iterator<Path> itDirs, RowDeserializer rowDeserializer, long maxRows) throws PolarBearException {
		this.itDirs = itDirs;
		this.maxRows = maxRows;
		this.rowDeserializer = rowDeserializer;
	}

	@Override
	public String[] next() {
		return row;
	}


	@Override
	public boolean hasNext() {
		try {
			if (this.maxRows > 0 && rowsFetched >= this.maxRows) {
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
			return false;
		}
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

	private boolean nextLine() throws PolarBearException {
		if (itRows == null || !itRows.hasNext()) {
			return false;
		} else {
			rowRaw = itRows.next();
			if (rowRaw == null || rowRaw.isEmpty()) { //remove empty lines from file
				log.debug("Empty line detected. Ignoring it");
				return nextLine();
			} else {
				row = (String[]) rowDeserializer.parse(rowRaw.getBytes());
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
			filesInDir = Files.walk(nextDir, 1).filter(Files::isRegularFile).sorted();
			itFilesInDir = filesInDir.iterator();
			if (nextFile()) {
				rowsFetched++;
				return true;
			}
		}
		return false;
	}
}
