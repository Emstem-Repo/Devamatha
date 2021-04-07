package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ExamMultipleAnswerScriptMasterBO;
import com.kp.cms.bo.exam.MarksEntryDetails;
import com.kp.cms.bo.exam.SubjectUtilBO;
import com.kp.cms.forms.exam.ExamSecuredMarksVerificationForm;
import com.kp.cms.to.exam.ExamSecuredMarksVerificationTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("unchecked")
public class ExamSecuredMarksVerificationHelper extends ExamGenHelper {

	public ArrayList<KeyValueTO> convertBOToTO_Subject_KeyVal(
			ArrayList<SubjectUtilBO> listBO) {

		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		Iterator itr = listBO.iterator();
		while (itr.hasNext()) {
			Object row[] = (Object[]) itr.next();
			listTO.add(new KeyValueTO((Integer) row[0], (String) row[1]
					+ (String) row[2]));
		}
		return listTO;

	}

	// To get the mark details(GRID)
	public ExamSecuredMarksVerificationTO convertBOToTO_get_securedMarksDetails(
			ArrayList<Object[]> select_secured_markdet_stu, String subjectType) {

		ExamSecuredMarksVerificationTO to = null;
		Iterator itr = select_secured_markdet_stu.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksVerificationTO();
			if (row[0] != null) {
				to.setRegNo((String) row[0]);
			}
			if (row[1] != null) {
				to.setRollNo((String) row[1]);
			}
			String setTheoryMarks = (String) row[2];
			String setPracticalMarks = (String) row[3];
			if (setTheoryMarks != null) {
				to.setTheoryMarks((String) row[2].toString());
			}
			if (setPracticalMarks != null) {
				to.setPracticalMarks((String) row[3].toString());
			}
			if (subjectType.equalsIgnoreCase("t")) {
				to.setMarks(setTheoryMarks);
			} else {
				to.setMarks(setPracticalMarks);
			}
			if (row[4] != null) {
				if (row[4].toString().equals("true")) {
					to.setMistake(true);
				} else {
					to.setMistake(false);
				}
			}

			if (row[5] != null) {
				if (row[5].toString().equals("true")) {
					to.setRetest(true);
				} else {
					to.setRetest(false);
				}

			}
			if (row[6] != null) {
				to.setStudentName((String) row[6]);
			}

			if (row[7] != null) {
				to.setExamMarksEntryId((Integer) row[7]);
			}
			if (row[8] != null) {
				to.setMarksEntryId((Integer) row[8]);
			}
			if (row[9] != null) {
				to.setDetailId((Integer) row[9]);
			}
			to.setIsTheoryPractical(subjectType);
		}
		return to;
	}

	
	// To get the mark details(GRID)
	public ExamSecuredMarksVerificationTO convertBOToTO_get_securedMarksDetails1(
			ArrayList<Object[]> select_secured_markdet_stu, String subjectType) {

		ExamSecuredMarksVerificationTO to = null;
		Iterator itr = select_secured_markdet_stu.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksVerificationTO();
			if (row[0] != null) {
				to.setRegNo((String) row[0]);
			}
			if (row[1] != null) {
				to.setRollNo((String) row[1]);
			}
			StringBuffer sname=new StringBuffer();
			if(row[2]!=null)
				sname=sname.append(row[2].toString());
			if(row[3]!=null)
				sname=sname.append(" "+row[3].toString());
			if(row[4]!=null)
				sname=sname.append(" "+row[4].toString());
			to.setStudentName(sname.toString());
			/*if (row[3] != null) {
				to.setStudentId((Integer) row[3]);
			}*/
			to.setTheoryMarks("");
			/*String setTheoryMarks = (String) row[2];
			String setPracticalMarks = (String) row[3];
			if (setTheoryMarks != null) {
				to.setTheoryMarks((String) row[2].toString());
			}
			if (setPracticalMarks != null) {
				to.setPracticalMarks((String) row[3].toString());
			}
			if (subjectType.equalsIgnoreCase("t")) {
				to.setMarks(setTheoryMarks);
			} else {
				to.setMarks(setPracticalMarks);
			}
			if (row[4] != null) {
				if (row[4].toString().equals("true")) {
					to.setMistake(true);
				} else {
					to.setMistake(false);
				}
			}

			if (row[5] != null) {
				if (row[5].toString().equals("true")) {
					to.setRetest(true);
				} else {
					to.setRetest(false);
				}

			}
			if (row[6] != null) {
				to.setStudentName((String) row[6]);
			}

			if (row[7] != null) {
				to.setExamMarksEntryId((Integer) row[7]);
			}
			if (row[8] != null) {
				to.setMarksEntryId((Integer) row[8]);
			}
			if (row[9] != null) {
				to.setDetailId((Integer) row[9]);
			}*/
			to.setIsTheoryPractical(subjectType);
			if(row[5]!=null)
				to.setClassId((row[5]).toString());
		}
		return to;
	}
	
	public ExamSecuredMarksVerificationTO convertBOToTO_get_securedMarksDetails3(
			ArrayList<Object[]> select_secured_markdet_stu) {

		ExamSecuredMarksVerificationTO to = null;
		Iterator itr = select_secured_markdet_stu.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksVerificationTO();
			if (row[0] != null) {
				to.setRegNo((String) row[0]);
			}
			if (row[1] != null) {
				to.setRollNo((String) row[1]);
			}
			if (row[2] != null) {
				to.setStudentName((String) row[2]);
			}
			if (row[3] != null) {
				to.setMiddleName((String) row[3]);
			}
			if (row[4] != null) {
				to.setLastName((String) row[4]);
			}
			if (row[5] != null) {
				to.setStudentId(Integer.parseInt(row[5].toString()));
			}
			
		}
		return to;
	}
	
	//For Checking the Student Validation.
	public ExamSecuredMarksVerificationTO convertBOToTO_get_securedMarksDetails2(
			ArrayList<Object[]> checkID) {

		ExamSecuredMarksVerificationTO to = null;
		Iterator itr = checkID.iterator();
		while (itr.hasNext()) {

			Object[] row = (Object[]) itr.next();
			to = new ExamSecuredMarksVerificationTO();
			if (row[0] != null) {
				to.setStudentId((Integer) row[0]);
			}
		}
		return to;
	}
	public HashMap<Integer, String> convertBOToTO_Mul_Ans_Script_KeyVal(
			List<Object> listBO) {

		HashMap<Integer, String> listTO = new HashMap<Integer, String>();
		for (Iterator it = listBO.iterator(); it.hasNext();) {
			ExamMultipleAnswerScriptMasterBO bo = (ExamMultipleAnswerScriptMasterBO) it
					.next();
			listTO.put(bo.getId(), bo.getName());

		}
		return listTO;

	}

	public String getQueryForEvaluatorEnteredMarks(ExamSecuredMarksVerificationForm objform) {
		String query="from MarksEntryDetails m" +
		" where m.subject.id=" +objform.getSubject()+
		" and m.marksEntry.exam.id="+objform.getExamId();
	if(objform.getEvaluatorType()!=null && !objform.getEvaluatorType().isEmpty()){
		query=query+" and m.marksEntry.evaluatorType !="+objform.getEvaluatorType();
	}
	if(objform.getAnswerScriptType()!=null && !objform.getAnswerScriptType().isEmpty()){
		query=query+" and m.marksEntry.answerScript !="+objform.getAnswerScriptType();
	}
	if(objform.getSubjectType()!=null && !objform.getSubjectType().isEmpty()){
		if(objform.getSubjectType().equalsIgnoreCase("Theory")){
			query=query+" and m.theoryMarks is not null and m.isTheorySecured=1";
		}else if(objform.getSubjectType().equalsIgnoreCase("Practical")){
			query=query+" and m.practicalMarks is not null and m.isPracticalSecured=1 ";
		}else{
			query=query+" and m.theoryMarks is not null and m.practicalMarks is not null and m.isTheorySecured=1 and m.isPracticalSecured=1";
		}
	}
	return query;
}

	public Map<Integer, Map<Integer, String>> convertBotoEvaMap(List evalMarksList, String subjectType)  throws Exception{
		Map<Integer, Map<Integer, String>> map=new HashMap<Integer, Map<Integer,String>>();
		Iterator<MarksEntryDetails> itr=evalMarksList.iterator();
		while (itr.hasNext()) {
			MarksEntryDetails marksEntryDetails = (MarksEntryDetails) itr.next();
			if(marksEntryDetails.getMarksEntry().getEvaluatorType()!=null){
				if(subjectType.equalsIgnoreCase("Theory") && marksEntryDetails.getTheoryMarks()!=null){
					if(map.containsKey(marksEntryDetails.getMarksEntry().getStudent().getId())){
						Map<Integer,String> m=map.remove(marksEntryDetails.getMarksEntry().getStudent().getId());
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getTheoryMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}else{
						Map<Integer,String> m=new HashMap<Integer, String>();
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getTheoryMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}
				}else if(subjectType.equalsIgnoreCase("Practical") && marksEntryDetails.getPracticalMarks()!=null){
					if(map.containsKey(marksEntryDetails.getMarksEntry().getStudent().getId())){
						Map<Integer,String> m=map.remove(marksEntryDetails.getMarksEntry().getStudent().getId());
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getPracticalMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}else{
						Map<Integer,String> m=new HashMap<Integer, String>();
						m.put(marksEntryDetails.getMarksEntry().getEvaluatorType(),marksEntryDetails.getPracticalMarks());
						map.put(marksEntryDetails.getMarksEntry().getStudent().getId(), m);
					}
				} 
			}
		}
		return map;
	}
}
