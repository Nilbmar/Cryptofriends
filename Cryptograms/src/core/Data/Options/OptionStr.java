package core.Data.Options;

public class OptionStr extends Option {
	private String value = null;
	
	public OptionStr(String name, String value) {
		type = OptionType.STRING;
		this.name = name;
		this.value = value;
	}
	
	public String getValue() { return value; }
}
