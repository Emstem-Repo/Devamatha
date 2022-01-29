package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamValuationScheduleDetails;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ValuationScheduleForm;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.exam.ValuationScheduleTO;
import com.kp.cms.utilities.CommonUtil;

public class ValuationScheduleHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile ValuationScheduleHelper newSecuredMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(ValuationScheduleHelper.class);
	private ValuationScheduleHelper() {
		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ValuationScheduleHelper getInstance() {
		if (newSecuredMarksEntryHelper == null) {
			newSecuredMarksEntryHelper = new ValuationScheduleHelper();
		}
		return newSecuredMarksEntryHelper;
	}
	/**
	 * @param ValuationScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationScheduleDetails> convertFormTOBO(ValuationScheduleForm valuationScheduleForm) throws Exception{
		List<ExamValuationScheduleDetails> examBOList = new ArrayList<ExamValuationScheduleDetails>();
		String[] allValuators=valuationScheduleForm.getSelectedEmployeeId();
		String[] allExternal=valuationScheduleForm.getSelectedExternalId();
	// Modified By Manu.This For valuators.
		if(allValuators!=null && allValuators.length>0){
		for(int i=0;i<allValuators.length;i++){
		ExamValuationScheduleDetails examBO = new ExamValuationScheduleDetails();
		if(valuationScheduleForm.getId() != 0){
			examBO.setId(valuationScheduleForm.getId());
		}
		ExamDefinition definitionBO = new ExamDefinition();
		definitionBO.setId(Integer.parseInt(valuationScheduleForm.getExamId()));
		examBO.setExam(definitionBO);
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(valuationScheduleForm.getSubjectId()));
		examBO.setSubject(subject);
		
		Users employee = new Users();
		employee.setId(Integer.parseInt(allValuators[i]));
		examBO.setUsers(employee);
			
			if(valuationScheduleForm.getDate() != null && !valuationScheduleForm.getDate().isEmpty())
			{
			examBO.setBoardValuationDate(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getDate()));
			}
			if(valuationScheduleForm.getValuationFrom() != null && !valuationScheduleForm.getValuationFrom().isEmpty())
			{
				examBO.setValuationFrom(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationFrom()));
			}
			if(valuationScheduleForm.getValuationTo() != null && !valuationScheduleForm.getValuationTo().isEmpty())
			{
				examBO.setValuationTo(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationTo()));
			}
		examBO.setCreatedBy(valuationScheduleForm.getUserId());
		examBO.setCreatedDate(new Date());
		examBO.setLastModifiedDate(new Date());
		examBO.setModifiedBy(valuationScheduleForm.getUserId());
		examBO.setIsActive(true);
		examBO.setIsValuator(valuationScheduleForm.getIsReviewer());
		examBOList.add(examBO);
	     }
	}
		// Modified By Manu.This For Externals.
		if(allExternal!=null && allExternal.length>0){
		for(int j=0;j<allExternal.length;j++){
			ExamValuationScheduleDetails examBO = new ExamValuationScheduleDetails();
			if(valuationScheduleForm.getId() != 0){
				examBO.setId(valuationScheduleForm.getId());
			}
			ExamDefinition definitionBO = new ExamDefinition();
			definitionBO.setId(Integer.parseInt(valuationScheduleForm.getExamId()));
			examBO.setExam(definitionBO);
			Subject subject = new Subject();
			subject.setId(Integer.parseInt(valuationScheduleForm.getSubjectId()));
			examBO.setSubject(subject);
			
				ExamValuators valuators = new ExamValuators();
				valuators.setId(Integer.parseInt(allExternal[j]));
				valuators.setCreatedBy(valuationScheduleForm.getUserId());
				valuators.setModifiedBy(valuationScheduleForm.getUserId());
				valuators.setCreatedDate(new Date());
				valuators.setLastModifiedDate(new Date());
				valuators.setIsActive(true);
				examBO.setExamValuators(valuators);
			
				
				if(valuationScheduleForm.getDate() != null && !valuationScheduleForm.getDate().isEmpty())
				{
				examBO.setBoardValuationDate(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getDate()));
				}
				if(valuationScheduleForm.getValuationFrom() != null && !valuationScheduleForm.getValuationFrom().isEmpty())
				{
					examBO.setValuationFrom(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationFrom()));
				}
				if(valuationScheduleForm.getValuationTo() != null && !valuationScheduleForm.getValuationTo().isEmpty())
				{
					examBO.setValuationTo(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationTo()));
				}
			examBO.setCreatedBy(valuationScheduleForm.getUserId());
			examBO.setCreatedDate(new Date());
			examBO.setLastModifiedDate(new Date());
			examBO.setModifiedBy(valuationScheduleForm.getUserId());
			examBO.setIsActive(true);
			examBO.setIsValuator(valuationScheduleForm.getIsReviewer());
			examBOList.add(examBO);
		     }
	}
		return examBOList;
	}
	
	/**
	 * @param listBO
	 * @param string
	 * @return
	 */
	public ArrayList<KeyValueTO> convertBOToTOSubjectMap(ArrayList<SubjectUtilBO> listBO, String sCodeName) throws Exception{
		ArrayList<KeyValueTO> listOfValues = new ArrayList<KeyValueTO>();

		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object row[] = (Object[]) itr.next();
			if (sCodeName.equalsIgnoreCase("sCode")) {
				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[2].toString() + " "
						+ row[1].toString()));
			} else {

				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[1].toString() + " "
						+ row[2].toString()));
			}
		}

		return listOfValues;
	}
	
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 */
	public List<ValuationScheduleTO> convertBOToTO(List<ExamValuationScheduleDetails> boList) throws Exception{
		List<ValuationScheduleTO> toList = new ArrayList<ValuationScheduleTO>();
		if(boList !=null){
			Iterator<ExamValuationScheduleDetails> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ExamValuationScheduleDetails scheduleDetails = (ExamValuationScheduleDetails) iterator.next();
				ValuationScheduleTO to = new ValuationScheduleTO();
				if(scheduleDetails.getExam() != null && scheduleDetails.getExam().getName() != null){
					to.setExamName(scheduleDetails.getExam().getName());
					to.setExamId(scheduleDetails.getExam().getId());
				}
				to.setId(scheduleDetails.getId());
				if(scheduleDetails.getUsers() != null && scheduleDetails.getUsers().getEmployee() != null && scheduleDetails.getUsers().getEmployee().getFirstName() != null){
					to.setEmployeeName(scheduleDetails.getUsers().getEmployee().getFirstName().toUpperCase());
				}else if(scheduleDetails.getUsers() != null && scheduleDetails.getUsers().getGuest() != null && scheduleDetails.getUsers().getGuest().getFirstName() != null){
					to.setEmployeeName(scheduleDetails.getUsers().getGuest().getFirstName().toUpperCase());
				}else if(scheduleDetails.getUsers() != null && scheduleDetails.getUsers().getUserName() != null){
					to.setEmployeeName(scheduleDetails.getUsers().getUserName().toUpperCase());
				}
				if(scheduleDetails.getExamValuators() != null && scheduleDetails.getExamValuators().getName() != null){
					to.setEmployeeName(scheduleDetails.getExamValuators().getName().toUpperCase());
				}
				if(scheduleDetails.getSubject() != null && scheduleDetails.getSubject().getName() != null){
					to.setSubjectName(scheduleDetails.getSubject().getName()+"("+scheduleDetails.getSubject().getCode()+")");
				}
				if(scheduleDetails.getBoardValuationDate()!=null){
					to.setBoardValuationDate(CommonUtil.formatDates(scheduleDetails.getBoardValuationDate()));
				}
				if(scheduleDetails.getValuationFrom()!=null){
					to.setValuationFrom(CommonUtil.formatDates(scheduleDetails.getValuationFrom()));
				}
				if(scheduleDetails.getValuationTo()!=null){
					to.setValuationTo(CommonUtil.formatDates(scheduleDetails.getValuationTo()));
				}
				to.setValuator(scheduleDetails.getIsValuator());
				
				toList.add(to);
			}
		}
		return toList;
	}
	
	/**
	 * @param valuationScheduleForm
	 * @param ExamValuationScheduleDetails
	 * @throws Exception
	 */
	public List<ValuationScheduleTO> pupulateValuationScheduleBOtoTO(List<ExamValuationScheduleDetails> sheduleList)
	throws Exception {
		log.info("Entering into pupulateValuationScheduleBOtoTO of ValuationScheduleHelper");
		ValuationScheduleTO scheduleTO = null;

		List<ValuationScheduleTO> newSchedList = new ArrayList<ValuationScheduleTO>();
		if (sheduleList != null && !sheduleList.isEmpty()) {
			Iterator<ExamValuationScheduleDetails> iterator = sheduleList.iterator();
			while (iterator.hasNext()) {
				ExamValuationScheduleDetails scheduleDetails = iterator.next();
				scheduleTO = new ValuationScheduleTO();
				if (scheduleDetails.getId() != 0) {
					scheduleTO.setId(scheduleDetails.getId());

					scheduleTO.setExamName(scheduleDetails.getExam().getName());
					scheduleTO.setSubjectName(scheduleDetails.getSubject().getName());
					scheduleTO.setValuator(scheduleDetails.getIsValuator());
					
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getBoardValuationDate()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
					scheduleTO.setBoardValuationDate(date);
					String date1 = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getValuationFrom()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
					scheduleTO.setValuationFrom(date1);
					String date2 = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getValuationTo()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
					scheduleTO.setValuationTo(date2);
					
					if(scheduleDetails.getExamValuators() != null){
						scheduleTO.setEmployeeName(scheduleDetails.getExamValuators().getName());
					}
					else if(scheduleDetails.getUsers().getEmployee() != null){
						scheduleTO.setEmployeeName(scheduleDetails.getUsers().getEmployee().getFirstName());
					}else if(scheduleDetails.getUsers().getGuest() != null){
						scheduleTO.setEmployeeName(scheduleDetails.getUsers().getGuest().getFirstName());
					}else{
						scheduleTO.setEmployeeName(scheduleDetails.getUsers().getUserName());
					}
					newSchedList.add(scheduleTO);
				}
			}
		}
		log.info("Leaving from pupulateExamValuatorsBOtoTO of ValuationScheduleHelper");
		return newSchedList;
	}
	
	
	public void setBotoForm(ValuationScheduleForm scheduleForm,ExamValuationScheduleDetails scheduleDetails)throws Exception{
		if(scheduleForm!=null){
			if (scheduleDetails.getBoardValuationDate() != null) {
				String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getBoardValuationDate()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
				scheduleForm.setDate(date);
				}
			if(scheduleDetails.getValuationFrom() != null){
				String date1 = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getValuationFrom()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
				scheduleForm.setValuationFrom(date1);
			}

			if(scheduleDetails.getValuationTo() != null){
				String date2 = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(scheduleDetails.getValuationTo()), ValuationScheduleHelper.SQL_DATEFORMAT,	ValuationScheduleHelper.FROM_DATEFORMAT);
				scheduleForm.setValuationTo(date2);
			}
			
			scheduleForm.setId(scheduleDetails.getId());
			scheduleForm.setAcademicYear(String.valueOf(scheduleDetails.getExam().getAcademicYear()));			
			scheduleForm.setExamType(scheduleDetails.getExam().getExamType().getName());
			scheduleForm.setExamId(String.valueOf(scheduleDetails.getExam().getId()));
			scheduleForm.setSubjectId(String.valueOf(scheduleDetails.getSubject().getId()));
			scheduleForm.setIsReviewer(scheduleDetails.getIsValuator());
			
			if(scheduleDetails.getExamValuators() != null){
				scheduleForm.setExternalEmployeeId(String.valueOf(scheduleDetails.getExamValuators().getId()));
				String[] extValue=new String[1];
				extValue[0]=String.valueOf(scheduleDetails.getExamValuators().getId());
				scheduleForm.setSelectedExternalId(extValue);
			}
			else{
				scheduleForm.setEmployeeId(String.valueOf(scheduleDetails.getUsers().getId()));
				String[] empValue=new String[1];
				empValue[0]=String.valueOf(String.valueOf(scheduleDetails.getUsers().getId()));
				scheduleForm.setSelectedEmployeeId(empValue);
			}
		}
	}
	
	/**
	 * @param ValuationScheduleForm
	 * @return
	 * @throws Exception
	 */
	public List<ExamValuationScheduleDetails> convertFormTOBOUpdate(ValuationScheduleForm valuationScheduleForm) throws Exception{
		
		List<ExamValuationScheduleDetails> examBOList = new ArrayList<ExamValuationScheduleDetails>();
		String[] allValuators=valuationScheduleForm.getSelectedEmployeeId();
		String[] allExternal=valuationScheduleForm.getSelectedExternalId();
		if(allValuators!=null && allValuators.length>0){
		for(int i=0;i<allValuators.length;i++){
		ExamValuationScheduleDetails examBO = new ExamValuationScheduleDetails();
		if(valuationScheduleForm.getId() != 0){
			examBO.setId(valuationScheduleForm.getId());
		}
		ExamDefinition definitionBO = new ExamDefinition();
		definitionBO.setId(Integer.parseInt(valuationScheduleForm.getExamId()));
		examBO.setExam(definitionBO);
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(valuationScheduleForm.getSubjectId()));
		examBO.setSubject(subject);
        Users employee = new Users();
		employee.setId(Integer.parseInt(allValuators[i]));
		examBO.setUsers(employee);
			
			if(valuationScheduleForm.getDate() != null && !valuationScheduleForm.getDate().isEmpty())
			{
			examBO.setBoardValuationDate(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getDate()));
			}
			if(valuationScheduleForm.getValuationFrom() != null && !valuationScheduleForm.getValuationFrom().isEmpty())
			{
				examBO.setValuationFrom(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationFrom()));
			}
			if(valuationScheduleForm.getValuationTo() != null && !valuationScheduleForm.getValuationTo().isEmpty())
			{
				examBO.setValuationTo(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationTo()));
			}
		examBO.setLastModifiedDate(new Date());
		examBO.setModifiedBy(valuationScheduleForm.getUserId());
		examBO.setIsActive(true);
		examBO.setIsValuator(valuationScheduleForm.getIsReviewer());
		examBOList.add(examBO);
		}
	}
		if(allExternal!=null && allExternal.length>0){
		for(int i=0;i<allExternal.length;i++){
			ExamValuationScheduleDetails examBO = new ExamValuationScheduleDetails();
			if(valuationScheduleForm.getId() != 0){
				examBO.setId(valuationScheduleForm.getId());
			}
			ExamDefinition definitionBO = new ExamDefinition();
			definitionBO.setId(Integer.parseInt(valuationScheduleForm.getExamId()));
			examBO.setExam(definitionBO);
			Subject subject = new Subject();
			subject.setId(Integer.parseInt(valuationScheduleForm.getSubjectId()));
			examBO.setSubject(subject);
	       
				ExamValuators valuators = new ExamValuators();
				valuators.setId(Integer.parseInt(allExternal[i]));
				examBO.setExamValuators(valuators);
				
				if(valuationScheduleForm.getDate() != null && !valuationScheduleForm.getDate().isEmpty())
				{
				examBO.setBoardValuationDate(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getDate()));
				}
				if(valuationScheduleForm.getValuationFrom() != null && !valuationScheduleForm.getValuationFrom().isEmpty())
				{
					examBO.setValuationFrom(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationFrom()));
				}
				if(valuationScheduleForm.getValuationTo() != null && !valuationScheduleForm.getValuationTo().isEmpty())
				{
					examBO.setValuationTo(CommonUtil.ConvertStringToSQLDate(valuationScheduleForm.getValuationTo()));
				}
			examBO.setLastModifiedDate(new Date());
			examBO.setModifiedBy(valuationScheduleForm.getUserId());
			examBO.setIsActive(true);
			examBO.setIsValuator(valuationScheduleForm.getIsReviewer());
			examBOList.add(examBO);
			}
	}
		return examBOList;
	   
	}

}
