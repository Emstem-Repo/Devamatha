package com.kp.cms.actions.admission;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.kp.cms.forms.admission.ExportPhotosForm;
import com.kp.cms.handlers.admission.ExportPhotosHandler;

public class ExportPhotosAction extends BaseDispatchAction {

	ExportPhotosHandler handler = new ExportPhotosHandler();
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(ExportPhotosAction.class);

	public ActionForward initExportPhotos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into init Export Photos action");
		errors.clear();
		messages.clear();
		ExportPhotosForm objForm = (ExportPhotosForm) form;
		int year = new Date().getYear();
		objForm.setListProgram(handler.getPrograms(year + 1900));
		objForm.setProgramId(null);
//		objForm.setFolderName(null);
		return mapping.findForward(CMSConstants.EXPORT_PHOTOS);
	}

//	public ActionForward savePhotos(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		log.info("entering into init Export Photos action");
//
//		ExportPhotosForm objForm = (ExportPhotosForm) form;
//		errors.clear();
//		messages.clear();
//		errors = objForm.validate(mapping, request);
//		saveErrors(request, errors);
//		if (errors.isEmpty()) {
////			String folderName = objForm.getFolderName();
////			handler.saveInages(Integer.parseInt(objForm.getAdmittedYear()),
////					Integer.parseInt(objForm.getProgramId()),request,response);
////			
////			handler.saveInages(mapping,form,request, response);
//			
//			ActionMessage message = new ActionMessage(
//					"knowledgepro.admission.ExportPhotos.success");
//			messages.add("messages", message);
//			saveMessages(request, messages);
//			objForm.setProgramId(null);
////			objForm.setFolderName(null);
//			int year = new Date().getYear();
//			objForm.setListProgram(handler.getPrograms(year + 1900));
//			objForm.setProgramId(null);
//			//objForm.setFolderName(null);
//		}
//		return mapping.findForward(CMSConstants.EXPORT_PHOTOS);
//	}
}
