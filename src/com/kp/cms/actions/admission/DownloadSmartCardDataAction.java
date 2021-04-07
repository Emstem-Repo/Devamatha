package com.kp.cms.actions.admission;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.forms.admission.GensmartCardDataForm;
import com.kp.cms.handlers.admission.GensmartCardDataHandler;
import com.kp.cms.to.admin.EmployeeTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.utilities.CommonUtil;

public class DownloadSmartCardDataAction extends DownloadAction {
	private static final Log log = LogFactory.getLog(ExportImagesAction.class);

	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("Entered DownloadSmartCardDataAction");
		GensmartCardDataForm objForm = (GensmartCardDataForm) form;
		
		File zipFile=null;
		//to download smart card data for students
		if(objForm.getSmartCardData()!=null && !objForm.getSmartCardData().isEmpty()){
		String fileName = "SmartCardData"
				+ Calendar.getInstance().getTimeInMillis();
		String zipFileName=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), "dd-MMM-yyyy","yyyy-MM-dd");
		String filePath=request.getRealPath("");
		filePath = filePath + "//TempFiles//"+ zipFileName + ".zip";
		 zipFile=new File(filePath);
		if (zipFile.exists()) {
			zipFile.delete();
		}
		String textFilePath=request.getRealPath("");
		textFilePath = textFilePath + "//TempFiles//"+ fileName + ".txt";
		File textFile = new File(textFilePath);
		
		String cName=null;
		int countStudents=0;
		int countTotalStudents=0;
		List<Integer> stuIdToSetGenerated= new ArrayList<Integer>();
		BufferedWriter output = null;
		  output = new BufferedWriter(new FileWriter(textFile));
		  Map<Integer, List<StudentTO>> smartCardData = objForm
			.getSmartCardData();
			Iterator it = smartCardData.entrySet().iterator();
			while (it.hasNext()) {
				countStudents=0;
				Map.Entry pairs = (Map.Entry) it.next();
				List<StudentTO> list = (List<StudentTO>) pairs.getValue();
				if (!list.isEmpty()) {
					Iterator<StudentTO> stuItr = list.iterator();
					while (stuItr.hasNext()) {
						StudentTO to = (StudentTO) stuItr.next();
						if((to.getRegisterNo()!=null && !to.getRegisterNo().trim().isEmpty()) && to.isPhotoAvaliable()){
						output.write(to.getBankAccNo()+"|"+to.getStudentName() + "|2|" + to.getRegisterNo()+"|"+to.getCourseCode()+"|"+to.getCourseName()+"|"+to.getDob()+"|"
								+to.getBloodGrp()+"|"+to.getGender().charAt(0)+"|"+to.getFatherName()+"|"+to.getMotherName()+"|"+to.getCourseCompletion()+"|"+to.getPermAddress1()+"|"
								+to.getPermAddress2()+"|"+to.getPermanantCityCode()+"|"+to.getPermanantBankStateId()+"|"+to.getPermAddressZip()+"||"+to.getEmail()+"|"+to.getCurrentAddress1()+"|"
								+to.getCurrentAddress2()+"|"+to.getCurrentAddressCity()+","+to.getCurrentAddressState()+","+to.getCurrentAddressZip()+"|"+to.getCurrentCityCode()+"|"
								+to.getCurrentBankStateId()+"|"+to.getCurrentAddressZip()+"|"+to.getMobileNo1()+"|||003|"+to.getFatherName()+"|"+to.getPbankCCode());
						output.newLine();
						countStudents++;
						stuIdToSetGenerated.add(to.getStudentId());
						}
					}
				}
				countTotalStudents+=countStudents;
				cName=GensmartCardDataHandler.getInstance().getCourseName((Integer)pairs.getKey());
				output.write("<br><br>-------"+cName+": "+countStudents+"--------<br><br>");
				output.newLine();
				output.newLine();
			}
			output.write("End of print. No of records: "+countTotalStudents);
		  output.close();
		  response.setHeader("Content-disposition", "attachment; filename =" + zipFileName + ".zip");
		  response.setContentType("application/zip");
		  ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
		  try {
				byte[] data = new byte[1000];
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(textFile));
				int count;
				out.putNextEntry(new ZipEntry(fileName+".txt"));
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				in.close();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		  List<Integer> studentIds=objForm.getStudentIds();
		  Map<String,byte[]> photoMap=GensmartCardDataHandler.getInstance().getStudentPhotos(studentIds);
		  Iterator itr = photoMap.entrySet().iterator();
		  InputStream in = null;
			while (itr.hasNext()) {
				Map.Entry pairs = (Map.Entry) itr.next();
				if(pairs.getKey()!=null){
					String imageName=pairs.getKey().toString()+".jpg";
					byte[] myFileBytes=(byte[])pairs.getValue();
					//byte[] myFileBytes=null;
					//if (pairs.getValue() != null)
					//	myFileBytes = (byte[])pairs.getValue();
					try {
						in = new ByteArrayInputStream(
								myFileBytes);
						byte buffer[] = new byte[2048];

						out.putNextEntry(new ZipEntry(
								imageName));
						int len;
						while ((len = in.read(buffer)) > 0) {
							out.write(buffer, 0, len);
						}
					} catch (IOException e) {
						e.printStackTrace();
						log.info("DownloadImageAction IOException, "+ e);
					} catch (Exception e) {
						e.printStackTrace();
						log.info("DownloadImageAction Exception, " + e);
					}
				}
				}
			out.flush();
			out.close();
		GensmartCardDataHandler.getInstance().update(stuIdToSetGenerated,objForm.getUserId());
		objForm.resetFields();
		objForm.setStudentIds(null);
		objForm.setSmartCardData(null);
		objForm.setPrint(false);
		}
