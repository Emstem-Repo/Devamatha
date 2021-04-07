package com.kp.cms.handlers.admission;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Grade;
import com.kp.cms.bo.admin.InterviewProgramCourse;
import com.kp.cms.bo.admin.InterviewResult;
import com.kp.cms.bo.admin.InterviewResultDetail;
import com.kp.cms.bo.admin.InterviewStatus;
import com.kp.cms.bo.admin.InterviewSubRounds;
import com.kp.cms.bo.admin.StudentSpecializationPrefered;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admission.InterviewResultEntryForm;
import com.kp.cms.to.admin.GradeTO;
import com.kp.cms.to.admission.InterviewResultDetailTO;
import com.kp.cms.to.admission.InterviewResultTO;
import com.kp.cms.transactions.admission.IUploadInterviewResultTransaction;
import com.kp.cms.transactionsimpl.admission.UploadInterviewResultTransactionImpl;
import com.kp.cms.utilities.CommonUtil;

public class UploadInterviewResultHandler {

	private static final Log log = LogFactory.getLog(UploadInterviewResultHandler.class);
	
	/**
	 * This method will return a unique instance of UploadInterviewResultHandler.
	 */
	private static volatile UploadInterviewResultHandler uploadInterviewResult = null;
	private UploadInterviewResultHandler() {
	}

	public static UploadInterviewResultHandler getInstance() {
		
		if (uploadInterviewResult == null) {
			uploadInterviewResult = new UploadInterviewResultHandler();
		}
		return uploadInterviewResult;
	}
	
	/**
	 * This is used to get AdmAppln details(appno, appid) in key-value pair from UploadInterviewResultTransactionImpl.
	 * @param year
	 * @param courseId
	 * @return map.
	 * @throws Exception
	 */
	
	public Map<Integer, Integer> getAdmAppDetails(int year, int courseId) throws Exception {
		log.info("call of getAdmAppDetails method in UploadInterviewResultHandler class.");
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		Map<Integer,Integer> map = transaction.getAdmApplnDetails(year,courseId);
		log.info("end of getAdmAppDetails method in UploadInterviewResultHandler class.");
		return map;
	}
	
	/**
	 * This method is used to get InterviewStatus details(status, statusId) in key-value pair from UploadInterviewResultTransactionImpl.
	 * @return map
	 * @throws Exception
	 */
	
	public Map<String, Integer> getInterviewStatusDetails() throws Exception{
		log.info("call of getInterviewStatusDetails method in UploadInterviewResultHandler class.");
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		log.info("end of getInterviewStatusDetails method in UploadInterviewResultHandler class.");
		return transaction.getInterviewStatusDetails();
	}
	
	/**
	 * /**
	 * This method is used to get Grade details(gradeName, gradeId) in key-value pair from UploadInterviewResultTransactionImpl.
	 * @return map
	 * @throws Exception
	 */

	public Map<String ,Integer> getGradeDetails() throws Exception{
		log.info("call of getGradeDetails method in UploadInterviewResultHandler class.");
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		log.info("end of getGradeDetails method in UploadInterviewResultHandler class.");
		return transaction.getGradeDetails();
	}
	
	
	
