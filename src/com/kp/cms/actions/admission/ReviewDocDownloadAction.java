package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.ReviewDocDownloadForm;

public class ReviewDocDownloadAction extends DownloadAction {
	private static final Log log = LogFactory.getLog(ReviewDocDownloadAction.class);
	/**
	 * Method to download the document with document id as the input
	 */
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DocumentDownloadAction");
		ReviewDocDownloadForm documentDownloadForm = (ReviewDocDownloadForm) form;
		HttpSession session = request.getSession(false);
		int docid=Integer.valueOf(documentDownloadForm.getDocumentId());
		List<ApplnDoc> uploadList=null;
		if (session != null){
			uploadList=(List<ApplnDoc>)session.getAttribute(CMSConstants.STUDENT_DOCUMENT_DETAILS);
		}
		ApplnDoc applnDoc = null;

		if(uploadList!=null){
			Iterator<ApplnDoc> docItr=uploadList.iterator();
			while (docItr.hasNext()) {
				ApplnDoc origDoc = (ApplnDoc) docItr.next();
				if(origDoc.getDocType()!=null && origDoc.getDocType().getId()== docid)
				{
					applnDoc=origDoc;
				}
			}
		}
		String fileName ="";
		String contentType ="";
		byte [] myFileBytes =null;
		if(applnDoc!=null){
			myFileBytes = applnDoc.getDocument();
		fileName = applnDoc.getName();
		contentType = applnDoc.getContentType();
		response.setHeader("Content-disposition", "attachment; filename ="+fileName);
		}
		File file = null;
		try {
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			file = new File(filePath+"outFile.txt");
			InputStream inputStream = new ByteArrayStreamInfo(contentType,
					myFileBytes).getInputStream();
			OutputStream out = new FileOutputStream(file);
			byte buffer[] = new byte[1024];
			int length = 0;
			while ((length = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, length);
			}	
				out.close();
				inputStream.close();
		} catch (IOException e) {
			log.info("DocumentDownloadAction IOException, " +e);
		} catch (Exception e){
			log.info("DocumentDownloadAction Exception, " +e);
		}
		
		log.info("Exit DocumentDownloadAction");
		return new FileStreamInfo(contentType, file);
		
	}

	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myPdfBytes) {
			this.contentType = contentType;
			this.bytes = myPdfBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}

}
