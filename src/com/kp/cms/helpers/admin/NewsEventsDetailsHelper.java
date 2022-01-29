package com.kp.cms.helpers.admin;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.admin.NewsEventsEntryAction;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.EmployeeStreamBO;
import com.kp.cms.bo.admin.MobNewsEventsCategory;
import com.kp.cms.bo.admin.NewsEventsContactDetails;
import com.kp.cms.bo.admin.NewsEventsDetails;
import com.kp.cms.bo.admin.NewsEventsParticipants;
import com.kp.cms.bo.admin.NewsEventsPhoto;
import com.kp.cms.bo.admin.NewsEventsResourse;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.NewsEventsEntryForm;
import com.kp.cms.to.admin.NewEventsResourseTO;
import com.kp.cms.to.admin.NewsEventsContactDetailsTO;
import com.kp.cms.to.admin.NewsEventsDetailsTO;
import com.kp.cms.to.admin.NewsEventsParticipantTO;
import com.kp.cms.to.admin.NewsEventsPhotosTO;
import com.kp.cms.transactions.admin.INewsEventsDetailsTransaction;
import com.kp.cms.transactionsimpl.admin.NewsEventsDetailsTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.ImageFilter;

public class NewsEventsDetailsHelper {
	public static volatile NewsEventsDetailsHelper mobNewsEventsDetailsHelper = null;
	INewsEventsDetailsTransaction txn=NewsEventsDetailsTransactionImpl.getInstance();
	
	public static NewsEventsDetailsHelper getInstance() {
		if (mobNewsEventsDetailsHelper == null) {
			mobNewsEventsDetailsHelper = new NewsEventsDetailsHelper();
			return mobNewsEventsDetailsHelper;
		}
		return mobNewsEventsDetailsHelper;
	}
	//News and events Entry Screen
	public NewsEventsDetails setDataToBo(NewsEventsEntryForm newsEventsForm,HttpServletRequest request) throws Exception
	{  
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		NewsEventsDetails mobNewsEventsDetails=new NewsEventsDetails();
		mobNewsEventsDetails=setDataToBO(newsEventsForm,mobNewsEventsDetails);
		if(newsEventsForm.getPreIsApproved()!=null && !newsEventsForm.getPreIsApproved().isEmpty() && newsEventsForm.getPreIsApproved().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setPreIsApproved(true);
			if(newsEventsForm.getPreApprovalDate()!=null && !newsEventsForm.getPreApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPreApprovalDate(CommonUtil.ConvertStringToDate(newsEventsForm.getPreApprovalDate()));
			}
			if(newsEventsForm.getPreApprovalRemarks()!=null && !newsEventsForm.getPreApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPreApprovalRemarks(newsEventsForm.getPreApprovalRemarks());
			}
		}else{
			mobNewsEventsDetails.setPreIsApproved(false);
		}
		if(newsEventsForm.getPostIsApproved()!=null && !newsEventsForm.getPostIsApproved().isEmpty() && newsEventsForm.getPostIsApproved().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setPostIsApproved(true);
			if(newsEventsForm.getPostApprovalDate()!=null && !newsEventsForm.getPostApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPostApprovalDate(CommonUtil.ConvertStringToDate(newsEventsForm.getPostApprovalDate()));
			}
			if(newsEventsForm.getPostApprovalRemarks()!=null && !newsEventsForm.getPostApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPostApprovalRemarks(newsEventsForm.getPostApprovalRemarks());
			}
		}else{
			mobNewsEventsDetails.setPostIsApproved(false);
		}
		
		if (newsEventsForm.getIconTmageFile() != null && newsEventsForm.getIconTmageFile().getFileSize()>0) {
			addIconToFileSystem(newsEventsForm, request,mobNewsEventsDetails);
		}
		if(newsEventsForm.getIsInvitationMailRequired().equalsIgnoreCase("Yes"))
		{
			mobNewsEventsDetails.setIsEventInvitationMail(true);
			if (newsEventsForm.getInvitationMail() != null && newsEventsForm.getInvitationMail().getFileSize()>0) {
				addInvitationToFileSystem(newsEventsForm, request,mobNewsEventsDetails);
			}
		}else
		{
			mobNewsEventsDetails.setIsEventInvitationMail(false);
		}
		if(newsEventsForm.getIsRegistrationRequired().equalsIgnoreCase("Yes"))
		{
			mobNewsEventsDetails.setIsRegistrationRequired(true);
			if (newsEventsForm.getRegistrationForm() != null && newsEventsForm.getRegistrationForm().getFileSize()>0) {
				addRegistrationToFileSystem(newsEventsForm, request,mobNewsEventsDetails);
			}
		}
		else
		 {
			mobNewsEventsDetails.setIsRegistrationRequired(false);
		 }
		if(newsEventsForm.getIsLiveTelecast().equalsIgnoreCase("Yes"))
			{
				mobNewsEventsDetails.setIsLiveTelecast(true);
			}
		else
			{
				mobNewsEventsDetails.setIsLiveTelecast(false);
			}
	

		Set<NewsEventsResourse> newsResourse = getResourseObjects(newsEventsForm); 
		if(newsResourse!=null && !newsResourse.isEmpty()){
			mobNewsEventsDetails.setNewsEventsResourse(newsResourse);
		}
		Set<NewsEventsParticipants> newsParticipants= getParticipantsObjects(newsEventsForm);  
		if(newsParticipants!=null && !newsParticipants.isEmpty()){
			mobNewsEventsDetails.setNewsEventsParticipants(newsParticipants);
		}
		Set<NewsEventsContactDetails> Contact = getContactObjects(newsEventsForm); 
		if(Contact!=null && !Contact.isEmpty()){
			mobNewsEventsDetails.setNewsEventsContactDetails(Contact);
		}
		
		// code added by nagarjuna 
		//start
		if(newsEventsForm.getPhotosTO() != null && !newsEventsForm.getPhotosTO().isEmpty()){
			Iterator<NewsEventsPhotosTO> iterator = newsEventsForm.getPhotosTO().iterator();
			Set<NewsEventsPhoto> newsEventsPhotos = new HashSet<NewsEventsPhoto>();
			while (iterator.hasNext()) {
				NewsEventsPhotosTO newsEventsPhotosTO = (NewsEventsPhotosTO) iterator.next();
				if(newsEventsPhotosTO.getPhotoFile() != null && newsEventsPhotosTO.getPhotoFile().getFileSize() >0){
					FormFile file = newsEventsPhotosTO.getPhotoFile();
					byte[] fileData = file.getFileData();
					String contentType=file.getContentType();
					String fileName=file.getFileName();
		           if(imageFilter.accept(fileName))
		           {
		        	   
		        	  Long date=new Date().getTime();
		        	  String dateString=String.valueOf(date);
			    	String newFileName=fileName.replaceAll(fileName, "newsTitle"+dateString+".jpeg");
			    	String filePath=request.getRealPath("");
			    	filePath = filePath + "//TempFiles//";
					File file1 = new File(filePath+newFileName);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				    OutputStream out = new FileOutputStream(file1);
				    byte buffer[] = new byte[1024];
				    int len;
				    while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
				    }
				   out.close();
				   inputStream.close();
				  
				   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadImage.path");
				   destinationPath=destinationPath+"/"+newFileName;
				  //compress Image Start
				   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
			        //showImage("Original Image", i);
			        // Show results with different compression ratio.
			        //compressAndShow(i, 0.5f);
				   float quality=0.2f;
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
			       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        //showImage("Compressed to " + quality + ": " + size + " bytes", out);
			        // Uncomment code below to save the compressed files.
			     //compress Image End
			        File file3 = new File(filePath+"compressed."+newFileName);
			        FileImageOutputStream output = new FileImageOutputStream(file3);
			        writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
				   File file4=new File(destinationPath);
				   FileInputStream fileInputStream=new FileInputStream(file3);
				   byte[] buf = new byte[(int) file3.length()] ;
				   OutputStream outputStream = new FileOutputStream(file4);
					for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
						outputStream.write(buf, 0, readNum); //no doubt here is 0
					}
					 NewsEventsPhoto bo = new NewsEventsPhoto();
						bo.setCreatedBy(newsEventsForm.getUserId());
						bo.setCreatedDate(new Date());
						bo.setIsActive(true);
						bo.setLastModifiedDate(new Date());
						bo.setModifiedBy(newsEventsForm.getUserId());
						bo.setPhotoName(newFileName);
						newsEventsPhotos.add(bo);
				   }
				}else if (newsEventsPhotosTO.getPhotoName() != null && !newsEventsPhotosTO.getPhotoName().trim().isEmpty() ){
					NewsEventsPhoto bo = new NewsEventsPhoto();
					bo.setCreatedBy(newsEventsForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setIsActive(true);
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(newsEventsForm.getUserId());
					bo.setPhotoName(newsEventsPhotosTO.getPhotoName());
					newsEventsPhotos.add(bo);
				}
			}
			mobNewsEventsDetails.setNewsEventsPhotos(newsEventsPhotos);
		}
         
		// end 
		mobNewsEventsDetails.setIsAdminApprovePost(false);
		mobNewsEventsDetails.setIsAdminApprovePre(false);
		mobNewsEventsDetails.setIsPostDeptEntry(false);
		mobNewsEventsDetails.setPostApproveStatus("Not Published");
		mobNewsEventsDetails.setPreApproveStatus("Not Published");
		mobNewsEventsDetails.setIsActive(true);
		mobNewsEventsDetails.setCreatedDate(new Date());
		mobNewsEventsDetails.setCreatedBy(newsEventsForm.getUserId());
		mobNewsEventsDetails.setModifiedBy(newsEventsForm.getUserId());
		mobNewsEventsDetails.setLastModifiedDate(new Date());
		
		return mobNewsEventsDetails;
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
	private Set<NewsEventsResourse> getResourseObjects(NewsEventsEntryForm newsEventsForm) {
		Set<NewsEventsResourse> resourse = new HashSet<NewsEventsResourse>();
		if (newsEventsForm.getResourseTO() != null && !newsEventsForm.getResourseTO().isEmpty()) {
			Iterator<NewEventsResourseTO> itr = newsEventsForm.getResourseTO().iterator();
			while (itr.hasNext()) {
				NewEventsResourseTO to = (NewEventsResourseTO) itr.next();
				NewsEventsResourse res = new NewsEventsResourse();
				if (to.getResourseName() != null && !to.getResourseName().isEmpty()
						|| to.getEmail() != null && !to.getEmail().isEmpty() 
						|| to.getContactNo()!=null && !to.getContactNo().isEmpty()
						|| to.getOtherInfo()!=null && !to.getOtherInfo().isEmpty()) {
					if (to.getId() > 0) {
						res.setId(to.getId());
					}
					res.setCreatedBy(newsEventsForm.getUserId());
					res.setCreatedDate(new Date());
					res.setModifiedBy(newsEventsForm.getUserId());
					res.setLastModifiedDate(new Date());
					res.setIsActive(true);
					res.setResourseName(to.getResourseName());
					res.setEmail(to.getEmail());
					res.setContactNo(to.getContactNo());
					res.setOtherInfo(to.getOtherInfo());
					resourse.add(res);
				}
			}
		}
	return resourse;
	}
	
	private Set<NewsEventsContactDetails> getContactObjects(NewsEventsEntryForm newsEventsForm) {
		Set<NewsEventsContactDetails> ctc = new HashSet<NewsEventsContactDetails>();
		if (newsEventsForm.getContactTO() != null && !newsEventsForm.getContactTO().isEmpty()) {
			Iterator<NewsEventsContactDetailsTO> itr = newsEventsForm.getContactTO().iterator();
			while (itr.hasNext()) {
				NewsEventsContactDetailsTO to = (NewsEventsContactDetailsTO) itr.next();
				NewsEventsContactDetails res = new NewsEventsContactDetails();
				if (to.getName() != null && !to.getName().isEmpty()
						|| to.getEmail() != null && !to.getEmail().isEmpty() 
						|| to.getContactNo()!=null && !to.getContactNo().isEmpty()
						|| to.getRemarks()!=null && !to.getRemarks().isEmpty()) {
					if (to.getId() > 0) {
						res.setId(to.getId());
					}
					res.setCreatedBy(newsEventsForm.getUserId());
					res.setCreatedDate(new Date());
					res.setModifiedBy(newsEventsForm.getUserId());
					res.setLastModifiedDate(new Date());
					res.setIsActive(true);
					res.setName(to.getName());
					res.setEmail(to.getEmail());
					res.setContactNo(to.getContactNo());
					res.setRemarks(to.getRemarks());
					ctc.add(res);
				}
			}
		}
	return ctc;
	}
	
	
	
	//Approval Code---------------------
	
