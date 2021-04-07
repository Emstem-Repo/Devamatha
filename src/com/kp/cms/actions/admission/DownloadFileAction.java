package com.kp.cms.actions.admission;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DownloadFileForm;

public class DownloadFileAction extends BaseDispatchAction {
private static final Log log = LogFactory.getLog(GensmartCardDataAction.class);
	
	/**
	 * Method to open the init jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDownloadFile(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered Download Formats Action Batch input");
		DownloadFileForm downloadFileForm = (DownloadFileForm) form;
		downloadFileForm.setFormatName(null);
		log.info("Exit Download Formats Action Batch input");
		
		return mapping.findForward(CMSConstants.DOWNLOAD_ZIP_FILE);
	}
	
}
