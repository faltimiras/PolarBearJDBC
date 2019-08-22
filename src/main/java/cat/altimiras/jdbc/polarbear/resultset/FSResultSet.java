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
	private String[] row;

	public FSResultSet(List<Field> fields, TableDefinition tableDefinition, DirsIterator itDirs) throws PolarBearException {
		super(fields, tableDefinition);
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
		try {
			return row[columnIndex - 1];
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		try {
			return Boolean.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		try {
			return Byte.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public short getShort(int columnIndex) throws SQLException {
		try {
			return Short.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public int getInt(int columnIndex) throws SQLException {
		try {
			return Integer.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public long getLong(int columnIndex) throws SQLException {
		try {
			return Long.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public float getFloat(int columnIndex) throws SQLException {
		try {
			return Float.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public double getDouble(int columnIndex) throws SQLException {
		try {
			return Double.valueOf(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		try {
			return BigDecimal.valueOf(Long.valueOf(row[columnIndex - 1]), scale);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		try {
			return row[columnIndex - 1].getBytes();
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public Date getDate(int columnIndex) throws SQLException {
		try {
			if (tableDefinition.getColumns().get(columnIndex - 1).getUnixtime()) {
				return new Date(Long.valueOf(row[columnIndex - 1]));
			} else {
				SimpleDateFormat formatter = tableDefinition.getColumns().get(columnIndex - 1).getDateFormat();
				return new Date(formatter.parse(row[columnIndex - 1]).getTime());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");

		} catch (Exception e) {
			throw new SQLException("Field is not a Date", e);
		}
	}

	@Override
	public Time getTime(int columnIndex) throws SQLException {
		try {
			if (tableDefinition.getColumns().get(columnIndex - 1).getUnixtime()) {
				return new Time(Long.valueOf(row[columnIndex - 1]));
			} else {
				SimpleDateFormat formatter = tableDefinition.getColumns().get(columnIndex - 1).getTimeFormat();
				return new Time(formatter.parse(row[columnIndex - 1]).getTime());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");

		} catch (Exception e) {
			throw new SQLException("Field is not a Time", e);
		}
	}

	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		try {
			if (tableDefinition.getColumns().get(columnIndex - 1).getUnixtime()) {
				return new Timestamp(Long.valueOf(row[columnIndex - 1]));
			} else {
				SimpleDateFormat formatter = tableDefinition.getColumns().get(columnIndex - 1).getTimestampFormat();
				return new Timestamp(formatter.parse(row[columnIndex - 1]).getTime());
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (Exception e) {
			throw new SQLException("Field is not a Timestamp", e);
		}
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		try {
			return new ByteArrayInputStream(row[columnIndex - 1].getBytes());
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		try {
			return new BigDecimal(row[columnIndex - 1]);
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}


	@Override
	public String getString(String columnLabel) throws SQLException {
		try {
			return row[tableDefinition.getPosition(columnLabel)];
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		try {
			return Boolean.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		try {
			return Byte.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		try {
			return Short.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		try {
			return Integer.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		try {
			return Long.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		try {
			return Float.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		try {
			return Double.valueOf(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		try {
			return BigDecimal.valueOf(Long.valueOf(row[tableDefinition.getPosition(columnLabel)]), scale);

		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		try {
			return row[tableDefinition.getPosition(columnLabel)].getBytes();
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		int columnIndex = tableDefinition.getPosition(columnLabel);
		return getDate(columnIndex + 1);
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		int columnIndex = tableDefinition.getPosition(columnLabel);
		return getTime(columnIndex + 1);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		int columnIndex = tableDefinition.getPosition(columnLabel);
		return getTimestamp(columnIndex + 1);
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return new ByteArrayInputStream(row[tableDefinition.getPosition(columnLabel)].getBytes());
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return tableDefinition.getPosition(columnLabel);
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		try {
			return new BigDecimal(row[tableDefinition.getPosition(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
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
}