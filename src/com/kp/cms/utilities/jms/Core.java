package com.kp.cms.utilities.jms;

import java.util.*;
import java.sql.Date;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.utilities.HibernateUtil;

public class Core {


	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {/*
		// TODO Auto-generated method stub
		List<String> l=new LinkedList<String>();
		List<CoreTo> l1=new LinkedList<CoreTo>();
		
		Session session = null;
		List<Object[]> attList = null;
		List<Object[]> attDates = null;
		try {
			//SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			//session = sessionFactory.openSession();
			session = HibernateUtil.getSession();
			Query studentQuery = session.createQuery("select attendanceStudents.attendance.attendanceDate,p.periodName,p.session, attendanceStudents.isPresent,attendanceStudents.isOnLeave,attendanceStudents.isCoCurricularLeave  from Student student  inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac  join attendanceStudents.attendance.attendancePeriods ap  join ap.period p where attendanceStudents.attendance.isMonthlyAttendance = 0    and   attendanceStudents.attendance.isActivityAttendance = 0    and  attendanceStudents.attendance.isCanceled = 0 and student.id = 1 and ac.classSchemewise.classes.id=49 order by attendanceStudents.attendance.attendanceDate,p.periodName  ");
			attList = studentQuery.list();
			studentQuery = session.createQuery("select distinct attendanceStudents.attendance.attendanceDate,attendanceStudents.attendance.attendanceDate from Student student  inner join student.attendanceStudents attendanceStudents join attendanceStudents.attendance.attendanceClasses ac  join attendanceStudents.attendance.attendancePeriods ap  join ap.period p where attendanceStudents.attendance.isMonthlyAttendance = 0    and   attendanceStudents.attendance.isActivityAttendance = 0    and  attendanceStudents.attendance.isCanceled = 0 and student.id = 1 and ac.classSchemewise.classes.id=49 order by attendanceStudents.attendance.attendanceDate ");
			attDates = studentQuery.list();

		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				////session.close();
			}
		}
		
		Iterator<Object[]> subjectWiseAttendanceSummaryIterator = attDates.iterator();
		while (subjectWiseAttendanceSummaryIterator.hasNext()) {

			Object[] object =(Object[]) subjectWiseAttendanceSummaryIterator.next();
		String s=object[0].toString();
		l.add(s);
		}
		
		Iterator subjectWiseAttendanceSummaryIterator1 = attList.iterator();
		while (subjectWiseAttendanceSummaryIterator1.hasNext()) {

				Object[] object = (Object[]) subjectWiseAttendanceSummaryIterator1.next();
				
				CoreTo to1=new CoreTo();
				to1.setAmpm(object[2].toString());
				to1.setColeave((Boolean)object[5]);
				to1.setOnleave((Boolean)object[4]);
				to1.setAtt((Boolean)object[3]);
				to1.setDt(object[0].toString());
				
				l1.add(to1);
		}
		
		
		l.add("2014-08-12");
		l.add("2014-08-13");
		l.add("2014-08-14");
		l.add("2014-08-15");
		
		
		
		CoreTo to1=new CoreTo();
		to1.setAmpm("am");
		to1.setColeave(false);
		to1.setOnleave(false);
		to1.setAtt(true);
		to1.setDt("2014-08-12");
		CoreTo to11=new CoreTo();
		to11.setAmpm("am");
		to11.setColeave(false);
		to11.setOnleave(false);
		to11.setAtt(true);
		to11.setDt("2014-08-12");
		CoreTo to111=new CoreTo();
		to111.setAmpm("pm");
		to111.setColeave(false);
		to111.setOnleave(false);
		to111.setAtt(true);
		to111.setDt("2014-08-12");
		
		CoreTo to2=new CoreTo();
		to2.setAmpm("am");
		to2.setColeave(false);
		to2.setOnleave(false);
		to2.setAtt(true);
		to2.setDt("2014-08-13");
		CoreTo to22=new CoreTo();
		to22.setAmpm("am");
		to22.setColeave(false);
		to22.setOnleave(false);
		to22.setAtt(true);
		to22.setDt("2014-08-13");
		CoreTo to222=new CoreTo();
		to222.setAmpm("pm");
		to222.setColeave(false);
		to222.setOnleave(false);
		to222.setAtt(false);
		to222.setDt("2014-08-13");
		CoreTo to2222=new CoreTo();
		to2222.setAmpm("pm");
		to2222.setColeave(false);
		to2222.setOnleave(false);
		to2222.setAtt(false);
		to2222.setDt("2014-08-13");
		
		
		CoreTo to3=new CoreTo();
		to3.setAmpm("am");
		to3.setColeave(true);
		to3.setOnleave(false);
		to3.setAtt(false);
		to3.setDt("2014-08-14");
		CoreTo to33=new CoreTo();
		to33.setAmpm("am");
		to33.setColeave(false);
		to33.setOnleave(false);
		to33.setAtt(false);
		to33.setDt("2014-08-14");
		CoreTo to333=new CoreTo();
		to333.setAmpm("pm");
		to333.setColeave(false);
		to333.setOnleave(true);
		to333.setAtt(false);
		to333.setDt("2014-08-14");
		CoreTo to3333=new CoreTo();
		to3333.setAmpm("am");
		to3333.setColeave(false);
		to3333.setOnleave(false);
		to3333.setAtt(false);
		to3333.setDt("2014-08-14");
		
		CoreTo to4=new CoreTo();
		to4.setAmpm("am");
		to4.setColeave(false);
		to4.setOnleave(false);
		to4.setAtt(true);
		to4.setDt("2014-08-15");
		CoreTo to44=new CoreTo();
		to44.setAmpm("am");
		to44.setColeave(false);
		to44.setOnleave(true);
		to44.setAtt(false);
		to44.setDt("2014-08-15");
		
		
		l1.add(to1);
		l1.add(to11);
		l1.add(to111);
		l1.add(to2);
		l1.add(to22);
		l1.add(to222);
		l1.add(to2222);
		l1.add(to3);
		l1.add(to33);
		l1.add(to333);
		l1.add(to3333);
		l1.add(to4);
		l1.add(to44);
		
		int amsestot=0;
		int pmsestot=0;
		int amsesheld=0;
		int pmsesheld=0;
		int amattabs=0;
		int pmattabs=0;
		int amattabsheld=0;
		int pmattabsheld=0;
		
		int amattpr=0;
		int pmattpr=0;
		List<CoreToAm> lam=new LinkedList<CoreToAm>();
		List<CoreToPm> lpm=new LinkedList<CoreToPm>();
		
		String periodsam="";
		String leavesam="";
		String periodspm="";
		String leavespm="";
		
		Iterator<String> i1=l.iterator();
		while(i1.hasNext()){
			String d=i1.next();
			amsesheld=0;
			pmsesheld=0;
			amattabsheld=0;
			pmattabsheld=0;
			
			CoreToAm toam=new CoreToAm();
			toam.setDt(d);
			
			List<String> pnames=new LinkedList<String>();
			List<String> coLeaves=new LinkedList<String>();
			
			CoreToPm topm=new CoreToPm();
			topm.setDt(d);
			
			List<String> pnamesp=new LinkedList<String>();
			List<String> coLeavesp=new LinkedList<String>();
			
		Iterator<CoreTo> i=l1.iterator();
		while(i.hasNext()){
			CoreTo to6=i.next();
			
			if(d.equalsIgnoreCase(to6.getDt())){
				
				if(to6.getAmpm().equalsIgnoreCase("am")){
					amsesheld=1;
					
					if(to6.isAtt()){
						
					}else if(to6.isColeave()||to6.isOnleave()){
						if(to6.isColeave()){
							String s="coleave";
							coLeaves.add(s);
							periodsam="p,";
							leavesam="coleave,";
						}
						if(to6.isOnleave()){
							String s="onleave";
							coLeaves.add(s);
							periodsam="p,";
							leavesam="onleave,";
						}
						
					}else{
						amattabsheld=1;
						System.out.println("-----absent am----- ");
						System.out.println(to6.getDt());
						String s="absent";
						pnames.add(s);
						periodsam="p,";
						leavesam="-,";
					}
					
					
				}else{
					pmsesheld=1;
					
						if(to6.isAtt()){
							
						}else if(to6.isColeave()||to6.isOnleave()){
							
							if(to6.isColeave()){
								String s="coleave";
								coLeavesp.add(s);
								periodspm="p,";
								leavespm="coleave,";
							
							}
							if(to6.isOnleave()){
								String s="onleave";
								coLeavesp.add(s);
								periodspm="p,";
								leavespm="coleave,";
							
							}
						}else{
							pmattabsheld=1;
							System.out.println("-----absent pm----- ");
							System.out.println(to6.getDt());
							String s="absent";
							pnamesp.add(s);
							periodspm="p,";
							leavespm="-,";
						}
						
				}
				
				if(pnames.size()!=0)
				toam.setPnames(pnames);
				if(coLeaves.size()!=0)
				toam.setCoLeaves(coLeaves);
				if(pnamesp.size()!=0)
				topm.setPnames(pnamesp);
				if(coLeavesp.size()!=0)
				topm.setCoLeaves(coLeavesp);
				if(!periodsam.equalsIgnoreCase(""))
				toam.setPeriods(periodsam);
				if(!periodspm.equalsIgnoreCase(""))
				topm.setPeriods(periodspm);
				if(!leavesam.equalsIgnoreCase(""))
				toam.setLeaves(leavesam);
				if(!periodspm.equalsIgnoreCase(""))
				topm.setLeaves(leavespm);
			}
		}
		
		amattabs=amattabs+amattabsheld;
		pmattabs=pmattabs+pmattabsheld;
		amsestot=amsestot+amsesheld;
		pmsestot=pmsestot+pmsesheld;
		if((toam.getPnames()!=null && toam.getPnames().size()!=0 )|| (toam.getCoLeaves()!=null && toam.getCoLeaves().size()!=0))
		lam.add(toam);
		if((topm.getPnames()!=null && topm.getPnames().size()!=0 )|| (topm.getCoLeaves()!=null && topm.getCoLeaves().size()!=0))
		lpm.add(topm);
		
	}
		amattpr=amsestot-amattabs;
		pmattpr=pmsestot-pmattabs;
		float amper=((float)amattpr/amsestot)*100;
		float pmper=((float)pmattpr/pmsestot)*100;
		float totper=(amper+pmper)/2;
		float totper1=((float)(amattpr+pmattpr)/(amsestot+pmsestot))*100;
	System.out.println(amsestot+"-----tot----- "+pmsestot);
	System.out.println(amattpr+"-----pr----- "+pmattpr);
	System.out.println(amattabs+"----abs------ "+pmattabs);
	System.out.println((amper)+"----per------ "+(pmper));
	System.out.println((totper)+"----totper------ "+(totper1));
	
	
	Iterator<CoreToAm> iam=lam.iterator();
	while(iam.hasNext()){
		CoreToAm cam=iam.next();
		System.out.println(cam.getCoLeaves()+"----am-"+cam.getDt()+"----- "+cam.getPnames());
		System.out.println(cam.getPeriods()+"          ----per  leav-        "+cam.getLeaves());
	}
	Iterator<CoreToPm> ipm=lpm.iterator();
	while(ipm.hasNext()){
		CoreToPm cpm=ipm.next();
		System.out.println(cpm.getCoLeaves()+"----pm-"+cpm.getDt()+"----- "+cpm.getPnames());
		System.out.println(cpm.getPeriods()+"         ----per  leav-             "+cpm.getLeaves());
	}
*/}
	
}







