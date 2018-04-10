package org.pdfsam.ui;


import org.pdfsam.news.HideNewsPanelRequest;
import org.pdfsam.news.ShowNewsPanelRequest;
import org.pdfsam.ui.news.NewsPanel;
import org.sejda.eventstudio.annotation.EventListener;

import javafx.animation.FadeTransition;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class ContentPaneContainer {
	private VBox newsContainer;
	private FadeTransition fadeIn;
	private FadeTransition fadeOut;

	public ContentPaneContainer(NewsPanel news) {
		setNewsContainer(new VBox(news));
        getNewsContainer().getStyleClass().add("news-container");
        getNewsContainer().managedProperty().bind(getNewsContainer().visibleProperty());
        getNewsContainer().setVisible(false);
        setFadeIn(new FadeTransition(new Duration(300), getNewsContainer()));
        getFadeIn().setFromValue(0);
        getFadeIn().setToValue(1);
        setFadeOut(new FadeTransition(new Duration(300), getNewsContainer()));
        getFadeOut().setFromValue(1);
        getFadeOut().setToValue(0);
        getFadeOut().setOnFinished(e -> {
        	getNewsContainer().setVisible(false);
        });
	}
	
	public VBox getNewsContainer() {
		return newsContainer;
	}

	public void setNewsContainer(VBox newsContainer) {
		this.newsContainer = newsContainer;
	}

	public FadeTransition getFadeIn() {
		return fadeIn;
	}

	public void setFadeIn(FadeTransition fadeIn) {
		this.fadeIn = fadeIn;
	}

	public FadeTransition getFadeOut() {
		return fadeOut;
	}

	public void setFadeOut(FadeTransition fadeOut) {
		this.fadeOut = fadeOut;
	}

	@EventListener(priority = Integer.MIN_VALUE)
	@SuppressWarnings("unused")
	public void onShowNewsPanel(ShowNewsPanelRequest request) {
		if (!newsContainer.isVisible()) {
			newsContainer.setVisible(true);
			fadeIn.play();
		}
	}

	@EventListener(priority = Integer.MIN_VALUE)
	@SuppressWarnings("unused")
	public void onHideNewsPanel(HideNewsPanelRequest request) {
		if (newsContainer.isVisible()) {
			fadeOut.play();
		}
	}
}