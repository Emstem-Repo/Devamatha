package com.kp.cms.actions.hostel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.AbsentiesListForm;
import com.kp.cms.handlers.hostel.AbsentiesListHandler;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;
import com.kp.cms.to.hostel.AbsentiesListTo;
import com.kp.cms.utilities.CommonUtil;

public class AbsentiesListAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AbsentiesListAction.class);
	/**
	 * init method
	 */
	public ActionForward initAbsentiesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AbsentiesListForm absentiesListForm=(AbsentiesListForm)form;
		reset(absentiesListForm);
		setDefaultValues(absentiesListForm);
		Map<Integer,String> map= AbsentiesListHandler.getInstance().getHostelMap();
		absentiesListForm.setHostelMap(map);
		return mapping.findForward("initAbsentiesList");
	}
	private void reset(AbsentiesListForm absentiesListForm) {
		absentiesListForm.setHostelId(null);
		absentiesListForm.setBlockMap(null);
		absentiesListForm.setUnitMap(null);
	}
	/**
	 * set the default values like previous day date and session
	 * @param absentiesListForm
	 * @throws Exception
	 */
	public void setDefaultValues(AbsentiesListForm absentiesListForm)throws Exception{
		Date currenDate=new Date();
		//Date lastDayDate=getPreviousDate(currenDate);
		absentiesListForm.setHolidaysFrom(CommonUtil.formatDates(currenDate));
		absentiesListForm.setHolidaysTo(CommonUtil.formatDates(currenDate));
		absentiesListForm.setHolidaysFromSession("Morning");
		absentiesListForm.setHolidaysToSession("Morning");
	}
	public Date getPreviousDate(Date date)throws Exception{
		Calendar c = Calendar.getInstance(); 
		c.setTime(date); 
		c.add(Calendar.DATE, -1);
		return c.getTime();
	}
	/**
	 * get the absenties list of hostel
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAbsentiesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
			AbsentiesListForm absentiesListForm=(AbsentiesListForm)form;
			absentiesListForm.setAbsentiesListTosList(null);
			ActionMessages messages = new ActionMessages();
			 ActionErrors errors = absentiesListForm.validate(mapping, request);
			try {
				Calendar calendar = GregorianCalendar.getInstance();
				calendar.setTime(new Date());
				if (!errors.isEmpty()) {
					saveErrors(request, errors);
				}else{
					//check from date and to date are more than to day date
					Boolean compareDates=AbsentiesListHandler.getInstance().checkTheDates(absentiesListForm);
					if(compareDates){
						errors.add("error", new ActionError("knowledgepro.hostel.absenties.date"));
						saveErrors(request, errors);
					}else{
						//List<AbsentiesListTo> list=AbsentiesListHandler.getInstance().absenties(absentiesListForm);
						List<AbsentiesListTo> list=AbsentiesListHandler.getInstance().getAbsentiesList(absentiesListForm);
						if(list!=null && !list.isEmpty()){
							absentiesListForm.setAbsentiesListTosList(list);
							absentiesListForm.setHostelId(null);
							return mapping.findForward("absentiesList");
						}else{
							setRequiredData(absentiesListForm);
							errors.add("error", new ActionError("knowledgepro.norecords"));
							saveErrors(request, errors);
						}
					}
				}
			} catch (Exception e) {
				if (e instanceof BusinessException) {
					String msgKey = super.handleBusinessException(e);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add("messages", message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					absentiesListForm.setErrorMessage(msg);
					absentiesListForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		return mapping.findForward("initAbsentiesList");
	}
	private void setRequiredData(AbsentiesListForm absentiesListForm)throws Exception {
		AbsentiesListHandler.getInstance().getBlockAndUnit(absentiesListForm);
	}
	public ActionForward sendMail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AbsentiesListForm absentiesListForm=(AbsentiesListForm)form;
		ActionMessages messages = new ActionMessages();
		try {
				//which are checked
				List<AbsentiesListTo> list=new ArrayList<AbsentiesListTo>();
				//disabling the tos which are checked
				List<AbsentiesListTo> list2=new ArrayList<AbsentiesListTo>();
				List<AbsentiesListTo> absentiesListTos=absentiesListForm.getAbsentiesListTosList();
				Iterator<AbsentiesListTo> iterator=absentiesListTos.iterator();
				while (iterator.hasNext()) {
					AbsentiesListTo absentiesListTo = (AbsentiesListTo) iterator.next();
					if(absentiesListTo.getChecked()!=null && !absentiesListTo.getChecked().isEmpty() && absentiesListTo.getChecked().equalsIgnoreCase("on")){
						list.add(absentiesListTo);
						absentiesListTo.setChecked(null);
						list2.add(absentiesListTo);
					}else{
						list2.add(absentiesListTo);
					}
					
				}
			
				boolean status=false;
				if(list!=null && !list.isEmpty()){
					if(absentiesListForm.getMethodName().equalsIgnoreCase("Send Mail")){
						status=AbsentiesListHandler.getInstance().sendMailForStudentOrParent(list,absentiesListForm.getMailFor());
					}else if(absentiesListForm.getMethodName().equalsIgnoreCase("Send Sms")){
						status=AbsentiesListHandler.getInstance().sendSmsForStudentOrParent(list,absentiesListForm.getMailFor());
					}
				}
				if(status){
					if(absentiesListForm.getMethodName().equalsIgnoreCase("Send Mail")){
						ActionMessage message = new ActionMessage("knowledgepro.admin.hostel.absent.student.mail.success");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						ActionMessage message = new ActionMessage("knowledgepro.admin.hostel.absent.student.sms.success");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
					absentiesListForm.setAbsentiesListTosList(null);
					absentiesListForm.setAbsentiesListTosList(list2);
				}else{
					if(absentiesListForm.getMethodName().equalsIgnoreCase("Send Mail")){
						ActionMessage message = new ActionMessage("knowledgepro.admin.hostel.absent.student.mail.fail");
						messages.add("messages", message);
						saveMessages(request, messages);
					}else{
						ActionMessage message = new ActionMessage("knowledgepro.admin.hostel.absent.student.sms.fail");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				}
			
			
			
		} catch (Exception e) {
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				absentiesListForm.setErrorMessage(msg);
				absentiesListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping.findForward("absentiesList");
	}
	
}
