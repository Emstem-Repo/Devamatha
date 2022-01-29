package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilatorDutyExemption;
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyExemptionForm;
import com.kp.cms.helpers.examallotment.ExamInvigilatorDutyExemptionHelper;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyExemptionTo;
import com.kp.cms.transactions.examallotment.IExamInvigilatorDutyExemptionTransaction;
import com.kp.cms.transactionsimpl.examallotment.ExamInvigilatorDutyExemptionTransImpl;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CommonUtil;


public class ExamInvigilatorDutyExemptionHandler {
	InvigilatorsForExamTransImpl invigilatorsForExamTransImpl=InvigilatorsForExamTransImpl.getInstance();
	IExamInvigilatorDutyExemptionTransaction examInvigilatorDutyExemptionTransImpl=ExamInvigilatorDutyExemptionTransImpl.getInstance();
	ExamInvigilatorDutyExemptionHelper examInvigilatorDutyExemptionHelper=ExamInvigilatorDutyExemptionHelper.getInstance();
	private static volatile ExamInvigilatorDutyExemptionHandler examInvigilatorDutyExemptionHandler=null;
	/**
	 * instance()
	 * @return
	 */
	public static ExamInvigilatorDutyExemptionHandler getInstance(){
		if(examInvigilatorDutyExemptionHandler == null){
			examInvigilatorDutyExemptionHandler=new ExamInvigilatorDutyExemptionHandler();
		}
		return examInvigilatorDutyExemptionHandler;
	}
	
	public void getAllMaps(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
			//deanary map
			Map<Integer,String> deanaryMap=invigilatorsForExamTransImpl.geDeanaryMap();
			if(deanaryMap!=null && !deanaryMap.isEmpty()){
				examInvigilatorDutyExemptionForm.setDeanaryMap(CommonUtil.sortMapByValue(deanaryMap));
			}
			//department map
			Map<Integer,String> deptMap=invigilatorsForExamTransImpl.getDepartmentMap();
			if(deptMap!=null && !deptMap.isEmpty()){
				examInvigilatorDutyExemptionForm.setDeptMap(CommonUtil.sortMapByValue(deptMap));
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
				examInvigilatorDutyExemptionForm.setWorkLocationMap(CommonUtil.sortMapByValue(workLocationMap));
			}
			//ExemptionMap
			Map<Integer,String> exemptionMap=examInvigilatorDutyExemptionTransImpl.getExemptionMap();
			if(exemptionMap!=null && !exemptionMap.isEmpty()){
				examInvigilatorDutyExemptionForm.setExemptionMap(CommonUtil.sortMapByValue(exemptionMap));
			}
		}

	public List<ExamInvigilatorDutyExemptionTo> getInvigilatorsList(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
		//the invigilators who are available
		StringBuilder query=examInvigilatorDutyExemptionHelper.createQueryForSearch(examInvigilatorDutyExemptionForm);
		List<ExamInvigilatorDutyExemption> list=examInvigilatorDutyExemptionTransImpl.getSearchedInvigilators(query);
		//teacher id as key id as value
		Map<Integer,Integer> userIds=examInvigilatorDutyExemptionHelper.getUserIdsFromBosList(list);
		examInvigilatorDutyExemptionForm.setExamInvigilatorsDutyExemptionMap(userIds);
		StringBuilder query1=examInvigilatorDutyExemptionHelper.createQuery(examInvigilatorDutyExemptionForm);
		List<Users> users=InvigilatorsForExamTransImpl.getInstance().getInvigilatorsList(query1);
		List<ExamInvigilatorDutyExemptionTo> list1=examInvigilatorDutyExemptionHelper.convertBosToTos(users,userIds);
		return list1;
	}

	public boolean addInvigilators(List<ExamInvigilatorDutyExemptionTo> list,
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm,
			List<Integer> theRecordsToSetIsActiveIsFalse,
			List<Integer> theRecordsToSetIsActiveIsTrue,
			List<Integer> therecordsExists) throws Exception{
		if(therecordsExists!=null && !therecordsExists.isEmpty()){
			List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions=examInvigilatorDutyExemptionTransImpl.getExamInvigilators(therecordsExists);
			Map<Integer,ExamInvigilatorDutyExemption> map=examInvigilatorDutyExemptionHelper.convertBosToMap(examInvigilatorDutyExemptions);
			List<ExamInvigilatorDutyExemption> examInvigilatorsToUpdate=examInvigilatorDutyExemptionHelper.modifyBosToUpdate(theRecordsToSetIsActiveIsFalse,theRecordsToSetIsActiveIsTrue,map,examInvigilatorDutyExemptionForm.getUserId());
			examInvigilatorDutyExemptionTransImpl.updateInvigilators(examInvigilatorsToUpdate);
		}
		List<ExamInvigilatorDutyExemption> examInvigilatorDutyExemptions1=examInvigilatorDutyExemptionHelper.convertTosToBos(list,examInvigilatorDutyExemptionForm.getExemptionId(),examInvigilatorDutyExemptionForm.getLocationId(),examInvigilatorDutyExemptionForm.getUserId());
		boolean flag=examInvigilatorDutyExemptionTransImpl.saveInvigilators(examInvigilatorDutyExemptions1);
		return flag;
	}
}
