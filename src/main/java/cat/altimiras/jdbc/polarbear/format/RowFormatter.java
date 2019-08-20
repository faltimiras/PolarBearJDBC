package cat.altimiras.jdbc.polarbear.format;

public interface RowFormatter {

	Object[] parse (String raw);

}
