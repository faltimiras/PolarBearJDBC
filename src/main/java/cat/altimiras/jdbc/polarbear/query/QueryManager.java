package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QueryManager {

	private final static Logger log = LoggerFactory.getLogger(QueryManager.class);

	private TableManager tableManager;

	public QueryManager(TableManager tableManager) {
		this.tableManager = tableManager;
	}

	public Query parse(String sql) throws PolarBearException {

		Parser parser = new Parser();
		try {
			Query query = parser.parse(sql);

			Validator validator = new Validator(tableManager);
			validator.validate(query);

			return query;
		} catch (QueryException e) {
			log.error("Error parsing the query", e);
			throw new PolarBearException("Parser exception", e);
		} catch (PolarBearException e) {
			throw e;
		} catch (Exception e) {
			log.error("Unexpected error",e);
			throw new PolarBearException("Unexpected error",e);
		}
	}
}