// to download smart card data for employee
		else if(objForm.getSmartCardDataEmp()!=null && !objForm.getSmartCardDataEmp().isEmpty()){

			String fileName = "EmployeeSmartCardData"
					+ Calendar.getInstance().getTimeInMillis();
			String zipFileName=CommonUtil.ConvertStringToDateFormat(CommonUtil.getStringDate(new Date()), "dd-MMM-yyyy","yyyy-MM-dd");
			String filePath=request.getRealPath("");
			filePath = filePath + "//TempFiles//"+ zipFileName + ".zip";
			 zipFile=new File(filePath);
			if (zipFile.exists()) {
				zipFile.delete();
			}
			String textFilePath=request.getRealPath("");
			textFilePath = textFilePath + "//TempFiles//"+ fileName + ".txt";
			File textFile = new File(textFilePath);
			
			String cName=null;
			int countStudents=0;
			int countTotalStudents=0;
			List<Integer> empIdToSetGenerated= new ArrayList<Integer>();
			BufferedWriter output = null;
			  output = new BufferedWriter(new FileWriter(textFile));
			  Map<Integer, List<EmployeeTO>> smartCardDataEmp = objForm.getSmartCardDataEmp();
				Iterator it = smartCardDataEmp.entrySet().iterator();
				while (it.hasNext()) {
					countStudents=0;
					Map.Entry pairs = (Map.Entry) it.next();
					List<EmployeeTO> list = (List<EmployeeTO>) pairs.getValue();
					if (!list.isEmpty()) {
						Iterator<EmployeeTO> empItr = list.iterator();
						while (empItr.hasNext()) {
							EmployeeTO to = (EmployeeTO) empItr.next();
							if((to.getFingerPrintId()!=null && !to.getFingerPrintId().trim().isEmpty()) && to.isPhotoAvailable()){
							output.write(to.getBankAccNo()+"|"+to.getName()+"|1|" + to.getFingerPrintId()+"|"+to.getDepartmentCode()+"|"+to.getDesignationName()+"|"+to.getDob()+"|"
									+to.getBloodGroup()+"|"+to.getGender()+"|||||||||||"+to.getCommunicationAddressLine1()+"|"
									+to.getCommunicationAddressLine2()+"|"+to.getCommunicationAddressCity()+","+to.getCommunicationStateName()+","+to.getCommunicationAddressZip()+"|"+to.getCurrentCityCode()+"|"
									+to.getCurrentBankStateId()+"|"+to.getCommunicationAddressZip()+"|"+to.getMobile()+"|||||||||||||");
							output.newLine();
							countStudents++;
							empIdToSetGenerated.add(to.getId());
							}
						}
					}
					countTotalStudents+=countStudents;
					cName=GensmartCardDataHandler.getInstance().getDepartmentName((Integer)pairs.getKey());
					output.write("<br><br>-------"+cName+": "+countStudents+"--------<br><br>");
					output.newLine();
					output.newLine();
				}
				output.write("End of print. No of records: "+countTotalStudents);
			  output.close();
			  response.setHeader("Content-disposition", "attachment; filename =" + zipFileName + ".zip");
			  response.setContentType("application/zip");
			  ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
			  try {
					byte[] data = new byte[1000];
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(textFile));
					int count;
					out.putNextEntry(new ZipEntry(fileName+".txt"));
					while ((count = in.read(data, 0, 1000)) != -1) {
						out.write(data, 0, count);
					}
					in.close();
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			  List<Integer> employeeIds=objForm.getEmployeeIds();
			  Map<String,byte[]> photoMap=GensmartCardDataHandler.getInstance().getEmployeePhotos(employeeIds);
			  Iterator itr = photoMap.entrySet().iterator();
			  InputStream in = null;
				while (itr.hasNext()) {
					Map.Entry pairs = (Map.Entry) itr.next();
					if(pairs.getKey()!=null){
						String imageName=pairs.getKey().toString()+".jpg";
						byte[] myFileBytes=null;
						if (pairs.getValue() != null)
							myFileBytes = (byte[])pairs.getValue();
						try {
							in = new ByteArrayInputStream(
									myFileBytes);
							byte buffer[] = new byte[2048];

							out.putNextEntry(new ZipEntry(
									imageName));
							int len;
							while ((len = in.read(buffer)) > 0) {
								out.write(buffer, 0, len);
							}
						} catch (IOException e) {
							e.printStackTrace();
							log.info("DownloadImageAction IOException, "+ e);
						} catch (Exception e) {
							e.printStackTrace();
							log.info("DownloadImageAction Exception, " + e);
						}
					}
					}
				out.flush();
				out.close();
			GensmartCardDataHandler.getInstance().updateEmployee(empIdToSetGenerated,objForm.getUserId());
			objForm.resetFields();
			objForm.setEmployeeIds(null);
			objForm.setSmartCardDataEmp(null);
			objForm.setPrint(false);
		}
		return new FileStreamInfo("write.txt", zipFile);
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
