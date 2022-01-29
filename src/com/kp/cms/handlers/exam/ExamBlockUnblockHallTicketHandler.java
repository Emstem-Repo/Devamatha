package com.kp.cms.handlers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;

import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.bo.exam.ExamDefinitionBO;
import com.kp.cms.bo.exam.ExamSupplementaryImprovementApplicationBO;
import com.kp.cms.forms.exam.ExamBlockUnblockForm;
import com.kp.cms.helpers.exam.ExamBlockUnblockHallTicketHelper;
import com.kp.cms.to.exam.ExamBlockUnBlockCandidatesTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactionsimpl.exam.ExamBlockUnblockHallTicketImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;

public class ExamBlockUnblockHallTicketHandler extends ExamGenHandler {

	ExamBlockUnblockHallTicketImpl impl = new ExamBlockUnblockHallTicketImpl();

	ExamBlockUnblockHallTicketHelper helper = new ExamBlockUnblockHallTicketHelper();

	public ArrayList<KeyValueTO> getExamNameList() throws Exception {
		return helper.convertBOToTO_ExamNameList(impl.select());

	}

	public ArrayList<ExamBlockUnBlockCandidatesTO> getData_block(int examId,
			ArrayList<Integer> listClassId, boolean useHallTicket, String isPrevious,String type)
			throws Exception {
		StringBuffer classIdString = new StringBuffer("");
		Map<Integer,String> detainOrDiscontinueMap=getDetainOrDiscontinueStudentsMap();
		Iterator<Integer> it = listClassId.iterator();
		while (it.hasNext()) {
			Integer clsId = (Integer) it.next();
			classIdString.append(clsId.toString() + ",");
		}
		String classIds = classIdString.toString();
		if(classIds.endsWith(",")){
			classIds = StringUtils.chop(classIds);
		}
		int examType = impl.getExamType(examId);
		ArrayList<ExamBlockUnBlockCandidatesTO> blockSupList = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		if(examType == 3 || examType == 6 || examType == 2){
			ArrayList<ExamSupplementaryImprovementApplicationBO>  studentList = impl.getSupStudentDetails(classIds, examId);
			//raghu
			blockSupList = helper.convertBOToTO_block_sup_Students(studentList, helper.convertBOToMap(impl.select_checkMarks(examId,
								listClassId, useHallTicket,type)), examId, true,detainOrDiscontinueMap);
		}
		if(examType == 3 || examType == 6){
			return blockSupList;
		}else
		if(isPrevious.equalsIgnoreCase("true")){
			ArrayList<ExamBlockUnBlockCandidatesTO> blockList = 
					helper.convertBOToTO_block_Previous_Students(impl.getPreviousStudentDetails(classIds),
							helper.convertBOToMap(impl.select_checkMarks(examId,
									listClassId, useHallTicket,type)), examId, true,detainOrDiscontinueMap);
			if(examType == 2){
				blockList.addAll(blockSupList);
			}
			return blockList;
		}
		else{
			ArrayList<ExamBlockUnBlockCandidatesTO> blockList = helper.convertBOToTO_block(impl.select_Students(listClassId),
					helper.convertBOToMap(impl.select_checkMarks(examId, listClassId, useHallTicket,type)), examId,detainOrDiscontinueMap);
			if(examType == 2){
				blockList.addAll(blockSupList);
			}
			return blockList;
		}

	}

	/**
	 * @return
	 * @throws Exception
	 */
	private Map<Integer, String> getDetainOrDiscontinueStudentsMap() throws Exception {
		Map<Integer, String> map=new HashMap<Integer, String>();
		String query="select s.student.id,s.detain,s.discontinued from ExamStudentDetentionRejoinDetails s " +
		"where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)";
		INewExamMarksEntryTransaction transaction=NewExamMarksEntryTransactionImpl.getInstance();
		List<Object[]> list=transaction.getDataForQuery(query);
		if(list!=null && !list.isEmpty()){
			Iterator<Object[]> itr=list.iterator();
			while (itr.hasNext()) {
				Object[] obj= (Object[]) itr.next();
				if(obj[1]!=null && (obj[1].toString().equalsIgnoreCase("1") || obj[1].toString().equalsIgnoreCase("true"))){
					map.put(Integer.parseInt(obj[0].toString()),"Detained");
				}
				else if(obj[2]!=null && (obj[2].toString().equalsIgnoreCase("1") || obj[2].toString().equalsIgnoreCase("true"))){
					map.put(Integer.parseInt(obj[0].toString()),"Discontinued");
				}
			}
		}
		return map;
	}

