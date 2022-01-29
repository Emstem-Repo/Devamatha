package com.kp.cms.forms.phd;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;

import com.itextpdf.text.pdf.hyphenation.TernaryTree.Iterator;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.BaseActionForm;
import com.kp.cms.to.phd.DocumentDetailsSubmissionTO;
import com.kp.cms.to.phd.PhdConferenceTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionTO;
import com.kp.cms.to.phd.PhdResearchDescriptionTO;
import com.kp.cms.to.phd.PhdStudentPanelMemberTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdDocumentSubmissionForm extends BaseActionForm {
    
	private int id;
	private String registerNo;
	private String studentId;
	private String studentName;
	private String courseName;
	private String submittedDate;
	private String courseId;
	private String batch;
	private String guide;
	private String guideId;
	private String coGuide;
	private String coGuideId;
	private String signedOn;
	private List<PhdDocumentSubmissionTO> studentDetails;
	private List<PhdDocumentSubmissionScheduleTO> studentDetailsList;
	private List<DocumentDetailsSubmissionTO> documentList;
	private List<PhdResearchDescriptionTO> researchDescription;
	private String researchDescriptionLength;
	private String researchPublication;
	private String synopsis;
	private String finalViva;
	private List<PhdStudentPanelMemberTO> synopsisList;
	private List<PhdStudentPanelMemberTO> finalVivaList;
	private String tempChecked;
	private String checked;
	private boolean guideDisplay;
	private boolean coGuideDisplay;
	private String vivaDate;
	private String vivaTitle;
	private String vivaDiscipline;
	private Map<String,String> guideShipMap;
	private String tempResearch;
	private String tempIssn;
	private boolean panelGuideDisplay;
	private boolean finalGuideDisplay;
	private int boId;
	private String synopsisId;
	private String vivaId;
	private String synopsisBoId;
	private String vivaBoId;
	private String focusValue;
	private String tempGuideId;
	private String tempCoGuideId;
	private String tempIntGuideId;
	private String tempIntCoGuideId;
	private List<PhdConferenceTO> conferenceList;
	private String conferenceLength;
	private String conference;
	private String tempOrganBy;
	private String tempNameProgram;
	
	public ActionErrors validate(ActionMapping mapping,
			HttpServletRequest request) {
		String formName = request.getParameter("formName");
		ActionErrors actionErrors = super.validate(mapping, request, formName);
		if(actionErrors.isEmpty()) {
		if(this.conferenceList!=null && !this.conferenceList.isEmpty()){
		java.util.Iterator<PhdConferenceTO> itr=this.conferenceList.iterator();
			while (itr.hasNext()) {
				PhdConferenceTO phdConferenceTO = (PhdConferenceTO) itr.next();
			
			if(phdConferenceTO.getDateFrom()!=null && !phdConferenceTO.getDateFrom().isEmpty() && phdConferenceTO.getDateTo()!=null && !phdConferenceTO.getDateTo().isEmpty()){
			Date startDate = CommonUtil.ConvertStringToDate(phdConferenceTO.getDateFrom());
			Date endDate = CommonUtil.ConvertStringToDate(phdConferenceTO.getDateTo());
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(startDate);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTime(endDate);
			long daysBetween = CommonUtil.getDaysBetweenDates(cal1, cal2);
			if(daysBetween <= 0) {
				actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_PHD_STARTDATE_CONNOTBELESS));
			          }
			      }
			   }
		     }
		if(this.researchDescription!=null && !this.researchDescription.isEmpty()){
			java.util.Iterator<PhdResearchDescriptionTO> itr=this.researchDescription.iterator();
				while (itr.hasNext()) {
					PhdResearchDescriptionTO phdResearchDescriptionTO = (PhdResearchDescriptionTO) itr.next();
				
				if(phdResearchDescriptionTO.getDescription()!=null && !phdResearchDescriptionTO.getDescription().isEmpty() && phdResearchDescriptionTO.getDescription().trim().length()>999){
					actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_PHD_RESEARCH_DESCRIPTION));
				      }
				   }
			     }
		  if (this.vivaTitle != null && !this.vivaTitle.isEmpty() && this.vivaTitle.trim().length()>249)  {
		    	actionErrors.add("error", new ActionError(CMSConstants.KNOWLEDGEPRO_PHD_VIVATITLE_LIMIT));
			}
	    }
		return actionErrors;
	}

	public void clearPage() 
	     {
		this.id=0;
		this.boId=0;
		this.synopsisId=null;
		this.synopsisBoId=null;
		this.vivaBoId=null;
		this.vivaId=null;
        this.registerNo=null;
        this.studentId=null;
        this.studentName=null;
        this.courseId=null;
        this.courseName=null;
        this.studentName=null;
        this.batch=null;
        this.guide=null;
        this.guideId=null;
        this.coGuide=null;
        this.coGuideId=null;
        this.signedOn=null;
        this.studentDetailsList=null;
        this.documentList=null;
        this.researchDescription=null;
        this.researchDescriptionLength=null;
        this.researchPublication=null;
        this.synopsis=null;
        this.finalViva=null;
        this.synopsisList=null;
        this.finalVivaList=null;
        this.checked=null;
        this.tempChecked=null;
        this.vivaDate=null;
        this.vivaTitle=null;
        this.vivaDiscipline=null;
        this.submittedDate=null;
        this.studentDetails=null;
        this.tempIssn=null;
        this.tempResearch=null;
        this.guideDisplay=false;
        this.coGuideDisplay=false;
        this.panelGuideDisplay=false;
        this.finalGuideDisplay=false;
        this.focusValue=null;
        this.tempGuideId="";
        this.tempCoGuideId="";
        this.tempIntGuideId="";
        this.tempIntCoGuideId="";
        this.conferenceList=null;
        this.conference=null;
        this.conferenceLength=null;
        this.tempOrganBy=null;
        this.tempNameProgram=null;
   }
