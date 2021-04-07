package com.kp.cms.actions.reports;

import java.util.Calendar;
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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ResidentCategory;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.ClasswiseStudentListForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.reports.ClasswiseStudentListHandler;
import com.kp.cms.handlers.reports.SecondLanguageHandler;
import com.kp.cms.helpers.admission.AdmissionFormHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admin.ResidentCategoryTO;
import com.kp.cms.to.reports.ClassStudentListTO;
import com.kp.cms.transactions.admission.IAdmissionFormTransaction;
import com.kp.cms.transactionsimpl.admission.AdmissionFormTransactionImpl;

@SuppressWarnings("deprecation")
public class ClasswiseStudentListAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ClasswiseStudentListAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initClasswiseStudentList(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		ClasswiseStudentListForm studentListForm = (ClasswiseStudentListForm) form;
		studentListForm.reset(mapping, request);
		HttpSession session = request.getSession(false);
		session.removeAttribute("classwithStudentList");
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			studentListForm.setYear(String.valueOf(currentYear) + "- " + String.valueOf(currentYear + 1));
			setpClassMapToRequest(request);
			setSecondLanguageListToRequest(request);
			setReligionListToRequest(request);
			setCasteListToRequest(request);
			getResidentTypes(request);
			if(CMSConstants.CASTE_ENABLED){
				studentListForm.setCasteDisplay(CMSConstants.CASTE_ENABLED);
			}
			
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			studentListForm.setErrorMessage(msg);
			studentListForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CLASSWISE_STUDENT_LIST_SEARCH);
		
	}	

	/**
	 * creating list and forwarding to jsp page
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward SearchStudentList(ActionMapping mapping, ActionForm form, 
					HttpServletRequest request, HttpServletResponse response) throws Exception{
			
		ClasswiseStudentListForm studentListForm = (ClasswiseStudentListForm) form;	
		HttpSession session = request.getSession(false);
		if(session.getAttribute("classwithStudentList")==null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = studentListForm.validate(mapping, request);
			try {
				if(!errors.isEmpty()){
					saveErrors(request, errors);
					setpClassMapToRequest(request);
					setSecondLanguageListToRequest(request);
					setReligionListToRequest(request);
					setCasteListToRequest(request);
					getResidentTypes(request);
					return mapping.findForward(CMSConstants.CLASSWISE_STUDENT_LIST_SEARCH);
				}
				OrganizationHandler orgHandler= OrganizationHandler.getInstance();
				List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
				if(tos!=null && !tos.isEmpty())
				{
					OrganizationTO orgTO=tos.get(0);

					studentListForm.setOrganizationName(orgTO.getOrganizationName());
				}				
				List<ClassStudentListTO> classStudentList = ClasswiseStudentListHandler.getInstance().getStudentList(studentListForm);
				session.setAttribute("classStudentList", classStudentList);
				session.setAttribute("classwithStudentList", studentListForm.getClassWithStudentToList());
			}catch (BusinessException businessException) {
				log.info("Exception SearchStudentList");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				studentListForm.setErrorMessage(msg);
				studentListForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		return mapping.findForward(CMSConstants.CLASSWISE_STUDENT_LIST_RESULT);
	}	
	
	
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private void setpClassMapToRequest(HttpServletRequest request) {
		log.info("entering into setpClassMapToRequest");
		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);

		Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
				.getClassesByYear(currentYear);
		log.info("exit of setpClassMapToRequest");
		request.setAttribute("classMap", classMap);
	}
	/**
	 * setting second language to request
	 * @param request
	 * @throws Exception
	 */
	public void setSecondLanguageListToRequest(HttpServletRequest request)	throws Exception {
		Map<String,String> secondLanguageMap = SecondLanguageHandler.getInstance().getAllSecondLanguages();
		if(secondLanguageMap.get("All")!= null){
			secondLanguageMap.remove("All");
		}
		request.setAttribute("secondLanguageMap", secondLanguageMap);		
	}
	/**
	 * setting religions to request
	 * @param request
	 * @throws Exception
	 */
	public void setReligionListToRequest(HttpServletRequest request)	throws Exception {
		List<ReligionTO> religionList= ReligionHandler.getInstance().getReligion();
		request.setAttribute("religionList", religionList);
	}
	/**
	 * setting religions to request
	 * @param request
	 * @throws Exception
	 */
	public void setCasteListToRequest(HttpServletRequest request)	throws Exception {
		List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
		request.setAttribute("casteList", castelist);
	}
	
	/**
	 * get resident category list
	 * @return
	 */
	public void getResidentTypes(HttpServletRequest request)throws Exception{
		log.info("Enter getResidentTypes ...");
		IAdmissionFormTransaction txn= new AdmissionFormTransactionImpl();
		List<ResidentCategory> residentbos=txn.getResidentTypes();
		AdmissionFormHelper helper= AdmissionFormHelper.getInstance();
		List<ResidentCategoryTO> residents=helper.convertResidentBOToTO(residentbos);
		log.info("Exit getResidentTypes ...");
		request.setAttribute("residentList", residents);
	}	
	
}
