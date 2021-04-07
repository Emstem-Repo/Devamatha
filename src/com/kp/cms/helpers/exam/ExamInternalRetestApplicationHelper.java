package com.kp.cms.helpers.exam;

/**
 * Mar 2, 2010
 * Created By 9Elements Team
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.to.exam.ExamInternalRetestApplicationSubjectsTO;
import com.kp.cms.to.exam.ExamInternalRetestApplicationTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("unchecked")
public class ExamInternalRetestApplicationHelper extends ExamGenHelper {

	// On SEARCH to get the student details

	public ArrayList<ExamSupplementaryImpApplicationTO> converBoToTO_Student_GetDetails(
			List<Object[]> list) throws BusinessException {

		ExamSupplementaryImpApplicationTO to;
		ArrayList<ExamSupplementaryImpApplicationTO> retutnList = new ArrayList<ExamSupplementaryImpApplicationTO>();
		Iterator itr = list.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSupplementaryImpApplicationTO();
			to.setClassName((String) row[0]);
			to.setRegNumber((String) row[1]);
			to.setRollNumber((String) row[2]);
			to.setStudentName((String) row[3]);
			to.setId((Integer) row[4]);
			retutnList.add(to);
		}
		Collections.sort(retutnList);
		return retutnList;
	}

	// ADD - to get the subjects for a particular student
	public ExamInternalRetestApplicationTO convertBOToTO_details(
			List<Object[]> subjectDetails, List<Object[]> examDetails,
			String edit) throws BusinessException {

		ExamInternalRetestApplicationTO to = new ExamInternalRetestApplicationTO();
		ArrayList<ExamInternalRetestApplicationSubjectsTO> listSubject = new ArrayList<ExamInternalRetestApplicationSubjectsTO>();
		ExamInternalRetestApplicationSubjectsTO iTO;
		int k = 0;
		Iterator itr = examDetails.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			if (row[0] == null) {
				k = 1;
			} else {
				if (edit.equalsIgnoreCase("edit")) {
					k = Integer.parseInt(row[0].toString());
				} else {
					k = Integer.parseInt(row[0].toString()) + 1;
				}
			}
			to.setChance(k);
			to.setClassName((String) row[1]);
			to.setStudentName((String) row[2]);
			to.setRegNumber((String) row[3]);
			to.setRollNumber((String) row[4]);
			to.setStudentId((Integer) row[5]);
			to.setId((Integer) row[6]);
			to.setClassId((Integer) row[7]);

		}

		Iterator itr1 = subjectDetails.iterator();
		while (itr1.hasNext()) {
			Integer theory = null;
			Integer practical = null;
			Object[] row = (Object[]) itr1.next();
			iTO = new ExamInternalRetestApplicationSubjectsTO();
			iTO.setSubjectCode((String) row[0]);
			iTO.setSubjectName((String) row[1]);
			iTO.setFees((String) row[2]);
			if (row[3] != null) {
				theory = Integer.parseInt(row[3].toString());
				if (theory == 1) {
					iTO.setIsCheckedDummy(true);

				} else {
					iTO.setIsCheckedDummy(false);

				}
			}
			if (row[4] != null) {
				practical = Integer.parseInt(row[4].toString());
				if (practical == 1) {
					iTO.setIsCheckedDummyPractical(true);

				} else {
					iTO.setIsCheckedDummyPractical(false);

				}
			}

			iTO.setSubjectId(row[5].toString());
			listSubject.add(iTO);

		}

		to.setSubjectList(listSubject);
		//Collections.sort(to,new ExamInternalRetestApplicationSubjectsTOComparator());
		return to;
	}

	// To get class name based on examId & roll/register no
	public HashMap<Integer, String> convertTOToMapClass(
			List classByExamNameRegNoOnly){

		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator itr = classByExamNameRegNoOnly.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();

			map.put(Integer.parseInt(row[0].toString()), row[1].toString());
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

}
