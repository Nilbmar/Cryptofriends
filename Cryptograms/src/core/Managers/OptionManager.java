package core.Managers;

import java.util.HashMap;

import core.Data.Options.Option;
import core.Data.Options.OptionBool;

public class OptionManager {
	private HashMap<String, Option> options = null;
	
	public OptionManager() {
		options = new HashMap<String, Option>();
		defaultOptions();
	}
	
	public void addOption(Option option) {
		if (option != null) {
			options.put(option.getName(), option);
		}
	}
	
	public Option getOption(String key) {
		Option option = null;
		if (options.containsKey(key)) {
			option = options.get(key);
		}
		
		return option;
	}
	
	/* TODO:
	 * THIS IS TEMPORARY FOR TESTING PURPOSES
	 * SO I DON'T HAVE TO CLUTTER UP GameManager
	 * WITH ADDING DEFAULT OPTIONS
	 */
	private void defaultOptions() {
		addOption(new OptionBool("hideAuthor", true));
	}
}
