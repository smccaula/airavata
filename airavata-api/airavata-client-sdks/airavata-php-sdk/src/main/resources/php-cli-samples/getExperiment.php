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
use Airavata\Model\Workspace\Experiment\ExperimentState;
use Thrift\Exception\TTransportException;
use Thrift\Protocol\TBinaryProtocol;
use Thrift\Transport\TBufferedTransport;
use Thrift\Transport\TSocket;
use Airavata\API\AiravataClient;

try {
    if (count($argv) < 2) {
        exit("Please provide an experimentID. Usage: \n");
    } else {
        $experimentId = $argv[1];
        $experiment = $airavataclient->getExperiment($experimentId);
        var_dump($experiment);
    }
} catch (InvalidRequestException $ire) {
    echo 'InvalidRequestException!<br><br>' . $ire->getMessage();
} catch (ExperimentNotFoundException $enf) {
    echo 'ExperimentNotFoundException!<br><br>' . $enf->getMessage();
} catch (AiravataClientException $ace) {
    echo 'AiravataClientException!<br><br>' . $ace->getMessage();
} catch (AiravataSystemException $ase) {
    echo 'AiravataSystemException!<br><br>' . $ase->getMessage();
} catch (TTransportException $tte) {
    echo 'TTransportException!<br><br>' . $tte->getMessage();
} catch (\Exception $e) {
    echo 'Exception!<br><br>' . $e->getMessage();
}

$transport->close();

?>

