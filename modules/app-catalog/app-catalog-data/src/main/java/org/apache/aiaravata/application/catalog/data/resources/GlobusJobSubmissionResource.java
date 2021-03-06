package org.apache.aiaravata.application.catalog.data.resources;

import org.airavata.appcatalog.cpi.AppCatalogException;
import org.apache.aiaravata.application.catalog.data.model.ComputeResource;
import org.apache.aiaravata.application.catalog.data.model.GlobusJobSubmission;
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

public class GlobusJobSubmissionResource extends AbstractResource {

    private final static Logger logger = LoggerFactory.getLogger(GlobusJobSubmissionResource.class);

    private String submissionID;
    private String resourceJobManager;
    private String securityProtocol;

    public void remove(Object identifier) throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GLOBUS_SUBMISSION);
            generator.setParameter(GlobusJobSubmissionConstants.SUBMISSION_ID, identifier);
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
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GLOBUS_SUBMISSION);
            generator.setParameter(GlobusJobSubmissionConstants.SUBMISSION_ID, identifier);
            Query q = generator.selectQuery(em);
            GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) q.getSingleResult();
            GlobusJobSubmissionResource globusJobSubmissionResource =
                    (GlobusJobSubmissionResource) AppCatalogJPAUtils.getResource(
                            AppCatalogResourceType.GLOBUS_SUBMISSION, globusJobSubmission);
            em.getTransaction().commit();
            em.close();
            return globusJobSubmissionResource;
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
        List<Resource> globusSubmissionResourceList = new ArrayList<Resource>();
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            Query q;
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GLOBUS_SUBMISSION);
            List results;
            if (fieldName.equals(GlobusJobSubmissionConstants.RESOURCE_JOB_MANAGER)) {
                generator.setParameter(GlobusJobSubmissionConstants.RESOURCE_JOB_MANAGER, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        GlobusJobSubmissionResource globusJobSubmissionResource =
                                (GlobusJobSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GLOBUS_SUBMISSION, globusJobSubmission);
                        globusSubmissionResourceList.add(globusJobSubmissionResource);
                    }
                }
            } else if (fieldName.equals(GlobusJobSubmissionConstants.SECURITY_PROTOCAL)) {
                generator.setParameter(GlobusJobSubmissionConstants.SECURITY_PROTOCAL, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        GlobusJobSubmissionResource globusJobSubmissionResource =
                                (GlobusJobSubmissionResource) AppCatalogJPAUtils.getResource(
                                        AppCatalogResourceType.GLOBUS_SUBMISSION, globusJobSubmission);
                        globusSubmissionResourceList.add(globusJobSubmissionResource);
                    }
                }
            } else {
                em.getTransaction().commit();
                em.close();
                logger.error("Unsupported field name for Globus submission resource.", new IllegalArgumentException());
                throw new IllegalArgumentException("Unsupported field name for Globus Submission resource.");
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
        return globusSubmissionResourceList;
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
        List<String> globusSubmissionResourceIDs = new ArrayList<String>();
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            Query q;
            AppCatalogQueryGenerator generator = new AppCatalogQueryGenerator(GLOBUS_SUBMISSION);
            List results;
            if (fieldName.equals(GlobusJobSubmissionConstants.SUBMISSION_ID)) {
                generator.setParameter(GlobusJobSubmissionConstants.SUBMISSION_ID, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        globusSubmissionResourceIDs.add(globusJobSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GlobusJobSubmissionConstants.GLOBUS_GATEKEEPER_EP)) {
                generator.setParameter(GlobusJobSubmissionConstants.GLOBUS_GATEKEEPER_EP, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        globusSubmissionResourceIDs.add(globusJobSubmission.getSubmissionID());
                    }
                }
            }
            else if (fieldName.equals(GlobusJobSubmissionConstants.SECURITY_PROTOCAL)) {
                generator.setParameter(GlobusJobSubmissionConstants.SECURITY_PROTOCAL, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        globusSubmissionResourceIDs.add(globusJobSubmission.getSubmissionID());
                    }
                }
            } else if (fieldName.equals(GlobusJobSubmissionConstants.RESOURCE_JOB_MANAGER)) {
                generator.setParameter(GlobusJobSubmissionConstants.RESOURCE_JOB_MANAGER, value);
                q = generator.selectQuery(em);
                results = q.getResultList();
                if (results.size() != 0) {
                    for (Object result : results) {
                        GlobusJobSubmission globusJobSubmission = (GlobusJobSubmission) result;
                        globusSubmissionResourceIDs.add(globusJobSubmission.getSubmissionID());
                    }
                }
            } else {
                em.getTransaction().commit();
                em.close();
                logger.error("Unsupported field name for Globus Submission resource.", new IllegalArgumentException());
                throw new IllegalArgumentException("Unsupported field name for Globus Submission resource.");
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
        return globusSubmissionResourceIDs;
    }

    public void save() throws AppCatalogException {
        EntityManager em = null;
        try {
            em = AppCatalogJPAUtils.getEntityManager();
            GlobusJobSubmission existingGlobusSubmission = em.find(GlobusJobSubmission.class, submissionID);
            em.close();

            em = AppCatalogJPAUtils.getEntityManager();
            em.getTransaction().begin();
            if (existingGlobusSubmission != null) {
                existingGlobusSubmission.setSubmissionID(submissionID);
                existingGlobusSubmission.setResourceJobManager(resourceJobManager);
                existingGlobusSubmission.setSecurityProtocol(securityProtocol);
                em.merge(existingGlobusSubmission);
            } else {
                GlobusJobSubmission globusJobSubmission = new GlobusJobSubmission();
                globusJobSubmission.setSubmissionID(submissionID);
                globusJobSubmission.setSecurityProtocol(securityProtocol);
                globusJobSubmission.setResourceJobManager(resourceJobManager);
                em.persist(globusJobSubmission);
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
            GlobusJobSubmission globusJobSubmission = em.find(GlobusJobSubmission.class, identifier);
            em.close();
            return globusJobSubmission != null;
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

    public String getSecurityProtocol() {
        return securityProtocol;
    }

    public void setSecurityProtocol(String securityProtocol) {
        this.securityProtocol = securityProtocol;
    }

}
