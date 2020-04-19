package cat.altimiras.jdbc.polarbear.query;

public class QueryException extends RuntimeException {

	public QueryException(String reason) {
		super(reason);
	}

	public QueryException(String reason, Throwable cause) {
		super(reason, cause);
	}
}
