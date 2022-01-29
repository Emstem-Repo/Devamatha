package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Map;

import com.kp.cms.bo.exam.ExamEligibilityCriteriaMasterBO;
import com.kp.cms.bo.exam.ExamStudentEligibilityEntryBO;
import com.kp.cms.helpers.exam.ExamStudentEligibilityEntryHelper;
import com.kp.cms.to.exam.ExamStudentEligibilityEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxExamImpl;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.exam.ExamStudentEligibilityEntryImpl;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityEntryHandler extends ExamGenHandler {

	ExamStudentEligibilityEntryHelper helper = new ExamStudentEligibilityEntryHelper();
	ExamStudentEligibilityEntryImpl impl = new ExamStudentEligibilityEntryImpl();

	public ArrayList<KeyValueTO> getListEligibilityCriteria() throws Exception {

		ArrayList<ExamEligibilityCriteriaMasterBO> a = new ArrayList(impl
				.select_ActiveOnly(ExamEligibilityCriteriaMasterBO.class));
		return helper.convertBOtoTO_ElegibilityCriterea(a);

	}

	public ArrayList<ExamStudentEligibilityEntryTO> search(
			ArrayList<Integer> listClassIds, Integer eligibilityCriteria,
			String academicYear) throws Exception {
		return helper.convertBOToTO_StudentEli(impl.select_StudentEli(
				listClassIds, eligibilityCriteria, academicYear));
	}

	public void update(ArrayList<ExamStudentEligibilityEntryTO> listEliStu,
			ArrayList<Integer> listClass, int eligibilityCriteria, String userId) throws Exception {
		ArrayList<ExamStudentEligibilityEntryBO> listBO_toInsert = new ArrayList<ExamStudentEligibilityEntryBO>();
		ArrayList<Integer> listClassId = new ArrayList<Integer>();

		for (ExamStudentEligibilityEntryTO eTO : listEliStu) {
			if (eTO.isEligibilityChecked()) {

				listBO_toInsert.add(helper.convertBOToTO_ExamStu(eTO, userId));
			}
			if (!listClassId.contains(eTO.getClassId())) {
				listClassId.add(eTO.getClassId());
			}

		}

		 impl.delete(listClass, eligibilityCriteria);

		impl.insert_List_Values(listBO_toInsert);

	}

	public Map<Integer, String> getClassCode(int year) throws Exception {
		CommonAjaxExamImpl impl = new CommonAjaxExamImpl();
		return impl.getClasesByAcademicYear(year);
	}

	public void deleteStudentEligibilityEntry(ArrayList<Integer> listClass,
			int eligibilityCriteria) throws Exception {
		impl.delete(listClass, eligibilityCriteria);

	}

}
