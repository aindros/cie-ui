package cie.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PasswordField extends JPanel {
	private final JPasswordField[] passwordFields;
	private Runnable onSubmit;

	public PasswordField(int digits) {
		passwordFields = new JPasswordField[digits];
		setLayout(new FlowLayout());
		setBackground(Color.WHITE);

		for (int i = 0; i < digits; i++) {
			JPasswordField passwordField = new JPasswordField();
			passwordField.setHorizontalAlignment(SwingConstants.CENTER);
			passwordField.setFont(new Font("Dialog", Font.BOLD, 25));
			passwordField.setPreferredSize(new Dimension(25, 25));
			add(passwordField);

			if (i < digits - 1) {
				add(Box.createHorizontalStrut(2));
			}

			passwordFields[i] = passwordField;
		}

		passwordFields[0].addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				} else {
					if (digits > 1) {
						passwordFields[1].requestFocus();
					}
				}
			}
		});

		for (int i = 1; i < passwordFields.length - 1; i++) {
			JPasswordField passwordFieldBefore = passwordFields[i - 1];
			JPasswordField passwordFieldNext = passwordFields[i + 1];

			passwordFields[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\b') {
						passwordFieldBefore.setText("");
						passwordFieldBefore.requestFocus();
					} else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
						e.consume();
					} else {
						passwordFieldNext.requestFocus();
					}
				}
			});
		}

		JPasswordField passwordFieldBefore = passwordFields[passwordFields.length - 2];
		JPasswordField passwordField = passwordFields[passwordFields.length - 1];
		passwordField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') {
					submit();
				} else if (e.getKeyChar() == '\b') {
					passwordFieldBefore.setText("");
					passwordFieldBefore.requestFocus();
				} else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				} else if (passwordField.getPassword().length > 0) {
					e.consume();
				}
			}
		});
	}

	public void onSubmit(Runnable action) {
		onSubmit = action;
	}

	public void submit() {
		onSubmit.run();
	}

	public String getPassword() {
		StringBuilder password = new StringBuilder();

		for (JPasswordField passwordField : passwordFields) {
			password.append(passwordField.getPassword());
		}

		return password.toString();
	}

	public void clear() {
		for (JPasswordField passwordField : passwordFields) {
			passwordField.setText("");
		}
	}
}
