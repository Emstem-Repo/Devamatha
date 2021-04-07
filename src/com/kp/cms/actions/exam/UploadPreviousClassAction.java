package com.kp.cms.actions.exam;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction.StreamInfo;
import org.apache.struts.upload.FormFile;

import com.Ostermiller.util.CSVParser;
import com.Ostermiller.util.LabeledCSVParser;
import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.exam.UploadPreviousClassForm;
import com.kp.cms.handlers.exam.UploadPreviousClassHandler;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.exam.PreviousClassDetailsTO;
import com.kp.cms.to.exam.UploadExamInternalMarkSupplementaryTO;
import com.kp.cms.utilities.CommonUtil;

	public class UploadPreviousClassAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(UploadPreviousClassAction.class);
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadPreviousClass(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UploadPreviousClassForm exportPreviousClassForm = (UploadPreviousClassForm) form;
			exportPreviousClassForm.resetFields();
			return mapping.findForward(CMSConstants.UPLOAD_PREVIOUSCLASS_INFO);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadPreviousClassInfo(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UploadPreviousClassForm exportPreviousClassForm = (UploadPreviousClassForm) form;
			log.info("call of uploadPreviousClassInfo method in ExportPreviousClassAction class.");
			 ActionErrors errors = exportPreviousClassForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			try {
				setUserId(request,exportPreviousClassForm);
				
				 if (errors.isEmpty()) {					
				FormFile myFile = exportPreviousClassForm.getThefile();
			    String contentType = myFile.getContentType();
			    	 
			   String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    Map<String,StudentTO> studentMap = UploadPreviousClassHandler.getInstance().getStudentDetails();
			    Map<String,Integer> classMap=UploadPreviousClassHandler.getInstance().getClassMap();
			    PreviousClassDetailsTO previousClassDetailsTO=null;
			    PreviousClassDetailsTO previousClassDetailsNotExistTO=null;
			    //StudentTO studentTO =null ;
			    List<PreviousClassDetailsTO> previousClassDetailsTOList = new ArrayList<PreviousClassDetailsTO>();
			    List<PreviousClassDetailsTO> previousClassDetailsNotExistTOList = new ArrayList<PreviousClassDetailsTO>();
			    List<String>duplicateList=new ArrayList<String>();
			     //if the uploaded document is excel file.
			    if(fileName.endsWith(".xlsx")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
					InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
					OutputStream out = new FileOutputStream(file1);
					byte buffer[] = new byte[1024];
					int len;
					while ((len = inputStream.read(buffer)) > 0){
						out.write(buffer, 0, len);
					}
					out.close();
					inputStream.close();
					String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL);
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					UploadExamInternalMarkSupplementaryTO uploadExamInternalMarkSupplementaryTO=null;
					String year=null;
			    	String regNo=null;
			    	String schemeNo=null;
					XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(request.getRealPath("")+ "//TempFiles//"+source));
	            	XSSFSheet sh=work.getSheetAt(0);
	            	Iterator rows = sh.rowIterator();
	            	 while (rows.hasNext()) {
	                 	XSSFRow row = (XSSFRow) rows.next();
	                     Iterator cells = row.cellIterator();
	                     while (cells.hasNext()) {
	                         XSSFCell cell = (XSSFCell) cells.next();
		                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
							if(cell.getColumnIndex() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
								
								year=cell.toString().trim();
								previousClassDetailsTO = new PreviousClassDetailsTO();
								previousClassDetailsNotExistTO=new PreviousClassDetailsTO();
								if(cell.toString().endsWith(".0")){
									String value = StringUtils.chop(cell.toString().trim());
									year = StringUtils.chop(value);
								}
		                	}
							
							if(cell.getColumnIndex() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
								regNo=cell.toString().trim();
								if(regNo.startsWith("9"))
			                		regNo="0"+regNo;
								if(regNo.endsWith(".0")){
									String value = StringUtils.chop(regNo);
									regNo = StringUtils.chop(value);
									if(studentMap!=null && studentMap.containsKey(regNo)){
										StudentTO studentTO =studentMap.get(regNo);
			                		    previousClassDetailsTO.setRegNo(studentTO.getRegisterNo());
			                		    previousClassDetailsTO.setStudentId(studentTO.getId());
			                		    previousClassDetailsTO.setYear(year);
									}
									else{
											previousClassDetailsNotExistTO.setRegNo(regNo);
											previousClassDetailsNotExistTO.setYear(year);
				        					
			                			}
								}else{
									if(studentMap!=null && studentMap.containsKey(regNo)){
										StudentTO studentTO =studentMap.get(regNo);
			                		    previousClassDetailsTO.setRegNo(studentTO.getRegisterNo());
			                		    previousClassDetailsTO.setStudentId(studentTO.getId());
			                		    previousClassDetailsTO.setYear(year);
										}
										else{
											previousClassDetailsNotExistTO.setRegNo(regNo);
											previousClassDetailsNotExistTO.setYear(year);
											
				                		}
								}	
								
		                   	} if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(classMap!=null && classMap.containsKey(cell.toString().trim().toLowerCase()+"_"+year)){
		                   			previousClassDetailsTO.setClassName(cell.toString().trim());
		                   			previousClassDetailsTO.setClassId(classMap.get(cell.toString().trim().toLowerCase()+"_"+year));
		                   		}else{
		                   			previousClassDetailsNotExistTO.setClassName(cell.toString().trim());
		                   			if(previousClassDetailsTO.getRegNo()!=null)
		                   			previousClassDetailsNotExistTO.setRegNo(previousClassDetailsTO.getRegNo());
		                   			previousClassDetailsNotExistTO.setYear(year);
		                   			
		                   		}
		                   		
		                   	} if(cell.getColumnIndex() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString().endsWith(".0")){
									String value = StringUtils.chop(cell.toString().trim());
									schemeNo = StringUtils.chop(value);	
									previousClassDetailsTO.setSchemeNo(schemeNo);
		                   		}else{
		                   			previousClassDetailsNotExistTO.setSchemeNo(cell.toString());
		                   		}
		                   	}
		                }
		            }
	                if(previousClassDetailsTO!=null)
	                {
		            	if(previousClassDetailsTO.getRegNo()!=null && previousClassDetailsTO.getClassName()!=null)
		            	{
		            		String checkDuplicate=previousClassDetailsTO.getRegNo()+"_"+previousClassDetailsTO.getClassName();
		            		if(!duplicateList.contains(checkDuplicate))
		            		{	
		            			previousClassDetailsTOList.add(previousClassDetailsTO);
		            			previousClassDetailsTO=null;
		            			duplicateList.add(checkDuplicate);
		            		}	
		            			previousClassDetailsNotExistTO=null;
		            	}
		            }
		            
		            if(previousClassDetailsNotExistTO!=null){
		            	previousClassDetailsNotExistTOList.add(previousClassDetailsNotExistTO);
		            	previousClassDetailsNotExistTO=null;
		            }
		    	}
		    
			    boolean isAdded;
	    	if(previousClassDetailsTOList.size()>0){
	    		isAdded = UploadPreviousClassHandler.getInstance().addUploadedData(exportPreviousClassForm,previousClassDetailsTOList);
	    	if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.success");
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.failure");
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    		}
			    }
		    	if(previousClassDetailsNotExistTOList.size() >0){
		    		writeToFile(previousClassDetailsNotExistTOList,request);
		    		request.setAttribute("fileType", "Excel");
		    		request.setAttribute("fileName", "PreviousClassesExcel");
		    	}
			 }else
			     if(fileName.endsWith(".xls")){
			    	 byte[] fileData = myFile.getFileData();
			    
			    	 String source1=prop.getProperty(CMSConstants.UPLOAD_PREVIOUS_CLASS_EXCEL_DESTINATION);
						
					File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
			    	 
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				OutputStream out = new FileOutputStream(file1);
				byte buffer[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
				}
				out.close();
				inputStream.close();
				String source=prop.getProperty(CMSConstants.UPLOAD_PREVIOUS_CLASS_EXCEL_DESTINATION);
				
				file = new File(request.getRealPath("")+ "//TempFiles//"+source);
			
			POIFSFileSystem system = new POIFSFileSystem(new FileInputStream(file));
		    HSSFWorkbook workbook = new HSSFWorkbook(system);
		    HSSFSheet sheet = workbook.getSheetAt(0);
		    HSSFRow row;
		    HSSFCell cell;

		    int rows = sheet.getPhysicalNumberOfRows();

		    int cols = 0; // No of columns
		    int tmp = 0;
		    
		    boolean dupClass;
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
		   
		    for(int r = 1; r < rows; r++) {
		    	String year=null;
		    	String regNo=null;
		    	String schemeNo=null;
		    	row = sheet.getRow(r);
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
							if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
								
								year=cell.toString().trim();
								previousClassDetailsTO = new PreviousClassDetailsTO();
								previousClassDetailsNotExistTO=new PreviousClassDetailsTO();
								if(cell.toString().endsWith(".0")){
									String value = StringUtils.chop(cell.toString().trim());
									year = StringUtils.chop(value);
								}
		                	}
							
							if(cell.getCellNum() == 1 && 1 <= cols && !StringUtils.isEmpty(cell.toString().trim())){
								regNo=cell.toString().trim();
								if(regNo.startsWith("9"))
			                		regNo="0"+regNo;
								if(regNo.endsWith(".0"))
								{
									String value = StringUtils.chop(regNo);
									regNo = StringUtils.chop(value);
									if(studentMap!=null && studentMap.containsKey(regNo)){
										StudentTO studentTO =studentMap.get(regNo);
			                		    previousClassDetailsTO.setRegNo(studentTO.getRegisterNo());
			                		    previousClassDetailsTO.setStudentId(studentTO.getId());
			                		    previousClassDetailsTO.setYear(year);
									}
									else{
											previousClassDetailsNotExistTO.setRegNo(regNo);
											previousClassDetailsNotExistTO.setYear(year);
				        					break;
			                			}
								}else{
									if(studentMap!=null && studentMap.containsKey(regNo)){
										StudentTO studentTO =studentMap.get(regNo);
			                		    previousClassDetailsTO.setRegNo(studentTO.getRegisterNo());
			                		    previousClassDetailsTO.setStudentId(studentTO.getId());
			                		    previousClassDetailsTO.setYear(year);
										}
										else{
											previousClassDetailsNotExistTO.setRegNo(cell.toString().trim());
											previousClassDetailsNotExistTO.setYear(year);
											break;
				                		}
								}	
								
		                   	} if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(classMap!=null && classMap.containsKey(cell.toString().trim().toLowerCase()+"_"+year)){
		                   			previousClassDetailsTO.setClassName(cell.toString().trim());
		                   			previousClassDetailsTO.setClassId(classMap.get(cell.toString().trim().toLowerCase()+"_"+year));
		                   		}else{
		                   			previousClassDetailsNotExistTO.setClassName(cell.toString().trim());
		                   			if(previousClassDetailsTO.getRegNo()!=null)
		                   			previousClassDetailsNotExistTO.setRegNo(previousClassDetailsTO.getRegNo());
		                   			previousClassDetailsNotExistTO.setYear(year);
		                   			break;
		                   		}
		                   		
		                   	} if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString().endsWith(".0")){
									String value = StringUtils.chop(cell.toString().trim());
									schemeNo = StringUtils.chop(value);	
									previousClassDetailsTO.setSchemeNo(schemeNo);
		                   		}else{
		                   			previousClassDetailsNotExistTO.setSchemeNo(cell.toString());
		                   		}
		                   	}
		                }
		            }if(previousClassDetailsTO!=null){
		            	if(previousClassDetailsTO.getRegNo()!=null && previousClassDetailsTO.getClassName()!=null)
		            	{
		            		String checkDuplicate=previousClassDetailsTO.getRegNo()+"_"+previousClassDetailsTO.getClassName();
		            		if(!duplicateList.contains(checkDuplicate))
		            		{	
		            			previousClassDetailsTOList.add(previousClassDetailsTO);
		            			previousClassDetailsTO=null;
		            			duplicateList.add(checkDuplicate);
		            		}	
		            			previousClassDetailsNotExistTO=null;
		            	}
		            }
		            
		            if(previousClassDetailsNotExistTO!=null){
		            	previousClassDetailsNotExistTOList.add(previousClassDetailsNotExistTO);
		            	previousClassDetailsNotExistTO=null;
		            }
		    	}
		    }
	    	if(previousClassDetailsTOList.size()>0){
	    		isAdded = UploadPreviousClassHandler.getInstance().addUploadedData(exportPreviousClassForm, previousClassDetailsTOList);
	    	if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.success");
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
		    	}else{
		    		//if adding is failure
		    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.failure");
		    		errors.add(CMSConstants.ERROR, message);
		    		addErrors(request, errors);
		    		}
			    }
		    	if(previousClassDetailsNotExistTOList.size() >0){
		    		writeToFile(previousClassDetailsNotExistTOList,request);
		    		request.setAttribute("fileType", "Excel");
		    		request.setAttribute("fileName", "PreviousClassesExcel");
		    	}
		    }else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
		     	  
		     	byte[] fileData    = myFile.getFileData();
			    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_CSV);
				
				File file1 = new File(request.getRealPath("")+ "//TempFiles//"+source1);
			    	 
				InputStream inputStream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
				OutputStream out = new FileOutputStream(file1);
				byte buffer[] = new byte[1024];
				int len;
				while ((len = inputStream.read(buffer)) > 0){
					out.write(buffer, 0, len);
				}
				out.close();
				inputStream.close();
				String source=prop.getProperty(CMSConstants.UPLOAD_INTERNAL_SUPPLEMENTARY_CSV);
				
				file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				FileInputStream stream1 = new FileInputStream(file);
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
				String year=null;
				
				while(parser.getLine()!=null){
					if(parser.getValueByLabel("AcademicYear") != null && !StringUtils.isEmpty(parser.getValueByLabel("AcademicYear"))){
						 year=parser.getValueByLabel("AcademicYear");
						previousClassDetailsTO = new PreviousClassDetailsTO();
						previousClassDetailsNotExistTO=new PreviousClassDetailsTO();
						}
					
					if(parser.getValueByLabel("RegisterNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNo"))){
						String regNo=parser.getValueByLabel("RegisterNo");
			      		if(studentMap!=null && studentMap.containsKey(regNo)){
			      			StudentTO studentTO =studentMap.get(regNo);
            		    previousClassDetailsTO.setRegNo(studentTO.getRegisterNo());
            		    previousClassDetailsTO.setStudentId(studentTO.getId());
            		    previousClassDetailsTO.setYear(year);
					}
					else{
							previousClassDetailsNotExistTO.setRegNo(regNo);
							previousClassDetailsNotExistTO.setYear(year);
            			}
					}
					if(parser.getValueByLabel("Class") != null && !StringUtils.isEmpty(parser.getValueByLabel("Class"))){
			      		if(classMap!=null && classMap.containsKey(parser.getValueByLabel("Class").trim().toLowerCase()+"_"+year)){
			      			previousClassDetailsTO.setClassName(parser.getValueByLabel("Class"));
                   			previousClassDetailsTO.setClassId(classMap.get(parser.getValueByLabel("Class").trim().toLowerCase()+"_"+year));
                   		}else{
                   			previousClassDetailsNotExistTO.setClassName(parser.getValueByLabel("Class"));
                   			if(previousClassDetailsTO.getRegNo()!=null)
                   			previousClassDetailsNotExistTO.setRegNo(previousClassDetailsTO.getRegNo());
                   			previousClassDetailsNotExistTO.setYear(year);
                   		}
					}
					if(parser.getValueByLabel("SchemeNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("SchemeNo"))){
						previousClassDetailsTO.setSchemeNo(parser.getValueByLabel("SchemeNo"));
					}
					if(previousClassDetailsTO!=null){
		            	if(previousClassDetailsTO.getRegNo()!=null && previousClassDetailsTO.getClassName()!=null)
		            	{
		            		String checkDuplicate=previousClassDetailsTO.getRegNo()+"_"+previousClassDetailsTO.getClassName();
		            		if(!duplicateList.contains(checkDuplicate))
		            		{	
		            			previousClassDetailsTOList.add(previousClassDetailsTO);
		            			previousClassDetailsTO=null;
		            			duplicateList.add(checkDuplicate);
		            		}	
		            			previousClassDetailsNotExistTO=null;
		            	}
		            }
		            
		            if(previousClassDetailsNotExistTO!=null){
		            	if(previousClassDetailsNotExistTO.getRegNo()!=null){
		            	previousClassDetailsNotExistTOList.add(previousClassDetailsNotExistTO);
		            	previousClassDetailsNotExistTO=null;
		            	}
		            }
				}
				boolean isAdded = false;
				if(previousClassDetailsTOList.size()>0){
		    		isAdded = UploadPreviousClassHandler.getInstance().addUploadedData(exportPreviousClassForm, previousClassDetailsTOList);
		    	if(isAdded){
		    		//if adding is success
		    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.success");
		    		messages.add("messages", message);
		    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure
			    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.failure");
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    		}
				    }
			    	if(previousClassDetailsNotExistTOList.size() >0){
			    		writeToCSVFile(previousClassDetailsNotExistTOList,request);
			    		request.setAttribute("fileType", "CSV");
			    		request.setAttribute("fileName", "PreviousClassesCSV");
			    	}
		    } else{		//if the uploaded document is not excel/csv file.
			      		ActionMessage message = new ActionMessage("knowledgepro.admission.upload.excelFile");
			    		errors.add(CMSConstants.ERROR,message);
			    		saveErrors(request, errors);
			    		return mapping.findForward(CMSConstants.UPLOAD_PREVIOUSCLASS_INFO);
				    }
					
				 
		    }else{
		    	saveErrors(request, errors);
		    	return mapping.findForward(CMSConstants.UPLOAD_PREVIOUSCLASS_INFO);
		    }
						
				 
				} catch (BusinessException businessException) {
					log.info("Exception upload Student Previous Classes");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					exportPreviousClassForm.setErrorMessage(msg);
					exportPreviousClassForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				
				log.info("end of upload Student Previous Classes method in ExportPreviousClassAction class.");
				return mapping.findForward(CMSConstants.UPLOAD_EXAM_LINKS_PAGE);
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
			
			 /**
			 * @param previousClassDetailsNotExistTOList
			 * @param request
			 * @throws Exception
			 */
			public void writeToFile(List<PreviousClassDetailsTO> previousClassDetailsNotExistTOList,HttpServletRequest request) throws Exception {
				 
				 Properties prop = new Properties();
					try {
						InputStream inputStream = CommonUtil.class.getClassLoader()
								.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
						prop.load(inputStream);
					} 
					catch (IOException e) {
						log.error("Error occured at  ExportPreviousClassAction ",e);
						throw new IOException(e);
					}
					String fileName=prop.getProperty(CMSConstants.UPLOAD_PREVIOUS_CLASS_EXCEL_DESTINATION);
					File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);	
					if(excelFile.exists()){
						excelFile.delete();
					}
					FileOutputStream fos = null;
					HSSFWorkbook wb = null;
					HSSFSheet sheet =null;
					HSSFRow row = null;
					int count = 0;
					
					try	{
						wb = new HSSFWorkbook();
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
						sheet = wb.createSheet("Student Previous Class");
						count = sheet.getFirstRowNum();
						// Create cells in the row and put some data in it.
						Iterator<PreviousClassDetailsTO> stuNotExistList = previousClassDetailsNotExistTOList.iterator();
					      while (stuNotExistList.hasNext()) 
					      {
					    	  row = sheet.createRow(count);
					    	  PreviousClassDetailsTO studentPreClassTO = (PreviousClassDetailsTO) stuNotExistList.next();
					    	  row.createCell((short)0).setCellValue(new HSSFRichTextString(studentPreClassTO.getYear()));
					    	  row.createCell((short)1).setCellValue(new HSSFRichTextString(studentPreClassTO.getRegNo()));
					    	  row.createCell((short)2).setCellValue(new HSSFRichTextString(studentPreClassTO.getClassName()));
					    	  row.createCell((short)3).setCellValue(new HSSFRichTextString(studentPreClassTO.getSchemeNo()));
					    	  row.createCell((short)4).setCellValue(new HSSFRichTextString(""));
					    	  count++;
					      }
						
						fos = new FileOutputStream(excelFile,true);
						wb.write(fos);
						HttpSession session = request.getSession();
						session.setAttribute(CMSConstants.EXCEL_BYTES, wb.getBytes());
						fos.flush();
						fos.close();
					}catch(Exception e){
						//log

					}
				  }
			
			public void writeToCSVFile(List<PreviousClassDetailsTO> previousClassDetailsNotExistTOList,HttpServletRequest request) throws Exception {
				 
				 Properties prop = new Properties();
					try {
						InputStream inputStream = CommonUtil.class.getClassLoader()
								.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
						prop.load(inputStream);
					} 
					catch (IOException e) {
						log.error("Error occured at writeTOCSV of Action ",e);
						throw new IOException(e);
					}
					String destination=prop.getProperty(CMSConstants.UPLOAD_PREVIOUS_CLASS_EXCEL_DESTINATION);
					File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+destination);	
					if(excelFile.exists()){
						excelFile.delete();
					}
					FileOutputStream fos = null;
					HSSFWorkbook wb = null;
					HSSFSheet sheet =null;
					HSSFRow row = null;
					HSSFCell cell = null;
					int count = 0;
					
					try	{
						wb = new HSSFWorkbook();
						HSSFCellStyle cellStyle = wb.createCellStyle();
						cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
						sheet = wb.createSheet("Student Previous Class");
						row = sheet.createRow(count);
						count = sheet.getFirstRowNum();
						// Create cells in the row and put some data in it.
						Iterator<PreviousClassDetailsTO> stuNotExistList = previousClassDetailsNotExistTOList.iterator();
					      while (stuNotExistList.hasNext()) {
					    	  row = sheet.createRow(count);
					    	  PreviousClassDetailsTO studentPreClassTO = (PreviousClassDetailsTO) stuNotExistList.next();
							row.createCell((short)0).setCellValue(studentPreClassTO.getYear());
							row.createCell((short)1).setCellValue(studentPreClassTO.getRegNo());
							row.createCell((short)2).setCellValue(studentPreClassTO.getClassName());
							row.createCell((short)3).setCellValue(studentPreClassTO.getSchemeNo());
							count++;
					      }
					fos = new FileOutputStream(excelFile,true);
					wb.write(fos);
					fos.flush();
					fos.close();
					
					String csvdestination = prop.getProperty(CMSConstants.UPLOAD_PREVIOUS_CLASS_CSV_DESTINATION);
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
					
					//log

				}
		  }
}
