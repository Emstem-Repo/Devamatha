package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.FeesConcessionReportForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.fee.FeeAccountHandler;
import com.kp.cms.handlers.fee.FeeDivisionHandler;
import com.kp.cms.handlers.reports.FeesConcessionHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.fee.FeeAccountTO;
import com.kp.cms.to.fee.FeeDivisionTO;
import com.kp.cms.to.reports.FeeConcessionReportTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class FeesConcessionAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FeesConcessionAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to fee concession search.
	 * @throws Exception
	 *        
	 */
	public ActionForward initFeeConcReport(ActionMapping mapping,
										   ActionForm form, HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
	 	
		log.debug("Entering initFeeConcReport ");
		FeesConcessionReportForm concessionReportForm = (FeesConcessionReportForm)form;
	 	try {
			concessionReportForm.reset(mapping, request);
			concessionReportForm.setPrint("false");
	 		concessionReportForm.setClassOrDate(true);
	 		concessionReportForm.setDivisionName(null);
	 		setDivListToRequest(request);
	 		setAccListToRequest(request);
	 		setProgramTypeListToRequest(request);
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			concessionReportForm.setErrorMessage(msg);
			concessionReportForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	 	log.debug("Leaving initFeeConcReport");
	 	
	 	return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayConcessionDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		FeesConcessionReportForm concessionReportForm = (FeesConcessionReportForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = concessionReportForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		if(concessionReportForm.getDivId() == null || concessionReportForm.getDivId().trim().isEmpty()){
			concessionReportForm.setDivisionName(null);
		}
		try {
			if(!concessionReportForm.getClassOrDate()){
				if(concessionReportForm.getStartDate() == null || concessionReportForm.getStartDate().trim().isEmpty()){
					errors.add("error", new ActionError("inventory.stockReceipt.amc.stDt.required"));
				}
				if(concessionReportForm.getEndDate() == null || concessionReportForm.getEndDate().trim().isEmpty()){
					errors.add("error", new ActionError("inventory.stockReceipt.amc.endDt.required"));
				}
			}			
			boolean isValidStartDate = true;
			boolean isValidEndDate = true;
			
			if(concessionReportForm.getStartDate()!= null && !concessionReportForm.getEndDate().trim().isEmpty()){
				isValidStartDate = CommonUtil.isValidDate(concessionReportForm.getStartDate());
			}
			if(concessionReportForm.getEndDate()!= null && !concessionReportForm.getEndDate().trim().isEmpty()){
				isValidEndDate = CommonUtil.isValidDate(concessionReportForm.getEndDate());
			}
			//date validation
			if(!isValidStartDate || !isValidEndDate){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMISSION_CURRICULUMSCHEME_DATEFORMAT_INVALID));
			}			
				
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
		 		setDivListToRequest(request);
		 		setAccListToRequest(request);
		 		setClassMapToRequest(request, concessionReportForm);
		 		setProgramTypeListToRequest(request);
				return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT);
			}
			Date startDate = CommonUtil.ConvertStringToDate(concessionReportForm.getStartDate());
			Date endDate = CommonUtil.ConvertStringToDate(concessionReportForm.getEndDate());;
			if(startDate!= null && endDate!= null){	
				if(startDate.compareTo(endDate) == 1){
					errors.add("error", new ActionError("knowledgepro.admission.curriculumscheme.datecompare"));
					saveErrors(request, errors);
			 		setDivListToRequest(request);
			 		setAccListToRequest(request);
			 		setClassMapToRequest(request, concessionReportForm);
			 		setProgramTypeListToRequest(request);
					return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT);
				}
			}
			OrganizationHandler orgHandler= OrganizationHandler.getInstance();
			List<OrganizationTO> tos=orgHandler.getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				concessionReportForm.setOrganizationName(orgTO.getOrganizationName());
			}				
			
			List<FeeConcessionReportTO> concessionList = FeesConcessionHandler.getInstance().getFeePaymentDetails(concessionReportForm);
			if(concessionList == null || concessionList.size() <= 0){
				errors.add("error", new ActionError("knowledgepro.norecords"));
				saveErrors(request, errors);
				setDivListToRequest(request);
		 		setAccListToRequest(request);
		 		setClassMapToRequest(request, concessionReportForm);
		 		setProgramTypeListToRequest(request);
				return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT);				
			}
			session.setAttribute("concessionList", concessionList);
			concessionReportForm.setPrint("true");
			setDivListToRequest(request);
	 		setAccListToRequest(request);
	 		setClassMapToRequest(request, concessionReportForm);
	 		setProgramTypeListToRequest(request);
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				concessionReportForm.setErrorMessage(msg);
				concessionReportForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}		
		return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT);
	
	}
	
	
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	
	public void setDivListToRequest(HttpServletRequest request)throws Exception{	
		List<FeeDivisionTO> feeDivisionList = FeeDivisionHandler.getInstance().getFeeDivisionList();
		request.setAttribute("feeDivList", feeDivisionList);
	}
	/**
	 * 
	 * @param request
	 * @throws Exception
	 */
	
	public void setAccListToRequest(HttpServletRequest request)throws Exception{	
		//raghu
		//List<FeeAccountTO> accountList = FeeAccountHandler.getInstance().getAllFeeAccounts();
		List<FeeAccountTO> accountList=new ArrayList<FeeAccountTO>();
		request.setAttribute("accountList", accountList);
	}
	/**
	 * Sets all the classes for the current year in request scope
	 */
	private void setClassMapToRequest(HttpServletRequest request, FeesConcessionReportForm concessionReportForm) {
		log.info("entering into setpClassMapToRequest");
		Map<Integer, String> classMap = new HashMap<Integer, String>();
		if(concessionReportForm.getProgramTypeId()!= null && !concessionReportForm.getProgramTypeId().trim().isEmpty()){
			classMap = CommonAjaxHandler.getInstance().getClassesByProgramTypeId(Integer.parseInt(concessionReportForm.getProgramTypeId()));
		}
		log.info("exit of setpClassMapToRequest");
		request.setAttribute("classMap", classMap);
	}
	/**
	 * 
	 * @param request
	 *            This method sets the program type list to Request useful in
	 *            populating in program type selection.
	 * @throws Exception
	 */
	public void setProgramTypeListToRequest(HttpServletRequest request)	throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.debug("leaving setProgramTypeListToRequest");
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward displayPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {
		FeesConcessionReportForm concessionReportForm = (FeesConcessionReportForm) form;
		concessionReportForm.setPrint("false");
		log.debug("Entering displayPage ");
		return mapping.findForward(CMSConstants.FEE_CONCESSION_REPORT_RESULT);
	}	

}
