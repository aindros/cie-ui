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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentParserUnitTest {
	@BeforeEach
	void setUp() {
	}

	@Test
	void test() {
		final Wrapper wrapper = new Wrapper();

		ArgumentParser parser = new ArgumentParser()
				.addOption(0, "pinwrong",          () -> wrapper.value = "pinwrong")
				.addOption(0, "cardnotregistered", () -> wrapper.value = "cardnotregistered")
				.addOption(0, "pinlocked",         () -> wrapper.value = "pinlocked");

		parser.parse("pinwrong");
		assertEquals("pinwrong", wrapper.value);

		parser.parse("cardnotregistered");
		assertEquals("cardnotregistered", wrapper.value);

		parser.parse("pinlocked");
		assertEquals("pinlocked", wrapper.value);

		wrapper.value = "none";
		parser.parse("pinlocked1");
		assertEquals("none", wrapper.value);
	}

	private static final class Wrapper {
		String value;
	}
}
