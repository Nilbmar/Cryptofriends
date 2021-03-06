package core.Loaders;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import core.Managers.PuzzleManager;

public class PuzzleLoader extends SQLLoader {
	private PuzzleManager puzzleMan;
	
	public PuzzleLoader(PuzzleManager puzzleMan) {
		this.puzzleMan = puzzleMan;
		setDatabaseName("PuzzleDatabase");
	}

	protected void loadResources(Connection conn) {
		System.out.println("Loading puzzles\n\n");
		String sql = "SELECT Key, Author, NumOfAuthorQuote, Quote, Subject, Hints FROM Phrase";
		
		try {
			Statement stmt = conn.createStatement();
			ResultSet resultSet = stmt.executeQuery(sql);			
			
			// Add each result from database to PuzzleManager
			while (resultSet.next()) {
                puzzleMan.addPuzzle(
                		resultSet.getInt("Key"), 
                		resultSet.getString("Hints"), resultSet.getString("Quote"), 
                		resultSet.getString("Subject"), resultSet.getString("Author"),
                		resultSet.getInt("NumOfAuthorQuote"));
			}
		} catch (SQLException sqlEx) {
			System.out.println("Problem creating SQL statement: " + sqlEx.getMessage());
		}
	}
}
