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

package org.apache.airavata.gfac;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.common.utils.AiravataUtils;
import org.apache.airavata.common.utils.StringUtil;
import org.apache.airavata.common.utils.StringUtil.CommandLineParameters;
import org.apache.airavata.commons.gfac.type.ApplicationDescription;
import org.apache.airavata.commons.gfac.type.HostDescription;
import org.apache.airavata.gfac.core.context.JobExecutionContext;
import org.apache.airavata.gfac.core.provider.GFacProvider;
import org.apache.airavata.gfac.core.provider.GFacProviderConfig;
import org.apache.airavata.gfac.core.provider.GFacProviderException;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class MetaScheduler {
	private static Logger log = LoggerFactory.getLogger(MetaScheduler.class);
	
	
	public static void main(String args[]) {
        try {
        	System.out.println("API version is " + client.getAPIVersion());
        } catch (Exception e) {
            logger.error("Error while connecting with server", e.getMessage());
            e.printStackTrace();
        }
	}
	
	
	public String getHostName() {
		return hostName;
	}
	
	public String getQueueName() {
		return queueName;
	}
	
	public static String getUserNameFromContext(JobExecutionContext jobContext) {
		if(jobContext.getTaskData() == null)
			return null;
		//FIXME: Discuss to get user and change this
		return "admin";
	}
	
    public static HostDescription pickaHost(AiravataAPI api, String serviceName) throws AiravataAPIInvocationException {
        List<HostDescription> registeredHosts = new ArrayList<HostDescription>();
        Map<String, ApplicationDescription> applicationDescriptors = api.getApplicationManager().getApplicationDescriptors(serviceName);
        for (String hostDescName : applicationDescriptors.keySet()) {
            registeredHosts.add(api.getApplicationManager().getHostDescription(hostDescName));
        }
        return scheduleHost(registeredHosts);
    }
	
    // what exception should this throw?
    
    public boolean submitThrottle(String experimentID, String taskID) throws OrchestratorException {
        return this.submitThrottle(experimentID, taskID, null);
    }
    
    // get array of jobs from orchestrator (should be able to find the layout)
    // jobs have a state characteristic
    
    // implement listeners?
    
    
    
    // need to maintain new database of queued job data
    
    // need a launchJob() like in Orchestrator
    
    // need a launchThrottleJob() for Orchestrator to call
    
    // for testing... assume calls and required data
    
    // right now... look for launchJob, queue data, db access
    
    
}


	
	

	
	


	

	

	
	

	
}
