package com.kp.cms.transactionsimpl.admin;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.OpenSyllabusEntry;
import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.SyllabusEntryProgramDetails;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.SyllabusEntryHodApprovalForm;
import com.kp.cms.transactions.admin.ISyllabusEntryHodApprovalTrans;
import com.kp.cms.utilities.HibernateUtil;

public class SyllabusEntryHodApprovalTransImpl implements ISyllabusEntryHodApprovalTrans{
	public static volatile SyllabusEntryHodApprovalTransImpl syllabusEntryHodApprovalTransImpl=null;
	//private constructor
	private SyllabusEntryHodApprovalTransImpl(){
		
	}
	//singleton object
	public static SyllabusEntryHodApprovalTransImpl getInstance(){
		if(syllabusEntryHodApprovalTransImpl==null){
			syllabusEntryHodApprovalTransImpl=new SyllabusEntryHodApprovalTransImpl();
			return syllabusEntryHodApprovalTransImpl;
		}
		return syllabusEntryHodApprovalTransImpl;
	}
	@Override
	public boolean checkForSyllabusEntryOpen(
			SyllabusEntryHodApprovalForm syllabusEntryForHodApprovalForm,
			Date date) throws Exception {
		boolean flag=false;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from OpenSyllabusEntry o where o.isActive=1 " +
					" and '"+date+"' between o.startDate and o.endDate ORDER BY o.batch DESC");
			List<OpenSyllabusEntry> list=query.list();
			if(list!=null && !list.isEmpty()){
				List<String> batchList=new ArrayList<String>();
				for (OpenSyllabusEntry openSyllabusEntry : list) {
					batchList.add(String.valueOf(openSyllabusEntry.getBatch()));
				}
				syllabusEntryForHodApprovalForm.setBatchYearList(batchList);
				flag=true;
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				// session.close();
			}
		}
		return flag;
	}
	@Override
	public Map<String, Integer> getCourseMap() throws Exception {
		Map<String,Integer> courseMap = new HashMap<String,Integer>();
		try {
			Session session = null;
			// SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from Course c where c.isActive=1");
			List<Course> list=query.list();
			if(list!=null && !list.isEmpty()){
				for (Course course : list) {
					courseMap.put(course.getName(),course.getId());
				}
			}
		} catch (Exception e) {
		}
		return courseMap;
	}
	@Override
	public List<SyllabusEntry> getSyllabusEntriesByIds(List<Integer> approveList)
			throws Exception {
		List<SyllabusEntry> list=new ArrayList<SyllabusEntry>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from SyllabusEntry e where e.isActive=1 and e.id in (:ids)");
			query.setParameterList("ids", approveList);
			list=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public boolean updateByHod(List<SyllabusEntry> list) throws Exception {
		Session session=null;
		Transaction transaction=null;
		boolean isSaved=false;
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			transaction.begin();
			for (SyllabusEntry syllabusEntry : list) {
				session.merge(syllabusEntry);		
			}
			transaction.commit();
			isSaved=true;
		}catch(Exception exception){
			if (transaction != null)
				transaction.rollback();
		}
		finally{
			session.flush();
			session.close();
		}
		return isSaved;
	}
	@Override
	public boolean saveProgramDetails(
			SyllabusEntryProgramDetails syllabusEntryProgramDetails)
			throws Exception {
		boolean flag=false;
		Session session = null;
		Transaction tx = null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			tx.begin();
			session.save(syllabusEntryProgramDetails);
			tx.commit();
			session.close();
			flag=true;
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		return flag;
	}
	@Override
	public List<SyllabusEntryProgramDetails> getProgramDetailsByBatch(
			String batchYear) throws Exception {
		List<SyllabusEntryProgramDetails> list=new ArrayList<SyllabusEntryProgramDetails>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from SyllabusEntryProgramDetails e where e.isActive=1 and e.batchYear="+Integer.parseInt(batchYear));
			list=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public List<Integer> getSubjectsByCOurse(int courseId, int batch)
			throws Exception {
		List<Integer> list=new ArrayList<Integer>();
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select s.id as subId from CurriculumSchemeSubject css  join css.curriculumSchemeDuration cd " +
					" join cd.curriculumScheme cs join css.subjectGroup sg join sg.subjectGroupSubjectses sgs " +
					" join sgs.subject s join cs.course c join s.department d where sg.isActive=1 " +
					" and sgs.isActive=1 and s.isActive=1 and cs.year="+batch+" and s.isCertificateCourse=0 " +
					" and c.id="+courseId+" group by c.id,s.id order by c.name,cd.semesterYearNo,s.code");
			list=query.list();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return list;
	}
	@Override
	public SyllabusEntryProgramDetails getProgramDetailsByBatchAndCourse(
			int course, int batch) throws Exception{
		SyllabusEntryProgramDetails programDetails=null;
		Session session = null;
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from SyllabusEntryProgramDetails e where e.isActive=1 and e.batchYear="+batch+" and e.courseId="+course);
			programDetails=(SyllabusEntryProgramDetails)query.uniqueResult();
			session.flush();
		}catch (Exception e) {
			if (session != null) {
				session.flush();
				// session.close();
			}
			throw new ApplicationException(e);
		}
		return programDetails;
	}
}
