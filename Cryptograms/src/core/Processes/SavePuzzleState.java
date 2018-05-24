package core.Processes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import core.Data.PuzzleState;

public class SavePuzzleState {
	private Path path = null;
	private String saveDataPath = "src/core/Assets/SaveData";
	private String separator = FileSystems.getDefault().getSeparator();
	private String ext = ".json";
	private String fileName = null;
	private String target = null;
	private PuzzleState puzzleState = null;

	public SavePuzzleState(PuzzleState puzzleState) {
		this.puzzleState = puzzleState;
	}
	
	private void setTarget() {
		if (puzzleState != null) {
			path = FileSystems.getDefault().getPath(saveDataPath).toAbsolutePath();
			fileName = puzzleState.getFileName();
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
		json.put("state", puzzleState.getState().toString());
		json.put("fileName", puzzleState.getFileName());
		json.put("letterCount", puzzleState.getPuzzleSize());
		
		
		JSONArray jsonArray = new JSONArray();
		String[] answers = puzzleState.getAnswers();
		for (int x = 0; x < answers.length; x++) {
			jsonArray.add(answers[x]);
		}
		
		json.put("answers", jsonArray);
		
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
