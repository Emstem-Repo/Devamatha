package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.hibernate.mapping.Array;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ExamRoomAllotment;
import com.kp.cms.bo.examallotment.ExamRoomAllotmentDetails;
import com.kp.cms.bo.studentfeedback.RoomEndMidSemRows;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.examallotment.RoomAllotmentStatusForm;
import com.kp.cms.to.examallotment.RoomAllotmentStatusDetailsTo;
import com.kp.cms.to.examallotment.RoomAllotmentStatusTo;
import com.kp.cms.to.studentfeedback.RoomEndMidSemRowsTo;
import com.kp.cms.transactions.examallotment.IRoomAllotmentStatusTransaction;
import com.kp.cms.transactionsimpl.examallotment.RoomAllotmentStatusTxnImpl;
import com.kp.cms.transactionsimpl.sap.SapMarksUploadEntryTransactionImpl;

public class RoomAllotmentStatusHelper {

	public static volatile RoomAllotmentStatusHelper roomAllotmentStatusHelper=null;
	
	/**
	 * @return
	 */
	public static RoomAllotmentStatusHelper getInstance(){
		if(roomAllotmentStatusHelper==null){
			roomAllotmentStatusHelper=new RoomAllotmentStatusHelper();
		}
		return roomAllotmentStatusHelper;
		
	}
	
	public RoomAllotmentStatusHelper() {
	}