	public void convertBoToForm(NewsEventsDetails newEventDetail, NewsEventsEntryForm newsEventsForm) throws Exception {
		if (newEventDetail != null) {
			if(newEventDetail.getId()>0){
				newsEventsForm.setSelectedNewsEventsId(String.valueOf(newEventDetail.getId()));
			}
		if(newEventDetail.getCreatedBy()!=null && !newEventDetail.getCreatedBy().isEmpty()){
			Employee emp=txn.getEmployeeIdFromUserId(newsEventsForm);
			if(emp!=null){
		if(newsEventsForm.getScreen().equalsIgnoreCase("Pre")){
			 newsEventsForm.setPreApproverId(String.valueOf(emp.getId()));
		}
		if(newsEventsForm.getScreen().equalsIgnoreCase("Post")){
			 newsEventsForm.setPostApproverId(String.valueOf(emp.getId()));
		}
			 newsEventsForm.setUserEmailId(emp.getWorkEmail());
			 newsEventsForm.setUserName(emp.getFirstName());
			}
		}
			
		if (newEventDetail.getIsEventInvitationMail() != null
				&& newEventDetail.getIsEventInvitationMail().equals(true)) {
			newsEventsForm.setIsInvitationMailRequired("Yes");
		}else
		{
			newsEventsForm.setIsInvitationMailRequired("No");
		}
		if (newEventDetail.getIsLiveTelecast() != null
				&& newEventDetail.getIsLiveTelecast().equals(true)) {
			newsEventsForm.setIsLiveTelecast("Yes");
		}
		else
		{
			newsEventsForm.setIsLiveTelecast("No");
		}
		if (newEventDetail.getIsRegistrationRequired() != null
				&& newEventDetail.getIsRegistrationRequired().equals(true)) {
			newsEventsForm.setIsRegistrationRequired("Yes");
		}
		else
		{
			newsEventsForm.setIsRegistrationRequired("No");
		}
		if(newEventDetail.getNewOrEvents()!=null && !newEventDetail.getNewOrEvents().isEmpty()){
				newsEventsForm.setNewsOrEvents(newEventDetail.getNewOrEvents());
		}	
			
		
	
		if(newsEventsForm.getScreen().equalsIgnoreCase("Post")){
			
			if (newEventDetail.getPreIsApproved() != null
					&& newEventDetail.getPreIsApproved().equals(true)) {
				newsEventsForm.setPreIsApproved("true");
				if(newEventDetail.getPreApprovalDate()!=null){
					newsEventsForm.setPreApprovalDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getPreApprovalDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
				}
				if(newEventDetail.getPreApprovalRemarks()!=null && !newEventDetail.getPreApprovalRemarks().isEmpty()){
					newsEventsForm.setPreApprovalRemarks(newEventDetail.getPreApprovalRemarks());
				}
				if(newEventDetail.getPreApproverId()!=null && newEventDetail.getPreApproverId().getId()>0){
					newsEventsForm.setPreApproverId(String.valueOf(newEventDetail.getPreApproverId().getId()));
				}
			}else
			{
				newsEventsForm.setPreIsApproved("false");
			}
			
		}
		if (newEventDetail.getAcademicYear()!= null
				&& !newEventDetail.getAcademicYear()
						.isEmpty()) {
			newsEventsForm.setAcademicYear(newEventDetail.getAcademicYear());
		}

		if (newEventDetail.getCategoryId() != null
				&& newEventDetail.getCategoryId().getId() > 0) {
			newsEventsForm.setCategoryId(String.valueOf(newEventDetail
					.getCategoryId().getId()));
		}
		if (newEventDetail.getCourseId() != null
				&& newEventDetail.getCourseId().getId() > 0) {
			newsEventsForm.setCourseId(String.valueOf(newEventDetail
					.getCourseId().getId()));
		}
		if (newEventDetail.getDeptId() != null
				&& newEventDetail.getDeptId().getId() > 0) {
			newsEventsForm.setDepartmentId(String.valueOf(newEventDetail
					.getDeptId().getId()));
		}
		if (newEventDetail.getSplCentre() != null
				&& newEventDetail.getSplCentre().getId() > 0) {
			newsEventsForm.setSplCenterId(String.valueOf(newEventDetail
					.getSplCentre().getId()));
		}
		if (newEventDetail.getStreamId() != null
				&& newEventDetail.getStreamId().getId() > 0) {
			newsEventsForm.setStreamId(String.valueOf(newEventDetail.getStreamId().getId()));
		}
		if (newEventDetail.getDateFrom() != null) {
			newsEventsForm.setDateFrom(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDateFrom().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if (newEventDetail.getDateTo() != null) {
			newsEventsForm.setDateTo(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDateTo().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if (newEventDetail.getDisplayFromDate() != null) {
			newsEventsForm.setDisplayFromDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDisplayFromDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if (newEventDetail.getDisplayToDate() != null) {
			newsEventsForm.setDisplayToDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDisplayToDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if (newEventDetail.getEventDescription() != null && !newEventDetail.getEventDescription().isEmpty()) {
			newsEventsForm.setEventDescription(newEventDetail.getEventDescription());
		}
		if (newEventDetail.getEventTitle()!= null && !newEventDetail.getEventTitle().isEmpty()) {
			newsEventsForm.setEventTitle(newEventDetail.getEventTitle());
		}
		if (newEventDetail.getEventWebPosition()!= null && !newEventDetail.getEventWebPosition().isEmpty()) {
			newsEventsForm.setEventWebPosition(newEventDetail.getEventWebPosition());
		}
		if (newEventDetail.getNewsWebPosition()!= null && !newEventDetail.getNewsWebPosition().isEmpty()) {
			newsEventsForm.setNewsWebPosition(newEventDetail.getNewsWebPosition());
		}
		if (newEventDetail.getOrganizedBy()!= null && !newEventDetail.getOrganizedBy().isEmpty()) {
			newsEventsForm.setOrganizedBy(newEventDetail.getOrganizedBy());
		}
		if(newEventDetail.getDeptId()!=null && newEventDetail.getDeptId().getId()>0){
			newsEventsForm.setDepartmentId(String.valueOf(newEventDetail.getDeptId().getId()));
		}
		if(newEventDetail.getCourseId()!=null && newEventDetail.getCourseId().getId()>0){
			newsEventsForm.setCourseId(String.valueOf(newEventDetail.getCourseId().getId()));
		}
		if(newEventDetail.getStreamId()!=null && newEventDetail.getStreamId().getId()>0){
			newsEventsForm.setStreamId(String.valueOf(newEventDetail.getStreamId().getId()));
		}
		if(newEventDetail.getSplCentre()!=null && newEventDetail.getSplCentre().getId()>0){
			newsEventsForm.setSplCenterId(String.valueOf(newEventDetail.getSplCentre().getId()));
		}
		if (newEventDetail.getParticipants()!= null && !newEventDetail.getParticipants().isEmpty()) {
			newsEventsForm.setParticipants(newEventDetail.getParticipants());
		}
		if(newEventDetail.getViewFor()!=null && !newEventDetail.getViewFor().isEmpty()){
			String[] ids = newEventDetail.getViewFor().split(",");
			newsEventsForm.setSelectedviewFor(ids);
		}
		if(newEventDetail.getSummary()!=null && !newEventDetail.getSummary().isEmpty()){
			newsEventsForm.setSummary(newEventDetail.getSummary());
		}
		
		///all Images.........
		if(newEventDetail.getEventReport()!= null && !newEventDetail.getEventReport().isEmpty()) {
			newsEventsForm.setReportName(newEventDetail.getEventReport());
			if(newEventDetail.getEventReport().endsWith("jpeg")){
				newsEventsForm.setReportIsImage("true");
				}else{newsEventsForm.setReportIsImage("false");}
			}
		if(newEventDetail.getIconImage()!=null && !newEventDetail.getIconImage().isEmpty()){
			newsEventsForm.setIconName(newEventDetail.getIconImage());
		}
		if (newEventDetail.getInvitationMail()!= null && !newEventDetail.getInvitationMail().isEmpty()) {
			
			newsEventsForm.setInvitationName(newEventDetail.getInvitationMail());
			if(newEventDetail.getInvitationMail().endsWith("jpeg")){
				newsEventsForm.setInvitationIsImage("true");
				}else{newsEventsForm.setInvitationIsImage("false");}
		}
		if (newEventDetail.getMaterialsPublished()!= null && !newEventDetail.getMaterialsPublished().isEmpty()) {
			newsEventsForm.setMaterialName(newEventDetail.getMaterialsPublished());
			if(newEventDetail.getMaterialsPublished().endsWith("jpeg")){
				newsEventsForm.setMaterialIsImage("true");
				}else{newsEventsForm.setMaterialIsImage("false");}
		}
		if (newEventDetail.getRegistrationForm()!= null && !newEventDetail.getRegistrationForm().isEmpty()) {
			newsEventsForm.setRegFormName(newEventDetail.getRegistrationForm());
			if(newEventDetail.getRegistrationForm().endsWith("jpeg")){
				newsEventsForm.setRegIsImage("true");
				}else{newsEventsForm.setRegIsImage("false");}
		}
		////images code Ends
		
		if(newsEventsForm.getScreen()!=null && !newsEventsForm.getScreen().isEmpty()){
			if(newsEventsForm.getScreen().equalsIgnoreCase("Admin")){
				if(newEventDetail.getIsAdminApprovePre()!=null && newEventDetail.getIsAdminApprovePre().equals(true)){
					if(newEventDetail.getPreApproveStatus()!=null && !newEventDetail.getPreApproveStatus().isEmpty())
					{
						newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());
					}
					newsEventsForm.setIsAdminApprovePre("true");
				}else
				{
					newsEventsForm.setIsAdminApprovePre("false");
					newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());

				}
				if(newEventDetail.getIsAdminApprovePost()!=null && newEventDetail.getIsAdminApprovePost().equals(true)){
					if(newEventDetail.getPostApproveStatus()!=null && !newEventDetail.getPostApproveStatus().isEmpty())
					{
						newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());
					}
					newsEventsForm.setIsAdminApprovePost("true");
				}else
				{
					newsEventsForm.setIsAdminApprovePost("false");
					newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());

				}
			}else
			{

				if(newEventDetail.getIsAdminApprovePre()!=null && newEventDetail.getIsAdminApprovePre().equals(true) )
				{
					newsEventsForm.setIsAdminApprovePre("true");
				}else
					newsEventsForm.setIsAdminApprovePre("false");
				if(newEventDetail.getPreApproveStatus()!=null && !newEventDetail.getPreApproveStatus().isEmpty())
				{
					newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());
				}
				
				if(newEventDetail.getIsAdminApprovePost()!=null && newEventDetail.getIsAdminApprovePost().equals(true) )
				{
					newsEventsForm.setIsAdminApprovePost("true");
				}else
					newsEventsForm.setIsAdminApprovePost("false");
				if(newEventDetail.getPostApproveStatus()!=null && !newEventDetail.getPostApproveStatus().isEmpty())
				{
					newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());
				}
			}
		}else
		{
			if(newEventDetail.getIsAdminApprovePre()!=null && newEventDetail.getIsAdminApprovePre().equals(true) )
			{
				newsEventsForm.setIsAdminApprovePre("true");
			}else
				newsEventsForm.setIsAdminApprovePre("false");
			if(newEventDetail.getPreApproveStatus()!=null && !newEventDetail.getPreApproveStatus().isEmpty())
			{
				newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());
			}
			
			if(newEventDetail.getIsAdminApprovePost()!=null && newEventDetail.getIsAdminApprovePost().equals(true) )
			{
				newsEventsForm.setIsAdminApprovePost("true");
			}else
				newsEventsForm.setIsAdminApprovePost("false");
			if(newEventDetail.getPostApproveStatus()!=null && !newEventDetail.getPostApproveStatus().isEmpty())
			{
				newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());
			}
		}
		if(newEventDetail.getIsPostDeptEntry()!=null && newEventDetail.getIsPostDeptEntry().equals(true) )
		{
			newsEventsForm.setIsPostDeptEntry("true");
		}else{
			newsEventsForm.setIsPostDeptEntry("false");
		}
		if (newEventDetail.getNewsEventsResourse()!= null) {
			Set<NewsEventsResourse> res = newEventDetail.getNewsEventsResourse();
			List<NewEventsResourseTO> resTOs = new ArrayList<NewEventsResourseTO>();		
			if (res != null && !res.isEmpty()) {
				Iterator<NewsEventsResourse> iterator = res.iterator();
				
				while(iterator.hasNext()) {
					NewsEventsResourse resourse = iterator.next();
						NewEventsResourseTO resTO = new NewEventsResourseTO();
						if (resourse.getId() > 0) {
							resTO.setId(resourse.getId());
						}
						if (resourse.getContactNo()!=null && StringUtils.isNotEmpty(resourse.getContactNo())) {
							resTO.setContactNo(resourse.getContactNo());
						}

						if (resourse.getEmail()!=null && StringUtils.isNotEmpty(resourse.getEmail())) {
							resTO.setEmail(resourse.getEmail());
						}

						if (resourse.getOtherInfo()!= null && !resourse.getOtherInfo().isEmpty()) {
							resTO.setOtherInfo(resourse.getOtherInfo());
						}
						if (resourse.getResourseName()!= null && !resourse.getResourseName().isEmpty()) {
							resTO.setResourseName(resourse.getResourseName());
						}
						resTOs.add(resTO);
					}
				}
				newsEventsForm.setResourseListSize(String.valueOf(resTOs.size()));
				newsEventsForm.setOrgResListSize(String.valueOf(resTOs.size()));
				// add 10 records
				for (int i = resTOs.size(); i < 20; i++) {
					NewEventsResourseTO resTo1 = new NewEventsResourseTO();
					resTOs.add(resTo1);
				}
				newsEventsForm.setResourseTO(resTOs);
			}
		
		
		if (newEventDetail.getNewsEventsContactDetails()!= null) {
			Set<NewsEventsContactDetails> res = newEventDetail.getNewsEventsContactDetails();
			List<NewsEventsContactDetailsTO> resTOs = new ArrayList<NewsEventsContactDetailsTO>();		
			if (res != null && !res.isEmpty()) {
				Iterator<NewsEventsContactDetails> iterator = res.iterator();
				
				while(iterator.hasNext()) {
					NewsEventsContactDetails contact = iterator.next();
					NewsEventsContactDetailsTO resTO = new NewsEventsContactDetailsTO();
						if (contact.getId() > 0) {
							resTO.setId(contact.getId());
						}
						if (contact.getContactNo()!=null && StringUtils.isNotEmpty(contact.getContactNo())) {
							resTO.setContactNo(contact.getContactNo());
						}

						if (contact.getEmail()!=null && StringUtils.isNotEmpty(contact.getEmail())) {
							resTO.setEmail(contact.getEmail());
						}

						if (contact.getRemarks()!= null && !contact.getRemarks().isEmpty()) {
							resTO.setRemarks(contact.getRemarks());
						}
						if (contact.getName()!= null && !contact.getName().isEmpty()) {
							resTO.setName(contact.getName());
						}
						resTOs.add(resTO);
					}
				}
				newsEventsForm.setContactListSize(String.valueOf(resTOs.size()));
				newsEventsForm.setOrgContactListSize(String.valueOf(resTOs.size()));
				// add 10 records
				for (int i = resTOs.size(); i < 20; i++) {
					NewsEventsContactDetailsTO resTo1 = new NewsEventsContactDetailsTO();
					resTOs.add(resTo1);
				}
				newsEventsForm.setContactTO(resTOs);
			}
		
		
		if (newEventDetail.getNewsEventsParticipants()!= null) {
			Set<NewsEventsParticipants> res = newEventDetail.getNewsEventsParticipants();
			List<NewsEventsParticipantTO> resTOs = new ArrayList<NewsEventsParticipantTO>();		
			if (res != null && !res.isEmpty()) {
				Iterator<NewsEventsParticipants> iterator = res.iterator();
				
				while(iterator.hasNext()) {
					NewsEventsParticipants resourse = iterator.next();
						NewsEventsParticipantTO resTO = new NewsEventsParticipantTO();
						if (resourse.getId() > 0) {
							resTO.setId(resourse.getId());
						}
						if (resourse.getInstitutionName()!=null && StringUtils.isNotEmpty(resourse.getInstitutionName())) {
							resTO.setInstitutionName(resourse.getInstitutionName());
						}

						if (resourse.getNoOfPeople()>0) {
							resTO.setNoOfPeople(String.valueOf(resourse.getNoOfPeople()));
						}

						if (resourse.getRemarks()!= null && !resourse.getRemarks().isEmpty()) {
							resTO.setRemarks(resourse.getRemarks());
						}
						resTOs.add(resTO);
					}
				}
			
				newsEventsForm.setParticipantsListSize(String.valueOf(resTOs.size()));
				newsEventsForm.setOrgPartListSize(String.valueOf(resTOs.size()));
				// add 10 records
				for (int i = resTOs.size(); i < 20; i++) {
					NewsEventsParticipantTO partto = new NewsEventsParticipantTO();
					resTOs.add(partto);
				}
				newsEventsForm.setPartcipantsTO(resTOs);
			}
		if(newEventDetail.getNewsEventsPhotos() != null){
			Iterator<NewsEventsPhoto> iterator = newEventDetail.getNewsEventsPhotos().iterator();
			List<NewsEventsPhotosTO> photoTOs = new ArrayList<NewsEventsPhotosTO>();

			while (iterator.hasNext()) {
				NewsEventsPhoto photo1 = iterator.next();
				if (photo1 != null && photo1.getIsActive() != null && photo1.getIsActive()) {
					NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();

					if (photo1.getId() > 0) {
						phototo.setId(photo1.getId());
					}
					if (photo1.getPhotoName()!=null && (StringUtils.isNotEmpty(photo1.getPhotoName()))) {
						phototo.setPhotoName(photo1.getPhotoName());
					}
					if (photo1.getNewsEventsId()!= null && photo1.getNewsEventsId().getId()>0) {
						phototo.setNewsEventsId(String.valueOf(photo1.getNewsEventsId().getId()));
					}
					photoTOs.add(phototo);
				}
			}
			newsEventsForm.setPhotoListSize(String.valueOf(photoTOs.size()));
			newsEventsForm.setOrgphotoListSize(String.valueOf(photoTOs.size()));
			// add 10 records
			for (int i = photoTOs.size(); i < 10; i++) {
				NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();
				photoTOs.add(phototo);
			}
			newsEventsForm.setPhotosTO(photoTOs);
		}	
		}
	}
	public NewsEventsPhotosTO convertSearchPhotoTOtoBO(NewsEventsPhoto newsPhoto,NewsEventsEntryForm newsEventsForm) throws Exception {
		NewsEventsPhotosTO newsTos = new NewsEventsPhotosTO();
		newsTos.setId(newsPhoto.getId());
		newsTos.setPhotoName(newsPhoto.getPhotoName());
		// write the code to get the photo from file sytem.
		
			if(newsTos.getPhoto()!=null){
				newsEventsForm.setPhotoBytes(newsTos.getPhoto());
			}
		//newsTos.setPhoto()
		//code ends here.
		return newsTos;
		
	}
	
	public List<NewsEventsDetailsTO> convertNewsSearchTOtoBO(List<NewsEventsDetails> newslist, NewsEventsEntryForm newsForm) throws Exception {
		INewsEventsDetailsTransaction txn = NewsEventsDetailsTransactionImpl.getInstance();
		List<MobNewsEventsCategory> categoryList = txn.getCategoryList();
		List<NewsEventsDetailsTO> newsTos = new ArrayList<NewsEventsDetailsTO>();
		String name = "";
		if (newslist != null) {
			Iterator<NewsEventsDetails> stItr = newslist.iterator();
			while (stItr.hasNext()) {
				name = "";
				NewsEventsDetails news = (NewsEventsDetails) stItr.next();
				NewsEventsDetailsTO newsTo = new NewsEventsDetailsTO();
				if (news.getId() > 0) {
					newsTo.setId(news.getId());
				}
				if (news.getEventTitle() != null) {
					newsTo.setEventTitle(news.getEventTitle());
				}
				if (news.getOrganizedBy()!= null && !news.getOrganizedBy().isEmpty()) {
					newsTo.setOrganisedBy(news.getOrganizedBy());
				}
				if (news.getParticipants() != null && !news.getParticipants().isEmpty()) {
					newsTo.setParticipants(news.getParticipants().toUpperCase());
				}
				if(news.getDateFrom()!=null){
					newsTo.setDateFrom(CommonUtil.ConvertStringToDateFormat(news.getDateFrom().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
				}
				if(news.getDateTo()!=null){
					newsTo.setDateTo(CommonUtil.ConvertStringToDateFormat(news.getDateTo().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
				}
				if (news.getCategoryId() != null
						&& news.getCategoryId().getId() > 0) {
					int categId = news.getCategoryId().getId();
					String categName = null;
					if (categoryList != null) {
						Iterator<MobNewsEventsCategory> desItr = categoryList
								.iterator();
						while (desItr.hasNext()) {
							MobNewsEventsCategory des = (MobNewsEventsCategory) desItr.next();
							int desigId = des.getId();
							if (desigId == categId) {
								categName = des.getCategory();
							}
						}					}
					newsTo.setCategory(categName);
				}
				
				if(news.getOrganizedBy()!=null && ! news.getOrganizedBy().isEmpty()){
					newsTo.setOrgBy(news.getOrganizedBy());
				}
				if(news.getDeptId()!=null && news.getDeptId().getId()>0){
					newsTo.setDepartmentName(news.getDeptId().getName());
				}
				if(news.getCourseId()!=null && news.getCourseId().getId()>0){
					newsTo.setCourseName(news.getCourseId().getName());
				}
				if(news.getSplCentre()!=null && news.getSplCentre().getId()>0){
					newsTo.setSplCentreName(news.getSplCentre().getName());
				}
				if(news.getStreamId()!=null && news.getStreamId().getId()>0){
					newsTo.setStreamName(news.getStreamId().getName());
				}
				if(newsForm.getScreen()!=null && !newsForm.getScreen().isEmpty() && newsForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
					if(news.getCreatedBy().equalsIgnoreCase(newsForm.getUserId())){
						newsTos.add(newsTo);
					}
				}else
				{
					newsTos.add(newsTo);
				}
			}
		}
		return newsTos;
	}
	
public NewsEventsDetails convertNewsFormToBO(NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request) throws Exception 
	{
	NewsEventsDetails mobNewsEventsDetails=new NewsEventsDetails();
	if(newsEventsEntryForm.getSelectedNewsEventsId()!=null && !newsEventsEntryForm.getSelectedNewsEventsId().isEmpty()){
		mobNewsEventsDetails.setId(Integer.parseInt(newsEventsEntryForm.getSelectedNewsEventsId()));
	}
		final ImageFilter imageFilter = new ImageFilter();
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		if(newsEventsEntryForm.getNewsOrEvents()!=null && !newsEventsEntryForm.getNewsOrEvents().isEmpty()){
			mobNewsEventsDetails.setNewOrEvents(newsEventsEntryForm.getNewsOrEvents());
		}	
		
		if(newsEventsEntryForm.getEventTitle()!=null && !newsEventsEntryForm.getEventTitle().isEmpty())
		{
			mobNewsEventsDetails.setEventTitle(newsEventsEntryForm.getEventTitle());
		}
		if(newsEventsEntryForm.getEventDescription()!=null && !newsEventsEntryForm.getEventDescription().isEmpty())
		{
			mobNewsEventsDetails.setEventDescription(newsEventsEntryForm.getEventDescription());
		}
		if (newsEventsEntryForm.getPhotosTO() != null && !newsEventsEntryForm.getPhotosTO().isEmpty()) {
			Set<NewsEventsPhoto> images = new HashSet<NewsEventsPhoto>();
			Iterator<NewsEventsPhotosTO> itr = newsEventsEntryForm.getPhotosTO().iterator();
			while (itr.hasNext()) {
			NewsEventsPhotosTO to = (NewsEventsPhotosTO) itr.next();
			int count=1;
			if ((to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0)) {
				NewsEventsPhoto img = new NewsEventsPhoto();
				if (to.getId() > 0) {
					img.setId(to.getId());
				}
			if (to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0) {
				FormFile file = to.getPhotoFile();
				byte[] fileData = file.getFileData();
				String contentType=file.getContentType();
				String fileName=file.getFileName();
		       if(imageFilter.accept(fileName))
		       {
		    	   Long date=new Date().getTime();
		     	  String dateString=String.valueOf(date);
			    	String newFileName=fileName.replaceAll(fileName, "eventPhoto"+count+dateString+".jpeg");
			    	String filePath=request.getRealPath("");
			    	filePath = filePath + "//TempFiles//";
					File file1 = new File(filePath+newFileName);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				    OutputStream out = new FileOutputStream(file1);
				    byte buffer[] = new byte[1024];
				    int len;
				    while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
				    }
				   out.close();
				   inputStream.close();
				   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadImage.path");
				   destinationPath=destinationPath+"/"+newFileName;
				  //compress Image Start
				   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
			        //showImage("Original Image", i);
			        // Show results with different compression ratio.
			        //compressAndShow(i, 0.5f);
			        float quality=0.2f;
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
			       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        //showImage("Compressed to " + quality + ": " + size + " bytes", out);
			        // Uncomment code below to save the compressed files.
			     //compress Image End
			        File file3 = new File(filePath+"compressed."+newFileName);
			        FileImageOutputStream output = new FileImageOutputStream(file3);
			        writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
				   File file4=new File(destinationPath);
				   FileInputStream fileInputStream=new FileInputStream(file3);
				   byte[] buf = new byte[(int) file3.length()] ;
				   OutputStream outputStream = new FileOutputStream(file4);
					for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
						outputStream.write(buf, 0, readNum); //no doubt here is 0
					}
					img.setPhotoName(newFileName);
			   }
			}
			img.setCreatedBy(newsEventsEntryForm.getUserId());
			img.setCreatedDate(new Date());
			img.setModifiedBy(newsEventsEntryForm.getUserId());
			img.setLastModifiedDate(new Date());
			img.setIsActive(true);
			images.add(img);
			}else if(to.getPhotoName() != null && !to.getPhotoName().trim().isEmpty()){
				NewsEventsPhoto img = new NewsEventsPhoto();
				if (to.getId() > 0) {
					img.setId(to.getId());
				}
				img.setCreatedBy(newsEventsEntryForm.getUserId());
				img.setCreatedDate(new Date());
				img.setModifiedBy(newsEventsEntryForm.getUserId());
				img.setLastModifiedDate(new Date());
				img.setIsActive(true);
				img.setPhotoName(to.getPhotoName());
				images.add(img);
			}
			}
			mobNewsEventsDetails.setNewsEventsPhotos(images);
		}
		if (newsEventsEntryForm.getIconTmageFile() != null && newsEventsEntryForm.getIconTmageFile().getFileSize()>0) {
			addIconToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
		}
		else if(newsEventsEntryForm.getIconName()!=null && !newsEventsEntryForm.getIconName().isEmpty()){
			  mobNewsEventsDetails.setIconImage(newsEventsEntryForm.getIconName());
		  }
		
		if(newsEventsEntryForm.getIsInvitationMailRequired().equalsIgnoreCase("Yes"))
			{
			mobNewsEventsDetails.setIsEventInvitationMail(true);
			if (newsEventsEntryForm.getInvitationMail() != null && newsEventsEntryForm.getInvitationMail().getFileSize()>0) {
				addInvitationToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
			}else if(newsEventsEntryForm.getInvitationName()!=null && !newsEventsEntryForm.getInvitationName().isEmpty()){
				  mobNewsEventsDetails.setInvitationMail(newsEventsEntryForm.getInvitationName());
			  }
			}
		else
			{
				mobNewsEventsDetails.setIsEventInvitationMail(false);
			}
	if(newsEventsEntryForm.getIsRegistrationRequired().equalsIgnoreCase("Yes"))
			{
				mobNewsEventsDetails.setIsRegistrationRequired(true);
				if (newsEventsEntryForm.getRegistrationForm() != null && newsEventsEntryForm.getRegistrationForm().getFileSize()>0) {
					addRegistrationToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
			  }else if(newsEventsEntryForm.getRegFormName()!=null && !newsEventsEntryForm.getRegFormName().isEmpty()){
					  mobNewsEventsDetails.setRegistrationForm(newsEventsEntryForm.getRegFormName());
				  }
			}else
			{
				mobNewsEventsDetails.setIsRegistrationRequired(false);
			}
	if (newsEventsEntryForm.getEventReport() != null && newsEventsEntryForm.getEventReport().getFileSize()>0) {
		addReportToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
	}
	else if(newsEventsEntryForm.getReportName()!=null && !newsEventsEntryForm.getReportName().isEmpty()){
		  mobNewsEventsDetails.setEventReport(newsEventsEntryForm.getReportName());
	  }
		
	if (newsEventsEntryForm.getMaterialsPublished() != null && newsEventsEntryForm.getMaterialsPublished().getFileSize()>0) {
		addMaterialToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
	}
	else if(newsEventsEntryForm.getMaterialName()!=null && !newsEventsEntryForm.getMaterialName().isEmpty()){
		  mobNewsEventsDetails.setMaterialsPublished(newsEventsEntryForm.getMaterialName());
	  }
   
	if(newsEventsEntryForm.getIsLiveTelecast().equalsIgnoreCase("Yes"))
			{
				mobNewsEventsDetails.setIsLiveTelecast(true);
			}
			else
			{
				mobNewsEventsDetails.setIsLiveTelecast(false);
			}
	if(newsEventsEntryForm.getIsPostDeptEntry()!=null && newsEventsEntryForm.getIsPostDeptEntry().equalsIgnoreCase("true"))
	{
		mobNewsEventsDetails.setIsPostDeptEntry(true);
	}
	else
	{
		mobNewsEventsDetails.setIsPostDeptEntry(false);
	}
	if(newsEventsEntryForm.getDateFrom()!=null && !newsEventsEntryForm.getDateFrom().isEmpty()){
		mobNewsEventsDetails.setDateFrom(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDateFrom()));
	}
	if(newsEventsEntryForm.getDateTo()!=null && !newsEventsEntryForm.getDateTo().isEmpty()){
		mobNewsEventsDetails.setDateTo(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDateTo()));
	}
	if(newsEventsEntryForm.getCategoryId()!=null && !newsEventsEntryForm.getCategoryId().isEmpty()){
		MobNewsEventsCategory mob=new MobNewsEventsCategory();
		mob.setId(Integer.parseInt(newsEventsEntryForm.getCategoryId()));
		mobNewsEventsDetails.setCategoryId(mob);
	}
	if(newsEventsEntryForm.getAcademicYear()!=null && !newsEventsEntryForm.getAcademicYear().isEmpty()){
		mobNewsEventsDetails.setAcademicYear(newsEventsEntryForm.getAcademicYear());
	}
	if(newsEventsEntryForm.getDisplayFromDate()!=null && !newsEventsEntryForm.getDisplayFromDate().isEmpty()){
		mobNewsEventsDetails.setDisplayFromDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDisplayFromDate()));
	}
	if(newsEventsEntryForm.getDisplayToDate()!=null && !newsEventsEntryForm.getDisplayToDate().isEmpty()){
		mobNewsEventsDetails.setDisplayToDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDisplayToDate()));
	}
	if(newsEventsEntryForm.getParticipants()!=null && !newsEventsEntryForm.getParticipants().isEmpty()){
		mobNewsEventsDetails.setParticipants(newsEventsEntryForm.getParticipants());
	}
	if(newsEventsEntryForm.getOrganizedBy()!=null && !newsEventsEntryForm.getOrganizedBy().isEmpty()){
		mobNewsEventsDetails.setOrganizedBy(newsEventsEntryForm.getOrganizedBy());
	}
	if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Course")){
		if(newsEventsEntryForm.getCourseId()!=null && !newsEventsEntryForm.getCourseId().isEmpty()){
		Course cs=new Course();
		cs.setId(Integer.parseInt(newsEventsEntryForm.getCourseId()));
		mobNewsEventsDetails.setCourseId(cs);
		}
	}
	if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Department")){
		if(newsEventsEntryForm.getDepartmentId()!=null && !newsEventsEntryForm.getDepartmentId().isEmpty()){
		Department ds=new Department();
		ds.setId(Integer.parseInt(newsEventsEntryForm.getDepartmentId()));
		mobNewsEventsDetails.setDeptId(ds);
		}
	}
	if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Deanery")){
		if(newsEventsEntryForm.getStreamId()!=null && !newsEventsEntryForm.getStreamId().isEmpty()){
		EmployeeStreamBO ss=new EmployeeStreamBO();
		ss.setId(Integer.parseInt(newsEventsEntryForm.getStreamId()));
		mobNewsEventsDetails.setStreamId(ss);
		}
	}
	if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Special Centers")){
		if(newsEventsEntryForm.getSplCenterId()!=null && !newsEventsEntryForm.getSplCenterId().isEmpty()){
		Department cs=new Department();
		cs.setId(Integer.parseInt(newsEventsEntryForm.getSplCenterId()));
		mobNewsEventsDetails.setSplCentre(cs);
		}
	}

	
	if(newsEventsEntryForm.getNewsWebPosition()!=null && !newsEventsEntryForm.getNewsWebPosition().isEmpty()){
		mobNewsEventsDetails.setNewsWebPosition(newsEventsEntryForm.getNewsWebPosition());
	}
	if(newsEventsEntryForm.getEventWebPosition()!=null && !newsEventsEntryForm.getEventWebPosition().isEmpty()){
		mobNewsEventsDetails.setEventWebPosition(newsEventsEntryForm.getEventWebPosition());
	}
	if(newsEventsEntryForm.getViewFor()!=null && !newsEventsEntryForm.getViewFor().isEmpty()){
		mobNewsEventsDetails.setViewFor(newsEventsEntryForm.getViewFor());
	}
	
	if(newsEventsEntryForm.getSummary()!=null && !newsEventsEntryForm.getSummary().isEmpty()){
		mobNewsEventsDetails.setSummary(newsEventsEntryForm.getSummary());
	}
	/*if(newsEventsEntryForm.getMaterialsPublished()!=null && !newsEventsEntryForm.getMaterialsPublished().isEmpty()){
		mobNewsEventsDetails.setMaterialsPublished(newsEventsEntryForm.getMaterialsPublished());
	}*/
	
	if(newsEventsEntryForm.getPreIsApproved()!=null && !newsEventsEntryForm.getPreIsApproved().isEmpty()){
		if(newsEventsEntryForm.getPreIsApproved().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setPreIsApproved(true);
			
			if(newsEventsEntryForm.getPreApprovalDate()!=null && !newsEventsEntryForm.getPreApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPreApprovalDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getPreApprovalDate()));
			}
			else
			{
				mobNewsEventsDetails.setPreApprovalDate(new Date());
			}
			if(newsEventsEntryForm.getPreApprovalRemarks()!=null && !newsEventsEntryForm.getPreApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPreApprovalRemarks(newsEventsEntryForm.getPreApprovalRemarks());
			}
			if(newsEventsEntryForm.getPreApproverId()!=null && !newsEventsEntryForm.getPreApproverId().isEmpty()){
					Employee preApprover =new Employee();
					preApprover.setId(Integer.parseInt(newsEventsEntryForm.getPreApproverId()));
					mobNewsEventsDetails.setPreApproverId(preApprover);
			}
		}else{
			mobNewsEventsDetails.setPreIsApproved(false);	
		}
	}
	else{
		mobNewsEventsDetails.setPreIsApproved(false);	
	}
	if(newsEventsEntryForm.getPostIsApproved()!=null && !newsEventsEntryForm.getPostIsApproved().isEmpty()){
		if(newsEventsEntryForm.getPostIsApproved().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setPostIsApproved(true);
			if(newsEventsEntryForm.getPostApprovalRemarks()!=null && !newsEventsEntryForm.getPostApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPostApprovalRemarks(newsEventsEntryForm.getPostApprovalRemarks());
			}
			if(newsEventsEntryForm.getPostApprovalDate()!=null && !newsEventsEntryForm.getPostApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPostApprovalDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getPostApprovalDate()));
			}
			else
			{
				mobNewsEventsDetails.setPostApprovalDate(new Date());
			}
			if(newsEventsEntryForm.getPostApproverId()!=null && !newsEventsEntryForm.getPostApproverId().isEmpty()){
				Employee postapprover =new Employee();
				postapprover.setId(Integer.parseInt(newsEventsEntryForm.getPostApproverId()));
				mobNewsEventsDetails.setPostApproverId(postapprover);
			}
		}else{
			mobNewsEventsDetails.setPostIsApproved(false);	
		}
		
	}
	else{
		mobNewsEventsDetails.setPostIsApproved(false);	
	}
	if(newsEventsEntryForm.getIsAdminApprovePre()!=null && !newsEventsEntryForm.getIsAdminApprovePre().isEmpty() && newsEventsEntryForm.getIsAdminApprovePre().equalsIgnoreCase("true")){
       mobNewsEventsDetails.setIsAdminApprovePre(true);	
	}else
	{
		mobNewsEventsDetails.setIsAdminApprovePre(false);
	}
	if(newsEventsEntryForm.getIsAdminApprovePost()!=null && !newsEventsEntryForm.getIsAdminApprovePost().isEmpty() && newsEventsEntryForm.getIsAdminApprovePost().equalsIgnoreCase("true")){
	       mobNewsEventsDetails.setIsAdminApprovePost(true);	
		}else
		{
			mobNewsEventsDetails.setIsAdminApprovePost(false);
		}
	if(newsEventsEntryForm.getPreAdminStatus()!=null && !newsEventsEntryForm.getPreAdminStatus().isEmpty()){
		mobNewsEventsDetails.setPreApproveStatus(newsEventsEntryForm.getPreAdminStatus());
	}else
	{
		mobNewsEventsDetails.setPreApproveStatus("Not Published");
	}
		
	if(newsEventsEntryForm.getPostAdminStatus()!=null && !newsEventsEntryForm.getPostAdminStatus().isEmpty()){
			mobNewsEventsDetails.setPostApproveStatus(newsEventsEntryForm.getPostAdminStatus());
	}else
	{
			mobNewsEventsDetails.setPostApproveStatus("Not Published");
		}
	if(newsEventsEntryForm.getIsPostDeptEntry()!=null && newsEventsEntryForm.getIsPostDeptEntry().equalsIgnoreCase("true")){
		mobNewsEventsDetails.setIsPostDeptEntry(true);
	}else
	{
		mobNewsEventsDetails.setIsPostDeptEntry(false);
	}
	Set<NewsEventsResourse> newsResourse = getResourseObjects(newsEventsEntryForm); 
	if(newsResourse!=null && !newsResourse.isEmpty()){
		mobNewsEventsDetails.setNewsEventsResourse(newsResourse);
	}
	
	Set<NewsEventsContactDetails> Contact = getContactObjects(newsEventsEntryForm); 
	if(Contact!=null && !Contact.isEmpty()){
		mobNewsEventsDetails.setNewsEventsContactDetails(Contact);
	}
	Set<NewsEventsParticipants> newsParticipants= getParticipantsObjects(newsEventsEntryForm);  
	if(newsParticipants!=null && !newsParticipants.isEmpty()){
		mobNewsEventsDetails.setNewsEventsParticipants(newsParticipants);
	}
	
	mobNewsEventsDetails.setIsActive(true);
	mobNewsEventsDetails.setCreatedBy(newsEventsEntryForm.getUserId());
	mobNewsEventsDetails.setCreatedDate(new Date());
	mobNewsEventsDetails.setModifiedBy(newsEventsEntryForm.getUserId());
	mobNewsEventsDetails.setLastModifiedDate(new Date());
	return mobNewsEventsDetails;
  }