public void clearPage1() 
    {
   this.boId=0;
   this.synopsisId=null;
   this.synopsisBoId=null;
   this.vivaBoId=null;
   this.vivaId=null;
   this.guide=null;
   this.guideId=null;
   this.coGuideId=null;
   this.coGuide=null;
   this.signedOn=null;
   this.focusValue=null;
   this.tempGuideId="";
   this.tempCoGuideId="";
   this.tempIntGuideId="";
   this.tempIntCoGuideId="";
}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRegisterNo() {
		return registerNo;
	}

	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getGuide() {
		return guide;
	}

	public void setGuide(String guide) {
		this.guide = guide;
	}

	public String getCoGuide() {
		return coGuide;
	}

	public void setCoGuide(String coGuide) {
		this.coGuide = coGuide;
	}

	public String getSignedOn() {
		return signedOn;
	}

	public void setSignedOn(String signedOn) {
		this.signedOn = signedOn;
	}

	public String getGuideId() {
		return guideId;
	}

	public void setGuideId(String guideId) {
		this.guideId = guideId;
	}

	public String getCoGuideId() {
		return coGuideId;
	}

	public void setCoGuideId(String coGuideId) {
		this.coGuideId = coGuideId;
	}

	public List<PhdDocumentSubmissionScheduleTO> getStudentDetailsList() {
		return studentDetailsList;
	}

	public void setStudentDetailsList(
			List<PhdDocumentSubmissionScheduleTO> studentDetailsList) {
		this.studentDetailsList = studentDetailsList;
	}

	public List<DocumentDetailsSubmissionTO> getDocumentList() {
		return documentList;
	}

	public void setDocumentList(List<DocumentDetailsSubmissionTO> documentList) {
		this.documentList = documentList;
	}

	public List<PhdResearchDescriptionTO> getResearchDescription() {
		return researchDescription;
	}

	public void setResearchDescription(
			List<PhdResearchDescriptionTO> researchDescription) {
		this.researchDescription = researchDescription;
	}

	public String getResearchDescriptionLength() {
		return researchDescriptionLength;
	}

	public void setResearchDescriptionLength(String researchDescriptionLength) {
		this.researchDescriptionLength = researchDescriptionLength;
	}

	public String getResearchPublication() {
		return researchPublication;
	}

	public void setResearchPublication(String researchPublication) {
		this.researchPublication = researchPublication;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getFinalViva() {
		return finalViva;
	}

	public void setFinalViva(String finalViva) {
		this.finalViva = finalViva;
	}

	public List<PhdStudentPanelMemberTO> getSynopsisList() {
		return synopsisList;
	}

	public void setSynopsisList(List<PhdStudentPanelMemberTO> synopsisList) {
		this.synopsisList = synopsisList;
	}

	public List<PhdStudentPanelMemberTO> getFinalVivaList() {
		return finalVivaList;
	}

	public void setFinalVivaList(List<PhdStudentPanelMemberTO> finalVivaList) {
		this.finalVivaList = finalVivaList;
	}

	public String getTempChecked() {
		return tempChecked;
	}

	public void setTempChecked(String tempChecked) {
		this.tempChecked = tempChecked;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getVivaDate() {
		return vivaDate;
	}

	public void setVivaDate(String vivaDate) {
		this.vivaDate = vivaDate;
	}

	public String getVivaTitle() {
		return vivaTitle;
	}

	public void setVivaTitle(String vivaTitle) {
		this.vivaTitle = vivaTitle;
	}

	public String getVivaDiscipline() {
		return vivaDiscipline;
	}

	public void setVivaDiscipline(String vivaDiscipline) {
		this.vivaDiscipline = vivaDiscipline;
	}

	public Map<String, String> getGuideShipMap() {
		return guideShipMap;
	}

	public void setGuideShipMap(Map<String, String> guideShipMap) {
		this.guideShipMap = guideShipMap;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public List<PhdDocumentSubmissionTO> getStudentDetails() {
		return studentDetails;
	}

	public void setStudentDetails(List<PhdDocumentSubmissionTO> studentDetails) {
		this.studentDetails = studentDetails;
	}

	public String getTempResearch() {
		return tempResearch;
	}

	public void setTempResearch(String tempResearch) {
		this.tempResearch = tempResearch;
	}

	public String getTempIssn() {
		return tempIssn;
	}

	public void setTempIssn(String tempIssn) {
		this.tempIssn = tempIssn;
	}

	public boolean isGuideDisplay() {
		return guideDisplay;
	}

	public void setGuideDisplay(boolean guideDisplay) {
		this.guideDisplay = guideDisplay;
	}

	public boolean isCoGuideDisplay() {
		return coGuideDisplay;
	}

	public void setCoGuideDisplay(boolean coGuideDisplay) {
		this.coGuideDisplay = coGuideDisplay;
	}

	public boolean isPanelGuideDisplay() {
		return panelGuideDisplay;
	}

	public void setPanelGuideDisplay(boolean panelGuideDisplay) {
		this.panelGuideDisplay = panelGuideDisplay;
	}

	public boolean isFinalGuideDisplay() {
		return finalGuideDisplay;
	}

	public void setFinalGuideDisplay(boolean finalGuideDisplay) {
		this.finalGuideDisplay = finalGuideDisplay;
	}

	public int getBoId() {
		return boId;
	}

	public void setBoId(int boId) {
		this.boId = boId;
	}

	public String getSynopsisId() {
		return synopsisId;
	}

	public void setSynopsisId(String synopsisId) {
		this.synopsisId = synopsisId;
	}

	public String getVivaId() {
		return vivaId;
	}

	public void setVivaId(String vivaId) {
		this.vivaId = vivaId;
	}

	public String getSynopsisBoId() {
		return synopsisBoId;
	}

	public void setSynopsisBoId(String synopsisBoId) {
		this.synopsisBoId = synopsisBoId;
	}

	public String getVivaBoId() {
		return vivaBoId;
	}

	public void setVivaBoId(String vivaBoId) {
		this.vivaBoId = vivaBoId;
	}

	public String getFocusValue() {
		return focusValue;
	}

	public void setFocusValue(String focusValue) {
		this.focusValue = focusValue;
	}

	public String getTempGuideId() {
		return tempGuideId;
	}

	public void setTempGuideId(String tempGuideId) {
		this.tempGuideId = tempGuideId;
	}

	public String getTempCoGuideId() {
		return tempCoGuideId;
	}

	public void setTempCoGuideId(String tempCoGuideId) {
		this.tempCoGuideId = tempCoGuideId;
	}

	public String getTempIntGuideId() {
		return tempIntGuideId;
	}

	public void setTempIntGuideId(String tempIntGuideId) {
		this.tempIntGuideId = tempIntGuideId;
	}

	public String getTempIntCoGuideId() {
		return tempIntCoGuideId;
	}

	public void setTempIntCoGuideId(String tempIntCoGuideId) {
		this.tempIntCoGuideId = tempIntCoGuideId;
	}

	public List<PhdConferenceTO> getConferenceList() {
		return conferenceList;
	}

	public void setConferenceList(List<PhdConferenceTO> conferenceList) {
		this.conferenceList = conferenceList;
	}

	public String getConferenceLength() {
		return conferenceLength;
	}

	public void setConferenceLength(String conferenceLength) {
		this.conferenceLength = conferenceLength;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public String getTempOrganBy() {
		return tempOrganBy;
	}

	public void setTempOrganBy(String tempOrganBy) {
		this.tempOrganBy = tempOrganBy;
	}

	public String getTempNameProgram() {
		return tempNameProgram;
	}

	public void setTempNameProgram(String tempNameProgram) {
		this.tempNameProgram = tempNameProgram;
	}
	
}
