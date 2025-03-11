package cie.ui;

import ch.swingfx.twinkle.NotificationBuilder;
import ch.swingfx.twinkle.event.NotificationEvent;
import ch.swingfx.twinkle.event.NotificationEventAdapter;
import ch.swingfx.twinkle.style.INotificationStyle;
import ch.swingfx.twinkle.style.closebutton.NullCloseButton;
import ch.swingfx.twinkle.style.theme.LightDefaultNotification;
import ch.swingfx.twinkle.window.Positions;
import lombok.RequiredArgsConstructor;

import java.util.function.Consumer;

public class Notify {
	private static final INotificationStyle DEFAULT_STYLE =
			new LightDefaultNotification().withCloseButton(new NullCloseButton());

	private static NotificationBuilder builder(String message,
	                                           int displayTime,
	                                           Consumer<NotificationEvent> actionClicked) {
		NotificationBuilder builder = new NotificationBuilder()
				.withStyle(DEFAULT_STYLE)
				.withFadeInAnimation(true)
				.withFadeOutAnimation(true)
				.withPosition(Positions.NORTH_EAST)
				.withTitle("CIE ID")
				.withMessage(message)
				.withDisplayTime(1000 * displayTime);

		if (actionClicked != null) {
			builder.withListener(new NotificationEventAdapterClicked(actionClicked));
		}

		return builder;
	}

	public static void message(String message, int displayTime, Consumer<NotificationEvent> actionClicked) {
		builder(message, displayTime, actionClicked).showNotification();
	}

	public static void message(String message, int displayTime) {
		builder(message, displayTime, null).showNotification();
	}

	@RequiredArgsConstructor
	private static final class NotificationEventAdapterClicked extends NotificationEventAdapter {
		private final Consumer<NotificationEvent> onClicked;

		@Override
		public void clicked(NotificationEvent event) {
			onClicked.accept(event);
		}
	}
}
