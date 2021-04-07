package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.GrievanceRemarkBo;
import com.kp.cms.bo.exam.GrievanceStudentBo;
import com.kp.cms.forms.exam.GrievanceStudentForm;
import com.kp.cms.forms.usermanagement.LoginForm;
import com.kp.cms.to.exam.GrievanceStudentTo;
import com.kp.cms.transactions.IGrievanceStudentTransaction;
import com.kp.cms.transactionsimpl.exam.GrievanceStudentTransactionImpl;



public class GrievanceStudentHelper {
	private static final Log log = LogFactory.getLog(GrievanceStudentHelper.class);
	public static volatile GrievanceStudentHelper grievanceStudentHelper = null;
	
	public static GrievanceStudentHelper getInstance() {
	if (grievanceStudentHelper == null) {
		grievanceStudentHelper = new GrievanceStudentHelper();
		return grievanceStudentHelper;
	}
	return grievanceStudentHelper;
	}
	
	public  List<GrievanceStudentTo>  convertBotoTo(List<GrievanceRemarkBo> grievanceRemarkBoList,GrievanceStudentForm grievanceStudentForm) throws Exception {
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		List<GrievanceStudentTo> list = new ArrayList<GrievanceStudentTo>();
		if(grievanceRemarkBoList!=null && !grievanceRemarkBoList.isEmpty()){
			Iterator<GrievanceRemarkBo> itr = grievanceRemarkBoList.iterator();
			while (itr.hasNext()) {
				GrievanceRemarkBo grievanceRemarkBo = (GrievanceRemarkBo) itr.next();
				GrievanceStudentTo grievanceStudentTo = new GrievanceStudentTo();
				grievanceStudentTo.setGrievanceNo(grievanceRemarkBo.getGrievanceSerialNo());
				Student student = iGrievanceStudentTransaction.getstudentById(grievanceRemarkBo.getStudent().getId());
				if(student!=null){
					grievanceStudentTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					grievanceStudentTo.setRegisterNo(student.getRegisterNo());
					grievanceStudentForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					grievanceStudentForm.setRegisterNo(student.getRegisterNo());
					
				}
				grievanceStudentTo.setCouresCode(grievanceRemarkBo.getSubject().getCode());
				grievanceStudentTo.setCourseTitle(grievanceRemarkBo.getSubject().getName());
				grievanceStudentTo.setSemister(String.valueOf(grievanceRemarkBo.getClasses().getTermNumber()));
				grievanceStudentTo.setEseAwardedMarks(grievanceRemarkBo.getEseAwardedMarks());
				grievanceStudentTo.setCiaAwardedMarks(grievanceRemarkBo.getCiaAwardedMarks());
				grievanceStudentTo.setId(grievanceRemarkBo.getId());
				grievanceStudentTo.setRemark(grievanceRemarkBo.getComment());
				grievanceStudentForm.setCourseTitle(grievanceRemarkBo.getSubject().getName());
				grievanceStudentForm.setCourseCode(grievanceRemarkBo.getSubject().getCode());
				//grievanceStudentForm.setGrievanceNo(grievanceRemarkBo.getGrievanceSerialNo());
				grievanceStudentForm.setSemester(String.valueOf(grievanceRemarkBo.getClasses().getTermNumber()));
				list.add(grievanceStudentTo);
			}
			
		}
		
		return list;
		
	}
	
	public  List<GrievanceStudentBo>  convertTotoBo(List<GrievanceStudentTo> studentList,GrievanceStudentForm grievanceStudentForm) throws Exception {
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		Iterator<GrievanceStudentTo> itr = studentList.iterator();
		List<GrievanceStudentBo> grievanceStudentBoList = new ArrayList<GrievanceStudentBo>();
		while (itr.hasNext()) {
			GrievanceStudentTo grievanceStudentTo = (GrievanceStudentTo) itr.next();
			GrievanceStudentBo grievanceStudentBo = new GrievanceStudentBo();
			if(grievanceStudentTo.getApproveCheck()!=null || grievanceStudentTo.getRejectCheck()!=null){
				if(grievanceStudentTo.getApproveCheck()!=null && grievanceStudentTo.getApproveCheck().equalsIgnoreCase("on") ){
					grievanceStudentBo.setIsHodApproved(true);
				}
				if(grievanceStudentTo.getRejectCheck()!=null && grievanceStudentTo.getRejectCheck().equalsIgnoreCase("on") ){
					grievanceStudentBo.setIsHodRejected(true);
				}
				if(grievanceStudentTo.getGrievanceRemark()!=null)
					grievanceStudentBo.setRemark(grievanceStudentTo.getGrievanceRemark());
				if(grievanceStudentTo.getGrievanceNo()!=null)
					grievanceStudentBo.setGrievanceNo(grievanceStudentTo.getGrievanceNo());
				grievanceStudentBo.setHodId(grievanceStudentForm.getUserId());
				grievanceStudentBoList.add(grievanceStudentBo);
			}
					
			
		}
		
		return grievanceStudentBoList;
		
	}
	public  List<GrievanceStudentTo>  convertBotoGrievanceTo(List<GrievanceRemarkBo> grievanceRemarkBoList,LoginForm loginForm) throws Exception {
		IGrievanceStudentTransaction iGrievanceStudentTransaction = GrievanceStudentTransactionImpl.getInstance();
		List<GrievanceStudentTo> list = new ArrayList<GrievanceStudentTo>();
		if(grievanceRemarkBoList!=null && !grievanceRemarkBoList.isEmpty()){
			Iterator<GrievanceRemarkBo> itr = grievanceRemarkBoList.iterator();
			while (itr.hasNext()) {
				GrievanceRemarkBo grievanceRemarkBo = (GrievanceRemarkBo) itr.next();
				GrievanceStudentTo grievanceStudentTo = new GrievanceStudentTo();
				grievanceStudentTo.setGrievanceNo(grievanceRemarkBo.getGrievanceSerialNo());
				Student student = iGrievanceStudentTransaction.getstudentById(grievanceRemarkBo.getStudent().getId());
				if(student!=null){
					grievanceStudentTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					grievanceStudentTo.setRegisterNo(student.getRegisterNo());
					loginForm.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
					loginForm.setRegNo(student.getRegisterNo());
					loginForm.setCourseName(student.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				grievanceStudentTo.setCouresCode(grievanceRemarkBo.getSubject().getCode());
				grievanceStudentTo.setCourseTitle(grievanceRemarkBo.getSubject().getName());
				grievanceStudentTo.setSemister(String.valueOf(grievanceRemarkBo.getClasses().getTermNumber()));
				grievanceStudentTo.setEseAwardedMarks(grievanceRemarkBo.getEseAwardedMarks());
				grievanceStudentTo.setCiaAwardedMarks(grievanceRemarkBo.getCiaAwardedMarks());
				grievanceStudentTo.setToatlMark(grievanceRemarkBo.getTotalAwardedMarks());
				grievanceStudentTo.setId(grievanceRemarkBo.getId());
				grievanceStudentTo.setRemark(grievanceRemarkBo.getComment());
				loginForm.setSchemeNo(String.valueOf(grievanceRemarkBo.getClasses().getTermNumber()));
				loginForm.setExamName(grievanceRemarkBo.getExamDefinition().getName());
				
				list.add(grievanceStudentTo);
			}
			
		}
		
		return list;
		
	}

}
