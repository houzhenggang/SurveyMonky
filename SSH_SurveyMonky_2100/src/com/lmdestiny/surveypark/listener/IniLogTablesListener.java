package com.lmdestiny.surveypark.listener;
import javax.annotation.Resource;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.lmdestiny.surveypark.service.LogService;
import com.lmdestiny.surveypark.util.LogUtil;


/**
 * ��ʼ����־�������
 */
@Component
@SuppressWarnings("rawtypes")
public class IniLogTablesListener implements ApplicationListener {
	@Resource
	private LogService logService ;
	
	public void onApplicationEvent(ApplicationEvent arg0) {
		//������ˢ���¼�
		if(arg0 instanceof ContextRefreshedEvent){
			String tableName = LogUtil.generateLogTableName(0);
			logService.createLogTable(tableName);
			
			tableName = LogUtil.generateLogTableName(1);
			logService.createLogTable(tableName);
			
			tableName = LogUtil.generateLogTableName(2);
			logService.createLogTable(tableName);
			System.out.println("��ʼ����־�����!!!");
		}
	}
}
