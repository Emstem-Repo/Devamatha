package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.kp.cms.bo.exam.ExamProgramUtilBO;
import com.kp.cms.bo.exam.ExamSecondLanguageMasterBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.forms.exam.ExamUniversityRegisterNumberEntryForm;
import com.kp.cms.helpers.exam.ExamUniversityRegisterNumberEntryHelper;
import com.kp.cms.to.exam.ExamUniversityRegisterNumberEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamUniversityRegisterNumberEntryImpl;

@SuppressWarnings("unchecked")
public class ExamUniversityRegisterNumberEntryHandler extends
		ExamStudentSpecializationHandler {
	ExamUniversityRegisterNumberEntryImpl impl = new ExamUniversityRegisterNumberEntryImpl();
	ExamUniversityRegisterNumberEntryHelper helper = new ExamUniversityRegisterNumberEntryHelper();

	public ArrayList<KeyValueTO> getSecondLanguage_List() {
		ArrayList<ExamSecondLanguageMasterBO> listBO = new ArrayList(impl
				.select_ActiveOnly(ExamSecondLanguageMasterBO.class));
		return helper.convertBOToTO_SecondLanguage_HashMap(listBO);
	}

	public ArrayList<ExamUniversityRegisterNumberEntryTO> getStudentDetails(
			int academicYear, int courseId, int schemeId, int schemeNo,
			String orderBy) {

		ArrayList<StudentUtilBO> listBO = new ArrayList(impl
				.select_student_details(academicYear, courseId, schemeId,
						schemeNo, orderBy));
		return helper.convertBOToTO_getStudentDetails(listBO);
	}

	// To update regNo
	public void update(ExamUniversityRegisterNumberEntryForm objform)
			throws Exception {

		ArrayList<ExamUniversityRegisterNumberEntryTO> listTO = objform
				.getStudentDetails();
		ArrayList<String> regNoList = null;

		HashMap<String, Integer> map = new HashMap<String, Integer>();
		int count=0;
		for (ExamUniversityRegisterNumberEntryTO examUniversityRegisterNumberEntryTO : listTO) {
			count++;
			regNoList = new ArrayList<String>();
			regNoList.add(examUniversityRegisterNumberEntryTO.getRegNo());
			// studentIdList.add(examUniversityRegisterNumberEntryTO.getId());
			map.put(examUniversityRegisterNumberEntryTO.getRegNo(),
					examUniversityRegisterNumberEntryTO.getId());
			impl.isDuplicated_Regno(map, regNoList,count);
			impl.update(examUniversityRegisterNumberEntryTO.getId(),
					examUniversityRegisterNumberEntryTO.getRegNo(), objform
							.getUserId());
		}

	}

	public HashMap<Integer, String> getProgramByYear(Integer year) {
		ExamUniversityRegisterNumberEntryImpl impl = new ExamUniversityRegisterNumberEntryImpl();
		ExamUniversityRegisterNumberEntryHelper helper = new ExamUniversityRegisterNumberEntryHelper();
		List<ExamProgramUtilBO> listBO = new ArrayList(impl
				.select_getProgramByAcademicYear(year));
		return helper.convertBOToTO_ExamUSN_entry_academic_year(listBO);
		// return null;
	}

}
