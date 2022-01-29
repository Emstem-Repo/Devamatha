package com.kp.cms.utilities.jms;

import java.util.*;
import java.util.Map.Entry;
import java.sql.Date;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.Attendance;
import com.kp.cms.bo.admin.AttendanceStudent;
import com.kp.cms.bo.admin.Period;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admin.StudentCourseAllotment;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.HibernateUtil;

public class Core3 {


	/** 
	 * @param args
	 */
	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		
		List<StudentTO> l=null;
		List<List<StudentTO>> l1=new LinkedList<List<StudentTO>>();
		
		Session session = null;
		List attList = null;
		List pList = null;
		List sList = null;
		Map m=new HashMap<Integer,StudentTO>();
		//String pta="Period4";
		List pnList = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session.createSQLQuery("SELECT period.period_name, student.roll_no,student.id,attendance_student.is_cocurricular_leave,attendance_student.is_on_leave,attendance_student.is_present"+
							" FROM    (   (   (   (   SHJULY7.attendance_period attendance_period  INNER JOIN  SHJULY7.period period   ON (attendance_period.period_id = period.id))"+
							" INNER JOIN SHJULY7.attendance attendance  ON (attendance_period.attendance_id = attendance.id))"+
							" INNER JOIN  SHJULY7.attendance_student attendance_student  ON (attendance_student.attendance_id = attendance.id))"+
							" INNER JOIN  SHJULY7.student student  ON (attendance_student.student_id = student.id))"+
							" INNER JOIN  SHJULY7.attendance_class attendance_class  ON (attendance_class.attendance_id = attendance.id)"+
							" where attendance_class.class_schemewise_id in (309) and attendance.attendance_date='2015-07-21'"+
							" group by  attendance_student.student_id,attendance_period.period_id order by period.period_name,student.roll_no");
			attList = studentQuery.list();
			
			studentQuery=session.createQuery(" select distinct(p.periodName) from Period p where p.isActive=1 and p.classSchemewise.id in (309,311) order by p.periodName");
			pList = studentQuery.list();
			
			studentQuery=session.createQuery(" select distinct(p.periodName) from Period p where p.isActive=1 and p.id in (809,815) order by p.periodName");
			pnList = studentQuery.list();
			
			studentQuery=session.createQuery("from Student s where s.classSchemewise.id in (309,311) and s.isAdmitted=1 and s.isActive=1 and s.admAppln.appliedYear=2015 " +
					" and s.id not in( select s.student.id from ExamStudentDetentionRejoinDetails s where (s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null)) ");
			sList = studentQuery.list();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		
		
		
		
		List<String> li=null;
		List li1=new LinkedList();
		Iterator<String> i = pList.iterator();
		while (i.hasNext()) {
			
			Iterator<Student> is = sList.iterator();
			String p =i.next();
			li=new LinkedList<String>();
		
			while (is.hasNext()) {
				Student s=is.next();
				StudentTO st=new StudentTO();
				st.setId(s.getId());
				st.setRollNo(s.getRollNo());
				st.setIsTaken(false);
				if(pnList.contains(p))
				st.setIsCurrent(true);
				m.put(s.getId(), st);
			}
			
			
			System.out.println("----------------------");
			Iterator<Object[]> i1 = attList.iterator();
			while (i1.hasNext()) {

			Object[] object =i1.next();
			String s=object[0].toString();	
			//System.out.println(p.getPeriodName()+"==="+s);
			if(p.equalsIgnoreCase(s)){
			li.add(object[0].toString()+"="+object[1].toString());	
			
			if(m.containsKey(Integer.parseInt(object[2].toString()))){
				StudentTO sto= (StudentTO)m.get(Integer.parseInt(object[2].toString()));
				sto.setIsTaken(true);
				sto.setCoCurricularLeavePresent(new Boolean(object[3].toString()));
				sto.setStudentLeave(new Boolean(object[4].toString()));
				if(new Boolean(object[3].toString()) || new Boolean(object[4].toString()) || new Boolean(object[5].toString()))
				sto.setTempChecked(true);
				sto.setIsCurrent(false);
				m.put(sto.getId(), sto);
			}
			
			}
			 
		}
		
		
		l=new LinkedList<StudentTO>();
		Set<Entry<Integer, StudentTO>> studentCourseAllotmentSet= m.entrySet();
		Iterator<Entry<Integer, StudentTO>> iset=studentCourseAllotmentSet.iterator();
		while(iset.hasNext())
		{
		Entry<Integer, StudentTO> e=iset.next();
		StudentTO sto=e.getValue();
		l.add(sto);
		System.out.println(p+"=====pname========"+sto.getRollNo()+"====rno====="+sto.getIsTaken()+"======take======"+sto.isTempChecked()+"====cur===="+""+sto.getIsCurrent());
		}
		li1.add(li);
		l1.add(l);
		}
	
		//System.out.println(li1);
		//System.out.println(l1);
		System.out.println(Calendar.getInstance().getTime());
		
		/*String s="a";
		String s1="b";
		s=s+s1;
		System.out.println(s);
		char c[]={'a','b','c','d','e'};
		String s3=new String(c,1,3);
		System.out.println(s3.substring(0,1));*/
		
}
	
}


