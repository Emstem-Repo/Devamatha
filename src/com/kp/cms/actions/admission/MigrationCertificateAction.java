package com.kp.cms.actions.admission;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.admission.StudentCertificateDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.MigrationCertificateForm;
import com.kp.cms.handlers.admission.MigrationCertificateHandler;

/**
 * @author dIlIp
 *
 */
public class MigrationCertificateAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(MigrationCertificateAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward initMigrationCertificate(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		MigrationCertificateForm certificateForm=(MigrationCertificateForm)form;
		try
		{
			certificateForm.resetFields();
			//setDate(certificateForm);
		}
		catch (Exception e) {
			 String msg = super.handleApplicationException(e);
			 certificateForm.setErrorMessage(msg);
			 certificateForm.setErrorStack(e.getMessage());
	         return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("migrationCertificateInit");
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward migrationCertificateSearch(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)
	{
		
		MigrationCertificateForm certificateForm=(MigrationCertificateForm)form;
		ActionErrors errors = new ActionErrors();
		certificateForm.clearStuDetails();
		
			try{
				
				setUserId(request, certificateForm);
				Student student = MigrationCertificateHandler.getInstance().verifyRegisterNumberAndGetDetails(certificateForm);
				if(student == null){
					certificateForm.setShowRecord("NO");
					return mapping.findForward("migrationCertificatePrint");
				}
				
				StudentCertificateDetails details = MigrationCertificateHandler.getInstance().checkForAlreadyPrinted(certificateForm);
				if(details!=null){
					//dont save record
					certificateForm.clearFew();
					return mapping.findForward("migrationCertificatePrint");
				}
				else{
					//save record
					boolean isSaved;
					isSaved = MigrationCertificateHandler.getInstance().saveMigrationCertificate(certificateForm);
					if(isSaved){
						
						boolean isCurrentNoSaved = MigrationCertificateHandler.getInstance().saveMigrationCurrentNumber(certificateForm);
						if(isCurrentNoSaved){
						certificateForm.clearFew();
						return mapping.findForward("migrationCertificatePrint");
						}
					}
					else{
						errors.add("error", new ActionError("knowledgepro.petticash.save.failed"));
						addErrors(request, errors);
						return mapping.findForward("migrationCertificateInit");
					}
				}
				
			}catch (Exception e) {
				log.debug(e.getMessage());
			}catch (NoSuchFieldError e) {
				certificateForm.setShowRecord("NOPREFIX");
				return mapping.findForward("migrationCertificatePrint");
			}
		
		addErrors(request, errors);
		return mapping.findForward("migrationCertificateInit");
		
	}

}
