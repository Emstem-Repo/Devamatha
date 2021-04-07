package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamValidationDetails;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.forms.exam.PendingExamResultsForm;
import com.kp.cms.helpers.exam.ExamValuationStatusHelper;
import com.kp.cms.helpers.exam.PendingExamResultsHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.exam.PendingExamResultsTO;
import com.kp.cms.transactions.admission.IPendingExamResultsTransactions;
import com.kp.cms.transactions.exam.IExamValuationStatusTransaction;
import com.kp.cms.transactionsimpl.exam.ExamValuationStatusTxImpl;
import com.kp.cms.transactionsimpl.exam.PendingExamResultsImpl;

public class PendingExamResultsHandler {
	private static final Log log = LogFactory.getLog(PendingExamResultsHandler.class);
	private static volatile PendingExamResultsHandler examCceFactorHandler = null;

	public static PendingExamResultsHandler getInstance() {
		if (examCceFactorHandler == null) {
			examCceFactorHandler = new PendingExamResultsHandler();
		}
		return examCceFactorHandler;
	}
	/**
	 * @param objForm
	 * @param errors
	 * @return
	 * @throws Exception 
	 */
	public List<PendingExamResultsTO> PendingExamResultsList(PendingExamResultsForm objForm, ActionErrors errors) throws Exception {
		IPendingExamResultsTransactions transaction=new PendingExamResultsImpl();
		objForm.setExamTypeName(NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(objForm.getExamType()),"ExamTypeUtilBO",true,"name"));
		List<Object[]> pendingResults=transaction.getPendingExamResultsList(objForm);
		if(pendingResults==null || pendingResults.isEmpty()){
			errors.add("error", new ActionError("knowledgepro.norecords"));
		}
   //	List<PendingExamResults> pendingExamResults =PendingExamResultsHelper.getInstance().convertFormToBos(pendingResults,objForm);
   //   boolean isAdded = transaction.addPendingExamResults(pendingExamResults, errors,objForm);
		List<PendingExamResultsTO> teacherClassTo=PendingExamResultsHelper.getInstance().setPendingExamResultsList(pendingResults,objForm);
		return teacherClassTo;
	}
	public boolean exportTOExcel(PendingExamResultsForm objForm,HttpServletRequest request, ActionErrors errors) throws Exception {
		boolean isUpdated = false;
		isUpdated = PendingExamResultsHelper.getInstance().convertToTOExcel(objForm,request);
		return isUpdated;	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	public void getviewDetails(PendingExamResultsForm objForm) throws Exception{
		IPendingExamResultsTransactions transaction=new PendingExamResultsImpl();
		List<StudentTO> studentTos = new ArrayList<StudentTO>();
		
		 List<StudentSupplementaryImprovementApplication> totalStudents = transaction.getTotalStudents(PendingExamResultsHelper.getInstance().getQueryForCurrentClassStudents(objForm));
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetailsSup(studentTos,totalStudents);
		objForm.setStudents(studentTos);
	}
	
	
/**
 * @param objForm
 * @throws Exception
 */
public void displayValuationDetails(PendingExamResultsForm objForm) throws Exception {
		
		IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
		IPendingExamResultsTransactions transaction1=new PendingExamResultsImpl();
		Integer internalExamId = transaction.getInternalExamId(objForm.getExamId());
		List<ExamValidationDetails> valuationList = transaction.getExamValidationList(objForm.getExamId());
		Map<Integer, List<ExamValidationDetails>> valuationMap = new HashMap<Integer, List<ExamValidationDetails>>(); 
		if(valuationList != null && !valuationList.isEmpty()){
			Iterator<ExamValidationDetails> iterator = valuationList.iterator();
			List<ExamValidationDetails> vList = new ArrayList<ExamValidationDetails>();
			while (iterator.hasNext()) {
				ExamValidationDetails examValidationDetails = (ExamValidationDetails) iterator.next();
				if(examValidationDetails.getSubject() != null && examValidationDetails.getSubject().getId() != 0){
					if(valuationMap.containsKey(examValidationDetails.getSubject().getId())){
						vList = valuationMap.remove(examValidationDetails.getSubject().getId());
					}
						vList.add(examValidationDetails);
					   valuationMap.put(examValidationDetails.getSubject().getId(), vList);
				}
			}
		}
		List<Object[]> details;
		if(objForm.getExamType().equalsIgnoreCase("Supplementary")){
			details = transaction1.getTotalVerfiedStudentForSupplementary(objForm.getExamId(),objForm.getClassId(),false,internalExamId);
		}else{
			details = transaction1.getTotalVerfiedStudent(objForm.getExamId(),objForm.getClassId(),objForm.getFinalYears(),false,internalExamId);
		}
		Map<Integer,Map<Integer, List<ExamValuationStatusTO>>> courseMap=new HashMap<Integer, Map<Integer,List<ExamValuationStatusTO>>>();
		Map<Integer, List<ExamValuationStatusTO>> verifyDetailsMap = null;
		/*code added by sudhir */
		List<Integer> courseListIds = new ArrayList<Integer>();
		List<Integer> subjectListIds = new ArrayList<Integer>();
		/*---------------------*/
		if(details != null){
			Iterator<Object[]> iterator = details.iterator();
			List<ExamValuationStatusTO> list=null;
			while (iterator.hasNext()) {
				Object[] objects = (Object[]) iterator.next();
				int id=0;
				if(objects[1] != null && objects[1].toString() != null){
					id = Integer.parseInt(objects[1].toString());
				}
				if(objects[9] != null && !objects[9].toString().isEmpty()){
					if(courseMap.containsKey(Integer.parseInt(objects[9].toString()))){
						verifyDetailsMap=courseMap.get(Integer.parseInt(objects[9].toString()));
					}else
						verifyDetailsMap=new HashMap<Integer, List<ExamValuationStatusTO>>();
					
					if(verifyDetailsMap.containsKey(id)){
						list=verifyDetailsMap.remove(id);
					}else
						list=new ArrayList<ExamValuationStatusTO>();
					ExamValuationStatusTO to = new ExamValuationStatusTO();
					if(objects[0] != null && objects[0].toString() != null){
						to.setSubjectName(objects[0].toString());
					}
					if(objects[14] != null && objects[14].toString() != null && objects[14].toString().equals("1")){
						to.setInternalSubject("Yes");
					}
					if(objects[8] != null && objects[8].toString() != null){
						to.setSubjectCode(objects[8].toString());
					}
					if(objects[1] != null && objects[1].toString() != null){
						to.setSubjectId(Integer.parseInt(objects[1].toString()));
					}
					if(objects[7] != null && objects[7].toString() != null){
						to.setCourseCode(objects[7].toString());
					}
					if(objects[2] != null && objects[2].toString() != null){
						to.setTotalStudent(Integer.parseInt(objects[2].toString()));
					}
					if(objects[3] != null && objects[3].toString() != null){
						to.setTotalEntered(Integer.parseInt(objects[3].toString()));
					}
					if(objects[4] != null && objects[4].toString() != null){
						to.setTotalVerified(Integer.parseInt(objects[4].toString()));
					}
					if(objects[5] != null && objects[5].toString() != null){
						to.setEvaluatorTypeId(objects[5].toString());
					}
					if(objects[10] != null && objects[10].toString() != null){
						to.setTheoryMultiple(objects[10].toString());
					}
					if(objects[12] != null && objects[12].toString() != null){
						to.setIsTheory(objects[12].toString());
					}
					if(objects[13] != null && objects[13].toString() != null){
						if(objects[13].toString().equalsIgnoreCase("0")){
							to.setCertificateCourse(false);
						}else if(objects[13].toString().equalsIgnoreCase("1")){
							to.setCertificateCourse(true);
						}
					}
					list.add(to);
					verifyDetailsMap.put(id, list);
					courseMap.put(Integer.parseInt(objects[9].toString()),verifyDetailsMap);
					courseListIds.add(Integer.parseInt(objects[9].toString()));
					subjectListIds.add(id);
				}
				
			}
		}
		/* code added by sudhir */
		Map<Integer,Map<String,List<StudentTO>>> newMap = null;
		if(courseListIds!=null && !courseListIds.isEmpty()){
			/*CHECKING MISMATCH MARKS FOR STUDENTS BASED ON COURSE*/
			List<Object[]> objectsList = transaction1.checkMismatchFoundForStudents(courseListIds,subjectListIds,objForm);
			/*IF MISMATCH FOUND,CONVERTING IT INTO MAP. PUTTING COURSEID AS KEY AND MAP<STRING,LIST<STUDENTTO> AS VALUE ,SUBJECTID AS KEY AND LIST<STUDENTTO> AS VALUE*/
			 newMap = PendingExamResultsHelper.getInstance().convertBOListToMap(objectsList);
			 objForm.setMisMatchStudentList(newMap);
		}
		/*--------------------*/
		Map<Integer, String> examStartDates = transaction.getExamSubjectDatesMap(subjectListIds,objForm.getExamId());
		List<ExamValuationStatusTO> valuationStatusTOs = PendingExamResultsHelper.getInstance().convertValues(valuationMap,courseMap,objForm,newMap,internalExamId,examStartDates);
		objForm.setValuationStatus(valuationStatusTOs);
		
	}

/**
 * @param objForm
 * @throws Exception
 */
public void viewValuationDetails(PendingExamResultsForm objForm) throws Exception{
	//IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();	
	IPendingExamResultsTransactions transaction1=new PendingExamResultsImpl();
	List<StudentTO> studentTos = new ArrayList<StudentTO>();
	//List<Student> totalStudents = new ArrayList<Student>();
	// /* code commented by chandra
	/*if(objForm.getExamType().equalsIgnoreCase("Regular")){
		totalStudents = transaction1.getTotalStudentsForVerifyDetails(PendingExamResultsHelper.getInstance().getQueryForCurrentClassStudents(objForm));
		List<MarksEntryDetails> students = transaction1.getDetailsForView(objForm);
		//code added by sudhir
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		//
		Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students);
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,objForm,listOfDetainedStudents,"current");
		totalStudents = transaction1.getTotalStudentsForVerifyDetails(PendingExamResultsHelper.getInstance().getQueryForPreviousClassStudents(objForm));
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetailsForRegular(studentTos,totalStudents,stuMap,objForm,listOfDetainedStudents,"previous");
	}else if(!objForm.getExamType().equalsIgnoreCase("Supplementary")){
		totalStudents = transaction1.getTotalStudentsForVerifyDetails(PendingExamResultsHelper.getInstance().getQueryForCurrentClassStudents(objForm));
		List<MarksEntryDetails> students = transaction1.getDetailsForView(objForm);
		Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students);
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,objForm);
		totalStudents = transaction.getTotalStudents(PendingExamResultsHelper.getInstance().getQueryForPreviousClassStudents(objForm));
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,objForm);
	}else{*/
	// */ code commented by chandra
	if(objForm.getExamType().equalsIgnoreCase("Supplementary")){
		List<Object[]> totalStudent = transaction1.getTotalStudents1(PendingExamResultsHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(objForm));
		List<MarksEntryDetails> students = transaction1.getDetailsForView(objForm);
		Map<Integer,Student> stuMap = ExamValuationStatusHelper.getInstance().getstudentMap(students,objForm.getExamType());
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetailsForSup(studentTos,totalStudent,stuMap,objForm);
	}
//		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(examValuationStatusForm));
//		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails(studentTos,totalStudents,stuMap,examValuationStatusForm);
	// code commented by chandra	
	/*}*/
	objForm.setStudents(studentTos);
}


/**
 * @param objForm
 * @throws Exception
 */
public void viewVerificationDetails(PendingExamResultsForm objForm) throws Exception{
	//IExamValuationStatusTransaction transaction = ExamValuationStatusTxImpl.getInstance();
	IPendingExamResultsTransactions transaction1=new PendingExamResultsImpl();
	List<StudentTO> studentTos = new ArrayList<StudentTO>();
	// /* code commented by chandra
	/*List<Student> totalStudents = new ArrayList<Student>();
	if(examValuationStatusForm.getExamType().equalsIgnoreCase("Regular")){
		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
		//code added by sudhir
		List<Integer> listOfDetainedStudents =ExamMarksEntryHandler.getInstance().getDetainedOrDiscontinuedStudents();
		//
		Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(examValuationStatusForm);
		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"current");
		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetailsRegular1(studentTos,totalStudents,stuMap,examValuationStatusForm,listOfDetainedStudents,"previous");
	}else if(!examValuationStatusForm.getExamType().equalsIgnoreCase("Supplementary")){
		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForCurrentClassStudents(examValuationStatusForm));
		Map<Integer,String> stuMap = transaction.getVerificationDetailsForView(examValuationStatusForm);
		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForPreviousClassStudents(examValuationStatusForm));
		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
	}else{*/
	// */ code commented by chandra
	if(objForm.getExamType().equalsIgnoreCase("Supplementary")){
		List<Object[]> totalStudent = transaction1.getTotalStudents1(PendingExamResultsHelper.getInstance().getQueryForSupplementaryCurrentClassStudents(objForm));
		Map<Integer,String> stuMap = transaction1.getVerificationDetailsForView(objForm);
		studentTos = PendingExamResultsHelper.getInstance().convertBOtoTOStudentDetailsForSup1(studentTos,totalStudent,stuMap,objForm);
	}
//		totalStudents = transaction.getTotalStudents(ExamValuationStatusHelper.getInstance().getQueryForSupplementaryPreviousClassStudents(examValuationStatusForm));
//		studentTos = ExamValuationStatusHelper.getInstance().convertBOtoTOStudentDetails1(studentTos,totalStudents,stuMap,examValuationStatusForm);
	// code commented  by chandra	
		/*}*/
		objForm.setStudents(studentTos);
}

}
