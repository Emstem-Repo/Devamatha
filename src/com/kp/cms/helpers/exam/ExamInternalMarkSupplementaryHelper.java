package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamInternalMarkSupplementaryDetailsBO;
import com.kp.cms.bo.exam.ExamStudentOverallInternalMarkDetailsBO;
import com.kp.cms.forms.exam.ExamInternalMarksSupplementaryForm;
import com.kp.cms.to.exam.ExamInternalMarksSupplementaryTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamInternalMarkSupplementaryHelper {
	// To get the main list
	/*public ArrayList<ExamInternalMarksSupplementaryTO> convertBOToTO_getStudentIntSuppMarksDetails(
			List listRow) throws Exception {
		ArrayList<ExamInternalMarksSupplementaryTO> listTO = new ArrayList<ExamInternalMarksSupplementaryTO>();
		ExamInternalMarksSupplementaryTO to = new ExamInternalMarksSupplementaryTO();
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			to = new ExamInternalMarksSupplementaryTO();
			if (row[0] != null) {
				to.setId((Integer) row[0]);
			}

			if (row[1] != null) {
				to.setSubjectId((Integer) row[1]);
			}
			if (row[2] != null) {
				to.setSubjectName(row[2].toString());
			}
			if (row[3] != null) {
				to.setSubjectCode(row[3].toString());
			}
			String setTheoryMarks = (String) row[4];
			String setPracticalMarks = (String) row[5];

			if (row[4] != null) {
				to.setTheoryMarks(setTheoryMarks);
			}
			if (row[5] != null) {
				to.setPracticalMarks(setPracticalMarks);
			}

			if (row[6] != null) {
				to.setIsTheoryPrac(row[6].toString().toUpperCase());
			}
			listTO.add(to);
		}
		return listTO;

	}*/

	@SuppressWarnings("unchecked")
	public ArrayList<KeyValueTO> convertBOToTO(List listRow) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		Iterator itr = listRow.iterator();
		while (itr.hasNext()) {
			Object[] row = (Object[]) itr.next();
			listTO.add(new KeyValueTO(Integer.parseInt(row[0].toString()),
					row[1].toString()));
		}
		Collections.sort(listTO,new KeyValueTOComparator());
		return listTO;
	}
	
	public List<ExamInternalMarksSupplementaryTO> setBOListToTOList(List<ExamStudentOverallInternalMarkDetailsBO> examStudentOverallInternalMarkDetailsBOs,Map<Integer, ExamInternalMarksSupplementaryTO> supplementaryInternalMarkMap) throws Exception{
		List<ExamInternalMarksSupplementaryTO> examOverAllInternalMarksTOList = new ArrayList<ExamInternalMarksSupplementaryTO>();
		ExamInternalMarksSupplementaryTO examInternalMarksSupplementaryTO=null;
		ExamInternalMarksSupplementaryTO examOverAllInternalMarksTO=null;
		Iterator<ExamStudentOverallInternalMarkDetailsBO> iterator = examStudentOverallInternalMarkDetailsBOs.iterator();
		while (iterator.hasNext()) {
			ExamStudentOverallInternalMarkDetailsBO examStudentOverallInternalMarkDetailsBO = (ExamStudentOverallInternalMarkDetailsBO) iterator.next();
			examOverAllInternalMarksTO=new ExamInternalMarksSupplementaryTO();
			examOverAllInternalMarksTO.setSubjectId(examStudentOverallInternalMarkDetailsBO.getSubjectId());
			if(supplementaryInternalMarkMap.containsKey(examStudentOverallInternalMarkDetailsBO.getSubjectId())){
				examInternalMarksSupplementaryTO=supplementaryInternalMarkMap.get(examStudentOverallInternalMarkDetailsBO.getSubjectId());
				examOverAllInternalMarksTO.setId(examInternalMarksSupplementaryTO.getId());
				examOverAllInternalMarksTO.setExamId(examInternalMarksSupplementaryTO.getExamId());
				examOverAllInternalMarksTO.setStudentId(examInternalMarksSupplementaryTO.getStudentId());
				examOverAllInternalMarksTO.setClassId(examInternalMarksSupplementaryTO.getClassId());
				examOverAllInternalMarksTO.setSubjectCode(examInternalMarksSupplementaryTO.getSubjectCode());
				examOverAllInternalMarksTO.setSubjectName(examInternalMarksSupplementaryTO.getSubjectName());
				examOverAllInternalMarksTO.setTheoryTotalSubInternalMarks(examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks());
				examOverAllInternalMarksTO.setPracticalTotalSubInternalMarks(examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks());
				examOverAllInternalMarksTO.setIsTheoryPrac(examInternalMarksSupplementaryTO.getIsTheoryPrac());
			}else{
				examOverAllInternalMarksTO.setStudentId(examStudentOverallInternalMarkDetailsBO.getStudentId());
				examOverAllInternalMarksTO.setExamId(examStudentOverallInternalMarkDetailsBO.getExamId());
				examOverAllInternalMarksTO.setClassId(examStudentOverallInternalMarkDetailsBO.getClassId());
				examOverAllInternalMarksTO.setSubjectId(examStudentOverallInternalMarkDetailsBO.getSubjectId());
				examOverAllInternalMarksTO.setSubjectCode(examStudentOverallInternalMarkDetailsBO.getSubject().getCode());
				examOverAllInternalMarksTO.setSubjectName(examStudentOverallInternalMarkDetailsBO.getSubject().getName());
				examOverAllInternalMarksTO.setTheoryTotalSubInternalMarks(examStudentOverallInternalMarkDetailsBO.getTheoryTotalSubInternalMarks());
				examOverAllInternalMarksTO.setPracticalTotalSubInternalMarks(examStudentOverallInternalMarkDetailsBO.getPracticalTotalSubInternalMarks());
				examOverAllInternalMarksTO.setPracticalTotalAttendenceMarks(examStudentOverallInternalMarkDetailsBO.getPracticalTotalAttendenceMarks());
				examOverAllInternalMarksTO.setTheoryTotalAttendenceMarks(examStudentOverallInternalMarkDetailsBO.getTheoryTotalAttendenceMarks());
				examOverAllInternalMarksTO.setTheoryMarks(examStudentOverallInternalMarkDetailsBO.getTheoryTotalMarks());
				examOverAllInternalMarksTO.setPracticalMarks(examStudentOverallInternalMarkDetailsBO.getPracticalTotalMarks());
				examOverAllInternalMarksTO.setPassOrFail(examStudentOverallInternalMarkDetailsBO.getPassOrFail());
				examOverAllInternalMarksTO.setIsTheoryPrac(examStudentOverallInternalMarkDetailsBO.getSubject().getIsTheoryPractical());
			}
			examOverAllInternalMarksTOList.add(examOverAllInternalMarksTO);
		}
		Collections.sort(examOverAllInternalMarksTOList);
		return examOverAllInternalMarksTOList;
	}
	
	public Map<Integer, ExamInternalMarksSupplementaryTO> convertBOToTOList(List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs) throws Exception{
		Map<Integer, ExamInternalMarksSupplementaryTO> supplementaryInternalMarkMap =  new HashMap<Integer, ExamInternalMarksSupplementaryTO>();
		ExamInternalMarksSupplementaryTO examInternalMarksSupplementaryTO=null;
		Iterator<ExamInternalMarkSupplementaryDetailsBO> iterator = examInternalMarkSupplementaryDetailsBOs.iterator();
		while (iterator.hasNext()) {
			ExamInternalMarkSupplementaryDetailsBO examInternalMarkSupplementaryDetailsBO = (ExamInternalMarkSupplementaryDetailsBO) iterator.next();
			examInternalMarksSupplementaryTO=new ExamInternalMarksSupplementaryTO();
			examInternalMarksSupplementaryTO.setSubjectId(examInternalMarkSupplementaryDetailsBO.getSubject().getId());
			examInternalMarksSupplementaryTO.setSubjectCode(examInternalMarkSupplementaryDetailsBO.getSubject().getCode());
			examInternalMarksSupplementaryTO.setSubjectName(examInternalMarkSupplementaryDetailsBO.getSubject().getName());
			examInternalMarksSupplementaryTO.setTheoryTotalSubInternalMarks(examInternalMarkSupplementaryDetailsBO.getTheoryTotalSubInternalMarks());
			examInternalMarksSupplementaryTO.setPracticalTotalSubInternalMarks(examInternalMarkSupplementaryDetailsBO.getPracticalTotalSubInternalMarks());
			examInternalMarksSupplementaryTO.setIsTheoryPrac(examInternalMarkSupplementaryDetailsBO.getSubject().getIsTheoryPractical());
			examInternalMarksSupplementaryTO.setId(examInternalMarkSupplementaryDetailsBO.getId());
			examInternalMarksSupplementaryTO.setClassId(examInternalMarkSupplementaryDetailsBO.getClasses().getId());
			examInternalMarksSupplementaryTO.setExamId(examInternalMarkSupplementaryDetailsBO.getExamDefinitionBO().getId());
			examInternalMarksSupplementaryTO.setStudentId(examInternalMarkSupplementaryDetailsBO.getStudent().getId());
			supplementaryInternalMarkMap.put(examInternalMarkSupplementaryDetailsBO.getSubject().getId(), examInternalMarksSupplementaryTO);
		}
//		supplementaryInternalMarkMap = (HashMap<Integer, ExamInternalMarksSupplementaryTO>) CommonUtil.sortMapByValue(supplementaryInternalMarkMap); // Code Commented By balaji
		return supplementaryInternalMarkMap;
	}
	
	public List<ExamInternalMarkSupplementaryDetailsBO> convertTOToBO(ExamInternalMarksSupplementaryForm objform, String studentId,Map<Integer,ExamInternalMarksSupplementaryTO> examInternalMarksSupplementaryMap)throws Exception {
		List<ExamInternalMarkSupplementaryDetailsBO> examInternalMarkSupplementaryDetailsBOs = new ArrayList<ExamInternalMarkSupplementaryDetailsBO>();
		ExamInternalMarkSupplementaryDetailsBO examInternalMarkSupplementaryDetailsBO=null;
		List<ExamInternalMarksSupplementaryTO> subjects = objform.getSubjects();
		Iterator<ExamInternalMarksSupplementaryTO> iterator = subjects.iterator();
		while (iterator.hasNext()) {
			ExamInternalMarksSupplementaryTO examInternalMarksSupplementaryTO = (ExamInternalMarksSupplementaryTO) iterator.next();
			examInternalMarkSupplementaryDetailsBO = new ExamInternalMarkSupplementaryDetailsBO();
			if(examInternalMarksSupplementaryMap.size()>0 && examInternalMarksSupplementaryMap.containsKey(examInternalMarksSupplementaryTO.getSubjectId())){
			ExamInternalMarksSupplementaryTO examSupplementaryTO=examInternalMarksSupplementaryMap.get(examInternalMarksSupplementaryTO.getSubjectId());
			//if(!examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks().equals(examSupplementaryTO.getTheoryTotalSubInternalMarks()) || !examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks().equals(examSupplementaryTO.getPracticalTotalSubInternalMarks())){
			examInternalMarkSupplementaryDetailsBO.setId(examSupplementaryTO.getId());
			Classes classes = new Classes();
			classes.setId(examSupplementaryTO.getClassId());
			examInternalMarkSupplementaryDetailsBO.setClasses(classes);
			Student student = new Student();
			student.setId(examSupplementaryTO.getStudentId());
			examInternalMarkSupplementaryDetailsBO.setStudent(student);
			ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
			
			Subject subject = new Subject();
			subject.setId(examSupplementaryTO.getSubjectId());
			examInternalMarkSupplementaryDetailsBO.setSubject(subject);
			if(!examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks().equals(examSupplementaryTO.getTheoryTotalSubInternalMarks()) || !examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks().equals(examSupplementaryTO.getPracticalTotalSubInternalMarks())){
				examDefinitionBO.setId(Integer.parseInt(objform.getExamNameId()));
				examInternalMarkSupplementaryDetailsBO.setExamDefinitionBO(examDefinitionBO);
			}
			else{
				examDefinitionBO.setId(examSupplementaryTO.getExamId());
				examInternalMarkSupplementaryDetailsBO.setExamDefinitionBO(examDefinitionBO);
			}
			if(!examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks().equals(examSupplementaryTO.getTheoryTotalSubInternalMarks())){
				examInternalMarkSupplementaryDetailsBO.setTheoryTotalSubInternalMarks(examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks());
			}
			else{
				examInternalMarkSupplementaryDetailsBO.setTheoryTotalSubInternalMarks(examSupplementaryTO.getTheoryTotalSubInternalMarks());
			}
			if(!examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks().equals(examSupplementaryTO.getPracticalTotalSubInternalMarks())){
				examInternalMarkSupplementaryDetailsBO.setPracticalTotalSubInternalMarks(examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks());
			}
			else{
				examInternalMarkSupplementaryDetailsBO.setPracticalTotalSubInternalMarks(examSupplementaryTO.getPracticalTotalSubInternalMarks());
			}
			}else
			{
				Student student = new Student();
				student.setId(examInternalMarksSupplementaryTO.getStudentId());
				examInternalMarkSupplementaryDetailsBO.setStudent(student);
				ExamDefinitionBO examDefinitionBO = new ExamDefinitionBO();
				examDefinitionBO.setId(examInternalMarksSupplementaryTO.getExamId());
				examInternalMarkSupplementaryDetailsBO.setExamDefinitionBO(examDefinitionBO);
				Classes classes = new Classes();
				classes.setId(examInternalMarksSupplementaryTO.getClassId());
				examInternalMarkSupplementaryDetailsBO.setClasses(classes);
				Subject subject = new Subject();
				subject.setId(examInternalMarksSupplementaryTO.getSubjectId());
				examInternalMarkSupplementaryDetailsBO.setSubject(subject);
				examInternalMarkSupplementaryDetailsBO.setTheoryTotalSubInternalMarks(examInternalMarksSupplementaryTO.getTheoryTotalSubInternalMarks());
				examInternalMarkSupplementaryDetailsBO.setPracticalTotalSubInternalMarks(examInternalMarksSupplementaryTO.getPracticalTotalSubInternalMarks());
				examInternalMarkSupplementaryDetailsBO.setPracticalTotalAttendenceMarks(examInternalMarksSupplementaryTO.getPracticalTotalAttendenceMarks());
				examInternalMarkSupplementaryDetailsBO.setTheoryTotalAttendenceMarks(examInternalMarksSupplementaryTO.getTheoryTotalAttendenceMarks());
				examInternalMarkSupplementaryDetailsBO.setTheoryTotalMarks(examInternalMarksSupplementaryTO.getTheoryMarks());
				examInternalMarkSupplementaryDetailsBO.setPracticalTotalMarks(examInternalMarksSupplementaryTO.getPracticalMarks());
				examInternalMarkSupplementaryDetailsBO.setPassOrFail(examInternalMarksSupplementaryTO.getPassOrFail());
			}
			if(examInternalMarkSupplementaryDetailsBO!=null)
			examInternalMarkSupplementaryDetailsBOs.add(examInternalMarkSupplementaryDetailsBO);
		}
		//Collections.sort(examInternalMarkSupplementaryDetailsBOs,new examInternalMarkSupplementaryDetailsComparator());
		return examInternalMarkSupplementaryDetailsBOs;
	}
}
