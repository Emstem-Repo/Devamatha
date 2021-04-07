package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.kp.cms.bo.exam.ExamEligibilityCriteriaMasterBO;
import com.kp.cms.bo.exam.ExamEligibilitySetupAdditionalEligibilityBO;
import com.kp.cms.bo.exam.ExamEligibilitySetupBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.forms.exam.ExamExamEligibilitySetUpForm;
import com.kp.cms.helpers.exam.ExamEligibilitySetupHelper;
import com.kp.cms.to.exam.ExamExamEligibilitySetUpTO;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamEligibilitySetupImpl;

public class ExamEligibilitySetupHandler extends ExamGenHandler {

	ExamEligibilitySetupImpl impl = new ExamEligibilitySetupImpl();

	ExamEligibilitySetupHelper helper = new ExamEligibilitySetupHelper();

	// To get the exam type list
	@SuppressWarnings("unchecked")
	public ArrayList<KeyValueTO> getExamTypeList() throws Exception {
		ArrayList<ExamTypeUtilBO> listBO = new ArrayList(impl
				.select_All(ExamTypeUtilBO.class));
		return helper.convertBOToTO_ExamType(listBO);

	}

	// To get additional eligibility list
	@SuppressWarnings("unchecked")
	public List<ExamExamEligibilityTO> getListAdditionalEligibility() throws Exception {
		List<ExamEligibilityCriteriaMasterBO> lBO = new ArrayList(impl
				.select_ActiveOnly(ExamEligibilityCriteriaMasterBO.class));
		return helper.convertBOToTO_checkAdditionalEligibility(lBO);
	}

	@SuppressWarnings("unchecked")
	public List<ExamExamEligibilityTO> getListAdditionalEligibilityToEdit(
			ArrayList<Integer> listAddElig) throws Exception {
		List<ExamEligibilityCriteriaMasterBO> lBO = new ArrayList(impl
				.select_ActiveOnly(ExamEligibilityCriteriaMasterBO.class));
		return helper.convertBOToTO_checkAdditionalEligibilityToEdit(lBO,
				listAddElig);
	}

	// To get the exam eligibility setup details
	@SuppressWarnings("unchecked")
	public List<ExamExamEligibilitySetUpTO> getListExamEligibilitySetUp() throws Exception {
		List<ExamEligibilitySetupBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamEligibilitySetupBO.class));
		return helper.convertBOToTO_ExamEligibilitySetUp(listBO);
	}

	public void delete(int id, String userId) throws Exception {
		impl.delete_AdditionalEligibilitySetUp(id);
		impl.delete_EligibilitySetUp(id);

	}

	public ExamExamEligibilitySetUpForm getUpdatableForm(
			ExamExamEligibilitySetUpForm objform, int classId, int examTypeId)
			throws Exception {
		ExamEligibilitySetupBO e = impl.select_ExamEligibilty(classId,
				examTypeId);

		objform.setClassId(Integer.toString(e.getClassId()));
		objform.setExamtypeId(Integer.toString(e.getExamTypeId()));
		objform
				.setNoEligibilityCheck(e.getIsNoEligibilityChecked() == 0 ? "off"
						: "on");
		objform.setExamFees(e.getIsExamFeesChecked() == 0 ? "off" : "on");
		objform.setAttendance(e.getIsAttendanceChecked() == 0 ? "off" : "on");
		objform.setCourseFees(e.getIsCourseFeesChecked() == 0 ? "off" : "on");
		return objform;
	}

	public void addElibilitySetup(String classId, int examTypeId,
			int noEligibilityCheck, int examFees, int attendance,
			int courseFees, ArrayList<Integer> listAddElig, String userId)
			throws Exception {

		ExamEligibilitySetupBO objEligiblity = null;
		ExamEligibilitySetupAdditionalEligibilityBO objAddElig = null;
		String[] listClassIds = classId.split(",");
		for (String classValues : listClassIds) {
			Integer.parseInt(classValues);
			objEligiblity = new ExamEligibilitySetupBO(Integer
					.parseInt(classValues), examTypeId, noEligibilityCheck,
					examFees, attendance, courseFees, userId);
			int id = 0;
			impl.isDuplicated(Integer.parseInt(classValues), examTypeId);

			id = impl.insert_returnId(objEligiblity);
			ArrayList<ExamEligibilitySetupAdditionalEligibilityBO> list = new ArrayList<ExamEligibilitySetupAdditionalEligibilityBO>();
			if (listAddElig.size() > 0) {
				for (Integer addEligiblity : listAddElig) {
					objAddElig = new ExamEligibilitySetupAdditionalEligibilityBO(
							id, addEligiblity, userId);
					list.add(objAddElig);

				}
				impl.insert_List(list);
			}

		}
	}

	// To Update
	public void updateEligibilitySetup(int eligibilitySetupId, int classId,
			int examTypeId, int isNoEligibilityChecked, int isExamFeesChecked,
			int isAttendanceChecked, int isCourseFeesChecked,
			ArrayList<Integer> listCheckedAddEligId, String userId)
			throws Exception {

		ExamEligibilitySetupAdditionalEligibilityBO objAddElig = null;

		impl.updateEligibilitySetup_impl(eligibilitySetupId,
				isNoEligibilityChecked, isExamFeesChecked, isAttendanceChecked,
				isCourseFeesChecked, userId, new Date());

		impl.delete_AdditionalEligibilitySetUp(eligibilitySetupId);
		ArrayList<ExamEligibilitySetupAdditionalEligibilityBO> list = new ArrayList<ExamEligibilitySetupAdditionalEligibilityBO>();
		if (listCheckedAddEligId.size() > 0) {

			for (Integer addEligiblity : listCheckedAddEligId) {
				objAddElig = new ExamEligibilitySetupAdditionalEligibilityBO(
						eligibilitySetupId, addEligiblity, userId);
				list.add(objAddElig);
			}
			impl.insert_List(list);
		}
	}

	public List<ExamExamEligibilityTO> getListAdditionalEligibilityEdit(
			int rowId) throws Exception {

		return helper.convertBOToTO_AdditionalEligibilityEdit(impl
				.getAdditionalEligibilitySetUpToEdit(rowId));

	}

	public void reactivate(int eligibilitySetupId, String userId) throws Exception {
		impl.reActivate_IExamGenBO(eligibilitySetupId, userId,
				ExamEligibilitySetupBO.class);
	}

	public int getClassId(int id) throws Exception {

		return impl.getClassId(id);
	}
}
