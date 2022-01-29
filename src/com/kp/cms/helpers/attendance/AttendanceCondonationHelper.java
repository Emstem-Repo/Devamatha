package com.kp.cms.helpers.attendance;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceCondonationForm;
import com.kp.cms.forms.reports.StudentWiseAttendanceSummaryForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.AttendanceCondonationHandler;
import com.kp.cms.handlers.attendance.StudentAttendanceSummaryHandler;
import com.kp.cms.helpers.exam.NewSupplementaryImpApplicationHelper;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.attendance.AttendanceCondonationTo;
import com.kp.cms.transactions.attandance.IAttendanceCondonationTransaction;
import com.kp.cms.transactionsimpl.admission.DisciplinaryDetailsImpl;
import com.kp.cms.transactionsimpl.attendance.AttendanceCondonationTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class AttendanceCondonationHelper {

	
	public static volatile AttendanceCondonationHelper self = null;
	
	public static AttendanceCondonationHelper getInstance(){
		if(self == null){
			self = new AttendanceCondonationHelper();
		}
		return self;
		
	}

	public List convertBotoTo(List studentBo,int mode, AttendanceCondonationForm stform) throws Exception {
		
		double count=0;
		double per=0;
		double cutoff=0;
		double total=0;
		DecimalFormat df = new DecimalFormat("#.##");
		List<Object[]> objlist = null;
		List<AttendanceCondonationTo> studentList = new ArrayList<AttendanceCondonationTo>();
		IAttendanceCondonationTransaction txn = AttendanceCondonationTransactionImpl.getInstance();
		

		if(!StringUtils.isEmpty(stform.getCutoff()) && stform.getCutoff()!=null){
			cutoff = Double.parseDouble(stform.getCutoff());
		}
		
		total=txn.gettotalattendance(stform).size(); //getting no of working days
		
		Iterator<Student> itr = studentBo.iterator();
		while(itr.hasNext()){
			
			Student bo = (Student)itr.next();
		    AttendanceCondonationTo to = new AttendanceCondonationTo(); 
		   
	   // objlist = txn.getstudentattendance(bo,stform,mode); //no of sessions student is abscent
		    
		    //calculating attendance percentage
		    /*if(objlist!=null  && total!=0){
		      count= objlist.size();
		      count= count/2;
		      count= total-count;
		      per=  (count/total)*100;
		      per =Double.parseDouble(df.format(per));
		      
		     }*/
		    
		    //session wise
		   /* DisciplinaryDetailsImpl impl = new DisciplinaryDetailsImpl();
			int classId = bo.getClassSchemewise().getClasses().getId();
			List studateList=impl.getDateList(bo.getId(), classId);
			List clsdateList=impl.getDateList(0, classId);
			List sessionAttendanceList=impl.getSessionAttendanceList(bo.getId(), classId);
			per=NewSupplementaryImpApplicationHelper.getInstance().getAttendancePercentage(studateList, sessionAttendanceList, clsdateList);
			*///period wise
			StudentWiseAttendanceSummaryForm form=new StudentWiseAttendanceSummaryForm();
			form.setClassesId(bo.getClassSchemewise().getClasses().getId()+"");
			//periodw wise
			per =StudentAttendanceSummaryHandler.getInstance().getStudentWiseSubjectsAttendancePercentage(bo.getId(), form, bo.getClassSchemewise().getClasses().getCourse().getId()+"");
			form=null;
		     if(per<cutoff || StringUtils.isEmpty(stform.getCutoff())){
		    	
		       to.setStudentName(bo.getAdmAppln().getPersonalData().getFirstName());
		       if(bo.getRegisterNo()!=null && !StringUtils.isEmpty(bo.getRegisterNo())){
		    	   to.setRegisterNo(bo.getRegisterNo());
		       }
		    
		       if(bo.getRollNo()!=null && !StringUtils.isEmpty(bo.getRollNo())){
		    	   to.setRollNo(bo.getRollNo());
		    	
		       }
		       to.setClasseId(Integer.parseInt(stform.getClassId()));
		       to.setPreviousPercentage(df.format(per));
		       to.setStudentId(bo.getId());
		       to.setIsOver(false);
		       
		       //restricting students from entering condonation who having morethan 1
		       List<AttendanceCondonation> ac = new ArrayList<AttendanceCondonation>();
		       ac = CommonAjaxHandler.getInstance().condonationRestrict(new Integer(bo.getId()).toString());
		       if(ac.size()>1){
		    	to.setIsOver(true);
		    	to.setDisplayMsg("Max limit over");
		    	   
		       }
		       
		       
		       studentList.add(to);
		    
		     }
		    
		}
		
		
		Collections.sort(studentList);
		return studentList;
	}

	public List convertBotoToEdit(List studentBo, String classId) {
		
		List<AttendanceCondonationTo> studentList = new ArrayList<AttendanceCondonationTo>();
		
		Iterator<AttendanceCondonation> itr = studentBo.iterator();
		
		while(itr.hasNext()){
			
			AttendanceCondonation bo = (AttendanceCondonation)itr.next();
			AttendanceCondonationTo to = new AttendanceCondonationTo();
			
			if(bo.getAddedPercentage()!=null)
			to.setAddedPercentage(bo.getAddedPercentage().toString());
			
			if(bo.getStudent().getRegisterNo()!=null)
			to.setRegisterNo(bo.getStudent().getRegisterNo());
			
			if(bo.getStudent().getRollNo()!=null)
			to.setRollNo(bo.getStudent().getRollNo());
			
			
			to.setStudentName(bo.getStudent().getAdmAppln().getPersonalData().getFirstName());
			
			if(to.getTotalPercentage()!=null)
			to.setTotalPercentage(bo.getTotalPercentage().toString());
			
			if(bo.getPreviousPercentage()!=null)
				to.setPreviousPercentage(bo.getPreviousPercentage().toString());
			
			to.setClasseId(Integer.parseInt(classId));
			
			to.setStudentId(bo.getStudent().getId());
			
			to.setId(bo.getId());
			to.setIsOver(false);
			
			studentList.add(to);
			
			
		}
		Collections.sort(studentList);
		return studentList;
	}

	public List convertTotoBo(AttendanceCondonationForm stform) {
		
		List<AttendanceCondonationTo> studentList = stform.getStudentList();
		List<AttendanceCondonation> studentListBo = new ArrayList<AttendanceCondonation>();
		
		Iterator<AttendanceCondonationTo> itr = studentList.iterator();
		
		while(itr.hasNext()){
			AttendanceCondonationTo to = itr.next();
			
			AttendanceCondonation bo = new AttendanceCondonation();
			if(  to.getAddedPercentage()!=null && !to.getAddedPercentage().isEmpty()){
			   
				bo.setAddedPercentage(Double.parseDouble(to.getAddedPercentage()));
				
				if(to.getTotalPercentage()!=null)
				bo.setTotalPercentage(Double.parseDouble(to.getTotalPercentage()));
				
				if(to.getPreviousPercentage()!=null)
					bo.setPreviousPercentage((Double.parseDouble(to.getPreviousPercentage())));
					
				
				Classes c = new Classes();
				c.setId(Integer.parseInt(stform.getClassId()));
				bo.setClasses(c);
				
				Student s = new Student();
				s.setId(to.getStudentId());
				bo.setStudent(s);
				
				bo.setCreateDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				bo.setCreatedBy(stform.getUserId());
				
				bo.setModifiedDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.getTodayDate()));
				bo.setModifiedBy(stform.getUserId());
				
				if(to.getId()!=null && to.getId()!=0)
				{
					bo.setId(to.getId());
				}
				
				bo.setIsActive(true);
				bo.setSemister(Integer.parseInt(stform.getScheme()));
				
				studentListBo.add(bo);
			}
			
		}
		
		return studentListBo;
	}
	
	
}
