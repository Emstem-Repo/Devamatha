package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.bo.admission.PgAdmSubjectForRank;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;

import com.kp.cms.forms.admission.PgAdmSubjectForRankForm;
import com.kp.cms.handlers.admin.CourseHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.PgAdmSubjectForRankHandler;
import com.kp.cms.handlers.admission.PgAdmSubjectForRankHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.CourseApplicationNoTO;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UGCoursesTO;
import com.kp.cms.to.admission.PgAdmSubjectForRankTo;

public class PgAdmSubjectForRankAction extends BaseDispatchAction {

	
	public ActionForward initAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
		try{
		List<PgAdmSubjectForRankTo> subjectlist = new ArrayList<PgAdmSubjectForRankTo>();
		subjectlist=PgAdmSubjectForRankHandler.getInstance().getSubject();
		admsbjctfrm.setSubjecttolist(subjectlist);
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		admsbjctfrm.setProgramTypeList(programTypeList);
		List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
		admsbjctfrm.setUgcourseList(ugcourseList);
		
		}catch (Exception e) {
			  String msg = super.handleApplicationException(e);
			  admsbjctfrm.setErrorMessage(msg);
			  admsbjctfrm.setErrorStack(e.getMessage());
		      return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	
	
	@SuppressWarnings("deprecation")
	public ActionForward addAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
            
		     PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
             ActionMessages messages = new ActionMessages();
             ActionErrors errors = admsbjctfrm.validate(mapping, request);
             boolean isSubjectEdited = false;
             if (errors.isEmpty()) {
            	 setUserId(request, admsbjctfrm);
            	 String subjectname =admsbjctfrm.getSubjectname();
            	 try {
               	     isSubjectEdited = PgAdmSubjectForRankHandler.getInstance().addAdmSubjectForRank(admsbjctfrm,request);
				
				
			     } catch (Exception e) {
			    	if(e instanceof DuplicateException){
			    		List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
					   admsbjctfrm.setSubjecttolist(subjectlist);
					   List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
						admsbjctfrm.setProgramTypeList(programTypeList);
 						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
						admsbjctfrm.setUgcourseList(ugcourseList);
						subjectname =admsbjctfrm.getSubjectname();
						Iterator<UGCoursesTO> itr=ugcourseList.iterator();	 
						 while(itr.hasNext()){
							 UGCoursesTO admsbjctbo=itr.next();
							 if(Integer.parseInt(subjectname)==admsbjctbo.getId()){
								 subjectname=admsbjctbo.getName();
							 }
						 } 
						 
						 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists",subjectname));
					      saveErrors(request, errors);
					   
				       return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
				   
			        }if(e instanceof ReActivateException) {
 			    	
 			    	   errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists.reactivate",subjectname));
 					   saveErrors(request, errors);
 					   
 					   List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
 						admsbjctfrm.setSubjecttolist(subjectlist);
 						List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
 						admsbjctfrm.setProgramTypeList(programTypeList);
 						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
 						admsbjctfrm.setUgcourseList(ugcourseList);
 						
 					   return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
				    } 
			        
			        String msg = super.handleApplicationException(e);
					admsbjctfrm.setErrorMessage(msg);
					admsbjctfrm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				
			     }	
			     if(isSubjectEdited){
			    	 
			    	 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			 		admsbjctfrm.setSubjecttolist(subjectlist);
			 		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			 		admsbjctfrm.setProgramTypeList(programTypeList);
			 		List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			 		admsbjctfrm.setUgcourseList(ugcourseList);
			 		subjectname =admsbjctfrm.getSubjectname();
					
			 		ActionMessage message=new ActionMessage("knowledgepro.admission.subject1.entry.added");
			    	 messages.add("messages",message);
			    	 saveMessages(request, messages);
			    	
			 		 admsbjctfrm.reset(mapping, request);
			 		
			     }else{
			    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.added.failure"));
					 saveErrors(request, errors);
					   
					 	List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
						admsbjctfrm.setSubjecttolist(subjectlist);
						List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
						admsbjctfrm.setProgramTypeList(programTypeList);
						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
						admsbjctfrm.setUgcourseList(ugcourseList);
						
			     }
			 }else{
				 saveErrors(request, errors);
				 	List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
					admsbjctfrm.setSubjecttolist(subjectlist);
					List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
					admsbjctfrm.setProgramTypeList(programTypeList);
					List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
					admsbjctfrm.setUgcourseList(ugcourseList);
					 
			 }
					
               
             return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	
	
	
	@SuppressWarnings("deprecation")
	public ActionForward updateAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
            
		     PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
             ActionMessages messages = new ActionMessages();
             ActionErrors errors = admsbjctfrm.validate(mapping, request);
             boolean isSubjectEdited = false;
             if (errors.isEmpty()) {
            	 setUserId(request, admsbjctfrm);
            	 String subjectname =admsbjctfrm.getSubjectname();
            	 try {
               	     isSubjectEdited = PgAdmSubjectForRankHandler.getInstance().editAdmSubjectForRank(admsbjctfrm,request);
				
				
			     } catch (Exception e) {
			    	if(e instanceof DuplicateException){
				       errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists",subjectname));
				       saveErrors(request, errors);
				   
				       List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
						admsbjctfrm.setSubjecttolist(subjectlist);
						List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
						admsbjctfrm.setProgramTypeList(programTypeList);
						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
						admsbjctfrm.setUgcourseList(ugcourseList);
						
				       return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
				   
			        }if(e instanceof ReActivateException) {
 			    	
 			    	   errors.add("error",new ActionError("knowledgepro.admission.subject.entry.exists.reactivate",subjectname));
 					   saveErrors(request, errors);
 					   
 					   List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
 						admsbjctfrm.setSubjecttolist(subjectlist);
 						List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
 						admsbjctfrm.setProgramTypeList(programTypeList);
 						List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
 						admsbjctfrm.setUgcourseList(ugcourseList);
 						
 					   return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
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
			    	 
			    	 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			 		admsbjctfrm.setSubjecttolist(subjectlist);
			 		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			 		admsbjctfrm.setProgramTypeList(programTypeList);
			 		List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			 		admsbjctfrm.setUgcourseList(ugcourseList);
			 		admsbjctfrm.reset(mapping, request);
			     }else{
			    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.edited.failure",subjectname));
					 saveErrors(request, errors);
					   
					 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
					admsbjctfrm.setSubjecttolist(subjectlist);
					List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
					admsbjctfrm.setProgramTypeList(programTypeList);
					List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
					admsbjctfrm.setUgcourseList(ugcourseList);
						
			     }
			 }else{
				 saveErrors(request, errors);
				 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
				admsbjctfrm.setSubjecttolist(subjectlist);
				List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
				admsbjctfrm.setProgramTypeList(programTypeList);
				List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
				admsbjctfrm.setUgcourseList(ugcourseList);
					
			 }
					
               
             return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	public ActionForward editAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
            
		     PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
		     try{
		     request.setAttribute("Update", "Update");// setting update attribute
						 
		    admsbjctfrm.setProgramId(admsbjctfrm.getSelectedProgram());
		    
		    String s[]=admsbjctfrm.getSelectedCourseId();
		    
		    if(s.length!=0){
		    	String courseIdarray[] = new String[s.length];
				for (int i = 0; i < courseIdarray.length; i++) {
					courseIdarray[i]=s[i];
				}
				 admsbjctfrm.setSelectedCourseId(courseIdarray);	
		    }
		    
		     List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
		     admsbjctfrm.setSubjecttolist(subjectlist);
		     List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		     admsbjctfrm.setProgramTypeList(programTypeList);
		     Map<Integer, String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(admsbjctfrm.getProgramTypeId()));
			 admsbjctfrm.setProgramMap(programMap);
			 List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			 admsbjctfrm.setUgcourseList(ugcourseList);
			 Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(admsbjctfrm.getProgramId()));
			 admsbjctfrm.setCourseMap(courseMap);
			} 
	catch (Exception e) {
				 
				String msg = super.handleApplicationException(e);
				admsbjctfrm.setErrorMessage(msg);
				admsbjctfrm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
				
				
			}
			  return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	
	 
