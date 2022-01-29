package com.kp.cms.actions.exam;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.forms.exam.ExamValuationStatusForm;
import com.kp.cms.to.exam.ExamValidationDetailsTO;
import com.kp.cms.to.exam.ExamValuationStatusTO;

public class ExamValuationStatusDownloadAction extends DownloadAction {
	private static final Log log = LogFactory.getLog(ExamValuationStatusDownloadAction.class);
	/* (non-Javadoc)
	 * @see org.apache.struts.actions.DownloadAction#getStreamInfo(org.apache.struts.action.ActionMapping, org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("Entered ExamValuationStatusDownloadAction");
		ExamValuationStatusForm valuationForm=(ExamValuationStatusForm)form;
		String excelfileName = "ValuationStatus";
		String filePath=request.getRealPath("")+ "//TempFiles//";

		File fCSV = new File(filePath+excelfileName+".xls");
		if(fCSV.exists()){
			fCSV.delete();
		}
		 response.setHeader("Content-disposition", "attachment; filename =" + excelfileName + ".xls");
		  response.setContentType("application/xls");
		  
		FileOutputStream csvfos = null;
		HSSFWorkbook wb = null;
		HSSFSheet sheet =null;
		HSSFRow row = null;
		int count = 0;
		if(valuationForm.getValuationStatus()!=null){
		try	{
			wb = new HSSFWorkbook();
			HSSFCellStyle cellStyle = wb.createCellStyle();
			cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("ValuationStatus");
			
			
			row = sheet.createRow(count);
			// Creating cells in the row and put some data in it.
			
			row.createCell((short)0).setCellValue("Program Type");// added by sudhir
			row.createCell((short)1).setCellValue("Course");
			row.createCell((short)2).setCellValue("Subject Code");
			row.createCell((short)3).setCellValue("Subject Name");
			row.createCell((short)4).setCellValue("Issued For Valuation To");
			row.createCell((short)5).setCellValue("Valuator");
			row.createCell((short)6).setCellValue("Valuation Completed");
			row.createCell((short)7).setCellValue("Verification Completed");
			row.createCell((short)8).setCellValue("Mismatch Found");
			row.createCell((short)9).setCellValue("Valuation Process Completed(COE)");
			row.createCell((short)10).setCellValue("Overall Process Completed (Exam Office)");
			row.createCell((short)11).setCellValue(" ");
			
			Iterator<ExamValuationStatusTO> itr=valuationForm.getValuationStatus().iterator();
			while (itr.hasNext()) {
				ExamValuationStatusTO examValuationStatusTO = (ExamValuationStatusTO) itr.next();
				if(request.getParameter("fileType").equalsIgnoreCase("NotCertificate")){
					if(!examValuationStatusTO.isCertificateCourse()){       // certificate courses are avoiding in export to excel 
						if(!examValuationStatusTO.getSubjectName().equalsIgnoreCase("HOLISTIC EDUCATION")){
							count = count +1;
							row = sheet.createRow(count);	
							row.createCell((short)0).setCellValue(examValuationStatusTO.getProgramType());	// added by sudhir
							row.createCell((short)1).setCellValue(examValuationStatusTO.getCourseCode());	
							row.createCell((short)2).setCellValue(examValuationStatusTO.getSubjectCode());
							row.createCell((short)3).setCellValue(examValuationStatusTO.getSubjectName());
							String empName="";
							if(examValuationStatusTO.getEvaluatorDetails()!=null && !examValuationStatusTO.getEvaluatorDetails().isEmpty()){
								Iterator<ExamValidationDetailsTO> evaluatorItr=examValuationStatusTO.getEvaluatorDetails().iterator();
								while (evaluatorItr.hasNext()) {
									ExamValidationDetailsTO examValidationDetailsTO = (ExamValidationDetailsTO) evaluatorItr.next();
									if(examValidationDetailsTO.getEmployeeName()!=null)
										if(empName.isEmpty())
											empName=examValidationDetailsTO.getEmployeeName();
										else 
											empName=empName+","+examValidationDetailsTO.getEmployeeName();
								}
								row.createCell((short)4).setCellValue(empName);
							}
							String evaluatorTypeId="";
							if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
								Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
								while (statusItr.hasNext()) {
									ExamValuationStatusTO statusTo = (ExamValuationStatusTO) statusItr.next();
									if(statusTo.getEvaluatorTypeId()!=null)
										if(evaluatorTypeId.isEmpty())
											evaluatorTypeId=statusTo.getEvaluatorTypeId();
										else 
											evaluatorTypeId=evaluatorTypeId+","+statusTo.getEvaluatorTypeId();
								}
								row.createCell((short)5).setCellValue(evaluatorTypeId);
							}
							String valuationCompleted="";
							if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
								Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
								while (statusItr.hasNext()) {
									ExamValuationStatusTO statusTo2 = (ExamValuationStatusTO) statusItr.next();
									if(statusTo2.getValuationCompleted()!=null)
										if(valuationCompleted.isEmpty())
											valuationCompleted=statusTo2.getValuationCompleted();
										else 
											valuationCompleted=valuationCompleted+","+statusTo2.getValuationCompleted();
								}
								row.createCell((short)6).setCellValue(valuationCompleted);
							}
							String verificationCompleted="";
							if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
								Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
								while (statusItr.hasNext()) {
									ExamValuationStatusTO statusTo3 = (ExamValuationStatusTO) statusItr.next();
									if(statusTo3.getVerificationCompleted()!=null)
										if(verificationCompleted.isEmpty())
											verificationCompleted=statusTo3.getVerificationCompleted();
										else 
											verificationCompleted=verificationCompleted+","+statusTo3.getVerificationCompleted();
								}
								row.createCell((short)7).setCellValue(verificationCompleted);
							}
							String mismatchFound="";
							if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
								Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
								while (statusItr.hasNext()) {
									ExamValuationStatusTO statusTo4 = (ExamValuationStatusTO) statusItr.next();
									if(statusTo4.getMisMatchFound()!=null)
										if(mismatchFound.isEmpty())
											mismatchFound=statusTo4.getMisMatchFound();
										else 
											mismatchFound=mismatchFound+","+statusTo4.getMisMatchFound();
								}
								row.createCell((short)8).setCellValue(mismatchFound);
							}
							if(examValuationStatusTO.getValuationProcess()!=null && examValuationStatusTO.getValuationProcess().equalsIgnoreCase("on"))
								row.createCell((short)9).setCellValue("Yes");
							else row.createCell((short)9).setCellValue("No");
							if(examValuationStatusTO.getOverallProcess()!=null && examValuationStatusTO.getOverallProcess().equalsIgnoreCase("on"))
								row.createCell((short)10).setCellValue("Yes");
							else row.createCell((short)10).setCellValue("No");
						}
					}
				}else if(request.getParameter("fileType").equalsIgnoreCase("Certificate")){
					count = count +1;
					row = sheet.createRow(count);	
					row.createCell((short)0).setCellValue(examValuationStatusTO.getProgramType());	// added by sudhir
					row.createCell((short)1).setCellValue(examValuationStatusTO.getCourseCode());	
					row.createCell((short)2).setCellValue(examValuationStatusTO.getSubjectCode());
					row.createCell((short)3).setCellValue(examValuationStatusTO.getSubjectName());
					String empName="";
					if(examValuationStatusTO.getEvaluatorDetails()!=null && !examValuationStatusTO.getEvaluatorDetails().isEmpty()){
						Iterator<ExamValidationDetailsTO> evaluatorItr=examValuationStatusTO.getEvaluatorDetails().iterator();
						while (evaluatorItr.hasNext()) {
							ExamValidationDetailsTO examValidationDetailsTO = (ExamValidationDetailsTO) evaluatorItr.next();
							if(examValidationDetailsTO.getEmployeeName()!=null)
								if(empName.isEmpty())
									empName=examValidationDetailsTO.getEmployeeName();
								else 
									empName=empName+","+examValidationDetailsTO.getEmployeeName();
						}
						row.createCell((short)4).setCellValue(empName);
					}
					String evaluatorTypeId="";
					if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
						Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
						while (statusItr.hasNext()) {
							ExamValuationStatusTO statusTo = (ExamValuationStatusTO) statusItr.next();
							if(statusTo.getEvaluatorTypeId()!=null)
								if(evaluatorTypeId.isEmpty())
									evaluatorTypeId=statusTo.getEvaluatorTypeId();
								else 
									evaluatorTypeId=evaluatorTypeId+","+statusTo.getEvaluatorTypeId();
						}
						row.createCell((short)5).setCellValue(evaluatorTypeId);
					}
					String valuationCompleted="";
					if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
						Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
						while (statusItr.hasNext()) {
							ExamValuationStatusTO statusTo2 = (ExamValuationStatusTO) statusItr.next();
							if(statusTo2.getValuationCompleted()!=null)
								if(valuationCompleted.isEmpty())
									valuationCompleted=statusTo2.getValuationCompleted();
								else 
									valuationCompleted=valuationCompleted+","+statusTo2.getValuationCompleted();
						}
						row.createCell((short)6).setCellValue(valuationCompleted);
					}
					String verificationCompleted="";
					if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
						Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
						while (statusItr.hasNext()) {
							ExamValuationStatusTO statusTo3 = (ExamValuationStatusTO) statusItr.next();
							if(statusTo3.getVerificationCompleted()!=null)
								if(verificationCompleted.isEmpty())
									verificationCompleted=statusTo3.getVerificationCompleted();
								else 
									verificationCompleted=verificationCompleted+","+statusTo3.getVerificationCompleted();
						}
						row.createCell((short)7).setCellValue(verificationCompleted);
					}
					String mismatchFound="";
					if(examValuationStatusTO.getStatusTOs()!=null && !examValuationStatusTO.getStatusTOs().isEmpty()){
						Iterator<ExamValuationStatusTO> statusItr=examValuationStatusTO.getStatusTOs().iterator();
						while (statusItr.hasNext()) {
							ExamValuationStatusTO statusTo4 = (ExamValuationStatusTO) statusItr.next();
							if(statusTo4.getMisMatchFound()!=null)
								if(mismatchFound.isEmpty())
									mismatchFound=statusTo4.getMisMatchFound();
								else 
									mismatchFound=mismatchFound+","+statusTo4.getMisMatchFound();
						}
						row.createCell((short)8).setCellValue(mismatchFound);
					}
					if(examValuationStatusTO.getValuationProcess()!=null && examValuationStatusTO.getValuationProcess().equalsIgnoreCase("on"))
						row.createCell((short)9).setCellValue("Yes");
					else row.createCell((short)9).setCellValue("No");
					if(examValuationStatusTO.getOverallProcess()!=null && examValuationStatusTO.getOverallProcess().equalsIgnoreCase("on"))
						row.createCell((short)10).setCellValue("Yes");
					else row.createCell((short)10).setCellValue("No");
				}
		}
			csvfos = new FileOutputStream(fCSV,true);
			wb.write(csvfos);
			csvfos.flush();
			csvfos.close();
	}
		catch(Exception e){
			e.printStackTrace();
		}
		}
		return new FileStreamInfo("write.csv", fCSV);
	}
	protected class ByteArrayStreamInfo implements StreamInfo {

		protected String contentType;
		protected byte[] bytes;

		public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
			this.contentType = contentType;
			this.bytes = myXLSBytes;
		}

		public String getContentType() {
			return contentType;
		}

		public InputStream getInputStream() throws IOException {
			return new ByteArrayInputStream(bytes);
		}
	}
}
