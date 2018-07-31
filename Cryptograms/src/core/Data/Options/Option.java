package core.Data.Options;

public abstract class Option {
	protected String name = null;
	protected OptionType type = null;
	public enum OptionType { BOOL, INT, STRING };
	
	public OptionType getType() { return type; }
	public String getName() { return name; }
	 
	
}
