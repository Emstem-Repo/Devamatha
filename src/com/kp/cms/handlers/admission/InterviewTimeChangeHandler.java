package com.kp.cms.handlers.admission;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Interview;
import com.kp.cms.bo.admin.InterviewCard;
import com.kp.cms.bo.admin.InterviewCardHistory;
import com.kp.cms.bo.admin.InterviewSchedule;
import com.kp.cms.bo.admission.InterviewSelectionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewTimeChangeForm;
import com.kp.cms.helpers.admission.InterviewTimeChangeHelper;
import com.kp.cms.to.admission.InterviewTimeChangeTO;
import com.kp.cms.transactions.admission.IInterviewChangeTimeTxn;
import com.kp.cms.transactionsimpl.admission.InterviewChangeTimeTxnImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

/**
 * @author kalyan.c
 *
 */
public class InterviewTimeChangeHandler {
	private static volatile InterviewTimeChangeHandler interviewTimeChangeHandler = null;
	private static Log log = LogFactory.getLog(InterviewTimeChangeHandler.class);
	private InterviewTimeChangeHandler() {

	}

	/**
	 * @return
	 * This method will return instance of the class 
	 * 
	 */
	public static InterviewTimeChangeHandler getInstance() {
		if (interviewTimeChangeHandler == null) {
			interviewTimeChangeHandler = new InterviewTimeChangeHandler();
		}
		return interviewTimeChangeHandler;
	}

	/**
	 * @param interviewTimeChangeForm
	 * @return
	 * @throws Exception
	 * This method will list the candidates based on the search criteria
	 */
	public List<InterviewTimeChangeTO> getAddIntResultCandidates(InterviewTimeChangeForm interviewTimeChangeForm) throws Exception {
		
		IInterviewChangeTimeTxn interveiwChangeTime = InterviewChangeTimeTxnImpl.getInstance();
		InterviewTimeChangeHelper interviewTimeChangeHelper = new InterviewTimeChangeHelper();
		
		InterviewTimeChangeTO interviewBatchEntryTO = interviewTimeChangeHelper.convertFormtoTo(interviewTimeChangeForm);
		String interviewTypeArray[] = interviewTimeChangeForm.getInterviewType();
		Set<Integer> interviewTypSet = new HashSet<Integer>();
		for(int i=0 ;i<interviewTypeArray.length;i++) {
			interviewTypSet.add(Integer.valueOf(interviewTypeArray[i]));
		}
		List<Integer> alreadyDataEnter= interveiwChangeTime.getAdmApplnResultEntered(interviewBatchEntryTO,interviewTypSet);
		List getSelectedCandidatesList = interveiwChangeTime.getSelectedCandidates(interviewBatchEntryTO,interviewTypSet);
		Map<Integer, String> historyMap = interveiwChangeTime.getHistoryMap();
		List<InterviewTimeChangeTO> selectedCandidates = interviewTimeChangeHelper.convertBotoTo(getSelectedCandidatesList,alreadyDataEnter,historyMap);

		return selectedCandidates;
	}

