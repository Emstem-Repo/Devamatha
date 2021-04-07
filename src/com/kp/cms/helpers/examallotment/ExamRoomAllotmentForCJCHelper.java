package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentCycle;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.examallotment.ExamRoomAllotmentForCJCForm;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.examallotment.ExamRoomAllotmentDetailsTO;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.utilities.CommonUtil;

public class ExamRoomAllotmentForCJCHelper {
	private static volatile ExamRoomAllotmentForCJCHelper helper ;
	public static ExamRoomAllotmentForCJCHelper getInstance(){
		if(helper == null){
			helper = new ExamRoomAllotmentForCJCHelper();
		}
		return helper;
	}
	private ExamRoomAllotmentForCJCHelper(){
		
	}
	public String getRoomDetails(int roomId,String examType) throws Exception{
		String str = "select endMidDetails from RoomMaster room " +
					 " join room.endMidSemRows endMidDetails"+
					 " where room.id="+roomId+" and room.isActive=1"+
					 " and endMidDetails.endMidSem='"+examType+"'"+
					 " and endMidDetails.isActive=1" +
					 " order by endMidDetails.columnNumber";
		return str;}
	/**
	 * @param objForm
	 * @param examType 
	 * @return
	 * @throws Exception
	 */
	public String getStudentsAllotedForCycleQuery( ExamRoomAllotmentForCJCForm objForm, String examType) throws Exception{
				String hqlQuery =" select roomDetails.allotment from ExamRoomAllotmentDetails roomDetails " +
				        " where roomDetails.isActive=1 " +
				        " and roomDetails.allotment.examDefinition.id='"+objForm.getExamid()+"'" +
				        " and roomDetails.allotment.midOrEndSem='"+examType+"'" +
				        " and roomDetails.allotment.room.id='"+objForm.getRoomId()+"' and roomDetails.allotment.isActive=1" +
				        " and roomDetails.allotment.room.blockId.locationId='"+objForm.getCampusId()+"'";
				if(objForm.getCycleId()!=null && !objForm.getCycleId().isEmpty()){
						hqlQuery=hqlQuery+" and roomDetails.allotment.cycle.id="+Integer.parseInt(objForm.getCycleId());
				}
			return hqlQuery=hqlQuery+" order by roomDetails.columnNO,roomDetails.rowNO,roomDetails.seatingPosition";
		}
	/**
	 * @param objForm
	 * @return
	 * @throws Exception
	 */
	public String getStudentsAllotedForDateSessionQuery( ExamRoomAllotmentForCJCForm objForm, String examType)throws Exception {
				String hqlQuery =" select roomDetails.allotment from ExamRoomAllotmentDetails roomDetails " +
				        " where roomDetails.isActive=1 " +
				        " and roomDetails.allotment.examDefinition.id='"+objForm.getExamid()+"'" +
				        " and roomDetails.allotment.midOrEndSem='"+examType+"'" +
				        " and roomDetails.allotment.room.id='"+objForm.getRoomId()+"' and roomDetails.allotment.isActive=1" +
				        " and roomDetails.allotment.room.blockId.locationId='"+objForm.getCampusId()+"'";
				if(objForm.getAllottedDate()!=null && !objForm.getAllottedDate().isEmpty()){
					hqlQuery=hqlQuery+" and roomDetails.allotment.date='"+CommonUtil.ConvertStringToSQLDate(objForm.getAllottedDate())+"'";
				}
				if(objForm.getSessionId()!=null && !objForm.getSessionId().isEmpty()){
					hqlQuery=hqlQuery+" and roomDetails.allotment.examinationSessions.id="+Integer.parseInt(objForm.getSessionId());
				}
			return hqlQuery=hqlQuery+" order by roomDetails.columnNO,roomDetails.rowNO,roomDetails.seatingPosition";
		}
	/**
	 * @param roomAllotmentDetailsList
	 * @param roomEndMidSemRows
	 * @param objForm
	 * @return
	 */
	public List<RoomAllotmentStatusTo> convertBoTOToList( ExamRoomAllotment roomAllotment, List<RoomEndMidSemRows> roomEndMidSemRows,
			ExamRoomAllotmentForCJCForm objForm,int maxRowCount)throws Exception {
        Map<String, RoomAllotmentStatusDetailsTo> studentRowsMap=new HashMap<String, RoomAllotmentStatusDetailsTo>();
        Map<Integer,List<RoomAllotmentStatusDetailsTo>> mainMap = new HashMap<Integer, List<RoomAllotmentStatusDetailsTo>>();
        if(roomAllotment!=null){
			objForm.setExamRoomAllotmentId(roomAllotment.getId());
        	Set<ExamRoomAllotmentDetails> roomAllotmentDetailsList = roomAllotment.getRoomAllotmentDetails();
        	if(roomAllotmentDetailsList!=null && !roomAllotmentDetailsList.isEmpty()){
        		for (ExamRoomAllotmentDetails examRoomAllotmentDetails : roomAllotmentDetailsList) {
        			RoomAllotmentStatusDetailsTo roomAllotmentStatusTo=new RoomAllotmentStatusDetailsTo();
        			if(examRoomAllotmentDetails.getStudent()!=null){
        				if(examRoomAllotmentDetails.getStudent().getRegisterNo()!=null &&
        						!examRoomAllotmentDetails.getStudent().getRegisterNo().isEmpty()){
        					roomAllotmentStatusTo.setRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
        					roomAllotmentStatusTo.setOrigRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
        					roomAllotmentStatusTo.setStudentId(examRoomAllotmentDetails.getStudent().getId());
        				}
        			}else{
        				roomAllotmentStatusTo.setRegisterNo(null);
        				roomAllotmentStatusTo.setOrigRegisterNo(null);
        			}
        			roomAllotmentStatusTo.setExamRoomAllotmentDetailsId(examRoomAllotmentDetails.getId());
        			roomAllotmentStatusTo.setId(examRoomAllotmentDetails.getId());
        			if(examRoomAllotmentDetails.getClasses()!=null){
        				roomAllotmentStatusTo.setClassName(examRoomAllotmentDetails.getClasses().getName());
        				roomAllotmentStatusTo.setClassId(examRoomAllotmentDetails.getClasses().getId());
        				roomAllotmentStatusTo.setSubjectName("");
        			}else{
        				roomAllotmentStatusTo.setClassName(" ");
        			}
        			if(examRoomAllotmentDetails.getSubject()!=null && !examRoomAllotmentDetails.getSubject().toString().isEmpty()){
        				roomAllotmentStatusTo.setSubject(examRoomAllotmentDetails.getSubject().getId());
        				roomAllotmentStatusTo.setSubjectName(examRoomAllotmentDetails.getSubject().getName()+" "+examRoomAllotmentDetails.getSubject().getCode());
        				roomAllotmentStatusTo.setClassName(" ");
        			}
        			roomAllotmentStatusTo.setSeatingPosition(examRoomAllotmentDetails.getSeatingPosition());
        			roomAllotmentStatusTo.setRowNO(examRoomAllotmentDetails.getRowNO());
        			roomAllotmentStatusTo.setColumnNO(examRoomAllotmentDetails.getColumnNO());
        			roomAllotmentStatusTo.setRoomNo(String.valueOf(examRoomAllotmentDetails.getAllotment().getRoom().getId()));
        			studentRowsMap.put(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition(), roomAllotmentStatusTo);
        		}
        		objForm.setAllotStudentDetailsMap(studentRowsMap);
        	}
        }
		if(roomEndMidSemRows!=null){
			List<RoomEndMidSemRows> roomDetails = new ArrayList<RoomEndMidSemRows>(roomEndMidSemRows);
			Collections.sort(roomDetails);
			List<ExamRoomAllotmentDetailsTO> list = new ArrayList<ExamRoomAllotmentDetailsTO>();
			boolean isFlag = false;
			for (RoomEndMidSemRows details : roomDetails) {
				if(details.getNoOfRows() != null && details.getColumnNumber() != null && details.getNoOfSetInDesk() != null){
					
					ExamRoomAllotmentDetailsTO allotmentDetailsTO = new ExamRoomAllotmentDetailsTO();
					List<RoomEndMidSemRowsTo> columnWiseRows = new ArrayList<RoomEndMidSemRowsTo>();
					boolean isRoomEndMidSemRecordCreated = false;
					for (int i = 1; i <= maxRowCount; i++) {
						
						int noOfRows = Integer.parseInt(details.getNoOfRows());
						if(noOfRows>=i){
							for(int j=1;j<=details.getNoOfSetInDesk();j++){
								RoomAllotmentStatusDetailsTo to1 = new RoomAllotmentStatusDetailsTo();
								to1.setColumnNO(Integer.parseInt(details.getColumnNumber()));
								to1.setRowNO(i);
								to1.setSeatingPosition(j);
								to1.setNoOfSeatsInDesk(details.getNoOfSetInDesk());
								to1.setTempCheck(false);
								List<RoomAllotmentStatusDetailsTo> toList =null;
								if(!mainMap.containsKey(to1.getRowNO())){
									toList = new ArrayList<RoomAllotmentStatusDetailsTo>();
									toList.add(to1);
								}else{
									toList= mainMap.get(to1.getRowNO());
									toList.add(to1);
								}
								mainMap.put(to1.getRowNO(), toList);
								if(!isRoomEndMidSemRecordCreated){
									RoomEndMidSemRowsTo to2 = new RoomEndMidSemRowsTo();
									to2.setNoOfSeats(details.getNoOfSetInDesk());
									to2.setRowNo(i);
									to2.setNoOfRows(Integer.parseInt(details.getNoOfRows()));
									to2.setNoOfColumns(Integer.parseInt(details.getColumnNumber()));
									to2.setSeatingPosition(j);
									if(isFlag){
										to2.setTempField("even");
									}else{
										to2.setTempField("odd");
									}
									if(isFlag){
										isFlag= false;
									}else{
										isFlag = true;
									}
									columnWiseRows.add(to2);
								}else{
									columnWiseRows = null;
								}
							}
							isRoomEndMidSemRecordCreated =true;
							if(columnWiseRows!=null && !columnWiseRows.isEmpty()){
								allotmentDetailsTO.setColumnWiseRows(columnWiseRows);
							}
						}else{
							for(int j=1;j<=details.getNoOfSetInDesk();j++){
								RoomAllotmentStatusDetailsTo to1 = new RoomAllotmentStatusDetailsTo();
								to1.setColumnNO(0);
//								to1.setRowNO(0);
								to1.setSeatingPosition(0);
								to1.setRowNO(i);
								to1.setTempCheck(true);
								List<RoomAllotmentStatusDetailsTo> toList =null;
								if(!mainMap.containsKey(to1.getRowNO())){
									toList = new ArrayList<RoomAllotmentStatusDetailsTo>();
									toList.add(to1);
								}else{
									toList= mainMap.get(to1.getRowNO());
									toList.add(to1);
								}
								mainMap.put(to1.getRowNO(), toList);
							}
						}
					}
					list.add(allotmentDetailsTO);
				}
			}
			objForm.setAllotmentDetailsToList(list);
		}
		List<RoomAllotmentStatusTo> allotmentStatusToList=new ArrayList<RoomAllotmentStatusTo>();
		if(!mainMap.isEmpty()){
			int columnOneTotalSeats = 0;
			int columnTwoTotalSeats = 0;
			int colOneAllotedSeats = 0;
			int colTwoAllotedSeats = 0;
			for (Entry<Integer, List<RoomAllotmentStatusDetailsTo>> rowsMap : mainMap.entrySet()) {
				boolean isFlag = false;
				RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
				 List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList=new ArrayList<RoomAllotmentStatusDetailsTo>();
					for (RoomAllotmentStatusDetailsTo detailsTo : rowsMap.getValue()) {
						detailsTo.setAlloted(false);
					  if(!detailsTo.isTempCheck()){
						if(studentRowsMap.containsKey(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition())){
							RoomAllotmentStatusDetailsTo statusDetailsTo = studentRowsMap.get(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition());
							detailsTo.setExamRoomAllotmentDetailsId(statusDetailsTo.getExamRoomAllotmentDetailsId());
							detailsTo.setRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setOrigRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setClassName(statusDetailsTo.getClassName());
							detailsTo.setStudentId(statusDetailsTo.getStudentId());
							detailsTo.setClassId(statusDetailsTo.getClassId());
							detailsTo.setRoomNo(statusDetailsTo.getRoomNo());
							if(statusDetailsTo.getSubject()>0){
								detailsTo.setSubject(statusDetailsTo.getSubject());
								detailsTo.setSubjectName(statusDetailsTo.getSubjectName());
								detailsTo.setClassName(null);
							}
							if(isFlag){
								detailsTo.setTempField("even");
								colTwoAllotedSeats++;
							}else {
								detailsTo.setTempField("odd");
								colOneAllotedSeats ++;
							}
							detailsTo.setAlloted(true);
						}
						if(isFlag){
							detailsTo.setTempField("even");
							columnTwoTotalSeats++;
						}else{
							detailsTo.setTempField("odd");
							columnOneTotalSeats++;
						}
					}
					  if(isFlag){
							detailsTo.setTempField("even");
						}else{
							detailsTo.setTempField("odd");
						}
						allotmentStatusDetailsToList.add(detailsTo);
						if(isFlag){
							isFlag= false;
						}else{
							isFlag = true;
						}
					}
					allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentStatusDetailsToList);
					allotmentStatusToList.add(allotmentStatusTo);
			}
			int colOneAvailableSeats = columnOneTotalSeats - colOneAllotedSeats;
			int colTwoAvailableSeats = columnTwoTotalSeats - colTwoAllotedSeats;
			objForm.setColumnOneTotalSeats(columnOneTotalSeats);
			objForm.setColumnTwoTotalSeats(columnTwoTotalSeats);
			objForm.setColOneAllotedSeats(colOneAllotedSeats);
			objForm.setColTwoAllotedSeats(colTwoAllotedSeats);
			objForm.setColOneAvailableSeats(colOneAvailableSeats);
			objForm.setColTwoAvailableSeats(colTwoAvailableSeats);
//			for Single Column Allotment 
			columnOneTotalSeats = columnOneTotalSeats+columnTwoTotalSeats;
			objForm.setTempTotalSeats(columnOneTotalSeats);
			objForm.setTempAllotedSeats(0);
			objForm.setTempAvailableSeats(columnOneTotalSeats);
			
		}
		return allotmentStatusToList;
	}
	/**
	 * @param objForm
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String getLastRegisterNoAlloted(int examId,String examType,String campusId,int classId,int subjectId, String mode)throws Exception {
		String hqlQuery =" select max(roomDetails.student.registerNo) from ExamRoomAllotmentDetails roomDetails " +
					        " where roomDetails.isActive=1 " +
					        " and roomDetails.allotment.examDefinition.id='"+examId+"'" +
					        " and roomDetails.allotment.midOrEndSem='"+examType+"'" +
					        " and roomDetails.allotment.isActive=1" ;
					 if(mode.equalsIgnoreCase("Class")){
					    	   hqlQuery = hqlQuery + " and roomDetails.classes.id='"+classId+"'";
					 }else if(mode.equalsIgnoreCase("Subject")){
					    	   hqlQuery = hqlQuery + " and roomDetails.subject.id='"+subjectId+"'"+
					    	   	" and roomDetails.classes.course.workLocation.id='"+campusId+"'";
					 }
					hqlQuery = hqlQuery + " order by roomDetails.student.registerNo";
		return hqlQuery;
	}
	/**
	 * @param detailsTOList
	 * @param allotedStudentMap
	 * @param studentMap 
	 * @param oddOrEvenRow 
	 * @param roomId 
	 * @return
	 * @throws Exception
	 */
	public Map<String, RoomAllotmentStatusDetailsTo> getAllotingStudentMap( List<ExamRoomAllotmentDetailsTO> detailsTOList,
			Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap, Map<String, StudentTO> studentMap, String oddOrEvenRow, String roomId)throws Exception {
		if(detailsTOList!=null && !detailsTOList.isEmpty()){
			studentMap = CommonUtil.sortMapByKey(studentMap);
			Collection<StudentTO> student = studentMap.values();
			LinkedList<StudentTO> stu = new LinkedList<StudentTO>(student);
			 Collections.sort(stu);
			for (ExamRoomAllotmentDetailsTO examRoomAllotmentDetailsTO : detailsTOList) {
				if(stu == null || stu.isEmpty()){
					break;
				}
				for (RoomEndMidSemRowsTo to : examRoomAllotmentDetailsTO.getColumnWiseRows()) {
					if(stu == null || stu.isEmpty()){
						break;
					}
					if(to.getTempField().equalsIgnoreCase(oddOrEvenRow)){
					for(int i=1;i<=to.getNoOfRows();i++){
							RoomAllotmentStatusDetailsTo statusTo = null;
							if(allotedStudentMap!=null && !allotedStudentMap.isEmpty()){
								if(!allotedStudentMap.containsKey(to.getNoOfColumns()+"_"+i+"_"+to.getSeatingPosition())){
									statusTo = new RoomAllotmentStatusDetailsTo();
								}else{
									continue;
								}
							}else{
								statusTo = new RoomAllotmentStatusDetailsTo();
							}
							if(stu == null || stu.isEmpty()){
								break;
							}
							statusTo.setStudentId(stu.getFirst().getStudentId());
							statusTo.setRegisterNo(stu.getFirst().getRegisterNo());
							statusTo.setOrigRegisterNo(stu.getFirst().getRegisterNo());
							statusTo.setClassName(stu.getFirst().getClassName());
							statusTo.setClassId(stu.getFirst().getClassId());
							statusTo.setSeatingPosition(to.getSeatingPosition());
							statusTo.setRowNO(i);
							statusTo.setColumnNO(to.getNoOfColumns());
							if(stu.getFirst().getSubjectId()!=null && !stu.getFirst().getSubjectId().isEmpty()){
								statusTo.setSubject(Integer.parseInt(stu.getFirst().getSubjectId()));
								statusTo.setSubjectName(stu.getFirst().getSubjectName());
							}
						    statusTo.setRoomNo(roomId);
							stu.removeFirst();
							
							if(allotedStudentMap == null || allotedStudentMap.isEmpty()){
								allotedStudentMap = new HashMap<String, RoomAllotmentStatusDetailsTo>();
							}
							allotedStudentMap.put(to.getNoOfColumns()+"_"+i+"_"+to.getSeatingPosition(), statusTo);
						}
					}
				}
			}
		}
		return allotedStudentMap;
	}
	/**
	 * @param allotmentStatusTos
	 * @param allotedStudentMap
	 * @param objForm 
	 * @return
	 * @throws Exception
	 */
	public List<RoomAllotmentStatusTo> convertMapToTOList( List<RoomAllotmentStatusTo> allotmentStatusTos,
			Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap, ExamRoomAllotmentForCJCForm objForm)throws Exception {
		List<RoomAllotmentStatusTo> allotmentStatusList = new ArrayList<RoomAllotmentStatusTo>();
		int columnOneTotalSeats = 0;
		int columnTwoTotalSeats = 0;
		int colOneAllotedSeats = 0;
		int colTwoAllotedSeats = 0;
		boolean isFlag=false;
		for (RoomAllotmentStatusTo roomAllotmentStatusTo : allotmentStatusTos) {
			RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
			List<RoomAllotmentStatusDetailsTo> allotmentTOList = new ArrayList<RoomAllotmentStatusDetailsTo>();
			for (RoomAllotmentStatusDetailsTo to : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
				to.setAlloted(false);
				  if(!to.isTempCheck()){
					  if(allotedStudentMap.containsKey(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition())){
						  RoomAllotmentStatusDetailsTo statusDetailsTo = allotedStudentMap.get(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition());
						  to.setAlloted(true);
						  to.setOrigRegisterNo(statusDetailsTo.getOrigRegisterNo());
						  to.setRegisterNo(statusDetailsTo.getRegisterNo());
						  to.setClassName(statusDetailsTo.getClassName());
						  to.setClassId(statusDetailsTo.getClassId());
						  to.setStudentId(statusDetailsTo.getStudentId());
						  if(statusDetailsTo.getSubject()!=0){
							  to.setSubject(statusDetailsTo.getSubject());
							  to.setSubjectName(statusDetailsTo.getSubjectName());
							  to.setClassName("");
						  }
						  to.setRoomNo(statusDetailsTo.getRoomNo());
						 /* if(to.getTempField().equalsIgnoreCase("even")){
							  colTwoAllotedSeats++;
						  }else if(to.getTempField().equalsIgnoreCase("odd")){
							  colOneAllotedSeats ++;
						  }*/
						  if(isFlag){
							  to.setTempField("even");
								colTwoAllotedSeats++;
							}else {
								to.setTempField("odd");
								colOneAllotedSeats ++;
							}
					  }
					  /*if(to.getTempField().equalsIgnoreCase("even")){
						  columnTwoTotalSeats++;
					  }else if(to.getTempField().equalsIgnoreCase("odd")){
						  columnOneTotalSeats ++;
					  }*/
					  if(isFlag){
						  to.setTempField("even");
							columnTwoTotalSeats++;
						}else{
							to.setTempField("odd");
							columnOneTotalSeats++;
						}
				  }
				  if(isFlag){
						to.setTempField("even");
					}else{
						to.setTempField("odd");
					}
					allotmentTOList.add(to);
					if(isFlag){
						isFlag= false;
					}else{
						isFlag = true;
					}
			}
			allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentTOList);
			allotmentStatusList.add(allotmentStatusTo);
		}
		int colOneAvailableSeats = columnOneTotalSeats - colOneAllotedSeats;
		int colTwoAvailableSeats = columnTwoTotalSeats - colTwoAllotedSeats;
		objForm.setColumnOneTotalSeats(columnOneTotalSeats);
		objForm.setColumnTwoTotalSeats(columnTwoTotalSeats);
		objForm.setColOneAllotedSeats(colOneAllotedSeats);
		objForm.setColTwoAllotedSeats(colTwoAllotedSeats);
		objForm.setColOneAvailableSeats(colOneAvailableSeats);
		objForm.setColTwoAvailableSeats(colTwoAvailableSeats);
