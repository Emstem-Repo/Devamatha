package com.kp.cms.actions.employee;

import java.util.List;

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
import com.kp.cms.forms.employee.EmployeeSearchForm;
import com.kp.cms.handlers.employee.EmployeeSearchHandler;
import com.kp.cms.to.employee.EmployeeSearchTO;

public class EmployeeSearchAction extends BaseDispatchAction{

	private static final Log log = LogFactory.getLog(EmployeeSearchAction.class);
	
	public ActionForward initEmpSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initEmpSearch in EmployeeSearchAction class..");
		log.info("end of initEmpSearch in EmployeeSearchAction class..");
		return mapping.findForward(CMSConstants.EMP_SEARCH);
	}
	
	public ActionForward searchEmployee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering of initEmpSearch in EmployeeSearchAction class..");
		EmployeeSearchForm searchForm = (EmployeeSearchForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = searchForm.validate(mapping, request);
		try	{
		if (errors.isEmpty()) {
			setUserId(request, searchForm);
			List<EmployeeSearchTO> empList = EmployeeSearchHandler.getInstance().getSearchBy(searchForm);
			if(empList != null && empList.size() != 0){
				searchForm.setEmpSearchList(empList);
			}else{
				searchForm.reset(mapping, request);
				messages.add(CMSConstants.ERROR,new ActionMessage(CMSConstants.KNOWLEDGEPRO_NORECORDS));
				addErrors(request, messages);
				return mapping.findForward(CMSConstants.EMP_SEARCH);
			}
		}else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.EMP_SEARCH);
		}
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			searchForm.setErrorMessage(msg);
			searchForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of initEmpSearch in EmployeeSearchAction class..");
		return mapping.findForward(CMSConstants.EMP_SEARCH_RESULT);
	}
}