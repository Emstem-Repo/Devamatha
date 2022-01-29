package com.kp.cms.helpers.pettycash;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.kp.cms.bo.admin.PcAccountHead;
import com.kp.cms.bo.admin.PcReceipts;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.CollectionLedgerForm;
import com.kp.cms.to.pettycash.CollectionLedgerTO;
import com.kp.cms.to.pettycash.ConsolidatedCollectionLedgerTO;
import com.kp.cms.to.pettycash.PcAccountHeadsTO;
import com.kp.cms.utilities.CommonUtil;

public class CollectionLedgerHelper {
	/**
	 * Singleton object of CollectionLedgerHelper
	 */
	private static volatile CollectionLedgerHelper collectionLedgerHelper = null;
	private static final Log log = LogFactory.getLog(CollectionLedgerHelper.class);
	private static final String DISPLAY = "display";
	private CollectionLedgerHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static CollectionLedgerHelper getInstance() {
		if (collectionLedgerHelper == null) {
			collectionLedgerHelper = new CollectionLedgerHelper();
		}
		return collectionLedgerHelper;
	}
	/**
	 * converting the PcAccountHead to PcAccountHeadsTO
	 * @param account
	 * @return
	 */
	public List<PcAccountHeadsTO> convertBOtoTO(List<PcAccountHead> account) {
		List<PcAccountHeadsTO> accountList=new ArrayList<PcAccountHeadsTO>();
		Iterator<PcAccountHead> iterator=account.iterator();
		while (iterator.hasNext()) {
			PcAccountHead pcAccountHead = (PcAccountHead) iterator.next();
			PcAccountHeadsTO pcAccountHeadsTO=new PcAccountHeadsTO();
			pcAccountHeadsTO.setId(pcAccountHead.getId());
			pcAccountHeadsTO.setAccName(pcAccountHead.getAccName());
			pcAccountHeadsTO.setAccCode(pcAccountHead.getAccCode());
			//pcAccountHeadsTO.setName(pcAccountHead.getName());
			accountList.add(pcAccountHeadsTO);
		}
		return accountList;
	}
	
	public String convertBOtoTOCodeName(String accountName) {
		
		String Name="";
		 Name=accountName;
		return Name;
	}
	
