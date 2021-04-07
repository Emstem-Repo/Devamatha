package com.kp.cms.actions.exam;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
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
import com.kp.cms.forms.exam.ConsolidateMarksCardSiNoForm;
import com.kp.cms.forms.exam.MarksCardSiNoForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.exam.ConsolidateMarksCardSiNoHandler;
import com.kp.cms.handlers.exam.MarksCardSiNoHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.ConsolidateMarksCardSiNoTO;
import com.kp.cms.to.exam.MarksCardSiNoTO;


public class ConsolidateMarksCardSiNoAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ConsolidateMarksCardSiNoAction.class);
	
	
	public ActionForward initSiNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm = (ConsolidateMarksCardSiNoForm)form;
		try{
			consolidateMarksCardSiNoForm.reset();
			setData(consolidateMarksCardSiNoForm);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
	}
	
	private void setData(ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm) throws Exception {
		// TODO Auto-generated method stub
		List<ConsolidateMarksCardSiNoTO> toList = ConsolidateMarksCardSiNoHandler.getInstance().getDataConvert();
		consolidateMarksCardSiNoForm.setToList(toList);
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		consolidateMarksCardSiNoForm.setProgramTypeList(programTypeList);
	}

	public ActionForward save(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		ConsolidateMarksCardSiNoForm consolidateMarksCardSiNoForm = (ConsolidateMarksCardSiNoForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors = consolidateMarksCardSiNoForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				if(StringUtils.isNumeric(consolidateMarksCardSiNoForm.getStartNo())){
					boolean isAvailable = ConsolidateMarksCardSiNoHandler.getInstance().getData(consolidateMarksCardSiNoForm);
					if(isAvailable){
						errors.add("errors",new ActionError(CMSConstants.ALREADY_AVAILABLE));
						saveErrors(request, errors);
						List<ConsolidateMarksCardSiNoTO> toList = ConsolidateMarksCardSiNoHandler.getInstance().getDataConvert();
						consolidateMarksCardSiNoForm.setToList(toList);
						consolidateMarksCardSiNoForm.reset();
						setData(consolidateMarksCardSiNoForm);
						return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
					}else{
						boolean isAdded = ConsolidateMarksCardSiNoHandler.getInstance().save(consolidateMarksCardSiNoForm);
						if(isAdded){
							ActionMessage message = new ActionMessage("knowledgepro.exam.marks.si.no.added");
							messages.add(CMSConstants.MESSAGES, message);
							saveMessages(request, messages);
							consolidateMarksCardSiNoForm.reset();
							consolidateMarksCardSiNoForm.setIsAdded(true);
							List<ConsolidateMarksCardSiNoTO> toList = ConsolidateMarksCardSiNoHandler.getInstance().getDataConvert();
							consolidateMarksCardSiNoForm.setToList(toList);
							consolidateMarksCardSiNoForm.reset();
							setData(consolidateMarksCardSiNoForm);
							return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
						}
					}
				}else {
					errors.add("errors", new ActionError(CMSConstants.ENTER_ONLY_NUMERIC));
					saveErrors(request, errors);
					setData(consolidateMarksCardSiNoForm);
					return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
					
				}
			  }else{
				saveErrors(request, errors);
				setData(consolidateMarksCardSiNoForm);
				return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setData(consolidateMarksCardSiNoForm);
		return mapping.findForward(CMSConstants.INIT_CONSOLIDATE_MARKS_SI_NO);
	}
	
	

		

}