public NewsEventsEntryForm convertNewsBOtoToEdit(NewsEventsEntryForm newsEventsEntryForm, NewsEventsDetails mobNewsEventsDetails) {
	if(mobNewsEventsDetails.getId()>0){
		newsEventsEntryForm.setSelectedNewsEventsId(String.valueOf(mobNewsEventsDetails.getId()));
	}
	if(mobNewsEventsDetails.getNewOrEvents()!=null && !mobNewsEventsDetails.getNewOrEvents().isEmpty()){
		newsEventsEntryForm.setNewsOrEvents(mobNewsEventsDetails.getNewOrEvents());
	}
	if(mobNewsEventsDetails.getCategoryId()!=null && mobNewsEventsDetails.getCategoryId().getId()>0){
		newsEventsEntryForm.setCategoryName(mobNewsEventsDetails.getCategoryId().getCategory());
		newsEventsEntryForm.setCategoryId(String.valueOf(mobNewsEventsDetails.getCategoryId().getId()));
	}
	if(mobNewsEventsDetails.getEventDescription()!=null && ! mobNewsEventsDetails.getEventDescription().isEmpty()){
		newsEventsEntryForm.setEventDescription(mobNewsEventsDetails.getEventDescription());
	}
	if(mobNewsEventsDetails.getEventTitle()!=null && !mobNewsEventsDetails.getEventTitle().isEmpty()){
		newsEventsEntryForm.setEventTitle(mobNewsEventsDetails.getEventTitle());
	}
	if(mobNewsEventsDetails.getDateFrom()!=null){
		newsEventsEntryForm.setDateFrom(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getDateFrom().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if(mobNewsEventsDetails.getDateTo()!=null){
		newsEventsEntryForm.setDateTo(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getDateTo().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if(mobNewsEventsDetails.getDisplayFromDate()!=null){
		newsEventsEntryForm.setDisplayFromDate(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getDisplayFromDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if(mobNewsEventsDetails.getDisplayToDate()!=null){
		newsEventsEntryForm.setDisplayToDate(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getDisplayToDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if(mobNewsEventsDetails.getAcademicYear()!=null && !mobNewsEventsDetails.getAcademicYear().isEmpty()){
		newsEventsEntryForm.setAcademicYear(mobNewsEventsDetails.getAcademicYear());
	}
	if(mobNewsEventsDetails.getParticipants()!=null && ! mobNewsEventsDetails.getParticipants().isEmpty()){
		newsEventsEntryForm.setParticipants(mobNewsEventsDetails.getParticipants());
	}
	if(mobNewsEventsDetails.getSummary()!=null && ! mobNewsEventsDetails.getSummary().isEmpty()){
		newsEventsEntryForm.setSummary(mobNewsEventsDetails.getSummary());
	}
	if(mobNewsEventsDetails.getNewsWebPosition()!=null && ! mobNewsEventsDetails.getNewsWebPosition().isEmpty()){
		newsEventsEntryForm.setNewsWebPosition(mobNewsEventsDetails.getNewsWebPosition());
	}
	if(mobNewsEventsDetails.getEventWebPosition()!=null && ! mobNewsEventsDetails.getEventWebPosition().isEmpty()){
		newsEventsEntryForm.setEventWebPosition(mobNewsEventsDetails.getEventWebPosition());
	}
	
	/*if(mobNewsEventsDetails.getViewFor()!=null && ! mobNewsEventsDetails.getViewFor().isEmpty()){
		newsEventsEntryForm.setViewFor(mobNewsEventsDetails.getViewFor());
	}*/
		
	if(newsEventsEntryForm.getScreen().equalsIgnoreCase("PostEventDeptEntry")){
		if(mobNewsEventsDetails.getViewFor()!=null && !mobNewsEventsDetails.getViewFor().isEmpty()){
			newsEventsEntryForm.setViewFor(mobNewsEventsDetails.getViewFor());
		}
		newsEventsEntryForm.setIsPostDeptEntry("true");
	}
	else{
		if(mobNewsEventsDetails.getViewFor()!=null && !mobNewsEventsDetails.getViewFor().isEmpty()){
			String[] ids = mobNewsEventsDetails.getViewFor().split(",");
			newsEventsEntryForm.setSelectedviewFor(ids);
		}
		newsEventsEntryForm.setIsPostDeptEntry("false");
		
	}
	if(mobNewsEventsDetails.getOrganizedBy()!=null && ! mobNewsEventsDetails.getOrganizedBy().isEmpty()){
		newsEventsEntryForm.setOrganizedBy(mobNewsEventsDetails.getOrganizedBy());
	}
	if(mobNewsEventsDetails.getDeptId()!=null && mobNewsEventsDetails.getDeptId().getId()>0){
		newsEventsEntryForm.setDepartmentId(String.valueOf(mobNewsEventsDetails.getDeptId().getId()));
		newsEventsEntryForm.setDepartmentName(mobNewsEventsDetails.getDeptId().getName());
	}
	if(mobNewsEventsDetails.getCourseId()!=null && mobNewsEventsDetails.getCourseId().getId()>0){
		newsEventsEntryForm.setCourseId(String.valueOf(mobNewsEventsDetails.getCourseId().getId()));
		newsEventsEntryForm.setCourseName(mobNewsEventsDetails.getCourseId().getName());
	}
	if(mobNewsEventsDetails.getSplCentre()!=null && mobNewsEventsDetails.getSplCentre().getId()>0){
		newsEventsEntryForm.setSplCenterId(String.valueOf(mobNewsEventsDetails.getSplCentre().getId()));
		newsEventsEntryForm.setSplCentreName(mobNewsEventsDetails.getSplCentre().getName());
	}
	if(mobNewsEventsDetails.getStreamId()!=null && mobNewsEventsDetails.getStreamId().getId()>0){
		newsEventsEntryForm.setStreamId(String.valueOf(mobNewsEventsDetails.getStreamId().getId()));
		newsEventsEntryForm.setStreamName(mobNewsEventsDetails.getStreamId().getName());
	}
	if(mobNewsEventsDetails.getMaterialsPublished()!=null && ! mobNewsEventsDetails.getMaterialsPublished().isEmpty()){
		newsEventsEntryForm.setMaterialName(mobNewsEventsDetails.getMaterialsPublished());
		if(mobNewsEventsDetails.getMaterialsPublished().endsWith("jpeg")){
			newsEventsEntryForm.setMaterialIsImage("true");
			}else{newsEventsEntryForm.setMaterialIsImage("false");}
	}
	if(mobNewsEventsDetails.getEventReport()!=null && ! mobNewsEventsDetails.getEventReport().isEmpty()){
		newsEventsEntryForm.setReportName(mobNewsEventsDetails.getEventReport());
		if(mobNewsEventsDetails.getEventReport().endsWith("jpeg")){
			newsEventsEntryForm.setReportIsImage("true");
			}else{newsEventsEntryForm.setReportIsImage("false");}
	}
	if(mobNewsEventsDetails.getIconImage()!=null && ! mobNewsEventsDetails.getIconImage().isEmpty()){
		newsEventsEntryForm.setIconName(mobNewsEventsDetails.getIconImage());
	}
		
		if (mobNewsEventsDetails.getIsEventInvitationMail() != null
				&& mobNewsEventsDetails.getIsEventInvitationMail().equals(true)) {
			
			newsEventsEntryForm.setIsInvitationMailRequired("Yes");
			if(mobNewsEventsDetails.getInvitationMail()!=null && !mobNewsEventsDetails.getInvitationMail().isEmpty())
			{
				newsEventsEntryForm.setInvitationName(mobNewsEventsDetails.getInvitationMail());
				if(mobNewsEventsDetails.getInvitationMail().endsWith("jpeg")){
					newsEventsEntryForm.setInvitationIsImage("true");
					}else{newsEventsEntryForm.setInvitationIsImage("false");}
			}
		}else
		{
			newsEventsEntryForm.setIsInvitationMailRequired("No");
		}
		if (mobNewsEventsDetails.getIsLiveTelecast() != null
				&& mobNewsEventsDetails.getIsLiveTelecast().equals(true)) {
			newsEventsEntryForm.setIsLiveTelecast("Yes");
		}
		else
		{
			newsEventsEntryForm.setIsLiveTelecast("No");
		}
		if (mobNewsEventsDetails.getIsRegistrationRequired() != null
				&& mobNewsEventsDetails.getIsRegistrationRequired().equals(true)) {
			newsEventsEntryForm.setIsRegistrationRequired("Yes");
			if(mobNewsEventsDetails.getRegistrationForm()!=null && !mobNewsEventsDetails.getRegistrationForm().isEmpty())
			{
				newsEventsEntryForm.setRegFormName(mobNewsEventsDetails.getRegistrationForm());
				if(mobNewsEventsDetails.getRegistrationForm().endsWith("jpeg")){
					newsEventsEntryForm.setRegIsImage("true");
					}else{newsEventsEntryForm.setRegIsImage("false");}
			}
		}
		else
		{
			newsEventsEntryForm.setIsRegistrationRequired("No");
		}
	
	
	
	if(mobNewsEventsDetails.getEventReport()!=null && !mobNewsEventsDetails.getEventReport().isEmpty())
	{
		newsEventsEntryForm.setReportName(mobNewsEventsDetails.getEventReport());
	}

	if (mobNewsEventsDetails.getPreIsApproved() != null	&& mobNewsEventsDetails.getPreIsApproved().equals(true)) {
		newsEventsEntryForm.setPreIsApproved("true");
		if(mobNewsEventsDetails.getPreApprovalDate()!=null){
			newsEventsEntryForm.setPreApprovalDate(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getPreApprovalDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if(mobNewsEventsDetails.getPreApprovalRemarks()!=null && !mobNewsEventsDetails.getPreApprovalRemarks().isEmpty()){
			newsEventsEntryForm.setPreApprovalRemarks(mobNewsEventsDetails.getPreApprovalRemarks());
		}
		if(mobNewsEventsDetails.getPreApproverId()!=null && mobNewsEventsDetails.getPreApproverId().getId()>0){
			newsEventsEntryForm.setPreApproverId(String.valueOf(mobNewsEventsDetails.getPreApproverId().getId()));
		}
	}else
	{
		newsEventsEntryForm.setPreIsApproved("false");
	}
	if (mobNewsEventsDetails.getPostIsApproved() != null
			&& mobNewsEventsDetails.getPostIsApproved().equals(true)) {
		newsEventsEntryForm.setPostIsApproved("true");
		if(mobNewsEventsDetails.getPostApprovalDate()!=null){
			newsEventsEntryForm.setPostApprovalDate(CommonUtil.ConvertStringToDateFormat(mobNewsEventsDetails.getPostApprovalDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if(mobNewsEventsDetails.getPostApprovalRemarks()!=null && !mobNewsEventsDetails.getPostApprovalRemarks().isEmpty()){
			newsEventsEntryForm.setPostApprovalRemarks(mobNewsEventsDetails.getPostApprovalRemarks());
		}
		if(mobNewsEventsDetails.getPostApproverId()!=null && mobNewsEventsDetails.getPostApproverId().getId()>0){
			newsEventsEntryForm.setPostApproverId(String.valueOf(mobNewsEventsDetails.getPostApproverId().getId()));
		}
		
	}else
	{
		newsEventsEntryForm.setPostIsApproved("false");
	}
		if(mobNewsEventsDetails.getIsAdminApprovePost()!=null && mobNewsEventsDetails.getIsAdminApprovePost().equals(true)){
			newsEventsEntryForm.setIsAdminApprovePost("true");
		}else
		{
			newsEventsEntryForm.setIsAdminApprovePost("false");
		}
		if(mobNewsEventsDetails.getIsAdminApprovePre()!=null && mobNewsEventsDetails.getIsAdminApprovePre().equals(true))
		{
			newsEventsEntryForm.setIsAdminApprovePre("true");
		}else{
			newsEventsEntryForm.setIsAdminApprovePre("false");
		}
		if(mobNewsEventsDetails.getPostApproveStatus()!=null && !mobNewsEventsDetails.getPostApproveStatus().isEmpty())
		 {
			newsEventsEntryForm.setPostAdminStatus(mobNewsEventsDetails.getPostApproveStatus());
		 }
		else
		{
			newsEventsEntryForm.setPostAdminStatus("Not Published");
		}
		 if(mobNewsEventsDetails.getPreApproveStatus()!=null && !mobNewsEventsDetails.getPreApproveStatus().isEmpty())
		 {
			 newsEventsEntryForm.setPreAdminStatus(mobNewsEventsDetails.getPreApproveStatus());
		 }
		 else
			{
				newsEventsEntryForm.setPreAdminStatus("Not Published");
			}
		 
  List<NewEventsResourseTO> resTOs = new ArrayList<NewEventsResourseTO>();
	if (mobNewsEventsDetails.getNewsEventsResourse() != null) {
		Set<NewsEventsResourse> res = mobNewsEventsDetails.getNewsEventsResourse();
		if (res != null && !res.isEmpty()) {
			Iterator<NewsEventsResourse> iterator = res.iterator();
			while (iterator.hasNext()) {
				NewsEventsResourse resourse = iterator.next();
				if (resourse != null) {
					NewEventsResourseTO resto = new NewEventsResourseTO();

					if (resourse.getId() > 0) {
						resto.setId(resourse.getId());
					}
					if (resourse.getResourseName()!=null && (StringUtils.isNotEmpty(resourse.getResourseName()))) {
						resto.setResourseName(resourse.getResourseName());
					}
					if (resourse.getContactNo()!=null && (StringUtils.isNotEmpty(resourse.getContactNo()))){
						resto.setContactNo(resourse.getContactNo());
					}
					if (resourse.getEmail()!= null && !resourse.getEmail().isEmpty()) {
						resto.setEmail(resourse.getEmail());
					}
					if (resourse.getNewsEventsId()!= null && resourse.getNewsEventsId().getId()>0) {
						resto.setNewsEventsId(String.valueOf(resourse.getNewsEventsId().getId()));
					}
					if (resourse.getOtherInfo()!= null && !resourse.getOtherInfo().isEmpty()) {
						resto.setOtherInfo(resourse.getOtherInfo());
					}
					resTOs.add(resto);
				}
			}
			newsEventsEntryForm.setResourseListSize(String.valueOf(resTOs.size()));
			newsEventsEntryForm.setOrgResListSize(String.valueOf(resTOs.size()));
		} 
		// add 10 records to list
		for (int i = resTOs.size(); i < 20; i++) {
			NewEventsResourseTO resto = new NewEventsResourseTO();
			resTOs.add(resto);
		}
		newsEventsEntryForm.setResourseTO(resTOs);
	
	}
	List<NewsEventsContactDetailsTO> ctcTOs = new ArrayList<NewsEventsContactDetailsTO>();	
	if (mobNewsEventsDetails.getNewsEventsContactDetails()!= null) {
		Set<NewsEventsContactDetails> res = mobNewsEventsDetails.getNewsEventsContactDetails();
			
		if (res != null && !res.isEmpty()) {
			Iterator<NewsEventsContactDetails> iterator = res.iterator();
			
			while(iterator.hasNext()) {
				NewsEventsContactDetails contact = iterator.next();
				NewsEventsContactDetailsTO resTO = new NewsEventsContactDetailsTO();
					if (contact.getId() > 0) {
						resTO.setId(contact.getId());
					}
					if (contact.getContactNo()!=null && StringUtils.isNotEmpty(contact.getContactNo())) {
						resTO.setContactNo(contact.getContactNo());
					}

					if (contact.getEmail()!=null && StringUtils.isNotEmpty(contact.getEmail())) {
						resTO.setEmail(contact.getEmail());
					}

					if (contact.getRemarks()!= null && !contact.getRemarks().isEmpty()) {
						resTO.setRemarks(contact.getRemarks());
					}
					if (contact.getName()!= null && !contact.getName().isEmpty()) {
						resTO.setName(contact.getName());
					}
					ctcTOs.add(resTO);
				}
			}
		newsEventsEntryForm.setContactListSize(String.valueOf(ctcTOs.size()));
		newsEventsEntryForm.setOrgContactListSize(String.valueOf(ctcTOs.size()));
			// add 10 records
			for (int i = ctcTOs.size(); i < 20; i++) {
				NewsEventsContactDetailsTO resTo1 = new NewsEventsContactDetailsTO();
				ctcTOs.add(resTo1);
			}
			newsEventsEntryForm.setContactTO(ctcTOs);
		}
	List<NewsEventsParticipantTO> partTOs = new ArrayList<NewsEventsParticipantTO>();
	if (mobNewsEventsDetails.getParticipants() != null) {
		Set<NewsEventsParticipants> part = mobNewsEventsDetails.getNewsEventsParticipants();
		if (part != null && !part.isEmpty()) {
			Iterator<NewsEventsParticipants> iterator = part.iterator();
			while (iterator.hasNext()) {
				NewsEventsParticipants participants = iterator.next();
				if (participants != null) {
					NewsEventsParticipantTO partto = new NewsEventsParticipantTO();

					if (participants.getId() > 0) {
						partto.setId(participants.getId());
					}
					if (participants.getInstitutionName()!=null && (StringUtils.isNotEmpty(participants.getInstitutionName()))) {
						partto.setInstitutionName(participants.getInstitutionName());
					}
					if(participants.getNoOfPeople()>0) {
						partto.setNoOfPeople(String.valueOf(participants.getNoOfPeople()));
					}
					if (participants.getRemarks()!=null && (StringUtils.isNotEmpty(participants.getRemarks()))){
						partto.setRemarks(participants.getRemarks());
					}
					if (participants.getNewsEventsId()!= null && participants.getNewsEventsId().getId()>0) {
						partto.setNewsEventsId(String.valueOf(participants.getNewsEventsId().getId()));
					}
					
					partTOs.add(partto);
				}
			}
			newsEventsEntryForm.setParticipantsListSize(String.valueOf(partTOs.size()));
			newsEventsEntryForm.setOrgPartListSize(String.valueOf(partTOs.size()));
		} 
		// add 10 records to list
		for (int i = partTOs.size(); i < 20; i++) {
			NewsEventsParticipantTO partto = new NewsEventsParticipantTO();
			partTOs.add(partto);
		}
		newsEventsEntryForm.setPartcipantsTO(partTOs);
	}
	List<NewsEventsPhotosTO> photoTOs = new ArrayList<NewsEventsPhotosTO>();
	if (mobNewsEventsDetails.getNewsEventsPhotos() != null) {
		Set<NewsEventsPhoto> photo = mobNewsEventsDetails.getNewsEventsPhotos();
		if (photo != null && !photo.isEmpty()) {
			Iterator<NewsEventsPhoto> iterator = photo.iterator();

			while (iterator.hasNext()) {
				NewsEventsPhoto photo1 = iterator.next();
				if (photo1 != null && photo1.getIsActive() != null && photo1.getIsActive()) {
					NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();

					if (photo1.getId() > 0) {
						phototo.setId(photo1.getId());
					}
					if (photo1.getPhotoName()!=null && (StringUtils.isNotEmpty(photo1.getPhotoName()))) {
						phototo.setPhotoName(photo1.getPhotoName());
					}
					if (photo1.getNewsEventsId()!= null && photo1.getNewsEventsId().getId()>0) {
						phototo.setNewsEventsId(String.valueOf(photo1.getNewsEventsId().getId()));
					}
					photoTOs.add(phototo);
				}
			}
			newsEventsEntryForm.setPhotoListSize(String.valueOf(photoTOs.size()));
			newsEventsEntryForm.setOrgphotoListSize(String.valueOf(photoTOs.size()));
		} 
		// add 10 records to list
		for (int i = photoTOs.size(); i < 10; i++) {
			NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();
			photoTOs.add(phototo);
		}
		newsEventsEntryForm.setPhotosTO(photoTOs);
	}
	return newsEventsEntryForm;
}


private Set<NewsEventsParticipants> getParticipantsObjects(NewsEventsEntryForm newsEventsForm) {
	Set<NewsEventsParticipants> participants = new HashSet<NewsEventsParticipants>();
	if (newsEventsForm.getPartcipantsTO()!= null && !newsEventsForm.getPartcipantsTO().isEmpty()) {
		Iterator<NewsEventsParticipantTO> itr = newsEventsForm.getPartcipantsTO().iterator();
		while (itr.hasNext()) {
			NewsEventsParticipantTO to = (NewsEventsParticipantTO) itr.next();
			NewsEventsParticipants res = new NewsEventsParticipants();
			if (to.getInstitutionName() != null && !to.getInstitutionName().isEmpty()
					|| to.getNoOfPeople() != null && !to.getNoOfPeople().isEmpty() 
					|| to.getRemarks()!=null && !to.getRemarks().isEmpty())
					 {
				if (to.getId() > 0) {
					res.setId(to.getId());
				}
				res.setCreatedBy(newsEventsForm.getUserId());
				res.setCreatedDate(new Date());
				res.setModifiedBy(newsEventsForm.getUserId());
				res.setLastModifiedDate(new Date());
				res.setIsActive(true);
				res.setInstitutionName(to.getInstitutionName());
				res.setNoOfPeople(Integer.parseInt(to.getNoOfPeople()));
				res.setRemarks(to.getRemarks());
				participants.add(res);
			}
		}
	}
return participants;
}

public NewsEventsDetails convertNewsFormToBOPrePost(NewsEventsEntryForm newsEventsEntryForm,HttpServletRequest request) throws Exception 
{
	NewsEventsDetails mobNewsEventsDetails=new NewsEventsDetails();
	final ImageFilter imageFilter = new ImageFilter();
	Properties prop=new Properties();
	InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	prop.load(stream);
	if(newsEventsEntryForm.getSelectedNewsEventsId()!=null && !newsEventsEntryForm.getSelectedNewsEventsId().isEmpty())
	{
		mobNewsEventsDetails.setId(Integer.parseInt(newsEventsEntryForm.getSelectedNewsEventsId()));
	}
	if(newsEventsEntryForm.getNewsOrEvents()!=null && !newsEventsEntryForm.getNewsOrEvents().isEmpty()){
		mobNewsEventsDetails.setNewOrEvents(newsEventsEntryForm.getNewsOrEvents());
	}	
	if(newsEventsEntryForm.getEventTitle()!=null && !newsEventsEntryForm.getEventTitle().isEmpty())
	{
		mobNewsEventsDetails.setEventTitle(newsEventsEntryForm.getEventTitle());
	}
	if(newsEventsEntryForm.getEventDescription()!=null && !newsEventsEntryForm.getEventDescription().isEmpty())
	{
		mobNewsEventsDetails.setEventDescription(newsEventsEntryForm.getEventDescription());
	}
	// code modified by nagarjuna
	//start
	if (newsEventsEntryForm.getPhotosTO() != null && !newsEventsEntryForm.getPhotosTO().isEmpty()) {
		Set<NewsEventsPhoto> images = new HashSet<NewsEventsPhoto>();
		Iterator<NewsEventsPhotosTO> itr = newsEventsEntryForm.getPhotosTO().iterator();
		while (itr.hasNext()) {
		NewsEventsPhotosTO to = (NewsEventsPhotosTO) itr.next();
		int count=1;
		if ((to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0)) {
			NewsEventsPhoto img = new NewsEventsPhoto();
			if (to.getId() > 0) {
				img.setId(to.getId());
			}
		if (to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0) {
			FormFile file = to.getPhotoFile();
			byte[] fileData = file.getFileData();
			String contentType=file.getContentType();
			String fileName=file.getFileName();
		   if(imageFilter.accept(fileName))
		   {
			   Long date=new Date().getTime();
		 	  String dateString=String.valueOf(date);
		    	String newFileName=fileName.replaceAll(fileName, "eventPhoto"+count+dateString+".jpeg");
		    	String filePath=request.getRealPath("");
		    	filePath = filePath + "//TempFiles//";
				File file1 = new File(filePath+newFileName);
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
			    OutputStream out = new FileOutputStream(file1);
			    byte buffer[] = new byte[1024];
			    int len;
			    while ((len = inputStream.read(buffer)) > 0){
				out.write(buffer, 0, len);
			    }
			   out.close();
			   inputStream.close();
			   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadImage.path");
			   destinationPath=destinationPath+"/"+newFileName;
			  //compress Image Start
			   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
		        //showImage("Original Image", i);
		        // Show results with different compression ratio.
		        //compressAndShow(i, 0.5f);
		        float quality=0.2f;
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
		       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
		        //showImage("Compressed to " + quality + ": " + size + " bytes", out);
		        // Uncomment code below to save the compressed files.
		     //compress Image End
		        File file3 = new File(filePath+"compressed."+newFileName);
		        FileImageOutputStream output = new FileImageOutputStream(file3);
		        writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
			   File file4=new File(destinationPath);
			   FileInputStream fileInputStream=new FileInputStream(file3);
			   byte[] buf = new byte[(int) file3.length()] ;
			   OutputStream outputStream = new FileOutputStream(file4);
				for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
					outputStream.write(buf, 0, readNum); //no doubt here is 0
				}
				img.setPhotoName(newFileName);
		   }
		}
		img.setCreatedBy(newsEventsEntryForm.getUserId());
		img.setCreatedDate(new Date());
		img.setModifiedBy(newsEventsEntryForm.getUserId());
		img.setLastModifiedDate(new Date());
		img.setIsActive(true);
		images.add(img);
		}else if(to.getPhotoName() != null && !to.getPhotoName().trim().isEmpty()){
			NewsEventsPhoto img = new NewsEventsPhoto();
			if (to.getId() > 0) {
				img.setId(to.getId());
			}
			img.setCreatedBy(newsEventsEntryForm.getUserId());
			img.setCreatedDate(new Date());
			img.setPhotoName(to.getPhotoName());
			img.setModifiedBy(newsEventsEntryForm.getUserId());
			img.setLastModifiedDate(new Date());
			img.setIsActive(true);
			images.add(img);
		}
		}
		mobNewsEventsDetails.setNewsEventsPhotos(images);
	}
	//end
	
	
	
	if (newsEventsEntryForm.getIconTmageFile() != null && newsEventsEntryForm.getIconTmageFile().getFileSize()>0) {
		addIconToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
	}
	else if(newsEventsEntryForm.getIconName()!=null && !newsEventsEntryForm.getIconName().isEmpty()){
		  mobNewsEventsDetails.setIconImage(newsEventsEntryForm.getIconName());
	  }
	
	if(newsEventsEntryForm.getIsInvitationMailRequired().equalsIgnoreCase("Yes"))
		{
		mobNewsEventsDetails.setIsEventInvitationMail(true);
		if (newsEventsEntryForm.getInvitationMail() != null && newsEventsEntryForm.getInvitationMail().getFileSize()>0) {
			addInvitationToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
		}else if(newsEventsEntryForm.getInvitationName()!=null && !newsEventsEntryForm.getInvitationName().isEmpty()){
			  mobNewsEventsDetails.setInvitationMail(newsEventsEntryForm.getInvitationName());
		  }
		}else
			{
			mobNewsEventsDetails.setIsEventInvitationMail(false);
			}
if(newsEventsEntryForm.getIsRegistrationRequired().equalsIgnoreCase("Yes"))
		{
			mobNewsEventsDetails.setIsRegistrationRequired(true);
			if (newsEventsEntryForm.getRegistrationForm() != null && newsEventsEntryForm.getRegistrationForm().getFileSize()>0) {
				addRegistrationToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
		  }else if(newsEventsEntryForm.getRegFormName()!=null && !newsEventsEntryForm.getRegFormName().isEmpty()){
				  mobNewsEventsDetails.setRegistrationForm(newsEventsEntryForm.getRegFormName());
			  }
		}else
		{
			mobNewsEventsDetails.setIsRegistrationRequired(false);
		}
if (newsEventsEntryForm.getEventReport() != null && newsEventsEntryForm.getEventReport().getFileSize()>0) {
	addReportToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
}
else if(newsEventsEntryForm.getReportName()!=null && !newsEventsEntryForm.getReportName().isEmpty()){
	  mobNewsEventsDetails.setEventReport(newsEventsEntryForm.getReportName());
  }
	
if (newsEventsEntryForm.getMaterialsPublished() != null && newsEventsEntryForm.getMaterialsPublished().getFileSize()>0) {
	addMaterialToFileSystem(newsEventsEntryForm, request,mobNewsEventsDetails);
}
else if(newsEventsEntryForm.getMaterialName()!=null && !newsEventsEntryForm.getMaterialName().isEmpty()){
	  mobNewsEventsDetails.setMaterialsPublished(newsEventsEntryForm.getMaterialName());
  }
	
	

if(newsEventsEntryForm.getIsLiveTelecast().equalsIgnoreCase("Yes"))
{
	mobNewsEventsDetails.setIsLiveTelecast(true);
}
else
{
	mobNewsEventsDetails.setIsLiveTelecast(false);
}

if(newsEventsEntryForm.getIsPostDeptEntry().equalsIgnoreCase("true"))
{
	mobNewsEventsDetails.setIsPostDeptEntry(true);
}
else
{
	mobNewsEventsDetails.setIsPostDeptEntry(false);
}

if(newsEventsEntryForm.getDateFrom()!=null && !newsEventsEntryForm.getDateFrom().isEmpty()){
	mobNewsEventsDetails.setDateFrom(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDateFrom()));
}
if(newsEventsEntryForm.getDateTo()!=null && !newsEventsEntryForm.getDateTo().isEmpty()){
	mobNewsEventsDetails.setDateTo(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDateTo()));
}
if(newsEventsEntryForm.getCategoryId()!=null && !newsEventsEntryForm.getCategoryId().isEmpty()){
	MobNewsEventsCategory mob=new MobNewsEventsCategory();
	mob.setId(Integer.parseInt(newsEventsEntryForm.getCategoryId()));
	mobNewsEventsDetails.setCategoryId(mob);
}
if(newsEventsEntryForm.getAcademicYear()!=null && !newsEventsEntryForm.getAcademicYear().isEmpty()){
	mobNewsEventsDetails.setAcademicYear(newsEventsEntryForm.getAcademicYear());
}
if(newsEventsEntryForm.getDisplayFromDate()!=null && !newsEventsEntryForm.getDisplayFromDate().isEmpty()){
	mobNewsEventsDetails.setDisplayFromDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDisplayFromDate()));
}
if(newsEventsEntryForm.getDisplayToDate()!=null && !newsEventsEntryForm.getDisplayToDate().isEmpty()){
	mobNewsEventsDetails.setDisplayToDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getDisplayToDate()));
}
if(newsEventsEntryForm.getParticipants()!=null && !newsEventsEntryForm.getParticipants().isEmpty()){
	mobNewsEventsDetails.setParticipants(newsEventsEntryForm.getParticipants());
}
if(newsEventsEntryForm.getOrganizedBy()!=null && !newsEventsEntryForm.getOrganizedBy().isEmpty()){
	mobNewsEventsDetails.setOrganizedBy(newsEventsEntryForm.getOrganizedBy());

if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Course")){
	if(newsEventsEntryForm.getCourseId()!=null && !newsEventsEntryForm.getCourseId().isEmpty()){
	Course cs=new Course();
	cs.setId(Integer.parseInt(newsEventsEntryForm.getCourseId()));
	mobNewsEventsDetails.setCourseId(cs);
	}
}
if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Department")){
	if(newsEventsEntryForm.getDepartmentId()!=null && !newsEventsEntryForm.getDepartmentId().isEmpty()){
	Department ds=new Department();
	ds.setId(Integer.parseInt(newsEventsEntryForm.getDepartmentId()));
	mobNewsEventsDetails.setDeptId(ds);
	}
}
if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Deanery")){
	if(newsEventsEntryForm.getStreamId()!=null && !newsEventsEntryForm.getStreamId().isEmpty()){
	EmployeeStreamBO ss=new EmployeeStreamBO();
	ss.setId(Integer.parseInt(newsEventsEntryForm.getStreamId()));
	mobNewsEventsDetails.setStreamId(ss);
	}
}
if(newsEventsEntryForm.getOrganizedBy().equalsIgnoreCase("Special Centers")){
	if(newsEventsEntryForm.getSplCenterId()!=null && !newsEventsEntryForm.getSplCenterId().isEmpty()){
	Department cs=new Department();
	cs.setId(Integer.parseInt(newsEventsEntryForm.getSplCenterId()));
	mobNewsEventsDetails.setSplCentre(cs);
	}
}
}

if(newsEventsEntryForm.getNewsWebPosition()!=null && !newsEventsEntryForm.getNewsWebPosition().isEmpty()){
	mobNewsEventsDetails.setNewsWebPosition(newsEventsEntryForm.getNewsWebPosition());
}
if(newsEventsEntryForm.getEventWebPosition()!=null && !newsEventsEntryForm.getEventWebPosition().isEmpty()){
	mobNewsEventsDetails.setEventWebPosition(newsEventsEntryForm.getEventWebPosition());
}
if(newsEventsEntryForm.getViewFor()!=null && !newsEventsEntryForm.getViewFor().isEmpty()){
	mobNewsEventsDetails.setViewFor(newsEventsEntryForm.getViewFor());
}
if(newsEventsEntryForm.getSummary()!=null && !newsEventsEntryForm.getSummary().isEmpty()){
	mobNewsEventsDetails.setSummary(newsEventsEntryForm.getSummary());
}


if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Pre")){
			mobNewsEventsDetails.setPreIsApproved(true);
			mobNewsEventsDetails.setPostIsApproved(false);
			
			if(newsEventsEntryForm.getPreApprovalDate()!=null && !newsEventsEntryForm.getPreApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPreApprovalDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getPreApprovalDate()));
			}
			else
			{
				mobNewsEventsDetails.setPreApprovalDate(new Date());
			}
			if(newsEventsEntryForm.getPreApprovalRemarks()!=null && !newsEventsEntryForm.getPreApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPreApprovalRemarks(newsEventsEntryForm.getPreApprovalRemarks());
			}
			if(newsEventsEntryForm.getPreApproverId()!=null && !newsEventsEntryForm.getPreApproverId().isEmpty()){
					Employee preApprover =new Employee();
					preApprover.setId(Integer.parseInt(newsEventsEntryForm.getPreApproverId()));
					mobNewsEventsDetails.setPreApproverId(preApprover);
			}
		
}else if(newsEventsEntryForm.getScreen().equalsIgnoreCase("Post")){
	if(newsEventsEntryForm.getPreIsApproved()!=null && !newsEventsEntryForm.getPreIsApproved().isEmpty()){
		if(newsEventsEntryForm.getPreIsApproved().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setPreIsApproved(true);
			
			if(newsEventsEntryForm.getPreApprovalDate()!=null && !newsEventsEntryForm.getPreApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPreApprovalDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getPreApprovalDate()));
			}
			else
			{
				mobNewsEventsDetails.setPreApprovalDate(new Date());
			}
			if(newsEventsEntryForm.getPreApprovalRemarks()!=null && !newsEventsEntryForm.getPreApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPreApprovalRemarks(newsEventsEntryForm.getPreApprovalRemarks());
			}
			if(newsEventsEntryForm.getPreApproverId()!=null && !newsEventsEntryForm.getPreApproverId().isEmpty()){
					Employee preApprover =new Employee();
					preApprover.setId(Integer.parseInt(newsEventsEntryForm.getPreApproverId()));
					mobNewsEventsDetails.setPreApproverId(preApprover);
			}
		}else{
			mobNewsEventsDetails.setPreIsApproved(false);	
		}
	}

			mobNewsEventsDetails.setPostIsApproved(true);
			if(newsEventsEntryForm.getPostApprovalRemarks()!=null && !newsEventsEntryForm.getPostApprovalRemarks().isEmpty()){
				mobNewsEventsDetails.setPostApprovalRemarks(newsEventsEntryForm.getPostApprovalRemarks());
			}
			if(newsEventsEntryForm.getPostApprovalDate()!=null && !newsEventsEntryForm.getPostApprovalDate().isEmpty()){
				mobNewsEventsDetails.setPostApprovalDate(CommonUtil.ConvertStringToDate(newsEventsEntryForm.getPostApprovalDate()));
			}else
				mobNewsEventsDetails.setPostApprovalDate(new Date());
			
			if(newsEventsEntryForm.getPostApproverId()!=null && !newsEventsEntryForm.getPostApproverId().isEmpty()){
				Employee postapprover =new Employee();
				postapprover.setId(Integer.parseInt(newsEventsEntryForm.getPostApproverId()));
				mobNewsEventsDetails.setPostApproverId(postapprover);
			}
		}

