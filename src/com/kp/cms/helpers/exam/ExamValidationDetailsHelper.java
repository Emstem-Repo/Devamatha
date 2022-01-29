package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.ExamValuationAnswerScript;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamValidationDetailsForm;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;

public class ExamValidationDetailsHelper {
	/**
	 * Singleton object of NewSecuredMarksEntryHelper
	 */
	private static volatile ExamValidationDetailsHelper newSecuredMarksEntryHelper = null;
	private static final Log log = LogFactory.getLog(ExamValidationDetailsHelper.class);
	private ExamValidationDetailsHelper() {
		
	}
	
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	/**
	 * return singleton object of newSecuredMarksEntryHandler.
	 * @return
	 */
	public static ExamValidationDetailsHelper getInstance() {
		if (newSecuredMarksEntryHelper == null) {
			newSecuredMarksEntryHelper = new ExamValidationDetailsHelper();
		}
		return newSecuredMarksEntryHelper;
	}
	/**
	 * @param examValidationDetailsForm
	 * @return
	 * @throws Exception
	 */
	public ExamValidationDetails convertFormTOBO(ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		ExamValidationDetails examBO = new ExamValidationDetails();
		if(examValidationDetailsForm.getId() != 0){
			examBO.setId(examValidationDetailsForm.getId());
		}
		ExamDefinition definitionBO = new ExamDefinition();
		definitionBO.setId(Integer.parseInt(examValidationDetailsForm.getExamId()));
		examBO.setExam(definitionBO);
		Subject subject = new Subject();
		subject.setId(Integer.parseInt(examValidationDetailsForm.getSubjectId()));
		examBO.setSubject(subject);
		if(examValidationDetailsForm.getEmployeeId() != null && examValidationDetailsForm.getEmployeeId().equalsIgnoreCase("Other")){
			if(examValidationDetailsForm.getOtherEmpId() != null && examValidationDetailsForm.getOtherEmpId().equalsIgnoreCase("Other")){
				ExamValuators valuators = new ExamValuators();
				valuators.setName(examValidationDetailsForm.getOtherEmployee());
				valuators.setCreatedBy(examValidationDetailsForm.getUserId());
				valuators.setModifiedBy(examValidationDetailsForm.getUserId());
				valuators.setCreatedDate(new Date());
				valuators.setLastModifiedDate(new Date());
				valuators.setIsActive(true);
				examBO.setExamValuators(valuators);
			}else{
				ExamValuators valuators = new ExamValuators();
				valuators.setId(Integer.parseInt(examValidationDetailsForm.getOtherEmpId()));
				examBO.setExamValuators(valuators);
			}
		}else{
			Users employee = new Users();
			employee.setId(Integer.parseInt(examValidationDetailsForm.getEmployeeId()));
			examBO.setUsers(employee);
		}
		examBO.setCreatedBy(examValidationDetailsForm.getUserId());
		examBO.setCreatedDate(new Date());
		examBO.setLastModifiedDate(new Date());
		examBO.setModifiedBy(examValidationDetailsForm.getUserId());
//		examBO.setDate(CommonUtil.ConvertStringToSQLDate(examValidationDetailsForm.getDate()));
		examBO.setIsActive(true);
		examBO.setIsValuator(examValidationDetailsForm.getIsReviewer());
		Set<ExamValuationAnswerScript> answerScripts = new HashSet<ExamValuationAnswerScript>();
		ExamValuationAnswerScript bo = new ExamValuationAnswerScript();
		bo.setCreatedBy(examValidationDetailsForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setIsActive(true);
		bo.setIssueDate(CommonUtil.ConvertStringToSQLDate(examValidationDetailsForm.getDate()));
		bo.setLastModifiedDate(new Date());
		bo.setModifiedBy(examValidationDetailsForm.getUserId());
		bo.setNumberOfAnswerScripts(Integer.parseInt(examValidationDetailsForm.getAnswers()));
		bo.setChallanGenerated(false);
		answerScripts.add(bo);
		examBO.setAnswerScripts(answerScripts);
		return examBO;
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
	public List<ExamValidationDetailsTO> convertBOToTO(List<ExamValidationDetails> boList) throws Exception{
		List<ExamValidationDetailsTO> toList = new ArrayList<ExamValidationDetailsTO>();
		if(boList !=null){
			Iterator<ExamValidationDetails> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
				ExamValidationDetailsTO to = new ExamValidationDetailsTO();
				if(examValidationDetails.getExam() != null && examValidationDetails.getExam().getName() != null){
					to.setExamName(examValidationDetails.getExam().getName());
				}
				to.setId(examValidationDetails.getId());
				if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getEmployee() != null && examValidationDetails.getUsers().getEmployee().getFirstName() != null){
					to.setEmployeeName(examValidationDetails.getUsers().getEmployee().getFirstName());
				}else if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getUserName() != null){
					to.setEmployeeName(examValidationDetails.getUsers().getUserName());
				}
				if(examValidationDetails.getExamValuators() != null && examValidationDetails.getExamValuators().getName() != null){
					to.setEmployeeName(examValidationDetails.getExamValuators().getName());
				}
				if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getName() != null){
					to.setSubjectName(examValidationDetails.getSubject().getName()+"("+examValidationDetails.getSubject().getCode()+")");
				}
				
				if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Project Major")){
					to.setValuator("Project Evaluation Major");
				}else if(examValidationDetails.getIsValuator() != null && !examValidationDetails.getIsValuator().isEmpty() && examValidationDetails.getIsValuator().equalsIgnoreCase("Project Minor")){
					to.setValuator("Project Evaluation Minor");
				}else{
					to.setValuator(examValidationDetails.getIsValuator());
				}
				toList.add(to);
			}
		}
		return toList;
	}
	/**
	 * @param examValidationDetailsForm
	 * @param examValidationDetails
	 * @throws Exception
	 */
	public void convertBOToForm(ExamValidationDetailsForm examValidationDetailsForm,ExamValidationDetails examValidationDetails) throws Exception{
		
		if(examValidationDetails != null){
			if(examValidationDetails.getExam() != null && examValidationDetails.getExam().getId() != 0){
				examValidationDetailsForm.setExamId(String.valueOf(examValidationDetails.getExam().getId()));
			}
			if(examValidationDetails.getExam() != null && examValidationDetails.getExam().getExamType() != null && examValidationDetails.getExam().getExamType().getName() != null){
				examValidationDetailsForm.setExamType(examValidationDetails.getExam().getExamType().getName());
			}
			if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
				examValidationDetailsForm.setSubjectId(String.valueOf(examValidationDetails.getSubject().getId()));
			}
			if(examValidationDetails.getUsers() != null && examValidationDetails.getUsers().getId() != 0){
				examValidationDetailsForm.setEmployeeId(String.valueOf(examValidationDetails.getUsers().getId()));
			}else{
				examValidationDetailsForm.setOtherEmployee(examValidationDetails.getOtherEmployee());
				examValidationDetailsForm.setEmployeeId("Other");
			}
//			if(examValidationDetails.getDate() != null){
//				String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(examValidationDetails.getDate()), ExamValidationDetailsHelper.SQL_DATEFORMAT,	ExamValidationDetailsHelper.FROM_DATEFORMAT);
//				examValidationDetailsForm.setDate(date);
//			}
			if(examValidationDetails.getExam() != null && examValidationDetails.getExam().getExamType() != null && examValidationDetails.getExam().getExamType().getName() != null){
				examValidationDetailsForm.setExamType(examValidationDetails.getExam().getExamType().getName());
			}
		}
	}
	/**
	 * @param boList
	 * @return
	 * @throws Exception
	 * added by mehaboob
	 */
	public List<ExamValidationDetailsTO> convertBOToTOBySubject(List<ExamValuationAnswerScript> boList,ExamValidationDetailsForm examValidationDetailsForm) throws Exception{
		List<ExamValidationDetailsTO> toList=new LinkedList<ExamValidationDetailsTO>();
		String valuatorsDetails="";
		String reviewerDetails="";
		String internalOrExternal=null;
		if(boList !=null){
			Iterator<ExamValuationAnswerScript> iterator = boList.iterator();
			while (iterator.hasNext()) {
				ExamValidationDetailsTO to=new ExamValidationDetailsTO();
				ExamValuationAnswerScript examValuationAnswerScript = (ExamValuationAnswerScript) iterator.next();
				if(examValuationAnswerScript.getIssueDate() != null){
					String date = CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(examValuationAnswerScript.getIssueDate()), ExamValidationDetailsHelper.SQL_DATEFORMAT,	ExamValidationDetailsHelper.FROM_DATEFORMAT);
					to.setIssueDate(date);
				}if(examValuationAnswerScript.getNumberOfAnswerScripts()!=0){
					  to.setAnswerScripts(String.valueOf(examValuationAnswerScript.getNumberOfAnswerScripts()));	
				}
				to.setId(examValuationAnswerScript.getId());
				if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null 
						&& examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee() != null 
						&& examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName() != null 
						&&!examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName().isEmpty()){
					
					to.setEmployeeName(examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName());
					to.setOtherEmployee("InternalValuator");
					if(examValidationDetailsForm.getUpdatedScripts()!=null){
						internalOrExternal=examValuationAnswerScript.getValidationDetailsId().getUsers().getEmployee().getFirstName();	
					}
				}// /* code added by chandra
				else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null && examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest() != null 
						&& examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName() != null && !examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName().isEmpty()){
					
					to.setEmployeeName(examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName());
					to.setOtherEmployee("InternalValuator");
					if(examValidationDetailsForm.getUpdatedScripts()!=null){
						internalOrExternal=examValuationAnswerScript.getValidationDetailsId().getUsers().getGuest().getFirstName();	
					}
				}// */
				else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getUsers() != null 
						&& examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName() != null && !examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName().isEmpty()){
					
					to.setEmployeeName(examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName());
					to.setOtherEmployee("InternalValuator");
					if(examValidationDetailsForm.getUpdatedScripts()!=null){
						internalOrExternal=examValuationAnswerScript.getValidationDetailsId().getUsers().getUserName();	
					}
				}else if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getOtherEmployee() != null 
						&& !examValuationAnswerScript.getValidationDetailsId().getOtherEmployee().isEmpty()){
					
					to.setEmployeeName(examValuationAnswerScript.getValidationDetailsId().getOtherEmployee());
					to.setOtherEmployee("ExternalValuator");
					if(examValidationDetailsForm.getUpdatedScripts()!=null){
						internalOrExternal=examValuationAnswerScript.getValidationDetailsId().getOtherEmployee()+"(E)";	
					}
					
					
				}
				if(examValuationAnswerScript.getValidationDetailsId() != null && examValuationAnswerScript.getValidationDetailsId().getExamValuators() != null 
						&& examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName() != null && !examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName().isEmpty()){
					
					to.setEmployeeName(examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName());
					to.setOtherEmployee("ExternalValuator");
					if(examValidationDetailsForm.getUpdatedScripts()!=null){
						internalOrExternal=examValuationAnswerScript.getValidationDetailsId().getExamValuators().getName()+"(E)";	
					}
					
				}if(examValuationAnswerScript.getValidationDetailsId().getIsValuator() != null && !examValuationAnswerScript.getValidationDetailsId().getIsValuator().isEmpty() && examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Major")){
					to.setValuator("Project Evaluation Major");
				}else if(examValuationAnswerScript.getValidationDetailsId().getIsValuator() != null && !examValuationAnswerScript.getValidationDetailsId().getIsValuator().isEmpty() && examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Project Minor")){
					to.setValuator("Project Evaluation Minor");
				}else{
					to.setValuator(examValuationAnswerScript.getValidationDetailsId().getIsValuator());
				}
				if(examValidationDetailsForm.getUpdatedScripts()!=null){
					if(examValuationAnswerScript.getValidationDetailsId().getIsValuator().equalsIgnoreCase("Reviewer")){
							reviewerDetails=reviewerDetails+", "+internalOrExternal;
					}else {
						valuatorsDetails=valuatorsDetails+", "+internalOrExternal;
					}
				}
				toList.add(to);
			}
		}
		if(valuatorsDetails!=null && !valuatorsDetails.isEmpty()){
		    valuatorsDetails=removeDuplicateWord(valuatorsDetails);
		    examValidationDetailsForm.setValuatorDetails(valuatorsDetails);
		}if(reviewerDetails!=null && !reviewerDetails.isEmpty()){
			reviewerDetails=removeDuplicateWord(reviewerDetails);
			examValidationDetailsForm.setReviewerDetails(reviewerDetails);
		}
		return toList;
	}
	/**
	 * @param word
	 * @return
	 */
	public String removeDuplicateWord(String word){
		String[] wordList = word.split(",");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < wordList.length; i++) {
		    boolean found = false;
		    for (int j = i+1; j < wordList.length; j++) {
		        if (wordList[j].equals(wordList[i])) {
		            found = true;
		            break;
		        }
		    }
		    if (!found) {
		        if (sb.length() > 0)
		            sb.append(',');
		        sb.append(wordList[i]);
		    }
		}
		return sb.toString();
		
	}
}
