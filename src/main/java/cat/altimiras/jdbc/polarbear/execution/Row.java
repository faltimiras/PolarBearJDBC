package cat.altimiras.jdbc.polarbear.execution;

public class Row {
	private final int[] positionConversion;

	private final String[] rawDataRow;

	public Row(int[] positionConversion, String[] rawDataRow) {
		this.positionConversion = positionConversion;
		this.rawDataRow = rawDataRow;
	}

	public String getValue(int pos) {
		return rawDataRow[positionConversion[pos]];
	}
}