	/**
	 * This method will add uploaded file data to BO from TO.
	 * @param interviewResult
	 * @param user
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean addUploadedData(Set<InterviewResultTO> interviewResult, String user,List<StudentSpecializationPrefered> studentSpecializationBoList) throws Exception {
		log.info("call of addUploadedData method in UploadInterviewResultHandler class.");
		boolean isAdded = false;
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		//InterviewResultEntryHelper entryHelper = new InterviewResultEntryHelper();
		List<InterviewResult> interviewList = convertTOtoBO(interviewResult, user);
		List<InterviewResult> iReslutList = new ArrayList<InterviewResult>();
		InterviewResult iResult = null;
		int subRoundId = 0;
		int roundId = 0;
		int admId = 0;
		if(interviewList!=null && !interviewList.isEmpty()){
			 Iterator<InterviewResult> appItr=interviewList.iterator();
			 while (appItr.hasNext()) {
				iResult = (InterviewResult) appItr.next();
				if(iResult != null && iResult.getInterviewSubRounds() != null){
					subRoundId = iResult.getInterviewSubRounds().getId();
				}
				if(iResult != null && iResult.getInterviewProgramCourse() != null){
					roundId = iResult.getInterviewProgramCourse().getId();
				}
				if(iResult != null && iResult.getAdmAppln() != null){
					admId = iResult.getAdmAppln().getId();
				}
				// if all subroundId, roundId and admId are not equal to 0. 
				if(subRoundId !=0 && roundId != 0 && admId != 0){
					if(transaction.checkDuplicate(roundId, admId, subRoundId)){
						int id = transaction.getInterviewResultId(roundId, admId, subRoundId);
							iResult.setId(id);
							iReslutList.add(iResult);
					}
					else{
						iReslutList.add(iResult);
					}
				}else{
					//if only roundId and admId are present.
					if(!transaction.checkDuplicateWithoutSubRound(roundId, admId)){
						int id = transaction.getInterviewResultId(roundId, admId, subRoundId);
						iResult.setId(id);
						iReslutList.add(iResult);
					}
					else{
						iReslutList.add(iResult);
					}
					}
				}
			}
			if(iReslutList != null && !iReslutList.isEmpty()){
				isAdded = transaction.addUploadData(iReslutList,studentSpecializationBoList,user);
			}
		
		log.info("end of addUploadedData method in UploadInterviewResultHandler class.");
		return isAdded;
	}
	/**
	 * 
	 * @param mainroundId
	 * @param subroundId
	 * @return
	 * @throws Exception
	 */
	public int getInterviewersPerPanel(int mainroundId, int subroundId) throws Exception {
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		int interviewersPerPanel = 	transaction.getInterviewersPerPanel(mainroundId,subroundId);
		return interviewersPerPanel;
	}
	/**
	 * This method is used to convert TO to BO.
	 * @param interviewResultList
	 * @param user
	 * @return list of type InterviewResult.
	 */
	public List<InterviewResult> convertTOtoBO(Set<InterviewResultTO> interviewResultList, String user) throws Exception{
		List<InterviewResult> interviewList = new ArrayList<InterviewResult>();
		InterviewResultTO interviewResultTO;
		Iterator<InterviewResultTO> itr = interviewResultList.iterator();
		while (itr.hasNext()) {
			interviewResultTO = (InterviewResultTO) itr.next();
			InterviewResult interviewResult = new InterviewResult();
			
			if(interviewResultTO!=null && interviewResultTO.getAdmApplnTO()!=null && interviewResultTO.getInterviewProgramCourseTO()!=null){
				if(interviewResultTO != null && interviewResultTO.getAdmApplnTO() != null){
					AdmAppln admAppln = new AdmAppln();
					admAppln.setId(interviewResultTO.getAdmApplnTO().getId());
					interviewResult.setAdmAppln(admAppln);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewProgramCourseTO() != null){
					InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
					interviewProgramCourse.setId(interviewResultTO.getInterviewProgramCourseTO().getId());
					interviewResult.setInterviewProgramCourse(interviewProgramCourse);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewSubroundId() != 0){
					InterviewSubRounds interviewSubRounds = new InterviewSubRounds();
					interviewSubRounds.setId(interviewResultTO.getInterviewSubroundId());
					interviewResult.setInterviewSubRounds(interviewSubRounds);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewStatusTO() != null){
					InterviewStatus interviewStatus = new InterviewStatus();
					interviewStatus.setId(interviewResultTO.getInterviewStatusTO().getId());
					interviewResult.setInterviewStatus(interviewStatus);
				}
				
				HashSet<InterviewResultDetail> interviewResultDetailSet = new LinkedHashSet<InterviewResultDetail>();
				if(interviewResultTO != null && interviewResultTO.getInterviewResultDetail()!=null && !interviewResultTO.getInterviewResultDetail().isEmpty()){
					InterviewResultDetail interviewResultDetail;
					Iterator<InterviewResultDetailTO> iterator = interviewResultTO.getInterviewResultDetail().iterator();
					while (iterator.hasNext()) {
						interviewResultDetail = new InterviewResultDetail();
						InterviewResultDetailTO interviewResultDetailTO = (InterviewResultDetailTO) iterator.next();
						Grade grade=new Grade();
						grade.setId(interviewResultDetailTO.getGradeObtainedId());
						interviewResultDetail.setGrade(grade);
						interviewResultDetail.setComments(interviewResultDetailTO.getComments());
						interviewResultDetailSet.add(interviewResultDetail);
					}
				
				}
				
				
				if(interviewResultDetailSet != null && !interviewResultDetailSet.isEmpty()){
					interviewResult.setInterviewResultDetails(interviewResultDetailSet);
				}
				
				interviewResult.setCreatedBy(user);
		//		interviewResult.setComments(interviewResultTO.getComments());
				interviewResult.setCreatedDate(new Date());
				interviewResult.setModifiedBy(user);
				interviewResult.setLastModifiedDate(new Date());
				interviewResult.setIsActive(true);
			
				interviewList.add(interviewResult);
			}
		}
		return interviewList;
	}
	

