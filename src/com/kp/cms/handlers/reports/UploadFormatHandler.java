package com.kp.cms.handlers.reports;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.forms.reports.UploadFormatForm;
import com.kp.cms.helpers.reports.UploadFormatHelper;

	public class UploadFormatHandler {
		private static Log log = LogFactory.getLog(UploadFormatHandler.class);
		
		public static volatile UploadFormatHandler uploadFormatHandler=null;
		/**
		 * @return
		 * This method will return instance of this classes
		 */
		public static UploadFormatHandler getInstance(){
			if(uploadFormatHandler==null)
				uploadFormatHandler= new UploadFormatHandler();
			return uploadFormatHandler;
		}
		private UploadFormatHandler(){
			
		}
		public boolean exportTOExcel(UploadFormatForm uploadFormatForm,HttpServletRequest request) throws Exception{
			UploadFormatHelper.getInstance().exportTOExcel(uploadFormatForm, request); 
			return true;
		}
		public boolean exportTOCsv(UploadFormatForm uploadFormatForm,HttpServletRequest request)throws Exception {
			UploadFormatHelper.getInstance().exportTOCSV(uploadFormatForm, request); 
			return true;
		}
		
}
