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

import org.apache.airavata.api.Airavata;
import org.apache.airavata.gfac.core.context.JobExecutionContext;
 import org.apache.airavata.gfac.core.cpi.GFacImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.airavata.common.utils.AiravataUtils;
import org.apache.airavata.api.client.AiravataClientFactory;
import org.apache.thrift.TException;

public class MetaScheduler {
    private static Logger log = LoggerFactory.getLogger(MetaScheduler.class);
    private static Airavata.Client client;
    public static final String THRIFT_SERVER_HOST = "localhost";
     public static final int THRIFT_SERVER_PORT = 8080;

    public static boolean submitThrottleJob(String experimentId, String taskId, String gatewayId) {
        
        // save to table
        System.out.println("Received Call to Throttle: " + experimentId + " : " + taskId + " : " + gatewayId);
         
        return true;
        
    }
            
    public boolean submitJob(String experimentId, String taskId, String gatewayId) throws TException {
        logger.info("GFac Recieved the Experiment: " + experimentId + " TaskId: " + taskId);
         GFac gfac = getGfac();
        try {
            return gfac.submitJob(experimentId, taskId, gatewayId);
        } catch (GFacException e) {
            throw new TException("Error launching the experiment : " + e.getMessage(), e);
         }
    }
    
    
    public static String getUserNameFromContext(JobExecutionContext jobContext) {
        if(jobContext.getTaskData() == null)
            return null;
        //FIXME: Discuss to get user and change this
         return "admin";
    }
    
//    public static HostDescription pickaHost(AiravataAPI api, String serviceName) throws AiravataAPIInvocationException {
//        List<HostDescription> registeredHosts = new ArrayList<HostDescription>();
 //        Map<String, ApplicationDescription> applicationDescriptors = api.getApplicationManager().getApplicationDescriptors(serviceName);
//        for (String hostDescName : applicationDescriptors.keySet()) {
 //            registeredHosts.add(api.getApplicationManager().getHostDescription(hostDescName));
//        }
//        return scheduleHost(registeredHosts);
//    }
    
    // what exception should this throw?
     
//    public boolean submitThrottle(String experimentID, String taskID)   {
//        return this.submitThrottle(experimentID, taskID, null);
//    }
    
    // get array of jobs from orchestrator (should be able to find the layout)
     // jobs have a state characteristic
    
    // implement listeners?
    
    
    
    // need to maintain new database of queued job data
    
    // need a launchJob() like in Orchestrator
     
    // need a launchThrottleJob() for Orchestrator to call
    
    // for testing... assume calls and required data
    
    // right now... look for launchJob, queue data, db access
    
    
 }