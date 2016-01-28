package factoryduke;

public abstract class Template {
	private String identifier;
	private Class clazz;

	public Template(Class clazz, String identifier) {
		this.clazz = clazz;
		this.identifier = identifier;
	}

	public abstract <T> T create();

	public String getIdentifier() {
		return this.identifier;
	}

	public Class getClazz() {
		return this.clazz;
	}
}