if(newsEventsEntryForm.getPrePostEventAdm()!=null && !newsEventsEntryForm.getPrePostEventAdm().isEmpty() && newsEventsEntryForm.getPrePostEventAdm().equalsIgnoreCase("Pre-Event")){
	if(newsEventsEntryForm.getPreAdminStatus()!=null && !newsEventsEntryForm.getPreAdminStatus().isEmpty()){
		mobNewsEventsDetails.setPreApproveStatus(newsEventsEntryForm.getPreAdminStatus());
		mobNewsEventsDetails.setIsAdminApprovePre(true);
	}else
	{
		if(newsEventsEntryForm.getIsAdminApprovePre()!=null && !newsEventsEntryForm.getIsAdminApprovePre().isEmpty() && newsEventsEntryForm.getIsAdminApprovePre().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setIsAdminApprovePre(true);
		}else{
			mobNewsEventsDetails.setIsAdminApprovePre(false);
		}
		
	}
}
else if(newsEventsEntryForm.getPrePostEventAdm()!=null && !newsEventsEntryForm.getPrePostEventAdm().isEmpty() && newsEventsEntryForm.getPrePostEventAdm().equalsIgnoreCase("Post-Event")){
	if(newsEventsEntryForm.getPostAdminStatus()!=null && !newsEventsEntryForm.getPostAdminStatus().isEmpty()){
		mobNewsEventsDetails.setPostApproveStatus(newsEventsEntryForm.getPostAdminStatus());
		mobNewsEventsDetails.setIsAdminApprovePost(true);
	}else
	{
		if(newsEventsEntryForm.getIsAdminApprovePost()!=null && !newsEventsEntryForm.getIsAdminApprovePost().isEmpty() && newsEventsEntryForm.getIsAdminApprovePost().equalsIgnoreCase("true")){
			mobNewsEventsDetails.setIsAdminApprovePost(true);
		}
		else{
			mobNewsEventsDetails.setIsAdminApprovePost(false);
		}
	}
}
else
{
	if(newsEventsEntryForm.getIsAdminApprovePost()!=null && !newsEventsEntryForm.getIsAdminApprovePost().isEmpty() && newsEventsEntryForm.getIsAdminApprovePost().equalsIgnoreCase("true")){
	mobNewsEventsDetails.setIsAdminApprovePost(true);
	}
	else{
	mobNewsEventsDetails.setIsAdminApprovePost(false);
	}
	
	if(newsEventsEntryForm.getIsAdminApprovePre()!=null && !newsEventsEntryForm.getIsAdminApprovePre().isEmpty() && newsEventsEntryForm.getIsAdminApprovePre().equalsIgnoreCase("true")){
		mobNewsEventsDetails.setIsAdminApprovePre(true);
	}else{
		mobNewsEventsDetails.setIsAdminApprovePre(false);
	}
	if(newsEventsEntryForm.getPostAdminStatus()!=null && !newsEventsEntryForm.getPostAdminStatus().isEmpty()){
		mobNewsEventsDetails.setPostApproveStatus(newsEventsEntryForm.getPostAdminStatus());
	}
	if(newsEventsEntryForm.getPreAdminStatus()!=null && !newsEventsEntryForm.getPreAdminStatus().isEmpty()){
		mobNewsEventsDetails.setPreApproveStatus(newsEventsEntryForm.getPreAdminStatus());
	}
}
Set<NewsEventsResourse> newsResourse = getResourseObjects(newsEventsEntryForm); 
if(newsResourse!=null && !newsResourse.isEmpty()){
	mobNewsEventsDetails.setNewsEventsResourse(newsResourse);
}
Set<NewsEventsParticipants> newsParticipants= getParticipantsObjects(newsEventsEntryForm);  
if(newsParticipants!=null && !newsParticipants.isEmpty()){
	mobNewsEventsDetails.setNewsEventsParticipants(newsParticipants);
}
Set<NewsEventsContactDetails> Contact = getContactObjects(newsEventsEntryForm); 
if(Contact!=null && !Contact.isEmpty()){
	mobNewsEventsDetails.setNewsEventsContactDetails(Contact);
}
mobNewsEventsDetails.setIsActive(true);
mobNewsEventsDetails.setCreatedBy(newsEventsEntryForm.getUserId());
mobNewsEventsDetails.setCreatedDate(new Date());
mobNewsEventsDetails.setModifiedBy(newsEventsEntryForm.getUserId());
mobNewsEventsDetails.setLastModifiedDate(new Date());
return mobNewsEventsDetails;
}

