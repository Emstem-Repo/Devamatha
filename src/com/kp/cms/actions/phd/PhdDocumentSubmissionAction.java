package com.kp.cms.actions.phd;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.phd.PhdDocumentSubmissionForm;
import com.kp.cms.handlers.phd.PhdDocumentSubmissionHandler;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdConferenceTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionTO;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.to.phd.PhdResearchDescriptionTO;
import com.kp.cms.to.phd.PhdResearchPublicationTo;
import com.kp.cms.to.phd.PhdStudentPanelMemberTO;
import com.kp.cms.transactions.phd.IPhdDocumentSubmissionTransactions;
import com.kp.cms.transactionsimpl.phd.PhdDocumentSubmissionImpl;
import com.kp.cms.utilities.CommonUtil;

/**
 * @author lenovo
 *
 */
@SuppressWarnings("deprecation")
public class PhdDocumentSubmissionAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(PhdDocumentSubmissionAction.class);
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	
	IPhdDocumentSubmissionTransactions trancations=new PhdDocumentSubmissionImpl();
	// gets initial list of Exam Definition
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPhdDocumentSubmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
	       throws Exception {
		   PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		   objForm.clearPage();
		   initializaData(objForm);
		   setUserId(request, objForm);
		  return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}
	
	
	/**
	 * @param objForm
	 */
	private void initializaData(PhdDocumentSubmissionForm objForm) {
		if(objForm.getResearchDescription()==null || objForm.getResearchDescription().isEmpty()){
		PhdResearchDescriptionTO phdResearchDescriptionTO=new PhdResearchDescriptionTO();
		List<PhdResearchDescriptionTO> publicationList=new ArrayList<PhdResearchDescriptionTO>();
		phdResearchDescriptionTO.setResearchPublication("");
		phdResearchDescriptionTO.setIssn("");
		phdResearchDescriptionTO.setIssueNumber("");
		phdResearchDescriptionTO.setLevel("");
		phdResearchDescriptionTO.setNameJournal("");
		phdResearchDescriptionTO.setTitle("");
		phdResearchDescriptionTO.setDatePhd("");
		phdResearchDescriptionTO.setVolumeNo("");
		phdResearchDescriptionTO.setDescription("");
		objForm.setResearchDescriptionLength(String.valueOf(publicationList.size()));
		publicationList.add(phdResearchDescriptionTO);
		objForm.setResearchDescription(publicationList);
		}
		
		if(objForm.getConferenceList()==null || objForm.getConferenceList().isEmpty()){
			PhdConferenceTO phdConferenceTO=new PhdConferenceTO();
			List<PhdConferenceTO> congerenceList=new ArrayList<PhdConferenceTO>();
			phdConferenceTO.setParticipated("");
			phdConferenceTO.setOrganizedBy("");
			phdConferenceTO.setNameProgram("");
			phdConferenceTO.setDateFrom("");
			phdConferenceTO.setDateTo("");
			phdConferenceTO.setLevel("");
			objForm.setResearchDescriptionLength(String.valueOf(congerenceList.size()));
			congerenceList.add(phdConferenceTO);
			objForm.setConferenceList(congerenceList);
			}
	}


	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchStudentList(ActionMapping mapping,ActionForm form, HttpServletRequest request,HttpServletResponse response) 
    throws Exception {

		log.info("Entering setClassEntry");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		ActionMessages messages = new ActionMessages();
		objForm.clearPage1();
		objForm.setStudentDetails(null);
		ActionErrors errors = objForm.validate(mapping, request);
		if (errors.isEmpty()) {
	try {
			setUserId(request, objForm);
			setGuideDetails(request,objForm);
			setStudentDetails(objForm,request,errors);
			getSubmissionDetails(objForm);
			initializaData(objForm);
			objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
			objForm.setConferenceLength(Integer.toString(objForm.getConferenceList().size()));
		  
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		} else {
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
		}
		log.info("Leaving setClassEntry");
		saveErrors(request, errors);
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}
	/**
	 * @param objForm
	 * @throws Exception
	 */
	private void getSubmissionDetails(PhdDocumentSubmissionForm objForm) throws Exception{
		 List<PhdDocumentSubmissionScheduleTO> documentSubmissionTos=PhdDocumentSubmissionHandler.getInstance().getDocumentSubmissionByReg(objForm);
		 objForm.setStudentDetailsList(documentSubmissionTos);
	}


	/**
	 * @param request
	 * @param objForm 
	 * @throws Exception
	 */
	private void setGuideDetails(HttpServletRequest request, PhdDocumentSubmissionForm objForm) throws Exception{
		 
		List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
        request.getSession().setAttribute("GuideDetails", listValues);
        
        List<PhdEmployeeTo> listVal=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
        request.getSession().setAttribute("PanelGuideDetails", listVal);
        
        List<PhdEmployeeTo> listVall=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
        request.getSession().setAttribute("VivaGuideDetails", listVall);
        
    	List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
        request.getSession().setAttribute("CoGuideDetails", listValue);
        
        List<PhdResearchPublicationTo> research=PhdDocumentSubmissionHandler.getInstance().setResearchPublication();
        request.getSession().setAttribute("ResearchPublications",research);
        
        Map<String,String> guideShipMap=trancations.guideShipMap();
		 if(guideShipMap!=null){
			 objForm.setGuideShipMap(guideShipMap);
			
		 }
}
	private void setStudentDetails(PhdDocumentSubmissionForm objForm,HttpServletRequest request, ActionErrors errors) throws Exception{
		List<PhdDocumentSubmissionTO> study=PhdDocumentSubmissionHandler.getInstance().getStudentDetailsList(objForm,errors,request);
		if(study==null || study.isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.cancelattendance.norecord"));
			addErrors(request, errors);
		}
		objForm.setStudentDetails(study);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPhdDocumentSubmission(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setUserId(request,objForm);
		boolean checking=true;
		objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if(objForm.isGuideDisplay()==objForm.isCoGuideDisplay()){
			if(objForm.getGuideId().equalsIgnoreCase(objForm.getCoGuideId())){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.guid.coguide.notsame"));
				addErrors(request, errors);
			}
		}
		if(errors.isEmpty()){
		validateNoofGuides(objForm,errors);
		}
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				
				Iterator<PhdDocumentSubmissionScheduleTO> itr=objForm.getStudentDetailsList().iterator();
				while (itr.hasNext()) {
					PhdDocumentSubmissionScheduleTO phdDocumentSubmissionScheduleTO = (PhdDocumentSubmissionScheduleTO) itr.next();
					if(phdDocumentSubmissionScheduleTO.getSubmittedDate().isEmpty() || phdDocumentSubmissionScheduleTO.getSubmittedDate()==null){
						checking=false;
					}
				}
				if((objForm.getVivaDate()==null || objForm.getVivaDate().isEmpty()) && (objForm.getVivaDiscipline()==null || objForm.getVivaDiscipline().isEmpty())
				  && (objForm.getVivaTitle()==null || objForm.getVivaTitle().isEmpty())){
					checking=true;
				}
				if(checking){
				isAdded = PhdDocumentSubmissionHandler.getInstance().addPhdDocumentSubmission(objForm,errors,session);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.StudyAggrement.addsuccess"));
					saveMessages(request, messages);
			      }else{
				     if(errors==null || errors.isEmpty()){
				      errors.add("error", new ActionError("knowledgepro.norecords"));
			            } 
				       }
				  }else{
					  
					  
					  errors.add("error", new ActionError("knowledgepro.phd.all.document.not.submitted"));
					  saveErrors(request, errors);
					  setDocumentsubmissionList(objForm);
					  objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
					  if(objForm.isFinalGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("VivaGuideDetails", listValue);
							}
						if(objForm.isPanelGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
						     request.getSession().setAttribute("PanelGuideDetails", listValue);
							}
						if(objForm.isGuideDisplay()){
							List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("GuideDetails", listValues);
							}
						if(objForm.isCoGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("CoGuideDetails", listValue);
							}
					  return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
				    }
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			
			setDocumentsubmissionList(objForm);
			
			List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
			if(listsyno!=null && !listsyno.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
					if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
						synopsisto.setTempChecked1("on");
					}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
						synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
					}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
						synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
					}if(synopsisbo.isCheckPanel()){
						synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
					}listNew.add(synopsisto);
				}objForm.setSynopsisList(listNew);
			}
			List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
			if(listFinal!=null && !listFinal.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
					if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
						fianlTo.setTempChecked2("on");
					}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
						fianlTo.setVivaName(fianlBo.getVivaName());
					}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
						fianlTo.setVivaId(fianlBo.getVivaId());
					}if(fianlBo.isCheckViva()){
						fianlTo.setCheckViva(fianlBo.isCheckViva());
					}finalNew.add(fianlTo);
				}objForm.setFinalVivaList(finalNew);
			}
			
		
			if(objForm.isFinalGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}
			if(objForm.isPanelGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
			     request.getSession().setAttribute("PanelGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("PanelGuideDetails", listValue);
				}
			if(objForm.isGuideDisplay()){
				List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValues);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("GuideDetails", listValue);
				}
			if(objForm.isCoGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
			        request.getSession().setAttribute("CoGuideDetails", listValue);
				}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
		}
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		saveErrors(request, errors);
		if(errors==null || errors.isEmpty()){
		objForm.clearPage();
		}
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}
	/**
	 * @param mappingobjForm.getExamType()
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward AddMoreGuide(ActionMapping mapping,ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		log.info("call of addSubCategory method in InvSubCategogyAction class.");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setUserId(request,objForm);
		boolean checking=true;
		objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = objForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if(objForm.isGuideDisplay()==objForm.isCoGuideDisplay()){
			if(objForm.getGuideId().equalsIgnoreCase(objForm.getCoGuideId())){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.guid.coguide.notsame"));
				addErrors(request, errors);
			}
		}
		if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				
				Iterator<PhdDocumentSubmissionScheduleTO> itr=objForm.getStudentDetailsList().iterator();
				while (itr.hasNext()) {
					PhdDocumentSubmissionScheduleTO phdDocumentSubmissionScheduleTO = (PhdDocumentSubmissionScheduleTO) itr.next();
					if(phdDocumentSubmissionScheduleTO.getSubmittedDate().isEmpty() || phdDocumentSubmissionScheduleTO.getSubmittedDate()==null){
						checking=false;
					}
				}
				if((objForm.getVivaDate()==null || objForm.getVivaDate().isEmpty()) && (objForm.getVivaDiscipline()==null || objForm.getVivaDiscipline().isEmpty())
				  && (objForm.getVivaTitle()==null || objForm.getVivaTitle().isEmpty())){
					checking=true;
				}
				if(checking){
				isAdded = PhdDocumentSubmissionHandler.getInstance().addPhdDocumentSubmission(objForm,errors,session);
				if (isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.phd.StudyAggrement.addsuccess"));
					saveMessages(request, messages);
			      }else{
				     if(errors==null || errors.isEmpty()){
				      errors.add("error", new ActionError("knowledgepro.norecords"));
			            } 
				       }
				  }else{
					  
					  
					  errors.add("error", new ActionError("knowledgepro.phd.all.document.not.submitted"));
					  saveErrors(request, errors);
					  setDocumentsubmissionList(objForm);
					  objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
					  if(objForm.isFinalGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("VivaGuideDetails", listValue);
							}
						if(objForm.isPanelGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
						     request.getSession().setAttribute("PanelGuideDetails", listValue);
							}
						if(objForm.isGuideDisplay()){
							List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("GuideDetails", listValues);
							}
						if(objForm.isCoGuideDisplay()){
							List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
					        request.getSession().setAttribute("CoGuideDetails", listValue);
							}
					  return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
				    }
				}
			catch (Exception exception) {
				log.error("Error occured in caste Entry Action", exception);
				String msg = super.handleApplicationException(exception);
				objForm.setErrorMessage(msg);
				objForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			
			setDocumentsubmissionList(objForm);
			
			List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
			if(listsyno!=null && !listsyno.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
					if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
						synopsisto.setTempChecked1("on");
					}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
						synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
					}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
						synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
					}if(synopsisbo.isCheckPanel()){
						synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
					}listNew.add(synopsisto);
				}objForm.setSynopsisList(listNew);
			}
			List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
			if(listFinal!=null && !listFinal.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
					if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
						fianlTo.setTempChecked2("on");
					}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
						fianlTo.setVivaName(fianlBo.getVivaName());
					}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
						fianlTo.setVivaId(fianlBo.getVivaId());
					}if(fianlBo.isCheckViva()){
						fianlTo.setCheckViva(fianlBo.isCheckViva());
					}finalNew.add(fianlTo);
				}objForm.setFinalVivaList(finalNew);
			}
			
		
			if(objForm.isFinalGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}
			if(objForm.isPanelGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
			     request.getSession().setAttribute("PanelGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("PanelGuideDetails", listValue);
				}
			if(objForm.isGuideDisplay()){
				List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValues);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("GuideDetails", listValue);
				}
			if(objForm.isCoGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
			        request.getSession().setAttribute("CoGuideDetails", listValue);
				}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
		}
		log.info("end of AddValuatorCharges method in ValuatorChargesAction class.");
		saveErrors(request, errors);
		if(errors==null || errors.isEmpty()){
		objForm.clearPage();
		}
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}

	/**
	 * @param objForm
	 * @param errors
	 * @throws Exception 
	 */
	private void validateNoofGuides(PhdDocumentSubmissionForm objForm,ActionErrors errors) throws Exception {
		PhdDocumentSubmissionHandler.getInstance().validateNoofGuides(objForm,errors);
		
	}


	/**
	 * @param objForm
	 */
	private void setDocumentsubmissionList(PhdDocumentSubmissionForm objForm) {	
		Iterator<PhdDocumentSubmissionScheduleTO> itr=objForm.getStudentDetailsList().iterator();
		List<PhdDocumentSubmissionScheduleTO> documentDetailsList=new ArrayList<PhdDocumentSubmissionScheduleTO>();
	     while (itr.hasNext()) {
		  PhdDocumentSubmissionScheduleTO oldto = (PhdDocumentSubmissionScheduleTO) itr.next();
		  PhdDocumentSubmissionScheduleTO newTo=new PhdDocumentSubmissionScheduleTO();
		  
		    newTo.setId(oldto.getId());
			objForm.setStudentName(objForm.getStudentName());
			objForm.setCourseName(objForm.getCourseName());
			newTo.setStudentId(oldto.getStudentId());
			newTo.setCourseId(oldto.getCourseId());
			newTo.setDocumentName(oldto.getDocumentName());
			newTo.setDocumentId(oldto.getDocumentId());
				newTo.setAssignDate(oldto.getAssignDate());
				newTo.setDocumentAssiDate(CommonUtil.ConvertStringToDate(oldto.getAssignDate()));
				newTo.setIsReminderMail(oldto.getIsReminderMail());
				newTo.setGuidesFee(oldto.getGuidesFee());
				newTo.setCanSubmitOnline(oldto.getCanSubmitOnline());
				newTo.setTempChecked(oldto.getChecked());
				newTo.setSubmittedDate(oldto.getSubmittedDate());
				newTo.setGuideFeeGenerated(oldto.getGuideFeeGenerated());
			documentDetailsList.add(newTo);
	     }
	     objForm.setStudentDetailsList(documentDetailsList);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addResearchPublication(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);
		
		List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
		if(listsyno!=null && !listsyno.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
				if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
					synopsisto.setTempChecked1("on");
				}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
					synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
				}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
					synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
				}if(synopsisbo.isCheckPanel()){
					synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
				}listNew.add(synopsisto);
			}objForm.setSynopsisList(listNew);
		}
		List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
		if(listFinal!=null && !listFinal.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
				if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
					fianlTo.setTempChecked2("on");
				}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
					fianlTo.setVivaName(fianlBo.getVivaName());
				}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
					fianlTo.setVivaId(fianlBo.getVivaId());
				}if(fianlBo.isCheckViva()){
					fianlTo.setCheckViva(fianlBo.isCheckViva());
				}finalNew.add(fianlTo);
			}objForm.setFinalVivaList(finalNew);
		}
		
		
		if(objForm.getResearchDescription()!=null){
				List<PhdResearchDescriptionTO> list=objForm.getResearchDescription();
				PhdResearchDescriptionTO researchPublicationTo=new PhdResearchDescriptionTO();
				researchPublicationTo.setResearchPublication("");
				researchPublicationTo.setIssn("");
				researchPublicationTo.setIssueNumber("");
				researchPublicationTo.setLevel("");
				researchPublicationTo.setNameJournal("");
				researchPublicationTo.setTitle("");
				researchPublicationTo.setDatePhd("");
				researchPublicationTo.setVolumeNo("");
				researchPublicationTo.setDescription("");
				list.add(researchPublicationTo);
				objForm.setResearchDescriptionLength(String.valueOf(list.size()));
				objForm.setFocusValue("synopsis");
		}
		if(objForm.isFinalGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}
		if(objForm.isPanelGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		     request.getSession().setAttribute("PanelGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("PanelGuideDetails", listValue);
			}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValue);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteResearchPublication(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		List<PhdResearchDescriptionTO> list=objForm.getResearchDescription();
		setDocumentsubmissionList(objForm);
		 PhdDocumentSubmissionHandler.getInstance().deleteResearchPublication(objForm);
		
		 List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
			if(listsyno!=null && !listsyno.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
					if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
						synopsisto.setTempChecked1("on");
					}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
						synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
					}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
						synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
					}if(synopsisbo.isCheckPanel()){
						synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
					}listNew.add(synopsisto);
				}objForm.setSynopsisList(listNew);
			}
			List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
			if(listFinal!=null && !listFinal.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
					if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
						fianlTo.setTempChecked2("on");
					}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
						fianlTo.setVivaName(fianlBo.getVivaName());
					}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
						fianlTo.setVivaId(fianlBo.getVivaId());
					}if(fianlBo.isCheckViva()){
						fianlTo.setCheckViva(fianlBo.isCheckViva());
					}finalNew.add(fianlTo);
				}objForm.setFinalVivaList(finalNew);
			}
		 
		 
		Iterator<PhdResearchDescriptionTO> itrr=list.iterator();
	while (itrr.hasNext()) {
			PhdResearchDescriptionTO phdResearchDescriptionTO = (PhdResearchDescriptionTO) itrr.next();
		if(phdResearchDescriptionTO.getId()!=null){
			if(phdResearchDescriptionTO.getId()==objForm.getId()){
			list.remove(phdResearchDescriptionTO);
			break;
			}
		}else if(phdResearchDescriptionTO.getIssn().equalsIgnoreCase(objForm.getTempIssn()) && phdResearchDescriptionTO.getResearchPublication().equalsIgnoreCase(objForm.getTempResearch())){
			list.remove(phdResearchDescriptionTO);
			break;
		}
				}
		objForm.setResearchDescription(list);
		//	setStudentDetails(objForm,request,errors);
		//	initializaData(objForm);
			objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
			objForm.setFocusValue("synopsis");
			if(objForm.isFinalGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}
			if(objForm.isPanelGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
			     request.getSession().setAttribute("PanelGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("PanelGuideDetails", listValue);
				}
			if(objForm.isGuideDisplay()){
				List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValues);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("GuideDetails", listValue);
				}
			if(objForm.isCoGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
			        request.getSession().setAttribute("CoGuideDetails", listValue);
				}
		log.info("End of resetExperienceInfo of PHD_DOCUMENT_SUBMISSION");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addConference(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);
		
		List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
		if(listsyno!=null && !listsyno.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
				if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
					synopsisto.setTempChecked1("on");
				}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
					synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
				}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
					synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
				}if(synopsisbo.isCheckPanel()){
					synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
				}listNew.add(synopsisto);
			}objForm.setSynopsisList(listNew);
		}
		List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
		if(listFinal!=null && !listFinal.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
				if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
					fianlTo.setTempChecked2("on");
				}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
					fianlTo.setVivaName(fianlBo.getVivaName());
				}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
					fianlTo.setVivaId(fianlBo.getVivaId());
				}if(fianlBo.isCheckViva()){
					fianlTo.setCheckViva(fianlBo.isCheckViva());
				}finalNew.add(fianlTo);
			}objForm.setFinalVivaList(finalNew);
		}
		
		
		if(objForm.getConferenceList()!=null){
				List<PhdConferenceTO> list=objForm.getConferenceList();
				PhdConferenceTO phdConferenceTO=new PhdConferenceTO();
				phdConferenceTO.setParticipated("");
				phdConferenceTO.setOrganizedBy("");
				phdConferenceTO.setNameProgram("");
				phdConferenceTO.setDateFrom("");
				phdConferenceTO.setDateTo("");
				phdConferenceTO.setLevel("");
				list.add(phdConferenceTO);
				objForm.setConferenceLength(String.valueOf(list.size()));
				objForm.setFocusValue("synopsis");
		}
		if(objForm.isFinalGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}
		if(objForm.isPanelGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		     request.getSession().setAttribute("PanelGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("PanelGuideDetails", listValue);
			}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValue);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}else{
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteConference(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpResumeSubmissionAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		List<PhdConferenceTO> list=objForm.getConferenceList();
		setDocumentsubmissionList(objForm);
		 PhdDocumentSubmissionHandler.getInstance().deleteConference(objForm);
		
		 List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
			if(listsyno!=null && !listsyno.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
					if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
						synopsisto.setTempChecked1("on");
					}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
						synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
					}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
						synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
					}if(synopsisbo.isCheckPanel()){
						synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
					}listNew.add(synopsisto);
				}objForm.setSynopsisList(listNew);
			}
			List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
			List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
			if(listFinal!=null && !listFinal.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
				while (finalto.hasNext()) {
					PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
					PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
					if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
						fianlTo.setTempChecked2("on");
					}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
						fianlTo.setVivaName(fianlBo.getVivaName());
					}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
						fianlTo.setVivaId(fianlBo.getVivaId());
					}if(fianlBo.isCheckViva()){
						fianlTo.setCheckViva(fianlBo.isCheckViva());
					}finalNew.add(fianlTo);
				}objForm.setFinalVivaList(finalNew);
			}
		 
		 
		Iterator<PhdConferenceTO> itrr=list.iterator();
	while (itrr.hasNext()) {
		PhdConferenceTO phdConferenceTO = (PhdConferenceTO) itrr.next();
		if(phdConferenceTO.getId()!=null){
			if(phdConferenceTO.getId()==objForm.getId()){
			list.remove(phdConferenceTO);
			break;
			}
		}else if(phdConferenceTO.getOrganizedBy().equalsIgnoreCase(objForm.getTempOrganBy()) && phdConferenceTO.getNameProgram().equalsIgnoreCase(objForm.getTempNameProgram())){
			list.remove(phdConferenceTO);
			break;
		}
				}
		objForm.setConferenceList(list);
		//	setStudentDetails(objForm,request,errors);
		//	initializaData(objForm);
			objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
			objForm.setFocusValue("synopsis");
			if(objForm.isFinalGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("VivaGuideDetails", listValue);
				}
			if(objForm.isPanelGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
			     request.getSession().setAttribute("PanelGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("PanelGuideDetails", listValue);
				}
			if(objForm.isGuideDisplay()){
				List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("GuideDetails", listValues);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
			        request.getSession().setAttribute("GuideDetails", listValue);
				}
			if(objForm.isCoGuideDisplay()){
				List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		        request.getSession().setAttribute("CoGuideDetails", listValue);
				}else{
					List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setCoGuideDetails();
			        request.getSession().setAttribute("CoGuideDetails", listValue);
				}
		log.info("End of resetExperienceInfo of PHD_DOCUMENT_SUBMISSION");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward AddSynopisToList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);
		String mode="synopsis";
		boolean flag=false;
		
		List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
		if(listsyno!=null && !listsyno.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
				if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
					synopsisto.setTempChecked1("on");
				}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
					synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
				}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
					synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
				}if(synopsisbo.isCheckPanel()){
					synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
				}listNew.add(synopsisto);
			}objForm.setSynopsisList(listNew);
		}
		List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
		if(listFinal!=null && !listFinal.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
				if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
					fianlTo.setTempChecked2("on");
				}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
					fianlTo.setVivaName(fianlBo.getVivaName());
				}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
					fianlTo.setVivaId(fianlBo.getVivaId());
				}if(fianlBo.isCheckViva()){
					fianlTo.setCheckViva(fianlBo.isCheckViva());
				}finalNew.add(fianlTo);
			}objForm.setFinalVivaList(finalNew);
		}
		
		
		if(objForm.getSynopsis()!=null && !objForm.getSynopsis().isEmpty()){
			    
			
			
				List<PhdStudentPanelMemberTO> list=objForm.getSynopsisList();
				PhdStudentPanelMemberTO synopsisTo=new PhdStudentPanelMemberTO();
				
				if(list!=null && !list.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> to=list.iterator();
				while (to.hasNext()) {
					PhdStudentPanelMemberTO phdEmployee = (PhdStudentPanelMemberTO) to.next();
					if(phdEmployee.getSynopsisId().equalsIgnoreCase(objForm.getSynopsis())){
						flag=true;
					}
				}
				if(!flag){
					String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(Integer.parseInt(objForm.getSynopsis()),objForm,mode);
					 synopsisTo.setSynopsisName(name);
					 synopsisTo.setSynopsisId(objForm.getSynopsis());
					 synopsisTo.setCheckPanel(objForm.isPanelGuideDisplay());
					 list.add(synopsisTo);
					 objForm.setSynopsisList(list);
					 objForm.setSynopsis(null);
				}
				}else{
					String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(Integer.parseInt(objForm.getSynopsis()),objForm,mode);
					synopsisTo.setSynopsisName(name);
					synopsisTo.setSynopsisId(objForm.getSynopsis());
					synopsisTo.setCheckPanel(objForm.isPanelGuideDisplay());
					list=new ArrayList<PhdStudentPanelMemberTO>();
					list.add(synopsisTo);
					objForm.setSynopsisList(list);
					objForm.setSynopsis(null);
				}
				objForm.setFocusValue("vivaDiscipline");
		}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		if(objForm.getFinalViva()!=null && !objForm.getFinalViva().isEmpty()){
			if(objForm.isFinalGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}
		}
		 List<PhdEmployeeTo> listVal=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
	     request.getSession().setAttribute("PanelGuideDetails", listVal);
		objForm.setPanelGuideDisplay(false);
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward AddFinalVivaList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);
		String mode="viva";
		boolean flag=false;
		
		List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
		if(listsyno!=null && !listsyno.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
				if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
					synopsisto.setTempChecked1("on");
				}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
					synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
				}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
					synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
				}if(synopsisbo.isCheckPanel()){
					synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
				}listNew.add(synopsisto);
			}objForm.setSynopsisList(listNew);
		}
		List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
		if(listFinal!=null && !listFinal.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO fianlToo = new PhdStudentPanelMemberTO();
				if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
					fianlToo.setTempChecked2("on");
				}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
					fianlToo.setVivaName(fianlBo.getVivaName());
				}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
					fianlToo.setVivaId(fianlBo.getVivaId());
				}if(fianlBo.isCheckViva()){
					fianlToo.setCheckViva(fianlBo.isCheckViva());
				}finalNew.add(fianlToo);
			}objForm.setFinalVivaList(finalNew);
		}
		
		if(objForm.getFinalViva()!=null && !objForm.getFinalViva().isEmpty()){
			
				List<PhdStudentPanelMemberTO> list=objForm.getFinalVivaList();
				PhdStudentPanelMemberTO finalTo=new PhdStudentPanelMemberTO();
				
				if(list!=null && !list.isEmpty()){
				Iterator<PhdStudentPanelMemberTO> to=list.iterator();
				while (to.hasNext()) {
					PhdStudentPanelMemberTO phdEmployee = (PhdStudentPanelMemberTO) to.next();
					if(phdEmployee.getVivaId().equalsIgnoreCase(objForm.getFinalViva())){
						flag=true;
					}
				}
				if(!flag){
					String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(Integer.parseInt(objForm.getFinalViva()),objForm,mode);
					finalTo.setVivaName(name);
					finalTo.setVivaId(objForm.getFinalViva());
					finalTo.setCheckViva(objForm.isFinalGuideDisplay());
					list.add(finalTo);
					objForm.setFinalVivaList(list);
					objForm.setFinalViva(null);
				}
				}else{
					String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(Integer.parseInt(objForm.getFinalViva()),objForm,mode);
					finalTo.setVivaName(name);
					finalTo.setVivaId(objForm.getFinalViva());
					finalTo.setCheckViva(objForm.isFinalGuideDisplay());
					list=new ArrayList<PhdStudentPanelMemberTO>();
					list.add(finalTo);
					objForm.setFinalVivaList(list);
					objForm.setFinalViva(null);
				}
				objForm.setFocusValue("vivaDiscipline");
		}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		if(objForm.getSynopsis()!=null && !objForm.getSynopsis().isEmpty()){
			if(objForm.isPanelGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		     request.getSession().setAttribute("PanelGuideDetails", listValue);
			}
		}
		 List<PhdEmployeeTo> listVall=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
	     request.getSession().setAttribute("VivaGuideDetails", listVall);
		objForm.setFinalGuideDisplay(false);
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletesynopsisList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);		
		List<PhdStudentPanelMemberTO> finalNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listFinal=objForm.getFinalVivaList();
		if(listFinal!=null && !listFinal.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listFinal.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO fianlBo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO fianlTo = new PhdStudentPanelMemberTO();
				if(fianlBo.getChecked2()!=null && !fianlBo.getChecked2().isEmpty()){
					fianlTo.setTempChecked2("on");
				}if(fianlBo.getVivaName()!=null && !fianlBo.getVivaName().isEmpty()){
					fianlTo.setVivaName(fianlBo.getVivaName());
				}if(fianlBo.getVivaId()!=null && !fianlBo.getVivaId().isEmpty()){
					fianlTo.setVivaId(fianlBo.getVivaId());
				}if(fianlBo.isCheckViva()){
					fianlTo.setCheckViva(fianlBo.isCheckViva());
				}finalNew.add(fianlTo);
			}objForm.setFinalVivaList(finalNew);
		}
		List<PhdStudentPanelMemberTO> list=objForm.getSynopsisList();
		String mode="Synopsis";
		 PhdDocumentSubmissionHandler.getInstance().deletesynopsisList(objForm.getId(), objForm.getUserId(),mode);
		
	if(list!=null && !list.isEmpty()){
		Iterator<PhdStudentPanelMemberTO> to=list.iterator();
		while (to.hasNext()) {
			PhdStudentPanelMemberTO phdEmployee = (PhdStudentPanelMemberTO) to.next();
			if(phdEmployee.getChecked1()!=null && !phdEmployee.getChecked1().isEmpty()){
				phdEmployee.setTempChecked1("on");
			}
		if(phdEmployee.getId()!=null){
			if(phdEmployee.getId()==objForm.getId()){
			list.remove(phdEmployee);
			break;
					}
		}else if(phdEmployee.getChecked1()==null || phdEmployee.getChecked1().isEmpty()){
			list.remove(phdEmployee);
			break;
		}
				}
		objForm.setSynopsisList(list);
		
	} 
	//	setStudentDetails(objForm,request,errors);
	//	initializaData(objForm);
		objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
		objForm.setFocusValue("vivaDiscipline");
		if(objForm.isFinalGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}
		if(objForm.isPanelGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		     request.getSession().setAttribute("PanelGuideDetails", listValue);
			}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteVivaList(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("Befinning of resetExperienceInfo of EmpInfoAction");
		PhdDocumentSubmissionForm objForm = (PhdDocumentSubmissionForm) form;
		setDocumentsubmissionList(objForm);	
		List<PhdStudentPanelMemberTO> listNew=new ArrayList<PhdStudentPanelMemberTO>();
		List<PhdStudentPanelMemberTO> listsyno=objForm.getSynopsisList();
		if(listsyno!=null && !listsyno.isEmpty()){
			Iterator<PhdStudentPanelMemberTO> finalto=listsyno.iterator();
			while (finalto.hasNext()) {
				PhdStudentPanelMemberTO synopsisbo = (PhdStudentPanelMemberTO) finalto.next();
				PhdStudentPanelMemberTO synopsisto=new PhdStudentPanelMemberTO();
				if(synopsisbo.getChecked1()!=null && !synopsisbo.getChecked1().isEmpty()){
					synopsisto.setTempChecked1("on");
				}if(synopsisbo.getSynopsisName()!=null && !synopsisbo.getSynopsisName().isEmpty()){
					synopsisto.setSynopsisName(synopsisbo.getSynopsisName());
				}if(synopsisbo.getSynopsisId()!=null && !synopsisbo.getSynopsisId().isEmpty()){
					synopsisto.setSynopsisId(synopsisbo.getSynopsisId());
				}if(synopsisbo.isCheckPanel()){
					synopsisto.setCheckPanel(synopsisbo.isCheckPanel());
				}listNew.add(synopsisto);
			}objForm.setSynopsisList(listNew);
		}
		List<PhdStudentPanelMemberTO> list=objForm.getFinalVivaList();
		String mode="Viva";
		 PhdDocumentSubmissionHandler.getInstance().deletesynopsisList(objForm.getId(), objForm.getUserId(),mode);
		
			    
		if(list!=null && !list.isEmpty()){
		Iterator<PhdStudentPanelMemberTO> to=list.iterator();
		while (to.hasNext()) {
			PhdStudentPanelMemberTO phdEmployee = (PhdStudentPanelMemberTO) to.next();
			if(phdEmployee.getChecked2()!=null && !phdEmployee.getChecked2().isEmpty()){
				phdEmployee.setTempChecked2("on");
			}
		if(phdEmployee.getId()!=null){
			if(phdEmployee.getId()==objForm.getId()){
			list.remove(phdEmployee);
			break;
					}
		}else if(phdEmployee.getChecked2()==null || phdEmployee.getChecked2().isEmpty()){
			list.remove(phdEmployee);
			break;
		}
				}
		objForm.setFinalVivaList(list);
		
	}
	//	setStudentDetails(objForm,request,errors);
	//	initializaData(objForm);
		objForm.setResearchDescriptionLength(Integer.toString(objForm.getResearchDescription().size()));
		objForm.setFocusValue("vivaDiscipline");
		if(objForm.isFinalGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("VivaGuideDetails", listValue);
			}
		if(objForm.isPanelGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
		     request.getSession().setAttribute("PanelGuideDetails", listValue);
			}
		if(objForm.isGuideDisplay()){
			List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("GuideDetails", listValues);
			}
		if(objForm.isCoGuideDisplay()){
			List<PhdEmployeeTo> listValue=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
	        request.getSession().setAttribute("CoGuideDetails", listValue);
			}
		log.info("End of resetExperienceInfo of EmployeeInfoFormNew");
		return mapping.findForward(CMSConstants.PHD_DOCUMENT_SUBMISSION);
   }
}
