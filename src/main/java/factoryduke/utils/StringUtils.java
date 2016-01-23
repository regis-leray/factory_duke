package factoryduke.utils;

public class StringUtils {

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static boolean isEmpty(String s) {
		if ((s != null) && (s.trim().length() > 0))
			return false;
		else
			return true;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static boolean isBlank(String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if ((Character.isWhitespace(str.charAt(i)) == false)) {
				return false;
			}
		}
		return true;
	}
}
