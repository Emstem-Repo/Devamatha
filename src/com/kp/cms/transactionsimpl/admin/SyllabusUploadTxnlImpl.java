package com.kp.cms.transactionsimpl.admin;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.transactions.admin.ISyllabusUploadTransaction;
import com.kp.cms.utilities.HibernateUtil;

public class SyllabusUploadTxnlImpl implements ISyllabusUploadTransaction {

	
	  public static volatile SyllabusUploadTxnlImpl syllabusUploadTxnlImpl = null;
	    private static Log log = LogFactory.getLog(SyllabusUploadTxnlImpl.class);
	  
	    /**
	     * @return
	     */
	    public static SyllabusUploadTxnlImpl getInstance() {
			if (syllabusUploadTxnlImpl == null) {
				syllabusUploadTxnlImpl = new SyllabusUploadTxnlImpl();
				return syllabusUploadTxnlImpl;
			}
			return syllabusUploadTxnlImpl;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.ISyllabusUploadTransaction#getSubejectByYearAndSubjectCode(java.lang.String, java.lang.String)
		 */
		@Override
		public Integer getSubejectByYearAndSubjectCode(String academicYear,
				String subjectCode) {
			Session session=null;
			Integer subjectId=0;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("select s.id as subjectId from CurriculumSchemeSubject css "+  
				   " join css.curriculumSchemeDuration cd join cd.curriculumScheme cs "+
				   " join css.subjectGroup sg join sg.subjectGroupSubjectses sgs "+
				   " join sgs.subject s where sg.isActive=1 and sgs.isActive=1 and s.isActive=1 "+
				   " and cs.year='"+academicYear+"' and s.code='"+subjectCode+"' group by s.id");
				subjectId= (Integer) query.uniqueResult();
					
			}catch(Exception e){
				log.info("exception occured in duplicate check..");
				e.printStackTrace();
				if(session!=null){
					session.close();
				}
			}
			return subjectId;
		}

		/* (non-Javadoc)
		 * @see com.kp.cms.transactions.admin.ISyllabusUploadTransaction#saveSyllabusEntries(java.util.List)
		 */
		@Override
		public boolean saveSyllabusEntries(List<SyllabusEntry> entriesList) {
			Session session=null;
			Transaction transaction=null;
			boolean isSaved=false;
			try{
				session=HibernateUtil.getSession();
				transaction=session.beginTransaction();
				transaction.begin();
				if(entriesList!=null && !entriesList.isEmpty()){
					for (SyllabusEntry syllabusEntry : entriesList) {
						session.saveOrUpdate(syllabusEntry);		
					}
					isSaved=true;
				}
				transaction.commit();
			}catch(Exception exception){
				if (transaction != null)
					transaction.rollback();
				log.debug("Error during saving data...", exception);
			}
			finally{
				session.flush();
				session.close();
			}
			return isSaved;
		}

		@Override
		public List<SyllabusEntry> getSyllabusEntryBySubjectIdList(
				List<Integer> subjectIdlist) {
			Session session=null;
			List<SyllabusEntry> syllabusEntries=null;
			try{
				session=HibernateUtil.getSession();
				Query query=session.createQuery("from SyllabusEntry syllabus where syllabus.isActive=1 and syllabus.subject.id in (:subjectIdList)");
				query.setParameterList("subjectIdList", subjectIdlist);
				syllabusEntries= query.list();
					
			}catch(Exception e){
				log.info("exception occured in duplicate check..");
				e.printStackTrace();
				if(session!=null){
					session.close();
				}
			}
			return syllabusEntries;
		}
}
