package com.kp.cms.actions.hostel;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.StudentReqStatusForm;
import com.kp.cms.handlers.hostel.StudentReqStatusHandler;
import com.kp.cms.to.hostel.StudentReqStatusTO;

public class StudentReqStatusAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(StudentReqStatusHandler.class);
	/**
	 * This method performs getting student details when student logs in StudentReqStatusAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward initReqViewStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			log.info("entering execute in StudentReqStatusAction class..");
			StudentReqStatusForm reqStatusForm = (StudentReqStatusForm)form;
			try {
				HttpSession session = request.getSession();
				String studentid = (String) session.getAttribute("studentid");
				reqStatusForm.resetFields();
				if(studentid != null && !StringUtils.isEmpty(studentid)){
					//call of StudentReqStatusHandler class
					List<StudentReqStatusTO> studentDetailsTOList = StudentReqStatusHandler.getInstance().getStudentReqDetailsList(Integer.parseInt(studentid));
					if(studentDetailsTOList !=null){
						reqStatusForm.setList(studentDetailsTOList);
					}
				}
			} catch (BusinessException businessException) {
				log.error("error occured in execute of StudentReqStatusAction class.",businessException);
				String msgKey = super.handleBusinessException(businessException);
				reqStatusForm.setErrorMessage(msgKey);
				reqStatusForm.setErrorStack(businessException.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch (Exception exception) {
				log.error("error occured in execute of StudentReqStatusAction class.",exception);
				String msg = super.handleApplicationException(exception);
				reqStatusForm.setErrorMessage(msg);
				reqStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
			log.info("exit of execute in StudentReqStatusAction class..");
		return mapping.findForward(CMSConstants.STUD_REQ_STATUS_SEARCH);
	}
	
	/**
	 * This method performs getting student details when student logs in StudentReqStatusAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	
	public ActionForward initViewStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			log.info("entering execute in StudentReqStatusAction class..");
			StudentReqStatusForm reqStatusForm = (StudentReqStatusForm)form;
			try {
				HttpSession session = request.getSession();
				String studentid = (String) session.getAttribute("studentid");
				if(reqStatusForm.getHlId() != null && !StringUtils.isEmpty(reqStatusForm.getHlId())){
					//call of StudentReqStatusHandler class
					StudentReqStatusTO studentDetailsTOList = StudentReqStatusHandler.getInstance().getStudentDetailsList(Integer.parseInt(reqStatusForm.getHlId()));
					if(studentDetailsTOList !=null){
						reqStatusForm.setStudentDetailsTOList(studentDetailsTOList);
					}
				}else{
					List<StudentReqStatusTO> studentDetailsTOList = StudentReqStatusHandler.getInstance().getStudentReqDetailsList(Integer.parseInt(studentid));
					if(studentDetailsTOList !=null){
						reqStatusForm.setList(studentDetailsTOList);
					}
					return mapping.findForward(CMSConstants.STUD_REQ_STATUS_SEARCH);
				}
			} catch (BusinessException businessException) {
				log.error("error occured in execute of StudentReqStatusAction class.",businessException);
				String msgKey = super.handleBusinessException(businessException);
				reqStatusForm.setErrorMessage(msgKey);
				reqStatusForm.setErrorStack(businessException.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			} catch (Exception exception) {
				log.error("error occured in execute of StudentReqStatusAction class.",exception);
				String msg = super.handleApplicationException(exception);
				reqStatusForm.setErrorMessage(msg);
				reqStatusForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
			}
			log.info("exit of execute in StudentReqStatusAction class..");
		return mapping.findForward(CMSConstants.STUD_REQ_STATUS);
	}
}