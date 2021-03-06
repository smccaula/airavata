package org.apache.airavata.client.samples;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.airavata.api.Airavata;
import org.apache.airavata.api.client.AiravataClientFactory;
import org.apache.airavata.model.error.AiravataClientException;
import org.apache.airavata.model.error.AiravataSystemException;
import org.apache.airavata.model.error.ExperimentNotFoundException;
import org.apache.airavata.model.error.InvalidRequestException;
import org.apache.airavata.client.AiravataAPIFactory;
import org.apache.airavata.client.api.AiravataAPI;
import org.apache.airavata.client.api.exception.AiravataAPIInvocationException;
import org.apache.airavata.client.tools.UltrascanDocumentCreator;
import org.apache.airavata.common.exception.ApplicationSettingsException;
import org.apache.airavata.common.utils.AiravataUtils;
import org.apache.airavata.common.utils.ClientSettings;
import org.apache.airavata.model.util.ExperimentModelUtil;
import org.apache.airavata.model.util.ProjectModelUtil;
import org.apache.airavata.model.workspace.Project;
import org.apache.airavata.model.workspace.experiment.*;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateLaunchExperimentUS3 {
	
	 //FIXME: Read from a config file
    public static final String THRIFT_SERVER_HOST = "gw111.iu.xsede.org";
    public static final int THRIFT_SERVER_PORT = 8930;
    private final static Logger logger = LoggerFactory.getLogger(CreateLaunchExperiment.class);
    private static final String DEFAULT_USER = "default.registry.user";
    private static final String DEFAULT_GATEWAY = "default.registry.gateway";
    public static void main(String[] args) {
        try {
            AiravataUtils.setExecutionAsClient();
            final Airavata.Client airavata = AiravataClientFactory.createAiravataClient(THRIFT_SERVER_HOST, THRIFT_SERVER_PORT);
            System.out.println("API version is " + airavata.getAPIVersion());
//            addDescriptors();
//            final String expId = createExperimentForTrestles(airavata);
            final String expId = createUS3ExperimentForTrestles(airavata);
//            final String expId = createExperimentForStampede(airavata);
//            final String expId = createUS3ExperimentForStampede(airavata);
            System.out.println("Experiment ID : " + expId);
            launchExperiment(airavata, expId);
            System.out.println("Launched successfully");
//            try {
//                Thread.sleep(20000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
            Thread monitor = (new Thread(){
                 public void run() {
                     Map<String, JobStatus> jobStatuses = null;
                     while (true) {
                         try {
                             jobStatuses = airavata.getJobStatuses(expId);
                             Set<String> strings = jobStatuses.keySet();
                             for (String key : strings) {
                                 JobStatus jobStatus = jobStatuses.get(key);
                                 if(jobStatus == null){
                                     return;
                                 }else {
                                     if (JobState.COMPLETE.equals(jobStatus.getJobState())) {
                                         System.out.println("Job completed Job ID: " + jobStatus.getJobState().toString());
                                         return;
                                     }else{
                                        System.out.println("Job ID:" + key + jobStatuses.get(key).getJobState().toString());
                                     }
                                 }
                             }
                             Thread.sleep(20000);
                         } catch (Exception e) {
                             e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                         }
                     }
                 }
            });
            monitor.start();
            try {
                monitor.join();
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

//            Experiment experiment = airavata.getExperiment(expId);
//            System.out.println("retrieved exp id : " + experiment.getExperimentID());
        } catch (Exception e) {
            logger.error("Error while connecting with server", e.getMessage());
            e.printStackTrace();
        }
    }
    public static void addDescriptors() throws AiravataAPIInvocationException,ApplicationSettingsException  {
        try {
            UltrascanDocumentCreator documentCreator = new UltrascanDocumentCreator(getAiravataAPI());
            documentCreator.createMPIPBSDocsTrestles();
            documentCreator.createEchoPBSDocsforTestles();
            documentCreator.createEchoSlurmDocsofStampede();
            documentCreator.createMPISLURMDocsStampede();
        } catch (AiravataAPIInvocationException e) {
            logger.error("Unable to create airavata API", e.getMessage());
            throw new AiravataAPIInvocationException(e);
        } catch (ApplicationSettingsException e) {
            logger.error("Unable to create airavata API", e.getMessage());
            throw new ApplicationSettingsException(e.getMessage());
        }
    }

    private static AiravataAPI getAiravataAPI() throws AiravataAPIInvocationException, ApplicationSettingsException {
        AiravataAPI airavataAPI;
        try {
            String sysUser = ClientSettings.getSetting(DEFAULT_USER);
            String gateway = ClientSettings.getSetting(DEFAULT_GATEWAY);
            airavataAPI = AiravataAPIFactory.getAPI(gateway, sysUser);
        } catch (AiravataAPIInvocationException e) {
            logger.error("Unable to create airavata API", e.getMessage());
            throw new AiravataAPIInvocationException(e);
        } catch (ApplicationSettingsException e) {
            logger.error("Unable to create airavata API", e.getMessage());
            throw new ApplicationSettingsException(e.getMessage());
        }
        return airavataAPI;
    }

    public static String createExperimentForTrestles(Airavata.Client client) throws TException  {
        try{
            List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
            DataObjectType input = new DataObjectType();
            input.setKey("echo_input");
            input.setType(DataType.STRING);
            input.setValue("echo_output=Hello World");
            exInputs.add(input);

            List<DataObjectType> exOut = new ArrayList<DataObjectType>();
            DataObjectType output = new DataObjectType();
            output.setKey("echo_output");
            output.setType(DataType.STRING);
            output.setValue("");
            exOut.add(output);

            Project project = ProjectModelUtil.createProject("project1", "admin", "test project");
            String projectId = client.createProject(project);

            Experiment simpleExperiment =
                    ExperimentModelUtil.createSimpleExperiment(projectId, "admin", "US3EchoExperimentTrestles", "US3EchoTrestles", "US3EchoTrestles", exInputs);
            simpleExperiment.setExperimentOutputs(exOut);

            ComputationalResourceScheduling scheduling = ExperimentModelUtil.createComputationResourceScheduling("trestles.sdsc.edu", 1, 1, 1, "shared", 0, 0, 1, "uot111");
            scheduling.setResourceHostId("gsissh-trestles");
            UserConfigurationData userConfigurationData = new UserConfigurationData();
            userConfigurationData.setAiravataAutoSchedule(false);
            userConfigurationData.setOverrideManualScheduledParams(false);
            userConfigurationData.setComputationalResourceScheduling(scheduling);
            simpleExperiment.setUserConfigurationData(userConfigurationData);
            return client.createExperiment(simpleExperiment);
        } catch (AiravataSystemException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataSystemException(e);
        } catch (InvalidRequestException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new InvalidRequestException(e);
        } catch (AiravataClientException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataClientException(e);
        }catch (TException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new TException(e);
        }
    }
    
    public static String createUS3ExperimentForTrestles (Airavata.Client client) throws AiravataSystemException, InvalidRequestException, AiravataClientException, TException  {
        try{
            List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
            DataObjectType input = new DataObjectType();
            input.setKey("input");
            input.setType(DataType.URI);
            input.setValue("file:///home/airavata/input/hpcinput.tar");
            exInputs.add(input);

            List<DataObjectType> exOut = new ArrayList<DataObjectType>();
            DataObjectType output = new DataObjectType();
            output.setKey("output");
            output.setType(DataType.URI);
            output.setValue("");
            DataObjectType output1 = new DataObjectType();
            output1.setKey("stdout");
            output1.setType(DataType.STDOUT);
            output1.setValue("");
            DataObjectType output2 = new DataObjectType();
            output2.setKey("stderr");
            output2.setType(DataType.STDERR);
            output2.setValue("");
            exOut.add(output);
            exOut.add(output1);
            exOut.add(output2);

            Project project = ProjectModelUtil.createProject("project1", "admin", "test project");
            String projectId = client.createProject(project);

            Experiment simpleExperiment = ExperimentModelUtil.createSimpleExperiment(projectId, "admin", "US3ExperimentTrestles", "US3AppTrestles", "US3AppTrestles", exInputs);
            simpleExperiment.setExperimentOutputs(exOut);

            ComputationalResourceScheduling scheduling = ExperimentModelUtil.createComputationResourceScheduling("trestles.sdsc.edu", 2, 32, 0, "shared", 0, 0, 0, "uot111");
            UserConfigurationData userConfigurationData = new UserConfigurationData();
            
            scheduling.setResourceHostId("gsissh-trestles");
            userConfigurationData.setAiravataAutoSchedule(false);
            userConfigurationData.setOverrideManualScheduledParams(false);
        
            AdvancedOutputDataHandling dataHandling = new AdvancedOutputDataHandling();
            dataHandling.setOutputDataDir("/home/airavata/output/");
            userConfigurationData.setAdvanceOutputDataHandling(dataHandling);
        
            userConfigurationData.setComputationalResourceScheduling(scheduling);
            simpleExperiment.setUserConfigurationData(userConfigurationData);
            return client.createExperiment(simpleExperiment);
        } catch (AiravataSystemException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataSystemException(e);
        } catch (InvalidRequestException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new InvalidRequestException(e);
        } catch (AiravataClientException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataClientException(e);
        }catch (TException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new TException(e);
        }
    }
    public static String createUS3ExperimentForStampede (Airavata.Client client) throws AiravataSystemException, InvalidRequestException, AiravataClientException, TException  {
        try{
            List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
            DataObjectType input = new DataObjectType();
            input.setKey("input");
            input.setType(DataType.URI);
            input.setValue("file:///home/airavata/input/hpcinput.tar");
            exInputs.add(input);

            List<DataObjectType> exOut = new ArrayList<DataObjectType>();
            DataObjectType output = new DataObjectType();
            output.setKey("output");
            output.setType(DataType.URI);
            output.setValue("");
            DataObjectType output1 = new DataObjectType();
            output1.setKey("stdout");
            output1.setType(DataType.STDOUT);
            output1.setValue("");
            DataObjectType output2 = new DataObjectType();
            output2.setKey("stderr");
            output2.setType(DataType.STDERR);
            output2.setValue("");
            exOut.add(output);
            exOut.add(output1);
            exOut.add(output2);

            Project project = ProjectModelUtil.createProject("project1", "admin", "test project");
            String projectId = client.createProject(project);

            Experiment simpleExperiment = ExperimentModelUtil.createSimpleExperiment(projectId, "admin", "US3ExperimentStampede", "US3AppStampede", "US3AppStampede", exInputs);
            simpleExperiment.setExperimentOutputs(exOut);

            ComputationalResourceScheduling scheduling = ExperimentModelUtil.createComputationResourceScheduling("stampede.tacc.xsede.org", 2, 32, 0, "normal", 0, 0, 0, "TG-MCB070039N");

            scheduling.setResourceHostId("gsissh-stampede");
            UserConfigurationData userConfigurationData = new UserConfigurationData();
           
            userConfigurationData.setAiravataAutoSchedule(false);
            userConfigurationData.setOverrideManualScheduledParams(false);
            userConfigurationData.setComputationalResourceScheduling(scheduling);
        
            AdvancedOutputDataHandling dataHandling = new AdvancedOutputDataHandling();
            dataHandling.setOutputDataDir("/home/airavata/output/");
            userConfigurationData.setAdvanceOutputDataHandling(dataHandling);
        
            simpleExperiment.setUserConfigurationData(userConfigurationData);
            return client.createExperiment(simpleExperiment);
        } catch (AiravataSystemException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataSystemException(e);
        } catch (InvalidRequestException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new InvalidRequestException(e);
        } catch (AiravataClientException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataClientException(e);
        }catch (TException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new TException(e);
        }
    }
    public static String createExperimentForStampede(Airavata.Client client) throws TException  {
        try{
            List<DataObjectType> exInputs = new ArrayList<DataObjectType>();
            DataObjectType input = new DataObjectType();
            input.setKey("echo_input");
            input.setType(DataType.STRING);
            input.setValue("echo_output=Hello World");
            exInputs.add(input);

            List<DataObjectType> exOut = new ArrayList<DataObjectType>();
            DataObjectType output = new DataObjectType();
            output.setKey("echo_output");
            output.setType(DataType.STRING);
            output.setValue("");
            exOut.add(output);

            Project project = ProjectModelUtil.createProject("project1", "admin", "test project");
            String projectId = client.createProject(project);

            Experiment simpleExperiment =
                    ExperimentModelUtil.createSimpleExperiment(projectId, "admin", "US3EchoExperimentStatus", "US3EchoStampede", "US3EchoStampede", exInputs);
            simpleExperiment.setExperimentOutputs(exOut);

            ComputationalResourceScheduling scheduling =
                    ExperimentModelUtil.createComputationResourceScheduling("stampede.tacc.xsede.org", 1, 1, 1, "development", 0, 0, 1, "TG-MCB070039N");
            scheduling.setResourceHostId("gsissh-stampede");
            UserConfigurationData userConfigurationData = new UserConfigurationData();
            userConfigurationData.setAiravataAutoSchedule(false);
            userConfigurationData.setOverrideManualScheduledParams(false);
            userConfigurationData.setComputationalResourceScheduling(scheduling);
            simpleExperiment.setUserConfigurationData(userConfigurationData);
            return client.createExperiment(simpleExperiment);
        } catch (AiravataSystemException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataSystemException(e);
        } catch (InvalidRequestException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new InvalidRequestException(e);
        } catch (AiravataClientException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new AiravataClientException(e);
        }catch (TException e) {
            logger.error("Error occured while creating the experiment...", e.getMessage());
            throw new TException(e);
        }
    }

    public static void launchExperiment (Airavata.Client client, String expId)
            throws TException{
        try {
            client.launchExperiment(expId, "testToken");
        } catch (ExperimentNotFoundException e) {
            logger.error("Error occured while launching the experiment...", e.getMessage());
            throw new ExperimentNotFoundException(e);
        } catch (AiravataSystemException e) {
            logger.error("Error occured while launching the experiment...", e.getMessage());
            throw new AiravataSystemException(e);
        } catch (InvalidRequestException e) {
            logger.error("Error occured while launching the experiment...", e.getMessage());
            throw new InvalidRequestException(e);
        } catch (AiravataClientException e) {
            logger.error("Error occured while launching the experiment...", e.getMessage());
            throw new AiravataClientException(e);
        }catch (TException e) {
            logger.error("Error occured while launching the experiment...", e.getMessage());
            throw new TException(e);
        }
    }
}
