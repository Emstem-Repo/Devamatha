package com.kp.cms.actions.idcard;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.GeneratePasswordForm;
import com.kp.cms.forms.idcard.ExportIDCardPhotosForm;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.helpers.reports.StudentDetailsReportHelper;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.utilities.CurrentAcademicYear;

public class ExportIDCardPhotosAction extends BaseDispatchAction {
	
	private static Log log = LogFactory.getLog(ExportIDCardPhotosAction.class);
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	
	
	
	
	public ActionForward initExportIDCardPhotosAction(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//GeneratePasswordForm gnForm=(GeneratePasswordForm)form;
		
		ExportIDCardPhotosForm exportIDCardPhotosForm=(ExportIDCardPhotosForm) form;
		try {
			// initialize program type
			setUserId(request, exportIDCardPhotosForm);
			exportIDCardPhotosForm.setProgramTypeId(null);
		
			List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance().getProgramType();
			exportIDCardPhotosForm.setProgramTypeList(programTypeList);
			
			
		
		}catch (ApplicationException e) {
			log.error("error in init application detail page...",e);
			
				String msg = super.handleApplicationException(e);
				exportIDCardPhotosForm.setErrorMessage(msg);
				exportIDCardPhotosForm.setErrorStack(e.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
		} 
		catch (Exception e) {
			log.error("error in init application detail page...",e);
				throw e;
		}
		
		return mapping.findForward(CMSConstants.INIT_EXPORT_IDCARD_PHOTOS);
	}
	
	
	
	
	
	
	

	
	public ActionForward downloadExcelFile(ActionMapping mapping, ActionForm form,	HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		log.debug("call of downloadExcelFile method in ExportIDCardPhotos.class");
		ExportIDCardPhotosForm exportIDCardPhotosForm=(ExportIDCardPhotosForm) form;
		ActionMessages messages = new ActionMessages();
		ActionErrors errors=new ActionErrors();
		if(exportIDCardPhotosForm.getYear()!=null && !exportIDCardPhotosForm.getYear().isEmpty() &&
				exportIDCardPhotosForm.getProgramTypeId()!=null && !exportIDCardPhotosForm.getProgramTypeId().isEmpty())
		{
			
			String fileName="test.xls";
			File excelFile = new File(request.getRealPath("")+ "//TempFiles//"+fileName);
			if(excelFile.exists()){
				excelFile.delete();
			}
			FileOutputStream fos = new FileOutputStream(fileName);
			ByteArrayOutputStream bos=new ByteArrayOutputStream();
			XSSFWorkbook wb=null;
			XSSFSheet sheet=null;
			XSSFRow row=null;
			int count = 0;
			wb=new XSSFWorkbook();
			XSSFCellStyle cellStyle=wb.createCellStyle();
			CreationHelper createHelper = wb.getCreationHelper();
			cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd/MM/yyyy"));
			sheet = wb.createSheet("Student Report");
			row = sheet.createRow(count);
			count = sheet.getFirstRowNum();
			// Create cells in the row and put some data in it.
			row.createCell((short)1).setCellValue("Register No");
			row.createCell((short)2).setCellValue("Student Name");
			for(int i=0;i<3;i++)
			{
				count = count +1;
				row = sheet.createRow(count);
				row.createCell((short)1).setCellValue("name1");
				row.createCell((short)1).setCellValue("name2");
			}
			bos=new ByteArrayOutputStream();
			wb.write(bos);
			HttpSession session = request.getSession();
			session.setAttribute(CMSConstants.EXCEL_BYTES,bos.toByteArray());
			bos.flush();
			fos.close();
			
		}
		else
		{
			log.debug("Error in getting form date ");
			errors.add("error", new ActionError("knowledgepro.admin.Caste.name.exists"));
			saveErrors(request, errors);
		}
		
		log.debug("end of downloadExcelFile method in ExportIDCardPhotos.class");
		return mapping.findForward(CMSConstants.INIT_EXPORT_IDCARD_PHOTOS);
	}
						
	
	
	
	

}
