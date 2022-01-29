package com.kp.cms.actions.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
import org.apache.poi.util.StringUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmBioDataCJCForm;
import com.kp.cms.handlers.admin.AdmBioDataCJCHandler;
import com.kp.cms.helpers.admin.AdmBioDataCJCHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.utilities.CommonUtil;

public class AdmBioDataCJCAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmBioDataCJCAction.class);
 /**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward initAdmBioData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	return mapping.findForward(CMSConstants.INIT_ADM_BIO_DATA);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward admBioDataEntry(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AdmBioDataCJCForm admBioDataForm = (AdmBioDataCJCForm)form;
	admBioDataForm.resetFields();
	return mapping.findForward(CMSConstants.ADM_BIO_DATA_ENTRY);
}
/**
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
public ActionForward uploadAdmBioData(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AdmBioDataCJCForm admBioDataForm = (AdmBioDataCJCForm)form;
	log.info("call of uploadAdmBioData method in AdmBioDataCJCAction class.");
	 ActionErrors errors = admBioDataForm.validate(mapping, request);
	ActionMessages messages = new ActionMessages();
	setUserId(request,admBioDataForm);
	try{
		if(errors.isEmpty()){
			FormFile myFile = admBioDataForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    List<AdmBioDataCJCTo> admBioDataList= null;
		   
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_ADM_BIODATA);
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
				String source=prop.getProperty(CMSConstants.UPLOAD_ADM_BIODATA);
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
			    admBioDataList=new ArrayList<AdmBioDataCJCTo>();
			    for(int r = 1; r < rows; r++) {
			        row = sheet.getRow(r);
			        AdmBioDataCJCTo admBioDataTo =new AdmBioDataCJCTo();
			        if(row != null) {
			        	 for(int c = 0; c < cols;c++) {
			        		 cell = row.getCell((byte)c);
			        		 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
			        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setApplnNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setPercentage(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setRegNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setClasses(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setName(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setYear(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setSection(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setFatherName(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setSecondLanguage(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setReligion(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setCaste(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setScStBcBt(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setSex(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String dob= cell.toString().trim();
			        					  admBioDataTo.setDob(AdmBioDataCJCHelper.ConvertStringToSQLDate(dob));
			        				 }
			        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setNationality(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setState(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setLastInst(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setTelephone(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAddress1(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAddress2(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAddress3(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAddress4(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setOffRemark(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setPrnRemark(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setFeesAmt(Float.parseFloat(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String fPaidDate= cell.toString().trim();
			        					 admBioDataTo.setFpaiddate(AdmBioDataCJCHelper.ConvertStringToSQLDate(fPaidDate));
			        				 }
			        				 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setChalanNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setAnnFees(Float.parseFloat(removeFileExtension(cell.toString().trim())));
			        				 }
			        				 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String afPdDate= cell.toString().trim();
			        					 admBioDataTo.setAfPdDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(afPdDate));
			        				 }
			        				 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setAfChlnNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setScholarship(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 31 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String dateAdm= cell.toString().trim();
			        					 admBioDataTo.setDateAdm(AdmBioDataCJCHelper.ConvertStringToSQLDate(dateAdm));
			        				 }
			        				 if(cell.getCellNum() == 32 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setTcGiven(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 33 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setPassportNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 34 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setPlaceIssued(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 35 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String dateIssued= cell.toString().trim();
			        					 admBioDataTo.setDateIssued(AdmBioDataCJCHelper.ConvertStringToSQLDate(dateIssued));
			        				 }
			        				 if(cell.getCellNum() == 36 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAnnIncome(Float.parseFloat(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 37 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setPlaceBirth(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 38 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setStateBirth(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 39 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setCountry(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 40 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setfOccupation(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 41 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setExamPassed(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 42 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setExRegNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 43 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setYearPass(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 44 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setMonthPass(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 45 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setAdmTcNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 46 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String admTcDt= cell.toString().trim();
			        					  admBioDataTo.setAdmTcDt(AdmBioDataCJCHelper.ConvertStringToSQLDate(admTcDt));
			        				 }
			        				 if(cell.getCellNum() == 47 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String dateLeave= cell.toString().trim();
			        					 admBioDataTo.setDateLeave(AdmBioDataCJCHelper.ConvertStringToSQLDate(dateLeave));
			        				 }
			        				 if(cell.getCellNum() == 48 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setReasonLeave(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 49 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setTcNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 50 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 String tcDate= cell.toString().trim();
			        					  admBioDataTo.setTcDate(AdmBioDataCJCHelper.ConvertStringToSQLDate(tcDate));
			        				 }
			        				 if(cell.getCellNum() == 51 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setExamResult(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 52 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setRemarks(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 53 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setNri(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 54 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setLig(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 55 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setReligious(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 56 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setSelfFinan(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 57 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setsFinanCat(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 58 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setMotherName(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 59 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setExamRegNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 60 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setStudentNo(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 61 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setCet(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(cell.getCellNum() == 62 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setFeePayable(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 63 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setForeign(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 64 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setAdmitCat(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 65 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setBloodGroup(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 66 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setIndSpCdt(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 67 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setPhyHandicapped(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 68 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setMedmInstr(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 69 && !StringUtils.isEmpty(cell.toString().trim())){
			        					 admBioDataTo.setsEvnyKar(cell.toString().trim());
			        				 }
			        				 if(cell.getCellNum() == 70 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
			        					 admBioDataTo.setMobileNumber(removeFileExtension(cell.toString().trim()));
			        				 }
			        				 if(admBioDataForm.getAcademicYear()!=null && !admBioDataForm.getAcademicYear().isEmpty()){
			        					 admBioDataTo.setAcademicYear(admBioDataForm.getAcademicYear());
			        				 }
			        			 }
	                         
	                     } 
			        	admBioDataList.add(admBioDataTo);
			        	 }
			        }
            	 if(!admBioDataList.isEmpty()){
            		 isAdded=AdmBioDataCJCHandler.getInstance().addAdmBioData(admBioDataList,admBioDataForm);
            	 }
		   
		    if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		
	    	}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
	 }else{
	    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
    		errors.add(CMSConstants.ERROR, message);
    		addErrors(request, errors);
    		return mapping.findForward(CMSConstants.ADM_BIO_DATA_ENTRY);
	    }
		}else{
	    	saveErrors(request, errors);
	    	return mapping.findForward(CMSConstants.ADM_BIO_DATA_ENTRY);
	    }
				
		 
	}catch (BusinessException businessException) {
		String msgKey = super.handleBusinessException(businessException);
		ActionMessage message = new ActionMessage(msgKey);
		messages.add(CMSConstants.MESSAGES, message);
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	} catch (Exception exception) {	
		String msg = super.handleApplicationException(exception);
		admBioDataForm.setErrorMessage(msg);
		admBioDataForm.setErrorStack(exception.getMessage());
		return mapping.findForward(CMSConstants.ERROR_PAGE);
	}
	List<String> duplicate=admBioDataForm.getRegNos();
	if(duplicate!=null && !duplicate.isEmpty()){
		StringBuilder ids=new StringBuilder();
		Iterator it=duplicate.iterator();
		while(it.hasNext()){
			ids.append(it.next().toString()).append(", ");
		}
		int len=ids.length()-2;
        if(ids.toString().endsWith(", ")){
            ids.setCharAt(len, ' ');
        }
		errors.add("error",new ActionError( "knowledgepro.admission.uploadSuplimarks.duplicate" ,ids.toString().trim()));
		saveErrors(request, errors);
	}
	return mapping.findForward(CMSConstants.ADM_BIO_DATA_ENTRY);
}
	/**
	 * @author christ
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
