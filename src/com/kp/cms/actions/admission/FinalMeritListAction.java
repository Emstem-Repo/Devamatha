package com.kp.cms.actions.admission;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.FinalMeritListForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.ResidentCategoryHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.FinalMeritListSearchHandlar;
import com.kp.cms.helpers.admission.FinalMeritListSearchHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.FinalMeritListSearchTo;
import com.kp.cms.transactionsimpl.ajax.CommonAjaxImpl;

/**
 *  Perform final merit list search related actions.
 */
public class FinalMeritListAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(FinalMeritListAction.class);
	
	
	/**
	 * 
	 * Performs the initialize final merit list .
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
	public ActionForward initFinalMeritList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initFinalMeritList of FinalMeritListAction class");
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		finalMeritListForm.resetFields(finalMeritListForm);
		setRequiredDatatoForm(finalMeritListForm, request);
		List<OrganizationTO> tos=OrganizationHandler.getInstance().getOrganizationDetails();
		if(tos!=null && !tos.isEmpty())
		{
			OrganizationTO orgTO=tos.get(0);
			finalMeritListForm.setNeedApproval(orgTO.isFinalMeritListApproval());
		}
		log.info("exit into initFinalMeritList of FinalMeritListAction class");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST);
	}

	/**
	 * 
	 * Performs the cancel final merit list search action.
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
	public ActionForward cancelFinalMeritList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into cancelFinalMeritList of FinalMeritListAction class");
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		finalMeritListForm.resetFields(finalMeritListForm);
		setRequiredDatatoForm(finalMeritListForm, request);
		List<OrganizationTO> tos=OrganizationHandler.getInstance().getOrganizationDetails();
		if(tos!=null && !tos.isEmpty())
		{
			OrganizationTO orgTO=tos.get(0);
			finalMeritListForm.setNeedApproval(orgTO.isFinalMeritListApproval());
		}
		log.info("exit into cancelFinalMeritList of FinalMeritListAction class");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST);
	}

	/**
	 * 
	 * Performs the get final merit list search results action.
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
	public ActionForward getFinalMeritList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into getFinalMeritList of FinalMeritListAction class");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;

		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		ActionErrors errors = finalMeritListForm.validate(mapping, request);
		if (errors.isEmpty()) {

			try {
				finalMeritListForm.setUnivercity(finalMeritListForm
						.getUnivercityName());
				finalMeritListForm.setReligionName(finalMeritListForm
						.getReligionId());
				finalMeritListForm.setResidentCategoryName(finalMeritListForm
						.getResidentCategoryId());
				finalMeritListForm.setIsRural(finalMeritListForm.getBelongsTo());

				if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
					
					int selectedList = FinalMeritListSearchHandlar.getInstanse()
					.getFinalMeritListSelectedCount(finalMeritListForm, true,
							null);
					
					// Max intake check
					int maxIntake = FinalMeritListSearchHandlar.getInstanse()
							.getMaxIntakeFromCourse(
									Integer.valueOf(finalMeritListForm.getCourseId()));
					if (selectedList > maxIntake) {
						maxIntake = 0;
					} else {
						maxIntake = maxIntake - selectedList;
					}
					finalMeritListForm.setProgramWise(0);
					// set max intake
					finalMeritListForm.setMaxIntake(maxIntake);
				}else{
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
				
				if(finalMeritListForm.getCourseId()!=null && !finalMeritListForm.getCourseId().trim().isEmpty()){
					Map<Integer,String> interviewMap = CommonAjaxImpl.getInstance()
					.getinterViewTypybyCourse(
							Integer.valueOf(finalMeritListForm.getCourseId()),
							Integer.valueOf(finalMeritListForm.getYear()));
					// interview present for course or not
					finalMeritListForm.setInterviewPresentForCourse(interviewMap.isEmpty());
				}else{
					Map<Integer,String> interviewMap = CommonAjaxImpl.getInstance()
					.getInterviewTypebyProgram(
							Integer.valueOf(finalMeritListForm.getProgramId()),
							Integer.valueOf(finalMeritListForm.getYear()));
					// interview present for course or not
					finalMeritListForm.setInterviewPresentForCourse(interviewMap.isEmpty());
				}
				
				// not selected list
				List<FinalMeritListSearchTo> searchresults = FinalMeritListSearchHandlar
						.getInstanse().getFinalMeritListSearchResult(
								finalMeritListForm, false, false);

				if (searchresults.isEmpty()) {
					message = new ActionMessage(
							"knowledgepro.admission.noresultsfound");
					messages.add(CMSConstants.MESSAGES, message);
					addErrors(request, messages);
				}

				finalMeritListForm.setFinalMeritList(searchresults);
				FinalMeritListSearchHandlar.getInstanse().createFinalMeritListMailTO(finalMeritListForm);
			} catch(Exception e) {
				log.error("Error while submitting selected list"+e.getMessage());
				String msg = super.handleApplicationException(e);
				finalMeritListForm.setErrorMessage(msg);
				finalMeritListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			log.info("exit into getFinalMeritList of FinalMeritListAction class");
			return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_SEARCH);
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(finalMeritListForm, request);
			return mapping.findForward(CMSConstants.FINAL_MERIT_LIST);
		}

	}

	/**
	 * Set the required data to form.
	 * 
	 * @param finalMeritListForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(FinalMeritListForm finalMeritListForm,
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDatatoForm of FinalMeritListAction class");
		// Set Religion
		ReligionHandler religiHandler = ReligionHandler.getInstance();
		request.setAttribute("religionList", religiHandler.getReligion());

		// Set resident category
		ResidentCategoryHandler residentCategoryHandler = ResidentCategoryHandler
				.getInstance();
		request.setAttribute("residentCategoryList", residentCategoryHandler
				.getResidentCategory());

		List<UniversityTO> universityList = UniversityHandler.getInstance()
				.getUniversity();

		request.setAttribute("univercityList", universityList);

		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);

		List<CasteTO> casteCategoryList = CasteHandler.getInstance()
				.getCastes();
		request.setAttribute("casteCategoryList", casteCategoryList);

		List<NationalityTO> countryList;
		try {
			countryList = FinalMeritListSearchHelper.getNationalities();
			request.setAttribute("countryList", countryList);
		} catch (Exception e) {
			log.error("error in setRequiredDatatoForm",e);
			throw new ApplicationException(e);

		}
		log.info("exit into setRequiredDatatoForm of FinalMeritListAction class");
	}
}