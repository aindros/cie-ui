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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JFrame;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.event.NotificationEvent;
import ch.swingfx.twinkle.event.NotificationEventAdapter;
import ch.swingfx.twinkle.style.closebutton.NullCloseButton;
import ch.swingfx.twinkle.style.theme.LightDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import it.ipzs.cieid.IntroFrame;
import it.ipzs.cieid.MainFrame;
import it.ipzs.cieid.util.Utils;


public class Main {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {

				if(args.length > 0 && args[0].equals("pinwrong"))
				{
					notifyPinWrong();
				}
				else if(args.length > 0 && args[0].equals("cardnotregistered"))
				{
					notifyCardNotRegistered();
				}
				else if(args.length > 0 && args[0].equals("pinlocked"))
				{
					notifyPinLocked();
				}
				else
				{
					try {
						showUI(args);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	public static void showUI(String[] args)
	{
		Main window = new Main(args);
		window.frame.setVisible(true);
	}
	/**
	 * Create the application.
	 */
	public Main(String[] args) {
		initialize(args);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(String[] args) {

		if("false".equals(Utils.getProperty("nomore", "false")))
		{
			frame = new IntroFrame();
		}
		else
		{
			frame = new MainFrame(args);
		}

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private static void notifyPinWrong()
	{
		NotificationBuilder nb = createNotificationBuilder();
		nb.withTitle("CIE ID");
		nb.withMessage("PIN errato");
		//nb.withIcon(CrmIcons.CALL);
		nb.withDisplayTime(1000 * 5);

//		nb.withListener(new NotificationEventAdapter() {
//			@Override
//			public void clicked(NotificationEvent event)
//			{
//				// do nothing
//				setLookAndFeel();
//				MainApplication.showUI(new String[] {});
//			}
//		});

		nb.showNotification();
	}

	private static void notifyCardNotRegistered()
	{
		NotificationBuilder nb = createNotificationBuilder();
		nb.withTitle("CIE ID");
		nb.withMessage("Carta non abbinata, premere qui per abbinare la CIE");
		//nb.withIcon(CrmIcons.CALL);
		nb.withDisplayTime(1000 * 10);

		nb.withListener(new NotificationEventAdapter() {
			@Override
			public void clicked(NotificationEvent event)
			{
//				setLookAndFeel();
				Main.showUI(new String[] {});
			}
		});

		nb.showNotification();
	}

	private static void notifyPinLocked()
	{
		NotificationBuilder nb = createNotificationBuilder();
		nb.withTitle("CIE ID");
		nb.withMessage("Carta bloccata, premere qui per sbloccarla con il PUK");
		//nb.withIcon(CrmIcons.CALL);
		nb.withDisplayTime(1000 * 10);

		nb.withListener(new NotificationEventAdapter() {
			@Override
			public void clicked(NotificationEvent event)
			{
				Main.showUI(new String[] {"unlock"});
			}
		});

		nb.showNotification();
	}

	public static NotificationBuilder createNotificationBuilder()
	{
		NotificationBuilder nb = new NotificationBuilder();
		LightDefaultNotification style = new LightDefaultNotification();
		style.withCloseButton(new NullCloseButton());
		nb.withStyle(style);
		nb.withFadeInAnimation(true);
		nb.withFadeOutAnimation(true);
		nb.withPosition(Positions.NORTH_EAST);
		//nb.withDisplayTime(10000);

		return nb;
	}

}
