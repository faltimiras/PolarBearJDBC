package cat.altimiras.jdbc.polarbear.connection;

import cat.altimiras.jdbc.polarbear.PolarBearException;

import java.sql.Connection;

public class ConnectionFactory {


	public static String getProviderName(String url) {

		String[] parts = url.split("//");
		if (parts.length != 2) {
			return null;
		}

		String[] def = parts[0].split(":");
		if (def.length != 3) {
			return null;
		}
		if (!def[0].equals("jdbd") && !def[1].equals("polarbear")) {
			return null;
		}

		if (def[2].equals("s3") || def[2].equals("fs") || def[2].equals("gcs")) {
			return def[2];
		}

		return null;
	}

	public static Connection getConnection(String url, String user, String psw) throws PolarBearException {
		try {
			String provider = getProviderName(url);
			if (provider == null) {
				throw new PolarBearException("url definition incorrect: Must follow format jdbc::polarbear:<provider>//<url|fs>");
			}

			String target = url.substring(url.indexOf("//") +2);

			switch (provider) {
				case "s3":
					return new S3Connection(target, user, psw);
				case "fs":
					return new FSConnection(target);
				case "gcs":
					return new GCSConnection(target, user, psw);
				default:
					throw new PolarBearException("provider not supported");
			}

		} catch (PolarBearException e) {
			throw e;
		} catch (Exception e) {
			throw new PolarBearException("url definition incorrect: Must follow format jdbc:polarbear:<provider>//<url|fs>", e);
		}

	}

}
