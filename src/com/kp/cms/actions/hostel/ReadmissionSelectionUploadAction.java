package com.kp.cms.actions.hostel;

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
import java.util.Map.Entry;

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
import com.kp.cms.actions.admission.SmartCardNumberUploadAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.hostel.ReadmissionSelectionUploadForm;
import com.kp.cms.handlers.hostel.HostelEntryHandler;
import com.kp.cms.handlers.hostel.ReadmissionSelectionUploadHandler;
import com.kp.cms.handlers.hostel.UploadTheOfflineApplicationDetailsToSystemHandler;
import com.kp.cms.to.admin.HostelOnlineApplicationTo;
import com.kp.cms.to.hostel.HostelTO;
import com.kp.cms.to.hostel.UploadTheOfflineApplicationDetailsToSystemTo;

@SuppressWarnings("deprecation")
public class ReadmissionSelectionUploadAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(ReadmissionSelectionUploadAction.class);
	public ActionForward initReadmissionSelectionUpload(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,HttpServletResponse response) throws Exception {
		ReadmissionSelectionUploadForm objform = (ReadmissionSelectionUploadForm) form;
		objform.resetFields();
		setHostelListToRequest(request,objform);
		return mapping.findForward("initReadmissionSelectionUpload");
	}
	public void setHostelListToRequest(HttpServletRequest request,ReadmissionSelectionUploadForm objform) throws Exception {
		log.debug("inside setHostelListToRequest");
		 List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetails();
		 request.setAttribute("hostelList", hostelList);
		
	}
	
	
	public ActionForward uploadReadmissionSelection(ActionMapping mapping,ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

			log.info("Entered uploadSapMarks input");
			ReadmissionSelectionUploadForm objform = (ReadmissionSelectionUploadForm) form;
			ActionMessages messages=new ActionMessages();
			objform.reset();
			setUserId(request, objform);
			ActionErrors errors=objform.validate(mapping, request);
			
			String str= "";
			String str2="Duplicate entry found for the following register numbers:";
			
			StringBuilder dupRegNum=new StringBuilder();
			String roomTypeNotMachingMsg="Room Type is not matching for selected hostel for below register numbers:";
			StringBuilder roomTypeNotMachingRegNum=new StringBuilder();
			
			try{
			if(errors.isEmpty()){
				
				if(objform.getThefile() == null){
					ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
					errors.add(CMSConstants.ERROR, message);
					addErrors(request, errors);
				return mapping.findForward("initReadmissionSelectionUpload");
				}
			FormFile myFile = objform.getThefile();
			String contentType = myFile.getContentType();
			String fileName    = myFile.getFileName();
			File file = null;
			Properties prop = new Properties();
			InputStream stream = SmartCardNumberUploadAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			prop.load(stream);
			List<UploadTheOfflineApplicationDetailsToSystemTo> results = null;
			List<String> regNumList=new ArrayList<String>();
			List<UploadTheOfflineApplicationDetailsToSystemTo> toList = new ArrayList<UploadTheOfflineApplicationDetailsToSystemTo>();
			//if the uploading file is excel file
			if(fileName.endsWith(".xls")){
			//Map<String,Integer> registerNumMap=UploadBlockListForHallticketOrMarkscardHandler.getInstance().getStudentIdByStudentRegNum(String.valueOf(year));
			byte[] fileData = myFile.getFileData();
			String source1=prop.getProperty(CMSConstants.Upload_OFFLINE_APPLICATION_XLS);
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
			String source=prop.getProperty(CMSConstants.Upload_OFFLINE_APPLICATION_XLS);
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
			results= new ArrayList<UploadTheOfflineApplicationDetailsToSystemTo>();
			List<String> dupRegNumber=new ArrayList<String>();
			for(int r = 1; r < rows; r++) {
			
			row = sheet.getRow(r);
			UploadTheOfflineApplicationDetailsToSystemTo uploadTo = new UploadTheOfflineApplicationDetailsToSystemTo();
			if(row != null) {
			for(int c = 0; c < cols;c++) {
				cell = row.getCell((byte)c);
				if(cell != null && !StringUtils.isEmpty(cell.toString())) {
					String cell2=cell.toString();
				
					if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell2)){
						uploadTo.setStRegNum(removeFileExtension(cell2.trim()));
						}
				
					if(cell.getCellNum() == 1 && !StringUtils.isEmpty(cell2)){
						uploadTo.setRoomType(removeFileExtension(cell2.trim()));
						}
				
					
			
				}
			} 
			
			results.add(uploadTo);
			}else {
			continue;		        	
			}
			}
			if(results!=null && !results.isEmpty()){
				Iterator<UploadTheOfflineApplicationDetailsToSystemTo> itr = results.iterator();
				while (itr.hasNext()) {
					UploadTheOfflineApplicationDetailsToSystemTo to = (UploadTheOfflineApplicationDetailsToSystemTo) itr.next();
				regNumList.add(to.getStRegNum());
				}
			}
			Map<String,Integer> registerNumMap=ReadmissionSelectionUploadHandler.getInstance().getStudentIdByStudentRegNum(regNumList);
			Map<String,Integer> roomTypeMap=UploadTheOfflineApplicationDetailsToSystemHandler.getInstance().getHlRoomType(Integer.parseInt(objform.getHostelId()));
			
			
			if(registerNumMap!=null && !registerNumMap.isEmpty()){
				if(results!=null && !results.isEmpty()){
					Iterator<UploadTheOfflineApplicationDetailsToSystemTo> itr = results.iterator();
					while (itr.hasNext()) {
						UploadTheOfflineApplicationDetailsToSystemTo to = (UploadTheOfflineApplicationDetailsToSystemTo) itr.next();
						UploadTheOfflineApplicationDetailsToSystemTo newTo=new UploadTheOfflineApplicationDetailsToSystemTo();
					boolean isDupRegNo=false;
					if(!dupRegNumber.contains(to.getStRegNum())){
						if(registerNumMap.containsKey(to.getStRegNum())){
							newTo.setStudentId(registerNumMap.get(to.getStRegNum()));
							dupRegNumber.add(to.getStRegNum());
						}else{
							if(str==null || str.isEmpty()){
								str=str+removeFileExtension(to.getStRegNum());
							}else{
								str=str+","+removeFileExtension(to.getStRegNum());
							}
							isDupRegNo=true;
						}
					}else{
						if(dupRegNum.length()==0)
						dupRegNum=dupRegNum.append(removeFileExtension(to.getStRegNum()));
						else
						dupRegNum=dupRegNum.append(",").append(removeFileExtension(to.getStRegNum()));
						objform.setDupliRegNum(true);
						isDupRegNo=true;
					}
					boolean isRoomTypeMaching=false;
					Iterator<Entry<String,Integer>> iterator = roomTypeMap.entrySet().iterator();
					while (iterator.hasNext()) {
						Map.Entry<String,Integer> entry = (Map.Entry<String,Integer>) iterator.next();
						String roomType=entry.getKey();
						if(roomType!=null && !roomType.isEmpty() && roomType.equalsIgnoreCase(to.getRoomType())){
							isRoomTypeMaching=true;
							newTo.setRoomTypeId(entry.getValue());
							newTo.setRoomTypeName(entry.getKey());
							break;
						}
						
					}
					
					if(!isRoomTypeMaching){
						if(roomTypeNotMachingRegNum.length()==0)
							roomTypeNotMachingRegNum=roomTypeNotMachingRegNum.append(removeFileExtension(to.getStRegNum()));
						else
							roomTypeNotMachingRegNum=roomTypeNotMachingRegNum.append(",").append(removeFileExtension(to.getStRegNum()));
						objform.setIsRoomTypeNotMaching(true);
						isDupRegNo=true;
					}
						if(newTo!=null &&  newTo.getStudentId()!=0){
							if(!isDupRegNo)
							toList.add(newTo);
						}
					}
				}
					if(toList!=null && toList.size()>0){
						isAdded=ReadmissionSelectionUploadHandler.getInstance().uploadOfflineApplication(toList,objform,registerNumMap);
					}
			}	
				
			if(isAdded){
				//sending SMS and Email to selected students code added by chandra
				List<HostelOnlineApplicationTo> selectedStudentList=objform.getSelectedStudentList();
				if(selectedStudentList!=null && !selectedStudentList.isEmpty()){
					ReadmissionSelectionUploadHandler.getInstance().sendingSMSToStudents(selectedStudentList);
					ReadmissionSelectionUploadHandler.getInstance().sendingMailToStudents(selectedStudentList);
					objform.setSelectedStudentList(null);
				}
				if(str!=null && !str.isEmpty()){
				ActionMessage message = new ActionMessage(CMSConstants.Upload_Except_Reg_Numbers);
				messages.add("messages", message);
				saveMessages(request, messages);
				errors.add("error", new ActionError("knowledgepro.admin.exam.dup",str));
				addErrors(request, errors);
				}else{
					ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
					messages.add("messages", message);
					saveMessages(request, messages);
				}
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
			
			if(objform.getDupliRegNum()!=null && objform.getDupliRegNum()){
				objform.setDupRegNumMsg(str2);
				objform.setDupRegNum(dupRegNum.toString());
			}
			if(objform.getIsRoomTypeNotMaching()!=null && objform.getIsRoomTypeNotMaching()){
				objform.setRoomTypeNotMachingMsg(roomTypeNotMachingMsg);
				objform.setRoomTypeNotMachingRegNum(roomTypeNotMachingRegNum.toString());
			}
			
			/*if(objform.isWrgDateFormat()){
			objform.setDisplayWrgDateMsg(wrongFormat.toString());
			}
			if(objform.isEmptyDate()){
			objform.setDisplayEmptyDate(emptyDate.toString());
			}*/
			
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
			setHostelListToRequest(request,objform);
			log.info("Leaving uploadSapMarksAction ");
			return mapping.findForward("initReadmissionSelectionUpload");
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
