package cie.ui;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.closebutton.NullCloseButton;
import ch.swingfx.twinkle.style.theme.LightDefaultNotification;
import ch.swingfx.twinkle.window.Positions;

public class Notify {
	private static final INotificationStyle DEFAULT_STYLE =
			new LightDefaultNotification().withCloseButton(new NullCloseButton());

	public static NotificationBuilder builder() {
		return new NotificationBuilder()
				.withStyle(DEFAULT_STYLE)
				.withFadeInAnimation(true)
				.withFadeOutAnimation(true)
				.withPosition(Positions.NORTH_EAST);
	}
}
