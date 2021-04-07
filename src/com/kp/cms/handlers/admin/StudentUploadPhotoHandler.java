package com.kp.cms.handlers.admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.MimetypesFileTypeMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admin.StudentUploadPhotoAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.MobileMessaging;
import com.kp.cms.bo.admin.SMSTemplate;
import com.kp.cms.bo.admin.StudentUploadPhoto;
import com.kp.cms.bo.studentLogin.StudentPhotoUpload;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.StudentUploadPhotoForm;
import com.kp.cms.helpers.admin.StudentUploadPhotoHelper;
import com.kp.cms.to.admin.StudentUploadPhotoTO;
import com.kp.cms.transactions.admin.IStudentUploadPhotoTransaction;
import com.kp.cms.transactionsimpl.admin.StudentUploadPhotoTransactionImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.PropertyUtil;
import com.kp.cms.utilities.jms.MailTO;

public class StudentUploadPhotoHandler {
	private static final Log log = LogFactory.getLog(StudentUploadPhotoHandler.class);
    	public static volatile StudentUploadPhotoHandler studentUploadPhotoHandler = null;

	public static StudentUploadPhotoHandler getInstance() {
		if (studentUploadPhotoHandler == null) {
			studentUploadPhotoHandler = new StudentUploadPhotoHandler();
			return studentUploadPhotoHandler;
		}
		return studentUploadPhotoHandler;
	}
	