public void convertBoToFormAdmin(NewsEventsDetails newEventDetail, NewsEventsEntryForm newsEventsForm) throws Exception {
	if (newEventDetail != null) {
		if(newEventDetail.getId()>0){
			newsEventsForm.setSelectedNewsEventsId(String.valueOf(newEventDetail.getId()));
		}
		
	if(newEventDetail.getNewOrEvents()!=null && !newEventDetail.getNewOrEvents().isEmpty()){
			newsEventsForm.setNewsOrEvents(newEventDetail.getNewOrEvents());
	}

	if (newEventDetail.getIsEventInvitationMail() != null
			&& newEventDetail.getIsEventInvitationMail().equals(true)) {
		newsEventsForm.setIsInvitationMailRequired("Yes");
	}else
	{
		newsEventsForm.setIsInvitationMailRequired("No");
	}
	if (newEventDetail.getIsLiveTelecast() != null
			&& newEventDetail.getIsLiveTelecast().equals(true)) {
		newsEventsForm.setIsLiveTelecast("Yes");
	}
	else
	{
		newsEventsForm.setIsLiveTelecast("No");
	}
	if (newEventDetail.getIsRegistrationRequired() != null
			&& newEventDetail.getIsRegistrationRequired().equals(true)) {
		newsEventsForm.setIsRegistrationRequired("Yes");
	}
	else
	{
		newsEventsForm.setIsRegistrationRequired("NO");
	}	
	if (newEventDetail.getPreIsApproved() != null
			&& !newEventDetail.getPreIsApproved().equals(true)) {
		newsEventsForm.setPreIsApproved("true");
	}else
	{
		newsEventsForm.setPreIsApproved("false");
	}
	if (newEventDetail.getAcademicYear()!= null
			&& !newEventDetail.getAcademicYear()
					.isEmpty()) {
		newsEventsForm.setAcademicYear(newEventDetail.getAcademicYear());
	}

	if (newEventDetail.getCategoryId() != null
			&& newEventDetail.getCategoryId().getId() > 0) {
		newsEventsForm.setCategoryId(String.valueOf(newEventDetail
				.getCategoryId().getId()));
		newsEventsForm.setCategoryName(newEventDetail
				.getCategoryId().getCategory());

	}
	
	if (newEventDetail.getDateFrom() != null) {
		newsEventsForm.setDateFrom(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDateFrom().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if (newEventDetail.getDateTo() != null) {
		newsEventsForm.setDateTo(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDateTo().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if (newEventDetail.getDisplayFromDate() != null) {
		newsEventsForm.setDisplayFromDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDisplayFromDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if (newEventDetail.getDisplayToDate() != null) {
		newsEventsForm.setDisplayToDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getDisplayToDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
	}
	if (newEventDetail.getEventDescription() != null && !newEventDetail.getEventDescription().isEmpty()) {
		newsEventsForm.setEventDescription(newEventDetail.getEventDescription());
	}
	if (newEventDetail.getEventTitle()!= null && !newEventDetail.getEventTitle().isEmpty()) {
		newsEventsForm.setEventTitle(newEventDetail.getEventTitle());
	}
	
		
	if (newEventDetail.getEventWebPosition()!= null && !newEventDetail.getEventWebPosition().isEmpty()) {
		newsEventsForm.setEventWebPosition(newEventDetail.getEventWebPosition());
	}
	if (newEventDetail.getEventReport()!= null && !newEventDetail.getEventReport().isEmpty()) {
	     newsEventsForm.setReportName(newEventDetail.getEventReport());
	     if(newEventDetail.getEventReport().endsWith("jpeg")){
				newsEventsForm.setReportIsImage("true");
				}else{newsEventsForm.setReportIsImage("false");}
			
	}
	if(newEventDetail.getIconImage()!=null && !newEventDetail.getIconImage().isEmpty()){
		newsEventsForm.setIconName(newEventDetail.getIconImage());
	}
	if (newEventDetail.getInvitationMail()!= null && !newEventDetail.getInvitationMail().isEmpty()) {
		newsEventsForm.setInvitationName(newEventDetail.getInvitationMail());
		if(newEventDetail.getInvitationMail().endsWith("jpeg")){
			newsEventsForm.setInvitationIsImage("true");
			}else{newsEventsForm.setInvitationIsImage("false");}
		
	}
	if (newEventDetail.getMaterialsPublished()!= null && !newEventDetail.getMaterialsPublished().isEmpty()) {
		newsEventsForm.setMaterialName(newEventDetail.getMaterialsPublished());
		if(newEventDetail.getMaterialsPublished().endsWith("jpeg")){
			newsEventsForm.setMaterialIsImage("true");
			}else{newsEventsForm.setMaterialIsImage("false");}
	}
	if(newEventDetail.getRegistrationForm()!=null && !newEventDetail.getRegistrationForm().isEmpty()){
		newsEventsForm.setRegFormName(newEventDetail.getRegistrationForm());

		if(newEventDetail.getRegistrationForm().endsWith("jpeg")){
			newsEventsForm.setRegIsImage("true");
			}else{newsEventsForm.setRegIsImage("false");}
	}
	if (newEventDetail.getNewsWebPosition()!= null && !newEventDetail.getNewsWebPosition().isEmpty()) {
		newsEventsForm.setNewsWebPosition(newEventDetail.getNewsWebPosition());
	}
	
	if (newEventDetail.getOrganizedBy()!= null && !newEventDetail.getOrganizedBy().isEmpty()) {
		newsEventsForm.setOrganizedBy(newEventDetail.getOrganizedBy());
	}
	if(newEventDetail.getDeptId()!=null && newEventDetail.getDeptId().getId()>0){
		newsEventsForm.setDepartmentId(String.valueOf(newEventDetail.getDeptId().getId()));
		newsEventsForm.setDepartmentName(newEventDetail.getDeptId().getName());
	}else
	{
		newsEventsForm.setDepartmentName("");
	}
	if(newEventDetail.getCourseId()!=null && newEventDetail.getCourseId().getId()>0){
		newsEventsForm.setCourseId(String.valueOf(newEventDetail.getCourseId().getId()));
		newsEventsForm.setCourseName(newEventDetail.getCourseId().getName());
	}else
	{
		newsEventsForm.setCourseName("");
	}
	if(newEventDetail.getStreamId()!=null && newEventDetail.getStreamId().getId()>0){
		newsEventsForm.setStreamId(String.valueOf(newEventDetail.getStreamId().getId()));
		newsEventsForm.setStreamName(newEventDetail.getStreamId().getName());
	}else
	{
		newsEventsForm.setStreamName("");
	}
	if(newEventDetail.getSplCentre()!=null && newEventDetail.getSplCentre().getId()>0){
		newsEventsForm.setSplCenterId(String.valueOf(newEventDetail.getSplCentre().getId()));
		newsEventsForm.setSplCentreName(newEventDetail.getSplCentre().getName());
	}else
	{
		newsEventsForm.setSplCentreName("");
	}
	if (newEventDetail.getParticipants()!= null && !newEventDetail.getParticipants().isEmpty()) {
		newsEventsForm.setParticipants(newEventDetail.getParticipants());
	}
	if (newEventDetail.getPostApprovalDate()!= null) {
		newsEventsForm.setPostApprovalDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getPostApprovalDate().toString(), "yyyy-mm-dd",
				"dd/mm/yyyy"));
	}
	if (newEventDetail.getPostIsApproved()!= null && newEventDetail.getPostIsApproved().equals(true)) {
		newsEventsForm.setPostIsApproved("true");
	}else
	{
		newsEventsForm.setPostIsApproved("false");
	}
	if(newEventDetail.getPostApprovalRemarks()!=null && !newEventDetail.getPostApprovalRemarks().isEmpty()){
		newsEventsForm.setPostApprovalRemarks(newEventDetail.getPostApprovalRemarks());
	}
	if(newEventDetail.getPreApprovalRemarks()!=null && !newEventDetail.getPreApprovalRemarks().isEmpty()){
		newsEventsForm.setPreApprovalRemarks(newEventDetail.getPreApprovalRemarks());
	}
	if (newEventDetail.getPreApprovalDate()!= null) {
		newsEventsForm.setPreApprovalDate(CommonUtil.ConvertStringToDateFormat(newEventDetail.getPreApprovalDate().toString(), "yyyy-mm-dd",
				"dd/mm/yyyy"));
	}
	if(newEventDetail.getPreApproverId()!=null && newEventDetail.getPreApproverId().getId()>0){
		newsEventsForm.setPreApproverId(String.valueOf(newEventDetail.getPreApproverId().getId()));
	}
	if(newEventDetail.getPostApproverId()!=null && newEventDetail.getPostApproverId().getId()>0){
		newsEventsForm.setPreApproverId(String.valueOf(newEventDetail.getPostApproverId().getId()));
	}
	/*if(newEventDetail.getViewFor()!=null && !newEventDetail.getViewFor().isEmpty()){
		newsEventsForm.setViewFor(newEventDetail.getViewFor());
	}*/
	if(newsEventsForm.getScreen().equalsIgnoreCase("Admin")){
		if(newEventDetail.getViewFor()!=null && !newEventDetail.getViewFor().isEmpty()){
			newsEventsForm.setViewFor(newEventDetail.getViewFor());
		}
	}else
	{
	if(newEventDetail.getViewFor()!=null && !newEventDetail.getViewFor().isEmpty()){
		String[] ids = newEventDetail.getViewFor().split(",");
		newsEventsForm.setSelectedviewFor(ids);
	}
	}
	if(newEventDetail.getSummary()!=null && !newEventDetail.getSummary().isEmpty()){
		newsEventsForm.setSummary(newEventDetail.getSummary());
	}
	if (newEventDetail.getIsPostDeptEntry()!= null && newEventDetail.getIsPostDeptEntry().equals(true)) {
		newsEventsForm.setIsPostDeptEntry("true");
	}else
	{
		newsEventsForm.setIsPostDeptEntry("false");
	}
	if(newsEventsForm.getScreen()!=null && !newsEventsForm.getScreen().isEmpty()){
		if(newsEventsForm.getScreen().equalsIgnoreCase("Admin")){
			if(newEventDetail.getIsAdminApprovePre()!=null && newEventDetail.getIsAdminApprovePre().equals(true)){
				if(newEventDetail.getPreApproveStatus()!=null && !newEventDetail.getPreApproveStatus().isEmpty())
				{
					newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());
				}
				newsEventsForm.setIsAdminApprovePre("true");
			}else
			{
				newsEventsForm.setIsAdminApprovePre("false");
				newsEventsForm.setPreAdminStatus(newEventDetail.getPreApproveStatus());

			}
			if(newEventDetail.getIsAdminApprovePost()!=null && newEventDetail.getIsAdminApprovePost().equals(true)){
				if(newEventDetail.getPostApproveStatus()!=null && !newEventDetail.getPostApproveStatus().isEmpty())
				{
					newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());
				}
				newsEventsForm.setIsAdminApprovePost("true");
			}else
			{
				newsEventsForm.setIsAdminApprovePost("false");
				newsEventsForm.setPostAdminStatus(newEventDetail.getPostApproveStatus());

			}
		}
	}
	if (newEventDetail.getNewsEventsResourse()!= null) {
		Set<NewsEventsResourse> res = newEventDetail.getNewsEventsResourse();
		List<NewEventsResourseTO> resTOs = new ArrayList<NewEventsResourseTO>();		
		if (res != null && !res.isEmpty()) {
			Iterator<NewsEventsResourse> iterator = res.iterator();
			while(iterator.hasNext()) {
				NewsEventsResourse resourse = iterator.next();
				if (resourse != null) {
					NewEventsResourseTO resTO = new NewEventsResourseTO();
					if (resourse.getId() > 0) {
						resTO.setId(resourse.getId());
					}
					if (resourse.getContactNo()!=null && StringUtils.isNotEmpty(resourse.getContactNo())) {
						resTO.setContactNo(resourse.getContactNo());
					}

					if (resourse.getEmail()!=null && StringUtils.isNotEmpty(resourse.getEmail())) {
						resTO.setEmail(resourse.getEmail());
					}

					if (resourse.getOtherInfo()!= null && !resourse.getOtherInfo().isEmpty()) {
						resTO.setOtherInfo(resourse.getOtherInfo());
					}
					if (resourse.getResourseName()!= null && !resourse.getResourseName().isEmpty()) {
						resTO.setResourseName(resourse.getResourseName());
					}
					resTOs.add(resTO);
				}
			}
		} 
		newsEventsForm.setResourseListSize(String.valueOf(resTOs.size()));
		newsEventsForm.setOrgResListSize(String.valueOf(resTOs.size()));
		newsEventsForm.setResourseTO(resTOs);
	}
	if (newEventDetail.getNewsEventsContactDetails()!= null) {
		Set<NewsEventsContactDetails> res = newEventDetail.getNewsEventsContactDetails();
		List<NewsEventsContactDetailsTO> resTOs = new ArrayList<NewsEventsContactDetailsTO>();		
		if (res != null && !res.isEmpty()) {
			Iterator<NewsEventsContactDetails> iterator = res.iterator();
			
			while(iterator.hasNext()) {
				NewsEventsContactDetails contact = iterator.next();
				NewsEventsContactDetailsTO resTO = new NewsEventsContactDetailsTO();
					if (contact.getId() > 0) {
						resTO.setId(contact.getId());
					}
					if (contact.getContactNo()!=null && StringUtils.isNotEmpty(contact.getContactNo())) {
						resTO.setContactNo(contact.getContactNo());
					}

					if (contact.getEmail()!=null && StringUtils.isNotEmpty(contact.getEmail())) {
						resTO.setEmail(contact.getEmail());
					}

					if (contact.getRemarks()!= null && !contact.getRemarks().isEmpty()) {
						resTO.setRemarks(contact.getRemarks());
					}
					if (contact.getName()!= null && !contact.getName().isEmpty()) {
						resTO.setName(contact.getName());
					}
					resTOs.add(resTO);
				}
			}
			newsEventsForm.setContactListSize(String.valueOf(resTOs.size()));
			newsEventsForm.setOrgContactListSize(String.valueOf(resTOs.size()));
			newsEventsForm.setContactTO(resTOs);
		}
	if (newEventDetail.getNewsEventsParticipants()!= null) {
		Set<NewsEventsParticipants> res = newEventDetail.getNewsEventsParticipants();
		List<NewsEventsParticipantTO> resTOs = new ArrayList<NewsEventsParticipantTO>();
		if (res != null && !res.isEmpty()) {
			Iterator<NewsEventsParticipants> iterator = res.iterator();
			
			while(iterator.hasNext()) {
				NewsEventsParticipants resourse = iterator.next();
				if (resourse != null) {
					NewsEventsParticipantTO resTO = new NewsEventsParticipantTO();
					if (resourse.getId() > 0) {
						resTO.setId(resourse.getId());
					}
					if (resourse.getInstitutionName()!=null && StringUtils.isNotEmpty(resourse.getInstitutionName())) {
						resTO.setInstitutionName(resourse.getInstitutionName());
					}
					
					if (resourse.getNoOfPeople()>0) {
						resTO.setNoOfPeople(String.valueOf(resourse.getNoOfPeople()));
					}
					
					if (resourse.getRemarks()!= null && !resourse.getRemarks().isEmpty()) {
						resTO.setRemarks(resourse.getRemarks());
					}
					resTOs.add(resTO);
				}
			}
		} 
		newsEventsForm.setParticipantsListSize(String.valueOf(resTOs.size()));
		newsEventsForm.setOrgPartListSize(String.valueOf(resTOs.size()));
		
		newsEventsForm.setPartcipantsTO(resTOs);
	}	
	if(newEventDetail.getNewsEventsPhotos() != null && !newEventDetail.getNewsEventsPhotos().isEmpty()){
			Iterator<NewsEventsPhoto> iterator = newEventDetail.getNewsEventsPhotos().iterator();
			List<NewsEventsPhotosTO> photoTOs = new ArrayList<NewsEventsPhotosTO>();

			while (iterator.hasNext()) {
				NewsEventsPhoto photo1 = iterator.next();
				if (photo1 != null && photo1.getIsActive() != null && photo1.getIsActive()) {
					NewsEventsPhotosTO phototo = new NewsEventsPhotosTO();

					if (photo1.getId() > 0) {
						phototo.setId(photo1.getId());
					}
					if (photo1.getPhotoName()!=null && (StringUtils.isNotEmpty(photo1.getPhotoName()))) {
						phototo.setPhotoName(photo1.getPhotoName());
					}
					if (photo1.getNewsEventsId()!= null && photo1.getNewsEventsId().getId()>0) {
						phototo.setNewsEventsId(String.valueOf(photo1.getNewsEventsId().getId()));
					}
					photoTOs.add(phototo);
				}
			}
			newsEventsForm.setPhotoListSize(String.valueOf(photoTOs.size()));
			newsEventsForm.setOrgphotoListSize(String.valueOf(photoTOs.size()));
			newsEventsForm.setPhotosTO(photoTOs);
		}	
}
}
/**
 * @param newsEventsEntryForm
 * @param request 
 * @throws Exception
 */
public void addPhotosToFileSystem(NewsEventsEntryForm newsEventsEntryForm, HttpServletRequest request) throws  Exception{
	if (newsEventsEntryForm.getPhotosTO() != null && !newsEventsEntryForm.getPhotosTO().isEmpty()) {
		List<NewsEventsPhotosTO> images = new ArrayList<NewsEventsPhotosTO>();
		final ImageFilter imageFilter = new ImageFilter();
		Properties prop=new Properties();
		InputStream stream=NewsEventsEntryAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		Iterator<NewsEventsPhotosTO> itr = newsEventsEntryForm.getPhotosTO().iterator();
		while (itr.hasNext()) {
			NewsEventsPhotosTO to = (NewsEventsPhotosTO) itr.next();
			int count=1;
			if ((to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0)) {
				NewsEventsPhotosTO img = new NewsEventsPhotosTO();
				if (to.getId() > 0) {
					img.setId(to.getId());
			}
			if (to.getPhotoFile() != null && to.getPhotoFile().getFileSize()>0) {
				FormFile file = to.getPhotoFile();
				byte[] fileData = file.getFileData();
				String contentType=file.getContentType();
				String fileName=null;
				if(file.getFileName()!=null && !file.getFileName().isEmpty()){
					fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
			   if(imageFilter.accept(fileName)){
				   Long date=new Date().getTime();
			 	  	String dateString=String.valueOf(date);
			    	String newFileName=fileName.replaceAll(fileName, "eventPhoto"+count+dateString+".jpeg");
			    	String filePath=request.getRealPath("");
			    	filePath = filePath + "//TempFiles//";
					File file1 = new File(filePath+newFileName);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				    OutputStream out = new FileOutputStream(file1);
				    byte buffer[] = new byte[1024];
				    int len;
				    while ((len = inputStream.read(buffer)) > 0){
				    	out.write(buffer, 0, len);
				    }
				   out.close();
				   inputStream.close();
				   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadImage.path");
				   destinationPath=destinationPath+"/"+newFileName;
				  //compress Image Start
				   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
			        //showImage("Original Image", i);
			        // Show results with different compression ratio.
			        //compressAndShow(i, 0.5f);
			        float quality=0.2f;
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
			       ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
			        //showImage("Compressed to " + quality + ": " + size + " bytes", out);
			        // Uncomment code below to save the compressed files.
			     //compress Image End
			        File file3 = new File(filePath+"compressed."+newFileName);
			        FileImageOutputStream output = new FileImageOutputStream(file3);
			        writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
				   File file4=new File(destinationPath);
				   FileInputStream fileInputStream=new FileInputStream(file3);
				   byte[] buf = new byte[(int) file3.length()] ;
				   OutputStream outputStream = new FileOutputStream(file4);
					for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
						outputStream.write(buf, 0, readNum); //no doubt here is 0
					}
					img.setPhotoName(newFileName);
			   }
			}
			}
			img.setCreatedBy(newsEventsEntryForm.getUserId());
			img.setCreatedDate(new Date());
			img.setModifiedBy(newsEventsEntryForm.getUserId());
			img.setLastModifiedDate(new Date());
			img.setIsActive(true);
			images.add(img);
			}else if(to.getPhotoName() != null && !to.getPhotoName().trim().isEmpty()){
				NewsEventsPhotosTO img = new NewsEventsPhotosTO();
				if (to.getId() > 0) {
					img.setId(to.getId());
				}
				img.setCreatedBy(newsEventsEntryForm.getUserId());
				img.setCreatedDate(new Date());
				img.setPhotoName(to.getPhotoName());
				img.setModifiedBy(newsEventsEntryForm.getUserId());
				img.setLastModifiedDate(new Date());
				img.setIsActive(true);
				images.add(img);
			}
		}
		newsEventsEntryForm.setPhotosTO(images);
		newsEventsEntryForm.setPhotoListSize(String.valueOf(images.size()));
		for (int i = images.size(); i < 10; i++) {
			NewsEventsPhotosTO resTo1=new NewsEventsPhotosTO();
			images.add(resTo1);
		}
	}
	
}

