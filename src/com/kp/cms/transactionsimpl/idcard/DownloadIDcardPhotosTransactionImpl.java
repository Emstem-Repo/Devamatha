package com.kp.cms.transactionsimpl.idcard;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.employee.EmpImages;
import com.kp.cms.transactions.idcard.IDownloadIDcardPhotosTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class DownloadIDcardPhotosTransactionImpl implements IDownloadIDcardPhotosTransaction {
	private static final Log log = LogFactory.getLog(DownloadIDcardPhotosTransactionImpl.class);
	public static volatile DownloadIDcardPhotosTransactionImpl downloadIDcardPhotosTransactionImpl = null;
	// getting single instance
	public static DownloadIDcardPhotosTransactionImpl getInstance(){
		if(downloadIDcardPhotosTransactionImpl==null){
			downloadIDcardPhotosTransactionImpl = new DownloadIDcardPhotosTransactionImpl();
			return downloadIDcardPhotosTransactionImpl;
		}
		return downloadIDcardPhotosTransactionImpl;
	}
	@Override
	public String getprogramNameById(String pgmId) throws Exception {
		
		log.debug("call of getclassNameById method in DownloadIDcardPhotosTransactionImpl.class");
		//String className="";
		String pgmName="";
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String HQLQuery="select p from Course p where p.id=:pgmId";
			Query query=session.createQuery(HQLQuery);
			query.setString("pgmId", pgmId);
			Course program=(Course) query.uniqueResult();
			pgmName =program.getName();
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error("Error in getclassNameById method in DownloadIDcardPhotosTransactionImpl.class");
		}
		
		log.debug("end of getclassNameById method in DownloadIDcardPhotosTransactionImpl.class");
		
		return pgmName;
	}
	@Override
	public int getTotalImageCount(int year, String pgmId,String applnNo) throws Exception {
		log.debug("call of getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		int ImageCount=0;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			/*String hqlQuery="select count(apnDoc.id) from AdmAppln adm inner join adm.applnDocs apnDoc" +
					" inner join adm.students  st inner join st.classSchemewise cs inner join cs.curriculumSchemeDuration cd " +
					" where cd.academicYear=:year and cs.id=:classId and apnDoc.isPhoto=1 and apnDoc.document is not null ";*/
			
			/*String hqlQuery=" select count(a.id) from ApplnDoc a"+
							" join a.admAppln.students st  where  a.isPhoto=1 "+ 
							" and  st.isAdmitted=1 and st.isActive = 1 "+ 
							" and st.admAppln.isSelected=1 and st.admAppln.isCancelled=0 "+  
							" and st.admAppln.appliedYear=:year "+
							" and st.admAppln.courseBySelectedCourseId.program.id=:pgmId order by st.registerNo";*/
			
			
		
			
			if(!applnNo.isEmpty())
				
			{/*
				String hqlQuery1=" select count(apnDoc.id) from AdmAppln adm "+
				" inner join adm.applnDocs apnDoc "+ 
				" inner join adm.students st "+
				" inner join  st.classSchemewise cs "+
				" inner join cs.curriculumSchemeDuration cd "+ 
				//" where cd.academicYear=:year "+
				" where adm.appliedYear=:year "+
				" and adm.applnNo=:applnNo " +
				//" and apnDoc.isPhoto=1 " +
				"and apnDoc.document is not null ";*/
				
				

				String hqlQuery1=" select count(doc) from AdmAppln a " +
				" inner join a.students s" +
				" inner join s.admAppln.applnDocs doc where a.appliedYear=:year and a.courseBySelectedCourseId.id=:pgmId" +
				" and a.applnNo=:applnNo"+
				" a.isBypassed=1" +
				" and doc.isPhoto=1 and s.isAdmitted=1";
				
				Query query= session.createQuery(hqlQuery1.toString());
				query.setInteger("year", year);
				query.setString("pgmId", pgmId);
				query.setString("applnNo", applnNo);
				ImageCount=Integer.parseInt(query.uniqueResult().toString());
				
			}
			else
			{
			
		/*	
			String hqlQuery=" select count(apnDoc.id) from AdmAppln adm "+
			" inner join adm.applnDocs apnDoc "+ 
			" inner join adm.students st "+
			" inner join  st.classSchemewise cs "+
			" inner join cs.curriculumSchemeDuration cd "+ 
			//" where cd.academicYear=:year "+
			" where adm.appliedYear=:year "+
			" and  st.admAppln.courseBySelectedCourseId.program.id=:pgmId "+
			//" and apnDoc.isPhoto=1 " +
			"and apnDoc.document is not null ";*/
				
				
				String hqlQuery=" select count(doc) from AdmAppln a " +
				" inner join a.students s" +
				" inner join s.admAppln.applnDocs doc where a.appliedYear=:year and a.courseBySelectedCourseId.id=:pgmId" +
				" and "+
				" a.isBypassed=1 " +
				" and doc.isPhoto=1 and s.isAdmitted=1";


				Query query= session.createQuery(hqlQuery.toString());
				query.setInteger("year", year);
				query.setString("pgmId", pgmId);
				ImageCount=Integer.parseInt(query.uniqueResult().toString());
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		}
		log.debug("end of getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		return ImageCount;
	}
	@Override
	public List<ApplnDoc> getImages(int year, int page, int pagesize,String pgmId,String applnNo)
			throws Exception {
		log.debug("calld of getImages method in DownloadIDcardPhotosTransactionImpl.class");
		Session session  = null;
		List<ApplnDoc> applnDocs = new ArrayList<ApplnDoc>();
		try
		{
			session=HibernateUtil.getSession();
			/*String hql="select apnDoc from AdmAppln adm inner join adm.applnDocs apnDoc" +
			" inner join adm.students  st inner join st.classSchemewise cs inner join cs.curriculumSchemeDuration cd " +
			" where cd.academicYear=:year and cs.id=:classId and apnDoc.isPhoto=1 and apnDoc.document is not null and st.isAdmitted=1";*/
			
			
			
			if(!applnNo.isEmpty())
			{
			
			/*StringBuffer hql = new StringBuffer("select apnDoc from AdmAppln adm "+
							" inner join adm.applnDocs apnDoc "+ 
							" inner join adm.students st "+
							" inner join  st.classSchemewise cs "+
							" inner join cs.curriculumSchemeDuration cd "+ 
							//" where cd.academicYear=:year "+
							" where adm.appliedYear=:year "+
							" and adm.applnNo=:applnNo " +
							//" and apnDoc.isPhoto=1 " +
							"and apnDoc.document is not null");
			*/
				StringBuffer hql = new StringBuffer(" select doc from AdmAppln a " +
						" inner join a.students s" +
						" inner join s.admAppln.applnDocs doc where a.appliedYear=:year and a.courseBySelectedCourseId.id=:pgmId" +
						" and a.applnNo=:applnNo"+
						" a.isBypassed=1 " +
						" and doc.isPhoto=1 and s.isAdmitted=1");
			
			Query query=session.createQuery(hql.toString());
			query.setInteger("year", year);
			query.setString("pgmId", pgmId);			
			query.setString("applnNo", applnNo);
			query.setFirstResult((page - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			applnDocs=query.list();
			}
			else
			{
			
				StringBuffer hql = new StringBuffer(" select doc from AdmAppln a " +
						" inner join a.students s" +
						" inner join s.admAppln.applnDocs doc where a.appliedYear=:year and a.courseBySelectedCourseId.id=:pgmId" +
						" and"+
						" a.isBypassed=1 " +
						" and doc.isPhoto=1 and s.isAdmitted=1");
			
			/*	
				String hql=" select count(st.id) from AdmAppln adm " +
				" inner join adm.applnDocs apnDoc "+ 
				"inner join adm.students st  " +
				"inner join  st.classSchemewise cs " +
				" inner join cs.curriculumSchemeDuration cd  where adm.appliedYear=:year  " +
				"and  st.admAppln.courseBySelectedCourseId.program.id=:pgmId  ";
			
		
			*/
			
			
			Query query=session.createQuery(hql.toString());
			query.setInteger("year", year);
			query.setString("pgmId", pgmId);
			query.setFirstResult((page - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			applnDocs=query.list();
			}
			
		}
		catch (Exception e) {
			log.error("Errorn in getImages method in DownloadIDcardPhotosTransactionImpl.class");
			e.printStackTrace();
		}
		log.debug("end  of getImages method in DownloadIDcardPhotosTransactionImpl.class");
		return applnDocs;
	}
	
	
	
	
	@Override
	public int getTotalImageCount1() throws Exception {
		log.debug("call of getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		int ImageCount=0;
		Session session=null;
		try
		{
			session=HibernateUtil.getSession();
			String hqlQuery="select count(ei.id) from EmpImages ei where ei.empPhoto is not null and ei.employee.isActive=1";
			Query query= session.createQuery(hqlQuery);
			ImageCount=Integer.parseInt(query.uniqueResult().toString());
			
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("Error in getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		}
		log.debug("end of getTotalImageCount method in DownloadIDcardPhotosTransactionImpl.class");
		return ImageCount;
	}
	@Override
	public List<EmpImages> getImages1(int page, int pagesize)
			throws Exception {
		log.debug("calld of getImages method in DownloadIDcardPhotosTransactionImpl.class");
		Session session  = null;
		List<EmpImages> applnDocs = new ArrayList<EmpImages>();
		try
		{
			session=HibernateUtil.getSession();
			String hql="from EmpImages ei where ei.empPhoto is not null and ei.employee.isActive=1";
			Query query=session.createQuery(hql);
			//query.setInteger("year", year);
			//query.setInteger("classId", classId);
			query.setFirstResult((page - 1) * pagesize); 
			query.setMaxResults(pagesize); 
			applnDocs=query.list();
			
		}
		catch (Exception e) {
			log.error("Errorn in getImages method in DownloadIDcardPhotosTransactionImpl.class");
			e.printStackTrace();
		}
		log.debug("end  of getImages method in DownloadIDcardPhotosTransactionImpl.class");
		return applnDocs;
	}
	
}
