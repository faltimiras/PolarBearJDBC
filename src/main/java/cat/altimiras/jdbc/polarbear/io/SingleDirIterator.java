package cat.altimiras.jdbc.polarbear.io;

import java.nio.file.Path;
import java.util.Iterator;

public class SingleDirIterator implements Iterator<Path> {
	private boolean hasNext = true;

	private final Path path;

	public SingleDirIterator(Path path) {
		this.path = path;
	}

	@Override
	public boolean hasNext() {
		try {
			return hasNext;
		} finally {
			this.hasNext = false;
		}
	}

	@Override
	public Path next() {
		return this.path;
	}
}