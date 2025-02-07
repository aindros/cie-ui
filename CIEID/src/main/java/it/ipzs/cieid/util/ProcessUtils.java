package it.ipzs.cieid.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ProcessUtils {
	public static boolean isRunning(String name) throws IOException {
		if (OSUtils.isUnix()) {
			Process process = Runtime.getRuntime().exec("pgrep " + name);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			/* There will be one line, the pid */
			String line = reader.readLine();

			reader.close();

			/* TODO Updating to Java 11+, we can use isBlank() instead calling trim().isEmpty() */
			return line != null && !line.trim().isEmpty();
		}

		return true;
	}
}
