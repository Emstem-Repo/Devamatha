package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm;
import com.kp.cms.helpers.examallotment.ExamRoomAllotmentForCJCHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.transactions.examallotment.IExamRoomAllotmentForCJCTransaction;
import com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction;
import com.kp.cms.transactionsimpl.examallotment.ExamRoomAllotmentForCJCTxnImpl;
import com.kp.cms.transactionsimpl.examallotment.RoomAllotmentStatusTxnImpl;

public class ExamRoomAllotmentForCJCHandler {
	private static volatile ExamRoomAllotmentForCJCHandler handler;
	public static ExamRoomAllotmentForCJCHandler getInstance(){
		if(handler == null){
			handler = new ExamRoomAllotmentForCJCHandler();
		}
		return handler;
	}
	private ExamRoomAllotmentForCJCHandler(){
		
	}
	IExamRoomAllotmentForCJCTransaction transaction = ExamRoomAllotmentForCJCTxnImpl.getInstance();
	/**
	 * @param objForm
	 * @param request
	 * @param examType 
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusTo> getRoomDetailsAllotment(ExamRoomAllotmentForCJCForm objForm, HttpServletRequest request, String examType) throws Exception{
		/* This Query gets the details of Selected room*/
		String roomDetailsQuery = ExamRoomAllotmentForCJCHelper.getInstance().getRoomDetails(Integer.parseInt(objForm.getRoomId()),examType);
		List<RoomEndMidSemRows> roomEndMidSemRows =  transaction.getDetailsForQuery(roomDetailsQuery);
		/*--------------------------------------------*/
		/* the below Query gets the maxRows among all columns in the room.*/
		IRoomAllotmentStatusTransaction itxn = RoomAllotmentStatusTxnImpl.getInstance();
		int maxRowCount = itxn.getMaxRowCount(Integer.parseInt(objForm.getRoomId()),examType);
		
