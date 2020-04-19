package cat.altimiras.jdbc.polarbear.format;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableDefinition;

public class CSVDeserializer extends RowDeserializer {
	private final String separator;

	public CSVDeserializer(TableDefinition tableDefinition) {
		super(tableDefinition);
		this.separator = tableDefinition.getSeparator();
	}

	@Override
	public String[] parse(byte[] raw) throws PolarBearException {
		if (raw == null) {
			return null;
		}
		return new String(raw).split(this.separator, -1);
	}
}
