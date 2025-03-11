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

import java.awt.EventQueue;
import javax.swing.JFrame;
import cie.ui.Notify;
import it.ipzs.cieid.IntroFrame;
import it.ipzs.cieid.MainFrame;
import it.ipzs.cieid.util.Utils;


public class Main {
	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(() -> new ArgumentParser()
				.addOption(0, "pinwrong",          () -> Notify.message("PIN errato", 5))
				.addOption(0, "cardnotregistered", Main::notifyCardNotRegistered)
				.addOption(0, "pinlocked",         Main::notifyPinLocked)
				.addDefaultOption(() -> {
					try {
						showUI(args);
					} catch (Exception e) {
						e.printStackTrace();
					}
				})
				.parse(args));
	}

	private JFrame frame;

	public static void showUI(String... args) {
		Main window = new Main(args);
		window.frame.setVisible(true);
	}

	/**
	 * Create the application.
	 */
	public Main(String[] args) {
		if("false".equals(Utils.getProperty("nomore", "false"))) {
			frame = new IntroFrame();
		} else {
			frame = new MainFrame(args);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void notifyCardNotRegistered() {
		Notify.message("Carta non abbinata, premere qui per abbinare la CIE", 10, (e) -> Main.showUI());
	}

	private static void notifyPinLocked() {
		Notify.message("Carta bloccata, premere qui per sbloccarla con il PUK", 10, (e) -> Main.showUI("unlock"));
	}
}
