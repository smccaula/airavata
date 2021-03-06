<?php
/**
 * Bundle all thrift and Airavata stubs into a include file. This is simple but not so elegant way.
 *  Contributions welcome to improve writing PHP Client Samples.
 *
 */
include 'getAiravataClient.php';
global $airavataclient;
global $transport;

use Airavata\API\Error\AiravataClientException;
use Airavata\API\Error\AiravataSystemException;
use Airavata\API\Error\ExperimentNotFoundException;
use Airavata\API\Error\InvalidRequestException;
use Airavata\Client\AiravataClientFactory;
use Thrift\Protocol\TBinaryProtocol;
use Thrift\Transport\TBufferedTransport;
use Thrift\Transport\TSocket;
use Airavata\API\AiravataClient;

use Airavata\Model\Workspace\Project;
use Airavata\Model\Workspace\Experiment\Experiment;

try {
    if (count($argv) < 2) {
        exit("Please provide the experimentId to be launched. Usage: php launchExperiment.php <experimentId> \n");
    } else {
        $experimentId = $argv[1];
        $airavataclient->launchExperiment($experimentId, $airavataconfig['AIRAVATA_CREDENTIAL_STORE_TOKEN']);
        echo "Experiment $experimentId is launched. \n";
    }
} catch (InvalidRequestException $ire) {
    print 'InvalidRequestException: ' . $ire->getMessage() . "\n";
} catch (AiravataClientException $ace) {
    print 'Airavata System Exception: ' . $ace->getMessage() . "\n";
} catch (AiravataSystemException $ase) {
    print 'Airavata System Exception: ' . $ase->getMessage() . "\n";
} catch (ExperimentNotFoundException $enf) {
    print 'Experiment Not Found Exception: ' . $enf->getMessage() . "\n";
} catch (LaunchValidationException $lve) {
    print 'Experiment Validation Failed: ' . $lve->getMessage() . "\n";
}

$transport->close();

?>
