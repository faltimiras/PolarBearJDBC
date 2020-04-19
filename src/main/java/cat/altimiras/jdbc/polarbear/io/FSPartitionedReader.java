package cat.altimiras.jdbc.polarbear.io;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.format.RowDeserializer;

import java.nio.file.Path;
import java.util.Iterator;

public class FSPartitionedReader extends FSBaseReader {

	public FSPartitionedReader(Iterator<Path> itDirs, RowDeserializer rowDeserializer, long maxRows) throws PolarBearException {
		super(itDirs, rowDeserializer, maxRows);
	}
}