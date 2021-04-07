 package com.kp.cms.actions.applicationform;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;
import org.zefer.html.doc.p;

import com.ibm.icu.math.BigDecimal;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.actions.admission.OnlineApplicationAction;
import com.kp.cms.bo.admin.FeeFinancialYear;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.OnlineApplicationForm;
import com.kp.cms.forms.applicationform.ApplicationRegistrationForm;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.applicationform.ApplicationRegistrationHandler;
import com.kp.cms.handlers.fee.FeePaymentHandler;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.to.admin.ApplnDocTO;
import com.kp.cms.to.admin.ReligionTO;
import com.kp.cms.to.admission.PersonalDataTO;
import com.kp.cms.to.applicationform.ProfessorBooksandArticlesTO;
import com.kp.cms.to.applicationform.ProfessorEducationDetailsTO;
import com.kp.cms.to.applicationform.ProfessorPersonalDataTO;
import com.kp.cms.to.applicationform.ProfessorPostDoctoralExperTO;
import com.kp.cms.to.applicationform.ProfessorPostDoctoralExperTO;
import com.kp.cms.to.applicationform.ProfessorQualificationTO;
import com.kp.cms.to.applicationform.ProfessorTeachingExperTO;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.transactionsimpl.applicationform.ApplicationRegistrationHandlerTxnImp;
import com.kp.cms.transactions.applicationform.IApplicationRegistrationTransaction;

public class ApplicationRegistrationAction  extends BaseDispatchAction{
	

	public ActionForward initApplicationRegistration(ActionMapping mapping, ActionForm form,
				   HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		
		try{
			HttpSession session = request.getSession(true);
			session.setAttribute("isApplicationServlet", true);
			registrationForm.setIsNetQualification(false);
			registrationForm.setIsJrfQualification(false);
			setRequiredDataToForm(request,registrationForm);
			
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			e.printStackTrace();
		}
		
		
		return mapping.findForward("initApplicationRegistration");
	}

	public void setRequiredDataToForm(HttpServletRequest request, ApplicationRegistrationForm registrationForm) throws Exception{
		Map<Integer, String> deptMap = CommonAjaxHandler.getInstance().getDepartments();
		registrationForm.setDepartmentMap(deptMap);
		
		List<ReligionTO> religionList = ReligionHandler.getInstance().getReligion();
		
		registrationForm.setReligionList(religionList);
		
		List<ProfessorEducationDetailsTO> qualificationDetails=ApplicationRegistrationHandler.getInstance().getQualificationDetails(registrationForm.getEducationalDetails());
		registrationForm.setEducationalDetails(qualificationDetails);
		
		List<ProfessorBooksandArticlesTO> netAndJrfList=new ArrayList<ProfessorBooksandArticlesTO>();
		ProfessorBooksandArticlesTO list=new ProfessorBooksandArticlesTO();
		list.setBooks("");
		list.setArticles("");
		netAndJrfList.add(list);
		
		registrationForm.setBooksAndArticles(netAndJrfList);
		registrationForm.setBookSize(String.valueOf(netAndJrfList.size()));
		
		
		List<ProfessorPostDoctoralExperTO> docExpList=new ArrayList<ProfessorPostDoctoralExperTO>();
		ProfessorPostDoctoralExperTO postDoc=new ProfessorPostDoctoralExperTO();
		postDoc.setCollageName("");
		postDoc.setFromDate("");
		postDoc.setToDate("");
		docExpList.add(postDoc);
		registrationForm.setPostDocExp(docExpList);
		registrationForm.setPostDocSize(String.valueOf(docExpList.size()));
		
		
		List<ProfessorTeachingExperTO> teachExpList=new ArrayList<ProfessorTeachingExperTO>();
		ProfessorTeachingExperTO teachDoc=new ProfessorTeachingExperTO();
		teachDoc.setCollageName("");
		teachDoc.setFromDate("");
		teachDoc.setToDate("");
		teachExpList.add(teachDoc);
		registrationForm.setTeachingExp(teachExpList);
		registrationForm.setTeachingExpSize(String.valueOf(teachExpList.size()));
		
		
	}
	