	/**
	 * @param studentUploadPhotoForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	
	public boolean uploadPhotos(StudentUploadPhotoForm studentUploadPhotoForm,HttpServletRequest request) throws Exception{
		List<StudentUploadPhotoTO> studentUploadPhotoTOList=new ArrayList<StudentUploadPhotoTO>();
		StudentUploadPhotoTO studentUploadPhotoTO;
		String relativePath;
		boolean flag=false;
		
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        String path=prop.getProperty("knowledgepro.student.upload.image.path");
        String year=studentUploadPhotoForm.getYear();
       // String  s1="C:/studentPhotos";
       // relativePath=s1+"/"+year;
        relativePath=path+year;
        File f = new File(relativePath);
        FileInputStream fis=null;
        System.out.println("relativePath********" + relativePath );
        try{
        	File[] filearr =  f.listFiles();
            log.error("relativePath********" + relativePath );
            log.error("filearr.length   " + filearr.length);
            if(filearr.length!=0){
        		int maxPhotoSize = 0;
        		maxPhotoSize = Integer.parseInt(prop.getProperty(CMSConstants.MAX_UPLOAD_PHOTOSIZE_KEY));
        		for(int j=0;j<filearr.length;j++){
    	   			File f3=filearr[j];
    	   			if(maxPhotoSize > f3.length()){
    	   				studentUploadPhotoTO= new StudentUploadPhotoTO();
    	   				byte[] b = new byte[(int)f3.length ()];
    	   				fis=new FileInputStream(f3);
    	   				// convert image into byte array
    	   				fis.read(b);
    	   				studentUploadPhotoTO.setContentType(new MimetypesFileTypeMap().getContentType(f3));
    	   				studentUploadPhotoTO.setCreatedDate(new Date());
    	   				studentUploadPhotoTO.setDoc(b);
    	   				studentUploadPhotoTO.setIsActive(true);
    	   				studentUploadPhotoTO.setFileName(f3.getName());
    	   				studentUploadPhotoTO.setLastModifiedDate(new Date());
    	   				studentUploadPhotoTO.setCreatedBy(studentUploadPhotoForm.getUserId());
    	   				if(studentUploadPhotoForm.getIsApplicationNo().equalsIgnoreCase("ApplicationNo")){
    	   					studentUploadPhotoTO.setApplnNo(Integer.parseInt(removeFileExtension(f3.getName())));
    	   				}else{
    	   					studentUploadPhotoTO.setRegNo(removeFileExtension(f3.getName()));
    	   				}
    	   				studentUploadPhotoTOList.add(studentUploadPhotoTO);
    	   			}
    	   			
        		}
    	        log.error(studentUploadPhotoTOList + "studentUploadPhotoTOList" + "size" + studentUploadPhotoTOList.size());
    	   		
    	       int uploadPhotos=uploadStudentPhotos1(studentUploadPhotoTOList, year, studentUploadPhotoForm);
    	       log.error(uploadPhotos + "uploadPhotos");
    	       IStudentUploadPhotoTransaction iStudentUploadPhotoTransaction=StudentUploadPhotoTransactionImpl.getInstance();
    	       
    	       if (uploadPhotos==studentUploadPhotoTOList.size()) {
    				// success .
    	    	   File f1 = new File(relativePath);
    		       File[] filearr1 =  f1.listFiles();
    		       for(int j=0;j<filearr1.length;j++){
    		   		File f3=filearr[j];
    		   		if(maxPhotoSize > f3.length()){
    		   			f3.delete();		   		
    		   		}
    		      }
    		       flag=true;
    		    }else{
    				// success .
    		    	   File f1 = new File(relativePath);
    			       File[] filearr1 =  f1.listFiles();
    			       for(int j=0;j<filearr1.length;j++){
    			   		File f3=filearr[j];
    			   		AdmAppln admAppln =  iStudentUploadPhotoTransaction.validateAdmAppln(removeFileExtension(f3.getName()), studentUploadPhotoForm.getYear());
    			   		if(admAppln != null){
    			   			if(maxPhotoSize > f3.length()){
    			   				f3.delete();			   		
    			   			}
    			   		}
    			      }
    			       flag=true;
    			    
    	    	   
    	       }
           }
            else{
            	throw new NullPointerException();
            }
        }catch(Exception exception){
        	log.error("Error occured in uploadStudentPhotos of StudentUploadPhotoHandler", exception);
        }finally{
        	if(fis!=null)
        	    fis.close();
        }
       return flag;
}
	
	/**
	 * @param studentUploadPhotoTOList
	 * to insert images in to the database
	 * @param studentUploadPhotoForm 
	 * @throws Exception
	 */
	public int uploadStudentPhotos1(List<StudentUploadPhotoTO> studentUploadPhotoTOList, String year, StudentUploadPhotoForm studentUploadPhotoForm) throws Exception{
		List<StudentUploadPhoto> studentUploadPhotoBOList=StudentUploadPhotoHelper.getInstance().copyAdmittedThroughTosToBos(studentUploadPhotoTOList, studentUploadPhotoForm);
		log.error("studentUploadPhotoBOList" + studentUploadPhotoBOList.size());
		IStudentUploadPhotoTransaction iStudentUploadPhotoTransaction=StudentUploadPhotoTransactionImpl.getInstance();
		return iStudentUploadPhotoTransaction.uploadStudentPhotos(studentUploadPhotoBOList, year, studentUploadPhotoForm);
	}
	/**
	 * to remove the file extension
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return null;
	}

	
	/**
	 * @param studentUploadPhotoForm
	 * @param studentId
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
public boolean uploadFinalYearPhotos(StudentUploadPhotoForm studentUploadPhotoForm,String studentId) throws Exception{
		IStudentUploadPhotoTransaction iStudentUploadPhotoTransaction=StudentUploadPhotoTransactionImpl.getInstance();
		StudentPhotoUpload photoUpload=iStudentUploadPhotoTransaction.checkStudentPhotoIsApprovedOrRejected(studentId);
		if(photoUpload!=null){
			    FileOutputStream fos = new FileOutputStream(CMSConstants.FINAL_YEAR_STUDENT_PHOTO_FOLDER_PATH+studentId+".jpg");
			    fos.write(studentUploadPhotoForm.getPhoto());
			    fos.close();
              	photoUpload.setIsRejected(false);
              	photoUpload.setReasonReject(null);
              	photoUpload.setIsApproved(false);
              	photoUpload.setModifiedBy(studentUploadPhotoForm.getUserId());
              	photoUpload.setLastModifiedDate(new Date());
		}else{
			photoUpload=StudentUploadPhotoHelper.getInstance().createStudentPhotoUploadBO(studentUploadPhotoForm,studentId);
		}
		return iStudentUploadPhotoTransaction.uploadFinalYearStudentPhoto(photoUpload);
}

	/**
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public Map<Integer, String> getClassesFromStudentPhotoUpload()throws Exception {
		Map<Integer, String> photoClassMap=new HashMap<Integer, String>();
		IStudentUploadPhotoTransaction iStudentUploadPhotoTransaction=StudentUploadPhotoTransactionImpl.getInstance();
		List<Object[]> list=iStudentUploadPhotoTransaction.getClassesFromStudentUploadPhotos();
		if(list!=null && !list.isEmpty()){
			for (Object[] objects : list) {
				if(objects[0].toString()!=null && !objects[0].toString().isEmpty()
						&& objects[1].toString()!=null && !objects[1].toString().isEmpty()){
					photoClassMap.put(Integer.parseInt(objects[0].toString()), objects[1].toString());		
			
			}
		 }
		}
		return photoClassMap;
   }

	/**
	 * @param studentUploadPhotoForm
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public List<StudentUploadPhotoTO> viewFinalYearStudentPhotos(StudentUploadPhotoForm studentUploadPhotoForm)throws Exception {
		IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		List<StudentUploadPhotoTO> uploadPhotoTOList=new ArrayList<StudentUploadPhotoTO>();
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        String path=prop.getProperty("knowledgepro.final.year.student.image.path");
        String path1=prop.getProperty("knowledgepro.student.image.path");
        FileInputStream fis=null;
        List<StudentPhotoUpload> studentList=transaction.getStudentIdByClassId(studentUploadPhotoForm);
        	if(studentList!=null && !studentList.isEmpty()){
        		File f = new File(path);
                File[] filearr =  f.listFiles();
                if(filearr!=null && filearr.length!=0){
	                if(filearr.length!=0){
	                	for(int j=0;j<filearr.length;j++){
	        	   			File f3=filearr[j];
	        	   			StudentUploadPhotoTO to=null;
	        	   			for (StudentPhotoUpload upload : studentList) {
								if(String.valueOf(upload.getStudent().getId()).equalsIgnoreCase(removeFileExtension(f3.getName()))){
									to=new StudentUploadPhotoTO();
									File f4 = new File(path1);
					                File[] filearr1 =  f4.listFiles();
					                if(filearr1!=null && filearr1.length!=0){
						                if(filearr1.length!=0){
						                	for(int k=0;k<filearr1.length;k++){
						        	   			File f5=filearr1[k];
						        	   			if(String.valueOf(upload.getStudent().getId()).equalsIgnoreCase(removeFileExtension(f5.getName()))){
						        	   				to.setOldFileName(f5.getName());
						        	   			}
						                	}
						                }
					                }
									byte[] b = new byte[(int)f3.length ()];
									fis=new FileInputStream(f3);
			    	   				// convert image into byte array
			    	   				fis.read(b);
									to.setFileName(f3.getName());
									to.setStudentId(String.valueOf(upload.getStudent().getId()));
									to.setContentType(new MimetypesFileTypeMap().getContentType(f3));
									to.setDoc(b);
									if(upload.getStudent().getRegisterNo()!=null && !upload.getStudent().getRegisterNo().isEmpty()){
										to.setRegNo(upload.getStudent().getRegisterNo());	
									}
									if(upload.getStudent().getAdmAppln()!=null){
										if(upload.getStudent().getAdmAppln().getPersonalData()!=null){
											if(upload.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null
													&& !upload.getStudent().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
												to.setStudentName(upload.getStudent().getAdmAppln().getPersonalData().getFirstName());
											}
										}
									}if(upload.getStudent().getClassSchemewise()!=null){
										if(upload.getStudent().getClassSchemewise().getClasses()!=null){
											if(upload.getStudent().getClassSchemewise().getClasses().getName()!=null
													&& !upload.getStudent().getClassSchemewise().getClasses().getName().isEmpty()){
												to.setStudentClass(upload.getStudent().getClassSchemewise().getClasses().getName());
											}
										}
									}
								}
							}
	        	   			if(to!=null){
	        	   			uploadPhotoTOList.add(to);
	        	   			}
	                	}
	                }
                } 
        	}
        	if(fis!=null)
        	    fis.close();
        	studentUploadPhotoForm.setLengthOfStudentPhotos(uploadPhotoTOList.size());
			return uploadPhotoTOList;
	}

	/**
	 * @param uploadPhotoForm
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public boolean approveFinalYearStudentPhotos(StudentUploadPhotoForm uploadPhotoForm)throws Exception {
		IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		return transaction.updateFinalYearStudentPhotos(uploadPhotoForm);
	}

	/**
	 * @param uploadPhotoForm
	 * @return
	 * added  by mahi
	 * @throws Exception
	 */
	public boolean rejectFinalYearStudentPhotos(StudentUploadPhotoForm uploadPhotoForm)throws Exception {
		IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		List<Integer> studentIdList=new ArrayList<Integer>();
		List<StudentPhotoUpload> photoUploadList=new ArrayList<StudentPhotoUpload>();
		for (StudentUploadPhotoTO photoTO : uploadPhotoForm.getUploadPhotoTOList()) {
			 if(photoTO.getChecked()!=null && photoTO.getChecked().equalsIgnoreCase("on")){
				 StudentPhotoUpload upload=transaction.getStudentPhotoUploadByStudentId(Integer.parseInt(photoTO.getStudentId()));
				 if(upload!=null){
					 studentIdList.add(upload.getStudent().getId());
					 upload.setIsRejected(true);
					 upload.setModifiedBy(uploadPhotoForm.getUserId());
					 upload.setLastModifiedDate(new Date());
					 upload.setReasonReject(uploadPhotoForm.getRejectedReason());
					 photoUploadList.add(upload);
				 }
			 }
			
		}
		if(!studentIdList.isEmpty()){
			uploadPhotoForm.setStudentIdList(studentIdList);
		}
		 return transaction.updateStudentPhotoUploadList(photoUploadList);
	}

