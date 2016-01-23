package factoryduke;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class FactoryScanner {

	private String[] packages = new String[0];

	private Class[] interfaces = new Class[0];

	private Class[] annotations = new Class[0];

	public FactoryScanner withPackages(String... packages) {
		this.packages = packages;
		return this;
	}

	public FactoryScanner addPackages(String... packages) {
		this.packages = Stream.of(this.packages, packages).flatMap(Stream::of).toArray(String[]::new);
		return this;
	}

	public FactoryScanner withInterfaces(Class... interfaces) {
		this.interfaces = interfaces;
		return this;
	}

	public FactoryScanner withAnnotations(Class... annotations) {
		this.annotations = annotations;
		return this;
	}

	public Optional<String> scanOne() {
		return scan().stream().findFirst();
	}

	public List<String> scan() {
		final FastClasspathScanner scanner = new FastClasspathScanner(packages).scan();
		final List<String> founded = new ArrayList<>();

		founded.addAll(scanWith(interfaces, clazz -> scanner.getNamesOfClassesImplementing(clazz)));
		founded.addAll(scanWith(annotations, clazz -> scanner.getNamesOfClassesWithAnnotation(clazz)));

		return founded;
	}

	private List<String> scanWith(Class[] classes, Function<Class, List<String>> func) {
		List<String> matches = new ArrayList<>();
		for (Class clazz : classes) {
			matches.addAll(func.apply(clazz));
		}
		return matches;
	}

}