package org.apache.aiaravata.application.catalog.data.resources;

import org.airavata.appcatalog.cpi.AppCatalogException;
import org.apache.aiaravata.application.catalog.data.model.ComputeResource;
import org.apache.aiaravata.application.catalog.data.model.GSISSHSubmission;
import org.apache.aiaravata.application.catalog.data.util.AppCatalogJPAUtils;
import org.apache.aiaravata.application.catalog.data.util.AppCatalogQueryGenerator;
import org.apache.aiaravata.application.catalog.data.util.AppCatalogResourceType;
import org.apache.airavata.common.exception.ApplicationSettingsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GSISSHSubmissionResource extends AbstractResource {

    private final static Logger logger = LoggerFactory.getLogger(GSISSHSubmissionResource.class);

    private String submissionID;
    private String resourceJobManager;
    private int sshPort;
    private String installedPath;
    private String monitorMode;

    public void remove(Object identifier) throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GSISSH_SUBMISSION);
            generator.setParameter(GSISSHSubmissionConstants.SUBMISSION_ID, identifier);
            Query q = generator.deleteQuery(em);
            q.executeUpdate();
            em.getTransaction().commit();
            em.close();
        } catch (ApplicationSettingsException e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public Resource get(Object identifier) throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GSISSH_SUBMISSION);
            generator.setParameter(GSISSHSubmissionConstants.SUBMISSION_ID, identifier);
            Query q = generator.selectQuery(em);
            GSISSHSubmission gsisshSubmission = (GSISSHSubmission) q.getSingleResult();
            GSISSHSubmissionResource gsisshSubmissionResource =
                    (GSISSHSubmissionResource) AppCatalogJPAUtils.getResource(AppCatalogResourceType.GSISSH_SUBMISSION
                            , gsisshSubmission);
            em.getTransaction().commit();
            em.close();
            return gsisshSubmissionResource;
        } catch (ApplicationSettingsException e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public List<Resource> get(String fieldName, Object value) throws AppCatalogException {
        List<Resource> gsiSSHSubmissionResourceList = new ArrayList<Resource>();
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            Query q;
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GSISSH_SUBMISSION);
            List results;
            if (fieldName.equals(GSISSHSubmissionConstants.MONITOR_MODE)) {
                generator.setParameter(GSISSHSubmissionConstants.MONITOR_MODE, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        GSISSHSubmissionResource gsisshSubmissionResource =
                                (GSISSHSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GSISSH_SUBMISSION, gsisshSubmission);
                        gsiSSHSubmissionResourceList.add(gsisshSubmissionResource);
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.INSTALLED_PATH)) {
                generator.setParameter(GSISSHSubmissionConstants.INSTALLED_PATH, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        GSISSHSubmissionResource gsisshSubmissionResource =
                                (GSISSHSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GSISSH_SUBMISSION, gsisshSubmission);
                        gsiSSHSubmissionResourceList.add(gsisshSubmissionResource);
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.SSH_PORT)) {
                generator.setParameter(GSISSHSubmissionConstants.SSH_PORT, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        GSISSHSubmissionResource gsisshSubmissionResource =
                                (GSISSHSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GSISSH_SUBMISSION, gsisshSubmission);
                        gsiSSHSubmissionResourceList.add(gsisshSubmissionResource);
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.RESOURCE_JOB_MANAGER)) {
                generator.setParameter(GSISSHSubmissionConstants.RESOURCE_JOB_MANAGER, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        GSISSHSubmissionResource gsisshSubmissionResource =
                                (GSISSHSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GSISSH_SUBMISSION, gsisshSubmission);
                        gsiSSHSubmissionResourceList.add(gsisshSubmissionResource);
                    }
                }
            } else {
                em.getTransaction().commit();
                em.close();
                logger.error("Unsupported field name for GSISSH submission resource.", new IllegalArgumentException());
                throw new IllegalArgumentException("Unsupported field name for GSISSH Submission resource.");
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return gsiSSHSubmissionResourceList;
    }

    @Override
    public List<Resource> getAll() throws AppCatalogException {
        return null;
    }

    @Override
    public List<String> getAllIds() throws AppCatalogException {
        return null;
    }

    public List<String> getIds(String fieldName, Object value) throws AppCatalogException {
        List<String> gsiSSHSubmissionResourceIDs = new ArrayList<String>();
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            Query q;
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GSISSH_SUBMISSION);
            List results;
            if (fieldName.equals(GSISSHSubmissionConstants.SUBMISSION_ID)) {
                generator.setParameter(GSISSHSubmissionConstants.SUBMISSION_ID, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        gsiSSHSubmissionResourceIDs.add(gsisshSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.SSH_PORT)) {
                generator.setParameter(GSISSHSubmissionConstants.SSH_PORT, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        gsiSSHSubmissionResourceIDs.add(gsisshSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.MONITOR_MODE)) {
                generator.setParameter(GSISSHSubmissionConstants.MONITOR_MODE, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        gsiSSHSubmissionResourceIDs.add(gsisshSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.RESOURCE_JOB_MANAGER)) {
                generator.setParameter(GSISSHSubmissionConstants.RESOURCE_JOB_MANAGER, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        gsiSSHSubmissionResourceIDs.add(gsisshSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GSISSHSubmissionConstants.INSTALLED_PATH)) {
                generator.setParameter(GSISSHSubmissionConstants.INSTALLED_PATH, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GSISSHSubmission gsisshSubmission = (GSISSHSubmission) result;
                        gsiSSHSubmissionResourceIDs.add(gsisshSubmission.getSubmissionID());
                    }
                }
            } else {
                em.getTransaction().commit();
                em.close();
                logger.error("Unsupported field name for GSISSH Submission resource.", new IllegalArgumentException());
                throw new IllegalArgumentException("Unsupported field name for GSISSH Submission resource.");
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
        return gsiSSHSubmissionResourceIDs;
    }

    public void save() throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            GSISSHSubmission existingGSISSHSubmission = em.find(GSISSHSubmission.class, submissionID);
            em.close();

            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            if (existingGSISSHSubmission != null) {
                existingGSISSHSubmission.setSubmissionID(submissionID);
                existingGSISSHSubmission.setSshPort(sshPort);
                existingGSISSHSubmission.setResourceJobManager(resourceJobManager);
                existingGSISSHSubmission.setInstalledPath(installedPath);
                existingGSISSHSubmission.setMonitorMode(monitorMode);
                em.merge(existingGSISSHSubmission);
            } else {
                GSISSHSubmission gsisshSubmission = new GSISSHSubmission();
                gsisshSubmission.setSubmissionID(submissionID);
                gsisshSubmission.setSshPort(sshPort);
                gsisshSubmission.setResourceJobManager(resourceJobManager);
                gsisshSubmission.setInstalledPath(installedPath);
                gsisshSubmission.setMonitorMode(monitorMode);
                em.persist(gsisshSubmission);
            }
            em.getTransaction().commit();
            em.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }

    }

    public boolean isExists(Object identifier) throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            GSISSHSubmission gsisshSubmission = em.find(GSISSHSubmission.class, identifier);
            em.close();
            return gsisshSubmission != null;
        } catch (ApplicationSettingsException e) {
            logger.error(e.getMessage(), e);
            throw new AppCatalogException(e);
        } finally {
            if (em != null && em.isOpen()) {
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                em.close();
            }
        }
    }

    public String getSubmissionID() {
        return submissionID;
    }

    public void setSubmissionID(String submissionID) {
        this.submissionID = submissionID;
    }

    public String getResourceJobManager() {
        return resourceJobManager;
    }

    public void setResourceJobManager(String resourceJobManager) {
        this.resourceJobManager = resourceJobManager;
    }

    public int getSshPort() {
        return sshPort;
    }

    public void setSshPort(int sshPort) {
        this.sshPort = sshPort;
    }

    public String getInstalledPath() {
        return installedPath;
    }

    public void setInstalledPath(String installedPath) {
        this.installedPath = installedPath;
    }

    public String getMonitorMode() {
        return monitorMode;
    }

    public void setMonitorMode(String monitorMode) {
        this.monitorMode = monitorMode;
    }

}
