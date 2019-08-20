package cat.altimiras.jdbc.polarbear.statement;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest(DirsIterator.class)
public class DirsIteratorTest {

	private LocalDateTime before = LocalDateTime.of(2019, 1, 1, 12, 55);
	private LocalDateTime after = LocalDateTime.of(2019, 1, 1, 12, 59);

	@Test(expected = IllegalArgumentException.class)
	public void validateStepPostive() throws Exception {
		new DirsIterator(Paths.get("/"), before, after, -1, 100);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validateMaxLimitPostive() throws Exception {
		new DirsIterator(Paths.get("/"), before, after, 1, -8);
	}

	@Test(expected = IllegalArgumentException.class)
	public void validateAfterBefore() throws Exception {
		new DirsIterator(Paths.get("/"), after, before, 1, 5);
	}

	@Test
	public void step() {

		mockStatic(Files.class);
		when(Files.isDirectory(Mockito.any())).thenReturn(true);
		when(Files.exists(Mockito.any())).thenReturn(true);

		DirsIterator iterator = new DirsIterator(Paths.get("/"), before, after, 1, 5);

		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/55", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/56", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/57", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/58", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/59", iterator.next().toString());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void step2() {

		mockStatic(Files.class);
		when(Files.isDirectory(Mockito.any())).thenReturn(true);
		when(Files.exists(Mockito.any())).thenReturn(true);

		DirsIterator iterator = new DirsIterator(Paths.get("/"), before, after, 2, 5);

		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/55", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/57", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/59", iterator.next().toString());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void maxNotFound() {

		mockStatic(Files.class);
		when(Files.isDirectory(Mockito.any())).thenReturn(true);
		when(Files.exists(Mockito.any())).thenReturn(true, true, true, false);

		LocalDateTime after = LocalDateTime.of(2020, 1, 1, 12, 59);
		DirsIterator iterator = new DirsIterator(Paths.get("/"), before, after, 2, 5);

		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/55", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/57", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/59", iterator.next().toString());
		assertFalse(iterator.hasNext());
	}

	@Test
	public void notFoundReset() {

		mockStatic(Files.class);
		when(Files.isDirectory(Mockito.any())).thenReturn(true);
		when(Files.exists(Mockito.any())).thenReturn(true, false, false, false, true);

		DirsIterator iterator = new DirsIterator(Paths.get("/"), before, after, 1, 4);

		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/55", iterator.next().toString());
		assertTrue(iterator.hasNext());
		assertEquals("/2019/01/01/12/59", iterator.next().toString());
		assertFalse(iterator.hasNext());
	}

}