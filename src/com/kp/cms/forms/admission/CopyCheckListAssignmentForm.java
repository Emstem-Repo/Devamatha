package com.kp.cms.forms.admission;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;

	public class CopyCheckListAssignmentForm extends BaseActionForm{
		
		private int id;
		private String fromYear;
		private String toYear;
		private List<CheckListTO> displayCheckList;
		private List<DocTO> doclist;
		private String method;
		private List<CheckListTO> backupCheckList;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getFromYear() {
			return fromYear;
		}
		public void setFromYear(String fromYear) {
			this.fromYear = fromYear;
		}
		public String getToYear() {
			return toYear;
		}
		public void setToYear(String toYear) {
			this.toYear = toYear;
		}
		
		
		public List<DocTO> getDoclist() {
			return doclist;
		}
		public void setDoclist(List<DocTO> doclist) {
			this.doclist = doclist;
		}
		public String getMethod() {
			return method;
		}
		public void setMethod(String method) {
			this.method = method;
		}
		public List<CheckListTO> getBackupCheckList() {
			return backupCheckList;
		}
		public void setBackupCheckList(List<CheckListTO> backupCheckList) {
			this.backupCheckList = backupCheckList;
		}
		public List<CheckListTO> getDisplayCheckList() {
			return displayCheckList;
		}
		public void setDisplayCheckList(List<CheckListTO> displayCheckList) {
			this.displayCheckList = displayCheckList;
		}
		public void clear(){
			this.id=0;
			this.fromYear=null;
			this.toYear=null;
			this.method=null;
			this.displayCheckList=null;
		}
		
		public ActionErrors validate(ActionMapping mapping,
				HttpServletRequest request) {
			String formName = request.getParameter(CMSConstants.FORMNAME);
			ActionErrors actionErrors = super.validate(mapping, request, formName);
			return actionErrors;
		}
}
