package com.kp.cms.actions.fee;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.hibernate.Session;

import com.ibm.icu.math.BigDecimal;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.FeePayment;
import com.kp.cms.bo.admin.FeeVoucher;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BillGenerationException;
import com.kp.cms.exceptions.ChallanAlreadyPrintedException;
import com.kp.cms.exceptions.CurriculumSchemeNotFoundException;
import com.kp.cms.exceptions.DataNotFoundException;
import com.kp.cms.exceptions.FreeShipException;
import com.kp.cms.exceptions.RegNumNotExistException;
import com.kp.cms.exceptions.SubjectGroupNotDefinedException;
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.forms.fee.FeePaymentForm;
import com.kp.cms.handlers.admin.AdmittedThroughHandler;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamGenHandler;
import com.kp.cms.handlers.fee.FeeCategoryHandler;
import com.kp.cms.handlers.fee.FeePaidHandler;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.helpers.fee.FeePaymentHelper;
import com.kp.cms.to.admin.AdmittedThroughTO;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.fee.FeeCategoryTo;
import com.kp.cms.to.fee.FeeDisplayTO;
import com.kp.cms.to.fee.FeePaymentDisplayTO;
import com.kp.cms.to.fee.FeePaymentEditTO;
import com.kp.cms.to.fee.FeePaymentTO;
import com.kp.cms.to.fee.FeeStudentDetailTO;
import com.kp.cms.transactionsimpl.fee.FeePaymentTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;
import com.kp.cms.utilities.HibernateUtil;

/**
 * Action class for FeePayment related.
 * This class handles the making,canceling,viewing of fee payments.
 */
