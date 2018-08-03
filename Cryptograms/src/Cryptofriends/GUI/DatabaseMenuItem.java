package Cryptofriends.GUI;

import javafx.scene.control.MenuItem;

public class DatabaseMenuItem extends MenuItem {
	private String databaseName = null;
	
	public DatabaseMenuItem(String dbName) {
		setName(dbName);
	}
	
	private void setName(String dbName) {
		databaseName = dbName;
		String name = null;
		
		if (dbName.toLowerCase().contains("database")) {
			int endIndex = dbName.toLowerCase().indexOf("database");
			name = dbName.toUpperCase().substring(0, 1) 
					+ dbName.toLowerCase().substring(1, endIndex);
		} else {
			name = dbName.substring(0, 1) 
					+ dbName.toLowerCase().substring(1, dbName.length());
		}
		
		this.setText(name);
	}

}
