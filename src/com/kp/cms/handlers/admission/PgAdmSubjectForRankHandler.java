package com.kp.cms.handlers.admission;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admission.PgAdmSubjectForRank;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.PgAdmSubjectForRankForm;

import com.kp.cms.helpers.admission.PgAdmSubjectForRankHelper;
import com.kp.cms.to.admission.PgAdmSubjectForRankTo;
import com.kp.cms.transactions.admission.IPgAdmSubjectForRank;
import com.kp.cms.transactionsimpl.admission.PgAdmSubjectForRankTransactionImpl;

public class PgAdmSubjectForRankHandler {
	
	private static volatile PgAdmSubjectForRankHandler admsubjectforrankhandler = null;
	
	 public static PgAdmSubjectForRankHandler getInstance(){
		 if(admsubjectforrankhandler == null){
			 admsubjectforrankhandler = new PgAdmSubjectForRankHandler();
		 } 
		 
		 return admsubjectforrankhandler;
		 
	 }

	public List<PgAdmSubjectForRankTo> getSubject() {
		IPgAdmSubjectForRank transaction = new PgAdmSubjectForRankTransactionImpl();
		List<PgAdmSubjectForRank> subjectlist = transaction.getSubject();
		List<PgAdmSubjectForRankTo> subjecttolist = PgAdmSubjectForRankHelper.convertBotoTo(subjectlist);
		return subjecttolist;
	}

	public boolean addAdmSubjectForRank(PgAdmSubjectForRankForm admsbjctfrm,
			HttpServletRequest request) throws Exception {
		
		IPgAdmSubjectForRank transaction = new PgAdmSubjectForRankTransactionImpl();
		boolean isSubjectEdited = false;
		List<PgAdmSubjectForRank> admsbjctbolist = PgAdmSubjectForRankHelper.convertTotoBo(admsbjctfrm,"ADD");
		List<PgAdmSubjectForRank> admsbjctbo1 = transaction.isDuplicated(admsbjctbolist);
		 if(admsbjctbo1!=null && admsbjctbo1.size()!=0){
		Iterator<PgAdmSubjectForRank> itr=admsbjctbolist.iterator();	 
		 while(itr.hasNext()){
			 PgAdmSubjectForRank admsbjctbo=itr.next();
		 
		if (admsbjctbo != null && admsbjctbo.getIsActive() ) {
			admsbjctfrm.setSubjectname(new Integer(admsbjctbo.getuGCoursesBO().getId()).toString());
			throw new DuplicateException();
		/*else if(admsbjctbo1 !=null && !admsbjctbo1.getIsActive()) {
			request.getSession().setAttribute("subj",admsbjctbo1);
			throw new ReActivateException();
		}else if(transaction != null){
			isSubjectEdited = transaction.add(admsbjctbo);
		}*/
		}
		if(transaction != null){
			isSubjectEdited = transaction.add(admsbjctbo);
		}
		 }
		 }
		 else
		 {
			 Iterator<PgAdmSubjectForRank> itr=admsbjctbolist.iterator();
			 
			 while(itr.hasNext()){
				 PgAdmSubjectForRank admsbjctbo=itr.next();
			 
			
			/*else if(admsbjctbo1 !=null && !admsbjctbo1.getIsActive()) {
				request.getSession().setAttribute("subj",admsbjctbo1);
				throw new ReActivateException();
			}else if(transaction != null){
				isSubjectEdited = transaction.add(admsbjctbo);
			}*/
			
			if(transaction != null){
				isSubjectEdited = transaction.add(admsbjctbo);
			}
			 }
		 }
	 return isSubjectEdited;
		
	}

	public boolean editAdmSubjectForRank(PgAdmSubjectForRankForm admsbjctfrm,
			HttpServletRequest request) throws Exception {
		
		IPgAdmSubjectForRank transaction = new PgAdmSubjectForRankTransactionImpl();
		boolean isSubjectEdited = false;
	    List<PgAdmSubjectForRank> admsbjctbolist = PgAdmSubjectForRankHelper.convertTotoBo(admsbjctfrm,"EDIT");
		List<PgAdmSubjectForRank> admsbjctbo1 = transaction.isDuplicatedforUpdate(admsbjctbolist);
		 Iterator<PgAdmSubjectForRank> itr=admsbjctbo1.iterator();
		 while(itr.hasNext()){
			 PgAdmSubjectForRank admsbjctbo2=itr.next();
		 
		if (admsbjctbo2 != null && admsbjctbo2.getIsActive() ) {
			throw new DuplicateException();
		/*else if(admsbjctbo1 !=null && !admsbjctbo1.getIsActive()) {
			request.getSession().setAttribute("subj",admsbjctbo1);
			throw new ReActivateException();
		}else if(transaction != null){
			isSubjectEdited = transaction.add(admsbjctbo);
		}*/
		}
		 }
		 
		 if(transaction != null){
			 Iterator<PgAdmSubjectForRank> itr1=admsbjctbolist.iterator();
			 while(itr1.hasNext()){
				 PgAdmSubjectForRank admsbjctbo2=itr1.next();
			
				isSubjectEdited = transaction.edit(admsbjctbo2);
			 }
			}
			
		
	 return isSubjectEdited;
		
		
	}

	public boolean deleteSubject(int id, String userId) {
		IPgAdmSubjectForRank transaction = new PgAdmSubjectForRankTransactionImpl();
		boolean isSubjectDeleted = false;
		if(transaction != null){
			isSubjectDeleted = transaction.delete(id,userId);
		}
		return isSubjectDeleted;
		
	}

	public boolean reactivateSubject(PgAdmSubjectForRank subj, String userId) {
		
		IPgAdmSubjectForRank transaction = new PgAdmSubjectForRankTransactionImpl();
		boolean isSubjectreactivated = false;
		if(transaction != null){
			isSubjectreactivated = transaction.reActivate(subj,userId);
			}
		return isSubjectreactivated;
	}
	
	

}
