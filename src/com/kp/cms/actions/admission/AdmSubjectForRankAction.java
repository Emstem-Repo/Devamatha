package com.kp.cms.actions.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmSubjectForRank;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.AdmSubjectForRankForm;
import com.kp.cms.handlers.admission.AdmSubjectForRankHandler;
import com.kp.cms.to.admission.AdmSubjectForRankTo;

public class AdmSubjectForRankAction extends BaseDispatchAction {

	
	public ActionForward initAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		AdmSubjectForRankForm admsbjctfrm = (AdmSubjectForRankForm) form;
		try{
		List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
		admsbjctfrm.setSubjecttolist(subjectlist);
		}catch (Exception e) {
			  String msg = super.handleApplicationException(e);
			  admsbjctfrm.setErrorMessage(msg);
			  admsbjctfrm.setErrorStack(e.getMessage());
		      return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
	}
	
	
	@SuppressWarnings("deprecation")
	public ActionForward addAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
            
		     AdmSubjectForRankForm admsbjctfrm = (AdmSubjectForRankForm) form;
             ActionMessages messages = new ActionMessages();
             ActionErrors errors = admsbjctfrm.validate(mapping, request);
             boolean isSubjectEdited = false;
             if (errors.isEmpty()) {
            	 setUserId(request, admsbjctfrm);
            	 String subjectname =admsbjctfrm.getSubjectname();
            	 try {
               	     isSubjectEdited = AdmSubjectForRankHandler.getInstance().addAdmSubjectForRank(admsbjctfrm,request);
				
				
			     } catch (Exception e) {
			    	if(e instanceof DuplicateException){
				       errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists",subjectname));
				       saveErrors(request, errors);
				   
				       List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					   admsbjctfrm.setSubjecttolist(subjectlist);
                   
				       return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
				   
			        }if(e instanceof ReActivateException) {
 			    	
 			    	   errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists.reactivate",subjectname));
 					   saveErrors(request, errors);
 					   
 					  List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					   admsbjctfrm.setSubjecttolist(subjectlist);
 					 
 					   return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
				    } 
			        
			        String msg = super.handleApplicationException(e);
					admsbjctfrm.setErrorMessage(msg);
					admsbjctfrm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			     }	
			     if(isSubjectEdited){
			    	 ActionMessage message=new ActionMessage("knowledgepro.admission.subject.entry.added",subjectname);
			    	 messages.add("messages",message);
			    	 saveMessages(request, messages);
			    	 admsbjctfrm.reset(mapping, request);
			    	 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					 admsbjctfrm.setSubjecttolist(subjectlist);
					
			     }else{
			    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.added.failure",subjectname));
					 saveErrors(request, errors);
					   
					 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					 admsbjctfrm.setSubjecttolist(subjectlist);
			     }
			 }else{
				 saveErrors(request, errors);
				 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
				 admsbjctfrm.setSubjecttolist(subjectlist);
				 
			 }
					
               
             return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
	}
	
	
	
	@SuppressWarnings("deprecation")
	public ActionForward editAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
            
		     AdmSubjectForRankForm admsbjctfrm = (AdmSubjectForRankForm) form;
             ActionMessages messages = new ActionMessages();
             ActionErrors errors = admsbjctfrm.validate(mapping, request);
             boolean isSubjectEdited = false;
             if (errors.isEmpty()) {
            	 setUserId(request, admsbjctfrm);
            	 String subjectname =admsbjctfrm.getSubjectname();
            	 try {
               	     isSubjectEdited = AdmSubjectForRankHandler.getInstance().editAdmSubjectForRank(admsbjctfrm,request);
				
				
			     } catch (Exception e) {
			    	if(e instanceof DuplicateException){
				       errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists",subjectname));
				       saveErrors(request, errors);
				   
				       List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					   admsbjctfrm.setSubjecttolist(subjectlist);
                   
				       return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
				   
			        }if(e instanceof ReActivateException) {
 			    	
 			    	   errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists.reactivate",subjectname));
 					   saveErrors(request, errors);
 					   
 					  List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					   admsbjctfrm.setSubjecttolist(subjectlist);
 					 
 					   return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
				    } 
			        
			        String msg = super.handleApplicationException(e);
					admsbjctfrm.setErrorMessage(msg);
					admsbjctfrm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			     }	
			     if(isSubjectEdited){
			    	 ActionMessage message=new ActionMessage("knowledgepro.admission.subject.entry.edited",subjectname);
			    	 messages.add("messages",message);
			    	 saveMessages(request, messages);
			    	 admsbjctfrm.reset(mapping, request);
			    	 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					 admsbjctfrm.setSubjecttolist(subjectlist);
					
			     }else{
			    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.edited.failure",subjectname));
					 saveErrors(request, errors);
					   
					 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
					 admsbjctfrm.setSubjecttolist(subjectlist);
			     }
			 }else{
				 saveErrors(request, errors);
				 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
				 admsbjctfrm.setSubjecttolist(subjectlist);
				 
			 }
					
               
             return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
	}
	
	@SuppressWarnings("deprecation")
	public ActionForward deleteAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 AdmSubjectForRankForm admsbjctfrm = (AdmSubjectForRankForm) form;
         ActionMessages messages = new ActionMessages();
         ActionErrors errors = admsbjctfrm.validate(mapping, request);
         boolean isSubjectDeleted = false;
         String subjectname =admsbjctfrm.getSubjectname();
           try {
			setUserId(request, admsbjctfrm);
			int id=admsbjctfrm.getId();
			isSubjectDeleted =  AdmSubjectForRankHandler.getInstance().deleteSubject(id,admsbjctfrm.getUserId());
		} catch (Exception e) {
			
			String msg = super.handleApplicationException(e);
			admsbjctfrm.setErrorMessage(msg);
			admsbjctfrm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
			
			
		}
		if(isSubjectDeleted){
			 ActionMessage message = new ActionMessage("knowledgepro.admission.subject.entry.deleted",subjectname);
	    	 messages.add("messages",message);
	    	 saveMessages(request, messages);
	    	 admsbjctfrm.reset(mapping, request);
	    	 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
			
	     }else{
	    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.deletefailure"));
			 saveErrors(request, errors);
			   
			 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
	     }
		  
		return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
	}
	
	@SuppressWarnings("deprecation")
	public ActionForward reActivateAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmSubjectForRankForm admsbjctfrm = (AdmSubjectForRankForm) form;
		 ActionMessages messages = new ActionMessages();
	     ActionErrors errors = admsbjctfrm.validate(mapping, request);
	     AdmSubjectForRank subj =(AdmSubjectForRank)request.getSession().getAttribute("subj");
	     
	     boolean issubjectReactivated = false;
	     try {
			setUserId(request, admsbjctfrm);
			issubjectReactivated = AdmSubjectForRankHandler.getInstance().reactivateSubject(subj,admsbjctfrm.getUserId());
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			admsbjctfrm.setErrorMessage(msg);
			admsbjctfrm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(issubjectReactivated){
			 ActionMessage message = new ActionMessage("knowledgepro.admission.subject.entry.reactivate.success");
	    	 messages.add("messages",message);
	    	 saveMessages(request, messages);
	    	 admsbjctfrm.reset(mapping, request);
	    	 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
			
	     }else{
	    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.reactivate.failure"));
			 saveErrors(request, errors);
			   
			 List<AdmSubjectForRankTo> subjectlist = AdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
	     }
		  
		return mapping.findForward(CMSConstants.SUBJECT_FOR_RANK);
	}
	

}
