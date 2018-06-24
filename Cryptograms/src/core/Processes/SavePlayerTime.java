package core.Processes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.Data.PlayerTime;

public class SavePlayerTime {
	private Path path = null;
	private String saveDataPath = "resources/PlayerTimes";
	private String separator = FileSystems.getDefault().getSeparator();
	private String ext = ".json";
	private String fileName = null;
	private String target = null;
	private String playerName = null;
	private PlayerTime playerTime;
	

	public SavePlayerTime(PlayerTime playerTime, String playerName) {
		this.playerTime = playerTime;
		this.playerName = playerName;
	}
	
	private void setTarget() {
		if (playerTime != null) {
			path = FileSystems.getDefault().getPath(saveDataPath).toAbsolutePath();
			
			// Create the folder if it doesn't exist
			File directory = new File(saveDataPath);
		    if (!directory.exists()){
		        directory.mkdir();
		    }
		    
			fileName = "player_" + playerName;
			target = path.toString() + separator + fileName + ext;
		}
	}
	
	// Suppress Warnings is the only option
	// JSONObject extends HashMap, 
	// but doesn't allow for <k, v> parameters to be set
	@SuppressWarnings("unchecked")
	public void save() {
		setTarget();
		
		JSONObject json = new JSONObject();
		json.put("playerName", playerName);
		json.put("totalTime", playerTime.getTotalTime());		
		
		JSONArray jsonArray = new JSONArray();
		String[] times = playerTime.getPuzzleTimes();
		System.out.println("Save - times array: ");
		for (int x = 0; x < times.length; x++) {
			System.out.println("time - " + x + ": " + times[x]);
			jsonArray.add(times[x]);
		}
		
		json.put("puzzleTimes", jsonArray);
		
		try {
			FileWriter writer = new FileWriter(target);
			writer.write(json.toJSONString());
			writer.flush();
			writer.close();
		} catch (IOException ioExc) {
			System.out.println(ioExc.toString());
		}
	}
}
