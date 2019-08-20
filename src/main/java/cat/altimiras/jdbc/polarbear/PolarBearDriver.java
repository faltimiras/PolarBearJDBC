package cat.altimiras.jdbc.polarbear;

import cat.altimiras.jdbc.polarbear.connection.ConnectionFactory;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class PolarBearDriver implements Driver {

	//TODO
	//https://www.javaworld.com/article/2074249/create-your-own-type-3-jdbc-driver--part-1.html

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(PolarBearDriver.class);

	static {
		try {
			// Register the PolarBearDriver with DriverManager
			PolarBearDriver driverInst = new PolarBearDriver();
			DriverManager.registerDriver(driverInst);
			System.setSecurityManager(new SecurityManager());
		} catch (Exception e) {
			log.error("Error registering driver", e);
		}
	}

	public Connection connect(String url, Properties info) throws SQLException {
		return ConnectionFactory.getConnection(url, info.getProperty("user"), info.getProperty("password"));
	}

	public boolean acceptsURL(String url) throws SQLException {
		return ConnectionFactory.getProviderName(url) != null;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[0];
	}

	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 0;
	}

	public boolean jdbcCompliant() {
		return false;
	}

	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return null;
	}
}
