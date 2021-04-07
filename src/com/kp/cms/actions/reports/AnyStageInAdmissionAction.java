package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.reports.AnyStageInAdmissionForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.CandidateSearchHandler;
import com.kp.cms.handlers.reports.AnyStageInAdmissionHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class AnyStageInAdmissionAction extends BaseDispatchAction{

	private static Log log = LogFactory.getLog(AnyStageInAdmissionAction.class);
	
	public ActionForward initCandidateSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initCandidateSearch..");
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		anyStageInAdmissionForm.resetFields();
		HttpSession session = request.getSession(false);	
		session.removeAttribute("studentSearch");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		session.removeAttribute(CMSConstants.CSV_BYTES);		
		setRequiredDatatoForm((AnyStageInAdmissionForm) form, request);
		log.info("exit initCandidateSearch..");
			return mapping.findForward("initcandidateSearch");
	}
	/**
	 * @param AnyStageInAdmissionForm
	 * @param request
	 * This method is used to get program type and set it to form
	 */
	private void setRequiredDatatoForm(AnyStageInAdmissionForm anyStageInAdmissionForm,
			HttpServletRequest request) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);
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
	public ActionForward submitCandidateSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		log.info("entered submitCandidateSearch..");
		HttpSession session = request.getSession(false);
		if(session.getAttribute("studentSearch")==null){
			 ActionErrors errors = anyStageInAdmissionForm.validate(mapping, request);
			validateForNumaric(anyStageInAdmissionForm,errors);
			if (errors.isEmpty()) {	
				try {
					 List candidateSearch = AnyStageInAdmissionHandler.getInstance().getStudentSearchResults(anyStageInAdmissionForm);
					if(candidateSearch !=null){
						anyStageInAdmissionForm.setStudentSearch(candidateSearch);
						session.setAttribute("studentSearch",candidateSearch );
					}
				}catch(ApplicationException ae){
					String msg = super.handleApplicationException(ae);
					anyStageInAdmissionForm.setErrorMessage(msg);
					anyStageInAdmissionForm.setErrorStack(ae.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} 
				catch (Exception e) {
					throw e;
				}
			} else {
				addErrors(request, errors);
				setRequiredDatatoForm(anyStageInAdmissionForm, request);
				return mapping.findForward(CMSConstants.INIT_CANDIDATE_SEARCH);
			}
		}	
		log.info("exit submitCandidateSearch..");
		return mapping.findForward("anystageadmissionresult");
	}
	// export to excel
	public ActionForward initexportToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initexportToExcel..");
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		anyStageInAdmissionForm.setSelectedColumnsArray(null);
		anyStageInAdmissionForm.setUnselectedColumnsArray(null);
		CandidateSearchHandler.getInstance().getColumnsReportList(anyStageInAdmissionForm);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_EXCEL);
	}
	
	// export to excel
	public ActionForward exportToExcel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered exportToExcel..");
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = anyStageInAdmissionForm.validate(mapping, request);
		if (errors.isEmpty()) {	
			try {
		
		setUserId(request, anyStageInAdmissionForm);
		boolean isUpdated =	CandidateSearchHandler.getInstance().exportTOExcel(anyStageInAdmissionForm,request);
	
 		if(isUpdated){
 			anyStageInAdmissionForm.setMode("excel");
			anyStageInAdmissionForm.setDownloadExcel("download");
			ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
			messages.add("messages", message);
			saveMessages(request, messages);
//			anyStageInAdmissionForm.clear();
		}
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in CandidateSearchAction",ae);
			String msg = super.handleApplicationException(ae);
			anyStageInAdmissionForm.setErrorMessage(msg);
			anyStageInAdmissionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in CandidateSearchAction",e);
			String msg = super.handleApplicationException(e);
			anyStageInAdmissionForm.setErrorMessage(msg);
			anyStageInAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);			
			CandidateSearchHandler.getInstance().getColumnsReportList(anyStageInAdmissionForm);
			return mapping.findForward(CMSConstants.INIT_EXCEL);
		}
		log.info("exit exportToExcel..");
		return mapping.findForward(CMSConstants.SUBMIT_CANDIDATE_SEARCH);
}
	
	// export to excel
	public ActionForward initexportToCsv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered initexportToCsv..");
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		anyStageInAdmissionForm.setSelectedColumnsArray(null);
		anyStageInAdmissionForm.setUnselectedColumnsArray(null);
		CandidateSearchHandler.getInstance().getColumnsReportList(anyStageInAdmissionForm);
		log.info("exit initexportToCsv..");
		return mapping.findForward(CMSConstants.INIt_CSV);
}
	

