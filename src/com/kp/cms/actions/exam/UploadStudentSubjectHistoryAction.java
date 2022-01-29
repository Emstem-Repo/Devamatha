package com.kp.cms.actions.exam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.kp.cms.actions.exam.UploadInternalAttendanceOverAllAction.ByteArrayStreamInfo;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.exam.UploadStudentSubjectHistoryForm;
import com.kp.cms.handlers.exam.UploadInternalOverAllHandler;
import com.kp.cms.handlers.exam.UploadStudentSubjectHistoryHandler;
import com.kp.cms.to.exam.UploadInternalOverAllTo;

public class UploadStudentSubjectHistoryAction extends BaseDispatchAction {
	private static final Log log = LogFactory.getLog(UploadStudentSubjectHistoryAction.class);
	/**
	 * Method to set the required data to the form to display it in uploadStudentSubjectGroup.jsp
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initUploadStudentSubjectGroup(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered initUploadStudentSubjectGroup input");
		UploadStudentSubjectHistoryForm uploadStudentSubjectHistoryForm = (UploadStudentSubjectHistoryForm) form;
		uploadStudentSubjectHistoryForm.resetFields();
		log.info("Exit initUploadStudentSubjectGroup input");
		
		return mapping.findForward(CMSConstants.UPLOAD_STUDENT_SUBJECT_GROUP);
	}
	
	/**
	 * Method to select the candidates for score sheet result entry based on the input selected
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateExamStudentSubjectGroupHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		log.info("Entered ScoreSheetAction - getCandidates");
		UploadStudentSubjectHistoryForm uploadStudentSubjectHistoryForm = (UploadStudentSubjectHistoryForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors = uploadStudentSubjectHistoryForm.validate(mapping, request);
		setUserId(request, uploadStudentSubjectHistoryForm);
		if (errors.isEmpty()) {
		
			try {
				Map<String,Integer> studnetMap=UploadInternalOverAllHandler.getInstance().getStudentMap();
				Map<String,Integer> subjectMap=UploadInternalOverAllHandler.getInstance().getSubjectCodeMap();
				
				// reading xlsx code from here
				FormFile myFile = uploadStudentSubjectHistoryForm.getTheFile();
			    String contentType = myFile.getContentType();
			    String fileName    = myFile.getFileName();
				File file = null;
			    Properties prop = new Properties();
			    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
			    prop.load(stream);
			    Map<String,List<Integer>> resultMap=new HashMap<String, List<Integer>>();
			    //if the uploaded document is excel file.
			    if(fileName.endsWith(".xlsx")){
			    	byte[] fileData = myFile.getFileData();
			    	String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
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
					String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
					file = new File(request.getRealPath("")+ "//TempFiles//"+source);
					
					XSSFWorkbook work=new XSSFWorkbook(new FileInputStream(request.getRealPath("")+ "//TempFiles//"+source));
	            	XSSFSheet sh=work.getSheetAt(0);
	            	Iterator rows = sh.rowIterator();
	            	 while (rows.hasNext()) {
	                 	XSSFRow row = (XSSFRow) rows.next();
	                     Iterator cells = row.cellIterator();
	                     String year="";
	     		        String registerNo="";
	     		        String schemeNo="";
	     		        String subCode="";
	     		        String IsSecLanguage="";
	     		        String IsElective="";
	     		        String studentId="";
	                     while (cells.hasNext()) {
	                         XSSFCell cell = (XSSFCell) cells.next();
	                         
	                         if(cell != null && !StringUtils.isEmpty(cell.toString())) {
	                        	 
	                        	 if(cell.getColumnIndex() == 0 && !StringUtils.isEmpty(cell.toString()))
	 		                	{	
	 		                		if(cell.toString().trim().length()>0)
	 		                		{	
	 		                			registerNo=cell.toString().trim();
	 		                			if(registerNo.startsWith("9"))
	 		                				registerNo="0"+registerNo;
	 		                			if(registerNo.endsWith(".0"))
	 			                		{
	 			                			registerNo=StringUtils.chop(registerNo);
	 			                			registerNo=StringUtils.chop(registerNo);
	 			                		}
	 		                			if(studnetMap!=null && studnetMap.containsKey(registerNo)){
	 		                				studentId= String.valueOf(studnetMap.get(registerNo));
	 			                		}
	 		                		}
	 		                		
	 							}
	 		                	if(cell.getColumnIndex() ==1 && !StringUtils.isEmpty(cell.toString())){
	 		                		schemeNo=cell.toString().trim();
	 		                		if(schemeNo.endsWith(".0"))
	 		                		{
	 		                			schemeNo=StringUtils.chop(schemeNo);
	 		                			schemeNo=StringUtils.chop(schemeNo);
	 		                		}
	 		                	}
	 		                	if(cell.getColumnIndex() == 2 && !StringUtils.isEmpty(cell.toString())){
	 		                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
	 		                			subCode=String.valueOf(subjectMap.get(cell.toString().trim()));
	 		                		}
	 							} 
	 		                	if(cell.getColumnIndex() == 3 && !StringUtils.isEmpty(cell.toString())){
	 		                		IsSecLanguage=cell.toString().trim();
	 		                	}
	 		                	if(cell.getColumnIndex() == 4 && !StringUtils.isEmpty(cell.toString())){
	 		                		IsElective=cell.toString().trim();
	 		                	}
	 		                	if(cell.getColumnIndex() == 5 && !StringUtils.isEmpty(cell.toString())){
	 		                		year=cell.toString().trim();
	 		                	}
	                        	 
	                        	 
	 		                }
	 		            }
	                     if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(registerNo) && !StringUtils.isEmpty(schemeNo)
	 		            		&& !StringUtils.isEmpty(subCode) && !StringUtils.isEmpty(IsElective) && !StringUtils.isEmpty(IsSecLanguage) && !StringUtils.isEmpty(studentId)){
	 		            	if(IsSecLanguage.equalsIgnoreCase("true") || IsElective.equalsIgnoreCase("true")){
	 		      				if(IsSecLanguage.equalsIgnoreCase("true")){
	 		      					//checking for second language
	 		      					if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"
	 			      						+schemeNo+"_"+year+"_s")){
	 			      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s");
	 			      					if(!list.contains(Integer.parseInt(subCode))){
	 			      						list.add(Integer.parseInt(subCode));
	 			      					}
	 			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s",list);
	 			      				}else{
	 			      					List<Integer> list=new ArrayList<Integer>();
	 			      					list.add(Integer.parseInt(subCode));
	 			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s",list);
	 			      				}
	 		      				}else{
	 		      					// checking for elective
	 		      					if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e")){
	 			      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e");
	 			      					if(!list.contains(Integer.parseInt(subCode))){
	 			      						list.add(Integer.parseInt(subCode));
	 			      					}
	 			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e",list);
	 			      				}else{
	 			      					List<Integer> list=new ArrayList<Integer>();
	 			      					list.add(Integer.parseInt(subCode));
	 			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e",list);
	 			      				}
	 		      				}
	 		      			}else{
	 		      				// checking for common subject
	 		      				if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o")){
	 		      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o");
	 		      					if(!list.contains(Integer.parseInt(subCode))){
	 		      						list.add(Integer.parseInt(subCode));
	 		      					}
	 		      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o",list);
	 		      				}else{
	 		      					List<Integer> list=new ArrayList<Integer>();
	 		      					list.add(Integer.parseInt(subCode));
	 		      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o",list);
	 		      				}
	 		      			}
	 		            }
	               }
	            	 if(!resultMap.isEmpty()){
	 		    		String user = uploadStudentSubjectHistoryForm.getUserId();
	 		    		boolean isAdded = UploadStudentSubjectHistoryHandler.getInstance().addUploadedData(resultMap, user);
	 			    	if(isAdded){
	 			    		//if adding is success.
	 			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_SUCCESS);
	 			    		messages.add("messages", message);
	 			    		saveMessages(request, messages);
	 			    	}else{
	 			    		//if adding is failure.
	 			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_FAILURE);
	 			    		errors.add(CMSConstants.ERROR, message);
	 			    		addErrors(request, errors);
	 			    	}
	 				}
			    }else if(fileName.endsWith(".xls")){
			    	 byte[] fileData = myFile.getFileData();
					    
			    	 String source1=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
						
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
				String source=prop.getProperty(CMSConstants.UPLOAD_EXAM_INTERNAL_SUPPLEMENTARY);
				
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
		        String year="";
		        String registerNo="";
		        String schemeNo="";
		        String subCode="";
		        String IsSecLanguage="";
		        String IsElective="";
		        String studentId="";
		        if(row != null) {
		            for(int c = 0; c < cols;c++) {
		                cell = row.getCell((byte)c);
		                	if(cell.getCellNum() == 0 && !StringUtils.isEmpty(cell.toString()))
		                	{	
		                		if(cell.toString().trim().length()>0)
		                		{	
		                			registerNo=cell.toString().trim();
		                			if(registerNo.startsWith("9"))
		                				registerNo="0"+registerNo;
		                			if(registerNo.endsWith(".0"))
			                		{
			                			registerNo=StringUtils.chop(registerNo);
			                			registerNo=StringUtils.chop(registerNo);
			                		}
		                			if(studnetMap!=null && studnetMap.containsKey(registerNo)){
		                				studentId= String.valueOf(studnetMap.get(registerNo));
			                		}
		                		}
		                		
							}
		                	if(cell.getCellNum() ==1 && !StringUtils.isEmpty(cell.toString())){
		                		schemeNo=cell.toString().trim();
		                		if(schemeNo.endsWith(".0"))
		                		{
		                			schemeNo=StringUtils.chop(schemeNo);
		                			schemeNo=StringUtils.chop(schemeNo);
		                		}
		                	}
		                	if(cell.getCellNum() == 2 && !StringUtils.isEmpty(cell.toString())){
		                		if(subjectMap!=null && subjectMap.containsKey(cell.toString().trim())){
		                			subCode=String.valueOf(subjectMap.get(cell.toString().trim()));
		                		}
							} 
		                	if(cell.getCellNum() == 3 && !StringUtils.isEmpty(cell.toString())){
		                		IsSecLanguage=cell.toString().trim();
		                	}
		                	if(cell.getCellNum() == 4 && !StringUtils.isEmpty(cell.toString())){
		                		IsElective=cell.toString().trim();
		                	}
		                	if(cell.getCellNum() == 5 && !StringUtils.isEmpty(cell.toString())){
		                		year=cell.toString().trim();
		                	}
		                	
		                }
		            }
		            if(!StringUtils.isEmpty(year) && !StringUtils.isEmpty(registerNo) && !StringUtils.isEmpty(schemeNo)
		            		&& !StringUtils.isEmpty(subCode) && !StringUtils.isEmpty(IsElective) && !StringUtils.isEmpty(IsSecLanguage) && !StringUtils.isEmpty(studentId)){
		            	if(IsSecLanguage.equalsIgnoreCase("true") || IsElective.equalsIgnoreCase("true")){
		      				if(IsSecLanguage.equalsIgnoreCase("true")){
		      					//checking for second language
		      					if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"
			      						+schemeNo+"_"+year+"_s")){
			      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s");
			      					if(!list.contains(Integer.parseInt(subCode))){
			      						list.add(Integer.parseInt(subCode));
			      					}
			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s",list);
			      				}else{
			      					List<Integer> list=new ArrayList<Integer>();
			      					list.add(Integer.parseInt(subCode));
			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_s",list);
			      				}
		      				}else{
		      					// checking for elective
		      					if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e")){
			      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e");
			      					if(!list.contains(Integer.parseInt(subCode))){
			      						list.add(Integer.parseInt(subCode));
			      					}
			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e",list);
			      				}else{
			      					List<Integer> list=new ArrayList<Integer>();
			      					list.add(Integer.parseInt(subCode));
			      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_e",list);
			      				}
		      				}
		      			}else{
		      				// checking for common subject
		      				if(resultMap.containsKey(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o")){
		      					List<Integer> list=resultMap.remove(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o");
		      					if(!list.contains(Integer.parseInt(subCode))){
		      						list.add(Integer.parseInt(subCode));
		      					}
		      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o",list);
		      				}else{
		      					List<Integer> list=new ArrayList<Integer>();
		      					list.add(Integer.parseInt(subCode));
		      					resultMap.put(registerNo+"_"+studnetMap.get(registerNo)+"_"+schemeNo+"_"+year+"_o",list);
		      				}
		      			}
		            }
		        	}
			    if(!resultMap.isEmpty()){
		    		String user = uploadStudentSubjectHistoryForm.getUserId();
		    		boolean isAdded = UploadStudentSubjectHistoryHandler.getInstance().addUploadedData(resultMap, user);
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
				}
		    	}
				
		      	else if(fileName.endsWith(".csv")){         //if the uploaded document is csv file.
		     	  
		     	byte[] fileData    = myFile.getFileData();
			    String source1=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
				
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
				String source=prop.getProperty(CMSConstants.UPLOAD_INTERVIEW_CSV);
				
				file = new File(request.getRealPath("")+ "//TempFiles//"+source);
				FileInputStream stream1 = new FileInputStream(file);
				LabeledCSVParser parser= new LabeledCSVParser(new CSVParser(stream1));
				while(parser.getLine()!=null){
					if(parser.getValueByLabel("RegisterNumber") != null && !StringUtils.isEmpty(parser.getValueByLabel("RegisterNumber")) 
							&& parser.getValueByLabel("SchemeNo") != null && !StringUtils.isEmpty(parser.getValueByLabel("SchemeNo")) 
							&& parser.getValueByLabel("SubjectCode") != null && !StringUtils.isEmpty(parser.getValueByLabel("SubjectCode")) 
							&& parser.getValueByLabel("IsSecLanguage") != null && !StringUtils.isEmpty(parser.getValueByLabel("IsSecLanguage"))
							&& parser.getValueByLabel("IsElective") != null && !StringUtils.isEmpty(parser.getValueByLabel("IsElective"))
							&& parser.getValueByLabel("AdmittedYear") != null && !StringUtils.isEmpty(parser.getValueByLabel("AdmittedYear"))){
			      		if(studnetMap!=null && studnetMap.containsKey(parser.getValueByLabel("RegisterNumber")) 
			      				&& subjectMap!=null && subjectMap.containsKey(parser.getValueByLabel("SubjectCode"))){
			      			if(parser.getValueByLabel("IsSecLanguage").equalsIgnoreCase("true") || parser.getValueByLabel("IsElective").equalsIgnoreCase("true")){
			      				if(parser.getValueByLabel("IsSecLanguage").equalsIgnoreCase("true")){
			      					//checking for second language
			      					if(resultMap.containsKey(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_s")){
				      					List<Integer> list=resultMap.remove(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_s");
				      					if(!list.contains(subjectMap.get(parser.getValueByLabel("SubjectCode")))){
				      						list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
				      					}
				      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
					      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_s",list);
				      				}else{
				      					List<Integer> list=new ArrayList<Integer>();
				      					list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
				      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_s",list);
				      				}
			      				}else{
			      					// checking for elective
			      					if(resultMap.containsKey(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_e")){
				      					List<Integer> list=resultMap.remove(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_e");
				      					if(!list.contains(subjectMap.get(parser.getValueByLabel("SubjectCode")))){
				      						list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
				      					}
				      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
					      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_e",list);
				      				}else{
				      					List<Integer> list=new ArrayList<Integer>();
				      					list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
				      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_e",list);
				      				}
			      				}
			      			}else{
			      				// checking for common subject
			      				if(resultMap.containsKey(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
			      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_o")){
			      					List<Integer> list=resultMap.remove(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
			      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_o");
			      					if(!list.contains(subjectMap.get(parser.getValueByLabel("SubjectCode")))){
			      						list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
			      					}
			      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
				      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_o",list);
			      				}else{
			      					List<Integer> list=new ArrayList<Integer>();
			      					list.add(subjectMap.get(parser.getValueByLabel("SubjectCode")));
			      					resultMap.put(parser.getValueByLabel("RegisterNumber")+"_"+studnetMap.get(parser.getValueByLabel("RegisterNumber"))+"_"
			      						+parser.getValueByLabel("SchemeNo")+"_"+parser.getValueByLabel("AdmittedYear")+"_o",list);
			      				}
			      			}
			      		}else{
			      			continue;
			      		}
					}
					
		      	}
					boolean isAdded = false;
					if(!resultMap.isEmpty()){

			    		String user = uploadStudentSubjectHistoryForm.getUserId();
			    		isAdded = UploadStudentSubjectHistoryHandler.getInstance().addUploadedData(resultMap, user);
			    	if(isAdded){
			    		//if adding is success.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_SUCCESS);
			    		messages.add("messages", message);
			    		saveMessages(request, messages);
			    	}else{
			    		//if adding is failure.
			    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_EXAM_SUBJECT_GRP_HISTORY_FAILURE);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
			    	}
			    	
					}
					
			      	}else{		//if the uploaded document is not excel/csv file.
			      		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_INTERVIEW_DOCUMENT);
			    		errors.add(CMSConstants.ERROR,message);
			    		saveErrors(request, errors);
				    }
					} catch (Exception exception) {
				String msg = super.handleApplicationException(exception);
				uploadStudentSubjectHistoryForm.setErrorMessage(msg);
				uploadStudentSubjectHistoryForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			addErrors(request, errors);
			log.info("Exit Interview Batch Result - getSelectedCandidates errors not empty ");
			return mapping.findForward(CMSConstants.UPLOAD_STUDENT_SUBJECT_GROUP);
		}
		log.info("Entered ScoreSheetAction - getCandidates");
		return mapping.findForward(CMSConstants.UPLOAD_STUDENT_SUBJECT_GROUP);
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
}
