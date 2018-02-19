package core.Loaders;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.List;

public class PuzzleLoader implements Loader {
	private String target;
	private String phrase;
	private String databasePath = "src/core/Assets";
	private String databaseFileName = "PhraseDatabase.txt";
	private List<String> phrases;
	
	public PuzzleLoader() {
		//phrases = new ArrayList<String>();
	}
	
	public String getPhrase() {
		if (phrase == null) {
			load();
		}
		
		if (target != null) {
			try {
			int index = Integer.parseInt(target);
			phrase = phrases.get(index);
			}
			catch (NumberFormatException formatEx) {
				System.out.println("Target is not a number");
			}
			catch (IndexOutOfBoundsException indexEx) {
				System.out.println("Index out of bounds");
			}
		}
		return phrase;
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
