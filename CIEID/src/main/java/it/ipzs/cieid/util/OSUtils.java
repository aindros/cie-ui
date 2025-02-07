package it.ipzs.cieid.util;

/**
 * Utility class to check the operating system.
 */
public class OSUtils {
	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	private static boolean is(String...names) {
		for (String name : names) {
			if (OS_NAME.contains(name)) {
				return true;
			}
		}

		return false;
	}

	public static boolean isWindows() {
		return is("win");
	}

	public static boolean isMac() {
		return is("mac");
	}

	public static boolean isUnix() {
		return is("nix", "nux", "aix");
	}

	public static boolean isSolaris() {
		return is("sunos");
	}
}
