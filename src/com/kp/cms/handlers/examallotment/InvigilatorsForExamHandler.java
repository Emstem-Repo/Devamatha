package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.forms.examallotment.InvigilatorsForExamForm;
import com.kp.cms.helpers.examallotment.InvigilatorsForExamHelper;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class InvigilatorsForExamHandler {
	private static volatile InvigilatorsForExamHandler invigilatorsForExamHandler=null;
	 IInvigilatorsForExamTrans transaction=InvigilatorsForExamTransImpl.getInstance();
	 InvigilatorsForExamHelper helper=InvigilatorsForExamHelper.getInstance();
	/**
	 * instance()
	 * @return
	 */
	 private InvigilatorsForExamHandler(){
		 
	 }
	public static InvigilatorsForExamHandler getInstance(){
		if(invigilatorsForExamHandler == null){
			invigilatorsForExamHandler=new InvigilatorsForExamHandler();
		}
		return invigilatorsForExamHandler;
	}


	public void getAllMaps(InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		//get academic year
		int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
		invigilatorsForExamForm.setAcademicYear(String.valueOf(academicYear));
		invigilatorsForExamForm.setTempAcademicYear(String.valueOf(academicYear));
		//exam map by academic year
		Map<Integer,String> examMap=transaction.getExam(invigilatorsForExamForm.getAcademicYear());
		if(examMap!=null && !examMap.isEmpty()){
			invigilatorsForExamForm.setExamMap(CommonUtil.sortMapByValue(examMap));
		}
		//deanary map
		Map<Integer,String> deanaryMap=transaction.geDeanaryMap();
		if(deanaryMap!=null && !deanaryMap.isEmpty()){
			invigilatorsForExamForm.setDeanaryMap(CommonUtil.sortMapByValue(deanaryMap));
		}
		//department map
		Map<Integer,String> deptMap=transaction.getDepartmentMap();
		if(deptMap!=null && !deptMap.isEmpty()){
			invigilatorsForExamForm.setDeptMap(CommonUtil.sortMapByValue(deptMap));
		}
		//work location map
		List<EmployeeWorkLocationBO> locationBo = BlockBoImpl.getInstance().getEmpLocation();
		Map<Integer,String> workLocationMap=null;
		if(locationBo!=null){
			workLocationMap=new HashMap<Integer, String>();
			Iterator<EmployeeWorkLocationBO> iterator=locationBo.iterator();
			while (iterator.hasNext()) {
				EmployeeWorkLocationBO employeeWorkLocationBO = (EmployeeWorkLocationBO) iterator.next();
				workLocationMap.put(employeeWorkLocationBO.getId(), employeeWorkLocationBO.getName());
			}
		}
		if(workLocationMap!=null && !workLocationMap.isEmpty()){
			invigilatorsForExamForm.setWorkLocationMap(CommonUtil.sortMapByValue(workLocationMap));
		}
		
	}


	public List<InvigilatorsForExamTo> getInvigilatorsList(
			InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		//the invigilators who are available
		StringBuilder query=helper.createQueryForSearch(invigilatorsForExamForm);
		List<ExamInvigilatorAvailable> list=transaction.getSearchedInvigilators(query);
		//teacher id as key id as value
		Map<Integer,Integer> userIds=helper.getUserIdsFromBosList(list);
		invigilatorsForExamForm.setExamInvigilatorsMap(userIds);
		//invigilators who are exempted
		List<Integer> list2=transaction.getExamInvigilatorsAvailableWhoAreExemted();
		StringBuilder query1=helper.createQuery(invigilatorsForExamForm);
		List<Users> users=transaction.getInvigilatorsList(query1);
		List<InvigilatorsForExamTo> list1=helper.convertBosToTos(users,userIds,list2);
		return list1;
	}


	public boolean addInvigilators(List<InvigilatorsForExamTo> list,
			InvigilatorsForExamForm invigilatorsForExamForm, List<Integer> theRecordsToSetIsActiveIsFalse, List<Integer> theRecordsToSetIsActiveIsTrue, List<Integer> therecordsExists) throws Exception{
		if(therecordsExists!=null && !therecordsExists.isEmpty()){
			List<ExamInvigilatorAvailable> examInvigilatorsList=transaction.getExamInvigilators(therecordsExists);
			Map<Integer,ExamInvigilatorAvailable> map=helper.convertBosToMap(examInvigilatorsList);
			List<ExamInvigilatorAvailable> examInvigilatorsToUpdate=helper.modifyBosToUpdate(theRecordsToSetIsActiveIsFalse,theRecordsToSetIsActiveIsTrue,map,invigilatorsForExamForm.getUserId());
			transaction.updateInvigilators(examInvigilatorsToUpdate);
		}
		List<ExamInvigilatorAvailable> examInvigilatorAvailables=helper.convertTosToBos(list,invigilatorsForExamForm.getExamId(),invigilatorsForExamForm.getLocationId(),invigilatorsForExamForm.getUserId());
		boolean flag=transaction.saveInvigilators(examInvigilatorAvailables);
		return flag;
	}


	public List<InvigilatorsForExamTo> searchTheInvigilators(
			InvigilatorsForExamForm invigilatorsForExamForm) throws Exception {
		StringBuilder query=helper.createQueryForSearch(invigilatorsForExamForm);
		List<ExamInvigilatorAvailable> list=transaction.getSearchedInvigilators(query);
		List<InvigilatorsForExamTo> invigilatorsForExamTos=helper.convertBosToToslidt(list);
		return invigilatorsForExamTos;
	}


	public boolean updateInvigilators(List<Integer> list, String string) throws Exception{
		List<ExamInvigilatorAvailable> examInvigilatorAvailables=transaction.getExamInvigilators(list);
		List<ExamInvigilatorAvailable> examInvigilatorsList=helper.convertBosToBos(examInvigilatorAvailables,string);
		boolean flag=transaction.updateInvigilators(examInvigilatorsList);
		return flag;
	}
}
