package cat.altimiras.jdbc.polarbear.def;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.io.Reader;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.time.LocalDateTime;

public class S3TableManager extends TableManager {

	private final static org.slf4j.Logger log = LoggerFactory.getLogger(S3TableManager.class);

	private final S3Client s3Client;
	private final String bucket;

	public S3TableManager(S3Client s3Client, String bucket) {
		this.s3Client = s3Client;
		this.bucket = bucket;
	}

	@Override
	protected byte[] readDefinition(String name) throws PolarBearException {
		try {
			ResponseBytes<GetObjectResponse> raw = s3Client.getObject(
					GetObjectRequest.builder()
							.bucket(bucket)
							.key(makeKey(name)).build(),
					ResponseTransformer.toBytes());
			return raw.asByteArray();
		} catch (Exception e) {
			log.error("Error reading table {} maetadata", name, e);
			throw new PolarBearException("Error reading table '" + name + "' metadata", e);
		}
	}

	@Override
	protected void writeDefinition(String name, byte[] content) throws PolarBearException {
		try {
			s3Client.putObject(
					PutObjectRequest.builder()
							.bucket(bucket)
							.key(makeKey(name))
							.build(),
					RequestBody.fromBytes(content));
		} catch (Exception e) {
			throw new PolarBearException("Error persisting table '" + name + "' metadata");
		}
	}

	@Override
	public Reader read(String name, LocalDateTime from, LocalDateTime to, long maxRows) throws PolarBearException {
		return null;
	}

	private String makeKey(String name) {
		return name + ".json";
	}
}
