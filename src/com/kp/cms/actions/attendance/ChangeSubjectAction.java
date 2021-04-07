package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.attendance.ChangeSubjectForm;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.handlers.attendance.ChangeSubjectHandler;
import com.kp.cms.to.attendance.AttendanceTO;
import com.kp.cms.to.attendance.ChangeSubjectTo;
import com.kp.cms.utilities.CommonUtil;


public class ChangeSubjectAction extends BaseDispatchAction{
	private static Log log = LogFactory.getLog(ChangeSubjectAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initChangeSubject(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered initChangeSubject input");
		ChangeSubjectForm changeSubjectForm = (ChangeSubjectForm) form;// Type casting the Action form to Required Form
		changeSubjectForm.resetFields();//Reseting the fields for input jsp
		log.info("Exit initChangeSubject input");
		
		return mapping.findForward(CMSConstants.Change_Subject);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getClassDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ChangeSubjectAction - getClassDetails");
		ChangeSubjectForm changeSubjectForm = (ChangeSubjectForm) form;// Type casting the Action form to Required Form
		ActionErrors errors = changeSubjectForm.validate(mapping, request);
		validateDates(changeSubjectForm,errors);
			if (errors.isEmpty()) {
			try {
				
				boolean subjectName=ChangeSubjectHandler.getInstance().getSubjectName(changeSubjectForm);
				if(subjectName==false){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.change.subject.not.found"));
					saveErrors(request, errors);
					changeSubjectForm.resetFields();
					return mapping.findForward(CMSConstants.Change_Subject);
				}
				
				List<AttendanceTO> classesList=ChangeSubjectHandler.getInstance().getClassBySubjectId(changeSubjectForm);
				if(classesList.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.change.subject.data.found"));
					//errors.add(CMSConstants.ERROR, new ActionError("errors.invalid","Subject Code"));
					saveErrors(request, errors);
					changeSubjectForm.resetFields();
					return mapping.findForward(CMSConstants.Change_Subject);
				}
				changeSubjectForm.setClassList1(classesList);
			
				}  catch (Exception exception) {
						String msg = super.handleApplicationException(exception);
						changeSubjectForm.setErrorMessage(msg);
						changeSubjectForm.setErrorStack(exception.getMessage());
						return mapping.findForward(CMSConstants.ERROR_PAGE);
					}
				} else {
							addErrors(request, errors);
							saveErrors(request, errors);
							log.info("Exit ChangeSubjectAction - getClassDetails errors not empty ");
							return mapping.findForward(CMSConstants.Change_Subject);
						}
			changeSubjectForm.setFlag(true);
			log.info("Exit ChangeSubjectAction -errors empty getClassDetails");
			return mapping.findForward(CMSConstants.Change_Subject);
	}
	/**
	 * @param changeSubjectForm
	 * @param errors
	 * @throws Exception
	 */
	private void validateDates( ChangeSubjectForm changeSubjectForm, ActionErrors errors) throws Exception {
		if(CommonUtil.checkForEmpty(changeSubjectForm.getFromDate()) && CommonUtil.checkForEmpty(changeSubjectForm.getToDate())){
			Date startDate = CommonUtil.ConvertStringToDate(changeSubjectForm.getFromDate());
			Date endDate = CommonUtil.ConvertStringToDate(changeSubjectForm.getToDate());

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_FROMDATE_CONNOTBELESS));
			}
		}
	}
	
	public ActionForward saveSelectedSubjectData(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered ChangeSubjectAction - saveSelectedSubjectData");
		ChangeSubjectForm changeSubjectForm = (ChangeSubjectForm) form;// Type casting the Action form to Required Form
			ActionErrors errors = new ActionErrors();
		ActionMessages messages=new ActionMessages();
		setUserId(request,changeSubjectForm);
		if(changeSubjectForm.getChangedSubject()!=null && !changeSubjectForm.getChangedSubject().isEmpty()){
			try {
				
				List<Integer> idList=new ArrayList<Integer>();
			    Iterator itr=changeSubjectForm.getClassList1().iterator();
				  while(itr.hasNext()){
					  AttendanceTO to=(AttendanceTO)itr.next();
						if(to.getChecked1()!=null && to.getChecked1().equalsIgnoreCase("on") ){
						  Integer id=to.getId();
						  idList.add(id);
							}
				  }
				  if(idList==null || idList.isEmpty()){
					  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.subject.empty.entry"));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.Change_Subject);
				  }else{
						boolean isSaved=ChangeSubjectHandler.getInstance().saveSelectedSubjectData(idList,changeSubjectForm);
						if(isSaved==false ){
							errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.subject.update.fail"));
							saveErrors(request, errors);
						}else{
							messages.add(CMSConstants.MESSAGES, new ActionError("knowledgepro.exam.subject.update.success"));
							saveMessages(request, messages);
						}
				  }
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				changeSubjectForm.setErrorMessage(msg);
				changeSubjectForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.not.select.changesubject"));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.Change_Subject);
		}
		changeSubjectForm.resetFields();
		log.info("Exit ChangeSubjectAction - saveSelectedSubjectData");
		return mapping.findForward(CMSConstants.Change_Subject);
	}
}