	/**
	 * @return
	 * added by mahi
	 * @throws Exception
	 */
	public List<StudentUploadPhotoTO> getRejectedStudentPhotos()throws Exception {
		IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		List<StudentUploadPhotoTO> rejectedPhotoList=new ArrayList<StudentUploadPhotoTO>();
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        String path=prop.getProperty("knowledgepro.final.year.student.image.path");
        FileInputStream fis=null;
		List<StudentPhotoUpload> rejectedPhotos=transaction.getRejectedStudentPhotos();
		if(rejectedPhotos!=null && !rejectedPhotos.isEmpty()){
    		File f = new File(path);
            File[] filearr =  f.listFiles();
            if(filearr.length!=0){
            	for(int j=0;j<filearr.length;j++){
    	   			File f3=filearr[j];
    	   			StudentUploadPhotoTO to=null;
    	   			for (StudentPhotoUpload upload : rejectedPhotos) {
						if(String.valueOf(upload.getStudent().getId()).equalsIgnoreCase(removeFileExtension(f3.getName()))){
							to=new StudentUploadPhotoTO();
							byte[] b = new byte[(int)f3.length ()];
							fis=new FileInputStream(f3);
	    	   				// convert image into byte array
	    	   				fis.read(b);
							to.setFileName(f3.getName());
							to.setStudentId(String.valueOf(upload.getStudent().getId()));
							to.setContentType(new MimetypesFileTypeMap().getContentType(f3));
							to.setDoc(b);
							if(upload.getStudent().getRegisterNo()!=null && !upload.getStudent().getRegisterNo().isEmpty()){
								to.setRegNo(upload.getStudent().getRegisterNo());	
							}
							to.setRejectedReason(upload.getReasonReject());
							if(upload.getStudent().getAdmAppln()!=null){
								if(upload.getStudent().getAdmAppln().getPersonalData()!=null){
									if(upload.getStudent().getAdmAppln().getPersonalData().getFirstName()!=null
											&& !upload.getStudent().getAdmAppln().getPersonalData().getFirstName().isEmpty()){
										to.setStudentName(upload.getStudent().getAdmAppln().getPersonalData().getFirstName());
									}
								}
							}if(upload.getStudent().getClassSchemewise()!=null){
								if(upload.getStudent().getClassSchemewise().getClasses()!=null){
									if(upload.getStudent().getClassSchemewise().getClasses().getName()!=null
											&& !upload.getStudent().getClassSchemewise().getClasses().getName().isEmpty()){
										to.setStudentClass(upload.getStudent().getClassSchemewise().getClasses().getName());
									}
								}
							}
						}
					}
    	   			if(to!=null){
    	   				rejectedPhotoList.add(to);
    	   			}
            	}
            }
    	}
		if(fis!=null)
    	    fis.close();
		return rejectedPhotoList;
	}

