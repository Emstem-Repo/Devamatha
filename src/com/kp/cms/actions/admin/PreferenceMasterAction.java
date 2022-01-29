package com.kp.cms.actions.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;

public class PreferenceMasterAction extends BaseDispatchAction {
	
	public ActionForward initPreferenceMaster(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			return mapping.findForward(CMSConstants.INIT_PREFERENCE_MASTER);
	}

	public ActionForward submitPreferenceMaster(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return mapping.findForward(CMSConstants.SUBMIT_PREFERENCE_MASTER);
	}
}
