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

package cie.ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import cie.util.CIEResource;
import it.ipzs.cieid.MainFrame;
import it.ipzs.cieid.util.*;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;

import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;

public class IntroPanel extends JPanel {
	private JTabbedPane tabbedPane;
	private final MainFrame mainFrame;

	/**
	 * Create the frame.
	 */
	public IntroPanel(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		init();
		initWelcomePanel();
		initBeginningPanel();
	}

	private void init() {
		setBackground(Color.WHITE);
		setBounds(100, 100, 800, 600);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(0, -30, 800, 630);
		add(tabbedPane);
	}

	private void addTab(JPanel panel) {
		tabbedPane.addTab("New tab", null, panel, null);
	}

	private void initWelcomePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		addLabel(panel, "Benvenuto in CIE ID");
		addText(panel, "Il software che ti permette di autenticarti ai servizi online della Pubblica Amministrazione per mezzo della nuova carta d'identità elettronica");
		addButton(panel, "Continua", null);

		addImage(panel, "flusso_intro_01.png");

		addTab(panel);
	}

	private void initBeginningPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(Color.WHITE);

		final JCheckBox chckbxNoMore = new JCheckBox("Non mostrare più");
		chckbxNoMore.setBackground(Color.WHITE);
		chckbxNoMore.setBounds(591, 508, 157, 23);
		panel.add(chckbxNoMore);

		addLabel(panel, "Per iniziare");
		addText(panel, "Munisciti di un lettore di smart card contactless, della tua Carta di Identità Elettronica e del PIN");
		addButton(panel, "Inizia", e -> {
			if(chckbxNoMore.isSelected()) {
				Utils.setProperty("nomore", "true");
			}

			mainFrame.showHome();
		});

		addImage(panel, "flusso_intro_02.png");

		addTab(panel);
	}

	private void addLabel(JPanel panel, String text) {
		JLabel label = new JLabel(text);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("Dialog", Font.BOLD, 30));
		label.setLocation(0, 36);
		label.setSize(tabbedPane.getWidth(), 36);

		panel.add(label);
	}

	private void addText(JPanel panel, String text) {
		JTextPane textPane = new JTextPane();
		textPane.setText(text);
		textPane.setEditable(false);
		textPane.setBounds(150, 110, tabbedPane.getWidth() - 300, 55);
		panel.add(textPane);

		StyledDocument doc = textPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
	}

	private void addButton(JPanel panel, String caption, ActionListener actionListener) {
		JButton button = new JButton(caption);
		button.addActionListener(e -> tabbedPane.setSelectedIndex(1));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(30, 144, 255));
		button.setBounds(334, 499, 114, 25);
		panel.add(button);

		if (actionListener != null) {
			button.addActionListener(actionListener);
		}

		button.setBounds(337, 507, 114, 25);
	}

	private void addImage(JPanel panel, String name) {
		JLabel label = new JLabel("");
		label.setHorizontalAlignment(SwingConstants.CENTER);

		CIEResource.getImage(name)
				.ifPresent(bufferedImage -> label.setIcon(new ImageIcon(Utils.scaleimage(400, 300, bufferedImage))));

		label.setBounds(100, 150, tabbedPane.getWidth() - 200, 335);
		panel.add(label);
	}
}
