package com.kp.cms.actions.exam;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
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
import com.kp.cms.actions.admission.SmartCardNumberUploadAction;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.UploadBlockListForHallticketOrMarkscardForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.ExamAssignStudentsToRoomHandler;
import com.kp.cms.handlers.exam.ExamMarksEntryHandler;
import com.kp.cms.handlers.exam.UploadBlockListForHallticketOrMarkscardHandler;
import com.kp.cms.to.exam.UploadBlockListForHallticketOrMarkscardTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class UploadBlockListForHallticketOrMarkscardAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadBlockListForHallticketOrMarkscardAction.class);
	
	public ActionForward initUploadBlockListForHallticketOrMarkscard(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadBlockListForHallticketOrMarkscardForm objform = (UploadBlockListForHallticketOrMarkscardForm) form;
	//	objform.setListExamName(handler.getExamNameList());
		
		objform.resetFields();
		setExamNameToForm(objform);
/*		int examId = 0;
		if (objform.getListExamName() != null
				&& objform.getListExamName().size() != 0) {
			KeyValueTO to = objform.getListExamName().get(0);
			examId = to.getId();
		}*/
		if(objform.getExamId()!=null && objform.getExamId().length()>0){
			objform.setExamName(objform.getExamId());
		}
	
		return mapping.findForward(CMSConstants.UPLOAD_EXAM_BLOCK_UNBLOCK);
	}
	
	private void setExamNameToForm(UploadBlockListForHallticketOrMarkscardForm objform) throws Exception{
		
		int year=0;
		year=CurrentAcademicYear.getInstance().getAcademicyear();
		if(objform.getYear()!=null && !objform.getYear().isEmpty()){
			year=Integer.parseInt(objform.getYear());
		}
		if(year==0){
			Calendar calendar = Calendar.getInstance();
			year = calendar.get(Calendar.YEAR);
		}
		ExamAssignStudentsToRoomHandler examAssignStudenthandler = new ExamAssignStudentsToRoomHandler();
		objform.setExamTypeList((HashMap<Integer, String>) examAssignStudenthandler
				.getExamTypeList());
		if(objform.getExamType()==null || objform.getExamType().trim().isEmpty())
		objform.setExamType("1");
		
		Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),year); 
		objform.setExamNameMap(examMap);
		
		ExamMarksEntryHandler examhandler = new ExamMarksEntryHandler();
		String currentExam=examhandler.getCurrentExamName(objform.getExamType());
		if(currentExam!=null){
			objform.setExamName(currentExam);
		}
		
		
	}
	
	public ActionForward uploadExcelSheet(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
	
	log.info("Entered UploadSmartCardNumber input");
	UploadBlockListForHallticketOrMarkscardForm objform = (UploadBlockListForHallticketOrMarkscardForm) form;
	ActionMessages messages=new ActionMessages();
	setUserId(request, objform);
	ActionErrors errors=objform.validate(mapping, request);
	objform.reset();
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
		objform.resetFields();
		setExamNameToForm(objform);
		if(objform.getExamId()!=null && objform.getExamId().length()>0){
			objform.setExamName(objform.getExamId());
		}
		return mapping.findForward(CMSConstants.UPLOAD_EXAM_BLOCK_UNBLOCK);
	}
	/*String formname=request.getParameter(CMSConstants.FORMNAME);
	List umRegNoList=new ArrayList();*/
	String str= "";
	String str2="Duplicate entry found for the following register numbers ";
	String str3="";
	String str4="Please check and reupload:";
	String str5="Class not found:";
	String str6="Block list is already uploaded for the following register numbers";
	String str7="";
	String str8="File uploaded successfully except the following register numbers";
	try{
		if(objform.getThefile() == null){
			ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);
    		return mapping.findForward(CMSConstants.UPLOAD_EXAM_BLOCK_UNBLOCK);
		}
		FormFile myFile = objform.getThefile();
	    String contentType = myFile.getContentType();
	    String fileName    = myFile.getFileName();
	   	File file = null;
	    Properties prop = new Properties();
	    InputStream stream = SmartCardNumberUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	    prop.load(stream);
	    List<UploadBlockListForHallticketOrMarkscardTo> results = null;
		if ("2".equals(objform.getTypeId())) {
			objform.setType("M");
		} else {
			objform.setType("H");
		}
	    //if the uploading file is excel file
	    if(fileName.endsWith(".xls")){
	    	String year=objform.getYear();
	    	Map<String,Integer> map=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getClassIdByClassNameAndYear(year);
	    	Map<String,Integer> registerNumMap=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getStudentIdByStudentRegNum(year);
	    	byte[] fileData = myFile.getFileData();
	    	String source1=prop.getProperty(CMSConstants.Upload_Block_Hallticket_Markscard);
	    	String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File file1 = new File(filePath+source1);
			InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
		OutputStream out = new FileOutputStream(file1);
		byte buffer[] = new byte[1024];
		int len;
		while ((len = inputStream.read(buffer)) > 0){
			out.write(buffer, 0, len);
		}
		out.close();
		inputStream.close();
		String source=prop.getProperty(CMSConstants.Upload_Block_Hallticket_Markscard);
		file = new File(filePath+source);
		POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
	    HSSFWorkbook workbook = new HSSFWorkbook(system);
	    HSSFSheet sheet = workbook.getSheetAt(0);
	    HSSFRow row;
	    HSSFCell cell;
	    int rows = sheet.getPhysicalNumberOfRows();
	    int cols = 0; // No of columns
	    int tmp = 0;
	    // This trick ensures that we get the data properly even if it doesn't start from first few rows
	    for(int i = 0; i < rows; i++) {
	        row = sheet.getRow(i);
	        if(row != null) {
	            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
	            if(tmp > cols) {
		            cols = tmp;
		            break;
	            }
	        }
	    }
	    boolean isAdded = false;
	    results= new ArrayList<UploadBlockListForHallticketOrMarkscardTo>();
	    List<String> dupRegNumber=new ArrayList();
	    for(int r = 1; r < rows; r++) {
	    	int count=0;
	    	
	        row = sheet.getRow(r);
	        UploadBlockListForHallticketOrMarkscardTo uploadTo = new UploadBlockListForHallticketOrMarkscardTo();
	        if(row != null) {
	            for(int c = 0; c < cols;c++) {
	                cell = row.getCell((byte)c);
	                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	                	 String cell2=cell.toString();
						if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell2)){
						if(map.containsKey((cell2.trim()))){
                   				uploadTo.setClassId( map.get(cell2.trim()));
								}else{
										String str1= cell2.trim();
										str5=str5+str1+",";
										objform.setFlag1(true);
										break;
									  }
						}
						if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
							if(!dupRegNumber.contains(cell2.trim())){
								if(registerNumMap.containsKey(removeFileExtension(cell2.trim()))){
									uploadTo.setStudentId(registerNumMap.get(removeFileExtension(cell2.trim())));
									dupRegNumber.add((cell2.trim()));
								}else{
									String str1 = removeFileExtension(cell2.trim());
									str=str+str1+",";
									//objform.setFlag3(true);
									break;
								}
				             }else{
				            	 String str1 = removeFileExtension(cell2.trim());
				            	 str3=str3+ str1+",";
				            	 objform.setFlag(true);
				             	}
						}
						if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
							uploadTo.setBlockReason(cell2.trim());
						}
	                }else{
	                	if(c!=0 && c!=1){
	                	 cell = row.getCell((byte)(c-1));
	                	 String str1 = removeFileExtension(cell.toString().trim());
	                	 str=str+str1+",";
	                	 //objform.setFlag3(true);
	                	}
	                }
	                if(cell.getCellNum() == 2 ){
	                	if(uploadTo!=null && uploadTo.getClassId()!=0 && uploadTo.getStudentId()!=0 && uploadTo.getBlockReason()!=null){
	                	ExamBlockUnblockHallTicketBO examBo=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getExamBo(uploadTo,objform);
	                	if(examBo!=null){
	                		String sentence= examBo.getBlockReason();
	                		String[] words = sentence.split(",");  
	                		for (String word : words)  
	                		{  
	                			if(uploadTo.getBlockReason().equalsIgnoreCase(word)){
	                				count++;
	                				cell = row.getCell((byte)(c-1));
	                				String str1 = removeFileExtension(cell.toString().trim());
	                				str7=str7+str1+",";
	                				objform.setFlag2(true);
	                			}
	        			  }
	                	}
	                  }
	                }
                } 
            if(uploadTo!=null && uploadTo.getClassId()!=0 && uploadTo.getStudentId()!=0 && uploadTo.getBlockReason()!=null){
    			if(count==0){
            	uploadTo.setType(objform.getType());
	            uploadTo.setExamId(objform.getExamName());
	            uploadTo.setHallTktOrMarksCard(objform.getType());
	            	results.add(uploadTo);
	            }
            }
	        	}else {
	        			continue;		        	
	        			}
	    	}
	    if(results!=null && results.size()>0){
	    	isAdded=UploadBlockListForHallticketOrMarkscardHandler.getInstance().addUploadBlockListForHallticketOrMarkscard(results,objform,registerNumMap);
	    }
	    if(isAdded){
	    	if(str!=null && !str.isEmpty()){
				str = str.substring(0, (str.length() - 1));
	    	}
	    	if(str!=null && !str.isEmpty()){
	    		ActionMessage message = new ActionMessage(CMSConstants.Upload_Except_Reg_Numbers);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		errors.add("error", new ActionError("knowledgepro.admin.exam.dup",str));
	    		addErrors(request, errors);
	    	}else{
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
		    	messages.add("messages", message);
		    	saveMessages(request, messages);
	    	}
    	}else{
    		//if adding is failure
    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);
    	}
	    }else{
	    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);
	    }
	   
		if(str3!=null && !str3.isEmpty()){
			str3 = str3.substring(0, (str3.length() - 1));
			str3=str4+str3;
			objform.setDupRegNumMsg(str2);
			objform.setDupRegNum(str3);
			}
		if(str5!=null && !str5.isEmpty()){
			str5 = str5.substring(0, (str5.length() - 1));
			objform.setClassNotFound(str5);
			}
		if(str7!=null && !str7.isEmpty()){
			str7 = str7.substring(0, (str7.length() - 1));
			objform.setDuplireason(str6);
	    	objform.setDuplireasonNum(str7);
			}
	}catch (BusinessException businessException) {
		log.info("Exception UploadBlockListForHallticketOrMarkscardAction");
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		objform.setErrorMessage(msg);
		objform.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	/*if(objform.getDupRegNum()!=null && !objform.getDupRegNum().isEmpty() ){
		errors.add("error", new ActionError("knowledgepro.admin.exam.dup.regNum",objform.getDupRegNum()));
		addErrors(request, errors);
	}*/
	
	log.info("Leaving UploadBlockListForHallticketOrMarkscardAction ");
	//objform.resetFields();
	Map<Integer,String> examMap=CommonAjaxHandler.getInstance().getExamNameByExamTypeAndYear(objform.getExamType(),Integer.parseInt(objform.getYear())); 
	objform.setExamNameMap(examMap);
	return mapping.findForward(CMSConstants.UPLOAD_EXAM_BLOCK_UNBLOCK);
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
	
  /**
 * @author Megha
 *
 */
protected class ByteArrayStreamInfo implements StreamInfo {
		
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