	/**
	 * This method will add uploaded file data to BO from TO.
	 * @param interviewResult
	 * @param user
	 * @return boolean value.
	 * @throws Exception
	 */
	
	public boolean addEntranceUploadedData(Set<InterviewResultTO> interviewResult, String user) throws Exception {
		log.info("call of addEntranceUploadedData method in UploadInterviewResultHandler class.");
		boolean isAdded = false;
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		List<InterviewResult> interviewList = convertEntranceTOtoBO(interviewResult, user);
		List<InterviewResult> iReslutList = new ArrayList<InterviewResult>();
		InterviewResult iResult = null;
		int subRoundId = 0;
		int roundId = 0;
		int admId = 0;
		if(interviewList!=null && !interviewList.isEmpty()){
			 Iterator<InterviewResult> appItr=interviewList.iterator();
			 while (appItr.hasNext()) {
				iResult = (InterviewResult) appItr.next();
				if(iResult != null && iResult.getInterviewSubRounds() != null){
					subRoundId = iResult.getInterviewSubRounds().getId();
				}
				if(iResult != null && iResult.getInterviewProgramCourse() != null){
					roundId = iResult.getInterviewProgramCourse().getId();
				}
				if(iResult != null && iResult.getAdmAppln() != null){
					admId = iResult.getAdmAppln().getId();
				}
				// if all subroundId, roundId and admId are not equal to 0. 
				if(subRoundId !=0 && roundId != 0 && admId != 0){
					if(transaction.checkDuplicate(roundId, admId, subRoundId)){
						int id = transaction.getInterviewResultId(roundId, admId, subRoundId);
						
						int detailId = transaction.getEntranceIntResultDetailId(id);
						if(detailId > 0){
							Set<InterviewResultDetail> intResultDetail = iResult.getInterviewResultDetails();
							Iterator<InterviewResultDetail> intIt =  intResultDetail.iterator();
							InterviewResultDetail interviewResultDetail = new InterviewResultDetail();
//							InterviewResult interviewResult2 = new InterviewResult();
							while (intIt.hasNext()) {
								interviewResultDetail = intIt.next();
								interviewResultDetail.setId(detailId);
//								interviewResult2.setId(id);
//								interviewResultDetail.setInterviewResult(interviewResult2);
								
							}
							intResultDetail = new HashSet<InterviewResultDetail>();
							intResultDetail.add(interviewResultDetail);
							iResult.setInterviewResultDetails(intResultDetail);
						}
						
							iResult.setId(id);
							iReslutList.add(iResult);
					}
					else{
						iReslutList.add(iResult);
					}
				}else{
					//if only roundId and admId are present.
					if(!transaction.checkDuplicateWithoutSubRound(roundId, admId)){
						int id = transaction.getInterviewResultId(roundId, admId, subRoundId);
						int detailId = transaction.getEntranceIntResultDetailId(id);
						if(detailId > 0){
							Set<InterviewResultDetail> intResultDetail = iResult.getInterviewResultDetails();
							Iterator<InterviewResultDetail> intIt =  intResultDetail.iterator();
							InterviewResultDetail interviewResultDetail = new InterviewResultDetail();
							while (intIt.hasNext()) {
								interviewResultDetail = intIt.next();
								interviewResultDetail.setId(detailId);
								
							}
							intResultDetail = new HashSet<InterviewResultDetail>();
							intResultDetail.add(interviewResultDetail);
							iResult.setInterviewResultDetails(intResultDetail);
						}
						iResult.setId(id);
						iReslutList.add(iResult);
					}
					else{
						iReslutList.add(iResult);
					}
					}
				}
			}
			if(iReslutList != null && !iReslutList.isEmpty()){
				isAdded = transaction.addEntranceUploadData(iReslutList);
			}
		
		log.info("end of addUploadedData method in UploadInterviewResultHandler class.");
		return isAdded;
	}
	
