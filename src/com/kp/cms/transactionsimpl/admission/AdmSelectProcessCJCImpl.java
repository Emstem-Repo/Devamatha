package com.kp.cms.transactionsimpl.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.University;
import com.kp.cms.bo.admission.AdmSelectionSettings;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.AdmSelectionProcessCJCForm;
import com.kp.cms.transactions.admission.IAdmSelectProcessCJCTransaction;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class AdmSelectProcessCJCImpl implements IAdmSelectProcessCJCTransaction{
	
	private static volatile AdmSelectProcessCJCImpl admSelectTxn = null;
	
	public static AdmSelectProcessCJCImpl getInstance(){
		if(admSelectTxn == null){
			admSelectTxn = new AdmSelectProcessCJCImpl();
			return admSelectTxn;
		}
		return admSelectTxn;
	}
	
	public Map<String, String> getReligionMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Religion r where r.isActive=true");
			List<Religion> list=query.list();
			if(list!=null){
				Iterator<Religion> iterator=list.iterator();
				while(iterator.hasNext()){
					Religion religion=iterator.next();
					if(religion!=null && religion.getName()!=null && !religion.getName().isEmpty() && religion.getId()>0)
					map.put(String.valueOf(religion.getId()),religion.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}/*finally{
			if(session!=null){
				session.flush();
			}
		}*/
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public Map<String, String> getCasteMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from Caste r where r.isActive=true");
			List<Caste> list=query.list();
			if(list!=null){
				Iterator<Caste> iterator=list.iterator();
				while(iterator.hasNext()){
					Caste cste=iterator.next();
					if(cste!=null && cste.getName()!=null && !cste.getName().isEmpty() && cste.getId()>0)
					map.put(String.valueOf(cste.getId()),cste.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}/*finally{
			if(session!=null){
				session.flush();
			}
		}*/
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	public Map<String, String> getInstutionMap() throws Exception {
		Session session=null;
		Map<String,String> map=new LinkedHashMap<String, String>();
		try{
			session=HibernateUtil.getSession();
			Query query=session.createQuery("from College r where r.isActive=true and r.university.id=3");
			List<College> list=query.list();
			if(list!=null){
				Iterator<College> iterator=list.iterator();
				while(iterator.hasNext()){
					College clge=iterator.next();
					if(clge!=null && clge.getName()!=null && !clge.getName().isEmpty() && clge.getId()>0)
					map.put(String.valueOf(clge.getId()),clge.getName());
				}
			}
		}catch (Exception exception) {
			// TODO: handle exception
			throw new ApplicationException();
		}/*finally{
			if(session!=null){
				session.flush();
			}
		}*/
		map = (Map<String, String>) CommonUtil.sortMapByValue(map);
		return map;
	}
	
	public Map<String, String> getCourseMap() throws Exception {
		Session session = null;
		Map<String,String> courseMap = new HashMap<String, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from Course course where course.isActive =1";
			Query query = session.createQuery(str);
			List<Course> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<Course> iterator = list.iterator();
				while (iterator.hasNext()) {
					Course course = (Course) iterator.next();
					courseMap.put(String.valueOf(course.getId()), course.getName());
				}
			}
			courseMap = CommonUtil.sortMapByValue(courseMap);
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
			return courseMap;
	}
	
	public Map<String, String> getUniversityMap() throws Exception
	{
		Session session = null;
		Map<String,String> universityMap = new HashMap<String, String>();
		try{
			session = HibernateUtil.getSession();
			String str = "from University u where u.isActive =1";
			Query query = session.createQuery(str);
			List<University> list = query.list();
			if(list!=null && !list.isEmpty()){
				Iterator<University> iterator = list.iterator();
				while (iterator.hasNext()) {
					University univ = (University) iterator.next();
					universityMap.put(String.valueOf(univ.getId()), univ.getName());
				}
			}
			universityMap = CommonUtil.sortMapByValue(universityMap);
		}catch (Exception e) {
			throw new ApplicationException(e);
		}finally {
			if (session != null) {
				session.flush();
				//session.close();
			}
		}
			return universityMap;
	}
	
	
	public List<AdmAppln> getRunSetDataToTable(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		String QUERY="";
		List<AdmAppln> listTO=null;
		
	QUERY = "from AdmAppln a where a.isCancelled=0 " +
				"and a.isBypassed=0 and a.isFinalMeritApproved=0 " +
				"and a.isSelected=0 and a.appliedYear="+admSelProcessCJCForm.getYear();
		  
		Query query = session.createQuery(QUERY);
		listTO= query.list();
		return listTO;
	}
	
	public List<AdmSelectionSettings> getAdmSelectionSettings(AdmSelectionProcessCJCForm admSelProcessCJCForm)throws Exception{
		
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		
		List<AdmSelectionSettings> listTO=null;
		StringBuffer query = new StringBuffer("from AdmSelectionSettings ss "+
				" inner join ss.admSelectionSettingsDetails ssd "+
				"where ss.isActive=1 and ssd.isActive=1 and ss.batchYear="+ admSelProcessCJCForm.getAcademicYear()
				+ " and ss.listName=" +admSelProcessCJCForm.getListName());
			if(admSelProcessCJCForm.getUniversityId()!=null && !admSelProcessCJCForm.getUniversityId().isEmpty()){	
				query=query.append(" and ss.universityId="+admSelProcessCJCForm.getUniversityId());
			}
			query=query.append("order by ssd.selectionOrder");
			Query query1 = session.createQuery(query.toString());
			listTO= query1.list();
			return listTO;
	}

	@Override
	public boolean deleteAllData(List<AdmAppln> previousData,
			AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<AdmAppln> getpreviousData(
			AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
public boolean saveData(List<AdmAppln> allData, AdmSelectionProcessCJCForm admSelProcessCJCForm,int interPgmCourse) throws Exception {
		
		boolean result = false;
		Session session = null;
		Transaction txn = null;
		InterviewSchedule interviewSchedule;
		try {
			session = HibernateUtil.getSession();
			txn = session.beginTransaction();
			Iterator<AdmAppln>  itr=allData.iterator();
			while (itr.hasNext()) {
				AdmAppln app = (AdmAppln) itr.next();
				session.save(app);
				
				List<InterviewCard> interviewCardsToSave=new ArrayList<InterviewCard>();
			
							 interviewSchedule=getInterviewSchedule(admSelProcessCJCForm, interPgmCourse);
							if(interviewSchedule!=null){
								InterviewCard interviewCard=new InterviewCard();
								interviewCard.setAdmAppln(app);
								interviewCard.setInterview(interviewSchedule);
								interviewCard.setTime(admSelProcessCJCForm.getTime());
								interviewCard.setInterviewer(1);
								interviewCard.setCreatedBy(admSelProcessCJCForm.getUserId());
								interviewCard.setCreatedDate(new Date());
								interviewCard.setLastModifiedDate(new Date());
								interviewCard.setModifiedBy(admSelProcessCJCForm.getUserId());
								interviewCardsToSave.add(interviewCard);
							}
					addSelectionProcessWorkflowData(interviewCardsToSave,admSelProcessCJCForm,interPgmCourse);
				result = true;
			}
			txn.commit();
			session.flush();
		} catch (Exception e) {
			if (session.isOpen()) {
				txn.rollback();
			}
			throw new ApplicationException(e);
		}
		return result;
	}

public InterviewSchedule getInterviewSchedule(AdmSelectionProcessCJCForm admForm,int interviewPgmCourse) throws Exception {
	InterviewSchedule interviewSchedule = null;
	Session session = null;
	Transaction transaction = null;
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery("from InterviewSchedule e" +
				" where e.date= '" +CommonUtil.ConvertStringToSQLDate(admForm.getDate())  + "' and e.startTime = '" + admForm.getTime()
				+"' and e.interview.interviewProgramCourse.id='"+interviewPgmCourse 
				+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getBatchYear()+"' and p.isActive=1)");
		interviewSchedule=(InterviewSchedule) query.uniqueResult();
		if(interviewSchedule==null){
			Interview interview=null;
			//Set<InterviewSchedule> interviewScheduleSet;
			Query que = session.createQuery("from Interview e" +
					" where e.year="+admForm.getBatchYear()+" and e.interviewProgramCourse.id='"+interviewPgmCourse 
					+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getBatchYear()+"' and p.isActive=1)");
					
			List<Interview> intList=que.list();
			if(intList!=null && !intList.isEmpty()){
			 interview=intList.get(0);
			}
			else{
				transaction = session.beginTransaction();
				interview=new Interview();
				InterviewProgramCourse interviewProgramCourse=new InterviewProgramCourse();
				interviewProgramCourse.setId(interviewPgmCourse);
				interview.setInterviewProgramCourse(interviewProgramCourse);
				interview.setYear(Integer.parseInt(admForm.getBatchYear()));
				interview.setCreatedBy(admForm.getUserId());
				interview.setCreatedDate(new java.util.Date());
				interview.setModifiedBy(admForm.getUserId());
				interview.setLastModifiedDate(new java.util.Date());	
				session.save(interview);
				transaction.commit();
			}
			if(interview!=null && interview.getId()>0){
				transaction = session.beginTransaction();
				interviewSchedule=new InterviewSchedule();
				interviewSchedule.setInterview(interview);
				interviewSchedule.setDate(CommonUtil.ConvertStringToSQLDate(admForm.getDate()));
				interviewSchedule.setStartTime(admForm.getTime());
				interviewSchedule.setEndTime(admForm.getTime()+1);
				interviewSchedule.setVenue(admForm.getVenue());
				interviewSchedule.setCreatedBy(Integer.parseInt(admForm.getUserId()));
				interviewSchedule.setCreatedDate(new java.util.Date());
				interviewSchedule.setLastModifiedDate(new java.util.Date());
				interviewSchedule.setModifiedBy(admForm.getUserId());
				session.save(interviewSchedule);
				transaction.commit();
			}
		}
	} catch (Exception e) {
		if(transaction != null)
			transaction.rollback();
		throw  new ApplicationException(e);
		
	} finally {
		if (session != null) {
			session.flush();
			session.close();
		}
	}
	return interviewSchedule;
}

public List<InterviewProgramCourse> getInterviewPrgCourse(AdmSelectionProcessCJCForm admForm)throws Exception {
	List<InterviewProgramCourse> interPgmCourse =null;
	if (admForm.getCourseList()!=null && !admForm.getCourseList().isEmpty()) {
			
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery("from InterviewProgramCourse i where i.course.id in (:courseList) and i.year='"+admForm.getBatchYear()+"' and i.sequence=1 and i.isActive=1");
			query1.setParameterList("courseList", admForm.getCourseList());
			interPgmCourse =  query1.list();
		} catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
	}
	return interPgmCourse;
}

	@Override
	public List<Integer> getCourseIdsFromAdmSettings(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		List<Integer> CourseId=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery("select distinct(c.courseId.id) from AdmSelectionSettings c where c.isActive=1 and c.batchYear='"+admSelProcessCJCForm.getBatchYear()+"'");
			CourseId =  query1.list();
		} catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
	return CourseId;
	}
	
	public int getInterviewProgCourse(AdmSelectionProcessCJCForm admSelProcessCJCForm) throws Exception {
		Integer CourseId=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query1 = session.createQuery("select i.id from InterviewProgramCourse i where i.course.id ='"+admSelProcessCJCForm.getCourseId()+"' and i.year='"+admSelProcessCJCForm.getBatchYear()+"' and i.sequence=1 and i.isActive=1");
			CourseId =  (Integer) query1.uniqueResult();
		} catch (Exception e) 
		{
			throw  new ApplicationException(e);
		}
	return CourseId;
	}
	
public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave, AdmSelectionProcessCJCForm admForm,int interPgmCourse) throws Exception {
	
		Session session = null;
		Transaction transaction = null;
		boolean isAdd=false;
		InterviewCard oldInterviewCard=null;
		InterviewSelected interSelected=null;
		try {
			session = InitSessionFactory.getInstance().openSession();
			transaction = session.beginTransaction();
			if(interviewCardsToSave!=null && !interviewCardsToSave.isEmpty()){
				Iterator it = interviewCardsToSave.iterator();
				int count = 0;
				while (it.hasNext()) {
					InterviewCard card = (InterviewCard) it.next();
					if(card!=null){
						Query query = session.createQuery("FROM InterviewCard e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() + 
								" and e.interview.interview.interviewProgramCourse.id = "+interPgmCourse
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getBatchYear()+"' and p.isActive=1)");

						List<InterviewCard> cardList = query.list();
						if(cardList!= null && cardList.size() > 0){
							oldInterviewCard = cardList.get(0);
						}
						if(oldInterviewCard!= null){
							card.setId(oldInterviewCard.getId());
							session.merge(card);
						}
						else{
							session.save(card);
						}
						Query que = session.createQuery("From InterviewSelected e" +
								" where e.admAppln.id = " + card.getAdmAppln().getId() +
								" and e.interviewProgramCourse.id = "+interPgmCourse
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getBatchYear()+"' and p.isActive=1)");

						List<InterviewSelected> selectedList = que.list();
						if(selectedList!= null && selectedList.size() > 0){
							interSelected = selectedList.get(0);
							interSelected.setIsCardGenerated(true);
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(admForm.getUserId());
							session.update(interSelected);
						}
						else{
							interSelected=new InterviewSelected();
							InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
							interviewProgramCourse.setId(interPgmCourse);
							interSelected.setInterviewProgramCourse(interviewProgramCourse);
							AdmAppln adm=new AdmAppln();
							adm.setId(card.getAdmAppln().getId());
							interSelected.setAdmAppln(adm);
							interSelected.setIsCardGenerated(true);
							interSelected.setCreatedBy(admForm.getUserId());
							interSelected.setCreatedDate(new java.util.Date());
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(admForm.getUserId());
							session.save(interSelected);
						}
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
				}
			}
			transaction.commit();
			session.flush();
			session.close();
			isAdd=true;
			}
		}catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
				session.close();
			isAdd=false;
			throw  new ApplicationException(e);
		} finally {
			
		}
		return isAdd;
	
	}
}
