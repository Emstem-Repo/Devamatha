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
import com.kp.cms.forms.exam.UploadAddlBiodataInfoForm;
import com.kp.cms.handlers.exam.UploadAddlBiodataInfoHandler;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.admission.StudentBioDataTO;
import com.kp.cms.to.exam.ExamSpecializationTO;
import com.kp.cms.utilities.CommonUtil;

	public class UploadAddlBiodataInfoAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(UploadAddlBiodataInfoAction.class);
				
		public ActionForward uploadBioData(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UploadAddlBiodataInfoForm expoBiodataInfoForm = (UploadAddlBiodataInfoForm) form;
			expoBiodataInfoForm.resetFields();
			return mapping.findForward(CMSConstants.UPLOAD_BIODATA_INFO);
		}
		
		public ActionForward uploadExportBioDataInfo(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UploadAddlBiodataInfoForm expoBiodataInfoForm = (UploadAddlBiodataInfoForm) form;
			log.info("call of uploadExportBioDataInfo method in ExportAddlBiodataInfoAction class.");
			 ActionErrors errors = expoBiodataInfoForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			try {
				setUserId(request,expoBiodataInfoForm);
				
				 if (errors.isEmpty()) {
					
				FormFile myFile = expoBiodataInfoForm.getThefile();
			    String contentType = myFile.getContentType();
			    	 
			   String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    Map<String,StudentTO> studentMap = UploadAddlBiodataInfoHandler.getInstance().getStudentDetails();
			    Map<String,ExamSpecializationTO> speMap = UploadAddlBiodataInfoHandler.getInstance().getSpecializationDetails();
			    StudentBioDataTO stuBioDataTO=null;
			    StudentBioDataTO stuBioDatanotExistList=null;
			    ExamSpecializationTO examSpecializationTO;
			    List<StudentBioDataTO> stuBioDataTOList = new ArrayList<StudentBioDataTO>();
			    List<String>duplicateList=new ArrayList<String>();
			    List<StudentBioDataTO> stuBioDatanotExistTOList = new ArrayList<StudentBioDataTO>();
			    //HttpSession session = request.getSession();
			    String regNo=null;
			    boolean isAdded = false;
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
					
					XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(source));
	            	XSSFSheet sh=work.getSheetAt(0);
	            	Iterator rows = sh.rowIterator();
	            	 while (rows.hasNext()) {
	                 	XSSFRow row = (XSSFRow) rows.next();
	                     Iterator cells = row.cellIterator();
	                     while (cells.hasNext()) {
	                         XSSFCell cell = (XSSFCell) cells.next();
	                         
	                         if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	                        	 if(cell.getColumnIndex() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
	                        		 regNo=cell.toString().trim();
	                        		 if(regNo.startsWith("9"))
	 			                			regNo="0"+regNo;	
	                        		 if(regNo.endsWith(".0")){
	 									String value = StringUtils.chop(regNo);
	 									regNo = StringUtils.chop(value);
	 									
	 								}
	 									if(studentMap!=null && studentMap.containsKey(regNo)){
	 			                			stuBioDataTO = new StudentBioDataTO();
	 			                		    StudentTO	studentT =studentMap.get(regNo);
	 			                			stuBioDataTO.setStudentId(studentT.getId());
	 			                			stuBioDataTO.setRegNo(studentT.getRegisterNo());
	 									}
	 									else{
	 										stuBioDatanotExistList=new StudentBioDataTO();
	 										stuBioDatanotExistList.setRegNo(regNo);
	 										//errors.add(CMSConstants.ERRORS, new ActionError(""));
	 			        					break;
	 			                		}
	 		                	}
	 							
	 							if(cell.getColumnIndex() == 1 && !StringUtils.isEmpty(cell.toString().trim())){
	 		                   			if(speMap!=null && speMap.containsKey(cell.toString().trim())){
	 		                   				examSpecializationTO=speMap.get(cell.toString().trim());
	 		                   				stuBioDataTO.setSpecializationId(examSpecializationTO.getId());
	 			                		}else{
	 			                			stuBioDatanotExistList=new StudentBioDataTO();
	 			                			stuBioDatanotExistList.setRegNo(stuBioDataTO.getRegNo());
	 			                			stuBioDataTO=null;
	 			                			stuBioDatanotExistList.setSpecializationNotExists(cell.toString().trim());
	 			                			break;
	 			               			}
	 		                   	} if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
	 		                   		if(cell.toString()!=null){
	 			                   		if(cell.toString().endsWith(".0")){
	 										String value = StringUtils.chop(cell.toString().trim());
	 										value = StringUtils.chop(value);	
	 			                   				stuBioDataTO.setConsolidatedMarksCardNo(value);
	 			                   		}else{
	 			                   			stuBioDataTO.setConsolidatedMarksCardNo(cell.toString().trim());
	 			                   		}
	 		                   		}
	 		                   	} if(cell.getColumnIndex() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
	 		                   		if(cell.toString()!=null){
	 		                   			stuBioDataTO.setCourseNameForMarksCard(cell.toString().trim());
	 		                		}
	 		                   	}
	 		                }
	 		            }
	                     if(stuBioDataTO!=null)
	                     {
	                    	 if(stuBioDataTO.getStudentId()>0 && stuBioDataTO.getSpecializationId()>0)
	                    	 {
	                    		 String checkDuplicate=""+stuBioDataTO.getStudentId();
	                    		 if(!duplicateList.contains(checkDuplicate))
	                    		 {	 
	                    			 stuBioDataTOList.add(stuBioDataTO);
	                    			 stuBioDataTO=null;
	                    			 duplicateList.add(checkDuplicate);
	                    		 }	 
	                    	 }
	 		            }
	 		            
	 		            if(stuBioDatanotExistList!=null){
	 		            	stuBioDatanotExistTOList.add(stuBioDatanotExistList);
	 		            	stuBioDatanotExistList=null;
	 		            }
	               }
	            	 if(stuBioDataTOList.size()>0){
	     	    		isAdded = UploadAddlBiodataInfoHandler.getInstance().addUploadedData(stuBioDataTOList);
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
	     		    	if(stuBioDatanotExistTOList.size() >0){
	     		    		writeToExcelFile(stuBioDatanotExistTOList,request);
	     		    		//expoBiodataInfoForm.setDownloadExcel("download");
	     		    		String value="Excel";
	     		    		request.setAttribute("fileType",value);
	     		    		request.setAttribute("fileName", "AddlBioDataExcel");
	     		    	}
			    }else
			     if(fileName.endsWith(".xls")){
			    	 byte[] fileData = myFile.getFileData();
			    
			    	 String source1=prop.getProperty(CMSConstants.UPLOADBIODATA_EXCEL_DESTINATION);
						
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
				String source=prop.getProperty(CMSConstants.UPLOADBIODATA_EXCEL_DESTINATION);
				
				file = new File(request.getRealPath("")+ "//TempFiles//"+source);
			
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
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                if(cell != null && !StringUtils.isEmpty(cell.toString().trim())) {
							if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
								regNo=cell.toString();
								if(regNo.startsWith("9"))
 			                		regNo="0"+regNo;
								if(regNo.endsWith(".0")){
									String value = StringUtils.chop(regNo);
									regNo = StringUtils.chop(value);
									 	
	                        		if(studentMap!=null && studentMap.containsKey(regNo)){
			                			stuBioDataTO = new StudentBioDataTO();
			                		    StudentTO	studentT =studentMap.get(regNo);
			                			stuBioDataTO.setStudentId(studentT.getId());
			                			stuBioDataTO.setRegNo(studentT.getRegisterNo());
									}
									else{
										stuBioDatanotExistList=new StudentBioDataTO();
										stuBioDatanotExistList.setRegNo(regNo);
										//errors.add(CMSConstants.ERRORS, new ActionError(""));
			        					break;
			                		}
								}else{
									if(studentMap!=null && studentMap.containsKey(regNo)){
			                			stuBioDataTO = new StudentBioDataTO();
			                		    StudentTO	studentT =studentMap.get(regNo);
			                			stuBioDataTO.setStudentId(studentT.getId());
			                			stuBioDataTO.setRegNo(studentT.getRegisterNo());
										}
										else{
											stuBioDatanotExistList=new StudentBioDataTO();
											stuBioDatanotExistList.setRegNo(regNo);
											//errors.add(CMSConstants.ERRORS, new ActionError(""));
											break;
				                		}
								}
									
		                	}
							
							if(cell.getCellNum() == 1 && 1 <= cols && !StringUtils.isEmpty(cell.toString().trim())){
		                   			if(speMap!=null && speMap.containsKey(cell.toString().trim())){
		                   				examSpecializationTO=speMap.get(cell.toString().trim());
		                   				stuBioDataTO.setSpecializationId(examSpecializationTO.getId());
			                		}else{
			                			stuBioDatanotExistList=new StudentBioDataTO();
			                			stuBioDatanotExistList.setRegNo(stuBioDataTO.getRegNo());
			                			stuBioDataTO=null;
			                			stuBioDatanotExistList.setSpecializationNotExists(cell.toString().trim());
			                			break;
			               			}
		                   	} if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null){
			                   		if(cell.toString().endsWith(".0")){
										String value = StringUtils.chop(cell.toString().trim());
										value = StringUtils.chop(value);	
			                   				stuBioDataTO.setConsolidatedMarksCardNo(value);
			                   		}else{
			                   			stuBioDataTO.setConsolidatedMarksCardNo(cell.toString().trim());
			                   		}
		                   		}
		                   	} if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null){
		                   			stuBioDataTO.setCourseNameForMarksCard(cell.toString().trim());
		                		}
		                   	}
		                }
		            }if(stuBioDataTO!=null)
		            {
		            	String checkDuplicate=""+stuBioDataTO.getStudentId();
               		 	if(!duplicateList.contains(checkDuplicate))
               		 	{	 
               		 		stuBioDataTOList.add(stuBioDataTO);
               		 		stuBioDataTO=null;
               		 		duplicateList.add(checkDuplicate);
               		 	}
		            }
		            
		            if(stuBioDatanotExistList!=null){
		            	stuBioDatanotExistTOList.add(stuBioDatanotExistList);
		            	stuBioDatanotExistList=null;
		            }
		    	}
		    }
	    	if(stuBioDataTOList.size()>0){
	    		isAdded = UploadAddlBiodataInfoHandler.getInstance().addUploadedData(stuBioDataTOList);
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
		    	if(stuBioDatanotExistTOList.size() >0){
		    		writeToExcelFile(stuBioDatanotExistTOList,request);
		    		//expoBiodataInfoForm.setDownloadExcel("download");
		    		String value="Excel";
		    		request.setAttribute("fileType",value);
		    		request.setAttribute("fileName", "AddlBioDataExcel");
		    	}
		    }
			     else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
			     	  
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
						
						
						while(parser.getLine()!=null){
							if(parser.getValueByLabel("RegisterNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNumber"))){
								 regNo=parser.getValueByLabel("RegisterNumber");
								 stuBioDataTO = new StudentBioDataTO();
								 stuBioDatanotExistList=new StudentBioDataTO();
					      		if(studentMap!=null && studentMap.containsKey(regNo)){
					      			
		                		    StudentTO	studentT =studentMap.get(regNo);
		                			stuBioDataTO.setStudentId(studentT.getId());
		                			stuBioDataTO.setRegNo(studentT.getRegisterNo());
								}
								else{
									
									stuBioDatanotExistList.setRegNo(regNo);
									//errors.add(CMSConstants.ERRORS, new ActionError(""));
		        					//break;
		                		}
							}
							if(parser.getValueByLabel("SpecializedIn") != null && !StringUtils.isEmpty(parser.getValueByLabel("SpecializedIn"))){
					      		if(speMap!=null && speMap.containsKey(parser.getValueByLabel("SpecializedIn"))){
					      			examSpecializationTO=speMap.get(parser.getValueByLabel("SpecializedIn"));
	                   				stuBioDataTO.setSpecializationId(examSpecializationTO.getId());
					      		}else{
					      			if(stuBioDataTO.getRegNo()!=null){
		                			stuBioDatanotExistList.setRegNo(stuBioDataTO.getRegNo());
		                			stuBioDataTO=null;
					      			}
		                			stuBioDatanotExistList.setSpecializationNotExists(parser.getValueByLabel("SpecializedIn"));
		                			//break;
					      		}
							}
							if(parser.getValueByLabel("ConsolidatedMarkSheetNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("ConsolidatedMarkSheetNo"))){
								stuBioDataTO.setConsolidatedMarksCardNo(parser.getValueByLabel("ConsolidatedMarkSheetNo"));
							}
							if(parser.getValueByLabel("CoursenameForMarksCard") != null && !StringUtils.isEmpty(parser.getValueByLabel("CoursenameForMarksCard"))){
								stuBioDataTO.setCourseNameForMarksCard(parser.getValueByLabel("CoursenameForMarksCard"));
							}
							 if(stuBioDataTO !=null)
							 {
								 if(stuBioDataTO.getStudentId()>0 && stuBioDataTO.getSpecializationId()>0)
								 {
									 String checkDuplicate=""+stuBioDataTO.getStudentId();
				               		 if(!duplicateList.contains(checkDuplicate))
				               		 {	 
				               		 	stuBioDataTOList.add(stuBioDataTO);
				               		 	stuBioDataTO=null;
				               		 	duplicateList.add(checkDuplicate);
				               		 }
								 }
					         }
							 if(stuBioDatanotExistList!=null){
								 if(stuBioDatanotExistList.getRegNo()!=null){
					            	stuBioDatanotExistTOList.add(stuBioDatanotExistList);
					            	stuBioDatanotExistList=null;
								 }
					            }
				      	}
						if(stuBioDataTOList.size()>0){
				    		isAdded = UploadAddlBiodataInfoHandler.getInstance().addUploadedData(stuBioDataTOList);
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
					    	if(stuBioDatanotExistTOList.size() >0){
					    		writeToCSVFile(stuBioDatanotExistTOList,request);
					    		//expoBiodataInfoForm.setDownloadExcel("download");
					    		request.setAttribute("fileType", "CSV");
					    		request.setAttribute("fileName", "AddlBioDataCSV");
					    	}
			      	}
		    else{		//if the uploaded document is not excel/csv file.
			      		ActionMessage message = new ActionMessage("knowledgepro.admission.upload.excelFile");
			    		errors.add(CMSConstants.ERROR,message);
			    		saveErrors(request, errors);
			    		return mapping.findForward(CMSConstants.UPLOAD_BIODATA_INFO);
				    }
					
				 
		    }else{
		    	saveErrors(request, errors);
		    	return mapping.findForward(CMSConstants.UPLOAD_BIODATA_INFO);
		    }
						
				 
				} catch (BusinessException businessException) {
					log.info("Exception upload Student BioData");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					expoBiodataInfoForm.setErrorMessage(msg);
					expoBiodataInfoForm.setErrorStack(exception.getMessage());
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				}
				
				log.info("end of upload Student BioData method in ExportAddlBiodataInfoAction class.");
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
			
			 public void writeToExcelFile(List<StudentBioDataTO> stuBioDatanotExistTOList,HttpServletRequest request) throws Exception {
				 
				 Properties prop = new Properties();
					try {
						InputStream inputStream = CommonUtil.class.getClassLoader()
								.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
						prop.load(inputStream);
					} 
					catch (IOException e) {
						log.error("Error occured at  ExportAddlBiodataInfoAction ",e);
						throw new IOException(e);
					}
					String fileName=prop.getProperty(CMSConstants.UPLOADBIODATA_EXCEL_DESTINATION);
					File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);	
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
						sheet = wb.createSheet("Student BioData");
						count = sheet.getFirstRowNum();
						// Create cells in the row and put some data in it.
						Iterator<StudentBioDataTO> stuNotExistList = stuBioDatanotExistTOList.iterator();
					      while (stuNotExistList.hasNext()) {
					    	  row = sheet.createRow(count);
							StudentBioDataTO studentBioDataTO = (StudentBioDataTO) stuNotExistList.next();
							row.createCell((short)0).setCellValue(studentBioDataTO.getRegNo());
							row.createCell((short)1).setCellValue(studentBioDataTO.getSpecializationNotExists());
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
			 
			 public void writeToCSVFile(List<StudentBioDataTO> stuBioDatanotExistTOList,HttpServletRequest request) throws Exception {
				 
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
					String destination=prop.getProperty(CMSConstants.UPLOADBIODATA_EXCEL_DESTINATION);
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
						sheet = wb.createSheet("Student BioData");
						row = sheet.createRow(count);
						count = sheet.getFirstRowNum();
						// Create cells in the row and put some data in it.
						Iterator<StudentBioDataTO> stuNotExistList = stuBioDatanotExistTOList.iterator();
					      while (stuNotExistList.hasNext()) {
					    	  row = sheet.createRow(count);
							StudentBioDataTO studentBioDataTO = (StudentBioDataTO) stuNotExistList.next();
							row.createCell((short)0).setCellValue(studentBioDataTO.getRegNo());
							row.createCell((short)1).setCellValue(studentBioDataTO.getSpecializationNotExists());
							count++;
					      }
					fos = new FileOutputStream(excelFile,true);
					wb.write(fos);
					fos.flush();
					fos.close();
					
					String csvdestination = prop.getProperty(CMSConstants.UPLOADBIODATA_CSV_DESTINATION);
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