	/**
	 * This method is used to convert TO to BO.
	 * @param interviewResultList
	 * @param user
	 * @return list of type InterviewResult.
	 */
	public List<InterviewResult> convertEntranceTOtoBO(Set<InterviewResultTO> interviewResultList, String user) throws Exception{
		List<InterviewResult> interviewList = new ArrayList<InterviewResult>();
		InterviewResultTO interviewResultTO;
		Iterator<InterviewResultTO> itr = interviewResultList.iterator();
		while (itr.hasNext()) {
			interviewResultTO = (InterviewResultTO) itr.next();
			InterviewResult interviewResult = new InterviewResult();
			
			if(interviewResultTO!=null && interviewResultTO.getAdmApplnTO()!=null && interviewResultTO.getInterviewProgramCourseTO()!=null){
				if(interviewResultTO != null && interviewResultTO.getAdmApplnTO() != null){
					AdmAppln admAppln = new AdmAppln();
					admAppln.setId(interviewResultTO.getAdmApplnTO().getId());
					interviewResult.setAdmAppln(admAppln);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewProgramCourseTO() != null){
					InterviewProgramCourse interviewProgramCourse = new InterviewProgramCourse();
					interviewProgramCourse.setId(interviewResultTO.getInterviewProgramCourseTO().getId());
					interviewResult.setInterviewProgramCourse(interviewProgramCourse);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewSubroundId() != 0){
					InterviewSubRounds interviewSubRounds = new InterviewSubRounds();
					interviewSubRounds.setId(interviewResultTO.getInterviewSubroundId());
					interviewResult.setInterviewSubRounds(interviewSubRounds);
				}
				if(interviewResultTO != null && interviewResultTO.getInterviewStatusTO() != null){
					InterviewStatus interviewStatus = new InterviewStatus();
					interviewStatus.setId(interviewResultTO.getInterviewStatusTO().getId());
					interviewResult.setInterviewStatus(interviewStatus);
				}
				
				HashSet<InterviewResultDetail> interviewResultDetailSet = new LinkedHashSet<InterviewResultDetail>();
				if(interviewResultTO != null && interviewResultTO.getPercentage() != null && !interviewResultTO.getPercentage().trim().isEmpty()){
					InterviewResultDetail interviewResultDetail = new InterviewResultDetail();
					if(interviewResultTO.getPercentage()!= null && !interviewResultTO.getPercentage().trim().isEmpty()){
						interviewResultDetail.setPercentage(new BigDecimal(interviewResultTO.getPercentage()));
						interviewResultDetailSet.add(interviewResultDetail);
					}
				}
				if(interviewResultDetailSet != null && !interviewResultDetailSet.isEmpty()){
					interviewResult.setInterviewResultDetails(interviewResultDetailSet);
				}
				
				interviewResult.setCreatedBy(user);
				interviewResult.setComments(interviewResultTO.getComments());
				interviewResult.setCreatedDate(new Date());
				interviewResult.setModifiedBy(user);
				interviewResult.setLastModifiedDate(new Date());
				interviewResult.setIsActive(true);
			
				interviewList.add(interviewResult);
			}
		}
		return interviewList;
	}

