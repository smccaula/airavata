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
package org.apache.airavata.gfac.monitor.handlers;

import org.apache.airavata.common.exception.ApplicationSettingsException;
import org.apache.airavata.common.utils.ServerSettings;
import org.apache.airavata.gfac.core.context.JobExecutionContext;
import org.apache.airavata.gfac.core.cpi.BetterGfacImpl;
import org.apache.airavata.gfac.core.cpi.GFacImpl;
import org.apache.airavata.gfac.core.handler.GFacHandlerException;
import org.apache.airavata.gfac.core.handler.ThreadedHandler;
import org.apache.airavata.gfac.core.monitor.MonitorID;
import org.apache.airavata.gfac.monitor.HPCMonitorID;
import org.apache.airavata.gfac.monitor.exception.AiravataMonitorException;
import org.apache.airavata.gfac.monitor.impl.pull.qstat.HPCPullMonitor;
import org.apache.airavata.gfac.monitor.util.CommonUtils;
import org.apache.airavata.gsi.ssh.api.authentication.AuthenticationInfo;
import org.apache.airavata.gsi.ssh.impl.authentication.MyProxyAuthenticationInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * this handler is responsible for monitoring jobs in a pull mode
 * and currently this support multiple pull monitoring in grid resource and uses
 * commands like qstat,squeue and this supports sun grid enging monitoring too
 * which is a slight variation of qstat monitoring.
 */
public class GridPullMonitorHandler extends ThreadedHandler {
    private final static Logger logger = LoggerFactory.getLogger(GridPullMonitorHandler.class);

    private HPCPullMonitor hpcPullMonitor;

    private AuthenticationInfo authenticationInfo;

    public void initProperties(Properties properties) throws GFacHandlerException {
        String myProxyUser = null;
        try {
            myProxyUser = ServerSettings.getSetting("myproxy.username");
            String myProxyPass = ServerSettings.getSetting("myproxy.password");
            String certPath = ServerSettings.getSetting("trusted.cert.location");
            String myProxyServer = ServerSettings.getSetting("myproxy.server");
            setAuthenticationInfo(new MyProxyAuthenticationInfo(myProxyUser, myProxyPass, myProxyServer,
                    7512, 17280000, certPath));
            if(GFacImpl.getMonitorPublisher() != null){
                hpcPullMonitor = new HPCPullMonitor(GFacImpl.getMonitorPublisher(),getAuthenticationInfo());    // we use our own credentials for monitoring, not from the store
            }else {
                hpcPullMonitor = new HPCPullMonitor(BetterGfacImpl.getMonitorPublisher(),getAuthenticationInfo());  // we use our own credentials for monitoring, not from the store
            }
        } catch (ApplicationSettingsException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void run() {
        hpcPullMonitor.run();
    }

    public void invoke(JobExecutionContext jobExecutionContext) throws GFacHandlerException {
        super.invoke(jobExecutionContext);
        hpcPullMonitor.setGfac(jobExecutionContext.getGfac());
        MonitorID monitorID = new HPCMonitorID(getAuthenticationInfo(), jobExecutionContext);
        try {
            CommonUtils.addMonitortoQueue(hpcPullMonitor.getQueue(), monitorID);
        } catch (AiravataMonitorException e) {
            logger.error("Error adding monitorID object to the queue with experiment ", monitorID.getExperimentID());
        }
    }

    public AuthenticationInfo getAuthenticationInfo() {
        return authenticationInfo;
    }

    public HPCPullMonitor getHpcPullMonitor() {
        return hpcPullMonitor;
    }

    public void setAuthenticationInfo(AuthenticationInfo authenticationInfo) {
        this.authenticationInfo = authenticationInfo;
    }

    public void setHpcPullMonitor(HPCPullMonitor hpcPullMonitor) {
        this.hpcPullMonitor = hpcPullMonitor;
    }
}
