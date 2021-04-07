package com.kp.cms.transactionsimpl.admission;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.ExamCenter;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admin.InterviewSelected;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admission.AcknowledgeNumber;
import com.kp.cms.bo.admission.ApplnAcknowledgement;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.bo.admission.InterviewTimeSelection;
import com.kp.cms.bo.admission.InterviewVenueSelection;
import com.kp.cms.bo.admission.ReceivedThrough;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.AdmissionFormForm;
import com.kp.cms.forms.admission.ApplicationAcknowledgeForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.handlers.admission.StudentSearchHandler;
import com.kp.cms.to.admission.InterviewCardTO;
import com.kp.cms.to.admission.StudentSearchTO;
import com.kp.cms.transactions.admission.IApplicationAcknowledgeTxn;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;
import com.kp.cms.utilities.InitSessionFactory;

public class ApplicationAcknowledgeTxnImpl implements IApplicationAcknowledgeTxn{
	private static final Log log = LogFactory
	.getLog(ApplicationAcknowledgeTxnImpl.class);
	private static volatile ApplicationAcknowledgeTxnImpl acknowledgeTxn = null;
	public static ApplicationAcknowledgeTxnImpl getInstance(){
		if(acknowledgeTxn == null){
			acknowledgeTxn = new  ApplicationAcknowledgeTxnImpl();
			return acknowledgeTxn;
		}
		return acknowledgeTxn;
	}
	@Override
	public boolean saveAcknowledge(ApplnAcknowledgement acknowledge,ApplicationAcknowledgeForm appnAcknowledgementForm)
			throws Exception {
		Session session =null;
		Transaction transaction =null;
		boolean flag =false;
		String[] selectedCandidatesList;
		List<StudentSearchTO> totalStudentList;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(appnAcknowledgementForm.getMode().equalsIgnoreCase("save"))
			      session.save(acknowledge);
			else if(appnAcknowledgementForm.getMode().equalsIgnoreCase("update")){
				session.update(acknowledge);
			}
			transaction.commit();
			
			String quer = "from AdmAppln appln where appln.applnNo="+Integer.parseInt(acknowledge.getApplnNo());
			Query query = session.createQuery(quer);
			AdmAppln  appln = (AdmAppln)query.uniqueResult();
			if(appln !=null){
				selectedCandidatesList=new String[]{String.valueOf(appln.getId())};
				totalStudentList=new ArrayList<StudentSearchTO>();
				StudentSearchTO to=new StudentSearchTO();
				to.setAdmApplnId(String.valueOf(appln.getId()));
				String que1 = "select i.id from InterviewProgramCourse i where i.course.id=:courseId and i.year=:year and i.sequence=1 and i.isActive=1";
				Query query1 = session.createQuery(que1);
				query1.setInteger("courseId", appln.getCourseBySelectedCourseId().getId());
				query1.setInteger("year", appln.getAppliedYear());
				Integer  interPrgCrsId = (Integer)query1.uniqueResult();
				if(interPrgCrsId!=null){
				to.setInterviewProgCrsId(interPrgCrsId.toString());
				totalStudentList.add(to);
					if(appnAcknowledgementForm.getMode().equalsIgnoreCase("save")){
						StudentSearchHandler.getInstance().updateSelectedCandidates(selectedCandidatesList, totalStudentList, appnAcknowledgementForm.getUserId());
					}
				}
				else {
					throw new BusinessException();
				}
			//	appln.setAdmStatus(CMSConstants.ADM_STATUS_OFFLINE_APPLN);
			//	session.update(appln);
			}else{
				if(appnAcknowledgementForm.getMobileNo()!=null && !appnAcknowledgementForm.getMobileNo().isEmpty()){
					String mobileNo="91";
						   mobileNo=mobileNo+appnAcknowledgementForm.getMobileNo();
						 //Application No Added By Manu	
							if(mobileNo.length()==12){
								ApplicationStatusUpdateHandler.getInstance().sendSMSToStudent(mobileNo,CMSConstants.OFFLINE_APPLN_ACKNOWLEDGEMENT_SMS,appnAcknowledgementForm.getAppNo());
					}
				}
			}
			flag =true;
		}
		catch(BusinessException exception){
			throw new BusinessException();
		}
		catch(Exception exception){
			if (transaction != null) {
			transaction.rollback();
			}
			flag = false;
		}
		finally{
			if (session != null && session.isOpen()){
				session.flush();
				session.close();
			}
		}
		return flag;
	}
	@Override
	public ApplnAcknowledgement getDetails(ApplicationAcknowledgeForm appnAcknowledgementForm) throws Exception {
		Session session=null;
		ApplnAcknowledgement appln = null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ApplnAcknowledgement apln where apln.applnNo="+Integer.parseInt(appnAcknowledgementForm.getAppNo())+"";
			String quer1 = "from AdmAppln appln where appln.appliedYear="+appnAcknowledgementForm.getYear()+" and appln.applnNo="+Integer.parseInt(appnAcknowledgementForm.getAppNo());
			Query query = session.createQuery(quer);
			appln = (ApplnAcknowledgement)query.uniqueResult();
			Query query1 = session.createQuery(quer1);
			AdmAppln admAppln = (AdmAppln)query1.uniqueResult();
			if(admAppln!=null){
				appnAcknowledgementForm.setAvailability(true);
				appnAcknowledgementForm.setName(admAppln.getPersonalData().getFirstName());
				appnAcknowledgementForm.setCourseId(String.valueOf(admAppln.getCourseBySelectedCourseId().getId()));
				appnAcknowledgementForm.setDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(admAppln.getPersonalData().getDateOfBirth()), "dd-MMM-yyyy", "dd/MM/yyyy"));
				if(admAppln.getPersonalData()!=null)
				appnAcknowledgementForm.setMobileNo(admAppln.getPersonalData().getMobileNo2()!=null?admAppln.getPersonalData().getMobileNo2():"");
				Set<ApplnDoc>  appDoc=admAppln.getApplnDocs();
				if(appDoc!=null){
					Iterator<ApplnDoc> itr=appDoc.iterator();
					while(itr.hasNext()){
						ApplnDoc appdoc=(ApplnDoc)itr.next();
						if(appdoc.getIsPhoto()&& (appdoc.getDocument()!=null && appdoc.getDocument().length > 0)){
							appnAcknowledgementForm.setIsPhotoUpload(true);
						}
					}
				}
			}else
				appnAcknowledgementForm.setAvailability(false);
			
		}catch(Exception exp){
			throw new BusinessException(exp);
		}
		return appln;
	}
	@Override
	public Organisation getOrganizationDetails() throws Exception {
		Session session = null;
		Organisation org=null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from Organisation org";
			Query query = session.createQuery(quer);
			org = (Organisation)query.uniqueResult();
			
		}catch(Exception exp){
			throw new BusinessException(exp);
		}
		return org;
	}
	@Override
	public int getAcknowledgeId(
			String applnNO)
			throws Exception {
		Session session = null;
		int id=0;
		try{
			session = HibernateUtil.getSession();
			String quer="select appln.id from ApplnAcknowledgement appln where appln.applnNo='"+applnNO+"'";
			Query query = session.createQuery(quer);
			id = (Integer)query.uniqueResult();
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return id;
	}
	@Override
	public AcknowledgeNumber getSlipNumber() throws Exception {
		Session session = null;
		AcknowledgeNumber number = null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from AcknowledgeNumber number where number.isActive=1";
			Query query = session.createQuery(quer);
			number = (AcknowledgeNumber)query.uniqueResult();
		}catch(Exception ex){
			throw new BusinessException(ex);
		}
		return number;
	}
	@Override
	public void saveAckNumber(AcknowledgeNumber number,String mode) throws Exception {
		Session session = null;
		Transaction transaction = null;
		try{
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			if(mode.equalsIgnoreCase("save")){
				session.save(number);
			}else
				session.update(number);
			transaction.commit();
		}catch(Exception ex){
			if(transaction!=null){
				transaction.rollback();
			}
			throw new BusinessException(ex);
		}
		
	}
	@Override
	public ReceivedThrough getslipRequired(String receivedThrough) throws Exception {
		Session session = null;
		ReceivedThrough received = null;
		try{
			session = HibernateUtil.getSession();
			String quer= "from ReceivedThrough receive where receive.receivedThrough='"+receivedThrough+"'";
			Query query = session.createQuery(quer);
			received = (ReceivedThrough)query.uniqueResult();
		}catch(Exception ex){
			throw new BusinessException(ex);
		}
		return received;
	}
	@Override
	public ApplnAcknowledgement getApplnAcknowledgement(String appNo)
			throws Exception {
		Session session = null;
		ApplnAcknowledgement appln = null;
		try{
			session = HibernateUtil.getSession();
			String quer = "from ApplnAcknowledgement appln where appln.applnNo='"+appNo+"'";
			Query query = session.createQuery(quer);
			appln = (ApplnAcknowledgement)query.uniqueResult();
		}catch(Exception e){
			throw new BusinessException(e);
		}
		return appln;
	}
	
	
	
	public void getInterviewVenueFromExamCenter(String examCenter, String InterviewSelectionDate, ApplicationAcknowledgeForm admForm)throws Exception{
		InterviewVenueSelection venue;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from InterviewVenueSelection v where v.isActive=1 and v.interviewSelectionSchedule.id='"+InterviewSelectionDate+"' and v.examCenter.id='"+examCenter+"'");
			venue = (InterviewVenueSelection) query.uniqueResult();
			if(venue.getExamCenter().getId()>0){
				admForm.setInterviewVenue(String.valueOf(venue.getId()));
				admForm.setSelectedVenue(venue.getExamCenter().getCenter());
			}
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	
	public void getDateFromSelectionProcessId(String InterviewSelectionDate, ApplicationAcknowledgeForm admForm)throws Exception
	{
		InterviewSelectionSchedule schedule;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			
			Query query = session.createQuery("from InterviewSelectionSchedule v where v.isActive=1 and v.id='"+InterviewSelectionDate+"'");
			schedule = (InterviewSelectionSchedule) query.uniqueResult();
			if(schedule!=null && schedule.getSelectionProcessDate()!=null){
				admForm.setSelectedDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(schedule.getSelectionProcessDate()), "dd-MMM-yyyy", "dd/MM/yyyy"));
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
	}
	
	
	@SuppressWarnings("null")
	/*public InterviewCardTO getInterviewScheduleDetails(ApplicationAcknowledgeForm admForm)throws Exception{
		int j=0;
		int i=1;
		InterviewCardTO cardTo=new InterviewCardTO();
		
		Object[] obj, ObjExamCenter;
		Session session = null;
		String Venue=null;
		try {
			session = HibernateUtil.getSession();
			Query examCenterQuery = session.createQuery("select ec.address1, ec.address2, ec.center "+
					"from InterviewVenueSelection ivs "+
					"join ivs.examCenter ec "+
					"where ivs.isActive=1 and ec.isActive=1 and ivs.id= "+admForm.getInterviewVenue());	
			ObjExamCenter = (Object[]) examCenterQuery.uniqueResult();
					if(ObjExamCenter!=null){
						if(ObjExamCenter[0]!=null && ObjExamCenter[2]!=null ){
							Venue=ObjExamCenter[0].toString()+","+ObjExamCenter[2].toString();
						}
					}
			
			Query query1 = session.createQuery("from InterviewTimeSelection t where t.interviewSelectionSchedule.id="+admForm.getInterviewSelectionDate()+" order by t.time");
			List<InterviewTimeSelection> time = query1.list();
			if(time!=null){
				Iterator<InterviewTimeSelection> interviewTimeSelection= time.iterator();
				while (interviewTimeSelection.hasNext()) {
					InterviewTimeSelection timeSelection = (InterviewTimeSelection) interviewTimeSelection.next();
					Query query = session.createQuery("select count(a.id),iss.maxNumOfSeatsOffline, t.maxSeats, card.time ," +
													" iss.selectionProcessDate, v.examCenter.center, v.examCenter.id, v.examCenter.address1, v.examCenter.address2"+
													" from AdmAppln a "+
													" left join a.interScheduleSelection iss "+
													"left join iss.interviewVenueSelections v "+
													"left join iss.interviewTimeSelections t "+
													"left join a.interviewCards card "+
													"left join v.examCenter e "+
													"where a.interScheduleSelection.id="+ admForm.getInterviewSelectionDate()
													+" and a.examCenter.id="+admForm.getInterviewVenue()+" and a.appliedYear="+admForm.getApplicationYear()
													+" and v.id="+admForm.getInterviewVenue()
													+" and t.time='"+timeSelection.getTime().toString()+"' and card.time='"+timeSelection.getTime().toString()+"' group by a,t");
					obj = (Object[]) query.uniqueResult();
					if(obj!=null){
						if(obj[0].toString()!=null && Integer.parseInt(obj[0].toString())>0)
						{
							if(Integer.parseInt(obj[0].toString())<Integer.parseInt(obj[2].toString())){
								
								if(obj[4].toString()!=null){
									cardTo.setInterviewDate(obj[4].toString());
									cardTo.setInterviewDate(obj[4].toString());
								}
								if(obj[3].toString()!=null){
									cardTo.setTime(obj[3].toString());
								}
								if(obj[5].toString()!=null){
									cardTo.setVenue(obj[7].toString()+"," + obj[5].toString());
								}
								break;
							}else
							{
								break;
							}
						}
					}else
					{
						cardTo.setAdmApplnId(Integer.parseInt(admForm.getAdmApplnId()));
						cardTo.setInterviewDate(admForm.getSelectedDate());
						cardTo.setTime(timeSelection.getTime().toString());
						if(Venue!=null)
							cardTo.setVenue(Venue);
						else
							cardTo.setVenue(admForm.getSelectedVenue());
						break;
					}
				}
				
			}
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return cardTo;
	}*/
	
	
	public InterviewCardTO getInterviewScheduleDetails(ApplicationAcknowledgeForm admForm)throws Exception{
		InterviewCardTO cardTo=new InterviewCardTO();
		List<Object[]> inter;
		Object[]ObjExamCenter;
		Session session = null;
		String Venue=null;
		try {
			session = HibernateUtil.getSession();
			Query examCenterQuery = session.createQuery("select ec.address1, ec.address2, ec.center "+
			"from ExamCenter ec "+
			"where ec.isActive=1 and ec.id= "+admForm.getInterviewVenue());	
			ObjExamCenter = (Object[]) examCenterQuery.uniqueResult();
			if(ObjExamCenter!=null){
				if(ObjExamCenter[0]!=null && ObjExamCenter[1]!=null && ObjExamCenter[2]!=null ){
					Venue=ObjExamCenter[0].toString()+","+ObjExamCenter[1].toString()+","+ObjExamCenter[2].toString();
				}
			}
			Query query1 = session.createSQLQuery("select interview_time_selection.max_seats, "+
												"count(distinct interview_card.adm_appln_id) as total_generated, "+
												"interview_time_selection.time, "+
												"interview_time_selection.end_time, date_format(interview_selection_schedule.selection_process_date,'%d/%m/%Y') "+
												"from interview_time_selection "+
												"inner join interview_selection_schedule ON interview_time_selection.select_schedule_id = interview_selection_schedule.id "+
												"left join interview_schedule on interview_schedule.interview_selection_schedule_id = interview_selection_schedule.id "+
												/*"and interview_time_selection.time=interview_schedule.start_time "+
												"and interview_time_selection.end_time=interview_schedule.end_time "+*/
												"left join interview_card on interview_card.interview_schedule_id = interview_schedule.id "+
												"and interview_card.reporting_time=interview_time_selection.time "+
												"where interview_selection_schedule.id="+ admForm.getInterviewSelectionDate()
												+" and interview_time_selection.is_active=1 "+
												"group by interview_selection_schedule.id,interview_time_selection.time "+
												"order by interview_time_selection.time");
		inter =  query1.list();
			
		if(inter!=null){
			
		Iterator<Object[]> itr= inter.iterator();
		while (itr.hasNext()) {
			boolean flag=false;
			Object[] obj = (Object[]) itr.next();
			
				if(obj[0].toString()!=null && Integer.parseInt(obj[0].toString())>0)
				{
					if(Integer.parseInt(obj[0].toString())>Integer.parseInt(obj[1].toString()))
					{
						if(obj[4].toString()!=null){
							cardTo.setInterviewDate(obj[4].toString());
							//cardTo.setInterviewDate(obj[4].toString());
						}
						if(obj[2].toString()!=null){
							cardTo.setTime(obj[2].toString());
						}
						if(obj[3].toString()!=null){
							cardTo.setEndtime(obj[3].toString());
						}
						if(Venue!=null){
							cardTo.setVenue(Venue);
						}
						cardTo.setAdmApplnId(Integer.parseInt(admForm.getAdmApplnId()));
						flag=true;
					}
				}
				if(flag){
					break;
				}
			}
		}
			
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
				session.close();
			}
		}
		return cardTo;
	}
	
	public InterviewCard getInterviewCardBo(int admApplnId, int interviewScheduleId) throws Exception {
		InterviewCard cardBo = null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewCard e" +
					" where e.admAppln.id= " + admApplnId+" and e.interview.id="+interviewScheduleId);
			cardBo = (InterviewCard)query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return cardBo;
	}
	
	
	public InterviewSchedule getInterviewSchedule(InterviewCardTO interviewCardTO, ApplicationAcknowledgeForm admForm) throws Exception {
		InterviewSchedule interviewSchedule = null;
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewSchedule e" +
					" where e.date= '" +CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate())  + "' and e.startTime = '" + interviewCardTO.getTime()+
					"' and e.selectionScheduleId='"+admForm.getInterviewSelectionDate()+"' and e.interview.interviewProgramCourse.id=(select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");
			interviewSchedule=(InterviewSchedule) query.uniqueResult();
			if(interviewSchedule==null){
				Interview interview=null;
				//Set<InterviewSchedule> interviewScheduleSet;
				Query que = session.createQuery("from Interview e" +
						" where e.year="+admForm.getApplicationYear()+" and e.interviewProgramCourse.id=(select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
						+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");
						
				List<Interview> intList=que.list();
				if(intList!=null && !intList.isEmpty()){
				 interview=intList.get(0);
				}
				else{
					transaction = session.beginTransaction();
					interview=new Interview();
					InterviewProgramCourse interviewProgramCourse=new InterviewProgramCourse();
					if(interviewCardTO.getInterviewPrgCrsId()==0){
						int interviewPgmCourse= getInterviewPrgCourse(admForm);
						interviewProgramCourse.setId(interviewPgmCourse);
					}else
					{
						interviewProgramCourse.setId(interviewCardTO.getInterviewPrgCrsId());
					}
					interview.setInterviewProgramCourse(interviewProgramCourse);
					interview.setYear(interviewCardTO.getAppliedYear());
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
					interviewSchedule.setDate(CommonUtil.ConvertStringToSQLDate(interviewCardTO.getInterviewDate()));
					interviewSchedule.setStartTime(interviewCardTO.getTime());
					interviewSchedule.setEndTime(interviewCardTO.getEndtime());
					InterviewSelectionSchedule sc=new InterviewSelectionSchedule();
					sc.setId(Integer.parseInt(admForm.getInterviewSelectionDate()));
					interviewSchedule.setSelectionScheduleId(sc);
					interviewSchedule.setVenue(interviewCardTO.getVenue());
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
		
	public boolean addSelectionProcessWorkflowData(List<InterviewCard> interviewCardsToSave,String user, ApplicationAcknowledgeForm admForm, Integer interViewPgmCourse) throws Exception {
		
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
								" and e.interview.interview.interviewProgramCourse.id = (select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");

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
								" and e.interviewProgramCourse.id = (select p.id from InterviewProgramCourse p where p.program.id='"+admForm.getProgramId() 
								+"' and p.course.id='"+admForm.getCourseId()+"' and p.sequence=1 and p.year='"+admForm.getApplicationYear()+"' and p.isActive=1)");

						List<InterviewSelected> selectedList = que.list();
						if(selectedList!= null && selectedList.size() > 0){
							interSelected = selectedList.get(0);
							interSelected.setIsCardGenerated(true);
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(user);
							session.update(interSelected);
						}
						else{
							interSelected=new InterviewSelected();
							InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
							interviewProgramCourse.setId(interViewPgmCourse);
							interSelected.setInterviewProgramCourse(interviewProgramCourse);
							AdmAppln adm=new AdmAppln();
							adm.setId(card.getAdmAppln().getId());
							interSelected.setAdmAppln(adm);
							interSelected.setIsCardGenerated(true);
							interSelected.setCreatedBy(user);
							interSelected.setCreatedDate(new java.util.Date());
							interSelected.setLastModifiedDate(new java.util.Date());
							interSelected.setModifiedBy(user);
							session.save(interSelected);
						}
						if (++count % 20 == 0) {
							session.flush();
							session.clear();
						}
				}
				admForm.setAdmApplnId(String.valueOf(card.getAdmAppln().getId()));
					
			}
			transaction.commit();
			session.flush();
			session.close();
			if(admForm.isExamCenterRequired()){
				updateSeatNo(Integer.parseInt(admForm.getAdmApplnId()));
			}		
			
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

	
	
	public AdmAppln getAdmApplnId(ApplicationAcknowledgeForm admForm) throws Exception
	{
		AdmAppln appln=null;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query=session.createQuery("from AdmAppln a where a.applnNo="+admForm.getAppNo()+" and a.isCancelled=0 ");
			appln = (AdmAppln)query.uniqueResult();
		} catch (Exception e) {
			throw new ApplicationException(e);
		} finally {
			if (session != null) {
				session.flush();
			}
		}
		return appln;
		
	}
	
	public Integer getExamCenter(int programId, int examCenterId) throws Exception {
		log.info("entered getProgramDetails..");
		Session session = null;
		int currentSeatNo = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ExamCenter e where e.id = :examCenterId and e.program.id = :programId");
			query.setInteger("examCenterId", examCenterId);
			query.setInteger("programId", programId);
			List<ExamCenter> examCenterList = query.list();
			if(examCenterList!= null && examCenterList.size()!= 0){
				ExamCenter examCenter = (ExamCenter) examCenterList.get(0);
				currentSeatNo = examCenter.getCurrentSeatNo();
			}
			session.flush();
			return currentSeatNo;
		} catch (Exception e) {
			session.flush();
			session.close();
			log.info("Error while getting Program Details" + e);
			throw new ApplicationException(e);
		}
	}
	
	public void updateSeatNo(int applnId) throws ApplicationException {
		Session session = null;
		Transaction transaction = null;
		Transaction transaction1 = null;
		try {
			session = HibernateUtil.getSession();
			transaction = session.beginTransaction();
			transaction.begin();
			
				AdmAppln admAppln = (AdmAppln) session.get(AdmAppln.class, applnId);
				if(admAppln.getExamCenter() != null){
					int programId = admAppln.getCourseBySelectedCourseId().getProgram().getId(); 
					int examCenterId = admAppln.getExamCenter().getId();
					int seatNo = getExamCenter(programId,examCenterId); 
					if((admAppln.getSeatNo() == null || admAppln.getSeatNo().trim().isEmpty()) && seatNo > 0){
						admAppln.setSeatNo(Integer.toString(seatNo));
						session.update(admAppln);
						transaction.commit();
						transaction1=session.beginTransaction();
						int newSeatNo = seatNo + 1;
						ExamCenter examCenter = (ExamCenter) session.get(ExamCenter.class, examCenterId);
						examCenter.setCurrentSeatNo(newSeatNo);
						session.update(examCenter);
						transaction1.commit();
					}
			}
			
		} catch (Exception e) {
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
	}
		
	public int getInterviewPrgCourse (ApplicationAcknowledgeForm admForm)throws ApplicationException {
		log.info("entered getProgramDetails..");
		Session session = null;
		int InterviewPrgCourseId = 0;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from InterviewProgramCourse pc "
											+" where pc.program.id='"+admForm.getProgramId()+"'" +
											"and pc.course.id='"+admForm.getCourseId()+"' and pc.year='"+admForm.getApplicationYear()+"'" +
											" and pc.isActive=1 and pc.sequence=1 ");
			List<InterviewProgramCourse> List = query.list();
			if(List!= null && List.size()!= 0){
				InterviewProgramCourse pgmCourse = (InterviewProgramCourse) List.get(0);
				InterviewPrgCourseId = pgmCourse.getId();
			}
			session.flush();
			return InterviewPrgCourseId;
		} catch (Exception e) {
			session.flush();
			throw new ApplicationException(e);
		}
	}
public Integer getInterViewPgmCourse(ApplicationAcknowledgeForm admForm)throws Exception {
		
		Integer interPgmCourse = 0;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
			Query query = session.createQuery("select i.id from InterviewProgramCourse i " +
					"where i.program.id='"+admForm.getProgramId()+"' and i.course.id='"+admForm.getCourseId()+"' and i.year='"+admForm.getApplicationYear()+"' " +
							"and i.sequence=1 and i.isActive=1");
			interPgmCourse = (Integer) query.uniqueResult();
		} catch (Exception e) {
			throw  new ApplicationException(e);
		}
		return interPgmCourse;
	}	
	

}