public void addIconToFileSystem(NewsEventsEntryForm newsEventsForm, HttpServletRequest request,NewsEventsDetails  newsEventsDetails) 
throws  Exception
{		
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		FormFile file = newsEventsForm.getIconTmageFile();
		byte[] fileData = file.getFileData();
		if(file.getFileName()!=null && !file.getFileName().isEmpty()){
		String fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
		String contentType=file.getContentType();
		if(imageFilter.accept(fileName))
		{ 
		Long date=new Date().getTime();
		String dateString=String.valueOf(date);
		String newFileName=fileName.replaceAll(fileName, "IconImage"+dateString+".jpeg");
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+newFileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
	    OutputStream out = new FileOutputStream(file1);
	    byte buffer[] = new byte[1024];
	    int len;
	    while ((len = inputStream.read(buffer)) > 0){
		out.write(buffer, 0, len);
	    }
	   out.close();
	   inputStream.close();
	   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadIcon.path");
	   destinationPath=destinationPath+"/"+newFileName;
	 //compress Image Start
	   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
	    // Show results with different compression ratio.
	   float quality=0.2f;
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
	   ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
	    // Uncomment code below to save the compressed files.
	 //compress Image End
	    File file3 = new File(filePath+"compressed."+newFileName);
	    FileImageOutputStream output = new FileImageOutputStream(file3);
	    writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
	   File file4=new File(destinationPath);
	   FileInputStream fileInputStream=new FileInputStream(file3);
	   byte[] buf = new byte[(int) file3.length()] ;
	   OutputStream outputStream = new FileOutputStream(file4);
		for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
			outputStream.write(buf, 0, readNum); //no doubt here is 0
		}
		newsEventsDetails.setIconImage(newFileName);
		}
	}
}
	
	public void addInvitationToFileSystem(NewsEventsEntryForm newsEventsForm, HttpServletRequest request,NewsEventsDetails  newsEventsDetails) 
	throws  Exception
	{	
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		FormFile file = newsEventsForm.getInvitationMail();
		byte[] fileData = file.getFileData();
		
		if(file.getFileName()!=null && !file.getFileName().isEmpty()){
		String fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
		String contentType=file.getContentType();
		if(imageFilter.accept(fileName))
		{ 
		Long date=new Date().getTime();
		String dateString=String.valueOf(date);
		String newFileName=null;
		if((fileName.endsWith(".txt")) ||(fileName.endsWith(".TXT")) 
				|| (fileName.endsWith(".doc"))|| (fileName.endsWith(".DOC"))
				||(fileName.endsWith(".docx"))||(fileName.endsWith(".DOCX")) 
				||(fileName.endsWith(".xls"))||(fileName.endsWith(".XLS"))
				||(fileName.endsWith(".pdf")) ||(fileName.endsWith(".PDF"))
				|| (fileName.endsWith(".xlsx"))||(fileName.endsWith(".XLSX"))
				|| (fileName.endsWith(".ppt"))||(fileName.endsWith(".PPT"))
				|| (fileName.endsWith(".rtf"))||(fileName.endsWith(".rtf"))){
					
					String newFileName1="InvititationMail"+dateString;
					newFileName=newFileName1.concat(fileName);
				}
		else if((fileName.endsWith(".jpg"))||(fileName.endsWith(".JPG"))
				|| (fileName.endsWith(".jpeg"))||(fileName.endsWith(".JPEG")) 
				||(fileName.endsWith(".gif"))||(fileName.endsWith(".GIF"))
				|| (fileName.endsWith(".bmp"))||(fileName.endsWith(".BMP"))
				|| (fileName.endsWith(".png"))||(fileName.endsWith(".PNG")))
				{
					newFileName=fileName.replaceAll(fileName, "InvititationMail"+dateString+".jpeg");
				}
		else
		{
			if(newsEventsForm.getErrorMsg()!=null && !newsEventsForm.getErrorMsg().isEmpty()){
				
				newsEventsForm.setErrorMsg(newsEventsForm.getErrorMsg()+"Invititation Mail format is not valid");	
			}
			else
			{
				newsEventsForm.setErrorMsg("Invititation Mail format is not valid");
			}
		}
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+newFileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
	    OutputStream out = new FileOutputStream(file1);
	    byte buffer[] = new byte[1024];
	    int len;
	    while ((len = inputStream.read(buffer)) > 0){
		out.write(buffer, 0, len);
	    }
	   out.close();
	   inputStream.close();
	   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadInvitation.path");
	   destinationPath=destinationPath+"/"+newFileName;
	   File file3 = new File(filePath+newFileName);
	   File file4=new File(destinationPath);
	   FileInputStream fileInputStream=new FileInputStream(file3);
	   byte[] buf = new byte[(int) file3.length()] ;
	   OutputStream outputStream = new FileOutputStream(file4);
		for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
			outputStream.write(buf, 0, readNum); 
		}
		newsEventsDetails.setInvitationMail(newFileName);
	}
	}
}
	
	public void addReportToFileSystem(NewsEventsEntryForm newsEventsForm, HttpServletRequest request,NewsEventsDetails  newsEventsDetails) 
	throws  Exception
	{	
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		FormFile file = newsEventsForm.getEventReport();
		byte[] fileData = file.getFileData();
		
		if(file.getFileName()!=null && !file.getFileName().isEmpty()){
		String fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
		String contentType=file.getContentType();
		if(imageFilter.accept(fileName))
		{ 
		Long date=new Date().getTime();
		String dateString=String.valueOf(date);
		String newFileName=null;
		if((fileName.endsWith(".txt")) ||(fileName.endsWith(".TXT")) 
				|| (fileName.endsWith(".doc"))|| (fileName.endsWith(".DOC"))
				||(fileName.endsWith(".docx"))||(fileName.endsWith(".DOCX")) 
				||(fileName.endsWith(".xls"))||(fileName.endsWith(".XLS"))
				||(fileName.endsWith(".pdf")) ||(fileName.endsWith(".PDF"))
				|| (fileName.endsWith(".xlsx"))||(fileName.endsWith(".XLSX"))
				|| (fileName.endsWith(".ppt"))||(fileName.endsWith(".PPT"))
				|| (fileName.endsWith(".rtf"))||(fileName.endsWith(".rtf"))){
					
					String newFileName1="EventReport"+dateString;
					newFileName=newFileName1.concat(fileName);
				}
		else if((fileName.endsWith(".jpg"))||(fileName.endsWith(".JPG"))
				|| (fileName.endsWith(".jpeg"))||(fileName.endsWith(".JPEG")) 
				||(fileName.endsWith(".gif"))||(fileName.endsWith(".GIF"))
				|| (fileName.endsWith(".bmp"))||(fileName.endsWith(".BMP"))
				|| (fileName.endsWith(".png"))||(fileName.endsWith(".PNG")))
				{
					newFileName=fileName.replaceAll(fileName, "EventReport"+dateString+".jpeg");
				}
		else
		{
			if(newsEventsForm.getErrorMsg()!=null && !newsEventsForm.getErrorMsg().isEmpty()){
				
				newsEventsForm.setErrorMsg(newsEventsForm.getErrorMsg()+"Event Report format is not valid");	
			}
			else
			{
				newsEventsForm.setErrorMsg("Event Report format is not valid");
			}
		}
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+newFileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
	    OutputStream out = new FileOutputStream(file1);
	    byte buffer[] = new byte[1024];
	    int len;
	    while ((len = inputStream.read(buffer)) > 0){
		out.write(buffer, 0, len);
	    }
	   out.close();
	   inputStream.close();
	   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadReport.path");
	   destinationPath=destinationPath+"/"+newFileName;
	   File file3 = new File(filePath+newFileName);
	   File file4=new File(destinationPath);
	   FileInputStream fileInputStream=new FileInputStream(file3);
	   byte[] buf = new byte[(int) file3.length()] ;
	   OutputStream outputStream = new FileOutputStream(file4);
		for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
			outputStream.write(buf, 0, readNum); 
		}
		newsEventsDetails.setEventReport(newFileName);
	}
	}
}
	
	public void addMaterialToFileSystem(NewsEventsEntryForm newsEventsForm, HttpServletRequest request,NewsEventsDetails  newsEventsDetails) 
	throws  Exception
	{	
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		FormFile file = newsEventsForm.getMaterialsPublished();
		byte[] fileData = file.getFileData();
		
		if(file.getFileName()!=null && !file.getFileName().isEmpty()){
		String fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
		String contentType=file.getContentType();
		if(imageFilter.accept(fileName))
		{ 
		Long date=new Date().getTime();
		String dateString=String.valueOf(date);
		String newFileName=null;
		if((fileName.endsWith(".txt")) ||(fileName.endsWith(".TXT")) 
				|| (fileName.endsWith(".doc"))|| (fileName.endsWith(".DOC"))
				||(fileName.endsWith(".docx"))||(fileName.endsWith(".DOCX")) 
				||(fileName.endsWith(".xls"))||(fileName.endsWith(".XLS"))
				||(fileName.endsWith(".pdf")) ||(fileName.endsWith(".PDF"))
				|| (fileName.endsWith(".xlsx"))||(fileName.endsWith(".XLSX"))
				|| (fileName.endsWith(".ppt"))||(fileName.endsWith(".PPT"))
				|| (fileName.endsWith(".rtf"))||(fileName.endsWith(".rtf"))){
					
					String newFileName1="MaterialPublished"+dateString;
					newFileName=newFileName1.concat(fileName);
				}
			else if((fileName.endsWith(".jpg"))||(fileName.endsWith(".JPG"))
					|| (fileName.endsWith(".jpeg"))||(fileName.endsWith(".JPEG")) 
					||(fileName.endsWith(".gif"))||(fileName.endsWith(".GIF"))
					|| (fileName.endsWith(".bmp"))||(fileName.endsWith(".BMP"))
					|| (fileName.endsWith(".png"))||(fileName.endsWith(".PNG")))
				{
					newFileName=fileName.replaceAll(fileName, "MaterialPublished"+dateString+".jpeg");
				}
			else
			{
				if(newsEventsForm.getErrorMsg()!=null && !newsEventsForm.getErrorMsg().isEmpty()){
					
					newsEventsForm.setErrorMsg(newsEventsForm.getErrorMsg()+"Material Published format is not valid");	
				}
				else
				{
					newsEventsForm.setErrorMsg("Material Published format is not valid");
				}
			}
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+newFileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
	    OutputStream out = new FileOutputStream(file1);
	    byte buffer[] = new byte[1024];
	    int len;
	    while ((len = inputStream.read(buffer)) > 0){
		out.write(buffer, 0, len);
	    }
	   out.close();
	   inputStream.close();
	   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadMaterialPublished.path");
	   destinationPath=destinationPath+"/"+newFileName;
	   File file3 = new File(filePath+newFileName);
	   File file4=new File(destinationPath);
	   FileInputStream fileInputStream=new FileInputStream(file3);
	   byte[] buf = new byte[(int) file3.length()] ;
	   OutputStream outputStream = new FileOutputStream(file4);
		for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
			outputStream.write(buf, 0, readNum); 
		}
		newsEventsDetails.setMaterialsPublished(newFileName);
		}
	}
}
	public void addRegistrationToFileSystem(NewsEventsEntryForm newsEventsForm, HttpServletRequest request,NewsEventsDetails  newsEventsDetails) 
	throws  Exception
	{	
		Properties prop=new Properties();
		InputStream stream=NewsEventsDetailsHelper.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		final ImageFilter imageFilter = new ImageFilter();
		FormFile file = newsEventsForm.getRegistrationForm();
		byte[] fileData = file.getFileData();
		
		if(file.getFileName()!=null && !file.getFileName().isEmpty()){
		String fileName = checkForSpecialCharsAndGetNewFileName(file.getFileName()); 
		String contentType=file.getContentType();
		if(imageFilter.accept(fileName))
		{ 
		Long date=new Date().getTime();
		String dateString=String.valueOf(date);
		String newFileName=null;
		if((fileName.endsWith(".txt")) ||(fileName.endsWith(".TXT")) 
				|| (fileName.endsWith(".doc"))|| (fileName.endsWith(".DOC"))
				||(fileName.endsWith(".docx"))||(fileName.endsWith(".DOCX")) 
				||(fileName.endsWith(".xls"))||(fileName.endsWith(".XLS"))
				||(fileName.endsWith(".pdf")) ||(fileName.endsWith(".PDF"))
				|| (fileName.endsWith(".xlsx"))||(fileName.endsWith(".XLSX"))
				|| (fileName.endsWith(".ppt"))||(fileName.endsWith(".PPT"))
				|| (fileName.endsWith(".rtf"))||(fileName.endsWith(".rtf"))){
					
					String newFileName1="RegistrationForm"+dateString;
					newFileName=newFileName1.concat(fileName);
				}
		else if((fileName.endsWith(".jpg"))||(fileName.endsWith(".JPG"))
				|| (fileName.endsWith(".jpeg"))||(fileName.endsWith(".JPEG")) 
				||(fileName.endsWith(".gif"))||(fileName.endsWith(".GIF"))
				|| (fileName.endsWith(".bmp"))||(fileName.endsWith(".BMP"))
				|| (fileName.endsWith(".png"))||(fileName.endsWith(".PNG")))				
				{
					newFileName=fileName.replaceAll(fileName, "RegistrationForm"+dateString+".jpeg");
				}
		else
			{
			if(newsEventsForm.getErrorMsg()!=null && !newsEventsForm.getErrorMsg().isEmpty()){
				
				newsEventsForm.setErrorMsg(newsEventsForm.getErrorMsg()+"Registration Form format is not valid");	
			}
			else
			{
				newsEventsForm.setErrorMsg("Registration Form format is not valid");
			}
				
			}
		//String newFileName=fileName.replaceAll(fileName, "RegistrationForm"+dateString+".jpeg");
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//";
		File file1 = new File(filePath+newFileName);
		InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
	    OutputStream out = new FileOutputStream(file1);
	    byte buffer[] = new byte[1024];
	    int len;
	    while ((len = inputStream.read(buffer)) > 0){
		out.write(buffer, 0, len);
	    }
	   out.close();
	   inputStream.close();
	   String destinationPath=prop.getProperty("knowledgepro.admin.mobNewsEventsDetailsNews.uploadRegForm.path");
	   destinationPath=destinationPath+"/"+newFileName;
	   File file3 = new File(filePath+newFileName);
	   File file4=new File(destinationPath);
	   FileInputStream fileInputStream=new FileInputStream(file3);
	   byte[] buf = new byte[(int) file3.length()] ;
	   OutputStream outputStream = new FileOutputStream(file4);
		for (int readNum; (readNum = fileInputStream.read(buf)) != -1;) {
			outputStream.write(buf, 0, readNum); 
		}
		newsEventsDetails.setRegistrationForm(newFileName);
		}
	}
		
}
public File CompressImage(String filePath, String newFileName)throws Exception
{
	 //compress Image Start
	   BufferedImage i = ImageIO.read(new File(filePath+newFileName));
	    // Show results with different compression ratio.
	   float quality=0.2f;
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
	   ByteArrayInputStream in = new ByteArrayInputStream(bos.toByteArray());
	    // Uncomment code below to save the compressed files.
	 //compress Image End
	   	File file3 = new File(filePath+"compressed."+newFileName);
	    FileImageOutputStream output = new FileImageOutputStream(file3);
	    writer.setOutput(output); writer.write(null, new IIOImage(i, null,null), param);
	    return file3;
}

