package cie.ui;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
class PasswordFieldKeyAdapter extends KeyAdapter {
	private final int index;
	private final JPasswordField[] passwordFields;

	@Override
	public void keyReleased(KeyEvent e) {
		actionOnArrowKeys(index, e);
	}

	private void actionOnArrowKeys(int index, KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) {
			e.consume();
			return;
		}

		requestFocus(keyCode == KeyEvent.VK_RIGHT? index + 1 : index - 1);
	}

	protected void requestFocus(int index) {
		if (index < 0 || passwordFields.length <= index) return;
		passwordFields[index].requestFocus();
	}
}