	public  List<RoomAllotmentStatusTo> convertBoToToList(List<ExamRoomAllotmentDetails> roomAllotmentDetailsList, List<RoomEndMidSemRows> endMidSemRows, RoomAllotmentStatusForm allotmentStatusForm, int maxRowCount) {
         Map<String, RoomAllotmentStatusDetailsTo> studentRowsMap=new HashMap<String, RoomAllotmentStatusDetailsTo>();
         Map<Integer,List<RoomAllotmentStatusDetailsTo>> mainMap = new HashMap<Integer, List<RoomAllotmentStatusDetailsTo>>();
         if(roomAllotmentDetailsList!=null && !roomAllotmentDetailsList.isEmpty()){
			for (ExamRoomAllotmentDetails examRoomAllotmentDetails : roomAllotmentDetailsList) {
				
				/*if(studentRowsMap.containsKey(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition())){
					
					List<RoomAllotmentStatusDetailsTo> statusToList=studentRowsMap.get(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition());
					RoomAllotmentStatusDetailsTo roomAllotmentStatusTo=new RoomAllotmentStatusDetailsTo();
					if(examRoomAllotmentDetails.getStudent()!=null){
						if(examRoomAllotmentDetails.getStudent().getRegisterNo()!=null &&
								!examRoomAllotmentDetails.getStudent().getRegisterNo().isEmpty()){
							roomAllotmentStatusTo.setRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
							roomAllotmentStatusTo.setOrigRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
						}
					}else{
						roomAllotmentStatusTo.setRegisterNo(null);
						roomAllotmentStatusTo.setOrigRegisterNo(null);
					}
					roomAllotmentStatusTo.setId(examRoomAllotmentDetails.getId());
					if(examRoomAllotmentDetails.getClasses()!=null){
						roomAllotmentStatusTo.setClassName(examRoomAllotmentDetails.getClasses().getName());
					}else{
						roomAllotmentStatusTo.setClassName(" ");
					}
					roomAllotmentStatusTo.setSeatingPosition(examRoomAllotmentDetails.getSeatingPosition());
					roomAllotmentStatusTo.setRowNO(examRoomAllotmentDetails.getRowNO());
					roomAllotmentStatusTo.setColumnNO(examRoomAllotmentDetails.getColumnNO());
					statusToList.add(roomAllotmentStatusTo);
					studentRowsMap.put(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition(), statusToList);
				
				}else{
					
					List<RoomAllotmentStatusDetailsTo> statusToList=new ArrayList<RoomAllotmentStatusDetailsTo>();
					RoomAllotmentStatusDetailsTo roomAllotmentStatusTo=new RoomAllotmentStatusDetailsTo();
					if(examRoomAllotmentDetails.getStudent()!=null){
						if(examRoomAllotmentDetails.getStudent().getRegisterNo()!=null &&
								!examRoomAllotmentDetails.getStudent().getRegisterNo().isEmpty()){
							roomAllotmentStatusTo.setRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
							roomAllotmentStatusTo.setOrigRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
						}
					}else{
						roomAllotmentStatusTo.setRegisterNo(null);
						roomAllotmentStatusTo.setOrigRegisterNo(null);
					}
					roomAllotmentStatusTo.setId(examRoomAllotmentDetails.getId());
					if(examRoomAllotmentDetails.getClasses()!=null){
						roomAllotmentStatusTo.setClassName(examRoomAllotmentDetails.getClasses().getName());
					}else{
						roomAllotmentStatusTo.setClassName(" ");
					}
					roomAllotmentStatusTo.setSeatingPosition(examRoomAllotmentDetails.getSeatingPosition());
					roomAllotmentStatusTo.setRowNO(examRoomAllotmentDetails.getRowNO());
					roomAllotmentStatusTo.setColumnNO(examRoomAllotmentDetails.getColumnNO());
					statusToList.add(roomAllotmentStatusTo);
					studentRowsMap.put(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition(), statusToList);
				}*/
				RoomAllotmentStatusDetailsTo roomAllotmentStatusTo=new RoomAllotmentStatusDetailsTo();
				if(examRoomAllotmentDetails.getStudent()!=null){
					if(examRoomAllotmentDetails.getStudent().getRegisterNo()!=null &&
							!examRoomAllotmentDetails.getStudent().getRegisterNo().isEmpty()){
						roomAllotmentStatusTo.setRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
						roomAllotmentStatusTo.setOrigRegisterNo(examRoomAllotmentDetails.getStudent().getRegisterNo());
					}
				}else{
					roomAllotmentStatusTo.setRegisterNo(null);
					roomAllotmentStatusTo.setOrigRegisterNo(null);
				}
				roomAllotmentStatusTo.setId(examRoomAllotmentDetails.getId());
				if(examRoomAllotmentDetails.getClasses()!=null){
					roomAllotmentStatusTo.setClassName(examRoomAllotmentDetails.getClasses().getName());
				}else{
					roomAllotmentStatusTo.setClassName(" ");
				}
				if(examRoomAllotmentDetails.getSubject()!=null){
					roomAllotmentStatusTo.setSubject(examRoomAllotmentDetails.getSubject().getId());
				}
				roomAllotmentStatusTo.setSeatingPosition(examRoomAllotmentDetails.getSeatingPosition());
				roomAllotmentStatusTo.setRowNO(examRoomAllotmentDetails.getRowNO());
				roomAllotmentStatusTo.setColumnNO(examRoomAllotmentDetails.getColumnNO());
				roomAllotmentStatusTo.setRoomNo(String.valueOf(examRoomAllotmentDetails.getAllotment().getRoom().getId()));
				studentRowsMap.put(examRoomAllotmentDetails.getColumnNO()+"_"+examRoomAllotmentDetails.getRowNO()+"_"+examRoomAllotmentDetails.getSeatingPosition(), roomAllotmentStatusTo);
			}
		}
		if(endMidSemRows!=null){
			List<RoomEndMidSemRows> roomDetails = new ArrayList<RoomEndMidSemRows>(endMidSemRows);
			Collections.sort(roomDetails);
			for (RoomEndMidSemRows details : roomDetails) {
				if(details.getNoOfRows() != null && details.getColumnNumber() != null && details.getNoOfSetInDesk() != null){
						for (int i = 1; i <= maxRowCount; i++) {
							if(Integer.parseInt(details.getNoOfRows())>=i){
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
								}
							}else{
								for(int j=1;j<=details.getNoOfSetInDesk();j++){
									RoomAllotmentStatusDetailsTo to1 = new RoomAllotmentStatusDetailsTo();
									to1.setColumnNO(0);
									to1.setRowNO(0);
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
					}
				}
		}
		List<RoomAllotmentStatusTo> allotmentStatusToList=new ArrayList<RoomAllotmentStatusTo>();
		if(!mainMap.isEmpty()){
			for (Entry<Integer, List<RoomAllotmentStatusDetailsTo>> rowsMap : mainMap.entrySet()) {
				RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
				 List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList=new ArrayList<RoomAllotmentStatusDetailsTo>();
					for (RoomAllotmentStatusDetailsTo detailsTo : rowsMap.getValue()) {
						if(studentRowsMap.containsKey(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition())){
							RoomAllotmentStatusDetailsTo statusDetailsTo = studentRowsMap.get(detailsTo.getColumnNO()+"_"+detailsTo.getRowNO()+"_"+detailsTo.getSeatingPosition());
							detailsTo.setRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setOrigRegisterNo(statusDetailsTo.getRegisterNo());
							detailsTo.setClassName(statusDetailsTo.getClassName());
							detailsTo.setRoomNo(statusDetailsTo.getRoomNo());
						}
						allotmentStatusDetailsToList.add(detailsTo);
					}
					allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentStatusDetailsToList);
					allotmentStatusToList.add(allotmentStatusTo);
			}
		}
		
		/*if(!studentRowsMap.isEmpty()){
			for (Entry<Integer, List<RoomAllotmentStatusDetailsTo>> rowsMap : studentRowsMap.entrySet()) {
                   RoomAllotmentStatusTo allotmentStatusTo=new RoomAllotmentStatusTo();
                   List<RoomAllotmentStatusDetailsTo> allotmentStatusDetailsToList=new ArrayList<RoomAllotmentStatusDetailsTo>();
				for (RoomAllotmentStatusDetailsTo detailsTo : rowsMap.getValue()) {
					allotmentStatusDetailsToList.add(detailsTo);
					allotmentStatusTo.setAllotmentStatusDetailsToList(allotmentStatusDetailsToList);
				}
				allotmentStatusToList.add(allotmentStatusTo);
			}
		}*/
		return allotmentStatusToList;
	}

	public List<ExamRoomAllotmentDetails> checkWhichRegNoToUpdate(RoomAllotmentStatusForm allotmentStatusForm,ActionErrors errors) throws Exception {
		List<ExamRoomAllotmentDetails> allotmentDetailsList=new ArrayList<ExamRoomAllotmentDetails>();
		String invalidRegNo="";
		String alreadyExistRegNo="";
		for (RoomAllotmentStatusTo roomAllotmentStatusTo : allotmentStatusForm.getRoomAllotmentStatusToList()) {
			for (RoomAllotmentStatusDetailsTo allotmentStatusDetailsTo : roomAllotmentStatusTo.getAllotmentStatusDetailsToList()) {
				/*if(allotmentStatusDetailsTo.getRegisterNo().equalsIgnoreCase("1311785")){
					System.out.println("hi");
				}*/
				
				if(!allotmentStatusDetailsTo.isTempCheck()){
				if(!allotmentStatusDetailsTo.getRegisterNo().equalsIgnoreCase(allotmentStatusDetailsTo.getOrigRegisterNo())){
					IRoomAllotmentStatusTransaction transaction=RoomAllotmentStatusTxnImpl.getInstance();
					ExamRoomAllotmentDetails allotmentDetails=transaction.getDataFromOrigRegNo(allotmentStatusDetailsTo,allotmentStatusForm);
					if(allotmentStatusDetailsTo.getRegisterNo().isEmpty()){
						if(allotmentDetails!=null){
							allotmentDetails.setStudent(null);
							allotmentDetails.setClasses(null);
							allotmentDetails.setModifiedBy(allotmentStatusForm.getUserId());
							allotmentDetails.setLastModifiedDate(new Date());
							allotmentDetails.setIsActive(false);
							allotmentDetailsList.add(allotmentDetails);
						}
					}else{
						Student student= transaction.getStudentDetailsByRegNo(allotmentStatusDetailsTo.getRegisterNo());
						if(student!=null){
							ExamRoomAllotmentDetails modifyAllotmentDetails=transaction.checkStudentAlreadyAllocatedByRegNo(allotmentStatusDetailsTo.getRegisterNo(),allotmentStatusForm);
							if(modifyAllotmentDetails==null){
								if(allotmentDetails!=null){
									allotmentDetails.setStudent(student);
									if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null){
										allotmentDetails.setClasses(student.getClassSchemewise().getClasses());
									}
									allotmentDetails.setModifiedBy(allotmentStatusForm.getUserId());
									allotmentDetails.setLastModifiedDate(new Date());
									allotmentDetailsList.add(allotmentDetails);
								}else{ // code added by sudhir
									IRoomAllotmentStatusTransaction transaction1=RoomAllotmentStatusTxnImpl.getInstance();
									ExamRoomAllotment roomAllotment = transaction1.getRoomAllotment(allotmentStatusForm);
									allotmentDetails = new ExamRoomAllotmentDetails();
									allotmentDetails.setStudent(student);
									if(student.getClassSchemewise()!=null && student.getClassSchemewise().getClasses()!=null){
										allotmentDetails.setClasses(student.getClassSchemewise().getClasses());
									}
									allotmentDetails.setAllotment(roomAllotment);
									allotmentDetails.setColumnNO(allotmentStatusDetailsTo.getColumnNO());
									allotmentDetails.setRowNO(allotmentStatusDetailsTo.getRowNO());
									allotmentDetails.setSeatingPosition(allotmentStatusDetailsTo.getSeatingPosition());
									allotmentDetails.setIsActive(true);
									allotmentDetails.setCreatedBy(allotmentStatusForm.getUserId());
									allotmentDetails.setCreatedDate(new Date());
									allotmentDetails.setModifiedBy(allotmentStatusForm.getUserId());
									allotmentDetails.setLastModifiedDate(new Date());
									allotmentDetailsList.add(allotmentDetails);
								}//
							}else{
								alreadyExistRegNo=alreadyExistRegNo+","+allotmentStatusDetailsTo.getRegisterNo()+" Already Exist with Room No: "+modifyAllotmentDetails.getAllotment().getRoom().getRoomNo();
							}
						}else{
							invalidRegNo=invalidRegNo+","+allotmentStatusDetailsTo.getRegisterNo();
						}
					}
				}
				}
			}
		}
		if(!invalidRegNo.isEmpty()){
			invalidRegNo=invalidRegNo.substring(1);
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.allotment.room.status.entered.regNo",invalidRegNo));
		}
		if(!alreadyExistRegNo.isEmpty()){
			alreadyExistRegNo=alreadyExistRegNo.substring(1);
			errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.exam.allotment.room.status.already.existed.regNo",alreadyExistRegNo));
		}
		return allotmentDetailsList;
	}
}
