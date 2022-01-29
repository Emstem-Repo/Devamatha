package com.kp.cms.handlers.examallotment;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;


import com.kp.cms.actions.employee.EmployeeInfoEditAction;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.examallotment.ExamInvigilationDutySettings;
import com.kp.cms.bo.examallotment.ExamInvigilatorAvailable;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExamInviligatorExemptionDatewise;
import com.kp.cms.forms.employee.EmployeeInfoFormNew;
import com.kp.cms.forms.examallotment.ExamInviligatorAllotmentForm;
import com.kp.cms.helpers.exam.DownloadHallTicketHelper;
import com.kp.cms.helpers.examallotment.ExamInviligatorAllotmentHelper;
import com.kp.cms.to.exam.MarksCardTO;
import com.kp.cms.to.examallotment.InvigilatorsDatewiseExemptionTO;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;
import com.kp.cms.transactions.employee.IEmployeeInfoNewTransaction;
import com.kp.cms.transactions.examallotment.IExamInviligatorAllotmentTransaction;
import com.kp.cms.transactionsimpl.employee.EmployeeInfoNewTransactionImpl;
import com.kp.cms.transactionsimpl.examallotment.ExamInviligatorAllotmentImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class ExamInviligatorAllotmentHandler
{
private static volatile ExamInviligatorAllotmentHandler examInviligatorAllotmentHandler = null;
IExamInviligatorAllotmentTransaction txn=ExamInviligatorAllotmentImpl.getInstance();
ExamInviligatorAllotmentHelper helper=ExamInviligatorAllotmentHelper.getInstance();
private ExamInviligatorAllotmentHandler() {
	
}
/**
 * return singleton object of AdminHallTicketHandler.
 * @return
 */
public static ExamInviligatorAllotmentHandler getInstance() {
	if (examInviligatorAllotmentHandler == null) {
		examInviligatorAllotmentHandler = new ExamInviligatorAllotmentHandler();
	}
	return examInviligatorAllotmentHandler;
}

public void getInitialData(ExamInviligatorAllotmentForm InvForm)throws Exception {
	int academicYear = CurrentAcademicYear.getInstance().getCurrentAcademicYearforTeacher();
	InvForm.setAcademicYear(String.valueOf(academicYear));
	InvForm.setTempyear(String.valueOf(academicYear));
	 
	 Map<String,String> examMap=txn.getExamMap(InvForm);
	 if(examMap!=null){
		 InvForm.setExamMap(examMap);
	 }
	 Map<String,String> workLocationMap=txn.getWorkLocationMap();
	 if(workLocationMap!=null){
		 InvForm.setWorkLocationMap(workLocationMap);
	 }
}

public boolean getTeachersAllotment(ExamInviligatorAllotmentForm InvForm, ActionErrors errors)throws Exception {
	StringBuffer amSessionQuery=helper.SessionQuery(InvForm);
	boolean isUpdated=false;
	String examType=txn.getExamType(InvForm);
	InvForm.setExamType(examType);
	
	ExamInvigilationDutySettings settingsList=txn.getSettingsList(InvForm);
	List<Object[]> SessionList=txn.getSessionList(InvForm,amSessionQuery);
	if(SessionList!=null && !SessionList.isEmpty()){
		Iterator<Object[]> itr = SessionList.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			if(obj[0]!=null){
				
				InvForm.setAllotmentDate(obj[0].toString());
			}
			if(obj[1]!=null && obj[1].toString() != null){
				InvForm.setExamSessionId(Integer.parseInt(obj[1].toString()));
			}
			if(obj[2]!=null && obj[2].toString() != null){
				InvForm.setOrderNo(Integer.parseInt(obj[2].toString()));
			}
			if(obj[3]!=null && obj[3].toString() != null){
				InvForm.setSessionDescription(obj[3].toString());
			}
			txn.removeTeacherAllotment(InvForm);
			String teacherQuery=helper.TeacherQuery(InvForm);
			String reliverQuery=helper.ReliverQuery(InvForm);
			
			
			String studentQuery=null;
			if(InvForm.getExamSessionId()!=0){
				/*if(InvForm.getAllotmentSession().equalsIgnoreCase("AM")){
					 studentQuery=helper.RoomStudentQueryAm(InvForm);
				}
				else
				{
					 studentQuery=helper.roomStudentQueryPm(InvForm);
				}*/
				studentQuery = helper.roomStudentQuery(InvForm);
			}
			List<Object[]> studentList=txn.getStudentList(InvForm,studentQuery);
			
			if((studentList!=null && !studentList.isEmpty()) && settingsList!=null && examType!=null){
				List<Object[]> teacherList=txn.getTeacherList(InvForm,teacherQuery);
				if(teacherList!=null && !teacherList.isEmpty()){
					List<ExamInviligatorDuties> InvDutyList=helper.setInvigilatorDuty(InvForm,teacherList,studentList,settingsList, examType, errors);
					if(errors==null || errors.isEmpty()){
						isUpdated=txn.update(InvDutyList);
					}else
					{
						txn.removeTeacherAllotmentRollback(InvForm);
					}
				}
				List<Object[]> ReliverList=txn.getTeacherList(InvForm,reliverQuery);
				if(ReliverList!=null && !ReliverList.isEmpty()){
					List<ExamInviligatorDuties> ReliverDutyList=helper.setReliverDuty(InvForm,ReliverList,studentList,settingsList, examType, errors);
					if(errors==null || errors.isEmpty()){
						isUpdated=txn.update(ReliverDutyList);
					}else
					{
						txn.removeTeacherAllotmentRollback(InvForm);
					}
				}
			}
		}
	}
	return isUpdated;
	}
	public List<InvigilatorsForExamTo> getAvailableTeachersList(
			ExamInviligatorAllotmentForm InvForm, ActionErrors errors) throws Exception {
		StringBuilder query=helper.createQueryForSearch(InvForm);
		List<ExamInvigilatorAvailable> availableList=txn.setAvailableTeacher(query);
		List<InvigilatorsForExamTo> invigilatorsForExamTos=helper.convertBosToToslidt(availableList);
		
		return invigilatorsForExamTos;
	}

	public List<InvigilatorsDatewiseExemptionTO> getExemptedTeachersList(
			ExamInviligatorAllotmentForm InvForm, ActionErrors errors) throws Exception {
		StringBuilder query=helper.createQueryForSearchExempted(InvForm);
		List<ExamInviligatorExemptionDatewise> exemptedList=txn.setExemptedTeacher(query);
		List<InvigilatorsDatewiseExemptionTO> invigilatorsForExamTos=helper.convertBosToTosList(exemptedList);
		return invigilatorsForExamTos;
	}
	
}