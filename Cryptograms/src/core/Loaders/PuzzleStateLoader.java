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

import core.Data.PuzzleState;

public class PuzzleStateLoader implements Loader {
	private Path path = null;
	private String saveDataPath = "src/core/Assets/SaveData";
	private String separator = FileSystems.getDefault().getSeparator();
	private String ext = ".json";
	private String target = null;
	private PuzzleState puzzleState = null;

	public PuzzleState getPuzzleState() { return puzzleState; }
	
	@Override
	public void setTarget(String fileName) {
		path = FileSystems.getDefault().getPath(saveDataPath).toAbsolutePath();
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
				
				
				/// Data needed by PuzzleState
				int letterCount = Math.toIntExact((long) json.get("letterCount"));
				String fileName = (String) json.get("fileName");
				String state = (String) json.get("state");
				JSONArray jsonArray = (JSONArray) json.get("answers");
				
				// Setup PuzzleState
				puzzleState = new PuzzleState(letterCount, fileName);
				puzzleState.setState(state);
				
				// Have to suppress warning for Iterator- json doesn't allow for setting parameter
				@SuppressWarnings("unchecked")
				Iterator<String> iterator = jsonArray.iterator();
				String unparsedAnswer = null;
				int charNum = -1;
				while (iterator.hasNext()) {
					charNum++;
					unparsedAnswer = iterator.next();
					// Check for HINT
					if (unparsedAnswer.contains("HINT - HINT")) {
						puzzleState.answered(charNum, "HINT", "HINT");
					} else {
						// Separate Char
						String answerChar = "" + unparsedAnswer.charAt(0);
						
						// Separate playerKey
						int playerIndex = unparsedAnswer.indexOf(" - ") + 3;
						String playerKey = unparsedAnswer.substring(playerIndex);
						
						puzzleState.answered(charNum, answerChar, playerKey);
					}
				}
			} catch (IOException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