class CoreTo {
	private String ampm;
	private boolean coleave;
	private String dt;
	private boolean onleave;
	private boolean att;
	public String getAmpm() {
		return ampm;
	}
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}
	public boolean isColeave() {
		return coleave;
	}
	public void setColeave(boolean coleave) {
		this.coleave = coleave;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public boolean isOnleave() {
		return onleave;
	}
	public void setOnleave(boolean onleave) {
		this.onleave = onleave;
	}
	public boolean isAtt() {
		return att;
	}
	public void setAtt(boolean att) {
		this.att = att;
	}
	
	
	
}








class CoreToAm {
	private String ampm;
	private boolean coleave;
	private String dt;
	private boolean onleave;
	private boolean att;
	private List<String> pnames;
	private List<String> coLeaves;
	private String periods;
	private String leaves;
	
	
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getLeaves() {
		return leaves;
	}
	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}
	public String getAmpm() {
		return ampm;
	}
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}
	public boolean isColeave() {
		return coleave;
	}
	public void setColeave(boolean coleave) {
		this.coleave = coleave;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public boolean isOnleave() {
		return onleave;
	}
	public void setOnleave(boolean onleave) {
		this.onleave = onleave;
	}
	public boolean isAtt() {
		return att;
	}
	public void setAtt(boolean att) {
		this.att = att;
	}
	public List<String> getPnames() {
		return pnames;
	}
	public void setPnames(List<String> pnames) {
		this.pnames = pnames;
	}
	public List<String> getCoLeaves() {
		return coLeaves;
	}
	public void setCoLeaves(List<String> coLeaves) {
		this.coLeaves = coLeaves;
	}
	
	
	
}








