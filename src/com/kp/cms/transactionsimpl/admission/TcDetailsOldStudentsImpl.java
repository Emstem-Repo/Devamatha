package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Category;
import com.kp.cms.bo.admin.CharacterAndConduct;
import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.TCNumber;
import com.kp.cms.bo.admission.TcDetailsOldStudents;
import com.kp.cms.to.admission.TcDetailsOldStudentsTo;
import com.kp.cms.transactions.admission.ITcDetailsOldStudents;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class TcDetailsOldStudentsImpl implements ITcDetailsOldStudents{
	private static final Log log = LogFactory.getLog(TcDetailsOldStudentsImpl.class);
	private static TcDetailsOldStudentsImpl tcDetailsOldStudentsImpl=new TcDetailsOldStudentsImpl();
	private TcDetailsOldStudentsImpl(){}
	public static TcDetailsOldStudentsImpl getInstance(){
		return tcDetailsOldStudentsImpl;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getTcDetails(java.lang.String)
	 */
	@Override
	public String getTcDetails(String year,String tcFor)throws Exception{
		// TODO Auto-generated method stub
		if(year!=null && !year.isEmpty()){
		String query="from TcDetailsOldStudents tos where tos.acadamicYear="+year+" and tos.tcFor in("+tcFor+")";
		return query;
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getOldStudentsList(java.lang.String)
	 */
	@Override
	public List<TcDetailsOldStudents> getOldStudentsList(String query)throws Exception {
		// TODO Auto-generated method stub
		Session session=null;
		Transaction transaction=null;
		List<TcDetailsOldStudents> listOfOldStudents=null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			listOfOldStudents=session.createQuery(query).list();
			transaction.commit();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if(transaction!=null){
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
			}
		}
		return listOfOldStudents;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getReligionQuery()
	 */
	@Override
	public String getReligionQuery() {
		// TODO Auto-generated method stub
		String query="from Religion r where r.isActive=1";
		return query;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getReligionList(java.lang.String)
	 */
	@Override
	public List<Religion> getReligionList(String query)throws Exception {
		// TODO Auto-generated method stub
		List<Religion> religionList=null;
		Session session=null;
		Transaction transaction=null;
		try {
//			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			session = HibernateUtil.getSession();
			transaction=session.beginTransaction();
			religionList=session.createQuery(query).list();
			transaction.commit();
			session.flush();
			// session.close();
		} catch (Exception e) {
			log.error(e.getMessage());
			if(transaction!=null){
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
			}
		}
		return religionList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getCharacterAndConductList(java.lang.String)
	 */
	@Override
	public List<CharacterAndConduct> getCharacterAndConductList(String query)throws Exception {
		// TODO Auto-generated method stub
		List<CharacterAndConduct> characterAndConductList=new ArrayList<CharacterAndConduct>();
		Session session=null;
		Transaction transaction=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			characterAndConductList=session.createQuery(query).list();
			transaction.commit();
			session.flush();
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			if(transaction!=null){
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
			}
		}
		return characterAndConductList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getCharacterAndConductQuery()
	 */
	@Override
	public String getCharacterAndConductQuery() {
		// TODO Auto-generated method stub
		return "from CharacterAndConduct cc";
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getCategoryList(java.lang.String)
	 */
	@Override
	public List<Caste> getCategoryList(String query)throws Exception {
		// TODO Auto-generated method stub
		List<Caste> categoryList=new ArrayList<Caste>();
		Session session=null;
		Transaction transaction=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			categoryList=session.createQuery(query).list();
			transaction.commit();
			session.flush();
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			if(transaction!=null){
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
			}
		}
		return categoryList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getCategoryQuery()
	 */
	@Override
	public String getCategoryQuery() {
		// TODO Auto-generated method stub
		return "from Caste c where c.isActive=1";
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getNationalityList(java.lang.String)
	 */
	@Override
	public List<Nationality> getNationalityList(String query)throws Exception {
		// TODO Auto-generated method stub
		List<Nationality> nationalityList=new ArrayList<Nationality>();
		Session session=null;
		Transaction transaction=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			nationalityList=session.createQuery(query).list();
			transaction.commit();
			session.flush();
		}catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage());
			if(transaction!=null){
				transaction.rollback();
			}
			if (session != null) {
				session.flush();
			}
		}
		return nationalityList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getNationalityQuery()
	 */
	@Override
	public String getNationalityQuery() {
		// TODO Auto-generated method stub
		return "from Nationality n where n.isActive=1";
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getOldStudentsList()
	 */
	@Override
	public List<TcDetailsOldStudents> getOldStudentsList()throws Exception {
		// TODO Auto-generated method stub
		String query="from TcDetailsOldStudents t";
		List<TcDetailsOldStudents> registerNoList=new ArrayList<TcDetailsOldStudents>();
		Session session=null;
		Transaction transaction=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			registerNoList=session.createQuery(query).list();
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		
		return registerNoList;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getTcNoByYear(java.lang.String, java.lang.String)
	 */
	@Override
	public TCNumber getTcNoByYear(String year, String tcFor)throws Exception {
		// TODO Auto-generated method stub
		String query="from TCNumber t where t.tcFor='"+tcFor+"' and t.year='"+year+"' and t.isActive=1";
		TCNumber tcNumber=null;
		Transaction transaction=null;
		Session session=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			tcNumber=(TCNumber)session.createQuery(query).uniqueResult();
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		return tcNumber;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#saveOldStudentTcDetails(com.kp.cms.bo.admission.TcDetailsOldStudents)
	 */
	@Override
	public int saveOldStudentTcDetails(TcDetailsOldStudents tcDetailsOldStudents)throws Exception {
		// TODO Auto-generated method stub
		int flag=0;
		Session session=null;
		Transaction transaction=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(tcDetailsOldStudents);
			transaction.commit();
			session.flush();
			flag=1;
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#updateTcSLNO(com.kp.cms.bo.admin.TCNumber)
	 */
	@Override
	public void updateTcSLNO(TCNumber tcNumber)throws Exception {
		// TODO Auto-generated method stub
		Transaction transaction=null;
		Session session=null;
		try{
//			SessionFactory sessionFactory=InitSessionFactory.getInstance();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			session.saveOrUpdate(tcNumber);
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		
	}

	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#editTcDetailsOldStudents(java.lang.String)
	 */
	@Override
	public TcDetailsOldStudents editTcDetailsOldStudents(String id)throws Exception {
		// TODO Auto-generated method stub
		TcDetailsOldStudents tcDetailsOldStudents=null;
		Transaction transaction=null;
		Session session=null;
		String query="from TcDetailsOldStudents t where t.id="+Integer.parseInt(id);
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			tcDetailsOldStudents=(TcDetailsOldStudents)session.createQuery(query).uniqueResult();
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		return tcDetailsOldStudents;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#deleteTc(java.lang.String)
	 */
	@Override
	public boolean deleteTc(String id)throws Exception {
		// TODO Auto-generated method stub
		boolean flag=false;
		Transaction transaction=null;
		Session session=null;
			try{
				session=InitSessionFactory.getInstance().openSession();
				transaction=session.beginTransaction();
				TcDetailsOldStudents detailsOldStudents=(TcDetailsOldStudents)session.get(TcDetailsOldStudents.class, Integer.parseInt(id));
				session.delete(detailsOldStudents);
				transaction.commit();
				flag=true;
				session.flush();
			}catch (Exception exception) {
				// TODO: handle exception
				if(transaction!=null){
					transaction.rollback();
				}
				if(session!=null){
					session.flush();
				}
			}
		
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admission.ITcDetailsOldStudents#getTcOldStudentByRegNo(java.lang.String)
	 */
	@Override
	public TcDetailsOldStudents getTcOldStudentByRegNo(String registerNo)throws Exception {
		// TODO Auto-generated method stub
		TcDetailsOldStudents tcDetailsOldStudents=null;
		Transaction transaction=null;
		Session session=null;
		String query="from TcDetailsOldStudents t where t.registerNo='"+registerNo+"'";
		try{
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			tcDetailsOldStudents=(TcDetailsOldStudents)session.createQuery(query).uniqueResult();
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		return tcDetailsOldStudents;
	}
	@Override
	public List<TCNumber> getTcNo(String year) throws Exception {
		// TODO Auto-generated method stub
		String query="from TCNumber t where t.year='"+year+"' and t.isActive=1";
		List<TCNumber> tcNumber=null;
		Transaction transaction=null;
		Session session=null;
		try{
//			SessionFactory sessionFactory=HibernateUtil.getSessionFactory();
			session=HibernateUtil.getSession();
			transaction=session.beginTransaction();
			tcNumber=session.createQuery(query).list();
			transaction.commit();
			session.flush();
		}catch (Exception exception) {
			// TODO: handle exception
			if(transaction!=null){
				transaction.rollback();
			}
			if(session!=null){
				session.flush();
			}
		}
		return tcNumber;

	}
}
