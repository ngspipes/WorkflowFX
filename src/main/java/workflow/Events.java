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
package workflow;

import jfxwfutils.Event;
import workflow.elements.WorkflowConnection;
import workflow.elements.WorkflowItem;

public class Events {
	
	public final Event<WorkflowItem> addItem = new Event<>();
	public final Event<WorkflowItem> removeItem = new Event<>();
	public final Event<WorkflowConnection> connect = new Event<>();
	public final Event<WorkflowConnection> disconnect = new Event<>();
	
}
