package com.kp.cms.actions.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.imageio.ImageIO;
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
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.NewsEventsDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.handlers.admin.NewsEventsEntryHandler;
import com.kp.cms.handlers.employee.EmpResPubDetailsHandler;
import com.kp.cms.helpers.admin.NewsEventsDetailsHelper;
import com.kp.cms.to.admin.NewEventsResourseTO;
import com.kp.cms.to.admin.NewsEventsContactDetailsTO;
import com.kp.cms.to.admin.NewsEventsDetailsTO;
import com.kp.cms.to.admin.NewsEventsParticipantTO;
import com.kp.cms.to.admin.NewsEventsPhotosTO;
import com.kp.cms.transactions.admin.INewsEventsDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.NewsEventsDetailsTransactionImpl;

public class NewsEventsEntryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(NewsEventsEntryAction.class);
	private static final String MESSAGE_KEY = "messages";
	private static final String PHOTOBYTES = "PhotoBytes";
	
	INewsEventsDetailsTransaction iTransaction = NewsEventsDetailsTransactionImpl.getInstance();
	/**
	 * This method performs the retrieving of News details in MobNewsEventsDetailsAction class.
	 * @param mapping  - The ActionMapping used to select this instance
	 * @param form - The optional ActionForm bean for this request (if any)
	 * @param request - The HTTP request we are processing
	 * @param response - The HTTP response we are creating
	 * @return - The forward to which control should be transferred.
	 * @throws - Exception if an exception occurs
	 */
	//News and Events Entry Screen 
	public ActionForward initMobNewsEventsDetailsEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		NewsEventsEntryForm newsEventsEntryForm =(NewsEventsEntryForm)form;
		HttpSession session=request.getSession();
		session.setAttribute("field","Mobile  NewsEvents Datails");
		try 
		{
			/*Date d=new Date();
			d=new java.sql.Date(d.getTime());*/
			setUserId(request, newsEventsEntryForm);
			Employee emp=iTransaction.getEmployeeIdFromUserId(newsEventsEntryForm);
			if(emp!=null){
				newsEventsEntryForm.setUserEmailId(emp.getWorkEmail());
				newsEventsEntryForm.setUserName(emp.getFirstName());
			}
			cleanupEditSessionData(request);
			newsEventsEntryForm.setScreen("Entry");
			newsEventsEntryForm.reset(mapping, request);
			newsEventsEntryForm.resetTemp(mapping, request);
			newsEventsEntryForm.setPrePostEventAdm(null);
			setDataToForm(newsEventsEntryForm);
			List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
			newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			newsEventsEntryForm.setErrorMessage(msgKey);
			newsEventsEntryForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			newsEventsEntryForm.setErrorMessage(msg);
			newsEventsEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		log.info("end of initMobNewsEventsDetailsEntry in MobNewsEventsDetailsAction class. ");
		return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
	}
	private void cleanupEditSessionData(HttpServletRequest request) {
		log.info("enter cleanupEditSessionData...");
		HttpSession session = request.getSession(false);
		if (session == null) {
			return;
		} else {
			if (session.getAttribute(NewsEventsEntryAction.PHOTOBYTES) != null)
				session.removeAttribute(NewsEventsEntryAction.PHOTOBYTES);
		}
	}
	/**
	 * @param newsEventsEntryForm
	 * @throws Exception
	 */
	public void setDataToForm(NewsEventsEntryForm newsEventsEntryForm)throws Exception {
		 Date d=new Date();
		 newsEventsEntryForm.setTodaysDate(new java.sql.Date(d.getTime()));

		 Map<Integer, String> categoryList=NewsEventsEntryHandler.getInstance().getCategory();
		 newsEventsEntryForm.setCategoryList(categoryList);
		 
		 initializeResourse(newsEventsEntryForm);
		 initializeContactDetails(newsEventsEntryForm);
		 initializePhoto(newsEventsEntryForm);
		 initializeParticipants(newsEventsEntryForm);
		 
		 Map<String,String> streamMap=iTransaction.getStreamMap();
		 if(streamMap!=null){
			 newsEventsEntryForm.setStreamMap(streamMap);
		 }
		 
		 Map<String,String> departmentMap=iTransaction.getDepartmentMap();
		 if(departmentMap!=null)
			 newsEventsEntryForm.setDepartmentMap(departmentMap);
		 
		 Map<String,String> courseMap=iTransaction.getCourseMap();
		 if(courseMap!=null)
			 newsEventsEntryForm.setCourseMap1(courseMap);
		 
		 Map<String,String> splCenterMap=iTransaction.getSplCenterMap();
		 if(splCenterMap!=null)
			 newsEventsEntryForm.setSplCenterMap(splCenterMap);
		 newsEventsEntryForm.setIsRegistrationRequired("No");
		 newsEventsEntryForm.setIsLiveTelecast("No");
		 newsEventsEntryForm.setIsInvitationMailRequired("No");
	}
	public void setData(NewsEventsEntryForm newsEventsEntryForm)throws Exception {
		 Map<Integer, String> categoryList=NewsEventsEntryHandler.getInstance().getCategory();
		 newsEventsEntryForm.setCategoryList(categoryList);
		 Map<String,String> streamMap=iTransaction.getStreamMap();
		 if(streamMap!=null){
			 newsEventsEntryForm.setStreamMap(streamMap);
		 }
		 
		 Map<String,String> departmentMap=iTransaction.getDepartmentMap();
		 if(departmentMap!=null)
			 newsEventsEntryForm.setDepartmentMap(departmentMap);
		 
		 Map<String,String> courseMap=iTransaction.getCourseMap();
		 if(courseMap!=null)
			 newsEventsEntryForm.setCourseMap1(courseMap);
		 
		 Map<String,String> splCenterMap=iTransaction.getSplCenterMap();
		 if(splCenterMap!=null)
			 newsEventsEntryForm.setSplCenterMap(splCenterMap);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addMobNewsEventsDetails(ActionMapping mapping,ActionForm form, HttpServletRequest request
			,HttpServletResponse response)throws Exception
		{
		log.info("Call to add newsEventdeitals in mobnewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form; 
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = newsEventsEntryForm.validate(mapping, request);
		boolean isMobNewsEventsDetailsAdded = false;
		String eventTitle="";
		try
		{
			if(errors.isEmpty())
			{
				setUserId(request, newsEventsEntryForm);
				if(newsEventsEntryForm.getEventTitle()!=null && !newsEventsEntryForm.getEventTitle().isEmpty())
				{
					eventTitle=newsEventsEntryForm.getEventTitle().trim();
				}else {
					return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
				}
				NewsEventsDetails mobNewsEventsDetails=NewsEventsEntryHandler.getInstance().isMobNewsEventDetailsExist(eventTitle,0);

				if(mobNewsEventsDetails!=null && mobNewsEventsDetails.getIsActive()){
					errors.add(CMSConstants.MobNews_Events_Details_EXIST, new ActionError(CMSConstants.MobNews_Events_Details_EXIST));
					saveErrors(request, errors);
				}
				else if(mobNewsEventsDetails!=null && !mobNewsEventsDetails.getIsActive()){
					errors.add(CMSConstants.MobNews_Events_Details_REACTIVATE, new ActionError(CMSConstants.MobNews_Category_REACTIVATE));
					saveErrors(request, errors);			
				}else{
					isMobNewsEventsDetailsAdded = NewsEventsEntryHandler.getInstance().addMobNewsEventsDetails(newsEventsEntryForm,request);
					if (isMobNewsEventsDetailsAdded) {
						NewsEventsEntryHandler.getInstance().sendMailToPublication(newsEventsEntryForm);
						ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_ADD_SUCCESS);// Adding success message.
						messages.add("messages", message);
						saveMessages(request, messages);
						initializePhoto(newsEventsEntryForm);
						newsEventsEntryForm.reset(mapping, request);	
					}else{
						errors.add(CMSConstants.MobNews_Events_Details_ADD_FAILURE, new ActionError(CMSConstants.MobNews_Events_Details_ADD_FAILURE));// Adding failure message
					}
				}
			} else {
				saveErrors(request, errors);
				NewsEventsDetailsHelper.getInstance().addPhotosToFileSystem(newsEventsEntryForm, request);
				return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
			}

		}catch (BusinessException businessException) {
			log.info("Exception addMobNewsEventsDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsEntryForm.setErrorMessage(msg);
			newsEventsEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//getting all news Details details.

		newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		//session.setAttribute("categoryList", categoryList);	
		newsEventsEntryForm.setCategoryList(categoryList);
		log.info("end of addMobNewsEventsDetails in mobnewsEventsDetailsAction class. ");

		return mapping.findForward(CMSConstants.NEWS_EVENTS_SUBMIT_CONFIRM);
		}
	//News Events Entry Edit Part
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editMobNewsEventsDetails(ActionMapping mapping, ActionForm form,HttpServletRequest request
			,HttpServletResponse response)throws Exception{

		log.info("call of editMobNewsEventsDetails method in mobnewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=newsEventsEntryForm.validate(mapping, request);
		setUserId(request, newsEventsEntryForm);
		setDataToForm(newsEventsEntryForm);
		try
		{
			if(errors.isEmpty()){
				//id error is empty
				newsEventsEntryForm =(NewsEventsEntryForm) NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsEntryForm,Integer.parseInt(newsEventsEntryForm.getSelectedNewsEventsId()));
				request.setAttribute("mobNewsEventCategoryOperation", "edit");
				HttpSession session=request.getSession(false);
				if(session == null){
					return mapping.findForward(CMSConstants.LOGIN_PAGE);
				}else{
					session.setAttribute("Event Title",newsEventsEntryForm.getEventTitle());
				}
			}
			else{
				//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
		}catch (BusinessException businessException) {
			log.info("Exception addMobNewsEventsDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsEntryForm.setErrorMessage(msg);
			newsEventsEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		// newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		//session.setAttribute("categoryList", categoryList);	
		newsEventsEntryForm.setCategoryList(categoryList);
		log.info("end of addMobNewsEventsDetails in mobnewsEventsDetailsAction class. ");
		if(newsEventsEntryForm.getScreen() != null && newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}else
		{
		return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
		}
	}
	
	/**
	 * This method performs the updating of one record of updateMobNewsEventsDetails details in mobnewsEventsDetailsAction.
	 * @param mapping  The ActionMapping used to select this instance
	 * @param form     The optional ActionForm bean for this request (if any)
	 * @param request  The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	public ActionForward updateMobNewsEventsDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("call of updateMobNewsEventsDetails method in mobnewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = newsEventsEntryForm.validate(mapping, request);
		try
		{
			if(isCancelled(request)){
				newsEventsEntryForm=NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsEntryForm,Integer.parseInt(newsEventsEntryForm.getSelectedNewsEventsId()));
				HttpSession session=request.getSession(false);
				session.setAttribute("Event Title",newsEventsEntryForm.getEventTitle());
				List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
				newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
				Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
				newsEventsEntryForm.setCategoryList(categoryList);
				return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);	
			}
			if(errors.isEmpty()){
				setUserId(request, newsEventsEntryForm);
				HttpSession session=request.getSession(false);
			if(!newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
									
				String[] selectedviewFor = request.getParameterValues("selectedviewFor");
				String prg_str = null;
				if(selectedviewFor!=null && selectedviewFor.length>0){
				for (int x = 0; x < selectedviewFor.length; x++) {
					if(prg_str==null){
						prg_str = selectedviewFor[x] + ",";	
					}else
					{
					prg_str = prg_str + selectedviewFor[x] + ",";
					}
				}
				prg_str = prg_str.substring(0, prg_str.length() - 1);
				}
				newsEventsEntryForm.setViewFor(prg_str);
			}
				if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
				newsEventsEntryForm.setIsPostDeptEntry("true");
				}
				//	eventTitle=newsEventsEntryForm.getEventTitle().trim();
				/*NewsEventsDetails mobNewsEventsDetails = NewsEventsEntryHandler.getInstance().isMobNewsEventDetailsExist(eventTitle, 0);

					if(mobNewsEventsDetails!=null && mobNewsEventsDetails.getIsActive() && mobNewsEventsDetails.getEventTitle().equalsIgnoreCase(eventTitle)){

						errors.add(CMSConstants.MobNews_Events_Details_EXIST, new ActionError(CMSConstants.MobNews_Events_Details_EXIST));

						request.setAttribute("mobNewsEventCategoryOperation","edit");
						saveErrors(request, errors);

					}else if(mobNewsEventsDetails!=null && !mobNewsEventsDetails.getIsActive()){
						errors.add(CMSConstants.MobNews_Events_Details_REACTIVATE, new ActionError(CMSConstants.MobNews_Events_Details_REACTIVATE));
						saveErrors(request, errors);			
						request.setAttribute("mobNewsEventCategoryOperation","edit");
					}
					else{*/
					request.setAttribute("mobNewsEventCategoryOperation","edit");
						boolean isUpdated =NewsEventsEntryHandler.getInstance().updateMobNewsEventsDetails(newsEventsEntryForm,request);
						if(isUpdated){
							//if update is success.
							session.removeAttribute("eventTitle");
							ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_SUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							newsEventsEntryForm.reset(mapping, request);
							initializePhoto(newsEventsEntryForm);
						}
						if(!isUpdated){
							//if update is failure.
							ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_FAILURE);
							messages.add("messages", message);
							saveMessages(request, messages);
							newsEventsEntryForm.reset(mapping, request);
						}
					//}
				
			}else{
				newsEventsEntryForm.setPhotoListSize(newsEventsEntryForm.getOrgphotoListSize());
			}
		} catch (Exception exception) {	
			//String msg = super.handleApplicationException(exception);

			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		newsEventsEntryForm.setCategoryList(categoryList);
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SUBMIT_CONFIRM);
	}

	/**
	 * This method performs the deleting one record of newsEventsDetails details in mobnewsEventsDetailsAction class.
	 * @param mapping  The ActionMapping used to select this instance
	 * @param form     The optional ActionForm bean for this request (if any)
	 * @param request  The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */

	public ActionForward deleteMobNewsEventsDetails(ActionMapping mapping,ActionForm form
			,HttpServletRequest request,HttpServletResponse response)throws Exception{
				
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors=newsEventsEntryForm.validate(mapping, request);
	    try
	    {
	    	if(errors.isEmpty())
	    	{
	    		setUserId(request, newsEventsEntryForm);
	    		boolean isDelete=NewsEventsEntryHandler.getInstance().deleteMobNewsEventsDetails(Integer.parseInt(newsEventsEntryForm.getSelectedNewsEventsId()),newsEventsEntryForm.getUserId());
	    		if(isDelete){
					//if delete is success.
					ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_DELETE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsEntryForm.reset(mapping, request);
				}if(!isDelete){
					//if delete is failure.
					ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_DELETE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsEntryForm.reset(mapping, request);
				}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception MobNewsEventsCategory");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsEntryForm.setErrorMessage(msg);
			newsEventsEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		newsEventsEntryForm.setCategoryList(categoryList);
		log.info("end of deteteMobNewsDetails method in MobNewsEventsDetailsAction class.");
		return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
		
	}

/*
reactive method
	of Mobile News Event 
	*/
	public ActionForward reActivateMobNewsEventsDetails(ActionMapping mapping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception{
		log.info("call of reActivateMobNewsEventsDetails method in MobNewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
	    ActionMessages messages = new ActionMessages();
	    ActionErrors errors=newsEventsEntryForm.validate(mapping, request);
	    try
	    {
	    	if(errors.isEmpty())
	    	{
	    		setUserId(request, newsEventsEntryForm);
	    		boolean isActivated = NewsEventsEntryHandler.getInstance().reActivateMobNewsEventsDetails(newsEventsEntryForm.getDupId(),newsEventsEntryForm.getUserId());
	    		if(isActivated){
	    			//if reactivation is success.
		    		ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_REACTIVATE_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsEntryForm.reset(mapping, request);
	    		}else{
	    			//if reactivation is failure.
	    			ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_REACTIVATE_FAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					newsEventsEntryForm.reset(mapping, request);
	    		}
	    	}else{
	    		//errors are present
				errors.add(messages);
				saveErrors(request, errors);
			}
	    }catch (BusinessException businessException) {
			log.info("Exception reActivateMobNewsEventsDetails");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			newsEventsEntryForm.setErrorMessage(msg);
			newsEventsEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		newsEventsEntryForm.setCategoryList(categoryList);
		
		log.info("end of reActivateMobNewsEventsDetails method in MobNewsEventsDetailsAction class.");
		return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
	}
	
private void initializeResourse(NewsEventsEntryForm newsEventsEntryForm) {
		List<NewEventsResourseTO> list=new ArrayList<NewEventsResourseTO>();
		NewEventsResourseTO resTo=new NewEventsResourseTO();
		resTo.setResourseName("");
		resTo.setEmail("");
		resTo.setOtherInfo("");
		resTo.setContactNo("");
		list.add(resTo);
		newsEventsEntryForm.setResourseTO(list);
		newsEventsEntryForm.setResourseListSize(String.valueOf(list.size()));
		newsEventsEntryForm.setOrgResListSize(String.valueOf(list.size()));
		for (int i = list.size(); i < 10; i++) {
			NewEventsResourseTO resTo1=new NewEventsResourseTO();
			list.add(resTo1);
		}
		
}		
	

private void initializeContactDetails(NewsEventsEntryForm newsEventsEntryForm) {
	List<NewsEventsContactDetailsTO> list=new ArrayList<NewsEventsContactDetailsTO>();
	NewsEventsContactDetailsTO ctcTo=new NewsEventsContactDetailsTO();
	ctcTo.setName("");
	ctcTo.setEmail("");
	ctcTo.setRemarks("");
	ctcTo.setContactNo("");
	list.add(ctcTo);
	newsEventsEntryForm.setContactTO(list);
	newsEventsEntryForm.setContactListSize(String.valueOf(list.size()));
	newsEventsEntryForm.setOrgContactListSize(String.valueOf(list.size()));
	for (int i = list.size(); i < 10; i++) {
		NewsEventsContactDetailsTO resTo1=new NewsEventsContactDetailsTO();
		list.add(resTo1);
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
public ActionForward resetResourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	NewsEventsEntryForm newsEventsEntryForm=(	NewsEventsEntryForm)form;
	if(newsEventsEntryForm.getResourseTO()!=null)
	if(newsEventsEntryForm.getMode()!=null){
		if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			List<NewEventsResourseTO> list=newsEventsEntryForm.getResourseTO();
			NewEventsResourseTO resTo=new NewEventsResourseTO();
			resTo.setResourseName("");
			resTo.setEmail("");
			resTo.setOtherInfo("");
			resTo.setContactNo("");
			newsEventsEntryForm.setResourseListSize(String.valueOf(list.size()));
			list.add(resTo);
			newsEventsEntryForm.setMode(null);
			//String size=String.valueOf(list.size()-1);
			newsEventsEntryForm.setFocusValue("Resourse");
		}
	}
	setData(newsEventsEntryForm);
	if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
		if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
		{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
		}
		else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
		{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
		}
		else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}
	}

	return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
	
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward removeResourse(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
	log.info("Befinning of removeResourse of NewsEvents");
	NewsEventsEntryForm newsEventsEntryForm=(	NewsEventsEntryForm)form;
	List<NewEventsResourseTO> list=null;
	if(newsEventsEntryForm.getResourseTO()!=null)
	if(newsEventsEntryForm.getMode()!=null){
		if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
			// add one blank to add extra one
			list=newsEventsEntryForm.getResourseTO();
			if(list.size()>0)
			list.remove(list.size()-1);
			newsEventsEntryForm.setResourseListSize(String.valueOf(list.size()-1));
			newsEventsEntryForm.setFocusValue("Resourse");
		}
	}
	setData(newsEventsEntryForm);
	log.info("End of removeResourse of NewsEvents");
	if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
		if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
		{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
		}
		else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
		{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
		}
		else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}
	}

    return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}
	
private void initializeParticipants(NewsEventsEntryForm newsEventsEntryForm) {
	List<NewsEventsParticipantTO> list=new ArrayList<NewsEventsParticipantTO>();
	NewsEventsParticipantTO partto=new NewsEventsParticipantTO();
	partto.setNoOfPeople("");
	partto.setRemarks("");
	partto.setInstitutionName("");
	list.add(partto);
	newsEventsEntryForm.setPartcipantsTO(list);
	newsEventsEntryForm.setParticipantsListSize(String.valueOf(list.size()));
	newsEventsEntryForm.setOrgPartListSize(String.valueOf(list.size()));
	for (int i = list.size(); i < 10; i++) {
		NewsEventsParticipantTO partto1=new NewsEventsParticipantTO();
		list.add(partto1);
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
public ActionForward resetParticipants(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
if(newsEventsEntryForm.getPartcipantsTO()!=null)
if(newsEventsEntryForm.getMode()!=null){
	if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		List<NewsEventsParticipantTO> list=newsEventsEntryForm.getPartcipantsTO();
		NewsEventsParticipantTO partto=new NewsEventsParticipantTO();
		partto.setNoOfPeople("");
		partto.setRemarks("");
		partto.setInstitutionName("");
		newsEventsEntryForm.setParticipantsListSize(String.valueOf(list.size()));
		list.add(partto);
		newsEventsEntryForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		newsEventsEntryForm.setFocusValue("Participants");
	}
}
setData(newsEventsEntryForm);
if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
	if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
		return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
	}
}

return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}
/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward removeParticipants(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
log.info("Befinning of removeResourse of NewsEvents");
NewsEventsEntryForm newsEventsEntryForm=(	NewsEventsEntryForm)form;
List<NewsEventsParticipantTO> list=null;
if(newsEventsEntryForm.getPartcipantsTO()!=null)
if(newsEventsEntryForm.getMode()!=null){
	if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		list=newsEventsEntryForm.getPartcipantsTO();
		if(list.size()>0)
		list.remove(list.size()-1);
		newsEventsEntryForm.setParticipantsListSize(String.valueOf(list.size()-1));
		newsEventsEntryForm.setFocusValue("Participants");
	}
}
setData(newsEventsEntryForm);
if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
	if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
		return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
	}
}