private String checkForSpecialCharsAndGetNewFileName (String originalFileName) {
	String newString=null;
	final String[] splChars = {"#","+","$","&","!","@","%","^","*","(",")","-","=","{","}","[","]",";",":","'","|","?","<",">","~","`"};
	if(originalFileName!=null && !originalFileName.isEmpty()){
	newString = originalFileName;
	for (int i=0; i<splChars.length; i++)
	newString = StringUtils.replace(newString, splChars[i], "");
	}
	return newString;
	}


private  NewsEventsDetails setDataToBO(NewsEventsEntryForm newsEventsForm, NewsEventsDetails mobNewsEventsDetails)
{
	if(newsEventsForm.getAcademicYear()!=null && !newsEventsForm.getAcademicYear().isEmpty()){
		mobNewsEventsDetails.setAcademicYear(newsEventsForm.getAcademicYear());
	}
	if(newsEventsForm.getNewsOrEvents()!=null && !newsEventsForm.getNewsOrEvents().isEmpty()){
		mobNewsEventsDetails.setNewOrEvents(newsEventsForm.getNewsOrEvents());
	}	
	if(newsEventsForm.getEventTitle()!=null && !newsEventsForm.getEventTitle().isEmpty()){
		mobNewsEventsDetails.setEventTitle(newsEventsForm.getEventTitle());
	}
	if(newsEventsForm.getOrganizedBy()!=null && !newsEventsForm.getOrganizedBy().isEmpty()){
		mobNewsEventsDetails.setOrganizedBy(newsEventsForm.getOrganizedBy());
	}
	if(newsEventsForm.getOrganizedBy().equalsIgnoreCase("Course")){
		if(newsEventsForm.getCourseId()!=null && !newsEventsForm.getCourseId().isEmpty()){
		Course cs=new Course();
		cs.setId(Integer.parseInt(newsEventsForm.getCourseId()));
		mobNewsEventsDetails.setCourseId(cs);
		}
	}
	if(newsEventsForm.getOrganizedBy().equalsIgnoreCase("Department")){
		if(newsEventsForm.getDepartmentId()!=null && !newsEventsForm.getDepartmentId().isEmpty()){
		Department ds=new Department();
		ds.setId(Integer.parseInt(newsEventsForm.getDepartmentId()));
		mobNewsEventsDetails.setDeptId(ds);
		}
	}
	if(newsEventsForm.getOrganizedBy().equalsIgnoreCase("Deanery")){
		if(newsEventsForm.getStreamId()!=null && !newsEventsForm.getStreamId().isEmpty()){
		EmployeeStreamBO ss=new EmployeeStreamBO();
		ss.setId(Integer.parseInt(newsEventsForm.getStreamId()));
		mobNewsEventsDetails.setStreamId(ss);
		}
	}
	if(newsEventsForm.getOrganizedBy().equalsIgnoreCase("Special Centers")){
		if(newsEventsForm.getSplCenterId()!=null && !newsEventsForm.getSplCenterId().isEmpty()){
		Department cs=new Department();
		cs.setId(Integer.parseInt(newsEventsForm.getSplCenterId()));
		mobNewsEventsDetails.setSplCentre(cs);
		}
	}
	if(newsEventsForm.getEventDescription()!=null && !newsEventsForm.getEventDescription().isEmpty()){
	mobNewsEventsDetails.setEventDescription(newsEventsForm.getEventDescription());}
	
	if(newsEventsForm.getDateFrom()!=null && !newsEventsForm.getDateFrom().isEmpty()){
	mobNewsEventsDetails.setDateFrom(CommonUtil.ConvertStringToDate(newsEventsForm.getDateFrom()));
	}
	if(newsEventsForm.getDateTo()!=null && !newsEventsForm.getDateTo().isEmpty()){
	mobNewsEventsDetails.setDateTo(CommonUtil.ConvertStringToDate(newsEventsForm.getDateTo()));}

	if(newsEventsForm.getParticipants()!=null && !newsEventsForm.getParticipants().isEmpty()){
		mobNewsEventsDetails.setParticipants(newsEventsForm.getParticipants());
	}
	if(newsEventsForm.getViewFor()!=null && !newsEventsForm.getViewFor().isEmpty()){
		mobNewsEventsDetails.setViewFor(newsEventsForm.getViewFor());
	}
	if(newsEventsForm.getDisplayFromDate()!=null && !newsEventsForm.getDisplayFromDate().isEmpty()){
		mobNewsEventsDetails.setDisplayFromDate(CommonUtil.ConvertStringToDate(newsEventsForm.getDisplayFromDate()));
	}
	if(newsEventsForm.getDisplayToDate()!=null && !newsEventsForm.getDisplayToDate().isEmpty()){
		mobNewsEventsDetails.setDisplayToDate(CommonUtil.ConvertStringToDate(newsEventsForm.getDisplayToDate()));
	}
	if(newsEventsForm.getEventWebPosition()!=null && !newsEventsForm.getEventWebPosition().isEmpty()){
		mobNewsEventsDetails.setEventWebPosition(newsEventsForm.getEventWebPosition());
	}
	if(newsEventsForm.getNewsWebPosition()!=null && !newsEventsForm.getNewsWebPosition().isEmpty()){
		mobNewsEventsDetails.setNewsWebPosition(newsEventsForm.getNewsWebPosition());
	}
	if(newsEventsForm.getSummary()!=null && !newsEventsForm.getSummary().isEmpty()){
		mobNewsEventsDetails.setSummary(newsEventsForm.getSummary());
	}
	if(newsEventsForm.getCategoryId()!=null && !newsEventsForm.getCategoryId().isEmpty()){
		MobNewsEventsCategory mob=new MobNewsEventsCategory();
		mob.setId(Integer.parseInt(newsEventsForm.getCategoryId()));
		mobNewsEventsDetails.setCategoryId(mob);
	}
	return mobNewsEventsDetails;
}


}
