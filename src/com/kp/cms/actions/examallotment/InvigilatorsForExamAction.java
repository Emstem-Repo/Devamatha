package com.kp.cms.actions.examallotment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
import org.hibernate.mapping.Array;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.examallotment.InvigilatorsForExamForm;
import com.kp.cms.handlers.examallotment.InvigilatorsForExamHandler;
import com.kp.cms.to.examallotment.InvigilatorsForExamTo;

public class InvigilatorsForExamAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(InvigilatorsForExamAction.class);
InvigilatorsForExamHandler handler=InvigilatorsForExamHandler.getInstance();

	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initInvigilatorsForExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvigilatorsForExamForm invigilatorsForExamForm=(InvigilatorsForExamForm)form;
		reset(invigilatorsForExamForm);
		setRequiredDataToForm(invigilatorsForExamForm);
		return mapping.findForward("initInvigilatorsForExam");
	}

	private void reset(InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		invigilatorsForExamForm.setDeptId(null);
		invigilatorsForExamForm.setLocationId(null);
		invigilatorsForExamForm.setList(null);
		invigilatorsForExamForm.setExamId(null);
		invigilatorsForExamForm.setDeanaryId(null);
		invigilatorsForExamForm.setExamInvigilatorsMap(null);
	}

	private void setRequiredDataToForm(
			InvigilatorsForExamForm invigilatorsForExamForm) throws Exception{
		//set exammap,deanarymap and department map
		handler.getAllMaps(invigilatorsForExamForm);
		
	}
	/**
	 * get invigilators list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getInvigilatorsList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvigilatorsForExamForm invigilatorsForExamForm=(InvigilatorsForExamForm)form;
		invigilatorsForExamForm.setList(null);
		invigilatorsForExamForm.setExamInvigilatorsMap(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = invigilatorsForExamForm.validate(mapping, request);
		try {
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
			}else{
				List<InvigilatorsForExamTo> list=handler.getInvigilatorsList(invigilatorsForExamForm);
				if(list!=null && !list.isEmpty()){
					invigilatorsForExamForm.setList(list);
					return mapping .findForward(CMSConstants.INVIGILATORS_LIST);
				}else{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			invigilatorsForExamForm.setErrorMessage(msg);
			invigilatorsForExamForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInvigilatorsForExam");
	}
	/**
	 * update the examId and users in 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addInvigilators(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvigilatorsForExamForm invigilatorsForExamForm=(InvigilatorsForExamForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			setUserId(request, invigilatorsForExamForm); 
				Map<Integer,Integer> map=invigilatorsForExamForm.getExamInvigilatorsMap();
				List<Integer> theRecordsToSetIsActiveIsFalse=new ArrayList<Integer>();
				List<Integer> theRecordsToSetIsActiveIsTrue=new ArrayList<Integer>();
				List<Integer> therecordsExists=new ArrayList<Integer>();
				List<InvigilatorsForExamTo> list=new ArrayList<InvigilatorsForExamTo>();
				List<InvigilatorsForExamTo> invigilatorsForExamTos=invigilatorsForExamForm.getList();
				Iterator<InvigilatorsForExamTo> iterator=invigilatorsForExamTos.iterator();
				while (iterator.hasNext()) {
					InvigilatorsForExamTo invigilatorsForExamTo = (InvigilatorsForExamTo) iterator.next();
					if(invigilatorsForExamTo.getChecked()!=null && !invigilatorsForExamTo.getChecked().isEmpty() && invigilatorsForExamTo.getChecked().equalsIgnoreCase("on")){
						if(map!=null && !map.isEmpty() && map.containsKey(invigilatorsForExamTo.getUserId())){
							theRecordsToSetIsActiveIsTrue.add(map.get(invigilatorsForExamTo.getUserId()));
							therecordsExists.add(map.get(invigilatorsForExamTo.getUserId()));
						}else{
							list.add(invigilatorsForExamTo);
						}
						invigilatorsForExamTo.setChecked(null);
					}else if(map!=null && !map.isEmpty() && map.containsKey(invigilatorsForExamTo.getUserId())){
						theRecordsToSetIsActiveIsFalse.add(map.get(invigilatorsForExamTo.getUserId()));
						therecordsExists.add(map.get(invigilatorsForExamTo.getUserId()));
					}
				}
				setUserId(request, invigilatorsForExamForm); 
				if((list!=null && !list.isEmpty()) || (therecordsExists!=null && !therecordsExists.isEmpty())){
					boolean flag=handler.addInvigilators(list,invigilatorsForExamForm,theRecordsToSetIsActiveIsFalse,theRecordsToSetIsActiveIsTrue,therecordsExists);
					if(flag){
						reset(invigilatorsForExamForm);
						setRequiredDataToForm(invigilatorsForExamForm);
						ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilators.add.success");
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward("initInvigilatorsForExam");
					}else{
						errors.add("error", new ActionError("knowledgepro.exam.allotment.invigilators.add.fail"));
						saveErrors(request, errors);
					}
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.select.records.add"));
					saveErrors(request, errors);
				}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				invigilatorsForExamForm.setErrorMessage(msg);
				invigilatorsForExamForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping .findForward(CMSConstants.INVIGILATORS_LIST);
	}
	/**
	 * get invigilators list
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchTheInvigilators(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InvigilatorsForExamForm invigilatorsForExamForm=(InvigilatorsForExamForm)form;
		invigilatorsForExamForm.setList(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = invigilatorsForExamForm.validate(mapping, request);
		try {
			if(errors!=null && !errors.isEmpty()){
				saveErrors(request, errors);
			}else{
				List<InvigilatorsForExamTo> list=handler.searchTheInvigilators(invigilatorsForExamForm);
				if(list!=null && !list.isEmpty()){
					invigilatorsForExamForm.setList(list);
					setRequiredDataToForm(invigilatorsForExamForm);
					return mapping .findForward(CMSConstants.SEARCHED_INVIGILATORS_LIST);
				}else{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			invigilatorsForExamForm.setErrorMessage(msg);
			invigilatorsForExamForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initInvigilatorsForExam");
	}
	/**
	 * update the examId and users in 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateInvigilators(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		InvigilatorsForExamForm invigilatorsForExamForm=(InvigilatorsForExamForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
				List<Integer> list=new ArrayList<Integer>();
				List<InvigilatorsForExamTo> invigilatorsForExamTos=invigilatorsForExamForm.getList();
				Iterator<InvigilatorsForExamTo> iterator=invigilatorsForExamTos.iterator();
				while (iterator.hasNext()) {
					InvigilatorsForExamTo invigilatorsForExamTo = (InvigilatorsForExamTo) iterator.next();
					if(invigilatorsForExamTo.getChecked()==null){
						list.add(invigilatorsForExamTo.getId());
					}
				}
				setUserId(request, invigilatorsForExamForm); 
				if(list!=null && !list.isEmpty()){
					boolean flag=handler.updateInvigilators(list,invigilatorsForExamForm.getUserId());
					if(flag){
						reset(invigilatorsForExamForm);
						setRequiredDataToForm(invigilatorsForExamForm);
						ActionMessage message = new ActionMessage("knowledgepro.exam.icmr.updated");
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward("initInvigilatorsForExam");
					}else{
						errors.add("error", new ActionError("knowledgepro.phd.Setting.update.failed"));
						saveErrors(request, errors);
					}
				}else{
					errors.add(CMSConstants.ERROR, new ActionError("knowledgepro.exam.allotment.deselect.records.update"));
					saveErrors(request, errors);
				}
		}catch (Exception e) {
			e.printStackTrace();
			if (e instanceof BusinessException) {
				String msgKey = super.handleBusinessException(e);
				ActionMessage message = new ActionMessage(msgKey);
				messages.add("messages", message);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else if (e instanceof ApplicationException) {
				String msg = super.handleApplicationException(e);
				invigilatorsForExamForm.setErrorMessage(msg);
				invigilatorsForExamForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping .findForward(CMSConstants.SEARCHED_INVIGILATORS_LIST);
	}
}
