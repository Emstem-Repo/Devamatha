package com.kp.cms.actions.employee;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.employee.EmployeeInfoForm;
import com.kp.cms.handlers.admin.CountryHandler;
import com.kp.cms.handlers.admin.LanguageHandler;
import com.kp.cms.handlers.admission.AdmissionFormHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.employee.EducationMasterHandler;
import com.kp.cms.handlers.employee.EmployeeInfoHandler;
import com.kp.cms.handlers.usermanagement.UserInfoHandler;
import com.kp.cms.to.admin.EmpAcheivementTO;
import com.kp.cms.to.admin.EmpDependentsTO;
import com.kp.cms.to.admin.EmpEducationTO;
import com.kp.cms.to.admin.EmpImmigrationTO;
import com.kp.cms.to.admin.EmpJobAllowanceTO;
import com.kp.cms.to.admin.EmpJobTO;
import com.kp.cms.to.admin.EmpLeaveTO;
import com.kp.cms.to.admin.EmpWorkExperienceTO;
import com.kp.cms.to.admin.EmployeeInformactionTO;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.employee.EmployeeKeyValueTO;
import com.kp.cms.to.employee.EmployeeStreamTO;
import com.kp.cms.to.usermanagement.UserInfoTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.RenderYearList;

/**
 * Action CLASS FOR EMPLOYEE INFORMATION
 * 
 */
