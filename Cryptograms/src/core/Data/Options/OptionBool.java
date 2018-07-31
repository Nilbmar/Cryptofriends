package core.Data.Options;

public class OptionBool extends Option {
	private boolean value;
	
	public OptionBool(String name, boolean value) {
		type = OptionType.BOOL;
		this.name = name;
		this.value = value;		
	}
	
	public boolean getValue() { return value; }
}
