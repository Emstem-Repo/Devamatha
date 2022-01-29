package com.kp.cms.actions.admission;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.CheckListForm;
import com.kp.cms.forms.attendance.ClassEntryForm;
import com.kp.cms.handlers.admission.CheckListHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.attendance.ClassEntryHandler;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;
import com.kp.cms.utilities.CurrentAcademicYear;

/**
 * @author microhard
 * This action class will be used in Checklist CRUD operation. 
 *
 */
@SuppressWarnings("deprecation")
public class CheckListAction extends BaseDispatchAction {

	private static final Logger log=Logger.getLogger(CheckListAction.class);
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This will be invoked when user clicks on the checklist entry link.
	 */
	public ActionForward initCheckListEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CheckListForm checkListForm = (CheckListForm) form;
		// Removing baseActionFrom from session because
		// Each ajax request will parsist the map in baseActionForm.
		request.getSession().removeAttribute("baseActionForm");
		log.info("call of initCheckListEntry method in CheckListAction");
		try {
			//list containing programType, program, course, year, isMarksCard, isConsolidatedMarks, NeedToProduce 
			loadDetails(checkListForm);
			setUserId(request, checkListForm);
		} catch (Exception e) {
			if(e instanceof BusinessException){
				String msg = super.handleApplicationException(e);
				checkListForm.setErrorMessage(msg);
				checkListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}else {
				throw e;
			}
		}
		checkListForm.clearAll();
		log.info("end of initCheckListEntry method in CheckListAction");
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * This method invoked when user click for particular checklist entry update.
	 * and load the pre-necessary data to form to display. 
	 */
	public ActionForward initEditCheckListEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		CheckListForm checkListForm = (CheckListForm) form;
		Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(checkListForm.getProgramTypeId()));
		Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(checkListForm.getProgram()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
		request.setAttribute("checkListOperation","edit");
		
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}	
	
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		  This method will fetches all the checklist entries in data base.
	 */
	public ActionForward setCheckListEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CheckListForm checkListForm = (CheckListForm) form;
		log.info("call of setCheckListEntry method in CheckListAction");
		 ActionErrors errors = checkListForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		try {
			if(errors.isEmpty()) {
	
			CheckListHandler.getInstance().checkDuplicate(checkListForm);
						
			//list of documents coming from handler..
			List<DocTO> list = CheckListHandler.getInstance().getCheckListDocs();
			if (list != null) 
				checkListForm.setDoclist(list);
			}else {
					//if errors are present
					loadDetails(checkListForm);
					errors.add(messages);
					saveErrors(request, errors);
					return mapping.findForward(CMSConstants.CHECK_LIST);
			}
			
		} catch (DuplicateException e1) {
	    	errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_CHECKLISTENTRY_DUPLICATE));
    		saveErrors(request,errors);
    		return mapping.findForward(CMSConstants.CHECK_LIST);
	    } catch (ReActivateException e1) {
	    	errors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_CHECKLISTENTRY_REACTIVATE));
    		saveErrors(request,errors);
    		request.setAttribute("checkListOperation","reactivate");
    		return mapping.findForward(CMSConstants.CHECK_LIST);
	    } catch (Exception e) {
			if(e instanceof BusinessException){
				String msg = super.handleApplicationException(e);
				checkListForm.setErrorMessage(msg);
				checkListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}else {
			throw e;
		}
		}
		log.info("end of setCheckListEntry method in CheckListAction");
		request.setAttribute("checkListoperation", "edit");
		return mapping.findForward(CMSConstants.CHECK_DOCS);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		   This method will set the data to form for which edit operation is called.
	 */
	
	public ActionForward editSetCheckListEntry(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CheckListForm checkListForm = (CheckListForm) form;
		log.info("call of editSetCheckListEntry method in CheckListAction");
    	try {
				
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(checkListForm.getProgramTypeId()));
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(checkListForm.getProgram()));
			request.setAttribute("programMap", programMap);
			request.setAttribute("courseMap", courseMap);
			request.setAttribute("checkListOperation","edit");
			
			CheckListHandler.getInstance().setCheckListTOToForm(checkListForm);
			
			List<DocTO> docList= CheckListHandler.getInstance().editCheckList(checkListForm);
			checkListForm.setDoclist(docList);
			
		}catch(Exception e){
			String msg = super.handleApplicationException(e);
			checkListForm.setErrorMessage(msg);
			checkListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.CHECK_DOCS);
		}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 			This will add a new checklist entry to database.
	 */
	public ActionForward addCheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CheckListForm checkListForm = (CheckListForm) form;
		log.info("call of addCheckListAction in CheckListAction");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = checkListForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				CheckListHandler.getInstance().addCheckList(checkListForm);
				// if isadded is true then send success message.
				// assuming successfullly added with out the errors and Exceptions 
				loadDetails(checkListForm);
				ActionMessage message = new ActionMessage("knowledgepro.checklist.record.added");
				messages.add("messages", message);
				saveMessages(request, messages);
				checkListForm.clearAll();
			} else {
				//if errors are present
				errors.add(messages);
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.CHECK_DOCS);
			}

		} catch (Exception e) {
			    // Exception then display exception message in UI.
				String msg = super.handleApplicationException(e);
				checkListForm.setErrorMessage(msg);
				checkListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of addCheckList in CheckListAction");
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		   This will update the particular checklist entry in the database.
	 */
	public ActionForward updateCheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		CheckListForm checkListForm = (CheckListForm) form;
		log.info("call of addCheckListAction in CheckListAction");
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = checkListForm.validate(mapping, request);
		try {
			if (errors.isEmpty()) {
				CheckListHandler.getInstance().checkDuplicate(checkListForm);
				CheckListHandler.getInstance().updateCheckList(checkListForm);
				// if isadded is true then send success message.
				// assuming successfullly added with out the errors and Exceptions 
				loadDetails(checkListForm);
				ActionMessage message = new ActionMessage("knowledgepro.checklist.record.updated");
				messages.add("messages", message);
				saveMessages(request, messages);
				checkListForm.clearAll();
				
			} else {
				//if errors are present
				errors.add(messages);
				saveErrors(request, errors);
				loadDocCheckList(checkListForm,request);
				request.setAttribute("checkListOperation","edit");
				return mapping.findForward(CMSConstants.CHECK_DOCS);
			}

		} catch (DuplicateException e1) {
			log.error("error in CheckListAction",e1);
			errors.add("error",new ActionError("knowledgepro.checklist.courseExist"));
			saveErrors(request, errors);
			loadDocCheckList(checkListForm,request);
			request.setAttribute("checkListOperation","edit");
			return mapping.findForward(CMSConstants.CHECK_DOCS);
	    } catch (ReActivateException e1) {
	    	log.error("error in CheckListAction",e1);
	    	errors.add("error", new ActionError("knowledgepro.checklist.reactivate"));
    		saveErrors(request,errors);
			loadDocCheckList(checkListForm,request);
			request.setAttribute("checkListOperation","reactivate");
    		return mapping.findForward(CMSConstants.CHECK_DOCS);
	    } catch (Exception e) {
	    	log.error("error in CheckListAction",e);
			    // Exception then display exception message in UI.
				String msg = super.handleApplicationException(e);
				checkListForm.setErrorMessage(msg);
				checkListForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		}

		return mapping.findForward(CMSConstants.CHECK_LIST);
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		   This will be used in viewing the particular checklist record.
	 */
	public ActionForward viewCheckListDetails(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) 
		throws Exception {
		CheckListForm checkListForm = (CheckListForm) form;
		log.info("call of viewCheckListDetails in CheckListAction");
		try {
			
			List<DocTO> checkList=CheckListHandler.getInstance().viewCheckList(checkListForm);
			checkListForm.setDoclist(checkList);
			
			
		} catch (Exception e) {
			// Exception then display exception message in UI.
			String msg = super.handleApplicationException(e);
			checkListForm.setErrorMessage(msg);
			checkListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("end of viewCheckList in CheckListAction");
		return mapping.findForward(CMSConstants.VIEW_CHECK_LIST);
		}
	
	/**
	 * 	
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 *         This will delete the particular checklist entry from database means mark as delete.
	 */
	public ActionForward deleteCheckList(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		CheckListForm listForm = (CheckListForm) form;
		log.info("call of deleteCheckList in CheckListAction");
		ActionMessages messages = new ActionMessages();
		boolean isDeleted = false;
		try {
			//getting the docCheckListId from jsp and passing to handler which returns boolean value.
			isDeleted = CheckListHandler.getInstance().deleteCheckList(listForm);
		} catch (Exception e) {
			log.error("error in final submit of application page...",e);
			String msgKey=super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
		}

		if(isDeleted){
			ActionMessage message = new ActionMessage("knowledgepro.checklist.record.deleted");
			messages.add("messages", message);
			saveMessages(request, messages);
			listForm.clearAll();
			//list containing programType, program, course, year, isMarksCard, isConsolidatedMarks, NeedToProduce 
			loadDetails(listForm);
		}
		log.info("end of deleteCheckList in CheckListAction");
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}
	
	/**
	 * 	
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception
	 * 		   This  will make the record active after delete.	
	 */
	public ActionForward reActivateSetCheckListEntry(ActionMapping mapping, ActionForm form, 
			HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		CheckListForm listForm = (CheckListForm) form;
		log.info("call of reActivateSetCheckListEntry in CheckListAction");
		ActionMessages messages = new ActionMessages();
		boolean isReactivated = false;
		try {
			//getting the docCheckListId from jsp and passing to handler which returns boolean value.
			isReactivated = CheckListHandler.getInstance().reActivateCheckList(listForm);
		} catch (Exception e) {
			log.error("Error while reactivating checklist...",e);
			String msgKey=super.handleBusinessException(e);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add("messages", message);
		}

		if(isReactivated){
			ActionMessage message = new ActionMessage("knowledgepro.checklist.record.reactivated");
			messages.add("messages", message);
			saveMessages(request, messages);
			listForm.clearAll();
			//list containing programType, program, course, year, isMarksCard, isConsolidatedMarks, NeedToProduce 
			loadDetails(listForm);
		}
		log.info("end of reActivateSetCheckListEntry in CheckListAction");
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}
	
	/*
	 * This will load the necessary data in to form while loading
	 */
	public void loadDetails(CheckListForm checkListForm) 
		throws Exception{
		List<CheckListTO> checklist=null;
		if (checkListForm.getYear() == null
				|| checkListForm.getYear().isEmpty()) {
			Calendar calendar = Calendar.getInstance();
			int currentYear = calendar.get(Calendar.YEAR);
			
			int year = CurrentAcademicYear.getInstance().getAcademicyear();
			if (year != 0) {
				currentYear = year;
			}
			//list containing programType, program, course, year, isMarksCard, isConsolidatedMarks, NeedToProduce 
			checklist =CheckListHandler.getInstance().getCheckList(currentYear);
		} else {
			int year = Integer.parseInt(checkListForm.getYear().trim());
			checklist =CheckListHandler.getInstance().getCheckList(year);
		}
		
		if (checklist != null)
			checkListForm.setCheckList(checklist);
		//	list of documents coming from handler..
		List<DocTO> list = CheckListHandler.getInstance().getCheckListDocs();
		if (list != null) 
			checkListForm.setDoclist(list);
	}
	
	// This will load the doccheclist data into form
	public void loadDocCheckList(CheckListForm checkListForm,HttpServletRequest request) throws Exception{
		Map<Integer,String> programMap = new HashMap<Integer,String>();
		Map<Integer,String> courseMap = new HashMap<Integer,String>();
		if(checkListForm.getProgramTypeId() != null && checkListForm.getProgramTypeId().length() != 0)
			programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(checkListForm.getProgramTypeId()));
		if(checkListForm.getProgram() != null && checkListForm.getProgram().length() != 0)
			courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(checkListForm.getProgram()));
		request.setAttribute("programMap", programMap);
		request.setAttribute("courseMap", courseMap);
	}
	
	/**
	 * setting the checkList to form on change of the year  in the jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward setCheckList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entering setClassEntry");
		CheckListForm chForm = (CheckListForm) form;
		ActionMessages messages = new ActionMessages();
		try {
			loadDetails(chForm);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			chForm.setErrorMessage(msg);
			chForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("Leaving setClassEntry");
		return mapping.findForward(CMSConstants.CHECK_LIST);
	}
}