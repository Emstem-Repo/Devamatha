package com.kp.cms.handlers.admission;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.bo.exam.ExamStudentDetentionRejoinDetails;
import com.kp.cms.forms.admission.MigrationCertificateForm;
import com.kp.cms.transactions.admission.IMigrationCertificateTransaction;
import com.kp.cms.transactionsimpl.admission.MigrationCertificateTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author dIlIp
 *
 */
public class MigrationCertificateHandler {
	
	private static final Log log=LogFactory.getLog(MigrationCertificateHandler.class);
	public static volatile MigrationCertificateHandler mcHandler=null;
	private MigrationCertificateHandler(){
		
	}
	public static MigrationCertificateHandler getInstance(){
		if(mcHandler==null)
		{
			mcHandler=new MigrationCertificateHandler();
			return mcHandler;
		}
		return mcHandler;
	}
	
	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public Student verifyRegisterNumberAndGetDetails(MigrationCertificateForm certificateForm) throws Exception
	{
		IMigrationCertificateTransaction iTransaction = MigrationCertificateTransactionImpl.getInstance();
		Student student = iTransaction.verifyRegisterNumberAndGetDetails(certificateForm);
		if(student!=null){
			
			certificateForm.setStuId(student.getId());
			certificateForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
			certificateForm.setStudentCourse(student.getAdmAppln().getCourseBySelectedCourseId().getCertificateCourseName());
			certificateForm.setStudentRegNo(student.getRegisterNo());
			
			ExamStudentDetentionRejoinDetails rejoinDetailsBo = iTransaction.verifyStudentDetentionDiscontinued(certificateForm.getStuId());
			if(rejoinDetailsBo!=null){
				if(rejoinDetailsBo.getDetentionDate()!=null && !rejoinDetailsBo.getDetentionDate().toString().isEmpty()){
					certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(rejoinDetailsBo.getDetentionDate().toString().substring(5, 7))-1)+" "+rejoinDetailsBo.getDetentionDate().toString().substring(0, 4));
				}
				else if(rejoinDetailsBo.getDiscontinuedDate()!=null && !rejoinDetailsBo.getDiscontinuedDate().toString().isEmpty()){
					certificateForm.setStudentAcademicYearTo(CommonUtil.getMonthFullName(Integer.parseInt(rejoinDetailsBo.getDiscontinuedDate().toString().substring(5, 7))-1)+" "+rejoinDetailsBo.getDiscontinuedDate().toString().substring(0, 4));
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
	public StudentCertificateDetails checkForAlreadyPrinted(MigrationCertificateForm certificateForm) throws Exception {
		
		IMigrationCertificateTransaction iTransaction = MigrationCertificateTransactionImpl.getInstance();
		return iTransaction.checkForAlreadyPrinted(certificateForm);
	}

	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveMigrationCertificate(MigrationCertificateForm certificateForm) throws Exception {
		
		IMigrationCertificateTransaction iTransaction = MigrationCertificateTransactionImpl.getInstance();
		
		StudentCertificateDetails details = new StudentCertificateDetails();
		Student student = new Student();
				
		student.setId(certificateForm.getStuId());
		details.setStudentId(student);
		
		details.setCertificateNo(certificateForm.getMigrationCertificateNo());
		details.setPrintedDate(new Date());
		details.setType("Migration");
		
		return iTransaction.saveMigrationCertificate(details);
	}

	/**
	 * @param certificateForm
	 * @return
	 * @throws Exception
	 */
	public boolean saveMigrationCurrentNumber(MigrationCertificateForm certificateForm) throws Exception {
		
		IMigrationCertificateTransaction iTransaction = MigrationCertificateTransactionImpl.getInstance();
		return iTransaction.saveMigrationCertificateCurrentNumber(certificateForm);
		
	}


}
