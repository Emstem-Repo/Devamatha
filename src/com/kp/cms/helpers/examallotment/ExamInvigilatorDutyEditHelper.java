package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Users;
import com.kp.cms.bo.exam.ExamDefinition;
import com.kp.cms.bo.examallotment.ExamInviligatorDuties;
import com.kp.cms.bo.examallotment.ExaminationSessions;
import com.kp.cms.bo.studentfeedback.RoomMaster;
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyEditForm;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyEditTo;
import com.kp.cms.transactions.ajax.ICommonAjax;
import com.kp.cms.transactions.examallotment.IInvigilatorsForExamTrans;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;
import com.kp.cms.transactionsimpl.examallotment.InvigilatorsForExamTransImpl;
import com.kp.cms.utilities.CommonUtil;

public class ExamInvigilatorDutyEditHelper {
	private static volatile ExamInvigilatorDutyEditHelper examInvigilatorDutyEditHelper=null;
	IInvigilatorsForExamTrans transaction=InvigilatorsForExamTransImpl.getInstance();
	/**
	 * instance()
	 * @return
	 */
	private ExamInvigilatorDutyEditHelper(){
		
	}
	public static ExamInvigilatorDutyEditHelper getInstance(){
		if(examInvigilatorDutyEditHelper == null){
			examInvigilatorDutyEditHelper=new ExamInvigilatorDutyEditHelper();
		}
		return examInvigilatorDutyEditHelper;
	}
	public String createQuery(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		StringBuilder stringBuilder=new StringBuilder();
		stringBuilder.append("from ExamInviligatorDuties e where e.isActive=1 and e.examId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getExamId()) +
				" and e.workLocationId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getLocationId()));
		if(examInvigilatorDutyEditForm.getExamDate()!=null && !examInvigilatorDutyEditForm.getExamDate().isEmpty()){
			stringBuilder.append(" and e.examDate='"+CommonUtil.ConvertStringToSQLDate(examInvigilatorDutyEditForm.getExamDate())+"'");
		}
		if(examInvigilatorDutyEditForm.getExamSession()!=null && !examInvigilatorDutyEditForm.getExamSession().isEmpty()){
			stringBuilder.append(" and e.examinationSessions.id="+examInvigilatorDutyEditForm.getExamSession());
		}
		if(examInvigilatorDutyEditForm.getExamFacultyId()!=null && !examInvigilatorDutyEditForm.getExamFacultyId().isEmpty()){
			stringBuilder.append(" and e.teacherId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getExamFacultyId()));
		}
		if(examInvigilatorDutyEditForm.getExamRoomNo()!=null && !examInvigilatorDutyEditForm.getExamRoomNo().isEmpty()){
			stringBuilder.append(" and e.roomId.id="+Integer.parseInt(examInvigilatorDutyEditForm.getExamRoomNo()));
		}
		if(examInvigilatorDutyEditForm.getExamExaminerType()!=null && !examInvigilatorDutyEditForm.getExamExaminerType().isEmpty()){
			stringBuilder.append(" and e.invOrReliver='"+examInvigilatorDutyEditForm.getExamExaminerType()+"'");
		}
		return stringBuilder.toString();
	}
	public List<ExamInvigilatorDutyEditTo> convertBosToTos(
			List<ExamInviligatorDuties> examInviligatorDuties, Map<Integer, String> facultyMap, Map<Integer, String> roomNoMap) throws Exception{
		List<ExamInvigilatorDutyEditTo> list=new ArrayList<ExamInvigilatorDutyEditTo>();
		Map<String,String> examinerTypeMap=new HashMap<String, String>();
		examinerTypeMap.put("I", "Invigilator");
		examinerTypeMap.put("R", "Reliever");
		examinerTypeMap=CommonUtil.sortMapByValue(examinerTypeMap);
		/*Map<String,String> sessionMap=new HashMap<String, String>();
		sessionMap.put("AM", "AM");
		sessionMap.put("PM", "PM");*/
		Map<Integer,String> sessionMap=transaction.getExamSession();
		if(sessionMap!=null && !sessionMap.isEmpty())
		sessionMap=CommonUtil.sortMapByValue(sessionMap);
		Iterator<ExamInviligatorDuties> iterator=examInviligatorDuties.iterator();
		ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo=null;
		while (iterator.hasNext()) {
			ExamInviligatorDuties examInviligatorDuties2 = (ExamInviligatorDuties) iterator.next();
			examInvigilatorDutyEditTo=new ExamInvigilatorDutyEditTo();
			examInvigilatorDutyEditTo.setId(examInviligatorDuties2.getId());
			examInvigilatorDutyEditTo.setFacultyMap(facultyMap);
			examInvigilatorDutyEditTo.setRoomNoMap(roomNoMap);
			examInvigilatorDutyEditTo.setSessionMap(sessionMap);
			examInvigilatorDutyEditTo.setExaminerTypeMap(examinerTypeMap);
			examInvigilatorDutyEditTo.setSession(String.valueOf(examInviligatorDuties2.getExaminationSessions().getId()));
			examInvigilatorDutyEditTo.setHiddenSession(examInviligatorDuties2.getSession());
			examInvigilatorDutyEditTo.setFacultyId(String.valueOf(examInviligatorDuties2.getTeacherId().getId()));
			examInvigilatorDutyEditTo.setHiddenFacultyId(String.valueOf(examInviligatorDuties2.getTeacherId().getId()));
			examInvigilatorDutyEditTo.setRoomNo(String.valueOf(examInviligatorDuties2.getRoomId().getId()));
			examInvigilatorDutyEditTo.setHiddenRoomNo(String.valueOf(examInviligatorDuties2.getRoomId().getId()));
			examInvigilatorDutyEditTo.setExaminerType(examInviligatorDuties2.getInvOrReliver());
			examInvigilatorDutyEditTo.setHiddenExaminerType(examInviligatorDuties2.getInvOrReliver());
			examInvigilatorDutyEditTo.setDate(CommonUtil.formatDates(examInviligatorDuties2.getExamDate()));
			examInvigilatorDutyEditTo.setHiddenDate(CommonUtil.formatDates(examInviligatorDuties2.getExamDate()));
			if(examInviligatorDuties2.getIsApproved()!=null && examInviligatorDuties2.getIsApproved()){
				examInvigilatorDutyEditTo.setChecked("on");
			}
			list.add(examInvigilatorDutyEditTo);
		}
		return list;
	}
	public void addMoreList(
			Map<Integer, String> facultyMap, Map<Integer, String> roomNoMap, ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		List<ExamInvigilatorDutyEditTo> list=null;
		if(examInvigilatorDutyEditForm.getAddMorelist()!=null && !examInvigilatorDutyEditForm.getAddMorelist().isEmpty()){
			list=examInvigilatorDutyEditForm.getAddMorelist();
		}else{
			list=new ArrayList<ExamInvigilatorDutyEditTo>();
		}
		ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo=new ExamInvigilatorDutyEditTo();
		Map<String,String> examinerTypeMap=new HashMap<String, String>();
		examinerTypeMap.put("I", "Invigilator");
		examinerTypeMap.put("R", "Reliever");
		examinerTypeMap=CommonUtil.sortMapByValue(examinerTypeMap);
		/*Map<String,String> sessionMap=new HashMap<String, String>();
		sessionMap.put("AM", "AM");
		sessionMap.put("PM", "PM");*/
		Map<Integer,String> sessionMap=transaction.getExamSession();
		if(sessionMap!=null && !sessionMap.isEmpty())
		sessionMap=CommonUtil.sortMapByValue(sessionMap);
		examInvigilatorDutyEditTo.setFacultyMap(facultyMap);
		examInvigilatorDutyEditTo.setRoomNoMap(roomNoMap);
		examInvigilatorDutyEditTo.setSessionMap(sessionMap);
		examInvigilatorDutyEditTo.setExaminerTypeMap(examinerTypeMap);
		examInvigilatorDutyEditTo.setFlag("false");
			list.add(examInvigilatorDutyEditTo);
		examInvigilatorDutyEditForm.setAddMorelist(list);
			examInvigilatorDutyEditForm.setAddMoreFlag(true);
			examInvigilatorDutyEditForm.setFocus("dt_"+(list.size()-1));
			examInvigilatorDutyEditForm.setAddMoreListSize(examInvigilatorDutyEditForm.getAddMorelist().size());
	}
	public void changeTheFlagValue(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		List<ExamInvigilatorDutyEditTo> list1=new ArrayList<ExamInvigilatorDutyEditTo>();
		if(examInvigilatorDutyEditForm.getList()!=null && !examInvigilatorDutyEditForm.getList().isEmpty()){
			List<ExamInvigilatorDutyEditTo> list=examInvigilatorDutyEditForm.getList();
			Iterator<ExamInvigilatorDutyEditTo> iterator=list.iterator();
			while (iterator.hasNext()) {
				ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo = (ExamInvigilatorDutyEditTo) iterator.next();
				if(examInvigilatorDutyEditTo.getChecked()!=null && !examInvigilatorDutyEditTo.getChecked().isEmpty() && examInvigilatorDutyEditTo.getChecked().equalsIgnoreCase("on")){
					examInvigilatorDutyEditTo.setFlag("true");
				}else{
					examInvigilatorDutyEditTo.setFlag("false");
				}
				list1.add(examInvigilatorDutyEditTo);
			}
			examInvigilatorDutyEditForm.setList(null);
			examInvigilatorDutyEditForm.setList(list1);
		}
	}
	public List<ExamInviligatorDuties> changeIsApproved(
			List<ExamInviligatorDuties> examInviligatorDuties, String userId) throws Exception{
		List<ExamInviligatorDuties> list=new ArrayList<ExamInviligatorDuties>();
		Iterator<ExamInviligatorDuties> iterator=examInviligatorDuties.iterator();
		while (iterator.hasNext()) {
			ExamInviligatorDuties examInviligatorDuties2 = (ExamInviligatorDuties) iterator	.next();
			examInviligatorDuties2.setIsApproved(true);
			examInviligatorDuties2.setLastModifiedDate(new Date());
			examInviligatorDuties2.setModifiedBy(userId);
			list.add(examInviligatorDuties2);
		}
		return list;
	}
	public List<ExamInviligatorDuties> convertformToBos(
			ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm, HttpServletRequest request) throws Exception{
		List<ExamInviligatorDuties> list=new ArrayList<ExamInviligatorDuties>();
		Iterator<ExamInvigilatorDutyEditTo> iterator=examInvigilatorDutyEditForm.getAddMorelist().iterator();
		ExamInviligatorDuties examInviligatorDuties=null;
		StringBuilder stringBuilder=null;
		while (iterator.hasNext()) {
			ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo = (ExamInvigilatorDutyEditTo) iterator.next();
			if(examInvigilatorDutyEditTo.getDate()!=null && !examInvigilatorDutyEditTo.getDate().isEmpty()
					&& examInvigilatorDutyEditTo.getSession()!=null && !examInvigilatorDutyEditTo.getSession().isEmpty()
					&& examInvigilatorDutyEditTo.getExaminerType()!=null && !examInvigilatorDutyEditTo.getExaminerType().trim().isEmpty()
					&& examInvigilatorDutyEditTo.getFacultyId().trim()!=null && !examInvigilatorDutyEditTo.getFacultyId().trim().isEmpty()
					&& examInvigilatorDutyEditTo.getRoomNo()!=null && !examInvigilatorDutyEditTo.getRoomNo().isEmpty()){
				stringBuilder=new StringBuilder();
				stringBuilder.append("from ExamInviligatorDuties e where e.teacherId.id="+Integer.parseInt(examInvigilatorDutyEditTo.getFacultyId())
						+" and e.isActive=1 and e.examDate='"+CommonUtil.ConvertStringToSQLDate(examInvigilatorDutyEditTo.getDate()) +"'");
				if(examInvigilatorDutyEditTo.getExaminerType().equalsIgnoreCase("R")){
					stringBuilder.append(" and e.invOrReliver='"+examInvigilatorDutyEditTo.getExaminerType()+"'");
				}
				//checking duplicate for same date 
				if(examInvigilatorDutyEditForm.getAddNewType()==null){
					boolean flag=false;
					ICommonAjax iCommonAjax = CommonAjaxImpl.getInstance();
					List<ExamInviligatorDuties> examInviligatorDuties2=iCommonAjax.checkDuplicateInvigilator(stringBuilder.toString());
					if(examInvigilatorDutyEditTo.getExaminerType().equalsIgnoreCase("I")){
						if(examInviligatorDuties2!=null && !examInviligatorDuties2.isEmpty()){
							flag=true;
							Iterator<ExamInviligatorDuties> iterator1=examInviligatorDuties2.iterator();
							while (iterator1.hasNext()) {
								ExamInviligatorDuties examInviligatorDuties1 = (ExamInviligatorDuties) iterator1.next();
									if(!examInviligatorDuties1.getSession().equalsIgnoreCase(examInvigilatorDutyEditTo.getSession())){
										//if date and session both same
										request.setAttribute("date", examInvigilatorDutyEditTo.getDate());
										request.setAttribute("session", examInviligatorDuties1.getSession());
										request.setAttribute("list1","list1");
										throw new Exception();
									}
							}
						}
					}
					if(flag){
						//if date is same
						request.setAttribute("date", examInvigilatorDutyEditTo.getDate());
						request.setAttribute("session", examInvigilatorDutyEditTo.getSession());
						request.setAttribute("list","list");
						throw new Exception();
					}
				}
				
					examInviligatorDuties=new ExamInviligatorDuties();
					examInviligatorDuties.setExamDate(CommonUtil.ConvertStringToSQLDate(examInvigilatorDutyEditTo.getDate()));
					ExaminationSessions session=new ExaminationSessions();
					session.setId(Integer.parseInt(examInvigilatorDutyEditTo.getSession()));
					examInviligatorDuties.setExaminationSessions(session);
					examInviligatorDuties.setInvOrReliver(examInvigilatorDutyEditTo.getExaminerType());
					examInviligatorDuties.setIsActive(true);
					examInviligatorDuties.setCreatedBy(examInvigilatorDutyEditForm.getUserId());
					examInviligatorDuties.setCreatedDate(new Date());
					examInviligatorDuties.setModifiedBy(examInvigilatorDutyEditForm.getUserId());
					examInviligatorDuties.setLastModifiedDate(new Date());
					Users users=new Users();
					users.setId(Integer.parseInt(examInvigilatorDutyEditTo.getFacultyId()));
					examInviligatorDuties.setTeacherId(users);
					EmployeeWorkLocationBO examBo=new EmployeeWorkLocationBO();
					examBo.setId(Integer.parseInt(examInvigilatorDutyEditForm.getLocationId()));
					examInviligatorDuties.setWorkLocationId(examBo);
					ExamDefinition examDef=new ExamDefinition();
					examDef.setId(Integer.parseInt(examInvigilatorDutyEditForm.getExamId()));
					examInviligatorDuties.setExamId(examDef);
					RoomMaster room=new RoomMaster();
					room.setId(Integer.parseInt(examInvigilatorDutyEditTo.getRoomNo()));
					examInviligatorDuties.setRoomId(room);
					if(examInvigilatorDutyEditTo.getChecked()!=null && !examInvigilatorDutyEditTo.getChecked().isEmpty() && examInvigilatorDutyEditTo.getChecked().equalsIgnoreCase("on")){
						examInviligatorDuties.setIsApproved(true);
					}else{
						examInviligatorDuties.setIsApproved(false);
					}
					list.add(examInviligatorDuties);
			}else{
				if(examInvigilatorDutyEditTo.getRoomNo().trim().isEmpty()&& examInvigilatorDutyEditTo.getFacultyId().trim().isEmpty()
						&& examInvigilatorDutyEditTo.getExaminerType().trim().isEmpty() && examInvigilatorDutyEditTo.getSession().trim().isEmpty() 
						&& examInvigilatorDutyEditTo.getDate().trim().isEmpty()){
					request.setAttribute("field","Date,Session,Examiner Type,Faculty and Room No");
				}else if(examInvigilatorDutyEditTo.getDate().trim()==null || examInvigilatorDutyEditTo.getDate().trim().isEmpty()){
					request.setAttribute("field","Date");
				}else if(examInvigilatorDutyEditTo.getSession().trim()==null || examInvigilatorDutyEditTo.getSession().trim().isEmpty()){
					request.setAttribute("field","Session");
				}else if(examInvigilatorDutyEditTo.getExaminerType().trim()==null || examInvigilatorDutyEditTo.getExaminerType().trim().isEmpty()){
					request.setAttribute("field","Examiner Type");
				}else if(examInvigilatorDutyEditTo.getFacultyId().trim()==null || examInvigilatorDutyEditTo.getFacultyId().trim().isEmpty()){
					request.setAttribute("field","Faculty");
				}else if(examInvigilatorDutyEditTo.getRoomNo().trim()==null || examInvigilatorDutyEditTo.getRoomNo().trim().isEmpty()){
					request.setAttribute("field","Room No");
				}
				request.setAttribute("all","all");
				throw new Exception();
			}
		}
		return list;
	}
	public ExamInviligatorDuties convertToToBo(
			ExamInvigilatorDutyEditTo examInvigilatorDutyEditTo, ExamInvigilatorDutyEditForm examInvigilatorDutyEditForm) throws Exception{
		ExamInviligatorDuties examInviligatorDuties=new ExamInviligatorDuties();
		examInviligatorDuties.setId(examInvigilatorDutyEditTo.getId());
		examInviligatorDuties.setExamDate(CommonUtil.ConvertStringToSQLDate(examInvigilatorDutyEditTo.getDate()));
		ExaminationSessions session=new ExaminationSessions();
		session.setId(Integer.parseInt(examInvigilatorDutyEditTo.getSession()));
		examInviligatorDuties.setExaminationSessions(session);
		examInviligatorDuties.setInvOrReliver(examInvigilatorDutyEditTo.getExaminerType());
		examInviligatorDuties.setIsActive(true);
		examInviligatorDuties.setModifiedBy(examInvigilatorDutyEditForm.getUserId());
		examInviligatorDuties.setLastModifiedDate(new Date());
		Users users=new Users();
		users.setId(Integer.parseInt(examInvigilatorDutyEditTo.getFacultyId()));
		examInviligatorDuties.setTeacherId(users);
		EmployeeWorkLocationBO examBo=new EmployeeWorkLocationBO();
		examBo.setId(Integer.parseInt(examInvigilatorDutyEditForm.getLocationId()));
		examInviligatorDuties.setWorkLocationId(examBo);
		ExamDefinition examDef=new ExamDefinition();
		examDef.setId(Integer.parseInt(examInvigilatorDutyEditForm.getExamId()));
		examInviligatorDuties.setExamId(examDef);
		RoomMaster room=new RoomMaster();
		room.setId(Integer.parseInt(examInvigilatorDutyEditTo.getRoomNo()));
		examInviligatorDuties.setRoomId(room);
		if(examInvigilatorDutyEditTo.getTempChecked()!=null && !examInvigilatorDutyEditTo.getTempChecked().isEmpty() && examInvigilatorDutyEditTo.getTempChecked().equalsIgnoreCase("on")){
			examInviligatorDuties.setIsApproved(true);
		}else{
			examInviligatorDuties.setIsApproved(false);
		}
		return examInviligatorDuties;
	}
}
