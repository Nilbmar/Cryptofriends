package core.Loaders;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import core.Managers.PuzzleManager;

public class SQLLoader implements Loader {
	private PuzzleManager puzzleMan;
	private String target;
	private String phrase;
	private String databasePath = "src/core/Assets";
	private String databaseFileName = "PuzzleDatabase.sqlite3";
	//private String databaseFileName = "chinook.db";
	private String jdbc = "jdbc:sqlite:";
	private List<String> phrases;
	
	public SQLLoader(PuzzleManager puzzleMan) {
		this.puzzleMan = puzzleMan;
	}
	
	private void loadPuzzles(Connection conn) {
		System.out.println("Loading puzzles\n\n");
		String sql = "SELECT Author, NumOfAuthorQuote, Quote, Subject FROM Phrase";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);
			String key = null;
			
			// Add each result from database to PuzzleManager
			while (resultSet.next()) {
                key = resultSet.getString("Author") + ":" + resultSet.getInt("NumOfAuthorQuote");
                puzzleMan.addPuzzle(key, resultSet.getString("Quote"), resultSet.getString("Subject"));
			}
		} catch (SQLException sqlEx) {
			System.out.println("Problem creating SQL statement: " + sqlEx.getMessage());
		}
	}

	public void connect() {
		Connection conn = null;
		Path path = FileSystems.getDefault().getPath(databasePath, databaseFileName);
		try {
			String url = jdbc + path.toAbsolutePath();
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			
			loadPuzzles(conn);
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