	/**
	 * building the query
	 * @param collectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQueryForGroupCode(CollectionLedgerForm collectionLedgerForm,int userId) throws Exception {
//		String commonQuery=commonSearch();
		String searchQuery=" from PcReceipts rec ";
			if(collectionLedgerForm.getGroupCode()!=null && !collectionLedgerForm.getGroupCode().isEmpty()){
				searchQuery=searchQuery+" where rec.isActive=1 and rec.isCancelled=0 and rec.pcAccountHead.pcAccHeadGroup.code= '"+collectionLedgerForm.getGroupCode()+"'";
			}
			if(collectionLedgerForm.getStartDate()!=null && collectionLedgerForm.getEndDate()!=null 
					&& !collectionLedgerForm.getEndDate().isEmpty()&& !collectionLedgerForm.getStartDate().isEmpty())
			{
				searchQuery=searchQuery+" and rec.paidDate between '"+CommonUtil.ConvertStringToSQLDate(collectionLedgerForm.getStartDate())+"'"+" and '"+CommonUtil.ConvertStringToSQLExactDate(collectionLedgerForm.getEndDate())+"'";
			}
			if(userId!=0){
				searchQuery=searchQuery+" and rec.users.id="+userId;
			}
			if(collectionLedgerForm.getGroupCode()!=null){
				searchQuery=searchQuery+" order by rec.pcAccountHead.pcAccHeadGroup.code";
			}
			
		return searchQuery;
	}
	public String getSearchQueryForAccountHeadCode(CollectionLedgerForm collectionLedgerForm,int userId) throws Exception {
//		String commonQuery=commonSearch();
		String searchQuery=" from PcReceipts rec ";
			if(collectionLedgerForm.getAccountCode()!=null && !collectionLedgerForm.getAccountCode().isEmpty()){
				searchQuery=searchQuery+" where rec.isActive=1 and rec.isCancelled=0 and rec.pcAccountHead.accCode= '"+collectionLedgerForm.getAccountCode()+"'";
			}
			if(collectionLedgerForm.getStartDate()!=null && collectionLedgerForm.getEndDate()!=null 
					&& !collectionLedgerForm.getEndDate().isEmpty()&& !collectionLedgerForm.getStartDate().isEmpty())
			{
				searchQuery=searchQuery+" and rec.paidDate between '"+CommonUtil.ConvertStringToSQLDate(collectionLedgerForm.getStartDate())+"'"+" and '"+CommonUtil.ConvertStringToSQLExactDate(collectionLedgerForm.getEndDate())+"'";
			}
			if(userId!=0){
				searchQuery=searchQuery+" and rec.users.id="+userId;
			}
			if(collectionLedgerForm.getAccountCode()!=null){
				searchQuery=searchQuery+" order by rec.pcAccountHead.accCode";
			}
			

		return searchQuery;
	}
	/**
	 * common query for search
	 * @return
	 */
	private String commonSearch() {
		String commonQuery="select rec.paidDate,rec.number," +
				"rec.student.admAppln.applnNo," +
				"rec.student.registerNo," +
				"rec.student.rollNo,rec.name," +
				"rec.student.admAppln.courseBySelectedCourseId.program.name," +
				"rec.amount";
		return commonQuery;
	}
	/**
	 * converting list of objects into tos
	 * @param listofStudents
	 * @return
	 */
	public List<CollectionLedgerTO> convertListOfBOtoTOs(List listofStudents,CollectionLedgerForm collectionLedgerForm) throws Exception{
		List<CollectionLedgerTO>  list=new ArrayList<CollectionLedgerTO>();
		Iterator iterator=listofStudents.iterator();
		String dateTime="";
		String date="";
		String time="";
		String applNo="";
		String rollNo="";
		String regNo="";
		double amount;
		double totalAmount=0.0;
		while (iterator.hasNext()) {
			Object[] object = (Object[]) iterator.next();
			CollectionLedgerTO collectionLedgerTO=new CollectionLedgerTO();
			if(object[0]!=null){
				dateTime=object[0].toString();
				date=dateTime.substring(0,10);
				time=dateTime.substring(12,19);
				collectionLedgerTO.setDate(date);
				collectionLedgerTO.setTime(time);
			}
			if(object[1]!=null){
				collectionLedgerTO.setReceiptNumber(object[1].toString());
			}
			if(object[2]!=null){
				applNo=object[2].toString();
			}
			if(object[3]!=null){
				rollNo=object[3].toString();
			}
			if(object[4]!=null){
				regNo=object[4].toString();
			}
			//collectionLedgerTO.setApplRegRollNo(applNo+"/"+regNo+"/"+rollNo);
			collectionLedgerTO.setApplNo(applNo);
			collectionLedgerTO.setRollNo(rollNo);
			collectionLedgerTO.setRegNo(regNo);
			
			if(object[5]!=null){
				collectionLedgerTO.setName(object[5].toString());
			}
			if(object[6]!=null){
				collectionLedgerTO.setCourse(object[6].toString());
			}
			if(object[7]!=null){
				amount=Double.parseDouble(object[7].toString());
				totalAmount=totalAmount+amount;
				collectionLedgerTO.setAmount(object[7].toString()); 
			}
			list.add(collectionLedgerTO);
		}
		collectionLedgerForm.setTotalAmount(totalAmount);
		return list;
	}
	/**
	 * converting list of objects into tos
	 * @param listofStudents
	 * @return
	 */
	public List<CollectionLedgerTO> convertListOfBOtoTOs1(List listofStudents,CollectionLedgerForm collectionLedgerForm) throws Exception{
		List<CollectionLedgerTO>  list=new ArrayList<CollectionLedgerTO>();
		Iterator iterator=listofStudents.iterator();
		String dateTime="";
		String date="";
		String time="";
		String applNo="";
		String rollNo="";
		String regNo="";
		double amount;
		double totalAmount=0.0;
		while (iterator.hasNext()) {
			PcReceipts pcReceipts = (PcReceipts) iterator.next();
			CollectionLedgerTO collectionLedgerTO=new CollectionLedgerTO();
			if(pcReceipts.getPaidDate()!=null){
				dateTime=pcReceipts.getPaidDate().toString();
				date=dateTime.substring(0,10);
				time=dateTime.substring(12,19);
				collectionLedgerTO.setDate(date);
				collectionLedgerTO.setTime(time);
			}
			if(pcReceipts.getNumber()>0){
				collectionLedgerTO.setReceiptNumber(Integer.toString(pcReceipts.getNumber()));
			}
			if(pcReceipts.getStudent()!=null){
				if(pcReceipts.getStudent().getAdmAppln()!=null){
				applNo=Integer.toString(pcReceipts.getStudent().getAdmAppln().getApplnNo());
			/*	if(pcReceipts.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram()!=null)
					collectionLedgerTO.setCourse(pcReceipts.getStudent().getAdmAppln().getCourseBySelectedCourseId().getProgram().getName());
				}*/
				if(pcReceipts.getStudent().getClassSchemewise()!=null && pcReceipts.getStudent().getClassSchemewise().getClasses()!=null)
					collectionLedgerTO.setClassName(pcReceipts.getStudent().getClassSchemewise().getClasses().getName());
				}
				if(pcReceipts.getStudent().getRollNo()!=null){
					rollNo=pcReceipts.getStudent().getRollNo();
				}
				if(pcReceipts.getStudent().getRegisterNo()!=null){
					regNo=pcReceipts.getStudent().getRegisterNo();
				}
				//collectionLedgerTO.setApplRegRollNo(applNo+"/"+regNo+"/"+rollNo);
				collectionLedgerTO.setApplNo(applNo);
				collectionLedgerTO.setRollNo(rollNo);
				collectionLedgerTO.setRegNo(regNo);
			}
			if(pcReceipts.getName()!=null){
				collectionLedgerTO.setName(pcReceipts.getName().trim());
			}
			
			if(pcReceipts.getAmount()!=null){
				amount=Double.parseDouble(pcReceipts.getAmount().toString());
				totalAmount=totalAmount+amount;
				collectionLedgerTO.setAmount(pcReceipts.getAmount().toString()); 
			}
			list.add(collectionLedgerTO);
		}
		collectionLedgerForm.setTotalAmount(totalAmount);
		
		return list;
	}
	public CollectionLedgerTO selectedColumn(CollectionLedgerForm collectionLedgerForm) {
		
		CollectionLedgerTO reportTO = new CollectionLedgerTO();
		Iterator<CollectionLedgerTO> iterator = collectionLedgerForm.getCollectionList().iterator();
		int count = 0;
		while (iterator.hasNext()) {

			
				reportTO.setDateDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setDatePos((short)++count);
					
				reportTO.setTimeDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setTimePos((short)++count);
				
				reportTO.setReceiptNumberDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setReceiptNumberPos((short)++count);
				
				reportTO.setApplNoDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setApplNoPos((short)++count);
				
				reportTO.setRegNoDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setRegNoPos((short)++count);
				
				reportTO.setNameDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setNamePos((short)++count);
				
				reportTO.setClassNameDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setClassNamePos((short)++count);
				
				reportTO.setAmountDis(CollectionLedgerHelper.DISPLAY);
				reportTO.setAmountPos((short)++count);
				
				break;
			}
	
		return reportTO;
	}
	public boolean convertToExcel(CollectionLedgerTO collectionTo,CollectionLedgerForm collectionLedgerForm,HttpServletRequest request) throws Exception{
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
		String fileName=prop.getProperty(CMSConstants.COLLECTION_LEDGER_REPORT_EXCEL);
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
		
		if(collectionLedgerForm.getCollectionList()!=null){
			CollectionLedgerTO to=(CollectionLedgerTO)collectionLedgerForm.getCollectionList().get(0);
			int count = 0;
			Iterator<CollectionLedgerTO> iterator = collectionLedgerForm.getCollectionList().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Collection Ledger Reports");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(collectionTo.getDateDis()!=null && collectionTo.getDateDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getDatePos()).setCellValue("Date");
				}
				if(collectionTo.getTimeDis()!=null && collectionTo.getTimeDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getTimePos()).setCellValue("Time");
				}
				if(collectionTo.getReceiptNumberDis()!=null && collectionTo.getReceiptNumberDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getReceiptNumberPos()).setCellValue("Receipt Number");
				}
				if(collectionTo.getApplNoDis()!=null && collectionTo.getApplNoDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getApplNoPos()).setCellValue("Applno");
				}
				if(collectionTo.getRegNoDis()!=null && collectionTo.getRegNoDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getRegNoPos()).setCellValue("RegNo");
				}
				if(collectionTo.getNameDis()!=null && collectionTo.getNameDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getNamePos()).setCellValue("Name");
				}
				if(collectionTo.getClassNameDis()!=null && collectionTo.getClassNameDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getClassNamePos()).setCellValue("Class");
				}
				if(collectionTo.getAmountDis()!=null && collectionTo.getAmountDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY)){
					row.createCell((short)collectionTo.getAmountPos()).setCellValue("Amount");
				}
				
				while (iterator.hasNext()) {
					CollectionLedgerTO dataTo = (CollectionLedgerTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(collectionTo.getDateDis()!=null && collectionTo.getDateDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getDate()!=null){
						row.createCell((short)collectionTo.getDatePos()).setCellValue(dataTo.getDate());
					}if(collectionTo.getTimeDis()!=null && collectionTo.getTimeDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getTime()!=null){
						row.createCell((short)collectionTo.getTimePos()).setCellValue(dataTo.getTime());
					}if(collectionTo.getReceiptNumberDis()!=null && collectionTo.getReceiptNumberDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getReceiptNumber()!=null){
						row.createCell((short)collectionTo.getReceiptNumberPos()).setCellValue(dataTo.getReceiptNumber());
					}if(collectionTo.getApplNoDis()!=null && collectionTo.getApplNoDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getApplNo()!=null){
						row.createCell((short)collectionTo.getApplNoPos()).setCellValue(dataTo.getApplNo());
					}if(collectionTo.getRegNoDis()!=null && collectionTo.getRegNoDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getRegNo()!=null){
						row.createCell((short)collectionTo.getRegNoPos()).setCellValue(dataTo.getRegNo());
					}if(collectionTo.getNameDis()!=null && collectionTo.getNameDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getName()!=null){
						row.createCell((short)collectionTo.getNamePos()).setCellValue(dataTo.getName());
					}if(collectionTo.getClassNameDis()!=null && collectionTo.getClassNameDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getClassName()!=null){
						row.createCell((short)collectionTo.getClassNamePos()).setCellValue(dataTo.getClassName());
					}if(collectionTo.getAmountDis()!=null && collectionTo.getAmountDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && dataTo.getAmount()!=null){
						row.createCell((short)collectionTo.getAmountPos()).setCellValue(dataTo.getAmount());
					}
				
				}if(collectionTo.getAmountDis()!=null && collectionTo.getAmountDis().equalsIgnoreCase(CollectionLedgerHelper.DISPLAY) && collectionLedgerForm.getTotalAmount()>0){
					row = sheet.createRow(count+1);
					row.createCell((short)collectionTo.getAmountPos()-1).setCellValue("Total Amount =");
					row.createCell((short)collectionTo.getAmountPos()).setCellValue(collectionLedgerForm.getTotalAmount()+"");
					}
				
				bos=new ByteArrayOutputStream();
				wb.write(bos);
				HttpSession session = request.getSession();
				session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
				isUpdated=true;
				fos.flush();
				fos.close();
				
			}catch (Exception e) {
				//System.out.println(e);
			}
		}
		return isUpdated;
     
	}
	
}