//		for Single Column Allotment 
		columnOneTotalSeats = columnOneTotalSeats+columnTwoTotalSeats;
		objForm.setTempTotalSeats(columnOneTotalSeats);
		objForm.setTempAllotedSeats(0);
		objForm.setTempAvailableSeats(columnOneTotalSeats);
		return allotmentStatusList;
	}
	/**
	 * @param objForm
	 * @param errors 
	 * @return
	 * @throws Exception
	 */
	public ExamRoomAllotment createExamRoomAllotment( ExamRoomAllotmentForCJCForm objForm, ActionErrors errors) throws Exception{
		ExamRoomAllotment allotment = new ExamRoomAllotment();
		List<RoomAllotmentStatusTo> list = objForm.getRoomAllotmentStatusTo();
		if(list!=null && !list.isEmpty()){
			Set<ExamRoomAllotmentDetails> detailsSet = new HashSet<ExamRoomAllotmentDetails>();
			boolean makeIsActive = false;
			if(objForm.getExamRoomAllotmentId()>0){
				allotment.setId(objForm.getExamRoomAllotmentId());
			}
			ExamDefinition examDefinition = new ExamDefinition();
			examDefinition.setId(objForm.getExamid());
			allotment.setExamDefinition(examDefinition);
			allotment.setMidOrEndSem(objForm.getExamType());
			RoomMaster roomMaster = new RoomMaster();
			roomMaster.setId(Integer.parseInt(objForm.getRoomId()));
			allotment.setRoom(roomMaster);
			if(objForm.getIsDateWise().equalsIgnoreCase("Yes")){
				ExaminationSessions session = new ExaminationSessions();
				session.setId(Integer.parseInt(objForm.getSessionId()));
				allotment.setExaminationSessions(session);
				allotment.setDate(CommonUtil.ConvertStringToDate(objForm.getAllottedDate()));
			}else if(objForm.getIsDateWise().equalsIgnoreCase("No")){
				ExamRoomAllotmentCycle cycle = new ExamRoomAllotmentCycle();
				cycle.setId(Integer.parseInt(objForm.getCycleId()));
				allotment.setCycle(cycle);
			}
			if(objForm.getAllotAllCol().equalsIgnoreCase("on")){
				allotment.setFillAllColumns(true);
			}
			allotment.setCreatedBy(objForm.getUserId());
			allotment.setCreatedDate(new Date());
			allotment.setLastModifiedDate(new Date());
			allotment.setModifiedBy(objForm.getUserId());
//			allotment.setIsActive(true);
			
			for (RoomAllotmentStatusTo roomAllotmentStatusTo : list) {
				for (RoomAllotmentStatusDetailsTo to : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
					ExamRoomAllotmentDetails details = null;
					if(to.isAlloted()){
						 details = new ExamRoomAllotmentDetails();
//						 method for set the properties  of ExamRoomAllotmentDetails List.
						 setExamRoomAllotmentDetailsBoList(details,to,objForm,allotment);
						 details.setIsActive(true);
						 detailsSet.add(details);
						 makeIsActive = true;
					}else if(!to.isAlloted() && to.getExamRoomAllotmentDetailsId()>0){
						 details = new ExamRoomAllotmentDetails();
//						 method for set the properties  of ExamRoomAllotmentDetails List.
						 setExamRoomAllotmentDetailsBoList(details,to,objForm,allotment);
						 details.setIsActive(false);
						 detailsSet.add(details);
//						 makeIsActive = false;
					}
				}
			}
			
			if(detailsSet!=null && !detailsSet.isEmpty()){
				allotment.setRoomAllotmentDetails(detailsSet);
			}else {
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.allotment.room.list.empty"));
			}
			if(makeIsActive){
				allotment.setIsActive(true);
			}else{
				allotment.setIsActive(false);
			}
		}
		return allotment;
	}
	/**
	 * @param details
	 * @param to
	 * @param objForm
	 * @param allotment 
	 * @throws Exception
	 */
	private void setExamRoomAllotmentDetailsBoList( ExamRoomAllotmentDetails details, RoomAllotmentStatusDetailsTo to,
			ExamRoomAllotmentForCJCForm objForm, ExamRoomAllotment allotment) throws Exception{
		if(to.getExamRoomAllotmentDetailsId()>0){
			details.setId(to.getExamRoomAllotmentDetailsId());
		}
		details.setAllotment(allotment);
		if(to.getStudentId()>0){
			Student student = new Student();
			student.setId(to.getStudentId());
			details.setStudent(student);
		}
		if(to.getClassId()>0){
			Classes classes = new Classes();
			classes.setId(to.getClassId());
			details.setClasses(classes);
		}
		if(to.getSubject()>0){
			Subject subject = new Subject();
			subject.setId(to.getSubject());
			details.setSubject(subject);
		}
		details.setColumnNO(to.getColumnNO());
		details.setRowNO(to.getRowNO());
		details.setSeatingPosition(to.getSeatingPosition());
		details.setCreatedBy(objForm.getUserId());
		details.setCreatedDate(new Date());
		details.setModifiedBy(objForm.getUserId());
		details.setLastModifiedDate(new Date());
		
	}
	/**
	 * @param subjectBoList
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> convertBOListToMap(List<Object[]> subjectBoList)throws Exception {
		Map<Integer,String> subjectMap = null;
		if(subjectBoList!=null && !subjectBoList.isEmpty()){
			subjectMap = new HashMap<Integer, String>();
			for (Object[] obj : subjectBoList) {
				subjectMap.put(Integer.parseInt(obj[0].toString()), obj[1].toString()+"("+obj[2].toString()+")");
			}
		}
		return subjectMap;
	}
	/**
	 * @param roomAllotmentList
	 * @param roomEndMidSemRows
	 * @param objForm
	 * @param maxRowCount
	 * @return
	 */
	public List<RoomAllotmentStatusTo> convertBoTOToListForSingleAllotment( ExamRoomAllotment roomAllotment,
			List<RoomEndMidSemRows> roomEndMidSemRows,
			ExamRoomAllotmentForCJCForm objForm, int maxRowCount) {
        Map<String, RoomAllotmentStatusDetailsTo> studentRowsMap=new HashMap<String, RoomAllotmentStatusDetailsTo>();
        Map<Integer,List<RoomAllotmentStatusDetailsTo>> mainMap = new HashMap<Integer, List<RoomAllotmentStatusDetailsTo>>();
        if(roomAllotment!=null){
			objForm.setExamRoomAllotmentId(roomAllotment.getId());
        	Set<ExamRoomAllotmentDetails> roomAllotmentDetailsList = roomAllotment.getRoomAllotmentDetails();
        	if(roomAllotmentDetailsList!=null && !roomAllotmentDetailsList.isEmpty()){
        		for (ExamRoomAllotmentDetails examRoomAllotmentDetails : roomAllotmentDetailsList) {
        			RoomAllotmentStatusDetailsTo roomAllotmentStatusTo=new RoomAllotmentStatusDetailsTo();
        			if(examRoomAllotmentDetails.getStudent()!=null){
        				if(examRoomAllotmentDetails.getStudent().getRegisterNo()!=null &&
        						!examRoomAllotmentDetails.getStudent().getRegisterNo().isEmpty()){
        					roomAllotmentStatusTo.setRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
        					roomAllotmentStatusTo.setOrigRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
        					roomAllotmentStatusTo.setStudentId(examRoomAllotmentDetails.getStudent().getId());
        				}
        			}else{
        				roomAllotmentStatusTo.setRegisterNo(null);
        				roomAllotmentStatusTo.setOrigRegisterNo(null);
        			}
        			roomAllotmentStatusTo.setExamRoomAllotmentDetailsId(examRoomAllotmentDetails.getId());
        			roomAllotmentStatusTo.setId(examRoomAllotmentDetails.getId());
        			if(examRoomAllotmentDetails.getClasses()!=null){
        				roomAllotmentStatusTo.setClassName(examRoomAllotmentDetails.getClasses().getName());
        				roomAllotmentStatusTo.setClassId(examRoomAllotmentDetails.getClasses().getId());
        				roomAllotmentStatusTo.setSubjectName("");
        			}else{
        				roomAllotmentStatusTo.setClassName(" ");
        			}
        			if(examRoomAllotmentDetails.getSubject()!=null && !examRoomAllotmentDetails.getSubject().toString().isEmpty()){
        				roomAllotmentStatusTo.setSubject(examRoomAllotmentDetails.getSubject().getId());
        				roomAllotmentStatusTo.setSubjectName(examRoomAllotmentDetails.getSubject().getName()+" "+examRoomAllotmentDetails.getSubject().getCode());
        				roomAllotmentStatusTo.setClassName(" ");
        			}
        			roomAllotmentStatusTo.setSeatingPosition(examRoomAllotmentDetails.getSeatingPosition());
        			roomAllotmentStatusTo.setRowNO(examRoomAllotmentDetails.getRowNO());
        			roomAllotmentStatusTo.setColumnNO(examRoomAllotmentDetails.getColumnNO());
        			roomAllotmentStatusTo.setRoomNo(String.valueOf(examRoomAllotmentDetails.getAllotment().getRoom().getId()));
        			studentRowsMap.put(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition(), roomAllotmentStatusTo);
        		}
        		objForm.setAllotStudentDetailsMap(studentRowsMap);
        	}
        }
		if(roomEndMidSemRows!=null){
			List<RoomEndMidSemRows> roomDetails = new ArrayList<RoomEndMidSemRows>(roomEndMidSemRows);
			Collections.sort(roomDetails);
			List<ExamRoomAllotmentDetailsTO> list = new ArrayList<ExamRoomAllotmentDetailsTO>();
			for (RoomEndMidSemRows details : roomDetails) {
				if(details.getNoOfRows() != null && details.getColumnNumber() != null && details.getNoOfSetInDesk() != null){
					
					ExamRoomAllotmentDetailsTO allotmentDetailsTO = new ExamRoomAllotmentDetailsTO();
					List<RoomEndMidSemRowsTo> columnWiseRows = new ArrayList<RoomEndMidSemRowsTo>();
					boolean isRoomEndMidSemRecordCreated = false;
					for (int i = 1; i <= maxRowCount; i++) {
						
						int noOfRows = Integer.parseInt(details.getNoOfRows());
						if(noOfRows>=i){
							for(int j=1;j<=details.getNoOfSetInDesk();j++){
								RoomAllotmentStatusDetailsTo to1 = new RoomAllotmentStatusDetailsTo();
								to1.setColumnNO(Integer.parseInt(details.getColumnNumber()));
								to1.setRowNO(i);
								to1.setSeatingPosition(j);
								to1.setNoOfSeatsInDesk(details.getNoOfSetInDesk());
								to1.setTempCheck(false);
								List<RoomAllotmentStatusDetailsTo> toList =null;
								if(!mainMap.containsKey(to1.getRowNO())){
									toList = new ArrayList<RoomAllotmentStatusDetailsTo>();
									toList.add(to1);
								}else{
									toList= mainMap.get(to1.getRowNO());
									toList.add(to1);
								}
								mainMap.put(to1.getRowNO(), toList);
								if(!isRoomEndMidSemRecordCreated){
									RoomEndMidSemRowsTo to2 = new RoomEndMidSemRowsTo();
									to2.setNoOfSeats(details.getNoOfSetInDesk());
									to2.setRowNo(i);
									to2.setNoOfRows(Integer.parseInt(details.getNoOfRows()));
									to2.setNoOfColumns(Integer.parseInt(details.getColumnNumber()));
									to2.setSeatingPosition(j);
									to2.setTempField("odd");
									columnWiseRows.add(to2);
								}else{
									columnWiseRows = null;
								}
							}
							isRoomEndMidSemRecordCreated =true;
							if(columnWiseRows!=null && !columnWiseRows.isEmpty()){
								allotmentDetailsTO.setColumnWiseRows(columnWiseRows);
							}
						}else{
							for(int j=1;j<=details.getNoOfSetInDesk();j++){
								RoomAllotmentStatusDetailsTo to1 = new RoomAllotmentStatusDetailsTo();
								to1.setColumnNO(0);
								to1.setSeatingPosition(0);
								to1.setRowNO(i);
								to1.setTempCheck(true);
								List<RoomAllotmentStatusDetailsTo> toList =null;
								if(!mainMap.containsKey(to1.getRowNO())){
									toList = new ArrayList<RoomAllotmentStatusDetailsTo>();
									toList.add(to1);
								}else{
									toList= mainMap.get(to1.getRowNO());
									toList.add(to1);
								}
								mainMap.put(to1.getRowNO(), toList);
							}
						}
					}
					list.add(allotmentDetailsTO);
				}
			}
			objForm.setAllotmentDetailsToList(list);
		}
		List<RoomAllotmentStatusTo> allotmentStatusToList=new ArrayList<RoomAllotmentStatusTo>();
		if(!mainMap.isEmpty()){
			int columnOneTotalSeats = 0;
			int columnTwoTotalSeats = 0;
			int tempColumnOneTotalSeats =0;
			int tempColOneAllotedSeats =0;
			boolean isFlag = false;
			for (Entry<Integer, List<RoomAllotmentStatusDetailsTo>> rowsMap : mainMap.entrySet()) {
				RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
				 List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList=new ArrayList<RoomAllotmentStatusDetailsTo>();
					for (RoomAllotmentStatusDetailsTo detailsTo : rowsMap.getValue()) {
						detailsTo.setAlloted(false);
					  if(!detailsTo.isTempCheck()){
						if(studentRowsMap.containsKey(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition())){
							RoomAllotmentStatusDetailsTo statusDetailsTo = studentRowsMap.get(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition());
							detailsTo.setExamRoomAllotmentDetailsId(statusDetailsTo.getExamRoomAllotmentDetailsId());
							detailsTo.setRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setOrigRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setClassName(statusDetailsTo.getClassName());
							detailsTo.setStudentId(statusDetailsTo.getStudentId());
							detailsTo.setClassId(statusDetailsTo.getClassId());
							detailsTo.setRoomNo(statusDetailsTo.getRoomNo());
							if(statusDetailsTo.getSubject()>0){
								detailsTo.setSubject(statusDetailsTo.getSubject());
								detailsTo.setSubjectName(statusDetailsTo.getSubjectName());
								detailsTo.setClassName(null);
							}
								detailsTo.setTempField("odd");
								tempColOneAllotedSeats ++;
							detailsTo.setAlloted(true);
						}
						if(isFlag){
							columnTwoTotalSeats++;
						}else{
							columnOneTotalSeats++;
						}
							detailsTo.setTempField("odd");
							tempColumnOneTotalSeats++;
					}
						detailsTo.setTempField("odd");
						allotmentStatusDetailsToList.add(detailsTo);
						if(isFlag){
							isFlag= false;
						}else{
							isFlag = true;
						}
					}
					allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentStatusDetailsToList);
					allotmentStatusToList.add(allotmentStatusTo);
			}
			objForm.setColumnOneTotalSeats(columnOneTotalSeats);
			objForm.setColumnTwoTotalSeats(columnTwoTotalSeats);
			objForm.setColOneAllotedSeats(0);
			objForm.setColTwoAllotedSeats(0);
			objForm.setColOneAvailableSeats(columnOneTotalSeats);
			objForm.setColTwoAvailableSeats(columnTwoTotalSeats);
//			
			objForm.setTempTotalSeats(tempColumnOneTotalSeats);
			objForm.setTempAllotedSeats(tempColOneAllotedSeats);
			objForm.setTempAvailableSeats(tempColumnOneTotalSeats-tempColOneAllotedSeats);
		}
		return allotmentStatusToList;
	}
	/**
	 * @param allotmentStatusTos
	 * @param allotedStudentMap
	 * @param objForm
	 * @return
	 */
	public List<RoomAllotmentStatusTo> convertMapToTOListForSingleAllotment( List<RoomAllotmentStatusTo> allotmentStatusTos,
			Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap,
			ExamRoomAllotmentForCJCForm objForm) {
		List<RoomAllotmentStatusTo> allotmentStatusList = new ArrayList<RoomAllotmentStatusTo>();
		int columnOneTotalSeats = 0;
		int columnTwoTotalSeats = 0;
		int tempColumnOneTotalSeats =0;
		int tempColOneAllotedSeats =0;
		boolean isFlag =false;
		for (RoomAllotmentStatusTo roomAllotmentStatusTo : allotmentStatusTos) {
			RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
			List<RoomAllotmentStatusDetailsTo> allotmentTOList = new ArrayList<RoomAllotmentStatusDetailsTo>();
			for (RoomAllotmentStatusDetailsTo to : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
				to.setAlloted(false);
				  if(!to.isTempCheck()){
					  if(allotedStudentMap.containsKey(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition())){
						  RoomAllotmentStatusDetailsTo statusDetailsTo = allotedStudentMap.get(to.getColumnNO()+"_"+to.getRowNO()+"_"+to.getSeatingPosition());
						  to.setAlloted(true);
						  to.setOrigRegisterNo(statusDetailsTo.getOrigRegisterNo());
						  to.setRegisterNo(statusDetailsTo.getRegisterNo());
						  to.setClassName(statusDetailsTo.getClassName());
						  to.setClassId(statusDetailsTo.getClassId());
						  to.setStudentId(statusDetailsTo.getStudentId());
						  to.setTempField("Odd");
						  if(statusDetailsTo.getSubject()!=0){
							  to.setSubject(statusDetailsTo.getSubject());
							  to.setSubjectName(statusDetailsTo.getSubjectName());
							  to.setClassName("");
						  }
						  to.setRoomNo(statusDetailsTo.getRoomNo());
						  tempColOneAllotedSeats ++;
					  }
					  if(isFlag){
							columnTwoTotalSeats++;
						}else{
							columnOneTotalSeats++;
						}
					  tempColumnOneTotalSeats ++;
				  }
					allotmentTOList.add(to);
					if(isFlag){
						isFlag= false;
					}else{
						isFlag = true;
					}
			}
			allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentTOList);
			allotmentStatusList.add(allotmentStatusTo);
		}
		
		objForm.setColumnOneTotalSeats(columnOneTotalSeats);
		objForm.setColumnTwoTotalSeats(columnTwoTotalSeats);
		objForm.setColOneAllotedSeats(0);
		objForm.setColTwoAllotedSeats(0);
		objForm.setColOneAvailableSeats(columnOneTotalSeats);
		objForm.setColTwoAvailableSeats(columnTwoTotalSeats);
