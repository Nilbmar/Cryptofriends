package core.Loaders;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class SQLLoader implements Loader {
	private String target;
	private String phrase;
	private String databasePath = "src/core/Assets";
	//private String databaseFileName = "PhraseDatabase.sqlite3";
	private String databaseFileName = "chinook.db";
	private String jdbc = "jdbc:sqlite:";
	private List<String> phrases;

	public void connect() {
		Connection conn = null;
		Path path = FileSystems.getDefault().getPath(databasePath, databaseFileName);
		try {
			String url = jdbc + path.toAbsolutePath();
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
		} catch (SQLException sqlEx) {
			System.out.println("SQL Opening Exception: " + sqlEx.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
					System.out.println("Connection to SQLite has been closed.");
				}
			} catch (SQLException sqlEx) {
				System.out.println("SQL Closing Exception: " + sqlEx.getMessage());
			}
		}
	}
	
	@Override
	public void setTarget(String target) {
		this.target = target;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void load() {
		Path path = FileSystems.getDefault().getPath(databasePath, databaseFileName);
		if (databasePath != null && Files.exists(path, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
			try {
				phrases = Files.readAllLines(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
