package com.lmdestiny.serveypack.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class CreateLogTablesTask  extends QuartzJobBean{

	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("≥…π¶¡À");
	}

}
