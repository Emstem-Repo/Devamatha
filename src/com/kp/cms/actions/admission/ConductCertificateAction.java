package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.ConductCertificateForm;
import com.kp.cms.handlers.admission.ConductCertificateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.PrintTcDetailsTo;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ConductCertificateAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ConductCertificateAction.class);
	public ActionForward initConductCertificate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{	
		ConductCertificateForm certificateForm = (ConductCertificateForm)form;
		try
		{
			certificateForm.resetFields();
			initsetDataToForm(certificateForm,request);
		}
		catch (Exception e) {
			String msg = super.handleApplicationException(e);
			certificateForm.setErrorMessage(msg);
			certificateForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CONDUCT_CERTIFICATE);
	}
	private void initsetDataToForm(ConductCertificateForm certificateForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> classMap = setupClassMapToRequest(certificateForm,request);
		certificateForm.setClassMap(classMap);
	}
	
	public Map<Integer,String> setupClassMapToRequest(ConductCertificateForm certificateForm,HttpServletRequest request) throws Exception{
		log.info("Entering into setpClassMapToRequest of TransferCertificateAction");
		Map<Integer,String> classMap = new HashMap<Integer, String>();
		try {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			int year=CurrentAcademicYear.getInstance().getAcademicyear();
			if(year!=0){
				currentYear=year;
			}
			if(certificateForm.getYear()!=null && !certificateForm.getYear().isEmpty()){
				currentYear=Integer.parseInt(certificateForm.getYear());
			}
			classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
			request.setAttribute("classMap", classMap);
			return classMap;
		} catch (Exception e) {
			log.debug(e.getMessage());
			log.error("Error occured in setpClassMapToRequest of ConductCertificateAction");
		}
		log.info("Leaving into setpClassMapToRequest of ConductCertificateAction");
		return classMap;
	}
	
	
	public ActionForward printCC(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		ConductCertificateForm certificateForm=(ConductCertificateForm)form;
		try
		{		
			ActionErrors errors = certificateForm.validate(mapping, request);
			if (errors.isEmpty())
			{
				List<PrintTcDetailsTo>studentList= null;				
				studentList=ConductCertificateHandler.getInstance().getStudentsByClass(request,certificateForm);				
				certificateForm.setStudentList(studentList);
				certificateForm.resetFields();
			}
			else
			{
				addErrors(request, errors);
			}
		}
		catch (Exception e) {
			 String msg = super.handleApplicationException(e);
			 certificateForm.setErrorMessage(msg);
			 certificateForm.setErrorStack(e.getMessage());
	         return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.PRINT_CONDUCT_CERTIFICATE);		
	}	
	
	public ActionForward getCandidatesForCCPrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		ConductCertificateForm certificateForm = (ConductCertificateForm) form;
		ActionErrors errors = certificateForm.validate(mapping, request);
		List<TCDetailsTO>allStudents=null;
		if (errors.isEmpty()) {
			try {
				allStudents=ConductCertificateHandler.getInstance().getStudentsByName(certificateForm.getStudentName(),request,certificateForm);						 
				if (allStudents.isEmpty()) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);					
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.CONDUCT_CERTIFICATE);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				certificateForm.setErrorMessage(msg);
				certificateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);			
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.CONDUCT_CERTIFICATE);
		}
		certificateForm.setTcDetails(allStudents);
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.CONDUCT_CERTIFICATE);
	}
}
