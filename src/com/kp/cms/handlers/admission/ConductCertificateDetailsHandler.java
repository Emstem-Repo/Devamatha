package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ConductCertificateDetails;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.forms.admission.ConductCertificateDetailsForm;
import com.kp.cms.forms.admission.ConductCertificateForm;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.helpers.admission.ConductCertificateDetailsHelper;
import com.kp.cms.helpers.admission.ConductCertificateHelper;
import com.kp.cms.helpers.admission.TCDetailsHelper;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.transactions.admission.IConductCertificateDetailsTransaction;
import com.kp.cms.transactions.admission.ITCDetailsTransaction;
import com.kp.cms.transactionsimpl.admission.ConductCertificateDetailsImpl;
import com.kp.cms.transactionsimpl.admission.TCDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class ConductCertificateDetailsHandler {
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";
	private static final String SQL_DATEFORMAT = "dd-MMM-yyyy";
	private static volatile ConductCertificateDetailsHandler conductCertificateDetailsHandler = null;
	private static final Log log = LogFactory.getLog(ConductCertificateDetailsHandler.class);
	private ConductCertificateDetailsHandler() {
		
	}
	/**
	 * return singleton object of TCDetailsHandler.
	 * @return
	 */
	public static ConductCertificateDetailsHandler getInstance() {
		if (conductCertificateDetailsHandler == null) {
			conductCertificateDetailsHandler = new ConductCertificateDetailsHandler();
		}
		return conductCertificateDetailsHandler;
	}
	//pavani did for Checking whether TC is already done for this class
	public boolean verifyStudentsAllSaved(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception
	{
		ITCDetailsTransaction transaction=TCDetailsTransactionImpl.getInstance();
		List<Integer> studentList=transaction.getStudentFromTC(Integer.parseInt(conductCertificateDetailsForm.getClassId()));
		String query=ConductCertificateDetailsHelper.getInstance().getSearchQuery(conductCertificateDetailsForm);
		List<Student> studentOfThatClass=transaction.getStudentDetails(query);
		if(studentList.size()>0){
			return true;
		}
		else
			return false;
	}
	public boolean verifyStudentsPresent(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception
	{
		ITCDetailsTransaction transaction=TCDetailsTransactionImpl.getInstance();
		String query=ConductCertificateDetailsHelper.getInstance().getSearchQuery(conductCertificateDetailsForm);
		List<Student> studentOfThatClass=transaction.getStudentDetails(query);
		if(studentOfThatClass.isEmpty() && studentOfThatClass.size()==0){
			return false;
		}
		else
		return true;
	}
	public Boolean getUpdateStatus(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		List<Student> studentList=transaction.getStudentListForCCEntry(conductCertificateDetailsForm);
		//transaction.updateStudentCCNo(studentList,conductCertificateDetailsForm);
		return transaction.updateStudentTCDetails(studentList,conductCertificateDetailsForm);
		}
	public Boolean getUpdateStatusEdit(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		return transaction.updateStudentTCDetailsEdit(conductCertificateDetailsForm);
		}
	
	public List<BoardDetailsTO> getListOfCandidates(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		log.info("Entered into getListOfCandidates- ConductCertificateDetailsHandler");
		ITCDetailsTransaction transaction=TCDetailsTransactionImpl.getInstance();
		String query=ConductCertificateDetailsHelper.getInstance().getSearchQuery(conductCertificateDetailsForm);
		List<Student> studentList=transaction.getStudentDetails(query);
		log.info("Exit from getListOfCandidates- ConductCertificateDetailsHandler");
		return ConductCertificateDetailsHelper.getInstance().convertBotoToList(studentList);
	}
	public void getAllStudentTCDetailsByClass(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		log.info("Entered into getStudentTCDetails- BoardDetailsHandler");
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		//String subjects = transaction.getSubjectsForAllStudentsByClass(conductCertificateDetailsForm);
		ConductCertificateDetailsHelper helper=ConductCertificateDetailsHelper.getInstance();
		if (conductCertificateDetailsForm.getClassId()!=null) {
			helper.getformData(conductCertificateDetailsForm);
			
		}
		ExamDefinitionBO bo = transaction.getExamForAllStudentsByClass(conductCertificateDetailsForm); 	
		
		//TCDetailsHelper.getInstance().convertBotoFormAllStudentsByClass(subjects,bo,conductCertificateDetailsForm);		
		
		log.info("Exit from getStudentTCDetails- TCDetailsHandler");
	}
	
	public void getStudentTCDetails(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception {
		log.info("Entered into getStudentTCDetails- BoardDetailsHandler");
		
		String query=ConductCertificateDetailsHelper.getInstance().getSearchQueryForTCDetails(conductCertificateDetailsForm);
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		ConductCertificateDetails student=transaction.getStudentTCDetails(query);
		if(student!=null){
			if(student.getCharacterAndConduct()!=null)
		conductCertificateDetailsForm.setCharacterId(String.valueOf(student.getCharacterAndConduct().getId()));
		conductCertificateDetailsForm.setClassOfLeaving(student.getClassOfLeaving());
		conductCertificateDetailsForm.setCourseName(student.getCourseName());
		conductCertificateDetailsForm.setDateOfApplication(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getDateOfApplication()), SQL_DATEFORMAT, FROM_DATEFORMAT));
		conductCertificateDetailsForm.setClassId(String.valueOf(student.getClasses().getId()));
		conductCertificateDetailsForm.setId(String.valueOf(student.getId()));
		conductCertificateDetailsForm.setAcademicDuration(student.getAcademicDuration());
		}
		/*if(student.getIsKerala()){
			conductCertificateDetailsForm.setIsKerala("yes");
		}else{
			conductCertificateDetailsForm.setIsKerala("no");
		}*/
		
		log.info("Exit from getStudentTCDetails- TCDetailsHandler");
	}
	
	public List<PrintTcDetailsTo> getStudentsByClass(HttpServletRequest request,ConductCertificateDetailsForm  form)throws Exception 
	{
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		List<PrintTcDetailsTo> studentList=new ArrayList<PrintTcDetailsTo>();
		String rollNo=transaction.getStudentroleNo(form.getStudentId());
		List<Student>  studentBoList=transaction.getStudentList(form.getClassId(),form.getStudentId());
		studentList=ConductCertificateDetailsHelper.getInstance().convertStudentBoToTo(studentBoList,request,form,rollNo);
		return studentList;
	}
	
	/*public boolean verifyGeneratedTCNumbers(ConductCertificateDetailsForm conductCertificateDetailsForm) throws Exception
	{
		ITCDetailsTransaction transaction=TCDetailsTransactionImpl.getInstance();
		String query=ConductCertificateDetailsHelper.getInstance().getSearchQuery(conductCertificateDetailsForm);
		List<Student> studentOfThatClass=transaction.getStudentDetails(query);
		return TCDetailsHelper.getInstance().checkForExistingTCNumbersInOtherClasses(tcDetailsForm.getPrefixForTC(), 
																					 Integer.parseInt(tcDetailsForm.getStartingNumber()), 
																					 studentOfThatClass);
	}*/
	public String getCourseId(String classId) throws Exception
	{
		IConductCertificateDetailsTransaction transaction=ConductCertificateDetailsImpl.getInstance();
		String courseId=transaction.getClourseId(classId);
		return courseId;
	}

}
