package com.kp.cms.actions.examallotment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import com.kp.cms.forms.examallotment.ExamInvigilatorDutyExemptionForm;
import com.kp.cms.handlers.examallotment.ExamInvigilatorDutyExemptionHandler;
import com.kp.cms.to.examallotment.ExamInvigilatorDutyExemptionTo;

public class ExamInvigilatorDutyExemptionAction extends BaseDispatchAction{
	ExamInvigilatorDutyExemptionHandler examInvigilatorDutyExemptionHandler=ExamInvigilatorDutyExemptionHandler.getInstance();
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExamInvigilatorsDutyExemption(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm=(ExamInvigilatorDutyExemptionForm)form;
		reset(examInvigilatorDutyExemptionForm);
		setRequiredDataToForm(examInvigilatorDutyExemptionForm);
		return mapping.findForward("initExamInvigilatorsDutyExemption");
	}

	private void reset(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
		examInvigilatorDutyExemptionForm.setDeptId(null);
		examInvigilatorDutyExemptionForm.setLocationId(null);
		examInvigilatorDutyExemptionForm.setList(null);
		examInvigilatorDutyExemptionForm.setExemptionId(null);
		examInvigilatorDutyExemptionForm.setDeanaryId(null);
		examInvigilatorDutyExemptionForm.setExamInvigilatorsDutyExemptionMap(null);
	}

	private void setRequiredDataToForm(
			ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm) throws Exception{
		//set exemptionMap,deanarymap and department map
		examInvigilatorDutyExemptionHandler.getAllMaps(examInvigilatorDutyExemptionForm);
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
		ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm=(ExamInvigilatorDutyExemptionForm)form;
		examInvigilatorDutyExemptionForm.setList(null);
		examInvigilatorDutyExemptionForm.setExamInvigilatorsDutyExemptionMap(null);
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = examInvigilatorDutyExemptionForm.validate(mapping, request);
		try {
			if(!errors.isEmpty() && errors!=null){
				saveErrors(request, errors);
			}else{
				List<ExamInvigilatorDutyExemptionTo> list=examInvigilatorDutyExemptionHandler.getInvigilatorsList(examInvigilatorDutyExemptionForm);
				if(list!=null && !list.isEmpty()){
					examInvigilatorDutyExemptionForm.setList(list);
					setRequiredDataToForm(examInvigilatorDutyExemptionForm);
					return mapping .findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EXEMPTION_LIST);
				}else{
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}
		}catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			examInvigilatorDutyExemptionForm.setErrorMessage(msg);
			examInvigilatorDutyExemptionForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("initExamInvigilatorsDutyExemption");
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
		ExamInvigilatorDutyExemptionForm examInvigilatorDutyExemptionForm=(ExamInvigilatorDutyExemptionForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		try{
			setUserId(request, examInvigilatorDutyExemptionForm); 
				Map<Integer,Integer> map=examInvigilatorDutyExemptionForm.getExamInvigilatorsDutyExemptionMap();
				List<Integer> theRecordsToSetIsActiveIsFalse=new ArrayList<Integer>();
				List<Integer> theRecordsToSetIsActiveIsTrue=new ArrayList<Integer>();
				List<Integer> therecordsExists=new ArrayList<Integer>();
				List<ExamInvigilatorDutyExemptionTo> list=new ArrayList<ExamInvigilatorDutyExemptionTo>();
				List<ExamInvigilatorDutyExemptionTo> examInvigilatorDutyExemptionTos=examInvigilatorDutyExemptionForm.getList();
				Iterator<ExamInvigilatorDutyExemptionTo> iterator=examInvigilatorDutyExemptionTos.iterator();
				while (iterator.hasNext()) {
					ExamInvigilatorDutyExemptionTo examInvigilatorDutyExemptionTo = (ExamInvigilatorDutyExemptionTo) iterator.next();
					if(examInvigilatorDutyExemptionTo.getChecked()!=null && !examInvigilatorDutyExemptionTo.getChecked().isEmpty() && examInvigilatorDutyExemptionTo.getChecked().equalsIgnoreCase("on")){
						if(map!=null && !map.isEmpty() && map.containsKey(examInvigilatorDutyExemptionTo.getUserId())){
							theRecordsToSetIsActiveIsTrue.add(map.get(examInvigilatorDutyExemptionTo.getUserId()));
							therecordsExists.add(map.get(examInvigilatorDutyExemptionTo.getUserId()));
						}else{
							list.add(examInvigilatorDutyExemptionTo);
						}
						examInvigilatorDutyExemptionTo.setChecked(null);
					}else if(map!=null && !map.isEmpty() && map.containsKey(examInvigilatorDutyExemptionTo.getUserId())){
						theRecordsToSetIsActiveIsFalse.add(map.get(examInvigilatorDutyExemptionTo.getUserId()));
						therecordsExists.add(map.get(examInvigilatorDutyExemptionTo.getUserId()));
					}
				}
				if((list!=null && !list.isEmpty()) || (therecordsExists!=null && !therecordsExists.isEmpty())){
					boolean flag=examInvigilatorDutyExemptionHandler.addInvigilators(list,examInvigilatorDutyExemptionForm,theRecordsToSetIsActiveIsFalse,theRecordsToSetIsActiveIsTrue,therecordsExists);
					if(flag){
						reset(examInvigilatorDutyExemptionForm);
						setRequiredDataToForm(examInvigilatorDutyExemptionForm);
						ActionMessage message = new ActionMessage("knowledgepro.exam.allotment.invigilators.add.success");
						messages.add("messages", message);
						saveMessages(request, messages);
						return mapping.findForward("initExamInvigilatorsDutyExemption");
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
				examInvigilatorDutyExemptionForm.setErrorMessage(msg);
				examInvigilatorDutyExemptionForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			} else {
				throw e;
			}
		}
		return mapping .findForward(CMSConstants.EXAM_INVIGILATORS_DUTY_EXEMPTION_LIST);
	}
}
