package cat.altimiras.jdbc.polarbear.def;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.format.RowDeserializer;
import cat.altimiras.jdbc.polarbear.format.RowFormatterFactory;
import cat.altimiras.jdbc.polarbear.io.FSPartitionedReader;
import cat.altimiras.jdbc.polarbear.io.FSReader;
import cat.altimiras.jdbc.polarbear.io.Reader;
import cat.altimiras.jdbc.polarbear.statement.DirsIterator;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

public class FSTableManager extends TableManager {
	private final Path base;

	public FSTableManager(Path base) {
		this.base = base;
	}

	@Override
	protected byte[] readDefinition(String name) throws PolarBearException {
		try {
			Path metadata = base.resolve(name + ".json");
			if (!Files.exists(metadata) || !Files.isReadable(metadata)) {
				throw new PolarBearException("Table '" + name + "' do not exist");
			}
			return Files.readAllBytes(metadata);
		} catch (IOException e) {
			throw new PolarBearException("Error reading table '" + name + "' metadata");
		}
	}

	@Override
	protected void writeDefinition(String name, byte[] content) throws PolarBearException {
		try {
			Path metadata = base.resolve(name);
			Files.write(metadata, content);
		} catch (IOException e) {
			throw new PolarBearException("Error persisting table '" + name + "' metadata");
		}
	}

	@Override
	public Reader read(String name, LocalDateTime from, LocalDateTime to, long maxRows) throws PolarBearException {

		TableDefinition tableDefinition = this.definitions.get(name);
		RowDeserializer rowDeserializer = RowFormatterFactory.get(tableDefinition.getFormat(), tableDefinition);
		if (tableDefinition.isStarTable()) {
			if (from != null || to != null) {
				throw new PolarBearException("Star tables are not time partitioned");
			}
			return new FSReader(base.resolve(name), rowDeserializer);
		} else {
			DirsIterator dirsIterator = new DirsIterator(
				base.resolve(name),
				from,
				to,
				tableDefinition.getPartition().getPartitionsFormat(),
				tableDefinition.getPartition().getStep(),
				tableDefinition.getNotFoundMaxLimit());

			return new FSPartitionedReader(dirsIterator, rowDeserializer, maxRows);
		}
	}
}
