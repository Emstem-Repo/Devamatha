package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.HideTeachersForPeersEvaluation;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm;
import com.kp.cms.to.admin.HideTeachersForPeersEvaluationTo;

public class HideTeachersForPeersEvaluationHelper {
	private static final Log log = LogFactory.getLog(HideTeachersForPeersEvaluationHelper.class);
	public static volatile HideTeachersForPeersEvaluationHelper helper = null;
	public static HideTeachersForPeersEvaluationHelper getInstance(){
		if(helper == null){
			helper = new HideTeachersForPeersEvaluationHelper();
			return helper;
		}
		return helper;
	}
	public HideTeachersForPeersEvaluation convertDateFromFormToBO( HideTeachersForPeersEvaluationForm hideTeacherform) throws Exception{
		HideTeachersForPeersEvaluation teachersForPeersEvaluation = new HideTeachersForPeersEvaluation();
		if(hideTeacherform.getDepartmentId()!=null && !hideTeacherform.getDepartmentId().isEmpty()){
			Department department = new Department();
			department.setId(Integer.parseInt(hideTeacherform.getDepartmentId().trim()));
			teachersForPeersEvaluation.setDepartment(department);
		}
		if(hideTeacherform.getTeacherId()!=null && !hideTeacherform.getTeacherId().isEmpty()){
			Users users = new Users();
			users.setId(Integer.parseInt(hideTeacherform.getTeacherId().trim()));
			teachersForPeersEvaluation.setUsers(users);
		}
		teachersForPeersEvaluation.setCreatedBy(hideTeacherform.getUserId());
		teachersForPeersEvaluation.setCreatedDate(new Date());
		teachersForPeersEvaluation.setIsActive(true);
		return teachersForPeersEvaluation;
	}
	/**
	 * @param boList
	 * @return
	 */
	public List<HideTeachersForPeersEvaluationTo> populateBoTOTo( List<HideTeachersForPeersEvaluation> boList) throws Exception{
		List<HideTeachersForPeersEvaluationTo> toList = new ArrayList<HideTeachersForPeersEvaluationTo>();
		if(boList!=null && !boList.toString().isEmpty()){
			Iterator<HideTeachersForPeersEvaluation> iterator = boList.iterator();
			while (iterator.hasNext()) {
				HideTeachersForPeersEvaluation bo = (HideTeachersForPeersEvaluation) iterator
						.next();
				HideTeachersForPeersEvaluationTo to = new HideTeachersForPeersEvaluationTo();
				if (bo.getId() != 0) {
					to.setId(bo.getId());
				}
				if (bo.getDepartment() != null
						&& !bo.getDepartment().toString().isEmpty()) {
					to.setDepartmentId(bo.getDepartment().getId());
				}
				if (bo.getUsers() != null
						&& !bo.getUsers().toString().isEmpty()) {
					to.setTeacherId(bo.getUsers().getId());
				}
				if(bo.getUsers().getEmployee()!=null && !bo.getUsers().getEmployee().toString().isEmpty()){
					if(bo.getUsers().getEmployee().getFirstName()!=null && !bo.getUsers().getEmployee().getFirstName().isEmpty()){
						to.setTeacherName(bo.getUsers().getEmployee().getFirstName());
					}
				}else if(bo.getUsers().getUserName()!=null && !bo.getUsers().getUserName().isEmpty()){
					to.setTeacherName(bo.getUsers().getUserName());
				}
				toList.add(to);
			}
		}
		return toList;
	}

	/**
	 * @param hideTeacherform
	 * @return
	 */
	public String deleteQuery(int deptId, int teacherId)throws Exception {
		String deleteQuery = "from HideTeachersForPeersEvaluation hideTeacher where hideTeacher.isActive = 1 " +
		"and hideTeacher.department.id ="+deptId+ "and hideTeacher.users.id ="+teacherId;
		return deleteQuery;
	}
}
