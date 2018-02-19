package core.Loaders;

public class SQLLoader implements Loader {
	private String target;
	// TODO: ADD SQLite database to get and a get()

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
		// TODO Auto-generated method stub
		
	}

}
