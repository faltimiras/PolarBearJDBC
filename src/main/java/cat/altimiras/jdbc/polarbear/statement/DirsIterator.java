package cat.altimiras.jdbc.polarbear.statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;

public class DirsIterator implements Iterator<Path> {

	private final static Logger log = LoggerFactory.getLogger(DirsIterator.class);

	private final DateTimeFormatter pathGenerator;

	private final Path base;
	private final LocalDateTime to;
	private final int step;
	private final int notFoundMaxLimit;
	private LocalDateTime current;
	private Path next;

	public DirsIterator(Path base, LocalDateTime from, LocalDateTime to, String pathPattern, int step, int notFoundMaxLimit) {

		if (step < 0) {
			throw new IllegalArgumentException("step must be positive");
		}
		this.step = step;

		if (notFoundMaxLimit < 0) {
			throw new IllegalArgumentException("notFoundMaxLimit must be positive");
		}
		this.notFoundMaxLimit = notFoundMaxLimit;

		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from" + from + " must be before to:" + to);
		}

		this.pathGenerator = DateTimeFormatter.ofPattern(pathPattern);

		this.current = from;
		this.to = to;
		this.base = base;

	}

	@Override
	public boolean hasNext() {

		int notFound = 0;

		while (!current.isAfter(to) && notFound < notFoundMaxLimit) {

			next = base.resolve(Paths.get(pathGenerator.format(current)));

			if (Files.exists(next) && Files.isDirectory(next)) {
				log.debug("Next path: {}", next);
				current = current.plus(step, ChronoUnit.MINUTES);
				return true;
			} else {
				log.debug("Path do not exist: {}", next);
				current = current.plus(step, ChronoUnit.MINUTES);
				notFound++;
			}
		}

		return false;
	}

	@Override
	public Path next() {
		return next;
	}
}
