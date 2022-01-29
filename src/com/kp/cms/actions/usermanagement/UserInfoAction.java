package com.kp.cms.actions.usermanagement;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import javax.swing.ImageIcon;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.usermanagement.UserInfoForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.RenderYearList;
import org.apache.struts.actions.DownloadAction.StreamInfo;

@SuppressWarnings("deprecation")
public class UserInfoAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(UserInfoAction.class);

	/**
	 * Method to set the user details to the form to display in userInfo.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		UserInfoForm userInfoForm = (UserInfoForm) form;
		
		Map<Integer, String> department = UserInfoHandler.getInstance().getDepartment();
		userInfoForm.setDepartment(department);
		
		Map<Integer, String> designation = UserInfoHandler.getInstance().getDesignation();
		userInfoForm.setDesignation(designation);
		
		Map<Integer, String> roles = UserInfoHandler.getInstance().getRoles();
		userInfoForm.setRoles(roles);
		
		Map<Integer, String> country = CountryHandler.getInstance().getCountriesMap();
		userInfoForm.setCountry(country);
		
		resetFields(userInfoForm);
		userInfoForm.setSearchDepartment(null);
		userInfoForm.setFirstsearchName(null);
		userInfoForm.setMiddlesearchName(null);
		userInfoForm.setLastsearchName(null);
		userInfoForm.setSearchDob(null);
		userInfoForm.setId(0);
		userInfoForm.setEmplId(0);
		setUserId(request, userInfoForm);
		return mapping.findForward(CMSConstants.USER_INPUT);
	}
	
	/**
	 * Method to add user information
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public ActionForward addUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DuplicateException, Exception {
		
		UserInfoForm userInfoForm = (UserInfoForm) form;
		
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = userInfoForm.validate(mapping, request);
		errors = validatePhotoSize(userInfoForm, errors);
		if(userInfoForm.getEmpPhoto() != null && userInfoForm.getEmpPhoto().getFileName()!= null &&
				!userInfoForm.getEmpPhoto().getFileName().isEmpty() && userInfoForm.getEmpPhoto().getContentType()!= null
				&& !userInfoForm.getEmpPhoto().getContentType().isEmpty()){
			errors = validateImageHeightWidth(userInfoForm, errors,request);			
		}
		
		validatePhone(userInfoForm, errors);
		validateCommAddress(userInfoForm, errors);
		
		try {
			if (userInfoForm.getDateOfBirth() != null && !StringUtils.isEmpty(userInfoForm.getDateOfBirth())) {
				if (CommonUtil.isValidDate(userInfoForm.getDateOfBirth())) {
					boolean isValid = validatefutureDate(userInfoForm.getDateOfBirth());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
						}
					}
				}
			}
			
			if (userInfoForm.getDateOfJoining() != null && !StringUtils.isEmpty(userInfoForm.getDateOfJoining())) {
				if (CommonUtil.isValidDate(userInfoForm.getDateOfJoining())) {
					boolean isValid = validatefutureDate(userInfoForm.getDateOfJoining());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOJ_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOJ_LARGE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOJ_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOJ_LARGE));
						}
					}
				}
			}
			
			if (errors.isEmpty()) {
				
				setUserId(request, userInfoForm);
				
				if(UserInfoHandler.getInstance().addUserInfo(userInfoForm, "add")){
						
					ActionMessage message = new ActionMessage(CMSConstants.USER_INFO_ADDSUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					resetFields(userInfoForm);
					
					Map<Integer, String> department = UserInfoHandler.getInstance().getDepartment();
					userInfoForm.setDepartment(department);
					
					Map<Integer, String> designation = UserInfoHandler.getInstance().getDesignation();
					userInfoForm.setDesignation(designation);
					
					Map<Integer, String> roles = UserInfoHandler.getInstance().getRoles();
					userInfoForm.setRoles(roles);
					
					Map<Integer, String> country = CountryHandler.getInstance().getCountriesMap();
					userInfoForm.setCountry(country);
				}
			} else {
				saveErrors(request, errors);
			}
		}catch(DuplicateException e1){
			errors.add("error", new ActionError(CMSConstants.USER_INFO_USEREXIST));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.USER_INPUT);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			userInfoForm.setErrorMessage(msg);
			userInfoForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit add User Info");
		request.setAttribute("userOperation", "add");
		return mapping.findForward(CMSConstants.USER_INPUT);
	}
	
	/**
	 * Method to reset the form fields
	 * @param userInfoForm
	 */
	public void resetFields(UserInfoForm userInfoForm) {
		
		userInfoForm.setFirstName(null);
		userInfoForm.setMiddleName(null);
		userInfoForm.setLastName(null);
		userInfoForm.setFatherName(null);
		userInfoForm.setUserName(null);
		userInfoForm.setPassword(null);
		userInfoForm.setEmailId(null);
		userInfoForm.setDateOfBirth(null);
		userInfoForm.setDateOfJoining(null);
		userInfoForm.setBloodGroup(null);
		userInfoForm.setPhone1(null);
		userInfoForm.setPhone2(null);
		userInfoForm.setPhone3(null);
		userInfoForm.setMobile1(null);
		userInfoForm.setMobile2(null);
		userInfoForm.setMobile3(null);
		userInfoForm.setDepartmentId(null);
		userInfoForm.setDesignationId(null);
		userInfoForm.setRoleId(null);
		userInfoForm.setPermanentaddrLine1(null);
		userInfoForm.setPermanentaddrLine2(null);
		userInfoForm.setPermanentCity(null);
		userInfoForm.setPermanentCountryId(null);
		userInfoForm.setPermanentStateId(null);
		userInfoForm.setPermanentPinCode(null);
		userInfoForm.setCurrentaddrLine1(null);
		userInfoForm.setCurrentaddrLine2(null);
		userInfoForm.setCurrentCity(null);
		userInfoForm.setCurrentCountryId(null);
		userInfoForm.setCurrentStateId(null);
		userInfoForm.setCurrentPinCode(null);
		userInfoForm.setSameTempAddr(false);
		userInfoForm.setCurrentOtherState(null);
		userInfoForm.setPermanentOtherState(null);
		userInfoForm.setTeachingStaff(false);
		userInfoForm.setRestrictedUser(false);
	}
	
	/**
	 * Method to check the entered date is not a future date
	 * @param dateString
	 * @return
	 */
	private boolean validatefutureDate(String dateString) {
		String formattedString=CommonUtil.ConvertStringToDateFormat(dateString, "dd/MM/yyyy","MM/dd/yyyy");
		Date date = new Date(formattedString);
		Date curdate = new Date();
		if (date.compareTo(curdate) == 1){
			return false;
		}
		else{
			return true;
		}
	
	}
	
	/**
	 * Method to check the validity of the phone numbers entered
	 * @param userInfoForm
	 * @param errors
	 */
	private void validatePhone(UserInfoForm userInfoForm, ActionErrors errors) {
		
		if(userInfoForm.getPhone1()!=null && !StringUtils.isEmpty(userInfoForm.getPhone1()) && !StringUtils.isNumeric(userInfoForm.getPhone1()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if(userInfoForm.getPhone2()!=null && !StringUtils.isEmpty(userInfoForm.getPhone2()) && !StringUtils.isNumeric(userInfoForm.getPhone2()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if(userInfoForm.getPhone3()!=null && !StringUtils.isEmpty(userInfoForm.getPhone3()) && !StringUtils.isNumeric(userInfoForm.getPhone3()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		
		if(userInfoForm.getMobile1()!=null && !StringUtils.isEmpty(userInfoForm.getMobile1()) && !StringUtils.isNumeric(userInfoForm.getMobile1()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if(userInfoForm.getMobile2()!=null && !StringUtils.isEmpty(userInfoForm.getMobile2()) && !StringUtils.isNumeric(userInfoForm.getMobile2()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if(userInfoForm.getMobile3()!=null && !StringUtils.isEmpty(userInfoForm.getMobile3()) && !StringUtils.isNumeric(userInfoForm.getMobile3()) )
		{
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)!=null && !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID).hasNext()) {
				ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
	}
	
	/**
	 * Method to check the required fields of communication address if it is not same as permanent address
	 * @param userInfoForm
	 * @param errors
	 */
	private void validateCommAddress(UserInfoForm userInfoForm, ActionErrors errors) {
		
		if(!userInfoForm.isSameTempAddr()){
			if(!StringUtils.isNumeric(userInfoForm.getCurrentPinCode()))
			{
				ActionMessage error= new ActionMessage(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_COMM_ADDRESS_PINCODE_INVALID,error);
			}
		}
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return forward to edit page
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public ActionForward editUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									  throws DuplicateException, Exception {
		
		return mapping.findForward(CMSConstants.EDIT_USER_INFO);
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return list of users based on the search criteria
	 * @throws Exception
	 */
	public ActionForward searchUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
									throws Exception {
		
		UserInfoForm userInfoForm = (UserInfoForm)form;
		userInfoForm.setUserInfoTOList(null);
		List<UserInfoTO> userInfoTOList  = UserInfoHandler.getInstance().getUserDetails(userInfoForm.getSearchDob(), userInfoForm.getFirstsearchName(),userInfoForm.getMiddlesearchName(), userInfoForm.getLastsearchName(), userInfoForm.getSearchDepartment());
		request.setAttribute("userinfoList", userInfoTOList);
		userInfoForm.setUserInfoTOList(userInfoTOList);
		return mapping.findForward(CMSConstants.EDIT_USER_INFO);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return setting userinfo to form for edit
	 * @throws Exception
	 */
	public ActionForward editUsers(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
								HttpServletResponse response) throws Exception {
		UserInfoForm userInfoForm = (UserInfoForm)form;
		List<UserInfoTO> userInfoTOList  = UserInfoHandler.getInstance().getUserDetailsById(userInfoForm.getId());
		UserInfoHandler.getInstance().setRequiredDateToForm(userInfoTOList, userInfoForm);
		userInfoForm.setSearchDepartment(null);
		userInfoForm.setFirstsearchName(null);
		userInfoForm.setMiddlesearchName(null);
		userInfoForm.setLastsearchName(null);
		userInfoForm.setSearchDob(null);
		setstateMapToRequest(request, userInfoForm);
		request.setAttribute("userOperation", "update");
		return mapping.findForward(CMSConstants.USER_INPUT);
	}
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response Method to update user information
	 * @return
	 * @throws DuplicateException
	 * @throws Exception
	 */
	public ActionForward updateUserInfo(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)
										throws DuplicateException, Exception {
		
		UserInfoForm userInfoForm = (UserInfoForm) form;
		
		ActionMessages messages = new ActionMessages();
		 ActionErrors errors = userInfoForm.validate(mapping, request);
		errors = validatePhotoSize(userInfoForm, errors);
		if(userInfoForm.getEmpPhoto() != null && userInfoForm.getEmpPhoto().getFileName()!= null &&
				!userInfoForm.getEmpPhoto().getFileName().isEmpty() && userInfoForm.getEmpPhoto().getContentType()!= null
				&& !userInfoForm.getEmpPhoto().getContentType().isEmpty()){
			errors = validateImageHeightWidth(userInfoForm, errors,request);			
		}
		
		validatePhone(userInfoForm, errors);
		validateCommAddress(userInfoForm, errors);
		
		try {
			
			if(isCancelled(request)){
				List<UserInfoTO> userInfoTOList  = UserInfoHandler.getInstance().getUserDetailsById(userInfoForm.getId());
				UserInfoHandler.getInstance().setRequiredDateToForm(userInfoTOList, userInfoForm);
				request.setAttribute("userOperation", "update");
				return mapping.findForward(CMSConstants.USER_INPUT);
			}
			
			if (userInfoForm.getDateOfBirth() != null && !StringUtils.isEmpty(userInfoForm.getDateOfBirth())) {
				if (CommonUtil.isValidDate(userInfoForm.getDateOfBirth())) {
					boolean isValid = validatefutureDate(userInfoForm.getDateOfBirth());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOB_LARGE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOB_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOB_LARGE));
						}
					}
				}
			}
			
			if (userInfoForm.getDateOfJoining() != null && !StringUtils.isEmpty(userInfoForm.getDateOfJoining())) {
				if (CommonUtil.isValidDate(userInfoForm.getDateOfJoining())) {
					boolean isValid = validatefutureDate(userInfoForm.getDateOfJoining());
					if (!isValid) {
						if (errors.get(CMSConstants.ADMISSIONFORM_DOJ_LARGE) != null && !errors.get(CMSConstants.ADMISSIONFORM_DOJ_LARGE).hasNext()) {
							errors.add(CMSConstants.ADMISSIONFORM_DOJ_LARGE, new ActionError(CMSConstants.ADMISSIONFORM_DOJ_LARGE));
						}
					}
				}
			}
			
			if (errors.isEmpty()) {
				if(UserInfoHandler.getInstance().addUserInfo(userInfoForm, "edit")){
						
					ActionMessage message = new ActionMessage("knowledgepro.admission.userinfoupdated");
					messages.add("messages", message);
					saveMessages(request, messages);
					resetFields(userInfoForm);
					
					Map<Integer, String> department = UserInfoHandler.getInstance().getDepartment();
					userInfoForm.setDepartment(department);
					
					Map<Integer, String> designation = UserInfoHandler.getInstance().getDesignation();
					userInfoForm.setDesignation(designation);
					
					Map<Integer, String> roles = UserInfoHandler.getInstance().getRoles();
					userInfoForm.setRoles(roles);
					
					Map<Integer, String> country = CountryHandler.getInstance().getCountriesMap();
					userInfoForm.setCountry(country);
				}
			} else {
				saveErrors(request, errors);
			}
		}catch(DuplicateException e1){
			errors.add("error", new ActionError(CMSConstants.USER_INFO_USEREXIST));
			saveErrors(request, errors);
			request.setAttribute("userOperation", "update");
			return mapping.findForward(CMSConstants.USER_INPUT);
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			userInfoForm.setErrorMessage(msg);
			userInfoForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Exit update User Info");
		if (errors.isEmpty()) {
			request.setAttribute("userOperation", "add");
		}else
		{
			request.setAttribute("userOperation", "update");
		}
		return mapping.findForward(CMSConstants.USER_INPUT);
	}
	
	/**
	 * 
	 * @param request
	 * @param userInfoForm
	 */
	public void setstateMapToRequest(HttpServletRequest request, UserInfoForm userInfoForm) {
		if (userInfoForm.getPermanentCountryId() != null
				&& (!userInfoForm.getPermanentCountryId().isEmpty())) {
			Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(userInfoForm.getPermanentCountryId()));
			request.setAttribute("permAddrStateMap", stateMap);
		}
		if (userInfoForm.getCurrentCountryId() != null
				&& (!userInfoForm.getCurrentCountryId().isEmpty())) {
			Map<Integer, String> curStateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(userInfoForm.getCurrentCountryId()));
			request.setAttribute("tempAddrStateMap", curStateMap);
		}
		
		
		
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
	public ActionForward deleteUserInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request,
									HttpServletResponse response) throws Exception {

		log.debug("inside deleteUserInfo Action");
		UserInfoForm userInfoForm = (UserInfoForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
		if (userInfoForm.getId() != 0) {
			int userId = userInfoForm.getId();
			isDeleted = UserInfoHandler.getInstance().deleteUserInfo(userId);
			List<UserInfoTO> userInfoTOList  = UserInfoHandler.getInstance().getUserDetails(userInfoForm.getSearchDob(), userInfoForm.getFirstsearchName(),userInfoForm.getMiddlesearchName(), userInfoForm.getLastsearchName(), userInfoForm.getSearchDepartment());
			request.setAttribute("userinfoList", userInfoTOList);
			userInfoForm.setUserInfoTOList(userInfoTOList);
			}
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			userInfoForm.setErrorMessage(msg);
			userInfoForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		if (isDeleted) {
			// success deleted
			ActionMessage message = new ActionMessage("knowledgepro.usermanagement.userinfo.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			resetFields(userInfoForm);
		} else {
			// failure error message.
			errors.add("error", new ActionError("knowledgepro.usermanagement.userinfo.deletefailure"));
			saveErrors(request, errors);
		}
		log.debug("inside deleteUserInfo");
		return mapping.findForward(CMSConstants.EDIT_USER_INFO);
	}
	
	
	/**
	 * Validates the uploaded file size.
	 * 
	 * @param Checks for the uploaded file and it allows the user to upload maximum of 2MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validatePhotoSize(UserInfoForm userForm, ActionErrors errors)throws Exception {
		log.info("Inside of validateDocumentSize");
		FormFile theFile =userForm.getEmpPhoto();
		InputStream propStream=RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		int maxPhotoSize = 0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			 maxPhotoSize=Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		 }catch (IOException e) {
			 log.error("Error occured in validatePhotoSize",e);
		}		 
			if(theFile!=null && maxPhotoSize< theFile.getFileSize())
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
					errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE,error);
				}
			}
			else if(theFile!=null && maXSize< theFile.getFileSize())
			{
				if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)!=null && !errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE).hasNext()) {
					ActionMessage error = new ActionMessage(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
					errors.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,error);
				}
			}			
			return errors;
	}	
	/**
	 * 
	 * @param fileData
	 * @param contentType
	 * @param fileName
	 * @param errors
	 * @returns dimension validation error
	 * Checks for an image height and width
	 * @throws Exception
	 */
	public static ActionErrors validateImageHeightWidth(UserInfoForm userInfoForm, ActionErrors errors,HttpServletRequest request)throws Exception{
		File file = null;
		String fileName = userInfoForm.getEmpPhoto().getFileName();
		String contentType = userInfoForm.getEmpPhoto().getContentType();
		byte[] fileData = userInfoForm.getEmpPhoto().getFileData();
		File file1 = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
		OutputStream out = new FileOutputStream(file1);
		byte buffer[] = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
		out.close();
		inputStream.close();
		file = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		String path = file.getAbsolutePath();
		Image image = Toolkit.getDefaultToolkit().getImage(path);
		ImageIcon icon = new ImageIcon(image);
	    int height = icon.getIconHeight();
	    int width = icon.getIconWidth();
	      if(width > 133 || height > 128){
	    	  errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.photo.dimension.size"));
	      }
			return errors;
		}

}

class ByteArrayStreamInfo implements StreamInfo {	
	
	protected String contentType;
	protected byte[] bytes;	
	
	public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
		this.contentType = contentType;
		this.bytes = myDfBytes;
	}	
	public String getContentType() {
		return contentType;
	}	
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(bytes);
	}
	
}	