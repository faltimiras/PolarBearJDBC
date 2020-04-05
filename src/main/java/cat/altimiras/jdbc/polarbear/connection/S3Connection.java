package cat.altimiras.jdbc.polarbear.connection;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.S3TableManager;
import cat.altimiras.jdbc.polarbear.query.QueryParser;
import cat.altimiras.jdbc.polarbear.statement.S3Statement;
import software.amazon.awssdk.auth.credentials.AnonymousCredentialsProvider;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.nio.file.InvalidPathException;
import java.sql.SQLException;
import java.sql.Statement;

public class S3Connection extends PolarBearConnection {

	private final S3Client s3Client;
	private final String bucket;

	private final String user;
	private final String psw;

	//https://github.com/awsdocs/aws-doc-sdk-examples/tree/master/javav2/example_code/s3/src/main/java/com/example/s3
	//https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/javav2/example_code/s3/src/main/java/com/example/s3/S3ObjectOperations.java
	//https://github.com/awsdocs/aws-doc-sdk-examples/blob/master/javav2/example_code/s3/src/main/java/com/example/s3/S3AsyncOps.java

	public S3Connection(String target, String user, String psw) throws PolarBearException {
		super(target);
		this.user = user;
		this.psw = psw;
		try {

			AwsCredentialsProvider awsCredentialsProvider;
			if (user == null || user.isEmpty()) {
				awsCredentialsProvider = AnonymousCredentialsProvider.create();
			} else {
				AwsBasicCredentials credentials = AwsBasicCredentials.create(user, psw);
				awsCredentialsProvider = StaticCredentialsProvider.create(credentials);
			}


			Region region = Region.of(target.split("\\.")[1]);
			this.s3Client = S3Client.builder()
					.region(region)
					.credentialsProvider(awsCredentialsProvider)
					.build();

			this.bucket = target.split("/")[1];

			this.tableManager = new S3TableManager(s3Client, bucket);
			this.queryParser = new QueryParser(this.tableManager);

		} catch (InvalidPathException e) {
			throw new PolarBearException(target + "is not a file system path", e);

		} catch (Exception e) {
			throw new PolarBearException("Unable to connect to S3", e);
		}
	}

	@Override
	public Statement createStatement() throws SQLException {
		return new S3Statement(target, tableManager, queryParser, this, s3Client, bucket);
	}

	@Override
	public void close() throws SQLException {
		super.close();
		s3Client.close();
	}
}
