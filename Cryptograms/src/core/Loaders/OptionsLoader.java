package core.Loaders;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import core.Data.Options.Option.Options;
import core.Data.Options.OptionBool;
import core.Managers.OptionManager;

public class OptionsLoader implements Loader {
	private OptionManager optionMan = null;
	private Path path = null;
	private String fileName = "options";
	private String saveDataPath = "resources";
	private String separator = FileSystems.getDefault().getSeparator();
	private String ext = ".json";
	private String target = null;
	
	public void setOptionManager(OptionManager optionMan) {
		this.optionMan = optionMan;
	}
	
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
		setTarget(fileName);
		
		Path file = FileSystems.getDefault().getPath(target);
		
		// Only try to load if target is set and file targeted exists
		if (target != null && Files.exists(file, new LinkOption[]{LinkOption.NOFOLLOW_LINKS})) {
			
			JSONParser parser = new JSONParser();

			try {
				Object obj = parser.parse(new FileReader(target));
				JSONObject json = (JSONObject) obj;
				
				boolean hideAuthor = (boolean) json.get("HIDE_AUTHOR");
				optionMan.addOption(new OptionBool(Options.HIDE_AUTHOR, hideAuthor));
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			}
		}
		
	}
}