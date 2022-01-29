package com.kp.cms.helpers.admin;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.kp.cms.bo.admin.AdmFeeMain;
import com.kp.cms.bo.admin.AdmMeritList;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.AdmMeritListForm;
import com.kp.cms.helpers.admission.AdmissionBioDataReportHelper;
import com.kp.cms.to.admin.AdmBioDataCJCTo;
import com.kp.cms.to.admin.AdmMeritListReportTo;
import com.kp.cms.to.admin.AdmMeritTO;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.HibernateUtil;

public class AdmMeritHelper {
	private static final Log log = LogFactory
	.getLog(AdmMeritHelper.class);
	 public static volatile AdmMeritHelper admMeritHelper = null;
	 private static final String DISPLAY = "display";
	 private AdmMeritHelper(){
		 
	 }
	 public static AdmMeritHelper getInstance(){
		 if(admMeritHelper == null){
			 admMeritHelper = new AdmMeritHelper();
			 return admMeritHelper;
		 }
		 return admMeritHelper;
	 }
	 
	 public List<AdmMeritTO> convertExcelTOBO(File file){
		 List<AdmMeritTO> meritList=new ArrayList<AdmMeritTO>();
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
		        AdmMeritTO meritTO=new AdmMeritTO();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setName(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setAppNo(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setUrbanRural(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setReligion(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setScStScbt(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setCthPrOth(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setLastInstitute(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setKarNonkar(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setExamPass(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setYearPass(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setMonthPass(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setNoOfAttempts(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setMediumInstr(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 BigDecimal decimal=new BigDecimal(cell.toString().trim());
	        					 meritTO.setPercentage(decimal);
	        				 }
	        				 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setFirstPreference(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setRemarks(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setTotalMarks(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setMaxMarks(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setRank(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 
	        				 if(cell.getCellNum() == 19 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setChristee(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 20 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setOldRegisterNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 21 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setAdjRank(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 22 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setCategory(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 23 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setMeritSet(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 24 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setPh(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 25 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setSex(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 26 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setAddress1(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 27 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setAddress2(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 28 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setAddress3(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 29 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setAddress4(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 30 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setApplnOthers(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 31 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setOtherColleges(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 32 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setDistance(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 
	        				 if(cell.getCellNum() == 33 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 String submitDate= cell.toString().trim();
	        					 meritTO.setSubmitDate(AdmMeritHelper.ConvertStringToSQLDate(submitDate));
	        				 }
	        				 if(cell.getCellNum() == 34 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 BigDecimal decimal=new BigDecimal(cell.toString().trim());
	        					 meritTO.setOverPerWtg(decimal);
	        				 }
	        				 if(cell.getCellNum() == 35 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setSecondPreference(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 36 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setSecondLanguage(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 37 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setBloodGroup(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 38 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 String dob= cell.toString().trim();
	        					 meritTO.setDateOfBirth(AdmMeritHelper.ConvertStringToSQLDate(dob));
	        				 }
	        				 
	        				 if(cell.getCellNum() == 39 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setPinCode(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 40 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setNationality(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 41 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setSheetNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 42 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setSevnyrKar(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 43 && !StringUtils.isEmpty(cell.toString().trim())){
	        					 meritTO.setPlaceOfBirth(cell.toString().trim());
	        				 }
	        				 if(cell.getCellNum() == 44 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 meritTO.setMobileNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        			 }
                    
                } 
	        	 if(meritTO!=null){
	        		 meritList.add(meritTO);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	
		    }
	        
		 return meritList;
	 }
	 public String removeFileExtension(String fileName)
		{ 
		if(null != fileName && fileName.contains("."))
		{
		return fileName.substring(0, fileName.lastIndexOf("."));
		}
		return fileName;
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
	 public List<AdmMeritList> convertTOtoBO(List<AdmMeritTO> meritTO,AdmMeritListForm admMeritForm){
		 List<AdmMeritList> meritList=new ArrayList<AdmMeritList>();
		 Iterator<AdmMeritTO> itr=meritTO.iterator();
		 while(itr.hasNext()){
			 AdmMeritTO to=(AdmMeritTO)itr.next();
			 AdmMeritList bo=new AdmMeritList();
			 if(to.getAppNo()!=null && to.getAppNo()!=0){
				 bo.setAppNo(to.getAppNo());
			 }
			 if(to.getName()!=null && !to.getName().isEmpty()){
				 bo.setName(to.getName());
			 }
			 if(to.getUrbanRural()!=null && !to.getUrbanRural().isEmpty()){
				 bo.setUrbanRural(to.getUrbanRural());
			 }
			 if(to.getReligion()!=null && !to.getReligion().isEmpty()){
				 bo.setReligion(to.getReligion());
			 }
			 if(to.getScStScbt()!=null && !to.getScStScbt().isEmpty()){
				 bo.setScStScbt(to.getScStScbt());
			 }
			 if(to.getCthPrOth()!=null && !to.getCthPrOth().isEmpty()){
				 bo.setCthPrOth(to.getCthPrOth());
			 }
			 if(to.getLastInstitute()!=null && !to.getLastInstitute().isEmpty()){
				 bo.setLastInstitute(to.getLastInstitute());
			 }
			 if(to.getKarNonkar()!=null && !to.getKarNonkar().isEmpty()){
				 bo.setKarNonkar(to.getKarNonkar());
			 }
			 if(to.getExamPass()!=null && !to.getExamPass().isEmpty()){
				 bo.setExamPass(to.getExamPass());
			 }
			 if(to.getYearPass()!=null && !to.getYearPass().isEmpty()){
				 bo.setYearPass(to.getYearPass());
			 }
			 if(to.getMonthPass()!=null && !to.getMonthPass().isEmpty()){
				 bo.setMonthPass(to.getMonthPass());
			 }
			 if(to.getNoOfAttempts()!=null && to.getNoOfAttempts()!=0){
				 bo.setNoOfAttempts(to.getNoOfAttempts());
			 }else
				 bo.setNoOfAttempts(0);
			 if(to.getMediumInstr()!=null && !to.getMediumInstr().isEmpty()){
				 bo.setMediumInstr(to.getMediumInstr());
			 }
			 if(to.getPercentage()!=null){
				 bo.setPercentage(to.getPercentage());
			 }
			 if(to.getFirstPreference()!=null && !to.getFirstPreference().isEmpty()){
				 bo.setFirstPreference(to.getFirstPreference());
			 }
			 if(to.getRemarks()!=null && !to.getRemarks().isEmpty()){
				 bo.setRemarks(to.getRemarks());
			 }
			 if(to.getMaxMarks()!=null && to.getMaxMarks()!=0){
				 bo.setMaxMarks(to.getMaxMarks());
			 }else
				 bo.setMaxMarks(0);
			 if(to.getTotalMarks()!=null && to.getTotalMarks()!=0){
				 bo.setTotalMarks(to.getTotalMarks());
			 }else
				 bo.setTotalMarks(0);
			 if(to.getRank()!=null && !to.getRank().isEmpty()){
				 bo.setRank(to.getRank());
			 }
			 if(to.getChristee()!=null && !to.getChristee().isEmpty()){
				 if(to.getChristee().equalsIgnoreCase("true")){
					 bo.setChristee(true);
				 }else
					 bo.setChristee(false);
			 }
			 if(to.getOldRegisterNo()!=null && !to.getOldRegisterNo().isEmpty()){
				 bo.setOldRegisterNo(to.getOldRegisterNo());
			 }
			 if(to.getAdjRank()!=null && !to.getAdjRank().isEmpty()){
				 bo.setAdjRank(to.getAdjRank());
			 }
			 if(to.getCategory()!=null && !to.getCategory().isEmpty()){
				 bo.setCategory(to.getCategory());
			 }
			 if(to.getMeritSet()!=null && !to.getMeritSet().isEmpty()){
				 bo.setMeritSet(to.getMeritSet());
			 }
			 if(to.getPh()!=null && !to.getPh().isEmpty()){
				 bo.setPh(to.getPh());
			 }
			 if(to.getSex()!=null && !to.getSex().isEmpty()){
				 bo.setSex(to.getSex());
			 }
			 if(to.getAddress1()!=null && !to.getAddress1().isEmpty()){
				 bo.setAddress1(to.getAddress1());
			 }
			 if(to.getAddress2()!=null && !to.getAddress2().isEmpty()){
				 bo.setAddress2(to.getAddress2());
			 }
			 if(to.getAddress3()!=null && !to.getAddress3().isEmpty()){
				 bo.setAddress3(to.getAddress3());
			 }
			 if(to.getAddress4()!=null && !to.getAddress4().isEmpty()){
				 bo.setAddress4(to.getAddress4());
			 }
			 if(to.getApplnOthers()!=null && !to.getApplnOthers().isEmpty()){
				 bo.setApplnOthers(to.getApplnOthers());
			 }
			 if(to.getOtherColleges()!=null && !to.getOtherColleges().isEmpty()){
				 bo.setOtherColleges(to.getOtherColleges());
			 }
			 if(to.getDistance()!=null && !to.getDistance().isEmpty()){
				 bo.setDistance(to.getDistance());
			 }
			 if(to.getSubmitDate()!=null){
				 bo.setSubmitDate(to.getSubmitDate());
			 }
			 if(to.getOverPerWtg()!=null){
				 bo.setOverPerWtg(to.getOverPerWtg());
			 }
			 if(to.getSecondPreference()!=null && !to.getSecondPreference().isEmpty()){
				 bo.setSecondPreference(to.getSecondPreference());
			 }
			 if(to.getSecondLanguage()!=null && !to.getSecondLanguage().isEmpty()){
				 bo.setSecondLanguage(to.getSecondLanguage());
			 }
			 if(to.getBloodGroup()!=null && !to.getBloodGroup().isEmpty()){
				 bo.setBloodGroup(to.getBloodGroup());
			 }
			 if(to.getDateOfBirth()!=null){
				 bo.setDateOfBirth(to.getDateOfBirth());
			 }
			 if(to.getPinCode()!=null && !to.getPinCode().isEmpty()){
				 bo.setPinCode(to.getPinCode());
			 }
			 if(to.getNationality()!=null && !to.getNationality().isEmpty()){
				 bo.setNationality(to.getNationality());
			 }
			 if(to.getSheetNo()!=null && !to.getSheetNo().isEmpty()){
				 bo.setSheetNo(to.getSheetNo());
			 }
			 if(to.getSevnyrKar()!=null && !to.getSevnyrKar().isEmpty()){
				 if(to.getSevnyrKar().equalsIgnoreCase("true"))
				     bo.setSevnyrKar(true);
				 else
					 bo.setSevnyrKar(false);
			 }
			 if(to.getPlaceOfBirth()!=null && !to.getPlaceOfBirth().isEmpty()){
				 bo.setPlaceOfBirth(to.getPlaceOfBirth());
			 }
			 if(to.getMobileNo()!=null && !to.getMobileNo().isEmpty()){
				 bo.setMobileNo(to.getMobileNo());
			 }
			 bo.setAcademicYear(Integer.parseInt(admMeritForm.getAcademicYear()));
			 meritList.add(bo);
		 }
		 return meritList;
	 }
	 
	 public List<AdmFeeMain> convertExcelTOBOForFee(File file,AdmMeritListForm admMeritForm){
		 List<AdmFeeMain> feeMainList=new ArrayList<AdmFeeMain>();
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
		        AdmFeeMain feeMain=new AdmFeeMain();
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	        				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setBillNo(Integer.parseInt(removeFileExtension(cell.toString().trim())));
	        				 }
	        				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 String date=removeFileExtension(cell.toString().trim());
	        					 feeMain.setDate(AdmMeritHelper.ConvertStringToSQLDate(date));
	        				 }
	        				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setTime(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setApplnRegNo(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setClasses(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal nfee=new BigDecimal(cell.toString().trim());
	        					 feeMain.setNormalFee(nfee);
	        				 }
	        				 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal mfee=new BigDecimal(cell.toString().trim());
	        					 feeMain.setMaintFee(mfee);
	        				 }
	        				 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("0"))
	        						 feeMain.setExcemption(false);
	        					 else
	        						 feeMain.setExcemption(true);
	        				 }
	        				 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 if(cell.toString().equalsIgnoreCase("true"))
	        						 feeMain.setMoneyPaid(true);
	        					 else
	        						 feeMain.setMoneyPaid(false);
	        				 }
	        				 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setUserCode(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 BigDecimal mconcesion=new BigDecimal(cell.toString().trim());
	        					 feeMain.setMaintConcesion(mconcesion);
	        				 }
	        				 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
	        					 feeMain.setConcDesc(removeFileExtension(cell.toString().trim()));
	        				 }
	        				 if(admMeritForm.getAcademicYear()!=null && !admMeritForm.getAcademicYear().isEmpty()){
	        					 feeMain.setAcademicYear(Integer.parseInt(admMeritForm.getAcademicYear()));
	        				 }
	        			 }
                    
                } 
	        	 if(feeMain!=null){
	        		 feeMainList.add(feeMain);
	        	 	}
	        	 }
		    }}catch(Exception e){
		    	log.debug("Error during conversion...", e);
		    }
	        
		 return feeMainList;
	 }

	/**
	 * @param admMeritListForm
	 * @return
	 * @throws Exception
	 */
	public StringBuffer selectedQuery(AdmMeritListForm admMeritListForm) throws Exception {
		StringBuffer stringBuffer = new StringBuffer("from AdmMeritList meritList where");
		if(admMeritListForm.getAcademicYear()!=null && !admMeritListForm.getAcademicYear().isEmpty()){
			stringBuffer = stringBuffer.append(" meritList.academicYear='"+admMeritListForm.getAcademicYear()+"'");
		}
			Session session = null;
			Transaction tx =null;
			try{
				session = HibernateUtil.getSession();
				tx=session.beginTransaction();
				stringBuffer = stringBuffer.append(" and meritList.firstPreference='"+admMeritListForm.getCourseName()+"'");
				}catch (Exception e) {
				throw new ApplicationException(e);
				}
		return stringBuffer;
	}

	/**
	 * @param meritLists
	 * @return
	 * @throws Exception
	 */
	public List<AdmMeritTO> populateBOToTO(List<AdmMeritList> meritLists)throws Exception {
		List<AdmMeritTO> list = new ArrayList<AdmMeritTO>();
		if(meritLists!=null){
			Iterator<AdmMeritList> iterator = meritLists.iterator();
			while (iterator.hasNext()) {
				AdmMeritList admMeritList = (AdmMeritList) iterator.next();
				AdmMeritTO admMeritTO = new AdmMeritTO();
				if(admMeritList.getAppNo()!=null && !admMeritList.getAppNo().toString().isEmpty()){
					admMeritTO.setAppNo(admMeritList.getAppNo());
				}
				if(admMeritList.getName()!=null && !admMeritList.getName().isEmpty()){
					admMeritTO.setName(admMeritList.getName());
				}
				if(admMeritList.getReligion()!=null && !admMeritList.getReligion().isEmpty()){
					admMeritTO.setReligion(admMeritList.getReligion());
				}
				if(admMeritList.getScStScbt()!=null && !admMeritList.getScStScbt().isEmpty()){
					admMeritTO.setScStScbt(admMeritList.getScStScbt());
				}
				if(admMeritList.getCthPrOth()!=null && !admMeritList.getCthPrOth().isEmpty()){
					admMeritTO.setCthPrOth(admMeritList.getCthPrOth());
				}
				if(admMeritList.getLastInstitute()!=null && !admMeritList.getLastInstitute().isEmpty()){
					admMeritTO.setLastInstitute(admMeritList.getLastInstitute());
				}
				if(admMeritList.getKarNonkar()!=null && !admMeritList.getKarNonkar().isEmpty()){
					admMeritTO.setKarNonkar(admMeritList.getKarNonkar());
				}
				if(admMeritList.getExamPass()!=null && !admMeritList.getExamPass().isEmpty()){
					admMeritTO.setExamPass(admMeritList.getExamPass());
				}
				if(admMeritList.getYearPass()!=null && !admMeritList.getYearPass().isEmpty()){
					admMeritTO.setYearPass(admMeritList.getYearPass());
				}
				if(admMeritList.getMonthPass()!=null && !admMeritList.getMonthPass().isEmpty()){
					admMeritTO.setMonthPass(admMeritList.getMonthPass());
				}
				if(admMeritList.getNoOfAttempts()!=null && !admMeritList.getNoOfAttempts().toString().isEmpty()){
					admMeritTO.setNoOfAttempts(admMeritList.getNoOfAttempts());
				}
				if(admMeritList.getMediumInstr()!=null && !admMeritList.getMediumInstr().isEmpty()){
					admMeritTO.setMediumInstr(admMeritList.getMediumInstr());
				}
				if(admMeritList.getPercentage()!=null && !admMeritList.getPercentage().toString().isEmpty()){
					admMeritTO.setPercentage(admMeritList.getPercentage());
				}
				if(admMeritList.getFirstPreference()!=null && !admMeritList.getFirstPreference().isEmpty()){
					admMeritTO.setFirstPreference(admMeritList.getFirstPreference());
				}
				if(admMeritList.getTotalMarks()!=null &&!admMeritList.getTotalMarks().toString().isEmpty()){
					admMeritTO.setTotalMarks(admMeritList.getTotalMarks());
				}
				if(admMeritList.getMaxMarks()!=null && !admMeritList.getMaxMarks().toString().isEmpty()){
					admMeritTO.setMaxMarks(admMeritList.getMaxMarks());
				}
				if(admMeritList.getRank()!=null && !admMeritList.getRank().isEmpty()){
					admMeritTO.setRank(admMeritList.getRank());
				}
				if(admMeritList.getMeritSet()!=null && !admMeritList.getMeritSet().isEmpty()){
					admMeritTO.setMeritSet(admMeritList.getMeritSet());
				}
				if(admMeritList.getPh()!=null && !admMeritList.getPh().isEmpty()){
					admMeritTO.setPh(admMeritList.getPh());
				}
				if(admMeritList.getSex()!=null && !admMeritList.getSex().isEmpty()){
					admMeritTO.setSex(admMeritList.getSex());
				}
				String str= "";
				if(admMeritList.getAddress1()!=null && !admMeritList.getAddress1().isEmpty()){
					String str1=admMeritList.getAddress1();
					str = str+str1+",";
				}
				if(admMeritList.getAddress2()!=null && !admMeritList.getAddress2().isEmpty()){
					String str1=admMeritList.getAddress2();
					str = str+str1+",";
				}
				if(admMeritList.getAddress3()!=null && !admMeritList.getAddress3().isEmpty()){
					String str1=admMeritList.getAddress3();
					str = str+str1+",";
				}
				if(admMeritList.getAddress4()!=null && !admMeritList.getAddress4().isEmpty()){
					String str1=admMeritList.getAddress4();
					str = str+str1;
				}
				admMeritTO.setAddress1(str);
				if(admMeritList.getSubmitDate()!=null && !admMeritList.getSubmitDate().toString().isEmpty()){
					admMeritTO.setSubmitDate(admMeritList.getSubmitDate());
				}
				if(admMeritList.getOverPerWtg()!=null && !admMeritList.getOverPerWtg().toString().isEmpty()){
					admMeritTO.setOverPerWtg(admMeritList.getOverPerWtg());
				}
				if(admMeritList.getSecondLanguage()!=null && !admMeritList.getSecondLanguage().isEmpty()){
					admMeritTO.setSecondLanguage(admMeritList.getSecondLanguage());
				}
				if(admMeritList.getBloodGroup()!=null && !admMeritList.getBloodGroup().isEmpty()){
					admMeritTO.setBloodGroup(admMeritList.getBloodGroup());
				}
				if(admMeritList.getDateOfBirth()!=null && !admMeritList.getDateOfBirth().toString().isEmpty()){
					admMeritTO.setDob(CommonUtil.getStringDate(admMeritList.getDateOfBirth()));
				}
				if(admMeritList.getPinCode()!=null && !admMeritList.getPinCode().isEmpty()){
					admMeritTO.setPinCode(admMeritList.getPinCode());
				}
				if(admMeritList.getNationality()!=null && !admMeritList.getNationality().isEmpty()){
					admMeritTO.setNationality(admMeritList.getNationality());
				}
				if(admMeritList.getSheetNo()!=null && !admMeritList.getSheetNo().isEmpty()){
					admMeritTO.setSheetNo(admMeritList.getSheetNo());
				}
				if(admMeritList.getSevnyrKar()!=null){
					if(!admMeritList.getSevnyrKar().toString().isEmpty()){
						if(admMeritList.getSevnyrKar()){
							admMeritTO.setSevnyrKar("TRUE");
						}else{
							admMeritTO.setSevnyrKar("FALSE");
						}
					}else{
						admMeritTO.setSevnyrKar("");
					}
				}
				if(admMeritList.getPlaceOfBirth()!=null && !admMeritList.getPlaceOfBirth().isEmpty()){
					admMeritTO.setPlaceOfBirth(admMeritList.getPlaceOfBirth());
				}
				if(admMeritList.getMobileNo()!=null && !admMeritList.getMobileNo().isEmpty()){
					admMeritTO.setMobileNo(admMeritList.getMobileNo());
				}
				if(admMeritList.getAcademicYear()!=null && !admMeritList.getAcademicYear().toString().isEmpty()){
					admMeritTO.setAcademicYear(String.valueOf(admMeritList.getAcademicYear()));
				}
				list.add(admMeritTO);
			}
		}
		return list;
	}

	/**
	 * @param admMeritListForm
	 * @return
	 */
	public AdmMeritListReportTo selectedColumns( AdmMeritListForm admMeritListForm) throws Exception{
		AdmMeritListReportTo meritListReportTo = new AdmMeritListReportTo();
		List<String> selectedList = new ArrayList<String>();
		String [] selected= admMeritListForm.getSelectedColumnsArray();
		for(int i=0;i< selected.length ; i++){
			selectedList.add(selected[i]);
		}
		if(selectedList!=null){
			selectedList.add("Academic Year");
			Iterator<String> iterator =  selectedList.iterator();
			int count = 0;
			while(iterator.hasNext()){
				String columnName = iterator.next();
				if(columnName.equalsIgnoreCase("Name")){
					meritListReportTo.setNameDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setNamePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Appln No")){
					meritListReportTo.setApplnoDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setApplnoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Religion")){
					meritListReportTo.setReligionDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setReligionPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Caste")){
					meritListReportTo.setCasteDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setCastePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Category(sc_st_scbt)")){
					meritListReportTo.setCategoryDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setCategoryPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Previous Qualification Details")){
					meritListReportTo.setLastInsDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setLastInstPos((short)count++);
					meritListReportTo.setExamPassedDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setExamPassedPos((short)count++);
					meritListReportTo.setKarNonKarDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setKarNonKarPos((short)count++);
					meritListReportTo.setYearPassDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setYearPassPos((short)count++);
					meritListReportTo.setMonthPassDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setMonthPassPos((short)count++);
					meritListReportTo.setNoOfAttemptsDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setNoOfAttemptPos((short)count++);
					
				}
				if(columnName.equalsIgnoreCase("Medium Instr")){
					meritListReportTo.setMediuminstrDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setMediuminstrPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Percentage")){
					meritListReportTo.setPercentageDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setPercentagePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("First Preference")){
					meritListReportTo.setFirstPreferancesDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setFirstPreferancesPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Total Marks")){
					meritListReportTo.setTotMarksDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setTotMarksPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Max Marks")){
					meritListReportTo.setMaxMarksDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setMaxMarksPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Gender")){
					meritListReportTo.setGenderDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setGenderPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Address")){
					meritListReportTo.setAddressDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setAddressPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Weightage")){
					meritListReportTo.setWeightageDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setWeightagePos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Second Language")){
					meritListReportTo.setSecondLangDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setSecondLangPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Blood Group")){
					meritListReportTo.setBloodGroupDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setBloodGroupPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Date Of Birth")){
					meritListReportTo.setDobDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setDobPOs((short)count++);
				}
				if(columnName.equalsIgnoreCase("Nationality")){
					meritListReportTo.setNationalityDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setNationalityPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Seven years of Karnataka")){
					meritListReportTo.setSevnYrKarDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setSevnYrKatPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Place Birth")){
					meritListReportTo.setPlaceBirthDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setPlaceBirthPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Mobile No")){
					meritListReportTo.setMobileNoDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setMobileNoPos((short)count++);
				}
				if(columnName.equalsIgnoreCase("Academic Year")){
					meritListReportTo.setAcademicYearDis(AdmMeritHelper.DISPLAY);
					meritListReportTo.setAcademicYearPos((short)count++);
				}
			}
		}
		return meritListReportTo;
	}

	/**
	 * @param meritListSelectedTo
	 * @param admMeritListForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public boolean convertToExcel(AdmMeritListReportTo meritListSelectedTo, AdmMeritListForm admMeritListForm, HttpServletRequest request) throws Exception{
		boolean isUpdated=false;
		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.UPLOAD_MERITLIST1);
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		ByteArrayOutputStream bos=null;
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		XSSFCell cell=null;
		
		if(admMeritListForm.getAdmMeritTO()!=null){
			AdmMeritTO to=(AdmMeritTO)admMeritListForm.getAdmMeritTO().get(0);
			int count = 0;
			Iterator<AdmMeritTO> iterator = admMeritListForm.getAdmMeritTO().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Admission MeritList Report");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(meritListSelectedTo.getNameDis()!=null && meritListSelectedTo.getNameDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getNamePos()).setCellValue("Name");
				}
				if(meritListSelectedTo.getApplnoDis()!=null && meritListSelectedTo.getApplnoDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getApplnoPos()).setCellValue("Appln No");
				}
				if(meritListSelectedTo.getReligionDis()!=null && meritListSelectedTo.getReligionDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getReligionPos()).setCellValue("Religion");
				}
				if(meritListSelectedTo.getCategoryDis()!=null && meritListSelectedTo.getCategoryDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getCategoryPos()).setCellValue("Category");
				}
				if(meritListSelectedTo.getCasteDis()!=null && meritListSelectedTo.getCasteDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getCastePos()).setCellValue("Caste");
				}
				if(meritListSelectedTo.getLastInsDis()!=null && meritListSelectedTo.getLastInsDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getLastInstPos()).setCellValue("Last Institute");
				}
				if(meritListSelectedTo.getKarNonKarDis()!=null && meritListSelectedTo.getKarNonKarDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getKarNonKarPos()).setCellValue("Kar NonKar");
				}
				if(meritListSelectedTo.getExamPassedDis()!=null && meritListSelectedTo.getExamPassedDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getExamPassedPos()).setCellValue("Exam Passed");
				}
				if(meritListSelectedTo.getYearPassDis()!=null && meritListSelectedTo.getYearPassDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getYearPassPos()).setCellValue("Year Pass");
				}
				if(meritListSelectedTo.getMonthPassDis()!=null && meritListSelectedTo.getMonthPassDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getMonthPassPos()).setCellValue("Month Pass");
				}
				if(meritListSelectedTo.getNoOfAttemptsDis()!=null && meritListSelectedTo.getNoOfAttemptsDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getNoOfAttemptPos()).setCellValue("No Of Attempts");
				}
				if(meritListSelectedTo.getMediuminstrDis()!=null && meritListSelectedTo.getMediuminstrDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getMediuminstrPos()).setCellValue("Medium Instr");
				}
				if(meritListSelectedTo.getPercentageDis()!=null && meritListSelectedTo.getPercentageDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getPercentagePos()).setCellValue("Percentage");
				}
				if(meritListSelectedTo.getFirstPreferancesDis()!=null && meritListSelectedTo.getFirstPreferancesDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getFirstPreferancesPos()).setCellValue("First Preferance");
				}
				if(meritListSelectedTo.getTotMarksDis()!=null && meritListSelectedTo.getTotMarksDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getTotMarksPos()).setCellValue("Total Marks");
				}
				if(meritListSelectedTo.getMaxMarksDis()!=null && meritListSelectedTo.getMaxMarksDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getMaxMarksPos()).setCellValue("Max Marks");
				}
				if(meritListSelectedTo.getGenderDis()!=null && meritListSelectedTo.getGenderDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getGenderPos()).setCellValue("Gender");
				}
				if(meritListSelectedTo.getAddressDis()!=null && meritListSelectedTo.getAddressDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getAddressPos()).setCellValue("Address");
				}
				if(meritListSelectedTo.getWeightageDis()!=null && meritListSelectedTo.getWeightageDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getWeightagePos()).setCellValue("Weightage");
				}
				if(meritListSelectedTo.getSecondLangDis()!=null && meritListSelectedTo.getSecondLangDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getSecondLangPos()).setCellValue("Second Language");
				}
				if(meritListSelectedTo.getBloodGroupDis()!=null && meritListSelectedTo.getBloodGroupDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getBloodGroupPos()).setCellValue("Blood Group");
				}
				if(meritListSelectedTo.getDobDis()!=null && meritListSelectedTo.getDobDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getDobPOs()).setCellValue("Date Of Birth");
				}
				if(meritListSelectedTo.getNationalityDis()!=null && meritListSelectedTo.getNationalityDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getNationalityPos()).setCellValue("Nationality");
				}
				if(meritListSelectedTo.getSevnYrKarDis()!=null && meritListSelectedTo.getSevnYrKarDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getSevnYrKatPos()).setCellValue("sevnyr kar");
				}
				if(meritListSelectedTo.getPlaceBirthDis()!=null && meritListSelectedTo.getPlaceBirthDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getPlaceBirthPos()).setCellValue("Place Birth");
				}
				if(meritListSelectedTo.getMobileNoDis()!=null && meritListSelectedTo.getMobileNoDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getMobileNoPos()).setCellValue("Mobile No");
				}
				if(meritListSelectedTo.getAcademicYearDis()!=null && meritListSelectedTo.getAcademicYearDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY)){
					row.createCell((short)meritListSelectedTo.getAcademicYearPos()).setCellValue("Academic Year");
				}
				while (iterator.hasNext()) {
					AdmMeritTO meritTO = (AdmMeritTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(meritListSelectedTo.getNameDis()!=null && meritListSelectedTo.getNameDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getName()!=null){
						row.createCell((short)meritListSelectedTo.getNamePos()).setCellValue(meritTO.getName());
					}
					if(meritListSelectedTo.getApplnoDis()!=null && meritListSelectedTo.getApplnoDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getAppNo()!=null){
						row.createCell((short)meritListSelectedTo.getApplnoPos()).setCellValue(meritTO.getAppNo());
					}
					if(meritListSelectedTo.getReligionDis()!=null && meritListSelectedTo.getReligionDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getReligion()!=null){
						row.createCell((short)meritListSelectedTo.getReligionPos()).setCellValue(meritTO.getReligion());
					}
					if(meritListSelectedTo.getCategoryDis()!=null && meritListSelectedTo.getCategoryDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getScStScbt()!=null){
						row.createCell((short)meritListSelectedTo.getCategoryPos()).setCellValue(meritTO.getScStScbt());
					}
					if(meritListSelectedTo.getCasteDis()!=null && meritListSelectedTo.getCasteDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getCthPrOth()!=null){
						row.createCell((short)meritListSelectedTo.getCastePos()).setCellValue(meritTO.getCthPrOth());
					}
					if(meritListSelectedTo.getLastInsDis()!=null && meritListSelectedTo.getLastInsDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getLastInstitute()!=null){
						row.createCell((short)meritListSelectedTo.getLastInstPos()).setCellValue(meritTO.getLastInstitute());
					}
					if(meritListSelectedTo.getKarNonKarDis()!=null && meritListSelectedTo.getKarNonKarDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getKarNonkar()!=null){
						row.createCell((short)meritListSelectedTo.getKarNonKarPos()).setCellValue(meritTO.getKarNonkar());
					}
					if(meritListSelectedTo.getExamPassedDis()!=null && meritListSelectedTo.getExamPassedDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getExamPass()!=null){
						row.createCell((short)meritListSelectedTo.getExamPassedPos()).setCellValue(meritTO.getExamPass());
					}
					if(meritListSelectedTo.getYearPassDis()!=null && meritListSelectedTo.getYearPassDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getYearPass()!=null){
						row.createCell((short)meritListSelectedTo.getYearPassPos()).setCellValue(meritTO.getYearPass());
					}
					if(meritListSelectedTo.getMonthPassDis()!=null && meritListSelectedTo.getMonthPassDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getMonthPass()!=null){
						row.createCell((short)meritListSelectedTo.getMonthPassPos()).setCellValue(meritTO.getMonthPass());
					}
					if(meritListSelectedTo.getNoOfAttemptsDis()!=null && meritListSelectedTo.getNoOfAttemptsDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getNoOfAttempts()!=null){
						row.createCell((short)meritListSelectedTo.getNoOfAttemptPos()).setCellValue(meritTO.getNoOfAttempts());
					}
					if(meritListSelectedTo.getMediuminstrDis()!=null && meritListSelectedTo.getMediuminstrDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getMediumInstr()!=null){
						row.createCell((short)meritListSelectedTo.getMediuminstrPos()).setCellValue(meritTO.getMediumInstr());
					}
					if(meritListSelectedTo.getPercentageDis()!=null && meritListSelectedTo.getPercentageDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getPercentage()!=null){
						row.createCell((short)meritListSelectedTo.getPercentagePos()).setCellValue(meritTO.getPercentage().toString());
					}
					if(meritListSelectedTo.getFirstPreferancesDis()!=null && meritListSelectedTo.getFirstPreferancesDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getFirstPreference()!=null){
						row.createCell((short)meritListSelectedTo.getFirstPreferancesPos()).setCellValue(meritTO.getFirstPreference());
					}
					if(meritListSelectedTo.getTotMarksDis()!=null && meritListSelectedTo.getTotMarksDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getTotalMarks()!=null){
						row.createCell((short)meritListSelectedTo.getTotMarksPos()).setCellValue(meritTO.getTotalMarks());
					}
					if(meritListSelectedTo.getMaxMarksDis()!=null && meritListSelectedTo.getMaxMarksDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getMaxMarks()!=null){
						row.createCell((short)meritListSelectedTo.getMaxMarksPos()).setCellValue(meritTO.getMaxMarks());
					}
					if(meritListSelectedTo.getGenderDis()!=null && meritListSelectedTo.getGenderDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getSex()!=null){
						row.createCell((short)meritListSelectedTo.getGenderPos()).setCellValue(meritTO.getSex());
					}
					if(meritListSelectedTo.getAddressDis()!=null && meritListSelectedTo.getAddressDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getAddress1()!=null){
						row.createCell((short)meritListSelectedTo.getAddressPos()).setCellValue(meritTO.getAddress1());
					}
					if(meritListSelectedTo.getWeightageDis()!=null && meritListSelectedTo.getWeightageDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getOverPerWtg()!=null){
						row.createCell((short)meritListSelectedTo.getWeightagePos()).setCellValue(meritTO.getOverPerWtg().toString());
					}
					if(meritListSelectedTo.getSecondLangDis()!=null && meritListSelectedTo.getSecondLangDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getSecondLanguage()!=null){
						row.createCell((short)meritListSelectedTo.getSecondLangPos()).setCellValue(meritTO.getSecondLanguage());
					}
					if(meritListSelectedTo.getBloodGroupDis()!=null && meritListSelectedTo.getBloodGroupDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getBloodGroup()!=null){
						row.createCell((short)meritListSelectedTo.getBloodGroupPos()).setCellValue(meritTO.getBloodGroup());
					}
					if(meritListSelectedTo.getDobDis()!=null && meritListSelectedTo.getDobDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getDob()!=null){
						row.createCell((short)meritListSelectedTo.getDobPOs()).setCellValue(meritTO.getDob());
					}
					if(meritListSelectedTo.getNationalityDis()!=null && meritListSelectedTo.getNationalityDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getNationality()!=null){
						row.createCell((short)meritListSelectedTo.getNationalityPos()).setCellValue(meritTO.getNationality());
					}
					if(meritListSelectedTo.getSevnYrKarDis()!=null && meritListSelectedTo.getSevnYrKarDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getSevnyrKar()!=null){
						row.createCell((short)meritListSelectedTo.getSevnYrKatPos()).setCellValue(meritTO.getSevnyrKar());
					}
					if(meritListSelectedTo.getPlaceBirthDis()!=null && meritListSelectedTo.getPlaceBirthDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getPlaceOfBirth()!=null){
						row.createCell((short)meritListSelectedTo.getPlaceBirthPos()).setCellValue(meritTO.getPlaceOfBirth());
					}
					if(meritListSelectedTo.getMobileNoDis()!=null && meritListSelectedTo.getMobileNoDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getMobileNo()!=null){
						row.createCell((short)meritListSelectedTo.getMobileNoPos()).setCellValue(meritTO.getMobileNo());
					}
					if(meritListSelectedTo.getAcademicYearDis()!=null && meritListSelectedTo.getAcademicYearDis().equalsIgnoreCase(AdmMeritHelper.DISPLAY) && meritTO.getAcademicYear()!=null){
						row.createCell((short)meritListSelectedTo.getAcademicYearPos()).setCellValue(meritTO.getAcademicYear());
					}
				}
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				isUpdated=true;
				fos.flush();
				fos.close();
				
			}catch (Exception e) {
				//throw new ApplicationException();
				// TODO: handle exception
			}
		}
		return isUpdated;
}

}
