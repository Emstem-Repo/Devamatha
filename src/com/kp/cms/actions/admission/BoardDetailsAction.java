package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.forms.admission.BoardDetailsForm;
import com.kp.cms.handlers.admission.BoardDetailsHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admission.BoardDetailsTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class BoardDetailsAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(BoardDetailsAction.class);

	
	public ActionForward initBoardDetailsForTest(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initBoardDetails of BoardDetailsAction");
		BoardDetailsForm boardDetailsForm = (BoardDetailsForm) form;
		try {
			boardDetailsForm.resetFields();
//			// Setting the classMap to request scope
//			//setClassMapToRequest(request, boardDetailsForm);
			//setCourseMapToRequest(request, boardDetailsForm);
//			setYearMapToRequest(request,boardDetailsForm);
			setDataToForm(request,boardDetailsForm);
		} catch (Exception e) {
			log.error( "Error occured in initBoardDetails of BoardDetailsAction", e);
			String msg = super.handleApplicationException(e);
			boardDetailsForm.setErrorMessage(msg);
			boardDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initBoardDetails of BoardDetailsAction");
		return mapping.findForward("test");
	}
	
	
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
	public ActionForward initBoardDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entering into initBoardDetails of BoardDetailsAction");
		BoardDetailsForm boardDetailsForm = (BoardDetailsForm) form;
		try {
			boardDetailsForm.resetFields();
			// Setting the classMap to request scope
			//setClassMapToRequest(request, boardDetailsForm);
			//setCourseMapToRequest(request, boardDetailsForm);
			setDataToForm(request,boardDetailsForm);
		} catch (Exception e) {
			log.error( "Error occured in initBoardDetails of BoardDetailsAction", e);
			String msg = super.handleApplicationException(e);
			boardDetailsForm.setErrorMessage(msg);
			boardDetailsForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving into initBoardDetails of BoardDetailsAction");
		return mapping.findForward(CMSConstants.INIT_BOARD_DETAILS);
	}

	/**
	 * 
	 * @param request
	 * @param boardDetailsForm
	 *            Sets all the classes for the current year in request scope
	 * @throws Exception
	 */
	public void setClassMapToRequest(HttpServletRequest request,
			BoardDetailsForm boardDetailsForm) throws Exception {
		log.info("Entering into setClassMapToRequest of BoardDetailsAction");
		try {
			if (boardDetailsForm.getYear() == null
					|| boardDetailsForm.getYear().isEmpty()) {
				// Below statements is used to get the current year and for the
				// year get the class Map.
				Calendar calendar = Calendar.getInstance();
				int currentYear = calendar.get(Calendar.YEAR);

				// code by hari
				int year = CurrentAcademicYear.getInstance().getAcademicyear();
				if (year != 0) {
					currentYear = year;
				}// end
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
						.getClassesByYear(currentYear);
				request.setAttribute("classMap", classMap);
			}
			// Otherwise get the classMap for the selected year
			else {
				int year = Integer.parseInt(boardDetailsForm.getYear().trim());
				Map<Integer, String> classMap = CommonAjaxHandler.getInstance()
						.getClassesByYear(year);
				request.setAttribute("classMap", classMap);
			}
		} catch (Exception e) {
			log
					.error(
							"Error occured at setClassMapToRequest in  BoardDetailsAction Batch Action",
							e);
			throw new ApplicationException(e);
		}
		log.info("Leaving into setClassMapToRequest of BoardDetailsAction");
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
		BoardDetailsForm boardDetailsForm = (BoardDetailsForm) form;
		 ActionErrors errors = boardDetailsForm.validate(mapping, request);
		if (errors.isEmpty()) {
			try {
				List<BoardDetailsTO> boardList = BoardDetailsHandler
						.getInstance().getListOfCandidates(boardDetailsForm);
				if (boardList.isEmpty()) {
					errors
							.add(
									CMSConstants.ERROR,
									new ActionError(
											CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					//setClassMapToRequest(request, boardDetailsForm);
					//setCourseMapToRequest(request, boardDetailsForm);
					setDataToForm(request,boardDetailsForm);
					log.info("Exit getCandidates size 0");
					return mapping.findForward(CMSConstants.INIT_BOARD_DETAILS);
				}
				boardDetailsForm.setBoardList(boardList);
			} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				boardDetailsForm.setErrorMessage(msg);
				boardDetailsForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			//setClassMapToRequest(request, boardDetailsForm);
			//setCourseMapToRequest(request, boardDetailsForm);
			setDataToForm(request,boardDetailsForm);
			log.info("Exit getCandidates errors not empty ");
			return mapping.findForward(CMSConstants.INIT_BOARD_DETAILS);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.BOARD_DETAILS_RESULT);
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
	public ActionForward updateBoardDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("Entered BoardDetailsAction - getCandidates");
		BoardDetailsForm boardDetailsForm = (BoardDetailsForm) form;
		ActionMessages messages=new ActionMessages();
		ActionErrors errors = boardDetailsForm.validate(mapping, request);
		setUserId(request, boardDetailsForm);
		try {
			// This condition works when reset button will click in update mode
			if (isCancelled(request)) {
				List<BoardDetailsTO> boardList = BoardDetailsHandler.getInstance().getListOfCandidates(boardDetailsForm);
				boardDetailsForm.setBoardList(boardList);
				return mapping.findForward(CMSConstants.BOARD_DETAILS_RESULT);
			}
			validateListForDuplicate(boardDetailsForm);
			if (errors.isEmpty()) {
				Map<String,Integer> examList=BoardDetailsHandler.getInstance().getExistsExamRegNo();
				Map<String,Integer> stuNoList=BoardDetailsHandler.getInstance().getExistStudentNo();
				validateListForDuplicateAlreadyExists(examList,stuNoList,boardDetailsForm);
				boolean isUpdate=BoardDetailsHandler.getInstance().updateDetails(boardDetailsForm);
				if(isUpdate){
					boardDetailsForm.resetFields();
					ActionMessage message = new ActionMessage("knowledgepro.admission.boardDetails.success");
					messages.add("messages", message);
					saveMessages(request, messages);
				}else{
					errors.add("error", new ActionError("knowledgepro.admission.boardDetails.failure"));
					saveErrors(request, errors);
				}
			} else {
				addErrors(request, errors);
				setClassMapToRequest(request, boardDetailsForm);
				log.info("Exit getCandidates errors not empty ");
				return mapping.findForward(CMSConstants.BOARD_DETAILS_RESULT);
			}
		} catch (DuplicateException e) {
			errors.add("error", new ActionError("knowledgepro.admission.boardDetails.duplicateEntry", e.getMessage()));
			saveErrors(request, errors);
			return mapping.findForward(CMSConstants.BOARD_DETAILS_RESULT);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			boardDetailsForm.setErrorMessage(msg);
			boardDetailsForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		setClassMapToRequest(request, boardDetailsForm);
		return mapping.findForward(CMSConstants.INIT_BOARD_DETAILS);
	}

	/**
	 * @param examRegList
	 * @param stuNoList
	 * @param boardDetailsForm
	 * @throws Exception
	 */
	private void validateListForDuplicateAlreadyExists(Map<String,Integer> examRegList,
			Map<String,Integer> stuNoList, BoardDetailsForm boardDetailsForm) throws Exception {
		List<BoardDetailsTO> list = boardDetailsForm.getBoardList();
		Iterator<BoardDetailsTO> iterator = list.iterator();
		int sno = 1;
		StringBuffer slNos = new StringBuffer();
		StringBuffer stNos = new StringBuffer();
		while (iterator.hasNext()) {
			BoardDetailsTO boardDetailsTO = (BoardDetailsTO) iterator.next();
			if ((boardDetailsTO.getExamRegNo() == null || boardDetailsTO
					.getExamRegNo().trim().isEmpty())
					&& (boardDetailsTO.getStudentNo() == null || boardDetailsTO
							.getStudentNo().trim().isEmpty())) {
				sno++;
				continue;
			}
			if (boardDetailsTO.getExamRegNo() != null
					&& !boardDetailsTO.getExamRegNo().trim().isEmpty()) {
				if (examRegList.containsKey(boardDetailsTO.getExamRegNo())) {
					if(boardDetailsTO.getStudentId()!=examRegList.get(boardDetailsTO.getExamRegNo()))
					slNos.append(sno + ",");
				} 
			}
			if (boardDetailsTO.getStudentNo() != null
					&& !boardDetailsTO.getStudentNo().trim().isEmpty()) {
				if (stuNoList.containsKey(boardDetailsTO.getStudentNo())) {
					if(boardDetailsTO.getStudentId()!=stuNoList.get(boardDetailsTO.getStudentNo()))
					stNos.append(sno + ",");
				} 
			}
			sno++;
		}
		String msg = "";
		String slNumbers = slNos.toString();
		if (slNumbers != null && slNumbers.length() > 0) {
			if (slNumbers.endsWith(",")) {
				slNumbers = StringUtils.chop(slNumbers);
				msg = msg + "Duplicate Exam Register No at line no:"
						+ slNumbers;
			}
		}
		String stNumbers = stNos.toString();
		if (stNumbers != null && stNumbers.length() > 0) {
			if (stNumbers.endsWith(",")) {
				stNumbers = StringUtils.chop(stNumbers);
				msg = msg + " and Duplicate Student No at line no:" + stNumbers;
			}
		}
		if (msg != null && !msg.isEmpty()) {
			throw new DuplicateException(msg);
		}
	}

	/**
	 * @param boardDetailsForm
	 * @throws Exception
	 */
	private void validateListForDuplicate(BoardDetailsForm boardDetailsForm)
			throws Exception {
		List<BoardDetailsTO> list = boardDetailsForm.getBoardList();
		Iterator<BoardDetailsTO> iterator = list.iterator();
		List<String> examRegList = new ArrayList<String>();
		List<String> stuNoList = new ArrayList<String>();
		int sno = 1;
		StringBuffer slNos = new StringBuffer();
		StringBuffer stNos = new StringBuffer();
		while (iterator.hasNext()) {
			BoardDetailsTO boardDetailsTO = (BoardDetailsTO) iterator.next();
			if ((boardDetailsTO.getExamRegNo() == null || boardDetailsTO
					.getExamRegNo().trim().isEmpty())
					&& (boardDetailsTO.getStudentNo() == null || boardDetailsTO
							.getStudentNo().trim().isEmpty())) {
				sno++;
				continue;
			}
			if (boardDetailsTO.getExamRegNo() != null
					&& !boardDetailsTO.getExamRegNo().trim().isEmpty()) {
				if (!examRegList.contains(boardDetailsTO.getExamRegNo())) {
					examRegList.add(boardDetailsTO.getExamRegNo());
				} else {
					slNos.append(sno + ",");
				}
			}
			if (boardDetailsTO.getStudentNo() != null
					&& !boardDetailsTO.getStudentNo().trim().isEmpty()) {
				if (!stuNoList.contains(boardDetailsTO.getStudentNo())) {
					stuNoList.add(boardDetailsTO.getStudentNo());
				} else {
					stNos.append(sno + ",");
				}
			}
			sno++;
		}
		String msg = "";
		String slNumbers = slNos.toString();
		if (slNumbers != null && slNumbers.length() > 0) {
			if (slNumbers.endsWith(",")) {
				slNumbers = StringUtils.chop(slNumbers);
				msg = msg + "Duplicate Exam Register No at line no:"
						+ slNumbers;
			}
		}
		String stNumbers = stNos.toString();
		if (stNumbers != null && stNumbers.length() > 0) {
			if (stNumbers.endsWith(",")) {
				stNumbers = StringUtils.chop(stNumbers);
				msg = msg + " and Duplicate Student No at line no:" + stNumbers;
			}
		}
		if (msg != null && !msg.isEmpty()) {
			throw new DuplicateException(msg);
		}
	}
	
	/**
	 * @param request
	 * @param boardDetailsForm
	 * @throws Exception
	 */
	public void setCourseMapToRequest(HttpServletRequest request,
			BoardDetailsForm boardDetailsForm) throws Exception {
		log.info("Entering into setClassMapToRequest of BoardDetailsAction");

		Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}
		if (boardDetailsForm.getYear() != null && !boardDetailsForm.getYear().isEmpty()) {
			currentYear=Integer.parseInt(boardDetailsForm.getYear());
		}
		Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByYear(currentYear);
		//request.setAttribute("courseMap", courseMap);
		boardDetailsForm.setCourseMap(courseMap);
	
		log.info("Leaving into setClassMapToRequest of BoardDetailsAction");
	}
	
	/**
	 * @param request
	 * @param boardDetailsForm
	 * @throws Exception
	 */
	public void setDataToForm(HttpServletRequest request,
			BoardDetailsForm boardDetailsForm)throws Exception{
		Map<Integer,Integer> yearMap=BoardDetailsHandler.getInstance().getyearMap();
		boardDetailsForm.setYearMap(yearMap);
		Map<Integer,String> programMap=BoardDetailsHandler.getInstance().getProgramMap();
		boardDetailsForm.setProgramMap(programMap);
		//request.setAttribute("yearMap", yearMap);
	}
}
