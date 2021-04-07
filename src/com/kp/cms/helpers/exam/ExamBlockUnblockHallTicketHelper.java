package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.exam.ClassSchemewiseUtilBO;
import com.kp.cms.bo.exam.ClassUtilBO;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamStudentPreviousClassDetailsBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.bo.exam.StudentUtilBO;
import com.kp.cms.forms.exam.ExamBlockUnblockForm;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.KeyValueTOComparator;

public class ExamBlockUnblockHallTicketHelper {

	/* get list of students not yet blocked for the input parameters */
	public ArrayList<ExamBlockUnBlockCandidatesTO> convertBOToTO_block(
			ArrayList<ClassUtilBO> listStudents,
			HashMap<Integer, String> mapBlockedStudents, int examId,Map<Integer,String> detainOrDiscontinueMap) throws Exception {
		ArrayList<ExamBlockUnBlockCandidatesTO> listTO = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		ExamBlockUnBlockCandidatesTO eTO;
		String className;
		Integer classId;
		for (ClassUtilBO eBO : listStudents) {
			className = eBO.getName();
			classId = eBO.getId();
			Iterator<ClassSchemewiseUtilBO> ics = eBO
					.getClassSchemewiseUtilBOSet().iterator();
			while (ics.hasNext()) {
				ClassSchemewiseUtilBO classSchemewiseUtilBO = (ClassSchemewiseUtilBO) ics
						.next();
				Iterator<StudentUtilBO> iStu = classSchemewiseUtilBO
						.getStudentUtilBOSet().iterator();
				while (iStu.hasNext()) {
					StudentUtilBO studentUtilBO = (StudentUtilBO) iStu.next();
					// exclude students present in mapBlockedStudents
					//added by chandra
					if(studentUtilBO.getAdmApplnUtilBO().getIsCancelled()==0 || Integer.valueOf(studentUtilBO.getAdmApplnUtilBO().getIsCancelled())== null){
					if (!mapBlockedStudents.containsKey(studentUtilBO.getId())) {
						eTO = new ExamBlockUnBlockCandidatesTO();
						eTO.setStudentId(studentUtilBO.getId());
						eTO.setExamId(examId);
						eTO.setClassName(className);
						eTO.setClassId(classId);
						eTO.setRegNumber(studentUtilBO.getRegisterNo());
						eTO.setRollNumber(studentUtilBO.getRollNo());
						eTO.setName(studentUtilBO.getAdmApplnUtilBO()
								.getPersonalDataUtilBO().getName());
						eTO.setReason("");
						eTO.setIsSelected(false);
						if(detainOrDiscontinueMap.containsKey(studentUtilBO.getId())){
							eTO.setStatus(detainOrDiscontinueMap.get(studentUtilBO.getId()));
						}
						listTO.add(eTO);
					}
					}
				}
			}
		}
		Collections.sort(listTO);
		return listTO;
	}

