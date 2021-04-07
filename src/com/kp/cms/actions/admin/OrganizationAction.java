package com.kp.cms.actions.admin;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

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
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.OrganizationForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.RenderYearList;

@SuppressWarnings("deprecation")
public class OrganizationAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(OrganizationAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method initializes Organization details with the details
	 * @throws Exception
	 */

	public ActionForward initOrganizationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		log.info("Entring into Action initOrganizationDetails");
		OrganizationForm organizationForm=(OrganizationForm)form;
		try {
			organizationForm.clear();
			assignListToForm(organizationForm);
			HttpSession session = request.getSession(false);
			if(session!=null){
				session.removeAttribute(CMSConstants.EDIT_OPERATION);
			}
		} catch (Exception e) {
				log.error("Error in saving OrganizationDetails",e);
				String msg = super.handleApplicationException(e);
				organizationForm.setErrorMessage(msg);
				organizationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into Action initOrganizationDetails");
		return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method saves an organization details
	 * @throws Exception
	 */
	public ActionForward saveOrganizationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("In Action saveOrganizationDetails");
		OrganizationForm organizationForm=(OrganizationForm)form;
		 ActionErrors errors = organizationForm.validate(mapping, request);
		errors =  validateDocumentSize(organizationForm, errors);
		errors =  validatetopBarSize(organizationForm, errors);
		ActionMessages messages = new ActionMessages();		
		try {
			if(CommonUtil.checkForEmpty(organizationForm.getTimeLimit())){
				if(!StringUtils.isNumeric(organizationForm.getTimeLimit())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(organizationForm.getTimeLimit())){
				if(Integer.parseInt(organizationForm.getTimeLimit())>=60){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
					}
				}			
			}
			if(organizationForm.getOrganizationtopBar() != null){
				byte[]fileData = organizationForm.getOrganizationtopBar().getFileData();
				String fileName = organizationForm.getOrganizationtopBar().getFileName();
				String contentType = organizationForm.getOrganizationtopBar().getContentType();
				errors = validateImageHeightWidth(fileData,fileName,contentType,errors,request);			
			}
			if(organizationForm.getOrganizationtopBar1() != null){
				byte[]fileData = organizationForm.getOrganizationtopBar1().getFileData();
				String fileName = organizationForm.getOrganizationtopBar1().getFileName();
				String contentType = organizationForm.getOrganizationtopBar1().getContentType();
				errors = validateImageHeightWidth(fileData,fileName,contentType,errors,request);			
			}
			if(errors.isEmpty()){				
				/**
				 * Get all the records of Organization from DB
				 */
				List<OrganizationTO> orgDetails = OrganizationHandler.getInstance().getOrganizationDetails();
				/**
				 * If any record found then show the error message as delete them and again try for the same
				 */
			if(orgDetails != null && !orgDetails.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_DELETE_EXISTING));
					assignListToForm(organizationForm);
					saveErrors(request, errors);
			}
			else{
				boolean isAdded ;
				setUserId(request, organizationForm);
				/**
				 * If organization details is added successfully then show the success message else add the appropriate error message
				 */
				isAdded = OrganizationHandler.getInstance().saveOrganizationDetails(organizationForm,request);
				if(isAdded) {
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ORGANIZATION_ADD_SUCCESS));
					saveMessages(request, messages);
					assignListToForm(organizationForm);
					organizationForm.clear();
				}
				else {
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_ADD_FAILED));
						assignListToForm(organizationForm);
						saveErrors(request, errors);
				}
			}
			}
			else{
				assignListToForm(organizationForm);
				saveErrors(request, errors);
			}
		} catch (Exception e) {
				log.error("Error in saving OrganizationDetails",e);
				String msg = super.handleApplicationException(e);
				organizationForm.setErrorMessage(msg);
				organizationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving Action saveOrganizationDetails");
		return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return This method deletes a organization details
	 * @throws Exception
	 */

	public ActionForward deleteOrganizationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("In Action deleteOrganizationDetails");
		OrganizationForm organizationForm=(OrganizationForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request, organizationForm);
			boolean isDeleted;			
			//Pass the id to handler class method and delete the record
			isDeleted=OrganizationHandler.getInstance().deleteOrganizationDetails(organizationForm.getId(), organizationForm.getUserId());

			/**
			 * If delete is success then show the success message. Else add the appropriate error message
			 */
			if (isDeleted) {
				messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ADMIN_ORGANIZATION_DELETE_SUCCESS));
				saveMessages(request, messages);
				assignListToForm(organizationForm);
				organizationForm.clear();
			}
			else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(organizationForm);
			}
		} catch (Exception e) {
			log.error("Error occured in action while deleting organization details",e);
			if (e instanceof ApplicationException) {
				assignListToForm(organizationForm);
				return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
			}else {
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_DELETE_FAILED));
				saveErrors(request, errors);
				assignListToForm(organizationForm);
				return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
			}
		}
		log.info("Leaving Action deleteOrganizationDetails");
		return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
	}
	/**
	 * 
	 * @param form
	 * @throws Exception
	 * Gets all the organization details from backend
	 * Assign those to a list and show in UI
	 */
	
	public void assignListToForm(ActionForm form) throws Exception
	{	
		log.info("Entering into assignListToForm of Organization Action");
		OrganizationForm organizationForm = (OrganizationForm) form;
			List<OrganizationTO> organizationDetails = OrganizationHandler.getInstance().getOrganizationDetails();
				organizationForm.setOrganizationList(organizationDetails);
			log.info("Leaving into assignListToForm of Organization Action");
	}	
	
	
	/**
	 * Validates the uploaded logo
	 * 
	 * @param Checks for the uploaded logo and it allows the user to upload maximum of 1MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validateDocumentSize(OrganizationForm organizationForm,
			ActionErrors errors) throws Exception{
		log.info("Inside of validateDocumentSize of Organization Action");
		FormFile theFile =organizationForm.getOrganizationLogo();
		FormFile theFile1 =organizationForm.getOrganizationLogo();
		InputStream propStream=RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.ORGANIZATION_MAX_UPLOAD_FILESIZE_KEY));
		 }catch (IOException e) {
			 log.error("error occured in validateDcoumentSize in OrganizationAction",e);
			throw new ApplicationException(e);
		}		 
			if(theFile!=null && maXSize< theFile.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_ATTACHMENT_MAXSIZE));
			}
			if(theFile1!=null && maXSize< theFile1.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_ATTACHMENT_MAXSIZE));
			}
			log.info("End of validateDocumentSize");
			return errors;
	}
	/**
	 * Validates the uploaded topbar
	 * 
	 * @param Checks for the uploaded topbar and it allows the user to upload maximum of 1MB size
	 * @param errors
	 * @return ActionMessages
	 */
	private ActionErrors validatetopBarSize(OrganizationForm organizationForm,
			ActionErrors errors) throws Exception{
		log.info("Inside of validatetopBarSize");
		FormFile theFile =organizationForm.getOrganizationtopBar();
		FormFile theFile1 =organizationForm.getOrganizationtopBar();
		InputStream propStream=RenderYearList.class.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize=0;
		Properties prop= new Properties();
		 try {
			 prop.load(propStream);
			 maXSize=Integer.parseInt(prop.getProperty(CMSConstants.ORGANIZATION_MAX_UPLOAD_FILESIZE_KEY));
		 }catch (IOException e) {
			 log.error("error occured in validatetopBarSize of OrganizationAction" ,e);
			 throw new ApplicationException(e);
		 }		 
			if(theFile!=null && maXSize< theFile.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_ATTACHMENT_MAXSIZE));
			}
			if(theFile1!=null && maXSize< theFile1.getFileSize())
			{
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ADMIN_ORGANIZATION_ATTACHMENT_MAXSIZE));
			}
		log.info("End of validatetopBarSize");
		return errors;
	}
	
	/**
	 * Used to edit Organization Details
	 * Sets data to form based on the ID
	 */

	public ActionForward editOrganizationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into Organization Action of editOrganizationDetails");
		OrganizationForm organizationForm = (OrganizationForm)form;
		try {
			//Calls the handler to set the data to form based on the ID
			OrganizationHandler.getInstance().getOrganizationDetailsById(organizationForm);
			//Sets the existing organization details to form
			assignListToForm(organizationForm);
			request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
		} catch (Exception e) {
				log.error("Error in Editing OrganizationDetails",e);
				String msg = super.handleApplicationException(e);
				organizationForm.setErrorMessage(msg);
				organizationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into Organization Action of editOrganizationDetails");
		return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
	}
	/**
	 * Used to update Organization Details
	 * Updates the existing data.
	 */
	public ActionForward updateOrganizationDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entering into Organization Action of updateOrganizationDetails");
		OrganizationForm organizationForm =(OrganizationForm)form;
		 ActionErrors errors = organizationForm.validate(mapping, request);
		errors =  validateDocumentSize(organizationForm, errors);
		errors =  validatetopBarSize(organizationForm, errors);
		ActionMessages messages = new ActionMessages();
		try {
			if(CommonUtil.checkForEmpty(organizationForm.getTimeLimit())){
				if(!StringUtils.isNumeric(organizationForm.getTimeLimit())){
					if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
						errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
					}
				}			
			}
			if(CommonUtil.checkForEmpty(organizationForm.getTimeLimit())){
				if(Integer.parseInt(organizationForm.getTimeLimit())>=60){
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.admin.timeformat.organization"));
				}			
			}
			if(organizationForm.getOrganizationtopBar() != null){
				byte[]fileData = organizationForm.getOrganizationtopBar().getFileData();
				String fileName = organizationForm.getOrganizationtopBar().getFileName();
				String contentType = organizationForm.getOrganizationtopBar().getContentType();
				errors = validateImageHeightWidth(fileData,fileName,contentType,errors,request);			
			}
			if(organizationForm.getOrganizationtopBar1() != null){
				byte[]fileData = organizationForm.getOrganizationtopBar1().getFileData();
				String fileName = organizationForm.getOrganizationtopBar1().getFileName();
				String contentType = organizationForm.getOrganizationtopBar1().getContentType();
				errors = validateImageHeightWidth(fileData,fileName,contentType,errors,request);			
			}
			if (isCancelled(request)) {
				OrganizationHandler.getInstance().getOrganizationDetailsById(organizationForm);
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				assignListToForm(organizationForm);
				return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
			}
			else if(errors.isEmpty()){
				setUserId(request, organizationForm);
				boolean isUpdated;
				//Requests the handler to update the data
				isUpdated = OrganizationHandler.getInstance().updateOrganizationDetails(organizationForm,request);
				//If update is success append the success message else add the error message.
				if(isUpdated){
					messages.add(CMSConstants.MESSAGES, new ActionMessage(CMSConstants.ORGANIZATION_UPDATE_SUCCESS));
					saveMessages(request, messages);
					assignListToForm(organizationForm);
					organizationForm.clear();
				}
				else{
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.ORGANIZATION_UPDATE_FAILED));
					addErrors(request, errors);
					assignListToForm(organizationForm);
					organizationForm.clear();
				}
			}
			else{
				request.setAttribute(CMSConstants.OPERATION, CMSConstants.EDIT_OPERATION);
				addErrors(request, errors);
				assignListToForm(organizationForm);
				return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
			}
		} catch (Exception e) {
				log.error("Error in Editing OrganizationDetails",e);
				String msg = super.handleApplicationException(e);
				organizationForm.setErrorMessage(msg);
				organizationForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into Organization Action of updateOrganizationDetails");
		return mapping.findForward(CMSConstants.INIT_ORGANIZATION);
	}
	
	/**
	 * 
	 * @param fileData
	 * @param contentType
	 * @param fileName
	 * @param errors
	 * @returns dimension validation error
	 * Checks for an image height and width
	 * Only allows to upload image of dimension 238*100
	 * @throws Exception
	 */
	public static ActionErrors validateImageHeightWidth(byte[] fileData,String fileName,String contentType, ActionErrors errors,HttpServletRequest request)throws Exception{

		if(fileData!=null && fileName != null && !StringUtils.isEmpty(fileName) && contentType!=null && !StringUtils.isEmpty(contentType) ){
			File file = null;
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+fileName);
			InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			OutputStream out = new FileOutputStream(file1);
			byte buffer[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			}
			out.close();
			inputStream.close();
			file = new File(filePath+fileName);
			String path = file.getAbsolutePath();
			Image image = Toolkit.getDefaultToolkit().getImage(path);
			ImageIcon icon = new ImageIcon(image);
		    int height = icon.getIconHeight();
		    int width = icon.getIconWidth();
		      if(width > 238 || height > 100){
		    	  errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_TOPBAR_DIMENSION));
		      }
		    if(file.exists()){
		    	file.delete();
		    }
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