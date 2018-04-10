/* 
 * This file is part of the PDF Split And Merge source code
 * Created on 31/ott/2013
 * Copyright 2017 by Sober Lemur S.a.s. di Vacondio Andrea (info@pdfsam.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as 
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.pdfsam.ui;

import static org.sejda.eventstudio.StaticStudio.eventStudio;

import javax.inject.Inject;
import javax.inject.Named;

import org.pdfsam.news.HideNewsPanelRequest;
import org.pdfsam.news.ShowNewsPanelRequest;
import org.pdfsam.ui.commons.SetActiveModuleRequest;
import org.pdfsam.ui.dashboard.Dashboard;
import org.pdfsam.ui.event.SetActiveDashboardItemRequest;
import org.pdfsam.ui.news.NewsPanel;
import org.pdfsam.ui.workarea.WorkArea;
import org.sejda.eventstudio.annotation.EventListener;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

/**
 * Panel containing the main area where the modules pane and the dashboard pane are displayed
 * 
 * @author Andrea Vacondio
 * 
 */
public class ContentPane extends HBox {

    private ContentPaneContainer contentPaneContainer;
	private WorkArea modules;
    private Dashboard dashboard;
    @Inject
    public ContentPane(WorkArea modules, Dashboard dashboard, NewsPanel news,
            @Named("defaultDashboardItemId") String defaultDasboardItem) {
        this.modules = modules;
        this.dashboard = dashboard;
        StackPane stack = new StackPane(modules, dashboard);
        setHgrow(stack, Priority.ALWAYS);
        contentPaneContainer = new ContentPaneContainer(news);
        getChildren().addAll(stack, contentPaneContainer.getNewsContainer());
        eventStudio().addAnnotatedListeners(this);
        eventStudio().broadcast(new SetActiveDashboardItemRequest(defaultDasboardItem));
    }

    @EventListener(priority = Integer.MIN_VALUE)
    @SuppressWarnings("unused")
    public void onSetActiveModule(SetActiveModuleRequest request) {
        dashboard.setVisible(false);
        modules.setVisible(true);
    }

    @EventListener(priority = Integer.MIN_VALUE)
    @SuppressWarnings("unused")
    public void onSetActiveDashboardItem(SetActiveDashboardItemRequest request) {
        dashboard.setVisible(true);
        modules.setVisible(false);
    }

    @EventListener(priority = Integer.MIN_VALUE)
    @SuppressWarnings("unused")
    public void onShowNewsPanel(ShowNewsPanelRequest request) {
        contentPaneContainer.onShowNewsPanel(request);
    }

    @EventListener(priority = Integer.MIN_VALUE)
    @SuppressWarnings("unused")
    public void onHideNewsPanel(HideNewsPanelRequest request) {
        contentPaneContainer.onHideNewsPanel(request);
    }

}
