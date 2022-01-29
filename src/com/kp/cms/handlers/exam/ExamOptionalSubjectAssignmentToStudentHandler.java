package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplicantSubjectGroup;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.forms.exam.ExamOptionalSubjectAssignmentToStudentForm;
import com.kp.cms.helpers.exam.ExamOptionalSubjectAssignmentStudentHelper;
import com.kp.cms.to.exam.ExamOptAssSubTypeTO;
import com.kp.cms.transactionsimpl.exam.ExamOptionalSubjectAssignmentStudentImpl;


public class ExamOptionalSubjectAssignmentToStudentHandler extends
		ExamGenHandler {

	ExamOptionalSubjectAssignmentStudentHelper helper = new ExamOptionalSubjectAssignmentStudentHelper();
	ExamOptionalSubjectAssignmentStudentImpl impl = new ExamOptionalSubjectAssignmentStudentImpl();

	// To get the main list
	public ArrayList<ExamOptAssSubTypeTO> getStudentList(
			ArrayList<Integer> classIdList, ExamOptionalSubjectAssignmentToStudentForm objForm) throws Exception {
		return helper.convertBOToTO_getStudent_Details(impl
				.select_students_spec(classIdList), impl
				.select_specId_subGrpId(classIdList, objForm.getAcademicYear()), impl
				.select_subGrpId_SubGrpName(classIdList, objForm.getAcademicYear()), objForm);

	}

	// To add
	// public void add(ArrayList<ExamOptAssSubTypeTO> list) throws Exception {
	//
	// ExamStudentOptionalSubjectGroupBO objBO = null;
	//
	// for (ExamOptAssSubTypeTO to : list) {
	// if (to.getIsChecked()) {
	// objBO = new ExamStudentOptionalSubjectGroupBO(to.getId(), to
	// .getOptSubGroupId());
	// if (!impl.isDuplicated(to.getId(), to.getOptSubGroupId())) {
	// impl.insert(objBO);
	// }
	// } else {
	// impl.delete_optSubGrp(to.getId(), to.getOptSubGroupId());
	// }
	// }
	// }

	public void add(ArrayList<ExamOptAssSubTypeTO> list) throws Exception {

		List<ApplicantSubjectGroup> applnSubList  = new ArrayList<ApplicantSubjectGroup>();
		AdmAppln admAppln;
		SubjectGroup subjectGroup;
		ApplicantSubjectGroup applicantSubjectGroup;
		StringBuffer admApplNos = new StringBuffer();
		int size = list.size();
		int i = 0;
		if(list!= null && list.size() > 0){ 
			for (ExamOptAssSubTypeTO to : list) {
				if (to.getIsChecked()) {
					admAppln = new AdmAppln();
					applicantSubjectGroup = new ApplicantSubjectGroup();
					subjectGroup = new SubjectGroup();
					admAppln.setId(to.getAdmApplnId());
					applicantSubjectGroup.setAdmAppln(admAppln);
					subjectGroup.setId(to.getOptSubGroupId());
					applicantSubjectGroup.setSubjectGroup(subjectGroup);
					applnSubList.add(applicantSubjectGroup);
				}
				/*else{
					impl.deleteApplicantSubjectGroup(to.getAdmApplnId(), to.getOptSubGroupId());
				}*/
				i++;
				admApplNos.append(to.getAdmApplnId());
				if(i < size){
					admApplNos.append(",");
				}
			}
		}
		
		Map<Integer, Map<Integer, Integer>> admMap = impl.getApplicantSubjectGroup(admApplNos.toString());
		if(applnSubList.size() > 0){
			Iterator<ApplicantSubjectGroup> itr = applnSubList.iterator();
			while (itr.hasNext()) {
				ApplicantSubjectGroup applicantSubjectGroup2 = (ApplicantSubjectGroup) itr
						.next();
				Map<Integer, Integer> subMap = admMap.get(applicantSubjectGroup2.getAdmAppln().getId());
				if(subMap == null || subMap.size() <= 0 || !subMap.containsKey(applicantSubjectGroup2.getSubjectGroup().getId())){
					impl.insert(applicantSubjectGroup2);
				}

				
			}
		}
		
		/*for (ExamOptAssSubTypeTO to : list) {
			if (to.getIsChecked()) {
				objBO = new ExamStudentOptionalSubjectGroupBO(to.getId(), to
						.getOptSubGroupId());
				if (!impl.isDuplicated(to.getId(), to.getOptSubGroupId())) {
					impl.insert(objBO);
				}
			} else {
				impl.delete_optSubGrp(to.getId(), to.getOptSubGroupId());
			}
		}*/
	}

	public String getClassNameByIds(String[] classIds) throws Exception {

		try {
			StringBuilder classNames = new StringBuilder();
			for (String id : classIds) {
				classNames.append(((ClassUtilBO) impl.select_Unique(Integer.parseInt(id), ClassUtilBO.class)).getName()).append(", ");
			}
			classNames.setCharAt(classNames.length()-2, ' ');
			return classNames.toString().trim();
		} catch (Exception e) {
			throw e;
		}

	}

}
