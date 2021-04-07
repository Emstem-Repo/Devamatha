package com.kp.cms.actions.hostel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.exam.TempHallTicketOrIDCardForm;
import com.kp.cms.forms.hostel.AvailableSeatsForm;
import com.kp.cms.forms.hostel.FineCategoryForm;
import com.kp.cms.forms.hostel.FineEntryForm;
import com.kp.cms.forms.hostel.HostelVisitorsInfoForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.handlers.hostel.FineCategoryHandler;
import com.kp.cms.handlers.hostel.FineEntryHandler;
import com.kp.cms.handlers.hostel.HostelVisitorsInfoHandler;
import com.kp.cms.helpers.hostel.FineEntryHelper;
import com.kp.cms.to.hostel.FineCategoryTo;
import com.kp.cms.to.hostel.FineEntryTo;
import com.kp.cms.transactions.hostel.IHostelVisitorsInfoTransaction;
import com.kp.cms.transactionsimpl.hostel.HostelVisitorsInfoTransactionImpl;

public class FineEntryAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(FineEntryAction.class);
	/**
	 * init method
	 */
	public ActionForward initFineEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		reset(fineEntryForm);
		resetPrintFineEntryDetails(fineEntryForm);
		setRequiredDatatoForm(fineEntryForm);
		setFineEntryListToForm(fineEntryForm);
		// /* code added by chandra
		if (fineEntryForm.getRgNoFromHlTransaction() != null && !fineEntryForm.getRgNoFromHlTransaction().isEmpty()) {
			fineEntryForm.setRegNo(fineEntryForm.getRgNoFromHlTransaction());
			fineEntryForm.setAcademicYear(fineEntryForm.getYear());
			if(fineEntryForm.getRgNoFromHlTransaction() != null && !fineEntryForm.getRgNoFromHlTransaction().isEmpty()
					&& (fineEntryForm.getYear()!=null && !fineEntryForm.getYear().isEmpty())){
				String regNo=fineEntryForm.getRgNoFromHlTransaction();
				String academicYear=fineEntryForm.getYear();
				FineEntryHelper.getInstance().getStudentDetails(fineEntryForm, regNo, academicYear);
				request.setAttribute("admOperation", "add");
			}
		}
		// */ code added by chandra
		return mapping.findForward("fineEntry");
	}
	/**
	 * set required data to from
	 */
	public void setFineEntryListToForm(FineEntryForm fineEntryForm)throws Exception{
		List<FineEntryTo> list=FineEntryHandler.getInstance().getFineEntryList(fineEntryForm);
		fineEntryForm.setFineEntryToList(list);
	}
	/**
	 * set required data to form
	 * @param holidaysForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(FineEntryForm fineEntryForm) throws Exception{
		/*Map<String, String> hostelMap = new HashMap<String, String>();
		hostelMap = AvailableSeatsHandler.getInstance().getHostelMap();
		fineEntryForm.setHostelMap(hostelMap);*/
		 Map<String, String> categoryMap=FineEntryHandler.getInstance().getFineCategoryList();
		fineEntryForm.setCategoryMap(categoryMap);
	}
	/**
	 * method used to add  fine entry
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addFineEntry(ActionMapping mapping, ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {

		log.debug("inside action class. addFineEntry Action");
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = fineEntryForm.validate(mapping, request);
		boolean isAdded=false;
		try {
			if(!errors.isEmpty()){
				saveErrors(request, errors);
				setFineEntryListToForm(fineEntryForm);
				setRequiredDatatoForm(fineEntryForm);
				String regNo=fineEntryForm.getRegNo();
				String academicYear=fineEntryForm.getAcademicYear();
				if(!regNo.isEmpty()&& !academicYear.isEmpty()){
					FineEntryHelper.getInstance().getStudentDetails(fineEntryForm, regNo, academicYear);
				}
				request.setAttribute("admOperation", "add");
				return mapping.findForward("fineEntry");
				
			}
			String add="add";
			setUserId(request, fineEntryForm);  //setting user id to update last changed details
			IHostelVisitorsInfoTransaction transaction=HostelVisitorsInfoTransactionImpl.getInstance();
			String hlAdmissionId=transaction.getHlAdmissionId(fineEntryForm.getAcademicYear(),fineEntryForm.getRegNo());
			if(hlAdmissionId!=null && !hlAdmissionId.isEmpty()){
				fineEntryForm.setHlAdminId(hlAdmissionId);
				isAdded = FineEntryHandler.getInstance().addFineEntry(fineEntryForm,add);
			}
			if (isAdded) {
				// success .
				// set the fine entry details to print
				FineEntryHandler.getInstance().setTheFineDetailsToPrint(fineEntryForm,add);
				fineEntryForm.setPrintFineEntry("true");
				reset(fineEntryForm);
				
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.attn.fineEntry.info.addfail"));
				saveErrors(request, errors);
			}
		} catch (Exception e) {
			log.error("error in final submit of admitted through page...", e);
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineEntryForm.setErrorMessage(msg);
				fineEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		log.debug("Leaving action class addFineEntry");
		setFineEntryListToForm(fineEntryForm);
//		setRequiredDatatoForm(fineEntryForm);
		return mapping.findForward("fineEntry");
	}
	/**
	 * reset the values
	 */
	public void reset(FineEntryForm fineEntryForm){
		fineEntryForm.setHostelId(null);
		fineEntryForm.setAcademicYear(null);
		fineEntryForm.setRegNo(null);
		fineEntryForm.setCategoryId(null);
		fineEntryForm.setAmount(null);
		fineEntryForm.setFineEntryToList(null);
		fineEntryForm.setRemarks(null);
		fineEntryForm.setFlag(false);
		fineEntryForm.setHlAdminId(null);
		fineEntryForm.setStudentBed(null);
		fineEntryForm.setStudentBlock(null);
		fineEntryForm.setStudentCourse(null);
		fineEntryForm.setStudentHostel(null);
		fineEntryForm.setStudentName(null);
		fineEntryForm.setStudentRoom(null);
		fineEntryForm.setStudentUnit(null);
	}
	/**
	 * reset the print fine entry details
	 */
	public void resetPrintFineEntryDetails(FineEntryForm fineEntryForm){
		fineEntryForm.setpCategory(null);
		fineEntryForm.setpRegisterNo(null);
		fineEntryForm.setpStudentName(null);
		fineEntryForm.setpAmount(null);
		fineEntryForm.setpHostel(null);
		fineEntryForm.setPrintFineEntry(null);
	}
	/**
	 * deleting the visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFineEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		ActionMessages messages = new ActionMessages();

		try {
			boolean isDeleted = FineEntryHandler.getInstance().deleteFineEntry(fineEntryForm);
			if (isDeleted) {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.delete.success");
				messages.add("messages", message);
				saveMessages(request, messages);
//				reset(fineEntryForm);
			} else {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.delete.fail");
				messages.add("messages", message);
				saveMessages(request, messages);
			}
		} catch (Exception e) {
			log.error("error submitFineEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineEntryForm.setErrorMessage(msg);
				fineEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				fineEntryForm.setErrorMessage(msg);
				fineEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
//		Code added by sudhir 
		/*setFineEntryListToForm(fineEntryForm);
		setRequiredDatatoForm(fineEntryForm);*/
