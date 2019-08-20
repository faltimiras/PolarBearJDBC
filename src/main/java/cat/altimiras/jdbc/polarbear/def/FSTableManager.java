package cat.altimiras.jdbc.polarbear.def;

import cat.altimiras.jdbc.polarbear.PolarBearException;

import java.nio.file.Files;
import java.nio.file.Path;

public class FSTableManager extends TableManager {

	private Path base;

	public FSTableManager(Path base) {
		this.base = base;
	}

	@Override
	protected byte[] read(String name) throws PolarBearException {
		try {
			Path metadata = base.resolve(name + ".json");
			if (!Files.exists(metadata) || !Files.isReadable(metadata)) {
				throw new PolarBearException("Table " + name + " do not exist");
			}
			return Files.readAllBytes(metadata);
		} catch (Exception e) {
			throw new PolarBearException("Error reading table " + name + " metadata");
		}
	}

	@Override
	protected void write(String name, byte[] content) throws PolarBearException {
		try {
			Path metadata = base.resolve(name);
			Files.write(metadata, content);
		} catch (Exception e) {
			throw new PolarBearException("Error persisting table " + name + " metadata");
		}
	}
}
