package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admission.TCDetailsForm;
import com.kp.cms.forms.admission.TCFormatDetailsForm;
import com.kp.cms.handlers.admission.TCDetailsHandler;
import com.kp.cms.handlers.admission.TCFormatDetailsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TCFormatDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(BoardDetailsAction.class);

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return Initializes createPracticalBatch Initializes classMap and
	 *         subjectMap
	 * @throws Exception
	 */
	public ActionForward initTCDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initTCDetails of TCFormatDetailsAction");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		try {
			tcFormatDetailsForm.resetFields();
			// Setting the classMap to request scope
			setClassMapToRequest(request, tcFormatDetailsForm);
		} catch (Exception e) {
			log.error("Error occured in initTCDetails of TCFormatDetailsAction",e);
			String msg = super.handleApplicationException(e);
			tcFormatDetailsForm.setErrorMessage(msg);
			tcFormatDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initTCDetails of TCFormatDetailsAction");
		return mapping.findForward(CMSConstants.INIT_FORMAT_TC_DETAILS);
	}

	/**
	 * 
	 * @param request
	 * @param boardDetailsForm
	 *            Sets all the classes for the current year in request scope
	 * @throws Exception
	 */
	public void setClassMapToRequest(HttpServletRequest request,TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		log.info("Entering into setClassMapToRequest of TCDetailsAction");
		try {
			if (tcFormatDetailsForm.getAcademicYear() == null
					|| tcFormatDetailsForm.getAcademicYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance().getClassesByYear(currentYear);
				request.setAttribute("classMap", classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(tcFormatDetailsForm.getAcademicYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
						.getClassesByYear(year);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log.error("Error occured at setClassMapToRequest in  TCDetailsAction Batch Action",e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of TCDetailsAction");
	}
	/**
	 * Method to select the candidates for score sheet result entry based on the
	 * input selected
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		 ActionErrors errors = tcFormatDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<BoardDetailsTO> boardList = TCFormatDetailsHandler.getInstance().getListOfCandidates(tcFormatDetailsForm);
				if (boardList.isEmpty()) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setClassMapToRequest(request, tcFormatDetailsForm);
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_FORMAT_TC_DETAILS);
				}
				tcFormatDetailsForm.setBoardList(boardList);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcFormatDetailsForm.setErrorMessage(msg);
				tcFormatDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, tcFormatDetailsForm);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_FORMAT_TC_DETAILS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.TC_FORMAT_DETAILS_RESULT);
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the
	 * input selected
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getStudentDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		 ActionErrors errors = tcFormatDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				TCFormatDetailsHandler.getInstance().getStudentTCDetails(tcFormatDetailsForm);
				setDataToForm(tcFormatDetailsForm);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcFormatDetailsForm.setErrorMessage(msg);
				tcFormatDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.TC_FORMAT_DETAILS_RESULT);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.TC_FORMAT_DETAILS);
	}
	
	/**
	 * @param tcDetailsForm
	 * @throws Exception
	 */
	private void setDataToForm(TCFormatDetailsForm tcFormatDetailsForm) throws Exception {
		List<CharacterAndConductTO> list=TCDetailsHandler.getInstance().getCharacterAndConductList();
		tcFormatDetailsForm.setList(list);
	}

	/**
	 * Method to select the candidates for score sheet result entry based on the
	 * input selected
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateStudentTCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = tcFormatDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated=TCFormatDetailsHandler.getInstance().updateStudentTCDetails(tcFormatDetailsForm);
				if(isUpdated){
					ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admission.tc.details.added.failure"));
					saveErrors(request, errors);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcFormatDetailsForm.setErrorMessage(msg);
				tcFormatDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.TC_FORMAT_DETAILS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.TC_FORMAT_DETAILS_RESULT);
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
	public ActionForward initUpdateStudentTCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("initUpdateStudentTCDetails");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		try {
			setClassMapToRequest(request, tcFormatDetailsForm);
			TCDetailsTO to = new TCDetailsTO();
			to.setEligible("yes");
			to.setFeePaidUni("yes");
			tcFormatDetailsForm.setTcDetailsTO(to);
			
			setDataToForm(tcFormatDetailsForm);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tcFormatDetailsForm.setErrorMessage(msg);
			tcFormatDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("exit initUpdateStudentTCDetails");
		return mapping.findForward(CMSConstants.INIT_UPDATE_TC_FORMAT_DETAILS);
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
	public ActionForward processUpdateStudentTCDetails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("initUpdateStudentTCDetails");
		TCFormatDetailsForm tcFormatDetailsForm = (TCFormatDetailsForm) form;
		 ActionErrors errors = tcFormatDetailsForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isUpdated = false;
		try {
			
			if (errors.isEmpty()) {
				List<BoardDetailsTO> boardList = TCFormatDetailsHandler.getInstance().getListOfCandidates(tcFormatDetailsForm);
				isUpdated = TCFormatDetailsHandler.getInstance().processUpdateStudentTCDetails(tcFormatDetailsForm, boardList);
			}
			else{
				setClassMapToRequest(request, tcFormatDetailsForm);
				addErrors(request, errors);
				log.info("Exit getCandidates errors not empty ");
				return mapping.findForward(CMSConstants.INIT_UPDATE_TC_FORMAT_DETAILS);
			}
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			tcFormatDetailsForm.setErrorMessage(msg);
			tcFormatDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		if (isUpdated) {
			// success .
			ActionMessage message = new ActionMessage("knowledgepro.admission.tc.update.success");
			messages.add("messages", message);
			saveMessages(request, messages);
		} else {
			// failed
			errors.add("error", new ActionError("knowledgepro.admission.tc.update.failure"));
			saveErrors(request, errors);
			resetUpdateProcessFields(tcFormatDetailsForm);
			return mapping.findForward(CMSConstants.INIT_UPDATE_TC_FORMAT_DETAILS);
		}
		
		resetUpdateProcessFields(tcFormatDetailsForm);
		
		log.info("exit initUpdateStudentTCDetails");
		return mapping.findForward(CMSConstants.INIT_UPDATE_TC_FORMAT_DETAILS);
	} 
	/**
	 * 
	 * @param form
	 */
	public  void resetUpdateProcessFields(TCFormatDetailsForm form){
		form.setClassId(null);
		form.setYear(null);
		form.setAcademicYear(null);
		form.setRegisterNo(null);
		form.setStudentId(null);
		form.setTcDetailsTO(new TCDetailsTO());
		form.setMonth(null);
	}
	
}
