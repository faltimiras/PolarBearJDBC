package cat.altimiras.jdbc.polarbear;

import java.sql.SQLException;

public class PolarBearException extends SQLException {
	public PolarBearException(String reason) {
		super(reason);
	}

	public PolarBearException(String reason, Throwable cause) {
		super(reason, cause);
	}
}
