package com.kp.cms.actions.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admin.MBAEntranceExamForm;
import com.kp.cms.handlers.admin.MBAEntranceExamHandler;

@SuppressWarnings("deprecation")
public class MBAEntranceExamAction extends BaseDispatchAction {

	public ActionForward initMBAEntranceExam(ActionMapping mapping,
											 ActionForm form,
											 HttpServletRequest request,
											 HttpServletResponse response) throws Exception {
		MBAEntranceExamForm mbaEntranceExamForm = (MBAEntranceExamForm) form;
		try {
			setRequiredDataToForm(mbaEntranceExamForm);
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			String msg = super.handleApplicationException(ex);
			mbaEntranceExamForm.setErrorMessage(msg);
			mbaEntranceExamForm.setErrorStack(ex.getMessage());
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
		}	
	}

	private void setRequiredDataToForm(MBAEntranceExamForm mbaEntranceExamForm) throws Exception {
		mbaEntranceExamForm.setEntranceExamsTOs(MBAEntranceExamHandler.getInstance().getMBAEntranceExams());
	}

	public ActionForward submitMBAEntranceExam(ActionMapping mapping,
											   ActionForm form,
											   HttpServletRequest request,
											   HttpServletResponse response) throws Exception {
		MBAEntranceExamForm mbaEntranceExamForm = (MBAEntranceExamForm) form;
		ActionErrors errors = mbaEntranceExamForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		boolean isAdded = false;
		
		try {
			if(!errors.isEmpty()) {
				saveErrors(request, errors);				
				setRequiredDataToForm(mbaEntranceExamForm);
				if(mbaEntranceExamForm.getName().trim().equals("")) {
					mbaEntranceExamForm.setName(null);
				}
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
			boolean isSpcl=nameValidate(mbaEntranceExamForm.getName().trim());
			if(isSpcl) {
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);				
				setRequiredDataToForm(mbaEntranceExamForm);
				if((mbaEntranceExamForm.getName().trim()).isEmpty()) {
					mbaEntranceExamForm.setName(null);
				}
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
			setUserId(request, mbaEntranceExamForm);
			isAdded = MBAEntranceExamHandler.getInstance().addMBAEntranceExam(mbaEntranceExamForm, "Add");
			
			setRequiredDataToForm(mbaEntranceExamForm);
			}
			catch(DuplicateException ex) {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.exists"));
			saveErrors(request, errors);			
			setRequiredDataToForm(mbaEntranceExamForm);
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
			catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.reactivate"));
			saveErrors(request, errors);			
			setRequiredDataToForm(mbaEntranceExamForm);
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
		}
		catch(Exception ex){
			ex.printStackTrace();
			if (ex instanceof BusinessException) {
				String msgKey = super.handleBusinessException(ex);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			} else if (ex instanceof ApplicationException) {
				String msg = super.handleApplicationException(ex);
				mbaEntranceExamForm.setErrorMessage(msg);
				mbaEntranceExamForm.setErrorStack(ex.getMessage());
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
		}		
		if (isAdded) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.mbaEntranceExam.addSucess",mbaEntranceExamForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			mbaEntranceExamForm.reset(mapping, request);
		}
		else {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.addFailure",mbaEntranceExamForm.getName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
	}

	public ActionForward updateMBAEntranceExam(ActionMapping mapping,
					 						   ActionForm form,
					 						   HttpServletRequest request,
					 						   HttpServletResponse response) throws Exception {
		MBAEntranceExamForm mbaEntranceExamForm = (MBAEntranceExamForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = mbaEntranceExamForm.validate(mapping, request);
		boolean isUpdated = false;
		try {
			if (!errors.isEmpty()) {
				saveErrors(request, errors);				
				setRequiredDataToForm(mbaEntranceExamForm);
				if((mbaEntranceExamForm.getName().trim()).isEmpty()) {       
					mbaEntranceExamForm.setName(null);
				}
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
			
			boolean isSpcl=nameValidate(mbaEntranceExamForm.getName().trim());
			if(isSpcl) {
				errors.add("error", new ActionError("knowledgepro.admin.special"));
			}
			if (!errors.isEmpty()) {
				saveErrors(request, errors);				
				setRequiredDataToForm(mbaEntranceExamForm);
				if((mbaEntranceExamForm.getName().trim()).isEmpty()){
					mbaEntranceExamForm.setName(null);
				}
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
			
			setUserId(request, mbaEntranceExamForm);
			isUpdated = MBAEntranceExamHandler.getInstance().addMBAEntranceExam(mbaEntranceExamForm, "Edit");
		} catch (DuplicateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.exists"));
			saveErrors(request, errors);
			setRequiredDataToForm(mbaEntranceExamForm);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
		} catch (ReActivateException e1) {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.reactivate"));
			saveErrors(request, errors);			
			setRequiredDataToForm(mbaEntranceExamForm);
			request.setAttribute("admOperation", "edit");
			return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				mbaEntranceExamForm.setErrorMessage(msg);
				mbaEntranceExamForm.setErrorStack(e.getMessage());
				request.setAttribute("admOperation", "edit");
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
		}
		
		setRequiredDataToForm(mbaEntranceExamForm);
		if (isUpdated) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.mbaEntranceExam.updateSucess", mbaEntranceExamForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			mbaEntranceExamForm.reset(mapping, request);
		} else {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.updateFailure", mbaEntranceExamForm.getName()));
			saveErrors(request, errors);
		}
		request.setAttribute("admOperation", "add");
		return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
	}

	public ActionForward deleteMBAEntranceExam(ActionMapping mapping,
					 						   ActionForm form,
					 						   HttpServletRequest request,
					 						   HttpServletResponse response) throws Exception {
		MBAEntranceExamForm mbaEntranceExamForm = (MBAEntranceExamForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		boolean isDeleted = false;
		try {
			if (mbaEntranceExamForm.getId() != 0) {
				int dupId = mbaEntranceExamForm.getId();
				setUserId(request, mbaEntranceExamForm);
				isDeleted = MBAEntranceExamHandler.getInstance().deleteMBAEntranceExam(dupId, false, mbaEntranceExamForm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				mbaEntranceExamForm.setErrorMessage(msg);
				mbaEntranceExamForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
			}
		}
		
		setRequiredDataToForm(mbaEntranceExamForm);
		if (isDeleted) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.mbaEntranceExam.deleteSucess", mbaEntranceExamForm.getName());
			messages.add("messages", message);
			saveMessages(request, messages);
			mbaEntranceExamForm.reset(mapping, request);
		} else {
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.deleteFailure", mbaEntranceExamForm.getName()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);	
	}

	public ActionForward reActivateMBAEntranceExam(ActionMapping mapping,
						 						   ActionForm form,
						 						   HttpServletRequest request,
						 						   HttpServletResponse response) throws Exception {
		MBAEntranceExamForm mbaEntranceExamForm = (MBAEntranceExamForm) form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		boolean isActivated = false;
		try {
			if (mbaEntranceExamForm.getDupId() != 0) {
				int dupId = mbaEntranceExamForm.getDupId();
				setUserId(request, mbaEntranceExamForm);
				isActivated = MBAEntranceExamHandler.getInstance().deleteMBAEntranceExam(dupId, true, mbaEntranceExamForm);
			}
		} catch (Exception e) {
			e.printStackTrace();
			errors.add("error", new ActionError("knowledgepro.admin.mbaEntranceExam.reactivationFailure"));
			saveErrors(request, errors);
		}
		
		setRequiredDataToForm(mbaEntranceExamForm);
		if (isActivated) {
			ActionMessage message = new ActionMessage("knowledgepro.admin.mbaEntranceExam.reactivationSucess");
			messages.add("messages", message);
			saveMessages(request, messages);
		}
		return mapping.findForward(CMSConstants.INIT_MBA_EXAMS);
	}
	
	private boolean nameValidate(String name) {
		boolean result=false;
		Pattern p = Pattern.compile("[^A-Za-z0-9 \t]+");
		Matcher m = p.matcher(name);
		result = m.find();
		return result;
	}
}