class CoreToPm {
	private String ampm;
	private boolean coleave;
	private String dt;
	private boolean onleave;
	private boolean att;
	private List<String> pnames;
	private List<String> coLeaves;
	private String periods;
	private String leaves;
	
	
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getLeaves() {
		return leaves;
	}
	public void setLeaves(String leaves) {
		this.leaves = leaves;
	}
	public String getAmpm() {
		return ampm;
	}
	public void setAmpm(String ampm) {
		this.ampm = ampm;
	}
	public boolean isColeave() {
		return coleave;
	}
	public void setColeave(boolean coleave) {
		this.coleave = coleave;
	}
	public String getDt() {
		return dt;
	}
	public void setDt(String dt) {
		this.dt = dt;
	}
	public boolean isOnleave() {
		return onleave;
	}
	public void setOnleave(boolean onleave) {
		this.onleave = onleave;
	}
	public boolean isAtt() {
		return att;
	}
	public void setAtt(boolean att) {
		this.att = att;
	}
	public List<String> getPnames() {
		return pnames;
	}
	public void setPnames(List<String> pnames) {
		this.pnames = pnames;
	}
	public List<String> getCoLeaves() {
		return coLeaves;
	}
	public void setCoLeaves(List<String> coLeaves) {
		this.coLeaves = coLeaves;
	}
	
	
	
}

