package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.FinalMeritListForm;
import com.kp.cms.handlers.admission.FinalMeritListSearchHandlar;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.FinalMeritListSearchTo;

/**
 * Performs final merit list search related actions.
 */
@SuppressWarnings("deprecation")
public class FinalMeritListSearchAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(FinalMeritListSearchAction.class);
	
	/**
	 * 
	 * Performs the submit action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward submitSelectedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into submitSelectedList of FinalMeritListSearchAction class.");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		ActionMessage message = null;
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		setUserId(request, finalMeritListForm);
	    List<GroupTemplate> finalMeritListTemplate = FinalMeritListSearchHandlar.getInstanse().getFinalMeritListMailTemplate(finalMeritListForm.getCourseId(), finalMeritListForm.getProgramId());
		if (finalMeritListForm.getSelectedCandidates() == null) {
			message = new ActionMessage(
					"knowledgepro.admission.selectcandidate");
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

		} else if (finalMeritListTemplate == null
				|| (finalMeritListTemplate != null && finalMeritListTemplate
						.isEmpty())) {
			message = new ActionMessage(
					"knowledgepro.admission.finalmeritlistmailtemplate");
			messages.add(CMSConstants.MESSAGES, message);
			addErrors(request, messages);

		} else {

			try {
				
			if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
				FinalMeritListSearchHandlar.getInstanse().updateSelectedCandidates(
								finalMeritListForm.getSelectedCandidates(),!finalMeritListForm.isNeedApproval(),
								true, finalMeritListForm.getUserId(),"update");
				if(!finalMeritListForm.isNeedApproval()){
				//FinalMeritListSearchHandlar.getInstanse().sendMailToStudent(finalMeritListForm,request);
					FinalMeritListSearchHandlar.getInstanse().sendMailToApprovedStudent(finalMeritListForm,request);
					FinalMeritListSearchHandlar.getInstanse().sendSMSToApprovedStudent(finalMeritListForm,request);
				}
				message = new ActionMessage("knowledgepro.admission.addtolist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				int selectedList = FinalMeritListSearchHandlar.getInstanse()
						.getFinalMeritListSearchResult(finalMeritListForm,
								true, null).size();
		
				int maxIntake = FinalMeritListSearchHandlar.getInstanse()
						.getMaxIntakeFromCourse(
								Integer.valueOf(finalMeritListForm
										.getCourseId()));
				if (selectedList > maxIntake) {
					maxIntake = 0;
				} else {
					maxIntake = maxIntake - selectedList;
				}
				finalMeritListForm.setProgramWise(0);
				finalMeritListForm.setMaxIntake(maxIntake);
			}else{
				finalMeritListForm.setCourseId(null);
				 List<String> intakeExcessList =  validateIntake(finalMeritListForm);
				
				if (intakeExcessList != null && !intakeExcessList.isEmpty()) {
					 request.setAttribute("intakeExcessList", intakeExcessList);
					errors.add(CMSConstants.COURSE_INTAKE_EXCEEDS_LIST, new ActionError(CMSConstants.COURSE_INTAKE_EXCEEDS_LIST));
					saveErrors(request, errors);
				}
					FinalMeritListSearchHandlar.getInstanse().updateSelectedCandidates(
							finalMeritListForm.getSelectedCandidates(),!finalMeritListForm.isNeedApproval(),
							true, finalMeritListForm.getUserId(),"update");
					if(!finalMeritListForm.isNeedApproval()){
						//FinalMeritListSearchHandlar.getInstanse().sendMailToStudent(finalMeritListForm,request);
						FinalMeritListSearchHandlar.getInstanse().sendMailToApprovedStudent(finalMeritListForm,request);
					}
					message = new ActionMessage("knowledgepro.admission.addtolist");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					
					Map<Integer, Integer> selectedListMap = FinalMeritListSearchHandlar.getInstanse()
					.getFinalMeritListSelectedCountCoursewise(finalMeritListForm, true,
							null);
					
					// Max intake check
					Map<Integer, Integer> maxIntakeMap = FinalMeritListSearchHandlar.getInstanse()
							.getMaxIntakeFromProgram(Integer.valueOf(finalMeritListForm.getProgramId().trim()));
					
					Iterator selectedIntakeItr = selectedListMap.keySet().iterator();
					int selectedCount = 0;
					int maxIntakeCount = 0;
					while (selectedIntakeItr.hasNext()) {
						Integer selectedIntakeValue = (Integer) selectedIntakeItr.next();
						
						if(maxIntakeMap.containsKey(selectedIntakeValue)){
							selectedCount = selectedListMap.get(selectedIntakeValue);
							maxIntakeCount = maxIntakeMap.get(selectedIntakeValue);
							
							if(selectedCount > maxIntakeCount){
								maxIntakeMap.put(selectedIntakeValue, Integer.valueOf(0));
							}else{
								maxIntakeMap.put(selectedIntakeValue, Integer.valueOf(maxIntakeCount - selectedCount));
							}
						}
					}
					finalMeritListForm.setProgramWise(1);
					finalMeritListForm.setMaxIntakeMap(maxIntakeMap);
			}
			
			} catch (Exception e) {
				log.error("Error while submitting selected list"+e.getMessage());
				String msg = super.handleApplicationException(e);
				finalMeritListForm.setErrorMessage(msg);
				finalMeritListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}

		try {
			finalMeritListForm.setFinalMeritList(FinalMeritListSearchHandlar
					.getInstanse().getFinalMeritListSearchResult(
							finalMeritListForm, false, false));
		} catch (Exception e) {
			log.error("Error while setting the data"+e.getMessage() );
			String msg = super.handleApplicationException(e);
			finalMeritListForm.setErrorMessage(msg);
			finalMeritListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}

		finalMeritListForm.setSelectedCandidates(null);
		log.info("exit of submitSelectedList of FinalMeritListSearchAction class.");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_SEARCH);

	}

	/**
	 * 
	 * Performs the initializes final merit list selection list action.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initSelectedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initSelectedList of FinalMeritListSearchAction class.");
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;

		try {
//			//course empty means program wise
//			if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
//				finalMeritListForm.setProgramWise(false);
//			}
			finalMeritListForm.setFinalMeritList(FinalMeritListSearchHandlar
					.getInstanse().getFinalMeritListSelectedResult(
							finalMeritListForm, true, null));
		} catch (Exception e) {
			log.error("Error while initializing selected list"+e.getMessage() );
			String msg = super.handleApplicationException(e);
			finalMeritListForm.setErrorMessage(msg);
			finalMeritListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("exit of initSelectedList of FinalMeritListSearchAction class.");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_SELECTED);

	}

	/**
	 * 
	 * Removes selected candidates from the list.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward removeFromSelectedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into removeFromSelectedList of FinalMeritListSearchAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		setUserId(request, finalMeritListForm);
		if (finalMeritListForm.getSelectedCandidates() != null) {
			try {
				FinalMeritListSearchHandlar.getInstanse()
						.updateSelectedCandidates(
								finalMeritListForm.getSelectedCandidates(),false,
								false, finalMeritListForm.getUserId(),"remove");
				message = new ActionMessage(
						"knowledgepro.admission.addremovefromlist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
			} catch (Exception e) {
				log.error("Error while removing selected list"+e.getMessage() );
				String msg = super.handleApplicationException(e);
				finalMeritListForm.setErrorMessage(msg);
				finalMeritListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}

		}
		try {
			finalMeritListForm.setFinalMeritList(FinalMeritListSearchHandlar
					.getInstanse().getFinalMeritListSelectedResult(
							finalMeritListForm, true, null));
		} catch (Exception e) {
			log.error("Error while setting the data"+e.getMessage() );
			String msg = super.handleApplicationException(e);
			finalMeritListForm.setErrorMessage(msg);
			finalMeritListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

//		finalMeritListForm.setSelectedCandidates(null);

		log.info("exit of removeFromSelectedList of FinalMeritListSearchAction class.");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_SELECTED);

	}

	/**
	 * 
	 * Get the admission details.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getAdmissionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_SEARCH);

	}

	/**
	 * Method to validate the uploaded file size
	 * @param interviewResultEntryForm
	 * @param errors
	 * @return
	 */
	private List<String> validateIntake(FinalMeritListForm finalMeritListForm)throws Exception  {
		
		Map<Integer, Integer> maxIntakeMap = finalMeritListForm.getMaxIntakeMap();
		
		Map<Integer, Integer> selectedIntakeMap = new HashMap<Integer, Integer>();
		List<Integer> intakeCourseList = new ArrayList<Integer>();
		List<String> intakeExcessList = new ArrayList<String>();
		List<FinalMeritListSearchTo> selectedTOList = finalMeritListForm.getFinalMeritList();
		
		String []selectedCandidates = finalMeritListForm.getSelectedCandidates();
		
		for(int i=0; i< selectedCandidates.length; i++){
			
			Iterator<FinalMeritListSearchTo> selectedIntakeItr = selectedTOList.iterator();
			int count =0;
			
			while (selectedIntakeItr.hasNext()) {
				FinalMeritListSearchTo selectedIntake = (FinalMeritListSearchTo) selectedIntakeItr.next();
				
				if(selectedIntake.getFinalMeritSetId().equals(selectedCandidates[i])){
					if(selectedIntakeMap.containsKey(Integer.valueOf(selectedIntake.getMaxIntakeCourseId()))){
						count = selectedIntakeMap.get(Integer.valueOf(selectedIntake.getMaxIntakeCourseId()));
						count = count +1;
						selectedIntakeMap.put(Integer.parseInt(selectedIntake.getMaxIntakeCourseId()), Integer.valueOf(count));
					}else{
						selectedIntakeMap.put(Integer.parseInt(selectedIntake.getMaxIntakeCourseId()), Integer.valueOf(1));
					}
				}
			}
		}
		
		Iterator selectedIntakeMapItr = selectedIntakeMap.keySet().iterator();
		int selectedIntakeValue = 0;
		int maxIntakeValue = 0;
		while (selectedIntakeMapItr.hasNext()) {
			Integer object = (Integer) selectedIntakeMapItr.next();
			
			if(maxIntakeMap.containsKey(object)){
				selectedIntakeValue = selectedIntakeMap.get(object).intValue();
				maxIntakeValue = maxIntakeMap.get(object).intValue();
			}
			
			if(selectedIntakeValue > maxIntakeValue){
				intakeCourseList.add(object);
			}
		}
		
		Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(finalMeritListForm.getProgramId()));
		
		Iterator courseItr = courseMap.keySet().iterator();
		
		while (courseItr.hasNext()) {
			Integer course = (Integer) courseItr.next();
			
			if(intakeCourseList.contains(course)){
				intakeExcessList.add(courseMap.get(course));
			}
		}
		return intakeExcessList;
	}
}
