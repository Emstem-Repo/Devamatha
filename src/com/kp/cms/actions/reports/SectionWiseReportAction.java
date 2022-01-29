package com.kp.cms.actions.reports;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.SectionWiseForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.SectionWiseReportHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.SectionWiseReportTO;

public class SectionWiseReportAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(SectionWiseReportAction.class);
	private static final String SECTION_WISE_REPORT = "sectionWiseReport";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSectionWiseReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		SectionWiseForm sectionWiseForm = (SectionWiseForm) form;
		try{
			log.info("Entered initSectionWiseReport");
			sectionWiseForm.resetFields();
			setRequiredDataToForm(request);
			if(request.getParameter("KJC")!=null){
				sectionWiseForm.setKjcReport(true);
			}else{
				sectionWiseForm.setKjcReport(false);
			}
			HttpSession session = request.getSession(false);
			if(sectionWiseForm.getSectionWiseList() != null && sectionWiseForm.getSectionWiseList().size() !=0){
				List<String> sectionWiseList = sectionWiseForm.getSectionWiseList();
				Iterator<String> sectionWiseListIterator = sectionWiseList.iterator();
				int size=0;
				while (sectionWiseListIterator.hasNext()) {
					//String integer = (String) sectionWiseListIterator.next();
					if(session.getAttribute(SECTION_WISE_REPORT+size)!=null)
					session.removeAttribute(SECTION_WISE_REPORT+size);
					size++;
				}
			}
			log.info("Exit initSectionWiseReport");
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			sectionWiseForm.setErrorMessage(msg);
			sectionWiseForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("sectionWiseReportInput");
	}

	/**
	 * Method to set the required values to the form to display it in the UI.
	 * @param request
	 * @throws Exception
	 */
	public void setRequiredDataToForm(HttpServletRequest request) throws Exception {
		log.info("Entered setRequiredDataToForm..");
		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.info("Exit setRequiredDataToForm..");
	}
	
	/**
	 * This method is used to get the leave report details from database.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward submitSectionWiseReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered submitSectionWiseReport method of SectionWiseReportAction class..");
		SectionWiseForm sectionWiseForm = (SectionWiseForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = sectionWiseForm.validate(mapping, request);
		if (errors.isEmpty()) {	
			try {
				
				HttpSession session = request.getSession();
				List<OrganizationTO> list = OrganizationHandler.getInstance().getOrganizationDetails();
				if(list != null && list.size() != 0){
					Iterator<OrganizationTO> iterator = list.iterator();
					while (iterator.hasNext()) {
						OrganizationTO organizationTO = (OrganizationTO) iterator
								.next();
						sectionWiseForm.setOrganizationName(organizationTO.getOrganizationName());
					}
				}
			//call of handler.
			Map<Integer,List<SectionWiseReportTO>> sectionWiseMap = SectionWiseReportHandler.getInstance().getSectionWiseReportDetails(sectionWiseForm);
			if(sectionWiseMap != null && sectionWiseMap.size() != 0){
				Iterator<List<SectionWiseReportTO>> classCandidateListIterator = sectionWiseMap.values().iterator();
				String className = "";
				sectionWiseForm.getSectionWiseList().clear();
				int size = 0;
				while (classCandidateListIterator.hasNext()) {
					List<SectionWiseReportTO> list2 = (List<SectionWiseReportTO>) classCandidateListIterator
							.next();
					if(!list2.isEmpty()) {
						
						Iterator<SectionWiseReportTO> iterator = list2.iterator();
						while (iterator.hasNext()) {
							SectionWiseReportTO reportTO = (SectionWiseReportTO) iterator
									.next();
							
							className = reportTO.getClassName();
							if(!sectionWiseForm.getSectionWiseList().contains(className)){
								sectionWiseForm.getSectionWiseList().add(className);
							}
						}
					}
//						sectionWiseForm.getSectionWiseList().add(size);
					session.setAttribute(SECTION_WISE_REPORT+size,list2);
					size++;
				}
			}else{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				setRequiredDataToForm(request);
				return mapping.findForward("sectionWiseReportInput");
			}
			}catch (BusinessException businessException) {
				log.info("Exception submitSectionWiseReport");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				sectionWiseForm.setErrorMessage(msg);
				sectionWiseForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			}else {
				addErrors(request, errors);
				setRequiredDataToForm(request);
				return mapping.findForward("sectionWiseReportInput");
			}
		log.info("Exit of submitSectionWiseReport method of SectionWiseReportAction class..");
		if(sectionWiseForm.isKjcReport()){
			return mapping.findForward("sectionWiseReportResultForKJC");
		}else{
			return mapping.findForward("submitsectionWiseReport");
		}
	}
}