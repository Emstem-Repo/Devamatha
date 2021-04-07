package com.kp.cms.handlers.admission;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateCourseTeacher;
import com.kp.cms.forms.admission.CertificateCourseTeacherForm;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.helpers.admission.CertificateCourseTeacherHelper;
import com.kp.cms.to.admission.CertificateCourseTO;
import com.kp.cms.to.admission.CertificateCourseTeacherTO;
import com.kp.cms.transactions.admin.CertificateCourseTeacherImpl;
import com.kp.cms.transactions.admission.ICertificateCourseTeacherTransaction;

public class CertificateCourseTeacherHandler {
	/**
	 * Singleton object of DocumentExamEntryHandler
	 */
	private static volatile CertificateCourseTeacherHandler certificateCourseTeacherHandler = null;
	private static final Log log = LogFactory.getLog(CertificateCourseTeacherHandler.class);
	private CertificateCourseTeacherHandler() {
		
	}
	/**
	 * return singleton object of DocumentExamEntryHandler.
	 * @return
	 */
	public static CertificateCourseTeacherHandler getInstance() {
		if (certificateCourseTeacherHandler == null) {
			certificateCourseTeacherHandler = new CertificateCourseTeacherHandler();
		}
		return certificateCourseTeacherHandler;
	}
	ICertificateCourseTeacherTransaction transaction = new CertificateCourseTeacherImpl();
	/**
	 * getting the list of doctypeExams from database
	 * @return
	 */
	
	public boolean addCertificateCourseTeacher(CertificateCourseTeacherForm certificateCourseTeacherForm) throws Exception{
		CertificateCourseTeacher bo=CertificateCourseTeacherHelper.getInstance().convetFormToBo(certificateCourseTeacherForm);
		return transaction.addCertificateCourseTeacher(bo);
	}
	public CertificateCourseTeacher checkDuplicate(int cerCourseId, int teacherId) throws Exception{
		return transaction.checkDuplicate(cerCourseId,teacherId);
	}
	public List<CertificateCourseTeacherTO> getList() throws Exception{
		List<CertificateCourseTeacher> certificateCourseTeachers = transaction.getList();
		return CertificateCourseTeacherHelper.getInstance().convertFromBOTO(certificateCourseTeachers);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		//duplication checking.. taking each class and checking the duplication
//		for (int x = 0; x < selectedClass.length; x++){
//			isDuplicated = iPeriodTransaction.isClassAndPeriodNameDuplicated(Integer.parseInt(selectedClass[x]), periodForm.getPeriodName(), periodForm.getId());
//			if(isDuplicated){
//				throw new DuplicateException();
//			}
//			isDuplicated = iPeriodTransaction.isClassAndPeriodDuplicated(Integer.parseInt(selectedClass[x]), CommonUtil.getTime(periodForm.getStartHours(), startMins), CommonUtil.getTime(periodForm.getEndHours(), endMins), periodForm.getId());			
//			if(isDuplicated){
//				throw new DuplicateException();
//			}			
//		}
		
	public void setRequestedDataToForm(
			CertificateCourseTeacherForm certificateCourseTeacherForm) throws Exception{
		Map<Integer, String> teachersMap = UserInfoHandler.getInstance().getTeachingStaff();
		certificateCourseTeacherForm.setTeachersMap(teachersMap);
		List<CertificateCourseTO> courseList = CertificateCourseEntryHandler.getInstance().getActiveCourses(0);
		certificateCourseTeacherForm.setCourseList(courseList);
	}
	
	public boolean reactivateCertificateCourTeacher(int oldId, String userId)throws Exception{
		return transaction.reActive(oldId,userId);
	}
	
	public void getDetailById(CertificateCourseTeacherForm certificateCourseTeacherForm, HttpServletRequest request) throws Exception{
		CertificateCourseTeacher list = transaction.editCertificateCourseTeacher(certificateCourseTeacherForm.getCertificateCourseTeaId());
		if(list != null){
			CertificateCourseTeacherHelper.getInstance().setBodataToForm(list,certificateCourseTeacherForm);
		}
	}
	public boolean deleteCertificateCourseTeacher(int certificateCourTeaId, String userId) throws Exception{
		return transaction.deleteCertificateCourseTeacher(certificateCourTeaId,userId);
	}
	public boolean updateCertificateCourseTeacher(CertificateCourseTeacherForm certificateCourseTeacherForm) throws Exception{
		CertificateCourseTeacher certificateCourseTeacher = CertificateCourseTeacherHelper.getInstance().convertFormToCertificateCourseTeacherBo(certificateCourseTeacherForm);
		return transaction.updateCertificateCourseTeacher(certificateCourseTeacher);
	}
}

