package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamEligibilityCriteriaMasterBO;
import com.kp.cms.bo.exam.ExamStudentEligibilityEntryBO;
import com.kp.cms.to.exam.ExamStudentEligibilityEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamStudentEligibilityEntryImpl;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityEntryHelper extends ExamGenHelper {

	public ArrayList<ExamStudentEligibilityEntryTO> convertBOToTO_StudentEli(
			List listRows) throws Exception {
		ArrayList<ExamStudentEligibilityEntryTO> listEliStuTO = new ArrayList<ExamStudentEligibilityEntryTO>();
		if (listRows != null && listRows.size() > 0) {
			Iterator itr = listRows.iterator();
			while (itr.hasNext()) {
				int isEligibile = 0;
				Object[] row = (Object[]) itr.next();
				if (row[5] != null && row[5].toString().length() > 0) {
					if (row[5].toString().equals("true")) {
						isEligibile = 1;
					}
				}
				String a = "";
				if (row[4] != null) {
					a = (String) row[4];
				}
				// Integer studentId, String rollNo,
				// String regNO, String firstName, String lastName,
				// Integer eligibilityCheck, Integer classId - TO

				// select s.id as studentId ," - QUERY
				// + " s.roll_no, s.register_no, "
				// + " personal_data.first_name,"
				// + " personal_data.last_name,"
				// + " ee.is_eligibile, ee.id as eligibilityId,  "
				// + " ee.class_id,"
				// + " ee.eligibility_criteria_id, classes.id as classid
				listEliStuTO.add(new ExamStudentEligibilityEntryTO(
						(Integer) row[0], (String) row[1], (String) row[2],
						(String) row[3], a, isEligibile, (Integer) row[9]));
			}
		}
		Collections.sort(listEliStuTO);
		return listEliStuTO;              
	}

	public ExamStudentEligibilityEntryBO convertBOToTO_ExamStu(
			ExamStudentEligibilityEntryTO eto, String userId) throws Exception {
		ExamStudentEligibilityEntryBO e = new ExamStudentEligibilityEntryBO(
				userId);
		e.setClassId(eto.getClassId());
		// e.setExamId(0);
		e.setEligibilityCriteriaId(eto.getEligibilityCriteriaId());
		e.setIsEligibile(1);
		e.setStudentId(eto.getStudentId());
		return e;
	}

	public ArrayList<KeyValueTO> convertBOtoTO_ElegibilityCriterea(
			List<ExamEligibilityCriteriaMasterBO> list) throws Exception {
		ArrayList<KeyValueTO> listValues = new ArrayList<KeyValueTO>();
		for (ExamEligibilityCriteriaMasterBO objBO : list) {
			listValues.add(new KeyValueTO(objBO.getId(), objBO.getName()));
		}
		Collections.sort(listValues,new KeyValueTOComparator());
		return listValues;
	}

	public ArrayList<ExamStudentEligibilityEntryTO> convertFormToTO(
			ArrayList<Integer> listStudents, ArrayList<Integer> listClass,
			int elegibilityCriteria) throws Exception {
		ExamStudentEligibilityEntryTO e;
		ArrayList<ExamStudentEligibilityEntryTO> listReturn = new ArrayList<ExamStudentEligibilityEntryTO>();
		ExamStudentEligibilityEntryImpl impl = new ExamStudentEligibilityEntryImpl();
		for (Integer classId : listClass) {
			for (Integer studentId : listStudents) {
				if (!impl.getValidStudentForClass(studentId, classId)) {
					e = new ExamStudentEligibilityEntryTO();
					e.setClassId(classId);
					e.setEligibilityCriteriaId(elegibilityCriteria);
					e.setEligibilityCheck("on");
					e.setStudentId(studentId);
					listReturn.add(e);
				}

			}
		}
		Collections.sort(listReturn);
		return listReturn;             
	}

}