public class EmployeeInfoAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(EmployeeInfoAction.class);
	private static final String TO_DATEFORMAT = "MM/dd/yyyy";
	private static final String FROM_DATEFORMAT = "dd/MM/yyyy";

	/**
	 * initialize Employee info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initEmployeeInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter initEmployeeInfo page...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		setUserId(request, infoForm);

		try {
			// reset all

			infoForm.setEmployeeFound(false);
		
			infoForm.getEmployeeDetail().setLastName(null);
			infoForm.getEmployeeDetail().setFirstName(null);
			infoForm.getEmployeeDetail().setNickName(null);
			infoForm.getEmployeeDetail().setMiddleName(null);
			infoForm.getEmployeeDetail().setCode(null);
			infoForm.clearFields();
			// by default false.checking is implemented
			// infoForm.setEmployeeDetail(null);
			// infoForm.setEmployeeDetail(new EmployeeTO());
			infoForm.setAdminUser(false);

			// check admin user or not
			UserInfoHandler userhandle = UserInfoHandler.getInstance();
			List<UserInfoTO> userTos = userhandle.getUserDetailsById(Integer
					.parseInt(infoForm.getUserId()));
			UserInfoTO user = null;
			if (userTos != null && !userTos.isEmpty()) {
				user = userTos.get(0);

				// admin previledge check

				if (CMSConstants.ADMIN_LIST.contains(user.getRolesTO().getId())) {
					infoForm.setAdminUser(true);
				}
			}

			// Nationality
			AdmissionFormHandler formhandler = AdmissionFormHandler
					.getInstance();
			infoForm.setNationalities(formhandler.getNationalities());

			CountryHandler countryhandler = CountryHandler.getInstance();
			infoForm.setCountries(countryhandler.getCountries());

			LanguageHandler langHandler = LanguageHandler.getHandler();
			// fetching all mother tongues
			infoForm.setLanguages(langHandler.getLanguages());

			infoForm.setEducationMasters(EducationMasterHandler.getInstance()
					.getEducationMasterDetails());

			//infoForm.setAllowanceTos(EmployeeInfoHandler.getInstance()
			//		.getAllowanceMasters());
			infoForm.setAllowanceSize(infoForm.getAllowanceTos().size());
			infoForm.setLeaveTypeTOs(EmployeeInfoHandler.getInstance()
					.getLeaveTypes());

			// reporting employees
			infoForm.setReportingTos(EmployeeInfoHandler.getInstance()
					.getReportingTos());

			List<EmployeeStreamTO> streamTO = EmployeeInfoHandler.getInstance()
					.getEmployeeStreamMasters();
			if (streamTO != null && streamTO.size() > 0) {
				infoForm.setStreamTO(streamTO);
			}

			List<EmployeeKeyValueTO> listDepartment = EmployeeInfoHandler
					.getInstance().getDepartmentMasters();
			if (listDepartment != null && listDepartment.size() > 0) {
				infoForm.setListDepartment(listDepartment);
			}
			List<EmployeeKeyValueTO> listDesignation = EmployeeInfoHandler
					.getInstance().getDesignationMasters();
			if (listDesignation != null && listDesignation.size() > 0) {
				infoForm.setListDesignation(listDesignation);
			}
			List<EmployeeKeyValueTO> listWorkLocation = EmployeeInfoHandler
					.getInstance().getWorkLocationMasters();
			if (listWorkLocation != null && listWorkLocation.size() > 0) {
				infoForm.setListWorkLocation(listWorkLocation);
			}

			List<EmployeeKeyValueTO> listEmployeeType = EmployeeInfoHandler
					.getInstance().getEmployeeType();
			if (listEmployeeType != null && listEmployeeType.size() > 0) {
				infoForm.setListEmployeeType(listEmployeeType);
			}
			List<EmployeeKeyValueTO> listRoles = EmployeeInfoHandler
					.getInstance().getlistRoles();
			if (listRoles != null && listRoles.size() > 0) {
				infoForm.setListRoles(listRoles);
			}

		} catch (Exception e) {
			log.error("error in initEmployeeInfo...", e);
			throw e;

		}

		log.info("exit initEmployeeInfo page...");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

	/**
	 * forwards Employee personal info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeePersonalInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

	/**
	 * forwards Employee contact info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeContact(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoForm form2=(EmployeeInfoForm)form;
		EmployeeTO employeeTO= form2.getEmployeeDetail();
		if(employeeTO.getCommunicationCountryId()!=null && !employeeTO.getCommunicationCountryId().isEmpty()){
			 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTO.getCommunicationCountryId()));
			request.setAttribute("commStateMap", stateMap);
		}
		if(employeeTO.getPermanentCountryId()!=null && !employeeTO.getPermanentCountryId().isEmpty()){
			 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTO.getPermanentCountryId()));
			request.setAttribute("permStateMap", stateMap);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_CONTACT_PAGE);
	}

	/**
	 * forwards Employee Dependent info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeDependentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);
	}

	/**
	 * forwards Employee emergency contact info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeEmergContact(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_EMERG_CONT_PAGE);
	}

	/**
	 * forwards Employee Experience info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeExperience(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
	}

	/**
	 * forwards Employee Immigration info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeImmigration(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_IMMIGRATION_PAGE);
	}

	/**
	 * forwards Employee Education info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeEducationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
	}

	/**
	 * forwards Employee Job info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeJobInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);
	}

	/**
	 * forwards Employee Language info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeLanguageInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
	}

	/**
	 * forwards Employee Skills info page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeSkills(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
	}

	/**
	 * forwards Employee Report To page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward forwardEmployeeReportTo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return mapping.findForward(CMSConstants.EMPLOYEE_REPORTTO_PAGE);
	}

	public ActionForward forwardEmployeeUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		EmployeeInfoForm objform = (EmployeeInfoForm) form;
		EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
			.getEmployeeDetailsByEmployeeId(objform.getEmployeeDetail().getId(), objform);
		if (employeeTo != null) {
			objform.setEmployeeFound(true);
			objform.setEmployeeDetail(employeeTo);
			if(objform.getEmployeeDetail().getIsTeachingStaff()==null)
				objform.getEmployeeDetail().setIsTeachingStaff(true);
		}
		return mapping.findForward(CMSConstants.EMPLOYEE_USER_INFO_PAGE);
	}

	public ActionForward getEmployeeInfoDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter getEmployeeInfo page...");
		EmployeeInfoForm objform = (EmployeeInfoForm) form;
		ActionMessages errors = objform.validate(mapping, request);
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}
			// firstName
			String employeeId = request.getParameter("firstName");

			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetailsByEmployeeId(
							Integer.parseInt(employeeId), objform);

			if (employeeTo != null) {
				objform.setEmployeeFound(true);
				objform.setEmployeeDetail(employeeTo);

				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			} else {
				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}
		} catch (Exception e) {
			log.error("error in initEmployeeInfo...", e);
			throw e;
		}

	}

	public ActionForward getEmployeeDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter getEmployeeInfo page...");
		EmployeeInfoForm objform = (EmployeeInfoForm) form;
		try {

			String code = objform.getEmployeeDetail().getCode();
			String lastName = objform.getEmployeeDetail().getLastName();
			String firstName = objform.getEmployeeDetail().getFirstName();
			String middleName = objform.getEmployeeDetail().getMiddleName();
			String nickName = objform.getEmployeeDetail().getNickName();

			// clearValues(objform);

			ArrayList<EmployeeInformactionTO> list = EmployeeInfoHandler
					.getInstance().getEmployeeInfoDetails(code, lastName,
							firstName, middleName, nickName);

			if (list != null && list.size() > 0) {

				objform.setListOfEmployees(list);

				objform.getEmployeeDetail().setDummyCode(code);
				objform.getEmployeeDetail().setDummyLastName(lastName);
				objform.getEmployeeDetail().setDummyFirstName(firstName);
				objform.getEmployeeDetail().setDummyMiddleName(middleName);
				objform.getEmployeeDetail().setDummyNickName(nickName);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_DETAILS_DISPLAY);
			} else {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}
		} catch (Exception e) {
			log.error("error in initEmployeeInfo...", e);
			throw e;
		}

	}

	public ActionForward getEmployeeDetailsClose(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter getEmployeeInfo page...");
		EmployeeInfoForm objform = (EmployeeInfoForm) form;
		try {

			// clearValues(objform);

			String code = objform.getEmployeeDetail().getDummyCode();
			String lastName = objform.getEmployeeDetail().getDummyLastName();
			String firstName = objform.getEmployeeDetail().getDummyFirstName();
			String middleName = objform.getEmployeeDetail()
					.getDummyMiddleName();
			String nickName = objform.getEmployeeDetail().getDummyNickName();
			objform.clearFields();
			ArrayList<EmployeeInformactionTO> list = EmployeeInfoHandler
					.getInstance().getEmployeeInfoDetails(code, lastName,
							firstName, middleName, nickName);

			if (list != null && list.size() > 0) {
				objform.setListOfEmployees(list);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_INFO_DETAILS_DISPLAY);
			} else {

				ActionMessages messages = new ActionMessages();
				ActionMessage message = null;
				message = new ActionMessage(
						CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}
		} catch (Exception e) {
			log.error("error in initEmployeeInfo...", e);
			throw e;
		}

	}

	private void clearValues(EmployeeInfoForm infoForm) {

		// infoForm.setEmployeeFound(false);
		infoForm.getEmployeeDetail().setLastName(null);
		infoForm.getEmployeeDetail().setFirstName(null);
		infoForm.getEmployeeDetail().setNickName(null);
		infoForm.getEmployeeDetail().setMiddleName(null);
		infoForm.getEmployeeDetail().setCode(null);
		// by default false.checking is implemented
		// infoForm.setAdminUser(false);
	}

	/**
	 * Employee Search
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	// public ActionForward getEmployeeInfo(ActionMapping mapping,
	// ActionForm form, HttpServletRequest request,
	// HttpServletResponse response) throws Exception {
	// log.info("enter getEmployeeInfo page...");
	// EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
	// // validation if needed
	// ActionMessages errors = infoForm.validate(mapping, request);
	// if (errors == null)
	// errors = new ActionMessages();
	// try {
	// if (errors != null && !errors.isEmpty()) {
	// saveErrors(request, errors);
	// return mapping
	// .findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	// }
	// // infoForm.setEmployeeDetail(null);
	// EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
	// .getEmployeeDetails(infoForm);
	//			
	// if (employeeTo != null) {
	// infoForm.setEmployeeFound(true);
	// infoForm.setEmployeeDetail(employeeTo);
	//
	// if (CountryHandler.getInstance().getCountries() != null) {
	// // birthCountry & states
	// List<CountryTO> birthCountries = CountryHandler
	// .getInstance().getCountries();
	// if (birthCountries != null) {
	//
	// Iterator<CountryTO> cntitr = birthCountries.iterator();
	// while (cntitr.hasNext()) {
	// CountryTO countryTO = (CountryTO) cntitr.next();
	// if (infoForm.getEmployeeDetail()
	// .getCommunicationCountryId() != null
	// && countryTO.getId() == Integer
	// .parseInt(infoForm
	// .getEmployeeDetail()
	// .getCommunicationCountryId())) {
	// List<StateTO> stateList = countryTO
	// .getStateList();
	// Collections.sort(stateList,
	// new StateComparator());
	// infoForm.setEditStates(stateList);
	// }
	// }
	// }
	// }
	// List<EmpAllowanceTO> allowanceTos = EmployeeInfoHandler
	// .getInstance().getAllowanceMasters();
	//
	// if (allowanceTos != null && !allowanceTos.isEmpty()) {
	// infoForm.setAllowanceTos(allowanceTos);
	// }
	// List<EmployeeStreamTO> streamTO=EmployeeInfoHandler
	// .getInstance().getEmployeeStreamMasters();
	// if(streamTO!=null&&streamTO.size()>0){
	// infoForm.setStreamTO(streamTO);
	// }
	//
	// } else {
	// infoForm.setEmployeeFound(false);
	// ActionMessages messages = new ActionMessages();
	// ActionMessage message = null;
	// message = new ActionMessage(
	// CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND);
	// messages.add("messages", message);
	// saveMessages(request, messages);
	// return mapping
	// .findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	// }
	//
	// } catch (Exception e) {
	// log.error("error in initEmployeeInfo...", e);
	// throw e;
	//
	// }
	//
	// log.info("exit initEmployeeInfo page...");
	// return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	// }

	/**
	 * Employee Personal Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public ActionForward saveEmployeePersonalInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeePersonalInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		boolean valid = infoForm.validateEmail(infoForm.getEmployeeDetail().getEmail());
		ActionErrors actErrors = new ActionErrors();
		ActionMessages errors = infoForm.validate(mapping, request);
		errors = validatePhotoSize(infoForm.getEmployeeDetail()
				.getEmpPhotoName(), actErrors);
		if (infoForm.getEmployeeDetail().getEmpPhotoName() != null
				&& infoForm.getEmployeeDetail().getEmpPhotoName().getFileName() != null
				&& !infoForm.getEmployeeDetail().getEmpPhotoName().getFileName().equals("")
				&& infoForm.getEmployeeDetail().getEmpPhotoName()
						.getContentType() != null
				&& !infoForm.getEmployeeDetail().getEmpPhotoName()
						.getContentType().equals("")) {
			errors = validateImageHeightWidth(infoForm, actErrors,request);
		}
		if(!valid){
			ActionMessage error = new ActionMessage("admissionFormForm.emailId.invalid");
			actErrors.add("admissionFormForm.emailId.invalid",error);
		}
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.savePersonalDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_PERSONAL_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);

			}
		}
		catch (DuplicateException e) {
			ActionMessages messages = new ActionMessages();
			messages.add("messages", new ActionMessage(
					"knowledgepro.admin.alreadyExist", "Finger Print Id"));
			saveMessages(request, messages);
			return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
		}	
		catch (Exception e) {
			e.printStackTrace();
			log.error("error in saveEmployeePersonalInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeePersonalInfo...");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

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

	public static ActionErrors validateImageHeightWidth(EmployeeInfoForm form,
			ActionErrors errors,HttpServletRequest request) throws Exception {
		File file = null;
		String fileName = form.getEmployeeDetail().getEmpPhotoName()
				.getFileName();
		String contentType = form.getEmployeeDetail().getEmpPhotoName()
				.getContentType();
		byte[] fileData = form.getEmployeeDetail().getEmpPhotoName()
				.getFileData();
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

	public ActionForward saveEmployeeUserInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeUserInfo...");
		EmployeeInfoForm objForm = (EmployeeInfoForm) form;
		setUserId(request, objForm);
		ActionMessages errors = null;

		if (objForm.getEmployeeDetail().getFirstName() == null
				|| objForm.getEmployeeDetail().getFirstName().trim().length() == 0) {
			errors = new ActionMessages();
			errors.add("error",
					new ActionError("errors.required", "First Name"));
		}
		if (objForm.getEmployeeDetail().getCode() == null
				|| objForm.getEmployeeDetail().getCode().trim().length() == 0) {
			if (errors == null)
				errors = new ActionMessages();
			errors.add("error", new ActionError("errors.required", "Code"));
		}
		if (errors != null)
			saveErrors(request, errors);
			
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				Integer employeeId = EmployeeInfoHandler.getInstance()
						.saveUserDetails(objForm);
				if (employeeId != null && employeeId > 0) {
					objForm.setEmployeeFound(true);

					objForm.getEmployeeDetail().setId(employeeId);

					EmployeeTO employeeTo = EmployeeInfoHandler
							.getInstance()
							.getEmployeeDetailsByEmployeeId(employeeId, objForm);
					if (employeeTo != null) {
						objForm.setEmployeeDetail(employeeTo);
					}

					objForm.getEmployeeDetail().setUserName(null);
					objForm.getEmployeeDetail().setPassword(null);
					ActionMessages messages = new ActionMessages();

					messages.add("messages", new ActionMessage(
							"knowledgepro.admin.addsuccess", "Users"));
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.EMPLOYEE_USER_INFO_PAGE);
				}
			} else {

				return mapping
						.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
			}

		}
		catch (DuplicateException e) {
			ActionErrors messages = new ActionErrors();
			messages.add("messages", new ActionMessage("knowledgepro.admin.alreadyExist", "Code"));
			saveErrors(request,messages);
			return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
		}	
		catch (Exception e) {
			log.error("error in saveEmployeePersonalInfo...", e);

			throw e;

		}

		log.info("exit resetLanguageInfo...");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

	public ActionForward updateUserDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeUserInfo...");
		EmployeeInfoForm objForm = (EmployeeInfoForm) form;

		ActionMessages errors = validateErrorForUser(objForm);
		saveErrors(request, errors);
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (errors.isEmpty()) {
				boolean result = EmployeeInfoHandler.getInstance()
						.updateUserDetails(objForm);
				if (result) {
					objForm.setEmployeeFound(true);
					EmployeeTO employeeTo = null;
					employeeTo = objForm.getEmployeeDetail();
					employeeTo.setFirstName(employeeTo.getNewFirstName());
					employeeTo.setLastName(employeeTo.getNewLastName());
					employeeTo.setMiddleName(employeeTo.getNewMiddleName());
					employeeTo.setNickName(employeeTo.getNewNickName());
					employeeTo.setCode(employeeTo.getNewCode());
					if (employeeTo == null) {
						employeeTo = EmployeeInfoHandler.getInstance()
								.getEmployeeDetailsByEmployeeId(
										objForm.getEmployeeDetail().getId(),
										objForm);
						objForm.setEmployeeDetail(employeeTo);
					}
					ActionMessages messages = new ActionMessages();
					messages.add("messages", new ActionMessage(
							"knowledgepro.admin.updatesuccess", "Users"));
					saveMessages(request, messages);

					return mapping
							.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
				}
			} else {

				return mapping
						.findForward(CMSConstants.EMPLOYEE_USER_INFO_PAGE);
			}

		} catch (DuplicateException e) {
			ActionErrors messages = new ActionErrors();
			if(objForm.getDuplicateIn().equalsIgnoreCase("userName"))
				messages.add("messages", new ActionMessage("knowledgepro.admin.alreadyExist", "Users name"));
			else
				messages.add("messages", new ActionMessage("knowledgepro.admin.alreadyExist", "Code"));
			saveErrors(request,messages);
			return mapping.findForward(CMSConstants.EMPLOYEE_USER_INFO_PAGE);
		} catch (Exception e) {
			log.error("error in saveEmployeePersonalInfo...", e);

			throw e;

		}

		log.info("exit resetLanguageInfo...");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

	private ActionMessages validateErrorForUser(EmployeeInfoForm objForm) {
		ActionMessages errors = new ActionMessages();
		if (objForm.getEmployeeDetail().getUserName() == null
				|| objForm.getEmployeeDetail().getUserName().trim().length() == 0) {
			errors
					.add("error", new ActionError("errors.required",
							"User Name"));
		}
		if (objForm.getEmployeeDetail().getPassword() == null
				|| objForm.getEmployeeDetail().getPassword().trim().length() == 0) {
			errors.add("error", new ActionError("errors.required", "Password"));
		}
		if (objForm.getEmployeeDetail().getRoleId() == null
				|| objForm.getEmployeeDetail().getRoleId().trim().length() == 0) {
			errors.add("error", new ActionError("errors.required", "Role"));
		}
		if(objForm.getEmployeeDetail().getNewFirstName()==null 
				|| objForm.getEmployeeDetail().getNewFirstName().trim().length()==0){
			errors.add("error",new ActionError("errors.required","First Name"));
		}
		if(objForm.getEmployeeDetail().getNewLastName()==null 
				|| objForm.getEmployeeDetail().getNewLastName().trim().length()==0){
			errors.add("error",new ActionError("errors.required","Last Name"));
		}
		
		if(objForm.getEmployeeDetail().getIsTeachingStaff()==null)
		{
			errors.add("error",new ActionError("errors.required","Teaching Staff"));
		}
		if (objForm.getEmployeeDetail().getNewCode() == null
				|| objForm.getEmployeeDetail().getNewCode().trim().length() == 0 || objForm.getEmployeeDetail().getNewCode().isEmpty()) {
			errors.add("error", new ActionError("errors.required", "Employee Code"));
		}
		
		return errors;
	}

	/**
	 * Employee Contact Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeContactInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeContactInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		EmployeeTO employeeTO= infoForm.getEmployeeDetail();
		if(employeeTO.getCommunicationCountryId()!=null && !employeeTO.getCommunicationCountryId().isEmpty()){
			 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTO.getCommunicationCountryId()));
			request.setAttribute("commStateMap", stateMap);
		}
		if(employeeTO.getPermanentCountryId()!=null && !employeeTO.getPermanentCountryId().isEmpty()){
			 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTO.getPermanentCountryId()));
			request.setAttribute("permStateMap", stateMap);
		}
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (infoForm.getEmployeeDetail() != null)
				validatePhone(infoForm.getEmployeeDetail(), errors);
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_CONTACT_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveContactDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_CONTACT_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_CONTACT_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeContactInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeContactInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_CONTACT_PAGE);
	}

	/**
	 * Employee Emergency Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeEmergencyInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeEmergencyInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EMERG_CONT_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveEmergencyDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_EMERGENCY_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EMERG_CONT_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeEmergencyInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeEmergencyInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EMERG_CONT_PAGE);
	}

	/**
	 * Employee Emergency Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeSkillsInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeSkillsInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed

		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveSkillDetails(infoForm);
			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_SKILL_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
			}
		} catch (Exception e) {
			log.error("error in saveEmployeeSkillsInfo...", e);
			throw e;
		}
		log.info("exit saveEmployeeSkillsInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
	}

	/**
	 * Employee immigration Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeImmigrationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeImmigrationInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateImmigrations(emplyeeTO, errors);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_IMMIGRATION_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveImmigrationDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_IMMIGRATION_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_IMMIGRATION_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeImmigrationInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeImmigrationInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_IMMIGRATION_PAGE);
	}

	/**
	 * Employee dependent Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeDependentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeDependentInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateDependents(emplyeeTO, errors);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveDependentDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_DEPENDENT_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeDependentInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeDependentInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);
	}

	/**
	 * @param emplyeeTO
	 * @param errors
	 */
	private void validateDependents(EmployeeTO emplyeeTO, ActionMessages errors) {
		if (emplyeeTO.getDependents() != null) {
			Iterator<EmpDependentsTO> eduItr = emplyeeTO.getDependents()
					.iterator();
			while (eduItr.hasNext()) {
				EmpDependentsTO expTO = (EmpDependentsTO) eduItr.next();

				if (expTO.getChild1DateOfBirth() != null
						&& !StringUtils.isEmpty(expTO.getChild1DateOfBirth())
						&& !CommonUtil
								.isValidDate(expTO.getChild1DateOfBirth())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_DEPENDENT_CHILD1_DOB_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_DEPENDENT_CHILD1_DOB_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_DEPENDENT_CHILD1_DOB_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_DEPENDENT_CHILD1_DOB_INVALID,
										error);
					}
				}

				if (expTO.getChild2DateOfBirth() != null
						&& !StringUtils.isEmpty(expTO.getChild2DateOfBirth())
						&& !CommonUtil
								.isValidDate(expTO.getChild2DateOfBirth())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_DEPENDENT_CHILD2_DOB_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_DEPENDENT_CHILD2_DOB_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_DEPENDENT_CHILD2_DOB_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_DEPENDENT_CHILD2_DOB_INVALID,
										error);
					}
				}

			}
		}

	}

	/**
	 * @param emplyeeTO
	 * @param errors
	 */
	private void validateImmigrations(EmployeeTO emplyeeTO,
			ActionMessages errors) {
		if (emplyeeTO.getImmigrations() != null) {
			Iterator<EmpImmigrationTO> eduItr = emplyeeTO.getImmigrations()
					.iterator();
			while (eduItr.hasNext()) {
				EmpImmigrationTO expTO = (EmpImmigrationTO) eduItr.next();

				if (expTO.getPassportIssueDate() != null
						&& !StringUtils.isEmpty(expTO.getPassportIssueDate())
						&& !CommonUtil
								.isValidDate(expTO.getPassportIssueDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_ISSUEDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_ISSUEDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_ISSUEDT_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_ISSUEDT_INVALID,
										error);
					}
				}
				if (expTO.getPassportExpiryDate() != null
						&& !StringUtils.isEmpty(expTO.getPassportExpiryDate())
						&& !CommonUtil.isValidDate(expTO
								.getPassportExpiryDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_EXPDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_EXPDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_EXPDT_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_IMMIGRATION_PASSPORT_EXPDT_INVALID,
										error);
					}
				}

				if (expTO.getPassportIssueDate() != null
						&& !StringUtils.isEmpty(expTO.getPassportIssueDate())
						&& CommonUtil.isValidDate(expTO.getPassportIssueDate())
						&& expTO.getPassportExpiryDate() != null
						&& !StringUtils.isEmpty(expTO.getPassportExpiryDate())
						&& CommonUtil
								.isValidDate(expTO.getPassportExpiryDate())) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							expTO.getPassportIssueDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(expTO
							.getPassportExpiryDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE,
											new ActionError(
													CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors
								.get(CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME,
											new ActionError(
													CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME));
						}

					}
				}

				if (expTO.getVisaIssueDate() != null
						&& !StringUtils.isEmpty(expTO.getVisaIssueDate())
						&& !CommonUtil.isValidDate(expTO.getVisaIssueDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_IMMIGRATION_VISA_ISSUEDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_IMMIGRATION_VISA_ISSUEDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_IMMIGRATION_VISA_ISSUEDT_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_IMMIGRATION_VISA_ISSUEDT_INVALID,
										error);
					}
				}
				if (expTO.getVisaExpiryDate() != null
						&& !StringUtils.isEmpty(expTO.getVisaExpiryDate())
						&& !CommonUtil.isValidDate(expTO.getVisaExpiryDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_IMMIGRATION_VISA_EXPDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_IMMIGRATION_VISA_EXPDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_IMMIGRATION_VISA_EXPDT_INVALID);
						errors
								.add(
										CMSConstants.EMPLOYEE_IMMIGRATION_VISA_EXPDT_INVALID,
										error);
					}
				}

				if (expTO.getVisaIssueDate() != null
						&& !StringUtils.isEmpty(expTO.getVisaIssueDate())
						&& CommonUtil.isValidDate(expTO.getVisaIssueDate())
						&& expTO.getVisaExpiryDate() != null
						&& !StringUtils.isEmpty(expTO.getVisaExpiryDate())
						&& CommonUtil.isValidDate(expTO.getVisaExpiryDate())) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							expTO.getVisaIssueDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(expTO
							.getVisaExpiryDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE,
											new ActionError(
													CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_LARGE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors
								.get(CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME,
											new ActionError(
													CMSConstants.EMPLOYEE_IMMIGRATION_PISSUEDT_PEXPDT_SAME));
						}

					}
				}
			}
		}

	}

	/**
	 * Employee Education Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeEducationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeEducationInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateEducations(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveEducationDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_EDUCATION_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeEducationInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeEducationInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
	}

	/**
	 * Employee work experience Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeExperienceInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeExperienceInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateExperiences(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveExperienceDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_EXPERIENCE_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeExperienceInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeExperienceInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
	}

	/**
	 * Employee languages Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeLanguageInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeLanguageInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);

		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateExperiences(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveLanguageDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_LANGUAGE_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeLanguageInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeExperienceInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
	}

	/**
	 * Employee Job Info. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeJobInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeJobInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateJobinfo(emplyeeTO, errors, infoForm);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance().saveJobDetails(
					infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_JOB_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeJobInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeJobInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);
	}

	/**
	 * @param emplyeeTO
	 * @param errors
	 * @param infoForm
	 */
	private void validateJobinfo(EmployeeTO emplyeeTO, ActionMessages errors,
			EmployeeInfoForm infoForm) {

		if (emplyeeTO.getJobs() != null) {
			Iterator<EmpJobTO> eduItr = emplyeeTO.getJobs().iterator();
			while (eduItr.hasNext()) {
				EmpJobTO expTO = (EmpJobTO) eduItr.next();

				if (expTO.getJoiningDate() != null
						&& !StringUtils.isEmpty(expTO.getJoiningDate())
						&& !CommonUtil.isValidDate(expTO.getJoiningDate())) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_JOINDT_INVALID) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_JOINDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_JOINDT_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_JOINDT_INVALID,
								error);
					}
				}
				if (expTO.getDateOfRetirement() != null	&& !StringUtils.isEmpty(expTO.getDateOfRetirement())&& !CommonUtil.isValidDate(expTO.getDateOfRetirement())) 
				{
					if (errors.get(CMSConstants.EMPLOYEE_JOB_RETIREDT_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_JOB_RETIREDT_INVALID).hasNext()) 
					{
						ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_JOB_RETIREDT_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_RETIREDT_INVALID,error);
					}
				}
				
				if (expTO.getDateOfRejoin() != null	&& !StringUtils.isEmpty(expTO.getDateOfRejoin())&& !CommonUtil.isValidDate(expTO.getDateOfRejoin())) 
				{
					if (errors.get(CMSConstants.EMPLOYEE_JOB_REJOIN_INVALID) != null && !errors.get(CMSConstants.EMPLOYEE_JOB_REJOIN_INVALID).hasNext()) 
					{
						ActionMessage error = new ActionMessage(CMSConstants.EMPLOYEE_JOB_REJOIN_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_REJOIN_INVALID,error);
					}
				}

				if (expTO.getJoiningDate() != null
						&& !StringUtils.isEmpty(expTO.getJoiningDate())
						&& CommonUtil.isValidDate(expTO.getJoiningDate())
						&& expTO.getDateOfRetirement() != null
						&& !StringUtils.isEmpty(expTO.getDateOfRetirement())
						&& CommonUtil.isValidDate(expTO.getDateOfRetirement())) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							expTO.getJoiningDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(expTO
							.getDateOfRetirement(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE,
											new ActionError(
													CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors
								.get(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_SAME) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_SAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_SAME,
											new ActionError(
													CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_SAME));
						}

					}
				}

				if (expTO.getDateOfResign() != null
						&& !StringUtils.isEmpty(expTO.getDateOfResign())
						&& !CommonUtil.isValidDate(expTO.getDateOfResign())) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_RESIGNDT_INVALID) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_RESIGNDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_RESIGNDT_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_RESIGNDT_INVALID,
								error);
					}
				}
				if (expTO.getDateOfLeaving() != null
						&& !StringUtils.isEmpty(expTO.getDateOfLeaving())
						&& !CommonUtil.isValidDate(expTO.getDateOfLeaving())) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_LEAVEDT_INVALID,
								error);
					}
				}

				// if(expTO.getDateOfResign()!=null &&
				// !StringUtils.isEmpty(expTO.getDateOfResign()) &&
				// CommonUtil.isValidDate(expTO.getDateOfResign())
				// && expTO.getDateOfLeaving()!=null &&
				// !StringUtils.isEmpty(expTO.getDateOfLeaving()) &&
				// CommonUtil.isValidDate(expTO.getDateOfLeaving()))
				// {
				// String
				// formdate=CommonUtil.ConvertStringToDateFormat(expTO.getDateOfResign(),
				// EmployeeInfoAction.FROM_DATEFORMAT,EmployeeInfoAction.TO_DATEFORMAT);
				// String
				// todate=CommonUtil.ConvertStringToDateFormat(expTO.getDateOfLeaving(),
				// EmployeeInfoAction.FROM_DATEFORMAT,EmployeeInfoAction.TO_DATEFORMAT);
				// Date startdate=new Date(formdate);
				// Date enddate=new Date(todate);
				//					
				//					
				// if(startdate.compareTo(enddate)==1)
				// {
				// if
				// (errors.get(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE)!=null
				// &&
				// !errors.get(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE).hasNext())
				// {
				// errors.add(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE,
				// new
				// ActionError(CMSConstants.EMPLOYEE_JOB_JOINDT_RETIREDT_LARGE));
				// }
				// }
				//						
				// }

				if (expTO.getBasicPay() != null
						&& !StringUtils.isEmpty(expTO.getBasicPay())
						&& !CommonUtil.isValidDecimal(expTO.getBasicPay())) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_BASIC_INVALID) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_BASIC_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_BASIC_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_BASIC_INVALID,
								error);
					}
				}
				if (expTO.getGrossPay() != null
						&& !StringUtils.isEmpty(expTO.getGrossPay())
						&& !CommonUtil.isValidDecimal(expTO.getGrossPay())) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_GROSS_INVALID) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_GROSS_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_GROSS_INVALID);
						errors.add(CMSConstants.EMPLOYEE_JOB_GROSS_INVALID,
								error);
					}
				}

				if (expTO.getFinancialAssisstance() != null
						&& !StringUtils
								.isEmpty(expTO.getFinancialAssisstance())
						&& expTO.getFinancialAssisstance().length() > 250) {
					if (errors.get(CMSConstants.EMPLOYEE_JOB_FINANCE_LARGE) != null
							&& !errors.get(
									CMSConstants.EMPLOYEE_JOB_FINANCE_LARGE)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_JOB_FINANCE_LARGE);
						errors.add(CMSConstants.EMPLOYEE_JOB_FINANCE_LARGE,
								error);
					}
				}

				if (infoForm.getAllowances() != null) {
					Iterator<EmpJobAllowanceTO> alwItr = infoForm
							.getAllowances().iterator();
					while (alwItr.hasNext()) {
						EmpJobAllowanceTO allowanceTO = (EmpJobAllowanceTO) alwItr
								.next();

						if (allowanceTO.getAmount() != null
								&& !StringUtils
										.isEmpty(allowanceTO.getAmount())
								&& !CommonUtil.isValidDecimal(allowanceTO
										.getAmount())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_ALLOWANCE_AMOUNT_INVALID) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_ALLOWANCE_AMOUNT_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_ALLOWANCE_AMOUNT_INVALID);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_ALLOWANCE_AMOUNT_INVALID,
												error);
							}
						}

					}
				}

				if (infoForm.getLeaveTOs() != null) {
					Iterator<EmpLeaveTO> leaveItr = infoForm.getLeaveTOs()
							.iterator();
					while (leaveItr.hasNext()) {
						EmpLeaveTO leaveTO = (EmpLeaveTO) leaveItr.next();

						if (leaveTO.getLeavesAllocated() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesAllocated())
								&& !StringUtils.isNumeric(leaveTO
										.getLeavesAllocated())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOCATED_INVALID) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOCATED_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOCATED_INVALID);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOCATED_INVALID,
												error);
							}
						}

						if (leaveTO.getLeavesRemaining() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesRemaining())
								&& !StringUtils.isNumeric(leaveTO
										.getLeavesRemaining())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_LEAVE_REMAINING_INVALID) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_LEAVE_REMAINING_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_LEAVE_REMAINING_INVALID);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_LEAVE_REMAINING_INVALID,
												error);
							}
						}
						if (leaveTO.getLeavesSanctioned() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesSanctioned())
								&& !StringUtils.isNumeric(leaveTO
										.getLeavesSanctioned())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_LEAVE_SANCTIONED_INVALID) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_LEAVE_SANCTIONED_INVALID)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_LEAVE_SANCTIONED_INVALID);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_LEAVE_SANCTIONED_INVALID,
												error);
							}
						}

						if (leaveTO.getLeavesAllocated() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesAllocated())
								&& StringUtils.isNumeric(leaveTO
										.getLeavesAllocated())
								&& leaveTO.getLeavesRemaining() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesRemaining())
								&& StringUtils.isNumeric(leaveTO
										.getLeavesRemaining())
								&& Integer.parseInt(leaveTO
										.getLeavesAllocated()) < Integer
										.parseInt(leaveTO.getLeavesRemaining())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_REMAIN_LARGE) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_REMAIN_LARGE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_REMAIN_LARGE);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_REMAIN_LARGE,
												error);
							}
						}
						if (leaveTO.getLeavesAllocated() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesAllocated())
								&& StringUtils.isNumeric(leaveTO
										.getLeavesAllocated())
								&& leaveTO.getLeavesSanctioned() != null
								&& !StringUtils.isEmpty(leaveTO
										.getLeavesSanctioned())
								&& StringUtils.isNumeric(leaveTO
										.getLeavesSanctioned())
								&& Integer.parseInt(leaveTO
										.getLeavesAllocated()) < Integer
										.parseInt(leaveTO.getLeavesSanctioned())) {
							if (errors
									.get(CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_SANCTION_LARGE) != null
									&& !errors
											.get(
													CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_SANCTION_LARGE)
											.hasNext()) {
								ActionMessage error = new ActionMessage(
										CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_SANCTION_LARGE);
								errors
										.add(
												CMSConstants.EMPLOYEE_JOB_LEAVE_ALLOW_SANCTION_LARGE,
												error);
							}
						}

					}
				}

			}
		}
	}

	/**
	 * Employee achievements. page load
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addAchievements(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter addAchievements...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			List<EmpAcheivementTO> newlist = new ArrayList<EmpAcheivementTO>();
			// 2 blank records
			newlist.add(new EmpAcheivementTO());
			newlist.add(new EmpAcheivementTO());

			infoForm.setNewAchievementTOs(newlist);

		} catch (Exception e) {
			log.error("error in addAchievements...", e);

			throw e;

		}

		log.info("exit addAchievements...");
		return mapping.findForward(CMSConstants.EMPLOYEE_ACHIEVEMENT_PAGE);
	}

	/**
	 * Employee achievements. save
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward submitAchievements(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter submitAchievements...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_ACHIEVEMENT_PAGE);
			}

			EmployeeInfoHandler.getInstance().addAchievements(infoForm);

		} catch (Exception e) {
			log.error("error in submitAchievements...", e);

			throw e;

		}

		log.info("exit submitAchievements...");
		return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);
	}

	/**
	 * @param emplyeeTO
	 * @param errors
	 */
	private void validateExperiences(EmployeeTO emplyeeTO, ActionMessages errors) {
		if (emplyeeTO.getExperiences() != null) {
			Iterator<EmpWorkExperienceTO> eduItr = emplyeeTO.getExperiences()
					.iterator();
			while (eduItr.hasNext()) {
				EmpWorkExperienceTO expTO = (EmpWorkExperienceTO) eduItr.next();

				if (expTO.getStartDate() != null
						&& !StringUtils.isEmpty(expTO.getStartDate())
						&& !CommonUtil.isValidDate(expTO.getStartDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID);
						errors.add(
								CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID,
								error);
					}
				}
				if (expTO.getEndDate() != null
						&& !StringUtils.isEmpty(expTO.getEndDate())
						&& !CommonUtil.isValidDate(expTO.getEndDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID);
						errors.add(
								CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID,
								error);
					}
				}

				if (expTO.getStartDate() != null
						&& !StringUtils.isEmpty(expTO.getStartDate())
						&& CommonUtil.isValidDate(expTO.getStartDate())
						&& expTO.getEndDate() != null
						&& !StringUtils.isEmpty(expTO.getEndDate())
						&& CommonUtil.isValidDate(expTO.getEndDate())) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							expTO.getStartDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(expTO
							.getEndDate(), EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE,
											new ActionError(
													CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors
								.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME,
											new ActionError(
													CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME));
						}

					}
				}
			}
		}

	}

	/**
	 * validates education field
	 * 
	 * @param emplyeeTO
	 * @param errors
	 */
	private void validateEducations(EmployeeTO emplyeeTO, ActionMessages errors) {
		if (emplyeeTO.getEducations() != null) {
			Iterator<EmpEducationTO> eduItr = emplyeeTO.getEducations()
					.iterator();
			while (eduItr.hasNext()) {
				EmpEducationTO eduTO = (EmpEducationTO) eduItr.next();
				if (eduTO.getGpaGrade() != null
						&& !StringUtils.isEmpty(eduTO.getGpaGrade())
						&& !CommonUtil.isValidDecimal(eduTO.getGpaGrade())) {
					if (errors.get(CMSConstants.EMPLOYEE_EDUCATION_GPA_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_GPA_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_GPA_INVALID);
						errors.add(CMSConstants.EMPLOYEE_EDUCATION_GPA_INVALID,
								error);
					}
				}

				if (eduTO.getYear() != null
						&& !StringUtils.isEmpty(eduTO.getYear())
						&& !StringUtils.isNumeric(eduTO.getYear())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_EDUCATION_YEAR_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_YEAR_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_YEAR_INVALID);
						errors.add(
								CMSConstants.EMPLOYEE_EDUCATION_YEAR_INVALID,
								error);
					}
				}

				if (eduTO.getStartDate() != null
						&& !StringUtils.isEmpty(eduTO.getStartDate())
						&& !CommonUtil.isValidDate(eduTO.getStartDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID);
						errors.add(
								CMSConstants.EMPLOYEE_EDUCATION_STDT_INVALID,
								error);
					}
				}
				if (eduTO.getEndDate() != null
						&& !StringUtils.isEmpty(eduTO.getEndDate())
						&& !CommonUtil.isValidDate(eduTO.getEndDate())) {
					if (errors
							.get(CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID) != null
							&& !errors
									.get(
											CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID)
									.hasNext()) {
						ActionMessage error = new ActionMessage(
								CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID);
						errors.add(
								CMSConstants.EMPLOYEE_EDUCATION_ENDDT_INVALID,
								error);
					}
				}

				if (eduTO.getStartDate() != null
						&& !StringUtils.isEmpty(eduTO.getStartDate())
						&& CommonUtil.isValidDate(eduTO.getStartDate())
						&& eduTO.getEndDate() != null
						&& !StringUtils.isEmpty(eduTO.getEndDate())
						&& CommonUtil.isValidDate(eduTO.getEndDate())) {
					String formdate = CommonUtil.ConvertStringToDateFormat(
							eduTO.getStartDate(),
							EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					String todate = CommonUtil.ConvertStringToDateFormat(eduTO
							.getEndDate(), EmployeeInfoAction.FROM_DATEFORMAT,
							EmployeeInfoAction.TO_DATEFORMAT);
					Date startdate = new Date(formdate);
					Date enddate = new Date(todate);

					if (startdate.compareTo(enddate) == 1) {
						if (errors
								.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE,
											new ActionError(
													CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_LARGE));
						}
					}
					if (enddate.compareTo(startdate) == 0) {
						if (errors
								.get(CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME) != null
								&& !errors
										.get(
												CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME)
										.hasNext()) {
							errors
									.add(
											CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME,
											new ActionError(
													CMSConstants.EMPLOYEE_EDUCATION_STDT_ENDDT_SAME));
						}

					}
				}
			}
		}

	}

	/**
	 * Phone number validations
	 * 
	 * @param admForm
	 * @param errors
	 */
	private void validatePhone(EmployeeTO employeeDetails, ActionMessages errors) {
		log.info("enter validatePhone..");
		if (errors == null)
			errors = new ActionMessages();

		if (employeeDetails.getPermanentAddressHomeTelephone1() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getPermanentAddressHomeTelephone1())
				&& !StringUtils.isNumeric(employeeDetails
						.getPermanentAddressHomeTelephone1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getPermanentAddressHomeTelephone2() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getPermanentAddressHomeTelephone2())
				&& !StringUtils.isNumeric(employeeDetails
						.getPermanentAddressHomeTelephone2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getPermanentAddressHomeTelephone3() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getPermanentAddressHomeTelephone3())
				&& !StringUtils.isNumeric(employeeDetails
						.getPermanentAddressHomeTelephone3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}

		if (employeeDetails.getPermanentAddressMobile2() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getPermanentAddressMobile2())
				&& !StringUtils.isNumeric(employeeDetails
						.getPermanentAddressMobile2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (employeeDetails.getPermanentAddressMobile3() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getPermanentAddressMobile3())
				&& !StringUtils.isNumeric(employeeDetails
						.getPermanentAddressMobile3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}

		if (employeeDetails.getCurrentAddressHomeTelephone1() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressHomeTelephone1())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressHomeTelephone1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getCurrentAddressHomeTelephone2() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressHomeTelephone2())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressHomeTelephone2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getCurrentAddressHomeTelephone3() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressHomeTelephone3())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressHomeTelephone3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}

		if (employeeDetails.getCurrentAddressWorkTelephone1() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressWorkTelephone1())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressWorkTelephone1())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getCurrentAddressWorkTelephone2() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressWorkTelephone2())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressWorkTelephone2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}
		if (employeeDetails.getCurrentAddressWorkTelephone3() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressWorkTelephone3())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressWorkTelephone3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_PHONE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_PHONE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_PHONE_INVALID, error);
			}
		}

		if (employeeDetails.getCurrentAddressMobile2() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressMobile2())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressMobile2())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		if (employeeDetails.getCurrentAddressMobile3() != null
				&& !StringUtils.isEmpty(employeeDetails
						.getCurrentAddressMobile3())
				&& !StringUtils.isNumeric(employeeDetails
						.getCurrentAddressMobile3())) {
			if (errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID) != null
					&& !errors.get(CMSConstants.ADMISSIONFORM_MOBILE_INVALID)
							.hasNext()) {
				ActionMessage error = new ActionMessage(
						CMSConstants.ADMISSIONFORM_MOBILE_INVALID);
				errors.add(CMSConstants.ADMISSIONFORM_MOBILE_INVALID, error);
			}
		}
		log.info("exit validatePhone..");
	}

	/**
	 * reset check option for validation fail
	 * 
	 * @param applicantDetail
	 */
	private void resetSmoker(EmployeeTO employeeDetail) {
		log.info("enter resetSmoker...");

		if (employeeDetail.isSmoker()) {
			employeeDetail.setSmoker(false);
			employeeDetail.setTempsmoker(true);
		} else {
			employeeDetail.setSmoker(false);
			employeeDetail.setTempsmoker(false);
		}

	}

	/**
	 * reset check option for validation fail
	 * 
	 * @param applicantDetail
	 */
	private void resetInternal(EmployeeTO employeeDetail) {
		log.info("enter resetInternal...");
		if (employeeDetail.getExperiences() != null) {
			Iterator<EmpWorkExperienceTO> eduItr = employeeDetail
					.getExperiences().iterator();
			while (eduItr.hasNext()) {
				EmpWorkExperienceTO expTO = (EmpWorkExperienceTO) eduItr.next();

				if (expTO.isInternal()) {
					expTO.setInternal(false);
					expTO.setTempInternal(true);
				} else {
					expTO.setInternal(false);
					expTO.setTempInternal(false);
				}
			}
		}

	}

	/**
	 * Employee reporting to info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveEmployeeReportingInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter saveEmployeeReportingInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		// validation if needed
		ActionMessages errors = infoForm.validate(mapping, request);
		if (errors == null)
			errors = new ActionMessages();
		try {
			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateImmigrations(emplyeeTO, errors);
			}
			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_REPORTTO_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveReportingDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_REPORTTO_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_REPORTTO_PAGE);

			}
		} catch (Exception e) {
			log.error("error in saveEmployeeReportingInfo...", e);

			throw e;

		}

		log.info("exit saveEmployeeReportingInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_REPORTTO_PAGE);
	}

	/**
	 * reset personal info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetPersonalInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetPersonalInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetPersonalInfo...", e);
			throw e;

		}

		log.info("exit resetPersonalInfo...");
		return mapping.findForward(CMSConstants.INIT_EMPLOYEE_INFO_PAGE);
	}

	/**
	 * reset CONTACT info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetContactInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetContactInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			
				
				if(employeeTo.getCommunicationCountryId()!=null && !employeeTo.getCommunicationCountryId().isEmpty()){
					 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTo.getCommunicationCountryId()));
					request.setAttribute("commStateMap", stateMap);
				}
				if(employeeTo.getPermanentCountryId()!=null && !employeeTo.getPermanentCountryId().isEmpty()){
					 Map<Integer, String> stateMap = CommonAjaxHandler.getInstance().getStatesByCountryId(Integer.parseInt(employeeTo.getPermanentCountryId()));
					request.setAttribute("permStateMap", stateMap);
				}	
			}
		} catch (Exception e) {
			log.error("error in resetContactInfo...", e);

			throw e;

		}

		log.info("exit resetContactInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_CONTACT_PAGE);
	}

	/**
	 * reset Emrg. CONTACT info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetEmergContInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetEmergContInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetEmergContInfo...", e);

			throw e;

		}

		log.info("exit resetEmergContInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EMERG_CONT_PAGE);
	}

	/**
	 * reset dependents info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetDependentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetDependentInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				 List<EmpDependentsTO> dependentTos = employeeTo.getDependents();
				//dependentTos.add(new EmpDependentsTO());

				employeeTo.setDependents(dependentTos);
				infoForm.setEmployeeDetail(employeeTo);
				// private List<EmpDependentsTO> dependents;
			}
		} catch (Exception e) {
			log.error("error in resetDependentInfo...", e);

			throw e;

		}

		log.info("exit resetDependentInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);
	}

	/**
	 * reset immigration info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetImmigrationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetImmigrationInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetImmigrationInfo...", e);

			throw e;

		}

		log.info("exit resetImmigrationInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_IMMIGRATION_PAGE);
	}

	/**
	 * reset job info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetJobInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("enter resetJobInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetJobInfo...", e);

			throw e;

		}

		log.info("exit resetJobInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_JOB_PAGE);
	}

	/**
	 * reset repoting info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetReportingInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetReportingInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetReportingInfo...", e);

			throw e;

		}

		log.info("exit resetReportingInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_REPORTTO_PAGE);
	}

	/**
	 * reset experience info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetExperienceInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetExperienceInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		ActionMessages errors = infoForm.validate(mapping, request);

		try {

			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateExperiences(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveExperienceDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());

				EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
						.getEmployeeDetails(infoForm);
				if (employeeTo != null) {
					infoForm.setEmployeeDetail(employeeTo);
				}
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_EXPERIENCE_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);

			}

			// ======================

		} catch (Exception e) {
			log.error("error in resetExperienceInfo...", e);

			throw e;

		}

		log.info("exit resetExperienceInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
	}

	/**
	 * reset education info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetEducationInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetEducationInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		ActionMessages errors = infoForm.validate(mapping, request);

		try {

			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateEducations(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveEducationDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
						.getEmployeeDetails(infoForm);
				if (employeeTo != null) {
					infoForm.setEmployeeDetail(employeeTo);
				}
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_EDUCATION_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping
						.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);

			}

			// =============

		} catch (Exception e) {
			log.error("error in resetEducationInfo...", e);

			throw e;

		}

		log.info("exit resetEducationInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
	}

	/**
	 * reset skills info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetSkillsInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetSkillsInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;
		ActionMessages errors = infoForm.validate(mapping, request);

		try {

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveSkillDetails(infoForm);
			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());

				EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
						.getEmployeeDetails(infoForm);
				if (employeeTo != null) {
					infoForm.setEmployeeDetail(employeeTo);
				}
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_SKILL_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
			}

			// ==============

		} catch (Exception e) {
			log.error("error in resetSkillsInfo...", e);

			throw e;

		}

		log.info("exit resetSkillsInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
	}

	/**
	 * reset languages info
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward resetLanguageInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetLanguageInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		ActionMessages errors = infoForm.validate(mapping, request);
		try {

			EmployeeTO emplyeeTO = infoForm.getEmployeeDetail();
			if (emplyeeTO != null) {
				validateExperiences(emplyeeTO, errors);
			}

			if (errors != null && !errors.isEmpty()) {
				saveErrors(request, errors);
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
			}

			boolean result = EmployeeInfoHandler.getInstance()
					.saveLanguageDetails(infoForm);

			if (result) {
				resetSmoker(infoForm.getEmployeeDetail());
				resetInternal(infoForm.getEmployeeDetail());
				EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
						.getEmployeeDetails(infoForm);
				if (employeeTo != null) {
					infoForm.setEmployeeDetail(employeeTo);
				}
				ActionMessages messages = new ActionMessages();
				ActionMessage message = new ActionMessage(
						CMSConstants.EMPLOYEE_LANGUAGE_SUBMIT_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);

			}

			// =================

		} catch (Exception e) {
			log.error("error in resetLanguageInfo...", e);

			throw e;

		}

		log.info("exit resetLanguageInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
	}
	
	public ActionForward addMoreDependentInfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetDependentInfo...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			
				List<EmpDependentsTO> dependentTos = infoForm.getEmployeeDetail().getDependents();
				dependentTos.add(new EmpDependentsTO());
				// private List<EmpDependentsTO> dependents;
		} catch (Exception e) {
			log.error("error in resetDependentInfo...", e);

			throw e;

		}

		log.info("exit resetDependentInfo...");
		return mapping.findForward(CMSConstants.EMPLOYEE_DEPENDENT_PAGE);
	}
	
	public ActionForward resetSkillsValues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetSkillsValues...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetSkillsValues...", e);
			throw e;

		}

		log.info("exit resetSkillsValues...");
		return mapping.findForward(CMSConstants.EMPLOYEE_SKILLS_PAGE);
	}
	
	public ActionForward resetEducationValues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetEducationValues...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetEducationValues...", e);
			throw e;

		}

		log.info("exit resetEducationValues...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EDUCATION_PAGE);
	}
	
	public ActionForward resetExperienceValues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetExperienceValues...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetExperienceValues...", e);
			throw e;

		}

		log.info("exit resetExperienceValues...");
		return mapping.findForward(CMSConstants.EMPLOYEE_EXPERIENCE_PAGE);
	}
	
	public ActionForward resetLanguageValues(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("enter resetLanguageValues...");
		EmployeeInfoForm infoForm = (EmployeeInfoForm) form;

		try {
			EmployeeTO employeeTo = EmployeeInfoHandler.getInstance()
					.getEmployeeDetails(infoForm);
			if (employeeTo != null) {
				infoForm.setEmployeeDetail(employeeTo);
			}
		} catch (Exception e) {
			log.error("error in resetLanguageValues...", e);
			throw e;

		}

		log.info("exit resetLanguageValues...");
		return mapping.findForward(CMSConstants.EMPLOYEE_LANGUAGE_PAGE);
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