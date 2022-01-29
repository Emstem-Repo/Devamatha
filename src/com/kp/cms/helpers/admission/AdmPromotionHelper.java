package com.kp.cms.helpers.admission;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.kp.cms.bo.admission.PromoteSecondLang;
import com.kp.cms.bo.admission.PromoteSupliMarks;
import com.kp.cms.forms.admission.AdmPromotionForm;

public class AdmPromotionHelper {
	private static final Log log = LogFactory
	.getLog(AdmPromotionHelper.class);
	 public static volatile AdmPromotionHelper admPromotionHelper = null;
	 public static AdmPromotionHelper getInstance(){
		 if(admPromotionHelper == null){
			 admPromotionHelper = new AdmPromotionHelper();
			 return admPromotionHelper;
		 }
		 return admPromotionHelper;
	 }
	 
	 public List<PromoteSecondLang> convertExcelTOBOForSecondLang(File file,AdmPromotionForm admPromote){
		 List<PromoteSecondLang> secondLangList=new ArrayList<PromoteSecondLang>();
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
		        PromoteSecondLang secondLang=new PromoteSecondLang();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 secondLang.setSecondLanguage(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 secondLang.setLangCode(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(admPromote.getAcademicYear()!=null && !admPromote.getAcademicYear().isEmpty()){
	        					 secondLang.setAcademicYear(Integer.parseInt(admPromote.getAcademicYear()));
	        				 }
	        			 }
                    
                } 
	        	 if(secondLang!=null){
	        		 secondLangList.add(secondLang);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
	        
		 return secondLangList;
	 }
	 public String removeFileExtension(String fileName)
		{ 
		if(null != fileName && fileName.contains("."))
		{
		return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
		}
	 
	 public List<PromoteSupliMarks> convertExcelTOBOForSupliMarks(File file,AdmPromotionForm admPromote){
		 List<PromoteSupliMarks> supliMarksList=new ArrayList<PromoteSupliMarks>();
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
		        PromoteSupliMarks supliMarks=new PromoteSupliMarks();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setRegNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setClassCode(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub1=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub1(sub1);
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub2=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub2(sub2);
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub3=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub3(sub3);
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub4=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub4(sub4);
	        				 }
	        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub5=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub5(sub5);
	        				 }
	        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub6=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub6(sub6);
	        				 }
	        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal sub7=new BigDecimal(cell.toString().trim());
	        					 supliMarks.setMarkSub7(sub7);
	        				 }
	        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub1(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub2(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub3(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub4(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub5(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub6(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setGradeSub7(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 supliMarks.setSection(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setWithHeld(true);
	        					 else
	        						 supliMarks.setWithHeld(false);
	        				 }
	        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        					     supliMarks.setSupAttend(true);
	        					 else
	        						 supliMarks.setSupAttend(false);
	        				 }
	        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub1(true);
	        					 else
	        						 supliMarks.setSuplSub1(false);
	        				 }
	        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub2(true);
	        					 else
	        						 supliMarks.setSuplSub2(false);
	        				 }
	        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub3(true);
	        					 else
	        						 supliMarks.setSuplSub3(false);
	        				 }
	        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub4(true);
	        					 else
	        						 supliMarks.setSuplSub4(false);
	        				 }
	        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub5(true);
	        					 else
	        						 supliMarks.setSuplSub5(false);
	        				 }
	        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub6(true);
	        					 else
	        						 supliMarks.setSuplSub6(false);
	        				 }
	        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 supliMarks.setSuplSub7(true);
	        					 else
	        						 supliMarks.setSuplSub7(false);
	        				 }
	        				 if(admPromote.getAcademicYear()!=null && !admPromote.getAcademicYear().isEmpty()){
	        					 supliMarks.setAcademicYear(Integer.parseInt(admPromote.getAcademicYear()));
	        				 }
	        			 }
                    
                } 
	        	 if(supliMarks!=null){
	        		 supliMarksList.add(supliMarks);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
		 return supliMarksList;
	 }
}