	public ActionForward resetBooksInfo(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request,HttpServletResponse response) throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		
		if(registrationForm.getBooksAndArticles()!=null)
			if(registrationForm.getMode()!=null){
				if (registrationForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					List<ProfessorBooksandArticlesTO> list=registrationForm.getBooksAndArticles();
					ProfessorBooksandArticlesTO proBookDetails =new ProfessorBooksandArticlesTO();
					proBookDetails.setBooks("");
					proBookDetails.setArticles("");
					list.add(proBookDetails);
					registrationForm.setBookSize(String.valueOf(list.size()));
				}
		
		
	}
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward removeBooksInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		List<ProfessorBooksandArticlesTO> list=null;
		if(registrationForm.getBooksAndArticles()!=null)
		if(registrationForm.getBooksAndArticles().size()>0){
				list=registrationForm.getBooksAndArticles();
				list.remove(list.size()-1);
				registrationForm.setBookSize(String.valueOf(list.size()));
		}
		registrationForm.setBookSize(String.valueOf(list.size()-1));
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward resetPostDocInfo(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request,HttpServletResponse response) throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		
		if(registrationForm.getPostDocExp()!=null)
			if(registrationForm.getMode()!=null){
				if (registrationForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					List<ProfessorPostDoctoralExperTO> list=registrationForm.getPostDocExp();
					ProfessorPostDoctoralExperTO postDoc =new ProfessorPostDoctoralExperTO();
					postDoc.setFromDate("");
					postDoc.setToDate("");
					postDoc.setCollageName("");
					list.add(postDoc);
					registrationForm.setPostDocSize(String.valueOf(list.size()));
				}
		
		
	}
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	private void setRequiredDataToForm1(ApplicationRegistrationForm registrationForm) throws Exception{
		Map<Integer, String> subCasteMap = new HashMap<Integer, String>();
		if(registrationForm.getReligionId()!=null && !registrationForm.getReligionId().isEmpty()){
			subCasteMap = CommonAjaxHandler.getInstance().getSubCasteByReligion(Integer.parseInt(registrationForm.getReligionId()));
			registrationForm.setCasteMap(subCasteMap);
			
		}
		
	}

	public ActionForward removePostDocInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		List<ProfessorPostDoctoralExperTO> list=null;
		if(registrationForm.getPostDocExp()!=null)
		if(registrationForm.getPostDocExp().size()>0){
				list=registrationForm.getPostDocExp();
				list.remove(list.size()-1);
				registrationForm.setPostDocSize(String.valueOf(list.size()));
		}
		registrationForm.setPostDocSize(String.valueOf(list.size()-1));
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward resetTeachInfo(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request,HttpServletResponse response) throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		
		if(registrationForm.getTeachingExp()!=null)
			if(registrationForm.getMode()!=null){
				if (registrationForm.getMode().equalsIgnoreCase("ExpAddMore")) {
					List<ProfessorTeachingExperTO> list=registrationForm.getTeachingExp();
					ProfessorTeachingExperTO teachDoc =new ProfessorTeachingExperTO();
					teachDoc.setFromDate("");
					teachDoc.setToDate("");
					teachDoc.setCollageName("");
					list.add(teachDoc);
					registrationForm.setTeachingExpSize(String.valueOf(list.size()));
				}
		
		
	}
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward removeTeachInfo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		List<ProfessorTeachingExperTO> list=null;
		if(registrationForm.getTeachingExp()!=null)
		if(registrationForm.getTeachingExp().size()>0){
				list=registrationForm.getTeachingExp();
				list.remove(list.size()-1);
				registrationForm.setTeachingExpSize(String.valueOf(list.size()));
		}
		registrationForm.setTeachingExpSize(String.valueOf(list.size()-1));
		setRequiredDataToForm1(registrationForm);
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward registerProfeesor(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = registrationForm.validate(mapping, request);
		try{
		if(errors.isEmpty()){
			
			
		boolean isSucess=ApplicationRegistrationHandler.getInstance().storeProfessorDetails(registrationForm);
		
		if(isSucess){
			ProfessorPersonalData profData=  ApplicationRegistrationHandler.getInstance().getApplicantObject(registrationForm);
			if(profData!=null){
				HttpSession session=request.getSession(false);
				session.setAttribute("profeesorObject", profData);
				return mapping.findForward(CMSConstants.REDIRECT_TO_PGI_PAGE_FOR_PROFESSOR);
			}else{
				errors.add("error", new ActionError("knowledgepro.professore.failed.toStore"));
				saveErrors(request, errors);
				registrationForm.reset(mapping, request);
				setRequiredDataToForm(request,registrationForm);
				return mapping.findForward("initApplicationRegistration");
			}
			
		}
		}else{
			saveErrors(request, errors);
			registrationForm.reset(mapping, request);
			setRequiredDataToForm(request,registrationForm);
			return mapping.findForward("initApplicationRegistration");
		
		}
		
		}catch (Exception e) {
			errors.add("error", new ActionError("knowledgepro.professore.failed"));
			saveErrors(request, errors);
			registrationForm.reset(mapping, request);
			setRequiredDataToForm(request,registrationForm);
			return mapping.findForward("initApplicationRegistration");
		}
		
		return mapping.findForward("initApplicationRegistration");
	}
	
	public ActionForward redirectingToPGIForProfessor(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = registrationForm.validate(mapping, request);
		HttpSession session=request.getSession();
		ProfessorPersonalData personalData=(ProfessorPersonalData) session.getAttribute("profeesorObject");
		if(personalData!=null){
			try {
				String hash=ApplicationRegistrationHandler.getInstance().getParameterForPGIApplication(registrationForm,personalData);
				request.setAttribute("user_code", CMSConstants.PGI_MERCHANT_ID_FEDERAL);
				request.setAttribute("pass_code", CMSConstants.PGI_SECURITY_ID_FEDERAL);
				request.setAttribute("tran_id", CMSConstants.PGI_MERCHANT_ID_FEDERAL+registrationForm.getRefNo());
				request.setAttribute("amount", Double.parseDouble(registrationForm.getAmount())*100);
				request.setAttribute("charge_code", 'A');
				request.setAttribute("hash_value", hash);
				request.setAttribute("response_url", CMSConstants.PROFESSOR_APPLICATION_SERVLET);

			} catch (Exception e) {
				throw e;
			}
		
		}
		return mapping.findForward(CMSConstants.REDIRECT_TO_PGI_PAGE_PROFESSOER_APPLICATION);
	}
	
