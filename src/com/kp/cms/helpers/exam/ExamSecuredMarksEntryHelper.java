package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.to.exam.ExamSecuredMarksEntryTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("unchecked")
public class ExamSecuredMarksEntryHelper extends ExamGenHelper {

	public ArrayList<KeyValueTO> convertBOToTO_Subject_KeyVal(
			List<SubjectUtilBO> listBO) {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object row[] = (Object[]) itr.next();
			listTO.add(new KeyValueTO((Integer) row[0], (String) row[1]
					+ (String) row[2]));
		}
		return listTO;

	}

	public ExamSecuredMarksEntryTO convertBoToForEvaluatorCalculation(
			ArrayList<Object[]> perecentage_Difference) {
		ExamSecuredMarksEntryTO t = new ExamSecuredMarksEntryTO();
		if (perecentage_Difference != null && perecentage_Difference.size() > 0) {
			Iterator itr = perecentage_Difference.iterator();
			while (itr.hasNext()) {
				Object object[] = (Object[]) itr.next();
				if(object[1]!= null){
					t.setPercentageDifference(object[1].toString());
				}
				else{
					t.setPercentageDifference("0");
				}
				if(object[2] != null){
				t.setPreviousEvaluatorMarks(object[2].toString());
				}else{
					t.setPreviousEvaluatorMarks("0");
				}
			}
		}

		return t;
	}

	public HashMap<Integer, String> convertBOToTO_EvaluatorType(
			ArrayList<Object> evaluatorType) {

		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		Iterator itr = evaluatorType.iterator();
		while (itr.hasNext()) {
			Object object = (Object) itr.next();
			listTO.put((Integer) object, object.toString());
		}
		return listTO;
	}

	public ExamSecuredMarksEntryTO convertBOToTO_get_securedMarksDetails(
			ArrayList<Object[]> get_secured_mark_details) {
		ExamSecuredMarksEntryTO to = null;
		Iterator itr = get_secured_mark_details.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksEntryTO();

			if (row[7] != null) {
				to.setMarksEntryId(row[7].toString());
			}
			if (row[8] != null) {
				to.setDetailId(row[8].toString());
			}

		}
		return to;
	}

	// ON - VIEW - To Fetch Data
	public ExamSecuredMarksEntryTO convertBOToTO_get_view_details(
			ArrayList<Object[]> get_view_details, String subjectType) {

		ExamSecuredMarksEntryTO to = null;
		Iterator itr = get_view_details.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksEntryTO();

			if (row[0] != null) {
				to.setTheoryMarks(row[0].toString());
			}
			if (row[1] != null) {
				to.setPracticalMarks(row[1].toString());
			}
			if (subjectType.equalsIgnoreCase("theory")) {
				if (row[2] != null) {
					to.setPreviousEvaluatorMarks((row[2].toString()));
				}
			} else if (subjectType.equalsIgnoreCase("Practical")) {
				if (row[3] != null) {
					to.setPreviousEvaluatorMarks((row[3].toString()));
				}
			}
			if (row[4] != null) {
				if (row[4].toString().equals("1")) {
					to.setMistake(true);
				} else {
					to.setMistake(false);
				}
			}

			if (row[5] != null) {

				if (row[5].toString().equals("1")) {
					to.setRetest(true);
				} else {
					to.setRetest(false);
				}

			}
			if (row[6] != null) {
				to.setMarksEntryId(row[6].toString());
			}
			if (row[7] != null) {
				to.setDetailId((row[7].toString()));
			}

		}
		return to;
	}

	public ArrayList<KeyValueTO> convertBOToTO_SubjectMap(
			ArrayList<SubjectUtilBO> listBO, String sCodeName) {
		ArrayList<KeyValueTO> listOfValues = new ArrayList<KeyValueTO>();

		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object row[] = (Object[]) itr.next();
			if (sCodeName.equalsIgnoreCase("sCode")) {
				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[2].toString() + " "
						+ row[1].toString()));
			} else {

				listOfValues.add(new KeyValueTO(Integer.parseInt(row[0]
						.toString()), row[1].toString() + " "
						+ row[2].toString()));
			}
		}

		return listOfValues;
	}
}
