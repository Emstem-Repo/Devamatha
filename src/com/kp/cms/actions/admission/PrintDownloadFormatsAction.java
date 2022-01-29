package com.kp.cms.actions.admission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction;
import org.apache.struts.actions.DownloadAction.FileStreamInfo;
import org.apache.struts.actions.DownloadAction.StreamInfo;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.DownloadFormatsForm;
import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.InterviewResultTO;

public class PrintDownloadFormatsAction extends DownloadAction {
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	private static final Log log = LogFactory.getLog(PrintDownloadFormatsAction.class);

	/* (non-Javadoc)
	 * Method to download the formats
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@SuppressWarnings("deprecation")
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered PrintDownloadFormatsAction");
		DownloadFormatsForm objForm = (DownloadFormatsForm) form;
		String formatName=null;
		if( objForm.getFormatName()!=null &&  !objForm.getFormatName().isEmpty()){
			formatName= objForm.getFormatName();
		}
		File fCSV= null;
		// To download CSV formats
		if(formatName.trim().equalsIgnoreCase("ApplicationStatusUpdate") || formatName.trim().equalsIgnoreCase("UploadEntranceResult") 
				|| formatName.trim().equalsIgnoreCase("RegisterNoUpload") || formatName.trim().equalsIgnoreCase("AdmissionDataUpload") ||
				formatName.trim().equalsIgnoreCase("MobileNumberUpload") || formatName.trim().equalsIgnoreCase("SecondLanguageUpload")
				|| formatName.trim().equalsIgnoreCase("EmployeeAttendanceUpload") || formatName.trim().equalsIgnoreCase("EmployeeLeaveUpload") 
				|| formatName.trim().equalsIgnoreCase("EmployeePayScaleGradeUpload") || formatName.trim().equalsIgnoreCase("UniversityEmailUpload") 
				|| formatName.trim().equalsIgnoreCase("OMRDataUpload") || formatName.trim().equalsIgnoreCase("UniversityRegisterNo")){
			String fileName = formatName;
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			File excelFile = new File(filePath+fileName + ".xls");
			if(excelFile.exists()){
				excelFile.delete();
			}
			 response.setHeader("Content-disposition", "attachment; filename =" + fileName + ".csv");
			  response.setContentType("application/csv");
			  
			FileOutputStream fos = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet =null;
			HSSFRow row = null;
			 fCSV = new File(filePath+fileName+".csv");
			try	{
				wb = new HSSFWorkbook();
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
				sheet = wb.createSheet(formatName);
				row = sheet.createRow(0);
				// Creating cells in the row and put some data in it.
				if(formatName!=null && formatName.equalsIgnoreCase("ApplicationStatusUpdate")){
				row.createCell((short)0).setCellValue("Application NO");
				row.createCell((short)1).setCellValue("Application Status");
				row.createCell((short)2).setCellValue(" ");
				}
				else if (formatName!=null && formatName.trim().equalsIgnoreCase("UploadEntranceResult")){
					row.createCell((short)0).setCellValue("ApplicationNumber");
					row.createCell((short)1).setCellValue("Percentage");
					row.createCell((short)2).setCellValue("Status");
					row.createCell((short)3).setCellValue("Comments");
					row.createCell((short)4).setCellValue(" ");
				}
			
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("RegisterNoUpload")){
					row.createCell((short)0).setCellValue("ApplicationNumber");
					row.createCell((short)1).setCellValue("RegistrationNumber");
					row.createCell((short)2).setCellValue("ClassName");
					row.createCell((short)3).setCellValue("RollNumber");
					row.createCell((short)4).setCellValue(" ");
				}
	/*			else if(formatName!=null && formatName.trim().equalsIgnoreCase("AdmissionDataUpload")){
					row.createCell((short)0).setCellValue("Challan_NO");
					row.createCell((short)1).setCellValue("Journal_No");
					row.createCell((short)2).setCellValue("Challan_date");
					row.createCell((short)3).setCellValue("Challan_Amount");
					row.createCell((short)4).setCellValue("Bank_BranchCode");
					row.createCell((short)5).setCellValue("Application_Number");
					row.createCell((short)6).setCellValue("Academic_year");
					row.createCell((short)7).setCellValue("Name");
					row.createCell((short)8).setCellValue("Date_of_Birth");
					row.createCell((short)9).setCellValue("Place_of_Birth");
					row.createCell((short)10).setCellValue("Country_of_Birth");
					row.createCell((short)11).setCellValue("State_of_Birth");
					row.createCell((short)12).setCellValue("Nationality");
					row.createCell((short)13).setCellValue("Gender");
					row.createCell((short)14).setCellValue("Is_SportsPerson");
					row.createCell((short)15).setCellValue("Is_Handicapped");
					row.createCell((short)16).setCellValue("Blood_group");
					row.createCell((short)17).setCellValue("resident_category");
					row.createCell((short)18).setCellValue("Religion");
					row.createCell((short)19).setCellValue("Sub-religion");
					row.createCell((short)20).setCellValue("Caste");
					row.createCell((short)21).setCellValue("Area_type");
					row.createCell((short)22).setCellValue("PhoneCountryCode");
					row.createCell((short)23).setCellValue("PhoneAreaCode");
					row.createCell((short)24).setCellValue("Phone");
					row.createCell((short)25).setCellValue("MobileCountryCode");
					row.createCell((short)26).setCellValue("Mobile");
					row.createCell((short)27).setCellValue("email");
					row.createCell((short)28).setCellValue("passportno");
					row.createCell((short)29).setCellValue("passport_country");
					row.createCell((short)30).setCellValue("passport_validity");
					row.createCell((short)31).setCellValue("permanent_Adderss1");
					row.createCell((short)32).setCellValue("permanent_Adderss2");
					row.createCell((short)33).setCellValue("Permanent_address_city");
					row.createCell((short)34).setCellValue("permanent_address_state");
					row.createCell((short)35).setCellValue("permanent_address_country");
					row.createCell((short)36).setCellValue("permanent_address_pincode");
					row.createCell((short)37).setCellValue("current_Adderss1");
					row.createCell((short)38).setCellValue("current_Adderss2");
					row.createCell((short)39).setCellValue("current_address_city");
					row.createCell((short)40).setCellValue("current_address_state");
					row.createCell((short)41).setCellValue("current_address_country");
					row.createCell((short)42).setCellValue("current_address_pincode");
					row.createCell((short)43).setCellValue("University-board");
					row.createCell((short)44).setCellValue("Institute_Name");
					row.createCell((short)45).setCellValue("Max_Marks");
					row.createCell((short)46).setCellValue("obtained_Marks");
					row.createCell((short)47).setCellValue("Year_Of_passing");
					row.createCell((short)48).setCellValue("month_of_passing");
					row.createCell((short)49).setCellValue("No_Of_attempts");
					row.createCell((short)50).setCellValue("Exam_Name");
					row.createCell((short)51).setCellValue("Father_name");
					row.createCell((short)52).setCellValue("Father_Education");
					row.createCell((short)53).setCellValue("father_Income");
					row.createCell((short)54).setCellValue("Father_Occupation");
					row.createCell((short)55).setCellValue("Father_Income_Currencytype");
					row.createCell((short)56).setCellValue("father_email");
					row.createCell((short)57).setCellValue("Mother_name");
					row.createCell((short)58).setCellValue("Mother_Education");
					row.createCell((short)59).setCellValue("Mother_Income");
					row.createCell((short)60).setCellValue("mother_Occupation");
					row.createCell((short)61).setCellValue("Mother_Income_Currencytype");
					row.createCell((short)62).setCellValue("Mother_email");
					row.createCell((short)63).setCellValue("parent_Adderss1");
					row.createCell((short)64).setCellValue("parent_Adderss2");
					row.createCell((short)65).setCellValue("parent_Adderss3");
					row.createCell((short)66).setCellValue("parent_address_city");
					row.createCell((short)67).setCellValue("Parent_address_state");
					row.createCell((short)68).setCellValue("parent_address_country");
					row.createCell((short)69).setCellValue("parent_address_pincode");
					row.createCell((short)70).setCellValue("Parent_PhoneCountryCode");
					row.createCell((short)71).setCellValue("Parent_PhoneAreaCode");				
					row.createCell((short)72).setCellValue("Parent_Phone");
					row.createCell((short)73).setCellValue("parent_MobileCountryCode");
					row.createCell((short)74).setCellValue("parent_Mobile");
					row.createCell((short)75).setCellValue("Registration_Number");
					row.createCell((short)76).setCellValue("Roll_No");
					row.createCell((short)77).setCellValue("Class_Name");
					row.createCell((short)78).setCellValue("admitted_through");
					row.createCell((short)79).setCellValue("Is_Freeship");
					row.createCell((short)80).setCellValue("Subject_group1");
					row.createCell((short)81).setCellValue("Subject_group2");
					row.createCell((short)82).setCellValue("Subject_group3");
					row.createCell((short)83).setCellValue("Subject_group4");
					row.createCell((short)84).setCellValue("Subject_group5");
					row.createCell((short)85).setCellValue("Admission_date");
					row.createCell((short)86).setCellValue("Second_Language");
					row.createCell((short)87).setCellValue(" ");
				}**/
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("MobileNumberUpload")){
					row.createCell((short)0).setCellValue("RegisterNo");
					row.createCell((short)1).setCellValue("MobileCountryCode");
					row.createCell((short)2).setCellValue("Mobile");
					row.createCell((short)3).setCellValue("ParentMobileCountryCode");
					row.createCell((short)4).setCellValue("ParentMobile");
					row.createCell((short)5).setCellValue("PhoneCountryCode");
					row.createCell((short)6).setCellValue("PhoneAreaCode");
					row.createCell((short)7).setCellValue("Phone");
					row.createCell((short)8).setCellValue("ParentPhoneCountryCode");
					row.createCell((short)9).setCellValue("ParentPhoneAreaCode");
					row.createCell((short)10).setCellValue("ParentPhone");
					row.createCell((short)11).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("SecondLanguageUpload")){
					row.createCell((short)0).setCellValue("RegisterNo");
					row.createCell((short)1).setCellValue("SecondLanguage");
					row.createCell((short)2).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("EmployeeAttendanceUpload")){
					row.createCell((short)0).setCellValue("fingerPrintId");
					row.createCell((short)1).setCellValue("TerminalId");
					row.createCell((short)2).setCellValue("Date");
					row.createCell((short)3).setCellValue("time");
					row.createCell((short)4).setCellValue("status");
					row.createCell((short)5).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("EmployeeLeaveUpload")){
					row.createCell((short)0).setCellValue("Leave_Type");
					row.createCell((short)1).setCellValue("FingerPrint_Id");
					row.createCell((short)2).setCellValue("From_Date");
					row.createCell((short)3).setCellValue("To_Date");
					row.createCell((short)4).setCellValue("Is_Half_Day");
					row.createCell((short)5).setCellValue("Is_Am");
					row.createCell((short)6).setCellValue("Year");
					row.createCell((short)7).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("EmployeePayScaleGradeUpload")){
					row.createCell((short)0).setCellValue("EmployeeID");
					row.createCell((short)1).setCellValue("grade");
					row.createCell((short)2).setCellValue("BASIC");
					row.createCell((short)3).setCellValue("MRA");
					row.createCell((short)4).setCellValue("CA");
					row.createCell((short)5).setCellValue("HRA");
					row.createCell((short)6).setCellValue("OT");
					row.createCell((short)7).setCellValue("GP");
					row.createCell((short)8).setCellValue("CON");
					row.createCell((short)9).setCellValue("ARA");
					row.createCell((short)10).setCellValue("CEA");
					row.createCell((short)11).setCellValue("SA");
					row.createCell((short)12).setCellValue("REA");
					row.createCell((short)13).setCellValue("DA");
					row.createCell((short)14).setCellValue("Gross");
					row.createCell((short)15).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("UniversityEmailUpload")){
					row.createCell((short)0).setCellValue("RegisterNo");
					row.createCell((short)1).setCellValue("UniversityEmail");
					row.createCell((short)2).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("OMRDataUpload")){
					row.createCell((short)0).setCellValue("STUDENT_NAME");
					row.createCell((short)1).setCellValue("APPLN_NO");
					row.createCell((short)2).setCellValue("DATE");
					row.createCell((short)3).setCellValue("MONTH");
					row.createCell((short)4).setCellValue("YEAR");
					row.createCell((short)5).setCellValue("QUALIFYING_EXAM");
					row.createCell((short)6).setCellValue("YEAR_OF_PASSING");
					row.createCell((short)7).setCellValue("NUMBER_OF_ATTEMPTS");
					row.createCell((short)8).setCellValue("INSTITUTION_LAST_STUDIED");
					row.createCell((short)9).setCellValue("DOMICILE/STATUS");
					row.createCell((short)10).setCellValue("NATIONALITY");
					row.createCell((short)11).setCellValue("COURSE_APPLIED");
					row.createCell((short)12).setCellValue("SECOND_LANGUAGE");
					row.createCell((short)13).setCellValue("GENDER");
					row.createCell((short)14).setCellValue("RELIGION");
					row.createCell((short)15).setCellValue("CASTE/CATEGORY");
					row.createCell((short)16).setCellValue("BLOOD_GROUP");
					row.createCell((short)17).setCellValue("MAXIMUM_MARKS");
					row.createCell((short)18).setCellValue("MARKS_OBTAINED");
					row.createCell((short)19).setCellValue("MOBILE");
					row.createCell((short)20).setCellValue("FIRST_LINE_OF_ADDRESS");
					row.createCell((short)21).setCellValue("SECOND_LINE_OF_ADDRESS");
					row.createCell((short)22).setCellValue("THIRD_LINE_OF_ADDRESS");
					row.createCell((short)23).setCellValue("FOURTH_LINE_OF_ADDRESS");
					row.createCell((short)24).setCellValue("PIN_CODE");
					row.createCell((short)25).setCellValue(" ");
				}
				else if(formatName != null && formatName.trim().equalsIgnoreCase("UniversityRegisterNo")){
					row.createCell((short)0).setCellValue("Application_no");
					row.createCell((short)1).setCellValue("Register_no");
					row.createCell((short)2).setCellValue("University_Regno");
					row.createCell((short)3).setCellValue(" ");
				}
				
				fos = new FileOutputStream(excelFile,true);
				wb.write(fos);
				fos.flush();
				fos.close();
				
				OutputStream os = (OutputStream)new FileOutputStream(fCSV);
				
				String encoding = "UTF8";
				OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
				BufferedWriter bWriter = new BufferedWriter(osw);

				//Excel document to be imported
				WorkbookSettings ws = new WorkbookSettings();
				ws.setLocale(new Locale("en", "EN"));
				Workbook w = Workbook.getWorkbook(excelFile,ws);

				// Gets the sheets from workbook
				for (int scount = 0; scount < w.getNumberOfSheets(); scount++)
				{
				Sheet s = w.getSheet(scount);


				Cell[] rowcsv = null;

				// Gets the cells from sheet
				for (int i = 0 ; i < s.getRows() ; i++)
				{
					rowcsv = s.getRow(i);

				if (rowcsv.length >= 0)
				{
				for (int j = 0; j < rowcsv.length; j++)
				{
				
					bWriter.write(rowcsv[j].getContents());
					bWriter.write(',');
				}
				}
				bWriter.newLine();
				}
				}
				
				bWriter.flush();
				bWriter.close();	
				
			      File file1 = new File(filePath+fileName+".csv");
			      FileInputStream fileIn = new FileInputStream(file1);		
			      
			       
			      byte[] outputByte = new byte[fileIn.available()];
			      fileIn.read(outputByte);
			      HttpSession session = request.getSession();
			      session.setAttribute(CMSConstants.CSV_BYTES, outputByte);
				
				
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	// to download excel format 
		else if(formatName.trim().equalsIgnoreCase("UploadFinalMeritList") || formatName.trim().equalsIgnoreCase("UploadInterviewStatus")
				|| formatName.trim().equalsIgnoreCase("SmartCardNumberUpload") || formatName.trim().equalsIgnoreCase("EmployeeSmartCardNumberUpload")
				||formatName.trim().equalsIgnoreCase("UploadMarks") || formatName.trim().equalsIgnoreCase("UploadBlockHallticketOrMarks")
				|| formatName.trim().equalsIgnoreCase("UploadRoomDetails") || formatName.trim().equalsIgnoreCase("SAPMarksUploadDetails")
				|| formatName.trim().equalsIgnoreCase("UploadOfflineApplicationDetails") || formatName.trim().equalsIgnoreCase("ReadmissionSelection")){
			String excelfileName = formatName+ Calendar.getInstance().getTimeInMillis();
			String filePath=request.getRealPath("");
	    	filePath = filePath + "//TempFiles//";
			 fCSV = new File(filePath+excelfileName+".xls");
			if(fCSV.exists()){
				fCSV.delete();
			}
			 response.setHeader("Content-disposition", "attachment; filename =" + excelfileName + ".xls");
			  response.setContentType("application/xls");
			  
			FileOutputStream csvfos = null;
			HSSFWorkbook wb = null;
			HSSFSheet sheet =null;
			HSSFRow row = null;
			
			try	{
				wb = new HSSFWorkbook();
				HSSFCellStyle cellStyle = wb.createCellStyle();
				cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
				sheet = wb.createSheet(formatName);
				row = sheet.createRow(0);
				// Creating cells in the row and put some data in it.
				if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadFinalMeritList")){
				row.createCell((short)0).setCellValue("Application Number");
				row.createCell((short)1).setCellValue("Status");
				row.createCell((short)2).setCellValue("Remarks");
				row.createCell((short)3).setCellValue("Course Code");
				row.createCell((short)4).setCellValue("AdmissionScheduledDate");
				row.createCell((short)5).setCellValue("AdmissionScheduledTime");
				row.createCell((short)6).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadInterviewStatus")){
					row.createCell((short)0).setCellValue("ApplicationNumber");
					row.createCell((short)1).setCellValue("Status");
					row.createCell((short)2).setCellValue("Date");
					row.createCell((short)3).setCellValue("Time");
					row.createCell((short)4).setCellValue("Venue");
					row.createCell((short)5).setCellValue(" ");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("SmartCardNumberUpload")){
					row.createCell((short)0).setCellValue("SL.NO.");
					row.createCell((short)1).setCellValue("REG.NO.");
					row.createCell((short)2).setCellValue("Account no");
					row.createCell((short)3).setCellValue("CHN");
					row.createCell((short)4).setCellValue("NAME");
					row.createCell((short)5).setCellValue(" ");
					
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("EmployeeSmartCardNumberUpload")){
					row.createCell((short)0).setCellValue("Staff ID.");
					row.createCell((short)1).setCellValue("Account No.");
					row.createCell((short)2).setCellValue("Card No.");
					row.createCell((short)3).setCellValue("Name");
				}
				else if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadMarks")){
					row.createCell((short)0).setCellValue("RegisterNo");
					row.createCell((short)1).setCellValue("Marks 1");
					row.createCell((short)2).setCellValue(" ");
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadBlockHallticketOrMarks")){
					row.createCell((short)0).setCellValue("Class");
					row.createCell((short)1).setCellValue("Register No");
					row.createCell((short)2).setCellValue("Reason");
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadRoomDetails")){
					row.createCell((short)0).setCellValue("Register Number/Appln.No");
					row.createCell((short)1).setCellValue("Block");
					row.createCell((short)2).setCellValue("Unit");
					row.createCell((short)3).setCellValue("Room Number");
					row.createCell((short)4).setCellValue("Bed Number");
					row.createCell((short)5).setCellValue("Biometric Id");
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("SAPMarksUploadDetails")){
					row.createCell((short)0).setCellValue("Register No");
					row.createCell((short)1).setCellValue("Marks");
					row.createCell((short)2).setCellValue("Status");
					
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("UploadOfflineApplicationDetails")){
					row.createCell((short)0).setCellValue("Register No");
					row.createCell((short)1).setCellValue("Room Type");
					row.createCell((short)2).setCellValue("Application No");
					
				}else if(formatName!=null && formatName.trim().equalsIgnoreCase("ReadmissionSelection")){
					row.createCell((short)0).setCellValue("Register No");
					row.createCell((short)1).setCellValue("Room Type");
				}
				csvfos = new FileOutputStream(fCSV,true);
				wb.write(csvfos);
				csvfos.flush();
				csvfos.close();
		}
			catch(Exception e){
				e.printStackTrace();
			}
	}
		
		return new FileStreamInfo("write.csv", fCSV);
	}
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