	@SuppressWarnings("deprecation")
	public ActionForward deleteAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		 PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
         ActionMessages messages = new ActionMessages();
         ActionErrors errors = admsbjctfrm.validate(mapping, request);
         boolean isSubjectDeleted = false;
         String subjectname =admsbjctfrm.getSubjectname();
           try {
			setUserId(request, admsbjctfrm);
			int id=admsbjctfrm.getId();
			isSubjectDeleted =  PgAdmSubjectForRankHandler.getInstance().deleteSubject(id,admsbjctfrm.getUserId());
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
	    	 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			admsbjctfrm.setSubjecttolist(subjectlist);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admsbjctfrm.setProgramTypeList(programTypeList);
			List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			admsbjctfrm.setUgcourseList(ugcourseList);
				
			
	     }else{
	    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.deletefailure"));
			 saveErrors(request, errors);
			   
			 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			admsbjctfrm.setSubjecttolist(subjectlist);
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			admsbjctfrm.setProgramTypeList(programTypeList);
			List<UGCoursesTO> ugcourseList=ProgramTypeHandler.getInstance().getUgCourses();
			admsbjctfrm.setUgcourseList(ugcourseList);
				
	     }
		  
		return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	
	@SuppressWarnings("deprecation")
	public ActionForward reActivateAdmSubjectForRank(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PgAdmSubjectForRankForm admsbjctfrm = (PgAdmSubjectForRankForm) form;
		 ActionMessages messages = new ActionMessages();
	     ActionErrors errors = admsbjctfrm.validate(mapping, request);
	     AdmSubjectForRank subj =(AdmSubjectForRank)request.getSession().getAttribute("subj");
	     
	     boolean issubjectReactivated = false;
	     try {
			setUserId(request, admsbjctfrm);
			//issubjectReactivated = PgAdmSubjectForRankHandler.getInstance().reactivateSubject(subj,admsbjctfrm.getUserId());
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
	    	 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
			
	     }else{
	    	 errors.add("error",new ActionError("knowledgepro.admission.subject.entry.reactivate.failure"));
			 saveErrors(request, errors);
			   
			 List<PgAdmSubjectForRankTo> subjectlist = PgAdmSubjectForRankHandler.getInstance().getSubject();
			 admsbjctfrm.setSubjecttolist(subjectlist);
	     }
		  
		return mapping.findForward(CMSConstants.OPENCOURSE_FOR_RANK);
	}
	

}
