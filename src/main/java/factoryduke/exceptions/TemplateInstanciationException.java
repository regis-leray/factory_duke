package factoryduke.exceptions;

public class TemplateInstanciationException extends RuntimeException {
	public TemplateInstanciationException(String message) {
		super(message);
	}

	public TemplateInstanciationException(Throwable cause) {
		super(cause);
	}
}
