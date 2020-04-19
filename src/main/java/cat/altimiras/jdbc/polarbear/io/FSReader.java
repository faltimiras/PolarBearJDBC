package cat.altimiras.jdbc.polarbear.io;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.format.RowDeserializer;

import java.nio.file.Path;

/**
 * Reads the content of all files in base path
 */
public class FSReader extends FSBaseReader {

	public FSReader(Path base, RowDeserializer rowDeserializer) throws PolarBearException {
		super(new SingleDirIterator(base), rowDeserializer, -1);
	}
}
