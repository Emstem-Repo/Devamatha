package com.kp.cms.transactions.admin;

import java.util.List;

import com.kp.cms.bo.admin.PeerFeedbackSession;
import com.kp.cms.forms.admin.PeersFeedbackSessionForm;
import com.kp.cms.to.admin.PeersFeedbackSessionTo;

public interface IPeersFeedbackSessionTransaction {

	public List<PeerFeedbackSession> getPeersFeedbackSessionDetails() throws Exception;

	public boolean checkDuplicatePeersFeedback( PeersFeedbackSessionForm peersFeedbackSessionForm)throws Exception;

	public boolean savePeersFeedbackSession( PeersFeedbackSessionTo peersFeedbackSessionTo, String mode, PeersFeedbackSessionForm peersFeedbackSessionForm)throws Exception;

	public PeerFeedbackSession getEditPeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm)throws Exception;

	public boolean deletePeersFeedbackSession( PeersFeedbackSessionForm peersFeedbackSessionForm)throws Exception;

}
