package com.kp.cms.helpers.admin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.bo.admin.PeersEvaluationOpenSession;
import com.kp.cms.forms.admin.PeersEvaluationOpenSessionForm;
import com.kp.cms.to.admin.DepartmentEntryTO;
import com.kp.cms.to.admin.PeersEvaluationOpenSessionTo;
import com.kp.cms.utilities.CommonUtil;

public class PeersEvaluationOpenSessionHelper {
	public static volatile PeersEvaluationOpenSessionHelper helper = null;
	public static PeersEvaluationOpenSessionHelper getInstance(){
		if(helper == null){
			helper = new PeersEvaluationOpenSessionHelper();
			return helper;
		}
		return helper;
	}
	/**
	 * @param department
	 * @return
	 * @throws Exception
	 */
	public List<DepartmentEntryTO> copyDataFromBoTOTo( List<Department> department) throws Exception{
		List<DepartmentEntryTO> tos = new ArrayList<DepartmentEntryTO>();
		if(department!=null && !department.isEmpty()){
			Iterator<Department> iterator = department.iterator();
			while (iterator.hasNext()) {
				Department dept = (Department) iterator.next();
				DepartmentEntryTO entryTO = new DepartmentEntryTO();
				if(dept.getId()!=0){
					entryTO.setId(dept.getId());
				}
				if(dept.getName()!=null && !dept.getName().isEmpty()){
					entryTO.setName(dept.getName());
				}
				tos.add(entryTO);
			}
		}
		return tos;
	}
	/**
	 * @param openSessions
	 * @return
	 * @throws Exception
	 */
	public List<PeersEvaluationOpenSessionTo> convertBoTOTo( List<PeersEvaluationOpenSession> openSessions) throws Exception{
		List<PeersEvaluationOpenSessionTo> tos = new ArrayList<PeersEvaluationOpenSessionTo>();
		if(openSessions!=null && !openSessions.isEmpty()){
			Iterator<PeersEvaluationOpenSession> iterator = openSessions.iterator();
			while (iterator.hasNext()) {
				PeersEvaluationOpenSession bo = (PeersEvaluationOpenSession) iterator .next();
				PeersEvaluationOpenSessionTo sessionTo = new PeersEvaluationOpenSessionTo();
				if(bo.getId()!=0){
					sessionTo.setId(bo.getId());
				}
				if(bo.getDepartmentId()!=null && !bo.getDepartmentId().toString().isEmpty()){
					if(bo.getDepartmentId().getId()!=0){
						sessionTo.setDepartmentId(bo.getDepartmentId().getId());
					}
					if(bo.getDepartmentId().getName()!=null && !bo.getDepartmentId().getName().isEmpty()){
						sessionTo.setDepartmentName(bo.getDepartmentId().getName());
					}
				}
				if(bo.getStartDate()!=null && !bo.getStartDate().toString().isEmpty()){
					sessionTo.setStartDate(formatDate(bo.getStartDate()));
				}
				if(bo.getEndDate()!=null && !bo.getEndDate().toString().isEmpty()){
					sessionTo.setEndDate(formatDate(bo.getEndDate()));
				}
				if(bo.getPeerFeedbackSession()!=null && !bo.getPeerFeedbackSession().toString().isEmpty()){
					if(bo.getPeerFeedbackSession().getId()!=0){
						sessionTo.setSessionId(bo.getPeerFeedbackSession().getId());
					}
					if(bo.getPeerFeedbackSession().getSession()!=null && !bo.getPeerFeedbackSession().getSession().isEmpty()){
						sessionTo.setSessionName(bo.getPeerFeedbackSession().getSession());
					}
					if(bo.getPeerFeedbackSession().getAcademicYear()!=null && !bo.getPeerFeedbackSession().getAcademicYear().isEmpty()){
						sessionTo.setYear(bo.getPeerFeedbackSession().getAcademicYear());
					}
				}
				tos.add(sessionTo);
			}
		}
		return tos;
	}
	/**
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 */
	public List<PeersEvaluationOpenSession> convertDataFromFormTOBo( PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm) {
		List<PeersEvaluationOpenSession> openSessionList = new ArrayList<PeersEvaluationOpenSession>();
			String[] departmentIds = peersEvaluationOpenSessionForm.getDepartmentIds();
			int i ;
			for(i=0;i<departmentIds.length;i++){
				String deptId = departmentIds[i];
				PeersEvaluationOpenSession openSession = new PeersEvaluationOpenSession();
				Department department = new Department();
				department.setId(Integer.parseInt(deptId));
				openSession.setDepartmentId(department);
				if(peersEvaluationOpenSessionForm.getStartDate()!=null && !peersEvaluationOpenSessionForm.getStartDate().isEmpty()){
					openSession.setStartDate(CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm.getStartDate()));
				}
				if(peersEvaluationOpenSessionForm.getEndDate()!=null && !peersEvaluationOpenSessionForm.getEndDate().isEmpty()){
					openSession.setEndDate(CommonUtil.ConvertStringToDate(peersEvaluationOpenSessionForm.getEndDate()));
				}
				if(peersEvaluationOpenSessionForm.getSessionId()!=null && !peersEvaluationOpenSessionForm.getSessionId().isEmpty()){
					PeerFeedbackSession session = new PeerFeedbackSession();
					session.setId(Integer.parseInt(peersEvaluationOpenSessionForm.getSessionId()));
					openSession.setPeerFeedbackSession(session);
				}
				openSession.setCreatedBy(peersEvaluationOpenSessionForm.getUserId());
				openSession.setCreatedDate(new Date());
				openSession.setIsActive(true);
				openSessionList.add(openSession);
			}
			
