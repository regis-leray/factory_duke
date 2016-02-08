package factoryduke;

import java.util.List;
import java.util.Set;

public interface InstanceBuilder<T> {

	T toOne();

	CollectionBuilder<T> times(int size);

	interface CollectionBuilder<T> {
		List<T> toList();

		Set<T> toSet();
	}
}
