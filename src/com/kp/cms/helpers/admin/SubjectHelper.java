package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.struts.action.ActionForm;

import com.kp.cms.bo.admin.CertificateCourse;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SubjectCodeGroup;
import com.kp.cms.bo.admin.SubjectType;
import com.kp.cms.bo.exam.ConsolidatedSubjectStream;
import com.kp.cms.bo.exam.ExamMajorDepatmentCodeBO;
import com.kp.cms.forms.admin.SubjectEntryForm;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.to.exam.KeyValueTO;

public class SubjectHelper {
	public static volatile SubjectHelper subjectHelper = null;

	public static SubjectHelper getInstance() {
		if (subjectHelper == null) {
			subjectHelper = new SubjectHelper();
			return subjectHelper;
		}
		return subjectHelper;
	}

	/**
	 * 
	 * @param subjectbolist
	 * @return
	 */
	public List<SubjectTO> convertBOstoTos(List<Subject> subjectbolist) {
		List<SubjectTO> subjectList = null;
		if (subjectbolist != null) {
			subjectList = new ArrayList<SubjectTO>();
			Iterator<Subject> itr = subjectbolist.iterator();
			while (itr.hasNext()) {
				Subject subject = (Subject) itr.next();
				SubjectTO subjectTo = new SubjectTO();
				subjectTo.setId(subject.getId());
				subjectTo.setName(subject.getName());
				subjectTo.setCode(subject.getCode());
				if(subject.getHourpersem()!=null){
				subjectTo.setHourpersem(String.valueOf(subject.getHourpersem()));
				}
				if(subject.getQuestionbyrequired()!=null){
				subjectTo.setQuestionbyrequired(subject.getQuestionbyrequired() ? "Yes" : "No");
				}
				// subjectTo.setTotalmarks(Integer.toString(subject.getTotalMarks
				// ()));
				// subjectTo.setPassingmarks(Integer.toString(subject.
				// getPassingMarks()));
				subjectTo.setOptional(subject.getIsOptionalSubject() ? "Yes"
						: "No");
				subjectTo
						.setSecondlanguage(subject.getIsSecondLanguage() ? "Yes"
								: "No");
				// Added by Shwetha 9Elements
				subjectTo.setTheoryPractical(convertTheoryPraticalCode(subject
						.getIsTheoryPractical()));
				subjectTo.setSubjectType(subject.getSubjectType().getName());
				if(subject.getSchemeNo()>=0)
				{
				if(subject.getSchemeNo()==0)
					subjectTo.setSchemeNo("ALL");
				else
					subjectTo.setSchemeNo(String.valueOf(subject.getSchemeNo()));
				}
				if(subject.getDepartment()!=null && subject.getDepartment().getId()>0)
				{
					subjectTo.setDepartmentId(String.valueOf(subject.getDepartment().getId()));	
				}
				if(subject.getDepartment()!=null && subject.getDepartment().getId()>0)
				{
					subjectTo.setDepartmentName(subject.getDepartment().getName());	
				}
				if(subject.getConsolidatedSubjectStream() != null)
				{
					subjectTo.setConsolidatedSubjectStreamId(subject.getConsolidatedSubjectStream().getId());
				}
				if(subject.getSubjectTypeBasedOnMarks()!=null)
					subjectTo.setSubjectTypeBasedOnMarks(subject.getSubjectTypeBasedOnMarks());
				subjectList.add(subjectTo);
			}
		}
		return subjectList;
	}

