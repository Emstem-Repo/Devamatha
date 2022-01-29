package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import com.kp.cms.bo.admission.PromoteMarks;
import com.kp.cms.bo.admission.PromoteSubjects;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.transactions.admission.IPromoteMarksUploadTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class PromoteMarksUploadTxnImpl implements IPromoteMarksUploadTransaction {
	private static final Log log = LogFactory
	.getLog(PromoteMarksUploadTxnImpl.class);
	private static volatile PromoteMarksUploadTxnImpl marksUploadTxnImpl = null;
	public static PromoteMarksUploadTxnImpl getInstance(){
		if(marksUploadTxnImpl == null){
			marksUploadTxnImpl = new PromoteMarksUploadTxnImpl();
			return marksUploadTxnImpl;
		}
		return marksUploadTxnImpl;
	}
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.IPromoteMarksUploadTransaction#uploadPromoteMarks(java.util.List)
	 */
	@Override
	public boolean uploadPromoteMarks(List<PromoteMarks> promoteMarks,List<String> regNos,PromoteMarksUploadForm marksUploadForm)throws Exception {
		boolean isAdded= false;
		Session session= null;
		Transaction transaction = null;
		PromoteMarks marks =null;
		List<String> dupRegNos = new ArrayList<String>();
		try{
			session=HibernateUtil.getSession();
			transaction =session.beginTransaction();
			transaction.begin();
			if(promoteMarks!=null && !promoteMarks.isEmpty()){
				Iterator<PromoteMarks> iterator = promoteMarks.iterator();
				while (iterator.hasNext()) {
					PromoteMarks promoteMarks2 = (PromoteMarks) iterator.next();
					/*String str = "from PromoteMarks proMarks where proMarks.academicYear="+promoteMarks2.getAcademicYear()+"and proMarks.regNo='"+promoteMarks2.getRegNo()+"'";
					Query query = session.createQuery(str);
					marks = (PromoteMarks) query.uniqueResult();
					if(marks == null){
						session.save(promoteMarks2);
					}*/
					if(regNos!=null){
					    if(!regNos.contains(promoteMarks2.getRegNo())){
						    session.save(promoteMarks2);
					    }else
					    	dupRegNos.add(promoteMarks2.getRegNo());
					}else
						session.save(promoteMarks2);
				}
				transaction.commit();
				marksUploadForm.setDupRegNos(dupRegNos);
				session.flush();
				session.close();
				isAdded = true;
			}
			
		}catch (ConstraintViolationException e) {
			transaction.rollback();
			throw new BusinessException(e);
		} catch (Exception e) {
			transaction.rollback();
			throw new ApplicationException(e);
		}
		return isAdded;
	}
	@Override
	public List<PromoteMarks> getPromoteMarks(
			PromoteMarksUploadForm marksUploadForm) throws Exception {
		log.info("entering into getPromoteMarks in PromoteMarksUploadTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from PromoteMarks mark where mark.academicYear="+marksUploadForm.getAcademicYear()+" and mark.classCode='"+marksUploadForm.getCourseName()+"'";
			 Query query = session.createQuery(queryString);
			 List<PromoteMarks> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getPromoteMarks in PromoteMarksUploadTxnImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getPromoteMarks data..." , e);
			 throw e;
		 }
	}
	@Override
	public PromoteSubjects getPromoteSubjects(String course) throws Exception {
		log.info("entering into getPromoteSubjects in PromoteMarksUploadTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from PromoteSubjects sub where sub.classCode='"+course+"'";
			 Query query = session.createQuery(queryString);
			 List<PromoteSubjects> promoteSubjects = query.list();
			 session.flush();
			 Iterator<PromoteSubjects> itr=promoteSubjects.iterator();
			 PromoteSubjects subjects=new PromoteSubjects();
			 while(itr.hasNext()){
				 subjects=itr.next();
				 break;
			 }
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getPromoteSubjects in PromoteMarksUploadTxnImpl class");
			 return subjects;
		 } catch (Exception e) {
			 log.error("Error during getPromoteSubjects data..." , e);
			 throw e;
		 }
	}
	@Override
	public String getSecondLang(String regNo,Integer academicYear) throws Exception {
		log.info("entering into getSecondLang in PromoteMarksUploadTxnImpl class");
		Session session = null;
		String secondLang="";
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="select bio.secndLang from PromoteBioData bio where bio.regNo='"+regNo+"' and bio.academicYear="+academicYear;
			 Query query = session.createQuery(queryString);
			 Object obj = query.uniqueResult();
			 if(obj!=null)
				 secondLang=obj.toString();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getSecondLang in PromoteMarksUploadTxnImpl class");
			 return secondLang;
		 } catch (Exception e) {
			 log.error("Error during getSecondLang data..." , e);
			 throw e;
		 }
	}
	@Override
	public List<PromoteSupliMarks> getPromoteSupliMarks(
			PromoteMarksUploadForm marksUploadForm) throws Exception {
		log.info("entering into getPromoteSupliMarks in PromoteMarksUploadTxnImpl class");
		Session session = null;
		try {
     		 session = HibernateUtil.getSession();
			 String queryString="from PromoteSupliMarks mark where mark.academicYear="+marksUploadForm.getAcademicYear()+" and mark.classCode='"+marksUploadForm.getCourseName()+"'";
			 Query query = session.createQuery(queryString);
			 List<PromoteSupliMarks> list = query.list();
			 session.flush();
			 //session.close();
			 //sessionFactory.close();
			 log.info("leaving from getPromoteSupliMarks in PromoteMarksUploadTxnImpl class");
			 return list;
		 } catch (Exception e) {
			 log.error("Error during getPromoteSupliMarks data..." , e);
			 throw e;
		 }
	}
	@Override
	public Map<String,String> getCourses(String mode) throws Exception {
		Session session = null;
		List<String> courses = null;
		String quer ="";
		Map<String,String> courseMap =new HashMap<String, String>();
		try{
			session = HibernateUtil.getSession();
			if(mode.equalsIgnoreCase("promoteMarks")){
			    quer= "select distinct pmrks.classCode from PromoteMarks pmrks";
			}else if(mode.equalsIgnoreCase("supliMarks")){
				quer = "select distinct smrks.classCode from PromoteSupliMarks smrks order by smrks.classCode";
			}else if(mode.equalsIgnoreCase("bioData")){
				quer = "select distinct bio.classCode from PromoteBioData bio";
			}else if(mode.equalsIgnoreCase("AttnBioData")){
				quer = "select distinct attenbio.classes from AttnBiodataPuc attenbio";
			}else if(mode.equalsIgnoreCase("attnPucSub")){
				quer = "select distinct attenbio.classes from AttnPucCetMarks attenbio";
			}else if(mode.equalsIgnoreCase("AdmBiodata")){
				quer = "select distinct admBio.classes from AdmBioDataCJC admBio";
			}else if(mode.equalsIgnoreCase("AdmMeritList")){
				quer = "select distinct admMerit.firstPreference from AdmMeritList admMerit";
			}else if(mode.equalsIgnoreCase("tcDetails")){
				quer = "select distinct admTcDet.classes from AdmissionTcDetails admTcDet";
			}
			Query query = session.createQuery(quer);
		    courses = query.list();
		    if(courses!=null && !courses.isEmpty()){
		    	Iterator<String> itr = courses.iterator();
		    	while(itr.hasNext()){
		    		String course = (String)itr.next();
		    		if(course!=null && !course.equalsIgnoreCase("EMPTY")){
		    			courseMap.put(course, course);
		    		}
		    		
		    	}
		    	courseMap = (HashMap<String, String>) CommonUtil.sortMapByValue(courseMap);
		    }
		}catch(Exception ex){
			log.error("Error during getting data..." , ex);
			 throw ex;
		}
		return courseMap;
	}
	@Override
	public List<String> getPromoteMarksRegNos(int year) throws Exception {
		Session session = null;
		List<String> regNos = null;
		try{
			session = HibernateUtil.getSession();
			String quer = "from PromoteMarks marks where marks.academicYear="+year;
			Query query = session.createQuery(quer);
			regNos = query.list();
		}catch(Exception e){
			log.error("Error during getting data..." , e);
			 throw e;
		}
		return regNos;
	}
	

}
