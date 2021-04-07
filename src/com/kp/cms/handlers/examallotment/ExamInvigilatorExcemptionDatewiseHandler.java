package com.kp.cms.handlers.examallotment;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.forms.admin.DepartmentEntryForm;
import com.kp.cms.forms.examallotment.ExamInvigilatorExcemptionDatewiseForm;
import com.kp.cms.helpers.examallotment.ExamInvigilatorExcemptionDatewiseHelper;
import com.kp.cms.to.examallotment.ExamInvigilatorExcemptionDatewiseTO;
import com.kp.cms.transactions.admin.IDepartmentEntryTransaction;
import com.kp.cms.transactions.examallotment.IExamInvigilatorExemptionDatewise;
import com.kp.cms.transactionsimpl.admin.DepartmentEntryTransactionImpl;
import com.kp.cms.transactionsimpl.examallotment.ExamInvigilatorExemptionDatewiseImpl;
import com.kp.cms.transactionsimpl.studentfeedback.BlockBoImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExamInvigilatorExcemptionDatewiseHandler {
	private static volatile ExamInvigilatorExcemptionDatewiseHandler handler=null;
	IExamInvigilatorExemptionDatewise transaction=ExamInvigilatorExemptionDatewiseImpl.getInstance();
	ExamInvigilatorExcemptionDatewiseHelper helper=ExamInvigilatorExcemptionDatewiseHelper.getInstance();
	/**
	 * instance()
	 * @return
	 */
	public static ExamInvigilatorExcemptionDatewiseHandler getInstance(){
		if(handler == null){
			handler=new ExamInvigilatorExcemptionDatewiseHandler();
		}
		return handler;
	}


	@SuppressWarnings("unchecked")
	public void getAllMaps(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
		//get academic year
		int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
		invigilatorsForExamForm.setAcademicYear(String.valueOf(academicYear));
		invigilatorsForExamForm.setTempAcademicYear(String.valueOf(academicYear));
		//exam map by academic year
		Map<Integer,String> sessionMap=transaction.getsessionMap();
		if(sessionMap!=null && !sessionMap.isEmpty()){
			invigilatorsForExamForm.setSessionMap(sessionMap);
		}
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
	public List<ExamInvigilatorExcemptionDatewiseTO> getInvListAlreadyExemptedEdit(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
		StringBuffer query=helper.createQueryAlreadyExemptedEdit(invigilatorsForExamForm);
		List<ExamInviligatorExemptionDatewise> users=transaction.getInvListAlreadyExempted(query.toString());
		List<ExamInvigilatorExcemptionDatewiseTO> list=helper.convertBosToTosExempted(users);
		return list;
	}

	public Map<Integer,ExamInviligatorExemptionDatewise> getInvListAlreadyExempted(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm, Date dt) throws Exception{
		StringBuffer query=helper.createQueryAlreadyExempted(invigilatorsForExamForm, dt);
		List<ExamInviligatorExemptionDatewise> users=transaction.getInvListAlreadyExempted(query.toString());
		Map<Integer,ExamInviligatorExemptionDatewise> map=helper.convertBosToMap(users);
		return map;
	}
	public List<ExamInvigilatorExcemptionDatewiseTO> getInvigilatorsList(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception{
		StringBuffer query=helper.createQuery(invigilatorsForExamForm);
		List<Users> users=transaction.getInvigilatorsList(query.toString());
		List<Integer> list2=transaction.getExamInvigilatorsAvailableWhoAreExemted();
		List<ExamInvigilatorExcemptionDatewiseTO> list=helper.convertBosToTos(users,list2,invigilatorsForExamForm);
		return list;
	}
	public boolean addInvigilators(List<ExamInvigilatorExcemptionDatewiseTO> list,
		ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm, Date examDate, Map<Integer,ExamInviligatorExemptionDatewise> listOfTeachersExemptedAlready) throws Exception{
		boolean flag=false;		
		List<ExamInviligatorExemptionDatewise> invigilatorsForExamBos=helper.convertTosToBos(listOfTeachersExemptedAlready,examDate, list,invigilatorsForExamForm.getExamId(),invigilatorsForExamForm.getLocationId(),invigilatorsForExamForm.getUserId());
		if(invigilatorsForExamBos!=null && !invigilatorsForExamBos.isEmpty()){
			 flag=transaction.saveInvigilators(invigilatorsForExamBos);
		}
		return flag;
	}
	public List<ExamInvigilatorExcemptionDatewiseTO> searchTheInvigilators(ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception 
		{
		StringBuilder query=helper.createQueryForSearch(invigilatorsForExamForm);
		List<ExamInviligatorExemptionDatewise> list=transaction.getSearchedInvigilators(query.toString());
		List<ExamInvigilatorExcemptionDatewiseTO> invigilatorsForExamTos=helper.convertBosToToslidt(list);
		return invigilatorsForExamTos;
	}
	public boolean updateInvigilators(List<Integer> list) throws Exception{
		boolean flag=transaction.updateInvigilators(list);
		return flag;
	}
	
	public boolean deleteExmptEntry(int idExempted, boolean activate, ExamInvigilatorExcemptionDatewiseForm invigilatorsForExamForm) throws Exception {
		boolean isDeleted=false;
		isDeleted=transaction.deleteExemptedEntry(idExempted,activate,invigilatorsForExamForm);
		return isDeleted;
	}
}