return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}

private void initializePhoto(NewsEventsEntryForm newsEventsEntryForm) {
	List<NewsEventsPhotosTO> list=new ArrayList<NewsEventsPhotosTO>();
	NewsEventsPhotosTO resTo=new NewsEventsPhotosTO();
	resTo.setPhotoName("");
	list.add(resTo);
	newsEventsEntryForm.setPhotosTO(list);
	newsEventsEntryForm.setPhotoListSize(String.valueOf(list.size()));
	newsEventsEntryForm.setOrgphotoListSize(String.valueOf(list.size()));
	for (int i = list.size(); i < 10; i++) {
		NewsEventsPhotosTO resTo1=new NewsEventsPhotosTO();
		list.add(resTo1);
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
public ActionForward resetPhoto(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
if(newsEventsEntryForm.getPhotosTO()!=null)
if(newsEventsEntryForm.getMode()!=null){
	if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		List<NewsEventsPhotosTO> list=newsEventsEntryForm.getPhotosTO();
		NewsEventsPhotosTO resTo=new NewsEventsPhotosTO();
		resTo.setPhotoName("");
		newsEventsEntryForm.setPhotoListSize(String.valueOf(list.size()));
		newsEventsEntryForm.setOrgphotoListSize(String.valueOf(list.size()));
		list.add(resTo);
		newsEventsEntryForm.setMode(null);
		//String size=String.valueOf(list.size()-1);
		newsEventsEntryForm.setFocusValue("Photo");
	}
}
setData(newsEventsEntryForm);
if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
	if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
		return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
	}
}
return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}
/**
* @param mapping
* @param form
* @param request
* @param response
* @return
* @throws Exception
*/
public ActionForward removePhoto(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
log.info("Befinning of removeResourse of NewsEvents");
NewsEventsEntryForm newsEventsEntryForm=(	NewsEventsEntryForm)form;
List<NewsEventsPhotosTO> list=null;
if(newsEventsEntryForm.getPhotosTO()!=null)
if(newsEventsEntryForm.getMode()!=null){
	if (newsEventsEntryForm.getMode().equalsIgnoreCase("ExpAddMore")) {
		// add one blank to add extra one
		list=newsEventsEntryForm.getPhotosTO();
		if(list.size()>0)
		list.remove(list.size()-1);
		newsEventsEntryForm.setPhotoListSize(String.valueOf(list.size()-1));
		newsEventsEntryForm.setOrgphotoListSize(String.valueOf(list.size()-1));
		newsEventsEntryForm.setFocusValue("Photo");
	}
}
setData(newsEventsEntryForm);
if(newsEventsEntryForm.getScreen()!=null && !newsEventsEntryForm.getScreen().isEmpty()){
	if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre"))
	{
		return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
	}
	else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
		return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
	}
}
return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}


