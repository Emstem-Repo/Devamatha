package com.kp.cms.actions.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.exam.ExamProgramTypeUtilBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.employee.EmployeeInfoEditForm;
import com.kp.cms.forms.exam.ExamDefinitionForm;
import com.kp.cms.handlers.employee.EmployeeInfoEditHandler;
import com.kp.cms.handlers.exam.ExamDefinitionHandler;
import com.kp.cms.helpers.exam.ExamDefinationHelper;
import com.kp.cms.to.exam.ExamCourseSchemeDetailsTO;
import com.kp.cms.to.exam.ExamSchemeTO;
import com.kp.cms.to.exam.KeyValueTO;
import com.kp.cms.transactionsimpl.exam.ExamDefinationImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

@SuppressWarnings("deprecation")
public class ExamDefinitionAction extends BaseDispatchAction {

	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	ExamDefinitionHandler handler = new ExamDefinitionHandler();
	/*ExamDefinationImpl impl = new ExamDefinationImpl();
	ExamDefinationHelper helper = new ExamDefinationHelper();*/
	
	// gets initial list of Exam Definition
	public ActionForward initExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		objForm.clearPage(mapping, request);
		errors = objForm.validate(mapping, request);
		setUserId(request, objForm);
		//String[] date = CommonUtil.getTodayDate().split("/");
		int academicYear=CurrentAcademicYear.getInstance().getAcademicyear();
		//int academicYear = Integer.parseInt(date[2].toString());
		objForm.setTempAcademicYear(String.valueOf(academicYear));
		setRequestToList(objForm, request);
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}

	private ExamDefinitionForm setRequestToList(ExamDefinitionForm objForm,
			HttpServletRequest request) throws Exception {
		
		List<KeyValueTO> programTypeList = handler.getProgramTypeList();
		request.setAttribute("programTypeList", programTypeList);
		objForm.setProgramTypeList(programTypeList);
		objForm.setCheckedActiveDummy(true);
		String AcademicYear= objForm.getTempAcademicYear();
		objForm.setExamDefinitionList(handler.getListExamDefinitionBO_main(AcademicYear));
		objForm.setTempAcademicYear(AcademicYear);
		return objForm;
	}
	public ActionForward ExamDefinitionYearSort(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		setRequestToListAcademicYear(objForm, request);
		
		//objForm.setAcademicYear(AcademicYear);
		
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}
	
	public ActionForward ExamDefinitionProgramType(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		//String[] prgType = request.getParameterValues("selectedProgramType");
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}
	
	private ExamDefinitionForm setRequestToListAcademicYear(ExamDefinitionForm objForm,
			HttpServletRequest request) throws Exception {
		String AcademicYear= objForm.getAcademicYear();
		objForm.setExamDefinitionList(handler.getListExamDefinitionBO_main(AcademicYear));
		//objForm.setTempAcademicYear(AcademicYear);
		//objForm.setAcademicYear(AcademicYear);
	
		return objForm;
	}

	/*private ExamDefinitionForm setRequestToListByAcademicYear(HttpServletRequest request) throws Exception {
		ExamDefinitionForm objForm=new ExamDefinitionForm();
		String AcademicYear= objForm.getTempAcademicYear();
		objForm.setExamDefinitionList(handler.getListExamDefinitionBO_main(AcademicYear));

		return objForm;
	}*/
	public ActionForward setRequestToListByAcademicYear(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm=new ExamDefinitionForm();
		try {
			
			String AcYear = request.getParameter("academicYear");
			
			objForm.setExamDefinitionList(handler.getListExamDefinitionBO_main(AcYear));
			//objForm.setAcademicYear(AcYear);
			
		}
		catch (Exception e)
		{
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}
	
	public ActionForward deleteExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		messages.clear();
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		try {
			setUserId(request, objForm);
			handler.deleteExamDefinition(objForm.getExamDefId_progId(), objForm
					.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.examDefinition.deletesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			errors.clear();

		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
		} finally {
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);

	}

	public ActionForward reActivateExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		messages.clear();
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		try {
			setUserId(request, objForm);
			handler.reActivate(objForm.getId(), objForm.getUserId());
			ActionMessage message = new ActionMessage(
					"knowledgepro.exam.examDefinition.activatesuccess");
			messages.add("messages", message);
			saveMessages(request, messages);
			errors.clear();

		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
		} finally {
			setRequestToList(objForm, request);
		}
		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);

	}

	public ActionForward addExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		String[] prgType = request.getParameterValues("selectedProgramType");
		String[] prg = request.getParameterValues("selectedProgram");
		String[] AcademicYear =request.getParameterValues("academicYear");
		int academicYear=Integer.valueOf(AcademicYear[0]);
		
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		// if ((objForm.getSelectedProgramType() != null && objForm
		// .getSelectedProgramType().length > 0)) {
		// String a[] = objForm.getSelectedProgramType();
		//
		// if (null != a[0] && a[0].equals("")) {
		// errors.add("error", new ActionError(
		// "knowledgepro.admission.programtype.required"));
		//
		// }
		// } else {
		// errors.add("error", new ActionError(
		// "knowledgepro.admission.programtype.required"));
		//
		// }
		if(objForm.getExamTypeId() == 5 && (objForm.getRegularExamIdForRedo() == null || !objForm.getRegularExamIdForRedo().isEmpty())) {
			errors.add("error", new ActionError("knowledgepro.exam.definition.regularForRedo.required"));
		}
		if ((objForm.getSelectedProgram() != null && objForm
				.getSelectedProgram().length > 0)) {
			String a[] = objForm.getSelectedProgram();
			if (null != a[0] && a[0].equals("")) {
				errors.add("error", new ActionError(
						"knowledgepro.admission.program.required"));

			}
		}
		// } else {
		//
		// errors.add("error", new ActionError(
		// "knowledgepro.admission.program.required"));
		//
		// }
		try {
			setUserId(request, objForm);

			if (errors.isEmpty()) {
				objForm = handler.getProgramProgramType(prgType, prg, objForm);
				ArrayList<Integer> listOfProgram = new ArrayList<Integer>();
				String[] prog = objForm.getProgramIds().split(",");
				for (String string : prog) {
					listOfProgram.add(Integer.parseInt(string.trim()));
				}
				objForm.setExamCourseSchemeList(handler
						.getCourseSchemeList(objForm.getProgramIds()));
				objForm.setExamCourseSchemeListSize(objForm
						.getExamCourseSchemeList().size());
				if (objForm.getExamCourseSchemeListSize() == 0) {
					errors
							.add(
									"error",
									new ActionError(
											"knowledgepro.exam.examDefinition.CourseScheme.notexists"));
					saveErrors(request, errors);
					setRequestToList(objForm, request);
					objForm.clearPage(mapping, request);
					return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
				}
				objForm.setExamTypeList(handler.getExamTypeList());
				objForm.setInternalExamTypeList(handler
						.getInternalExamTypeList());
				/*String[] date = CommonUtil.getTodayDate().split("/");
				int academicYear = Integer.parseInt(date[2].toString());*/
				objForm.setMapInternalExam(handler
						.getInternalExamListByAcademicYear(academicYear));

				StringBuilder prg_str=new StringBuilder();
				for (int x = 0; x < prg.length; x++) {
					prg_str.append(Integer.parseInt(prg[x])).append(",");
				}
				prg_str.setCharAt(prg_str.length()-1, ' ');
				objForm.setProgramIds(prg_str.toString().trim());
				request.setAttribute("ExamDefinitionOperation", "add");

			} else {
				if (objForm.getSelectedProgramType() != null) {
					objForm.setProgramList(handler.getProgramList(objForm
							.getSelectedProgramType()));
					request.setAttribute("retainValues", "retain");
				}
				setRequestToList(objForm, request);

				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
			}
		} catch (Exception e) {
			errors.clear();
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			setRequestToList(objForm, request);
			return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);

		}

		return mapping.findForward(CMSConstants.EXAM_DEFINITION);
	}

	public ActionForward editExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		messages.clear();
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		try {
			setUserId(request, objForm);
			objForm = handler.getUpdatableForm(objForm);
			request.setAttribute("ExamDefinitionOperation", "edit");
			request.setAttribute("Update", "Update");
		} catch (Exception e) {
			String msg = super.handleApplicationException(e);
			objForm.setErrorMessage(msg);
			objForm.setErrorStack(e.getMessage());
		}
		return mapping.findForward(CMSConstants.EXAM_DEFINITION);

	}

	public ActionForward updateExamDefinition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);
		errors = validate(objForm);

		try {
			setUserId(request, objForm);
			if(objForm.getExamTypeId() == 5 && (objForm.getRegularExamIdForRedo() == null || !objForm.getRegularExamIdForRedo().isEmpty())) {
				errors.add("error", new ActionError("knowledgepro.exam.definition.regularForRedo.required"));
			}
			if (isCancelled(request)) {

				setUserId(request, objForm);
				setRequestToList(objForm, request);
				return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
			}
			if (errors.isEmpty()) {
				handler.update(objForm);
				setRequestToList(objForm, request);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examDefinition.modifiedsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				objForm.clearPage(mapping, request);
				errors.clear();
			} else {
				saveErrors(request, errors);
				setCheckBoxes(objForm);
				request.setAttribute("ExamDefinitionOperation", "edit");
				request.setAttribute("Update", "Update");
				return mapping.findForward(CMSConstants.EXAM_DEFINITION);

			}

		} catch (DuplicateException e1) {

			if (e1.getMessage().equalsIgnoreCase("examCode")) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examDefinition.code.exists"));

			} else {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examDefinition.exists"));
			}
			saveErrors(request, errors);

			objForm.setListProgramTypes(objForm.getListProgramTypes());
			objForm.setListPrograms(objForm.getListPrograms());
			objForm.setProgramIds(objForm.getProgramIds());
			objForm.setExamTypeList(handler.getExamTypeList());
			objForm.setMapInternalExam(handler
					.getInternalExamListByAcademicYear(Integer.parseInt(objForm
							.getAcademicYear())));
			objForm.setInternalExamTypeList(handler.getInternalExamTypeList());

			objForm.setExamCourseSchemeList(handler.getCourseSchemeList(objForm
					.getProgramIds()));
			objForm.setExamCourseSchemeListSize(objForm
					.getExamCourseSchemeList().size());
			setCheckBoxes(objForm);
			request.setAttribute("ExamDefinitionOperation", "edit");
			request.setAttribute("Update", "Update");

			return mapping.findForward(CMSConstants.EXAM_DEFINITION);

		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examDefinition.reactivate", e1.getID()));
			saveErrors(request, errors);
			objForm.clearPage(mapping, request);
		} catch (Exception e) {
			e.printStackTrace();
			objForm.clearPage(mapping, request);

			errors.clear();
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);

		}

		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}

	public ActionForward addExamDefinitionDetails(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ExamDefinitionForm objForm = (ExamDefinitionForm) form;
		errors.clear();
		messages.clear();
		errors = objForm.validate(mapping, request);

		errors = validate(objForm);
		try {
			setUserId(request, objForm);
			if("5".equalsIgnoreCase(String.valueOf(objForm.getExamTypeId()))) {
				if(objForm.getRegularExamIdForRedo() == null || objForm.getRegularExamIdForRedo().isEmpty()) {
					errors.add("error", new ActionError("knowledgepro.exam.definition.regularForRedo.required"));
				}
			}

			if (errors.isEmpty()) {

				handler.add(objForm);
				objForm.clearPage(mapping, request);
				ActionMessage message = new ActionMessage(
						"knowledgepro.exam.examDefinition.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);

			} else {
				saveErrors(request, errors);

				setCheckBoxes(objForm);
				String[] prgType = objForm.getSelectedProgramType();
				String[] prg = objForm.getSelectedProgram();
				objForm.setSchemeType(objForm.getSchemeType());
				objForm = handler.getProgramProgramType(prgType, prg, objForm);
				objForm.setExamTypeList(handler.getExamTypeList());
				objForm.setMapInternalExam(handler.getInternalExamListByAcademicYear(Integer.parseInt(objForm.getAcademicYear())));
				objForm.setInternalExamTypeList(handler.getInternalExamTypeList());

				//objForm.setExamCourseSchemeList(handler.getCourseSchemeList(objForm.getProgramIds()));
				//objForm.setExamCourseSchemeListSize(objForm.getExamCourseSchemeList().size());
				StringBuilder prg_str=new StringBuilder();
				for (int x = 0; x < prg.length; x++) {
					prg_str.append(Integer.parseInt(prg[x])).append(",");
				}
				prg_str.setCharAt(prg_str.length()-1, ' ');
				objForm.setProgramIds(prg_str.toString().trim());
				request.setAttribute("ExamDefinitionOperation", "add");
				return mapping.findForward(CMSConstants.EXAM_DEFINITION);

			}
		} catch (DuplicateException e1) {

			if (e1.getMessage().equalsIgnoreCase("examCode")) {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examDefinition.code.exists"));

			} else {
				errors.add("error", new ActionError(
						"knowledgepro.exam.examDefinition.exists"));

			}
			saveErrors(request, errors);

			String[] prgType = objForm.getSelectedProgramType();
			String[] prg = objForm.getSelectedProgram();
			List<String> progIds = new ArrayList<String>();
			for (String id : prg) {

				progIds.add(id);
			}
			objForm = handler.getProgramProgramType(prgType, prg, objForm);
			objForm.setExamTypeList(handler.getExamTypeList());
			objForm.setMapInternalExam(handler
					.getInternalExamListByAcademicYear(Integer.parseInt(objForm
							.getAcademicYear())));
			objForm.setInternalExamTypeList(handler.getInternalExamTypeList());

			objForm.setExamCourseSchemeList(handler.getCourseSchemeList(objForm
					.getProgramIds()));
			objForm.setExamCourseSchemeListSize(objForm
					.getExamCourseSchemeList().size());
			setCheckBoxes(objForm);
			String prg_str = "";
			for (int x = 0; x < prg.length; x++) {
				prg_str = prg_str + Integer.parseInt(prg[x]) + ",";

			}
			prg_str = prg_str.substring(0, prg_str.length() - 1);
			objForm.setProgramIds(prg_str);
			request.setAttribute("ExamDefinitionOperation", "add");
			return mapping.findForward(CMSConstants.EXAM_DEFINITION);

		} catch (ReActivateException e1) {
			errors.add("error", new ActionError(
					"knowledgepro.exam.examDefinition.reactivate", e1.getID()));
			saveErrors(request, errors);
			objForm.clearPage(mapping, request);

		} catch (Exception e) {
			e.printStackTrace();
			errors.clear();
			String msgKey = super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
			objForm.clearPage(mapping, request);

		} finally {
			setRequestToList(objForm, request);

		}

		return mapping.findForward(CMSConstants.EXAM_DEFINITION_DELETE);
	}

	private void setCheckBoxes(ExamDefinitionForm objForm) 
	{
		StringTokenizer str=new StringTokenizer(objForm.getCourseSchemeSelected(),",");
		while(str.hasMoreTokens())
		{
			StringTokenizer str1=new StringTokenizer(str.nextToken(),"_");
			while(str1.hasMoreTokens())
			{
				int courseId=Integer.parseInt(str1.nextToken());
				int schemeNo=Integer.parseInt(str1.nextToken());
				int schemeId=Integer.parseInt(str1.nextToken());
				int programId=Integer.parseInt(str1.nextToken());
				for(ExamCourseSchemeDetailsTO to:objForm.getExamCourseSchemeList())
				{
					if(to.getCourseId()==courseId && to.getSchemeId()==schemeId && to.getProgramId()==programId)
					{
						for(ExamSchemeTO schemeTO:to.getListDisplay())
						{
							if(schemeTO.getSchemeNo()==schemeNo)
								schemeTO.setSelected("on");
						}
					}
				}
			}
		}
		
	}

	private ActionErrors validate(ExamDefinitionForm objForm) 
	{
		if (objForm.getExamTypeId() == 0) 
		{
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.examType.required"));
		}
		if (objForm.getExamTypeId() == 1 || objForm.getExamTypeId() == 2) 
		{
			if (null == objForm.getInternalExamId()|| objForm.getInternalExamId().length == 0) 
			{
				errors.add("error",	new ActionError("knowledgepro.exam.examDefinition.internalExamName.required"));
			}
		}
		if (objForm.getExamTypeId() == 2 || objForm.getExamTypeId() == 3 || objForm.getExamTypeId() == 6) 
		{

			if (objForm.getJoiningBatchYear() < 1) 
			{
				errors.add("error",	new ActionError("knowledgepro.exam.examDefinition.joiningBatchYear.required"));
			}
		}
		if (objForm.getExamTypeId() == 4) {
			if (objForm.getInternalExamTypeId() == -1) {
				errors.add("error",	new ActionError("knowledgepro.exam.examDefinition.internalExamTypeId.required"));
			}
		}

		if (objForm.getCourseSchemeSelected().isEmpty()) {
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.CourseScheme.required"));
		}

		return errors;
	}

}
