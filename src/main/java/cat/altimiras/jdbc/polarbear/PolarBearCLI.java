package cat.altimiras.jdbc.polarbear;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class PolarBearCLI {
	private final static String SEPARATOR = " | ";

	public static void main(String... args) throws Exception {
		Options options = new Options();
		Option connection = new Option("c", "connection", true, "JDBC connection string");
		connection.setRequired(true);
		options.addOption(connection);
		options.addOption(new Option("u", "user", true, "User"));
		options.addOption(new Option("p", "password", false, "Password"));
		options.addOption(new Option("s", "step", false, "Rows are printed one by one. You need to press Enter"));
		options.addOption(new Option("d", "debug", false, "More error information"));

		CommandLineParser parser = new DefaultParser();
		CommandLine cmd = parser.parse(options, args);
		Scanner scanner = new Scanner(System.in);

		//Ask for password
		String password = null;
		String user = null;
		if (cmd.hasOption("u")) {
			user = cmd.getOptionValue("u");
		}
		if (user != null && cmd.hasOption("p")) {
			System.out.print("Password: ");
			password = scanner.next();
		}

		Class.forName("cat.altimiras.jdbc.polarbear.PolarBearDriver");
		Connection conn = DriverManager.getConnection(cmd.getOptionValue("c"), user, password);

		boolean step = cmd.hasOption("d");
		while (true) {
			System.out.println("query:");
			String query = scanner.nextLine();
			if (query.equalsIgnoreCase("exit")) {
				break;
			}
			if (!query.isEmpty()) {
				try {
					long ini = System.currentTimeMillis();
					Statement stmt = conn.createStatement();
					ResultSet rs = stmt.executeQuery(query);
					processResultSet(scanner, rs, step);
					long end = System.currentTimeMillis();
					System.out.println("End of results. Elapsed time(ms):" + (end - ini));
				} catch (PolarBearException e) {
					System.err.println("ERROR: " + e.getMessage());
					if (cmd.hasOption("d")) {
						e.printStackTrace();
					}
				}
			}
		}
		System.out.println("Bye");
	}

	private static void processResultSet(Scanner scanner, ResultSet rs, boolean step) throws Exception {
		while (rs.next()) {
			if (step) {
				scanner.nextLine();
			}
			printRow(rs);
			System.out.println();
		}
	}

	private static void printRow(ResultSet rs) {
		try {
			int i = 1;
			while (true) {
				System.out.print(rs.getString(i));
				System.out.print(SEPARATOR);
				i++;
			}
		} catch (Exception e) {
			//end of row, nothing to do
		}
	}
}