//export to CSV.	
	public ActionForward exportToCsv(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered exportToCsv..");
		AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = anyStageInAdmissionForm.validate(mapping, request);
		if (errors.isEmpty()) {	
			try {
		
		setUserId(request, anyStageInAdmissionForm);
		boolean isUpdated =	CandidateSearchHandler.getInstance().exportTOCSV(anyStageInAdmissionForm,request);
	
		if(isUpdated){
 			anyStageInAdmissionForm.setMode("csv");
			anyStageInAdmissionForm.setDownloadCSV("download");
			ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
			messages.add("messages", message);
			saveMessages(request, messages);
//			anyStageInAdmissionForm.clear();
		}
		}catch (ApplicationException ae) {
			log.error("error occured in exportToCsv in CandidateSearchAction",ae);
			String msg = super.handleApplicationException(ae);
			anyStageInAdmissionForm.setErrorMessage(msg);
			anyStageInAdmissionForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToCsv in CandidateSearchAction",e);
			String msg = super.handleApplicationException(e);
			anyStageInAdmissionForm.setErrorMessage(msg);
			anyStageInAdmissionForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		}else {
			addErrors(request, errors);			
			CandidateSearchHandler.getInstance().getColumnsReportList(anyStageInAdmissionForm);
			return mapping.findForward(CMSConstants.INIt_CSV);
		}
		log.info("exit exportToCsv..");
		return mapping.findForward(CMSConstants.SUBMIT_CANDIDATE_SEARCH);
}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * This method will get selected candidates
	 */
	public ActionForward getSelectedCandidatesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered getSelectedCandidatesList..");
		HttpSession session = request.getSession(false);
		if(session.getAttribute("studentSearch")==null){
			AnyStageInAdmissionForm anyStageInAdmissionForm = (AnyStageInAdmissionForm)form;
			ActionMessages messages = new ActionMessages();
			ActionMessage message = null;
	
			try {
				 List candidateSearch = CandidateSearchHandler.getInstance().getStudentSearchResults(anyStageInAdmissionForm);
				if(candidateSearch !=null){
					anyStageInAdmissionForm.setStudentSearch(candidateSearch);
					session.setAttribute("studentSearch",candidateSearch );
					if (candidateSearch.isEmpty()) {
						message = new ActionMessage(
								"knowledgepro.admission.noresultsfound");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				}
			}catch (ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				anyStageInAdmissionForm.setErrorMessage(msg);
				anyStageInAdmissionForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		}	
		log.info("exit getSelectedCandidatesList..");
		return mapping.findForward(CMSConstants.SELECTED_CANDIDATE_RESULT);
	}
	
	/**
	 * 
	 * @param studentSearchForm
	 * @param errors
	 * This method is used to validate numerics 
	 */
	private void validateForNumaric(AnyStageInAdmissionForm anyStageInAdmissionForm,
			ActionErrors errors) {
		log.info("entered validateForNumaric..");
		if (errors == null){
			errors = new ActionErrors();}		
			if(!StringUtils.isNumeric(anyStageInAdmissionForm.getWeightage())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_WEIGHTNUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_WEIGHTNUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_WEIGHTNUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_WEIGHTNUMARIC));
				}
			}
			if(!StringUtils.isNumeric(anyStageInAdmissionForm.getMarksObtained())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PERCENTNUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PERCENTNUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PERCENTNUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_PERCENTNUMARIC));
				}
			}			
			if(anyStageInAdmissionForm.getInterviewStartDate()!=null && !StringUtils.isEmpty(anyStageInAdmissionForm.getInterviewStartDate())&& !CommonUtil.isValidDate(anyStageInAdmissionForm.getInterviewStartDate())){
				if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
					errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
				}
			}
			if(anyStageInAdmissionForm.getInterviewEndDate()!=null && !StringUtils.isEmpty(anyStageInAdmissionForm.getInterviewEndDate())&& !CommonUtil.isValidDate(anyStageInAdmissionForm.getInterviewEndDate())){
				if (errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID) != null&& !errors.get(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID).hasNext()) {
					errors.add(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID,new ActionError(CMSConstants.ATTENDANCE_ATTENDANCE_DATEINVALID));
				}
			}
			if(errors==null ||errors.isEmpty()){
			//if start date greater than end date then showing error message
			if(CommonUtil.checkForEmpty(anyStageInAdmissionForm.getInterviewStartDate()) && CommonUtil.checkForEmpty(anyStageInAdmissionForm.getInterviewEndDate())){
				Date startDate = CommonUtil.ConvertStringToDate(anyStageInAdmissionForm.getInterviewStartDate());
				Date endDate = CommonUtil.ConvertStringToDate(anyStageInAdmissionForm.getInterviewEndDate());

				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(startDate);
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(endDate);
				long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
				if(daysBetween <= 0) {
					errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_STARTDATE_CONNOTBELESS));
				}
			}
			}
			log.info("exit validateForNumaric..");
	}	
}
