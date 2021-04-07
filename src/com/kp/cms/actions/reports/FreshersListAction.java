package com.kp.cms.actions.reports;

import java.util.Calendar;
import java.util.Date;
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
import com.kp.cms.forms.reports.FreshersListForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.FreshersListHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.StudentTO;

public class FreshersListAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(FreshersListAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns initializes freshers List home page
	 * @throws Exception
	 */
		
	public ActionForward initFreshersList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into initFreshersList of FreshersListAction");
		FreshersListForm listForm = (FreshersListForm)form;
		try {
			//Sets programType to formbean
			setRequiredDataToForm(listForm, request);
			listForm.clear();
			HttpSession session = request.getSession(false);
			if(session.getAttribute("fresherList")!=null){
				session.removeAttribute("fresherList");
			}
		} catch (Exception e) {
			log.error("Error occured in initFreshersList of FreshersListAction", e);
			String msg = super.handleApplicationException(e);
			listForm.setErrorMessage(msg);
			listForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initFreshersList of FreshersListAction");
		return mapping.findForward(CMSConstants.INIT_FRESHERS_LIST);
	}
	
	/*
	 * This method sets the required data to form and request.
	 */
	
	public void setRequiredDataToForm(FreshersListForm listForm,HttpServletRequest request) throws Exception{
		log.info("entered setRequiredDataToForm. of FreshersListAction");	
		    //setting programTypeList to Request
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			request.setAttribute("programTypeList", programTypeList);
			Map<Integer,String> programMap = new HashMap<Integer,String>();
			if(listForm.getProgramTypeId()!=null && listForm.getProgramTypeId().length()>0){
				programMap = CommonAjaxHandler.getInstance()
				.getProgramsByProgramType(Integer.parseInt(listForm.getProgramTypeId()));
			}
			request.setAttribute("programMap", programMap);
			Map<Integer,String> classMap = new HashMap<Integer,String>();
			if(listForm.getProgramId()!=null && listForm.getProgramId().length()>0){
				classMap = CommonAjaxHandler.getInstance()
				.getClassesByProgram(Integer.parseInt(listForm.getProgramId()));
			}
			request.setAttribute("classMap", classMap);
			log.info("Exit setRequiredDataToForm of FreshersListAction");	
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @returns fresher students list in class wise
	 * @throws Exception
	 */
	public ActionForward submitFreshersList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into submitFreshersList of FreshersListAction");
		FreshersListForm listForm = (FreshersListForm)form;
		HttpSession session = request.getSession(false);
		if(session.getAttribute("fresherList")==null){
			try{
				 ActionErrors errors = listForm.validate(mapping, request);
				if(errors.isEmpty()){
					List<StudentTO> fresherList = FreshersListHandler.getInstance().getFresherStudentList(listForm);
					session.setAttribute("fresherList", fresherList);
					//Getting the organization details
					OrganizationHandler orgHandler= OrganizationHandler.getInstance();
					List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
					if(tos!=null && !tos.isEmpty())
					{
						OrganizationTO orgTO=tos.get(0);
						listForm.setOrganizationName(orgTO.getOrganizationName());
					}
					Calendar cal= Calendar.getInstance();
					cal.setTime(new Date());
					int currentYear = cal.get(cal.YEAR);
					int nextYear = currentYear+1;
					listForm.setYear(String.valueOf(currentYear));
					listForm.setNextYear(String.valueOf(nextYear));
				}
				else{
					addErrors(request, errors);
					setRequiredDataToForm(listForm, request);
					return mapping.findForward(CMSConstants.INIT_FRESHERS_LIST);
				}	
			}		
			catch (Exception e) {
				log.error("Error occured in initFreshersList of FreshersListAction", e);
				String msg = super.handleApplicationException(e);
				listForm.setErrorMessage(msg);
				listForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		log.info("Leaving into submitFreshersList of FreshersListAction");
		return mapping.findForward(CMSConstants.FRESHERS_LIST_RESULT);
	}
}