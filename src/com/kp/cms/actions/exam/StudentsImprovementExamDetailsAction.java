package com.kp.cms.actions.exam;

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
import com.kp.cms.bo.exam.ExamStudentFinalMarkDetailsBO;
import com.kp.cms.bo.exam.StudentsImprovementExamDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.exam.PublishSupplementaryImpApplicationForm;
import com.kp.cms.forms.exam.StudentsImprovementExamDetailsForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.PublishSupplementaryImpApplicationHandler;
import com.kp.cms.handlers.exam.StudentsImprovementExamDetailsHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.exam.PublishSupplementaryImpApplicationTo;
import com.kp.cms.transactions.exam.IStudentsImprovementExamDetailsTransaction;
import com.kp.cms.transactionsimpl.exam.StudentsImprovementExamDetailsTransactionsImpl;
import com.kp.cms.utilities.CommonUtil;


public class StudentsImprovementExamDetailsAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(StudentsImprovementExamDetailsAction.class);

	public ActionForward init(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentsImprovementExamDetailsForm objForm = (StudentsImprovementExamDetailsForm) form;
		objForm.resetFields();
		try{
			
			}catch (Exception e) {}
		return mapping.findForward(CMSConstants.STUDENTS_IMPROVEMENT_EXAM_DETAILS);
	}
	
	public ActionForward loadClassByExamNameAndYear(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception
	{
		StudentsImprovementExamDetailsForm actionForm= (StudentsImprovementExamDetailsForm) form;
		try {
			Map<Integer, String> classMap=StudentsImprovementExamDetailsHandler.getInstance().loadClassByExamNameAndYear(actionForm);
			classMap=CommonUtil.sortMapByValueForAlphaNumeric(classMap);
			actionForm.setMapClass(classMap);
			Map<Integer, String> examNameMap = CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(actionForm.getExamType(),Integer.parseInt(actionForm.getYear())); ;// getting exam list based on exam Type and academic year
			examNameMap = (Map<Integer, String>) CommonUtil.sortMapByValue(examNameMap);// sorting the exam names based on name
			actionForm.setExamNameList(examNameMap);
		} catch (Exception e) {
			log.debug(e.getMessage());
			e.printStackTrace();
		}
		
		return mapping.findForward(CMSConstants.STUDENTS_IMPROVEMENT_EXAM_DETAILS);
	}
	
	public ActionForward addStudentsImprovementExamDetails(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		StudentsImprovementExamDetailsForm actionForm= (StudentsImprovementExamDetailsForm) form;
		boolean isAdded =false;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = actionForm.validate(mapping, request);
		IStudentsImprovementExamDetailsTransaction Txn=StudentsImprovementExamDetailsTransactionsImpl.getInstance();
		try{
			setUserId(request, actionForm);
			if(errors.isEmpty()){
				Txn.deleleteAlreadyExistedRecords(Integer.parseInt(actionForm.getClassCodeIdsFrom()));
				Map<String,ExamStudentFinalMarkDetailsBO> boMap = StudentsImprovementExamDetailsHandler.getInstance().getStudentsImprovementExamDetails(actionForm);
				if(boMap.size()>0){
					List<StudentsImprovementExamDetailsBO> boList = StudentsImprovementExamDetailsHandler.getInstance().saveStudentsImprovementExamMarksFlag(actionForm,boMap);
					isAdded = Txn.saveStudentsImprovementExamMarksFlag(boList);
					if (isAdded) {
						ActionMessage message = new ActionMessage("knowledgepro.exam.imp.exam.flag.add.success");
						messages.add("messages", message);
						saveMessages(request, messages);
						actionForm.reset(mapping, request);

					}else{
						// failed
						errors.add("error", new ActionError("knowledgepro.exam.imp.exam.flag.add.failure"));
						saveErrors(request, errors);

					}
				}
				else{
					// failed
					errors.add("error", new ActionError("knowledgepro.norecords"));
					saveErrors(request, errors);
				}
			}
		}
		catch (Exception e) {

				errors.add("error", new ActionError("knowledgepro.exam.imp.exam.flag.add.failure"));
				saveErrors(request, errors);
				
				return mapping.findForward(CMSConstants.CASTE_ENTRY);	
			
		
		}
		

		
	
		
		
		return mapping.findForward(CMSConstants.STUDENTS_IMPROVEMENT_EXAM_DETAILS);
	}

	
	
}
