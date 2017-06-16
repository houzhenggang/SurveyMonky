package com.lmdestiny.surveypark.service.impl;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmdestiny.surveypark.dao.BaseDao;
import com.lmdestiny.surveypark.model.security.Right;
import com.lmdestiny.surveypark.model.security.Role;
import com.lmdestiny.surveypark.service.RightService;
import com.lmdestiny.surveypark.service.RoleService;
import com.lmdestiny.surveypark.util.StringUtil;
import com.lmdestiny.surveypark.util.ValidateUtil;

/**
 *��ɫservice 
 */
@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {
	
	@Resource( name="roleDao")
	public void setDao(BaseDao<Role> dao){
		super.setDao(dao);
	}
	
	private RightService rightService;
	
	/**
	 * ����/���½�ɫ
	 */
	public void saveOrUpdateRole(Role model, Integer[] ids){
		//û�и���ɫ�����κ�Ȩ��
		if(!ValidateUtil.isValid(ids)){
			model.getRights().clear();
		}
		else{
			List<Right> rights = rightService.findRightsInRange(ids);
			model.setRights(new HashSet<Right>(rights));
		}
		this.saveOrUpdateEntity(model);
	}

	public RightService getRightService() {
		return rightService;
	}
	@Resource(name="rightService")
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}
	/**
	 * �����û�û�еĽ�ɫ
	 */
	public List<Role> findRolesNotInRange(Set<Role> roles) {
		if(ValidateUtil.isValid(roles)){
			String temp = "";
			for(Role r : roles){
				temp += r.getId()+",";
			}
			temp = temp.substring(0, temp.length()-1);
			
			String hql="from Role r where r.id not in ("+temp+")";
			return this.findEntityByHQL(hql);
		}
		return this.findAllEntities();
	}
	/**
	 * �����û����ڵĽ�ɫ
	 */
	public List<Role> findRolesInRange(Integer[] ids){
		if(ValidateUtil.isValid(ids)){
			String hql = "from Role r where r.id in ("+StringUtil.arr2Str(ids)+")" ;
			return this.findEntityByHQL(hql);
		}
		return null ;
	}
}
