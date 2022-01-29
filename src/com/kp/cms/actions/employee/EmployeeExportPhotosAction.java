package com.kp.cms.actions.employee;
	import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;

import com.kp.cms.forms.admission.ExportPhotosForm;
import com.kp.cms.forms.employee.EmployeeExportPhotoForm;
import com.kp.cms.handlers.admission.ExportPhotosHandler;
import com.kp.cms.transactionsimpl.admission.ExportPhotosImpl;

	public class EmployeeExportPhotosAction extends BaseDispatchAction {

		ExportPhotosHandler handler = new ExportPhotosHandler();
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		private static final Log log = LogFactory.getLog(EmployeeExportPhotosAction.class);

		public ActionForward initExportPhotos(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			log.info("entering into init Export Photos action");
			errors.clear();
			messages.clear();
		//b	EmployeeExportPhotoForm objForm = (EmployeeExportPhotoForm) form;
			
			return mapping.findForward(CMSConstants.EMPLOYEE_EXPORT_PHOTOS);
		}

		
		
		protected class ByteArrayStreamInfo implements StreamInfo {

			protected String contentType;
			protected byte[] bytes;

			public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
				this.contentType = contentType;
				this.bytes = myXLSBytes;
			}

			public String getContentType() {
				return contentType;
			}

			public InputStream getInputStream() throws IOException {
				return new ByteArrayInputStream(bytes);
			}
		}
		
		
		
		
		
		
	}



