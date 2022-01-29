package com.kp.cms.actions.idcard;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DownloadAction;
import org.apache.taglibs.standard.lang.jpath.expression.SubstringFunction;

import com.kp.cms.bo.admin.AdmittedThrough;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Program;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.idcard.DownloadIDcardPhotosForm;
import com.kp.cms.transactions.idcard.IDownloadIDcardPhotosTransaction;
import com.kp.cms.transactionsimpl.idcard.DownloadIDcardPhotosTransactionImpl;

public class DownloadIDcardPhotosAction  extends DownloadAction{
	ActionErrors errors = new ActionErrors();
	ActionMessages messages = new ActionMessages();
	String fileName = "";
	private static final Log log = LogFactory.getLog(DownloadIDcardPhotosAction.class);
	public ByteArrayOutputStream bos=null;
	@Override
	protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("call of getStreamInfo method in DownloadIDcardPhotosAction.class");
		DownloadIDcardPhotosForm downloadIDcardPhotosForm=(DownloadIDcardPhotosForm)form;
		//downloadIDcardPhotosForm.resetAll();i 
		errors.clear();
		messages.clear();
		//int programId=Integer.parseInt(downloadIDcardPhotosForm.getPgmId());
		int year;
		String pgmId;
		String applnNo=downloadIDcardPhotosForm.getApplnNo();
		//	String a=downloadIDcardPhotosForm.getProgramId();
		if(downloadIDcardPhotosForm.getYear()!=null && !downloadIDcardPhotosForm.getYear().isEmpty()
				&& downloadIDcardPhotosForm.getCourseId()!=null && !downloadIDcardPhotosForm.getCourseId().isEmpty())
		{
			year=Integer.parseInt(downloadIDcardPhotosForm.getYear());
			pgmId=downloadIDcardPhotosForm.getCourseId();
			IDownloadIDcardPhotosTransaction transaction= new DownloadIDcardPhotosTransactionImpl().getInstance();
			String pgmName=transaction.getprogramNameById(pgmId);
			if(!applnNo.isEmpty())
			{
				pgmName=applnNo;
			}
			String filePath = request.getRealPath("");
			filePath = filePath + "//TempFiles//";
			File tempFile = new File(filePath+fileName + ".zip");
			if (tempFile.exists()) {
				tempFile.delete();
			}
			fileName=pgmName.replaceAll(" ", "_");
		
		//	String coll_name="College OF"+ SubstringFunction();
			
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
			String contentType = fileName + ".zip";
			response.setHeader("Content-disposition", "attachment; filename ="
					+ fileName + ".zip");
			response.setContentType("application/zip");
			saveErrors(request, errors);
			int totalImages = 0;
			totalImages=transaction.getTotalImageCount(year,pgmId,applnNo);

			
			if(totalImages > 0){
				int PAGESIZE = 1000;
				int page = 1;
				int totalPages = (totalImages / PAGESIZE) + 1;

				while (page <= totalPages) {
					List<ApplnDoc> images = transaction.getImages(year,page,PAGESIZE,pgmId,applnNo);
					InputStream in = null;
					InputStream by=null;
					
					int size = images.size();
					if (size != 0) {
						for (int i = 0; i < size; i++) {
							ApplnDoc applnDoc = images.get(i);
							Set<Student> student = applnDoc.getAdmAppln().getStudents();

							Iterator< Student> iterator = student.iterator();
							String regNo = "";
							int studentId = 0;
							while (iterator.hasNext()) {
								Student student2 = (Student) iterator.next();
								regNo = student2.getRegisterNo();
								studentId = student2.getId();
							}
							if (applnDoc.getIsPhoto() != null){
								if (applnDoc.getIsPhoto()
										&& applnDoc.getDocument() != null) {
									byte[] myFileBytes = applnDoc.getDocument();
									String imageName="";
									if(regNo != null && !regNo.isEmpty()){
										imageName = regNo+ ".jpg";
									}
									else {
										imageName = studentId + ".jpg";
									}

									try {
										in = new ByteArrayInputStream(
												myFileBytes);
										byte buffer[] = new byte[2048];

										zipOutputStream.putNextEntry(new ZipEntry(
												imageName));
										int len;
										while ((len = in.read(buffer)) > 0) {
											zipOutputStream.write(buffer, 0, len);
										}
									} catch (IOException e) {
										log .info("DownloadHostelImagesAction IOException, "
												+ e);
									} catch (Exception e) {
										log .info("DownloadHostelImagesAction Exception, "
												+ e);
									}
								}
							}
							zipOutputStream.closeEntry();
						}
						
					}
					Collections.sort(images);
					String excelFile=getExcelData(pgmId,year,request,totalImages,images,pgmName);
					try {
						by =new ByteArrayInputStream(
								bos.toByteArray());
						byte buffer[] = new byte[2048];

						zipOutputStream.putNextEntry(new ZipEntry(
								excelFile));
						int len;
						while ((len = by.read(buffer)) > 0) {
							zipOutputStream.write(buffer, 0, len);
						}
					}
						
					
					catch (Exception e) {
						// TODO: handle exception
					}
					
					page = page + 1;
				}
				zipOutputStream.close();

			}

			log.debug("end of getStreamInfo method in DownloadIDcardPhotosAction.class");
			return new FileStreamInfo(contentType, tempFile);

		}else
		{
			errors.add("error", new ActionError("Error in getting year and classid"));
			log.error("Error in getting year and class id in DownloadIDcardPhotosAction.class");
			return null;
		}
		


	}
	private String getExcelData(String pgmId, int year, HttpServletRequest request, int totalImages, List<ApplnDoc> images,
			String className) throws IOException {
		
		int size = images.size();
		//className="abc";
		String fileName=className.replaceAll(" ", "_")+".xls";
		String fileName1=className.replaceAll(" ", "_");
		File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
		if(excelFile.exists()){
			excelFile.delete();
		}
		FileOutputStream fos = new FileOutputStream(fileName);
		
		XSSFWorkbook wb=null;
		XSSFSheet sheet=null;
		XSSFRow row=null;
		int count = 0;
		wb=new XSSFWorkbook();
		XSSFCellStyle cellStyle=wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		sheet = wb.createSheet("Sheet 1");
		row = sheet.createRow(count);
		count = sheet.getFirstRowNum();
		
		// Creating excel file heading 
	
		//String coll_name="College OF"+fileName.substring(5,14 );

		row.createCell((short)0).setCellValue("SNO");
		row.createCell((short)1).setCellValue("YearOfStudy");
		row.createCell((short)2).setCellValue("CourseOfStudy");
		row.createCell((short)3).setCellValue("ID");
		row.createCell((short)4).setCellValue("Reg.No");
		row.createCell((short)5).setCellValue("Photo No.");
		row.createCell((short)6).setCellValue("Cl.No");
		row.createCell((short)7).setCellValue("Name");
		row.createCell((short)8).setCellValue("Address");
		row.createCell((short)9).setCellValue("place_1");
		row.createCell((short)10).setCellValue("place_2");
		row.createCell((short)11).setCellValue("Parent Name");
		row.createCell((short)12).setCellValue("Res.Phone");
		row.createCell((short)13).setCellValue("Mobile");
		row.createCell((short)14).setCellValue("DOB");
		row.createCell((short)15).setCellValue("Gender");
		row.createCell((short)16).setCellValue("Height");
		row.createCell((short)17).setCellValue("Weight");
		row.createCell((short)18).setCellValue("Blood Group");
		row.createCell((short)19).setCellValue("Email");
		row.createCell((short)20).setCellValue("Bank Account");
		
		for(int i=0;i<size;i++)
		{
			ApplnDoc applnDoc = images.get(i);
			File path = new File("C:/Users/" + System.getProperty("user.name") + "/Downloads/");
			count = count +1;
			Set<Student> student = applnDoc.getAdmAppln().getStudents();

			Iterator< Student> iterator = student.iterator();
			int sno=count;
			String yearofstudy=null;
			String courseofstudy="";
			String id="";
			String regno="";
			int photono=0;
			String clno="";
			String name="";
			String address="";
			String place1="";
			String place2="";
			String parentname="";
			String resiphone="";
			String mobile="";
			Date dob=null;
			String gender="";
			String bloodgroup="";
			String email="";
			String bankAccount="";

			int height=0;
			BigDecimal weight=null;
			double weight2=0;

			
/*			String email="";
			String program="";
			String photo=path + "\\" +fileName1 + "\\" + applnDoc.getAdmAppln().getApplnNo()+".jpg";
			String course="";			
*/			
			while (iterator.hasNext()) {
				Student student2 = (Student) iterator.next();
				
				if(student2.getAdmAppln().getAdmissionNumber()!=null)
				{
					id=student2.getAdmAppln().getAdmissionNumber();					
				}
				if(student2.getRegisterNo()!=null)
				{
					regno=student2.getRegisterNo();					
				}
				if(student2.getRollNo()!=null)
				{
					clno=student2.getRollNo();				
				}
				if(student2.getAdmAppln().getAppliedYear()!=null)
				{
					if(student2.getAdmAppln().getCourse().getProgram().getProgramType().getId()==1)
					{
						int yos=student2.getAdmAppln().getAppliedYear();
						int duration=yos+3;
						yearofstudy=yos+"-"+duration;
					}
					if(student2.getAdmAppln().getCourse().getProgram().getProgramType().getId()==2)
					{
						int yos=student2.getAdmAppln().getAppliedYear();
						int duration=yos+2;
						yearofstudy=yos+"-"+duration;
					}
				}
				
				if(student2.getAdmAppln().getCourse()!=null)
				{
					courseofstudy=student2.getAdmAppln().getCourse().getName();
				}
				if(student2.getId()!=0)
				{
					photono=student2.getId();
				}			
				if(student2.getAdmAppln().getPersonalData().getFirstName()!=null && !student2.getAdmAppln().getPersonalData().getFirstName().isEmpty())
				{
					name=student2.getAdmAppln().getPersonalData().getFirstName();
				}
				
				if(student2.getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null && !student2.getAdmAppln().getPersonalData().getPermanentAddressLine1().isEmpty()){
					address=student2.getAdmAppln().getPersonalData().getPermanentAddressLine1().toString();
				}

				if(student2.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null &&
						!student2.getAdmAppln().getPersonalData().getPermanentAddressLine2().isEmpty())
				{						
					place1=student2.getAdmAppln().getPersonalData().getPermanentAddressLine2().toString();
				}

				if(student2.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!=null && !student2.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().isEmpty())
				{
					place2=student2.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().toString();
				}
				if(student2.getAdmAppln().getPersonalData().getPermanentAddressZipCode()!=null && !student2.getAdmAppln().getPersonalData().getPermanentAddressZipCode().isEmpty()){
					place2=place2+ " " +student2.getAdmAppln().getPersonalData().getPermanentAddressZipCode();
				}
				if(student2.getAdmAppln().getPersonalData().getFatherName()!=null)
				{
					parentname=student2.getAdmAppln().getPersonalData().getFatherName();
				}
				if(student2.getAdmAppln().getPersonalData().getPhNo1()!=null && student2.getAdmAppln().getPersonalData().getPhNo2()!=null && student2.getAdmAppln().getPersonalData().getPhNo3()!=null)
				{
					resiphone = student2.getAdmAppln().getPersonalData().getPhNo1()+student2.getAdmAppln().getPersonalData().getPhNo2()+" "+student2.getAdmAppln().getPersonalData().getPhNo3();
				}
				if(student2.getAdmAppln().getPersonalData().getMobileNo2()!= null && !student2.getAdmAppln().getPersonalData().getMobileNo2().isEmpty() )
				{
					mobile = student2.getAdmAppln().getPersonalData().getMobileNo2();
				}	
				if(student2.getAdmAppln().getPersonalData().getDateOfBirth()!=null)
				{
					dob = student2.getAdmAppln().getPersonalData().getDateOfBirth();
				}
				if(student2.getAdmAppln().getPersonalData().getGender()!=null)
				{
					gender=student2.getAdmAppln().getPersonalData().getGender();
				}
				if(student2.getAdmAppln().getPersonalData().getBloodGroup()!=null)
				{
					bloodgroup=student2.getAdmAppln().getPersonalData().getBloodGroup();
				}
				if(student2.getAdmAppln().getPersonalData().getEmail()!=null)
				{
					email=student2.getAdmAppln().getPersonalData().getEmail();
				}
				if(student2.getAdmAppln().getPersonalData().getHeight()!=null)
				{
					height=student2.getAdmAppln().getPersonalData().getHeight();
				}
				if(student2.getAdmAppln().getPersonalData().getWeight()!=null)
				{
					weight=student2.getAdmAppln().getPersonalData().getWeight();
					weight2=weight.doubleValue();
				}
				if(student2.getBankAccNo()!=null)
				{
					bankAccount=student2.getBankAccNo();
				}
/*				if(student2.getAdmAppln().getApplnNo()!=0)
				{
					appno=student2.getAdmAppln().getApplnNo();
				}
				if(student2.getAdmAppln().getPersonalData().getFirstName()!=null && !student2.getAdmAppln().getPersonalData().getFirstName().isEmpty())
				{
					name=student2.getAdmAppln().getPersonalData().getFirstName();
				}
				if(student2.getAdmAppln().getPersonalData().getPermanentAddressLine1()!=null && !student2.getAdmAppln().getPersonalData().getPermanentAddressLine1().isEmpty()){
					address=student2.getAdmAppln().getPersonalData().getPermanentAddressLine1().toString();
				}
				if(student2.getAdmAppln().getPersonalData().getPermanentAddressLine2()!=null &&
							!student2.getAdmAppln().getPersonalData().getPermanentAddressLine2().isEmpty())
				{						
					address=address+ " " +student2.getAdmAppln().getPersonalData().getPermanentAddressLine2().toString();
				}
				if(student2.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!=null && !student2.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().isEmpty())
				{
					address=address+ " " +student2.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().toString();
				}
				if(student2.getAdmAppln().getPersonalData().getCurrentAddressZipCode()!=null && !student2.getAdmAppln().getPersonalData().getCurrentAddressZipCode().isEmpty()){
					address=address+ " " +student2.getAdmAppln().getPersonalData().getCurrentAddressZipCode();
				}
				if(student2.getAdmAppln().getPersonalData().getEmail()!=null && !student2.getAdmAppln().getPersonalData().getEmail().isEmpty())
				{
					email = student2.getAdmAppln().getPersonalData().getEmail();
				}
				if(student2.getAdmAppln().getPersonalData().getMobileNo2()!= null && !student2.getAdmAppln().getPersonalData().getMobileNo2().isEmpty() )
				{
					mobile = student2.getAdmAppln().getPersonalData().getMobileNo2();
				}					
				if(student2.getAdmAppln().getPersonalData().getDateOfBirth()!=null)
				{
					dob = student2.getAdmAppln().getPersonalData().getDateOfBirth();
				}
				if(student2.getAdmAppln().getCourse().getProgram()!=null)
				{
					program = student2.getAdmAppln().getCourse().getProgram().getName();
				}
				if(student2.getAdmAppln().getCourse().getName()!=null)
				{
					course = student2.getAdmAppln().getCourse().getName();
				}
*/					
		}	
			row = sheet.createRow(count);
			row.createCell((short)0).setCellValue(sno);
			row.createCell((short)1).setCellValue(yearofstudy);
			row.createCell((short)2).setCellValue(courseofstudy);
			row.createCell((short)3).setCellValue(id);
			row.createCell((short)4).setCellValue(regno);
			row.createCell((short)5).setCellValue(photono);
			row.createCell((short)6).setCellValue(clno);
			row.createCell((short)7).setCellValue(name);
			row.createCell((short)8).setCellValue(address);
			row.createCell((short)9).setCellValue(place1);
			row.createCell((short)10).setCellValue(place2);
			row.createCell((short)11).setCellValue(parentname);
			row.createCell((short)12).setCellValue(resiphone);
			row.createCell((short)13).setCellValue(mobile);
			row.createCell((short)14).setCellValue(dob);
			row.createCell((short)15).setCellValue(gender);
			row.createCell((short)16).setCellValue(height);
			row.createCell((short)17).setCellValue(weight2);
			row.createCell((short)18).setCellValue(bloodgroup);
			row.createCell((short)19).setCellValue(email);
			row.createCell((short)20).setCellValue(bankAccount);

		}
		bos=new ByteArrayOutputStream();
		wb.write(bos);
		HttpSession session = request.getSession();
		session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
		bos.flush();
		fos.close();
		return fileName;
	}
	// 
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
