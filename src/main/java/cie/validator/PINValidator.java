package cie.validator;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class PINValidator implements Validator {
	private @Getter boolean valid = false;
	private final String pin;
	private final int pinSize;
	private final Component parentComponent;

	public PINValidator(String pin, int pinSize, Component parentComponent) {
		this.pin = pin;
		this.pinSize = pinSize;
		this.parentComponent = parentComponent;
	}

	private void showErrorDialog(String title, String message) {
		log.error("{}: {}", title, message);
		JOptionPane.showMessageDialog(parentComponent,
		                              message,
		                              title,
		                              JOptionPane.ERROR_MESSAGE);
	}

	private boolean pinIsNotNumeric() {
		for (int i = 0; i < pin.length(); i++) {
			if (!Character.isDigit(pin.charAt(i))) return true;
		}

		return false;
	}

	@Override
	public void validate() {
		if (pin.length() != pinSize || pinIsNotNumeric()) {
			showErrorDialog("PIN non corretto", "Il PIN deve essere composto da " + pinSize + " numeri");
			return;
		}

		valid = true;
	}
}
