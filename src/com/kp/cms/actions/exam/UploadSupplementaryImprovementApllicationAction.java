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
import com.kp.cms.forms.exam.UploadSupplementaryApllicationForm;
import com.kp.cms.handlers.exam.UploadSupplementaryApllicationHandler;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.to.exam.UploadSupplementaryAppTO;
import com.kp.cms.utilities.CommonUtil;

	public class UploadSupplementaryImprovementApllicationAction extends BaseDispatchAction{
		private static final Log log = LogFactory.getLog(UploadSupplementaryImprovementApllicationAction.class);
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadSupplementaryApllication(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			return mapping.findForward(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO);
		}
		
		/**
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward uploadSupplementaryApllicationInfo(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			UploadSupplementaryApllicationForm exportSupplementaryApllicationForm = (UploadSupplementaryApllicationForm) form;
			log.info("call of uploadPreviousClassInfo method in ExportPreviousClassAction class.");
			 ActionErrors errors = exportSupplementaryApllicationForm.validate(mapping, request);
			ActionMessages messages = new ActionMessages();
			try {
				setUserId(request,exportSupplementaryApllicationForm);
				
				 if (errors.isEmpty()) {
					 	Map<String,Integer> studentMap=UploadInternalOverAllHandler.getInstance().getStudentMap();
						Map<String,Integer> subjectMap=UploadInternalOverAllHandler.getInstance().getSubjectCodeMap();
						Map<String,Integer> examMap=UploadInternalOverAllHandler.getInstance().getExamMap();
						
				 List<UploadSupplementaryAppTO> supplementaryAppTOList = new ArrayList<UploadSupplementaryAppTO>();
			    List<UploadSupplementaryAppTO> supplementaryAppNotExistTOList = new ArrayList<UploadSupplementaryAppTO>();
			    List<String>duplicateList=new ArrayList<String>();
			    UploadSupplementaryAppTO supplementaryAppTO=null;
			    UploadSupplementaryAppTO supplementaryAppNotExistTO=null;
			    String regNo=null;
		    	String examName=null;
		    	String subCode=null;
		    	String fees=null;
		    	String schemeNo=null;
		    	String chance=null;
		    	boolean isAdded = false;
				FormFile myFile = exportSupplementaryApllicationForm.getThefile();
			    String contentType = myFile.getContentType();
			    	 
			   String fileName    = myFile.getFileName();
			   	File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    
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
					
					XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(request.getRealPath("")+ "//TempFiles//"+source));
	            	XSSFSheet sh=work.getSheetAt(0);
	            	Iterator rows = sh.rowIterator();
	            	 while (rows.hasNext()) {
	                 	XSSFRow row = (XSSFRow) rows.next();
	                     Iterator cells = row.cellIterator();
	                     while (cells.hasNext()) {
	                         XSSFCell cell = (XSSFCell) cells.next();
	                         
	                         if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	                        	 if(cell.getColumnIndex() == 0 && !StringUtils.isEmpty(cell.toString().trim())){
	                        		 supplementaryAppTO= new UploadSupplementaryAppTO();
	                        		 supplementaryAppNotExistTO=new UploadSupplementaryAppTO();
	                        		 examName=cell.toString().trim();
	                        		 if(examName.endsWith(".0")){
											String value = StringUtils.chop(examName);
											examName = StringUtils.chop(value);
	                        		 }	
	 									if(examMap!=null && examMap.containsKey(examName)){
	 										supplementaryAppTO.setExamId(examMap.get(examName));
	 									}else{
	 											supplementaryAppNotExistTO.setExamName(examName);
	 				        					continue;
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
											if(supplementaryAppTO!=null)
											supplementaryAppTO.setStudentId(studentMap.get(regNo));
										}else{
												if(supplementaryAppNotExistTO!=null)
												supplementaryAppNotExistTO.setRegNo(regNo);
					        					continue;
				                			}
									}else{
										if(studentMap!=null && studentMap.containsKey(regNo)){
											if(supplementaryAppTO!=null)
											supplementaryAppTO.setStudentId(studentMap.get(regNo));
										}else{
											if(supplementaryAppNotExistTO!=null)
												supplementaryAppNotExistTO.setRegNo(regNo);
					        					break;
				                			}
										}
	 		                   	} if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		 		                   	if(cell.toString()!=null){
			                   			if(cell.toString().endsWith(".0")){
											String value = StringUtils.chop(cell.toString().trim());
											schemeNo = StringUtils.chop(value);
											supplementaryAppTO.setSchemeNo(Integer.parseInt(schemeNo));
			                   			}else
			                   				supplementaryAppTO.setSchemeNo(Integer.parseInt(cell.toString().trim()));
			                   		}
	 		                   	} if(cell.getColumnIndex() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		 		                   	if(cell.toString()!=null){
			                   			if(cell.toString().endsWith(".0")){
											String value = StringUtils.chop(cell.toString().trim());
											chance = StringUtils.chop(value);
											supplementaryAppTO.setChance(Integer.parseInt(chance));
			                   			}else
				                   		supplementaryAppTO.setChance(Integer.parseInt(cell.toString().trim()));
			                   		}
	 		                   	}if(cell.getColumnIndex() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
										if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
											if(supplementaryAppTO!=null)
											supplementaryAppTO.setSubjectId(subjectMap.get(cell.toString().trim()));
										}else{
											if(supplementaryAppNotExistTO!=null)
												supplementaryAppNotExistTO.setExamName(examName);
												supplementaryAppNotExistTO.setRegNo(regNo);
												supplementaryAppNotExistTO.setSubjectCode(cell.toString());
					        					break;
				                			}
			                	}if(cell.getColumnIndex() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
			                   		if(cell.toString()!=null)
				                   		supplementaryAppTO.setIsFailedTheory((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
				                } if(cell.getColumnIndex() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
			                   		if(cell.toString()!=null)
				                   		supplementaryAppTO.setIsFailedPractical((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
			                   	}if(cell.getColumnIndex() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
			                   		if(cell.toString()!=null)
				                   		supplementaryAppTO.setIsAppearedTheory((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
			                   	} if(cell.getColumnIndex() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
			                   		if(cell.toString()!=null)
				                   		supplementaryAppTO.setIsAppearedPractical((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
			                   	}if(cell.getColumnIndex() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
			                   		if(cell.toString().endsWith(".0")){
										String value = StringUtils.chop(cell.toString().trim());
										fees = StringUtils.chop(value);
										supplementaryAppTO.setFees(fees);
			                   		}else
				                   		supplementaryAppTO.setFees(cell.toString().trim());
			                   	}
	 		                }
	 		            }
	                     if(supplementaryAppTO!=null){
	 		            	if(supplementaryAppTO.getStudentId()>0 && supplementaryAppTO.getSubjectId()>0 && supplementaryAppTO.getExamId()>0)
	 		            	{
	 		            		String checkDuplicate=supplementaryAppTO.getStudentId()+"_"+supplementaryAppTO.getExamId()+"_"+supplementaryAppTO.getSubjectId();
		 		            	if(!duplicateList.contains(checkDuplicate))
		 		            	{	
		 		            		supplementaryAppTOList.add(supplementaryAppTO);
		 		            		duplicateList.add(checkDuplicate);
		 		            	}
	 		            	}
	 		            }
	 		            if(supplementaryAppNotExistTO!=null){
	 		            	if(supplementaryAppNotExistTO.getRegNo()!=null){
	 		            	supplementaryAppNotExistTOList.add(supplementaryAppNotExistTO);
	 		            	supplementaryAppNotExistTO=null;
	 		            	}
	 		            }
	 		    	}
		 	    	if(supplementaryAppTOList.size()>0){
		 	    		isAdded = UploadSupplementaryApllicationHandler.getInstance().saveSupplementaryDetails(supplementaryAppTOList);
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
		 		    	if(supplementaryAppNotExistTOList.size() >0){
		 		    		writeToFile(supplementaryAppNotExistTOList,request);
		 		    		request.setAttribute("fileType","Excel");
		 		    		request.setAttribute("fileName", "ExamSupplementaryImprovementExcel");
		 		    	}
			    	}else if(fileName.endsWith(".xls")){
			    	 byte[] fileData = myFile.getFileData();
			    
			    	 String source1=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION);
						
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
				String source=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION);
				
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
								supplementaryAppTO= new UploadSupplementaryAppTO();
                       		 	supplementaryAppNotExistTO=new UploadSupplementaryAppTO();
                       		 	examName=cell.toString().trim();
	                       		 if(examName.endsWith(".0")){
										String value = StringUtils.chop(examName);
										examName = StringUtils.chop(value);
	                       		 }	
									if(examMap!=null && examMap.containsKey(examName)){
										supplementaryAppTO.setExamId(examMap.get(examName));
									}else{
											supplementaryAppNotExistTO.setExamName(examName);
				        					continue;
			                			}
		                	}
							if(cell.getCellNum() == 1 && 1 <= cols && !StringUtils.isEmpty(cell.toString().trim())){
								regNo=cell.toString().trim();
								if(regNo.startsWith("9"))
			                		regNo="0"+regNo;
 								if(regNo.endsWith(".0")){
									String value = StringUtils.chop(regNo);
									regNo = StringUtils.chop(value);
									if(studentMap!=null && studentMap.containsKey(regNo)){
										if(supplementaryAppTO!=null)
										supplementaryAppTO.setStudentId(studentMap.get(regNo));
									}else{
											if(supplementaryAppNotExistTO!=null)
											supplementaryAppNotExistTO.setRegNo(regNo);
				        					continue;
			                			}
								}else{
									if(studentMap!=null && studentMap.containsKey(regNo)){
										if(supplementaryAppTO!=null)
										supplementaryAppTO.setStudentId(studentMap.get(regNo));
									}else{
										if(supplementaryAppNotExistTO!=null)
											supplementaryAppNotExistTO.setRegNo(regNo);
				        					break;
			                			}
									}
		                   	} if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null){
		                   			if(cell.toString().endsWith(".0")){
										String value = StringUtils.chop(cell.toString().trim());
										schemeNo = StringUtils.chop(value);
										supplementaryAppTO.setSchemeNo(Integer.parseInt(schemeNo));
		                   			}else
		                   				supplementaryAppTO.setSchemeNo(Integer.parseInt(cell.toString().trim()));
		                   		}
		                   	} if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null){
		                   			if(cell.toString().endsWith(".0")){
										String value = StringUtils.chop(cell.toString().trim());
										chance = StringUtils.chop(value);
										supplementaryAppTO.setChance(Integer.parseInt(chance));
		                   			}else
			                   		supplementaryAppTO.setChance(Integer.parseInt(cell.toString().trim()));
		                   		}
		                   	}if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString().trim())){
									if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
										if(supplementaryAppTO!=null)
										supplementaryAppTO.setSubjectId(subjectMap.get(cell.toString().trim()));
									}else{
										if(supplementaryAppNotExistTO!=null)
											supplementaryAppNotExistTO.setExamName(examName);
											supplementaryAppNotExistTO.setRegNo(regNo);
											supplementaryAppNotExistTO.setSubjectCode(cell.toString());
				        					break;
			                			}
		                	}if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null)
			                   		supplementaryAppTO.setIsFailedTheory((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
			                } if(cell.getCellNum() == 6 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null)
			                   		supplementaryAppTO.setIsFailedPractical((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
		                   	}if(cell.getCellNum() == 7 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null)
			                   		supplementaryAppTO.setIsAppearedTheory((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
		                   	} if(cell.getCellNum() == 8 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString()!=null)
			                   		supplementaryAppTO.setIsAppearedPractical((cell.toString().trim().equalsIgnoreCase("TRUE"))?1:0);
		                   	}if(cell.getCellNum() == 9 && !StringUtils.isEmpty(cell.toString().trim())){
		                   		if(cell.toString().endsWith(".0")){
									String value = StringUtils.chop(cell.toString().trim());
									fees = StringUtils.chop(value);
									supplementaryAppTO.setFees(fees);
		                   		}else
			                   		supplementaryAppTO.setFees(cell.toString().trim());
		                   	}
		                }
		            }if(supplementaryAppTO!=null){
		            	if(supplementaryAppTO.getStudentId()>0 && supplementaryAppTO.getSubjectId()>0 && supplementaryAppTO.getExamId()>0){
 		            		String checkDuplicate=supplementaryAppTO.getStudentId()+"_"+supplementaryAppTO.getExamId()+"_"+supplementaryAppTO.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		supplementaryAppTOList.add(supplementaryAppTO);
	 		            		duplicateList.add(checkDuplicate);
	 		            	}

		            	}
		            }
		            if(supplementaryAppNotExistTO!=null){
		            	if(supplementaryAppNotExistTO.getRegNo()!=null){
		            	supplementaryAppNotExistTOList.add(supplementaryAppNotExistTO);
		            	supplementaryAppNotExistTO=null;
		            	}
		            }
		    	}
		    }
	    	if(supplementaryAppTOList.size()>0){
	    		isAdded = UploadSupplementaryApllicationHandler.getInstance().saveSupplementaryDetails(supplementaryAppTOList);
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
		    	if(supplementaryAppNotExistTOList.size() >0){
		    		writeToFile(supplementaryAppNotExistTOList,request);
		    		request.setAttribute("fileType","Excel");
		    		request.setAttribute("fileName", "ExamSupplementaryImprovementExcel");
		    	}
		    }else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
		     	byte[] fileData    = myFile.getFileData();
			    String source1=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_CSV_DESTINATION);
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
				String source=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_CSV_DESTINATION);
				file = new File(source);
				FileInputStream stream1 = new FileInputStream(file);
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
				while(parser.getLine()!=null){
					UploadSupplementaryAppTO exportAppTO = new UploadSupplementaryAppTO();
					 supplementaryAppNotExistTO=new UploadSupplementaryAppTO();
					if(parser.getValueByLabel("ExamName") != null && !StringUtils.isEmpty(parser.getValueByLabel("ExamName"))){
						examName=parser.getValueByLabel("ExamName");
		      		if(examMap!=null && examMap.containsKey(examName)){
		      			exportAppTO.setExamId(examMap.get(examName));
		      		}else{
		      			supplementaryAppNotExistTO.setExamName(examName);
		      			}
					}
					if(parser.getValueByLabel("RegisterNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNo"))){
						regNo=parser.getValueByLabel("RegisterNo");
			      		if(studentMap!=null && studentMap.containsKey(regNo)){
			      			exportAppTO.setStudentId(studentMap.get(regNo));
			      		}else{
			      			supplementaryAppNotExistTO.setRegNo(regNo);
			      		}
					}
					if(parser.getValueByLabel("SchemeNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("SchemeNo"))){
						exportAppTO.setSchemeNo(Integer.parseInt(parser.getValueByLabel("SchemeNo")));
					}
					if(parser.getValueByLabel("Chance") != null && !StringUtils.isEmpty(parser.getValueByLabel("Chance"))){
						exportAppTO.setChance(Integer.parseInt(parser.getValueByLabel("Chance")));
					}
					if(parser.getValueByLabel("SubjectCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubjectCode"))){
			      		if(subjectMap!=null && subjectMap.containsKey(parser.getValueByLabel("SubjectCode"))){
			      			exportAppTO.setSubjectId(subjectMap.get(parser.getValueByLabel("SubjectCode")));
			      		}else{
			      			supplementaryAppNotExistTO.setRegNo(regNo);
			      			supplementaryAppNotExistTO.setExamName(examName);
			      			supplementaryAppNotExistTO.setSubjectCode(parser.getValueByLabel("SubjectCode"));
			      			//break;
			      		}
					}
					if(parser.getValueByLabel("FailedTheory") != null && !StringUtils.isEmpty(parser.getValueByLabel("FailedTheory"))){
						exportAppTO.setIsFailedTheory((parser.getValueByLabel("FailedTheory").trim().equalsIgnoreCase("TRUE"))?1:0);
					}
					if(parser.getValueByLabel("FailedPractical") != null && !StringUtils.isEmpty(parser.getValueByLabel("FailedPractical"))){
						exportAppTO.setIsFailedPractical((parser.getValueByLabel("FailedPractical").trim().equalsIgnoreCase("TRUE"))?1:0);
					}
					if(parser.getValueByLabel("AppearedTheory") != null && !StringUtils.isEmpty(parser.getValueByLabel("AppearedTheory"))){
						exportAppTO.setIsAppearedTheory((parser.getValueByLabel("AppearedTheory").trim().equalsIgnoreCase("TRUE"))?1:0);
					}
					if(parser.getValueByLabel("AppearedPractical") != null && !StringUtils.isEmpty(parser.getValueByLabel("AppearedPractical"))){
						exportAppTO.setIsAppearedPractical((parser.getValueByLabel("AppearedPractical").trim().equalsIgnoreCase("TRUE"))?1:0);
					}
					if(parser.getValueByLabel("Fees") != null && !StringUtils.isEmpty(parser.getValueByLabel("Fees"))){
						exportAppTO.setFees(parser.getValueByLabel("Fees"));
					}
					 if(exportAppTO !=null && exportAppTO.getStudentId()>0 && exportAppTO.getExamId()>0 && exportAppTO.getSubjectId()>0){
		            		String checkDuplicate=exportAppTO.getStudentId()+"_"+exportAppTO.getExamId()+"_"+exportAppTO.getSubjectId();
	 		            	if(!duplicateList.contains(checkDuplicate))
	 		            	{	
	 		            		supplementaryAppTOList.add(exportAppTO);
	 		            		duplicateList.add(checkDuplicate);
	 		            	}

			            }
					 if(supplementaryAppNotExistTO!=null){
						 if(supplementaryAppNotExistTO.getRegNo()!=null){
			            	supplementaryAppNotExistTOList.add(supplementaryAppNotExistTO);
			            	supplementaryAppNotExistTO=null;
						 }
			            }
		      		}
					if(supplementaryAppTOList.size()>0){
						isAdded = UploadSupplementaryApllicationHandler.getInstance().saveSupplementaryDetails(supplementaryAppTOList);
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.success");
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage("knowledgepro.admission.uploadData.failure");
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    		}
			    	}
					if(supplementaryAppNotExistTOList.size() >0){
						writeTOCSV(supplementaryAppNotExistTOList,request);
						request.setAttribute("fileType","CSV");
			    		request.setAttribute("fileName", "ExamSupplementaryImprovementCSV");
			    		}
			      	}else{		//if the uploaded document is not excel/csv file.
				      		ActionMessage message = new ActionMessage("knowledgepro.admission.upload.excelFile");
				    		errors.add(CMSConstants.ERROR,message);
				    		saveErrors(request, errors);
				    		return mapping.findForward(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO);
					    	}
				    }else{
				    	saveErrors(request, errors);
				    	return mapping.findForward(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO);
				    }
				} catch (BusinessException businessException) {
					log.info("Exception upload Student BioData");
					String msgKey = super.handleBusinessException(businessException);
					ActionMessage message = new ActionMessage(msgKey);
					messages.add(CMSConstants.MESSAGES, message);
					return mapping.findForward(CMSConstants.ERROR_PAGE);
				} catch (Exception exception) {	
					String msg = super.handleApplicationException(exception);
					exportSupplementaryApllicationForm.setErrorMessage(msg);
					exportSupplementaryApllicationForm.setErrorStack(exception.getMessage());
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
			
			 /**
			 * @param previousClassDetailsNotExistTOList
			 * @param request
			 * @throws Exception
			 */
			public void writeToFile(List<UploadSupplementaryAppTO> supplementaryAppNotExistTOList,HttpServletRequest request) throws Exception {
				 
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
					String fileName=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION);
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
						sheet = wb.createSheet("Supplementary Details");
						count = sheet.getFirstRowNum();
						// Create cells in the row and put some data in it.
						Iterator<UploadSupplementaryAppTO> stuNotExistList = supplementaryAppNotExistTOList.iterator();
					      while (stuNotExistList.hasNext()) {
					    	  row = sheet.createRow(count);
					    	  UploadSupplementaryAppTO studentNotExistTO = (UploadSupplementaryAppTO) stuNotExistList.next();
							row.createCell((short)0).setCellValue(studentNotExistTO.getExamName());
							row.createCell((short)1).setCellValue(studentNotExistTO.getRegNo());
							row.createCell((short)2).setCellValue(studentNotExistTO.getSubjectCode());
							row.createCell((short)3).setCellValue(studentNotExistTO.getSchemeNo());
							count++;
					      }
						fos = new FileOutputStream(excelFile,true);
						wb.write(fos);
						HttpSession session = request.getSession(false);
						if(session!=null)
						session.setAttribute(CMSConstants.EXCEL_BYTES, wb.getBytes());
						fos.flush();
						fos.close();
					}catch(Exception e){
						//log
					}
				  }
			/**
			 * @param supplementaryAppNotExistTOList
			 * @param request
			 * @throws Exception
			 */
			public  void writeTOCSV(List<UploadSupplementaryAppTO> supplementaryAppNotExistTOList,HttpServletRequest request) throws Exception{
				Properties prop = new Properties();
				try {
					InputStream inputStream = CommonUtil.class.getClassLoader()
							.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
					prop.load(inputStream);
				} 
				catch (IOException e) {
					log.error("Error occured at writeTOCSV of UploadFormatHelper ",e);
					throw new IOException(e);
				}
				String destination=prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_EXCEL_DESTINATION);
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
					sheet = wb.createSheet("Supplementary Details");
					row = sheet.createRow(count);
					count = sheet.getFirstRowNum();
					// Create cells in the row and put some data in it.
					Iterator<UploadSupplementaryAppTO> stuNotExistList = supplementaryAppNotExistTOList.iterator();
				      while (stuNotExistList.hasNext()) {
				    	  row = sheet.createRow(count);
				    	  UploadSupplementaryAppTO studentNotExistTO = (UploadSupplementaryAppTO) stuNotExistList.next();
						row.createCell((short)0).setCellValue(studentNotExistTO.getExamName());
						row.createCell((short)1).setCellValue(studentNotExistTO.getRegNo());
						row.createCell((short)2).setCellValue(studentNotExistTO.getSubjectCode());
						row.createCell((short)3).setCellValue(studentNotExistTO.getSchemeNo());
						count++;
				      }
				fos = new FileOutputStream(excelFile,true);
				wb.write(fos);
				fos.flush();
				fos.close();
				String csvdestination = prop.getProperty(CMSConstants.UPLOAD_SUPPLEMENTARY_INFO_CSV_DESTINATION);
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
