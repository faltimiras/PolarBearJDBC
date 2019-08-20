package cat.altimiras.jdbc.polarbear.connection;

public class GCSConnection extends PolarBearConnection {

	private String user;
	private String psw;

	public GCSConnection(String target, String user, String psw) {
		super(target);
		this.user = user;
		this.psw = psw;
	}
}
