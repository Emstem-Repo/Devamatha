package com.kp.cms.handlers.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.AdmSubjectForRankForm;
import com.kp.cms.helpers.admission.AdmSubjectForRankHelper;
import com.kp.cms.to.admission.AdmSubjectForRankTo;
import com.kp.cms.transactions.admission.IAdmSubjectForRank;
import com.kp.cms.transactionsimpl.admission.AdmSubjectForRankTransactionImpl;

public class AdmSubjectForRankHandler {
	
	private static volatile AdmSubjectForRankHandler admsubjectforrankhandler = null;
	
	 public static AdmSubjectForRankHandler getInstance(){
		 if(admsubjectforrankhandler == null){
			 admsubjectforrankhandler = new AdmSubjectForRankHandler();
		 } 
		 
		 return admsubjectforrankhandler;
		 
	 }

	public List<AdmSubjectForRankTo> getSubject() {
		IAdmSubjectForRank transaction = new AdmSubjectForRankTransactionImpl();
		List<AdmSubjectForRank> subjectlist = transaction.getSubject();
		List<AdmSubjectForRankTo> subjecttolist = AdmSubjectForRankHelper.convertBotoTo(subjectlist);
		return subjecttolist;
	}

	public boolean addAdmSubjectForRank(AdmSubjectForRankForm admsbjctfrm,
			HttpServletRequest request) throws Exception {
		
		IAdmSubjectForRank transaction = new AdmSubjectForRankTransactionImpl();
		boolean isSubjectEdited = false;
		AdmSubjectForRank admsbjctbo = AdmSubjectForRankHelper.convertTotoBo(admsbjctfrm,"ADD");
		AdmSubjectForRank admsbjctbo1 = transaction.isDuplicated(admsbjctbo);
		 
		if (admsbjctbo1 != null && admsbjctbo1.getIsActive() ) {
			throw new DuplicateException();
		}else if(admsbjctbo1 !=null && !admsbjctbo1.getIsActive()) {
			request.getSession().setAttribute("subj",admsbjctbo1);
			throw new ReActivateException();
		}else if(transaction != null){
			isSubjectEdited = transaction.add(admsbjctbo);
		}
		
	 return isSubjectEdited;
		
	}

	public boolean editAdmSubjectForRank(AdmSubjectForRankForm admsbjctfrm,
			HttpServletRequest request) throws Exception {
		
		IAdmSubjectForRank transaction = new AdmSubjectForRankTransactionImpl();
		boolean isSubjectEdited = false;
		AdmSubjectForRank admsbjctbo = AdmSubjectForRankHelper.convertTotoBo(admsbjctfrm,"EDIT");
		AdmSubjectForRank admsbjctbo1 = transaction.isDuplicated(admsbjctbo);
		if(!admsbjctfrm.getOriggroupname().equals(admsbjctfrm.getGroupname().trim())
				       &&!admsbjctfrm.getOrigstream().equals(admsbjctfrm.getStream().trim())
				          &&!admsbjctfrm.getOrigsubjectname().equals(admsbjctfrm.getSubjectname().trim())){ 
		
	    if (admsbjctbo1 != null && admsbjctbo1.getIsActive() ) {
			throw new DuplicateException();
		}
		}
		if(admsbjctbo1 !=null && !admsbjctbo1.getIsActive()) {
			request.getSession().setAttribute("subj",admsbjctbo1);
			throw new ReActivateException();
		}
		if(transaction != null){
			isSubjectEdited = transaction.edit(admsbjctbo);
		}
		
	 return isSubjectEdited;
		
		
	}

	public boolean deleteSubject(int id, String userId) {
		IAdmSubjectForRank transaction = new AdmSubjectForRankTransactionImpl();
		boolean isSubjectDeleted = false;
		if(transaction != null){
			isSubjectDeleted = transaction.delete(id,userId);
		}
		return isSubjectDeleted;
		
	}

	public boolean reactivateSubject(AdmSubjectForRank subj, String userId) {
		
		IAdmSubjectForRank transaction = new AdmSubjectForRankTransactionImpl();
		boolean isSubjectreactivated = false;
		if(transaction != null){
			isSubjectreactivated = transaction.reActivate(subj,userId);
			}
		return isSubjectreactivated;
	}
	
	 
	 

}
