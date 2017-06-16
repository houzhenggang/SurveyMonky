package com.lmdestiny.surveypark.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lmdestiny.surveypark.model.security.Right;
import com.lmdestiny.surveypark.model.security.Role;

/**
 * �û�ʵ����
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
	 * �û�ע��ʱ�䲻�ܸ�
	 */
	private Date regDate = new Date();
	//��ɫ����
	private Set<Role> roles = new HashSet<Role>();
	//�û�Ȩ���ܺ�
	private long[] rightSum;
	//�Ƿ��ǳ�������Ա
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
	 * �����û������е�Ȩ���ܺ� 
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
	 * �ж��û��Ƿ����Ȩ��
	 */
	public boolean hasRight(Right r){
		int pos = r.getRightPos();
		long code = r.getRightCode();
		return !((rightSum[pos]&code) ==0);
	}
}