		return openSessionList;
	}
	/**
	 * @param date
	 * @return
	 */
	public String formatDate(Date date){ DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		String newDate=formatter.format(date);
		return newDate;
	}
	/**
	 * @param openSession 
	 * @param peersEvaluationOpenSessionForm
	 * @return
	 * @throws Exception
	 */
	public PeersEvaluationOpenSessionForm populateBoTOTo( PeersEvaluationOpenSession openSession, PeersEvaluationOpenSessionForm peersEvaluationOpenSessionForm)throws Exception {
		if(openSession!=null && !openSession.toString().isEmpty()){
			if(openSession.getId()!=0){
				peersEvaluationOpenSessionForm.setId(openSession.getId());
			}
			if(openSession.getStartDate()!=null && !openSession.getStartDate().toString().isEmpty()){
				peersEvaluationOpenSessionForm.setStartDate(formatDate(openSession.getStartDate()));
			}
			if(openSession.getEndDate()!=null && !openSession.getEndDate().toString().isEmpty()){
				peersEvaluationOpenSessionForm.setEndDate(formatDate(openSession.getEndDate()));
			}
			if(openSession.getDepartmentId()!=null && !openSession.getDepartmentId().toString().isEmpty()){
				if(openSession.getDepartmentId().getId()!=0){
					String deptId[] =  new String[1];
					deptId[0] = Integer.toString(openSession.getDepartmentId().getId());
					peersEvaluationOpenSessionForm.setDepartmentIds(deptId);
				}
			}
			if(openSession.getPeerFeedbackSession()!=null && !openSession.getPeerFeedbackSession().toString().isEmpty()){
				if(openSession.getPeerFeedbackSession().getId()!=0){
					peersEvaluationOpenSessionForm.setSessionId(String.valueOf(openSession.getPeerFeedbackSession().getId()));
				}
				if(openSession.getPeerFeedbackSession().getAcademicYear()!=null && !openSession.getPeerFeedbackSession().getAcademicYear().isEmpty()){
					peersEvaluationOpenSessionForm.setAcademicYear(openSession.getPeerFeedbackSession().getAcademicYear());
				}
			}
			
		}
		return peersEvaluationOpenSessionForm;
	}
}
