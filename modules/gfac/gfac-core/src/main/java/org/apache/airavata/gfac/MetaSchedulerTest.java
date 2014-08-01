package org.apache.airavata.gfac;

public class MetaSchedulerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
        System.out.println("Calling Throttle Job");  
        MetaScheduler.submitThrottleJob("exp ID","task ID","gatewayID");

	}

}
