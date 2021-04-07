package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.examallotment.ExamRoomAllotmentGroupWise;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentGroupWiseForm;
import com.kp.cms.handlers.hostel.HolidaysHandler;
import com.kp.cms.helpers.examallotment.ExamRoomAllotmentGroupWiseHelper;
import com.kp.cms.to.examallotment.ExamRoomAllotmentGroupWiseTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentGroupWiseTransaction;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentGroupWiseTxnImpl;

public class ExamRoomAllotmentGroupWiseHandler {

	public static volatile ExamRoomAllotmentGroupWiseHandler allotmentGroupWiseHandler=null;
	
	/**
	 * @return
	 */
	public static ExamRoomAllotmentGroupWiseHandler getInstance(){
		if(allotmentGroupWiseHandler==null){
			allotmentGroupWiseHandler=new ExamRoomAllotmentGroupWiseHandler();
		}
		return allotmentGroupWiseHandler;
	}
	public ExamRoomAllotmentGroupWiseHandler() {
	}
	
	
	/**
	 * @param allotmentGroupWiseForm
	 * @return
	 * @throws Exception
	 */
	public boolean addExamRoomAllotMentGroupWise(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm) throws Exception {
		IExamRoomAllotmentGroupWiseTransaction transaction=ExamRoomAllotmentGroupWiseTxnImpl.getInstance();
		List<ExamRoomAllotmentGroupWise> groupWiseList=ExamRoomAllotmentGroupWiseHelper.getInstance().convertGroupWiseFormToBo(allotmentGroupWiseForm);
		return transaction.addAllotmentGroupWiseCourses(groupWiseList);
	}
	
	/**
	 * @param allotmentGroupWiseForm
	 * @param midOrEndSem
	 * @param schemeNo
	 * @return
	 * @throws Exception
	 */
	public Map<String, Map<Integer, ExamRoomAllotmentGroupWiseTo>> getCourseDetailsByMidOrEndAndSchemeNo(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm,
			String midOrEndSem, int schemeNo) throws Exception {
		IExamRoomAllotmentGroupWiseTransaction transaction=ExamRoomAllotmentGroupWiseTxnImpl.getInstance();
		Map<Integer, String> courseMap=HolidaysHandler.getInstance().courseMap();
		List<ExamRoomAllotmentGroupWise> groupWiseList=transaction.getGroupWiseListByMidOrEndAndSchemeNo(midOrEndSem,schemeNo);
		if(groupWiseList!=null && !groupWiseList.isEmpty()){
			for (ExamRoomAllotmentGroupWise examRoomAllotmentGroupWise : groupWiseList) {
				if(courseMap.containsKey(examRoomAllotmentGroupWise.getCourse().getId())){
					courseMap.remove(examRoomAllotmentGroupWise.getCourse().getId());
				}
			}
		}
		allotmentGroupWiseForm.setCourseMap(courseMap);
		return ExamRoomAllotmentGroupWiseHelper.getInstance().convertGroupWiseBoToTo(groupWiseList);
	}
	/**
	 * @param allotmentGroupWiseForm
	 * @throws Exception
	 */
	public void editGroupWiseAllotment(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm) throws Exception {
		IExamRoomAllotmentGroupWiseTransaction transaction=ExamRoomAllotmentGroupWiseTxnImpl.getInstance();
		Map<Integer, String> selectedCourseMap=new HashMap<Integer, String>();
		List<Integer> courseIdList=transaction.getCourseIdListByMidEndSemAndSchemeNo(allotmentGroupWiseForm.getMidOrEndSem(), Integer.parseInt(allotmentGroupWiseForm.getSchemeNo()));
		Map<Integer, String> courseMap=HolidaysHandler.getInstance().courseMap();
		String selectedCourses=allotmentGroupWiseForm.getSelectedCourses().substring(1, allotmentGroupWiseForm.getSelectedCourses().length()-1);
		String[] courseIDs=selectedCourses.split(", ");
		for (int i = 0; i < courseIDs.length; i++) {
			if(courseMap.containsKey(Integer.parseInt(courseIDs[i]))){
				String courseName=courseMap.get(Integer.parseInt(courseIDs[i]));
				selectedCourseMap.put(Integer.parseInt(courseIDs[i]), courseName);
				courseMap.remove(Integer.parseInt(courseIDs[i]));
			}
			
		}
		if(courseIdList!=null  && !courseIdList.isEmpty()){
			for (Integer courseId : courseIdList) {
				if(courseMap.containsKey(courseId)){
					courseMap.remove(courseId);
				}
			}
		}
		allotmentGroupWiseForm.setCourseMap(courseMap);
		allotmentGroupWiseForm.setSelectedCourseMap(selectedCourseMap);
		
	}
	/**
	 * @param allotmentGroupWiseForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public boolean updateExamRoomAllotGroupWise(ExamRoomAllotmentGroupWiseForm allotmentGroupWiseForm,String mode) throws Exception {
		IExamRoomAllotmentGroupWiseTransaction transaction=ExamRoomAllotmentGroupWiseTxnImpl.getInstance();
		List<ExamRoomAllotmentGroupWise> groupWiseList=transaction.getGroupWiseListByMidOrEndAndSchemeNo(allotmentGroupWiseForm.getMidOrEndSem(), Integer.parseInt(allotmentGroupWiseForm.getSchemeNo()));
		List<ExamRoomAllotmentGroupWise> allotmentGroupWiseList=ExamRoomAllotmentGroupWiseHelper.getInstance().updateConvertFormToBO(groupWiseList,allotmentGroupWiseForm,mode);
		return transaction.updateOrDeleteGroupWise(allotmentGroupWiseList);
	}
}
