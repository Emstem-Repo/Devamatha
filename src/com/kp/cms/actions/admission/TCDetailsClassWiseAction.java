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
import com.kp.cms.forms.admission.TCDetailsClassWiseForm;
import com.kp.cms.handlers.admission.TCDetailsClassWiseHandler;
import com.kp.cms.handlers.admission.TCDetailsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.to.admission.CharacterAndConductTO;
import com.kp.cms.to.admission.TCDetailsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class TCDetailsClassWiseAction extends BaseDispatchAction {
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
		log.info("Entering into initTCDetails of TCDetailsAction");
		TCDetailsClassWiseForm tcDetailsForm = (TCDetailsClassWiseForm) form;
		try {
			tcDetailsForm.resetFields();
			// Setting the classMap to request scope
			setClassMapToRequest(request, tcDetailsForm);
		} catch (Exception e) {
			log.error("Error occured in initTCDetails of TCDetailsAction",e);
			String msg = super.handleApplicationException(e);
			tcDetailsForm.setErrorMessage(msg);
			tcDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initTCDetails of TCDetailsAction");
		return mapping.findForward(CMSConstants.INIT_TC_DETAILS_CLASS_WISE);
	}

	/**
	 * 
	 * @param request
	 * @param boardDetailsForm
	 *            Sets all the classes for the current year in request scope
	 * @throws Exception
	 */
	public void setClassMapToRequest(HttpServletRequest request,TCDetailsClassWiseForm tcDetailsForm) throws Exception {
		log.info("Entering into setClassMapToRequest of TCDetailsAction");
		try {
			if (tcDetailsForm.getAcademicYear() == null
					|| tcDetailsForm.getAcademicYear().isEmpty()) {
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
				int year = Integer.parseInt(tcDetailsForm.getAcademicYear().trim());
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
		TCDetailsClassWiseForm tcDetailsForm = (TCDetailsClassWiseForm) form;
		 ActionErrors errors = tcDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<BoardDetailsTO> boardList = TCDetailsClassWiseHandler.getInstance().getListOfCandidates(tcDetailsForm);
				if (boardList.isEmpty()) {
					errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setClassMapToRequest(request, tcDetailsForm);
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_TC_DETAILS_CLASS_WISE);
				}
				tcDetailsForm.setBoardList(boardList);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcDetailsForm.setErrorMessage(msg);
				tcDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setClassMapToRequest(request, tcDetailsForm);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_TC_DETAILS_CLASS_WISE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.TC_DETAILS_RESULT_CLASS_WISE);
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
	public ActionForward getTCDetailsClassWise(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		TCDetailsClassWiseForm tcDetailsForm = (TCDetailsClassWiseForm) form;
		ActionErrors errors = new ActionErrors();
		if(tcDetailsForm.getPassedStudents()==null)
		{
			errors.add(CMSConstants.ERROR,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_SELECT_STUDENTS));
			
		}
		if (errors.isEmpty()) {
			try {
				tcDetailsForm.setTcDetailsTO(new TCDetailsTO());
				tcDetailsForm.getTcDetailsTO().setMonth(tcDetailsForm.getMonth());
				tcDetailsForm.getTcDetailsTO().setYear(tcDetailsForm.getYear());
				setDataToForm(tcDetailsForm);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcDetailsForm.setErrorMessage(msg);
				tcDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.TC_DETAILS_RESULT_CLASS_WISE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.TC_DETAILS_CLASS_WISE);
	}
	
	/**
	 * @param tcDetailsForm
	 * @throws Exception
	 */
	private void setDataToForm(TCDetailsClassWiseForm tcDetailsForm) throws Exception {
		List<CharacterAndConductTO> list=TCDetailsHandler.getInstance().getCharacterAndConductList();
		tcDetailsForm.setList(list);
		
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
		TCDetailsClassWiseForm tcDetailsForm = (TCDetailsClassWiseForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = tcDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				boolean isUpdated=TCDetailsClassWiseHandler.getInstance().updateStudentTCDetails(tcDetailsForm);
				if(isUpdated){
					ActionMessage message = new ActionMessage("knowledgepro.admission.tc.details.added.successfully");
					messages.add("messages", message);
					saveMessages(request, messages);
					tcDetailsForm.resetFields();
					// Setting the classMap to request scope
					setClassMapToRequest(request, tcDetailsForm);
				}else{
					// failed
					errors.add("error", new ActionError("knowledgepro.admission.tc.details.added.failure"));
					saveErrors(request, errors);
				}
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				tcDetailsForm.setErrorMessage(msg);
				tcDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.TC_DETAILS_CLASS_WISE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.INIT_TC_DETAILS_CLASS_WISE);
	}
}
