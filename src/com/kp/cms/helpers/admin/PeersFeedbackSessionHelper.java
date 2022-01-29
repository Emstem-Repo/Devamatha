package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.forms.admin.PeersFeedbackSessionForm;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;

public class PeersFeedbackSessionHelper {
	public static volatile PeersFeedbackSessionHelper  feedbackSessionHelper = null;
	public static PeersFeedbackSessionHelper getInstance(){
		if(feedbackSessionHelper == null){
			feedbackSessionHelper  = new PeersFeedbackSessionHelper();
			return feedbackSessionHelper;
		}
		return feedbackSessionHelper;
	}
	/**
	 * @param feedbackSessions
	 * @return
	 * @throws Exception
	 */
	public List<PeersFeedbackSessionTo> convertBOToTO( List<PeerFeedbackSession> feedbackSessions) throws Exception{
		List<PeersFeedbackSessionTo> sessionTos = new ArrayList<PeersFeedbackSessionTo>();
		if(feedbackSessions!=null && !feedbackSessions.isEmpty()){
			Iterator<PeerFeedbackSession> iterator = feedbackSessions.iterator();
			while (iterator.hasNext()) {
				PeerFeedbackSession peerFeedbackSession = (PeerFeedbackSession) iterator .next();
				PeersFeedbackSessionTo to = new PeersFeedbackSessionTo();
				if(peerFeedbackSession.getId()!=0){
					to.setId(peerFeedbackSession.getId());
				}
				if(peerFeedbackSession.getSession()!=null && !peerFeedbackSession.getSession().isEmpty()){
					to.setSessionName(peerFeedbackSession.getSession());
				}
				if(peerFeedbackSession.getMonth()!=null && !peerFeedbackSession.getMonth().isEmpty()){
					to.setMonth(peerFeedbackSession.getMonth());
				}
				if(peerFeedbackSession.getYear()!=null &&!peerFeedbackSession.getYear().isEmpty()){
					to.setYear(peerFeedbackSession.getYear());
				}
				if(peerFeedbackSession.getAcademicYear()!=null && !peerFeedbackSession.getAcademicYear().isEmpty()){
					to.setAcademicYear(peerFeedbackSession.getAcademicYear());
				}
				sessionTos.add(to);
			}
		}
		return sessionTos;
	}
	/**
	 * @param peersFeedbackSessionForm
	 * @return
	 * @throws Exception
	 */
	public  PeersFeedbackSessionTo convertDataFromFormToTO( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception{
		PeersFeedbackSessionTo sessionTo = new PeersFeedbackSessionTo();
		if(peersFeedbackSessionForm.getId()!=0){
			sessionTo.setId(peersFeedbackSessionForm.getId());
		}
		if(peersFeedbackSessionForm.getSessionName()!=null && !peersFeedbackSessionForm.getSessionName().isEmpty()){
			sessionTo.setSessionName(peersFeedbackSessionForm.getSessionName());
		}
		if(peersFeedbackSessionForm.getMonth()!=null && !peersFeedbackSessionForm.getMonth().isEmpty()){
			sessionTo.setMonth(peersFeedbackSessionForm.getMonth());
		}
		if(peersFeedbackSessionForm.getYear()!=null && !peersFeedbackSessionForm.getYear().isEmpty()){
			sessionTo.setYear(peersFeedbackSessionForm.getYear());
		}
		if(peersFeedbackSessionForm.getAcademicYear()!=null && !peersFeedbackSessionForm.getAcademicYear().isEmpty()){
			sessionTo.setAcademicYear(peersFeedbackSessionForm.getAcademicYear());
		}
		return sessionTo;
	}
}
