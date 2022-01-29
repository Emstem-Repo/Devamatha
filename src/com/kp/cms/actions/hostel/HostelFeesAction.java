package com.kp.cms.actions.hostel;

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
import com.kp.cms.forms.hostel.HostelFeesForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.hostel.HostelAllocationHandler;
import com.kp.cms.handlers.hostel.HostelFeeHandler;
import com.kp.cms.handlers.hostel.HostelFeesTypeHandler;
import com.kp.cms.to.hostel.HostelFeesTo;
import com.kp.cms.to.hostel.HostelFeesTypeTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class HostelFeesAction extends BaseDispatchAction {
	private static Log log = LogFactory.getLog(HostelFeesAction.class);
	/**
	 * initialize method
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initHostelFees(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering initHostelFees of HostelFeesAction");
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		try {
			hostelFeesForm.clearMyFields();
			setRequestedDataToForm(request, hostelFeesForm);
		} catch (Exception e) {
			log.error("error in initHostelFees...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelFeesForm.setErrorMessage(msg);
				hostelFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting initHostelFees of HostelFeesAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	}
	public ActionForward viewFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering viewFeeDetails of HostelFeesAction");
		
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		try {
			getFeeDetailedListToView(request,hostelFeesForm); 
		} catch (Exception e) {
			log.error("error in initHostelFees...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelFeesForm.setErrorMessage(msg);
				hostelFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		log.debug("Exiting viewFeeDetails of HostelFeesAction");
		return mapping.findForward(CMSConstants.VIEW_HOSTEL_FEES);
	}
	
	/**
	 * saving the hostel fees accourding to the input.
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering submitFeeDetails of HostelFeesAction");
		
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		setUserId(request, hostelFeesForm);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors =  hostelFeesForm.validate(mapping, request);
		validateFeeAmount(hostelFeesForm,errors);
		String isSaved ="false";
		if(!errors.isEmpty()){
			setRoomTypeMapToForm(request, hostelFeesForm);
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);	
		}
		try {
		List<HostelFeesTypeTo> listWithAmount = hostelFeesForm.getFeeList();
			if(listWithAmount!= null && !listWithAmount.isEmpty()){
				isSaved = HostelFeeHandler.getInstance().checkForAmountFeildsAreNull(listWithAmount);
				if(isSaved.equalsIgnoreCase("empty")){
					setRoomTypeMapToForm(request, hostelFeesForm);
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_FEES_AtLEASTONE_REQUIRED));
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);	
				}
				else{
				isSaved = HostelFeeHandler.getInstance().saveFeeDetails(hostelFeesForm,listWithAmount);
				}
			}
			else{
				errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_FEES_AtLEASTONE_REQUIRED));
				saveErrors(request, errors);
				setRequestedDataToForm(request, hostelFeesForm);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
				}
		} catch (Exception e) {
				log.error("error in submitFeeDetails...", e);
				if (e instanceof ApplicationException) {
					String msg = super.handleApplicationException(e);
					hostelFeesForm.setErrorMessage(msg);
					hostelFeesForm.setErrorStack(e.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} else {
					throw e;
				}
			}
		if(isSaved.equalsIgnoreCase(CMSConstants.HOSTEL_FEES_TRUE)){
			ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_SAVE_SUCCESS);
			messages.add("messages", message);
			saveMessages(request, messages);
			hostelFeesForm.clearMyFields();
		}
		else if(isSaved.equalsIgnoreCase(CMSConstants.HOSTEL_FEES_ACTIVEEXISTS)){
			ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_HOSTELWITHROOMTYPE_EXIST);
			messages.add("messages", message);
			saveMessages(request, messages);
			setRoomTypeMapToForm(request, hostelFeesForm);
		}
		else if(isSaved.equalsIgnoreCase(CMSConstants.HOSTEL_FEES_NONACTIVEEXISTS)){
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.HOSTEL_FEES_REACTIVATE,hostelFeesForm.getHostelId(),hostelFeesForm.getRoomType()));
			saveErrors(request, errors);
			hostelFeesForm.clearMyFields();
		}
		else{
			errors.add("error", new ActionError(CMSConstants.HOSTEL_FEES_SAVE_FAILED));
			saveErrors(request, errors);
		}
		setRequestedDataToForm(request, hostelFeesForm);
		log.debug("Exiting submitFeeDetails of HostelFeesAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	}
	/**
	 * deleting the record of hostel fee 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteFeeDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("Entering deleteFeeDetails of HostelFeesAction");
		boolean isDeleted = false;
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			if(hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty() && hostelFeesForm.getRoomId()!=null && !hostelFeesForm.getRoomId().isEmpty() ){
				isDeleted = HostelFeeHandler.getInstance().deleteFeeDetails(Integer.valueOf(hostelFeesForm.getHostelId()),Integer.valueOf(hostelFeesForm.getRoomId()));
			}
			
		} catch (Exception e) {
			log.error("error in initAllocation...", e);
			if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				hostelFeesForm.setErrorMessage(msg);
				hostelFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
			}
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.hostel.allocation.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.hostel.allocation.deletefailure"));
			saveErrors(request, errors);
		}
		hostelFeesForm.clearMyFields();
		setRequestedDataToForm(request, hostelFeesForm); 
		log.debug("Exiting deleteFeeDetails of HostelFeesAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	}
	
	
	/**
	 * @param request
	 * @param hostelFeesForm
	 * @throws Exception
	 */
	public void getFeeDetailedListToView(HttpServletRequest request, HostelFeesForm hostelFeesForm)throws Exception {
	log.debug("Entering getFeeDetailedListToView HostelFeesAction");
	String total="0.0";
	List<HostelFeesTo> feeDetailedListToView = HostelFeeHandler.getInstance().getFeeDetailedListToView(Integer.valueOf(hostelFeesForm.getHostelId()),Integer.valueOf(hostelFeesForm.getRoomId()));
	for (HostelFeesTo hostelFeesTo : feeDetailedListToView) {
		if(hostelFeesTo.getHostelName()!=null && !hostelFeesTo.getHostelName().isEmpty() ){
		hostelFeesForm.setHostelName(hostelFeesTo.getHostelName());
		}
		if(hostelFeesTo.getRoomType()!=null && !hostelFeesTo.getRoomType().isEmpty()){
			hostelFeesForm.setRoomType(hostelFeesTo.getRoomType());	
		}
		if(hostelFeesTo.getTotal()!=null && !hostelFeesTo.getTotal().isEmpty()){
			total = hostelFeesTo.getTotal();
			hostelFeesForm.setTotal(total);
		}
		else{
			hostelFeesForm.setTotal(total);
		}
	}
	hostelFeesForm.setFeeDetailedListToView(feeDetailedListToView);
	request.setAttribute("feeDetailedListToView", feeDetailedListToView);
	log.debug("Exiting getFeeDetailedListToView of HostelFeesAction ");
	}
	
	
	
	
	/**
	 * reactivate the deleted record in hostel fees
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reActivateFeeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Start of reActivateFeeDetails of HostelFeesAction");
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, hostelFeesForm);
			String userId=hostelFeesForm.getUserId();
			boolean isReactivate;
			if((hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty()) && (hostelFeesForm.getRoomType()!=null && !hostelFeesForm.getRoomType().isEmpty())){
			isReactivate = HostelFeeHandler.getInstance().reActivateFeeDetails(Integer.valueOf(hostelFeesForm.getHostelId()),Integer.valueOf(hostelFeesForm.getRoomType()), userId);
			}
			else{
			isReactivate = false;
			}
			/**
			 * If reactivation is success then add the success message.
			 * Else add the appropriate error message.
			 */
			if (isReactivate) {
				setRequestedDataToForm(request, hostelFeesForm);
				hostelFeesForm.setHostelId(null);
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.HOSTEL_FEES_REACTIVATE_SUCCESS));
				saveMessages(request, messages);
				
			} else {
				errors.add("error", new ActionError(CMSConstants.HOSTEL_FEES_REACTIVATE_FAILED));
				saveErrors(request, errors);
				setRequestedDataToForm(request, hostelFeesForm);
			}
		} catch (Exception e) {
			log.error("Error in reactivating lastReceiptNumber");			
				String msg = super.handleApplicationException(e);
				hostelFeesForm.setErrorMessage(msg);
				hostelFeesForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("End of reActivateFeeDetails of HostelFeesAction");
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	}
	
	/**
	 * setting the requested Data to Form
	 * @param request
	 * @param hostelFeesForm
	 * @throws Exception
	 */
	public void setRequestedDataToForm(HttpServletRequest request,HostelFeesForm hostelFeesForm) throws Exception{
		List<HostelTO> hostelList = HostelAllocationHandler.getInstance().getHostelDetails();
		hostelFeesForm.setHostelList(hostelList);
		List<HostelFeesTypeTo> feeList = HostelFeeHandler.getInstance().getFeeListToDisplay();
		hostelFeesForm.setFeeList(feeList);
		List<HostelFeesTo> feeDetailedList = HostelFeeHandler.getInstance().getFeeDetailedListToDisplay();
		hostelFeesForm.setFeeAllDetails(feeDetailedList);
	}
	/**
	 * @param request
	 * @param hostelFeesForm
	 */
	public void setRoomTypeMapToForm(HttpServletRequest request,HostelFeesForm hostelFeesForm){
		if(hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty()){
			Map<Integer, String> roomTypeMap = CommonAjaxHandler.getInstance() .getRoomTypeByHostel(
					Integer.parseInt(hostelFeesForm.getHostelId()));
			request.setAttribute("roomTypeMap", roomTypeMap);
		}
	}
	
	public ActionForward editFeeDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		try
		{
			int hostelId = Integer.parseInt(hostelFeesForm.getHostelId());
			
			hostelFeesForm.setOldHostelId(hostelFeesForm.getHostelId());
			hostelFeesForm.setOldRoomTypeId(hostelFeesForm.getRoomType());
			List<HostelFeesTypeTo> hostelFeesTo = HostelFeeHandler.getInstance().getFeeDetailsToEdit(hostelFeesForm);
			hostelFeesForm.setFeeList(hostelFeesTo);
			Map<Integer,String> map = CommonAjaxHandler.getInstance().getRoomTypeByHostel(hostelId);
			request.setAttribute("roomTypeMap", map);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		}
		catch(Exception e)
		{
			String msg = super.handleApplicationException(e);
			hostelFeesForm.setErrorMessage(msg);
			hostelFeesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	
	}
	

	public ActionForward updateFeeDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		HostelFeesForm hostelFeesForm = (HostelFeesForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors =  hostelFeesForm.validate(mapping, request);
		setUserId(request, hostelFeesForm);
		validateFeeAmount(hostelFeesForm,errors);
		try
		{
			if(isCancelled(request))
			{
				
				hostelFeesForm.setHostelId(hostelFeesForm.getOldHostelId());
				hostelFeesForm.setRoomType(hostelFeesForm.getOldRoomTypeId());
				List<HostelFeesTypeTo> hostelFeesTypeTo = HostelFeeHandler.getInstance().getFeeDetailsToEdit(hostelFeesForm);
				
					hostelFeesForm.setFeeList(hostelFeesTypeTo);
				
				int hostelId = Integer.parseInt(hostelFeesForm.getOldHostelId());
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				Map<Integer,String> map = CommonAjaxHandler.getInstance().getRoomTypeByHostel(hostelId);
				request.setAttribute("roomTypeMap", map);
				return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
			}
			else
			{
				if(errors.isEmpty())
				{
					int curHostelId = Integer.parseInt(hostelFeesForm.getHostelId());
					int curRoomTypeId = Integer.parseInt(hostelFeesForm.getRoomType());
					
					int prevHostelId = Integer.parseInt(hostelFeesForm.getOldHostelId());
					int prevRoomTypeId =  Integer.parseInt(hostelFeesForm.getOldRoomTypeId());
					boolean isUpdated;
					if(curHostelId!=prevHostelId || curRoomTypeId!=prevRoomTypeId)
					{
						List<HostelFeesTo> hostelFeesTo = HostelFeeHandler.getInstance().getFeeDetailedListToView(curHostelId, curRoomTypeId);
						if(hostelFeesTo.size() != 0)
						{
							errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_FEES_HOSTELWITHROOMTYPE_EXIST));
							saveErrors(request, errors);
							setFeeDetailListToForm(request, hostelFeesForm);
							setRoomTypeMapToForm(request, hostelFeesForm);
							//getFeeDetailedListToView(request, hostelFeesForm);
						}
						else
						{
							
							if(!errors.isEmpty())
							{
								
								request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
								setRequestedDataToForm(request, hostelFeesForm);
								return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
							} 
							else
							{
								checkFeeNull(hostelFeesForm, request, errors);
								//validateFeeAmount(hostelFeesForm,errors);
								if(errors.isEmpty())
								{
								List<HostelFeesTypeTo> listWithAmount = hostelFeesForm.getFeeList();
								isUpdated = HostelFeeHandler.getInstance().updateFeeDetails(hostelFeesForm,listWithAmount);
								if(isUpdated)
								{
									ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_UPDATE_SUCCESS);
									messages.add("messages", message);
									saveMessages(request, messages);
									setRequestedDataToForm(request, hostelFeesForm);
									//setRoomTypeMapToForm(request, hostelFeesForm);
									hostelFeesForm.clearMyFields();
								}
								else 
								{
									ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_UPDATE_FAILED);
									messages.add("messages", message);
									saveMessages(request, messages);
									setRequestedDataToForm(request, hostelFeesForm);
								}
								}else
								{
									request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
									setRequestedDataToForm(request, hostelFeesForm);
									return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
								}
							}
						}
						request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					}
					else
					{
						checkFeeNull(hostelFeesForm, request, errors);
						//validateFeeAmount(hostelFeesForm,errors);
						if(errors.isEmpty())
						{
						List<HostelFeesTypeTo> listWithAmount = hostelFeesForm.getFeeList();
						isUpdated = HostelFeeHandler.getInstance().updateFeeDetails(hostelFeesForm,listWithAmount);
						if(isUpdated)
						{
							ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_UPDATE_SUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							setRequestedDataToForm(request, hostelFeesForm);
							hostelFeesForm.clearMyFields();
						}
						else 
						{
							if(hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty()){
								setRoomTypeMapToForm(request, hostelFeesForm);
							}
							ActionMessage message = new ActionMessage(CMSConstants.HOSTEL_FEES_UPDATE_FAILED);
							messages.add("messages", message);
							saveMessages(request, messages);
						}
						}else
						{
							if(hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty()){
								setRoomTypeMapToForm(request, hostelFeesForm);
							}
							request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
							return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
						}
					}
					
				}
				else
				{
					if(hostelFeesForm.getHostelId()!=null && !hostelFeesForm.getHostelId().isEmpty()){
						setRoomTypeMapToForm(request, hostelFeesForm);
					}
					saveErrors(request, errors);
					request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
					
				}
			}
		}catch(Exception e)
		{
			String msg = super.handleApplicationException(e);
			hostelFeesForm.setErrorMessage(msg);
			hostelFeesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		return mapping.findForward(CMSConstants.INIT_HOSTEL_FEES);
	}
	
	public void checkFeeNull(HostelFeesForm hostelFeesForm,HttpServletRequest request,ActionErrors errors)throws Exception
	{
		String isUpdated = "false";
		List<HostelFeesTypeTo> listWithAmount = hostelFeesForm.getFeeList();
		if(listWithAmount != null && !listWithAmount.isEmpty())
		{
			isUpdated = HostelFeeHandler.getInstance().checkForAmountFeildsAreNull(listWithAmount);
			if(isUpdated.equalsIgnoreCase("empty"))
			{
				setRoomTypeMapToForm(request, hostelFeesForm);
				errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.HOSTEL_FEES_AtLEASTONE_REQUIRED));
				saveErrors(request, errors);
			}
		}
	}
	
	public void setFeeDetailListToForm(HttpServletRequest request,HostelFeesForm hostelFeesForm) throws Exception{
		List<HostelTO> hostelList = HostelAllocationHandler.getInstance().getHostelDetails();
		hostelFeesForm.setHostelList(hostelList);
		
		List<HostelFeesTo> feeDetailedList = HostelFeeHandler.getInstance().getFeeDetailedListToDisplay();
		hostelFeesForm.setFeeAllDetails(feeDetailedList);
	}
	
public void validateFeeAmount(HostelFeesForm hostelFeesForm,ActionErrors errors)throws Exception{
		
		
		List<HostelFeesTypeTo> listWithAmount = hostelFeesForm.getFeeList();
		for (HostelFeesTypeTo hostelFeesTypeTo : listWithAmount) {
			if(hostelFeesTypeTo.getAmount()!=null && !hostelFeesTypeTo.getAmount().isEmpty()){
				if(!CommonUtil.isValidDecimal(hostelFeesTypeTo.getAmount()))
				{
					errors.add("errors",new ActionError(CMSConstants.HOSTEL_FEES_AMOUNT_NOTVALID));
				}
			}
		}
	}
}
