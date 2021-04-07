package com.kp.cms.actions.admin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.SubjectGroup;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.SubjectGroupEntryForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.SubjectGroupHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.SubjectGroupTO;
import com.kp.cms.to.admin.SubjectTO;
import com.kp.cms.utilities.CommonUtil;

@SuppressWarnings("deprecation")
public class SubjectGroupEntryAction extends BaseDispatchAction{
	
	private static final Logger log=Logger.getLogger(SubjectGroupEntryAction.class);
	
	/**
     * Performs to load details when you click on SubjectGroupEntry.
     * @param mapping  The ActionMapping used to select this instance
     * @param form     The optional ActionForm bean for this request (if any)
     * @param request  The HTTP request we are processing
     * @param response The HTTP response we are creating
     * @return forward to which control should be transferred,
     * @throws Exception if an exception occurs
     */

	public ActionForward initSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm=(SubjectGroupEntryForm)form;
		try {
			subjectGroupEntryForm.clearAll();
			subjectGroupEntryForm.setMovedSubjectsTORight(null);
			subjectGroupEntryForm.setSelectedSubjectsMap(null);
			//Getting subject name, code and id from Handler..
			getSubjects(subjectGroupEntryForm);
			getProgramTypeList(subjectGroupEntryForm,request);
			//Getting SubjectGroup Details from Hanlder..
//			List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroupEntry();
//			subjectGroupEntryForm.setSubjectGroupList(list);
			subjectGroupEntryForm.setSubjectGroupList(null);
			subjectGroupEntryForm.setProgramId(null);
			subjectGroupEntryForm.setListSecondLanguage(SubjectGroupHandler.getInstance().getListSecondLanguage());
			subjectGroupEntryForm.setProgramTypeId(null);
		} catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			subjectGroupEntryForm.setErrorMessage(msgKey);
			subjectGroupEntryForm.setErrorStack(businessException.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			subjectGroupEntryForm.setErrorMessage(msg);
			subjectGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}	
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
	}
	
	/**
	 * This method is used to saving a record to database by calling handler.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	
	@SuppressWarnings("unchecked")
	public ActionForward setSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm=(SubjectGroupEntryForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = subjectGroupEntryForm.validate(mapping, request);
		try {
			if(errors.isEmpty()){
				//errors are empty.
			setUserId(request,subjectGroupEntryForm);
			int courseId=Integer.parseInt(subjectGroupEntryForm.getCourseId());
			String subjectGroupName = subjectGroupEntryForm.getSubjectGroupName().trim();
			//call of Subject Group Handler by passing courseId and subject Group Name and return Subject Group BO instance.
			SubjectGroup subjectGroup=SubjectGroupHandler.getInstance().isSubjectGroupNameExist(courseId,subjectGroupName);
			//checking for Duplicate entry.
			if(subjectGroup!=null && subjectGroup.getIsActive()){
				errors.add(CMSConstants.SUBJECT_GROUP_NAME_EXIST,new ActionError(CMSConstants.SUBJECT_GROUP_NAME_EXIST));
				saveErrors(request, errors);
				
				Map<Integer,String> movedMap = new HashMap<Integer,String>();
				Map<Integer,String> subjectsMap = subjectGroupEntryForm.getSubjectsMap();
				String[] selectedArr = subjectGroupEntryForm.getMovedSubjectsTORight();
				if(subjectsMap!= null && selectedArr != null){
					for(int i=0;i<selectedArr.length;i++){
						if(subjectsMap.containsKey(Integer.parseInt(selectedArr[i]))){
							movedMap.put(Integer.valueOf(selectedArr[i]), subjectsMap.get(Integer.parseInt(selectedArr[i])));
							subjectsMap.remove(Integer.valueOf(selectedArr[i]));
						}
						subjectGroupEntryForm.setSelectedSubjectsMap(movedMap);
					}
				}
			}
			//checking for reactivation.
			else if(subjectGroup!=null && !subjectGroup.getIsActive()){
				errors.add(CMSConstants.SUBJECT_GROUP_REACTIVATE, new ActionError(CMSConstants.SUBJECT_GROUP_REACTIVATE));
				saveErrors(request, errors);
			}
			else{
				//String commonSubGp=subjectGroupEntryForm.getCommonSubjectGroup();
				// String common_SubGp=request.getParameter("commonSubjectGroup");
				//call of Subject Group Handler by passing form and returns boolean value.
				boolean isadded=SubjectGroupHandler.getInstance().setSubjectGroupEntry(subjectGroupEntryForm);
				//add success
				if(isadded){
					ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_ADDSUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
					subjectGroupEntryForm.clearAll();
					getSubjects(subjectGroupEntryForm);
					subjectGroupEntryForm.reset(mapping, request);
				}
				//add fails
				if(!isadded){
					ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_ADDFAILURE);
					messages.add("messages", message);
					saveMessages(request, messages);
					subjectGroupEntryForm.clearAll();
					getSubjects(subjectGroupEntryForm);
					subjectGroupEntryForm.reset(mapping, request);
				}
				}
			//call of programType list
			getProgramTypeList(subjectGroupEntryForm,request);
			//getting the details of SubjectGroup by Subject Group Handler.
			subjectGroupEntryForm.setDisplayList(true);
			List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
			subjectGroupEntryForm.setSubjectGroupList(list);
			HttpSession session=request.getSession(false);
			//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
			session.setAttribute("programMap", programMap);
			session.setAttribute("courseMap", courseMap);
			}else{
				//errors are present
				errors.add(messages);
				//to display the selected subjects after validation.
				String[] selectedArray = subjectGroupEntryForm.getMovedSubjectsTORight();
				Map<Integer,String> subjectsMap = subjectGroupEntryForm.getSubjectsMap();
				Map<Integer,String> movedMap = new HashMap<Integer,String>();
				Map<Integer,String> tempMap = new HashMap<Integer,String>();
				Map<Integer,String> temp = subjectGroupEntryForm.getTempMap();
				if(selectedArray != null && selectedArray.length != 0){
				for(int i=0;i<selectedArray.length;i++) {
					if(subjectsMap.containsKey(Integer.parseInt(selectedArray[i]))) {
						movedMap.put(Integer.valueOf(selectedArray[i]), subjectsMap.get(Integer.parseInt(selectedArray[i])));
						tempMap.put(Integer.valueOf(selectedArray[i]), subjectsMap.get(Integer.parseInt(selectedArray[i])));
						subjectsMap.remove(Integer.valueOf(selectedArray[i])); //remove selected subjects from first map.
					}else if(!StringUtils.isEmpty(subjectGroupEntryForm.getSubjectGroupName())){
						movedMap.put(Integer.valueOf(selectedArray[i]), temp.get(Integer.parseInt(selectedArray[i])));
						tempMap.put(Integer.valueOf(selectedArray[i]), temp.get(Integer.parseInt(selectedArray[i])));
						tempMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(tempMap);
					}else
						movedMap.put(Integer.valueOf(selectedArray[i]), temp.get(Integer.parseInt(selectedArray[i])));
				}
				}
				tempMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(tempMap);
				subjectGroupEntryForm.setTempMap(tempMap);
				if(!subjectGroupEntryForm.getSelectedIndex().equals("-1")){
					subjectGroupEntryForm.setSubjectsMap(subjectsMap);
				}else{ 
					getSubjects(subjectGroupEntryForm);
				}
				movedMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(movedMap);
				subjectGroupEntryForm.setSelectedSubjectsMap(movedMap);
				saveErrors(request, errors);
				//getting the details of SubjectGroup by Subject Group Handler.
				subjectGroupEntryForm.setDisplayList(true);
				List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
				subjectGroupEntryForm.setSubjectGroupList(list);
				HttpSession session=request.getSession(false);
				//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
				Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
				Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
				session.setAttribute("programMap", programMap);
				session.setAttribute("courseMap", courseMap);
			}
			} catch (BusinessException businessException) {
				log.info("Exception setSubjectGroupEntry");
				String msgKey = super.handleBusinessException(businessException);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add(CMSConstants.MESSAGES, message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				subjectGroupEntryForm.setErrorMessage(msg);
				subjectGroupEntryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
	}
	
	/**
	 * This method is used for editing a record and populate data from database by calling handler based on id.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	
	public ActionForward editSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm = (SubjectGroupEntryForm)form;
		ActionMessages messages = new ActionMessages();
		try{
			int subjectGroupId=subjectGroupEntryForm.getSubjectGroupEntryId();
			subjectGroupEntryForm.setSelectedSubjectGroupEntryId(subjectGroupId);
			getSubjects(subjectGroupEntryForm);
			//call of Subject Group Handler.
			SubjectGroupHandler.getInstance().getEditSubjectGroupEntry(subjectGroupId,subjectGroupEntryForm);
			//call of programType list
			getProgramTypeList(subjectGroupEntryForm,request);
			
			HttpSession session=request.getSession(false);
			//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
			if(session!=null){
			    session.setAttribute("programMap", programMap);
			    session.setAttribute("courseMap", courseMap);
			}
			request.setAttribute("subjectEntryOperation","edit");
			if(session == null){
    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
			}else{
				session.setAttribute("SGNAME",subjectGroupEntryForm.getSubjectGroupName());
				session.setAttribute("SubIds",subjectGroupEntryForm.getMovedSubjectsTORight());
    		}
		}catch (BusinessException businessException) {
			log.info("Exception editSubjectGroupEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			subjectGroupEntryForm.setErrorMessage(msg);
			subjectGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
	}
	
	/**
	 * This method is used for updating a record in database by calling handler.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	
	@SuppressWarnings("unchecked")
	public ActionForward updateSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm = (SubjectGroupEntryForm)form;
		boolean isUpdated=false;
		ActionErrors errors=subjectGroupEntryForm.validate(mapping, request);
	    ActionMessages messages = new ActionMessages();
	    HttpSession session=request.getSession(false);
	    try{
			if(isCancelled(request)){
				int subjectGroupId=subjectGroupEntryForm.getSelectedSubjectGroupEntryId();
				getSubjects(subjectGroupEntryForm);
				//call of Subject Group Handler.
				SubjectGroupHandler.getInstance().getEditSubjectGroupEntry(subjectGroupId,subjectGroupEntryForm);
				
				//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
				Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
				Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
				if(session!=null){
				    session.setAttribute("programMap", programMap);
				    session.setAttribute("courseMap", courseMap);
				}
				request.setAttribute("subjectEntryOperation","edit");
				//getting the details of SubjectGroup by Subject Group Handler.
				subjectGroupEntryForm.setDisplayList(true);
				List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
				subjectGroupEntryForm.setSubjectGroupList(list);
				if(session == null){
	    			return mapping.findForward(CMSConstants.LOGIN_PAGE);
				}else{
					session.setAttribute("SGNAME",subjectGroupEntryForm.getSubjectGroupName());
					session.setAttribute("SubIds",subjectGroupEntryForm.getMovedSubjectsTORight());
	    		}
				return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
			}
			Map<Integer, String> map = subjectGroupEntryForm.getSelectedSubjectsMap();
			if(errors.isEmpty()){
					//errors are empty
				
				 String common_SubGp=request.getParameter("commonSubjectGroup");
				 subjectGroupEntryForm.setCommonSubjectGroup(common_SubGp==null?"off":"on");
					setUserId(request,subjectGroupEntryForm);  //setting user id to form.
					int courseId=Integer.parseInt(subjectGroupEntryForm.getCourseId());
					String subjectGroupName = subjectGroupEntryForm.getSubjectGroupName().trim();
//					HttpSession session=request.getSession(false);
					String sgname = session.getAttribute("SGNAME").toString();
				if(!sgname.equals(subjectGroupName)){
					//call of Subject Group Handler by passing courseId and subject Group Name and return Subject Group BO instance.
					SubjectGroup subjectGroup=SubjectGroupHandler.getInstance().isSubjectGroupNameExist(courseId,subjectGroupName);
				//checking for Duplicate entry.
				if(subjectGroup!=null && subjectGroup.getIsActive() && subjectGroup.getName().equals(subjectGroupName)){
					errors.add(CMSConstants.SUBJECT_GROUP_NAME_EXIST,new ActionError(CMSConstants.SUBJECT_GROUP_NAME_EXIST));
					saveErrors(request, errors);
					request.setAttribute("subjectEntryOperation","edit");
					Map<Integer,String> subjectsMap = subjectGroupEntryForm.getSubjectsMap();
					String[] selectedArr = subjectGroupEntryForm.getMovedSubjectsTORight();
					if(subjectsMap!= null && selectedArr != null){
						for(int i=0;i<selectedArr.length;i++){
							if(subjectsMap.containsKey(Integer.parseInt(selectedArr[i]))){
								map.put(Integer.valueOf(selectedArr[i]), subjectsMap.get(Integer.parseInt(selectedArr[i])));
								subjectsMap.remove(Integer.valueOf(selectedArr[i]));
							}
							map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
							subjectGroupEntryForm.setSelectedSubjectsMap(map);
						}
					}
				}
				//checking for reactivation.
				else if(subjectGroup!=null && !subjectGroup.getIsActive()){
					errors.add(CMSConstants.SUBJECT_GROUP_REACTIVATE,new ActionError(CMSConstants.SUBJECT_GROUP_REACTIVATE));
					saveErrors(request, errors);
				}else{
				//call of Subject Group Handler and returns boolean value.
					
			isUpdated=SubjectGroupHandler.getInstance().updateSubjectGroupEntry(subjectGroupEntryForm );
			//update success
			if(isUpdated){
				//if update is success and removing the attributes from session scope.
				session.removeAttribute("SGNAME");
				session.removeAttribute("SubIds");
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_UPDATESUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
				getSubjects(subjectGroupEntryForm);
				subjectGroupEntryForm.reset(mapping, request);
			}
			//update fails
			if(!isUpdated){
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_UPDATEFAILURE);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
				getSubjects(subjectGroupEntryForm);
				subjectGroupEntryForm.reset(mapping, request);
			}
			}
				//getting the details of SubjectGroup by SubjectGroupHandler.
				List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroupEntry();
				subjectGroupEntryForm.setSubjectGroupList(list);
			}else{
				//call of SubjectGroupHandler and returns boolean value.
			isUpdated=SubjectGroupHandler.getInstance().updateSubjectGroupEntry(subjectGroupEntryForm );
			//update success
			if(isUpdated){
				//if update is success and removing the attributes from session scope.
				session.removeAttribute("SGNAME");
				session.removeAttribute("SubIds");
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_UPDATESUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
				getSubjects(subjectGroupEntryForm);
				subjectGroupEntryForm.reset(mapping, request);
			}
			//update fails
			if(!isUpdated){
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_UPDATEFAILURE);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
				getSubjects(subjectGroupEntryForm);
				subjectGroupEntryForm.reset(mapping, request);
			}
			}
				//getting the details of SubjectGroup by Subject Group Handler.
				subjectGroupEntryForm.setDisplayList(true);
				List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
				subjectGroupEntryForm.setSubjectGroupList(list);
				//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
				Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
				Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
				session.setAttribute("programMap", programMap);
				session.setAttribute("courseMap", courseMap);
			
			}else{
				
				//errors are present
				errors.add(messages);
				//to display the selected subjects after validation.
				String[] selectedArray = subjectGroupEntryForm.getMovedSubjectsTORight();
				Map<Integer,String> subjectsMap = subjectGroupEntryForm.getSubjectsMap();
				
				if(selectedArray != null && selectedArray.length != 0){
					for(int i=0;i<selectedArray.length;i++) {
						if(subjectsMap.containsKey(Integer.parseInt(selectedArray[i]))) {
							subjectsMap.remove(Integer.valueOf(selectedArray[i])); //remove selected subjects from first map.
					}else{
						subjectGroupEntryForm.setSelectedSubjectsMap(map);
					}
					}
					if(!subjectGroupEntryForm.getSelectedIndex().equals("-1")){
						subjectGroupEntryForm.setSubjectsMap(subjectsMap);
					}else{ 
						getSubjects(subjectGroupEntryForm);
					}
				}else{
					getSubjects(subjectGroupEntryForm);
				}
				
				//errors are present.
				saveErrors(request, errors);
				request.setAttribute("subjectEntryOperation","edit");
				if(!StringUtils.isEmpty(subjectGroupEntryForm.getProgramTypeId().trim()) && !StringUtils.isEmpty(subjectGroupEntryForm.getProgramId().trim())){
					Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
					Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
					session.setAttribute("programMap", programMap);
					session.setAttribute("courseMap", courseMap);
					request.setAttribute("subjectEntryOperation","edit");
				}
		}
		}catch (BusinessException businessException) {
			log.info("Exception updateSubjectGroupEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			subjectGroupEntryForm.setErrorMessage(msg);
			subjectGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		//call of programType list
		getProgramTypeList(subjectGroupEntryForm,request);
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
		}
	
	/**
	 * This method is used for deleting a record from database by calling handler based on id.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	
	public ActionForward deleteSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm = (SubjectGroupEntryForm)form;
		ActionMessages messages = new ActionMessages();
		HttpSession session=request.getSession();
		String tempProgramId=(String)session.getAttribute("tempProgramId");
		String tempProgramTypeId=(String)session.getAttribute("tempProgramTypeId");
		try {
			if(tempProgramId!=null && !tempProgramId.isEmpty()){
				subjectGroupEntryForm.setProgramId(tempProgramId);
			}
			if(tempProgramTypeId!=null && !tempProgramTypeId.isEmpty()){
				subjectGroupEntryForm.setProgramTypeId(tempProgramTypeId);
			}
			setUserId(request,subjectGroupEntryForm);
			int subGroupId = subjectGroupEntryForm.getSubjectGroupEntryId();
			//call of Subject Group Handler return boolean value.
			boolean isdelete=SubjectGroupHandler.getInstance().deleteSubjectGroupEntry(subGroupId, subjectGroupEntryForm.getUserId());
			//delete success
			if(isdelete){
				//call of programType list
				getProgramTypeList(subjectGroupEntryForm,request);
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_DELETESUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
			}
			//delete fails
			if(!isdelete){
				//call of programType list
				getProgramTypeList(subjectGroupEntryForm,request);
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_NAME_DELETEFAILURE);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
			}
			//getting the details of SubjectGroup by Subject Group Handler.
			subjectGroupEntryForm.setDisplayList(true);
			List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
			subjectGroupEntryForm.setSubjectGroupList(list);
			session=request.getSession(false);
			//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
			session.setAttribute("programMap", programMap);
			session.setAttribute("courseMap", courseMap);
		} catch (BusinessException businessException) {
			log.info("Exception deleteSubjectGroupEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			subjectGroupEntryForm.setErrorMessage(msg);
			subjectGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
	}
	
	/**
	 * This method is used for restoring a record from database by calling handler based on subject Group Name.
	 * @param mapping The ActionMapping used to select this instance
	 * @param form The optional ActionForm bean for this request (if any)
	 * @param request The HTTP request we are processing
	 * @param response The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception if an exception occurs
	 */
	
	public ActionForward reActivateSubjectGroupEntry(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SubjectGroupEntryForm subjectGroupEntryForm = (SubjectGroupEntryForm)form;
		ActionMessages messages = new ActionMessages();
		try {
			setUserId(request,subjectGroupEntryForm);
			//call of Subject Group Handler.
			SubjectGroupHandler.getInstance().reActivateSubjectGroupEntry(subjectGroupEntryForm);
				ActionMessage message = new ActionMessage(CMSConstants.SUBJECT_GROUP_REACTIVATE_SUCCESS);
				messages.add("messages", message);
				saveMessages(request, messages);
				subjectGroupEntryForm.clearAll();
			//call of programType list
			getProgramTypeList(subjectGroupEntryForm,request);
			//getting the details of SubjectGroup by Subject Group Handler.
			subjectGroupEntryForm.setDisplayList(true);
			List<SubjectGroupTO> list=SubjectGroupHandler.getInstance().getSubjectGroup(subjectGroupEntryForm.getProgramTypeId(),subjectGroupEntryForm.getProgramId());
			subjectGroupEntryForm.setSubjectGroupList(list);
			HttpSession session=request.getSession(false);
			//calling CommonAjaxHandler by passing programId and courseId and getting Map and setting to request scope. 
			Map<Integer,String> programMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(Integer.parseInt(subjectGroupEntryForm.getProgramId()));
			session.setAttribute("programMap", programMap);
			session.setAttribute("courseMap", courseMap);
		} catch (BusinessException businessException) {
			log.info("Exception reActivateSubjectGroupEntry");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			subjectGroupEntryForm.setErrorMessage(msg);
			subjectGroupEntryForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.SUBJECT_GROUP_ENTRY);
	}
	
	/**
	 * This method is used to get programTypeList and setting to Subject Group Entry Form.
	 * @param subjectGroupEntryForm
	 * @throws Exception
	 */
	public void getProgramTypeList(SubjectGroupEntryForm subjectGroupEntryForm,HttpServletRequest request) throws Exception{
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		subjectGroupEntryForm.setProgramTypeList(programTypeList);
		if(subjectGroupEntryForm.getProgramTypeId()!=null &&
				subjectGroupEntryForm.getProgramTypeId().length()>0 ){
			Map collegeMap = CommonAjaxHandler.getInstance().getProgramsByProgramType(Integer.parseInt(subjectGroupEntryForm.getProgramTypeId()));
			request.setAttribute("programMap", collegeMap);
		}
		if(subjectGroupEntryForm.getProgramId()!= null &&
				subjectGroupEntryForm.getProgramId().length() >0 ){
			Map<Integer,String> courseMap = CommonAjaxHandler.getInstance().getCourseByProgram(
					Integer.valueOf(subjectGroupEntryForm.getProgramId()));
			request.setAttribute("courseMap", courseMap);
		}
	}
	
	/**
	 * This method is used to get subjects from database by calling Subject Group Handler and setting to form.
	 * @param subjectGroupEntryForm
	 * @throws Exception
	 */
	
	@SuppressWarnings("unchecked")
	public void getSubjects(SubjectGroupEntryForm subjectGroupEntryForm) throws Exception {
		//call of Subject Group Handler and returns list of type SubjectTO.
		List<SubjectTO> subjectList=SubjectGroupHandler.getInstance().getSubjectDetails();
		Iterator<SubjectTO> iterator = subjectList.iterator(); //iterating subjectList
		SubjectTO subjectTO=null;
		Map<Integer,String> subjectsMap = new LinkedHashMap<Integer,String>();
//		String[] selected=new String[subjectList.size()];
		while (iterator.hasNext()) {
			subjectTO = (SubjectTO) iterator.next();  //type casting to SubjectTO and putting the values into Map.
			subjectTO.setNameCode(subjectTO.getCode()+"("+subjectTO.getName()+")"+"("+subjectTO.getTheoryPractical()+")");
			subjectsMap.put(subjectTO.getId(),subjectTO.getNameCode());
			
		}
		subjectsMap = (HashMap<Integer, String>) CommonUtil.sortMapByValue(subjectsMap);
		subjectGroupEntryForm.setSubjectsMap(subjectsMap); 
		subjectGroupEntryForm.setMapIds(subjectsMap);
		log.info("end of getSubjects method in SubjectGroupEntryAction class.");
	}
}