	/**
	 * @param examId
	 * @param listClassId
	 * @param useHallTicket
	 * @param isPrevious
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamBlockUnBlockCandidatesTO> getData_unblock(int examId,
			ArrayList<Integer> listClassId, boolean useHallTicket, String isPrevious,String type)
			throws Exception {
		Map<Integer,String> detainOrDiscontinueMap=getDetainOrDiscontinueStudentsMap();
		StringBuffer classIdString = new StringBuffer("");
		
		Iterator<Integer> it = listClassId.iterator();
		while (it.hasNext()) {
			Integer clsId = (Integer) it.next();
			classIdString.append(clsId.toString() + ",");
		}
		String classIds = classIdString.toString();
		if(classIds.endsWith(",")){
			classIds = StringUtils.chop(classIds);
		}
		int examType = impl.getExamType(examId);
		ArrayList<ExamBlockUnBlockCandidatesTO> blockSupList = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		if(examType == 3 || examType == 6 || examType == 2){
			ArrayList<ExamSupplementaryImprovementApplicationBO>  studentList = impl.getSupStudentDetails(classIds, examId);
			blockSupList = helper.convertBOToTO_block_sup_Students(studentList, helper.convertBOToMap(impl.select_checkMarks(examId,
								listClassId, useHallTicket,type)), examId, false,detainOrDiscontinueMap);
		}
		if(examType == 3 || examType == 6){
			return blockSupList;
		}else
		if(isPrevious.equalsIgnoreCase("true")){
			//raghu
			ArrayList<ExamBlockUnBlockCandidatesTO> blockList =  helper.convertBOToTO_block_Previous_Students(impl.getPreviousStudentDetails(classIds),
					helper.convertBOToMap(impl.select_checkMarks(examId,
							listClassId, useHallTicket,type)), examId, false,detainOrDiscontinueMap);
			if(examType == 2){
				blockList.addAll(blockSupList);
			}
			return blockList;
		}else{
			ArrayList<ExamBlockUnBlockCandidatesTO> blockList = helper.convertBOToTO_unblock(impl.select_Students(listClassId),
							helper.convertBOToMap(impl.select_checkMarks(examId,
									listClassId, useHallTicket,type)), examId,detainOrDiscontinueMap);
			if(examType == 2){
				blockList.addAll(blockSupList);
			}
			return blockList;
		}

	}

	public ArrayList<Integer> unblock(String stringOfClassId_StudentId, List<ExamBlockUnBlockCandidatesTO> listCandidateNames) throws Exception {
		ArrayList<Integer> l = new ArrayList<Integer>();
		if(listCandidateNames!= null && listCandidateNames.size() > 0){
			Iterator<ExamBlockUnBlockCandidatesTO> listItr = listCandidateNames.iterator();
			while (listItr.hasNext()) {
				ExamBlockUnBlockCandidatesTO examBlockUnBlockCandidatesTO = (ExamBlockUnBlockCandidatesTO) listItr
						.next();
				
				if(examBlockUnBlockCandidatesTO.getIsSelected()){
					l.add(examBlockUnBlockCandidatesTO.getId());
				}
				
			} 
		}
		
		
		
		/*if (stringOfClassId_StudentId != null) {

			String[] classId_StudentId = stringOfClassId_StudentId.split(",");
			for (int i = 0; i < classId_StudentId.length; i++) {

				l.add(getInt(classId_StudentId[i]));

			}
		}*/
		//impl.delete(l);
		return l;

	}
	public void unblockStudents(ArrayList<Integer> deleteList) throws Exception {
		impl.delete(deleteList);
	}

	private Integer getInt(String string) throws Exception {
		try {
			return new Integer(string.trim());
		} catch (Exception e) {
			return 0;
		}

	}

	public void block(
			ArrayList<ExamBlockUnblockHallTicketBO> listExamBlockUnblockHallTicketBO,ExamBlockUnblockForm objform )
			throws Exception {
		impl.insert_List(listExamBlockUnblockHallTicketBO,objform);
	}

	public String getExamName(String examId) throws Exception {
		int id;
		try {
			id = Integer.parseInt(examId);
			return ((ExamDefinitionBO) impl.select_Unique(id,
					ExamDefinitionBO.class)).getName();
		} catch (Exception e) {
			return "";
		}
	}

	public int getcurrentExam() throws Exception {

		return impl.getCurrentExam();
	}

	public List<ExamBlockUnBlockCandidatesTO> getListOfCandidates(int examId,
			ArrayList<Integer> listClassIds, boolean useHallTicket,char type) throws Exception {
		//raghu
		List<ExamBlockUnblockHallTicketBO> list=ExamBlockUnblockHallTicketImpl.getInstance().getListOfCandidates(examId,listClassIds,useHallTicket,type);
		Map<Integer,String> detainOrDiscontinueMap=getDetainOrDiscontinueStudentsMap();
		return ExamBlockUnblockHallTicketHelper.convertBOtoTO(list,detainOrDiscontinueMap);
	}

	public void updateStudentsRemarks(List<ExamBlockUnBlockCandidatesTO> listCandidatesName) throws Exception {
		ExamBlockUnblockHallTicketImpl.getInstance().updateStudentsRemarks(listCandidatesName);
		
	}

	/**
	 * @param objform
	 * @return
	 * @throws Exception
	 */
	public ArrayList<ExamBlockUnBlockCandidatesTO> getListOfBlockedCandidates(
			ExamBlockUnblockForm objform) throws Exception{
		ArrayList<Integer> listClassIds = new ArrayList<Integer>();
		if (objform.getClassValue() != null && objform.getClassValue().length != 0) {
			for (int x = 0; x < objform.getClassValue().length; x++) {
				if (objform.getClassValue()[x] != null && objform.getClassValue()[x].trim().length() > 0) {
					listClassIds.add(Integer.parseInt(objform.getClassValue()[x]));
				}
			}
		}
		List<Object[]> boList = impl.getListOfBlockedCandidates(objform.getExamType(),objform.getTypeId(),objform.getYear(),objform.getExamName(),listClassIds);
		ArrayList<ExamBlockUnBlockCandidatesTO> list = new ArrayList<ExamBlockUnBlockCandidatesTO>();
		if(boList != null && !boList.isEmpty()){
			Map<Integer,String> detainOrDiscontinueMap=getDetainOrDiscontinueStudentsMap();
			int examId=0;
			for (Object[] objects : boList) {
				ExamBlockUnBlockCandidatesTO to = new ExamBlockUnBlockCandidatesTO();
				if(objects[0] != null && objects[0].toString() != null){
					to.setId(Integer.parseInt(objects[0].toString()));
					if(objects[1] != null){
						to.setExamId(Integer.parseInt(objects[1].toString()));
						if(examId == 0 || examId != Integer.parseInt(objects[1].toString())){
							to.setExamName(getExamName(objects[1].toString()));
						}
						examId = Integer.parseInt(objects[1].toString());
					}
					if(objects[2] != null)
						to.setStudentId(Integer.parseInt(objects[2].toString()));
					if(objects[3] != null)
						to.setClassId(Integer.parseInt(objects[3].toString()));
					if(objects[4] != null)
						to.setName(objects[4].toString());
					if(objects[5] != null){
						to.setReason(objects[5].toString());
						to.setReasonOld(objects[5].toString());
					}
					if(objects[6] != null)
						to.setRegNumber(objects[6].toString());
					if(objects[7] != null)
						to.setClassName(objects[7].toString());
					if(detainOrDiscontinueMap.containsKey(Integer.parseInt(objects[2].toString())))
						to.setStatus(detainOrDiscontinueMap.get(Integer.parseInt(objects[2].toString())));
					list.add(to);
				}
			}
		}
		return list;
	}

	/**
	 * @param objform
	 * @throws Exception
	 */
	public void updateStudentsUnBlockAndReason(ExamBlockUnblockForm objform) throws Exception{
		if(objform.getListCandidatesName() != null){
			List<ExamBlockUnblockHallTicketBO> bolist = new ArrayList<ExamBlockUnblockHallTicketBO>();
			ArrayList<Integer> unBlockIds = new ArrayList<Integer>();
			for (ExamBlockUnBlockCandidatesTO to : objform.getListCandidatesName()) {
				if(objform.getBlockType().equalsIgnoreCase("Unblock")){
					if(to.getIsSelected() != null && to.getIsSelected()){
						unBlockIds.add(to.getId());
					}
				}else{
					if(!to.getReason().equalsIgnoreCase(to.getReasonOld())){
						ExamBlockUnblockHallTicketBO bo = new ExamBlockUnblockHallTicketBO();
						bo.setId(to.getId());
						bo.setExamId(to.getExamId());
						bo.setStudentId(to.getStudentId());
						bo.setClassId(to.getClassId());
						if(objform.getTypeId().equalsIgnoreCase("1"))
							bo.setHallTktOrMarksCard("H");
						else if(objform.getTypeId().equalsIgnoreCase("3"))
							bo.setHallTktOrMarksCard("A");
						else
							bo.setHallTktOrMarksCard("M");
						bo.setModifiedBy(objform.getUserId());
						bo.setLastModifiedDate(new Date());
						bo.setBlockReason(to.getReason());
						bolist.add(bo);
					}
				}
			}
			if(!unBlockIds.isEmpty()){
				impl.delete(unBlockIds);
			}
			if(!bolist.isEmpty())
				impl.updateStudentBlockUnBlockAndReason(bolist);
		}
	}

}
