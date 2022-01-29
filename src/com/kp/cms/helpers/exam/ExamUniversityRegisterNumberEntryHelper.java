package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.bo.exam.ExamUniversityRegisterNumberEntryBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.ExamUniversityRegisterNumberEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamUniversityRegisterNumberEntryHelper extends ExamGenHelper {
	@SuppressWarnings("unchecked")
	public HashMap<Integer, String> convertBOToTO_ExamUSN_entry_academic_year(
			List<ExamProgramUtilBO> listBO) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();

		for (ExamProgramUtilBO bo : listBO) {
			map.put(bo.getProgramID(), bo.getProgramName());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	// To get second language Hash Map
	public ArrayList<KeyValueTO> convertBOToTO_SecondLanguage_HashMap(
			ArrayList<ExamSecondLanguageMasterBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();

		for (ExamSecondLanguageMasterBO bo : listBO) {

			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public ArrayList<ExamUniversityRegisterNumberEntryTO> convertBOToTO_getStudentDetails(
			ArrayList<StudentUtilBO> listBO) {

		ArrayList<ExamUniversityRegisterNumberEntryTO> listTO = new ArrayList<ExamUniversityRegisterNumberEntryTO>();
		String name = "";
		String firstName = null, lastName = null;
		for (StudentUtilBO bo : listBO) {
			if (bo.getAdmApplnUtilBO().getPersonalDataUtilBO() != null) {
				firstName = bo.getAdmApplnUtilBO().getPersonalDataUtilBO()
						.getFirstName();
			}
			if (bo.getAdmApplnUtilBO().getPersonalDataUtilBO() != null) {
				lastName = bo.getAdmApplnUtilBO().getPersonalDataUtilBO()
						.getLastName();
			}
			name = ((firstName != null) ? firstName : "")
					+ ((lastName != null) ? lastName : "");
			listTO.add(new ExamUniversityRegisterNumberEntryTO(bo.getRollNo(),
					name, bo.getRegisterNo(), null, null, bo.getId()));
		}
		Collections.sort(listTO);
		return listTO;
	}

	public ArrayList<ExamUniversityRegisterNumberEntryBO> convertTotoBO(
			ArrayList<ExamUniversityRegisterNumberEntryTO> studentDetails,
			String userId, String academicYear, String schemeNo, String courseId) {

		ArrayList<ExamUniversityRegisterNumberEntryBO> listBO = new ArrayList<ExamUniversityRegisterNumberEntryBO>();
		for (ExamUniversityRegisterNumberEntryTO to : studentDetails) {

			listBO.add(new ExamUniversityRegisterNumberEntryBO(academicYear, to
					.getId(), Integer.parseInt(schemeNo), Integer
					.parseInt(courseId), null, to.getRegNo(), userId));
		}

		return listBO;
	}

}
