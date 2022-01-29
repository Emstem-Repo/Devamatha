package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
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
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.CandidatePGIDetails;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.SettlementExceptionuploadForm;
import com.kp.cms.handlers.admission.SettlementExceptionUploadHandler;
import com.kp.cms.to.admission.GenerateSettlementOrRefundPgiTo;
import com.kp.cms.utilities.CommonUtil;

public class SettlementExceptionuploadAction  extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(SettlementExceptionuploadAction.class);
	
	public ActionForward initSettlementExceptionUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		SettlementExceptionuploadForm objform = (SettlementExceptionuploadForm) form;
		//objform.resetFields();
		return mapping.findForward("initSettlementExceptionUpload");
	}
	
	
	public ActionForward uploadSettlementException(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			log.info("Entered uploadSapMarks input");
			SettlementExceptionuploadForm objform = (SettlementExceptionuploadForm) form;
			ActionMessages messages=new ActionMessages();
			objform.reset();
			setUserId(request, objform);
			ActionErrors errors=objform.validate(mapping, request);
			
			try{
				if(errors.isEmpty()){
					
					if(objform.getThefile() == null){
						ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
						errors.add(CMSConstants.ERROR, message);
						addErrors(request, errors);
					return mapping.findForward("initSettlementExceptionUpload");
					}
				FormFile myFile = objform.getThefile();
				String contentType = myFile.getContentType();
				String fileName    = myFile.getFileName();
				File file = null;
				Properties prop = new Properties();
				InputStream stream = SmartCardNumberUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(stream);
					List<GenerateSettlementOrRefundPgiTo> results = null;
					List<String> candidateRefNoList=new ArrayList<String>();
						
						//if the uploading file is excel file
					if(fileName.endsWith(".xls")){
					//Map<String,Integer> registerNumMap=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getStudentIdByStudentRegNum(String.valueOf(year));
					byte[] fileData = myFile.getFileData();
					String source1=prop.getProperty(CMSConstants.UPLOAD_SETTLEMENT_EXCEPTION_XLS);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_SETTLEMENT_EXCEPTION_XLS);
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
						for(int i = 4; i < rows; i++) {
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
					results= new ArrayList<GenerateSettlementOrRefundPgiTo>();
					
					for(int r = 5; r < rows; r++) {
					
							row = sheet.getRow(r);
							GenerateSettlementOrRefundPgiTo uploadTo = new GenerateSettlementOrRefundPgiTo();
							if(row != null) {
							for(int c = 0; c < cols;c++) {
								cell = row.getCell((byte)c);
								if(cell != null && !StringUtils.isEmpty(cell.toString())) {
									String cell2=cell.toString();
									
									if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell2)){
										uploadTo.setBankId(removeFileExtension(cell2.trim()));
										}
								
									if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell2)){
										uploadTo.setTxnRefNo(removeFileExtension(cell2.trim()));
										}
								
									if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell2)){
										uploadTo.setCandidateRefNo(removeFileExtension(cell2.trim()));
										}
								
									if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell2)){
										uploadTo.setBankRefNo(cell2.trim());
									}
									
									
									if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell2)){
										uploadTo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(cell2.trim(), "dd/MM/yyyy hh:mm:ss", "dd/MM/yyyy")));
									}
								}
							} 
							
							results.add(uploadTo);
							}else {
								continue;		        	
							}
					}
					if(results!=null && !results.isEmpty()){
						Iterator<GenerateSettlementOrRefundPgiTo> itr = results.iterator();
						while (itr.hasNext()) {
							GenerateSettlementOrRefundPgiTo to = (GenerateSettlementOrRefundPgiTo) itr.next();
							if(to.getCandidateRefNo()!=null && !to.getCandidateRefNo().isEmpty())
							candidateRefNoList.add(to.getCandidateRefNo());
						}
					}
					Map<String,CandidatePGIDetails> candidateRefNoMap=SettlementExceptionUploadHandler.getInstance().getTrnsactionPendingStatuscandiates(candidateRefNoList);
					isAdded=SettlementExceptionUploadHandler.getInstance().uploadSettlementException(candidateRefNoMap,results,objform);
						
					if(isAdded){
						/*String str=objform.getExceptedCanRefNo();
						if(str!=null && !str.isEmpty()){
						ActionMessage message = new ActionMessage(CMSConstants.Upload_Except_CANDIDATE_REF_NOS);
						messages.add("messages", message);
						saveMessages(request, messages);
						errors.add("error", new ActionError("knowledgepro.admin.exam.dup",str));
						addErrors(request, errors);
						}else{*/
							ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
							messages.add("messages", message);
							saveMessages(request, messages);
						//}
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
			return mapping.findForward("initSettlementExceptionUpload");
}
	
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
	public String removeFileExtension(String fileName)
	{ 
			if(null != fileName && fileName.contains("."))
			{
			return fileName.substring(0, fileName.lastIndexOf("."));
			}
		return fileName;
	}
}
