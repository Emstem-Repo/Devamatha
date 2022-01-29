package com.kp.cms.handlers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.bo.exam.ExamMajorDepatmentCodeBO;
import com.kp.cms.forms.admin.SubjectEntryForm;
import com.kp.cms.helpers.admin.SubjectHelper;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.admin.ISubjectTransaction;
import com.kp.cms.transactions.exam.IConsolidatedSubjectStreamTransaction;
import com.kp.cms.transactionsimpl.admin.SubjectTransactionImp;
import com.kp.cms.transactionsimpl.exam.ConsolidatedSubjectStreamTransactionImpl;

public class SubjectHandler {

	public static volatile SubjectHandler subjectHandler = null;

	public static SubjectHandler getInstance() {
		if (subjectHandler == null) {
			subjectHandler = new SubjectHandler();
			return subjectHandler;
		}
		return subjectHandler;
	}

	/**
	 * get all the subjects for UI display
	 * @param schemeNo 
	 * 
	 * @return getstudentTypeList
	 * @throws Exception
	 */
	public List<SubjectTO> getSubjectList(String schemeNo) throws Exception {
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		List<Subject> subjectBoList = subjectIntf.getSubjects(schemeNo);
		List<SubjectTO> getstudentTypeList = SubjectHelper.getInstance()
				.convertBOstoTos(subjectBoList);
		return getstudentTypeList;
	}

	/**
	 * duplication checking
	 * 
	 * @param code
	 * @param string 
	 * @return
	 * @throws Exception
	 */
	public Subject existanceCheck(String code,String subjectName, String subType) throws Exception {
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		return subjectIntf.existanceCheck(code,subjectName,subType);
	}
	//basim added
	public Subject subjectCodeDuplicate(String code)throws Exception
	{
		ISubjectTransaction subjectTrans=new SubjectTransactionImp();
		return subjectTrans.subjectCodeDuplicated(code);
	}
	//basim end

	/**
	 * 
	 * @param subjectcode
	 * @param subjectName
	 * @param isSecondlanguage
	 * @param optional
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	// public boolean addSubject(String subjectcode, String subjectName,
	// String isSecondlanguage, String optional, String userId)
	// throws Exception {
	// ISubjectTransaction subjectIntf = new SubjectTransactionImp();
	// boolean isSubjectAdded = false;
	// Subject subject = SubjectHelper.getInstance().createSubjectObject(
	// subjectcode, subjectName, isSecondlanguage, optional, userId);
	// if (subjectIntf != null) {
	// isSubjectAdded = subjectIntf.addSubject(subject);
	// }
	// return isSubjectAdded;
	//
	// }
	/**
	 * 
	 * @param aform
	 * @return
	 * @throws Exception
	 */
	public Boolean getActionForm(SubjectEntryForm subForm) throws Exception {
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		Subject subject = SubjectHelper.getInstance().createSubjectObject(
				subForm.getId());
		subject = subjectIntf.loadSubject(subject);
	
		SubjectHelper.getInstance().setFormbean(subject, subForm);
		return true;
	}

	/**
	 * this will update the subject based on the id
	 * 
	 * @param subjectEntryForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateSubject(SubjectEntryForm subjectEntryForm) throws Exception {
		Boolean isUpdated = false;
		Subject subject = SubjectHelper.getInstance().convertFormtoBOs(
				subjectEntryForm);
		if (subject != null) {
			ISubjectTransaction subjectIntf = new SubjectTransactionImp();
			isUpdated = subjectIntf.updateSubject(subject);

		}
		return isUpdated;
	}

	/**
	 * this will delete subject
	 * 
	 * @param id
	 * @param userId
	 * @param subjectEntryForm 
	 * @return
	 * @throws Exception
	 */
	public boolean deleteSubject(int subId, String userId, SubjectEntryForm subjectEntryForm) throws Exception {
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		Subject subject = SubjectHelper.getInstance().createSubjectObject(subId);
		subject = subjectIntf.loadSubject(subject);
		subject.setIsActive(false);
		subject.setModifiedBy(userId);
		subject.setLastModifiedDate(new Date());
		subjectEntryForm.setSchemeNo(Integer.toString(subject.getSchemeNo()));
		Boolean isDeleted = subjectIntf.updateSubject(subject);
		return isDeleted;
	}

	/**
	 * reactivate method
	 * 
	 * @param code
	 * @param userId
	 * @param subjectEntryForm 
	 * @return
	 * @throws Exception
	 */
	public boolean reActivateSubjectEntry(String code, String userId, SubjectEntryForm subjectEntryForm)
			throws Exception {
		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		return subjectIntf.reActivateSubjectEntry(code, userId,subjectEntryForm);
	}

