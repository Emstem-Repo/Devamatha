package com.kp.cms.transactionsimpl.attendance;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AttendanceCondonation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceCondonationForm;
import com.kp.cms.transactions.attandance.IAttendanceCondonationTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceCondonationTransactionImpl implements IAttendanceCondonationTransaction {
	
	public static volatile AttendanceCondonationTransactionImpl self = null;

	public static AttendanceCondonationTransactionImpl getInstance() {
		if(self==null){
			
			self = new AttendanceCondonationTransactionImpl();
		}
		
		return self;
	}

	@Override
	public List getCurrentClassStudents(AttendanceCondonationForm stform,int mode) throws ApplicationException {
          Session session = null;
          List studentlist=null;
          List studentlist2=null;
          try {
        	  
     
        	  
        	  session = HibernateUtil.getSession();
        	  //if(mode==1){
        	  Query query = session.createQuery("from Student st where st.isAdmitted=1"+
        			                            "and st.id not in(select at.student.id from AttendanceCondonation at where at.classes.id=:id and at.isActive=1)"+
        			                            "and st.classSchemewise.classes.id=:id and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1");
			  query.setInteger("id",Integer.parseInt(stform.getClassId()));
			  studentlist = query.list();
        	  //}else{
        	  Query query2 = session.createQuery(" select st from Student st "+
        			                            " join st.studentPreviousClassesHistory classHis" +
        			                            " where classHis.classes.id=:id" +
        			                            " and st.id not in(select at.student.id from AttendanceCondonation at where at.classes.id=:id and at.isActive=1)"+
        			                            " and st.isAdmitted=1 and st.isActive = 1 and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0  and st.admAppln.isApproved=1");	 
        	  query2.setInteger("id",Integer.parseInt(stform.getClassId()));
        	  studentlist2 = query2.list();
        	  
        	  studentlist.addAll(studentlist2);
			  
        	  //}
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		
		return studentlist;
	}

	@Override
	public List getEditStudent(AttendanceCondonationForm stform) throws ApplicationException {
		Session session = null;
        List studentlist=null;
        
        try {
      	  
      	  session = HibernateUtil.getSession();
      	 
      	  Query query = session.createQuery("from AttendanceCondonation a where a.classes.id=:id and a.isActive=1");	 
      	  query.setInteger("id",Integer.parseInt(stform.getClassId()));
      	  studentlist = query.list();
			  
        }catch (Exception e) {
          throw new ApplicationException(e);
		}
		
		return studentlist;
        
	}

	@Override
	public boolean SaveStudents(List studentBo) throws ApplicationException {
		Session session = null;
        boolean isSaved = false;
        try {
        	session = HibernateUtil.getSession();
        	Transaction txn = session.beginTransaction();
        	txn.begin();
        	
        	Iterator<AttendanceCondonation> itr = studentBo.iterator();
        	
        	while(itr.hasNext()){
        		AttendanceCondonation bo = (AttendanceCondonation)itr.next();
        		session.saveOrUpdate(bo);
        		
        	}
        	txn.commit();
        	isSaved = false;
		} catch (Exception e) {
			
			throw new ApplicationException(e);
		}
		return isSaved;
	}


	public List<Object[]> getstudentattendance(Student bo,AttendanceCondonationForm stform,int mode) throws ApplicationException {
	    
    	Session session = null;
		List objlist=null;
		try{
			
		
		session = HibernateUtil.getSession();
		Query query;
		if(mode==1){
		      query = session.createSQLQuery(" select attendance.id from attendance "+
                                             " inner join attendance_student on attendance_student.attendance_id = attendance.id "+
                                             " inner join attendance_class on attendance_class.attendance_id = attendance.id "+
                                             " inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id "+
                                             " inner join classes ON class_schemewise.class_id = classes.id "+
                                             " inner join attendance_period on attendance_period.attendance_id = attendance.id "+
                                             " inner join period ON attendance_period.period_id = period.id "+
                                             " and period.class_schemewise_id = class_schemewise.id "+ 
                                             " inner join student on attendance_student.student_id=student.id and student.class_schemewise_id= class_schemewise.id "+
                                             " inner join adm_appln on student.adm_appln_id=adm_appln.id "+
                                             " inner join personal_data on adm_appln. personal_data_id= personal_data.id "+
                                             " inner join course on classes.course_id = course.id"+
                                             " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+ 
                                             " where attendance.is_canceled=0 "+
                                             " and curriculum_scheme_duration.academic_year="+Integer.parseInt(stform.getAcademicYear())+
                                             " and classes.id="+Integer.parseInt(stform.getClassId())+" and student.id="+bo.getId()+
                                             " group by attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date,period.session "+
		                                     " having if(sum(attendance.hours_held)!=sum(if(attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1,attendance.hours_held,0)),1,0)=1");
		}else{
			
			 query =  session.createSQLQuery("select EXAM_student_previous_class_details.id  from EXAM_student_previous_class_details "+
                                             " inner join student on EXAM_student_previous_class_details.student_id= student.id "+
                                             " inner join attendance_student on attendance_student.student_id= student.id "+
                                             " inner join attendance on attendance_student.attendance_id = attendance.id "+
                                             " inner join attendance_class on attendance_class.attendance_id = attendance.id "+
                                             " inner join class_schemewise ON attendance_class.class_schemewise_id = class_schemewise.id "+
                                             " inner join classes ON class_schemewise.class_id = classes.id and EXAM_student_previous_class_details.class_id = classes.id "+
                                             " inner join attendance_period on attendance_period.attendance_id = attendance.id "+
                                             " inner join period ON attendance_period.period_id = period.id "+
                                             " and period.class_schemewise_id = class_schemewise.id "+
                                             " inner join adm_appln on student.adm_appln_id=adm_appln.id "+
                                             "  inner join personal_data on adm_appln. personal_data_id= personal_data.id "+
                                             " inner join course on classes.course_id = course.id "+
                                             " inner join curriculum_scheme_duration ON class_schemewise.curriculum_scheme_duration_id = curriculum_scheme_duration.id "+
                                             " where attendance.is_canceled=0 "+
                                             " and curriculum_scheme_duration.academic_year="+Integer.parseInt(stform.getAcademicYear())+
                                             " and classes.id="+Integer.parseInt(stform.getClassId())+" and student.id="+bo.getId()+
                                             " group by attendance_student.student_id,attendance_class.class_schemewise_id,attendance.attendance_date,period.session "+
                                             " having if(sum(attendance.hours_held)!=sum(if(attendance_student.is_present=1 || attendance_student.is_cocurricular_leave=1,attendance.hours_held,0)),1,0)=1");

		}
		objlist=query.list();
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return objlist;
	}

	
	@Override
	
	public List<Object[]> gettotalattendance(AttendanceCondonationForm stform) throws ApplicationException {
		Session session = null;
		List objlist=null;
		try{
			
		
		session = HibernateUtil.getSession();
	
		Query query = session.createSQLQuery(" select * ,if(attendance_class.class_schemewise_id is not null,1,0)as cnt from attendance "+
                                             " inner join attendance_class on attendance_class.attendance_id = attendance.id "+
                                             " inner join class_schemewise on attendance_class.class_schemewise_id=class_schemewise.id "+
                                             " inner join classes on class_schemewise.class_id=classes.id "+
                                             " where classes.id="+Integer.parseInt(stform.getClassId())+
                                             " and attendance.is_canceled=0 "+ 
                                             " group by classes.id,attendance_date");
		
		
		objlist=query.list();
		
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return objlist;
	}

	
	

	


	
	

}
