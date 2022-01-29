package com.kp.cms.actions.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.reports.CategoryWiseIntakeForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.reports.CategoryWiseIntakeHandler;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.reports.CategoryWiseMapTO;
import com.kp.cms.to.reports.CategoryWiseTO;

public class CategoryWiseIntakeAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(CategoryWiseIntakeAction.class);
	private static final String CANDIDATEWISE_STUDENTS_INTAKE = "categoryWiseListReport";
	
	public ActionForward initCategoryWiseIntake(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered into initCategoryWiseIntake in CategoryWiseIntakeAction class.");
		CategoryWiseIntakeForm categoryWiseIntakeForm = (CategoryWiseIntakeForm)form;
		try {
			categoryWiseIntakeForm.clear();
			setRequiredDataToForm(categoryWiseIntakeForm, request);
			HttpSession session = request.getSession(false);
			session.removeAttribute(CANDIDATEWISE_STUDENTS_INTAKE);
		}catch (Exception e) {
			log.error("Error occured at initCategoryWiseIntake of CategoryWiseIntakeAction",e);
			String msg = super.handleApplicationException(e);
			categoryWiseIntakeForm.setErrorMessage(msg);
			categoryWiseIntakeForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		log.info("exit of initCategoryWiseIntake in CategoryWiseIntakeAction class.");
		return mapping.findForward(CMSConstants.CANDIDATE_INTAKE);
	}
	/**
	 * This method is used to set Program Type to form.
	 * @param leaveReportForm
	 * @param request
	 * @throws Exception
	 */
	
	private void setRequiredDataToForm(CategoryWiseIntakeForm categoryWiseIntakeForm,
			HttpServletRequest request) throws Exception {
		log.info("entered into setRequiredDataToForm in CategoryWiseIntakeAction class.");
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		request.setAttribute("programTypeList", programTypeList);
		log.info("exit of setRequiredDataToForm in CategoryWiseIntakeAction class.");
	}

	/**
	 * Returns the resultList
	 */
	
	public ActionForward submitCategoryWiseIntake(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered into submitCategoryWiseIntake in CategoryWiseIntakeAction class.");
		CategoryWiseIntakeForm categoryWiseIntakeForm = (CategoryWiseIntakeForm)form;
		HttpSession session = request.getSession(false);
		if(session!=null){
			ActionMessages messages = new ActionMessages();
			ActionErrors errors = categoryWiseIntakeForm.validate(mapping, request);
			if (errors.isEmpty()) {	
				try {
					List<OrganizationTO> list = OrganizationHandler.getInstance().getOrganizationDetails();
					if(list != null && list.size() != 0){
						Iterator<OrganizationTO> iterator = list.iterator();
						while (iterator.hasNext()) {
							OrganizationTO organizationTO = (OrganizationTO) iterator
									.next();
							categoryWiseIntakeForm.setOrganizationName(organizationTO.getOrganizationName());
						}
					}
					//call of handler.
					Map<Integer, CategoryWiseTO> categoryWiseMap = CategoryWiseIntakeHandler.getInstance().getCategoryWiseIntakeDetails(categoryWiseIntakeForm);
					
					List<CasteTO> casteList = CasteHandler.getInstance().getCastes();
					CasteTO cto=new CasteTO();
					cto.setCasteId(0);
					cto.setCasteName("Others");
					casteList.add(cto);
					if(!categoryWiseMap.isEmpty()){
						List<CategoryWiseTO> categoryWiseList = new ArrayList<CategoryWiseTO>();
						categoryWiseList.addAll(categoryWiseMap.values());
						
						if(!categoryWiseList.isEmpty()){
							Iterator<CategoryWiseTO> iterator = categoryWiseList.iterator();
							
							while (iterator.hasNext()) {
								CategoryWiseTO object = (CategoryWiseTO) iterator.next();
								List<CategoryWiseMapTO> casteCategoryList = new ArrayList<CategoryWiseMapTO>();
								
								Iterator<CasteTO> castecategoryListItr = casteList.iterator();
								while (castecategoryListItr.hasNext()) {
									CasteTO casteTO = (CasteTO) castecategoryListItr.next();
									
									if(object.getCategoryMap().containsKey(casteTO.getCasteId())){
										casteCategoryList.add(object.getCategoryMap().get(casteTO.getCasteId()));
									}else{
										CategoryWiseMapTO categoryWiseMapTO = new CategoryWiseMapTO();
										
										categoryWiseMapTO.setCasteName(casteTO.getCasteName());
										categoryWiseMapTO.setIntakeValue(0);
										casteCategoryList.add(categoryWiseMapTO);
									}
								}
								object.setCategoryWiseList(casteCategoryList);
							}
						}
						session.setAttribute(CANDIDATEWISE_STUDENTS_INTAKE,categoryWiseList);
					}
				}catch (BusinessException businessException) {
					log.info("Exception submitCategoryWiseIntake");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					categoryWiseIntakeForm.setErrorMessage(msg);
					categoryWiseIntakeForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				}else {
					addErrors(request, errors);
					setRequiredDataToForm(categoryWiseIntakeForm, request);
					return mapping.findForward(CMSConstants.CANDIDATE_INTAKE);
				}
		}	
		log.info("exit of submitCategoryWiseIntake in CategoryWiseIntakeAction class.");
		return mapping.findForward(CMSConstants.CANDIDATE_INTAKE_RESULT);
	}
}