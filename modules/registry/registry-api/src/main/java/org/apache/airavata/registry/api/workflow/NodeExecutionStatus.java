/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package org.apache.airavata.registry.api.workflow;

import java.util.Calendar;
import java.util.Date;

import org.apache.airavata.registry.api.workflow.WorkflowExecutionStatus.State;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class NodeExecutionStatus {
    private State executionStatus;
    private Date statusUpdateTime = null;
    private WorkflowInstanceNode workflowInstanceNode;

    public NodeExecutionStatus() {
    }

    public State getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(State executionStatus) {
        this.executionStatus = executionStatus;
    }

    public Date getStatusUpdateTime() {
        return statusUpdateTime;
    }

    public void setStatusUpdateTime(Date statusUpdateTime) {
        this.statusUpdateTime = statusUpdateTime;
    }

    public NodeExecutionStatus(WorkflowInstanceNode workflowInstanceNode, State executionStatus) {
        this(workflowInstanceNode, executionStatus, null);
    }

    public NodeExecutionStatus(WorkflowInstanceNode workflowInstanceNode, State executionStatus, Date statusUpdateTime) {
        statusUpdateTime = statusUpdateTime == null ? Calendar.getInstance().getTime() : statusUpdateTime;
        setWorkflowInstanceNode(workflowInstanceNode);
        setExecutionStatus(executionStatus);
        setStatusUpdateTime(statusUpdateTime);
    }

	public WorkflowInstanceNode getWorkflowInstanceNode() {
		return workflowInstanceNode;
	}

	public void setWorkflowInstanceNode(WorkflowInstanceNode workflowInstanceNode) {
		this.workflowInstanceNode = workflowInstanceNode;
	}

}