	private String convertTheoryPraticalCode(String code) {
		if ("T".equalsIgnoreCase(code)) {
			return "Theory";
		} else if ("P".equalsIgnoreCase(code)) {
			return "Practical";
		} else {
			return "Both";
		}
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public Subject createSubjectObject(String code) {
		Subject subject = new Subject();
		subject.setCode(code);
		return subject;
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	public Subject createSubjectObject(int id) {
		Subject subject = new Subject();
		subject.setId(id);
		return subject;
	}

	/**
	 * 
	 * @param subjectcode
	 * @param subjectName
	 * @param isSecondlanguage
	 * @param optional
	 * @param userId
	 * @return
	 */
	public Subject createSubjectObject(String subjectcode, String subjectName,
			String isSecondlanguage, String optional, String userId) {
		Subject subject = new Subject();
		subject.setCode(subjectcode.trim());
		subject.setName(subjectName.trim());
		// subject.setTotalMarks(Integer.parseInt(totalmarks));
		// subject.setPassingMarks(Integer.parseInt(passingmarks));
		subject.setIsActive(true);
		subject.setCreatedBy(userId);
		subject.setModifiedBy(userId);
		subject.setCreatedDate(new Date());
		subject
				.setIsSecondLanguage(isSecondlanguage.equalsIgnoreCase("Yes") ? true
						: false);
		subject.setIsOptionalSubject(optional.equalsIgnoreCase("Yes") ? true
				: false);

		return subject;
	}

	/**
	 * 
	 * @param subjectBo
	 * @param actionform
	 * @return
	 */
	public SubjectEntryForm setFormbean(Subject subjectBo, SubjectEntryForm form) {
		if(subjectBo!=null){
		Subject subject = subjectBo;
		form.setId(subject.getId());
	    if(subject.getName()!=null){
		form.setName(subject.getName());
		form.setEditedname(subject.getName());
	    }
	    if(subject.getCode()!=null){
		form.setCode(subject.getCode());
	    form.setEditedcode(subject.getCode());
	    }
		// form.setTotalmarks(Integer.toString(subject.getTotalMarks()));
		// form.setPassingmarks(Integer.toString(subject.getPassingMarks()));
		if(subject.getIsOptionalSubject()!=null){
		form.setOptional((subject.getIsOptionalSubject()) ? "Yes" : "No");
		}
		if(subject.getIsSecondLanguage()!=null){
		form.setSecondlanguage(subject.getIsSecondLanguage() ? "Yes" : "No");
		}
		if(subject.getHourpersem()!=null)
		{
		form.setHourpersem(String.valueOf(subject.getHourpersem()));
		}
		if(subject.getQuestionbyrequired()!=null){
		form.setQuestionbyrequired(subject.getQuestionbyrequired() ? "Yes" : "No");
		}
		form.setSubjectTypeId(Integer.toString(subject.getSubjectTypeId()));
		if(subject.getIsTheoryPractical()!=null){
		form.setTheoryPractical(subject.getIsTheoryPractical());
		}
		if(subject.getIsTheoryPractical()!=null){
		form.setEditTheoryOrPractical(subject.getIsTheoryPractical());
		}
		form.setConsMCSName(subject.getConsldtdMarkCardSubName());
		form.setSubjectNameprefix(subject.getSubNamePrefix());
		if (subject.getSchemeNo()>=0) {
		    form.setSchemeNo(String.valueOf(subject.getSchemeNo()));
		}
		if (subject.getDepartment()!= null && subject.getDepartment().getId() > 0) {
			form.setDepartmentId(String.valueOf(subject.getDepartment().getId()));
		}
		if (subject.getMajorDeptCodeId() != null && subject.getMajorDeptCodeId() > 0) {
			form.setMajorDepartmentCodeId(Integer.toString(subject.getMajorDeptCodeId()));
		}
		if(subject.getIsCertificateCourse() == null){
			form.setIsCertificateCourse("No");
		}
		else{
			form.setIsCertificateCourse(subject.getIsCertificateCourse() ? "Yes" : "No");
//			if(subject.getCertificateCourse()!=null)
//			form.setCertificateId(String.valueOf(subject.getCertificateCourse().getId()));
		}
		if(subject.getIsAdditionalSubject()==null){
		   form.setIsAdditionalSubject("No");
		}else{
			form.setIsAdditionalSubject(subject.getIsAdditionalSubject()? "Yes" : "No");
		}if(subject.getCoCurricularSubject()==null){
		   form.setCoCurricularSubject("No");
		}else{
			form.setCoCurricularSubject(subject.getCoCurricularSubject()? "Yes" : "No");
		}
		if(subject.getEligibleCourse() != null && subject.getEligibleCourse().getId() !=0){
			form.setEligibleCourseId(String.valueOf(subject.getEligibleCourse().getId()));
		}
		//code added by mehaboob start
		if(subject.getSubjectCodeGroup()!=null && subject.getSubjectCodeGroup().getId()!=0){
			form.setSubjectCodeGroup(String.valueOf(subject.getSubjectCodeGroup().getId()));
		}
		//end
		if(subject.getConsolidatedSubjectStream() != null) {
			form.setConsolidatedSubjectStreamId(String.valueOf(subject.getConsolidatedSubjectStream().getId()));
		}
		if(subject.getSubjectTypeBasedOnMarks()!=null)
			form.setSubjectTypeBasedOnMarks(subject.getSubjectTypeBasedOnMarks());
		}
		return form;
	}

	/**
	 * 
	 * @param form
	 * @return
	 */
	public Subject convertFormtoBOs(SubjectEntryForm subjectEntryForm) {
		Subject subject = null;
			subject = new Subject();
			subject.setId(subjectEntryForm.getId());
			subject.setName(subjectEntryForm.getName().trim());
			subject.setCode(subjectEntryForm.getCode().trim());
			if(subjectEntryForm.getSchemeNo()!=null && !subjectEntryForm.getSchemeNo().isEmpty())
			{
			subject.setSchemeNo(Integer.parseInt(subjectEntryForm.getSchemeNo()));
			}
			if(subjectEntryForm.getDepartmentId()!=null && !subjectEntryForm.getDepartmentId().isEmpty())
			{
				Department d=new Department();
				d.setId(Integer.parseInt(subjectEntryForm.getDepartmentId()));
			    subject.setDepartment(d);
			}
			if(subjectEntryForm.getEligibleCourseId() != null && !subjectEntryForm.getEligibleCourseId().isEmpty()){
				Course course = new Course();
				course.setId(Integer.parseInt(subjectEntryForm.getEligibleCourseId()));
				subject.setEligibleCourse(course);
			}
			// subject.setTotalMarks(Integer.parseInt(subjectEntryForm.
			// getTotalmarks()));
			// subject.setPassingMarks(Integer.parseInt(subjectEntryForm.
			// getPassingmarks()));
			subject.setIsActive(true);
			subject.setIsOptionalSubject(subjectEntryForm.getOptional()
					.equalsIgnoreCase("Yes") ? true : false);
			subject.setIsSecondLanguage(subjectEntryForm.getSecondlanguage()
					.equalsIgnoreCase("Yes") ? true : false);
			if(subjectEntryForm.getHourpersem()!=null && !subjectEntryForm.getHourpersem().trim().isEmpty()){
				subject.setHourpersem(Integer.parseInt(subjectEntryForm.getHourpersem()));
			}
			subject.setQuestionbyrequired(subjectEntryForm.getQuestionbyrequired().
					 equalsIgnoreCase("Yes") ? true : false);
			subject.setModifiedBy(subjectEntryForm.getUserId());
			subject.setLastModifiedDate(new Date());

			// Added by Swetha - 9Elements
			subject.setSubjectTypeId(subjectEntryForm.getSubjectTypeId());
			subject.setIsTheoryPractical(subjectEntryForm.getTheoryPractical());
			subject.setConsldtdMarkCardSubName(subjectEntryForm
					.getConsMCSName());
			subject.setSubNamePrefix(subjectEntryForm.getSubjectNameprefix());
			String mjor=subjectEntryForm
			.getMajorDepartmentCodeId();
			if(mjor!=null&&mjor.length()>0){
				try {
					subject.setMajorDeptCodeId(new Integer(mjor));
				} catch (NumberFormatException e) {
					
				}
			}
			subject.setIsCertificateCourse(subjectEntryForm.getIsCertificateCourse()
					.equalsIgnoreCase("Yes") ? true : false);
//			if(subject.getIsCertificateCourse()){
//			if(subjectEntryForm.getCertificateId()!=null && !subjectEntryForm.getCertificateId().isEmpty()){
//			CertificateCourse certificateCourse=new CertificateCourse();
//			certificateCourse.setId(Integer.parseInt(subjectEntryForm.getCertificateId()));
//			subject.setCertificateCourse(certificateCourse);
//			
//			}}
			if(subjectEntryForm.getIsAdditionalSubject()!=null && !subjectEntryForm.getIsAdditionalSubject().isEmpty()){
			subject.setIsAdditionalSubject(subjectEntryForm.getIsAdditionalSubject().equalsIgnoreCase("Yes")? true : false);
			}if(subjectEntryForm.getCoCurricularSubject()!=null && !subjectEntryForm.getCoCurricularSubject().isEmpty()){
			subject.setCoCurricularSubject(subjectEntryForm.getCoCurricularSubject().equalsIgnoreCase("Yes")? true : false);
			}
			if(subjectEntryForm.getEligibleCourseId() != null && !subjectEntryForm.getEligibleCourseId().trim().isEmpty()){
				Course course = new Course();
				course.setId(Integer.parseInt(subjectEntryForm.getEligibleCourseId()));
				subject.setEligibleCourse(course );
			}else{
				subject.setEligibleCourse(null);
			}
			//code added by mehaboob start
            if(subjectEntryForm.getSubjectCodeGroup()!=null && !subjectEntryForm.getSubjectCodeGroup().isEmpty()){
            	SubjectCodeGroup codeGroup=new SubjectCodeGroup();
            	codeGroup.setId(Integer.parseInt(subjectEntryForm.getSubjectCodeGroup()));
            	subject.setSubjectCodeGroup(codeGroup);
            }else{
            	subject.setSubjectCodeGroup(null);
            }
            ConsolidatedSubjectStream consolidatedSubjectStream = new ConsolidatedSubjectStream();
            consolidatedSubjectStream.setId(Integer.parseInt(subjectEntryForm.getConsolidatedSubjectStreamId()));
            subject.setConsolidatedSubjectStream(consolidatedSubjectStream);
            if(subjectEntryForm.getSubjectTypeBasedOnMarks()!=null)
            	subject.setSubjectTypeBasedOnMarks(subjectEntryForm.getSubjectTypeBasedOnMarks());
            //end
		return subject;

	}

	// To get the subject type list
	public ArrayList<KeyValueTO> convertBOToTO_SubjectType(
			ArrayList<SubjectType> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (SubjectType bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		return listTO;
	}

	// To get the major department code list
	public ArrayList<KeyValueTO> convertBOToTO_MajorDeptCode(
			ArrayList<ExamMajorDepatmentCodeBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamMajorDepatmentCodeBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		return listTO;
	}

	public Subject createSubjectDefinitionObject(String subjectcode,
			String subjectName, int subjectTypeId, String isTheoryPractical,
			String consldtdMarkCardSubName, String subNamePrefix,
			String isSecondlanguage, String optional,
			String userId, String isAdditionalSubject,String isCertificateCourse , String cocurricularsubject, CertificateCourse certificateCourse,
			String hourpersem,String questionbyrequired,int schemeNo, int departmentId, String eligibleCourseId,String subjectCodeGroupId ,String isExternalOrInternalSubject,int consolidatedSubjectStreamId) {
		Subject subject = new Subject();
		subject.setCode(subjectcode.trim());
		subject.setName(subjectName.trim());
		// subject.setTotalMarks(Integer.parseInt(totalmarks));
		// subject.setPassingMarks(Integer.parseInt(passingmarks));
		subject.setIsActive(true);
		subject.setCreatedBy(userId);
		subject.setModifiedBy(userId);
		subject.setCreatedDate(new Date());
		subject.setIsSecondLanguage(isSecondlanguage.equalsIgnoreCase("Yes") ? true
						: false);
		subject.setIsOptionalSubject(optional.equalsIgnoreCase("Yes") ? true
				: false);
		if(hourpersem != null && !hourpersem.trim().isEmpty()){
			subject.setHourpersem(Integer.parseInt(hourpersem));
		}
		subject.setQuestionbyrequired(questionbyrequired.equalsIgnoreCase("Yes")? true :false);
		// Added by Swetha - 9Elements
		subject.setSubjectTypeId(subjectTypeId);
		subject.setIsTheoryPractical(isTheoryPractical);
		subject.setConsldtdMarkCardSubName(consldtdMarkCardSubName);
		subject.setSubNamePrefix(subNamePrefix);
		subject.setIsCertificateCourse(isCertificateCourse.equalsIgnoreCase("Yes") ? true: false);
//	    subject.setCertificateCourse(certificateCourse);
		if(isAdditionalSubject!=null && !isAdditionalSubject.isEmpty()){
        subject.setIsAdditionalSubject(isAdditionalSubject.equalsIgnoreCase("Yes") ? true: false);
		}if(cocurricularsubject!=null && !cocurricularsubject.isEmpty()){
        subject.setCoCurricularSubject(cocurricularsubject.equalsIgnoreCase("Yes") ? true:false);
		}
        if(schemeNo>=0)
		{
		subject.setSchemeNo(schemeNo);
		}
		if(departmentId>0)
		{
			Department d=new Department();
			d.setId(departmentId);
		    subject.setDepartment(d);
		}
		if(eligibleCourseId != null && !eligibleCourseId.trim().isEmpty()){
			Course course = new Course();
			course.setId(Integer.parseInt(eligibleCourseId));
			subject.setEligibleCourse(course);
		//code added by mehaboob start	
		}if(subjectCodeGroupId!=null && !subjectCodeGroupId.isEmpty()){
			SubjectCodeGroup subjectCodeGroup=new SubjectCodeGroup();
			subjectCodeGroup.setId(Integer.parseInt(subjectCodeGroupId));
			subject.setSubjectCodeGroup(subjectCodeGroup);
		}else{
			subject.setSubjectCodeGroup(null);
		}
		subject.setSubjectTypeBasedOnMarks(isExternalOrInternalSubject);
		ConsolidatedSubjectStream consolidatedSubjectStream = new ConsolidatedSubjectStream();
		consolidatedSubjectStream.setId(consolidatedSubjectStreamId);
		subject.setConsolidatedSubjectStream(consolidatedSubjectStream);
		//end
		return subject;
	}
}