	public InterviewResultTO convertFormtoTo(InterviewResultEntryForm interviewResultEntryForm) throws Exception {
		
		InterviewResultTO interviewBatchEntryTO =  new InterviewResultTO();
		
		interviewBatchEntryTO.setAppliedYear(interviewResultEntryForm.getApplicationYear());
		interviewBatchEntryTO.setCourseId(interviewResultEntryForm.getCourseId());
		interviewBatchEntryTO.setInterviewTypeId(Integer.parseInt(interviewResultEntryForm.getInterviewTypeId()));
		if(interviewResultEntryForm.getInterviewSubroundId()!= null && !interviewResultEntryForm.getInterviewSubroundId().isEmpty() && Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId())!= 0){
			interviewBatchEntryTO.setInterviewSubroundId(Integer.parseInt(interviewResultEntryForm.getInterviewSubroundId()));
		}
		interviewBatchEntryTO.setInterviewDate(interviewResultEntryForm.getInterviewDate());
		interviewBatchEntryTO.setStartTimeHours(interviewResultEntryForm.getStartingTimeHours());
		interviewBatchEntryTO.setStartTimeMins(interviewResultEntryForm.getStartingTimeMins());
		interviewBatchEntryTO.setEndTimeHours(interviewResultEntryForm.getEndingTimeHours());
		interviewBatchEntryTO.setEndTimeMins(interviewResultEntryForm.getEndingTimeMins());
		
