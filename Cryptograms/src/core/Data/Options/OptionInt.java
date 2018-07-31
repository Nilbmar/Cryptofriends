package core.Data.Options;

public class OptionInt extends Option {
	private int value = -1;
	
	public OptionInt(String name, int value) {
		type = OptionType.INT;
		this.name = name;
		this.value = value;
	}
	
	public int getValue() { return value; }
}
