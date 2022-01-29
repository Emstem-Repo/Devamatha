package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationSubjectTO;
import com.kp.cms.to.exam.ExamSupplementaryImpApplicationTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamSupplementaryImpApplicationHelper {

	// To Get Exam name list
	public List<KeyValueTO> convertBOToTO_ExamName(List<ExamDefinitionBO> listBO) {
		List<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamDefinitionBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}

	public ArrayList<ExamSupplementaryImpApplicationTO> convertBOToTO_student(
			List<StudentUtilBO> studentUtilBOlist, String className) {

		ArrayList<ExamSupplementaryImpApplicationTO> listTO = new ArrayList<ExamSupplementaryImpApplicationTO>();
		ExamSupplementaryImpApplicationTO objTO = null;
		for (StudentUtilBO bo : studentUtilBOlist) {
			objTO = new ExamSupplementaryImpApplicationTO();
			objTO.setId(bo.getId());
			objTO.setRegNumber(bo.getRegisterNo());
			objTO.setRollNumber(bo.getRollNo());
			objTO.setStudentName(bo.getAdmApplnUtilBO().getPersonalDataUtilBO()
					.getName());
			objTO.setClassName(className);

			listTO.add(objTO);
		}
		
		return listTO;

	}

	public List<ExamSupplementaryImpApplicationTO> convertBOTOTO(
			List listOfStudent, String rollNo, String regNo) {
		ArrayList<ExamSupplementaryImpApplicationTO> listTO = new ArrayList<ExamSupplementaryImpApplicationTO>();
		Iterator itr = listOfStudent.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			ExamSupplementaryImpApplicationTO to = new ExamSupplementaryImpApplicationTO();
			String reg = (String) row[2];
			String roll = (String) row[1];
			// to.setId((Integer) row[0]);
			to.setClassName((String) row[0]);
			to.setRollNumber(roll);
			to.setRegNumber(reg);
			String name = (String) row[3];
			if (row[4] != null) {
				name = name + " " + (String) row[4];
			}
			to.setStudentName(name);
			to.setStudentId((Integer) row[5]);
			listTO.add(to);

		}
		return listTO;
	}

	public List<ExamSupplementaryImpApplicationSubjectTO> convertBOToTO_Subjects(
			List listOfSubjects, StringBuffer chanceBuffer, int isSupp) {
		ArrayList<ExamSupplementaryImpApplicationSubjectTO> listTO = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
		ExamSupplementaryImpApplicationSubjectTO to;
		Iterator itr = listOfSubjects.iterator();
		int chance = 0;
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			boolean failedTheory = false;
			if (row[3] != null) {
				if (row[3].toString().equals("1")) {
					failedTheory = true;
				}
			}
			boolean failedPractical = false;
			if (row[4] != null) {

				if (row[4].toString().equals("1")) {
					failedPractical = true;
				}

			}
			boolean appearedTheory = false;
			if (row[6] != null) {
				if (row[6].toString().equals("1")) {
					appearedTheory = true;
				}

			}
			boolean appearedPractical = false;
			if (row[7] != null) {

				if (row[7].toString().equals("1")) {
					appearedPractical = true;
				}

			}
			String fees = "0.00";
			if (row[5] != null) {
				fees = (String) row[5];
			}
			int subjectId = 0;
			if (row[0] != null) {
				subjectId = (Integer) row[0];
			}

			Integer id = null;
			boolean controlDisable = false;
			if (row[8] != null) {
				id = (Integer) row[8];
			}
			if (isSupp == 1) {
				if (id != null) {
					controlDisable = false;
				} else {
					controlDisable = true;
				}
			} else {
				if ((failedTheory == true) || (failedPractical == true)) {
					controlDisable = true;
				} else {
					controlDisable = false;
				}
			}

			// if (id != null && isSupp == 1) {
			// controlDisable = false;
			// } else {
			// controlDisable = true;
			// }
			if (row[9] != null) {
				chance = (Integer) row[9];
			}
			to = new ExamSupplementaryImpApplicationSubjectTO();
			to.setId(id);
			to.setChance(chance);
			to.setSubjectId(subjectId);
			to.setSubjectCode((String) row[1]);
			to.setSubjectName((String) row[2]);
			to.setFees(fees);
			to.setIsFailedTheory(failedTheory);
			to.setIsFailedPractical(failedPractical);
			//to.setIsAppearedTheory(appearedTheory);
			to.setTempChecked(appearedTheory);
			//to.setIsAppearedPractical(appearedPractical);
			to.setTempPracticalChecked(appearedPractical);
			to.setControlDisable(controlDisable);
			if (row[10] != null) {
				to.setClassId((Integer) row[10]);
			}
			if(!failedPractical && !failedTheory){
				if (row[11] != null) {
					if (row[11].toString().equals("true")) {
						to.setIsOverallTheoryFailed(true);
						
					}else{
						to.setIsOverallTheoryFailed(false);
					}
				}else{
					to.setIsOverallTheoryFailed(false);
				}
				if (row[12] != null) {
					if (row[12].toString().equals("true")) {
						to.setIsOverallPracticalFailed(true);
					}else{
						to.setIsOverallPracticalFailed(false);
					}
				}else{
					to.setIsOverallPracticalFailed(false);
				}
			}else{
				to.setIsOverallTheoryFailed(false);
				to.setIsOverallPracticalFailed(false);
			}
			listTO.add(to);
		}
		chanceBuffer.append(chance);
		return listTO;
	}

	
	public List<ExamSupplementaryImpApplicationSubjectTO> convertBOToTOForAdd(
			List<Object[]> listOfSubjects) {
		ArrayList<ExamSupplementaryImpApplicationSubjectTO> appTOList = new ArrayList<ExamSupplementaryImpApplicationSubjectTO>();
		ExamSupplementaryImpApplicationSubjectTO to;
		Iterator<Object[]> itr = listOfSubjects.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamSupplementaryImpApplicationSubjectTO();
			to.setChance(1);
			if(row[0]!= null){
				to.setSubjectId((Integer) row[0]);	
			}
			if(row[1]!= null){
				to.setSubjectCode((String) row[1]);
			}
			if(row[2]!= null){
				to.setSubjectName((String) row[2]);
			}
			to.setFees("0.00");
//			to.setIsFailedTheory(false);
//			to.setIsFailedPractical(false);
			if (row[3] != null) {
				to.setClassId((Integer) row[3]);
			}
			
			
			appTOList.add(to);

		}
		return appTOList;
	}
}