		return interviewBatchEntryTO;
	}

	public byte[] exportToCSV(int interviewersPerPanel,List<InterviewResultTO> selectedCandidates) throws Exception {
		Properties prop = new Properties();
		 byte[] fileData=null;
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of CandidateHelper ",e);
			throw new IOException(e);
		}
		String fileName=prop.getProperty(CMSConstants.EXCEL_DESTINATION);
		File excelFile = new File(fileName);	
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		HSSFCell cell = null;
		
		if(selectedCandidates!=null){
		int count = 1;
		Iterator<InterviewResultTO> iterator = selectedCandidates.iterator();
		
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("MMM/dd/yyyy"));
			sheet = wb.createSheet("Upload Interview Result");
			row = sheet.createRow(0);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			row.createCell((short)0).setCellValue("Application Number");
			for(int i=1;i<=interviewersPerPanel ;i++){
				row.createCell((short)i).setCellValue("grade");
				count=count+1;
			}
			row.createCell((short)(interviewersPerPanel+1)).setCellValue("Status");
			row.createCell((short)(interviewersPerPanel+2)).setCellValue("Comments");
			
			count=0;
			while (iterator.hasNext()) {
				InterviewResultTO candidateSearch = (InterviewResultTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);				
				row.createCell((short)0).setCellValue(candidateSearch.getApplicationNo());
			}
			
			fos = new FileOutputStream(excelFile,true);
			wb.write(fos);
			fos.flush();
			fos.close();
			fileData= wb.getBytes();
		}catch(Exception e){
			//log

		}
		}
		return fileData;
	}
	
	
	
	/**
	 * @param interviewersPerPanel
	 * @param selectedCandidates
	 * @param request
	 * @throws Exception
	 */
	public void exportToCSV(int interviewersPerPanel,List<InterviewResultTO> selectedCandidates,HttpServletRequest request) throws Exception {
		

		Properties prop = new Properties();
		try {
			InputStream inputStream = CommonUtil.class.getClassLoader()
					.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(inputStream);
		} 
		catch (IOException e) {
			log.error("Error occured at exportTOExcel of CandidateHelper ",e);
			throw new IOException(e);
		}
		String destination=prop.getProperty(CMSConstants.EXCEL_DESTINATION);
		String status=prop.getProperty("knowledgepro.admission.upload.interviewresult.status.selected");
		int ip=(interviewersPerPanel*2)+1;
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+destination);	
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		if(selectedCandidates!=null){
			int count = 1;
			int label=1;
			Iterator<InterviewResultTO> iterator = selectedCandidates.iterator();
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Admission Report");
			row = sheet.createRow(0);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			row.createCell((short)0).setCellValue("ApplicationNumber");
			for(int i=1;i<=interviewersPerPanel ;i++){
				row.createCell((short)i).setCellValue("Grade"+i);
				count=count+1;
			}
			for(int i=interviewersPerPanel+1;i<=(interviewersPerPanel*2) ;i++){
				row.createCell((short)i).setCellValue("Comments"+label);
				label++;
				
			}
			row.createCell((short)((interviewersPerPanel*2)+1)).setCellValue("Status");
			row.createCell((short)((interviewersPerPanel*2)+2)).setCellValue("SpecializationPrefered");
			row.createCell((short)((interviewersPerPanel*2)+3)).setCellValue("BackLogs");
			row.createCell((short)((interviewersPerPanel*2)+4)).setCellValue("");
			
			count=0;
			while (iterator.hasNext()) {
				InterviewResultTO candidateSearch = (InterviewResultTO) iterator.next();
				count = count +1;
				row = sheet.createRow(count);				
				row.createCell((short)0).setCellValue(candidateSearch.getApplicationNo());
				row.createCell((short)ip).setCellValue(status);
			}
			fos = new FileOutputStream(excelFile,true);
			wb.write(fos);
			fos.flush();
			fos.close();
			
			String csvdestination = prop.getProperty(CMSConstants.CSV_DESTINATION);
			//File to store data in form of CSV
			File fCSV = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);

			OutputStream os = (OutputStream)new FileOutputStream(fCSV);
			
			String encoding = "UTF8";
			OutputStreamWriter osw = new OutputStreamWriter(os, encoding);
			BufferedWriter bWriter = new BufferedWriter(osw);

			//Excel document to be imported
			WorkbookSettings ws = new WorkbookSettings();
			ws.setLocale(new Locale("en", "EN"));
			Workbook w = Workbook.getWorkbook(new File(request.getRealPath("")+ "//TempFiles//"+destination),ws);

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
			
		      File file1 = new File(request.getRealPath("")+ "//TempFiles//"+csvdestination);
		      FileInputStream fileIn = new FileInputStream(file1);		
		      
		       
		      byte[] outputByte = new byte[fileIn.available()];
		      fileIn.read(outputByte);
		      HttpSession session = request.getSession();
		      session.setAttribute(CMSConstants.CSV_BYTES, outputByte);
	
		}catch(Exception e){
			
		}
		}
	}

	/**
	 * @param applicationYear
	 * @param courseIds
	 * @param interviewResultEntryForm 
	 * @return
	 * @throws Exception 
	 */
	public Map<Integer, Integer> getAdmAppDetailsForSelectedCourses(int applicationYear, String[] courseIds, InterviewResultEntryForm interviewResultEntryForm) throws Exception {
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		List<Integer> coursesList = new ArrayList<Integer>();
		for (int i = 0; i < courseIds.length; i++) {
			if(courseIds[i] != null){
				coursesList.add(Integer.parseInt(courseIds[i]));
			}
		}
		Map<Integer,Integer> map = transaction.getAdmApplnDetailsForSelectedCourses(applicationYear,coursesList,interviewResultEntryForm);
		return map;
	}

	/**
	 * @param interviewResultEntryForm
	 * @return
	 * @throws Exception 
	 */
	public boolean checkSubroundData(InterviewResultEntryForm interviewResultEntryForm) throws Exception {
		IUploadInterviewResultTransaction transaction = new UploadInterviewResultTransactionImpl();
		boolean isSubrountExist = transaction.checkSubroundData(interviewResultEntryForm);
		return isSubrountExist;
	}
}