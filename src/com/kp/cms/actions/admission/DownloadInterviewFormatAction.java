package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DownloadInterviewFormatForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admission.DownloadInterviewFormatHandler;
import com.kp.cms.handlers.admission.UploadInterviewResultHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.utilities.CommonUtil;

public class DownloadInterviewFormatAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(DownloadInterviewFormatAction.class);
	/**
	 * Method to set the required data to the form to display it in ScoreSheet.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDownloadInterview(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initDownloadInterview input");
		DownloadInterviewFormatForm downloadInterviewFormatForm = (DownloadInterviewFormatForm) form;
		downloadInterviewFormatForm.resetFields();
		setRequiredDatatoForm(downloadInterviewFormatForm, request);
		log.info("Exit initDownloadInterview input");
		
		return mapping.findForward(CMSConstants.DOWNLOAD_FORMAT_INIT);
	}
	
	/**
	 * Method to set all active Program Types to the form
	 * @param interviewBatchEntryForm
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(DownloadInterviewFormatForm downloadInterviewFormatForm, HttpServletRequest request) throws Exception {
		List<ProgramTypeTO> programTypeList=ProgramTypeHandler.getInstance().getProgramType();
		if(programTypeList != null){
			downloadInterviewFormatForm.setProgramTypeList(programTypeList);
		}
		Map collegeMap;
		Map<Integer,String> courseMap;
		if(downloadInterviewFormatForm.getCourseId()!=null && downloadInterviewFormatForm.getYear()!=null &&
				downloadInterviewFormatForm.getCourseId().length()>0 && downloadInterviewFormatForm.getYear().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByCourse
			(Integer.valueOf(downloadInterviewFormatForm.getCourseId()),Integer.valueOf(downloadInterviewFormatForm.getYear()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(downloadInterviewFormatForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		else if(downloadInterviewFormatForm.getProgramId()!= null && downloadInterviewFormatForm.getYear() != null &&
				downloadInterviewFormatForm.getProgramId().length() >0 && downloadInterviewFormatForm.getYear().length()>0){
			collegeMap = CommonAjaxHandler.getInstance().getInterviewTypeByProgram(Integer.valueOf(downloadInterviewFormatForm.getProgramId()),Integer.valueOf(downloadInterviewFormatForm.getYear()));
			request.setAttribute("interviewMap", collegeMap);
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.valueOf(downloadInterviewFormatForm.getProgramId()));
			request.setAttribute("coursesMap", courseMap);
		}
		if(downloadInterviewFormatForm.getInterviewTypeids()!=null && downloadInterviewFormatForm.getInterviewTypeids().length>0){
			Map<Integer, String> interviewSubroundsMap = CommonAjaxHandler.getInstance()
			.getInterviewSubroundsByInterviewTypeForMultiSelect(downloadInterviewFormatForm.getInterviewTypeids());
			request.setAttribute("interviewSubroundsMap",interviewSubroundsMap);
		}
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getCandidates(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		
		DownloadInterviewFormatForm downloadInterviewFormatForm = (DownloadInterviewFormatForm) form;
		downloadInterviewFormatForm.setExport(false);
		 ActionErrors errors = downloadInterviewFormatForm.validate(mapping, request);
		validateTime(downloadInterviewFormatForm, errors,request);
		int interviewPerPanel=0;
		if(errors.isEmpty()){
			//validate if errors is Empty
			interviewPerPanel=validateInterviewPerPanel(downloadInterviewFormatForm,errors);
		}
		if (errors.isEmpty()) {
			try {
				List<InterviewResultTO> selectedCandidates =DownloadInterviewFormatHandler.getInstance().getCandidates(downloadInterviewFormatForm,request);
				downloadInterviewFormatForm.resetFields();
				if(selectedCandidates==null || selectedCandidates.isEmpty()){
					errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
					saveErrors(request, errors);
					setRequiredDatatoForm(downloadInterviewFormatForm,request);
					return mapping.findForward(CMSConstants.DOWNLOAD_FORMAT_INIT);
				}else{
					UploadInterviewResultHandler.getInstance().exportToCSV(interviewPerPanel,selectedCandidates,request);
					downloadInterviewFormatForm.setExport(true);
				}
				
			}  catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				downloadInterviewFormatForm.setErrorMessage(msg);
				downloadInterviewFormatForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			setRequiredDatatoForm(downloadInterviewFormatForm, request);			
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.DOWNLOAD_FORMAT_INIT);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.DOWNLOAD_FORMAT_INIT);
	}

	/**
	 * @param downloadInterviewFormatForm
	 * @param errors
	 */
	private int validateInterviewPerPanel(DownloadInterviewFormatForm downloadInterviewFormatForm,ActionErrors errors) throws Exception {
		int interviewPerPanel=0;
		Map<Integer,Integer> interviewPerPanelMap;
		if(downloadInterviewFormatForm.getInterviewSubRoundIds()!=null && downloadInterviewFormatForm.getInterviewSubRoundIds().length!=0){
			interviewPerPanelMap=DownloadInterviewFormatHandler.getInstance().getInterviewPerPanel(downloadInterviewFormatForm.getInterviewSubRoundIds(),"Sub Round");
		}else{
			interviewPerPanelMap=DownloadInterviewFormatHandler.getInstance().getInterviewPerPanel(downloadInterviewFormatForm.getInterviewTypeids(),"Main Round");
		}
		if(!interviewPerPanelMap.isEmpty()){
			ArrayList<Integer> ar=new ArrayList<Integer>();
			ar.addAll(interviewPerPanelMap.values());
			int i=ar.get(0);
			Iterator<Integer> itr=ar.iterator();
			while (itr.hasNext()) {
				Integer integer = (Integer) itr.next();
				if(integer!=i){
					errors.add(CMSConstants.ERRORS, new ActionError("knowledgepro.admission.download.interviewresult.interviewperpanel"));
					break;
				}
			}
			if(errors.isEmpty()){
				interviewPerPanel=i;
			}
		}
		return interviewPerPanel;
	}

	/**
	 * @param downloadInterviewFormatForm
	 * @param errors
	 */
	private void validateTime(DownloadInterviewFormatForm downloadInterviewFormatForm,ActionErrors errors,HttpServletRequest request) throws Exception {
		
		String[] str = request.getParameterValues("interviewTypeids");
		if(str==null || str.length==0){
				errors.add(CMSConstants.ERROR,new ActionError("errors.required","Interview Type"));
		}
		if(downloadInterviewFormatForm.getInterviewTypeids()!=null && downloadInterviewFormatForm.getInterviewTypeids().length!=0){
			Map<Integer, String> map=CommonAjaxHandler.getInstance().getInterviewSubroundsByInterviewTypeForMultiSelect(downloadInterviewFormatForm.getInterviewTypeids());
			if(map!=null && map.size()>0){
				String[] str1 = request.getParameterValues("interviewSubRoundIds");
				if(str1==null || str1.length==0){
						errors.add(CMSConstants.ERROR,new ActionError("errors.required","Interview Sub Round"));
				}
			}
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getStartingTimeHours())){
			if(!StringUtils.isNumeric(downloadInterviewFormatForm.getStartingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getStartingTimeMins())){
			if(!StringUtils.isNumeric(downloadInterviewFormatForm.getStartingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getStartingTimeHours())){
			if(Integer.parseInt(downloadInterviewFormatForm.getStartingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getStartingTimeMins())){
			if(Integer.parseInt(downloadInterviewFormatForm.getStartingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getEndingTimeHours())){
			if(!StringUtils.isNumeric(downloadInterviewFormatForm.getEndingTimeHours())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getEndingTimeMins())){
			if(!StringUtils.isNumeric(downloadInterviewFormatForm.getEndingTimeMins())){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC,new ActionError(CMSConstants.KNOWLEDGEPRO_INTERVIEW_TIMENUMARIC));
				}
			}			
		}
		
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getEndingTimeHours())){
			if(Integer.parseInt(downloadInterviewFormatForm.getEndingTimeHours())>=24){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
		if(CommonUtil.checkForEmpty(downloadInterviewFormatForm.getEndingTimeMins())){
			if(Integer.parseInt(downloadInterviewFormatForm.getEndingTimeMins())>=60){
				if(errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT)!=null && !errors.get(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT).hasNext()){									
					errors.add(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT,new ActionError(CMSConstants.KNOWLEDGEPRO_ADMIN_TIMEFORMAT));
				}
			}			
		}
	}
}
