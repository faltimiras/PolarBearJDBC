package cat.altimiras.jdbc.polarbear.statement;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DirsIterator implements Iterator<Path> {
	private final static Logger log = LoggerFactory.getLogger(DirsIterator.class);

	private final DateTimeFormatter pathGenerator;

	private final Path base;

	private final LocalDateTime to;

	private final int step;

	private final int notFoundMaxLimit; //-1 disabled

	private LocalDateTime current;

	private Path next;

	public DirsIterator(Path base, LocalDateTime from, LocalDateTime to, String pathPattern, int step,
		int notFoundMaxLimit) {

		if (step < 0) {
			throw new IllegalArgumentException("step must be positive");
		}
		this.step = step;

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

		while (!current.isAfter(to) && (notFound == -1 || notFound < notFoundMaxLimit)) {

			next = base.resolve(Paths.get(pathGenerator.format(current)));

			if (Files.exists(next) && Files.isDirectory(next)) {
				log.debug("Next path: {}", next);
				current = current.plus(step, MINUTES);
				return true;
			} else {
				log.debug("Path do not exist: {}", next);
				current = current.plus(step, MINUTES);
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