	public ActionForward updatePGIResponseProfessor(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
        ActionErrors errors=new ActionErrors();
		
		ActionMessages messages=new ActionMessages(); 
		try{
			HttpSession session = request.getSession();
			ProfessorPersonalData personalData=(ProfessorPersonalData) session.getAttribute("profeesorObject");
			boolean isUpdated= ApplicationRegistrationHandler.getInstance().updateResponse(registrationForm,personalData);
			if(isUpdated){
				System.out.println("*************************IsTnxStatusSuccess============="+registrationForm.getIsTnxStatusSuccess()+"********************");
			if(registrationForm.getIsTnxStatusSuccess())
			{
					registrationForm.setTxnAmt(String.valueOf(Integer.parseInt(registrationForm.getAmount())/100));
					registrationForm.setDisplayPage("paymentsuccess");
					messages.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",registrationForm.getPgiStatus()));
					saveMessages(request, messages);
			}else
			{
				registrationForm.setPgiStatus("Payment Failure");
				errors.add(CMSConstants.MESSAGES,new ActionMessage("knowledgepro.admission.empty.err.message",registrationForm.getPgiStatus()));
				saveErrors(request, errors);
				registrationForm.setDisplayPage("payment");
			}
			
			
		}
		else{
			errors.add("error", new ActionError("knowledgepro.admission.pgi.update.failure"));
			saveErrors(request, errors);
		}
			
		}catch (BusinessException e) {
			errors.add("error", new ActionError("knowledgepro.admission.pgi.rejected"));
			saveErrors(request, errors);
		}
		
		catch (Exception e) {
			//throw e;
			System.out.println("************************ error details in online admission in updatePGIResponse*************************"+e);
			
		     errors.add("knowledgepro.admission.boardDetails.duplicateEntry", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry","Error was occured, please login and enter details again"));
		     saveErrors(request, errors);
		}
		
	/*	registrationForm.setPaymentSuccess(true);
		registrationForm.setTxnAmt("1");
		registrationForm.setTxnRefNo("12");
		registrationForm.setTxnDate("QDE"); for testing*/
		
		
		
		return mapping.findForward("printApllicationAndShowTransactionDetailsForProfessor");
	}
	public ActionForward printApplicationForm(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = registrationForm.validate(mapping, request);
		HttpSession session = request.getSession();
		ProfessorPersonalData personalData=(ProfessorPersonalData) session.getAttribute("profeesorObject");
		
		Boolean isPrint=(Boolean) session.getAttribute("isApplicationPrint");
		if(personalData==null && isPrint!=null && isPrint ){
		
			if(registrationForm.getDateOfBirth()==null || registrationForm.getDateOfBirth().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.applicationform.professore.dateofbirth"));
				saveErrors(request, errors);
				registrationForm.reset(mapping, request);
				return mapping.findForward("initApplicationPrint");
			}
			if(registrationForm.getMobileNo()==null || registrationForm.getMobileNo().isEmpty()){
				errors.add("error", new ActionError("knowledgepro.applicationform.professore.mobileno"));
				saveErrors(request, errors);
				registrationForm.reset(mapping, request);
				return mapping.findForward("initApplicationPrint");
			}
			
			
		 IApplicationRegistrationTransaction tx=ApplicationRegistrationHandlerTxnImp.getInstance();
		   personalData=tx.getDetailsForApplicationPrint(registrationForm);
		   
		   if(personalData==null){
				errors.add("error", new ActionError("knowledgepro.applicationform.professore.data"));
				saveErrors(request, errors);
				registrationForm.reset(mapping, request);
				return mapping.findForward("initApplicationPrint");
			}
		
		}
		
		if(personalData!=null){
			
			try{
			 ApplicationRegistrationHandler.getInstance().printData(registrationForm,personalData);
			 
			}catch (Exception e) {
				String msg = super.handleApplicationException(e);
				e.printStackTrace();
			}
		} 
		
		return mapping.findForward("printApllicationForProfessor");
	}
	
	
	public ActionForward initApplicationPrint(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request,HttpServletResponse response)throws Exception{
	ApplicationRegistrationForm registrationForm=(ApplicationRegistrationForm)form;
	
	try{
		HttpSession session = request.getSession(true);
		session.setAttribute("isApplicationPrint", true);
		
	}catch (Exception e) {
		String msg = super.handleApplicationException(e);
		e.printStackTrace();
	}
	
	
	return mapping.findForward("initApplicationPrint");
}
	
	
	
}