		String getStudentAllotedDetails = "";
		if(request.getParameter("propertyName").equalsIgnoreCase("Cycle")){
//			the below query gets if existing data of the selected room for Cycle.
			 getStudentAllotedDetails = ExamRoomAllotmentForCJCHelper.getInstance().getStudentsAllotedForCycleQuery(objForm,examType);
		}else if(request.getParameter("propertyName").equalsIgnoreCase("DateSession")){
//			the below query gets if existing data of the selected room for date and session.
			 getStudentAllotedDetails = ExamRoomAllotmentForCJCHelper.getInstance().getStudentsAllotedForDateSessionQuery(objForm,examType);
		}
		ExamRoomAllotment roomAllotmentList=(ExamRoomAllotment) transaction.getDetailsForQuery1(getStudentAllotedDetails);
		List<RoomAllotmentStatusTo> roomAllotmentStatusTos = null;
		if(roomAllotmentList!=null && roomAllotmentList.getFillAllColumns()!=null && roomAllotmentList.getFillAllColumns()){
			objForm.setAllotAllCol("on");
			roomAllotmentStatusTos = ExamRoomAllotmentForCJCHelper.getInstance().convertBoTOToListForSingleAllotment(roomAllotmentList,roomEndMidSemRows,objForm,maxRowCount);
		}else{
//		the below method is useful to construct the structure of the selected room.
			 roomAllotmentStatusTos = ExamRoomAllotmentForCJCHelper.getInstance().convertBoTOToList(roomAllotmentList,roomEndMidSemRows,objForm,maxRowCount);
		}
		return roomAllotmentStatusTos;
	}
	/** This method gets Register Nos based on the given number for the Class or Subject.
	 * @param objForm
	 * @param session
	 * @param request
	 * @throws Exception
	 */
	public void getRegisterNOsBWFromAndTo(ExamRoomAllotmentForCJCForm objForm,
			HttpSession session, HttpServletRequest request) throws Exception {
		String registerNo ="";
		String fromRegNo = "";
		String toRegNo = "";
		session.setAttribute("regNOCol1", registerNo);
		session.setAttribute("regNOCol2", registerNo);
		Map<String, String> map = new HashMap<String, String>();
		map.put(fromRegNo, toRegNo);
		request.setAttribute("optionMap", map);
		int examId = objForm.getExamid();
		String examType = objForm.getExamType();
		String campusId = objForm.getCampusId();
		int classId = 0;
		int subjectId = 0;
		if(objForm.getClassId()!=null && !objForm.getClassId().isEmpty()){
			classId = Integer.parseInt(objForm.getClassId());
		}
		if(objForm.getSubjectId()!=null && !objForm.getSubjectId().isEmpty()){
			subjectId = Integer.parseInt(objForm.getSubjectId());
		}
		int noOfStudents = 0;
		if (objForm.getNoOfStudents() != null && !objForm.getNoOfStudents().isEmpty()) {
			noOfStudents = Integer.parseInt(objForm.getNoOfStudents());
		}
		String hqlQuery ="";
		String mode = "";
		LinkedList<String> registerNoList = null;
		Map<Integer,LinkedList<String>> getLastRegNoMap = null;
		if(objForm.getLastRegNoMap()!=null && !objForm.getLastRegNoMap().isEmpty()){
			getLastRegNoMap = objForm.getLastRegNoMap();
		}
		if(request.getParameter("propertyName").equalsIgnoreCase("col1") || request.getParameter("propertyName").equalsIgnoreCase("col2")){
			if(getLastRegNoMap!=null && getLastRegNoMap.containsKey(classId)){
				LinkedList<String> studentList = getLastRegNoMap.get(classId);
				registerNo = studentList.getLast();
			}else{
				hqlQuery = ExamRoomAllotmentForCJCHelper.getInstance() .getLastRegisterNoAlloted(examId,examType,campusId,classId,subjectId,"Class");
				registerNo = transaction.getRegisterNo(hqlQuery);
			}
			registerNoList=transaction.getRegisterNumbersList(objForm, registerNo, noOfStudents,"Class",mode);
		}else if(request.getParameter("propertyName").equalsIgnoreCase("subject1") || request.getParameter("propertyName").equalsIgnoreCase("subject2")){
			 if(getLastRegNoMap!=null && getLastRegNoMap.containsKey(subjectId)){
				 LinkedList<String> studentList = getLastRegNoMap.get(subjectId);
				 registerNo  = studentList.getLast();
			 }else{
				 hqlQuery = ExamRoomAllotmentForCJCHelper.getInstance() .getLastRegisterNoAlloted(examId,examType,campusId,classId,subjectId,"Subject");
				 registerNo = transaction.getRegisterNo(hqlQuery);
			 }
			 registerNoList=transaction.getRegisterNumbersList(objForm, registerNo, noOfStudents,"Subject",mode);
		}
		if(registerNoList!=null && !registerNoList.isEmpty()){
			fromRegNo = registerNoList.getFirst();
			toRegNo = registerNoList.getLast();
		}
		if (!fromRegNo.isEmpty() && !toRegNo.isEmpty()) {
			map = new HashMap<String, String>();
			map.put(fromRegNo, toRegNo);
			request.setAttribute("optionMap", map);
		}
		if(request.getParameter("propertyName1").equalsIgnoreCase("odd")){
			session.setAttribute("regNOCol1", registerNo);
		}else if(request.getParameter("propertyName1").equalsIgnoreCase("even")){
			session.setAttribute("regNOCol2", registerNo);
		}
	}
	/** This method is used to make the Allotment List .
	 * @param objForm
	 * @param noOfStudents 
	 * @param lastRegisterNo 
	 * @param oddOrEven 
	 * @param subjectId 
	 * @param classId 
	 * @param campusId 
	 * @param classOrSubject 
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusTo> studentsAllotmentForRoom(ExamRoomAllotmentForCJCForm objForm, String lastRegisterNo, 
			int noOfStudents, String oddOrEven, int classId, int subjectId, String campusId) throws Exception{
		Map<String, StudentTO> studentMap = null;
		int classOrSubjectId = 0;
		if(objForm.getTempField().equalsIgnoreCase("Classes")){
			classOrSubjectId = classId;
			studentMap = transaction .getStudentForSelectedClass(objForm.getAcademicYear(), lastRegisterNo, noOfStudents,classId);
		}else if(objForm.getTempField().equalsIgnoreCase("Subject")){
			classOrSubjectId= subjectId;
			studentMap = transaction.getStudentsForSelectedSubject(objForm.getAcademicYear(), lastRegisterNo, noOfStudents,subjectId,campusId);
		}
//		to identify the class or subject last regNo .
		Set<String> studentSet =  studentMap.keySet();
		LinkedList<String> tempStudentList = new LinkedList<String>(studentSet);
		Collections.sort(tempStudentList);
		Map<Integer,LinkedList<String>> lastRegNoMap = objForm.getLastRegNoMap();
		if(lastRegNoMap==null || lastRegNoMap.isEmpty()){
			lastRegNoMap =  new HashMap<Integer, LinkedList<String>>();
		}
		lastRegNoMap.put(classOrSubjectId, tempStudentList);
		objForm.setLastRegNoMap(lastRegNoMap);
//		
		List<ExamRoomAllotmentDetailsTO> detailsTOList = objForm.getAllotmentDetailsToList();
		Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap = objForm.getAllotStudentDetailsMap();
		
		
		List<RoomAllotmentStatusTo> allotmentStatusTos = objForm.getRoomAllotmentStatusTo();
		List<RoomAllotmentStatusTo> roomAllotmentStatusTos =null;
		if(objForm.getAllotAllCol().equalsIgnoreCase("off")){
			allotedStudentMap = ExamRoomAllotmentForCJCHelper.getInstance().getAllotingStudentMap(detailsTOList,allotedStudentMap,studentMap,oddOrEven,objForm.getRoomId());
			roomAllotmentStatusTos=ExamRoomAllotmentForCJCHelper.getInstance().convertMapToTOList(allotmentStatusTos,allotedStudentMap,objForm);
		}else if(objForm.getAllotAllCol().equalsIgnoreCase("on")){
			allotedStudentMap = ExamRoomAllotmentForCJCHelper.getInstance().getSingleColumnAllotmentStudentMap(detailsTOList,allotedStudentMap,studentMap,objForm.getRoomId());
			roomAllotmentStatusTos=ExamRoomAllotmentForCJCHelper.getInstance().convertMapToTOListForSingleAllotment(allotmentStatusTos,allotedStudentMap,objForm);
		}
		objForm.setAllotStudentDetailsMap(allotedStudentMap);
		return roomAllotmentStatusTos;
	}
	/** This method is used to clear the details of the selected column Allotment list.
	 * @param objForm
	 * @param oddOrEvenColumn 
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusTo> clearStudentAllotmentDetailsList( ExamRoomAllotmentForCJCForm objForm, String oddOrEvenColumn) throws Exception{
		List<RoomAllotmentStatusTo> allotmentStatusTos = objForm.getRoomAllotmentStatusTo();
		List<RoomAllotmentStatusTo> tos = new ArrayList<RoomAllotmentStatusTo>();
		Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap = objForm.getAllotStudentDetailsMap();
		int oddRemainingStudents = objForm.getOddClassOrSubjectCount();
		int evenRemainingStudents = objForm.getEvenClassOrSubjectCount();
		if(allotmentStatusTos!=null && !allotmentStatusTos.isEmpty()){
			for (RoomAllotmentStatusTo roomAllotmentStatusTo : allotmentStatusTos) {
				RoomAllotmentStatusTo statusTo = new RoomAllotmentStatusTo();
				List<RoomAllotmentStatusDetailsTo> statusDetailsTos = new ArrayList<RoomAllotmentStatusDetailsTo>();
				for (RoomAllotmentStatusDetailsTo to : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
					if(to.getTempField().equalsIgnoreCase(oddOrEvenColumn)){
						if(to.isAlloted()){
							to.setOrigRegisterNo("");
							to.setRegisterNo("");
							to.setClassName("");
							to.setSubjectName("");
							to.setAlloted(false);
							if(oddOrEvenColumn.equalsIgnoreCase("odd")){
								objForm.setColOneAllotedSeats(objForm.getColOneAllotedSeats()-1) ;
								objForm.setColOneAvailableSeats(objForm.getColOneAvailableSeats()+1);
								oddRemainingStudents =oddRemainingStudents + 1;
							}else if(oddOrEvenColumn.equalsIgnoreCase("even")){
								objForm.setColTwoAllotedSeats(objForm.getColTwoAllotedSeats()-1);
								objForm.setColTwoAvailableSeats(objForm.getColTwoAvailableSeats()+1);
								evenRemainingStudents = evenRemainingStudents +1;
							}
							if(allotedStudentMap!=null && !allotedStudentMap.isEmpty()){
								allotedStudentMap.remove(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition());
							}
						}
					}
					statusDetailsTos.add(to);
				}
				statusTo.setAllotmentStatusDetailsToList(statusDetailsTos);
				tos.add(statusTo);
			}
			
		}
		if(oddOrEvenColumn.equalsIgnoreCase("Even")){
			objForm.setEvenClassOrSubjectCount(evenRemainingStudents);
		}else if(oddOrEvenColumn.equalsIgnoreCase("Odd")){
			objForm.setOddClassOrSubjectCount(oddRemainingStudents);
		}
		objForm.setAllotStudentDetailsMap(allotedStudentMap);
		return tos;
	}
	/** This method is used to save the final Allotment list into the database.
	 * @param objForm
	 * @param errors 
	 * @return
	 * @throws Exception
	 */
	public boolean getSubmitStudentAllotList( ExamRoomAllotmentForCJCForm objForm, ActionErrors errors) throws Exception{
		boolean isAdded = false;
		ExamRoomAllotment allotmentList = ExamRoomAllotmentForCJCHelper.getInstance().createExamRoomAllotment(objForm,errors);
		if(errors.isEmpty()){
			isAdded = transaction.getSaveOrUpdateAllotmentDetails(allotmentList);
		}
		return isAdded;
	}
	/**This method is used to get unAlloted Students count for selected Class or Subject.
	 * @param request
	 * @param objForm
	 * @param session 
	 * @throws Exception
	 */
	public void getRemainingStudentsCount(HttpServletRequest request, ExamRoomAllotmentForCJCForm objForm, HttpSession session)throws Exception {
		String registerNo ="";
		Map<Integer,Integer> map = new HashMap<Integer, Integer>();// reset the map and put it in session.
		map.put(0, 0);
		session.setAttribute("optionMap", map);
		String hqlQuery ="";
		LinkedList<String> registerNoList = null;
		int noOfStudents =0;
		String mode = "remainingCount";
		int examId = objForm.getExamid();
		String examType = objForm.getExamType();
		String campusId = objForm.getCampusId();
		int classId = 0;
		int subjectId = 0;
		if(objForm.getClassId()!=null && !objForm.getClassId().isEmpty()){
			classId = Integer.parseInt(objForm.getClassId());
		}
		if(objForm.getSubjectId()!=null && !objForm.getSubjectId().isEmpty()){
			subjectId = Integer.parseInt(objForm.getSubjectId());
		}
		Map<Integer,LinkedList<String>> getLastRegNoMap = null;
		if(objForm.getLastRegNoMap()!=null && !objForm.getLastRegNoMap().isEmpty()){
			getLastRegNoMap = objForm.getLastRegNoMap();
		}
		if(request.getParameter("propertyName").equalsIgnoreCase("Classes")){
			if(getLastRegNoMap!=null && getLastRegNoMap.containsKey(classId)){
				LinkedList<String> studentList = getLastRegNoMap.get(classId);
				registerNo = studentList.getLast();
			}else {
				hqlQuery = ExamRoomAllotmentForCJCHelper.getInstance() .getLastRegisterNoAlloted(examId,examType,campusId,classId,subjectId,"Class");
				registerNo = transaction.getRegisterNo(hqlQuery);
			}
			registerNoList=transaction.getRegisterNumbersList(objForm, registerNo, noOfStudents,"Class",mode);
		}else if(request.getParameter("propertyName").equalsIgnoreCase("Subject")){
			if(getLastRegNoMap!=null && getLastRegNoMap.containsKey(subjectId)){
				LinkedList<String> studentList = getLastRegNoMap.get(subjectId);
				registerNo = studentList.getLast();
			}else{
				hqlQuery = ExamRoomAllotmentForCJCHelper.getInstance() .getLastRegisterNoAlloted(examId,examType,campusId,classId,subjectId,"Subject");
				registerNo = transaction.getRegisterNo(hqlQuery);
				
			}
			registerNoList=transaction.getRegisterNumbersList(objForm, registerNo, noOfStudents,"Subject",mode);
		}
		if(registerNoList!=null && !registerNoList.isEmpty()){
			map = new HashMap<Integer, Integer>();
			int listSize = registerNoList.size();
			map.put(listSize, listSize);
			session.setAttribute("optionMap", map);
		}
		
	}
	/** This method is used to get the subjects based on the date and session in ExamTimeTable table.
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getSubjectMapForDateAndSession( ExamRoomAllotmentForCJCForm objForm) throws Exception{
		List<Object[]> subjectBoList = transaction.getSubjectDetailsOnDateAndSession(objForm);
		Map<Integer,String> subjectMap = ExamRoomAllotmentForCJCHelper.getInstance().convertBOListToMap(subjectBoList);
		return subjectMap;
	}
	/**
	 * @param objForm
	 * @param oddOrEvenColumn
	 * @return
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusTo> clearSingleColumnStudentAllotmentDetailsList( ExamRoomAllotmentForCJCForm objForm,
			String oddOrEvenColumn)throws Exception {
		List<RoomAllotmentStatusTo> allotmentStatusTos = objForm.getRoomAllotmentStatusTo();
		List<RoomAllotmentStatusTo> tos = new ArrayList<RoomAllotmentStatusTo>();
		Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap = objForm.getAllotStudentDetailsMap();
		int remainingStudents = objForm.getOddClassOrSubjectCount();
		if(allotmentStatusTos!=null && !allotmentStatusTos.isEmpty()){
			for (RoomAllotmentStatusTo roomAllotmentStatusTo : allotmentStatusTos) {
				RoomAllotmentStatusTo statusTo = new RoomAllotmentStatusTo();
				List<RoomAllotmentStatusDetailsTo> statusDetailsTos = new ArrayList<RoomAllotmentStatusDetailsTo>();
				for (RoomAllotmentStatusDetailsTo to : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
					if(to.getTempField().equalsIgnoreCase(oddOrEvenColumn)){
						if(to.isAlloted()){
							to.setOrigRegisterNo("");
							to.setRegisterNo("");
							to.setClassName("");
							to.setSubjectName("");
							to.setAlloted(false);
							objForm.setTempAllotedSeats(objForm.getTempAllotedSeats()-1);
							objForm.setTempAvailableSeats(objForm.getTempAvailableSeats()+1);
							if(allotedStudentMap!=null && !allotedStudentMap.isEmpty()){
								allotedStudentMap.remove(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition());
							}
							remainingStudents =remainingStudents + 1;
						}
					}
					statusDetailsTos.add(to);
				}
				statusTo.setAllotmentStatusDetailsToList(statusDetailsTos);
				tos.add(statusTo);
			}
		}
		objForm.setOddClassOrSubjectCount(remainingStudents);
		objForm.setAllotStudentDetailsMap(allotedStudentMap);
		return tos;
	}
}
