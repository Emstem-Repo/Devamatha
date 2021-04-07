package com.kp.cms.actions.fee;

import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.fee.FeeGroupHandler;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.to.fee.BulkChalanTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;


public class BulkChallanPrintForCJCAction extends BaseDispatchAction
{
	/*private static final String CLASSMAP = "classMap";
	private static final Log log = LogFactory.getLog(BulkChallanPrintForCJCAction.class);

	*//**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 *//*
	public ActionForward initBulkChallan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		FeePaymentForm feePaymentForm=(FeePaymentForm)form;
		feePaymentForm.clear();
		feePaymentForm.setTotalConcession(0);
		feePaymentForm.setTotalInstallment(0);
		feePaymentForm.setTotalScholarship(0);
		feePaymentForm.setTotalBalance(0);
		try
		{
			setClassMapToForm(feePaymentForm);
			feePaymentForm.setFeeGroupList(FeeGroupHandler.getInstance().getFeeGroups());
			feePaymentForm.setError("false");
			feePaymentForm.setPrintChallan("false");
			feePaymentForm.setFeeDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "mm/dd/yyyy", "dd/mm/yyyy"));
		}
		catch(Exception e){
	 		String msg = super.handleApplicationException(e);
			feePaymentForm.setErrorMessage(msg);
			feePaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
		return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
	}
	
	*//**
	 * @param feePaymentForm
	 *//*
	private void setClassMapToForm(FeePaymentForm feePaymentForm) throws Exception{
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(feePaymentForm.getAcademicYearForClass()!=null && !feePaymentForm.getAcademicYearForClass().isEmpty()){
			year=Integer.parseInt(feePaymentForm.getAcademicYearForClass());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		Map<Integer,String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(year);
		feePaymentForm.setClassMap(classMap);
	}

	*//**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 *//*
	public ActionForward checkprintBulkChallan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		FeePaymentForm feePaymentForm=(FeePaymentForm)form;
		 ActionErrors errors = feePaymentForm.validate(mapping, request);
		feePaymentForm.setPrintChallan("false");
		if(feePaymentForm.getSelectedfeeGroup()==null || feePaymentForm.getSelectedfeeGroup().length==0)
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.feeGroup.req"));
		else
		if(feePaymentForm.getSelectedfeeGroup()[0].trim().isEmpty())
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.feeGroup.req"));
		if(feePaymentForm.getFromReg()!=null && !feePaymentForm.getFromReg().trim().isEmpty())
		{
			if(feePaymentForm.getToReg()==null || feePaymentForm.getToReg().trim().isEmpty())
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.to.reg.req"));
		}
		if(feePaymentForm.getToReg()!=null && !feePaymentForm.getToReg().trim().isEmpty())
		{
			if(feePaymentForm.getFromReg()==null || feePaymentForm.getFromReg().trim().isEmpty())
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.from.reg.req"));
		}
		if(feePaymentForm.getFeeDate()==null || feePaymentForm.getFeeDate().isEmpty())
		{
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.Date.req"));
		}
		else
		{
			if(!CommonUtil.isValidDate(feePaymentForm.getFeeDate()))
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.curriculumscheme.dateformat.invalid"));
		}
		
		try
		{
			if(!errors.isEmpty()) 
			{  
				saveErrors(request, errors);
	 			feePaymentForm.setError(CMSConstants.TRUE);
			}
			else
			{
				feePaymentForm.setPrintChallan(CMSConstants.TRUE);
				HttpSession session = request.getSession();
				session.setAttribute("FeeYear", feePaymentForm.getFinancialYearId());
			}
			setClassMapToForm(feePaymentForm);
		}
		catch(Exception e){
	 		String msg = super.handleApplicationException(e);
			feePaymentForm.setErrorMessage(msg);
			feePaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
		return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
	}
	
	*//**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 *//*
	public ActionForward printBulkChallan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		FeePaymentForm feePaymentForm=(FeePaymentForm)form;
		HttpSession session = request.getSession();
		ActionMessages errors = new ActionErrors();
		String year = session.getAttribute("FeeYear").toString();
		feePaymentForm.setFinancialYearId(year);
		try
		{
			List<BulkChalanTo>bulkChalanTos=new ArrayList<BulkChalanTo>();
			feePaymentForm.setBulkChallanList(bulkChalanTos);
			FeePaymentHandler.getInstance().printBulkChallan(feePaymentForm,request,errors);
		}
		catch(Exception e){
	 		String msg = super.handleApplicationException(e);
			feePaymentForm.setErrorMessage(msg);
			feePaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
		String feeGroupName=feePaymentForm.getFeeGroupName();
 		if(feeGroupName.startsWith("Aided"))
 			return mapping.findForward(CMSConstants.FEE_PAYMENT_PRINTCHALLAN_CJC);
 		else
 			return mapping.findForward(CMSConstants.FEE_PAYMENT_PUC_PRINTCHALLAN);
	}
	
	public ActionForward checkReprintBulkChallan(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		FeePaymentForm feePaymentForm=(FeePaymentForm)form;
		 ActionErrors errors = feePaymentForm.validate(mapping, request);
		feePaymentForm.setRePrintChallan("false");
		if(feePaymentForm.getSelectedfeeGroup()==null || feePaymentForm.getSelectedfeeGroup().length==0)
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.feeGroup.req"));
		else
		if(feePaymentForm.getSelectedfeeGroup()[0].trim().isEmpty())
			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.feeGroup.req"));
		if(feePaymentForm.getFromReg()!=null && !feePaymentForm.getFromReg().trim().isEmpty())
		{
			if(feePaymentForm.getToReg()==null || feePaymentForm.getToReg().trim().isEmpty())
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.to.reg.req"));
		}
		if(feePaymentForm.getToReg()!=null && !feePaymentForm.getToReg().trim().isEmpty())
		{
			if(feePaymentForm.getFromReg()==null || feePaymentForm.getFromReg().trim().isEmpty())
				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.bulk.fee.payment.from.reg.req"));
		}
						
		try
		{
			if(!errors.isEmpty()) 
			{  
				saveErrors(request, errors);
	 			feePaymentForm.setError(CMSConstants.TRUE);
			}
			else
			{
				feePaymentForm.setPrintChallan(CMSConstants.FALSE);
				feePaymentForm.setRePrintChallan(CMSConstants.TRUE);
				HttpSession session = request.getSession();
				session.setAttribute("FeeYear", feePaymentForm.getFinancialYearId());
			}
		}
		catch(Exception e){
	 		String msg = super.handleApplicationException(e);
			feePaymentForm.setErrorMessage(msg);
			feePaymentForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	 	}
		return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
	}
	
	
	public ActionForward getReprintBulkChallanData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering reprintChallan ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
	 	//	feePaymentForm.clear();
		 	ActionMessages errors = feePaymentForm.validate(mapping, request);
		 	try {
		 			if(feePaymentForm.getFinancialYearId() == null || feePaymentForm.getFinancialYearId().trim().isEmpty()){
		 				errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.financial.year"));
		 				saveErrors(request, errors);	
		 				return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
		 			}
		 		
		 		if(errors.isEmpty()){
		 			List<BulkChalanTo>bulkChallanList=new ArrayList<BulkChalanTo>();
		 			feePaymentForm.setBulkChallanList(bulkChallanList);
		 			FeePaymentHandler.getInstance().getReprintBulkChallanData(feePaymentForm, request);
		 			
		 		//	if(feePaymentForm.getPucFeePayment())
		 		    FeePaymentHandler.getInstance().addToBulkChallanTo(feePaymentForm);
		 			feePaymentForm.setRePrintChallan(CMSConstants.TRUE);
		 			
		 		} else {
		 			saveErrors(request, errors);
		 			return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
		 		}
		 	} catch(DataNotFoundException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_DATA_NOT_FOUND));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.BULK_CHALLAN_PRINT);
		 	
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving reprintChallan ");
		 	String feeGroupName=feePaymentForm.getFeeGroupName();
		 	
	 		if(feeGroupName.startsWith("Aided"))
	 			return mapping.findForward(CMSConstants.FEE_PAYMENT_PRINTCHALLAN_CJC);
	 		else
	 			return mapping.findForward(CMSConstants.FEE_PAYMENT_PUC_PRINTCHALLAN);
		
	}

*/
}
