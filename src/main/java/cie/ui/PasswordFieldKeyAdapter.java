package cie.ui;

import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@RequiredArgsConstructor
class PasswordFieldKeyAdapter extends KeyAdapter {
	private final int index;
	private final JPasswordField[] passwordFields;
	private final Runnable onSubmitAction;
	private boolean filledLastField = false;

	@Override
	public void keyReleased(KeyEvent e) {
		actionOnArrowKeys(index, e);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (!isLastField() && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) return;
		filledLastField = passwordFields[index].getPassword().length > 0;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (keyTypedOnLastField(e)) return;

		/* If user insert a character in the last field, must be deleted before the cursor can move to the adjacent field */
		if (e.getKeyChar() == '\b' && !filledLastField) {
			clearAndFocus(index - 1);
			return;
		}

		if (isNotDigit(e.getKeyChar())) {
			e.consume();
			return;
		}

		requestFocus(index + 1);
	}

	private boolean keyTypedOnLastField(KeyEvent e) {
		if (!isLastField()) return false;

		if (isNewLine(e.getKeyChar())) {
			onSubmitAction.run();
			return true;
		}

		if (passwordFields[index].getPassword().length > 0) {
			e.consume();
			return true;
		}

		return false;
	}

	private void actionOnArrowKeys(int index, KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode != KeyEvent.VK_RIGHT && keyCode != KeyEvent.VK_LEFT) {
			e.consume();
			return;
		}

		requestFocus(keyCode == KeyEvent.VK_RIGHT? index + 1 : index - 1);
	}

	private boolean outOfRange(int index) {
		return index < 0 || passwordFields.length <= index;
	}

	private boolean isLastField() {
		if (outOfRange(index)) throw new RuntimeException(String.format("index: %s is out of range", index));
		return index == passwordFields.length - 1;
	}

	private boolean isNewLine(char c) {
		return c == '\n' || c == '\r';
	}

	protected void requestFocus(int index) {
		if (outOfRange(index)) return;
		passwordFields[index].requestFocus();
	}

	protected boolean isNotDigit(char c) {
		return c < '0' || c > '9';
	}

	private void clear(int index) {
		if (outOfRange(index)) return;
		passwordFields[index].setText("");
	}

	private void clearAndFocus(int index) {
		clear(index);
		requestFocus(index);
	}
}