	/**
	 * @param interviewTimeChangeForm
	 * @return
	 * @throws Exception
	 * This method will update the time
	 */
	public boolean resultBatchUpdate(InterviewTimeChangeForm interviewTimeChangeForm) throws Exception {
		
		IInterviewChangeTimeTxn interveiwChangeTime = InterviewChangeTimeTxnImpl.getInstance();
		InterviewTimeChangeHelper interviewTimeChangeHelper = new InterviewTimeChangeHelper();
		boolean batchResultUpdated = false;
		String userId = interviewTimeChangeForm.getUserId();
		
		List<InterviewTimeChangeTO> list = interviewTimeChangeForm.getSelectedCandidates();
		
		List<InterviewCard> interviewCardList = interviewTimeChangeHelper.convertTotoBo(list, userId);
		
		batchResultUpdated = interveiwChangeTime.batchResultUpdate(interviewCardList);
		
		return batchResultUpdated;
	}

	
	/**
	 * @param interviewTimeChangeTO
	 * @return
	 * This method will send mail to students whose time is updated
	 */
	public boolean sendMailToStudent(InterviewTimeChangeTO interviewTimeChangeTO) {
			boolean sent=false;
			Properties prop = new Properties();
			try {
				InputStream in = CommonUtil.class.getClassLoader()
						.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(in);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block

			log.error("Unable to read properties file...", e);
				return false;
			} catch (IOException e) {
				log.error("Unable to read properties file...", e);
				return false;
			}
//				String adminmail=prop.getProperty("knowledgepro.admin.mail");
				String adminmail=CMSConstants.MAIL_USERID;
				String toAddress=interviewTimeChangeTO.getEmail();
				String subject="Change in Interview Schedule";
				if(interviewTimeChangeTO.getApplicantName()!=null && interviewTimeChangeTO.getApplicationNo()!=null && interviewTimeChangeTO.getCourseName()!=null && interviewTimeChangeTO.getInterviewDate()!=null && interviewTimeChangeTO.getInterviewTime()!=null){
				String msg="<br>Application No:" + interviewTimeChangeTO.getApplicationNo() +
				"<br>Name of the Candidate:" + interviewTimeChangeTO.getApplicantName() +
				"<br>Course/Combination: "+interviewTimeChangeTO.getCourseName()+"<br>" +
				"<br>Interview Date: "+interviewTimeChangeTO.getInterviewDate()+"<br>" +
				"<br>Interview Time: "+interviewTimeChangeTO.getInterviewTime()+"<br>" +
						"Thanks,<br>Christ University Admin.<br>";
				
				MailTO mailto=new MailTO();
				mailto.setFromAddress(adminmail);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName("ChristUniversity");
				//sent=CommonUtil.sendMail("Christ University", adminmail, toAddress, subject, msg);
				sent=CommonUtil.postMail(mailto);
				}
			return sent;
	}
	/**
	 * @param interviewTimeChangeForm
	 * @return
	 * @throws Exception
	 * This method will update the time
	 */
	public boolean resultBatchUpdate1(InterviewTimeChangeForm interviewTimeChangeForm,HttpServletRequest request) throws Exception {
		
		IInterviewChangeTimeTxn interveiwChangeTime = InterviewChangeTimeTxnImpl.getInstance();
		boolean batchResultUpdated = false;
		String userId = interviewTimeChangeForm.getUserId();
		
		List<InterviewTimeChangeTO> list = interviewTimeChangeForm.getSelectedCandidates();
		
		List<InterviewCard> deleteinterviewCardList=new ArrayList<InterviewCard>();
//		List<InterviewCard> interviewCardList = interviewTimeChangeHelper.convertTotoBo(list, userId);
		List<InterviewCard> updateInterviewCardList = new ArrayList<InterviewCard>();
		List<InterviewSchedule> newSchedule=new ArrayList<InterviewSchedule>();
		List<String> cardIds = new ArrayList<String>();
		if (list != null) {
			Iterator<InterviewTimeChangeTO> it = list.iterator();

			while (it.hasNext()) {
				InterviewTimeChangeTO interviewTimeChangeTO = it.next();
				InterviewCard iCard = new InterviewCard();
				if(interviewTimeChangeTO!=null && interviewTimeChangeTO.getTimeID()!=null){
					iCard.setId(Integer.parseInt(interviewTimeChangeTO.getTimeID()));					
					if(!interviewTimeChangeTO.getInterviewTime().equals(interviewTimeChangeTO.getOldInterviewTime()) || !interviewTimeChangeTO.getInterviewDate().equals(interviewTimeChangeTO.getOldInterviewDate())){
						cardIds.add(interviewTimeChangeTO.getTimeID());
					}
				}	
				if(interviewTimeChangeTO!=null && interviewTimeChangeTO.getInterviewTime()!=null){
					iCard.setTime(interviewTimeChangeTO.getInterviewTime());				
				}
				if(interviewTimeChangeTO.getInterviewTime()!=null && interviewTimeChangeTO.getInterviewDate()!=null)
				{
					if(!interviewTimeChangeTO.getInterviewTime().equals(interviewTimeChangeTO.getOldInterviewTime()) && interviewTimeChangeTO.getInterviewDate().equals(interviewTimeChangeTO.getOldInterviewDate())){
						updateInterviewCardList.add(iCard);
					}else if(!interviewTimeChangeTO.getInterviewDate().equals(interviewTimeChangeTO.getOldInterviewDate())){
						deleteinterviewCardList.add(iCard);
						InterviewSchedule is=new InterviewSchedule();
						is.setStartTime(interviewTimeChangeTO.getStartTimeHours());
						is.setEndTime(interviewTimeChangeTO.getEndTimeHours());
						if(interviewTimeChangeTO.getSelectionProcessId()!=null && !interviewTimeChangeTO.getSelectionProcessId().isEmpty()){
							InterviewSelectionSchedule iss=new InterviewSelectionSchedule();
							iss.setId(Integer.parseInt(interviewTimeChangeTO.getSelectionProcessId()));
							is.setSelectionScheduleId(iss);
						}
						is.setCreatedDate(new Date());
						is.setCreatedBy(Integer.parseInt(userId));
						is.setLastModifiedDate(new Date());
						/* code added by sudhir */
						is.setModifiedBy(userId);
						/* code added by sudhir */
						is.setDate(CommonUtil.ConvertStringToSQLDate(interviewTimeChangeTO.getInterviewDate()));
						is.setTimeInterval(interviewTimeChangeTO.getTimeInterval());
						is.setVenue(interviewTimeChangeTO.getVenue());
						Interview i=new Interview();
						i.setId(interviewTimeChangeTO.getInterviewId());
						is.setInterview(i);
						InterviewCard ic1=new InterviewCard();
						ic1.setInterviewer(interviewTimeChangeTO.getInterviewers());
						ic1.setTime(interviewTimeChangeTO.getInterviewTime());
						AdmAppln admAppln=new AdmAppln();
						admAppln.setId(Integer.parseInt(interviewTimeChangeTO.getApplicationId()));
						ic1.setAdmAppln(admAppln);
						ic1.setCreatedBy(userId);
						ic1.setCreatedDate(new Date());
						ic1.setLastModifiedDate(new Date());
						ic1.setModifiedBy(userId);
						Set<InterviewCard> newSet=new HashSet<InterviewCard>();
						newSet.add(ic1);
						is.setInterviewCards(newSet);
						newSchedule.add(is);
					}
				}
			}
		}	
		interveiwChangeTime.addHistory(cardIds,userId);
		batchResultUpdated = interveiwChangeTime.batchResultUpdate1(updateInterviewCardList,deleteinterviewCardList,newSchedule,request,userId);
		
		return batchResultUpdated;
	}

	/**
	 * @param interviewTimeChangeForm
	 * @throws Exception
	 */
	public void getStudentRescheduledInfo(InterviewTimeChangeForm interviewTimeChangeForm) throws Exception{
		IInterviewChangeTimeTxn interveiwChangeTime = InterviewChangeTimeTxnImpl.getInstance();
		InterviewTimeChangeHelper helper = new InterviewTimeChangeHelper();
		List<InterviewCardHistory> boList = interveiwChangeTime.getScheduledHistory(interviewTimeChangeForm.getAppNo());
		List<InterviewTimeChangeTO> toList = helper.convertBoToTo(boList,interviewTimeChangeForm);
		interviewTimeChangeForm.setRescheduledHistory(toList);
	}

}
