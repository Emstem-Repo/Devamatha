package com.kp.cms.actions.employee;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.ImageIcon;

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
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.employee.OnlineResumerSubmissionForm;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.employee.OnlineResumerSubmissionHandler;
import com.kp.cms.to.employee.AchievementsTO;
import com.kp.cms.to.employee.JobTypeTO;
import com.kp.cms.to.employee.ProfessionalExperienceTO;
import com.kp.cms.utilities.RenderYearList;

public class OnlineResumerSubmissionAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(ApplyLeaveAction.class);

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initOnlineResumerSubmission(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the initOnlineResumerSubmission in OnlineResumerSubmissionAction");
		OnlineResumerSubmissionForm objform = (OnlineResumerSubmissionForm) form;
		cleanUpPageData(objform);
		setRequestDataToForm(objform);
		log.info("Exit from the initOnlineResumerSubmission in OnlineResumerSubmissionAction");
		return mapping.findForward(CMSConstants.VEW_ONLINE_RESUME_SUBMISSION);
	}

	private void cleanUpPageData(OnlineResumerSubmissionForm objform) {
		log.info("enter cleanUpPageData..");
		if (objform != null) {

			objform.setName(null);
			objform.setAddressLine1(null);
			objform.setAddressLine2(null);
			objform.setAddressLine3(null);
			objform.setNationalityId(null);
			objform.setZipCode(null);
			objform.setGender(null);
			objform.setCountryId(null);
			objform.setMaritalStatus(null);
			objform.setCity(null);
			objform.setDateOfBirth(null);
			objform.setPhone1(null);
			objform.setPhone2(null);
			objform.setPhone3(null);
			objform.setAge(null);
			objform.setMobPhone1(null);
			objform.setMobPhone2(null);
			objform.setMobPhone3(null);
			objform.setEmail(null);
			objform.setEmploymentStatus(null);
			objform.setExpectedSalaryLack(null);
			objform.setExpectedSalaryThousands(null);
			objform.setDesiredPost(null);
			objform.setDepartmentAppliedFor(null);
			objform.setDateOfJoining(null);
			objform.setVacancyType(null);
			objform.setRecommendedBy(null);
			objform.setPhoto(null);
			objform.setState(null);
		}
		log.info("exit cleanUpPageData..");

	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getProfessionalExperienceList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
				.info("Entering into the getProfessionalExperienceList in OnlineResumerSubmissionAction");
		OnlineResumerSubmissionForm objform = (OnlineResumerSubmissionForm) form;

		List<JobTypeTO> listJob = objform.getListOfJobType();

		OnlineResumerSubmissionHandler.getInstance().getListOfJobs(listJob);

		List<ProfessionalExperienceTO> list = OnlineResumerSubmissionHandler.getInstance()
				.getListOfProfessional(
						objform.getListOfProfessionalExperience());
		list.add(new ProfessionalExperienceTO());
		objform.setListOfProfessionalExperience(list);
		log
				.info("Exit from the getProfessionalExperienceList in OnlineResumerSubmissionAction");
		return mapping.findForward(CMSConstants.VEW_ONLINE_RESUME_SUBMISSION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getAchievementsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log
				.info("Entering into the getAchievementsList in OnlineResumerSubmissionAction");
		OnlineResumerSubmissionForm objform = (OnlineResumerSubmissionForm) form;
		List<AchievementsTO> list = objform.getListOfAchievements();
		list.add(new AchievementsTO());
		objform.setListOfAchievements(list);
		log
				.info("Exit from the getAchievementsList in OnlineResumerSubmissionAction");
		return mapping.findForward(CMSConstants.VEW_ONLINE_RESUME_SUBMISSION);
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOnlineResume(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into the saveOnlineResume in OnlineResumerSubmissionAction");
		OnlineResumerSubmissionForm objform = (OnlineResumerSubmissionForm) form;
		ActionErrors actErrors = objform.validate(mapping, request);
		if (objform.getPhoto() != null && objform.getPhoto().getFileName() != null && !objform.getPhoto().getFileName().isEmpty() && objform.getPhoto().getContentType() != null
				&& !objform.getPhoto().getContentType().isEmpty()) {
			actErrors = validateImageHeightWidth(objform, actErrors,request);
			actErrors = validatePhotoSize(objform.getPhoto(), actErrors);
		}
		validateQualificationDetails(objform,actErrors);
		if (actErrors.isEmpty()) {
			try {
				setUserId(request, objform);
				boolean flag = OnlineResumerSubmissionHandler.getInstance().saveOnlineResume(objform);
				if(flag){
					cleanUpPageData(objform);
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("knowledgepro.admin.addsuccess", "Online Resume");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequestDataToForm(objform);
				}else{
					ActionMessages messages = new ActionMessages();
					ActionMessage message = new ActionMessage("kknowledgepro.admin.addfailure", "Online Resume");
					messages.add("messages", message);
					saveMessages(request, messages);
					setRequestDataToForm(objform);
				}
			} catch (Exception e) {
				ActionMessages messages = new ActionMessages();
				messages.add("messages",new ActionMessage("org.hibernate.exception.ConstraintViolationException.message"));
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		}else{
			saveErrors(request, actErrors);
		}
		log.info("Exit from the saveOnlineResume in OnlineResumerSubmissionAction");
		return mapping.findForward(CMSConstants.VEW_ONLINE_RESUME_SUBMISSION);
	}

	private void validateQualificationDetails(OnlineResumerSubmissionForm objform, ActionErrors actErrors) {
		List<ProfessionalExperienceTO> listOfProfessionalExperience=objform.getListOfProfessionalExperience();
		if(listOfProfessionalExperience!=null && !listOfProfessionalExperience.isEmpty()){
			Iterator<ProfessionalExperienceTO> itr=listOfProfessionalExperience.iterator();
			while (itr.hasNext()) {
				ProfessionalExperienceTO professionalExperienceTO = (ProfessionalExperienceTO) itr.next();
				if(professionalExperienceTO.getCurrentOrganisation()==null || professionalExperienceTO.getCurrentOrganisation().isEmpty()){
					actErrors.add("errors",new ActionError("knowledgepro.employee.onlineresume.currentOrganisation"));
				}
				if(professionalExperienceTO.getFunctionalArea()==null || professionalExperienceTO.getFunctionalArea().isEmpty()){
					actErrors.add("errors",new ActionError("knowledgepro.employee.onlineresume.functionalArea"));
				}
				if(professionalExperienceTO.getTotalExperienceYear()==null || professionalExperienceTO.getTotalExperienceYear().isEmpty()){
					actErrors.add("errors",new ActionError("knowledgepro.employee.onlineresume.totalExpYear"));
				}
				if(professionalExperienceTO.getTotalExperienceMonth()==null || professionalExperienceTO.getTotalExperienceMonth().isEmpty()){
					actErrors.add("errors",new ActionError("knowledgepro.employee.onlineresume.totalExpMonth"));
				}
			}
		}
		
	}

	/**
	 * validating the image size
	 * @param theFile
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	private ActionErrors validatePhotoSize(FormFile theFile, ActionErrors errors)
			throws Exception {
		log.info("Inside of validateDocumentSize");
		InputStream propStream = RenderYearList.class
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES_1);
		int maXSize = 0;
		int maxPhotoSize = 0;
		Properties prop = new Properties();
		try {
			prop.load(propStream);
			maXSize = Integer.parseInt(prop
					.getProperty(CMSConstants.MAX_UPLOAD_FILESIZE_KEY));
			maxPhotoSize = Integer.parseInt(prop
					.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
		} catch (IOException e) {
			log.error("Error occured in validatePhotoSize", e);
		}
		if (theFile != null && maxPhotoSize < theFile.getFileSize()) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE);
				errors.add(CMSConstants.ADMISSIONFORM_PHOTO_MAXSIZE, error);
			}
		} else if (theFile != null && maXSize < theFile.getFileSize()) {
			if (errors.get(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE) != null
					&& !errors.get(
							CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE);
				errors
						.add(CMSConstants.ADMISSIONFORM_ATTACHMENT_MAXSIZE,
								error);
			}
		}
		return errors;
	}

	/**
	 * @param objform
	 * @param errors
	 * @return
	 * @throws Exception
	 */
	public static ActionErrors validateImageHeightWidth(
			OnlineResumerSubmissionForm objform, ActionErrors errors,HttpServletRequest request)
			throws Exception {
		File file = null;
		String fileName = objform.getPhoto().getFileName();
		String contentType = objform.getPhoto().getContentType();
		byte[] fileData = objform.getPhoto().getFileData();
		String filePath=request.getRealPath("");
    	filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+fileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType, fileData)
				.getInputStream();
		OutputStream out = new FileOutputStream(file1);
		byte buffer[] = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0) {
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
		if (width > 133 || height > 128) {
			errors.add(CMSConstants.ERROR, new ActionError(
					"knowledgepro.photo.dimension.size"));
		}
		return errors;
	}
	
	/**
	 * Setting the requested Data to FOrm
	 * @param objform
	 * @throws Exception
	 */
	public void setRequestDataToForm(OnlineResumerSubmissionForm objform) throws Exception{
		objform.setListNationalityMap(OnlineResumerSubmissionHandler.getInstance().getListNationalityMap());

		objform.setListCountryMap(OnlineResumerSubmissionHandler.getInstance().getListCountryMap());

		objform.setListOfJobType(OnlineResumerSubmissionHandler.getInstance().getListOfJobType());

		objform.setListDesignationMap(OnlineResumerSubmissionHandler.getInstance().getDesignationMap());

		objform.setListDepartmentMap(OnlineResumerSubmissionHandler.getInstance().getDepartmentMap());
		Map<Integer, String> map = OnlineResumerSubmissionHandler.getInstance().getQualificationMap();
		objform.setListQualificationMap(map);
		if (map != null) {
			objform.setListOfEdicationDetails(OnlineResumerSubmissionHandler.getInstance().getEdicationDetails(map));
		}

		objform.setListfunctionalAreaMap(OnlineResumerSubmissionHandler.getInstance().getFunctionalAreaMap());
		objform.setListOfProfessionalExperience(OnlineResumerSubmissionHandler.getInstance().getProfessionalExperienceList());
		objform.setListOfAchievements(OnlineResumerSubmissionHandler.getInstance().getAchievementsList());
	}
	
	public ActionForward initOutsideOnlineResumeSubmission(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OnlineResumerSubmissionForm onlineResumerSubmissionForm=(OnlineResumerSubmissionForm)form;
		HttpSession session= request.getSession(false);
		try
		{
			cleanUpPageData(onlineResumerSubmissionForm);
			setRequestDataToForm(onlineResumerSubmissionForm);
			setUserId(request, onlineResumerSubmissionForm);
			Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
			if(organisation!=null){
				// set photo to session
				if(organisation.getLogoContentType()!=null){
					if(session!=null){
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_LOGO, organisation.getLogo());
						session.setAttribute(CMSConstants.KNOWLEDGEPRO_TOPBAR, organisation.getTopbar());
					}
				}
			}
		}
		catch (Exception e) {
			 String msg = super.handleApplicationException(e);
			 onlineResumerSubmissionForm.setErrorMessage(msg);
			 onlineResumerSubmissionForm.setErrorStack(e.getMessage());
	         return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.VEW_ONLINE_RESUME_SUBMISSION);
	}
}
