package com.kp.cms.forms.employee;

	import java.util.Map;

	import javax.servlet.http.HttpServletRequest;

	import org.apache.struts.action.ActionErrors;
	import org.apache.struts.action.ActionMapping;

	import com.kp.cms.forms.BaseActionForm;

	public class EmployeeExportPhotoForm  extends BaseActionForm{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String fingerPrintId;
		
		
		
		private Map<Integer, String> listProgram;
		public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter("formName");
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
		public void setListProgram(Map<Integer, String> listProgram) {
			this.listProgram = listProgram;
		}

		public Map<Integer, String> getListProgram() {
			return listProgram;
		}

		
		public String getFingerPrintId() {
			return fingerPrintId;
		}
		public void setFingerPrintId(String fingerPrintId) {
			this.fingerPrintId = fingerPrintId;
		}

		

	}



