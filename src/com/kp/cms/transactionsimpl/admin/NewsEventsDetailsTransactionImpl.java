package com.kp.cms.transactionsimpl.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Currency;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.bo.admin.NewsEventsContactDetails;
import com.kp.cms.bo.admin.NewsEventsDetails;
import com.kp.cms.bo.admin.NewsEventsParticipants;
import com.kp.cms.bo.admin.NewsEventsPhoto;
import com.kp.cms.bo.admin.NewsEventsResourse;
import com.kp.cms.bo.employee.EmployeeSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.transactions.admin.INewsEventsDetailsTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class NewsEventsDetailsTransactionImpl implements INewsEventsDetailsTransaction {

	private static final Logger log = Logger.getLogger(NewsEventsDetailsTransactionImpl.class);
	public static volatile NewsEventsDetailsTransactionImpl mobNewsEventsDetailsTransactionImpl = null;

	/**
	 * This method is used to create single instance of NewsEventsDetailsTransactionImpl.
	 * @return instance of MobNewsEventsDetailsTransactionImpl.
	 */
	public static NewsEventsDetailsTransactionImpl getInstance() {
		if (mobNewsEventsDetailsTransactionImpl == null) {
			mobNewsEventsDetailsTransactionImpl = new NewsEventsDetailsTransactionImpl();
			return mobNewsEventsDetailsTransactionImpl;
		}
		return mobNewsEventsDetailsTransactionImpl;
	}
	
	
	public Map<String, String> getDepartmentMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department d where d.isActive=true");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department department=iterator.next();
					if(department.getId()!=0 && department.getName()!=null && !department.getName().isEmpty())
					map.put(String.valueOf(department.getId()),department.getName());
				}
			}
		}catch (Exception exception) {
			
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	public Map<String, String> getStreamMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from EmployeeStreamBO s where s.isActive=true");
			List<EmployeeStreamBO> list=query.list();
			if(list!=null){
				Iterator<EmployeeStreamBO> iterator=list.iterator();
				while(iterator.hasNext()){
					EmployeeStreamBO stream=iterator.next();
					if(stream!=null && stream.getName()!=null && !stream.getName().isEmpty() && stream.getId()>0)
					map.put(String.valueOf(stream.getId()),stream.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	public Map<String, String> getCourseMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Course s where s.isActive=true and s.onlyForApplication=false");
			List<Course> list=query.list();
			if(list!=null){
				Iterator<Course> iterator=list.iterator();
				while(iterator.hasNext()){
					Course cs=iterator.next();
					if(cs!=null && cs.getName()!=null && !cs.getName().isEmpty() && cs.getId()>0)
					map.put(String.valueOf(cs.getId()),cs.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	
	
	public Map<String, String> getSplCenterMap() throws Exception {
		Session session=null;
		Map<String,String> map=new HashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Department s where s.isActive=true and s.isAcademic=false");
			List<Department> list=query.list();
			if(list!=null){
				Iterator<Department> iterator=list.iterator();
				while(iterator.hasNext()){
					Department cs=iterator.next();
					if(cs!=null && cs.getName()!=null && !cs.getName().isEmpty() && cs.getId()>0)
					map.put(String.valueOf(cs.getId()),cs.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}finally{
			if(session!=null){
				session.flush();
			}
		}
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

	@Override
	public List<NewsEventsDetails> getMobNewsEventsDetails()
	throws Exception {
		log.info("call of getMobNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		List<NewsEventsDetails> mobNewsEventsDetailsList = null;

		try {
			session = InitSessionFactory.getInstance().openSession();
			mobNewsEventsDetailsList = session.createQuery("from NewsEventsDetails c where c.isActive = 1 and c.postIsApproved=0 and c.preIsApproved=0").list();

		} catch (Exception e) {
			log.error("Unable to get getMobNewsEventsDetails" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getMobNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return mobNewsEventsDetailsList;
	}
	public List<NewsEventsDetails> getNewsEventsDetailsForEdit()
	throws Exception {
		log.info("call of getNewsEventsDetailsForEdit in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Date d=new Date();
		d=new java.sql.Date(d.getTime());
		
		List<NewsEventsDetails> mobNewsEventsDetailsList = null;

		try {
			session = InitSessionFactory.getInstance().openSession();
			mobNewsEventsDetailsList = session.createQuery("from NewsEventsDetails c where c.isActive = 1 and c.postIsApproved=0 and c.dateFrom>='"+ d+"'").list();

		} catch (Exception e) {
			log.error("Unable to get getNewsEventsDetailsForEdit" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getNewsEventsDetailsForEdit in NewsEventsDetailsTransactionImpl class.");
		return mobNewsEventsDetailsList;
	}

	@Override
	public List<MobNewsEventsCategory> getCategoryList() throws Exception {
		log.info("call of getCategoryList in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		List<MobNewsEventsCategory> newsEventsCategoriesList;
		try
		{
			session=HibernateUtil.getSession();
			newsEventsCategoriesList=session.createQuery("from MobNewsEventsCategory c where c.isActive = 1 order by c.category").list();			
		}catch (Exception e) {
			log.error("Unable to get getCategoryList" ,e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of getCategoryList in NewsEventsDetailsTransactionImpl class.");
		return newsEventsCategoriesList;
	}

	@Override
	public NewsEventsDetails isMobNewsEventDetailsExist(String eventTitle,
			int titleId) throws Exception {
		log.debug("inside isNewsEventDetailsExist");
		Session session=null;
		NewsEventsDetails mobNewsEventsDetails;
		try
		{
			session=HibernateUtil.getSession();
			String sqlString="from NewsEventsDetails m where m.eventTitle = :newsTitle";
			if(titleId !=0){
				sqlString = sqlString + " and m.id != :curId";
			}
			Query query = session.createQuery(sqlString);
			query.setString("newsTitle", eventTitle);
			if(titleId !=0){
				query.setInteger("curId", titleId);
			}

			mobNewsEventsDetails = (NewsEventsDetails) query.uniqueResult();
			session.flush();
			//session.close();
			//sessionFactory.close();
			log.debug("NewsEventsDetails");
			return mobNewsEventsDetails;
		} catch (Exception e) {
			log.error("Error during duplcation checking..." , e);
			session.flush();
			//session.close();
			throw new ApplicationException(e);
		}
	}

	public boolean addMobNewsEventDetails(NewsEventsDetails mobNewsEventsDetails) throws Exception {
		log.info("call of addMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isAdded = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.save(mobNewsEventsDetails);
			transaction.commit();
			isAdded = true;
		} catch (Exception e) {
			isAdded = false;
			log.error("Unable to addNewsEventDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of addMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		return isAdded;
	}



	@Override
	public NewsEventsDetails editMobNewsEventDetails(int id) throws Exception {

		log.info("call of editMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");

		Session session = null;
		//Transaction transaction = null;
		NewsEventsDetails mobNewsEventsDetails=null;
		try {
			session = HibernateUtil.getSession();
			//transaction = session.beginTransaction();
			Query query = session
			.createQuery("from NewsEventsDetails c where c.id = :Id");
			query.setInteger("Id", id);
			mobNewsEventsDetails = (NewsEventsDetails) query.uniqueResult();
		} catch (Exception e) {
			log.error("Unable to editmobNewsEventsDetails", e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("end of addMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		return mobNewsEventsDetails;
	}

	@Override
	public boolean reActivateMobNewsEventDetails(String eventTitle,
			String userId) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateMobNewsEventDetails(
			NewsEventsDetails mobNewsEventsDetails) throws Exception {

		log.info("call of updateMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		
		Session session = null;
		Transaction transaction = null;
		boolean isUpdated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			session.saveOrUpdate(mobNewsEventsDetails);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to updateMobNewsEventDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of updateMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		return isUpdated;
	}

	
	
	@Override
	public boolean deleteNewsEventsDetails(int mobNewaEventsDatailsId,
			String userId) throws Exception {
		
		log.info("call of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isDeleted = false;
		try
		{
			
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			NewsEventsDetails moNewsEventsDetails = (NewsEventsDetails) session.get(NewsEventsDetails.class, mobNewaEventsDatailsId);
			moNewsEventsDetails.setLastModifiedDate(new Date());
			moNewsEventsDetails.setModifiedBy(userId);
			moNewsEventsDetails.setIsActive(Boolean.FALSE);
			session.update(moNewsEventsDetails);
			transaction.commit();
			isDeleted = true;
		} catch (Exception e) {
			isDeleted = false;
			log.error("Unable to delete FeeHeadings" ,e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return isDeleted;
	}

	@Override
	public boolean reActivateMobNewsEventsDetails(int dupId, String userId)
			throws Exception {
		
		log.info("call of reActivateMobNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		boolean isActivated = false;
		try {
			/*SessionFactory sessionFactory = InitSessionFactory.getInstance();
			session = sessionFactory.openSession();*/
			session = HibernateUtil.getSession();
			Query query = session
					.createQuery("from NewsEventsDetails c where c.id = :id");
			query.setInteger("id", dupId);
			NewsEventsDetails mobNewsEventsDetails = (NewsEventsDetails) query.uniqueResult();
			transaction = session.beginTransaction();
			mobNewsEventsDetails.setIsActive(true);
			mobNewsEventsDetails.setModifiedBy(userId);
			mobNewsEventsDetails.setLastModifiedDate(new Date());
			session.update(mobNewsEventsDetails);
			transaction.commit();
			isActivated = true;
		} catch (Exception e) {
			isActivated = false;
			log.error("Unable to reActivateMobNewsEventsDetails" , e);
			if (transaction != null) {
				transaction.rollback();
			}
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of reActivateMobNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return isActivated;
	}

	public NewsEventsDetails GetNewsEventsDetails(NewsEventsEntryForm newsEventsForm)throws Exception{
		
		Session session = null;
		NewsEventsDetails mobNewsEventsDetails=null;
		try {
			
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from NewsEventsDetails c where c.isActive=1 and c.id = "+newsEventsForm.getSelectedNewsEventsId());
			mobNewsEventsDetails = (NewsEventsDetails) query.uniqueResult();
		} catch (Exception e) {
			log.error("Unable to Get NewsEventsDetails", e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		log.info("end of GetNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return mobNewsEventsDetails;
		
	}

	
	public StringBuffer getSerchedNewsQuery(int categoryId, String participants, Date dateFrom, 
			Date dateTo, String eventTitle, String academicYear, String organizedById) throws Exception
			{
			StringBuffer query = new StringBuffer(
					"from NewsEventsDetails n"
					+" where n.isActive = 1 and n.postIsApproved=0 and n.preIsApproved=0");
			
			
			if (academicYear != null && !StringUtils.isEmpty(academicYear.trim())) {
				query = query.append(" and n.academicYear= '"+ academicYear+"'");
			}
			if (eventTitle != null && !StringUtils.isEmpty(eventTitle.trim())) {
				query = query.append(" and n.eventTitle like '"
						+ eventTitle+"%'");
			}
			if (categoryId > 0) {
					query = query.append(" and n.categoryId='"+ categoryId+"'");
				}
			if (participants!=null && !participants.isEmpty()) {
				query = query.append(" and n.participants='"+ participants+"'");
			}
			if (organizedById !=null && !organizedById.isEmpty()) {
				query = query.append(" and n.organizedBy='"+ organizedById+"'");
			}
			if (dateFrom!=null) {
				query = query.append(" and n.dateFrom='"+ dateFrom+"'");
			}
			if (dateTo!=null) {
				query = query.append(" and n.dateTo='"+ dateTo+"'");
			}
			query.append(" order by n.categoryId");
		return query;
	}
	
	public List<NewsEventsDetails> getSerchedNews(StringBuffer query)
			throws Exception {
		Session session = null;
		List<NewsEventsDetails> empList;
		try {
			session = HibernateUtil.getSession();
			Query queri = session.createQuery(query.toString());
			empList = queri.list();
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		}
		return empList;
	}
	
	public NewsEventsPhoto getSerchedPhoto(NewsEventsEntryForm newsEventsForm)throws Exception
	{
	Session session = null;
	NewsEventsPhoto photo;
	try {
		session = HibernateUtil.getSession();
		String query="from NewsEventsPhoto p where p.id=" +newsEventsForm.getSelectedPhotoId();
		Query queri = session.createQuery(query);
		photo = (NewsEventsPhoto) queri.uniqueResult();
		
	} catch (Exception e) {
		throw new ApplicationException(e);
	}
	return photo;
}
	
	public StringBuffer getSerchedNewsQueryAdmin(NewsEventsEntryForm newsForm) throws Exception
			{
			StringBuffer query = new StringBuffer("from NewsEventsDetails n where n.isActive = 1");
			
			if(newsForm.getPrePostEventAdm()!=null && !newsForm.getPrePostEventAdm().isEmpty()){
				if(newsForm.getPrePostEventAdm().equalsIgnoreCase("Pre-Event"))
				{
					query=query.append(" and n.postIsApproved=0 and n.preIsApproved=1 ");
					
					if(newsForm.getStatus()!=null && !newsForm.getStatus().isEmpty()){
						query=query.append(" and n.preApproveStatus ='"+newsForm.getStatus()+"'");
					}
				}
				
				else if(newsForm.getPrePostEventAdm().equalsIgnoreCase("Post-Event"))
				{
					query=query.append(" and n.postIsApproved=1 and n.preIsApproved=1 ");
				
					if(newsForm.getStatus()!=null && !newsForm.getStatus().isEmpty()){
						query=query.append(" and n.postApproveStatus ='"+newsForm.getStatus()+"'");
					}
				}
			}
			
			if (newsForm.getTodaysDate()!=null) {
				query = query.append(" and n.dateFrom>='"+newsForm.getTodaysDate()+"'");
			}
			
			query.append(" order by n.dateFrom");
		return query;
	}
	
	public StringBuffer getSerchedNewsQueryPost(int categoryId, String participants, Date dateFrom, 
			Date dateTo, String eventTitle, String academicYear, String organizedById) throws Exception
			{
			StringBuffer query = new StringBuffer(
					"from NewsEventsDetails n"
					+" where n.isActive = 1 and n.postIsApproved=0 and n.preIsApproved=1");
			
			
			if (academicYear != null && !StringUtils.isEmpty(academicYear.trim())) {
				query = query.append(" and n.academicYear= '"+ academicYear+"'");
			}
			if (eventTitle != null && !StringUtils.isEmpty(eventTitle.trim())) {
				query = query.append(" and n.eventTitle like '"
						+ eventTitle+"%'");
			}
			if (categoryId > 0) {
					query = query.append(" and n.categoryId='"+ categoryId+"'");
				}
			if (participants!=null && !participants.isEmpty()) {
				query = query.append(" and n.participants='"+ participants+"'");
			}
			if (organizedById !=null && !organizedById.isEmpty()) {
				query = query.append(" and n.organizedBy='"+ organizedById+"'");
			}
			if (dateFrom!=null) {
				query = query.append(" and n.dateFrom='"+ dateFrom+"'");
			}
			if (dateTo!=null) {
				query = query.append(" and n.dateTo='"+ dateTo+"'");
			}
			query.append(" order by n.categoryId");
		return query;
	}
	
	
	public StringBuffer getSerchedNewsPostEventDept(int categoryId, String participants, Date dateFrom, 
			Date dateTo, String eventTitle, String academicYear, String organizedById) throws Exception
			{
			StringBuffer query = new StringBuffer(
					"from NewsEventsDetails n"
					+" where n.isActive = 1 and n.postIsApproved=0 and n.preIsApproved=1");
			
			
			if (academicYear != null && !StringUtils.isEmpty(academicYear.trim())) {
				query = query.append(" and n.academicYear= '"+ academicYear+"'");
			}
			if (eventTitle != null && !StringUtils.isEmpty(eventTitle.trim())) {
				query = query.append(" and n.eventTitle like '"
						+ eventTitle+"%'");
			}
			if (categoryId > 0) {
					query = query.append(" and n.categoryId='"+ categoryId+"'");
				}
			if (participants!=null && !participants.isEmpty()) {
				query = query.append(" and n.participants='"+ participants+"'");
			}
			if (organizedById !=null && !organizedById.isEmpty()) {
				query = query.append(" and n.organizedBy='"+ organizedById+"'");
			}
			if (dateFrom!=null) {
				query = query.append(" and n.dateFrom='"+ dateFrom+"'");
			}
			if (dateTo!=null) {
				query = query.append(" and n.dateTo='"+ dateTo+"'");
			}
			query.append(" order by n.categoryId");
		return query;
	}
	
	
	//-----amin Update Code------------
	/*applicationNumber = String.valueOf(application);
	employeeSettings.setCurrentApplicationNo(String.valueOf(application));
	employeeSettings.setModifiedBy(empResumeSubmissionForm.getUserId());
	employeeSettings.setLastModifiedDate(new Date());
	session.update(employeeSettings);
	txn.commit();*/
	
	public boolean updateNewsEventDetailsAdmin(NewsEventsEntryForm newsEventsEntryForm) throws Exception {
		log.info("call of updateMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		NewsEventsDetails newsEventsDetails=null;
		boolean isUpdated = false;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			Query query=session.createQuery("select e from NewsEventsDetails e where e.id= "+newsEventsEntryForm.getSelectedNewsEventsId());
			query.setLockMode("e", LockMode.UPGRADE);
			newsEventsDetails=(NewsEventsDetails) query.uniqueResult();
			
			if(newsEventsEntryForm.getPrePostEventAdm()!=null && !newsEventsEntryForm.getPrePostEventAdm().isEmpty() && newsEventsEntryForm.getPrePostEventAdm().equalsIgnoreCase("Pre-Event")){
				if(newsEventsEntryForm.getPreAdminStatus()!=null && !newsEventsEntryForm.getPreAdminStatus().isEmpty()){
					newsEventsDetails.setPreApproveStatus(newsEventsEntryForm.getPreAdminStatus());
					newsEventsDetails.setIsAdminApprovePre(true);
				}else
				{
					newsEventsDetails.setIsAdminApprovePre(false);
				}
			}
			if(newsEventsEntryForm.getPrePostEventAdm()!=null && !newsEventsEntryForm.getPrePostEventAdm().isEmpty() && newsEventsEntryForm.getPrePostEventAdm().equalsIgnoreCase("Post-Event")){
				if(newsEventsEntryForm.getPostAdminStatus()!=null && !newsEventsEntryForm.getPostAdminStatus().isEmpty()){
					newsEventsDetails.setPostApproveStatus(newsEventsEntryForm.getPostAdminStatus());
					newsEventsDetails.setIsAdminApprovePost(true);
				}else
				{
					newsEventsDetails.setIsAdminApprovePost(false);
				}
			}

			newsEventsDetails.setModifiedBy(newsEventsEntryForm.getUserId());
			newsEventsDetails.setLastModifiedDate(new Date());
			session.update(newsEventsDetails);
			transaction.commit();
			isUpdated = true;
		} catch (Exception e) {
			isUpdated = false;
			log.error("Unable to updateMobNewsEventDetails" , e);
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
		log.info("end of updateMobNewsEventDetails in NewsEventsDetailsTransactionImpl class.");
		return isUpdated;
	}


	/* (non-Javadoc)
	 * @see com.kp.cms.transactions.admin.INewsEventsDetailsTransaction#deletePhoto(int)
	 */
	@Override
	public boolean deletePhoto(int photoId, String userId) throws Exception {
		
		log.info("call of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			NewsEventsPhoto photo = (NewsEventsPhoto) session.createQuery("from NewsEventsPhoto n where n.id="+photoId).uniqueResult();
			photo.setLastModifiedDate(new Date());
			photo.setModifiedBy(userId);
			photo.setIsActive(Boolean.FALSE);
			if(photo.getNewsEventsId() != null){
				NewsEventsDetails details = new NewsEventsDetails();
				details.setId(photo.getNewsEventsId().getId());
				photo.setNewsEventsId(details);
			}
			session.update(photo);
			transaction.commit();
		} catch (Exception e) {
			return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return true;
	}
public boolean deleteResourse(int resourseId, String userId) throws Exception {
		
		log.info("call of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		Session session = null;
		Transaction transaction = null;
		try
		{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			NewsEventsResourse res = (NewsEventsResourse) session.createQuery("from NewsEventsResourse n where n.id="+resourseId).uniqueResult();
			res.setLastModifiedDate(new Date());
			res.setModifiedBy(userId);
			res.setIsActive(Boolean.FALSE);
			if(res.getNewsEventsId() != null){
				NewsEventsDetails details = new NewsEventsDetails();
				details.setId(res.getNewsEventsId().getId());
				res.setNewsEventsId(details);
			}
			session.update(res);
			transaction.commit();
		} catch (Exception e) {
			return false;
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		log.info("end of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
		return true;
	}
public boolean deleteContact(int contactId, String userId) throws Exception {
	
	log.info("call of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
	Session session = null;
	Transaction transaction = null;
	try
	{
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		NewsEventsContactDetails contact = (NewsEventsContactDetails) session.createQuery("from NewsEventsContactDetails n where n.id="+contactId).uniqueResult();
		contact.setLastModifiedDate(new Date());
		contact.setModifiedBy(userId);
		contact.setIsActive(Boolean.FALSE);
		if(contact.getNewsEventsId() != null){
			NewsEventsDetails details = new NewsEventsDetails();
			details.setId(contact.getNewsEventsId().getId());
			contact.setNewsEventsId(details);
		}
		session.update(contact);
		transaction.commit();
	} catch (Exception e) {
		return false;
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	log.info("end of deleteNewsEventsDetails in NewsEventsDetailsTransactionImpl class.");
	return true;
}
public boolean deleteParticipants(int partId, String userId) throws Exception {
	
	Session session = null;
	Transaction transaction = null;
	try
	{
		session = HibernateUtil.getSession();
		transaction = session.beginTransaction();
		NewsEventsParticipants part = (NewsEventsParticipants) session.createQuery("from NewsEventsParticipants n where n.id="+partId).uniqueResult();
		part.setLastModifiedDate(new Date());
		part.setModifiedBy(userId);
		part.setIsActive(Boolean.FALSE);
		if(part.getNewsEventsId() != null){
			NewsEventsDetails details = new NewsEventsDetails();
			details.setId(part.getNewsEventsId().getId());
			part.setNewsEventsId(details);
		}
		session.update(part);
		transaction.commit();
	} catch (Exception e) {
		return false;
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	log.info("end of deleteParticipants in NewsEventsDetailsTransactionImpl class.");
	return true;
}
	
	public Employee getEmployeeIdFromUserId(NewsEventsEntryForm newsForm) throws Exception {
		String userId=newsForm.getUserId();
		Session session=null;
		Employee employee=null;
		try {
		session=HibernateUtil.getSession();
			Query query=session.createQuery("select u.employee from Users u where u.isActive=1 and u.id="+userId);
			employee=(Employee) query.uniqueResult();

	}catch (Exception exception) {
		
		throw new ApplicationException();
	}finally{
		if(session!=null){
			session.flush();
		}
	}
		return employee;
	}
	

}

