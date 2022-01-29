package com.kp.cms.actions.admission;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.kp.cms.actions.pettycash.PettyCashAccountHeadsAction;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admission.BoardDetailsForm;
import com.kp.cms.forms.admission.PromoteBioDataUploadForm;
import com.kp.cms.forms.admission.PromoteMarksUploadForm;
import com.kp.cms.forms.pettycash.PettyCashAccountHeadsForm;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.handlers.ajax.CommonAjaxHandler;
import com.kp.cms.handlers.pettycash.PettyCashAccountHeadsHandler;
import com.kp.cms.to.admission.PromoteMarksUploadTo;
import com.kp.cms.to.exam.PromoteSupliMarksTo;
import com.kp.cms.to.pettycash.AccountHeadsTo;
import com.kp.cms.utilities.CurrentAcademicYear;

public class PromoteMarksUploadAction extends BaseDispatchAction{
	
	private static final Log log = LogFactory.getLog(PromoteMarksUploadAction.class);
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteMarksUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteMarksUploadForm prMarksUploadForm = (PromoteMarksUploadForm)form;
		prMarksUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_PROMOTE_MARKS_UPLOAD);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward promoteMarksUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		 ActionErrors errors = marksUploadForm.validate(mapping, request);
		ActionMessages messages = new ActionMessages();
		setUserId(request, marksUploadForm);
		try{
			if(errors.isEmpty()){
				FormFile formFile = marksUploadForm.getTheFile();
				String contentType = formFile.getContentType();
				String fileName = formFile.getFileName();
				Properties prop = new Properties();
				File file = null;
				InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
				prop.load(inputStream);
				List<PromoteMarksUploadTo> promoteMarksList = null;
				 	if(fileName.endsWith(".xls")){
				 		byte[] fileData = formFile.getFileData();
				 		String source = prop.getProperty(CMSConstants.UPLOAD_PROMOTE_MARKS);
				 		File file1 = new File(request.getRealPath("")+"//TempFiles//"+source);
				 		InputStream stream = new ByteArrayStreamInfo(contentType,fileData).getInputStream();
						OutputStream out = new FileOutputStream(file1);
						byte buffer[] = new byte[1024];
						int len;
						while ((len = stream.read(buffer)) > 0){
							out.write(buffer, 0, len);
						}
						out.close();
						stream.close();
						String source1=prop.getProperty(CMSConstants.UPLOAD_PROMOTE_MARKS);
						file = new File(request.getRealPath("")+"//TempFiles//"+source1);
						
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
					    promoteMarksList = new ArrayList<PromoteMarksUploadTo>();
					    for(int r = 1; r < rows; r++) {
					    	row= sheet.getRow(r);
					    	PromoteMarksUploadTo marksUploadTo = new PromoteMarksUploadTo();
					    	if(row!=null){
					    		for(int c = 0; c < cols;c++){
					    			cell=row.getCell((byte)c);
					    			 if(cell != null && !StringUtils.isEmpty(cell.toString())) {
					    				 if(cell.getCellNum() == 0 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 marksUploadTo.setRegNo(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 1 && !StringUtils.isEmpty(removeFileExtension(cell.toString().trim()))){
					    					 marksUploadTo.setClassCode(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 2 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
					    					 marksUploadTo.setMrkSub1(removeFileExtension(cell.toString().trim()));
					    				 }
					    				 if(cell.getCellNum() == 3 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
					    					 marksUploadTo.setMrkSub2(removeFileExtension(cell.toString().trim()));
					    				 }
										 if(cell.getCellNum() == 4 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setMrkSub3(removeFileExtension(cell.toString().trim()));			    					 
										 }
										 if(cell.getCellNum() == 5 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setMrkSub4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 6 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setMrkSub5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 7 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setMrkSub6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 8 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setMrkSub7(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 9 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub1(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 10 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub2(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 11 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub3(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 12 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub4(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 13 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub5(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 14 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub6(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 15 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setGradeSub7(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 16 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setSection(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 17 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setWithHeld(removeFileExtension(cell.toString().trim()));
										 }
										 if(cell.getCellNum() == 18 && !StringUtils.isEmpty(removeFileExtension(cell.toString()))){
											 marksUploadTo.setSupAttend(removeFileExtension(cell.toString().trim()));
										 }
										 if(marksUploadForm.getAcademicYear()!=null && !marksUploadForm.getAcademicYear().isEmpty()){
											 marksUploadTo.setAcademicYear(marksUploadForm.getAcademicYear());
										 }
					    			 }
					    		}
					    		if(marksUploadTo!=null){
					    			promoteMarksList.add(marksUploadTo);
					    		}
					    	}
					    }
					    if(promoteMarksList!=null && !promoteMarksList.isEmpty()){
					    	isAdded = PromoteMarksUploadHandler.getInstance().uploadPromoteMarks(promoteMarksList,marksUploadForm);
					    }
					    if(isAdded){
				    		//if adding is success
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_SUCCESS);
				    		messages.add("messages", message);
				    		saveMessages(request, messages);
				    		
				    	}else{
				    		//if adding is failure
				    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_ADM_BIODATA_FAILURE);
				    		errors.add(CMSConstants.ERROR, message);
				    		addErrors(request, errors);
				    	}
				 	}
				 	else{
				    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
			    		errors.add(CMSConstants.ERROR, message);
			    		addErrors(request, errors);
				    }
			}else{
				saveErrors(request, errors);
			}
			
		}catch (BusinessException businessException) {
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			marksUploadForm.setErrorMessage(msg);
			marksUploadForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=marksUploadForm.getDupRegNos();
		if(duplicate!=null && !duplicate.isEmpty()){
			StringBuilder ids=new StringBuilder();
			Iterator it=duplicate.iterator();
			while(it.hasNext()){
				ids.append(it.next().toString()).append(", ");
			}
			int len=ids.length()-2;
	        if(ids.toString().endsWith(", ")){
	            ids.setCharAt(len, ' ');
	        }
			errors.add("error",new ActionError( "knowledgepro.promote.uploadPromoteMarks.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		return mapping.findForward(CMSConstants.INIT_PROMOTE_MARKS_UPLOAD);
	}
	
	/**
	 * @author christ
	 *
	 */
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
	 * @param fileName
	 * @return
	 */
	public String removeFileExtension(String fileName)
	{ 
	if(null != fileName && fileName.contains("."))
	{
	return fileName.substring(0, fileName.lastIndexOf("."));
	}
	return fileName;
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExam(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		marksUploadForm.resetFields();
		return mapping.findForward(CMSConstants.INIT_EXAM);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteMarks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		marksUploadForm.resetFields();
		String mode = "promoteMarks";
		setCourseMapToForm(marksUploadForm,mode);
		return mapping.findForward(CMSConstants.INIT_PROMOTE);
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPromoteMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered getPromoteMarks..");	
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		 ActionErrors errors = marksUploadForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {	
			try {
			     List<PromoteMarksUploadTo> promoteMarksToList=PromoteMarksUploadHandler.getInstance().getPromoteMarks(marksUploadForm,session);
			     session.setAttribute("promoteMarksToList", promoteMarksToList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				marksUploadForm.setErrorMessage(msg);
				marksUploadForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_PROMOTE);
		}
		log.info("Exit getPromoteMarks..");
		return mapping.findForward(CMSConstants.PROMOTE_MARKS_RESULT);
	}
	/**
	 * @param marksUploadForm
	 * @throws Exception
	 */
	public void setCourseMapToForm(PromoteMarksUploadForm marksUploadForm,String mode) throws Exception {
		log.info("Entering into setCourseMapToForm of PromoteMarksUploadAction");
		/*Calendar calendar = Calendar.getInstance();
		int currentYear = calendar.get(Calendar.YEAR);
		
		int year = CurrentAcademicYear.getInstance().getAcademicyear();
		if (year != 0) {
			currentYear = year;
		}
		if (marksUploadForm.getAcademicYear() != null && !marksUploadForm.getAcademicYear().isEmpty()) {
			currentYear=Integer.parseInt(marksUploadForm.getAcademicYear());
		}
		Map<Integer, String> courseMap = CommonAjaxHandler.getInstance().getCourseByYear(currentYear);
		//request.setAttribute("courseMap", courseMap);
		marksUploadForm.setCourseMap(courseMap);*/
		Map<String,String> courses = PromoteMarksUploadHandler.getInstance().getCourses(mode);
		marksUploadForm.setCourses(courses);
		log.info("Leaving into setCourseMapToForm of PromoteMarksUploadAction");
	}
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initPromoteSuplyMarksReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		marksUploadForm.resetFields();
		String mode = "supliMarks";
		setCourseMapToForm(marksUploadForm,mode);
		return mapping.findForward(CMSConstants.INIT_PROMOTE_SUPLIMARKS);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getPromoteSupliMarks(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entered getPromoteSupliMarks..");	
		PromoteMarksUploadForm marksUploadForm = (PromoteMarksUploadForm)form;
		 ActionErrors errors = marksUploadForm.validate(mapping, request);
		HttpSession session=request.getSession();
		if (errors.isEmpty()) {	
			try {
			     List<PromoteSupliMarksTo> promoteSupliMarksToList=PromoteMarksUploadHandler.getInstance().getPromoteSupliMarks(marksUploadForm, session);
			     session.setAttribute("promoteSupliMarksToList", promoteSupliMarksToList);
			}catch(ApplicationException ae){
				String msg = super.handleApplicationException(ae);
				marksUploadForm.setErrorMessage(msg);
				marksUploadForm.setErrorStack(ae.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
			catch (Exception e) {
					throw e;
			}
		} else {
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_PROMOTE_SUPLIMARKS);
		}
		log.info("Exit getPromoteSupliMarks..");
		return mapping.findForward(CMSConstants.INIT_PROMOTE_SUPLIMARKS_RESULT);
	}
}