	// Added by Shwetha 9Elements
	// To get the subject type list
	public ArrayList<KeyValueTO> getSubjectTypeList() {
		SubjectTransactionImp impl = new SubjectTransactionImp();
		ArrayList<SubjectType> lBO = new ArrayList(impl
				.select_ActiveOnly(SubjectType.class));
		return SubjectHelper.getInstance().convertBOToTO_SubjectType(lBO);

	}

	// Added by Shwetha 9Elements
	// To get the major department code list
	public ArrayList<KeyValueTO> getMajorDeptCodeList() {
		SubjectTransactionImp impl = new SubjectTransactionImp();
		ArrayList<ExamMajorDepatmentCodeBO> lBO = new ArrayList(impl
				.select_ActiveOnly(ExamMajorDepatmentCodeBO.class));
		return SubjectHelper.getInstance().convertBOToTO_MajorDeptCode(lBO);
	}

	public boolean addSubject(String subjectcode, String subjectName,
			String isSecondlanguage, String optional, String userId,
			int subjectTypeId, String isTheoryPractical,
			String consldtdMarkCardSubName, String subNamePrefix,
			String majorDeptCodeId,String isAdditionalSubject ,String isCertificateCourse , String cocurricularsubject, CertificateCourse certificateCourse,
			String hourpersem,String questionbyrequired, int schemeNo, int departmentId, String eligibleCourseId,String subjectCodeGroupId, String isExternalOrInternalSubject,int consolidatedSubjectStreamId) throws Exception {

		ISubjectTransaction subjectIntf = new SubjectTransactionImp();
		boolean isSubjectAdded = false;
		Subject subject = SubjectHelper.getInstance()
				.createSubjectDefinitionObject(subjectcode, subjectName,
						subjectTypeId, isTheoryPractical,
						consldtdMarkCardSubName, subNamePrefix,
						isSecondlanguage, optional, userId,isAdditionalSubject ,cocurricularsubject,
						isCertificateCourse,certificateCourse,hourpersem,questionbyrequired, schemeNo, departmentId,eligibleCourseId,subjectCodeGroupId,isExternalOrInternalSubject,consolidatedSubjectStreamId);
		try {
			if (majorDeptCodeId != null && majorDeptCodeId.length() > 0) {
				int m = Integer.parseInt(majorDeptCodeId);
				subject.setMajorDeptCodeId(m);
			}
		} catch (RuntimeException e) {
		}
			isSubjectAdded = subjectIntf.addSubject(subject);
		return isSubjectAdded;

	}
	// getting year by selected CertificateCourse id
	public int getYear(int id){
		
		ISubjectTransaction subjectTxn = new SubjectTransactionImp();
		int year=subjectTxn.getYear(id);
		
		return year;
	}

	public Map<Integer, String> getEligibleCourseMap() throws Exception{
		ISubjectTransaction impl = new SubjectTransactionImp();
		return impl.getEligibleCourseMap();
	}

	/**
	 * @return
	 * code added by mehaboob to subject code group map
	 * @throws Exception 
	 */
	public Map<Integer, String> getSubjectCodeGroupMap() throws Exception {
		ISubjectTransaction impl = new SubjectTransactionImp();
		List<SubjectCodeGroup> codeGroupList=impl.getSubjectCodeGroupMap();
		Map<Integer, String> subjectCodeGroupMap=new LinkedHashMap<Integer, String>();
		if(codeGroupList!=null && !codeGroupList.isEmpty()){
			for (SubjectCodeGroup subjectCodeGroup : codeGroupList) {
				subjectCodeGroupMap.put(subjectCodeGroup.getId(), subjectCodeGroup.getSubjectsGroupName());
			}
		}
		return subjectCodeGroupMap;
		
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<String> getSubjectCodes() throws Exception {
		ISubjectTransaction impl = new SubjectTransactionImp();
		List<String> subjectCodeList=impl.getSubjectCodes();
		return subjectCodeList;
	}

	public List<String> getSubjectNameList() throws Exception {
		ISubjectTransaction impl = new SubjectTransactionImp();
		return impl.getSubjectNameList();
	}
	
	public List<ConsolidatedSubjectStream> getSubjectStreamsForConsolidatedMarksCard(SubjectEntryForm subjectEntryForm) throws Exception
	{
		List<ConsolidatedSubjectStream> subjectStreams = null;
		IConsolidatedSubjectStreamTransaction tx = ConsolidatedSubjectStreamTransactionImpl.getInstance();
		subjectStreams = tx.getConsolidatedSubjectStreams();
		return subjectStreams;
	}
}
