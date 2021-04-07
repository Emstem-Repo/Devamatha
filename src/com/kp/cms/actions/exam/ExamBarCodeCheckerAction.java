package com.kp.cms.actions.exam;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.lang.Validate;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.hibernate.classic.Validatable;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.ExamBarCodeCheckerForm;
import com.kp.cms.forms.exam.ExamUpdateCommonSubjectGroupForm;
import com.kp.cms.handlers.exam.ExamBarCodeCheckerHandler;
import com.kp.cms.handlers.exam.ExamRegDecrypt;
import com.kp.cms.handlers.exam.ExamSecuredMarksVerificationHandler;
import com.kp.cms.handlers.exam.ExamUpdateCommonSubjectGroupHandler;
import com.sun.net.ssl.internal.www.protocol.https.Handler;
@SuppressWarnings("unused")
public class ExamBarCodeCheckerAction extends BaseDispatchAction {
	ExamBarCodeCheckerHandler handler = new ExamBarCodeCheckerHandler();
	
	// gets initial list of Exam Definition
	public ActionForward initExamBarCodeChecker(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ExamBarCodeCheckerForm objForm =(ExamBarCodeCheckerForm)form;
		
		return mapping
				.findForward(CMSConstants.EXAM_BARCODE_CHECKER);

	}

	public ActionForward onCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ExamBarCodeCheckerForm objform =(ExamBarCodeCheckerForm)form;
		String regNo = objform.getRegistrNo();
		objform.validate(mapping, request);
			objform.setRegistrNo(handler.getDecryptRegNo(regNo));

		return mapping
		.findForward(CMSConstants.EXAM_BARCODE_CHECKER);
		

	}
	

	
}
