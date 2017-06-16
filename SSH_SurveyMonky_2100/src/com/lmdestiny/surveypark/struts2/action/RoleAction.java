package com.lmdestiny.surveypark.struts2.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lmdestiny.surveypark.model.security.Right;
import com.lmdestiny.surveypark.model.security.Role;
import com.lmdestiny.surveypark.service.RightService;
import com.lmdestiny.surveypark.service.RoleService;

/**
 *角色action 
 */
@Controller("roleAction")
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {

	private static final long serialVersionUID = 8184781006443019890L;
	
	private List<Role> allRoles;
	private RoleService roleService;
	private RightService rightService;
	private Integer[] ownRightIds;
	//角色没有的权限集合
	private List<Right> noOwnRights ;
	//权限列表
	private Integer roleId ;
	
	public String findAllRoles(){
		this.allRoles = roleService.findAllEntities();
		return "roleListPage";
	}
	
	public String toAddRolePage(){
		this.noOwnRights = rightService.findAllEntities();
		return "addRolePage";
	}
	
	/**
	 * 保存更新角色
	 */
	public String saveOrUpdateRole(){
		roleService.saveOrUpdateRole(model,ownRightIds);
		return "findAllRolesAction" ;
	}
	/**
	 * 编辑角色
	 */
	public String editRole(){
		this.model = roleService.getEntity(roleId);
		this.noOwnRights = rightService.findRightsNotInRange(model.getRights());
		return "editRolePage" ;
	}
	
	/**
	 * 删除角色
	 */
	public String deleteRole(){
		Role r = new Role();
		r.setId(roleId);
		roleService.deleteEntity(r);
		return "findAllRolesAction" ;
	}
	
	public List<Role> getAllRoles() {
		return allRoles;
	}
	public void setAllRoles(List<Role> allRoles) {
		this.allRoles = allRoles;
	}


	public RoleService getRoleService() {
		return roleService;
	}

	@Resource(name="roleService")
	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public List<Right> getNoOwnRights() {
		return noOwnRights;
	}

	public void setNoOwnRights(List<Right> noOwnRights) {
		this.noOwnRights = noOwnRights;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RightService getRightService() {
		return rightService;
	}
	@Resource(name="rightService")
	public void setRightService(RightService rightService) {
		this.rightService = rightService;
	}

	public Integer[] getOwnRightIds() {
		return ownRightIds;
	}

	public void setOwnRightIds(Integer[] ownRightIds) {
		this.ownRightIds = ownRightIds;
	}

}
