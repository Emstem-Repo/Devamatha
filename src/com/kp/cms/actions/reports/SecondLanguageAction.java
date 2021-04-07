package com.kp.cms.actions.reports;

import java.util.HashMap;
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
import com.kp.cms.forms.reports.SecondLanguageForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.SecondLanguageHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.CourseWithStudentTO;

public class SecondLanguageAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SecondLanguageAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns initializes secondLanguage Report
	 * @throws Exception
	 */
	public ActionForward initSecondLanguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initSecondLanguage. of SecondLanguageAction");	
		SecondLanguageForm languageForm = (SecondLanguageForm)form;
		try {
			languageForm.clear();
			setRequiredDataToForm(languageForm, request);
			HttpSession session = request.getSession(false);
			if(session.getAttribute("secondLanguageStudentList")!=null){
				session.removeAttribute("secondLanguageStudentList");
			}
			if(session.getAttribute("totalStudentList")!=null){
				session.removeAttribute("totalStudentList");
			}
		} catch (Exception e) {
			log.error("Error in initSecondLanguage in SecondLanguageAction",e);
			String msg = super.handleApplicationException(e);
			languageForm.setErrorMessage(msg);
			languageForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initSecondLanguage. of SecondLanguageAction");	
		return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns students of the second language
	 * @throws Exception
	 */
	public ActionForward submitSecondLaguage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered submitSecondLaguage. of SecondLanguageAction");	
		SecondLanguageForm languageForm = (SecondLanguageForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("secondLanguageStudentList")==null && 
		session.getAttribute("totalStudentList")==null){
			 ActionErrors errors = languageForm.validate(mapping, request);
			try{
				if(errors.isEmpty()){
					List<CourseWithStudentTO> secondLanguageStudentList = SecondLanguageHandler.getInstance().getSecondLanguageStudents(languageForm);
					session.setAttribute("secondLanguageStudentList", secondLanguageStudentList);
					session.setAttribute("totalStudentList", languageForm.getTotalStudentList());
					languageForm.setStudentList(secondLanguageStudentList);
					//Required to get the organization name
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();					
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						languageForm.setOrganizationName(orgTO.getOrganizationName());
					}
				}
				else{
					setRequiredDataToForm(languageForm, request);
					addErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_SECOND_LANGUAGE);
				}
			}
			catch (Exception e) {
				log.error("Error in submitSecondLaguage in SecondLanguageAction",e);
				String msg = super.handleApplicationException(e);
				languageForm.setErrorMessage(msg);
				languageForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving submitSecondLaguage. of SecondLanguageAction");	
		return mapping.findForward(CMSConstants.SECOND_LANGUAGE_RESULT);
	}
	
	
	/*
	 * This method sets the required data to form and request.
	 */
	public void setRequiredDataToForm(SecondLanguageForm languageForm ,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of SecondLanguageAction");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
			
			Map<Integer,String> courseMap = new HashMap<Integer,String>();
			//Setting courseMap to Request
			if(languageForm.getProgramId()!=null && !languageForm.getProgramId().isEmpty()){
				courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(languageForm.getProgramId()));
			}
			request.setAttribute("courseMap", courseMap);
			
			Map<String,String> secondLanguageMap = SecondLanguageHandler.getInstance().getAllSecondLanguages();
			request.setAttribute("secondLanguageMap", secondLanguageMap);			
			log.info("Exit setRequiredDataToForm.SecondLanguageAction");	
	}
}
