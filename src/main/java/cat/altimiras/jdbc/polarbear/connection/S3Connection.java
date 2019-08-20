package cat.altimiras.jdbc.polarbear.connection;

public class S3Connection extends PolarBearConnection {

	private String user;
	private String psw;

	public S3Connection(String target, String user, String psw) {
		super(target);
		this.user = user;
		this.psw = psw;
	}
}
