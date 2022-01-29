package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamStudentSubGrpHistoryBO;
import com.kp.cms.forms.admission.CancelPromotionForm;
import com.kp.cms.to.admission.CancelPromotionTo;
import com.kp.cms.to.attendance.SubjectGroupDetailsTo;

public class CancelAdmissionHelper {
 private static volatile CancelAdmissionHelper cancelAdmissionHelper = null;
 public static CancelAdmissionHelper getInstance(){
	 if(cancelAdmissionHelper == null){
		 cancelAdmissionHelper = new CancelAdmissionHelper();
		 return cancelAdmissionHelper;
	 }
	 return cancelAdmissionHelper;
 }
/**
 * @param student
 * @return
 */
public List<CancelPromotionTo> populateBoTOTo(Student stu) {
	List<CancelPromotionTo> list = new ArrayList<CancelPromotionTo>();
	CancelPromotionTo promotionTo = new CancelPromotionTo();
	if(stu!=null){
		promotionTo.setStudentId(stu.getId());
		if(stu.getAdmAppln()!=null){
			promotionTo.setAdmapplnId(stu.getAdmAppln().getId());
			if(stu.getAdmAppln().getPersonalData()!=null){
				if(stu.getAdmAppln().getPersonalData().getFirstName()!=null && !stu.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					promotionTo.setStudentName(stu.getAdmAppln().getPersonalData().getFirstName());
				}
			}
		}
		if(stu.getClassSchemewise()!=null){
			promotionTo.setSemesterNo(stu.getClassSchemewise().getCurriculumSchemeDuration().getSemesterYearNo());
			if(stu.getClassSchemewise().getClasses()!=null){
				if(stu.getClassSchemewise().getClasses().getName()!=null && !stu.getClassSchemewise().getClasses().getName().isEmpty()){
					promotionTo.setClasses(stu.getClassSchemewise().getClasses().getName());
				}
			}
		}
		if(stu.getAdmAppln().getApplicantSubjectGroups()!=null){
			Set<ApplicantSubjectGroup> set = stu.getAdmAppln().getApplicantSubjectGroups();
			List<SubjectGroupDetailsTo> subjectGroupList = new ArrayList<SubjectGroupDetailsTo>();
			Iterator<ApplicantSubjectGroup> iterator = set.iterator();
			while (iterator.hasNext()) {
				ApplicantSubjectGroup applicantSubjectGroup = (ApplicantSubjectGroup) iterator.next();
				SubjectGroupDetailsTo to= new SubjectGroupDetailsTo();
				if(applicantSubjectGroup.getSubjectGroup()!=null){
					to.setSubjectGroupId(applicantSubjectGroup.getSubjectGroup().getId());
					if(applicantSubjectGroup.getSubjectGroup().getName()!=null && !applicantSubjectGroup.getSubjectGroup().getName().isEmpty()){
						to.setSubjectGroupName(applicantSubjectGroup.getSubjectGroup().getName());
					}
				}
				subjectGroupList.add(to);
			}
			promotionTo.setSubjectGroupTo(subjectGroupList);
		}
		list.add(promotionTo);
	}
	
	return list;
}
/**
 * @param previousClass
 * @return
 */
public CancelPromotionTo copyBOToTO( ExamStudentPreviousClassDetailsBO previousClass) {
	CancelPromotionTo to = new CancelPromotionTo();
	if(previousClass!=null){
		to.setExamStuClassId(previousClass.getId());
		to.setClassId(previousClass.getClassId());
	}
	return to;
}
/**
 * @param classSchemewise
 * @param promotionTo
 * @return
 */
public CancelPromotionTo copyClassSchemeDetailsBOToTO(
		ClassSchemewise classSchemewise, CancelPromotionTo promotionTo) {
	if(classSchemewise!=null){
		promotionTo.setClassSchemwiseId(classSchemewise.getId());
	}
	return promotionTo;
}
/**
 * @param previousSubGrp
 * @return
 */
public List<CancelPromotionTo> copySubjectGrpHistoryTOTo( List<ExamStudentSubGrpHistoryBO> previousSubGrp) {
	List<CancelPromotionTo> tos =new ArrayList<CancelPromotionTo>();
	if(previousSubGrp!=null){
		Iterator<ExamStudentSubGrpHistoryBO> iterator = previousSubGrp.iterator();
		while (iterator.hasNext()) {
			ExamStudentSubGrpHistoryBO examStudentSubGrpHistoryBO = (ExamStudentSubGrpHistoryBO) iterator .next();
			CancelPromotionTo promotionTo = new CancelPromotionTo();
			promotionTo.setExamSubGrpId(examStudentSubGrpHistoryBO.getId());
			promotionTo.setSubGrpId(examStudentSubGrpHistoryBO.getSubjectGroupId());
			tos.add(promotionTo);
		}
	}
	return tos;
}

}
