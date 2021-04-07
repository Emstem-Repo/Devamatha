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
import com.kp.cms.forms.exam.MarksCardSiNoForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.exam.MarksCardSiNoHandler;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.exam.MarksCardSiNoTO;

public class MarksCardSiNoAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(MarksCardSiNoAction.class);
	
	
	public ActionForward initSiNo(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response)throws Exception{
		MarksCardSiNoForm cardSiNoForm = (MarksCardSiNoForm)form;
		try{
			cardSiNoForm.reset();
			setData(cardSiNoForm);
			setUserId(request, cardSiNoForm);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return mapping.findForward(CMSConstants.INIT_SI_NO);
	}
	
	private void setData(MarksCardSiNoForm cardSiNoForm) throws Exception {
		// TODO Auto-generated method stub
		List<MarksCardSiNoTO> toList = MarksCardSiNoHandler.getInstance().getDataConvert();
		cardSiNoForm.setToList(toList);		
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
		cardSiNoForm.setProgramTypeList(programTypeList);
	}

	public ActionForward save(ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception{
		MarksCardSiNoForm cardSiNoForm = (MarksCardSiNoForm)form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = new ActionErrors();
		errors = cardSiNoForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
				if(StringUtils.isNumeric(cardSiNoForm.getStartNo())){
					boolean isAvailable = MarksCardSiNoHandler.getInstance().getData(cardSiNoForm);
					if(isAvailable){
						errors.add("errors",new ActionError(CMSConstants.ALREADY_AVAILABLE));
						saveErrors(request, errors);
						List<MarksCardSiNoTO> toList = MarksCardSiNoHandler.getInstance().getDataConvert();
						cardSiNoForm.setToList(toList);
						cardSiNoForm.reset();
						setData(cardSiNoForm);
						return mapping.findForward(CMSConstants.INIT_SI_NO);
					}else{
						boolean isAdded = MarksCardSiNoHandler.getInstance().save(cardSiNoForm);
						if(isAdded){
							ActionMessage message = new ActionMessage("knowledgepro.exam.marks.si.no.added");
							messages.add(CMSConstants.MESSAGES, message);
							saveMessages(request, messages);
							cardSiNoForm.reset();
							cardSiNoForm.setIsAdded(true);
							List<MarksCardSiNoTO> toList = MarksCardSiNoHandler.getInstance().getDataConvert();
							cardSiNoForm.setToList(toList);
							cardSiNoForm.reset();
							setData(cardSiNoForm);
							return mapping.findForward(CMSConstants.INIT_SI_NO);
						}
					}
				}else {
					errors.add("errors", new ActionError(CMSConstants.ENTER_ONLY_NUMERIC));
					saveErrors(request, errors);
					setData(cardSiNoForm);
					return mapping.findForward(CMSConstants.INIT_SI_NO);
					
				}
			  }else{
				saveErrors(request, errors);
				setData(cardSiNoForm);
				return mapping.findForward(CMSConstants.INIT_SI_NO);
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setData(cardSiNoForm);
		return mapping.findForward(CMSConstants.INIT_SI_NO);
	}
}
