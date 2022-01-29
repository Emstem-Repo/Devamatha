package com.kp.cms.handlers.admin;

import java.util.List;

import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.forms.admin.PeersFeedbackSessionForm;
import com.kp.cms.helpers.admin.PeersFeedbackSessionHelper;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;
import com.kp.cms.transactions.admin.IPeersFeedbackSessionTransaction;
import com.kp.cms.transactionsimpl.admin.PeersFeedbackSessionTxnImpl;

public class PeersFeedbackSessionHandler {
 public static volatile PeersFeedbackSessionHandler feedbackSessionHandler =null;
 public static PeersFeedbackSessionHandler getInstance(){
	 if(feedbackSessionHandler == null){
		 feedbackSessionHandler = new PeersFeedbackSessionHandler();
		 return feedbackSessionHandler;
	 }
	 return feedbackSessionHandler;
 }
 IPeersFeedbackSessionTransaction transaction = PeersFeedbackSessionTxnImpl.getInstance();
/**
 * @return
 * @throws Exception
 */
public List<PeersFeedbackSessionTo> getPeersFeedbackSessionDetails() throws Exception{
	List<PeerFeedbackSession> feedbackSessions = transaction.getPeersFeedbackSessionDetails();
	List<PeersFeedbackSessionTo> peersFeedbackSessionTos = PeersFeedbackSessionHelper.getInstance().convertBOToTO(feedbackSessions);
	return peersFeedbackSessionTos;
}
/**
 * @param peersFeedbackSessionForm
 * @return
 * @throws Exception
 */
public boolean checkExistPeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception{
	boolean isDuplicate =transaction.checkDuplicatePeersFeedback(peersFeedbackSessionForm);
	return isDuplicate;
}
/**
 * @param peersFeedbackSessionForm
 * @param mode
 * @return
 * @throws Exception
 */
public boolean addPeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm, String mode) throws Exception{
	PeersFeedbackSessionTo peersFeedbackSessionTo = PeersFeedbackSessionHelper.getInstance().convertDataFromFormToTO(peersFeedbackSessionForm);
	boolean isAdded = transaction.savePeersFeedbackSession(peersFeedbackSessionTo,mode,peersFeedbackSessionForm);
	return isAdded;
}
/**
 * @param peersFeedbackSessionForm
 * @throws Exception
 */
public void editPeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm) throws Exception{
	PeerFeedbackSession feedbackSession = transaction.getEditPeersFeedbackSession(peersFeedbackSessionForm);
	if(feedbackSession.getId()!=0){
		peersFeedbackSessionForm.setId(feedbackSession.getId());
	}
	if(feedbackSession.getSession()!=null && !feedbackSession.getSession().isEmpty()){
		peersFeedbackSessionForm.setSessionName(feedbackSession.getSession());
	}
	if(feedbackSession.getMonth()!=null && !feedbackSession.getMonth().isEmpty()){
		peersFeedbackSessionForm.setMonth(feedbackSession.getMonth());
	}
	if(feedbackSession.getYear()!=null && !feedbackSession.getYear().isEmpty()){
		peersFeedbackSessionForm.setYear(feedbackSession.getYear());
	}
	if(feedbackSession.getAcademicYear()!=null && !feedbackSession.getAcademicYear().isEmpty()){
		peersFeedbackSessionForm.setAcademicYear(feedbackSession.getAcademicYear());
	}
	}
/**
 * @param peersFeedbackSessionForm
 * @return
 * @throws Exception
 */
public boolean deletePeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm)throws Exception {
	boolean isDeleted = transaction.deletePeersFeedbackSession(peersFeedbackSessionForm);
	return isDeleted;
}
}
