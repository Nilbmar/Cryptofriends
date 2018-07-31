package core.Data.Options;

public abstract class Option {
	protected Options name = null;
	protected OptionType type = null;
	public enum OptionType { BOOL, INT, STRING };
	public enum Options { HIDE_AUTHOR }
	
	public OptionType getType() { return type; }
	public Options getName() { return name; }
	 
	
}
