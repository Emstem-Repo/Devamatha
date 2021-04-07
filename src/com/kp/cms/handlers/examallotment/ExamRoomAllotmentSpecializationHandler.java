package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentSpecialization;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentSpecializationForm;
import com.kp.cms.helpers.examallotment.ExamRoomAllotmentSpecializationHelpers;
import com.kp.cms.to.examallotment.ExamRoomAllotmentSpecializationTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentSpecializationTransactions;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentSpecializationTxnImpl;
import com.kp.cms.utilities.PropertyUtil;

public class ExamRoomAllotmentSpecializationHandler {
	private static volatile ExamRoomAllotmentSpecializationHandler handler = null;
	public static ExamRoomAllotmentSpecializationHandler getInstance(){
		if(handler == null){
			handler = new ExamRoomAllotmentSpecializationHandler();
		}
		return handler;
		
	}
	IExamRoomAllotmentSpecializationTransactions  transaction =ExamRoomAllotmentSpecializationTxnImpl.getInstance();

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveExamRoomSpecializationDetails( ExamRoomAllotmentSpecializationForm objForm) throws Exception {
		List<ExamRoomAllotmentSpecialization> existedBOList = transaction.getAlreadyExistedBoList(objForm.getMidEndSem(),objForm.getSchemeNo());
		Map<Integer,ExamRoomAllotmentSpecialization> existDataMap = ExamRoomAllotmentSpecializationHelpers.getInstance().convertBOListToMap(existedBOList);
		List<ExamRoomAllotmentSpecialization> boList = ExamRoomAllotmentSpecializationHelpers .getInstance().createSpecializationBOList(objForm,existDataMap);
		return transaction.saveOrUpdateBOList(boList);
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public Map<String,Map<Integer,ExamRoomAllotmentSpecializationTo>> getExamRoomSpecializationList( ExamRoomAllotmentSpecializationForm objForm) throws Exception{
		String getExamSpecializationDataQuery = ExamRoomAllotmentSpecializationHelpers.getInstance().getExamSpecializationDataQuery(objForm);
		List<ExamRoomAllotmentSpecialization> boList = PropertyUtil.getInstance().getListOfData(getExamSpecializationDataQuery);
		Map<String,Map<Integer,ExamRoomAllotmentSpecializationTo>> semWiseMap = ExamRoomAllotmentSpecializationHelpers.getInstance().populateBOListTOMap(boList);
		return semWiseMap;
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateExamRoomSpecializationDetails( ExamRoomAllotmentSpecializationForm objForm) throws Exception {
		List<ExamRoomAllotmentSpecialization> existedBOList = transaction.getAlreadyExistedBoList(objForm.getMidEndSem(),objForm.getSchemeNo());
		List<ExamRoomAllotmentSpecialization> boList = ExamRoomAllotmentSpecializationHelpers.getInstance().updateSpecializationBoList(objForm,existedBOList);
		return transaction.saveOrUpdateBOList(boList);
	}
	/**
	 * @param objForm
	 * @param request
	 * @throws Exception
	 */
	public void getEditSpecializationDetails( ExamRoomAllotmentSpecializationForm objForm, HttpServletRequest request) throws Exception{
		Map<Integer,String> tempCourseMap = objForm.getCourseMap();
		Map<Integer,String> selectedCourseMap = new HashMap<Integer, String>();
		String coursesList = objForm.getCourseList();
		String coursesList1 =coursesList.substring(1, coursesList.length()-1);
		String[] c =coursesList1.split(",");
	    for(int i=0;i<c.length;i++){
	    	int courseId = Integer.valueOf(c[i].trim());
	    	if(tempCourseMap.containsKey(courseId)){
	    		String courseName= tempCourseMap.get(Integer.valueOf(courseId));
	    		tempCourseMap.remove(Integer.valueOf(courseId));
	    		selectedCourseMap.put(Integer.valueOf(courseId), courseName);
	    	}
	    }
	    objForm.setCourseMap(tempCourseMap);
	    objForm.setSelectedCourseMap(selectedCourseMap);
	}

	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteExamRoomSpecializationDetails( ExamRoomAllotmentSpecializationForm objForm) throws Exception{
		List<ExamRoomAllotmentSpecialization> existedBOList = transaction.getAlreadyExistedBoList(objForm.getMidEndSem(),objForm.getSchemeNo());
		return transaction.deleteSpecializationDetails(existedBOList,objForm);
	}
}
