package com.kp.cms.actions.sap;

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
import java.util.Iterator;
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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.exam.ExamBlockUnblockHallTicketBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.sap.UploadSAPMarksForm;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.exam.UploadBlockListForHallticketOrMarkscardHandler;
import com.kp.cms.handlers.sap.UploadSAPMarksHandler;
import com.kp.cms.to.exam.UploadBlockListForHallticketOrMarkscardTo;
import com.kp.cms.to.sap.UploadSAPMarksTo;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class UploadSAPMarksAction extends BaseDispatchAction{
private static final Log log = LogFactory.getLog(UploadSAPMarksAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadSAPMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		UploadSAPMarksForm objform = (UploadSAPMarksForm) form;
		objform.resetFields();
		return mapping.findForward(CMSConstants.UPLOAD_SAP_MARKS);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadSapMarks(ActionMapping mapping,ActionForm form, HttpServletRequest request,
											HttpServletResponse response) throws Exception {
	
	log.info("Entered uploadSapMarks input");
	UploadSAPMarksForm objform = (UploadSAPMarksForm) form;
	objform.resetFields();
	ActionMessages messages=new ActionMessages();
	setUserId(request, objform);
	ActionErrors errors=objform.validate(mapping, request);
	
	int year=0;
	year=CurrentAcademicYear.getInstance().getAcademicyear();
	
	if(year==0){
		Calendar calendar = Calendar.getInstance();
		year = calendar.get(Calendar.YEAR);
	}
	
	String str= "";
	String str2="Duplicate entry found for the following register numbers:";
	/*String str3="";
	String str4="Please check and reupload:";*/
	StringBuilder dupRegNum=new StringBuilder();
	String emptyStatusMsg="Status is empty for the following register numbers:";
	StringBuilder emptyStatusRegNum=new StringBuilder();
	//StringBuilder wrongFormat=new StringBuilder("Please enter the date format(DD-MM-YYYY)  for this follwing register numbers: ");
	//StringBuilder emptyDate=new StringBuilder("Please enter the date  for this follwing register numbers: ");
	/*Map<String,Integer> monthMap =new HashMap<String,Integer>();
	monthMap.put("Jan",1);
	monthMap.put("Feb",2);
	monthMap.put("Mar",3);
	monthMap.put("Apr",4);
	monthMap.put("May",5);
	monthMap.put("Jun",6);
	monthMap.put("Jul",7);
	monthMap.put("Aug",8);
	monthMap.put("Sep",9);
	monthMap.put("Oct",10);
	monthMap.put("Nov",11);
	monthMap.put("Dec",12);*/
	try{
		if(errors.isEmpty()){
		if(objform.getThefile() == null){
			ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);
    		return mapping.findForward(CMSConstants.UPLOAD_SAP_MARKS);
		}
		FormFile myFile = objform.getThefile();
	    String contentType = myFile.getContentType();
	    String fileName    = myFile.getFileName();
	   	File file = null;
	    Properties prop = new Properties();
	    InputStream stream = SmartCardNumberUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
	    prop.load(stream);
	    List<UploadSAPMarksTo> results = null;
	    List<String> regNumList=new ArrayList<String>();
	    List<UploadSAPMarksTo> toList = new ArrayList<UploadSAPMarksTo>();
	    //if the uploading file is excel file
	    if(fileName.endsWith(".xls")){
	    	//Map<String,Integer> registerNumMap=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getStudentIdByStudentRegNum(String.valueOf(year));
	    	byte[] fileData = myFile.getFileData();
	    	String source1=prop.getProperty(CMSConstants.Upload_SAP_MARKS_XLS);
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
		String source=prop.getProperty(CMSConstants.Upload_SAP_MARKS_XLS);
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
	    results= new ArrayList<UploadSAPMarksTo>();
	    List<String> dupRegNumber=new ArrayList<String>();
	    for(int r = 1; r < rows; r++) {
	    	
	        row = sheet.getRow(r);
	        UploadSAPMarksTo uploadTo = new UploadSAPMarksTo();
	        if(row != null) {
	            for(int c = 0; c < cols;c++) {
	                cell = row.getCell((byte)c);
	                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	                	 String cell2=cell.toString();
						
						if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell2)){
							uploadTo.setStRegNum(removeFileExtension(cell2.trim()));
						}
						
						if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
							uploadTo.setMarks(removeFileExtension(cell2.trim()));
						}
						
						if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
							uploadTo.setStatus(cell2.trim());
						}
						/*if(cell.getCellNum() == 3 ){
							if(cell2.trim()!=null && !cell2.trim().isEmpty()){
								if(cell.getCellType()==0 && cell.getDateCellValue()!=null){
								 String date=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(cell.getDateCellValue()), "dd-MMM-yyyy",
									"dd/MM/yyyy");
								 uploadTo.setDate(date);
								}else{

									String date=(cell2.trim()).toString();
									String[] dateformat=date.split("/");
									if(dateformat==null){
										dateformat=date.split("-");
									}
									
										if (dateformat != null ) {
											if(dateformat[0]!=null && Integer.parseInt(dateformat[0]) > 0){
												if(Integer.parseInt(dateformat[0])>31){
													wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
													objform.setWrgDateFormat(true);
													break;
												}
											}else if(dateformat[0]==null || Integer.parseInt(dateformat[0])==0){
												wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
												objform.setWrgDateFormat(true);
												break;
											}
											 if(dateformat[1]!=null ){
												 if(StringUtils.isNumeric(dateformat[1])){
													 if(Integer.parseInt(dateformat[1])>12){
															wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
															objform.setWrgDateFormat(true);
															break;
														}
												 }else{
													 if(!monthMap.containsKey(dateformat[1])){
														 wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
															objform.setWrgDateFormat(true);
															break; 
													 }
												 }
											}else if(dateformat[1]==null || Integer.parseInt(dateformat[1])==0){
												wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
												objform.setWrgDateFormat(true);
												break;
											}
											  if(dateformat[2]!=null && Integer.parseInt(dateformat[2]) > 0){
												if(Integer.parseInt(dateformat[2]) < 1990){
													wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
													objform.setWrgDateFormat(true);
													break;
												}
												
											}else if(dateformat[2]==null || Integer.parseInt(dateformat[2])==0){
												wrongFormat=wrongFormat.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
												objform.setWrgDateFormat(true);
												break;
											}
											  if(StringUtils.isNumeric(dateformat[1])){
												  uploadTo.setDate(cell2.trim());
											  }else{
												  uploadTo.setDate(dateformat[0]+"/"+monthMap.get(dateformat[1])+"/"+dateformat[2]); 
											  }
									uploadTo.setDate(cell2.trim());
							}
					}else{
						emptyDate=emptyDate.append((UploadSAPMarksHandler.getInstance().getRegNumByStudentId(uploadTo.getStudentId())).toString());
						objform.setEmptyDate(true);
						break;
					}
						}*/
	                }
                } 
            
	            results.add(uploadTo);
    	}else {
    			continue;		        	
				}
    	}
	    if(results!=null && !results.isEmpty()){
			Iterator<UploadSAPMarksTo> itr = results.iterator();
			while (itr.hasNext()) {
				UploadSAPMarksTo to = (UploadSAPMarksTo) itr.next();
				regNumList.add(to.getStRegNum());
			}
	    }
	    Map<String,Integer> registerNumMap=UploadSAPMarksHandler.getInstance().getStudentIdByStudentRegNum(String.valueOf(year),regNumList);
	    if(results!=null && !results.isEmpty()){
			Iterator<UploadSAPMarksTo> itr = results.iterator();
			while (itr.hasNext()) {
				UploadSAPMarksTo to = (UploadSAPMarksTo) itr.next();
				UploadSAPMarksTo newTo=new UploadSAPMarksTo();
				boolean isDupRegNo=false;
				if(!dupRegNumber.contains(to.getStRegNum())){
					if(registerNumMap.containsKey(to.getStRegNum())){
						newTo.setStudentId(registerNumMap.get(to.getStRegNum()));
						dupRegNumber.add(to.getStRegNum());
					}else{
						if(str==null || str.isEmpty()){
							str=str+removeFileExtension(to.getStRegNum());
						}else{
							str=str+","+removeFileExtension(to.getStRegNum());
						}
						isDupRegNo=true;
					}
	             }else{
	            	 if(dupRegNum.length()==0)
	            		 dupRegNum=dupRegNum.append(removeFileExtension(to.getStRegNum()));
	            	 else
	            		 dupRegNum=dupRegNum.append(",").append(removeFileExtension(to.getStRegNum()));
	            	 objform.setDupliRegNum(true);
	            	 isDupRegNo=true;
	             	}
				newTo.setMarks(to.getMarks());
				if(to.getStatus()!=null && !to.getStatus().isEmpty()){
					newTo.setStatus(to.getStatus());
				}else{
					if(emptyStatusRegNum.length()==0)
						emptyStatusRegNum=emptyStatusRegNum.append(removeFileExtension(to.getStRegNum()));
					else
						emptyStatusRegNum=emptyStatusRegNum.append(",").append(removeFileExtension(to.getStRegNum()));
					objform.setEmptyStatus(true);
					isDupRegNo=true;
				}
				if(newTo!=null && (newTo.getStatus()!=null &&  !newTo.getStatus().isEmpty() )&& newTo.getStudentId()!=0){
					if(!isDupRegNo)
						toList.add(newTo);
					}
			}
	    }
	    if(toList!=null && toList.size()>0){
	    	isAdded=UploadSAPMarksHandler.getInstance().uploadSapMarks(toList,objform);
	    }
	    if(isAdded){
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
	   
		if(objform.isDupliRegNum()){
			objform.setDupRegNumMsg(str2);
			objform.setDupRegNum(dupRegNum.toString());
			}
		if(objform.getEmptyStatus()){
			objform.setEmptyStatusMsg(emptyStatusMsg);
			objform.setEmptuStatusRegNum(emptyStatusRegNum.toString());
			}
		
		/*if(objform.isWrgDateFormat()){
			objform.setDisplayWrgDateMsg(wrongFormat.toString());
		}
		if(objform.isEmptyDate()){
			objform.setDisplayEmptyDate(emptyDate.toString());
		}*/
		
		}else{
			saveErrors(request, errors);
			objform.resetFields();
		}
	}catch (BusinessException businessException) {
		log.info("Exception uploadSapMarksAction");
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
	
	log.info("Leaving uploadSapMarksAction ");
	objform.setExamDate(null);
	return mapping.findForward(CMSConstants.UPLOAD_SAP_MARKS);
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
 * @author Chandhu
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
