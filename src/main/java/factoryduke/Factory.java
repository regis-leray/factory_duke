package factoryduke;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) //can use in class only.
public @interface Factory {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.METHOD) //can use in method only.
	@interface Define {
	}
}