	/* get list of students not yet blocked for the input parameters */
	/**
	 * @param listStudents
	 * @param mapBlockedStudents
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamBlockUnBlockCandidatesTO> convertBOToTO_unblock(
			ArrayList<ClassUtilBO> listStudents,
			HashMap<Integer, String> mapBlockedStudents, int examId,Map<Integer,String> detainOrDiscontinueMap) throws Exception {
		ArrayList<ExamBlockUnBlockCandidatesTO> listTO = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		ExamBlockUnBlockCandidatesTO eTO;
		String className;
		Integer classId;
		for (ClassUtilBO eBO : listStudents) {
			className = eBO.getName();
			classId = eBO.getId();
			Iterator<ClassSchemewiseUtilBO> ics = eBO
					.getClassSchemewiseUtilBOSet().iterator();
			while (ics.hasNext()) {
				ClassSchemewiseUtilBO classSchemewiseUtilBO = (ClassSchemewiseUtilBO) ics
						.next();
				Iterator<StudentUtilBO> iStu = classSchemewiseUtilBO
						.getStudentUtilBOSet().iterator();
				while (iStu.hasNext()) {
					StudentUtilBO studentUtilBO = (StudentUtilBO) iStu.next();
					// include only students present in mapBlockedStudents
					String reason="";
					if (mapBlockedStudents.containsKey(studentUtilBO.getId())) {
						eTO = new ExamBlockUnBlockCandidatesTO();
						String[] id=mapBlockedStudents.get(studentUtilBO.getId()).split("_");
						int len=id.length;
						if(len>=2){
						reason=id[1];
						}
						eTO.setId(Integer.parseInt(id[0]));
						eTO.setReason(reason);
						/*eTO
								.setId(mapBlockedStudents.get(studentUtilBO8888
										.getId()));*/
						eTO.setStudentId(studentUtilBO.getId());
						eTO.setExamId(examId);
						eTO.setClassName(className);
						eTO.setClassId(classId);
						eTO.setRegNumber(studentUtilBO.getRegisterNo());
						eTO.setRollNumber(studentUtilBO.getRollNo());
						eTO.setName(studentUtilBO.getAdmApplnUtilBO()
								.getPersonalDataUtilBO().getName());
//						eTO.setReason("");
						eTO.setIsSelected(false);
						if(detainOrDiscontinueMap.containsKey(studentUtilBO.getId())){
							eTO.setStatus(detainOrDiscontinueMap.get(studentUtilBO.getId()));
						}
						listTO.add(eTO);
					}
				}
			}
		}
		return listTO;
	}

	public ArrayList<ExamBlockUnblockHallTicketBO> createBOList(String examId,
			String hallTktOrMarksCard, String stringOfClassId_StudentId,
			String userId, List<ExamBlockUnBlockCandidatesTO> listCandidateNames) throws Exception {
		ArrayList<ExamBlockUnblockHallTicketBO> listBO = new ArrayList<ExamBlockUnblockHallTicketBO>();
		if(listCandidateNames!= null && listCandidateNames.size() > 0){
			Iterator<ExamBlockUnBlockCandidatesTO> listItr = listCandidateNames.iterator();
			while (listItr.hasNext()) {
				ExamBlockUnBlockCandidatesTO examBlockUnBlockCandidatesTO = (ExamBlockUnBlockCandidatesTO) listItr
						.next();
				
				if(examBlockUnBlockCandidatesTO.getIsSelected()){
					ExamBlockUnblockHallTicketBO eBO = new ExamBlockUnblockHallTicketBO(
							Integer.toString(examBlockUnBlockCandidatesTO.getClassId()), Integer.toString(examBlockUnBlockCandidatesTO.getExamId()),
							hallTktOrMarksCard, Integer.toString(examBlockUnBlockCandidatesTO.getStudentId()), userId, examBlockUnBlockCandidatesTO.getReason());
					
					listBO.add(eBO);
				}
				
			} 
		}


		return listBO;
	}

	public ArrayList<KeyValueTO> convertBOToTO_ExamNameList(
			ArrayList<ExamDefinitionBO> listBO) throws Exception {
		ArrayList<KeyValueTO> listTO = new ArrayList<KeyValueTO>();
		for (ExamDefinitionBO bo : listBO) {
			listTO.add(new KeyValueTO(bo.getId(), bo.getName()));

		}
		Collections.sort(listTO, new KeyValueTOComparator());
		return listTO;
	}

	public HashMap<Integer, String> convetBOToMap(
			ArrayList<ClassUtilBO> listClassUtil) throws Exception {
		HashMap<Integer, String> mapClasses = new HashMap<Integer, String>();
		for (ClassUtilBO c : listClassUtil) {
			mapClasses.put(c.getId(), c.getName());
		}
		mapClasses = (HashMap<Integer, String>) CommonUtil.sortMapByValue(mapClasses);
		return mapClasses;
	}

	public HashMap<Integer, String> convertBOToMap(
			ArrayList<ExamBlockUnblockHallTicketBO> select_checkMarks) throws Exception {
		// map of StudentID as Key and BlockedID as value
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (ExamBlockUnblockHallTicketBO e : select_checkMarks) {
			try {
					map.put(e.getStudentId(), e.getId()+"_"+e.getBlockReason());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return map;
	}
	/**
	 * 
	 * @param listStudents
	 * @param mapBlockedStudents
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamBlockUnBlockCandidatesTO> convertBOToTO_block_Previous_Students(
			ArrayList<ExamStudentPreviousClassDetailsBO> listStudents,
			HashMap<Integer, String> mapBlockedStudents, int examId, boolean isBlock,Map<Integer,String> detainOrDiscontinueMap) throws Exception {
		ArrayList<ExamBlockUnBlockCandidatesTO> listTO = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		ExamBlockUnBlockCandidatesTO eTO;
		for (ExamStudentPreviousClassDetailsBO eBO : listStudents) {
			eTO = new ExamBlockUnBlockCandidatesTO();
			boolean addToList = false;
			String reason="";
			if(isBlock){
				if (!mapBlockedStudents.containsKey(eBO.getStudentId())){
					addToList = true;
				}
			}
			else{
				if (mapBlockedStudents.containsKey(eBO.getStudentId())){
					addToList = true;
					String[] id=mapBlockedStudents.get(eBO.getStudentId()).split("_");
					reason=id[1];
					eTO.setId(Integer.parseInt(id[0]));
					eTO.setReason(reason);
				}
			}
			if (addToList) {
				eTO.setStudentId(eBO.getStudentId());
				eTO.setExamId(examId);
				eTO.setClassName( eBO.getClassUtilBO().getName());
				eTO.setClassId(eBO.getClassId());
				eTO.setRegNumber(eBO.getStudentUtilBO().getRegisterNo());
				eTO.setRollNumber(eBO.getStudentUtilBO().getRollNo());
				eTO.setName(eBO.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getName());
				eTO.setReason(reason);
				eTO.setIsSelected(false);
				if(detainOrDiscontinueMap.containsKey(eBO.getStudentId())){
					eTO.setStatus(detainOrDiscontinueMap.get(eBO.getStudentId()));
				}
				listTO.add(eTO);
			}
		}
		Collections.sort(listTO);
		return listTO;
	}
	
	/**
	 * 
	 * @param listStudents
	 * @param mapBlockedStudents
	 * @param examId
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamBlockUnBlockCandidatesTO> convertBOToTO_block_sup_Students(
			ArrayList<ExamSupplementaryImprovementApplicationBO> listStudents,
			HashMap<Integer, String> mapBlockedStudents, int examId, boolean isBlock,Map<Integer,String> detainOrDiscontinueMap) throws Exception {
		ArrayList<ExamBlockUnBlockCandidatesTO> listTO = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		ExamBlockUnBlockCandidatesTO eTO;
		for (ExamSupplementaryImprovementApplicationBO eBO : listStudents) {
			eTO = new ExamBlockUnBlockCandidatesTO();
			boolean addToList = false;
			String reason="";
			if(isBlock){
				if (!mapBlockedStudents.containsKey(eBO.getStudentId())){
					addToList = true;
				}
			}
			else{
				if (mapBlockedStudents.containsKey(eBO.getStudentId())){
					addToList = true;
					String[] id=mapBlockedStudents.get(eBO.getStudentId()).split("_");
					reason=id[1];
					eTO.setId(Integer.parseInt(id[0]));
					eTO.setReason(reason);
					
				}
			}
			if (addToList) {
				eTO.setStudentId(eBO.getStudentId());
				eTO.setExamId(examId);
				eTO.setClassName( eBO.getClasses().getName());
				eTO.setClassId(eBO.getClasses().getId());
				eTO.setRegNumber(eBO.getStudentUtilBO().getRegisterNo());
				eTO.setRollNumber(eBO.getStudentUtilBO().getRollNo());
				eTO.setName(eBO.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getName());
				eTO.setReason(reason);
				eTO.setIsSelected(false);
				if(detainOrDiscontinueMap.containsKey(eBO.getStudentId())){
					eTO.setStatus(detainOrDiscontinueMap.get(eBO.getStudentId()));
				}
				listTO.add(eTO);
			}
		}
		Collections.sort(listTO);
		return listTO;
	}

	/**
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public static List<ExamBlockUnBlockCandidatesTO> convertBOtoTO(List<ExamBlockUnblockHallTicketBO> list,Map<Integer,String> detainOrDiscontinueMap) throws Exception {
		List<ExamBlockUnBlockCandidatesTO> tos=new ArrayList<ExamBlockUnBlockCandidatesTO>();
		if(list!=null && !list.isEmpty()){
			Iterator<ExamBlockUnblockHallTicketBO> itr=list.iterator();
			while (itr.hasNext()) {
				ExamBlockUnblockHallTicketBO bo = (ExamBlockUnblockHallTicketBO) itr.next();
				ExamBlockUnBlockCandidatesTO to=new ExamBlockUnBlockCandidatesTO();
				to.setId(bo.getId());
				to.setName(bo.getStudentUtilBO().getAdmApplnUtilBO().getPersonalDataUtilBO().getFirstName());
				to.setRegNumber(bo.getStudentUtilBO().getRegisterNo());
				to.setReason(bo.getBlockReason());
				to.setClassName(bo.getClassUtilBO().getName());
				if(detainOrDiscontinueMap.containsKey(bo.getStudentId())){
					to.setStatus(detainOrDiscontinueMap.get(bo.getStudentId()));
				}
				tos.add(to);
			}
		}
		return tos;
	}

}
