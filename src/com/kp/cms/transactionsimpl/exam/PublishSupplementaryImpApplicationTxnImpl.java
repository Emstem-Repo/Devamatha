package com.kp.cms.transactionsimpl.exam;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.kp.cms.bo.exam.PublishSupplementaryImpApplication;
import com.kp.cms.bo.exam.StudentSupplementaryImprovementApplication;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.transactions.exam.IPublishSupplementaryImpApplicationTxn;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class PublishSupplementaryImpApplicationTxnImpl implements IPublishSupplementaryImpApplicationTxn {
	/**
	 * Singleton object of PublishSupplementaryImpApplicationTxnImpl
	 */
	private static volatile PublishSupplementaryImpApplicationTxnImpl publishSupplementaryImpApplicationTxnImpl = null;
	private static final Log log = LogFactory.getLog(PublishSupplementaryImpApplicationTxnImpl.class);
	private PublishSupplementaryImpApplicationTxnImpl() {
		
	}
	/**
	 * return singleton object of PublishSupplementaryImpApplicationTxnImpl.
	 * @return
	 */
	public static PublishSupplementaryImpApplicationTxnImpl getInstance() {
		if (publishSupplementaryImpApplicationTxnImpl == null) {
			publishSupplementaryImpApplicationTxnImpl = new PublishSupplementaryImpApplicationTxnImpl();
		}
		return publishSupplementaryImpApplicationTxnImpl;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.exam.IPublishSupplementaryImpApplicationTxn#loadClassByExamNameAndYear(com.kp.cms.forms.BaseActionForm)
	 */
	@Override
	public List<StudentSupplementaryImprovementApplication> loadClassByExamNameAndYear(
			PublishSupplementaryImpApplicationForm actionForm) throws Exception {

		Session session=null;
        Transaction transaction=null;
        List list=null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="select supp.classes.id,supp.classes.name,sche.curriculumSchemeDuration.curriculumScheme.year from StudentSupplementaryImprovementApplication supp join supp.classes.classSchemewises sche where supp.examDefinition.academicYear="+actionForm.getYear()+" and supp.examDefinition.id="+actionForm.getExamId();
      	  list=session.createQuery(str).list();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	throw  new ApplicationException(e);
      	   }
       return list;
		}
	
	
	public String isDuplicate(int id,int examId, int classId) throws Exception {
		Session session = null;
		String className="";
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		session = sessionFactory.openSession();

		try {
			Criteria crit = session
					.createCriteria(PublishSupplementaryImpApplication.class);
			crit.add(Restrictions.eq("exam.id", examId));
			crit.add(Restrictions.eq("classCode.id", classId));
			List<PublishSupplementaryImpApplication> list = crit.list();

			if (list.size() > 0) {
				for (Iterator it = list.iterator(); it.hasNext();) {
					PublishSupplementaryImpApplication eBO = (PublishSupplementaryImpApplication) it
							.next();
					if (id != eBO.getId())
					{
						if (eBO.getIsActive() == true) {
							className=eBO.getClassCode().getName();
						}
					}
					else{
						className ="";
					}
				}
			}
			return className;
		}
		catch (Exception e) {
			throw  new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		
	}
	@Override
	public List getClassNameAndYearByClassId(int id) throws Exception {
		Session session=null;
        Transaction transaction=null;
       List list =null;
        try{
      	    session=InitSessionFactory.getInstance().openSession();
      	    String str="select cls.name,sche.curriculumSchemeDuration.curriculumScheme.year from Classes cls join cls.classSchemewises sche where cls.id="+id;
      	  list= session.createQuery(str).list();
      	    session.close();
          }catch(Exception e){
      	    if (transaction != null)
      		   transaction.rollback();
      	log.debug("Error during deleting ValuatorCharges data...", e);
      	throw  new ApplicationException(e);
      	   }
       return list;
	}
}
