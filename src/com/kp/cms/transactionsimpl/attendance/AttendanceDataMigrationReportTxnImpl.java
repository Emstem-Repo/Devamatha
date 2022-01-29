package com.kp.cms.transactionsimpl.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.AttnBiodataPuc;
import com.kp.cms.bo.admin.AttnMarksUpload;
import com.kp.cms.bo.admin.AttnPucSubjects;
import com.kp.cms.bo.admin.InternalMarkUpload;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.attendance.AttendanceDataMigrationForm;
import com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AttendanceDataMigrationReportTxnImpl implements IAttendanceDataMigrationReportTxn {
	private static final Log log = LogFactory
	.getLog(AttendanceDataMigrationReportTxnImpl.class);
	private static volatile AttendanceDataMigrationReportTxnImpl txnImpl = null;
	public static AttendanceDataMigrationReportTxnImpl getInstance(){
		if(txnImpl == null){
			txnImpl = new AttendanceDataMigrationReportTxnImpl();
			return txnImpl;
		}
		return txnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn#getCourseMap()
	 */
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.attandance.IAttendanceDataMigrationReportTxn#getAttnBioDataDetails(java.lang.StringBuffer)
	 */
	@Override
	public List<AttnBiodataPuc> getAttnBioDataDetails(StringBuffer query) throws Exception {
		Session session = null;
		List<AttnBiodataPuc> biodata;
		try{
			session = HibernateUtil.getSession();
			Query qry = session.createQuery(query.toString());
			 biodata =qry.list();
			 
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return biodata;
	}
	@Override
	public List<AttnMarksUpload> getAttendanceMarks(
			AttendanceDataMigrationForm attnDataMigrationForm) throws Exception {
		log.info("entering into getAttendanceMarks in AttendanceDataMigrationReportTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from AttnMarksUpload attn where attn.academicYear="+attnDataMigrationForm.getAcademicYear()+ "and attn.classes='"+attnDataMigrationForm.getClassName()+"' and attn.testIdent='"+attnDataMigrationForm.getTestIdent()+"'";
			 Query query = session.createQuery(queryString);
			 List<AttnMarksUpload> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAttendanceMarks in AttendanceDataMigrationReportTxnImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAttendanceMarks data..." , e);
			 throw e;
		 }
	}
	@Override
	public String getSecondLang(String regNo,String year) throws Exception {
		log.info("entering into getSecondLang in AttendanceDataMigrationReportTxnImpl class");
		Session session = null;
		String secondLang="";
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="select bio.secndLang from AttnBiodataPuc bio where bio.regNo='"+regNo+"' and bio.academicYear='"+year+"'";
			 Query query = session.createQuery(queryString);
			 Object sLang = query.uniqueResult();
			 if(sLang!=null)
				 secondLang=sLang.toString();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getSecondLang in AttendanceDataMigrationReportTxnImpl class");
			 return secondLang;
		 } catch (Exception e) {
			 log.error("Error during getSecondLang data..." , e);
			 throw e;
		 }
	}
	@Override
	public AttnPucSubjects getAttnSubject(String className) throws Exception {
		log.info("entering into getAttnSubject in AttendanceDataMigrationReportTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from AttnPucSubjects sub where sub.classes='"+className+"'";
			 Query query = session.createQuery(queryString);
			 List<AttnPucSubjects> attnSubjects = query.list();
			 session.flush();
			 Iterator<AttnPucSubjects> itr=attnSubjects.iterator();
			 AttnPucSubjects subjects=new AttnPucSubjects();
			 while(itr.hasNext()){
				 subjects=itr.next();
				 break;
			 }
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getAttnSubject in AttendanceDataMigrationReportTxnImpl class");
			 return subjects;
		 } catch (Exception e) {
			 log.error("Error during getAttnSubject data..." , e);
			 throw e;
		 }
	}
	@Override
	public Map<String, String> getClasses(String mode) throws Exception {
		Session session=null;
		Map<String,String> classMap=new HashMap<String, String>();
		String quer="";
		try{
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("AttendanceMarks")){
			quer="select distinct m.classes from AttnMarksUpload m order by m.classes asc";
			}
			if(mode.equalsIgnoreCase("InternalMarks")){
				quer="select distinct m.classes from InternalMarkUpload m order by m.classes asc";
				}
			Query query=session.createQuery(quer);
			List<String> classes=query.list();
			Iterator<String> itr=classes.iterator();
			while(itr.hasNext()){
				String className=itr.next();
				classMap.put(className, className);
			}
		}catch(Exception e){
			log.error("Error during getClasses data..." , e);
			 throw e;
		}
		classMap=(HashMap<String,String>)CommonUtil.sortMapByKey(classMap);
		return classMap;
	}
	@Override
	public Map<String, String> getTestIdent(String mode) throws Exception {
		Session session=null;
		Map<String,String> testMap=new HashMap<String, String>();
		String quer="";
		try{
			session=HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("AttendanceMarks")){
			quer="select distinct m.testIdent from AttnMarksUpload m order by m.testIdent";
			}if(mode.equalsIgnoreCase("InternalMarks")){
				quer="select distinct m.testIdent from InternalMarkUpload m order by m.testIdent";
			}
			Query query=session.createQuery(quer);
			List<String> classes=query.list();
			Iterator<String> itr=classes.iterator();
			while(itr.hasNext()){
				String test=itr.next();
				testMap.put(test, test);
			}
			
		}catch(Exception e){
			log.error("Error during getTestIdent data..." , e);
			 throw e;
		}
		return testMap;
	}
	@Override
	public List<InternalMarkUpload> getInternalMarks(AttendanceDataMigrationForm attnDataMigrationForm) throws Exception {
		log.info("entering into getAttendanceMarks in AttendanceDataMigrationReportTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from InternalMarkUpload inter where inter.academicYear="+attnDataMigrationForm.getAcademicYear()+ "and inter.classes='"+attnDataMigrationForm.getClassName()+"' and inter.testIdent='"+attnDataMigrationForm.getTestIdent()+"'";
			 Query query = session.createQuery(queryString);
			 List<InternalMarkUpload> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getInternalMarks in AttendanceDataMigrationReportTxnImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getAttendanceMarks data..." , e);
			 throw e;
		 }
	}
}
