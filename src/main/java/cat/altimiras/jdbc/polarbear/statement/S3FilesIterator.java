package cat.altimiras.jdbc.polarbear.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.S3Object;
import software.amazon.awssdk.services.s3.paginators.ListObjectsV2Iterable;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class S3FilesIterator implements Iterator<InputStream> {

	private final static Logger log = LoggerFactory.getLogger(S3FilesIterator.class);

	private final S3Client s3Client;
	private final String bucket;
	private final String table;

	private final DateTimeFormatter pathGenerator;
	private final LocalDateTime to;
	private LocalDateTime current;
	private Iterator<S3Object> it;

	public S3FilesIterator(S3Client s3Client, String bucket, String table, LocalDateTime from, LocalDateTime to, String pathPattern) {
		this.s3Client = s3Client;
		this.bucket = bucket;
		this.table = table;

		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from" + from + " must be before to:" + to);
		}

		this.pathGenerator = DateTimeFormatter.ofPattern(pathPattern);

		this.current = from;
		this.to = to;

		String path = greatestCommonPrefix(pathGenerator.format(current), pathGenerator.format(to));
		String prefix = pathPattern.startsWith("/") ? table + path : table + "/" + path;

		ListObjectsV2Request listReq = ListObjectsV2Request.builder()
				.bucket(bucket)
				.prefix(prefix)
				.maxKeys(500)
				.build();

		ListObjectsV2Iterable files = s3Client.listObjectsV2Paginator(listReq);

		it = files.contents().iterator();
	}

	private String greatestCommonPrefix(String a, String b) {
		int minLength = Math.min(a.length(), b.length());
		for (int i = 0; i < minLength; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				return a.substring(0, i);
			}
		}
		return a.substring(0, minLength);
	}

	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	@Override
	public InputStream next() {

		S3Object s3Object = it.next();
		log.debug("Object to check {}", s3Object.key());
		if (s3Object.size() == 0) {
			log.debug("Discarding {} (size=0)", s3Object.key());
			//this breaks iterator specification, but there is no way to just return files
			return null;
		}

		log.debug("Getting object {}", s3Object.key());
		ResponseInputStream<GetObjectResponse> raw = s3Client.getObject(
				GetObjectRequest.builder()
						.bucket(bucket)
						.key(s3Object.key()).build(),
				ResponseTransformer.toInputStream());
		return raw;
	}
}