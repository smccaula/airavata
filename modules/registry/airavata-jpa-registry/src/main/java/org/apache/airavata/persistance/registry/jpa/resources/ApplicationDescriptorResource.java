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
package org.apache.airavata.persistance.registry.jpa.resources;

import org.apache.airavata.persistance.registry.jpa.Resource;
import org.apache.airavata.persistance.registry.jpa.ResourceType;
import org.apache.airavata.persistance.registry.jpa.ResourceUtils;
import org.apache.airavata.persistance.registry.jpa.model.*;
import org.apache.airavata.registry.cpi.RegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDescriptorResource extends AbstractResource {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationDescriptorResource.class);
    private String name;
    private String gatewayName;
    private String updatedUser;
    private String content;
    private String hostDescName;
    private String serviceDescName;

    /**
     *
     * @param name application descriptor name
     * @param gatewayName  gateway name
     * returns ApplicationDescriptorResource
     */
    public ApplicationDescriptorResource(String name, String gatewayName) {
        this.setName(name);
        this.gatewayName = gatewayName;
    }

    /**
     *
     */
    public ApplicationDescriptorResource() {
    }

    /**
     *
     * @return gateway name
     */
    public String getGatewayName() {
        return gatewayName;
    }

    /**
     *
     * @param gatewayName gateway name
     */
    public void setGatewayName(String gatewayName) {
        this.gatewayName = gatewayName;
    }

    /**
     *
     * @param updatedUser updated user
     */
    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    /**
     *
     * @return name of the application descriptor
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     *
     * @return  host descriptor name
     */
    public String getHostDescName() {
        return hostDescName;
    }

    /**
     *
     * @return service descriptor name
     */
    public String getServiceDescName() {
        return serviceDescName;
    }

    /**
     *
     * @param content content of the application descriptor
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @param hostDescName host descriptor name
     */
    public void setHostDescName(String hostDescName) {
        this.hostDescName = hostDescName;
    }

    /**
     *
     * @param serviceDescName  service descriptor name
     */
    public void setServiceDescName(String serviceDescName) {
        this.serviceDescName = serviceDescName;
    }

    /**
     * Since application descriptors are at the leaf level, this method is not
     * valid for application descriptors
     * @param type  child resource types
     * @return UnsupportedOperationException
     */
    public Resource create(ResourceType type) throws RegistryException {
        logger.error("Unsupported operation for application descriptor resource " +
                "since application descriptors could not create child resources.. ", new UnsupportedOperationException());
        throw new UnsupportedOperationException();
    }

    /**
     * Since application descriptors are at the leaf level, this method is not
     * valid for application descriptors
     * @param type child resource types
     * @param name name of the resource
     */
    public void remove(ResourceType type, Object name) throws RegistryException{
        logger.error("Unsupported operation for application descriptor resource " +
                "since application descriptors could not remove child resources.. ", new UnsupportedOperationException());
        throw new UnsupportedOperationException();
    }

    /**
     * key should be gateway_name, application_descriptor_ID, host_descriptor_ID, service_descriptor_ID
     *
     * @param keys primary keys of the Application_descriptor table
     */
//    public void removeMe(Object[] keys) {
//        EntityManager em = ResourceUtils.getEntityManager();
//        em.getTransaction().begin();
//        QueryGenerator queryGenerator = new QueryGenerator(Application_Descriptor);
//        queryGenerator.setParameter(ApplicationDescriptorConstants.GATEWAY_NAME, keys[0]);
//        queryGenerator.setParameter(ApplicationDescriptorConstants.APPLICATION_DESC_ID, keys[1]);
//        queryGenerator.setParameter(ApplicationDescriptorConstants.HOST_DESC_ID, keys[2]);
//        queryGenerator.setParameter(ApplicationDescriptorConstants.SERVICE_DESC_ID, keys[3]);
//        Query q = queryGenerator.deleteQuery(em);
//        q.executeUpdate();
//        em.getTransaction().commit();
//        em.close();
//    }

    /**
     *
     * Since application descriptors are at the leaf level, this method is not
     * valid for application descriptors
     * @param type child resource types
     * @param name name of the resource
     * @return UnsupportedOperationException
     */
    public Resource get(ResourceType type, Object name) throws RegistryException{
        logger.error("Unsupported operation for application descriptor resource " +
                "since there are no child resources generated by application descriptors.. ", new UnsupportedOperationException());
        throw new UnsupportedOperationException();
    }


    /**
     * Since application descriptors are at the leaf level, this method is not
     * valid for application descriptors
     * @param type child resource types
     * @return UnsupportedOperationException
     */
    public List<Resource> get(ResourceType type) throws RegistryException {
        logger.error("Unsupported operation for application descriptor resource " +
                "since there are no child resources generated by application descriptors.. ", new UnsupportedOperationException());
        throw new UnsupportedOperationException();
    }

    /**
     *  save application descriptor to database
     */
    public void save() throws RegistryException{
        EntityManager em = null;
        try{
            em = ResourceUtils.getEntityManager();
            Application_Descriptor existingAppDesc = em.find(Application_Descriptor.class, new Application_Descriptor_PK(gatewayName, name));
            em.close();

            em = ResourceUtils.getEntityManager();
            em.getTransaction().begin();
            Application_Descriptor applicationDescriptor = new Application_Descriptor();
            applicationDescriptor.setApplication_descriptor_ID(getName());

            Gateway gateway = em.find(Gateway.class, gatewayName);
            Users user = em.find(Users.class, updatedUser);
            applicationDescriptor.setGateway(gateway);
            applicationDescriptor.setGateway_name(gateway.getGateway_name());
            applicationDescriptor.setUser(user);
            byte[] contentBytes = content.getBytes();
            applicationDescriptor.setApplication_descriptor_xml(contentBytes);
            applicationDescriptor.setService_descriptor_ID(serviceDescName);
            applicationDescriptor.setHost_descriptor_ID(hostDescName);
            if (existingAppDesc != null) {
                existingAppDesc.setUser(user);
                existingAppDesc.setGateway(gateway);
                existingAppDesc.setGateway_name(gateway.getGateway_name());
                existingAppDesc.setApplication_descriptor_xml(contentBytes);
                existingAppDesc.setHost_descriptor_ID(hostDescName);
                existingAppDesc.setService_descriptor_ID(serviceDescName);
                applicationDescriptor = em.merge(existingAppDesc);
            } else {
                em.persist(applicationDescriptor);
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e){
            logger.error(e.getMessage(), e);
            throw new RegistryException(e);
        }finally {
            if (em != null && em.isOpen()){
                if (em.getTransaction().isActive()){
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

    }

    /**
     * Since application descriptors are at the leaf level, this method is not
     * valid for application descriptors
     * @param type child resource types
     * @param name name of the resource
     * @return UnsupportedOperationException
     */
    public boolean isExists(ResourceType type, Object name) throws RegistryException {
        logger.error("Unsupported operation for application descriptor resource " +
                "since there are no child resources generated by application descriptors.. ", new UnsupportedOperationException());
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param name application descriptor name
     */
    public void setName(String name) {
        this.name = name;
    }
}
