package com.kp.cms.actions.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;
import com.kp.cms.handlers.admin.StudentUploadPhotoHandler;
import com.kp.cms.to.admin.StudentUploadPhotoTO;

@SuppressWarnings("deprecation")
public class StudentUploadPhotoAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(StudentUploadPhotoAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * Works for upload student photos to database
	 * @throws Exception
	 */
	public ActionForward uploadStudentPhotos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		log.info("entering into uploadStudentPhotos  type action");	
		StudentUploadPhotoForm studentUploadPhotoForm = (StudentUploadPhotoForm)form;
		ActionErrors errors = studentUploadPhotoForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request, studentUploadPhotoForm);
		
		if (!errors.isEmpty()) {
			saveErrors(request, errors);		
			return mapping.findForward(CMSConstants.UPLOAD_PHOTOS);
			}
		try{	
			StudentUploadPhotoHandler studentUploadPhotoHandler=StudentUploadPhotoHandler.getInstance();
			boolean isAdded=studentUploadPhotoHandler.uploadPhotos(studentUploadPhotoForm,request);
			if (isAdded) {
				// success .
				ActionMessage message = new ActionMessage("knowledgepro.admin.uploadstudentphoto.addsuccess");
				messages.add("messages", message);
				saveMessages(request, messages);
				studentUploadPhotoForm.reset(mapping, request);
				studentUploadPhotoForm.setYear(null);
				studentUploadPhotoForm.setIsApplicationNo("ApplicationNo");
			} else {
				// failed
				errors.add("error", new ActionError("knowledgepro.admin.uploadstudentphoto.addfailure"));
				saveErrors(request, errors);
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			if(msg.equals("The Images name should be application No....")){
				ActionMessage message = new ActionMessage("knowledgepro.admin.uploadstudentphoto.applNo");
				messages.add("messages", message);
				saveMessages(request, messages);
//				studentUploadPhotoForm.reset(mapping, request);
				studentUploadPhotoForm.setYear(null);
				studentUploadPhotoForm.setIsApplicationNo("ApplicationNo");
				return  mapping.findForward(CMSConstants.UPLOAD_PHOTOS);
			}
			if(e instanceof NullPointerException){
				ActionMessage message = new ActionMessage("knowledgepro.admin.uploadstudentphoto.NoPhotos");
				messages.add("messages", message);
				saveMessages(request, messages);

				studentUploadPhotoForm.setYear(null);
				studentUploadPhotoForm.setIsApplicationNo("ApplicationNo");
				return  mapping.findForward(CMSConstants.UPLOAD_PHOTOS);	
			}
			studentUploadPhotoForm.setErrorMessage(msg);
			studentUploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		
		log.info("exit from  student upload photo action");
		return  mapping.findForward(CMSConstants.UPLOAD_PHOTOS);
		
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadStudentPhotos(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StudentUploadPhotoForm studentUploadPhotoForm=(StudentUploadPhotoForm)form;
		try {
			studentUploadPhotoForm.setYear(null);
			studentUploadPhotoForm.setIsApplicationNo("ApplicationNo");
		} catch (Exception e) {
			
			log.error("Error occured in initUploadStudentPhotos of StudentUploadPhotoAction", e);
		}
		
		return mapping.findForward(CMSConstants.UPLOAD_PHOTOS);
	}
	
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * added by mahi
	 */
	public ActionForward initUploadFinalYearStudentPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			StudentUploadPhotoForm studentUploadPhotoForm=(StudentUploadPhotoForm)form;
			resetFields(studentUploadPhotoForm);
			HttpSession session=request.getSession(false);
			if(session.getAttribute("studentIdforConvocation").toString()!=null && !session.getAttribute("studentIdforConvocation").toString().isEmpty()){
				studentUploadPhotoForm.setUploadPhotoDetails(StudentUploadPhotoHandler.getInstance().checkStudentPhotoIsApprovedOrRejected(session.getAttribute("studentIdforConvocation").toString(),studentUploadPhotoForm));
			}
			log.info("Entered in initUploadFinalYearStudentPhoto of StudentUploadPhotoAction");
		
		return mapping.findForward(CMSConstants.UPLOAD_FINAL_YEAR_STUDENT_PHOTOS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * added by mahi
	 */
	public ActionForward uploadFinalYearStudentPhoto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered in uploadFinalYearStudentPhoto of StudentUploadPhotoAction");
		StudentUploadPhotoForm studentUploadPhotoForm = (StudentUploadPhotoForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, studentUploadPhotoForm);
		HttpSession session=request.getSession(false);
		validateData(studentUploadPhotoForm,errors,request);
		try{	
			if(errors.isEmpty()){
				boolean isSaved=false;
				if(session.getAttribute("studentIdforConvocation").toString()!=null && !session.getAttribute("studentIdforConvocation").toString().isEmpty()){
					 isSaved=StudentUploadPhotoHandler.getInstance().uploadFinalYearPhotos(studentUploadPhotoForm,session.getAttribute("studentIdforConvocation").toString());
					 if(isSaved){
						    messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.final.year.student.photo.uploaded.successfully"));
							saveMessages(request, messages);
							resetFields(studentUploadPhotoForm);
							if(session.getAttribute("studentIdforConvocation").toString()!=null && !session.getAttribute("studentIdforConvocation").toString().isEmpty()){
								studentUploadPhotoForm.setUploadPhotoDetails(StudentUploadPhotoHandler.getInstance().checkStudentPhotoIsApprovedOrRejected(session.getAttribute("studentIdforConvocation").toString(),studentUploadPhotoForm));
							}
					 }else{
						    errors.add("error", new ActionError("knowledgepro.final.year.student.photo.uploaded.failure"));
							addErrors(request, errors);
							resetFields(studentUploadPhotoForm);
							if(session.getAttribute("studentIdforConvocation").toString()!=null && !session.getAttribute("studentIdforConvocation").toString().isEmpty()){
								studentUploadPhotoForm.setUploadPhotoDetails(StudentUploadPhotoHandler.getInstance().checkStudentPhotoIsApprovedOrRejected(session.getAttribute("studentIdforConvocation").toString(),studentUploadPhotoForm));
							}
					 }
				}
			}else{
				addErrors(request, errors);
				studentUploadPhotoForm.setStudentPhoto(null);
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			studentUploadPhotoForm.setErrorMessage(msg);
			studentUploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.STUDENT_ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.UPLOAD_FINAL_YEAR_STUDENT_PHOTOS);
	}
	
	/**
	 * @param studentUploadPhotoForm
	 * @param errors
	 * added by mahi
	 */
	private void validateData(StudentUploadPhotoForm studentUploadPhotoForm,ActionErrors errors,HttpServletRequest request) {
		try{
		int maxPhotoSize=102400;
		FormFile file=null;
		if(studentUploadPhotoForm.getStudentPhoto()!=null)
			file=studentUploadPhotoForm.getStudentPhoto();
		
		byte[] fileData = file.getFileData();
		String contentType=file.getContentType();
		String fileName=file.getFileName();
		if( file!=null && maxPhotoSize< file.getFileSize()){
				String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+fileName);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			    OutputStream out = new FileOutputStream(file1);
			    byte buffer[] = new byte[1024];
			    int len;
			    while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			    }
			    out.close();
			    inputStream.close();
			    //compress Image Start
			    BufferedImage i = ImageIO.read(new File(filePath+fileName));
			    float quality=0.9f;
		        // Get a ImageWriter for jpeg format.
		        Iterator<ImageWriter> writers = ImageIO.getImageWritersBySuffix("jpeg");
		        if (!writers.hasNext()) throw new IllegalStateException("No writers found");
		          ImageWriter writer = (ImageWriter) writers.next();
		        // Create the ImageWriteParam to compress the image.
		        ImageWriteParam param = writer.getDefaultWriteParam();
		        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		        param.setCompressionQuality(quality);
		       // The output will be a ByteArrayOutputStream (in memory)
		        ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
		        ImageOutputStream ios = ImageIO.createImageOutputStream(bos);
		        writer.setOutput(ios);
		        writer.write(null, new IIOImage(i, null, null), param);
		        ios.flush(); // otherwise the buffer size will be zero!
		        // From the ByteArrayOutputStream create a RenderedImage.
		        @SuppressWarnings("unused")
				ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
		        File file3 = new File(filePath+"compressed."+fileName);
		        FileImageOutputStream output = new FileImageOutputStream(file3);
		        writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
		        byte[] buf = new byte[(int) file3.length()] ;
		        FileInputStream fileInputStream=new FileInputStream(file3);
		        fileInputStream.read(buf);
				if(file!=null && file.getFileData().length !=0){
					studentUploadPhotoForm.setPhoto(buf);
				}
				 //compress Image End
		}else{
			if(file!=null && file.getFileData().length !=0){
				studentUploadPhotoForm.setPhoto(file.getFileData());
			}
		}
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * added by mahi
	 */
	public ActionForward initFinalYearStudentPhotoDisplay(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentUploadPhotoForm uploadPhotoForm=(StudentUploadPhotoForm) form;
		resetFields(uploadPhotoForm);
		ActionErrors errors = new ActionErrors();
		try{
			setDataToStudentPhotoDisplay(uploadPhotoForm,errors,request);
		}catch (Exception e) {
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			uploadPhotoForm.setErrorMessage(msg);
			uploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FINAL_YEAR_STUDENT_PHOTO_DISPLAY);
	}
	
	/**
	 * @param photoForm
	 * @throws Exception
	 * added by mahi
	 */
	public void setDataToStudentPhotoDisplay(StudentUploadPhotoForm photoForm,ActionErrors errors,HttpServletRequest request) throws Exception{
       Map<Integer, String> photoClassMap=StudentUploadPhotoHandler.getInstance().getClassesFromStudentPhotoUpload();
       photoForm.setPhotoClassMap(photoClassMap);
       List<StudentUploadPhotoTO> uploadPhotoTOList=StudentUploadPhotoHandler.getInstance().viewFinalYearStudentPhotos(photoForm);
		if(uploadPhotoTOList!=null && !uploadPhotoTOList.isEmpty()){
			photoForm.setUploadPhotoTOList(uploadPhotoTOList);
		}else{
			 errors.add("error", new ActionError("knowledgepro.final.year.student.photo.uploaded.empty"));
			 addErrors(request, errors);
		}
	}
	
	/**
	 * @param photoForm
	 * added by mahi
	 */
	public void resetFields(StudentUploadPhotoForm photoForm){
		photoForm.setPhotoClassMap(null);
		photoForm.setStudentPhoto(null);
		photoForm.setStudentClassId(null);
		photoForm.setPhoto(null);
		photoForm.setUploadPhotoTOList(null);
		photoForm.setRejectedReason(null);
		photoForm.setUploadPhotoDetails(null);
		photoForm.setStudentImageName(null);
		photoForm.setStudentIdList(null);
		photoForm.setLengthOfStudentPhotos(0);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public ActionForward viewFinalYearStudentPhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentUploadPhotoForm studentUploadPhotoForm = (StudentUploadPhotoForm)form;
		ActionErrors errors = new ActionErrors();
		setUserId(request, studentUploadPhotoForm);
		try{
			List<StudentUploadPhotoTO> uploadPhotoTOList=StudentUploadPhotoHandler.getInstance().viewFinalYearStudentPhotos(studentUploadPhotoForm);
			if(uploadPhotoTOList!=null && !uploadPhotoTOList.isEmpty()){
				studentUploadPhotoForm.setUploadPhotoTOList(uploadPhotoTOList);
			}else{
				 errors.add("error", new ActionError("knowledgepro.final.year.student.photo.uploaded.empty"));
				 addErrors(request, errors);
				 studentUploadPhotoForm.setUploadPhotoTOList(null);
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			studentUploadPhotoForm.setErrorMessage(msg);
			studentUploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FINAL_YEAR_STUDENT_PHOTO_DISPLAY);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return added by mahi
	 * @throws Exception
	 */
	public ActionForward approveStudentPhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentUploadPhotoForm uploadPhotoForm = (StudentUploadPhotoForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, uploadPhotoForm);
		try{
			boolean isUploaded=StudentUploadPhotoHandler.getInstance().approveFinalYearStudentPhotos(uploadPhotoForm);
			if(isUploaded){
				    boolean isMailSmsSent=StudentUploadPhotoHandler.getInstance().sendMailAndSmsToStudents(uploadPhotoForm,"Approved");
				    if(isMailSmsSent){
					    messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.final.year.student.photo.approved.successfully"));
						saveMessages(request, messages);
						resetFields(uploadPhotoForm);
						setDataToStudentPhotoDisplay(uploadPhotoForm, errors, request);
				    }else{
				    	errors.add("error", new ActionError("knowledgepro.final.year.student.photo.approved.sending.mail.sms"));
						addErrors(request, errors);
				    }
			}else{
					errors.add("error", new ActionError("knowledgepro.final.year.student.photo.approved.failure"));
					addErrors(request, errors);
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			uploadPhotoForm.setErrorMessage(msg);
			uploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FINAL_YEAR_STUDENT_PHOTO_DISPLAY);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public ActionForward rejectFinalYearStudentPhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentUploadPhotoForm uploadPhotoForm = (StudentUploadPhotoForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		setUserId(request, uploadPhotoForm);
		try{
			boolean isRejected=StudentUploadPhotoHandler.getInstance().rejectFinalYearStudentPhotos(uploadPhotoForm);
			if(isRejected){
				 boolean isMailSmsSent=StudentUploadPhotoHandler.getInstance().sendMailAndSmsToStudents(uploadPhotoForm,"Rejected");
				    if(isMailSmsSent){
				    	messages.add(CMSConstants.MESSAGES, new ActionMessage("knowledgepro.final.year.student.photo.rejected.successfully"));
						saveMessages(request, messages);
						resetFields(uploadPhotoForm);
						setDataToStudentPhotoDisplay(uploadPhotoForm, errors, request);	
				    }else{
				    	errors.add("error", new ActionError("knowledgepro.final.year.student.photo.rejected.sending.mail.sms"));
						addErrors(request, errors);
						resetFields(uploadPhotoForm);
				    }
			}else{
					errors.add("error", new ActionError("knowledgepro.final.year.student.photo.rejected.failure"));
					addErrors(request, errors);
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			uploadPhotoForm.setErrorMessage(msg);
			uploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward(CMSConstants.INIT_FINAL_YEAR_STUDENT_PHOTO_DISPLAY);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public ActionForward getRejectedStudentPhotos(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		StudentUploadPhotoForm uploadPhotoForm = (StudentUploadPhotoForm)form;
		setUserId(request, uploadPhotoForm);
		try{
			List<StudentUploadPhotoTO> rejectedPhotoList=StudentUploadPhotoHandler.getInstance().getRejectedStudentPhotos();
			if(rejectedPhotoList!=null && !rejectedPhotoList.isEmpty()){
				request.setAttribute("rejectedPhotoList", rejectedPhotoList);
			}else{
				request.setAttribute("msg", "There are no Rejected Photos Found");
			}
		}catch(Exception e){
			log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoAction", e);
			String msg = super.handleApplicationException(e);
			uploadPhotoForm.setErrorMessage(msg);
			uploadPhotoForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		return mapping.findForward("ajaxResponseForRejectedPhotos");
	}
	
	public class ByteArrayStreamInfo implements StreamInfo {
		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myDfBytes) {
			this.contentType = contentType;
			this.bytes = myDfBytes;
		}
	
		public String getContentType() {
			return contentType;
		}
	
		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
