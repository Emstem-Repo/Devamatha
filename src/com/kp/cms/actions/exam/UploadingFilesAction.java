package com.kp.cms.actions.exam;

import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.UploadingFilesForm;

public class UploadingFilesAction extends BaseDispatchAction{

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadingFiles(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
		UploadingFilesForm uploadingFilesForm = (UploadingFilesForm)form;
		uploadingFilesForm.setFormFile(null);
		return mapping.findForward(CMSConstants.INIT_UPLOAD_FILES);
		}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward submitUploadingFiles(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UploadingFilesForm uploadingFilesForm = (UploadingFilesForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		try{
			if(uploadingFilesForm.getFormFile()!=null && !uploadingFilesForm.getFormFile().toString().isEmpty()){
				FormFile file = uploadingFilesForm.getFormFile();
				String filePath = request.getRealPath("");
				filePath= filePath +"/UploadedFiles";
				File folder = new File(filePath);
				if(!folder.exists()){
					folder.mkdir();
				}
				String fileName = file.getFileName();
				if(!("").equals(fileName)){
					File uploadFileName = new File(filePath, fileName);
					if(!uploadFileName.exists()){
						FileOutputStream fos = new FileOutputStream(uploadFileName);
						fos.write(file.getFileData());
						fos.flush();
						fos.close();
					}
					request.setAttribute("filePath",uploadFileName.getAbsoluteFile());
				}
			}else{
				errors .add( "error", new ActionError( "knowledgepro.exam.upload.file.isRequired"));
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.INIT_UPLOAD_FILES);
			}
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			uploadingFilesForm.setErrorMessage(msg);
			uploadingFilesForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.INIT_UPLOAD_FILES);
		}
		ActionMessage message = new ActionError( "knowledgepro.exam.upload.file.success");
		messages.add("messages", message);
		saveMessages(request, messages);
		uploadingFilesForm.setFormFile(null);
		return mapping.findForward(CMSConstants.INIT_UPLOAD_FILES);
	}
}
