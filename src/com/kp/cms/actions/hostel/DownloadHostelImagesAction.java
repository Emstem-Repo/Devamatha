package com.kp.cms.actions.hostel;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.hostel.HostelExportPhotosForm;
import com.kp.cms.handlers.hostel.AvailableSeatsHandler;
import com.kp.cms.transactions.hostel.IDownloadHostelImagesTransaction;
import com.kp.cms.transactionsimpl.hostel.DownloadHostelImagesTxnImpl;

public class DownloadHostelImagesAction extends DownloadAction{
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(DownloadHostelImagesAction.class);
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered DownloadHostelImagesAction");
		HostelExportPhotosForm objForm = (HostelExportPhotosForm)form;
		IDownloadHostelImagesTransaction transaction = DownloadHostelImagesTxnImpl.getInstance();
		errors.clear();
		messages.clear();
		int year = (objForm.getAcademicYear() != null && objForm.getAcademicYear().trim().length() > 0 ? Integer .parseInt(objForm.getAcademicYear()) : 0);
		int hostelId = (objForm.getHostelId()!=null && objForm.getHostelId().trim().length() > 0 ? Integer.parseInt(objForm.getHostelId()) : 0);
		int blockId = (objForm.getBlockId()!=null && objForm.getBlockId().trim().length() > 0 ? Integer.parseInt(objForm.getBlockId()) : 0);
		int unitId = (objForm.getUnitId()!=null && objForm.getUnitId().trim().length() > 0 ? Integer.parseInt(objForm.getUnitId()) : 0);
		String fileName = "HostelImages";
		String filePath = request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File tempFile = new File(filePath+fileName + ".zip");
		if (tempFile.exists()) {
			tempFile.delete();
		}
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
		String contentType = fileName + ".zip";
		response.setHeader("Content-disposition", "attachment; filename ="
				+ fileName + ".zip");
		response.setContentType("application/zip");
		saveErrors(request, errors);
		//if(errors.isEmpty()){
			int totalImages = 0;
			totalImages = transaction.getImages(year,hostelId,blockId,unitId);
			if(totalImages > 0){
				int PAGESIZE = 5;
				int page = 1;
				int totalPages = (totalImages / PAGESIZE) + 1;

				while (page <= totalPages) {
					List<ApplnDoc> images = transaction.getImages(year, hostelId, blockId, unitId,page,PAGESIZE);
					InputStream in = null;
					int size = images.size();
					if (size != 0) {
						for (int i = 0; i < size; i++) {
							ApplnDoc applnDoc = images.get(i);
							Set<Student> student = applnDoc.getAdmAppln().getStudents();
							Iterator< Student> iterator = student.iterator();
							String regNo ="";
							while (iterator.hasNext()) {
								Student student2 = (Student) iterator.next();
								regNo = student2.getRegisterNo(); 
							}
							if (applnDoc.getIsPhoto() != null){
								if (applnDoc.getIsPhoto()
										&& applnDoc.getDocument() != null) {
									byte[] myFileBytes = applnDoc.getDocument();
									String imageName="";
									if(regNo != null && !regNo.isEmpty()){
										imageName = regNo+ ".jpg";
									}else{
										imageName = applnDoc.getAdmAppln().getApplnNo()+".jpg";
									}
									

									try {
										in = new ByteArrayInputStream(
												myFileBytes);
										byte buffer[] = new byte[2048];

										zipOutputStream.putNextEntry(new ZipEntry(
												imageName));
										int len;
										while ((len = in.read(buffer)) > 0) {
											zipOutputStream.write(buffer, 0, len);
										}
									} catch (IOException e) {
										e.printStackTrace();
										log .info("DownloadHostelImagesAction IOException, "
														+ e);
									} catch (Exception e) {
										e.printStackTrace();
										log .info("DownloadHostelImagesAction Exception, "
														+ e);
									}
								  }
								}
								zipOutputStream.closeEntry();
							}
						}
						page = page + 1;
				}
				zipOutputStream.close();
			}
			objForm.setHostelId(null);
			objForm.setBlockId(null);
			objForm.setUnitId(null);
		//}
		return new FileStreamInfo(contentType, tempFile);
	}
	/**
	 * @author christ
	 *
	 */
	
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
