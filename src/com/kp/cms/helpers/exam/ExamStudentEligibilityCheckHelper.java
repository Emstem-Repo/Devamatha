package com.kp.cms.helpers.exam;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamStudentEligibilityCheckBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.ExamStudentEligibilityCheckTO;
import com.kp.cms.to.exam.ExamStudentEligibilityEntryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

@SuppressWarnings("unchecked")
public class ExamStudentEligibilityCheckHelper extends ExamGenHelper {

	public ArrayList<ExamStudentEligibilityEntryTO> convertBOToTO_StudentEli(
			List listRows) throws Exception {
		ArrayList<ExamStudentEligibilityEntryTO> listEliStuTO = new ArrayList<ExamStudentEligibilityEntryTO>();
		Iterator itr = listRows.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			listEliStuTO.add(new ExamStudentEligibilityEntryTO(
					(Integer) row[0], (String) row[1], (String) row[2],
					(String) row[3], (String) row[4], (Integer) row[5],
					(Integer) row[6], (Integer) row[7], (Integer) row[8],
					(Integer) row[9]));
		}
		Collections.sort(listEliStuTO);
		return listEliStuTO;
	}

	public ExamStudentEligibilityCheckBO convertBOToTO_ExamStu(
			ExamStudentEligibilityEntryTO eto, String userId) throws Exception {
		ExamStudentEligibilityCheckBO e = new ExamStudentEligibilityCheckBO(
				userId);
		e.setClassId(eto.getClassId());
		e.setExamId(eto.getExamId());
		e.setStudentId(eto.getStudentId());

		return e;
	}

	public ArrayList<KeyValueTO> convertBOToTO_ExamType_KeValueTO(
			ArrayList<ExamTypeUtilBO> listBO) throws Exception {
		ArrayList<KeyValueTO> list = new ArrayList<KeyValueTO>();
		for (ExamTypeUtilBO bo : listBO) {
			list.add(new KeyValueTO(bo.getId(), bo.getName()));

		}
		Collections.sort(list,new KeyValueTOComparator());
		return list;
	}

	// To FETCH students based on display(Eligible, Non - Eligible, Both)
	public ArrayList<ExamStudentEligibilityCheckTO> convertBOToTO_Students(
			List<Object[]> select_students_eligible) throws Exception {
		ExamStudentEligibilityCheckTO to;
		ArrayList<ExamStudentEligibilityCheckTO> retutnList = new ArrayList<ExamStudentEligibilityCheckTO>();
		Iterator itr = select_students_eligible.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamStudentEligibilityCheckTO();
			if (row[0] != null) {
				to.setRollNo(row[0].toString());
			}
			if (row[1] != null) {
				to.setClassCode(row[1].toString());
			}
			if (row[2] != null) {
				to.setRegNO(row[3].toString());

			}
			if (row[3] != null) {
				to.setStudentName(row[3].toString());

			}
			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;
	}

	// To FETCH additional eligibility list for class

	public ArrayList<KeyValueTO> convertBOToTO_additionalEligibility(
			List<Object> list) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		if (list != null) {
			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				Object valString[] = (Object[]) iterator.next();
				if (valString[1] != null) {
					listTO.add(new KeyValueTO((Integer) valString[1],
							(String) valString[0]));
				}
			}
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;

	}

	public HashMap<Integer, Integer> convertListToHashMap(
			List<Object[]> get_eligCheck_details) throws Exception {
		HashMap<Integer, Integer> genericMap = new HashMap<Integer, Integer>();
		if (get_eligCheck_details != null && get_eligCheck_details.size() > 0) {
			for (Iterator iterator = get_eligCheck_details.iterator(); iterator
					.hasNext();) {
				Object[] objects = (Object[]) iterator.next();
				Integer id = null, value = null;
				if (objects[0] != null) {
					id = new BigInteger(objects[0].toString()).intValue();
				}
				if (objects[1] != null) {
					value = new BigInteger(objects[1].toString()).intValue();
				}
				genericMap.put(id, value);
			}
		}
		genericMap = (HashMap<Integer, Integer>) CommonUtil.sortMapByValue(genericMap);
		return genericMap;
	}

	public HashMap<Integer, ExamStudentEligibilityCheckTO> convertListToTO(
			List<Object[]> get_studentDetails_courseFees) throws Exception {
		HashMap<Integer, ExamStudentEligibilityCheckTO> listOfStudents = new HashMap<Integer, ExamStudentEligibilityCheckTO>();
		ExamStudentEligibilityCheckTO to = null;
		if (get_studentDetails_courseFees != null
				&& get_studentDetails_courseFees.size() > 0) {
			for (Iterator iterator = get_studentDetails_courseFees.iterator(); iterator
					.hasNext();) {
				to = new ExamStudentEligibilityCheckTO();
				Object[] objects = (Object[]) iterator.next();
				if (objects[4] != null) {
					to.setStudentName(objects[4].toString());
				}
				if (objects[1] != null) {
					to.setRollNo(objects[1].toString());
				}
				if (objects[2] != null) {
					to.setClassCode(objects[2].toString());
				}
				if (objects[3] != null) {
					to.setRegNO(objects[3].toString());
				}
				if (objects[5] != null) {
					to.setRowId((Integer) objects[5]);
				}
				if (objects[6] != null) {
					to.setDetailId((Integer) objects[6]);
				}
				if (objects[7] != null) {
					to.setRemarks((String) objects[7]);
				}
				if (objects[8] != null) {

					to.setDummyexamElgibility((Integer.parseInt(objects[8]
							.toString()) == 1) ? true : false);

				}
				if (objects[0] != null) {
					listOfStudents.put(Integer.parseInt(objects[0].toString()),
							to);
				}
			}
		}
		listOfStudents = (HashMap<Integer, ExamStudentEligibilityCheckTO>) CommonUtil.sortMapByValue(listOfStudents);
		return listOfStudents;
	}

	public HashMap<Integer, ArrayList<ExamExamEligibilityTO>> convertBOToTO_checkAdditionalEligibility_list(
			List<Object[]> listAdditionalEligibility_list) throws Exception {
		HashMap<Integer, ArrayList<ExamExamEligibilityTO>> additionalValuesMap = new HashMap<Integer, ArrayList<ExamExamEligibilityTO>>();
		ExamExamEligibilityTO to = null;
		ArrayList<ExamExamEligibilityTO> list = null;
		if (listAdditionalEligibility_list != null
				&& listAdditionalEligibility_list.size() > 0) {
			for (Iterator iterator = listAdditionalEligibility_list.iterator(); iterator
					.hasNext();) {
				to = new ExamExamEligibilityTO();
				Object[] objects = (Object[]) iterator.next();
				if (objects[1] != null) {
					to.setId((Integer) objects[1]);
				}
				if (objects[2] != null) {
					boolean value = (new BigInteger(objects[2].toString())
							.intValue() == 1) ? true : false;
					to.setDummyAdditionalFee(value);
				}
				if (objects[3] != null) {
					to.setDetailId((Integer) objects[3]);
				}
				if (objects[0] != null) {
					int studentId = (Integer) objects[0];
					if (additionalValuesMap.containsKey(studentId)) {
						list = additionalValuesMap.remove(studentId);
						list.add(to);
					} else {
						list = new ArrayList<ExamExamEligibilityTO>();
						list.add(to);
					}
					additionalValuesMap.put(studentId, list);
				}

			}
		}
		additionalValuesMap = (HashMap<Integer, ArrayList<ExamExamEligibilityTO>>) CommonUtil.sortMapByValue(additionalValuesMap);
		return additionalValuesMap;
	}
}
