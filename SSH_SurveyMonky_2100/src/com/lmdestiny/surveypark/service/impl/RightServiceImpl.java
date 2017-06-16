package com.lmdestiny.surveypark.service.impl;

import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lmdestiny.surveypark.dao.BaseDao;
import com.lmdestiny.surveypark.model.security.Right;
import com.lmdestiny.surveypark.service.RightService;
import com.lmdestiny.surveypark.util.StringUtil;
import com.lmdestiny.surveypark.util.ValidateUtil;

/**
 * Ȩ��service
 */
@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements RightService {
	
	@Resource(name = "rightDao")
	public void setDao(BaseDao<Right> dao) {
		super.setDao(dao);
	}

	// ����/����Ȩ��
	public void saveOrUpdateRight(Right r) {
		int pos = 0 ;
		long code = 1L ; 
		// �������
		if (r.getId() == null) {
			/*
			 * String hql =
			 * "from Right r order by r.rightCode desc ,r.rightPos desc";
			 * List<Right> rights = this.findEntityByHQL(hql); if
			 * (!ValidateUtil.isValid(rights)) { pos = 0; code = 1L; } else {
			 * Right right = rights.get(0); if (right.getRightCode() > (code <<
			 * 60)) { r.setRightPos(r.getRightPos() + 1); r.setRightCode(1L); }
			 * else { r.setRightPos(right.getRightPos());
			 * r.setRightCode(right.getRightCode() << 1); } }
			 */

			String hql = "select max(r.rightPos),max(r.rightCode) from Right r "
					+ "where r.rightPos = (select max(rr.rightPos) from Right rr)" ;
			Object[] arr = (Object[]) this.uniqueResult(hql);
			Integer topPos = (Integer) arr[0];
			Long topCode = (Long) arr[1];
			if(topPos == null){
				pos = 0 ;
				code = 1L ;
			}
			else{
				if(topCode >= (1L << 60)){
					pos = topPos + 1 ;
					code = 1L ;
				}
				else{
					pos = topPos ;
					code = topCode << 1 ;
				}
			}
			
			r.setRightPos(pos);
			r.setRightCode(code);
		}
		this.saveOrUpdateEntity(r);
	}

	/**
	 * ����url���Ȩ��
	 */
	public void appendRightByURL(String url) {
		String hql = "select count(*) from Right r where r.rightUrl =?";
		Long count = (Long) this.uniqueResult(hql, url);
		if (count == 0) {
			Right r = new Right();
			r.setRightUrl(url);
			this.saveOrUpdateRight(r);
		}

	}

	/**
	 * ��������Ȩ��
	 */
	public void batchUpdateRights(List<Right> allRights) {
		String hql = "update Right r set r.rightName = ? where r.id = ?";
		if (ValidateUtil.isValid(allRights)) {
			for (Right r : allRights) {
				this.batchEntityByHQL(hql, r.getRightName(), r.getId());
			}
		}
	}
	
	/**
	 * ��ѯ��ָ����Χ�ڵ�Ȩ��
	 */
	public List<Right> findRightsInRange(Integer[] ids){
		if(ValidateUtil.isValid(ids)){
		String hql="from Right r where r.id in ("+StringUtil.arr2Str(ids)+")";
		List<Right> rights = this.findEntityByHQL(hql);
		return rights;
		}
		return null;
	}
	/**
	 * ��ѯ����ָ����Χ�ڵ�Ȩ��
	 */
	public List<Right> findRightsNotInRange(Set<Right> rights) {
		String temp="";
		if(ValidateUtil.isValid(rights)){
			for(Right r :rights){
				temp +=r.getId()+",";
			}
			temp = temp.substring(0, temp.length()-1);
			String hql = "from Right r where r.id not in ("+temp+")";
			List<Right> rs = this.findEntityByHQL(hql);
			return rs; 
		}
		return this.findAllEntities();
	}
	/**
	 * ����Ȩ��λ���ֵ
	 */
	public int getMaxRightPos() {
		String hql ="select max(rightpos) from Right r";
		return (Integer)this.uniqueResult(hql);
	}

}
