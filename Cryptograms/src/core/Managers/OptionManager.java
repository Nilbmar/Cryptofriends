package core.Managers;

import java.util.HashMap;

import core.Data.Options.Option;
import core.Data.Options.Option.Options;
import core.Data.Options.OptionBool;
import core.Data.Options.OptionInt;
import core.Data.Options.OptionStr;
import core.Loaders.OptionsLoader;

public class OptionManager {
	private HashMap<Options, Option> options = null;
	
	public OptionManager() {
		options = new HashMap<Options, Option>();
		defaultOptions();
		OptionsLoader loader = new OptionsLoader();
		loader.setOptionManager(this);
		loader.load();
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
	
	public boolean getBool(Options option) {
		boolean value = false;
		
		try {
			value = ((OptionBool) options.get(option)).getValue();
		} catch (Exception exc) {
			wrongValue(option.name());
		}
		
		return value;
	}
	
	public int getInt(Options option) {
		int value = -1;
		
		try {
			value = ((OptionInt) options.get(option)).getValue();
		} catch (Exception exc) {
			wrongValue(option.name());
		}
		
		return value;
	}
	
	public String getString(Options option) {
		String value = null;
		
		try {
			value = ((OptionStr) options.get(option)).getValue();
		} catch (Exception exc) {
			wrongValue(option.name());
		}
		
		return value;
	}
	
	private void wrongValue(String name) {
		System.out.println("Option " + name + " is not available or of the wrong type.");
	}
	
	/* TODO:
	 * THIS IS TEMPORARY FOR TESTING PURPOSES
	 * SO I DON'T HAVE TO CLUTTER UP GameManager
	 * WITH ADDING DEFAULT OPTIONS
	 */
	private void defaultOptions() {
		addOption(new OptionBool(Options.HIDE_AUTHOR, true));
	}
}
