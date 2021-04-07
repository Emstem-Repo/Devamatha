package com.kp.cms.actions.admin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.kp.cms.actions.admission.StudentEditAction;
import com.kp.cms.actions.employee.EmployeeInfoEditAction;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.forms.admission.StudentEditForm;
import com.kp.cms.forms.employee.EmpResPubDetailsForm;
import com.kp.cms.handlers.admin.CertificateRequestOnlineHandler;
import com.kp.cms.handlers.admin.CommonTemplateHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.admission.StudentEditHandler;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.exam.NewSupplementaryImpApplicationHandler;
import com.kp.cms.to.admin.AssignCertificateRequestPurposeTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificateRequestMarksCardTO;
import com.kp.cms.to.admin.CertificateRequestOnlineTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;
import com.kp.cms.transactions.admin.ISingleFieldMasterTransaction;
import com.kp.cms.transactions.exam.INewExamMarksEntryTransaction;
import com.kp.cms.transactions.pettycash.ICashCollectionTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateRequestOnlineImpl;
import com.kp.cms.transactionsimpl.admin.SingleFieldMasterTransactionImpl;
import com.kp.cms.transactionsimpl.exam.NewExamMarksEntryTransactionImpl;
import com.kp.cms.transactionsimpl.pettycash.CashCollectionTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;
import com.kp.cms.utilities.print.HtmlPrinter;

public class CertificateRequestOnlineAction extends BaseDispatchAction{
	
	private static final String MESSAGE_KEY = "messages";
	
	private static final Logger log = Logger.getLogger(CertificateRequestOnlineAction.class);
	CertificateRequestOnlineHandler handler=CertificateRequestOnlineHandler.getInstance();
	 ICertificateRequestOnlineTransaction txn=new CertificateRequestOnlineImpl();
	 
