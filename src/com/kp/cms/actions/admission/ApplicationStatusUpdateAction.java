package com.kp.cms.actions.admission;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.ApplicationStatusUpdateForm;
import com.kp.cms.handlers.admission.ApplicationStatusUpdateHandler;
import com.kp.cms.to.admission.ApplicationStatusUpdateTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ApplicationStatusUpdateAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ApplicationStatusUpdateAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initApplicationStatusUpdate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ApplicationStatusUpdateForm applicationStatusUpdateForm = (ApplicationStatusUpdateForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","application_missing_documents");
		
		applicationStatusUpdateForm.reset();
		setRequestedDataToForm(applicationStatusUpdateForm);
		return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);
	}
	/**
	 * @param applicationStatusUpdateForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(ApplicationStatusUpdateForm applicationStatusUpdateForm) throws Exception {
		// 1. Set the applicationStatusUpdate list
		Map<Integer,String> statusMap=ApplicationStatusUpdateHandler.getInstance().getApplicationStatus();
		applicationStatusUpdateForm.setApplicationStatusMap(statusMap);
		List<ApplicationStatusUpdateTO> statusUpdateTO = ApplicationStatusUpdateHandler.getInstance().getApplicationStatusList();
		applicationStatusUpdateForm.setStatusUpdateTO(statusUpdateTO);
		int year=CurrentAcademicYear.getInstance().getAcademicyear();
		applicationStatusUpdateForm.setYear(String.valueOf(year));
		applicationStatusUpdateForm.setOrigYear(String.valueOf(year));
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addApplicationStatusUpdate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApplicationStatusUpdateForm applicationStatusUpdateForm = (ApplicationStatusUpdateForm) form;
		setUserId(request, applicationStatusUpdateForm);
		ActionMessages messages = new ActionMessages();
		boolean isDuplicate=false;
		 ActionErrors errors = applicationStatusUpdateForm.validate(mapping, request);
		FormFile csvFile=applicationStatusUpdateForm.getCsvFile();
		if(csvFile.getFileName()!=null && !csvFile.getFileName().isEmpty()){
			if(csvFile.getFileName()!=null && !StringUtils.isEmpty(csvFile.getFileName())){
				String extn="";
				int indx=csvFile.getFileName().lastIndexOf(".");
				if(indx!=-1)
				extn=csvFile.getFileName().substring(indx, csvFile.getFileName().length());
				if(!extn.equalsIgnoreCase(".CSV")){
					
					 if(errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR).hasNext()){
						ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR);
						errors.add(CMSConstants.ADMISSIONFORM_OMR_FILETYPEERROR, error);
						}
				}
			}else{
				if(errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED)!=null && !errors.get(CMSConstants.ADMISSIONFORM_OMR_REQUIRED).hasNext()){
					ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_OMR_REQUIRED);
					errors.add(CMSConstants.ADMISSIONFORM_OMR_REQUIRED, error);
					}
			}
			Map<String,String> applicationStatusMap=ApplicationStatusUpdateHandler.getInstance().getApplicationStatusMap();
			boolean flag=ApplicationStatusUpdateHandler.getInstance().addUploadedData(csvFile.getInputStream(), applicationStatusMap,applicationStatusUpdateForm);
			List<Integer> applnNos=applicationStatusUpdateForm.getApplnNos();
			List<Integer> applnNosUnavailabe=applicationStatusUpdateForm.getApplnNosUnavailable();
			List<Integer> applnStatusUnavailable=applicationStatusUpdateForm.getApplnStatusUnavailable();
			List<Integer> mailNotSentIds=applicationStatusUpdateForm.getMailNotSentIds();
			if(applnNos!=null && !applnNos.isEmpty()){
				StringBuilder appNos=new StringBuilder();
				Iterator it=applnNos.iterator();
				while(it.hasNext()){
					appNos.append(it.next().toString()).append(",");
				}
				int len=appNos.length()-1;
		        if(appNos.toString().endsWith(",")){
		        	appNos.setCharAt(len, ' ');
		        }
				//ActionMessage error=new ActionMessage("knowledgepro.admission.applicationStatusUpdate.upload.duplicate");
				//errors.add("error", error);
				errors.add("error",new ActionError( "knowledgepro.admission.applicationStatusUpdate.upload.duplicate" ,appNos.toString().trim()));
				isDuplicate=true;
				addErrors(request, errors);
				applicationStatusUpdateForm.reset();
				//return mapping.findForward(CMSConstants.ADMISSIONFORM_CSVUPLOADCONFIRMPAGE);
			}if(applnNosUnavailabe!=null && !applnNosUnavailabe.isEmpty()){
				String appNos="";
				Iterator it=applnNosUnavailabe.iterator();
				while(it.hasNext()){
					appNos= appNos+it.next().toString()+",";
				}
				int len=appNos.length()-1;
		        if(appNos.endsWith(",")){
		            StringBuffer buff=new StringBuffer(appNos);
		            buff.setCharAt(len, ' ');
		            appNos=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.admission.ApplicantStatusUpdate.admappln.unavailable" ,appNos));
				addErrors(request, errors);
			}if(applnStatusUnavailable!=null && !applnStatusUnavailable.isEmpty()){
				String appNos="";
				Iterator it=applnStatusUnavailable.iterator();
				while(it.hasNext()){
					appNos= appNos+it.next().toString()+",";
				}
				int len=appNos.length()-1;
		        if(appNos.endsWith(",")){
		            StringBuffer buff=new StringBuffer(appNos);
		            buff.setCharAt(len, ' ');
		            appNos=buff.toString();
		        }
				errors.add("error",new ActionError( "knowledgepro.admission.ApplicantStatusUpdate.applnStatus.unavailable" ,appNos));
				addErrors(request, errors);
			}
			if(mailNotSentIds!=null && !mailNotSentIds.isEmpty()){
				String appNos="";
				Iterator it=mailNotSentIds.iterator();
				while(it.hasNext()){
					appNos= appNos+it.next().toString()+",";
				}
				int len=appNos.length()-1;
		        if(appNos.endsWith(",")){
		            StringBuffer buff=new StringBuffer(appNos);
		            buff.setCharAt(len, ' ');
		            appNos=buff.toString();
		        }
				errors.add("error",new ActionError("knowledgepro.admission.ApplicantStatusUpdate.mail" ,applicationStatusUpdateForm.getReason()));
				addErrors(request, errors);
			}
			 if(flag && isDuplicate)
			{
				messages.add(CMSConstants.MESSAGES,new ActionMessage( "knowledgepro.admission.applicationStatusUpdate.upload.success"));
				saveMessages(request, messages);
				applicationStatusUpdateForm.reset();
			}else if(flag){
				messages.add(CMSConstants.MESSAGES,new ActionMessage( "knowledgepro.admission.applicationStatusUpdate.upload"));
				saveMessages(request, messages);
				applicationStatusUpdateForm.reset();
			}else if(!isDuplicate){
				ActionMessage error=new ActionMessage("knowledgepro.admission.applicationStatusUpdate.upload.fail");
				errors.add("error", error);
				applicationStatusUpdateForm.reset();
				//errors.add("error", new ActionError( "knowledgepro.admission.applicationStatusUpdate.upload.fail"));
				//addErrors(request, errors);
				//saveErrors(request, errors);
			}
			//CSVUpdater.parseOMRData(csvfile.getInputStream(),admForm.getUserId(),courseMap);
		}
		else{
			if(applicationStatusUpdateForm.getApplicationNo()==null || applicationStatusUpdateForm.getApplicationNo().isEmpty()){
				errors.add("error", new ActionError( "knowledgepro.admission.applicationStatusUpdate.applNo.required"));
				addErrors(request, errors);
			}
			if(applicationStatusUpdateForm.getApplicationStatus()==null || applicationStatusUpdateForm.getApplicationStatus().isEmpty()){
				errors.add("error", new ActionError( "knowledgepro.admission.applicationStatusUpdate.status.required"));
				addErrors(request, errors);
			}
		 if (errors.isEmpty()) {
			try {
				boolean isAdded = false;
				 isDuplicate=ApplicationStatusUpdateHandler.getInstance().duplicateCheck(applicationStatusUpdateForm);
				if(!isDuplicate){
				isAdded = ApplicationStatusUpdateHandler.getInstance()
						.addApplicationStatusUpdate(applicationStatusUpdateForm);
				int mailNotSentId=applicationStatusUpdateForm.getMailNotSentId();
				if(mailNotSentId!=0){
					errors.add("error",new ActionError("knowledgepro.admission.ApplicantStatusUpdate.mail" ,applicationStatusUpdateForm.getReason()));
					addErrors(request, errors);
				}
				if (isAdded) {
					//ActionMessage message = new ActionMessage( "knowledgepro.employee.payScale.addsuccess");// Adding // added // message.
					messages.add(CMSConstants.MESSAGES, new ActionMessage( "knowledgepro.admission.applicationStatus.update.add"));
					saveMessages(request, messages);
					applicationStatusUpdateForm.reset();
				} else{
					errors.add("error", new ActionError( "knowledgepro.admission.applicationStatus.update.add.fail"));
					addErrors(request, errors);
					applicationStatusUpdateForm.reset();
				}
				}else{
					errors.add("error", new ActionError( "knowledgepro.admission.applicationStatusUpdate.duplicate"));
					addErrors(request, errors);
				}
			}
			catch(BusinessException businessException){
				if(businessException.getLocalizedMessage().equalsIgnoreCase("NoRecordsFound")){
					errors.add("error", new ActionError( "knowledgepro.admission.applicationStatus.update.add.fail"));
				}
				else if(businessException.getLocalizedMessage().equalsIgnoreCase("CardGenerated")){
					errors.add("error", new ActionError( "knowledgepro.admission.applicationStatus.update.card.generated"));
				}
				addErrors(request, errors);
				applicationStatusUpdateForm.reset();
				return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);
			}
			catch (Exception exception) {
				log.error("Error occured in ApplicationStatusUpdateAction", exception);
				String msg = super.handleApplicationException(exception);
				applicationStatusUpdateForm.setErrorMessage(msg);
				applicationStatusUpdateForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		 } else {
			setRequestedDataToForm(applicationStatusUpdateForm);
			return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);
		 }
	}
		saveErrors(request, errors);
		setRequestedDataToForm(applicationStatusUpdateForm);
		return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStatusUpdateByAppNO(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ApplicationStatusUpdateForm applicationStatusUpdateForm = (ApplicationStatusUpdateForm) form;
		ActionErrors errors = new ActionErrors();
		//errors = applicationStatusUpdateForm.validate(mapping, request);
		/*if(applicationStatusUpdateForm.getYear()==null || applicationStatusUpdateForm.getYear().isEmpty()){
			errors.add("error", new ActionError( "knowledgepro.admission.applicationStatus.update.year.required"));
			addErrors(request, errors);
		}*/
		if(applicationStatusUpdateForm.getApplicationNo()==null || applicationStatusUpdateForm.getApplicationNo().isEmpty()){
			errors.add("error", new ActionError( "knowledgepro.admission.statusUpdate.appNo.required"));
			addErrors(request, errors);
		}
		if (errors.isEmpty()) {
		   try{
			   List<ApplicationStatusUpdateTO> statusUpdateTo=ApplicationStatusUpdateHandler.getInstance().getApplicationStatusUpdate(applicationStatusUpdateForm);
			   applicationStatusUpdateForm.setStatusUpdateTO(statusUpdateTo);
		   }catch(Exception e){
			   log.error("Error occured in ApplicationStatusUpdateAction", e);
				String msg = super.handleApplicationException(e);
				applicationStatusUpdateForm.setErrorMessage(msg);
				applicationStatusUpdateForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		   }
		}else{
			saveErrors(request, errors);
			setRequestedDataToForm(applicationStatusUpdateForm);
			return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);
		}
		return mapping.findForward(CMSConstants.APPLICATION_STATUS_UPDATE);

	}
}
