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

package org.apache.airavata.persistance.registry.jpa.model;

import java.io.Serializable;

public class Application_Descriptor_PK implements Serializable {
    private String gateway_name;
    private String application_descriptor_ID;

    public Application_Descriptor_PK(String gateway_name, String application_descriptor_ID) {
        this.gateway_name = gateway_name;
        this.application_descriptor_ID = application_descriptor_ID;
    }

    public Application_Descriptor_PK() {
        ;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 1;
    }

    public String getGateway_name() {
        return gateway_name;
    }

    public String getApplication_descriptor_ID() {
        return application_descriptor_ID;
    }

    public void setGateway_name(String gateway_name) {
        this.gateway_name = gateway_name;
    }

    public void setApplication_descriptor_ID(String application_descriptor_ID) {
        this.application_descriptor_ID = application_descriptor_ID;
    }

}
