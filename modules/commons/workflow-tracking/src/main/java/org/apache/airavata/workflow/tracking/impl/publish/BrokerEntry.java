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

package org.apache.airavata.workflow.tracking.impl.publish;

import org.apache.xmlbeans.XmlObject;

final class BrokerEntry {

    public BrokerEntry(String message) {
        this.message = message;
        this.xml = null;
    }

    public BrokerEntry(XmlObject xml) {
        this.xml = xml;
        this.message = null;
    }

    public final String getMessage() {
        if (message == null)
            return xml.xmlText();
        else
            return message;
    }

    public final XmlObject getXmlObject() {
        return xml;
    }

    private final String message;
    private final XmlObject xml;
}