//		
		objForm.setTempTotalSeats(tempColumnOneTotalSeats);
		objForm.setTempAllotedSeats(tempColOneAllotedSeats);
		objForm.setTempAvailableSeats(tempColumnOneTotalSeats-tempColOneAllotedSeats);
		return allotmentStatusList;
	}
	/**
	 * @param detailsTOList
	 * @param allotedStudentMap
	 * @param studentMap
	 * @param oddOrEven
	 * @param roomId
	 * @return
	 * @throws Exception
	 */
	public Map<String, RoomAllotmentStatusDetailsTo> getSingleColumnAllotmentStudentMap( List<ExamRoomAllotmentDetailsTO> detailsTOList,
			Map<String, RoomAllotmentStatusDetailsTo> allotedStudentMap, Map<String, StudentTO> studentMap, String roomId) throws Exception{
		if(detailsTOList!=null && !detailsTOList.isEmpty()){
			studentMap = CommonUtil.sortMapByKey(studentMap);
			Collection<StudentTO> student = studentMap.values();
			LinkedList<StudentTO> stu = new LinkedList<StudentTO>(student);
			 Collections.sort(stu);
			for (ExamRoomAllotmentDetailsTO examRoomAllotmentDetailsTO : detailsTOList) {
				if(stu == null || stu.isEmpty()){
					break;
				}
				for (RoomEndMidSemRowsTo to : examRoomAllotmentDetailsTO.getColumnWiseRows()) {
					if(stu == null || stu.isEmpty()){
						break;
					}
					for(int i=1;i<=to.getNoOfRows();i++){
							RoomAllotmentStatusDetailsTo statusTo = null;
							if(allotedStudentMap!=null && !allotedStudentMap.isEmpty()){
								if(!allotedStudentMap.containsKey(to.getNoOfColumns()+"_"+i+"_"+to.getSeatingPosition())){
									statusTo = new RoomAllotmentStatusDetailsTo();
								}else{
									continue;
								}
							}else{
								statusTo = new RoomAllotmentStatusDetailsTo();
							}
							if(stu == null || stu.isEmpty()){
								break;
							}
							statusTo.setStudentId(stu.getFirst().getStudentId());
							statusTo.setRegisterNo(stu.getFirst().getRegisterNo());
							statusTo.setOrigRegisterNo(stu.getFirst().getRegisterNo());
							statusTo.setClassName(stu.getFirst().getClassName());
							statusTo.setClassId(stu.getFirst().getClassId());
							statusTo.setSeatingPosition(to.getSeatingPosition());
							statusTo.setRowNO(i);
							statusTo.setColumnNO(to.getNoOfColumns());
//							statusTo.setTempField("Odd");
							if(stu.getFirst().getSubjectId()!=null && !stu.getFirst().getSubjectId().isEmpty()){
								statusTo.setSubject(Integer.parseInt(stu.getFirst().getSubjectId()));
								statusTo.setSubjectName(stu.getFirst().getSubjectName());
							}
						    statusTo.setRoomNo(roomId);
							stu.removeFirst();
							
							if(allotedStudentMap == null || allotedStudentMap.isEmpty()){
								allotedStudentMap = new HashMap<String, RoomAllotmentStatusDetailsTo>();
							}
							allotedStudentMap.put(to.getNoOfColumns()+"_"+i+"_"+to.getSeatingPosition(), statusTo);
						}
				}
			}
		}
		return allotedStudentMap;
	}
	}
