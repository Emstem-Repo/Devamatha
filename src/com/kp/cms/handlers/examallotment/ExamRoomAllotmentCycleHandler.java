package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycleDetails;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentCycleForm;
import com.kp.cms.helpers.examallotment.ExamRoomAllotementCycleHelper;
import com.kp.cms.to.examallotment.ExamRoomAllotmentCycleTO;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentCycleTransaction;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentCycleTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;

public class ExamRoomAllotmentCycleHandler {
	private static volatile ExamRoomAllotmentCycleHandler handler;
	public static ExamRoomAllotmentCycleHandler getInstance(){
		if(handler == null){
			handler = new ExamRoomAllotmentCycleHandler();
		}
		return handler;
	}
	IExamRoomAllotmentCycleTransaction transaction = ExamRoomAllotmentCycleTxnImpl.getInstance();
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveRoomAllotmentCycleDetails( ExamRoomAllotmentCycleForm objForm) throws Exception{
		List<ExamRoomAllotmentCycleDetails> roomCycleDetailsBoList = ExamRoomAllotementCycleHelper.getInstance().convertFormToBOList(objForm);
		return transaction.saveRoomCycleBo(roomCycleDetailsBoList);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public Map<Integer,String> getCourseListBySchemeNo(ExamRoomAllotmentCycleForm objForm) throws Exception{
		String hqlQuery = ExamRoomAllotementCycleHelper.getInstance().getExistedRoomCycleHqlQuery(objForm.getCycleId(), objForm.getSchemeNo());
		List<Course> allotmentCycleBoList = transaction.getListOfData(hqlQuery);
		Map<Integer,String> courseMap = new HashMap<Integer, String>();
		for (Course course : allotmentCycleBoList) {
			courseMap.put(course.getId(), course.getName());
		}
		return CommonUtil.sortMapByValue(courseMap) ;
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer,Map<String,Map<String,List<Integer>>>> getRoomAllotmentCycleDetails() throws Exception{
		List<ExamRoomAllotmentCycleDetails> cycleDetailsBoList = transaction.getRoomAllotCycleDetails();
		Map<Integer,Map<String,Map<String,List<Integer>>>> mapList = ExamRoomAllotementCycleHelper.getInstance().convertBoListToMapList(cycleDetailsBoList);
		return mapList;
	}
	public List<ExamRoomAllotmentCycleTO> getCycleEntryDetails() throws Exception{
		String hqlQuery = ExamRoomAllotementCycleHelper.getInstance().getRoomAllotCycleDetailsQuery();
		List<ExamRoomAllotmentCycle> allotmentCyclesList = PropertyUtil.getInstance().getListOfData(hqlQuery);
		List<ExamRoomAllotmentCycleTO> toList = ExamRoomAllotementCycleHelper.getInstance().convertBoListTOToList(allotmentCyclesList);
		return toList;
	}
	/**
	 * @param objForm
	 * @return
	 */
	public boolean checkDuplicateEntry(ExamRoomAllotmentCycleForm objForm) throws Exception{
		boolean isDuplicate = false;
		String duplicateCheckQuery = ExamRoomAllotementCycleHelper.getInstance().getDuplicateCheckQuery(objForm);
		ExamRoomAllotmentCycle allotmentCyclesList = (ExamRoomAllotmentCycle) PropertyUtil.getDataForUniqueObject(duplicateCheckQuery);
		if(allotmentCyclesList!=null ){
			if(allotmentCyclesList.getId() == objForm.getId()){
				isDuplicate = false;
			}else{
				isDuplicate = true;
			}
		}
		return isDuplicate;
	}
	/**
	 * @param objForm
	 * @param mode 
	 * @return
	 * @throws Exception
	 */
	public boolean addRoomCycleDetails(ExamRoomAllotmentCycleForm objForm, String mode)throws Exception {
		boolean isAdded = false;
		ExamRoomAllotmentCycle bo = ExamRoomAllotementCycleHelper.getInstance().convertFormToBO(objForm,mode);
		if(mode.equalsIgnoreCase("Add")){
			isAdded= PropertyUtil.getInstance().save(bo);
		}else if(mode.equalsIgnoreCase("Edit")){
			isAdded= PropertyUtil.getInstance().update(bo);
		}
		return isAdded;
		
	}
	/**
	 * @param id
	 * @param activate
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public boolean deleteRoomCycleDetails(int id, boolean activate, String userId) throws Exception{
		return transaction.deleteRoomCycle(id,activate,userId);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getCycleMap() throws Exception{
		String hqlQuery = ExamRoomAllotementCycleHelper.getInstance().getRoomAllotCycleDetailsQuery();
		List<ExamRoomAllotmentCycle> boList  = PropertyUtil.getInstance().getListOfData(hqlQuery);
		Map<Integer, String> cycleMap = ExamRoomAllotementCycleHelper.getInstance().convertBoListToMap(boList);
		return cycleMap;
	}
	/**
	 * @param cycleId
	 * @return
	 * @throws Exception
	 */
	public ExamRoomAllotmentCycle getMidOrEndAndSessionDetailsByCycleId( int cycleId) throws Exception{
		return transaction.getMidEndAndSessionByCycleId(cycleId);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void getEditCycleListDetails(ExamRoomAllotmentCycleForm objForm)throws Exception {
		ExamRoomAllotmentCycle cycle = transaction.getMidSemSchemeNoByCycleId(objForm.getCycleId());
		objForm.setMidOrEndSem(cycle.getMidOrEnd());
		objForm.setSessionName(cycle.getSession());
		Map<Integer,String> tempCourseMap = objForm.getCourseMap();
		Map<Integer,String> selectedCourseMap = new HashMap<Integer, String>();
		String coursesList = objForm.getCourseList();
		String coursesList1 =coursesList.substring(1, coursesList.length()-1);
		String[] c =coursesList1.split(",");
	    for (String courseId : c) {
	    	int coursId = Integer.parseInt(courseId.trim());
	    	if(tempCourseMap.containsKey(coursId)){
	    		String courseName= tempCourseMap.get(coursId);
	    		tempCourseMap.remove(coursId);
	    		selectedCourseMap.put(coursId, courseName);
	    	}
		}
	    selectedCourseMap = CommonUtil.sortMapByKey(selectedCourseMap);
	    objForm.setCourseMap(tempCourseMap);
	    objForm.setSelectedCourseMap(selectedCourseMap);
	}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception 
	 */
	public  boolean deleteCycleDetails(ExamRoomAllotmentCycleForm objForm) throws Exception {
		String deleteCycleHqlQuery  = ExamRoomAllotementCycleHelper.getInstance().getCycleDetailsQuery(objForm);
		List<ExamRoomAllotmentCycleDetails> boList = PropertyUtil.getInstance().getListOfData(deleteCycleHqlQuery);
		return transaction.updateAllotmentCycleDetails(boList,objForm,"delete");
	}
	/**
	 * @param objForm
	 * @return
	 */
	public boolean updateCycleDetails(ExamRoomAllotmentCycleForm objForm)throws Exception {
		String cycleHqlQuery  = ExamRoomAllotementCycleHelper.getInstance().getCycleDetailsQuery(objForm);
		List<ExamRoomAllotmentCycleDetails> existBoList = PropertyUtil.getInstance().getListOfData(cycleHqlQuery);
		List<ExamRoomAllotmentCycleDetails> updateBoList = ExamRoomAllotementCycleHelper.getInstance().updateCycleDetailsList(existBoList,objForm);
		return transaction.updateAllotmentCycleDetails(updateBoList,objForm,"Edit");
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getExamSessionMap() throws Exception{
		return transaction.getExaminationSessionMap();
	}
}
