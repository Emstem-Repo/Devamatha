package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.exam.CurriculumSchemeUtilBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.PersonalDataUtilBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.exam.ExamSupplementaryImpAppForm;
import com.kp.cms.helpers.exam.ExamSupplementaryImpApplicationHelper;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamSupplementaryImprovementApplicationImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamSupplementaryImpAppHandler extends ExamGenHandler {
	ExamSupplementaryImpApplicationHelper helper = new ExamSupplementaryImpApplicationHelper();
	ExamSupplementaryImprovementApplicationImpl impl = new ExamSupplementaryImprovementApplicationImpl();

	public List<KeyValueTO> getExamNameList_SI() {

		List<ExamDefinitionBO> lBO = impl.select_ActiveOnly_ExamName_SI();
		return helper.convertBOToTO_ExamName(lBO);

	}

	// SEARCH: get students for course and scheme
	public ExamSupplementaryImpAppForm getStudentNameList(
			ExamSupplementaryImpAppForm objform) throws Exception {

		String regNo = objform.getRegNo();
		String rollNo = objform.getRollNo();

		boolean rollNoPresent = false;
		boolean regNoPresent = false;
		// boolean useRollNo = false;
		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;
			// useRollNo = true;
		}
		if (regNo != null && regNo.length() > 0) {
			regNoPresent = true;
		}
		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent Roll and Reg No");
			}
		}

		int schemeNo = (objform.getSchemeNo() != null
				&& objform.getSchemeNo().trim().length() > 0 ? Integer
				.parseInt(objform.getSchemeNo()) : 0);
		objform.setSchemeNo(objform.getSchemeNo());

		String examName = getExamNameByExamId(Integer.parseInt(objform
				.getExamNameId()));
		String course = null, scheme = null;
		Integer courseId = null, schemeId = null;
		if (objform.getCourseId() != null
				&& objform.getCourseId().trim().length() > 0) {
			course = getCourseName(Integer.parseInt(objform.getCourseId()));
			courseId = Integer.parseInt(objform.getCourseId());
		}
		if (CommonUtil.checkForEmpty(objform.getSchemeNo())) {

			schemeId = getSchemeId(courseId);
			scheme = getSchemeName(schemeId);
		}
		boolean isSupplementary = false;

		if (objform.getSupplementaryImprovement().equalsIgnoreCase(
				"Supplementary")) {

			isSupplementary = true;
		}
		ExamSupplementaryImpApplicationTO suppTO = new ExamSupplementaryImpApplicationTO(
				regNo, rollNo, rollNoPresent, regNoPresent, courseId, schemeNo,
				schemeId, isSupplementary);
		objform = getSubjects(suppTO, objform);
		
		objform.setSupplementaryImprovement(objform
				.getSupplementaryImprovement());
		objform.setExamNameId_value(objform.getExamNameId());
		objform.setExamName(examName);
		objform.setCourseName(course);
		objform.setSchemeNo(objform.getSchemeNo());
		objform.setSchemeName(scheme);
		return objform;
	}

	public void delete(int id, int schemeNo) {
		impl.delete_suppImpr(id, schemeNo);
	}

	// ADD: get subjects list
	public ExamSupplementaryImpAppForm getlistSubjects(
			ExamSupplementaryImpAppForm objform) throws Exception {

		String regNo = objform.getRegNo().trim();
		String rollNo = objform.getRollNo().trim();

		boolean rollNoPresent = false;
		boolean regNoPresent = false;

		if (rollNo != null && rollNo.length() > 0) {
			rollNoPresent = true;

		}
		if (regNo != null && regNo.length() > 0) {
			regNoPresent = true;
		}
		if (rollNoPresent && regNoPresent) {
			if (!validate_Stu_rollReg(rollNo, regNo)) {
				throw new BusinessException("Inconsistent Roll and Reg No");
			}
		}

		int schemeNo = (objform.getSchemeNo() != null
				&& objform.getSchemeNo().trim().length() > 0 ? Integer
				.parseInt(objform.getSchemeNo()) : 0);
		int supImpId = 0;
		

		StudentUtilBO studentBo = impl.getStudentRollORReg(rollNo, regNo,
				rollNoPresent);
		objform.setStudentId(studentBo.getId().toString());
		
		if(impl.checkDuplicate(studentBo.getId(), Integer.parseInt(objform.getExamNameId()), schemeNo)){
			throw new DuplicateException();
		}
		// Previous Class History done by 9 Elements
		objform.setListSubject(helper.convertBOToTOForAdd( impl.getSubjectListForaStudent(regNo, schemeNo, rollNo)));
		//curren class code done by balaji
		List<ExamSupplementaryImpApplicationSubjectTO> list=helper.convertBOToTOForAdd(impl.getSubjectListForCurrentClassForAStudent(regNo, schemeNo, rollNo));
		
		if(list!=null && !list.isEmpty()){
			if(objform.getListSubject()!=null && !objform.getListSubject().isEmpty() && list!=null && !list.isEmpty()){
				list.addAll(objform.getListSubject());
			}
			objform.setListSubject(list);
		}
		objform.setCourseName(getCourseName((objform.getCourseId() != null
				&& objform.getCourseId().trim().length() > 0 ? Integer
				.parseInt(objform.getCourseId()) : 0)));

		objform.setSchemeName(objform.getSchemeNo());

		objform.setSupplementaryImprovementId(supImpId);
		
		PersonalDataUtilBO personalBo = studentBo.getAdmApplnUtilBO()
				.getPersonalDataUtilBO();
		StringBuffer nameBuffer = new StringBuffer();
		if (personalBo.getFirstName() != null) {
			nameBuffer.append(personalBo.getFirstName() + " ");
		}
		if (personalBo.getLastName() != null) {
			nameBuffer.append(personalBo.getLastName());
		}
		objform.setStudentName(nameBuffer.toString());
		
		objform.setChance("1");
		return objform;
	}

	private List<Integer> getSubjectIdList(

	List<ExamSupplementaryImpApplicationSubjectTO> listSupplmntTO) {

		List<Integer> subjectIdList = new ArrayList<Integer>();
		if (listSupplmntTO != null && listSupplmntTO.size() > 0) {
			for (ExamSupplementaryImpApplicationSubjectTO to : listSupplmntTO) {
				if (to != null)
					subjectIdList.add(to.getSubjectId());
			}
		}

		return subjectIdList;
	}

	public static void main(String[] args) {
		ExamSupplementaryImpAppForm objform = new ExamSupplementaryImpAppForm();
		ExamSupplementaryImpAppHandler h = new ExamSupplementaryImpAppHandler();
		int schemeId = h.getSchemeId(9);
		ExamSupplementaryImpApplicationTO suppTO = new ExamSupplementaryImpApplicationTO(
				"0914406", null, false, true, 9, 2, schemeId, true);
		objform.setCourseId("9");

		objform = h.getSubjects(suppTO, objform);
	}

	private ExamSupplementaryImpAppForm getSubjects(
			ExamSupplementaryImpApplicationTO suppTO,
			ExamSupplementaryImpAppForm objform) {
		Integer courseId = null;
		if (objform.getCourseId() != null
				&& objform.getCourseId().trim().length() > 0) {
			courseId = Integer.parseInt(objform.getCourseId());
		}
		List<Object[]> sbo = impl.select_student(suppTO.getRollNumber(), suppTO
				.getRegNumber(), suppTO.getRollNoPresent(), suppTO
				.getRegNoPresent(), objform.getSchemeNo() != null
				&& objform.getSchemeNo().trim().length() > 0 ? Integer
				.parseInt(objform.getSchemeNo()) : 0, suppTO
				.getIsSupplementary(), courseId, objform.getExamNameId());
		ArrayList<ExamSupplementaryImpApplicationTO> returnList = new ArrayList<ExamSupplementaryImpApplicationTO>();
		for (Object[] row : sbo) {

			int studentId = (Integer) row[0];
			String rollNo = "", regNo = "", className = "", stuName = "";
			if (row[2] != null) {
				rollNo = row[2].toString();
			}
			if (row[3] != null) {
				regNo = row[3].toString();
			}
			if (row[1] != null) {
				className = row[1].toString();
			}
			if (row[4] != null) {
				stuName = row[4].toString();
			}
			returnList.add(new ExamSupplementaryImpApplicationTO(studentId,
					className, rollNo, regNo, stuName));

		}
		objform.setListStudentName(returnList);
		Collections.sort(returnList);

		return objform;
	}

	public int getSchemeId(int courseId) {
		int schemeId = 0;
		ArrayList<CurriculumSchemeUtilBO> listBO = impl
				.select_Scheme_By_Course(courseId);
		for (CurriculumSchemeUtilBO bo : listBO) {
			schemeId = (bo == null ? 0 : bo.getCourseSchemeId());
		}
		return schemeId;
	}

	public void add(List<ExamSupplementaryImpApplicationSubjectTO> listSubject,
			Integer examId, String UserId, Integer studentId,
			String supplimentaryImpnt, int schemeNo) throws Exception {
		ArrayList<ExamSupplementaryImprovementApplicationBO> addList = new ArrayList<ExamSupplementaryImprovementApplicationBO>();
		ArrayList<ExamSupplementaryImprovementApplicationBO> updatelist = new ArrayList<ExamSupplementaryImprovementApplicationBO>();
		for (ExamSupplementaryImpApplicationSubjectTO to : listSubject) {
			ExamSupplementaryImprovementApplicationBO objBO = null;

			/*if (to.getAppearedTheory() != null
					|| to.getAppearedPractical() != null) {*/

				objBO = new ExamSupplementaryImprovementApplicationBO();
				objBO.setExamId(examId);
				objBO.setStudentId(studentId);
				objBO.setSubjectId(to.getSubjectId());
				if(to.getFailedTheory() != null){
					objBO.setIsFailedTheory(1);
				}else{
					objBO.setIsFailedTheory((to.getIsFailedTheory()) ? 1 : 0);
				}	
				 if(to.getFailedPractical() != null){
					 objBO.setIsFailedPractical(1); 
				 }
				 else{
					 objBO.setIsFailedPractical((to.getIsFailedPractical()) ? 1: 0);
				 }
				/*objBO.setIsAppearedTheory((to.getAppearedTheory() != null) ? 1
						: 0);
				objBO
						.setIsAppearedPractical((to.getAppearedPractical() != null) ? 1
								: 0);*/
				if(to.getIsAppearedTheory()){
					objBO.setIsAppearedTheory(1);
				}
				else{
					objBO.setIsAppearedTheory(0);
				}
				if(to.getIsAppearedPractical()){
					objBO.setIsAppearedPractical(1);
				}
				else{
					objBO.setIsAppearedPractical(0);
				}
				objBO.setFees(to.getFees());
				objBO.setSchemeNo(schemeNo);
				// objBO.setChance(to.getChance() + 1);
				objBO.setChance(to.getChance());
				objBO.setIsTheoryOverallFailed(to.getIsOverallTheoryFailed());
				objBO.setIsPracticalOverallFailed(to.getIsOverallPracticalFailed());
				
				if(to.getClassId() > 0){
					Classes classes = new Classes();
					classes.setId(to.getClassId());
					objBO.setClasses(classes);
				}
				
				if (supplimentaryImpnt != null
						&& supplimentaryImpnt.trim().length() > 0) {
					if (supplimentaryImpnt.equalsIgnoreCase("Supplementary")) {
						objBO.setIsSupplementary(1);
						objBO.setIsImprovement(0);
					} else if (supplimentaryImpnt
							.equalsIgnoreCase("Improvement")) {
						objBO.setIsImprovement(1);
						objBO.setIsSupplementary(0);
					}

				}
				if (to.getId() == null) {
					objBO.setCreatedBy(UserId);
					objBO.setCreatedDate(new Date());
					addList.add(objBO);
				} else {
					objBO.setId(to.getId());
					objBO.setModifiedBy(UserId);
					objBO.setLastModifiedDate(new Date());
					updatelist.add(objBO);
				}

			//}

		}
		if (addList.size() > 0) {
			impl.insert_List(addList);
		}
		if (updatelist != null && updatelist.size() > 0) {
			impl.update_List(updatelist);
		}
	}

	public ExamSupplementaryImpAppForm getUpdatableForm(
			ExamSupplementaryImpAppForm objform) {

		int studentId = Integer.parseInt(objform.getStudentId());
		int schemeNo = (objform.getSchemeNo() != null
				&& objform.getSchemeNo().trim().length() > 0 ? Integer
				.parseInt(objform.getSchemeNo()) : impl
				.getSuppSchemeForStudent(studentId, objform.getExamNameId(), objform.getSupplementaryImprovement()));
		// int courseId = Integer.parseInt(objform.getCourseId());
		Integer courseId = impl.getCourseOfStudent(studentId);
		int schemeId = getSchemeId(courseId);
		StringBuffer chanceBuffer = new StringBuffer();
		int is_supp = 0;
		if (objform.getSupplementaryImprovement().equalsIgnoreCase(
				"Supplementary")) {
			is_supp = 1;
		}

		objform.setListSubject(helper.convertBOToTO_Subjects(impl
				.select_Subjects(courseId, schemeId, schemeNo, studentId,
						Integer.parseInt(objform.getExamNameId()), is_supp),
				chanceBuffer, is_supp));

		objform.setCourseName(getCourseName(courseId));
		objform.setSchemeName(getSchemeName(schemeId));
		int supImpId = 0;
		if (objform.getSupplementaryImprovement().equalsIgnoreCase(
				"Supplementary")) {
			supImpId = 1;
		}
		if (objform.getSupplementaryImprovement().equalsIgnoreCase(
				"Improvement")) {
			supImpId = 1;
		}
		objform.setSupplementaryImprovementId(supImpId);
		objform.setSupplementaryImprovement(objform
				.getSupplementaryImprovement());

		List<Object[]> list = impl.getStudentDetails(studentId);
		String regNo = "";
		String rollNo = "";
		String firstName = "";
		String sectionName = "";
		Iterator<Object[]> itr = list.iterator();
		while (itr.hasNext()) {
			Object[] row = itr.next();
			regNo = (row[0] == null ? "" : (String) row[0]);
			rollNo = (row[1] == null ? "" : (String) row[1]);
			firstName = (row[2] == null ? "" : (String) row[2]);
			sectionName = (row[3] == null ? "" : (String) row[3]);

		}
		objform.setRegNo(regNo);
		objform.setRollNo(rollNo);
		objform.setSection(sectionName);
		objform.setStudentName(firstName);
		objform.setChance(chanceBuffer.toString());
		objform.setSchemeNo(Integer.toString(schemeNo));
		return objform;
	}
}
