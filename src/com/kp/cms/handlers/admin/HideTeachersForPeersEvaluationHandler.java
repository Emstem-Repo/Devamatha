package com.kp.cms.handlers.admin;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.HideTeachersForPeersEvaluation;
import com.kp.cms.forms.admin.HideTeachersForPeersEvaluationForm;
import com.kp.cms.helpers.admin.HideTeachersForPeersEvaluationHelper;
import com.kp.cms.to.admin.HideTeachersForPeersEvaluationTo;
import com.kp.cms.transactions.admin.IHideTeachersForPeersEvaluationTransaction;
import com.kp.cms.transactions.studentfeedback.IEvaHiddenSubjectTeacherTransaction;
import com.kp.cms.transactionsimpl.admin.HideTeachersForPeersEvaluationTxnImpl;

public class HideTeachersForPeersEvaluationHandler {
	private static final Log log = LogFactory.getLog(HideTeachersForPeersEvaluationHandler.class);
	public static volatile HideTeachersForPeersEvaluationHandler handler = null;
	public static HideTeachersForPeersEvaluationHandler getInstance(){
		if(handler == null){
			handler = new HideTeachersForPeersEvaluationHandler();
			return handler;
		}
		return handler;
	}
	IHideTeachersForPeersEvaluationTransaction transaction = HideTeachersForPeersEvaluationTxnImpl.getInstance();
	/**
	 * @param hideTeacherform
	 * @return
	 * @throws Exception
	 */
	public boolean hideTeacherForPeersEvaluation(
			HideTeachersForPeersEvaluationForm hideTeacherform)
			throws Exception {
		HideTeachersForPeersEvaluation hideTeachersBo = HideTeachersForPeersEvaluationHelper
				.getInstance().convertDateFromFormToBO(hideTeacherform);
		boolean isHidden = transaction.hideTeachers(hideTeachersBo);
		return isHidden;
	}

	/**
	 * @param hideTeacherform
	 * @return
	 * @throws Exception
	 */
	public boolean duplicateCheck( HideTeachersForPeersEvaluationForm hideTeacherform)
			throws Exception {
		int deptId = Integer.parseInt(hideTeacherform.getDepartmentId().trim());
		int teacherId = Integer.parseInt(hideTeacherform.getTeacherId());
		String deleteQuery = HideTeachersForPeersEvaluationHelper.getInstance().deleteQuery(deptId,teacherId);
		boolean isDuplicate = transaction.duplicateCheck(deleteQuery);
		return isDuplicate;
	}
	/**
	 * @param hideTeacherform
	 * @return
	 */
	public List<HideTeachersForPeersEvaluationTo> getHiddenTeachers(
			HideTeachersForPeersEvaluationForm hideTeacherform)
			throws Exception {
		List<HideTeachersForPeersEvaluation> boList = transaction
				.getHiddenTeachers(hideTeacherform);
		List<HideTeachersForPeersEvaluationTo> toList = HideTeachersForPeersEvaluationHelper
				.getInstance().populateBoTOTo(boList);
		return toList;
	}
	/**
	 * @param hideTeacherform
	 * @param teachersMap 
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> iterateTeachersMap( HideTeachersForPeersEvaluationForm hideTeacherform, Map<Integer, String> teachersMap) throws Exception{
		List<HideTeachersForPeersEvaluationTo> toList = hideTeacherform.getHiddenTeachersList();
		if (toList != null && !toList.isEmpty()) {
			Iterator<HideTeachersForPeersEvaluationTo> iterator = toList
					.iterator();
			while (iterator.hasNext()) {
				HideTeachersForPeersEvaluationTo to = (HideTeachersForPeersEvaluationTo) iterator
						.next();
				if (teachersMap.containsKey(to.getTeacherId())) {
					/*
					 * teachersMap key contains the teacherId ,then removing
					 * from the teachersMap
					 */
					teachersMap.remove(to.getTeacherId());
				}

			}
		}
		return teachersMap;
	}
	/**
	 * @param hideTeacherform
	 * @return
	 * @throws Exception
	 */
	public boolean deleteHiddenTeachers( HideTeachersForPeersEvaluationForm hideTeacherform) throws Exception{
		int deptId = Integer.parseInt(hideTeacherform.getDepartmentId().trim());
		int teacherId = Integer.parseInt(hideTeacherform.getTeacherId());
		String deleteQuery = HideTeachersForPeersEvaluationHelper.getInstance().deleteQuery(deptId,teacherId);
		boolean isDeleted = transaction.deleteHiddenTeachers(deleteQuery,hideTeacherform);
		return isDeleted;
	}

	/**
	 * @param departmentId
	 * @return
	 * @throws Exception
	 */
	public String getDepartmentName(int departmentId)throws Exception {
		String departmentName = transaction.getDepartmentName(departmentId);
		return departmentName;
	}
}
