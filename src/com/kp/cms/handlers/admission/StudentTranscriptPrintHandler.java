package com.kp.cms.handlers.admission;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.StudentTranscriptPrintForm;
import com.kp.cms.handlers.exam.NewSecuredMarksEntryHandler;
import com.kp.cms.helpers.admission.StudentTranscriptPrintHelper;
import com.kp.cms.to.admission.StudentTranscriptPrintTO;
import com.kp.cms.transactions.admission.IStudentTranscriptPrintTransaction;
import com.kp.cms.transactionsimpl.admission.StudentTranscriptPrintTransactionImpl;

/**
 * @author dIlIp
 *
 */
public class StudentTranscriptPrintHandler {
	
	private static final Log log=LogFactory.getLog(StudentTranscriptPrintHandler.class);
	public static volatile StudentTranscriptPrintHandler mcHandler=null;
	IStudentTranscriptPrintTransaction iTransaction = StudentTranscriptPrintTransactionImpl.getInstance();
	
	private StudentTranscriptPrintHandler(){
		
	}
	
	public static StudentTranscriptPrintHandler getInstance(){
		if(mcHandler==null)
		{
			mcHandler=new StudentTranscriptPrintHandler();
			return mcHandler;
		}
		return mcHandler;
	}
	
	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public Student verifyRegisterNumberAndGetDetails(StudentTranscriptPrintForm certificateForm) throws Exception
	{
		log.info("inside verifyRegisterNumberAndGetDetails method");

		Student student = iTransaction.verifyRegisterNumberAndGetDetails(certificateForm);
		if(student!=null){
			certificateForm.setStuId(student.getId());
			certificateForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			certificateForm.setStudentCourse(student.getAdmAppln().getCourseBySelectedCourseId().getCertificateCourseName());
			//certificateForm.setRegNo(student.getRegisterNo());
			
			ExamStudentDetentionRejoinDetails rejoinDetailsBo = iTransaction.verifyStudentDetentionDiscontinued(certificateForm.getStuId());
			if(rejoinDetailsBo!=null){
				if(rejoinDetailsBo.getDetentionDate()!=null && !rejoinDetailsBo.getDetentionDate().toString().isEmpty()){
					certificateForm.setStudentAcademicYearTo(rejoinDetailsBo.getDetentionDate().toString().substring(0, 4));
				}
				else if(rejoinDetailsBo.getDiscontinuedDate()!=null && !rejoinDetailsBo.getDiscontinuedDate().toString().isEmpty()){
					certificateForm.setStudentAcademicYearTo(rejoinDetailsBo.getDiscontinuedDate().toString().substring(0, 4));
				}
			}
			
			iTransaction.getStudentAcademicDetails(certificateForm);
		}
		return student;
		
	}
	
	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public StudentCertificateDetails checkForAlreadyPrinted(StudentTranscriptPrintForm certificateForm) throws Exception {
		
		return iTransaction.checkForAlreadyPrinted(certificateForm);
	}
	
	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveStudentTranscriptPrint(StudentTranscriptPrintForm certificateForm) throws Exception {
		
		StudentCertificateDetails details = new StudentCertificateDetails();
		Student student = new Student();
				
		student.setId(certificateForm.getStuId());
		details.setStudentId(student);
		
		details.setCertificateNo(certificateForm.getTranscriptNo());
		details.setPrintedDate(new Date());
		details.setType("Transcript");
		
		return iTransaction.saveStudentMarksCardsPrint(details);
	}
	
	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveStudentCertificateNumberCurrentNumber(StudentTranscriptPrintForm certificateForm) throws Exception {
		
		return iTransaction.saveStudentCertificateNumberCurrentNumber(certificateForm);
		
	}
	
	
	/**
	 * @param certificateForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Map<Integer, List<StudentTranscriptPrintTO>> getStudentForInput(StudentTranscriptPrintForm certificateForm,HttpServletRequest request) 
	throws Exception{
		
		log.info("inside getStudentForInput method");
		String classId = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(certificateForm.getStuId(),"Student",true,"classSchemewise.classes.id");
		String courseId = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(classId),"Classes",true,"course.id");
		String programTypeId = NewSecuredMarksEntryHandler.getInstance().getPropertyValue(Integer.parseInt(classId),"Classes",true,"course.program.programType.id");
		
		boolean isPG=false;
		if(programTypeId!=null && CMSConstants.PG_ID.contains(Integer.parseInt(programTypeId)))
			isPG=true;
		
		String consolidateQuery = StudentTranscriptPrintHelper.getInstance().getConsolidateQuery();
		
		List<Object[]> list = iTransaction.getStudentMarks(consolidateQuery, certificateForm.getStuId());
		
		List<String> appearedList = iTransaction.getSupplimentaryAppeared(certificateForm.getStuId());
		
		String certificateCourseQuery="select subject_id,is_optional,scheme_no,student_id from student_certificate_course  where is_cancelled=0 and  student_id in (:id)";
		List certificateList=iTransaction.getStudentMarks(certificateCourseQuery,certificateForm.getStuId());
		
		Map<Integer,Map<Integer,Map<Integer,Boolean>>> certificateMap=getCertificateSubjectMap(certificateList);
		
		Map<Integer,Map<String, StudentTranscriptPrintTO>> boMap=StudentTranscriptPrintHelper.getInstance().getBoListForInput(list,certificateMap,appearedList,courseId);
		
		Map<Integer, List<StudentTranscriptPrintTO>> newMap = StudentTranscriptPrintHelper.getInstance().getTranscript(boMap, certificateForm, isPG);
		
		if(isPG)
			certificateForm.setDescription(CMSConstants.TRANSCRIPT_DESCRIPTION_PG);
		else
			certificateForm.setDescription(CMSConstants.TRANSCRIPT_DESCRIPTION_UG);
		
		return newMap;
	}
	
	
	/**
	 * @param certificateList
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private Map<Integer, Map<Integer, Map<Integer, Boolean>>> getCertificateSubjectMap(List certificateList)  throws Exception{
		Map<Integer,Map<Integer,Map<Integer,Boolean>>> map=new HashMap<Integer,Map<Integer, Map<Integer,Boolean>>>();
		Map<Integer,Map<Integer,Boolean>> outerMap;
		Map<Integer,Boolean> innerMap;
		if(certificateList!=null && !certificateList.isEmpty()){
			Iterator<Object[]> itr=certificateList.iterator();
			while (itr.hasNext()) {
				Object[] objects = (Object[]) itr.next();
				if(objects[0]!=null && objects[1]!=null && objects[2]!=null  && objects[3]!=null){
					if(map.containsKey(Integer.parseInt(objects[3].toString())))
						outerMap=map.get(Integer.parseInt(objects[3].toString()));
					else
						outerMap=new HashMap<Integer, Map<Integer,Boolean>>();
						
					if(outerMap.containsKey(Integer.parseInt(objects[2].toString()))){
						innerMap=outerMap.remove(Integer.parseInt(objects[2].toString()));
					}else
						innerMap=new HashMap<Integer, Boolean>();
					
					innerMap.put(Integer.parseInt(objects[0].toString()),(Boolean)objects[1]);
					outerMap.put(Integer.parseInt(objects[2].toString()),innerMap);
					map.put(Integer.parseInt(objects[3].toString()), outerMap);
				}
			}
		}
		return map;
	}

}
