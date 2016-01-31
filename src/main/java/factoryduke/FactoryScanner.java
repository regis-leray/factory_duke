package factoryduke;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;

public class FactoryScanner {

	private String[] packages = new String[0];

	private Class[] interfaces = new Class[0];

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

	public List<String> scan() {
		final FastClasspathScanner scanner = new FastClasspathScanner(packages).scan();
		List<String> matches = new ArrayList<>();
		for (Class clazz : interfaces) {
			matches.addAll(scanner.getNamesOfClassesImplementing(clazz));
		}
		return matches;
	}
}
