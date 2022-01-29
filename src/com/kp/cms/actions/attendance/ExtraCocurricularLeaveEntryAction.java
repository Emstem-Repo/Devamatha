package com.kp.cms.actions.attendance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.forms.attendance.ExtraCocurricularLeaveEntryForm;
import com.kp.cms.handlers.attendance.CocurricularAttendnaceEntryHandler;
import com.kp.cms.to.attendance.CocurricularAttendnaceEntryTo;
import com.kp.cms.handlers.attendance.ExtraCocurricularLeaveEntryHandler;
public class ExtraCocurricularLeaveEntryAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(ExtraCocurricularLeaveEntryAction.class);
	/**
	 * 
	 * Performs the Extra co curricular Attendance entry
	 *  .
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	
	public ActionForward initExtraCocurricularLeaveEntry(ActionMapping mapping,	ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of initExtraCocurricularLeaveEntry method in ExtraCocurricularLeaveEntryAction.class");
		ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm = (ExtraCocurricularLeaveEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		extraCocurricularLeaveEntryForm.clearAll();
		HttpSession session = request.getSession();
		String studentid = (String) session.getAttribute("studentid");
		String courseId = (String) session.getAttribute("stuCourseId");
		if(studentid!=null && !studentid.isEmpty() && courseId!=null && !courseId.isEmpty())
		{
			setRequiredDataTOForm(extraCocurricularLeaveEntryForm, request, response);
			Map<Date, CocurricularAttendnaceEntryTo> cocurricularAttendnaceEntryTos = ExtraCocurricularLeaveEntryHandler.getInstance().getCocurricularAttendanceMap(extraCocurricularLeaveEntryForm,studentid,courseId);
			if(cocurricularAttendnaceEntryTos.isEmpty()){
				errors.add(CMSConstants.ERROR, new ActionError(CMSConstants.KNOWLEDGEPRO_ADMISSION_NORECORDSFOUND));
				saveErrors(request, errors);
				extraCocurricularLeaveEntryForm.clearAll();
				setRequiredDataTOForm(extraCocurricularLeaveEntryForm, request, response);
				log.info("Exit initAttendanceReEntrySecondPage - getSelectedCandidates size 0");
			}else{
				
				
				Collection<CocurricularAttendnaceEntryTo> clist=cocurricularAttendnaceEntryTos.values();
				List<CocurricularAttendnaceEntryTo> list=new ArrayList<CocurricularAttendnaceEntryTo>(clist);
				Collections.sort(list);
				extraCocurricularLeaveEntryForm.setList(list);
				extraCocurricularLeaveEntryForm.setCocurricularAttendanceEntryToList(cocurricularAttendnaceEntryTos);
			}
		}
		else
		{
			ActionMessage message = new ActionMessage("knowledgepro.show.attendance.message");
			messages.add("messages", message);
			messages.add("messages",new ActionMessage("knowledgepro.show.attendance.totalmessage"));
			saveMessages(request, messages);
		}
		
		log.debug("end of initExtraCocurricularLeaveEntry method in ExtraCocurricularLeaveEntryAction.class");
		return mapping.findForward(CMSConstants.EXTRA_COCURRICULAR_ATTENDANCE_ENTRY);
	}
	private void setRequiredDataTOForm(ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm,HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("call of setRequiredDataTOForm method in ExtraCocurricularLeaveEntryAction.class");
		Map<Integer, String> cocurricularActivity= new HashMap<Integer, String>();
		cocurricularActivity =  CocurricularAttendnaceEntryHandler.getInstance().getActivityMap();
		extraCocurricularLeaveEntryForm.setCocurriculartActivityMap(cocurricularActivity);
		
		
		log.debug("end of setRequiredDataTOForm method in ExtraCocurricularLeaveEntryAction.class");
		
	}
	public ActionForward  saveCocurricularDetails(ActionMapping mapping,	ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		log.debug("call of saveCocurricularDetails method in ExtraCocurricularLeaveEntryAction.class");
		ExtraCocurricularLeaveEntryForm extraCocurricularLeaveEntryForm = (ExtraCocurricularLeaveEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isAdded = false;
		try
		{
			isAdded = ExtraCocurricularLeaveEntryHandler.getInstance().saveCocurricularDetails(extraCocurricularLeaveEntryForm);
			if(isAdded)
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.studentLogin.extra.curricular.leave.entry.subit.success" );
				messages.add("messages", message);
				saveMessages(request, messages);
				extraCocurricularLeaveEntryForm.clearAll();
				setRequiredDataTOForm(extraCocurricularLeaveEntryForm, request,response);
			}
			else
			{
				ActionMessage message = new ActionMessage("knowledgepro.attendance.studentLogin.extra.curricular.leave.entry.subit.failed" );
				messages.add("messages", message);
				saveMessages(request, messages);
				setRequiredDataTOForm(extraCocurricularLeaveEntryForm, request,response);
			}
			
		}
		catch (Exception e) {
			log.debug(e.getMessage());
			setRequiredDataTOForm(extraCocurricularLeaveEntryForm,request,response);
			errors.add("error", new ActionError(e.getMessage()));
	 		saveErrors(request,errors);
	 		return mapping.findForward(CMSConstants.EXTRA_COCURRICULAR_ATTENDANCE_ENTRY);
			// TODO: handle exception
		}
		log.debug("end of saveCocurricularDetails method in ExtraCocurricularLeaveEntryAction.class");
		//return mapping.findForward(CMSConstants.EXTRA_COCURRICULAR_ATTENDANCE_ENTRY_SUCCES);
		return initExtraCocurricularLeaveEntry(mapping, extraCocurricularLeaveEntryForm, request, response);

	}
	
	
}
