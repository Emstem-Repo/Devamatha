package com.kp.cms.actions.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.BankMISForm;
import com.kp.cms.handlers.admin.BankMISHandler;
import com.kp.cms.to.admin.BankMISTO;
import com.kp.cms.utilities.CommonUtil;

public class BankMISAction extends BaseDispatchAction {

	private static final Log log = LogFactory.getLog(BankMISAction.class);

	public ActionForward initDispaly(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initDisplay in BankMISAction class");
		log.info("exit of initDisplay in BankMISAction class");
		return mapping.findForward(CMSConstants.BANKMISVIEW);
	}

	@SuppressWarnings({ "deprecation", "unchecked"})
	public ActionForward saveExcelData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entering saveExcelData in BankMISAction class");
		BankMISForm bankMISForm = (BankMISForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = bankMISForm.validate(mapping, request);

		FormFile myFile = bankMISForm.getFormFile();
		String contentType = myFile.getContentType();
		File file = null;
		Properties prop = new Properties();
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		prop.load(stream);
		List<BankMISTO> bankDetailsList = null;
		BankMISTO bankMISTO;
		try {
			if (errors.isEmpty()) {
				setUserId(request, bankMISForm);
				String extn="";
				int indx=myFile.getFileName().lastIndexOf(".");
				if(indx!=-1){
					extn=myFile.getFileName().substring(indx, myFile.getFileName().length());
				}
				if(extn.equalsIgnoreCase(".XLS")){
					byte[] fileData = myFile.getFileData();
	
					String source1 = prop
							.getProperty(CMSConstants.BANKEXCELFILE);
	
					File file1 = new File(source1);
	
					InputStream inputStream = new ByteArrayStreamInfo(contentType,
							fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source = prop
							.getProperty(CMSConstants.BANKEXCELFILE);
					file = new File(source);
	
					FileInputStream fs = null;
					try {
						fs = new FileInputStream(file);
						List refList = BankMISHandler.getInstance().getRefNos();
							WorkbookSettings ws = null;
							Workbook workbook = null;
							Sheet s = null;
							Cell rowData[] = null;
							int rowCount = 0;
							int columnCount = 0;
							
							try {
								ws = new WorkbookSettings();
								ws.setLocale(new Locale("en", "EN"));
								workbook = Workbook.getWorkbook(fs, ws);
					 
								//Getting Default Sheet i.e. 0
								s = workbook.getSheet(0);
								rowCount = s.getRows();
								columnCount = s.getColumns();
								bankDetailsList = new ArrayList<BankMISTO>();
								List dupRefList = new ArrayList();
					 			//Reading Individual Row Content
								for (int i = 1; i < rowCount; i++) {
									//Get Individual Row
									bankMISTO = new BankMISTO();
									rowData = s.getRow(i);
									if (rowData[1].getContents().length() != 0) { // the first date column must not null
										for (int j = 0; j < columnCount; j++) {
											switch (j) {
											case 0:
												String s1 = CommonUtil.ConvertStringToDateFormat(rowData[j].getContents(), "dd.MM.yy","dd/MM/yyyy");
												bankMISTO.setTxnDate(CommonUtil.ConvertStringToSQLDate(s1));
												break;
											case 1:
												bankMISTO.setJournalNo(rowData[j].getContents());
												break;
											case 5:
												bankMISTO.setTxnBranch(rowData[j].getContents());
												break;
											case 6:
												if(!refList.contains(rowData[j].getContents()) && !dupRefList.contains(rowData[j].getContents())){
													dupRefList.add(rowData[j].getContents());
													bankMISTO.setRefNumber(rowData[j].getContents());
												}else{
													continue;
												}
												break;
											
											default:
												break;
											}
										}
										if(bankMISTO.getRefNumber() != null){
											bankDetailsList.add(bankMISTO);
										}else{
											continue;
										}
									}
								}
								workbook.close();			
							} catch (IOException e) {
								log.error("erroor occured in saveExcelData method of BankMISAction class..");
							} catch (BiffException e) {
								log.error("erroor occured in saveExcelData method of BankMISAction class..");
							}
					
					} catch (IOException e) {
						log.error("erroor occured in saveExcelData method of BankMISAction class..");
					} catch (Exception e) {
						log.error("erroor occured in saveExcelData method of BankMISAction class..");
					}finally {
						try {
							fs.close();
						} catch (IOException e) {
							log.error("erroor occured in saveExcelData method of BankMISAction class..");
						}
					}
					
					// call of handler.
					boolean isUploaded = BankMISHandler.getInstance().saveBankDetails(bankDetailsList);
					if (isUploaded) {
						// if success
						ActionMessage message = new ActionMessage(
								CMSConstants.UPLOAD_DOC_SUCCESS);
						messages.add("messages", message);
						saveMessages(request, messages);
					} else {
						// if failure
						ActionMessage message = new ActionMessage(
								CMSConstants.UPLOAD_DOC_FAILURE);
						errors.add("error", message);
						saveErrors(request, errors);
				}
				}else{
					// if upload doc is not excel.
		      		ActionMessage message = new ActionMessage(CMSConstants.UPLOADEXCELFILEREQ);
		      		errors.add("error",message);
		      		saveErrors(request, errors);
				}
			}else{
				saveErrors(request, errors);
			}	
		} catch (BusinessException businessException) {
			log.info("Exception saveExcelData");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {
			String msg = super.handleApplicationException(exception);
			bankMISForm.setErrorMessage(msg);
			bankMISForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		log.info("exit of saveExcelData in BankMISAction class.");
		return mapping.findForward(CMSConstants.SUBMITBANKMISVIEW);
	}

	/**
	 * This method is used to convert file data based on content type.
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