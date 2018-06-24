package core.Loaders;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.Data.PlayerTime;

public class TimeLoader implements Loader {
	private PlayerTime playerTime = null;
	private Path path = null;
	private String saveDataPath = "resources/PlayerTimes";
	private String separator = FileSystems.getDefault().getSeparator();
	private String ext = ".json";
	private String target = null;
	
	public void setPlayerTimeObj(PlayerTime playerTime) {
		this.playerTime = playerTime;
	}
	
	@Override
	public void setTarget(String playerName) {
		path = FileSystems.getDefault().getPath(saveDataPath).toAbsolutePath();
		String fileName = "player_" + playerName;
		target = path.toString() + separator + fileName + ext;
	}

	@Override
	public String getTarget() {
		return target;
	}

	@Override
	public void load() {
		Path file = FileSystems.getDefault().getPath(target);
		
		// Only try to load if target is set and file targeted exists
		if (target != null && Files.exists(file, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
			
			JSONParser parser = new JSONParser();

			try {
				Object obj = parser.parse(new FileReader(target));
				JSONObject json = (JSONObject) obj;
				
				// Data needed by PlayerTime
				long totalTime = (long) json.get("totalTime");
				JSONArray jsonArray = (JSONArray) json.get("puzzleTimes");
				
				playerTime.loadPrevTotalTime(totalTime);
				
				// Iterate array in JSON file
				// and
				// separate the puzzle name from the puzzle time
				// separator is a colon
				//
				// Have to suppress warning for Iterator
				// json doesn't allow for setting parameter
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonArray.iterator();
				String unparsed = null;
				int indexOfSeparator = -1;
				char separatorChar = ':';
				while (iterator.hasNext()) {
					unparsed = iterator.next();
					indexOfSeparator = unparsed.indexOf(separatorChar);
					String puzzleName = unparsed.substring(0, indexOfSeparator);
					long puzzleTime = Long.parseLong(unparsed.substring(indexOfSeparator + 1));
					playerTime.loadPrevPuzzleTimes(puzzleName, puzzleTime);
				}
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
}