	/**
	 * @param studentId
	 * @return
	 * Added by Mahi
	 * @throws Exception
	 */
	public String checkStudentPhotoIsApprovedOrRejected(String studentId,StudentUploadPhotoForm photoForm) throws Exception {
		IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		Properties prop = new Properties();
		InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
        prop.load(in);
        String path=prop.getProperty("knowledgepro.final.year.student.image.path");
		StudentPhotoUpload photoUpload=transaction.checkStudentPhotoIsApprovedOrRejected(studentId);
		String uploadPhotoDetails=null;
		if(photoUpload!=null){
			File f = new File(path);
            File[] filearr =  f.listFiles();
            if(filearr!=null && filearr.length!=0){
	            if(filearr.length!=0){
	            	for(int j=0;j<filearr.length;j++){
	    	   			File f3=filearr[j];
	    	   			if(String.valueOf(photoUpload.getStudent().getId()).equalsIgnoreCase(removeFileExtension(f3.getName()))){
	    	   				photoForm.setStudentImageName(f3.getName());
	    	   			}
	            	}
	            }
            }
			if(photoUpload.getIsApproved()){
				uploadPhotoDetails="Approved";
				photoForm.setRejectedReason("Photo Status : Approved");
			}else if(photoUpload.getIsRejected()){
				photoForm.setRejectedReason("Photo Status : Rejected<br> Reason : "+photoUpload.getReasonReject());
				uploadPhotoDetails="Rejected";
			}else {
				uploadPhotoDetails="Pending";
				photoForm.setRejectedReason("Photo Status : Under Review");
			}
		}
		return uploadPhotoDetails;
	}

