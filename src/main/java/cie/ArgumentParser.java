/*-
 * Copyright (c) 2017-2022, the respective contributors, as shown by the AUTHORS file.
 * Copyright 2025 Alessandro Iezzi <aiezzi AT alessandroiezzi PERIOD it>
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS “AS IS”
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package cie;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

public class ArgumentParser {
	private final Map<String, Option> options = new HashMap<>();
	private Option defaultOption = null;

	public ArgumentParser addOption(String option, Runnable action) {
		return addOption(null, option, action);
	}

	public ArgumentParser addOption(Integer index, String option, Runnable action) {
		options.put(option, new Option(index, option, action));
		return this;
	}

	private void showHelp() {}

	public ArgumentParser addDefaultOption(Runnable action) {
		defaultOption = new Option(null, null, action);
		return this;
	}

	private void runDefaultOption() {
		if (defaultOption == null) return;

		defaultOption.action.run();
	}

	public void parse(String... args) {
		if (args == null || args.length == 0) {
			runDefaultOption();
			return;
		}

		for (int i = 0; i < args.length; i++) {
			String arg = args[i];

			if (!options.containsKey(arg)) {
				showHelp();
				continue;
			}

			Option option = options.get(arg);
			if (option.index == null || i == option.index) {
				option.action.run();
			}
		}
	}

	@AllArgsConstructor
	private static final class Option {
		final Integer index;
		final String name;
		final Runnable action;
	}
}
