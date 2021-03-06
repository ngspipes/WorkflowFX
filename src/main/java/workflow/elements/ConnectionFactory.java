/*-
 * Copyright (c) 2016, NGSPipes Team <ngspipes@gmail.com>
 * All rights reserved.
 *
 * This file is part of NGSPipes <http://ngspipes.github.io/>.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package workflow.elements;

import components.connect.Connectible;
import components.connect.connection.IConnection;
import components.connect.connector.Connector;
import components.shortcut.Keys;
import components.shortcut.Shortcut;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import jfxutils.Utils;
import jfxwfutils.Historic;
import workflow.Elements;
import workflow.WorkflowConfigurator;

import java.util.function.Function;

public class ConnectionFactory {
	
	private final Elements elements;
	private final WorkflowConfigurator config;
	private final Historic historic;
	
		
	protected ConnectionFactory(Elements elements, WorkflowConfigurator config, Historic historic){
		this.elements = elements;
		this.config = config;
		this.historic = historic;
	}

	
	public WorkflowConnection create(Connector connector, WorkflowItem initItem, WorkflowItem endItem, Object state){
		WorkflowConnection connection = new WorkflowConnection(config, historic, connector, state, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(WorkflowItem initItem, WorkflowItem endItem, Object state){
		WorkflowConnection connection = new WorkflowConnection(config, historic, state, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(Connector connector, WorkflowItem initItem, WorkflowItem endItem){
		WorkflowConnection connection = new WorkflowConnection(config, historic, connector, initItem, endItem);
		mount(connection);
		return connection;
	}
	
	public WorkflowConnection create(WorkflowItem initItem, WorkflowItem endItem){
		WorkflowConnection connection = new WorkflowConnection(config, historic, initItem, endItem);
		mount(connection);
		return connection;
	}

	
	
	private void mount(WorkflowConnection connection){
		mountConnectible(connection);
		mountShortcuts(connection);
	}
	
	private void mountConnectible(WorkflowConnection connection){
		Function<WorkflowItem, IConnection<?>> factory = this.config.getConnectionFactory();
		
		new Connectible(connection.getConnector(), factory.apply(connection.getInitItem()), factory.apply(connection.getEndItem())).connect();
	}
	
	private void mountShortcuts(WorkflowConnection connection){
		if(!config.getPermitDisconnectShortcut())
			return;
		
		Keys keys = new Keys();
		keys.add(KeyCode.DELETE, ()->elements.removeConnection(connection));
		new Shortcut<>(connection.getRoot(), keys).mount();
		setFocusListener(connection.getConnector());
	}

	private void setFocusListener(Connector connector) {
		EventHandler<? super MouseEvent> oldHandler = connector.getNode().getOnMouseClicked();
		EventHandler<? super MouseEvent> newHandler = (event)->{
			connector.getNode().requestFocus();
			event.consume();
		};
		newHandler = Utils.chain(oldHandler, newHandler);
		connector.getNode().setOnMouseClicked(newHandler);
	}
		
}
