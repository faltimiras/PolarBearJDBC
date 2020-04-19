package cat.altimiras.jdbc.polarbear.query;

import cat.altimiras.jdbc.polarbear.PolarBearException;
import cat.altimiras.jdbc.polarbear.def.FSTableManager;
import cat.altimiras.jdbc.polarbear.def.TableManager;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ValidatorTest {

	private TableManager tableManager;
	private Parser parser = new Parser();

	@Before
	public void setUp() {
		tableManager = new FSTableManager(Paths.get("src/test/resources/fs"));
	}

	@Test
	public void okStar() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select * from test_table2");

		validator.validate(query);
	}

	@Test
	public void okSelectFields() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select f1, f2 as a from test_table2");

		validator.validate(query);

		assertEquals("test_table2", query.getFields().get(0).getTable());
		assertEquals("f1", query.getFields().get(0).getName());
		assertEquals("test_table2", query.getFields().get(1).getTable());
		assertEquals("f2", query.getFields().get(1).getName());
		assertEquals("a", query.getFields().get(1).getAlias());
	}

	@Test
	public void notExistField() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select notexist from test_table2");

		try {
			validator.validate(query);
			fail("Must fail");
		} catch (PolarBearException e){
			assertEquals("Field 'notexist' do not exist", e.getMessage());
		}
	}

	@Test
	public void notExistWithTableField() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select table.notexist from test_table2");

		try {
			validator.validate(query);
			fail("Must fail");
		} catch (PolarBearException e){
			assertEquals("Table 'table' do not exist", e.getMessage());
		}
	}

	@Test
	public void fillWhereFields() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select * from test_table2 where f1 > 44");

		validator.validate(query);

		assertEquals("test_table2", query.getWhere().getOperand1AsField().getTable());
		assertEquals("f1", query.getWhere().getOperand1AsField().getName());
	}

	@Test
	public void notExistWhereFields() throws Exception {
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select * from test_table2 where notExist > 44");

		try {
			validator.validate(query);
			fail("Must fail");
		} catch (PolarBearException e){
			assertEquals("Field 'notExist' do not exist", e.getMessage());
		}
	}

	@Test
	public void repeatedAlias() throws Exception{
		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select f1 as a, f2 as a from test_table2 where notExist > 44");

		try {
			validator.validate(query);
			fail("Must fail");
		} catch (PolarBearException e){
			assertEquals("Alias 'a' is already used in another field", e.getMessage());
		}
	}

	@Test
	public void moreThanOnePartitionedTable() throws Exception{

		Validator validator = new Validator(tableManager);
		Query query = parser.parse("select f1 as a, f2 as a from test_table2, test_table1 where notExist > 44");

		try {
			validator.validate(query);
			fail("Must fail");
		} catch (PolarBearException e){
			assertEquals("Only one time partitioned tables is allowed", e.getMessage());
		}
	}
}