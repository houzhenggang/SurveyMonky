package com.lmdestiny.surveypark.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Right;
import com.lmdestiny.surveypark.model.security.Role;

/**
 * 用户实体类
 * @author wangbin
 *
 */
public class User {
	private Integer id;
	private String  email;
	private String name;
	private String nickName;
	private String password;
	/**
	 * 用户注册时间不能改
	 */
	private Date regDate = new Date();
	//角色集合
	private Set<Role> roles = new HashSet<Role>();
	//用户权限总和
	private long[] rightSum;
	//是否是超级管理员
	private boolean superAdmin;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public long[] getRightSum() {
		return rightSum;
	}
	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}
	public boolean isSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(boolean superAdmin) {
		this.superAdmin = superAdmin;
	}
	/**
	 * 计算用户所具有的权限总和 
	 */
	public void calculateRightSum() {
		int pos =0;
		long code =0L;
		for(Role r : roles){
			if("-1".equals(r.getRoleValue())){
				this.superAdmin =true;
				roles =null;
				return ;
			}
			for(Right right: r.getRights()){
				code = right.getRightCode();
				pos = right.getRightPos();
				this.rightSum[pos] = this.rightSum[pos]|code;
			}
		}
		roles = null;
	}
	/**
	 * 判断用户是否具有权限
	 */
	public boolean hasRight(Right r){
		int pos = r.getRightPos();
		long code = r.getRightCode();
		return !((rightSum[pos]&code) ==0);
	}
}
