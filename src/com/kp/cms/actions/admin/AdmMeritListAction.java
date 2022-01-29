package com.kp.cms.actions.admin;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.admin.AdmMeritListForm;
import com.kp.cms.handlers.admin.AdmMeritHandler;
import com.kp.cms.handlers.admission.PromoteMarksUploadHandler;
import com.kp.cms.to.admin.AdmMeritTO;

public class AdmMeritListAction extends BaseDispatchAction{
	private static final Log log = LogFactory.getLog(AdmMeritListAction.class);
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmMeritListUpload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initAdmMeritList input");
		AdmMeritListForm admMeritForm=(AdmMeritListForm)form;
		admMeritForm.resetFields();
		log.info("Leaving initAdmMeritList input");
		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
	} 
	
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadMeritList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadMeritList input");
		AdmMeritListForm admMeritForm=(AdmMeritListForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, admMeritForm);
		ActionErrors errors = admMeritForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			/*if(admMeritForm.getTheFile() == null){
				ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			}*/
			FormFile myFile = admMeritForm.getTheFile();
		    String contentType = myFile.getContentType();
		    String fileName    = myFile.getFileName();
		   	File file = null;
		    Properties prop = new Properties();
		    InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		    prop.load(stream);
		    //if the uploading file is excel file
		    if(fileName.endsWith(".xls")){
		    	byte[] fileData = myFile.getFileData();
		    	String source1=prop.getProperty(CMSConstants.UPLOAD_MERITLIST1);
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
			String source=prop.getProperty(CMSConstants.UPLOAD_MERITLIST1);
			file = new File(filePath+source);
			boolean isAdded=AdmMeritHandler.getInstance().uploadMeritList(file, admMeritForm);
			
		    if(isAdded){
	    		//if adding is success
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		messages.add("messages", message);
	    		saveMessages(request, messages);
	    		
	    		
	    	}else{
	    		//if adding is failure
	    		ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
	    	}
		    
		    }else{
		    	admMeritForm.reset();
		    	ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		errors.add(CMSConstants.ERROR, message);
	    		addErrors(request, errors);
		    }
			}else{
				admMeritForm.reset();
				saveErrors(request, errors);
				return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
				
			}
		}catch (BusinessException businessException) {
			log.info("Exception uploadMeritList");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admMeritForm.setErrorMessage(msg);
			admMeritForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<Integer> duplicate=admMeritForm.getAppNos();
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
			errors.add("error",new ActionError( "knowledgepro.employee.uploadMeritList.duplicate" ,ids.toString().trim()));
			saveErrors(request, errors);
		}
		log.info("Leaving UploadMeritList input");
		return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmFeeMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered initAdmFeeMain input");
		AdmMeritListForm admMeritForm=(AdmMeritListForm)form;
		admMeritForm.resetFields();
		log.info("Leaving initAdmFeeMain input");
		return mapping.findForward(CMSConstants.UPLOAD_FEEMAIN);
	} 
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward uploadAdmFeeMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("Entered uploadFeeMain input");
		AdmMeritListForm admMeritForm=(AdmMeritListForm)form;
		ActionMessages messages=new ActionMessages();
		setUserId(request, admMeritForm);
		ActionErrors errors = admMeritForm.validate(mapping, request);
		try{
			if(errors.isEmpty()){
			     /*if(admMeritForm.getTheFile() == null){
				    ActionMessage message = new ActionMessage(CMSConstants.EXCEL_FILE_REQUIRED);
	    		    errors.add(CMSConstants.ERROR, message);
	    		    addErrors(request, errors);
	    		    return mapping.findForward(CMSConstants.UPLOAD_MERITLIST);
			     }*/
			     FormFile myFile = admMeritForm.getTheFile();
		         String contentType = myFile.getContentType();
		         String fileName    = myFile.getFileName();
		   	     File file = null;
		         Properties prop = new Properties();
		         InputStream stream = AdmMeritListAction.class.getClassLoader().getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
		         prop.load(stream);
		         //if the uploading file is excel file
		         if(fileName.endsWith(".xls")){
		    	     byte[] fileData = myFile.getFileData();
		    	     String source1=prop.getProperty(CMSConstants.UPLOAD_FEEMAIN1);
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
			         String source=prop.getProperty(CMSConstants.UPLOAD_FEEMAIN1);
			         file = new File(filePath+source);
			         boolean isAdded=AdmMeritHandler.getInstance().uploadAdmFeemain(file, admMeritForm);
			
		             if(isAdded){
	    		        //if adding is success
	    		        ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_SUCCESS);
	    		        messages.add("messages", message);
	    		        saveMessages(request, messages);
	    		
	    		
	    	         }else{
	    		        //if adding is failure
	    		        ActionMessage message = new ActionMessage(CMSConstants.UPLOAD_SMARTCARD_FAILURE);
	    		        if(errors.get(CMSConstants.UPLOAD_SMARTCARD_FAILURE)!=null && !errors.get(CMSConstants.UPLOAD_SMARTCARD_FAILURE).hasNext())
	    		            errors.add(CMSConstants.ERROR, message);
	    		        addErrors(request, errors);
	    	         }
		    
		             }else{
		    	         ActionMessage message = new ActionMessage(CMSConstants.PLEASE_UPLOAD_SMARTCARD_XLS);
	    		         errors.add(CMSConstants.ERROR, message);
	    		         addErrors(request, errors);
	    		         admMeritForm.reset();
		             }
			 }else{
				  saveErrors(request, errors);
				  return mapping.findForward(CMSConstants.UPLOAD_FEEMAIN);
				
			  }
		}catch (BusinessException businessException) {
			log.info("Exception uploadFeemain");
			String msgKey = super.handleBusinessException(businessException);
			ActionMessage message = new ActionMessage(msgKey);
			messages.add(CMSConstants.MESSAGES, message);
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		} catch (Exception exception) {	
			String msg = super.handleApplicationException(exception);
			admMeritForm.setErrorMessage(msg);
			admMeritForm.setErrorStack(exception.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		List<String> duplicate=admMeritForm.getRegNos();
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
			errors.add("error",new ActionError( "knowledgepro.admin.uploadFeemain.duplicate" ,ids.toString().trim()));
			//addErrors(request, errors);
			saveErrors(request, errors);
		}
		log.info("Leaving UploadFeemain input");
		return mapping.findForward(CMSConstants.UPLOAD_FEEMAIN);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initAdmMeritListReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmMeritListForm admMeritListForm = (AdmMeritListForm)form;
		admMeritListForm.setCourseName(null);
		HttpSession session = request.getSession(false);
		session.removeAttribute("AdmMeritTO");
		session.removeAttribute(CMSConstants.EXCEL_BYTES);
		setRequiredDataTOForm(admMeritListForm);
		return mapping.findForward(CMSConstants.INIT_ADM_MERITLIST_REPORT);
	}
	private void setRequiredDataTOForm(AdmMeritListForm admMeritListForm) throws Exception {
		String mode = "AdmMeritList";
		Map<String,String> courses=PromoteMarksUploadHandler.getInstance().getCourses(mode);
		admMeritListForm.setCourses(courses);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchAdmMeritList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdmMeritListForm admMeritListForm = (AdmMeritListForm)form;
		ActionMessages errors = new ActionErrors();
		admMeritListForm.setMode(null);
		admMeritListForm.setDownloadExcel(null);
		if (admMeritListForm.getAcademicYear()!=null && !admMeritListForm.getAcademicYear().isEmpty()) {	
			try {
			HttpSession session = request.getSession();
			 List<AdmMeritTO> admMeritTO = AdmMeritHandler.getInstance().getAdmMeritListSearch(admMeritListForm,request);
			if(!admMeritTO.isEmpty()){	
				admMeritListForm.setAdmMeritTO(admMeritTO);
				session.setAttribute("AdmMeritTO",admMeritTO);
			}

			} catch (Exception exception) {	
				String msg = super.handleApplicationException(exception);
				admMeritListForm.setErrorMessage(msg);
				admMeritListForm.setErrorStack(exception.getMessage());
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}
		} else {
			errors.add("error", new ActionError("knowledgepro.exam.examDefinition.academicYear.required"));
			addErrors(request, errors);
			return mapping.findForward(CMSConstants.INIT_ADM_MERITLIST_REPORT);
		}
		return mapping.findForward(CMSConstants.INIT_ADM_MERITLIST_SECOND_PAGE);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initExportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("entered initexportToExcel..");
		AdmMeritListForm admMeritListForm = (AdmMeritListForm)form;
		admMeritListForm.setSelectedColumnsArray(null);
		admMeritListForm.setUnselectedColumnsArray(null);
		log.info("exit initexportToExcel..");
		return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_MERITLIST_REPORT);
	}
	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportToExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		log.info("entered exportToExcel..");
		AdmMeritListForm admMeritListForm = (AdmMeritListForm)form;
		ActionErrors errors = new ActionErrors();
		ActionMessages messages = new ActionMessages();
		admMeritListForm.setMode(null);
		admMeritListForm.setDownloadExcel(null);
		
			try {
				if(admMeritListForm.getSelectedColumnsArray()!=null && !admMeritListForm.getSelectedColumnsArray().toString().isEmpty()){
					setUserId(request, admMeritListForm);
					boolean isUpdated =	AdmMeritHandler.getInstance().exportTOExcel(admMeritListForm,request);
			 		if(isUpdated){
			 			admMeritListForm.setMode("excel");
			 			admMeritListForm.setDownloadExcel("download");
						ActionMessage message = new ActionMessage("knowledgepro.reports.columnsUpdate");
						messages.add("messages", message);
						saveMessages(request, messages);
					}
				}else {
					errors.add("error", new ActionError("knowledgepro.select.atleast.onecolumn"));
					addErrors(request, errors);			
					return mapping.findForward(CMSConstants.INIT_EXCEL_ADM_MERITLIST_REPORT);
				}
		
		}catch (ApplicationException ae) {
			log.error("error occured in exportToExcel in AdmissionBioDataReportAction",ae);
			String msg = super.handleApplicationException(ae);
			admMeritListForm.setErrorMessage(msg);
			admMeritListForm.setErrorStack(ae.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
		catch (Exception e) {
			log.error("error occured in exportToExcel in AdmissionBioDataReportAction",e);
			String msg = super.handleApplicationException(e);
			admMeritListForm.setErrorMessage(msg);
			admMeritListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);
		}
	
		log.info("exit exportToExcel..");

		return mapping.findForward(CMSConstants.INIT_ADM_MERITLIST_SECOND_PAGE);
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
