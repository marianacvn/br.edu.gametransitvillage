package br.com.ihm.marianacvn.view;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Objects;

public class HelpPanel extends BasePanel {
	public HelpPanel(String key, String imageBackground) {
		super(key, imageBackground);
		setLayout(null);
		initTutorialTextAreaV2();
	}

	private void initTutorialTextAreaV2() {
		final JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBounds(100, 50, BaseFrame.DEFAULT_WIDTH - 200, BaseFrame.DEFAULT_HEIGHT - 100);
		fxPanel.setBorder(new LineBorder(Color.BLACK, 1));
		this.add(fxPanel);

		Platform.runLater(() -> {
			WebView webView = new WebView();
			webView.getEngine().load(
					Objects.requireNonNull(HelpPanel.class.getResource("/assets/files/help-text.html")).toExternalForm()
			);

			fxPanel.setScene(new Scene(webView));
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage.getImage(), 0, 0, null);
	}

}
