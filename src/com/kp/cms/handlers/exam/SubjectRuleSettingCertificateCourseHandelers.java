package com.kp.cms.handlers.exam;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.forms.exam.SubjectRuleSettingsForm;
import com.kp.cms.helpers.exam.SubjectRuleSettingCertificateCourseHelpers;
import com.kp.cms.to.exam.SubjectRuleSettingCertificateCourseTo;
import com.kp.cms.transactions.exam.ISubjectRuleSettingCertificateCourseTransaction;
import com.kp.cms.transactionsimpl.exam.SubjectRuleSettingCertificateCourseTransactionImpl;

 public class SubjectRuleSettingCertificateCourseHandelers {
	private static final Log log = LogFactory.getLog(SubjectRuleSettingCertificateCourseHandelers.class);
	ISubjectRuleSettingCertificateCourseTransaction curTransaction=SubjectRuleSettingCertificateCourseTransactionImpl.getInstance();
	
	private static volatile SubjectRuleSettingCertificateCourseHandelers instance=null;

	private SubjectRuleSettingCertificateCourseHandelers(){
		
	}
	
	public static SubjectRuleSettingCertificateCourseHandelers getInstance(){
		log.info("Start getInstance of PhdEmployeeHandelers");
		if(instance==null){
			instance=new SubjectRuleSettingCertificateCourseHandelers();
		}
		log.info("End getInstance of PhdEmployeeHandelers");
		return instance;
	}
	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public List<SubjectRuleSettingCertificateCourseTo> searchSubCerDetails (SubjectRuleSettingsForm objform) throws Exception{
		   List<CertificateCourse> cerBoList=curTransaction.getCertificateCourseList(objform);
		   List<SubjectRuleSettingCertificateCourseTo> cerToList=SubjectRuleSettingCertificateCourseHelpers.getInstance().convertBosToTOs(cerBoList);
			return cerToList;
		}
	/**
	 * @param objform
	 * @throws Exception
	 */
	public List<SubjectGroup> getSubjectsForInput(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		return curTransaction.getSubjectGroupsForInput(subjectRuleSettingsForm);
	}
	/**
	 * @param subjectRuleSettingsForm
	 * @return
	 */
	public boolean saveSubjectRules(SubjectRuleSettingsForm subjectRuleSettingsForm) throws Exception {
		return SubjectRuleSettingCertificateCourseHelpers.getInstance().getBoObjectForUpdate(subjectRuleSettingsForm);
	}
}
