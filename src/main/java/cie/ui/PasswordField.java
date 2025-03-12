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
			public void keyReleased(KeyEvent e) {
				actionOnArrowKeys(0, e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				} else {
					requestFocus(1);
				}
			}
		});

		for (int i = 1; i < passwordFields.length - 1; i++) {
			JPasswordField passwordFieldBefore = passwordFields[i - 1];

			final int index = i;
			passwordFields[i].addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					actionOnArrowKeys(index, e);
				}

				@Override
				public void keyTyped(KeyEvent e) {
					if (e.getKeyChar() == '\b') {
						passwordFieldBefore.setText("");
						requestFocus(index - 1);
					} else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
						e.consume();
					} else {
						requestFocus(index + 1);
					}
				}
			});
		}

		final int index = passwordFields.length - 1;
		JPasswordField passwordFieldBefore = passwordFields[index - 1];
		passwordFields[index].addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				actionOnArrowKeys(index, e);
			}

			@Override
			public void keyTyped(KeyEvent e) {
				if (e.getKeyChar() == '\n' || e.getKeyChar() == '\r') {
					submit();
				} else if (e.getKeyChar() == '\b') {
					passwordFieldBefore.setText("");
					requestFocus(index - 1);
				} else if (e.getKeyChar() < '0' || e.getKeyChar() > '9') {
					e.consume();
				} else if (passwordFields[index].getPassword().length > 0) {
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

	@Override
	public void requestFocus() {
		super.requestFocus();
		passwordFields[0].requestFocus();
	}

	private void requestFocus(int index) {
		if (index < 0 || passwordFields.length <= index) return;
		passwordFields[index].requestFocus();
	}

	private void actionOnArrowKeys(int index, KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) {
			e.consume();
			return;
		}

		requestFocus(keyCode == KeyEvent.VK_RIGHT? index + 1 : index - 1);
	}
}
