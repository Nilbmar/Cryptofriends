package core.Managers;

import java.io.File;
import java.io.FilenameFilter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class DatabaseManager {
	private String separator = System.getProperty("file.separator");
	private String ext = ".sqlite3";
	private String databasePath = "resources" + separator + "puzzles";
	private String databaseFileName = null; //"PuzzleDatabase" + ext;
	private String jdbc = "jdbc:sqlite:";
	private ArrayList<String> dbNames = null;
	
	public DatabaseManager() {
		dbNames = new ArrayList<String>();
		mapDatabases();
		
		System.out.println("Database Names: " + getDatabaseNames());
	}
	
	private void mapDatabases() {		
		File dir = new File(databasePath);
		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(ext);
		    }
		});

		// Strip the extension off of filename
		// and use only filename for key in dbMap
		int indexOfExt = -1;
		String fileName = null;
		for (File file : files) {
			indexOfExt = file.getName().indexOf(ext);
			fileName = file.getName().substring(0, indexOfExt);
			
			dbNames.add(fileName);
		}
	}
	
	public void load(String fileName) {
		setFilename(fileName);
		
		if (fileExists()) {
			Connection conn = connect();
			System.out.println(conn.toString());
			try {
				conn.close();
			} catch (SQLException sqlExc) {
				sqlExc.printStackTrace();
			}
		}
	}
	
	public boolean fileExists() {
		boolean exists = false;
		if (databasePath != null && databaseFileName != null
				&& !databasePath.isEmpty() && !databaseFileName.isEmpty()) {
			
			String target = databasePath + separator + databaseFileName;
			
			File file = new File(target);
			if (file != null && file.exists()) {
				exists = true;
			}	
		}
		
		return exists;
	}
	
	public Connection connect() {
		Connection conn = null;
		String target = databasePath + separator + databaseFileName;
		String url = jdbc + target;
		
		try {
			conn = DriverManager.getConnection(url);
		} catch (SQLException sqlExc) {
			sqlExc.printStackTrace();
		}
		
		return conn;
	}

	public String getPath() { return databasePath; }
	public void setPath(String databasePath) {
		this.databasePath = databasePath;
	}
	
	public String getFilename() { return databaseFileName; }
	public void setFilename(String filename) {
		databaseFileName = filename + ext;
	}
	
	public ArrayList<String> getDatabaseNames() {
		return dbNames;
	}

}
