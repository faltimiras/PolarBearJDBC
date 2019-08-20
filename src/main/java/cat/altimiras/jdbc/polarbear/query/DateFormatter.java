package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

class DateFormatter {

	private static final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
			.appendPattern("[yyyy-MM-dd HH:mm][yyyy-MM-dd][yyyy/MM/dd HH:mm]").toFormatter();
	private static final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
			.appendPattern("[yyyy-MM-dd][yyyy/MM/dd]").toFormatter();

	static LocalDateTime parse(String strDate) throws PolarBearException {
		try {
			return LocalDateTime.from(dateTimeFormatter.parse(strDate));
		} catch (DateTimeException e) {
			try {
				return LocalDate.from(dateFormatter.parse(strDate)).atStartOfDay();
			} catch (DateTimeException ee) {
				throw new PolarBearException("Date format not valid", ee);
			}
		} catch (Exception e) {
			throw new PolarBearException("Date format not valid", e);
		}
	}

}