@SuppressWarnings("deprecation")
public class FeePaymentAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(FeePaymentAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to feeApplicationSearch.
	 * @throws Exception
	 */
	public ActionForward initFeePaymentSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		 	log.debug("Entering initFeePaymentSearch ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	feePaymentForm.clear();
		 	feePaymentForm.setApplicationId(null);
		 	feePaymentForm.setRegistrationNo(null);
		 	feePaymentForm.setRollNumber(null);
		 	feePaymentForm.setYear(null);
		 	feePaymentForm.setAdmApplnId(null);
		 	feePaymentForm.setFeePayStudentList(null);
		 	feePaymentForm.setPersonalDataId(null);
		 	feePaymentForm.setIsCurrent("yes");
		 	feePaymentForm.setStudentName(null);
		 	feePaymentForm.setClassName(null);
		 	feePaymentForm.setClassId(null);
			//FeePaidHandler.getInstance().setFinancialYearId(feePaymentForm);
		 	setClassMapToRequest(request, feePaymentForm);
		 	HttpSession session = request.getSession();
		 	session.setAttribute("finId", feePaymentForm.getFinancialYearId());
		 	log.debug("Leaving initFeePaymentSearch ");
		    return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to Feepayment first page after loading necessary data.
	 * @throws Exception
	 */
	
	public ActionForward initNewFeePaymentSecond(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering newFeePayment ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	feePaymentForm.clear();
		 	ActionMessages errors = new ActionErrors();
		 	ActionMessages messages = new ActionMessages();
		 	try {
		 		if(feePaymentForm.getStudentName()!= null && !feePaymentForm.getStudentName().trim().isEmpty()){
		 			feePaymentForm.setFeePayStudentList(FeePaymentHandler.getInstance().getStudentList(feePaymentForm.getStudentName(), feePaymentForm.getYear()));
		 			feePaymentForm.setStudentName(null);
		 			return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 		}
		 		if(feePaymentForm.getRegistrationNo()!= null && !feePaymentForm.getRegistrationNo().trim().isEmpty()){
		 			if(validSpecialChar(feePaymentForm.getRegistrationNo())){
				 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_REG_SPECIAL_NOT_ALLOWED));
				 		saveErrors(request, errors);
			    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 			}
		 		}
		 		else if(feePaymentForm.getRollNumber()!= null && !feePaymentForm.getRollNumber().trim().isEmpty()){
		 			if(validSpecialChar(feePaymentForm.getRollNumber())){
				 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_ROLL_SPECIAL_NOT_ALLOWED));
				 		saveErrors(request, errors);
			    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 			}
		 		}

		 		
		 		int count = 0;
		 		if(feePaymentForm.getApplicationId() != null && feePaymentForm.getApplicationId().length() !=0){
		 			count = count + 1;
		 		}
		 		if(feePaymentForm.getRegistrationNo() !=null && feePaymentForm.getRegistrationNo().length() !=0){
		 			count = count + 1;
		 		}
		 		if(feePaymentForm.getRollNumber()!= null && feePaymentForm.getRollNumber().length()!= 0){
		 			count = count + 1;
		 		}
		 		if(feePaymentForm.getYear()== null || feePaymentForm.getYear().trim().isEmpty()){
			 		errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.appliedyear.required"));
			 		saveErrors(request, errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 		}
		 		/*else if((feePaymentForm.getApplicationId().length() == 0 && feePaymentForm.getRegistrationNo().length() == 0
s			 			&& feePaymentForm.getRollNumber().length() == 0)) {
			 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_APPORREGI_REQUIRED));
			 		saveErrors(request, errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
			 		
			 	} */
		 		/*if(feePaymentForm.getClassId()==null || feePaymentForm.getClassId().toString().isEmpty()){
		 			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.classes.required"));
			 		saveErrors(request, errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 			
		 		}*/
		 		else if (feePaymentForm.getYear().length()  == 0 ) {
			 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_ACADEMICYEAR_REQUIRED));
		    		feePaymentForm.setError(CMSConstants.TRUE);
			 		saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
			 	} 
			 	else if (count > 1) {
			 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_BOTH_NOTREQUIRED));
		    		saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
			 	}
		 		List<AdmittedThroughTO> admittedList = AdmittedThroughHandler.getInstance().getAdmittedThrough();
		 		feePaymentForm.setAdmittedThroughList(admittedList);
		 		
		 		List<FeeCategoryTo> feeCategoryList = FeeCategoryHandler.getInstance().getFeeCategories();
		 		feePaymentForm.setFeeCategoryList(feeCategoryList);
		 		
		 		List<CasteTO> castelist = CasteHandler.getInstance().getCastes();
		 		feePaymentForm.setCasteList(castelist);
				ExamGenHandler genHandler = new ExamGenHandler();
				HashMap<Integer, String> secondLanguage = genHandler.getSecondLanguage();
				feePaymentForm.setSecondLanguageList(secondLanguage);
				// subject Group
			 	//FeePaymentHandler.getInstance().initSecondPageFeePayment(feePaymentForm);
			 	List<FeeStudentDetailTO> studentList = FeePaymentHandler.getInstance().getStudentListByClass(feePaymentForm);
			 	if(studentList.size()!=0)
			 		feePaymentForm.setStudentListForFee(studentList);
			 	else{
				 	setClassMapToRequest(request, feePaymentForm);
			 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
			 		saveErrors(request,errors);
		    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
			 	}
			 	HttpSession session = request.getSession();
			 	session.setAttribute("finId", feePaymentForm.getFinancialYearId());
			 	ClassSchemewise cls = FeePaymentTransactionImpl.getInstance().getClassesById(feePaymentForm);
			 	feePaymentForm.setClassName(cls.getClasses().getName());			 	
			 	
			 	String[] sem = new String[]{String.valueOf(cls.getClasses().getTermNumber())};
			 	feePaymentForm.setSelectedSems(sem);
			 	
			 	Map<Integer,String> feeOptionalGroupMap = FeePaymentHelper.getInstance().getFeeAdditionalFeeGroup();
				feePaymentForm.setFeeOptionalGroupMap(feeOptionalGroupMap);
		 	} catch(DataNotFoundException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(e.getMessage()));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	} catch(FreeShipException e) {
		 		ActionMessage message = new ActionMessage(CMSConstants.FEE_APPLICATIONNO_FREESHIP);
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	} catch(SubjectGroupNotDefinedException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_APPLICATION_SUBGROUP_NOTPRESENT));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	} catch(CurriculumSchemeNotFoundException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_APPLICATION_CURRICULUM_NOTPRESENT));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	} catch(RegNumNotExistException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_REGISTERNNO_NOTPRESENT));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	}catch(ApplicationException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PREVIOUS_AMOUNT_AVAILABLE));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
		 	}  catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	//return mapping.findForward(CMSConstants.FEE_PAYMENT_FARWARD_SECOND_SEARCH);
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_STUDENTS_SEARCH);
	}	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to feeApplicationSearch.
	 * 
	 * @throws Exception
	 */
	public ActionForward displayFeePaymentDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering addNewFeePayment "); 
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	ActionErrors errors = new ActionErrors();
		 	ActionMessages messages=new ActionMessages();
		 	errors = feePaymentForm.validate(mapping, request);
		 	feePaymentForm.setDateTime(CommonUtil.formatDate(new Date(), "dd/MM/yyyy") );
		 	if(feePaymentForm.getOptionalFeeSelectedIndex().equals("-1")) {
		 		feePaymentForm.setSelectedfeeOptionalGroup(null); 
		 	}
		 	try {
		 		if(errors.isEmpty()) { 
				 	FeePaymentHandler.getInstance().initSecondPageFeePayment(feePaymentForm);
		 			FeePaymentHandler.getInstance().getFeePaymentDetails(feePaymentForm);
		 			if(feePaymentForm.getFeeCategory()!=null)
		 			{
		 				Iterator iterator1=feePaymentForm.getFeeCategoryList().iterator();
		 				while(iterator1.hasNext())
		 				{
		 					FeeCategoryTo  at1=(FeeCategoryTo)iterator1.next();
		 					if(feePaymentForm.getFeeCategory().equalsIgnoreCase(String.valueOf(at1.getFeeCategoryId())))
		 					{
		 						feePaymentForm.setFeeCategoryName(at1.getFeeCategoryName());
		 					}
		 				}
		 			}
		 			if(feePaymentForm.getCasteId()!=null)
		 			{
		 				Iterator iterator2=feePaymentForm.getCasteList().iterator();
		 				while(iterator2.hasNext())
		 				{
		 					CasteTO  castTo=(CasteTO)iterator2.next();
		 					if(feePaymentForm.getCasteId().equalsIgnoreCase(String.valueOf(castTo.getCasteId())))
		 					{
		 						feePaymentForm.setCasteName(castTo.getCasteName());
		 					}
		 				} 
		 			}
		 			long noOfChallan=FeePaymentHandler.getInstance().getNoOfChallansForcourse(feePaymentForm);
		 			ActionMessage message = new ActionMessage("knowledgePro.fee.payment.no.of.challan.generated", noOfChallan);
		 			messages.add("messages", message);
		 			saveMessages(request, messages);
		 			feePaymentForm.setPaymentMode("1");
		 			//feePaymentForm.setAcademicYear(feePaymentForm.getAcademicYear()+"-"+(Integer.parseInt(feePaymentForm.getAcademicYear())+1));
		 		} else {
		 			saveErrors(request, errors);
		 			feePaymentForm.setError(CMSConstants.TRUE);
		 			return mapping.findForward(CMSConstants.FEE_PAYMENT_STUDENTS_SEARCH);
		 	}
		 	} catch(DataNotFoundException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_FEEDEFINITION_NOTPRESENT));
	    		saveErrors(request,errors);
	    		feePaymentForm.setError(CMSConstants.TRUE);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_STUDENTS_SEARCH);
		 	} catch(ChallanAlreadyPrintedException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_CHALLAN_PRINTED_ALREADY));
		 		saveErrors(request,errors);
		 		feePaymentForm.setError(CMSConstants.TRUE);
		 		return mapping.findForward(CMSConstants.FEE_PAYMENT_STUDENTS_SEARCH);
		 	} catch(Exception e) {
		 		log.warn("Exception while inserting");
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	
		 	return mapping.findForward("feePaymentDetails");
	}	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Redirect the control to feeApplicationSearch.
	 * @throws Exception
	 */
	public ActionForward initPrintChallen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering printChallon "); 	
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
			ActionErrors errors=new ActionErrors();
			boolean completelyPaid = false;
		 	try
		 	{
		 		errors = feeVoucherValidation(errors,feePaymentForm);
		 		if(!errors.isEmpty())
			 	{	
			 		saveErrors(request, errors);
			 		feePaymentForm.clearVoucher();
			 		return mapping.findForward("feePaymentDetails");
			 	}
		 		
		 		List<FeePaymentDisplayTO> feePaylist = feePaymentForm.getFeePaymentDisplayTOList();
		 		if(feePaylist!= null && feePaylist.size() > 0)
		 		{
		 			Iterator<FeePaymentDisplayTO> feePayItr = feePaylist.iterator();
		 			while (feePayItr.hasNext()) 
		 			{
		 				FeePaymentDisplayTO feePayTO = (FeePaymentDisplayTO) feePayItr.next();
		 				Iterator<FeeDisplayTO> itr1 = feePayTO.getFeeDispTOList().iterator();
		 				while (itr1.hasNext()) 
		 				{
							FeeDisplayTO feeDisplayTO = (FeeDisplayTO) itr1.next();
							if(feeDisplayTO.getPaidAmount()!= null && !feeDisplayTO.getPaidAmount().trim().isEmpty() && feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty())
							{
								double paidAmt = 0;
								if(feeDisplayTO.getPaidAmount()!= null && !feeDisplayTO.getPaidAmount().trim().isEmpty())
								{
									paidAmt = Double.parseDouble(feeDisplayTO.getPaidAmount());
								}
								double amt = 0;
								if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty())
								{
									amt = Double.parseDouble(feeDisplayTO.getTotalAmount());
								}
								if(paidAmt > amt )
								{
									errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_PAID_AMT_GREATER));
									break;
								}
//								if(paidAmt < amt )
//								{
//									errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_PAID_AMT_SMALLER));
//									break;
//								}
								FeePayment feePayment = FeePaymentHandler.getInstance().getFeeDetailsForApplication(feePaymentForm);
								if(feePayment != null){
									if(feePayment.getTotalFeePaid() != null && new BigDecimal(feePayment.getTotalFeePaid().toString()).doubleValue() != 0.00){
										if(feePayment.getTotalFeePaid().doubleValue() == feePayment.getTotalAmount().doubleValue()){
											completelyPaid = true;
										}else {
											completelyPaid = false;
										}
									}
								}
							}
						}
					}
		 			if (errors != null && !errors.isEmpty()) 
		 			{
			 			saveErrors(request, errors);
			 			return mapping.findForward("feePaymentDetails");
			 		}		 			
		 		}
		 		feePaylist = feePaymentForm.getFeePaymentDisplayTOList();
		 		if(feePaylist!= null && feePaylist.size() > 0)
		 		{
		 			Iterator<FeePaymentDisplayTO> feePayItr = feePaylist.iterator();
		 			while (feePayItr.hasNext()) 
		 			{
		 				FeePaymentDisplayTO feePayTO = (FeePaymentDisplayTO) feePayItr.next();
		 				Iterator<FeeDisplayTO> itr1 = feePayTO.getFeeDispTOList().iterator();
		 				while (itr1.hasNext()) 
		 				{
							FeeDisplayTO feeDisplayTO = (FeeDisplayTO) itr1.next();
							if(feeDisplayTO.getConcessionAmt()!= null && !feeDisplayTO.getConcessionAmt().trim().isEmpty() && feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty())
							{
								double concAmt = 0;
								if(feeDisplayTO.getConcessionAmt()!= null && !feeDisplayTO.getConcessionAmt().isEmpty())
								{
									concAmt = Double.parseDouble(feeDisplayTO.getConcessionAmt());
								}
								double paidAmt = 0; 
								if(feeDisplayTO.getPaidAmount()!= null && !feeDisplayTO.getPaidAmount().trim().isEmpty())
								{
									paidAmt = Double.parseDouble(feeDisplayTO.getPaidAmount());
								}
								double amt = 0;
								if(feeDisplayTO.getTotalAmount()!= null && !feeDisplayTO.getTotalAmount().trim().isEmpty())
								{
									amt =  Double.parseDouble(feeDisplayTO.getTotalAmount());
								}
								if(concAmt > (amt - paidAmt))
								{
									errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_PAID_AMT_GREATER));
									break;
								}
//								if(paidAmt < amt )
//								{
//									errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_PAID_AMT_SMALLER));
//									break;
//								}
								FeePayment feePayment = FeePaymentHandler.getInstance().getFeeDetailsForApplication(feePaymentForm);
								if(feePayment != null){
									if(feePayment.getTotalFeePaid() != null && new BigDecimal(feePayment.getTotalFeePaid().toString()).doubleValue() != 0.00){
										if(feePayment.getTotalFeePaid().doubleValue() == feePayment.getTotalAmount().doubleValue()){
											completelyPaid = true;
										}else {
											completelyPaid = false;
										}
									}
								}
							}
							
						}
					}
		 			if (errors != null && !errors.isEmpty()) {
			 			saveErrors(request, errors);
			 			return mapping.findForward("feePaymentDetails");
			 		}
		 			
		 			if(feePaymentForm.getTotalConcession() > 0 && (feePaymentForm.getConcessionReferenceNo() == null || feePaymentForm.getConcessionReferenceNo().trim().isEmpty()) )
		 			{
		 				errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.payment.conc.voucher.required"));
			 			saveErrors(request, errors);
			 			return mapping.findForward("feePaymentDetails");
		 			}
		 			
		 			if(feePaymentForm.getGrandTotal() <= 0)
		 			{
		 				feePaymentForm.setManualClassName(null);
			 			errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.challan.printed.already"));
			 			saveErrors(request, errors);
			 			return mapping.findForward("feePaymentDetails");
			 		}
		 			
		 			
		 			
		 		}
		 		
		 		
		 	}catch(Exception e){
		 		String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	
		 	boolean isAdded =false;
		 	try {
		 		if(!completelyPaid){
		 		isAdded = FeePaymentHandler.getInstance().printChallen(feePaymentForm);
		 		}
		 	} catch(BillGenerationException e) {
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PRINTCHANNEL_BILLGEN_FAILURE));
	    		saveErrors(request,errors);
		 	} catch(ChallanAlreadyPrintedException e) {
			 	errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_CHALLAN_PRINTED_ALREADY));
			 	saveErrors(request,errors);
			 	feePaymentForm.setError(CMSConstants.TRUE);
			 	return mapping.findForward(CMSConstants.FEE_PAYMENT_STUDENTS_SEARCH);
			 } catch (Exception e) {
		 		log.debug(e.getMessage());
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_CHALLAN_FAILURE));
	    		saveErrors(request,errors);
		 	}
		 	if(isAdded){
		 		
		 		if(feePaymentForm.getNetPayable() <= 0){
	 				feePaymentForm.setManualClassName(null);
	 				feePaymentForm.setStudentName(null);
	 			 	feePaymentForm.clear();
	 			 	feePaymentForm.setApplicationId(null);
	 			 	feePaymentForm.setRegistrationNo(null);
	 			 	feePaymentForm.setRollNumber(null);
	 			 	feePaymentForm.setYear(null);
	 			 	feePaymentForm.setAdmApplnId(null);
	 			 	feePaymentForm.setFeePayStudentList(null);
	 			 	feePaymentForm.setPersonalDataId(null);
	 			 	
	 			 	ActionMessages messages = new ActionMessages();
		    		ActionMessage message = new ActionMessage("knowledgepro.fee.netpayable.zero");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
	 			 	
		 			//errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.fee.netpayable.zero"));
		 			//saveErrors(request, errors);
		 			//return mapping.findForward("feePaymentDetails");	
	 			}
		 		else{
			 		feePaymentForm.setPrintChallan(CMSConstants.TRUE);
			 		ActionMessages messages = new ActionMessages();
		    		ActionMessage message = new ActionMessage(CMSConstants.FEE_CHALLAN_SUCCESS);
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
		 		}
	    	}
		 	feePaymentForm.setApplicationId(null);
		 	feePaymentForm.setRegistrationNo(null);
		 	feePaymentForm.setRollNumber(null);
		 	feePaymentForm.setAdmApplnId(null);
		 	feePaymentForm.setPersonalDataId(null);
		 	feePaymentForm.setSecondLanguage(null);
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_SEARCH);
	}	
	
	public ActionForward dispalyChallan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering dispalyChallan ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	feePaymentForm.setSecondLanguage(null);
		 	try {
		 		FeePaymentHandler.getInstance().getChallanData(feePaymentForm, request);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
	 		return mapping.findForward(CMSConstants.FEE_PAYMENT_PRINTCHALLAN);
	}		
	public ActionForward initReprintChallan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering initReprintChallan ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
		 		feePaymentForm.setIsReprintChalan(false);
		 		feePaymentForm.clear();
		 		feePaymentForm.clearMain();
		 		
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving initReprintChallan ");		 	
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
	}	
	
	public ActionForward getReprintChallanData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering reprintChallan ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	ActionMessages errors = new ActionErrors();
	 		feePaymentForm.clear();
	 		feePaymentForm.setRePrint("yes");
	 		errors = feePaymentForm.validate(mapping, request);
	 		try {
		 		if(feePaymentForm.getIsReprintChalan()){
		 			if(feePaymentForm.getFinancialYearId() == null || feePaymentForm.getFinancialYearId().trim().isEmpty()){
		 				errors.add("error", new ActionError("knowledgepro.fee.concession.slip.book.financial.year"));
		 				saveErrors(request, errors);	
		 				return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
		 			}
		 		}
		 		if(errors.isEmpty()){
		 			FeePaymentHandler.getInstance().getChallanData(feePaymentForm, request);
		 			feePaymentForm.setPrintChallan(CMSConstants.TRUE);
		 		} else {
		 			saveErrors(request, errors);
		 			return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
		 		}
		 	} catch(DataNotFoundException e) {
//		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_BILLNO_NOTPRESENT));
		 		errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.ATTENDANCE_ENTRY_NORECORD));
	    		saveErrors(request,errors);
	    		return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving reprintChallan ");
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
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
	public ActionForward dispalyChallanFromFeePayment(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering dispalyChallan ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
	 			FeePaymentHandler.getInstance().getChallanData(feePaymentForm, request);
//	 			feePaymentForm.setChallanPrintDate(CommonUtil.getStringDate(CommonUtil.ConvertStringToDate(feePaymentForm.getDateTime())));
//	 			feePaymentForm.setChallanPrintDate(CommonUtil.ConvertStringToDateFormat(feePaymentForm.getDateTime(),"yyyy-MM-dd", "dd/MM/yyyy").toString());
	 			
//		 		FeePaymentHandler.getInstance().copyPrintChallenData(feePaymentForm);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 		feePaymentForm.setManualClassName(null);
		 		return mapping.findForward(CMSConstants.FEE_PAYMENT_PRINTCHALLAN);
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
	public ActionForward initReprintChallanWithYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering initReprintChallanWithYear ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
		 		feePaymentForm.clear();
		 		feePaymentForm.clearMain();
		 		feePaymentForm.setIsReprintChalan(true);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving initReprintChallanWithYear ");		 	
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_REPRINTCHALLAN);
	}

	
	public ActionErrors feeVoucherValidation(ActionErrors errors,FeePaymentForm feePaymentForm)throws Exception
	{	
		if(feePaymentForm.getDateTime()==null || StringUtils.isEmpty(feePaymentForm.getDateTime())){
			if (errors.get(CMSConstants.DATE_REQUIRE) != null && !errors.get(CMSConstants.DATE_REQUIRE).hasNext()) {
				errors.add(CMSConstants.DATE_REQUIRE,new ActionError(CMSConstants.DATE_REQUIRE));
			}
		}
		if(feePaymentForm.getDateTime()!=null && !StringUtils.isEmpty(feePaymentForm.getDateTime())&& !CommonUtil.isValidDate(feePaymentForm.getDateTime())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		
		errors = new ActionErrors();
		return errors;	
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
	public ActionForward initFeePaymentEditSearch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering initFeePaymentEdit ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
		 		feePaymentForm.setBillNo(null);
		 		feePaymentForm.setFeePaid(false);
		 	} catch(Exception e) {
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving initFeePaymentEdit ");		 	
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);
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
	public ActionForward feePaymentEdit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering feePaymentEdit ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
		 		ActionErrors errors=new ActionErrors();
		 		errors = feePaymentForm.validate(mapping, request);
		 		if(feePaymentForm.getBillNo()!= null && !feePaymentForm.getBillNo().trim().isEmpty()){
		 			if(validSpecialChar(feePaymentForm.getBillNo())){
				 		errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admin.special"));
						saveErrors(request, errors);
				 		return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);	
		 			}
		 		}
		 					
		 		
		 		if(errors.isEmpty()){
		 			FeePaymentEditTO feePaymentEdit = FeePaymentHandler.getInstance().getFeePaymentDetailsForEdit(Integer.parseInt(feePaymentForm.getBillNo()), Integer.parseInt(feePaymentForm.getFinancialYearId()));
			 		if(feePaymentEdit !=null){
			 			feePaymentForm.setFeePaymentEditTO(feePaymentEdit);
			 			Map<Integer,String> paymentModeMap = FeePaymentHandler.getInstance().getAllFeePaymentMode();
						feePaymentForm.setPaymentModeMap(paymentModeMap);
			 		}else{
			 			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
				 		return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);
			 		}
		 		}else{
		 			saveErrors(request, errors);
			 		return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);
		 		}
		 	} catch(Exception e) {
		 		log.warn("Exception while inserting");
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving feePaymentEdit ");		 	
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT_DETAILS);
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
	public ActionForward feePaymentEditUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		 	log.debug("Entering feePaymentEdit ");
		 	FeePaymentForm feePaymentForm = (FeePaymentForm)form;
		 	try {
		 		ActionMessages messages = new ActionMessages();
		 		ActionErrors errors=new ActionErrors();
		 		errors = feePaymentForm.validate(mapping, request);
		 		if(feePaymentForm.getFeePaymentEditTO().getDateTime() == null || feePaymentForm.getFeePaymentEditTO().getDateTime().trim().isEmpty()){
		 			if (errors.get("knowledgepro.fee.payment.date.required") != null && !errors.get("knowledgepro.fee.payment.date.required").hasNext()) {
						errors.add("knowledgepro.fee.payment.date.required",new ActionError("knowledgepro.fee.payment.date.required"));
					}
		 		}
		 		errors = editFeeVoucherValidation(errors,feePaymentForm);
		 		
		 		if(errors.isEmpty()){
		 			if(FeePaymentHandler.getInstance().feePaymentDetailsUpdate(feePaymentForm)){
		 				ActionMessage message = new ActionMessage(CMSConstants.FEE_PAYMENT_EDIT_SUCCESS);
						messages.add(CMSConstants.MESSAGES, message);
						saveMessages(request, messages);
						feePaymentForm.setBillNo(null);
						return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);
		 			}
		 		}else{
		 			saveErrors(request, errors);
			 		return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT_DETAILS);
		 		}
		 	} catch(Exception e) {
		 		log.warn("Exception while inserting");
				String msg = super.handleApplicationException(e);
				feePaymentForm.setErrorMessage(msg);
				feePaymentForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		 	}
		 	log.debug("Leaving feePaymentEdit ");		 	
		 	return mapping.findForward(CMSConstants.FEE_PAYMENT_EDIT);
	}
	/**
	 * special character validation
	 * @param name
	 * @return
	 */
	private boolean validSpecialChar(String regRollNo)
	{
		boolean result=false;
		Pattern pattern = Pattern.compile("[^A-Za-z0-9  \\s \t]+");
        Matcher matcher = pattern.matcher(regRollNo);
        result = matcher.find();
        return result;

	}
	
	/**
	 * 
	 * @param errors
	 * @param feePaymentForm
	 * @return
	 * @throws Exception
	 */
	public ActionErrors editFeeVoucherValidation(ActionErrors errors,FeePaymentForm feePaymentForm)throws Exception
	{	
		List <FeeVoucher> feeVoucherList=null;
		String errortask=null;
		String validationtask=null;
		String alphanumvalidationtask=null;
		Integer finYearId=null;
		Integer concessionRefNo=null;
		Integer scholarshipRefNo=null;
		Integer installmentRefNo=null;
		Integer alphaNumConcessionRefNo=null;
		Integer alphaNumScholarshipRefNo=null;
		Integer alphaNumInstallmentRefNo=null;
		Integer feeVoucherStartNo=null;
		Integer feeVoucherEndNo=null;
		boolean found = false;
		
		if(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo()!=null && !feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo().equals(""))
		{
			if(StringUtils.isNumeric(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo()))
			{
				 concessionRefNo=Integer.valueOf(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo());
				}
			else if(StringUtils.isAlphanumeric(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo()))
			{
				if(!StringUtils.isAlpha(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo()))
				{
					alphaNumConcessionRefNo=CommonUtil.getIntegerFromAlphaNumeric(feePaymentForm.getFeePaymentEditTO().getConcessionVoucherNo());
				}
				else
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
				}
			}
			else
			{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
			}
			
		}
		if(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo()!=null && !feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo().trim().isEmpty())
		{
			if(StringUtils.isNumeric(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo()))
			{
				installmentRefNo=Integer.valueOf(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo());
				}
			else if(StringUtils.isAlphanumeric(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo()))
			{
				if(!StringUtils.isAlpha(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo()))
				{
				alphaNumInstallmentRefNo=CommonUtil.getIntegerFromAlphaNumeric(feePaymentForm.getFeePaymentEditTO().getInstallmentVoucherNo());
				}
				else
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
				}	
			}
			else
			{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
			}
			
		}
		if(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo()!=null && !feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo().trim().isEmpty())
		{
			if(StringUtils.isNumeric(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo()))
			{
				
				scholarshipRefNo=Integer.valueOf(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo());
			}
			else if(StringUtils.isAlphanumeric(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo()))
			{
				if(!StringUtils.isAlpha(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo()))
				{
				alphaNumScholarshipRefNo=CommonUtil.getIntegerFromAlphaNumeric(feePaymentForm.getFeePaymentEditTO().getScholarshipVoucherNo());
				}
				else
				{
					errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
				}
			}
			else
			{
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_VOUCHER_VALIDATION));
			}
			
		}
			if(errors.isEmpty())
			{
			finYearId=FeePaymentHandler.getInstance().getFinancialYearId();
			FeePaymentHandler feePaymentHandler=FeePaymentHandler.getInstance();
			if(finYearId!=null)
			{
				feeVoucherList=feePaymentHandler.getFeeVoucherList(finYearId);
			}
			if(feeVoucherList!=null && !feeVoucherList.isEmpty())
			{
			for(FeeVoucher feeVoucher:feeVoucherList)
			{
				errortask=null;
				validationtask=null;
				alphanumvalidationtask=null;
				feeVoucherStartNo=null;
				feeVoucherEndNo=null;
				if(StringUtils.isAlphanumeric(feeVoucher.getStartingNumber()))
				{
					feeVoucherStartNo=CommonUtil.getIntegerFromAlphaNumeric(feeVoucher.getStartingNumber());
				}
				if(StringUtils.isAlphanumeric(feeVoucher.getEndingNumber()))
				{
					feeVoucherEndNo=CommonUtil.getIntegerFromAlphaNumeric(feeVoucher.getEndingNumber());
				}
				if(feeVoucher.getType().equalsIgnoreCase("Concession"))
				{
					if(alphaNumConcessionRefNo!=null)
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						  if(!(StringUtils.isAlphanumeric(feeVoucher.getStartingNumber())&& (StringUtils.isAlphanumeric(feeVoucher.getEndingNumber()))))
						  {
							  validationtask=feeVoucher.getType();
							  
						  }
						  else
						  {
							  /*if(alphaNumConcessionRefNo<feeVoucherStartNo||alphaNumConcessionRefNo>feeVoucherEndNo)
								{
									errortask=feeVoucher.getType();
								}*/
							  
							  if(!(alphaNumConcessionRefNo>=feeVoucherStartNo && alphaNumConcessionRefNo<=feeVoucherEndNo))
								{
									errortask=feeVoucher.getType();
								}
								else{
									found = true;
								}							  
							  
						  }
						}
					}
					else if(concessionRefNo!=null)	
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						if(!(StringUtils.isNumeric(feeVoucher.getStartingNumber())&& (StringUtils.isNumeric(feeVoucher.getEndingNumber()))))
						  {
							alphanumvalidationtask=feeVoucher.getType();
						  }
						else
						if((concessionRefNo<Integer.valueOf(feeVoucher.getStartingNumber())) 
								|| (concessionRefNo>Integer.valueOf(feeVoucher.getEndingNumber())))
						{
							errortask=feeVoucher.getType();
						}
					}
					}
				}
				else if(feeVoucher.getType().equalsIgnoreCase("Installment"))
				{
					if(alphaNumInstallmentRefNo!=null)
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						  if(!(StringUtils.isAlphanumeric(feeVoucher.getStartingNumber())&& (StringUtils.isAlphanumeric(feeVoucher.getEndingNumber()))))
						  {
							  validationtask=feeVoucher.getType();
						  }
						  else
						  {
							 /* if(alphaNumInstallmentRefNo<feeVoucherStartNo||alphaNumInstallmentRefNo>feeVoucherEndNo)
								{
									errortask=feeVoucher.getType();
								}*/
								if(!(alphaNumInstallmentRefNo>=feeVoucherStartNo && alphaNumInstallmentRefNo<=feeVoucherEndNo))
								{
									errortask=feeVoucher.getType();
								}
								else{
									found = true;
								}								
						  }
						}
					}
					else if(installmentRefNo!=null)	
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						if(!(StringUtils.isNumeric(feeVoucher.getStartingNumber())&& (StringUtils.isNumeric(feeVoucher.getEndingNumber()))))
						  {
							alphanumvalidationtask=feeVoucher.getType();
							 
						  }
						else
						if((installmentRefNo<Integer.valueOf(feeVoucher.getStartingNumber()) )
								|| (installmentRefNo>Integer.valueOf(feeVoucher.getEndingNumber())))
						{
							errortask=feeVoucher.getType();
						}
						}
					}
	
				}
				else
				{
					if(alphaNumScholarshipRefNo!=null)
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						  if(!(StringUtils.isAlphanumeric(feeVoucher.getStartingNumber())&& (StringUtils.isAlphanumeric(feeVoucher.getEndingNumber()))))
						  {
							  validationtask=feeVoucher.getType();
						  }
						  else
						  {	/*
							  if(alphaNumScholarshipRefNo<feeVoucherStartNo||alphaNumScholarshipRefNo>feeVoucherEndNo)
								{
									errortask=feeVoucher.getType();
								}*/
							  if(!(alphaNumScholarshipRefNo>=feeVoucherStartNo && alphaNumScholarshipRefNo<=feeVoucherEndNo))
								{
									errortask=feeVoucher.getType();
								}
								else{
									found = true;
								}							  
						  }
						}
					}
					else if(scholarshipRefNo!=null)	
					{
						if(feeVoucher.getStartingNumber()!=null && feeVoucher.getEndingNumber()!=null)
						{
						if(!(StringUtils.isNumeric(feeVoucher.getStartingNumber())&& (StringUtils.isNumeric(feeVoucher.getEndingNumber()))))
						  {
							alphanumvalidationtask=feeVoucher.getType();
							  
						  }
						else
						if((scholarshipRefNo<Integer.valueOf(feeVoucher.getStartingNumber()) )
								|| (scholarshipRefNo>Integer.valueOf(feeVoucher.getEndingNumber())))
						{
							errortask=feeVoucher.getType();
						}
						}
					}

				}
				if(errortask!=null && !errortask.equals(""))
				{
					if(errortask.equalsIgnoreCase("Concession") ||errortask.equalsIgnoreCase("Installment")||errortask.equalsIgnoreCase("Scholarship"))
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_CONCESSION, feeVoucher.getType(),feeVoucher.getStartingNumber(),feeVoucher.getEndingNumber()));
					}
					
				}
				if(alphanumvalidationtask!=null && !alphanumvalidationtask.equals(""))
				{
					if(alphanumvalidationtask.equalsIgnoreCase("Concession") || alphanumvalidationtask.equalsIgnoreCase("Installment") || alphanumvalidationtask.equalsIgnoreCase("Scholarship"))
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_INSTALLMENT, feeVoucher.getType()));
					}
				}
				
				if(validationtask!=null && !validationtask.equals(""))
				{
					if(validationtask.equalsIgnoreCase("Concession") || validationtask.equalsIgnoreCase("Installment") || validationtask.equalsIgnoreCase("Scholarship"))
					{
						errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.FEE_PAYMENT_SCHOLARSHIP, feeVoucher.getType()));
						
					}
				}
		}
			}
			}
			if(found){
				errors = new ActionErrors();
			}
		return errors;	
	}
	
	// vinodha
	public void setClassMapToRequest(HttpServletRequest request,FeePaymentForm feePaymentForm) throws Exception {
		log.info("Entering into setClassMapToRequest of TCDetailsAction");
		try {
			if (feePaymentForm.getAcademicYear() == null
					|| feePaymentForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}
				
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClasesByAcademicYear(currentYear);
				request.setAttribute("classMap", classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(feePaymentForm.getAcademicYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
						.getClassesByYearNew(year);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  FeePaymentAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of FeePaymentAction");
	}
}