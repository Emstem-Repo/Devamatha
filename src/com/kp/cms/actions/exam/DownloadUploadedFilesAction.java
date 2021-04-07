package com.kp.cms.actions.exam;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.DownloadUploadedFileForm;
import com.kp.cms.to.exam.DownloadUploadedFileTO;

public class DownloadUploadedFilesAction extends BaseDispatchAction{
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(DownloadUploadedFilesAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initDownloadUploadedFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		DownloadUploadedFileForm downloadForm = (DownloadUploadedFileForm)form;
		try{
			List<DownloadUploadedFileTO> downloadFileTo = new ArrayList<DownloadUploadedFileTO>();
			downloadForm.setFormatName(null);
			String s1 = request.getRealPath("");
			
			s1= s1 +"/UploadedFiles";
			File f = new File(s1);
			
			File[] filearr =  f.listFiles();
			if(filearr.length!=0){
				for(int i=0;i<filearr.length;i++){
					File fileName = filearr[i];
					String fName = fileName.getName();
					DownloadUploadedFileTO fileTO = new DownloadUploadedFileTO();
					fileTO.setFileName(fName);
					fileTO.setOrgFileName(removeFileExtension(fName));
					downloadFileTo.add(fileTO);
				}
			}
			downloadForm.setDownloadFiles(downloadFileTo);
		}catch (Exception e) {
			String msg = super.handleApplicationException(e);
			downloadForm.setErrorMessage(msg);
			downloadForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.INIT_DOWNLOAD_UPLOADED_FILES);
		}
		return mapping.findForward(CMSConstants.INIT_DOWNLOAD_UPLOADED_FILES);
	}
	/**
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
}
