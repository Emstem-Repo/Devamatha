package com.kp.cms.forms.admission;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;

/*
 * @author Balagangadhar.K
 * 
 * A Bean class associated for CheckListAction
 * 
 */

public class CheckListForm extends BaseActionForm {

	//drop down list variables.
	
	private String program;
	private String course;
//	private String programTypeName;
//	private String programName;
//	private String courseName;
	private String year;
	private String name;
	private String mode;
	
	//check box variables.
	private int docChecklistId;
	private int id;
	private boolean selectCheckbox;
	
	//radio button variables.
	
	private String isMarks_card;
	private String consolidated_Marks;
	private String need_To_Produce;
	
	
	//List variables for displaying in JSP.
	private List<ProgramTypeTO> programTypeList;
	private List<CheckListTO> checkList;
	private List<DocTO> doclist;
	private CheckListTO checkListTO;

	//method for dispatch action.
	private String method;

	private String isMC;
	private String isCM;
	private String NTP;

	// setter and getter methods
	
	public String getYear() {
		return year;
	}

	public String getMC() {
		return isMC;
	}

	public void setMC(String isMC) {
		this.isMC = isMC;
	}

	public String getCM() {
		return isCM;
	}

	public void setCM(String isCM) {
		this.isCM = isCM;
	}

	public String getNTP() {
		return NTP;
	}

	public void setNTP(String ntp) {
		NTP = ntp;
	}

	public boolean isSelectCheckbox() {
		return selectCheckbox;
	}

	public void setSelectCheckbox(boolean selectCheckbox) {
		this.selectCheckbox = selectCheckbox;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getIsMarks_card() {
		return isMarks_card;
	}

	public void setIsMarks_card(String isMarks_card) {
		this.isMarks_card = isMarks_card;
	}

	public String getConsolidated_Marks() {
		return consolidated_Marks;
	}

	public void setConsolidated_Marks(String consolidated_Marks) {
		this.consolidated_Marks = consolidated_Marks;
	}

	public String getNeed_To_Produce() {
		return need_To_Produce;
	}

	public void setNeed_To_Produce(String need_To_Produce) {
		this.need_To_Produce = need_To_Produce;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDocChecklistId() {
		return docChecklistId;
	}

	public void setDocChecklistId(int docChecklistId) {
		this.docChecklistId = docChecklistId;
	}

	public CheckListTO getCheckListTO() {
		return checkListTO;
	}

	public void setCheckListTO(CheckListTO checkListTO) {
		this.checkListTO = checkListTO;
	}

	public List<DocTO> getDoclist() {
		return doclist;
	}

	public void setDoclist(List<DocTO> doclist) {
		this.doclist = doclist;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<CheckListTO> getCheckList() {
		return checkList;
	}

	public void setCheckList(List<CheckListTO> checkList) {
		this.checkList = checkList;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public CheckListForm() {
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<ProgramTypeTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setProgramTypeList(List<ProgramTypeTO> programTypeList) {
		this.programTypeList = programTypeList;
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping, javax.servlet.ServletRequest)
	 */
	@Override
	public void reset(ActionMapping mapping, ServletRequest request) {
		// TODO Auto-generated method stub
		super.reset(mapping, request);
		this.doclist = new ArrayList<DocTO>();
	}

	
	public void resetCheckList(ActionMapping mapping, HttpServletRequest request) {
		// TODO Auto-generated method stub
//		super.reset(mapping, request);
		this.consolidated_Marks = null;
		this.isMarks_card = null;
		this.need_To_Produce = null;
		this.year = null;
//		this.checkList=null;
//		this.doclist=null;
	}
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
	}
	public void clearAll(){
		super.clear();
		this.course=null;
		this.consolidated_Marks=null;
		this.isMarks_card=null;
		this.name=null;
//		this.year=null;
		this.need_To_Produce=null;
		this.program=null;
		this.checkListTO = null;
	}
}