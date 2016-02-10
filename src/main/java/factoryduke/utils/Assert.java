/**
 * The MIT License
 * Copyright (c) 2016 Regis Leray
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package factoryduke.utils;

public final class Assert {

	private Assert(){
	}

	public static Assertions that(){
		return new Assertions();
	}

	public static class Assertions {
		/**
		 * Assert that an object is not {@code null} .
		 * <pre class="code">Assert.notNull(clazz, "The class must not be null");</pre>
		 * @param object the object to check
		 * @param message the exception message to use if the assertion fails
		 * @throws IllegalArgumentException if the object is {@code null}
		 */
		public Assertions notNull(Object object, String message) {
			if (object == null) {
				throw new IllegalArgumentException(message);
			}
			return this;
		}

		/**
		 * Assert that an object is not {@code null} .
		 * <pre class="code">Assert.notNull(clazz);</pre>
		 * @param object the object to check
		 * @throws IllegalArgumentException if the object is {@code null}
		 */
		public Assertions notNull(Object object) {
			notNull(object, "[Assertion failed] - this argument is required; it must not be null");
			return this;
		}

		/**
		 * Assert a boolean expression, throwing {@code IllegalArgumentException}
		 * if the test result is {@code false}.
		 * <pre class="code">Assert.isTrue(i &gt; 0, "The value must be greater than zero");</pre>
		 * @param expression a boolean expression
		 * @param message the exception message to use if the assertion fails
		 * @throws IllegalArgumentException if expression is {@code false}
		 */
		public Assertions isTrue(boolean expression, String message) {
			if (!expression) {
				throw new IllegalArgumentException(message);
			}
			return this;
		}

		/**
		 * Assert a boolean expression, throwing {@code IllegalStateException}
		 * if the test result is {@code false}. Call isTrue if you wish to
		 * throw IllegalArgumentException on an assertion failure.
		 * <pre class="code">Assert.state(id == null, "The id property must not already be initialized");</pre>
		 * @param expression a boolean expression
		 * @param message the exception message to use if the assertion fails
		 * @throws IllegalStateException if expression is {@code false}
		 */
		public Assertions state(boolean expression, String message) {
			if (!expression) {
				throw new IllegalStateException(message);
			}
			return this;
		}

		/**
		 * Assert a boolean expression, throwing {@link IllegalStateException}
		 * if the test result is {@code false}.
		 * <p>Call {@link #isTrue(boolean)} if you wish to
		 * throw {@link IllegalArgumentException} on an assertion failure.
		 * <pre class="code">Assert.state(id == null);</pre>
		 * @param expression a boolean expression
		 * @throws IllegalStateException if the supplied expression is {@code false}
		 */
		public Assertions state(boolean expression) {
			state(expression, "[Assertion failed] - this state invariant must be true");
			return this;
		}


		/**
		 * Assert a boolean expression, throwing {@code IllegalArgumentException}
		 * if the test result is {@code false}.
		 * <pre class="code">Assert.isTrue(i &gt; 0);</pre>
		 * @param expression a boolean expression
		 * @throws IllegalArgumentException if expression is {@code false}
		 */
		public Assertions isTrue(boolean expression) {
			isTrue(expression, "[Assertion failed] - this expression must be true");
			return this;
		}

		/**
		 * Assert a boolean expression, throwing {@code IllegalArgumentException}
		 * if the test result is {@code false}.
		 * <pre class="code">Assert.isTrue(i &gt; 0);</pre>
		 * @param expression a boolean expression
		 * @throws IllegalArgumentException if expression is {@code false}
		 */
		public Assertions isNot(boolean expression) {
			isTrue(!expression, "[Assertion failed] - this expression must be true");
			return this;
		}

		/**
		 * Assert that the given String has valid text content; that is, it must not
		 * be {@code null} and must contain at least one non-whitespace character.
		 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
		 * @param text the String to check
		 * @param message the exception message to use if the assertion fails
		 * @see StringUtils#hasText
		 */
		public static void hasText(String text, String message) {
			if (!StringUtils.isNotBlank(text)) {
				throw new IllegalArgumentException(message);
			}
		}

		/**
		 * Assert that the given String has valid text content; that is, it must not
		 * be {@code null} and must contain at least one non-whitespace character.
		 * <pre class="code">Assert.hasText(name, "'name' must not be empty");</pre>
		 * @param text the String to check
		 * @see StringUtils#hasText
		 */
		public static void hasText(String text) {
			hasText(text, "[Assertion failed] - this String argument must have text; it must not be null, empty, or blank");
		}
	}
}