// Photo view and Delete Code


public ActionForward ViewPhoto(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
	NewsEventsEntryHandler.getInstance().getSearchedPhoto(newsEventsForm);
	return mapping.findForward(CMSConstants.MobNews_Events_Details_Entry);
}


//Pre Approval Code Starts Here-------------------------------------------------------------------------
		

		/*public ActionForward initNewsEventsSearch(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
			cleanupEditSessionData(request);
			newsEventsForm.reset(mapping, request);
			newsEventsForm.resetTemp(mapping, request);
			newsEventsForm.setPrePostEventAdm(null);
			newsEventsForm.setScreen("Pre");
			setDataToForm(newsEventsForm);
			return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_APPROVAL);
		}*/









		public ActionForward initNewsEventsSearch(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
			cleanupEditSessionData(request);
			newsEventsForm.setPrePostEventAdm(null);
			newsEventsForm.setScreen("Pre");
			Date d=new Date();
			newsEventsForm.setTodaysDate(new java.sql.Date(d.getTime()));
			setUserId(request, newsEventsForm);

			ActionMessages errors = newsEventsForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_APPROVAL);
				}
				List<NewsEventsDetailsTO> newsToList = NewsEventsEntryHandler.getInstance().getSearchedNews(newsEventsForm);
				if (newsToList.isEmpty()) {
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					newsEventsForm.setNewsList(newsToList);
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_APPROVAL);
				}	
				newsEventsForm.setNewsList(newsToList);
			} catch (ApplicationException e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_APPROVAL);
		
			} catch (Exception e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_APPROVAL);
			}
			log.info("exit getSearchedStudents..");
			return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_APPROVAL);
		}
		
		public ActionForward loadPreApproval(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception 
		{	
		log.info("call of editMobNewsEventsDetails method in mobnewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsForm=(NewsEventsEntryForm)form;
		cleanupEditSessionData(request);
		ActionMessages messages=new ActionMessages();
		try
		{
		boolean flag=false;
		if( StringUtils.isNotEmpty(newsEventsForm.getSelectedNewsEventsId()));
		{
		newsEventsForm.reset(mapping, request);
		setDataToForm(newsEventsForm);
		setUserId(request,newsEventsForm);
		flag=NewsEventsEntryHandler.getInstance().getNewEventsDetails(newsEventsForm);
		}
		if(flag){
			if(newsEventsForm.getScreen().equalsIgnoreCase("Post"))
				return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
			else
				return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
		}
		else
		{
			messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.EMPLOYEE_NOT_VALID));
			saveMessages(request, messages);
			setDataToForm(newsEventsForm);
			return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_APPROVAL);
		}
	}catch (Exception exception) {
			if (exception instanceof ApplicationException) {
				String msg = super.handleApplicationException(exception);
				newsEventsForm.setErrorMessage(msg);
				newsEventsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else
			throw exception;
	  	}
	  }
		public ActionForward updatePrePost(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
		{
			NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = newsEventsEntryForm.validate(mapping, request);
			try
			{
				if(errors.isEmpty()){
					setUserId(request, newsEventsEntryForm);
					HttpSession session=request.getSession(false);
					String[] selectedviewFor = request.getParameterValues("selectedviewFor");
					String prg_str = null;
					if(selectedviewFor!=null && selectedviewFor.length>0){
					for (int x = 0; x < selectedviewFor.length; x++) {
						if(prg_str==null){
							prg_str = selectedviewFor[x] + ",";	
						}else
						{
						prg_str = prg_str + selectedviewFor[x] + ",";
						}
					}
					prg_str = prg_str.substring(0, prg_str.length() - 1);
					}
					newsEventsEntryForm.setViewFor(prg_str);
					boolean isUpdated =NewsEventsEntryHandler.getInstance().updatePrePost(newsEventsEntryForm,request);
					if(isUpdated){
						NewsEventsEntryHandler.getInstance().sendMailToAdmin(newsEventsEntryForm);
						NewsEventsEntryHandler.getInstance().sendMailToUser(newsEventsEntryForm);
								//if update is success.
								session.removeAttribute("eventTitle");
								ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_SUCCESS);
								messages.add("messages", message);
								saveMessages(request, messages);
								newsEventsEntryForm.reset(mapping, request);
							}
							if(!isUpdated){
								//if update is failure.
								ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_FAILURE);
								messages.add("messages", message);
								saveMessages(request, messages);
								newsEventsEntryForm.reset(mapping, request);
							}
						//}
					}else{
						NewsEventsDetailsHelper.getInstance().addPhotosToFileSystem(newsEventsEntryForm,request);
						//errors are present
						errors.add(messages);
						request.setAttribute("currencyOperation","edit");
						saveErrors(request, errors);
						if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post"))
							return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
						else
							return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
					}
				
			} catch (Exception exception) {	
				//String msg = super.handleApplicationException(exception);

				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			newsEventsEntryForm.reset(mapping, request);
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SUBMIT_CONFIRM);
		}

		
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward GetNewsOrganisedBy(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
			NewsEventsEntryForm newsEventsForm=(NewsEventsEntryForm)form;
			try{
				String Response="";
				if(newsEventsForm.getOrganizedBy()!=null && !newsEventsForm.getOrganizedBy().isEmpty()){
					String organisedBy=newsEventsForm.getOrganizedBy();
					if(organisedBy.equalsIgnoreCase("Course")){
						Response="Course";
						newsEventsForm.setIsCourse("true");
						request.setAttribute(CMSConstants.OPTION_MAP, Response);
					}else if(organisedBy.equalsIgnoreCase("Department")){
						Response="Department";
						newsEventsForm.setIsDepartment("true");
						request.setAttribute(CMSConstants.OPTION_MAP, Response);
					}else if(organisedBy.equalsIgnoreCase("Deanery")){
						Response="Deanery";
						newsEventsForm.setIsStream("true");
						request.setAttribute(CMSConstants.OPTION_MAP, Response);
					}else if(organisedBy.equalsIgnoreCase("Special Centers")){
						Response="Special Centers";
						newsEventsForm.setIsSplCentre("true");
						request.setAttribute(CMSConstants.OPTION_MAP, Response);
					}
				}
			}catch(Exception e){
				log.debug(e.getMessage());
			}
			return mapping.findForward("MobNews_Events_Details_Entry");
		}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPhotoFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		byte[] bytearray = null;
		try{
			String photoName = request.getParameter("photoName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadImage.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			bytearray=baos.toByteArray();
			request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (IOException e) {
			request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	
	public ActionForward getIconFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		try{
			String photoName = request.getParameter("iconName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadIcon.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] bytearray=baos.toByteArray();
			 request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	public ActionForward getInvitationFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		try{
			String photoName = request.getParameter("invitationName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadInvitation.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] bytearray=baos.toByteArray();
			 request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	public ActionForward getRegFormFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		try{
			String photoName = request.getParameter("regFormName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadRegForm.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] bytearray=baos.toByteArray();
			 request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	public ActionForward getMaterialPublishedFromFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		try{
			String photoName = request.getParameter("materialName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadMaterialPublished.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] bytearray=baos.toByteArray();
			 request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	public ActionForward getReportFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		try{
			String photoName = request.getParameter("reportName");
			Properties prop=new Properties();
			InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			String soursePath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadMaterialPublished.path");
			soursePath = soursePath+"/"+ photoName;
			File file = new File(soursePath);
			BufferedImage originalImage=ImageIO.read(file);
			ByteArrayOutputStream baos=new ByteArrayOutputStream();
			ImageIO.write(originalImage, "jpg", baos );
			byte[] bytearray=baos.toByteArray();
			 request.getSession().setAttribute("PhotoBytes", bytearray);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VIEW_PHOTO_NEWS_EVENTS);
	}
	
	//------------------Post Event Department Entry---------------------
	
	public ActionForward initPostDeptEntrySearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		cleanupEditSessionData(request);
		Date d=new Date();
		newsEventsForm.setTodaysDate(new java.sql.Date(d.getTime()));
		setUserId(request, newsEventsForm);
		newsEventsForm.setPrePostEventAdm(null);
		newsEventsForm.setScreen("PostEventDeptEntry");
		ActionMessages errors = newsEventsForm.validate(mapping, request);
		try {
			if (!errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
				}
				List<NewsEventsDetailsTO> newsToList = NewsEventsEntryHandler.getInstance().getPostEventDeptEntry(newsEventsForm);
				if (newsToList.isEmpty()) {
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					newsEventsForm.setNewsList(newsToList);
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
				}	
				newsEventsForm.setNewsList(newsToList);
			} catch (ApplicationException e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
		
			} catch (Exception e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
		
				return mapping
						.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
		
			}
			log.info("exit getSearchedStudents..");
			return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
	}
	
	public ActionForward getSearchNewsPostEvents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
	//	HttpSession session=request.getSession();
		cleanupEditSessionData(request);
		ActionMessages errors = newsEventsForm.validate(mapping, request);
	try {
		if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_POST_DEPARTMENT);
			}
			List<NewsEventsDetailsTO> newsToList = NewsEventsEntryHandler.getInstance().getSearchedNewsAdminPost(newsEventsForm);
			if (newsToList == null || newsToList.isEmpty()) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				newsEventsForm.setNewsList(newsToList);
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
			}	
			newsEventsForm.setNewsList(newsToList);
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_POST_DEPARTMENT);
	
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
	
			return mapping
					.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_POST_DEPARTMENT);
	
		}
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
	}
	
	///-----------Admin Screen Code----------------------------------------------------------------------------
	
	public ActionForward initAdminSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		cleanupEditSessionData(request);
		newsEventsForm.reset(mapping, request);
		newsEventsForm.resetTemp(mapping, request);
		setDataToForm(newsEventsForm);
		newsEventsForm.setPrePostEventAdm(null);
		newsEventsForm.setScreen("Admin");
		setUserId(request, newsEventsForm);
		return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_ADMIN);
	}
	
	public ActionForward initPostSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		cleanupEditSessionData(request);
		newsEventsForm.setPrePostEventAdm(null);
		newsEventsForm.setScreen("Post");
		Date d=new Date();
		newsEventsForm.setTodaysDate(new java.sql.Date(d.getTime()));
		setUserId(request, newsEventsForm);
		ActionMessages errors = newsEventsForm.validate(mapping, request);
		try {
			if (errors != null && !errors.isEmpty()) {
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
				}
				List<NewsEventsDetailsTO> newsToList = NewsEventsEntryHandler.getInstance().getSearchedPost(newsEventsForm);
				if (newsToList == null || newsToList.isEmpty()) {
					ActionMessages messages = new ActionMessages();
					ActionMessage message = null;
					newsEventsForm.setNewsList(newsToList);
					message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
					messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
					saveMessages(request, messages);
					return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
				}	
				newsEventsForm.setNewsList(newsToList);
			} catch (ApplicationException e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
		
			} catch (Exception e) {
				log.error("error in getSearchedStudents...", e);
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
		
				return mapping
						.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
		
			}
			log.info("exit getSearchedStudents..");
			return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
		
		
	}
	
	public ActionForward getSearchAdminNewsEvents(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		//HttpSession session=request.getSession();
		cleanupEditSessionData(request);
	    //newsEventsForm.reset(mapping, request);
		//setDataToForm(newsEventsForm);
		ActionMessages errors = newsEventsForm.validate(mapping, request);
	try {
		if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_ADMIN);
			}
			List<NewsEventsDetailsTO> newsToList = NewsEventsEntryHandler.getInstance().getSearchedNewsAdminPost(newsEventsForm);
			if (newsToList == null || newsToList.isEmpty()) {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				newsEventsForm.setNewsList(newsToList);
				message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
			}	
			newsEventsForm.setNewsList(newsToList);
		} catch (ApplicationException e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_ADMIN);
	
		} catch (Exception e) {
			log.error("error in getSearchedStudents...", e);
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
			message = new ActionMessage(
					CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
			messages.add(NewsEventsEntryAction.MESSAGE_KEY, message);
			saveMessages(request, messages);
	
			return mapping
					.findForward(CMSConstants.INIT_NEWS_EVENTS_SEARCH_ADMIN);
	
		}
		log.info("exit getSearchedStudents..");
		return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
	}
	
	public ActionForward loadAdminScreen (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception 
	{	
	log.info("call of editMobNewsEventsDetails method in mobnewsEventsDetailsAction class.");
	NewsEventsEntryForm newsEventsForm=(NewsEventsEntryForm)form;
	cleanupEditSessionData(request);
	ActionMessages messages=new ActionMessages();
	try
	{
	boolean flag=false;
	if( StringUtils.isNotEmpty(newsEventsForm.getSelectedNewsEventsId()));
	{
	newsEventsForm.reset(mapping, request);
	setDataToForm(newsEventsForm);
	newsEventsForm.setPhotosTO(null);
	setUserId(request,newsEventsForm);
	flag=NewsEventsEntryHandler.getInstance().getNewEventsDetailsAdmin(newsEventsForm);
	}
	if(flag){
		return mapping.findForward(CMSConstants.NEWS_EVENTS_ADMIN);
	}
	else
	{
		messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.FEE_DATA_NOT_FOUND));
		saveMessages(request, messages);
		setDataToForm(newsEventsForm);
			return mapping.findForward(CMSConstants.LIST_NEWS_EVENTS_SEARCH_ADMIN);
	}
}catch (Exception exception) {
		if (exception instanceof ApplicationException) {
			String msg = super.handleApplicationException(exception);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
	}else
		throw exception;
  	}
  }
	
	public ActionForward updateNewsEventsAdmin(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.info("call of updateMobNewsEventsDetails method in mobnewsEventsDetailsAction class.");
		NewsEventsEntryForm newsEventsEntryForm=(NewsEventsEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = newsEventsEntryForm.validate(mapping, request);
		String eventTitle="";
		try
		{
			if(errors.isEmpty()){
				setUserId(request, newsEventsEntryForm);
				HttpSession session=request.getSession(false);

				if(newsEventsEntryForm!=null)
				{
					String[] selectedviewFor = request.getParameterValues("selectedviewFor");
					StringBuilder prg_str = new StringBuilder();
					if(selectedviewFor!=null && selectedviewFor.length>0){
					for (int x = 0; x < selectedviewFor.length; x++) {
						prg_str.append(Integer.parseInt(selectedviewFor[x])).append(",");

					}
					prg_str.setCharAt(prg_str.length()-1, ' ');
					}
					newsEventsEntryForm.setViewFor(prg_str.toString().trim());
				
						boolean isUpdated =NewsEventsEntryHandler.getInstance().updateMobNewsEventsAdmin(newsEventsEntryForm,request);
						if(isUpdated){
							//if update is success.
							session.removeAttribute("eventTitle");
							ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_SUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
							newsEventsEntryForm.reset(mapping, request);
						}
						if(!isUpdated){
							//if update is failure.
							ActionMessage message = new ActionMessage(CMSConstants.MobNews_Events_Details_UPDATE_FAILURE);
							messages.add("messages", message);
							saveMessages(request, messages);
							newsEventsEntryForm.reset(mapping, request);
						}
				}else{
					//errors are present
					errors.add(messages);
					request.setAttribute("currencyOperation","edit");
					saveErrors(request, errors);
				}
			}
		} catch (Exception exception) {	
			//String msg = super.handleApplicationException(exception);

			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		newsEventsEntryForm.reset(mapping, request);
		List<NewsEventsDetailsTO> newsEventsDetailsList= NewsEventsEntryHandler.getInstance().getMobNewsEventsDetails(newsEventsEntryForm);
		newsEventsEntryForm.setMobNewsEventsDetails(newsEventsDetailsList);	
		Map<Integer, String> categoryList =NewsEventsEntryHandler.getInstance().getCategory();
		//session.setAttribute("categoryList", categoryList);	
		newsEventsEntryForm.setCategoryList(categoryList);
		return mapping.findForward(CMSConstants.NEWS_EVENTS_SUBMIT_CONFIRM);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		setUserId(request, newsEventsForm);
		try{
			boolean delete = false;
			if(newsEventsForm.getPhotoId() != 0)
				delete = NewsEventsEntryHandler.getInstance().deletePhoto(newsEventsForm.getPhotoId(),newsEventsForm.getUserId());
			if(delete){
				if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsForm,Integer.parseInt(newsEventsForm.getSelectedNewsEventsId()));
					request.setAttribute("mobNewsEventCategoryOperation", "edit");
				}else{
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().getNewEventsDetails(newsEventsForm);
					
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}else{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
		}
	}
	
	public ActionForward deleteResourse(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		setUserId(request, newsEventsForm);
		try{
			boolean delete = false;
			if(newsEventsForm.getPhotoId() != 0)
				delete = NewsEventsEntryHandler.getInstance().deleteResourse(Integer.parseInt(newsEventsForm.getResourseId()),newsEventsForm.getUserId());
			if(delete){
				if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsForm,Integer.parseInt(newsEventsForm.getSelectedNewsEventsId()));
					request.setAttribute("mobNewsEventCategoryOperation", "edit");
				}else{
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().getNewEventsDetails(newsEventsForm);
					
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}else{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
		}
	}
	
	public ActionForward deleteContact(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		setUserId(request, newsEventsForm);
		try{
			boolean delete = false;
			if(newsEventsForm.getPhotoId() != 0)
				delete = NewsEventsEntryHandler.getInstance().deleteContact(Integer.parseInt(newsEventsForm.getContactId()),newsEventsForm.getUserId());
			if(delete){
				if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsForm,Integer.parseInt(newsEventsForm.getSelectedNewsEventsId()));
					request.setAttribute("mobNewsEventCategoryOperation", "edit");
				}else{
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().getNewEventsDetails(newsEventsForm);
					
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}else{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
		}
	}
	
	public ActionForward deleteParticipants(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		setUserId(request, newsEventsForm);
		try{
			boolean delete = false;
			if(newsEventsForm.getPhotoId() != 0)
				delete = NewsEventsEntryHandler.getInstance().deleteParticipants(Integer.parseInt(newsEventsForm.getParticipantsId()),newsEventsForm.getUserId());
			if(delete){
				if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsForm,Integer.parseInt(newsEventsForm.getSelectedNewsEventsId()));
					request.setAttribute("mobNewsEventCategoryOperation", "edit");
				}else{
					setData(newsEventsForm);
					NewsEventsEntryHandler.getInstance().getNewEventsDetails(newsEventsForm);
					
				}
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		if(newsEventsForm.getScreen() != null && newsEventsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
			return mapping.findForward(CMSConstants.NEWS_EVENTS_SEARCH_POST_EVENT);
		}else{
			return mapping.findForward(CMSConstants.NEWS_EVENTS_POST_APPROVAL);
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
	public ActionForward deletePhotoPreApproval(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		NewsEventsEntryForm newsEventsForm =(NewsEventsEntryForm)form;
		setUserId(request, newsEventsForm);
		try{
			boolean delete = false;
			if(newsEventsForm.getPhotoId() != 0)
				delete = NewsEventsEntryHandler.getInstance().deletePhoto(newsEventsForm.getPhotoId(),newsEventsForm.getUserId());
			if(delete){
				NewsEventsEntryHandler.getInstance().editMobNewsEventDetails(newsEventsForm,Integer.parseInt(newsEventsForm.getSelectedNewsEventsId()));
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			newsEventsForm.setErrorMessage(msg);
			newsEventsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.NEWS_EVENTS_PRE_APPROVAL);
	}
}
