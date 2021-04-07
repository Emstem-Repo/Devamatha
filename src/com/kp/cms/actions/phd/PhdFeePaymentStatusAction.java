package com.kp.cms.actions.phd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.phd.PhdFeePaymentStatusForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.phd.PhdFeePaymentStatusHandler;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.to.phd.PhdFeePaymentStatusTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.jms.MailTO;

public class PhdFeePaymentStatusAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(PhdFeePaymentStatusAction.class);
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward initPhdFeePaymentStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdFeePaymentStatusForm feePaymentStatusForm = (PhdFeePaymentStatusForm)form;
			try{
			feePaymentStatusForm.clearList();
			List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
			Iterator<ProgramTypeTO> iterator = programTypeList.iterator();
			while (iterator.hasNext()) {
				ProgramTypeTO programbo = (ProgramTypeTO) iterator.next();
				if(programbo.getProgramTypeId() == 4){
					feePaymentStatusForm.setCurrentProgramType(programbo.getProgramTypeId());
					break;
				}
			}
			ArrayList<KeyValueTO> list=new ArrayList<KeyValueTO>();
			ArrayList<CourseTO> courseList=new ArrayList<CourseTO>();
			if(feePaymentStatusForm.getCurrentProgramType() !=0){
				list=CommonAjaxHandler.getInstance().getCoursesByProgramTypes1(String.valueOf(feePaymentStatusForm.getCurrentProgramType()));
			}
			
			Iterator<KeyValueTO> iterator1 = list.iterator();
			while (iterator1.hasNext()) {
				KeyValueTO to = (KeyValueTO) iterator1.next();
				CourseTO courseTo=new CourseTO();
				courseTo.setId(to.getId());
				courseTo.setName(to.getDisplay());
				courseList.add(courseTo);
			}
			feePaymentStatusForm.setProgramTypeList(programTypeList);
			feePaymentStatusForm.setCourseList(courseList);
			}catch (Exception exception) {
				log.error("Error occured in initPhdFeePaymentStatus of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				feePaymentStatusForm.setErrorMessage(msg);
				feePaymentStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			return mapping.findForward(CMSConstants.PHD_FEE_PAYMENT_STATUS);
		}
		/**
		 * @param FeePaymentStatusForm
		 * @throws Exception
		 */
		private void setCourseToForm(PhdFeePaymentStatusForm FeePaymentStatusForm) throws Exception{
			List<CourseTO> listValues=PhdFeePaymentStatusHandler.getInstance().getCoursesByProgramTypes(FeePaymentStatusForm.getProgramTypeId());
			FeePaymentStatusForm.setCourseList(listValues);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward getFeePaymentStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdFeePaymentStatusForm feePaymentStatusForm = (PhdFeePaymentStatusForm)form;
			feePaymentStatusForm.clearList1();
			 ActionErrors errors = feePaymentStatusForm.validate(mapping, request);
			  if(errors.isEmpty())
			     {
			try{
				
				List<PhdFeePaymentStatusTO> pendingDocument = PhdFeePaymentStatusHandler.getInstance().getFeePaymentStatus(feePaymentStatusForm);
				if(pendingDocument == null || pendingDocument.isEmpty()){
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
					setCourseToForm(feePaymentStatusForm);
					return mapping.findForward(CMSConstants.PHD_FEE_PAYMENT_STATUS);
				}
				feePaymentStatusForm.setStudentDetailsList(pendingDocument);// setting to the form 
				setCourseToForm(feePaymentStatusForm);
				}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				feePaymentStatusForm.setErrorMessage(msg);
				feePaymentStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}	} else
		     {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.PHD_FEE_PAYMENT_STATUS);
			}
			return mapping.findForward(CMSConstants.PHD_FEE_PAYMENT_STATUS);
		}
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		@SuppressWarnings("deprecation")
		public ActionForward SendMailsFeePaymentStatus(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			PhdFeePaymentStatusForm feePaymentStatusForm = (PhdFeePaymentStatusForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = new ActionErrors();
			int i=0;
			try{
				List<PhdFeePaymentStatusTO> mailIds = feePaymentStatusForm.getStudentDetailsList();
				if(mailIds != null){
					Iterator<PhdFeePaymentStatusTO> iterator = mailIds.iterator();
					TemplateHandler temphandle=TemplateHandler.getInstance();
					List<GroupTemplate> list= temphandle.getDuplicateCheckList("Phd- Fee payment Due");
					if(list != null && !list.isEmpty()) {
						while (iterator.hasNext()) {
							PhdFeePaymentStatusTO feePaymentTo = (PhdFeePaymentStatusTO) iterator.next();
							MailTO mailto = new MailTO();
							if(feePaymentTo.geteMail() != null && !feePaymentTo.geteMail().trim().isEmpty() && feePaymentTo.getChecked()!=null){
							String desc ="";
							if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
								desc = list.get(0).getTemplateDescription();
							}
							desc = desc.replace("[RegisterNo]", feePaymentTo.getRegisterNo());
							desc = desc.replace("[NAME]", feePaymentTo.getStudentName());
							desc = desc.replace("[FEEPAYMENT_FOR_ACADEMICYEAR]", feePaymentStatusForm.getYear());
							desc = desc.replace("[COURSE]", feePaymentTo.getCourseName());
							mailto.setToAddress(feePaymentTo.geteMail());
							mailto.setFromAddress("appadmin@christuniversity.in");
							mailto.setMessage(desc);
							mailto.setSubject("Fee payment Due - Christ University");
							CommonUtil.sendMail(mailto);
							i++;
						}
					  }
					}
				}
			}catch (Exception exception) {
				log.error("Error occured in getStudentDetails of AssignSubjectGroupHistoryAction",exception);
				String msg = super.handleApplicationException(exception);
				feePaymentStatusForm.setErrorMessage(msg);
				feePaymentStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}if(i>0){	
			ActionMessage message = new ActionMessage("knowledgepro.phd.sendMails.success");
			messages.add("messages", message);
			saveMessages(request, messages);
			}else{
				errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
				saveErrors(request, errors);
			}
			return mapping.findForward(CMSConstants.PHD_FEE_PAYMENT_STATUS);
		}
}