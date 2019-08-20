package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

public class DateFormatterTest {

	@Test
	public void localDateTimeWithoutTime() throws Exception {

		LocalDateTime dateTime = DateFormatter.parse("2019/12/12");
		assertEquals("2019-12-12T00:00", dateTime.toString());
	}

	@Test
	public void localDateTimeWithoutTime2() throws Exception {

		LocalDateTime dateTime = DateFormatter.parse("2019-12-12");
		assertEquals("2019-12-12T00:00", dateTime.toString());
	}

	@Test
	public void localDateTime() throws Exception {

		LocalDateTime dateTime = DateFormatter.parse("2019/12/12 15:55");
		assertEquals("2019-12-12T15:55", dateTime.toString());
	}

	@Test
	public void localDateTime2() throws Exception {

		LocalDateTime dateTime = DateFormatter.parse("2019-12-12 13:33");
		assertEquals("2019-12-12T13:33", dateTime.toString());
	}

	@Test(expected = PolarBearException.class)
	public void wrongFormat() throws Exception {
		DateFormatter.parse("2019-15-12 13:33");
	}
}
