package com.kp.cms.handlers.admission;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AssignCertificateCourse;
import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.AssignCertificateCourseForm;
import com.kp.cms.helpers.admission.AssignCertificateCourseHelper;
import com.kp.cms.to.admission.AssignCertificateCourseDetailsTO;
import com.kp.cms.to.admission.AssignCertificateCourseTO;
import com.kp.cms.transactions.admission.IAssignCertificateCourseTransaction;
import com.kp.cms.transactions.admission.ICertificateCourseEntryTxn;
import com.kp.cms.transactionsimpl.admission.AssignCertificateCourseTransactionImpl;
import com.kp.cms.transactionsimpl.admission.CertificateCourseEntryTxnImpl;

public class AssignCertificateCourseHandler {
	/**
	 * Singleton object of AssignCertificateCourseHandler
	 */
	private static volatile AssignCertificateCourseHandler assignCertificateCourseHandler = null;
	private static final Log log = LogFactory.getLog(AssignCertificateCourseHandler.class);
	private AssignCertificateCourseHandler() {
		
	}
	/**
	 * return singleton object of AssignCertificateCourseHandler.
	 * @return
	 */
	public static AssignCertificateCourseHandler getInstance() {
		if (assignCertificateCourseHandler == null) {
			assignCertificateCourseHandler = new AssignCertificateCourseHandler();
		}
		return assignCertificateCourseHandler;
	}
	/**
	 * @return
	 */
	public List<AssignCertificateCourseTO> getAssignCertificateCourses(int year,String semType) throws Exception {
		IAssignCertificateCourseTransaction transaction=AssignCertificateCourseTransactionImpl.getInstance();
		List<AssignCertificateCourse> list=transaction.getAllAssignCertificateCourses(year,semType);
		return AssignCertificateCourseHelper.getInstance().convertBosToTOs(list);
	}
	/**
	 * @param assignCertificateCourseForm
	 * @param string
	 * @return
	 */
	public boolean addAssignCertificateCourse(AssignCertificateCourseForm assignCertificateCourseForm,String mode) throws Exception{
		IAssignCertificateCourseTransaction transaction=AssignCertificateCourseTransactionImpl.getInstance();
		String query=AssignCertificateCourseHelper.getInstance().getDuplicateCheckQuery(assignCertificateCourseForm);
		AssignCertificateCourse oldBo=transaction.checkDuplicateAssignCertificateCourse(query);
		if(oldBo!=null && oldBo.getId()!=assignCertificateCourseForm.getId()){
			if(oldBo.getIsActive()){
				throw new DuplicateException();
			}else{
				assignCertificateCourseForm.setId(oldBo.getId());
				throw new ReActivateException();
			}
		}
		AssignCertificateCourse bo=AssignCertificateCourseHelper.getInstance().convertFormTOBO(assignCertificateCourseForm,mode);
		return transaction.saveAssignCertificateCourse(bo);
	}
	/**
	 * @param assignCertificateCourseForm
	 * @param mode
	 * @return
	 */
	public boolean deleteAssignCertificateCourse(AssignCertificateCourseForm assignCertificateCourseForm,String mode) {
		IAssignCertificateCourseTransaction transaction=AssignCertificateCourseTransactionImpl.getInstance();
		return transaction.deleteAssignCertificateCourse(assignCertificateCourseForm,mode);
	}
	/**
	 * @return
	 * @throws Exception
	 */
	public List<AssignCertificateCourseDetailsTO> getActiveCertificateCourses(int year,String semType) throws Exception {
		ICertificateCourseEntryTxn iCertificateCourseEntryTxn = CertificateCourseEntryTxnImpl.getInstance();
		List<CertificateCourse> courseBoList=iCertificateCourseEntryTxn.getActiveCertificateCourses(year,semType);
		return AssignCertificateCourseHelper.getInstance().getAssignCertificateCourseDetails(courseBoList,new HashMap<Integer, Integer>());
	}
	/**
	 * @param assignCertificateCourseForm
	 * @throws Exception
	 */
	public void editAssignCertificateCourse(AssignCertificateCourseForm assignCertificateCourseForm) throws Exception{
		IAssignCertificateCourseTransaction transaction=AssignCertificateCourseTransactionImpl.getInstance();
		AssignCertificateCourse bo=transaction.getAssignCertificateCourse(assignCertificateCourseForm.getId());
		AssignCertificateCourseHelper.getInstance().convertBoToTo(bo,assignCertificateCourseForm);
	}
}
