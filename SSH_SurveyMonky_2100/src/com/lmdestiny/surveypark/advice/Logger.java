package com.lmdestiny.surveypark.advice;

import java.util.Map;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;

import com.lmdestiny.surveypark.model.Log;
import com.lmdestiny.surveypark.model.User;
import com.lmdestiny.surveypark.service.LogService;
import com.lmdestiny.surveypark.util.StringUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * Logger
 */
public class Logger {
	
	
	private LogService logService ;
	/**
	 * 记录
	 */
	public Object record(ProceedingJoinPoint pjp){
		Log log = new Log();
		try {
			ActionContext ac = ActionContext.getContext();
			//设置操作人
			if(ac != null){
				Map<String, Object> session = ac.getSession();
				if(session != null){
					User user = (User) session.get("user");
					if(user != null){
						log.setOperator("" + user.getId() + ":" + user.getEmail());
					}
				}
			}
			//操作名称
			String mname = pjp.getSignature().getName();
			log.setOperName(mname);
			//操作参数 
			Object[] params = pjp.getArgs();
			log.setOperParams(StringUtil.arr2Str(params));
			//调用目标对象的方法
			Object ret = pjp.proceed();
			//设置操作结果
			log.setOperResult("success");
			//设置结果消息
			if(ret != null){
				log.setResultMsg(ret.toString());
			}
			return ret ;
		} catch (Throwable e) {
			log.setOperResult("failure");
			log.setResultMsg(e.getMessage());
		}
		finally{
			logService.saveEntity(log);
		}
		return null ;
	}
	public LogService getLogService() {
		return logService;
	}
	@Resource(name="logService")
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
