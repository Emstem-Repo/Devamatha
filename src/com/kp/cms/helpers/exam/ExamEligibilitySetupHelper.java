package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamEligibilityCriteriaMasterBO;
import com.kp.cms.bo.exam.ExamEligibilitySetupBO;
import com.kp.cms.bo.exam.ExamTypeUtilBO;
import com.kp.cms.to.exam.ExamExamEligibilitySetUpTO;
import com.kp.cms.to.exam.ExamExamEligibilityTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;


public class ExamEligibilitySetupHelper {

	public ArrayList<KeyValueTO> convertBOToTO_ExamType(
			ArrayList<ExamTypeUtilBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamTypeUtilBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public List<ExamExamEligibilityTO> convertBOToTO_checkEligibility(
			List<ExamEligibilitySetupBO> listBO) throws Exception {
		ArrayList<ExamExamEligibilityTO> listTO = new ArrayList<ExamExamEligibilityTO>();
		ExamExamEligibilityTO to;
		for (ExamEligibilitySetupBO bo : listBO) {
			to = new ExamExamEligibilityTO();
			to.setId(bo.getId());
			to.setValue(0);
			to.setDisplay(bo.getName());
			listTO.add(to);
		}
	Collections.sort(listTO);
		return listTO;
	}

	public List<ExamExamEligibilityTO> convertBOToTO_checkAdditionalEligibility(
			List<ExamEligibilityCriteriaMasterBO> listBO) throws Exception {
		ArrayList<ExamExamEligibilityTO> listTO = new ArrayList<ExamExamEligibilityTO>();
		ExamExamEligibilityTO to;
		for (ExamEligibilityCriteriaMasterBO bo : listBO) {
			to = new ExamExamEligibilityTO();
			to.setId(bo.getId());
			to.setValue(0);
			to.setDisplay(bo.getName());
			listTO.add(to);
		}
		Collections.sort(listTO);
		return listTO;
	}

	public List<ExamExamEligibilityTO> convertBOToTO_checkAdditionalEligibilityToEdit(
			List<ExamEligibilityCriteriaMasterBO> listBO,
			ArrayList<Integer> listAddElig) throws Exception {
		ArrayList<ExamExamEligibilityTO> listTO = new ArrayList<ExamExamEligibilityTO>();
		ExamExamEligibilityTO to;
		for (ExamEligibilityCriteriaMasterBO bo : listBO) {
			to = new ExamExamEligibilityTO();
			to.setId(bo.getId());
			if (listAddElig.contains(bo.getId())) {
				to.setValue(1);
			} else {
				to.setValue(0);
			}
			to.setDisplay(bo.getName());
			listTO.add(to);
		}
         Collections.sort(listTO);
		return listTO;
	}

	public List<ExamExamEligibilitySetUpTO> convertBOToTO_ExamEligibilitySetUp(
			List<ExamEligibilitySetupBO> listBO) throws Exception {
		List<ExamExamEligibilitySetUpTO> listTO = new ArrayList<ExamExamEligibilitySetUpTO>();
		for (ExamEligibilitySetupBO bo : listBO) {
			listTO.add(new ExamExamEligibilitySetUpTO(bo.getId(),
					bo.classUtilBO.getId(), bo.classUtilBO.getName(),
					bo.examTypeUtilBO.getId(), bo.examTypeUtilBO.getName()));
			
		}
		Collections.sort(listTO);
		return listTO;
	}

	@SuppressWarnings("unchecked")
	public List<ExamExamEligibilityTO> convertBOToTO_AdditionalEligibilityEdit(
			List listToEdit) throws Exception {

		ArrayList<ExamExamEligibilityTO> retutnList = new ArrayList<ExamExamEligibilityTO>();
		Iterator itr = listToEdit.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			int id = 0;
			if (row[2] != null) {
				id = 1;
			}
			retutnList.add(new ExamExamEligibilityTO((Integer) row[0],
					(String) row[1], id));
		}
		Collections.sort(retutnList);
		return retutnList;

	}

}
