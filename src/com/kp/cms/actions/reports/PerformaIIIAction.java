package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.PerformaIIIForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ResidentCategoryHandler;
import com.kp.cms.handlers.reports.PerformaIIIHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.reports.PerformaIIIMapTO;
import com.kp.cms.to.reports.PerformaIIITO;

public class PerformaIIIAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(PerformaIIIAction.class);
	private static final String PERFORMA_REPORT = "performaIIIReport";
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPerformaIII(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		PerformaIIIForm performaIIIForm = (PerformaIIIForm) form;
		
		try{
			log.info("Entered initPerformaIII");
			setRequiredDataToForm(request);
			performaIIIForm.resetFields();
			HttpSession session = request.getSession(false);
			session.removeAttribute(PERFORMA_REPORT);
			log.info("Exit initPerformaIII");
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			performaIIIForm.setErrorMessage(msg);
			performaIIIForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.PERFORMA_III_INPUT);
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
	 * Method is to get the candidates based on the search criteria
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitPerformaIII(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered submitPerformaIII");
		PerformaIIIForm performaIIIForm = (PerformaIIIForm) form;
		
		HttpSession session = request.getSession(false);
		if(session.getAttribute(PERFORMA_REPORT)==null){
			
			 ActionErrors errors = performaIIIForm.validate(mapping, request);
			
			if (errors.isEmpty()) {
				try {
					List<OrganizationTO> list = OrganizationHandler.getInstance().getOrganizationDetails();
					if(list != null && list.size() != 0){
						Iterator<OrganizationTO> iterator = list.iterator();
						while (iterator.hasNext()) {
							OrganizationTO organizationTO = (OrganizationTO) iterator
									.next();
							performaIIIForm.setOrganizationName(organizationTO.getOrganizationName());
						}
					}
					Map<Integer, PerformaIIITO> performIIITOMap = PerformaIIIHandler.getInstance().getCourseIntakeDetails(performaIIIForm);				
					
					List<ResidentCategoryTO> categoryList =	ResidentCategoryHandler.getInstance().getResidentCategory();
					
					if (performIIITOMap != null) {
						List<PerformaIIITO> finalList = new ArrayList<PerformaIIITO>();
						finalList.addAll(performIIITOMap.values());
						
						if(finalList != null && !finalList.isEmpty()){
							Iterator<PerformaIIITO> finalListItr = finalList.iterator();
							
							while (finalListItr.hasNext()) {
								PerformaIIITO performaIIITO = (PerformaIIITO) finalListItr.next();
								List<PerformaIIIMapTO> residentCategoryList = new ArrayList<PerformaIIIMapTO>();
								
								Iterator<ResidentCategoryTO> categoryListItr = categoryList.iterator();
								
								while (categoryListItr.hasNext()) {
									ResidentCategoryTO residentCategory = (ResidentCategoryTO) categoryListItr.next();
									
									if(performaIIITO.getCategoryMap().containsKey(residentCategory.getId())){
										residentCategoryList.add(performaIIITO.getCategoryMap().get(residentCategory.getId()));
									}else{
										PerformaIIIMapTO performaIIIMapTO = new PerformaIIIMapTO();
										
										performaIIIMapTO.setCategoryName(residentCategory.getName());
										performaIIIMapTO.setIntakeValue(0);
										residentCategoryList.add(performaIIIMapTO);
									}
								}
								performaIIITO.setCategoryList(residentCategoryList);
							}
						}
						session.setAttribute(PERFORMA_REPORT, finalList);
					}	
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					performaIIIForm.setErrorMessage(msg);
					performaIIIForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			} else {
				addErrors(request, errors);
				setRequiredDataToForm(request);
				return mapping.findForward(CMSConstants.PERFORMA_III_INPUT);
			}
	}
		log.info("Exit submitPerformaIII");
		return mapping.findForward(CMSConstants.PERFORMA_III_REPORT);
	}
}