	/**
	 * Added by mahi
	 * @param uploadPhotoForm
	 * @throws Exception 
	 */
	public boolean sendMailAndSmsToStudents(StudentUploadPhotoForm uploadPhotoForm,String approvedOrRejected) throws Exception {
		    IStudentUploadPhotoTransaction transaction=StudentUploadPhotoTransactionImpl.getInstance();
		    String senderNumber=CMSConstants.SMS_SENDER_NUMBER;
			String senderName=CMSConstants.SMS_SENDER_NAME;
			Properties prop = new Properties();
			boolean sentMail=false;
			boolean sentSms=false;
			boolean finalSent=false;
			InputStream in = StudentUploadPhotoAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	        prop.load(in);
			String sendSms= prop.getProperty("knowledgepro.sms.send");
			String desc="";
			String adminmail=CMSConstants.MAIL_USERID;
			String sendMail= prop.getProperty("knowledgepro.mail.send");
			String fromName=prop.getProperty("knowledgepro.admission.studentmail.fromName");
			MailTO mailto = new MailTO();
			String desc1 ="";
			if(sendSms!=null && sendSms.equalsIgnoreCase("true") && sendMail!=null && sendMail.equalsIgnoreCase("true")){
			SMSTemplateHandler temphandle=SMSTemplateHandler.getInstance();
			List<MobileMessaging> smsListBos=new ArrayList<MobileMessaging>();
			TemplateHandler mailTemplate=TemplateHandler.getInstance();
			List<SMSTemplate> smsTemplate= null;
			List<GroupTemplate> mailTemplateList= null;
			if(approvedOrRejected.equalsIgnoreCase("Approved")){
				smsTemplate= temphandle.getDuplicateCheckList(0,"Photo Upload Student Login-Approval");
				mailTemplateList= mailTemplate.getDuplicateCheckList(0,"Photo Upload Student Login-Approval");
			}else{
				smsTemplate= temphandle.getDuplicateCheckList(0,"Photo Upload Student Login-Rejection");
				mailTemplateList= mailTemplate.getDuplicateCheckList(0,"Photo Upload Student Login-Rejection");
			}
			
	        for (Integer studentId : uploadPhotoForm.getStudentIdList()) {
	        	//sending sms start
	        	 StudentPhotoUpload photoUpload=transaction.checkStudentPhotoIsApprovedOrRejected(String.valueOf(studentId));
	        	String mobileNo="";
	        	if(smsTemplate != null && !smsTemplate.isEmpty()) {
	        	desc = smsTemplate.get(0).getTemplateDescription();
	        	}
	        	if(photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1()!=null && !photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1().isEmpty()){
	        		if(photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0091") || photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("+91")
	        				|| photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("091") || photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1().trim().equals("0"))
	        			mobileNo = "91";
	        		else
	        			mobileNo=photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo1();
	        	}else{
	        		mobileNo="91";
	        	}
	        	if(photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo2()!=null && !photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo2().isEmpty()){
	        		mobileNo=mobileNo+photoUpload.getStudent().getAdmAppln().getPersonalData().getMobileNo2();
	        	}
	        desc=desc.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, photoUpload.getStudent().getRegisterNo());
	        desc=desc.replace(CMSConstants.TEMPLATE_STUDENT_NAME, photoUpload.getStudent().getAdmAppln().getPersonalData().getFirstName());
	        if(!approvedOrRejected.equalsIgnoreCase("Approved")){
	        	desc=desc.replace(CMSConstants.TEMPLATE_REJECTED_REASON, photoUpload.getReasonReject());	
	        }
	        if(StringUtils.isNumeric(mobileNo) && (mobileNo.length()==12 && desc.length()<=160)){
	        	MobileMessaging mob=new MobileMessaging();
	        	mob.setDestinationNumber(mobileNo);
	        	mob.setMessageBody(desc);
	        	mob.setMessagePriority(3);
	        	mob.setSenderName(senderName);
	        	mob.setSenderNumber(senderNumber);
	        	mob.setMessageEnqueueDate(new Date());
	        	mob.setIsMessageSent(false);
	        	smsListBos.add(mob);
	        }
	        //end sms send
	        //start send mail
		        String mailsId=photoUpload.getStudent().getAdmAppln().getPersonalData().getEmail();
				if(mailTemplateList != null && !mailTemplateList.isEmpty()) {
				desc1 = mailTemplateList.get(0).getTemplateDescription();
				desc1=desc1.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO, photoUpload.getStudent().getRegisterNo());
				desc1=desc1.replace(CMSConstants.TEMPLATE_STUDENT_NAME, photoUpload.getStudent().getAdmAppln().getPersonalData().getFirstName());
				if(!approvedOrRejected.equalsIgnoreCase("Approved")){
			        	desc1=desc1.replace(CMSConstants.TEMPLATE_REJECTED_REASON, photoUpload.getReasonReject());	
			    }
				mailto.setFromAddress(adminmail);
				mailto.setFromName(fromName);
				mailto.setMessage(desc1);
				if(approvedOrRejected.equalsIgnoreCase("Approved")){
				    mailto.setSubject("Photo Upload Approval");
				}else{
					mailto.setSubject("Photo Upload Rejection");
				}
				mailto.setToAddress(mailsId);
				sentMail=CommonUtil.sendMail(mailto);
			//end mail
		   }
	    }
	        sentSms=PropertyUtil.getInstance().saveSMSList(smsListBos);
	}
			if(sentMail && sentSms){
				finalSent=true;
			}
			return finalSent;
 }
}
