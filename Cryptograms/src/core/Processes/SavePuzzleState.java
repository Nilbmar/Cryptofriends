package core.Processes;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

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
	
	public void save() {
		setTarget();
		
		try {
			FileWriter writer = new FileWriter(target);
			writer.write("This is the " + target + " file");
			writer.flush();
			writer.close();
		} catch (IOException ioExc) {
			System.out.println(ioExc.toString());
		}
	}
	
}