     public ActionForward initCertificateRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
        try
        {
        	setUserId(request,crForm);
        	crForm.resetFields();
        	crForm.setIsReject("false");
            setDataToForm(crForm,request);
        }
        catch(Exception e)
        {
            String msg = super.handleApplicationException(e);
            crForm.setErrorMessage(msg);
            crForm.setErrorStack(e.getMessage());
            return mapping.findForward(CMSConstants.ERROR_PAGE);
        }
        log.info("End of initAcademicYearYearEntry in AcademicYearAction class");
        return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
    }
     
     public  void setDataToForm(CertificateRequestOnlineForm objform, HttpServletRequest request)throws Exception {
    	 if(objform.getUserId()!=null && !objform.getUserId().isEmpty()){
    		 txn.getStudentId(Integer.parseInt(objform.getUserId()),objform);
			}  	    	
    	 List<CertificateDetailsTo> list=handler.getCertificateDetails();
    	 objform.setCertificateDetails(list);
    	 objform.getCertificateRequestOnlineTO().setCertificateDetailsTo(list);
    	 initializeMarksCard(objform);
    	
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
			objform.setRegNo(student.getRegisterNo());
			objform.setDob(null);
			objform.setOriginalDob(CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(student.getAdmAppln().getPersonalData().getDateOfBirth()), NewStudentCertificateCourseAction.SQL_DATEFORMAT,NewStudentCertificateCourseAction.FROM_DATEFORMAT));
		}
     }
     /**
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward initStudentEdit(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
    	CertificateRequestOnlineForm stForm = (CertificateRequestOnlineForm) form;
 		try {
 			setUserId(request, stForm);
 			cleanupTempValuesSession(stForm);
 			setDataToForm(stForm,request);
 			stForm.setPrintPage(null);
 		} catch (ApplicationException e) {
 			log.error("error in initStudentEdit...", e);
 			String msg = super.handleApplicationException(e);
 			stForm.setErrorMessage(msg);
 			stForm.setErrorStack(e.getMessage());
 			return mapping.findForward(CMSConstants.ERROR_PAGE);
 		} catch (Exception e) {
 			log.error("error in initStudentEdit...", e);
 			throw e;
 		}
 		return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_SEARCH);
 	}
    private void cleanupTempValuesSession(CertificateRequestOnlineForm stForm) {
		stForm.setTempRegisterNo(null);
		stForm.setTempFirstName(null);
		stForm.setStartDate(null);
		stForm.setEndDate(null);
		stForm.setCertificateId(null);
	}
    
    
    public ActionForward CertificateHelpMenu(ActionMapping mapping,
			   ActionForm form, HttpServletRequest request,
			   HttpServletResponse response) throws Exception {

return mapping.findForward(CMSConstants.CERTIFICATE_TEMPLATE_HELPTEMPLATE);
}	
    
   /* public  void loadDataToForm(CertificateRequestOnlineForm stForm)throws Exception {
    	 Map<String,String> certificateMap=new HashMap<String, String>();
    	 certificateMap=empTransaction.getDesignationMap();
		 if(certificateMap!=null){
			stForm.setcer.setDesignationMap(designationMap);
			
		 }
    }*/
    
    
 	/**
 	 * gets list of students as per search criteria
 	 * 
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	public ActionForward getSearchedStudents(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
 		CertificateRequestOnlineForm stForm = (CertificateRequestOnlineForm) form;
 		ActionMessages errors = stForm.validate(mapping, request);
 		try {
 			if (errors != null && !errors.isEmpty()) {
 				saveErrors(request, errors);
 				return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_SEARCH);
 			}
 			
 			List<CertificateRequestOnlineTO> studentToList = handler.getSearchedStudents(stForm);
 			if (studentToList.isEmpty()) {

 				ActionMessages messages = new ActionMessages();
 				ActionMessage message = null;
 				message = new ActionMessage(
 						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
 				messages.add(CertificateRequestOnlineAction.MESSAGE_KEY, message);
 				saveMessages(request, messages);

 				return mapping
 						.findForward(CMSConstants.STUDENT_PENDING_LIST_SEARCH);

 			}
 			stForm.setStudentToList(studentToList);
 		} catch (ApplicationException e) {
 			log.error("error in getSearchedStudents...", e);
 			String msg = super.handleApplicationException(e);
 			stForm.setErrorMessage(msg);
 			stForm.setErrorStack(e.getMessage());
 			return mapping.findForward(CMSConstants.ERROR_PAGE);
 		} catch (Exception e) {
 			log.error("error in getSearchedStudents...", e);
 			throw e;

 		}
 		return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
 	}
     /**
 	 * @param mapping
 	 * @param form
 	 * @param request
 	 * @param response
 	 * @return
 	 * @throws Exception
 	 */
 	public ActionForward verifyStudentSmartCardForStudentLogin(ActionMapping mapping,
 			ActionForm form, HttpServletRequest request,
 			HttpServletResponse response) throws Exception {
 		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;// Type casting the Action form to Required Form
 		ActionErrors errors = new ActionErrors();
 		setUserId(request,crForm);//setting the userId to Form
 		try {
 			crForm.setPrintPage(null);
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
 				return mapping.findForward(CMSConstants.INIT_CERT_REQ_STUDENT_VEIRFY_SMART);
 			}
 			
 		}catch (ReActivateException e) {
 			errors.add("error", new ActionError("knowledgepro.admission.certificate.course.available"));
 			saveErrors(request, errors);
 			return mapping.findForward(CMSConstants.INIT_CERT_REQ_STUDENT_VEIRFY_SMART);
 		} catch (Exception e) {
 			log.error("Error in getCertificateCourses"+e.getMessage());
 			String msg = super.handleApplicationException(e);
 			crForm.setErrorMessage(msg);
 			crForm.setErrorStack(e.getMessage());
 			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
 		}
 		
 		return mapping.findForward(CMSConstants.INIT_CERT_REQ_STUDENT_VEIRFY_SMART_1);
 	}

 	public ActionForward addCertificateApplicationForStudentLogin(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = crForm.validate(mapping, request);
		validateData(crForm,errors);
		resetCheckBox(crForm);
		setUserId(request,crForm);
		if (errors.isEmpty()) {
			try {
				crForm.setPrintCertificateReq(false);
				crForm.setPrintPage(null);
				String msg="";
				boolean isSaved=handler.saveSupplementaryApplicationForStudentLogin(crForm);
				msg=crForm.getMsg();
				if(isSaved){
					//handler.sendSMSToStudent(crForm.getStudentId(),CMSConstants.CERTIFICATE_APPLICATION_SMS_TEMPLATE);
					handler.sendMailToStudent(crForm,CMSConstants.CERTIFICATE_APPLICATION_SUBMITTED_SUCESS_TEMPLATE);
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.request.success.online"));
					saveMessages(request, messages);
					String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(crForm.getOnlinePaymentId(),request);
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
					errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Certificate Request was not successfull, Reason:"+msg));
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
			log.info("Exit CertificateRequestOnlineAction - addCertificateApplicationForStudentLogin errors not empty ");
			return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
		}
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
	}
     
 	
 	@SuppressWarnings("deprecation")
	public ActionForward calculateAmount(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = crForm.validate(mapping, request);
		validateData(crForm,errors);
		boolean flagIsIdCard=false;
		//resetCheckBox(crForm);
		setUserId(request,crForm);
		if (errors.isEmpty()) {
			double amount=0;
			try {			
					List<CertificateDetailsTo> list = crForm.getCertificateRequestOnlineTO().getCertificateDetailsTo();
						
						for (CertificateDetailsTo to : list) {
					
						if(to.isChecked())
								{
									
								if(to.getMarksCard().equalsIgnoreCase("true"))
								{
										if(crForm.getCertificateRequestOnlineTO().getCertificateDetailsTo()!=null){
											List<CertificateRequestMarksCardTO> list1 = to.getMarksCardTo();
											double fee = 0;
											for (int i = 0; i < list1.size(); i++) {
												CertificateRequestMarksCardTO certificateTO = list1.get(i);
												if(certificateTO.getCertDetailsId()==to.getId())
												{
													if(certificateTO.getSemester()!=null && !certificateTO.getSemester().isEmpty() 
															|| certificateTO.getYear()!=null && !certificateTO.getYear().isEmpty() 
															|| certificateTO.getMonth()!=null && !certificateTO.getMonth().isEmpty()
															|| certificateTO.getType()!=null && !certificateTO.getType().isEmpty()){
															fee = fee + Double.parseDouble(to.getOriginalFees());
															to.setFees(String.valueOf(fee));	
													}
												}
											
										}
									}
							}
								if(to.getIsIdCard().equalsIgnoreCase("false")){
									amount=amount+(Double.parseDouble(to.getFees()));	}
								else{
									amount=amount+0;
									flagIsIdCard=true;
								}
								// we are adding only '0'Rs in case where student is applying for duplicate Id Card.They have to pay amount at the counter when they receive the ID card....		
							}	}		
						crForm.setTotalFees(amount);
				if(amount==0){
					if(!flagIsIdCard){
					
					if (errors.get(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED) != null
							&& !errors.get(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED)
									.hasNext()) {
							ActionMessage error = new ActionMessage(
									CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED);
							errors.add(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED, error);
					}
					
					
					
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
					}
					else
					{
						String msg="";
						boolean isSaved=handler.saveIdCardApplications(crForm);
						msg=crForm.getMsg();
						if(isSaved){
							messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.request.success.online"));
							messages.add(CMSConstants.MESSAGES1,new ActionMessage("knowledgepro.certificate.request.id.Card.Information"));
							saveMessages(request, messages);
							//String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(crForm.getOnlinePaymentId(),request);
							crForm.resetFields();
							crForm.setPrintCertificateReq(true);
							setDataToForm(crForm,request);
							return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
					}
				}
				}
			}  catch (RuntimeException exception) {
				throw exception;
			}catch(Exception ex){
				String msg = super.handleApplicationException(ex);
				crForm.setErrorMessage(msg);
				crForm.setErrorStack(ex.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			resetCheckBox(crForm);
			addErrors(request, errors);
			log.info("Exit calculateAmount--------------------------------------- ");
			return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
		}
		log.info("Entered calculateAmount---------------------------------------");
		return mapping.findForward(CMSConstants.INIT_CERT_REQ_STUDENT_VEIRFY_SMART);
	}
 	
 	
private void initializeMarksCard(CertificateRequestOnlineForm crForm) {
	
		CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
		if(to != null){
			
			List<CertificateDetailsTo> newList = new ArrayList<CertificateDetailsTo>();
			List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
			if(certificateDetailsTos != null){
				Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
				while (iterator.hasNext()) {
					CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
					List<CertificateRequestMarksCardTO> list=new ArrayList<CertificateRequestMarksCardTO>();
						CertificateRequestMarksCardTO marksCardTO = new CertificateRequestMarksCardTO();	
						if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
						{
						
						marksCardTO.setCertDetailsId(certificateDetailsTo.getId());
						marksCardTO.setMonth("");
						marksCardTO.setYear("");
						marksCardTO.setSemester("");
						marksCardTO.setType("");
						list.add(marksCardTO);
						
						}
					certificateDetailsTo.setMarksCardTo(list);
					newList.add(certificateDetailsTo);
				}
			}
			to.setCertificateDetailsTo(newList);
			crForm.setCertificateRequestOnlineTO(to);
		}
	}
public ActionForward removeExtraMarksCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{ 	
	CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
	CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
	if(to != null){
		int certId=Integer.parseInt(crForm.getAddMoreCertId());
		List<CertificateDetailsTo> newList = new ArrayList<CertificateDetailsTo>();
		List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
		if(certificateDetailsTos != null){
			Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
			while (iterator.hasNext()) {
				CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
				List<CertificateRequestMarksCardTO> list=certificateDetailsTo.getMarksCardTo();
				if(crForm.getMode()!=null){
					if (crForm.getMode().equalsIgnoreCase("ExpAddMore")) {
						
					CertificateRequestMarksCardTO marksCardTO = new CertificateRequestMarksCardTO();	
					if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
					{
						if(certId == certificateDetailsTo.getId())
						{
						certificateDetailsTo.setChecked(false);
						list=certificateDetailsTo.getMarksCardTo();
						for(int i=1; i!=list.size();){
						marksCardTO.setMonth("");
						marksCardTO.setYear("");
						marksCardTO.setSemester("");
						marksCardTO.setType("");
						list.remove(list.size()-1);
						String size=String.valueOf(list.size()-1);
						crForm.setFocusValue("marksCardHide_"+size);
						}
						}
						marksCardTO.setMarksCardLength(list.size());
						crForm.setMarksCardLength(String.valueOf(marksCardTO.getMarksCardLength()));
				}
				crForm.setMarksCardLength(String.valueOf(list.size()-1));
				}
				}
				if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
				{
					if(certificateDetailsTo.isChecked()){
						if(certId == certificateDetailsTo.getId())
						{
						double fee = Double.parseDouble(certificateDetailsTo.getOriginalFees());
						fee = fee * (Integer.parseInt(crForm.getMarksCardLength())+1);
						certificateDetailsTo.setFees(String.valueOf(fee));
						
						}
					}
					}
					certificateDetailsTo.setMarksCardTo(list);
					newList.add(certificateDetailsTo);
					}
				}
				to.setCertificateDetailsTo(newList);
				crForm.setCertificateRequestOnlineTO(to);
				}
	resetCheckBox(crForm);
	return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
}

 	public ActionForward resetMarksCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		
		CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
		if(to != null){
			int certId=Integer.parseInt(crForm.getAddMoreCertId());
			List<CertificateDetailsTo> newList = new ArrayList<CertificateDetailsTo>();
			List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
			if(certificateDetailsTos != null){
				Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
				while (iterator.hasNext()) {
					CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
					List<CertificateRequestMarksCardTO> list=certificateDetailsTo.getMarksCardTo();
					if(crForm.getMode()!=null){
						if (crForm.getMode().equalsIgnoreCase("ExpAddMore")) {
							
						CertificateRequestMarksCardTO marksCardTO = new CertificateRequestMarksCardTO();	
						if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
						{
							if(certId == certificateDetailsTo.getId())
							{
							marksCardTO.setCertDetailsId(certificateDetailsTo.getId());
							marksCardTO.setMonth("");
							marksCardTO.setYear("");
							marksCardTO.setSemester("");
							marksCardTO.setType("");
							marksCardTO.setMarksCardLength(list.size());
							list.add(marksCardTO);
							crForm.setMode(null);
							crForm.setMarksCardLength(String.valueOf(marksCardTO.getMarksCardLength()));
							String size=String.valueOf(list.size()-1);
							crForm.setFocusValue("marksCardHide_"+size);
							}
						}
						}
					}
					if(certificateDetailsTo.isChecked()){
						if(certId == certificateDetailsTo.getId())
						{
						double fee = Double.parseDouble(certificateDetailsTo.getOriginalFees());
						fee = fee * (Integer.parseInt(crForm.getMarksCardLength())+1);
						certificateDetailsTo.setFees(String.valueOf(fee));
						//certificateDetailsTo.setChecked(true);
						//certificateDetailsTo.setTempChecked(true);
						}
					}
					certificateDetailsTo.setMarksCardTo(list);
					newList.add(certificateDetailsTo);
					}
				}
				to.setCertificateDetailsTo(newList);
				crForm.setCertificateRequestOnlineTO(to);
			}
		crForm.setFocusValue("marksCardHide");
		resetCheckBox(crForm);
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
}
 	public ActionForward removeMarksCard(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		
		CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
		if(to != null){
			int certId=Integer.parseInt(crForm.getAddMoreCertId());
			List<CertificateDetailsTo> newList = new ArrayList<CertificateDetailsTo>();
			List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
			if(certificateDetailsTos != null){
				Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
				while (iterator.hasNext()) {
					CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
					List<CertificateRequestMarksCardTO> list=certificateDetailsTo.getMarksCardTo();
					if(crForm.getMode()!=null){
						if (crForm.getMode().equalsIgnoreCase("ExpAddMore")) {
							
						CertificateRequestMarksCardTO marksCardTO = new CertificateRequestMarksCardTO();	
						if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
						{
							if(certId == certificateDetailsTo.getId())
							{
							list=certificateDetailsTo.getMarksCardTo();
							if(list.size()>1){
							list.remove(list.size()-1);
							String size=String.valueOf(list.size()-1);
							crForm.setFocusValue("marksCardHide_"+size);
							}
							}
							marksCardTO.setMarksCardLength(list.size());
							crForm.setMarksCardLength(String.valueOf(marksCardTO.getMarksCardLength()));
					}
					crForm.setMarksCardLength(String.valueOf(list.size()-1));
					}
					}
						if(certificateDetailsTo.isChecked()){
							if(certId == certificateDetailsTo.getId())
							{
							double fee = Double.parseDouble(certificateDetailsTo.getOriginalFees());
							fee = fee * (Integer.parseInt(crForm.getMarksCardLength())+1);
							certificateDetailsTo.setFees(String.valueOf(fee));
							//certificateDetailsTo.setChecked(true);
							//certificateDetailsTo.setTempChecked(true);
							}
							
						}
						certificateDetailsTo.setMarksCardTo(list);
						newList.add(certificateDetailsTo);
						}
					}
					to.setCertificateDetailsTo(newList);
					crForm.setCertificateRequestOnlineTO(to);
					}
		resetCheckBox(crForm);
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
	}
	
	public ActionForward SubmitCompletedList(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = crForm.validate(mapping, request);
		setUserId(request,crForm);
		if (errors.isEmpty()) {
		try {
		int count=0;
		if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){	
			Iterator<CertificateRequestOnlineTO> itr=crForm.getStudentToList().iterator();
			while (itr.hasNext()) {
				CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
				if(to.getIsCompleted()!=null && to.getIsCompleted())
				{
					count=count+1;
				}
			}
		}
		if(count==0)
		{
			if (errors.get(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED) != null
					&& !errors.get(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED)
							.hasNext()) {
					ActionMessage error = new ActionMessage(
							CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED);
					errors.add(CMSConstants.KNOWLEDGEPRO_CERTIFICATE_NORECORD_SELECTED, error);
			}
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
		}
		else
		{
    		boolean isSaved=handler.saveCompletedCertificate(crForm);
				
				if(isSaved){
					
							handler.sendSMSToStudent(crForm,CMSConstants.COMPLETED_APPLICATION_SMS_TEMPLATE);
							handler.sendMailToStudent(crForm,CMSConstants.CERTIFICATE_APPLICATION_MAIL_TEMPLATE);
					
							ActionMessage message=new ActionMessage(CMSConstants.CERT_SUBMIT_CONFIRM);
							messages.add(CMSConstants.MESSAGES,message);
							saveMessages(request, messages);
							crForm.resetFields();
				            setDataToForm(crForm,request);
				            crForm.setIsReject("false");
							return mapping.findForward(CMSConstants.CERT_SUBMIT_CONFIRM);
						}else{
							messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.EMP_INFO_ERRORSUBMIT_CONFIRM));
							saveMessages(request, messages);
							return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
						}
					}
					}
					 catch (Exception exception) {
						if (exception instanceof ApplicationException) {
							String msg = super.handleApplicationException(exception);
							crForm.setErrorMessage(msg);
							crForm.setErrorStack(exception.getMessage());
							return mapping.findForward(CMSConstants.ERROR_PAGE);
					}else
						throw exception;
					}
				
				}else{
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
				}
		
		
		}
	
	public void resetCheckBox(CertificateRequestOnlineForm crForm){	
	CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
	if(to != null){
		
		List<CertificateDetailsTo> newList = new ArrayList<CertificateDetailsTo>();
		List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
		if(certificateDetailsTos != null){
			Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
			while (iterator.hasNext()) {
				CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
				certificateDetailsTo.setTempChecked(certificateDetailsTo.isChecked());
				
				
				/*(if(certificateDetailsTo.getAssignPurposeTo()!= null){
					
					List<AssignCertificateRequestPurposeTO> purposeList = new ArrayList<AssignCertificateRequestPurposeTO>();
					List<AssignCertificateRequestPurposeTO> purposeTos = certificateDetailsTo.getAssignPurposeTo();
					if(purposeTos != null){
						Iterator<AssignCertificateRequestPurposeTO> iterator1 = purposeTos.iterator();
						while (iterator1.hasNext()) {
							AssignCertificateRequestPurposeTO purposeTo = (AssignCertificateRequestPurposeTO) iterator1.next();
							purposeTo.setTempPurposeChecked(purposeTo.getAssignChecked());
							purposeList.add(purposeTo);
						}
					}
				certificateDetailsTo.setAssignPurposeTo(purposeList);
				}*/
				newList.add(certificateDetailsTo);
		}
	}
	to.setCertificateDetailsTo(newList);
	crForm.setCertificateRequestOnlineTO(to);
}
}
	
	private void validateData(CertificateRequestOnlineForm crForm,ActionErrors errors) {
		try{
			CertificateRequestOnlineTO to=crForm.getCertificateRequestOnlineTO();
			if(to != null){
				
				List<CertificateDetailsTo> certificateDetailsTos = to.getCertificateDetailsTo();
				if(certificateDetailsTos != null){
					Iterator<CertificateDetailsTo> iterator = certificateDetailsTos.iterator();
					while (iterator.hasNext()) {
						CertificateDetailsTo certificateDetailsTo = (CertificateDetailsTo) iterator.next();
						if(certificateDetailsTo.getIsReasonRequired().equalsIgnoreCase("true"))
						{
						
							if(certificateDetailsTo.isChecked()){
								if (certificateDetailsTo.getStudentRemarks() == null || certificateDetailsTo.getStudentRemarks().isEmpty())  {
									if (errors.get(CMSConstants.STUDENT_REMARKS_REQUIRED) != null
											&& !errors.get(CMSConstants.STUDENT_REMARKS_REQUIRED)
													.hasNext()) {
										ActionMessage error = new ActionMessage(CMSConstants.STUDENT_REMARKS_REQUIRED);
										errors.add(CMSConstants.STUDENT_REMARKS_REQUIRED, error);
									}
								}
						
						}
						}
						
						List<CertificateRequestMarksCardTO> list1 = certificateDetailsTo.getMarksCardTo();
						double fee = 0;
						for (int i = 0; i < list1.size(); i++) {
							CertificateRequestMarksCardTO marksCardTO = list1.get(i);
							if(certificateDetailsTo.getMarksCard().equalsIgnoreCase("true"))
							{
							  if(certificateDetailsTo.isChecked()){
								if (marksCardTO.getMonth() == null || marksCardTO.getMonth().isEmpty())  {
									if (errors.get(CMSConstants.MARKSCARD_MONTH_REQUIRED) != null
											&& !errors.get(CMSConstants.MARKSCARD_MONTH_REQUIRED)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.MARKSCARD_MONTH_REQUIRED);
										errors.add(CMSConstants.MARKSCARD_MONTH_REQUIRED, error);
									}
								}
								if (marksCardTO.getYear() == null || marksCardTO.getYear().isEmpty())  {
									if (errors.get(CMSConstants.MARKSCARD_YEAR_REQUIRED) != null
											&& !errors.get(CMSConstants.MARKSCARD_YEAR_REQUIRED)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.MARKSCARD_YEAR_REQUIRED);
										errors.add(CMSConstants.MARKSCARD_YEAR_REQUIRED, error);
									}
								}
								if (marksCardTO.getType() == null || marksCardTO.getType().isEmpty())  {
									if (errors.get(CMSConstants.MARKSCARD_EXAM_TYPE_REQUIRED) != null
											&& !errors.get(CMSConstants.MARKSCARD_EXAM_TYPE_REQUIRED)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.MARKSCARD_EXAM_TYPE_REQUIRED);
										errors.add(CMSConstants.MARKSCARD_EXAM_TYPE_REQUIRED, error);
									}
								}
								if (marksCardTO.getSemester() == null || marksCardTO.getSemester().isEmpty())  {
									if (errors.get(CMSConstants.MARKSCARD_SEMESTER_REQUIRED) != null
											&& !errors.get(CMSConstants.MARKSCARD_SEMESTER_REQUIRED)
													.hasNext()) {
										ActionMessage error = new ActionMessage(
												CMSConstants.MARKSCARD_SEMESTER_REQUIRED);
										errors.add(CMSConstants.MARKSCARD_SEMESTER_REQUIRED, error);
									}}
								}}
							 }
						}
					}
			    }
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean sendMailToStudent(EmpResPubDetailsForm admForm) throws Exception {
		boolean sent=false;
		Properties prop = new Properties();
		try {
			InputStream inStr = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inStr);
		} catch (FileNotFoundException e) {
		log.error("Unable to read properties file...", e);
			return false;
		} catch (IOException e) {
			log.error("Unable to read properties file...", e);
			return false;
		}
			List<GroupTemplate> list=null;
			//get template and replace dynamic data
			TemplateHandler temphandle=TemplateHandler.getInstance();
			 list= temphandle.getDuplicateCheckList(CMSConstants.EMPLOYEE_RESEARCH_APPROVER_MAIL_TEMPLATE);
			
			if(list != null && !list.isEmpty()) {

				String desc = list.get(0).getTemplateDescription();
				//send mail to applicant
				//String adminmail=prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_TOID);
				String approverAddress=admForm.getApproverEmailId();
				String fromName = prop.getProperty(CMSConstants.EMPLOYEE_RESEARCH_PUBLICATION_FROMNAME);
				String fromAddress = CMSConstants.MAIL_USERID;
				//String[] mailIds = adminmail.split(",");
				//for (int i = 0; i < mailIds.length; i++) {
					//String toAddress=mailIds[i];	
				String toAddress=approverAddress;
				String name=admForm.getEmployeeName();
				name=name+ "(" + admForm.getFingerPrintId() +")";
				String subject= "Research and Publication details submitted by "+name;
				String message =desc;
							//message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_MESSAGE,name);
				message = message.replace(CMSConstants.TEMPLATE_EMPLOYEE_NAME,name);
				sent=sendMail(toAddress, subject, message, fromName, fromAddress);
				HtmlPrinter.printHtml(message);

					//	}
						
				}
						 
			return sent;
	}

	/**
	 * Common Send mail
	 * @param admForm
	 * @return
	 */
	public boolean sendMail(String mailID, String sub,String message, String fromName, String fromAddress) {
			boolean sent=false;
				String toAddress=mailID;
				// MAIL TO CONSTRUCTION
				String subject=sub;
				String msg=message;
			
				MailTO mailto=new MailTO();
				mailto.setFromAddress(fromAddress);
				mailto.setToAddress(toAddress);
				mailto.setSubject(subject);
				mailto.setMessage(msg);
				mailto.setFromName(fromName);
				
				sent=CommonUtil.sendMail(mailto);
			return sent;
	}
	
	public ActionForward RejectCertificateRequest(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	        CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors=crForm.validate(mapping, request);
			boolean flag=false;
			if(errors.isEmpty()){
				try {
					flag=handler.saveRejected(crForm);
					if(flag){
						
						handler.sendSMSToStudent(crForm,CMSConstants.CERTIFICATE_APPLICATION_REJECTED_SMS_TEMPLATE);
						handler.sendMailToStudent(crForm,CMSConstants.CERTIFICATE_APPLICATION_REJECTED_MAIL_TEMPLATE);
						
						ActionMessage message=new ActionMessage("knowledgepro.certifacatedetails.Rejected");
						messages.add(CMSConstants.MESSAGES,message);
						saveMessages(request, messages);
						crForm.resetFields();
			            setDataToForm(crForm,request);
			            crForm.setIsReject("true");
						return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
					}else{
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STUDENT_PENDING_LIST_RESULT));
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
					}
				} catch (Exception exception) {
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
			}
		}
	
	
	public ActionForward getDescription(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	        CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
			String desc=handler.getdesc(Integer.parseInt(crForm.getCertDescId()));
			crForm.setDescription(desc);
			return mapping.findForward(CMSConstants.STUDENT_CERTIFICATE_DESCRIPTION);
	}
	
	public ActionForward printTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 CertificateRequestOnlineForm commonTemplateForm = (CertificateRequestOnlineForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession();
		try {
			handler.getTemplatePrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.setPrintPage(null);
				return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
			}else{
				session.setAttribute("MessageList", commonTemplateForm.getMessageList());
			}
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				commonTemplateForm.setErrorMessage(msg);
				commonTemplateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		commonTemplateForm.setPrintPage("true");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
	}
	
   public ActionForward printCertificateTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
	CertificateRequestOnlineForm commonTemplateForm = (CertificateRequestOnlineForm)form;
	commonTemplateForm.setDate(CommonUtil.getTodayDate());
	HttpSession session = request.getSession();
	commonTemplateForm.setMessageList((List)session.getAttribute("MessageList"));
	commonTemplateForm.setPrintPage(null);
	commonTemplateForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.CERTIFICATE_TEMPLATE_PRINT);
	}
   
   
   public ActionForward getCertificateStatus(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		CertificateRequestOnlineForm stForm = (CertificateRequestOnlineForm) form;
		ActionMessages errors = stForm.validate(mapping, request);
		try {
			setUserId(request,stForm);
			if(stForm.getUserId()!=null && !stForm.getUserId().isEmpty()){
	    		 txn.getStudentId(Integer.parseInt(stForm.getUserId()),stForm);
				} 
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CERTIFICATE_STATUS);
			}
			
			List<CertificateRequestOnlineTO> studentToList = handler.getCertificateStatus(stForm);
			if (studentToList.isEmpty()) {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(CertificateRequestOnlineAction.MESSAGE_KEY, message);
				saveMessages(request, messages);

				return mapping.findForward(CMSConstants.CERTIFICATE_STATUS);

			}
			stForm.setStudentToList(studentToList);
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			String msg = super.handleApplicationException(e);
			stForm.setErrorMessage(msg);
			stForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			throw e;

		}
		return mapping.findForward(CMSConstants.CERTIFICATE_STATUS);
	}
   
   
   public ActionForward  RemarksAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	        CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
			ActionMessages messages=new ActionMessages();
			ActionErrors errors=crForm.validate(mapping, request);
			boolean flag=false;
			if(errors.isEmpty()){
				try {
					flag=handler.saveRemarks(crForm);
					
					if(flag){
						List<CertificateRequestOnlineTO> studentToList = handler.getSearchedStudents(crForm);
			 			if (studentToList.isEmpty()) {

			 				ActionMessage message = null;
			 				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			 				messages.add(CertificateRequestOnlineAction.MESSAGE_KEY, message);
			 				saveMessages(request, messages);

			 				return mapping
			 						.findForward(CMSConstants.STUDENT_PENDING_LIST_SEARCH);

			 			}
			 			crForm.setStudentToList(studentToList);
						
						
						ActionMessage message=new ActionMessage("knowledgepro.certifacatedetails.Remarks.added");
						messages.add(CMSConstants.MESSAGES,message);
						saveMessages(request, messages);
						crForm.resetFields();
			            setDataToForm(crForm,request);
						return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
					}else{
						messages.add(CMSConstants.MESSAGES,new ActionMessage(CMSConstants.STUDENT_PENDING_LIST_RESULT));
						saveMessages(request, messages);
						return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
					}
				} catch (Exception exception) {
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
			}else{
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.STUDENT_PENDING_LIST_RESULT);
			}
		}
  
	
   
   public ActionForward SaveIDCardAction(ActionMapping mapping, ActionForm form, HttpServletRequest request,
		   HttpServletResponse response) throws Exception {
	CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
	ActionMessages messages=new ActionMessages();
	ActionErrors errors = crForm.validate(mapping, request);
	validateData(crForm,errors);
	resetCheckBox(crForm);
	setUserId(request,crForm);
	if (errors.isEmpty()) {
		try {
			crForm.setPrintCertificateReq(false);
			crForm.setPrintPage(null);
			String msg="";
			boolean isSaved=handler.saveIdCardApplications(crForm);
			msg=crForm.getMsg();
			if(isSaved){
				messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.certificate.request.success.online"));
				saveMessages(request, messages);
				String printData=NewSupplementaryImpApplicationHandler.getInstance().getPrintData(crForm.getOnlinePaymentId(),request);
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
				errors.add(CMSConstants.ERROR,new ActionError("knowledgepro.admission.empty.err.message","Certificate Request was not successfull, Reason:"+msg));
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
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
	}
	return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
}
   
   public ActionForward ShowDescription(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CertificateRequestOnlineForm crForm = (CertificateRequestOnlineForm)form;
		setUserId(request,crForm);
		resetCheckBox(crForm);
		try {			
			List<CertificateDetailsTo> list = crForm.getCertificateRequestOnlineTO().getCertificateDetailsTo();
			String desc=null;	
			String desc1=null;
					for (CertificateDetailsTo to : list) {
					
						if(to.isChecked())
						{
							desc=handler.getdesc(to.getId());
							if(desc1!=null){
								desc1=desc1+desc;
							}else{
								desc1=desc;
							}
								
						}		
			}
				crForm.setDescription(desc1);
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				crForm.setErrorMessage(msg);
				crForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			crForm.setIsDescriptionDisplayed("true");
		return mapping.findForward(CMSConstants.INIT_CERTIFICATE_REQUEST_ONLINE);
	}
 	  
   
}
	
	
