package factoryduke.utils;

import java.util.Optional;

public class ObjectUtils {

	private ObjectUtils() {
	}

	public static <T> T instanciate(Optional<String> clazz, T defaultInstance) {
		return clazz.map(c -> {
			try {
				return (T) Class.forName(c).newInstance();
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw new IllegalStateException(e);
			}
		}).orElse(defaultInstance);
	}
}
