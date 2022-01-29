package com.kp.cms.actions.admin;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.NewStudentCertificateCourseAction;
import com.kp.cms.bo.admin.ConvocationRegistration;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.OnlineExamSuppApplicationForm;
import com.kp.cms.handlers.admin.OnlineExamSuppApplicationHandler;
import com.kp.cms.handlers.exam.NewSupplementaryImpApplicationHandler;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admission.OnlinePaymentRecieptsTo;
import com.kp.cms.transactions.admin.IOnlineExamSuppApplicationTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.admin.OnlineExamSuppApplicationImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class OnlineExamSuppApplicationAction extends BaseDispatchAction{
	
	private static final String MESSAGE_KEY = "messages";
	
	private static final Logger log = Logger.getLogger(OnlineExamSuppApplicationAction.class);
	
	OnlineExamSuppApplicationHandler handler=OnlineExamSuppApplicationHandler.getInstance();
	IOnlineExamSuppApplicationTransaction txn=new OnlineExamSuppApplicationImpl();
	 
     public ActionForward initOnlineSuppExam(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        log.info("Start of initAcademicYearYearEntry in AcademicYearAction class");
        OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
        try
        {   
        	String Venue="";  
        	String DateOfExam="";
        	String DateOfExamMBA="";
        	String DateOfExamENG="";
        	String DateOfExamPrev15="";
        	String DateOfExamPrev16="";
        	String DateOfExamPrev18="";
        	setUserId(request,crForm);
            setDataToForm(crForm,request);
            crForm.setPrintReceipt(false);
            crForm.setAlreadyApplied(false);
            Properties prop = new Properties();
	        InputStream stream = OnlineExamSuppApplicationAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        prop.load(stream);
            String fee = prop.getProperty(CMSConstants.LINK_FOR_FEE);
            if(crForm.getProgramId().equalsIgnoreCase("4") || crForm.getProgramId().equalsIgnoreCase("94") 
            || crForm.getProgramId().equalsIgnoreCase("81") || crForm.getProgramId().equalsIgnoreCase("82") 
           || crForm.getProgramId().equalsIgnoreCase("74")){
            	Venue= prop.getProperty(CMSConstants.LINK_FOR_VENUE1);
            	DateOfExamENG= prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAM1);
            	crForm.setVenueEngg(Venue);
            }
            else if(crForm.getProgramId().equalsIgnoreCase("3")){
            	if(crForm.getCourseId().equalsIgnoreCase("1")){
            		Venue= prop.getProperty(CMSConstants.LINK_FOR_VENUE1);
            		DateOfExamMBA= prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAM3);
            		crForm.setVenueMBA(Venue);
            	}
            	else
            	{
            		Venue= prop.getProperty(CMSConstants.LINK_FOR_VENUE);
            		DateOfExamMBA= prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAM3);
            		crForm.setVenueMBA(Venue);
            	}
            }
            else
            {
            	Venue= prop.getProperty(CMSConstants.LINK_FOR_VENUE);
            	DateOfExam= prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAM);
            	//DateOfExamPrev15=prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAMPREV15);
            	DateOfExamPrev16=prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAMPREV16);
            	DateOfExamPrev18=prop.getProperty(CMSConstants.LINK_FOR_DATE_OF_EXAMPREV18);
            	crForm.setVenueOther(Venue);
            
            }
           // crForm.setDateOfExamPrev15(DateOfExamPrev15);
            crForm.setDateOfExamPrev16(DateOfExamPrev16);
            crForm.setDateOfExamPrev18(DateOfExamPrev18);
            crForm.setDateOfExamPrev19(DateOfExam);
            crForm.setDateOfExamEngg(DateOfExamENG);
            crForm.setDateOfExamMBA(DateOfExamMBA);
            crForm.setTotalFees(Double.parseDouble(fee));
           
            boolean alreadyApplied=txn.CheckStudentAlreadyApplied(crForm);
            if(alreadyApplied){
            	crForm.setAlreadyApplied(true);
            	crForm.setPrintReceipt(true);
            	return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE);
            }
            
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            crForm.setErrorMessage(msg);
            crForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of initAcademicYearYearEntry in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE);
    }
     
     public  void setDataToForm(OnlineExamSuppApplicationForm objform, HttpServletRequest request)throws Exception {
    	 
    	 if(objform.getUserId()!=null && !objform.getUserId().isEmpty()){
    		 txn.getStudentId(Integer.parseInt(objform.getUserId()),objform);
			}  	    	
    	
    	// list=handler.getCertificateDetails();
    	
 		ICashCollectionTransaction cashCollectionTransaction = CashCollectionTransactionImpl.getInstance();
		int finId = cashCollectionTransaction.getCurrentFinancialYear();
		if(finId<=0){
			objform.setFeesNotConfigured(true);
		}else{
			String query="from OnlineBillNumber o where o.pcFinancialYear.id = "+finId+" and o.isActive = 1";
			INewExamMarksEntryTransaction txn=NewExamMarksEntryTransactionImpl.getInstance();
			List list1=txn.getDataForQuery(query);
			if(list1==null || list1.isEmpty())
			objform.setFeesNotConfigured(true);
			objform.setFinId(finId);
		}
		ISingleFieldMasterTransaction txn=SingleFieldMasterTransactionImpl.getInstance();
		Student student=(Student) txn.getMasterEntryDataById(Student.class,objform.getStudentId());
		if(student!=null){
			//objform.setStudentId(student.getId());
			objform.setNameOfStudent(student.getAdmAppln().getPersonalData().getFirstName()+(student.getAdmAppln().getPersonalData().getMiddleName()!=null?student.getAdmAppln().getPersonalData().getMiddleName():"")+(student.getAdmAppln().getPersonalData().getLastName()!=null?student.getAdmAppln().getPersonalData().getLastName():""));
			objform.setClassName(student.getClassSchemewise()!=null?student.getClassSchemewise().getClasses().getName():"");
			objform.setAcademicYear(String.valueOf(student.getClassSchemewise().getCurriculumSchemeDuration().getAcademicYear()));
			objform.setRegNo(student.getRegisterNo());
			objform.setStdClassId(student.getClassSchemewise().getClasses().getId());
			objform.setDob(null);
			objform.setProgramId(String.valueOf(student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getId()));
			objform.setCourseId(String.valueOf(student.getAdmAppln().getCourseBySelectedCourseId().getId()));
			objform.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
		}
		if(objform.getPasses() != 0){
			objform.setTotalFees(objform.getGuestAmount()*objform.getPasses());
		}
     }
     
     public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
  			ActionForm form, HttpServletRequest request,
  			HttpServletResponse response) throws Exception {
    	 OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;// Type casting the Action form to Required Form
  		ActionErrors errors = new ActionErrors();
  		setUserId(request,crForm);//setting the userId to Form
  		try {
  		//	crForm.setPrintPage(null);
  			boolean isValidSmartCard=handler.verifySmartCard(crForm.getSmartCardNo(),crForm.getStudentId());
  			if(!isValidSmartCard){
  				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
  			}
  			if(crForm.getDob()!=null){
  				if(!crForm.getDob().equalsIgnoreCase(crForm.getOriginalDob()))
  					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
  			}
  			
  			if(!errors.isEmpty()){
  				saveErrors(request, errors);
  				return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART);
  			}
  			
  		}catch (ReActivateException e) {
  			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
  			saveErrors(request, errors);
  			return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART);
  		} catch (Exception e) {
  			log.error("Error in getCertificateCourses"+e.getMessage());
  			String msg = super.handleApplicationException(e);
  			crForm.setErrorMessage(msg);
  			crForm.setErrorStack(e.getMessage());
  			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
  		}
  		
  		return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART_1);
  	}

  	public ActionForward addCertificateApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
 		
 		log.info("Entered NewExamMarksEntryAction - saveMarks");
 		OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
 		ActionMessages messages=new ActionMessages();
 		ActionErrors errors = crForm.validate(mapping, request);
 		setUserId(request,crForm);
 		if (errors.isEmpty()) {
 			try {
 				crForm.setPrintCertificateReq(false);
 				//crForm.setPrintPage(null);
 				String msg="";
 				boolean isSaved=handler.saveSupplementaryApplication(crForm);
 				msg=crForm.getMsg();
 				if(isSaved){
 					crForm.setAlreadyApplied(true);
 					//handler.sendSMSToStudent(crForm.getStudentId(),CMSConstants.CERTIFICATE_APPLICATION_SMS_TEMPLATE);
 					//handler.sendMailToStudent(crForm,CMSConstants.CERTIFICATE_APPLICATION_SUBMITTED_SUCESS_TEMPLATE);
 					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.Online.SuppExam.success.online"));
 					saveMessages(request, messages);
 					String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(crForm.getTempOnlinePayId(),request);
 					// resetting the fields
 					crForm.resetFields();
 					//setting data for print
 					crForm.setPrintData(printData);
 					crForm.setPrintCertificateReq(true);
 					setDataToForm(crForm,request);
 				}else{
 					if(msg==null || msg.isEmpty()){
 						msg="Payment Failed";
 						}
 					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Online Application was not successfull, Reason:"+msg));
 					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
 					saveErrors(request, errors);
 				}
 			}  catch (Exception exception) {
 				String msg = super.handleApplicationException(exception);
 				crForm.setErrorMessage(msg);
 				crForm.setErrorStack(exception.getMessage());
 				return mapping.findForward(CMSConstants.ERROR_PAGE);
 			}
 		} else {
 			addErrors(request, errors);
 			log.info("Exit NewExamMarksEntryAction - saveMarks errors not empty ");
 			return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE);
 		}
 		log.info("Entered NewExamMarksEntryAction - saveMarks");
 		return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE);
 	}
  	
  	public ActionForward initOnlineReciepts(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
  		log.debug("Entering the initOnlineRecieptsForStudentLogin");
		OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
		List<OnlinePaymentRecieptsTo> paymentList=handler.getOnlinePaymentReciepts(crForm.getTempOnlinePayId(),crForm.getStudentId(),request);
		Collections.sort(paymentList);
		crForm.setPaymentList(paymentList);
		List<OnlinePaymentRecieptsTo> paymentListPrint=crForm.getPaymentList();
		for (OnlinePaymentRecieptsTo to : paymentListPrint) {
				request.setAttribute("MSG",to.getMsg());
		}
		log.debug("Exit  from initOnlineRecieptsForStudentLogin");
		return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE_PRINT_DATA);
	}
  	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initConvocationRegistration(ActionMapping mapping,ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
		HttpSession session=request.getSession();
		clearFormData(crForm);
		try{
			handler.setDataToForm(crForm,session.getAttribute("stuCourseId").toString());
			ConvocationRegistration convocationRegistration = handler.loadConvocationRegistration(session);
			crForm.setStudentName(session.getAttribute("STUNAME").toString());
			if(convocationRegistration != null){
				crForm.setRecordExist(true);
				return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
  			crForm.setErrorMessage(msg);
  			crForm.setErrorStack(e.getMessage());
  			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
	}

	/**
	 * @param crForm
	 */
	private void clearFormData(OnlineExamSuppApplicationForm crForm) {
		crForm.setRecordExist(false);
		crForm.setParticipation(null);
		crForm.setGuestAmount(0);
		crForm.setPasses(0);
		crForm.setGuestPassRequired(null);
		crForm.setStudentName(null);
		crForm.setConvocationSessionId(0);
		crForm.setValidThruYear(null);
		crForm.setValidThruMonth(null);
		crForm.setSmartCardNo(null);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOnlinePaymentPage(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		HttpSession session=request.getSession();
		try{
			if(crForm.getParticipation() == null || crForm.getParticipation().isEmpty()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.select.participation.message"));
				saveErrors(request, errors);
			}
			if(crForm.getGuestPassRequired() == null ){
				//crForm.setGuestPassRequired(false);
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.select.guest.message"));
				saveErrors(request, errors);
			}else if(crForm.getGuestPassRequired() && crForm.getPasses() ==0 && crForm.getPassAvailable()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.select.passes.required"));
				saveErrors(request, errors);
			}
			if(crForm.getGuestPassRequired() != null && crForm.getGuestPassRequired() && crForm.getPassAvailable()){
				handler.checkForAvailablePasses(crForm,session.getAttribute("stuCourseId").toString());
				if(crForm.getOnepassAvailbale()){
					if(crForm.getPasses() != 1){
						errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.convocation.onepass"));
						saveErrors(request, errors);
					}
				}
				if(!crForm.getPassAvailable()){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.convocation.nopass"));
					saveErrors(request, errors);
				}
			}
			if(!errors.isEmpty()){
				return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
			}
			ConvocationRegistration convocationRegistration = handler.loadConvocationRegistration(session);
			if(convocationRegistration != null){
				crForm.setRecordExist(true);
				return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
			}
			if(crForm.getGuestPassRequired() != null && crForm.getGuestPassRequired() && crForm.getPassAvailable()){
				setUserId(request,crForm);
				setDataToForm(crForm,request);
				boolean isValidSmartCard=handler.verifySmartCard(crForm.getSmartCardNo(),crForm.getStudentId());
				if(!isValidSmartCard){
	  				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
	  				saveErrors(request, errors);
	  			}
			}else{
				setUserId(request,crForm);
				boolean isAdded=handler.saveConvocationRegistration(crForm,session);
				if(isAdded)
				{
					ActionMessage message = new ActionMessage("knowledgepro.convocation.registration.success.message");// Adding success message.
					messages.add("messages", message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.SUCCESS_CONVOCATION_REGISTRATION);
				}else{
					ActionMessage message = new ActionMessage("knowledgepro.convocation.registration.failure.message");// Adding failure message.
					messages.add("messages", message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
  			crForm.setErrorMessage(msg);
  			crForm.setErrorStack(e.getMessage());
  			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_CONVOCATION_ONLINE_PAYMENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward verifyStudentSmartCardForConvocation(ActionMapping mapping,
  			ActionForm form, HttpServletRequest request,
  			HttpServletResponse response) throws Exception {
    	 OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;// Type casting the Action form to Required Form
  		ActionErrors errors = new ActionErrors();
  		setUserId(request,crForm);//setting the userId to Form
  		try {
  		//	crForm.setPrintPage(null);
  			boolean isValidSmartCard=handler.verifySmartCard(crForm.getSmartCardNo(),crForm.getStudentId());
  			if(!isValidSmartCard){
  				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter the valid last 5 digits of your smart card number"));
  			}
  			if(crForm.getDob()!=null){
  				if(!crForm.getDob().equalsIgnoreCase(crForm.getOriginalDob()))
  					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Please Enter Valid Date Of Birth"));
  			}
  			
  			if(!errors.isEmpty()){
  				saveErrors(request, errors);
  				return mapping.findForward(CMSConstants.INIT_CONVOCATION_ONLINE_PAYMENT);
  			}
  			
  		}catch (ReActivateException e) {
  			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
  			saveErrors(request, errors);
  			return mapping.findForward(CMSConstants.INIT_SUPP_EXAM_ONLINE_VEIRFY_SMART);
  		} catch (Exception e) {
  			log.error("Error in getCertificateCourses"+e.getMessage());
  			String msg = super.handleApplicationException(e);
  			crForm.setErrorMessage(msg);
  			crForm.setErrorStack(e.getMessage());
  			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
  		}
  		
  		return mapping.findForward(CMSConstants.CONVOCATION_ONLINE_PAYMENT_CONFIRM);
  	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveConvocationDetails(ActionMapping mapping,
  			ActionForm form, HttpServletRequest request,
  			HttpServletResponse response) throws Exception {
		OnlineExamSuppApplicationForm crForm = (OnlineExamSuppApplicationForm)form;
 		ActionErrors errors = new ActionErrors();
 		ActionMessages messages=new ActionMessages();
 		HttpSession session = request.getSession();
 		setUserId(request,crForm);
		try {
			handler.checkForAvailablePasses(crForm,session.getAttribute("stuCourseId").toString());
			if(crForm.getOnepassAvailbale()){
				if(crForm.getPasses() != 1){
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.convocation.onepass"));
					saveErrors(request, errors);
				}
			}
			if(!crForm.getPassAvailable()){
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.convocation.nopass"));
				saveErrors(request, errors);
			}
			if(!errors.isEmpty()){
				return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
			}
			ConvocationRegistration convocationRegistration = handler.loadConvocationRegistration(session);
			if(convocationRegistration != null){
				crForm.setRecordExist(true);
				return mapping.findForward(CMSConstants.INIT_CONVOCATION_REGISTRATION);
			}
			String msg="";
			boolean isSaved=handler.saveConvocationDetails(crForm,session);
			msg=crForm.getMsg();
			if(isSaved){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.convocation.registration.success.message"));
				saveMessages(request, messages);
			}else{
				if(msg==null || msg.isEmpty()){
					msg="Payment Failed";
					}
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Online Application was not successfull, Reason:"+msg));
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Kindly rectify the errors mentioned and re-submit the application" ));
				saveErrors(request, errors);
			}
		}  catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			crForm.setErrorMessage(msg);
			crForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SUCCESS_CONVOCATION_REGISTRATION);
	 		
 	}

  	
}
