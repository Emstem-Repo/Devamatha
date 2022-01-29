package com.kp.cms.helpers.attendance;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.kp.cms.bo.admin.PucApprovedLeaves;
import com.kp.cms.bo.admin.PucClassHeld;
import com.kp.cms.bo.admin.PucDefineClassSubject;
import com.kp.cms.forms.attendance.PucAttendanceForm;
import com.kp.cms.helpers.admin.AdmMeritHelper;
import com.kp.cms.utilities.CommonUtil;

public class PucAttendanceHelper {
	private static final Log log = LogFactory
	.getLog(PucAttendanceHelper.class);
	 public static volatile PucAttendanceHelper pucAttHelper = null;
	 public static PucAttendanceHelper getInstance(){
		 if(pucAttHelper == null){
			 pucAttHelper = new PucAttendanceHelper();
			 return pucAttHelper;
		 }
		 return pucAttHelper;
	 }
	
	public List<PucClassHeld> convertExcelTOBOForClassHeld(File file,PucAttendanceForm pucAttendanceForm){
		 List<PucClassHeld> classHeldList=new ArrayList<PucClassHeld>();
		 try{
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
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        PucClassHeld classHeld=new PucClassHeld();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 classHeld.setClasses(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 classHeld.setClassPrac(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 classHeld.setSubLCode(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 classHeld.setBatchNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal held=new BigDecimal(cell.toString().trim());
	        					 classHeld.setClassHeld(held);
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 classHeld.setElective(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(pucAttendanceForm.getAcademicYear()!=null && !pucAttendanceForm.getAcademicYear().isEmpty()){
	        					 classHeld.setAcademicYear(Integer.parseInt(pucAttendanceForm.getAcademicYear()));
	        				 }
	        			 }
                   
               } 
	        	 if(classHeld!=null){
	        		 classHeldList.add(classHeld);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
		 return classHeldList;
	 }
	
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
	
	public List<PucDefineClassSubject> convertExcelTOBOForDefineClassSubjects(File file,PucAttendanceForm pucAttendanceForm){
		 List<PucDefineClassSubject> defineClassSubject=new ArrayList<PucDefineClassSubject>();
		 try{
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
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        PucDefineClassSubject defineSubject=new PucDefineClassSubject();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setClasses(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 defineSubject.setSubjectLCode(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 defineSubject.setLanguage(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        					     defineSubject.setLang(true);
	        					 else
	        						 defineSubject.setLang(false);
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setClassPracticle(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setNoOfBatches(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setSubjectNo(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setSubLveNo(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 defineSubject.setTeachCode(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setClassTaken(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 defineSubject.setElective(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(pucAttendanceForm.getAcademicYear()!=null && !pucAttendanceForm.getAcademicYear().isEmpty()){
	        					 defineSubject.setAcademicYear(Integer.parseInt(pucAttendanceForm.getAcademicYear()));
	        				 }
	        			 }
                  
              } 
	        	 if(defineSubject!=null){
	        		 defineClassSubject.add(defineSubject);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
		 return defineClassSubject;
	 }
	
	public List<PucApprovedLeaves> convertExcelTOBOForApprovedLeaves(File file,PucAttendanceForm pucAttendanceForm){
		 List<PucApprovedLeaves> approvedLeaveList=new ArrayList<PucApprovedLeaves>();
		 try{
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
		    for(int r = 1; r < rows; r++) {
		        row = sheet.getRow(r);
		        PucApprovedLeaves approvedLeaves=new PucApprovedLeaves();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setRegNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 approvedLeaves.setClasses(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 String startDate= cell.toString().trim();
	        					 approvedLeaves.setLeaveStartDate(PucAttendanceHelper.ConvertStringToSQLDate(startDate));
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 String endDate= cell.toString().trim();
	        					 approvedLeaves.setLeaveEndDate(PucAttendanceHelper.ConvertStringToSQLDate(endDate));
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub1(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub2(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub3(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub4(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub5(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub6(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub7(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub8(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub9(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveSub10(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeaveLang(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeavePracl(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeavePrac2(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeavePrac3(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 approvedLeaves.setLeavePrac4(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(pucAttendanceForm.getAcademicYear()!=null && !pucAttendanceForm.getAcademicYear().isEmpty()){
	        					 approvedLeaves.setAcademicYear(Integer.parseInt(pucAttendanceForm.getAcademicYear()));
	        				 }
	        			 }
             } 
	        	approvedLeaveList.add(approvedLeaves);
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
		 return approvedLeaveList;
	 }
	
	public static java.sql.Date ConvertStringToSQLDate(String str) {
		java.sql.Date sqldate = null;
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if(CommonUtil.isStringContainsNumbers(str)){
			String formattedDate = formatter.format(new Date(str.substring(4,6) +"/"+str.substring(6,8) +"/"+str.substring(0,4)));
			sqldate = CommonUtil.ConvertStringToSQLDate(formattedDate);
		}else{
			String formatDate = "";
			if (str != null & !str.isEmpty())
				formatDate = formatSqlDateTime(str);
			
			if (formatDate != null & !formatDate.isEmpty()) {
				Date date = new Date(formatDate);
				sqldate = new java.sql.Date(date.getTime());
			}
		}
		return sqldate;
	}
	
	 public static String formatSqlDateTime(String dateString) {
			String formatDate = "";
			Date date = null;
			try {
				DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
				SimpleDateFormat pattern = new SimpleDateFormat("MM/dd/yy");
				if (dateString != null & !dateString.isEmpty()) {
					date = dateFormat.parse(dateString);
					formatDate = pattern.format(date);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return formatDate;
		}
}
