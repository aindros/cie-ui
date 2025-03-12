package cie.ui;

import cie.validator.Validator;
import lombok.RequiredArgsConstructor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class PasswordField extends JPanel {
	private final JPasswordField[] passwordFields;
	private Runnable onSubmit;
	private final List<BiFunction<String, Integer, Validator>> onSubmitValidators = new ArrayList<>();

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

			passwordField.addKeyListener(new PasswordFieldKeyAdapter(i, passwordFields, this::submit));
			passwordFields[i] = passwordField;
		}
	}

	public void onSubmit(Runnable action) {
		onSubmit = action;
	}

	public void submit() {
		if (onSubmitValidators != null) {
			for (BiFunction<String, Integer, Validator> func : onSubmitValidators) {
				Validator validator = func.apply(getPassword(), passwordFields.length);
				validator.validate();
				if (!validator.isValid()) {
					return;
				}
			}
		}
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

	public PasswordField addOnSubmitValidator(BiFunction<String, Integer, Validator> validator) {
		onSubmitValidators.add(validator);
		return this;
	}
}
