package cat.altimiras.jdbc.polarbear.connection;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.FSTableManager;
import cat.altimiras.jdbc.polarbear.statement.FSStatement;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.sql.Statement;

public class FSConnection extends  PolarBearConnection{

	public FSConnection(String target) throws PolarBearException {
		super(target);

		try {
			Path basePath = Paths.get(target);

			if (!Files.isDirectory(basePath)) {
				throw new PolarBearException(target + "is not a directory");
			}
			this.tableManager = new FSTableManager(basePath);

		} catch (InvalidPathException e){
			throw new PolarBearException(target + "is not a file system path", e);
		} catch (SecurityException e) {
			throw new PolarBearException(target + "is not readable", e);
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new FSStatement(target, tableManager, queryParser);
	}

}
