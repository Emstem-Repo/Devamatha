package com.kp.cms.actions.hostel;

import java.util.List;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ResidentStudentInfoForm;
import com.kp.cms.handlers.hostel.HostelCheckinHandler;
import com.kp.cms.handlers.hostel.ResidentStudentInfoHandler;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.ResidentStudentInfoTO;

public class ResidentStudentInfoAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(ResidentStudentInfoAction.class);
	
	
	/**
	 * Method to set the required data to the form to display it in residentStudentInfo.jsp
	 */
	public ActionForward initResidentStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initResidentStudent in ResidentStudentInfoAction");
		ResidentStudentInfoForm residentStudentInfoForm = (ResidentStudentInfoForm) form;
		residentStudentInfoForm.resetFields();
		setHostelEntriesToForm(residentStudentInfoForm);
		log.info("Exit from initResidentStudent in ResidentStudentInfoAction");
		return mapping.findForward(CMSConstants.INIT_RESIDENT_STUDENT);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchResidentStudent(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered searchResidentStudent in ResidentStudentInfoAction");
		ResidentStudentInfoForm residentStudentInfoForm = (ResidentStudentInfoForm) form;
		ActionErrors errors = new ActionErrors();
		validateStudentDetails(residentStudentInfoForm,errors);
		if(errors.isEmpty()){
			try{
				ResidentStudentInfoHandler handler=ResidentStudentInfoHandler.getInstance();
				if(residentStudentInfoForm.getStudentId().trim()!=null && !residentStudentInfoForm.getStudentId().trim().isEmpty()){
					ResidentStudentInfoTO residentTo=handler.searchStudentById(residentStudentInfoForm.getStudentId(),residentStudentInfoForm.getHostelId());
					if(residentTo==null){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_RESIDENT_STUDENT);
					}
					else{
						residentStudentInfoForm.setRto(residentTo);
						return mapping.findForward(CMSConstants.SEARCH_RESIDENT_STUDENT);
					}
				}else if(residentStudentInfoForm.getStudentName().trim()!=null && !residentStudentInfoForm.getStudentName().trim().isEmpty()){
					List<ResidentStudentInfoTO> StudentList=handler.searchStudentByName(residentStudentInfoForm.getStudentName(),residentStudentInfoForm.getHostelId());
					if(StudentList==null ||StudentList.isEmpty()){
						errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
						saveErrors(request, errors);
						return mapping.findForward(CMSConstants.INIT_RESIDENT_STUDENT);
					}else{
						residentStudentInfoForm.setList(StudentList);
						return mapping.findForward(CMSConstants.SEARCH_BY_NAME);
					}
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				residentStudentInfoForm.setErrorMessage(msg);
				residentStudentInfoForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			} 
		
		else{
			addErrors(request, errors);
			log.info("Exit searchResidentStudent Result - searchResidentStudent errors not empty ");
			return mapping.findForward(CMSConstants.INIT_RESIDENT_STUDENT);
		}
			
		log.info("Exit from searchResidentStudent in ResidentStudentInfoAction");
		return mapping.findForward(CMSConstants.SEARCH_RESIDENT_STUDENT);
	}

	/**
	 * Validating the student Details
	 */
	private void validateStudentDetails(ResidentStudentInfoForm residentStudentInfoForm, ActionErrors errors) {
		if((residentStudentInfoForm.getStudentId().trim()==null ||residentStudentInfoForm.getStudentId().trim().isEmpty())&&(residentStudentInfoForm.getStudentName().trim()==null ||residentStudentInfoForm.getStudentName().trim().isEmpty())){
			if (errors.get(CMSConstants.STUDENT_ID_NAME) != null&& !errors.get(CMSConstants.STUDENT_ID_NAME).hasNext()) {
				errors.add(CMSConstants.STUDENT_ID_NAME,new ActionError(CMSConstants.STUDENT_ID_NAME));
			}
		}
		if(residentStudentInfoForm.getStudentId().trim()!=null && !residentStudentInfoForm.getStudentId().trim().isEmpty() && !StringUtils.isNumeric(residentStudentInfoForm.getStudentId().trim())){
			if (errors.get(CMSConstants.STUDENT_ID_NUMBER) != null&& !errors.get(CMSConstants.STUDENT_ID_NUMBER).hasNext()) {
				errors.add(CMSConstants.STUDENT_ID_NUMBER,new ActionError(CMSConstants.STUDENT_ID_NUMBER));
			}
		}
		if(residentStudentInfoForm.getHostelId() == null || residentStudentInfoForm.getHostelId().isEmpty())
		{
			errors.add(CMSConstants.ERRORS, new ActionError(CMSConstants.RESIDENT_STUDENT_HOSTEL_REQUIRED));
		}
	}
	
	/**
	 * getting the student details by HlApplnId
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentByName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered searchResidentStudent in ResidentStudentInfoAction");
		ResidentStudentInfoForm residentStudentInfoForm = (ResidentStudentInfoForm) form;
		ResidentStudentInfoHandler handler=ResidentStudentInfoHandler.getInstance();
		if(residentStudentInfoForm.getHostelApplnId().trim()!=null && !residentStudentInfoForm.getHostelApplnId().trim().isEmpty()){
			ResidentStudentInfoTO residentTo=handler.searchHlApplnById(residentStudentInfoForm.getHostelApplnId());
			residentStudentInfoForm.setRto(residentTo);
		}
		return mapping.findForward(CMSConstants.SEARCH_RESIDENT_STUDENT);
	}
	
	public void setHostelEntriesToForm(ResidentStudentInfoForm residentStudentInfoForm) throws Exception{
		log.debug("Entering setHostelEntries ResidentStudentInfoAction");
		List<HostelTO> hostelList = HostelCheckinHandler.getInstance().getHostelDetails();
		residentStudentInfoForm.setHostelList(hostelList);
		log.debug("Exiting setHostelEntries of ResidentStudentInfoAction ");
	}
	
}
