package cat.altimiras.jdbc.polarbear.resultset;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;
import cat.altimiras.jdbc.polarbear.format.RowFormatter;
import cat.altimiras.jdbc.polarbear.format.RowFormatterFactory;
import cat.altimiras.jdbc.polarbear.query.Field;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class PolarBearResultSet implements ResultSet {

	protected final TableDefinition tableDefinition;
	protected final RowFormatter rowFormatter;
	protected final List<Field> queryFields;
	protected final Statement statement;
	// position in the raw file by name
	protected Map<String, Integer> queryFieldsByNameInFile;
	// position in the row by name. row can contain less fields than in file, due to select field1... reduce number of fields
	protected Map<String, Integer> queryFieldsByNameInRow;

	protected String[] row;

	public PolarBearResultSet(List<Field> queryFields, TableDefinition tableDefinition, Statement statement) throws PolarBearException {
		this.tableDefinition = tableDefinition;
		this.rowFormatter = RowFormatterFactory.get(tableDefinition.getFormat(), tableDefinition);
		this.queryFields = queryFields;
		this.statement = statement;
		byIdentifier(queryFields);
	}

	private void  byIdentifier(List<Field> queryFields) {

		Map<String, Integer> tableColumnsByName = tableDefinition.getColumnsByName();
		queryFieldsByNameInFile = new LinkedHashMap(queryFields.size());
		queryFieldsByNameInRow = new HashMap<>(queryFields.size());
		for (int i = 0; i < queryFields.size(); i++) {
			Field field = queryFields.get(i);
			int pos = tableColumnsByName.get(field.getName());
			queryFieldsByNameInFile.put(field.getAlias() == null ? field.getName() : field.getAlias(), pos);
			queryFieldsByNameInRow.put(field.getAlias() == null ? field.getName() : field.getAlias(), i);
		}
	}

	@Override
	public Statement getStatement() throws SQLException {
		return statement;
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
			return row[queryFieldsByNameInRow.get(columnLabel)];
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		try {
			return Boolean.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		try {
			return Byte.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public short getShort(String columnLabel) throws SQLException {
		try {
			return Short.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public int getInt(String columnLabel) throws SQLException {
		try {
			return Integer.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public long getLong(String columnLabel) throws SQLException {
		try {
			return Long.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public float getFloat(String columnLabel) throws SQLException {
		try {
			return Float.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		try {
			return Double.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		try {
			return BigDecimal.valueOf(Long.valueOf(row[queryFieldsByNameInRow.get(columnLabel)]), scale);

		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		try {
			return row[queryFieldsByNameInRow.get(columnLabel)].getBytes();
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		}
	}

	@Override
	public Date getDate(String columnLabel) throws SQLException {
		int columnIndex = queryFieldsByNameInRow.get(columnLabel);
		return getDate(columnIndex + 1);
	}

	@Override
	public Time getTime(String columnLabel) throws SQLException {
		int columnIndex = queryFieldsByNameInRow.get(columnLabel);
		return getTime(columnIndex + 1);
	}

	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		int columnIndex = queryFieldsByNameInRow.get(columnLabel);
		return getTimestamp(columnIndex + 1);
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		return new ByteArrayInputStream(row[queryFieldsByNameInRow.get(columnLabel)].getBytes());
	}

	@Override
	public int findColumn(String columnLabel) throws SQLException {
		return queryFieldsByNameInRow.get(columnLabel);
	}

	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		try {
			return new BigDecimal(row[queryFieldsByNameInRow.get(columnLabel)]);
		} catch (NullPointerException e) {
			throw new SQLException("Field does not exit");
		} catch (NumberFormatException e) {
			throw new SQLException("Field is not a number");
		}
	}


	@Override
	public boolean wasNull() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clearWarnings() throws SQLException {
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isBeforeFirst() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isAfterLast() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFirst() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isLast() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void beforeFirst() throws SQLException {

	}

	@Override
	public void afterLast() throws SQLException {

	}

	@Override
	public boolean first() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean last() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean previous() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {

	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {

	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getType() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		return false;
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void insertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isClosed() throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new UnsupportedOperationException();
	}
}