//		setFineEntryListToForm(fineEntryForm);
		List<FineEntryTo> fineEntryTos = FineEntryHandler.getInstance().getSearchFineEntryListForRegNo(fineEntryForm);
		if(fineEntryTos!=null && !fineEntryTos.isEmpty()){
			fineEntryForm.setFineEntryToList(fineEntryTos);
		}
		return mapping.findForward("initEditFineEntry");
	}
	/**
	 * editing the Fine Entry details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editFineEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		fineEntryForm.setFineEntryToList(null);
		fineEntryForm.setFlag(true);
		log.debug("Entering editFineEntry ");
		try{
			FineEntryHandler.getInstance().editFineEntry(fineEntryForm);
			if(request.getParameter("propertyName")!=null && !request.getParameter("propertyName").isEmpty()){
				if(request.getParameter("propertyName").equalsIgnoreCase("view")){
					// success .
					// set the fine entry details to print
					resetPrintFineEntryDetails(fineEntryForm);
					FineEntryHandler.getInstance().setTheFineDetailsToPrint(fineEntryForm,"view");
					fineEntryForm.setPrintFineEntry("true");
					return mapping.findForward("viewFineEntry");
				}
			}
			request.setAttribute("admOperation", "edit");
			//request.setAttribute("Update", "Update");// setting update attribute
			log.debug("Leaving editVisitorsInfo ");
		}catch (Exception e) {
			log.error("error in editing editFineEntry...", e);
			String msg = super.handleApplicationException(e);
			fineEntryForm.setErrorMessage(msg);
			fineEntryForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
//		setFineEntryListToForm(fineEntryForm);
//		setRequiredDatatoForm(fineEntryForm);
		return mapping.findForward("editfineEntry");
	}
	/**
	 * updating the available Seats details
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateFineEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		setUserId(request, fineEntryForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = fineEntryForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				String add="update";
				boolean isUpdated =FineEntryHandler.getInstance().addFineEntry(fineEntryForm,add);
					if (isUpdated) {
						FineEntryHandler.getInstance().setTheFineDetailsToPrint(fineEntryForm,add);
						fineEntryForm.setPrintFineEntry("true");
						//reset(fineEntryForm);
					} else {
						errors .add( "error", new ActionError( "knowledgepro.hostel.fineEntry.updatefial"));
						request.setAttribute("admOperation", "edit");
						setFineEntryListToForm(fineEntryForm);
						setRequiredDatatoForm(fineEntryForm);
						return mapping.findForward("editfineEntry");
					}
			}else{
				saveErrors(request, errors);
				request.setAttribute("admOperation", "edit");
				setFineEntryListToForm(fineEntryForm);
				setRequiredDatatoForm(fineEntryForm);
				return mapping.findForward("editfineEntry");
			}
		} catch (BusinessException businessException) {
			log.info("Exception submitOpenConnectionDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			fineEntryForm.setErrorMessage(msg);
			fineEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
//		Code added by sudhir 
//		setFineEntryListToForm(fineEntryForm);
		List<FineEntryTo> fineEntryTos = FineEntryHandler.getInstance().getSearchFineEntryListForRegNo(fineEntryForm);
		if(fineEntryTos!=null && !fineEntryTos.isEmpty()){
			fineEntryForm.setFineEntryToList(fineEntryTos);
		}
//		setRequiredDatatoForm(fineEntryForm);
		return mapping.findForward("initEditFineEntry");
	}
	/**
	 * deleting the visitors information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateWhenPaidTheFine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering ");
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try {
			boolean isPaid = FineEntryHandler.getInstance().updateWhenPaidTheFine(fineEntryForm);
			if (isPaid) {
				ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.updatesuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				reset(fineEntryForm);
			} else {
				errors .add( "error", new ActionError("knowledgepro.hostel.fineEntry.updatefial"));
				saveErrors(request, errors);
				/*ActionMessage message = new ActionMessage("knowledgepro.hostel.fineEntry.updatefial");
				messages.add("messages", message);
				saveMessages(request, messages);*/
			}
		} catch (Exception e) {
			log.error("error submitFineEntry...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				fineEntryForm.setErrorMessage(msg);
				fineEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				String msg = super.handleApplicationException(e);
				fineEntryForm.setErrorMessage(msg);
				fineEntryForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}
		setFineEntryListToForm(fineEntryForm);
		setRequiredDatatoForm(fineEntryForm);
		return mapping.findForward("fineEntry");
	}
	public ActionForward printFineEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		fineEntryForm.setPrintFineEntry(null);
		return mapping.findForward(CMSConstants.PRINT_FINE_ENTRY_DETAILS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEditFineEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		reset(fineEntryForm);
		resetPrintFineEntryDetails(fineEntryForm);
		setRequiredDatatoForm(fineEntryForm);
		return mapping.findForward("initEditFineEntry");
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFineEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FineEntryForm fineEntryForm=(FineEntryForm)form;
		 ActionErrors errors = fineEntryForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				List<FineEntryTo> fineEntryTos = FineEntryHandler.getInstance().getSearchFineEntryListForRegNo(fineEntryForm);
				if(fineEntryTos!=null && !fineEntryTos.isEmpty()){
					fineEntryForm.setFineEntryToList(fineEntryTos);
				}else{
					errors.add("error", new ActionError("knowledgepro.attendance.subjectgroup.no.records"));
					saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
			}
		}catch (Exception e) {
			log.error("Error in searchFineEntry of fineEntry Action",e);
			String msg = super.handleApplicationException(e);
			fineEntryForm.setErrorMessage(msg);
			fineEntryForm.setErrorStack(e.getMessage());
		}
		return mapping.findForward("initEditFineEntry");
	}
}
