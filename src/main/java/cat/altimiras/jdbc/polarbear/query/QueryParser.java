package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

	//ex: SELECT * FROM table WHERE ts > 123 AND ts < 456

	private static final Pattern queryStructure = Pattern.compile("(?i)SELECT\\s*(.*)\\s*(?i)FROM\\s*(\\S*)\\s*((?i)WHERE\\s*(.*))?");

	private static final Pattern tsFieldStructure = Pattern.compile("(.*)\\s(<|>|<=|=>)\\s*'(.*?[^\\\\])??((\\\\\\\\)+)?+'");

	public Query parse(String sql) throws PolarBearException {

		Query query = new Query();
		Matcher matcher = queryStructure.matcher(sql.trim());
		if (matcher.find()) {
			query.setTable(matcher.group(2).trim());
			query.setFields(fields(matcher.group(1).trim()));
			if (matcher.group(4) != null) {
				Limits limits = tsLimits(matcher.group(4).trim());
				query.setTsUpLimit(limits.upper);
				query.setTsLowerLimit(limits.lower);
			}
		} else {
			throw new PolarBearException("Invalid query: " + sql);
		}
		return query;
	}

	/**
	 * @param fields raw fields sql part. field1 as alias1, field2
	 *
	 * @return
	 */
	private List<Field> fields(String fields) throws PolarBearException {

		if (fields.trim().equals("*")) {
			return null;
		}

		List<Field> queryFields = new ArrayList<>();
		String[] fieldsDefs = fields.split(",");
		for (String fieldDef : fieldsDefs) {
			String[] fieldAlias = fieldDef.split("(?i) as ");
			Field f;
			switch (fieldAlias.length) {
				case 1:
					f = new Field(fieldAlias[0].trim(), null);
					break;
				case 2:
					f = new Field(fieldAlias[0].trim(), fieldAlias[1].trim());
					break;
				default:
					throw new PolarBearException("Invalid fields definition in SELECT");
			}
			queryFields.add(f);
		}
		return queryFields;
	}

	//TODO support clever queries -> antlr

	/**
	 * @param sql ts => 1 | ts < 2 | ts > 122 and ts <= 555
	 *
	 * @return
	 *
	 * @throws PolarBearException
	 */
	private Limits tsLimits(String sql) throws PolarBearException {

		String[] parts = sql.split("(?i)(\\s)+and(\\s)+");

		Limits limits = new Limits();

		if (parts.length > 2) {
			throw new PolarBearException("Invalid time limits on WHERE. Only supported simple time range definition with AND and <,> Close to: " + sql);
		}

		String tsField = null;
		for (String s : parts) {

			Matcher matcher = tsFieldStructure.matcher(s.trim());
			if (matcher.find()) {
				int a = 0;
				/*if (matcher.groupCount() != 4) {
					throw new PolarBearException("Invalid time limit close to: " + sql);
				}*/
				if (tsField == null) {
					tsField = matcher.group(1);
				} else if (!tsField.equals(matcher.group(1))) {
					throw new PolarBearException("Filtering fields are not equals");
				}

				switch (matcher.group(2)) {
					case "<":
						limits.upper = DateFormatter.parse(matcher.group(3)).minus(1, ChronoUnit.MINUTES);
						break;
					case "<=":
						limits.upper = DateFormatter.parse(matcher.group(3));
						break;
					case ">":
						limits.lower = DateFormatter.parse(matcher.group(3)).plus(1, ChronoUnit.MINUTES);
						break;
					case "=>":
						limits.lower = DateFormatter.parse(matcher.group(3));
						break;
					default:
						throw new PolarBearException("Comparator not supported. only >,>=,<,<=");
				}
			/*	if ( matcher.group(2).equals(">")){
					limits.lower = DateFormatter.parse( matcher.group(3));
				}
				else if ( matcher.group(2).equals("<") ){
					limits.upper = DateFormatter.parse( matcher.group(3));
				}
				else {
					throw new PolarBearException("Comparator not supported. only > <");
				}*/

			} else {
				throw new PolarBearException("Invalid time limit close to: " + sql);
			}

/*
			String[] filterParts = s.split("(\\s)+");
			if (filterParts.length != 3){
				throw new PolarBearException("Invalid time limit close to: " + sql);
			}
*/
			//validate filtering same field
	/*		if (tsField == null) {
				tsField = filterParts[0];
			} else if (!tsField.equals(filterParts[0])){
				throw new PolarBearException("Filtering fields are not equals");
			}


			if (filterParts[1].equals(">")){
				limits.lower = DateFormatter.parse(filterParts[2]);
			}
			else if (filterParts[1].equals("<") ){
				limits.upper = DateFormatter.parse(filterParts[2]);
			}
			else {
				throw new PolarBearException("Comparator not supported. only > <");
			}

	 */
		}
		return limits;
	}

	private class Limits {
		private LocalDateTime upper;
		private LocalDateTime lower;
	}

}
