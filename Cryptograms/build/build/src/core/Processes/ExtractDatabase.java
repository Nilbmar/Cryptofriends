package core.Processes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.glass.ui.PlatformFactory;

public class ExtractDatabase {
	/*
	public ExtractDatabase() {
		File dir;
		String dbUrl = "jdbc:sqlite:";
		try {
		    dir = PlatformFactory.getPlatformFactory().getPrivateStorage();
		    File db = new File (dir, DB_NAME);
		 
		    // extract the database from JAR/APK/IPA and move it to app location
		    extractDatabase("/databases", dir.getAbsolutePath(), DB_NAME);
		                
		    dbUrl = dbUrl + db.getAbsolutePath();
		} catch (IOException ex) {
		    System.out.println("Error " + ex);
		}
	}
	
	private void extractDatabase(String pathIni, String pathEnd, String name) {
		Class<?> sqliteClass = null;
		try {
			sqliteClass = Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try (InputStream myInput = sqliteClass.getResourceAsStream(pathIni+ "/" + name)) {
	        String outFileName =  pathEnd + "/" + name;
	        try (OutputStream myOutput = new FileOutputStream(outFileName)) {
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = myInput.read(buffer)) > 0) {
	                myOutput.write(buffer, 0, length);
	            }
	            myOutput.flush();
	        } catch (IOException ex) { 
	        	
	        }
	    } catch (IOException ex) { 
	    	
	    } 
	}
	*/
}
