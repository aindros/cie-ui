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

import it.ipzs.cieid.MainFrame;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class InsertPINPanel extends CIEPanel {
	private final JButton buttonPair = new JButton("Abbina");
	private JButton buttonCancel = new JButton("Annulla");
	private final JLabel labelInsertPIN = new JLabel("Inserisci il PIN");
	private PasswordField passwordField;
	private Runnable onCancelAction = null;

	public InsertPINPanel() {
		super("Abbina la tua CIE", "Dopo aver collegato e installato il lettore di smart card, posiziona la CIE sul lettore ed inserisci il PIN");
		initComponents();
	}

	private void initComponents() {
		JLabel labelImage = new JLabel("");
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setIcon(new ImageIcon(MainFrame.class.getResource("/it/ipzs/cieid/res/icona_lettore_card_white.png")));
		labelImage.setBounds(29, 194, 211, 205);
		add(labelImage);

		labelInsertPIN.setHorizontalAlignment(SwingConstants.CENTER);
		labelInsertPIN.setFont(new Font("Dialog", Font.BOLD, 22));
		labelInsertPIN.setBounds(252, 259, 299, 36);
		add(labelInsertPIN);

		passwordField = new PasswordField(8);
		passwordField.setBounds(250, 300, 300, 30);
		add(passwordField);

		buttonPair.addActionListener(e -> {
			log.info("Inizia 'Abbina'");
			passwordField.submit();
		});
		buttonPair.setForeground(Color.WHITE);
		buttonPair.setBackground(new Color(30, 144, 255));
		buttonPair.setBounds(258, 524, 114, 25);

		initButtonsPanel();
	}

	private void initButtonsPanel() {
		buttonCancel = new JButton("Annulla");
		buttonCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (onCancelAction != null) {
					log.info("Annulla abbinamento carta");
					onCancelAction.run();
				}
			}
		});
		buttonCancel.setForeground(Color.WHITE);
		buttonCancel.setBackground(new Color(30, 144, 255));

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setBackground(new Color(255, 255, 255));
		FlowLayout flowLayout = (FlowLayout) buttonsPanel.getLayout();
		flowLayout.setHgap(100);
		buttonsPanel.setBounds(133, 500, 384, 36);
		buttonsPanel.add(buttonCancel);
		buttonsPanel.add(buttonPair);
		add(buttonsPanel);
	}

	public String getPassword() {
		return passwordField.getPassword();
	}

	public void clear() {
		passwordField.clear();
	}

	public void onSubmit(Runnable action) {
		passwordField.onSubmit(action);
	}

	public void onCancel(Runnable action) {
		onCancelAction = action;
	}

	@Override
	public void requestFocus() {
		super.requestFocus();
		passwordField.requestFocus();
	}

	/**
	 * @deprecated Must be found something to incapsulate this button.
	 */
	@Deprecated
	public JButton getButtonPair() {
		return buttonPair;
	}

	/**
	 * @deprecated Must be found something to incapsulate this button.
	 */
	@Deprecated
	public JButton getButtonCancel() {
		return buttonCancel;
	}
}
