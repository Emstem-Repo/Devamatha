package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.forms.admission.CertificateCourseTeacherForm;
import com.kp.cms.handlers.admin.DocumentExamEntryHandler;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.transactions.admin.IDocumentExamEntryTransaction;
import com.kp.cms.transactionsimpl.admin.DocumentExamEntryTransactionImpl;
import com.sun.org.apache.bcel.internal.generic.RETURN;

public class CertificateCourseTeacherHelper {
	/**
	 * Singleton object of DocumentExamEntryHandler
	 */
	private static volatile CertificateCourseTeacherHelper certificateCourseTeacherHelper = null;
	private static final Log log = LogFactory.getLog(CertificateCourseTeacherHelper.class);
	private CertificateCourseTeacherHelper() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static CertificateCourseTeacherHelper getInstance() {
		if (certificateCourseTeacherHelper == null) {
			certificateCourseTeacherHelper = new CertificateCourseTeacherHelper();
		}
		return certificateCourseTeacherHelper;
	}
	/**
	 * getting the list of doctypeExams from database
	 * @return
	 */
	
	public CertificateCourseTeacher convetFormToBo(CertificateCourseTeacherForm certificateCourseTeacherForm){
		
		CertificateCourseTeacher bo = new CertificateCourseTeacher();
		bo.setStartTime(certificateCourseTeacherForm.getStartHours()+":"+certificateCourseTeacherForm.getStartMins());
		bo.setEndTime(certificateCourseTeacherForm.getEndHours()+":"+certificateCourseTeacherForm.getStartMins());
		bo.setVenue(certificateCourseTeacherForm.getVenue());
		bo.setModifiedBy(certificateCourseTeacherForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		CertificateCourse certificateCourse = new CertificateCourse();
		certificateCourse.setId(Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseId()));
		bo.setCertificateCourse(certificateCourse);
		Users users = new Users();
		users.setId(Integer.parseInt(certificateCourseTeacherForm.getTeacherId()));
		bo.setUsers(users);
		bo.setCreatedBy(certificateCourseTeacherForm.getUserId());
		bo.setIsActive(true);
		return bo;
	}
	
	public List<CertificateCourseTeacherTO> convertFromBOTO(List<CertificateCourseTeacher> certificateCourseTeachers){
		List<CertificateCourseTeacherTO> certificateCourseTeacherTO = new ArrayList<CertificateCourseTeacherTO>();
		if(!certificateCourseTeachers.isEmpty()){
		Iterator<CertificateCourseTeacher> itr = certificateCourseTeachers.iterator();
		while (itr.hasNext()) {
			CertificateCourseTeacher certificateCourseTeacher = (CertificateCourseTeacher) itr.next();
			CertificateCourseTeacherTO to = new CertificateCourseTeacherTO();
			to.setId(certificateCourseTeacher.getId());
			to.setVenue(certificateCourseTeacher.getVenue());
			to.setCertificateCourseName(certificateCourseTeacher.getCertificateCourse().getCertificateCourseName());
			to.setCreatedBy(certificateCourseTeacher.getCreatedBy());
			to.setCreatedDate(certificateCourseTeacher.getCreatedDate());
			to.setStartTime(certificateCourseTeacher.getStartTime().substring(0, 5));
			to.setEndTime(certificateCourseTeacher.getEndTime().substring(0,5));
			to.setIsActive(certificateCourseTeacher.getIsActive());
			to.setUsersName(certificateCourseTeacher.getUsers().getUserName());
			certificateCourseTeacherTO.add(to);
		}
		}
		return certificateCourseTeacherTO;
	}
	
	public void setBodataToForm(CertificateCourseTeacher listBO, CertificateCourseTeacherForm certificateCourseTeacherForm){
		
		certificateCourseTeacherForm.setVenue(listBO.getVenue());
		certificateCourseTeacherForm.setStartHours(listBO.getStartTime().substring(0,2));
		certificateCourseTeacherForm.setStartMins(listBO.getStartTime().substring(3,5));
		certificateCourseTeacherForm.setEndHours(listBO.getEndTime().substring(0,2));
		certificateCourseTeacherForm.setEndMins(listBO.getEndTime().substring(3,5));
		certificateCourseTeacherForm.setCertificateCourseId(Integer.toString(listBO.getCertificateCourse().getId()));
		certificateCourseTeacherForm.setTeacherId(Integer.toString(listBO.getUsers().getId()));
		certificateCourseTeacherForm.setOldCertificateCourseId(Integer.toString(listBO.getCertificateCourse().getId()));
		certificateCourseTeacherForm.setOldTeacherId(Integer.toString(listBO.getUsers().getId()));
		
	}
	
	public CertificateCourseTeacher convertFormToCertificateCourseTeacherBo(CertificateCourseTeacherForm certificateCourseTeacherForm){
		CertificateCourseTeacher bo = new CertificateCourseTeacher();
		bo.setId(Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseTeaId()));
		bo.setStartTime(certificateCourseTeacherForm.getStartHours()+":"+certificateCourseTeacherForm.getStartMins());
		bo.setEndTime(certificateCourseTeacherForm.getEndHours()+":"+certificateCourseTeacherForm.getEndMins());
		bo.setVenue(certificateCourseTeacherForm.getVenue());
		bo.setModifiedBy(certificateCourseTeacherForm.getUserId());
		bo.setCreatedDate(new Date());
		bo.setLastModifiedDate(new Date());
		CertificateCourse certificateCourse = new CertificateCourse();
		certificateCourse.setId(Integer.parseInt(certificateCourseTeacherForm.getCertificateCourseId()));
		bo.setCertificateCourse(certificateCourse);
		Users users = new Users();
		users.setId(Integer.parseInt(certificateCourseTeacherForm.getTeacherId()));
		bo.setUsers(users);
		bo.setCreatedBy(certificateCourseTeacherForm.getUserId());
		bo.setIsActive(true);
	return bo;
	}
}
