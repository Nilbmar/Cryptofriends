package core.Loaders;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class SQLLoader implements Loader {
	private String target;
	private String databasePath = "resources";
	private String databaseFileName;
	private String jdbc = "jdbc:sqlite:";
	
	public SQLLoader() {
	}
	
	protected abstract void loadResources(Connection conn);
	
	private void connect() {
		Connection conn = null;
		Path path = FileSystems.getDefault().getPath(databasePath, databaseFileName);
		try {
			String url = jdbc + path.toAbsolutePath();
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			
			loadResources(conn);
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
	
	protected void setDatabaseName(String name) {
		databaseFileName = name + ".sqlite3";
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
		connect();
	}
}
