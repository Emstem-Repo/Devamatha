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

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.pettycash.ConsolidatedCollectionLedgerForm;
import com.kp.cms.to.pettycash.ConsolidatedCollectionLedgerTO;
import com.kp.cms.utilities.CommonUtil;

public class ConsolidatedCollectionLedgerHelper {
	/**
	 * Singleton object of ConsolidatedCollectionLedgerHelper
	 */
	private static volatile ConsolidatedCollectionLedgerHelper collectionLedgerHelper = null;
	private static final Log log = LogFactory.getLog(ConsolidatedCollectionLedgerHelper.class);
	private static final String DISPLAY = "display";
	private ConsolidatedCollectionLedgerHelper() {
		
	}
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ConsolidatedCollectionLedgerHelper getInstance() {
		if (collectionLedgerHelper == null) {
			collectionLedgerHelper = new ConsolidatedCollectionLedgerHelper();
		}
		return collectionLedgerHelper;
	}
	
	/**
	 * building the query
	 * @param ConsolidatedCollectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public String getSearchQueryForAccountHeadCode(ConsolidatedCollectionLedgerForm collectionLedgerForm,int userId) throws Exception {
//		String commonQuery=commonSearch();
		String searchQuery="select DATE_FORMAT(pc_receipts.paid_date,'%d/%m/%Y') as dt,pc_account_head.bank_acc_no,sum(pc_receipts.amount) as netamount from pc_receipts " +
				           " inner join pc_account_head on pc_receipts.pc_account_head_id = pc_account_head.id "+
		                   " where pc_receipts.is_active=1 and  pc_receipts.is_cancelled=0 ";
			if(collectionLedgerForm.getStartDate()!=null && collectionLedgerForm.getEndDate()!=null 
					&& !collectionLedgerForm.getEndDate().isEmpty()&& !collectionLedgerForm.getStartDate().isEmpty())
			{
				searchQuery=searchQuery+" and pc_receipts.paid_date between '"+CommonUtil.ConvertStringToSQLDate(collectionLedgerForm.getStartDate())+"'"+" and '"+CommonUtil.ConvertStringToSQLExactDate(collectionLedgerForm.getEndDate())+"'";
			}
			if(userId!=0){
				searchQuery=searchQuery+" and pc_receipts.user_id="+userId;
			}
			searchQuery=searchQuery+" group by DATE_FORMAT(pc_receipts.paid_date,'%d/%m/%Y'),pc_account_head.bank_acc_no"+
				                   " order by pc_receipts.paid_date,pc_account_head.bank_acc_no";

		return searchQuery;
	}
	/**
	 * converting list of objects into tos
	 * @param listofStudents
	 * @return
	 */
	public List<ConsolidatedCollectionLedgerTO> convertListOfBOtoTOs1(List listofStudents,ConsolidatedCollectionLedgerForm collectionLedgerForm) throws Exception{
		List<ConsolidatedCollectionLedgerTO>  list=new ArrayList<ConsolidatedCollectionLedgerTO>();
		Iterator iterator=listofStudents.iterator();
		String dateTime="";
		String date="";
		double total=0;
		while (iterator.hasNext()) {
			Object[] pcReceipts = (Object[]) iterator.next();
			ConsolidatedCollectionLedgerTO collectionLedgerTO=new ConsolidatedCollectionLedgerTO();
			if(pcReceipts[0].toString()!=null){
				dateTime=pcReceipts[0].toString();
				date=dateTime.substring(0,10);
				collectionLedgerTO.setDate(date);
			}
			collectionLedgerTO.setAccountNo(pcReceipts[1].toString());
			collectionLedgerTO.setNetAmount(pcReceipts[2].toString());
			total=total+Double.parseDouble(pcReceipts[2].toString());
			list.add(collectionLedgerTO);
		}
		collectionLedgerForm.setTotalAmount(total);
		return list;
	}
	/**
	 * @param collectionLedgerForm
	 * @return
	 * @throws Exception
	 */
	public ConsolidatedCollectionLedgerTO selectedColumns(ConsolidatedCollectionLedgerForm collectionLedgerForm) throws Exception{
		
		ConsolidatedCollectionLedgerTO reportTO = new ConsolidatedCollectionLedgerTO();
		Iterator<ConsolidatedCollectionLedgerTO> iterator = collectionLedgerForm.getConsolidatedList().iterator();
		int count = 0;
		while (iterator.hasNext()) {

				reportTO.setDateDis(ConsolidatedCollectionLedgerHelper.DISPLAY);
				reportTO.setDatePos((short)++count);
					
				reportTO.setAccountNoDis(ConsolidatedCollectionLedgerHelper.DISPLAY);
				reportTO.setAccountNoPos((short)++count);
				
				reportTO.setNetAmountDis(ConsolidatedCollectionLedgerHelper.DISPLAY);
				reportTO.setNetAmountPos((short)++count);
				break;
			}
	
		return reportTO;
	}
	/**
	 * @param admBioTo
	 * @param collectionLedgerForm
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public boolean convertToTOExcel(ConsolidatedCollectionLedgerTO admBioTo,ConsolidatedCollectionLedgerForm collectionLedgerForm,
			HttpServletRequest request) throws IOException{
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
		String fileName=prop.getProperty(CMSConstants.CONSOLIDATED_COLLECTION_LEDGER);
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
		
		if(collectionLedgerForm.getConsolidatedList()!=null){
			ConsolidatedCollectionLedgerTO to=(ConsolidatedCollectionLedgerTO)collectionLedgerForm.getConsolidatedList().get(0);
			int count = 0;
			Iterator<ConsolidatedCollectionLedgerTO> iterator = collectionLedgerForm.getConsolidatedList().iterator();
			try{
				wb=new XSSFWorkbook();
				XSSFCellStyle cellStyle=wb.createCellStyle();
				CreationHelper createHelper = wb.getCreationHelper();
				cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
				sheet = wb.createSheet("Consolidated Collection Ledger");
				row = sheet.createRow(count);
				count = sheet.getFirstRowNum();
				// Create cells in the row and put some data in it.
				if(admBioTo.getDateDis()!=null && admBioTo.getDateDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY)){
					row.createCell((short)admBioTo.getDatePos()).setCellValue("Date");
				}
				if(admBioTo.getAccountNoDis()!=null && admBioTo.getAccountNoDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY)){
					row.createCell((short)admBioTo.getAccountNoPos()).setCellValue("Account No");
				}
				if(admBioTo.getNetAmountDis()!=null && admBioTo.getNetAmountDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY)){
					row.createCell((short)admBioTo.getNetAmountPos()).setCellValue("Net Amount");
				}
				
				while (iterator.hasNext()) {
					ConsolidatedCollectionLedgerTO dataTo = (ConsolidatedCollectionLedgerTO) iterator.next();
					count = count +1;
					row = sheet.createRow(count);
					if(admBioTo.getDateDis()!=null && admBioTo.getDateDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY) && dataTo.getDate()!=null){
						row.createCell((short)admBioTo.getDatePos()).setCellValue(dataTo.getDate());
					}
					if(admBioTo.getAccountNoDis()!=null && admBioTo.getAccountNoDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY) && dataTo.getAccountNo()!=null){
						row.createCell((short)admBioTo.getAccountNoPos()).setCellValue(dataTo.getAccountNo());
					}
					if(admBioTo.getNetAmountDis()!=null && admBioTo.getNetAmountDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY) && dataTo.getNetAmount()!=null){
						row.createCell((short)admBioTo.getNetAmountPos()).setCellValue(dataTo.getNetAmount());
					}
				}
				if(admBioTo.getNetAmountDis()!=null && admBioTo.getNetAmountDis().equalsIgnoreCase(ConsolidatedCollectionLedgerHelper.DISPLAY) && collectionLedgerForm.getTotalAmount()>0){
				row = sheet.createRow(count+1);
				row.createCell((short)admBioTo.getNetAmountPos()-1).setCellValue("Total Amount =");
				row.createCell((short)admBioTo.getNetAmountPos()).setCellValue(collectionLedgerForm.getTotalAmount()+"");
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
