package com.kp.cms.forms.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.exam.ExamFooterAgreementTO;
import com.kp.cms.to.exam.KeyValueTO;

@SuppressWarnings("serial")
public class ExamFooterAgreementForm extends BaseActionForm {

	/**
	 * 
	 */
	private int id;
	private String name;
	private String typeId;
	private String selectedProgramType;
	private String classCodeIdsFrom;
	private String[] classCodeIdsTo;

	private Map<Integer, String> mapClass;
	private Map<Integer, String> mapSelectedClass;
	private List<KeyValueTO> programTypeList;
	private String date;
	private String templateDescription;
	private ArrayList<ExamFooterAgreementTO> mainList;
	private String halTcktOrMarksCard;

	public void clearPage() {
		this.name = "";
		this.typeId = "";
		this.classCodeIdsFrom = null;
		this.selectedProgramType = null;
		this.templateDescription = null;
		this.date = null;
		halTcktOrMarksCard = "";
	}

	@Override
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter(CMSConstants.FORMNAME);
		ActionErrors actionErrors = super.validate(mapping, request, formName);

		return actionErrors;
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

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getClassCodeIdsFrom() {
		return classCodeIdsFrom;
	}

	public void setClassCodeIdsFrom(String classCodeIdsFrom) {
		this.classCodeIdsFrom = classCodeIdsFrom;
	}

	public String[] getClassCodeIdsTo() {
		return classCodeIdsTo;
	}

	public void setClassCodeIdsTo(String[] classCodeIdsTo) {
		this.classCodeIdsTo = classCodeIdsTo;
	}

	public Map<Integer, String> getMapClass() {
		return mapClass;
	}

	public void setMapClass(Map<Integer, String> mapClass) {
		this.mapClass = mapClass;
	}

	public Map<Integer, String> getMapSelectedClass() {
		return mapSelectedClass;
	}

	public void setMapSelectedClass(Map<Integer, String> mapSelectedClass) {
		this.mapSelectedClass = mapSelectedClass;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}

	public ArrayList<ExamFooterAgreementTO> getMainList() {
		return mainList;
	}

	public void setMainList(ArrayList<ExamFooterAgreementTO> mainList) {
		this.mainList = mainList;
	}

	public void setProgramTypeList(List<KeyValueTO> programTypeList) {
		this.programTypeList = programTypeList;
	}

	public List<KeyValueTO> getProgramTypeList() {
		return programTypeList;
	}

	public void setSelectedProgramType(String selectedProgramType) {
		this.selectedProgramType = selectedProgramType;
	}

	public String getSelectedProgramType() {
		return selectedProgramType;
	}

	public String getHalTcktOrMarksCard() {
		return halTcktOrMarksCard;
	}

	public void setHalTcktOrMarksCard(String halTcktOrMarksCard) {
		this.halTcktOrMarksCard = halTcktOrMarksCard;
	}
	
}
