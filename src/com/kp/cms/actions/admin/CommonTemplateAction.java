package com.kp.cms.actions.admin;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.handlers.admin.CommonTemplateHandler;
import com.kp.cms.to.admin.SportsTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class CommonTemplateAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(CommonTemplateAction.class);
	/**
	 * No Due Certificate First page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNoDuePrint(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE);
		
	}
	/**
	 * to get No Due details to print certificate
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printNoDue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession();
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				commonTemplateForm.setPrintPage(null);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE);
			}
			CommonTemplateHandler.getInstance().getNoDuePrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE);
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE);
	
	}
	/**
	 * to print No Due Certificate
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printNoDueCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
	commonTemplateForm.setDate(CommonUtil.getTodayDate());
	HttpSession session = request.getSession();
	commonTemplateForm.setMessageList((List)session.getAttribute("MessageList"));
	commonTemplateForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.SHOW_NODUE);

	}
	/**
	 * 
	 * To Display Visa Letter First page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initVisaLetter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
	commonTemplateForm.setDate(CommonUtil.getTodayDate());
	commonTemplateForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.INIT_VISALETTER);

	}
	/**
	 * 
	 * To get Visa Letter datails to print
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printVisaLetter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession();
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_VISALETTER);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_VISALETTER);
			}
			CommonTemplateHandler.getInstance().getVisaPrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_VISALETTER);
			}else{
				session.setAttribute("VisaMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_VISALETTER);
	}
	/**
	 * 
	 * To print Visa Letter
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printVisaCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
			commonTemplateForm.setDate(CommonUtil.getTodayDate());
			HttpSession session = request.getSession();
			commonTemplateForm.setMessageList((List)session.getAttribute("VisaMessageList"));
			commonTemplateForm.reset(mapping, request);
			return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * 
	 * To display Attempt Certificate First Page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAttemptCertificate(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ATTEMPT);

	}
	/**
	 * 
	 * to get Attempt certificate details to print
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printAttemptCertificate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getExamDate() == null || commonTemplateForm.getExamDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attemptCertificate.examDate"));
			}
			if(commonTemplateForm.getAttempts() == null || commonTemplateForm.getAttempts().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.attemptCertificate.attempts"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ATTEMPT);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim()); 
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ATTEMPT);
			}
			CommonTemplateHandler.getInstance().getAttemptCertificatePrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ATTEMPT);
			}else{
				session.setAttribute("AttemptMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ATTEMPT);
	}
	/**
	 * to print Attempt certificate
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward attemptCertificatePrint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("AttemptMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initScholarshipTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getScholarshipDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
			}
			CommonTemplateHandler.getInstance().getScholarshipPrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
			}else{
				session.setAttribute("ScholarshipMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printScholarshipDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("ScholarshipMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMediumTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MEDIUM);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMediumDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MEDIUM);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
			}
			CommonTemplateHandler.getInstance().getMediumPrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MEDIUM);
			}else{
				session.setAttribute("ScholarshipMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MEDIUM);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMediumDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("ScholarshipMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * To display Sports template page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSportsTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		setSportsListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SPORTS);
	}
	/**
	 * to get sports details   
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getSportsDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		setSportsListToRequest(request);
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getSportsId() == null || commonTemplateForm.getSportsId().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.sports.required"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SPORTS);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SPORTS);
			}
			CommonTemplateHandler.getInstance().getSportsPrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SPORTS);
			}else{
				session.setAttribute("SportsMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SPORTS);
	}
	
	/**
	 * to print Sports details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printSportsDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("SportsMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param request
	 * @throws Exception
	 */
	public void setSportsListToRequest(HttpServletRequest request) throws Exception {
		log.debug("inside setProgramTypeListToRequest");
		List<SportsTO> sportsList = CommonTemplateHandler.getInstance().getSportsList();
		request.setAttribute("sportsList", sportsList);
	}
	/**
	 * To display Sports template page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initTutionFeeFormat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TUTIONFEE);
	}
	/**
	 * to get sports details   
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getTutionFeeFormatDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TUTIONFEE);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TUTIONFEE);
			}
			CommonTemplateHandler.getInstance().getTutionFeeDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TUTIONFEE);
			}else{
				session.setAttribute("TutionMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TUTIONFEE);
	}
	
	/**
	 * to print Sports details
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printTutionFeeFormat(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("TutionMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initForeignNOCTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FOREIGN);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getForeignNOCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getStartDate() == null || commonTemplateForm.getStartDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getEndDate() == null || commonTemplateForm.getEndDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			validateStartDate(commonTemplateForm,errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FOREIGN);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FOREIGN);
			}
			CommonTemplateHandler.getInstance().getForeignNOCPrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FOREIGN);
			}else{
				session.setAttribute("NOCMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FOREIGN);
	}
	
	/**
	 * @param commonTemplateForm
	 * @param errors
	 */
	private void validateStartDate(CommonTemplateForm commonTemplateForm,
			ActionErrors errors) {
		if(commonTemplateForm.getStartDate()!=null && !StringUtils.isEmpty(commonTemplateForm.getStartDate())&& !CommonUtil.isValidDate(commonTemplateForm.getStartDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(commonTemplateForm.getEndDate()!=null && !StringUtils.isEmpty(commonTemplateForm.getEndDate())&& !CommonUtil.isValidDate(commonTemplateForm.getEndDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(commonTemplateForm.getEndDate()!=null && !StringUtils.isEmpty(commonTemplateForm.getEndDate())&& CommonUtil.isValidDate(commonTemplateForm.getEndDate())){
			
			if(CommonUtil.checkForEmpty(commonTemplateForm.getStartDate()) && CommonUtil.checkForEmpty(commonTemplateForm.getEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(commonTemplateForm.getStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(commonTemplateForm.getEndDate());
				
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
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
	public ActionForward printForeignNOCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("NOCMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FEE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getAcademicYear() == null || commonTemplateForm.getAcademicYear().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.fee.academicyear.required"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FEE);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FEE);
			}
			CommonTemplateHandler.getInstance().getFeePrintDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FEE);
			}else{
				session.setAttribute("FEEMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_FEE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
	commonTemplateForm.setDate(CommonUtil.getTodayDate());
	HttpSession session = request.getSession();
	commonTemplateForm.setMessageList((List)session.getAttribute("FEEMessageList"));
	commonTemplateForm.reset(mapping, request);
	return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);

	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMarkTranscriptFirstPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMarkTranscriptFirstPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE);
			}
			CommonTemplateHandler.getInstance().getMarkTranscriptDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE);
			}else{
				session.setAttribute("MarkListIPU", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarkTranscriptFirstPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("MarkListIPU"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMarkTranscriptSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_TWO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMarkTranscriptSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			errors = validateFields(commonTemplateForm,errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_TWO);
			}
			CommonTemplateHandler.getInstance().getSecondPUMarkTranscriptDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_TWO);
			}else{
				session.setAttribute("MarkListIIPU", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_TWO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarkTranscriptSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("MarkListIIPU"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMarkTranscriptFirstAndSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE_TWO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMarkTranscriptFirstAndSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			errors = validateFields(commonTemplateForm,errors);
			if (!errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE_TWO);
			}
			CommonTemplateHandler.getInstance().getPUMarkTranscriptDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE_TWO);
			}else{
				session.setAttribute("MARKLISTII", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_TRANSCRIPTPU_ONE_TWO);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMarkTranscriptFirstAndSecondSecondPU(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("MARKLISTII"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAddressAndDOB(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ADDRESS_DOB);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAddressAndDOBDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ADDRESS_DOB);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_SCHOLARSHIP);
			}
			CommonTemplateHandler.getInstance().getAddressAndDOBDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ADDRESS_DOB);
			}else{
				session.setAttribute("AddressMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_ADDRESS_DOB);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printAddressAndDOBDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("AddressMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initNCCTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		setSportsListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_NCC);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getNCCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		setSportsListToRequest(request);
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getNcc() == null || commonTemplateForm.getNcc().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.ncc.required"));
			}
			if(commonTemplateForm.getCampYear() == null || commonTemplateForm.getCampYear().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.fee.academicyear.required"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_NCC);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_NCC);
			}
			CommonTemplateHandler.getInstance().getNCCDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_NCC);
			}else{
				session.setAttribute("NCCMessageList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_NCC);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printNCCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("NCCMessageList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initMotherTongueTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		commonTemplateForm.reset(mapping, request);
		setSportsListToRequest(request);
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MOTHER_TONGUE);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getMotherTongueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		ActionMessages messages = new ActionMessages();
		setSportsListToRequest(request);
		ActionErrors errors = commonTemplateForm.validate(mapping, request);
		HttpSession session = request.getSession(false);
		
		try {
			if(commonTemplateForm.getRegNoFrom() == null || commonTemplateForm.getRegNoFrom().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNOFrom"));
			}
			if(commonTemplateForm.getRegNoTo() == null || commonTemplateForm.getRegNoTo().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.regNoTo"));
			}
			if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
			}
			if(commonTemplateForm.getMotherTongue() == null || commonTemplateForm.getMotherTongue().trim().isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.mothertongue.required"));
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MOTHER_TONGUE);
			}
			boolean isValidRegdNo;
			isValidRegdNo = validateRegdNos(commonTemplateForm.getRegNoFrom().trim(), commonTemplateForm.getRegNoTo().trim());
			if(!isValidRegdNo){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MOTHER_TONGUE);
			}
			CommonTemplateHandler.getInstance().getMotherTongueDetails(commonTemplateForm, request);
			if(commonTemplateForm.getMessageList() == null || commonTemplateForm.getMessageList().size() <= 0){
				errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.norecords"));
				saveErrors(request, errors);
				commonTemplateForm.reset(mapping, request);
				return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MOTHER_TONGUE);
			}else{
				session.setAttribute("MotherTongueList", commonTemplateForm.getMessageList());
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
		return mapping.findForward(CMSConstants.INIT_COMMONTEMPLATE_MOTHER_TONGUE);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward printMotherTongueDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CommonTemplateForm commonTemplateForm = (CommonTemplateForm) form;
		commonTemplateForm.setDate(CommonUtil.getTodayDate());
		HttpSession session = request.getSession();
		commonTemplateForm.setMessageList((List)session.getAttribute("MotherTongueList"));
		commonTemplateForm.reset(mapping, request);
		return mapping.findForward(CMSConstants.PRINT_COMMONTEMPLATE);
	}
	/**
	 * 
	 * @param regdNoFrom
	 * @param regdNoTo
	 * @return Used to validate regd nos.
	 * Only alphanumeric is allowed
	 * @throws Exception
	 */
	private boolean validateRegdNos(String regdNoFrom, String regdNoTo) throws Exception{
		log.info("Entering into validateRegdNos");
		if(StringUtils.isAlphanumeric(regdNoFrom) && StringUtils.isAlphanumeric(regdNoTo)){
			return true;
		}
		else{
			log.info("Leaving into validateRegdNos");
			return false;
		}
	}
	/**
	 * @param commonTemplateForm
	 * @return
	 */
	private ActionErrors validateFields(CommonTemplateForm commonTemplateForm,ActionErrors errors) {
		if(!StringUtils.isAlphanumeric(commonTemplateForm.getRegNo())){
			errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ATTENDANCE_CREATE_PRACTICAL_REGDNO_TYPE));
		}
		if(commonTemplateForm.getRegNo() == null || commonTemplateForm.getRegNo().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.regno.required"));
		}
		if(commonTemplateForm.getExamRegNo() == null || commonTemplateForm.getExamRegNo().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.examregno.required"));
		}
		if(commonTemplateForm.getDate() == null || commonTemplateForm.getDate().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.addressPrint.date"));
		}
		if(commonTemplateForm.getDate()!=null && !StringUtils.isEmpty(commonTemplateForm.getDate())&& !CommonUtil.isValidDate(commonTemplateForm.getDate())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(commonTemplateForm.getExamYear() == null || commonTemplateForm.getExamYear().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.examination.date.required"));
		}
		if(commonTemplateForm.getResult() == null || commonTemplateForm.getResult().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.examination.result.required"));
		}
		if(commonTemplateForm.getExamYear()!=null && !StringUtils.isEmpty(commonTemplateForm.getExamYear())&& !CommonUtil.isValidDate(commonTemplateForm.getExamYear())){
			if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
				errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
			}
		}
		if(commonTemplateForm.getSubjectone() == null || commonTemplateForm.getSubjectone().trim().isEmpty() || 
				commonTemplateForm.getSubjecttwo() == null || commonTemplateForm.getSubjecttwo().trim().isEmpty() || 
				commonTemplateForm.getSubjectthree() == null || commonTemplateForm.getSubjectthree().trim().isEmpty() || 
				commonTemplateForm.getSubjectfore() == null || commonTemplateForm.getSubjectfore().trim().isEmpty() || 
				commonTemplateForm.getSubjectfive() == null || commonTemplateForm.getSubjectfive().trim().isEmpty() || 
				commonTemplateForm.getSubjectsix() == null || commonTemplateForm.getSubjectsix().trim().isEmpty()){
			errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.template.marks.required"));
		}
		
		return errors;
	}
}
