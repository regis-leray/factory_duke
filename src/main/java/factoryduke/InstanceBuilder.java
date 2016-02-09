package factoryduke;

import java.util.List;
import java.util.Set;

public interface InstanceBuilder<T> {

	T toOne();

	InstanceBuilder<T> skipGlobalCallbacks(boolean skip);

	CollectionBuilder<T> times(int size);

	interface CollectionBuilder<T> {
		List<T> toList();

		Set<T> toSet();
	}
}
