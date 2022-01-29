package com.kp.cms.helpers.phd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdConference;
import com.kp.cms.bo.phd.PhdDocumentSubmissionBO;
import com.kp.cms.bo.phd.PhdDocumentSubmissionSchedule;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdInternalGuideBo;
import com.kp.cms.bo.phd.PhdResearchDescription;
import com.kp.cms.bo.phd.PhdResearchPublication;
import com.kp.cms.bo.phd.PhdStudentPanelMember;
import com.kp.cms.forms.phd.PhdDocumentSubmissionForm;
import com.kp.cms.handlers.phd.PhdDocumentSubmissionHandler;
import com.kp.cms.to.phd.PhdConferenceTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionScheduleTO;
import com.kp.cms.to.phd.PhdDocumentSubmissionTO;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.to.phd.PhdResearchDescriptionTO;
import com.kp.cms.to.phd.PhdResearchPublicationTo;
import com.kp.cms.to.phd.PhdStudentPanelMemberTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdDocumentSubmissionHelper {
	private static final Log log = LogFactory.getLog(PhdDocumentSubmissionHelper.class);
	public static volatile PhdDocumentSubmissionHelper documentSubmissionHelper = null;

	public static PhdDocumentSubmissionHelper getInstance() {
		if (documentSubmissionHelper == null) {
			documentSubmissionHelper = new PhdDocumentSubmissionHelper();
		}
		return documentSubmissionHelper;
	}


	/**
	 * @param guideBOs
	 * @return
	 */
	public List<PhdEmployeeTo> convertBOsToTO(List<PhdEmployee> guideBOs) {
		List<PhdEmployeeTo> guideDetailsTOList=new ArrayList<PhdEmployeeTo>();
	    Iterator<PhdEmployee> iterator=guideBOs.iterator();
	    while(iterator.hasNext())
	    {
	    	PhdEmployee guideDetailsBO=(PhdEmployee) iterator.next();
	    	PhdEmployeeTo guideDetailsTO=new PhdEmployeeTo();
	    	guideDetailsTO.setId(guideDetailsBO.getId());
	    	guideDetailsTO.setName(guideDetailsBO.getName());
	    	guideDetailsTOList.add(guideDetailsTO);
	    }
		return guideDetailsTOList;
     }
	/**
	 * @param studentdetails
	 * @param objForm
	 * @param submissionBo 
	 * @param request 
	 * @return
	 * @throws Exception 
	 */
	public List<PhdDocumentSubmissionTO> setStudentdatatolist(List<Object[]> studentdetails, PhdDocumentSubmissionForm objForm, PhdDocumentSubmissionBO submissionBo, HttpServletRequest request) throws Exception {
		List<PhdDocumentSubmissionTO> details=new ArrayList<PhdDocumentSubmissionTO>();
		
		
		if(studentdetails!=null && !studentdetails.isEmpty()){
			Iterator itr=studentdetails.iterator();
			while (itr.hasNext()) {
				Object[] object = (Object[]) itr.next();
				PhdDocumentSubmissionTO PhdTo=new PhdDocumentSubmissionTO();
				if(object[0]!=null && object[1]!=null && object[2]!=null){
					PhdTo.setStudentName(object[0].toString()+" "+object[1].toString()+""+object[2].toString());
					objForm.setStudentName(object[0].toString()+" "+object[1].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]!=null){
					PhdTo.setStudentName(object[0].toString()+""+object[2].toString());
					objForm.setStudentName(object[0].toString()+""+object[2].toString());
				}if(object[0]!=null &&  object[2]==null){
					PhdTo.setStudentName(object[0].toString());
					objForm.setStudentName(object[0].toString());
				}if(object[3]!=null){
					PhdTo.setStudentId(object[3].toString());
					objForm.setStudentId(object[3].toString());
				}if(object[4]!=null){
					PhdTo.setBatch(object[4].toString());
					objForm.setBatch(object[4].toString());
				}if(object[5]!=null){
					PhdTo.setCourseName(object[5].toString());
					objForm.setCourseName(object[5].toString());
				}if(object[6]!=null){
					PhdTo.setCourseId(object[6].toString());
				}if(object[7]!=null){
					PhdTo.setGuideId(object[7].toString());
					objForm.setGuideId(object[7].toString());
					objForm.setTempGuideId(object[7].toString());
				}if(object[8]!=null){
					PhdTo.setCoGuideId(object[8].toString());
					objForm.setCoGuideId(object[8].toString());
					objForm.setTempCoGuideId(object[8].toString());
				}if(object[9]!=null){
					PhdTo.setSignedOn(object[9].toString());
					objForm.setSignedOn(object[9].toString());
				}else{
					PhdTo.setSignedOn(null);
				}if(object[10]!=null){
					PhdTo.setId(Integer.parseInt(object[10].toString()));
					objForm.setId(Integer.parseInt(object[10].toString()));
					objForm.setBoId(Integer.parseInt(object[10].toString()));
				}if(object[11]!=null){
					PhdTo.setVivaDate(object[11].toString());
					objForm.setVivaDate(object[11].toString());
				}else{
					PhdTo.setVivaDate(null);
				}if(object[12]!=null){
					PhdTo.setVivaTitle(object[12].toString());
					objForm.setVivaTitle(object[12].toString());
				}if(object[13]!=null){
					PhdTo.setVivaDiscipline(object[13].toString());
					objForm.setVivaDiscipline(object[13].toString());
				}if(object[14]!=null){
					PhdTo.setGuideDisplay(true);
					objForm.setGuideDisplay(true);
					List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
			        request.getSession().setAttribute("GuideDetails", listValues);
					PhdTo.setGuideId(object[14].toString());
					objForm.setGuideId(object[14].toString());
					objForm.setTempIntGuideId(object[14].toString());
				}
				if(object[15]!=null){
					PhdTo.setCoGuideDisplay(true);
					objForm.setCoGuideDisplay(true);
					List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalCoGuideDetails();
			        request.getSession().setAttribute("CoGuideDetails", listValues);
					PhdTo.setCoGuideId(object[15].toString());
					objForm.setCoGuideId(object[15].toString());
					objForm.setTempIntCoGuideId(object[15].toString());
				}
				details.add(PhdTo);
			}
		}
		
		
		if(submissionBo!=null){
		if (submissionBo.getResearchDescription()!=null && !submissionBo.getResearchDescription().isEmpty()) {
			Set<PhdResearchDescription> description = submissionBo.getResearchDescription();
			if (description != null && !description.isEmpty()) {
				Iterator<PhdResearchDescription> iterator = description.iterator();
				List<PhdResearchDescriptionTO> descriptionList = new ArrayList<PhdResearchDescriptionTO>();

				while (iterator.hasNext()) {
					PhdResearchDescription descriptionBo = iterator.next();
					if (descriptionBo != null) {
						PhdResearchDescriptionTO descriptionTo =new  PhdResearchDescriptionTO();
						
						if (descriptionBo.getId()> 0) {
							descriptionTo.setId(descriptionBo.getId());
						}
						if (descriptionBo.getResearchPublication()!=null) {
							descriptionTo.setResearchPublication(Integer.toString(descriptionBo.getResearchPublication().getId()));
						}
						if (descriptionBo.getIssn()!=null && !descriptionBo.getIssn().isEmpty()) {
							descriptionTo.setIssn(descriptionBo.getIssn());
						}	if (descriptionBo.getIssueNumber()!=null && !descriptionBo.getIssueNumber().isEmpty()) {
							descriptionTo.setIssueNumber(descriptionBo.getIssueNumber());
						}	if (descriptionBo.getLevel()!=null && !descriptionBo.getLevel().isEmpty()) {
							descriptionTo.setLevel(descriptionBo.getLevel());
						}	if (descriptionBo.getNameJournal()!=null && !descriptionBo.getNameJournal().isEmpty()) {
							descriptionTo.setNameJournal(descriptionBo.getNameJournal());
						}	if (descriptionBo.getTitle()!=null && !descriptionBo.getTitle().isEmpty()) {
							descriptionTo.setTitle(descriptionBo.getTitle());
						}	if (descriptionBo.getDatePhd()!=null && !descriptionBo.getDatePhd().isEmpty()) {
							descriptionTo.setDatePhd(descriptionBo.getDatePhd());
						}	if (descriptionBo.getVolumeNo()!=null && !descriptionBo.getVolumeNo().isEmpty()) {
							descriptionTo.setVolumeNo(descriptionBo.getVolumeNo());
						}	if (descriptionBo.getDescription()!=null && !descriptionBo.getDescription().isEmpty()) {
							descriptionTo.setDescription(descriptionBo.getDescription());
						}
						descriptionList.add(descriptionTo);
						}
				}
				Collections.sort(descriptionList);
				objForm.setResearchDescription(descriptionList);
				
			} else {
				PhdResearchDescriptionTO empPreviousOrgTo=new PhdResearchDescriptionTO();
				
				List<PhdResearchDescriptionTO> teachingList=new ArrayList<PhdResearchDescriptionTO>();
				empPreviousOrgTo.setResearchPublication("");
				empPreviousOrgTo.setIssn("");
				empPreviousOrgTo.setIssueNumber("");
				empPreviousOrgTo.setLevel("");
				empPreviousOrgTo.setNameJournal("");
				empPreviousOrgTo.setTitle("");
				empPreviousOrgTo.setDatePhd("");
				empPreviousOrgTo.setVolumeNo("");
				empPreviousOrgTo.setDescription("");
				objForm.setResearchDescriptionLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objForm.setResearchDescription(teachingList);
			}
		}
		
		if (submissionBo.getConference()!=null && !submissionBo.getConference().isEmpty()) {
			Set<PhdConference> description = submissionBo.getConference();
			if (description != null && !description.isEmpty()) {
				Iterator<PhdConference> iterator = description.iterator();
				List<PhdConferenceTO> descriptionList = new ArrayList<PhdConferenceTO>();

				while (iterator.hasNext()) {
					PhdConference descriptionBo = iterator.next();
					if (descriptionBo != null) {
						PhdConferenceTO descriptionTo =new  PhdConferenceTO();
						
						if (descriptionBo.getId()> 0) {
							descriptionTo.setId(descriptionBo.getId());
						}
						if (descriptionBo.getParticipated()!=null && !descriptionBo.getParticipated().isEmpty()) {
							descriptionTo.setParticipated(descriptionBo.getParticipated());
						}	if (descriptionBo.getOrganizedBy()!=null && !descriptionBo.getOrganizedBy().isEmpty()) {
							descriptionTo.setOrganizedBy(descriptionBo.getOrganizedBy());
						}	if (descriptionBo.getNameProgram()!=null && !descriptionBo.getNameProgram().isEmpty()) {
							descriptionTo.setNameProgram(descriptionBo.getNameProgram());
						}	if (descriptionBo.getDateFrom()!=null && !descriptionBo.getDateFrom().toString().isEmpty()) {
							descriptionTo.setDateFrom(CommonUtil.ConvertStringToDateFormat(descriptionBo.getDateFrom().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
						}	if (descriptionBo.getDateTo()!=null && !descriptionBo.getDateTo().toString().isEmpty()) {
							descriptionTo.setDateTo(CommonUtil.ConvertStringToDateFormat(descriptionBo.getDateTo().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
						}	if (descriptionBo.getLevel()!=null && !descriptionBo.getLevel().isEmpty()) {
							descriptionTo.setLevel(descriptionBo.getLevel());
						}
						descriptionList.add(descriptionTo);
						}
				}
				Collections.sort(descriptionList);
				objForm.setConferenceList(descriptionList);
				
			} else {
				PhdConferenceTO phdConferenceTO=new PhdConferenceTO();
				
				List<PhdConferenceTO> conferenceList=new ArrayList<PhdConferenceTO>();
				phdConferenceTO.setParticipated("");
				phdConferenceTO.setOrganizedBy("");
				phdConferenceTO.setNameProgram("");
				phdConferenceTO.setDateFrom("");
				phdConferenceTO.setDateTo("");
				phdConferenceTO.setLevel("");
				objForm.setResearchDescriptionLength(String.valueOf(conferenceList.size()));
				conferenceList.add(phdConferenceTO);
				objForm.setConferenceList(conferenceList);
			}
		}
		
		
		if (submissionBo.getSynopsisVivaPanel()!=null && !submissionBo.getSynopsisVivaPanel().isEmpty()) {
			Set<PhdStudentPanelMember> synopsisViva = submissionBo.getSynopsisVivaPanel();
			if (synopsisViva != null && !synopsisViva.isEmpty()) {
				Iterator<PhdStudentPanelMember> iterator = synopsisViva.iterator();
				
				List<PhdStudentPanelMemberTO> synopsisList = new ArrayList<PhdStudentPanelMemberTO>();
				List<PhdStudentPanelMemberTO> vivaList = new ArrayList<PhdStudentPanelMemberTO>();

				while (iterator.hasNext()) {
					PhdStudentPanelMember synopsysVivaBo = iterator.next();
					if (synopsysVivaBo != null) {
						PhdStudentPanelMemberTO synopsysTo = new PhdStudentPanelMemberTO();
						PhdStudentPanelMemberTO vivaTo = new PhdStudentPanelMemberTO();
						
						if(synopsysVivaBo.getSelectedPanel()){
						if(synopsysVivaBo.getSynopsisPanel()){
						if (synopsysVivaBo.getId() > 0) {
							synopsysTo.setId(synopsysVivaBo.getId());
						}
						String mode="synopsiss";
						if(synopsysVivaBo.getSynopsisVivaPanel()!=null){
						if (synopsysVivaBo.getSynopsisVivaPanel().getId()>0) {
							synopsysTo.setSynopsisId(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
							String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(synopsysVivaBo.getSynopsisVivaPanel().getId(),objForm,mode);
							synopsysTo.setSynopsisName(name);
							synopsysTo.setCheckPanel(false);
						}}else{
							synopsysTo.setSynopsisId(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
							String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameEmployeeById(synopsysVivaBo.getSynopsisVivaEmployee().getId(),objForm);
							synopsysTo.setSynopsisName(name);
							synopsysTo.setCheckPanel(true);
						}
						if (synopsysVivaBo.getSelectedPanel()) {
							synopsysTo.setTempChecked1("on");
						}else{
							synopsysTo.setChecked1(null);
							synopsysTo.setTempChecked1(null);
						}
						
						synopsisList.add(synopsysTo);
						}
						if(synopsysVivaBo.getVivaPanel()){
							if (synopsysVivaBo.getId() > 0) {
								vivaTo.setId(synopsysVivaBo.getId());
							}
							String mode="vivaa";
							if(synopsysVivaBo.getSynopsisVivaPanel()!=null){
							if (synopsysVivaBo.getSynopsisVivaPanel().getId()>0) {
								vivaTo.setVivaId(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
								String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameById(synopsysVivaBo.getSynopsisVivaPanel().getId(),objForm,mode);
								vivaTo.setVivaName(name);
								vivaTo.setCheckViva(false);
							}}else{
								vivaTo.setVivaId(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
								String name=PhdDocumentSubmissionHandler.getInstance().getPanelNameEmployeeById(synopsysVivaBo.getSynopsisVivaEmployee().getId(),objForm);
								vivaTo.setVivaName(name);
								vivaTo.setCheckViva(true);
							}
							if (synopsysVivaBo.getSelectedPanel()) {
								vivaTo.setTempChecked2("on");
							}else{
								vivaTo.setChecked2(null);
								vivaTo.setTempChecked2(null);
							}
							
							vivaList.add(vivaTo);
							}
						
						}else if(!synopsysVivaBo.getSelectedPanel()){
							if(synopsysVivaBo.getSynopsisPanel()){
							if (synopsysVivaBo.getId() > 0) {
								objForm.setSynopsisBoId(Integer.toString(synopsysVivaBo.getId()));
							}
							if(synopsysVivaBo.getSynopsisVivaPanel()!=null){
								if (synopsysVivaBo.getSynopsisVivaPanel().getId()>0) {
									objForm.setSynopsisId(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
									objForm.setSynopsis(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
									List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
							        request.getSession().setAttribute("PanelGuideDetails", listValues);
									objForm.setPanelGuideDisplay(false);
								}}else if(synopsysVivaBo.getSynopsisVivaEmployee()!=null){
									List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
							        request.getSession().setAttribute("PanelGuideDetails", listValues);
									objForm.setSynopsisId(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
									objForm.setSynopsis(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
									objForm.setPanelGuideDisplay(true);
								}
							}if(synopsysVivaBo.getVivaPanel()){
								if (synopsysVivaBo.getId() > 0) {
									objForm.setVivaBoId(Integer.toString(synopsysVivaBo.getId()));
								}
								if(synopsysVivaBo.getSynopsisVivaPanel()!=null){
									if (synopsysVivaBo.getSynopsisVivaPanel().getId()>0) {
										objForm.setVivaId(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
										objForm.setFinalViva(Integer.toString(synopsysVivaBo.getSynopsisVivaPanel().getId()));
										List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setGuideDetails();
								        request.getSession().setAttribute("VivaGuideDetails", listValues);
										objForm.setFinalGuideDisplay(false);
									}}else if(synopsysVivaBo.getSynopsisVivaEmployee()!=null){
										List<PhdEmployeeTo> listValues=PhdDocumentSubmissionHandler.getInstance().setInternalGuideDetails();
								        request.getSession().setAttribute("VivaGuideDetails", listValues);
										objForm.setVivaId(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
										objForm.setFinalViva(Integer.toString(synopsysVivaBo.getSynopsisVivaEmployee().getId()));
										objForm.setFinalGuideDisplay(true);
									}
								}
						}
						}
				}
				Collections.sort(synopsisList);
				objForm.setSynopsisList(synopsisList);
				Collections.sort(vivaList);
				objForm.setFinalVivaList(vivaList);
				
			} 
		}
		
		}
		return details;
	}
	/**
	 * @param objForm
	 * @return
	 */
	public PhdDocumentSubmissionBO convertFormToBos(PhdDocumentSubmissionForm objForm) {
		PhdDocumentSubmissionBO aggrementBo=new PhdDocumentSubmissionBO();
		Set<PhdResearchDescription> researchDescriptionSet=new HashSet<PhdResearchDescription>();
		Set<PhdConference> phdConferenceSet=new HashSet<PhdConference>();
		Set<PhdStudentPanelMember> synopsisPanelMembes=new HashSet<PhdStudentPanelMember>();
			if(objForm.getBoId()!=0){
			aggrementBo.setId(objForm.getBoId());
			}
			
			
			if(objForm.getResearchDescription()!=null){
				List<PhdResearchDescriptionTO> researchPublication=objForm.getResearchDescription();
				Iterator<PhdResearchDescriptionTO> iterator=researchPublication.iterator();
				while(iterator.hasNext()){
					PhdResearchDescriptionTO phdResearchdescriptionTo=iterator.next();
					PhdResearchDescription researchDescriptionBo=null;
					if(phdResearchdescriptionTo!=null){
						if((phdResearchdescriptionTo.getResearchPublication()!=null && !phdResearchdescriptionTo.getResearchPublication().isEmpty())
								|| (phdResearchdescriptionTo.getDescription()!=null && !phdResearchdescriptionTo.getDescription().isEmpty())
								|| (phdResearchdescriptionTo.getIssn()!=null && !phdResearchdescriptionTo.getIssn().isEmpty())
								|| (phdResearchdescriptionTo.getIssueNumber()!=null && !phdResearchdescriptionTo.getIssueNumber().isEmpty())
								|| (phdResearchdescriptionTo.getNameJournal()!=null && !phdResearchdescriptionTo.getNameJournal().isEmpty()))
						{
							researchDescriptionBo=new PhdResearchDescription();
													
							if(phdResearchdescriptionTo.getId()!=null && phdResearchdescriptionTo.getId().intValue()>0){
								researchDescriptionBo.setId(phdResearchdescriptionTo.getId());
							}
							
							if(phdResearchdescriptionTo.getResearchPublication()!=null && !phdResearchdescriptionTo.getResearchPublication().isEmpty()){
								PhdResearchPublication level=new PhdResearchPublication();
								level.setId(Integer.parseInt(phdResearchdescriptionTo.getResearchPublication()));
								researchDescriptionBo.setResearchPublication(level);
							}
							if(phdResearchdescriptionTo.getIssn()!=null && !phdResearchdescriptionTo.getIssn().isEmpty()){
								researchDescriptionBo.setIssn(phdResearchdescriptionTo.getIssn());
							}
							if(phdResearchdescriptionTo.getIssueNumber()!=null && !phdResearchdescriptionTo.getIssueNumber().isEmpty()){
								researchDescriptionBo.setIssueNumber(phdResearchdescriptionTo.getIssueNumber());
							}
							if(phdResearchdescriptionTo.getLevel()!=null && !phdResearchdescriptionTo.getLevel().isEmpty()){
								researchDescriptionBo.setLevel(phdResearchdescriptionTo.getLevel());
							}
							if(phdResearchdescriptionTo.getNameJournal()!=null && !phdResearchdescriptionTo.getNameJournal().isEmpty()){
								researchDescriptionBo.setNameJournal(phdResearchdescriptionTo.getNameJournal());
							}
							if(phdResearchdescriptionTo.getTitle()!=null && !phdResearchdescriptionTo.getTitle().isEmpty()){
								researchDescriptionBo.setTitle(phdResearchdescriptionTo.getTitle());
							}
							if(phdResearchdescriptionTo.getDatePhd()!=null && !phdResearchdescriptionTo.getDatePhd().isEmpty()){
								researchDescriptionBo.setDatePhd(phdResearchdescriptionTo.getDatePhd());
							}
							if(phdResearchdescriptionTo.getVolumeNo()!=null && !phdResearchdescriptionTo.getVolumeNo().isEmpty()){
								researchDescriptionBo.setVolumeNo(phdResearchdescriptionTo.getVolumeNo());
							}
							
							if(phdResearchdescriptionTo.getDescription()!=null && !phdResearchdescriptionTo.getDescription().isEmpty()){
								researchDescriptionBo.setDescription(phdResearchdescriptionTo.getDescription());
							}
												
							researchDescriptionBo.setIsActive(true);
							researchDescriptionBo.setCreatedBy(objForm.getUserId());
							researchDescriptionBo.setCreatedDate(new Date());
							researchDescriptionBo.setModifiedBy(objForm.getUserId());
							researchDescriptionBo.setModifiedDate(new Date());
							researchDescriptionSet.add(researchDescriptionBo);
						}
					}
				}
			}
			
			aggrementBo.setResearchDescription(researchDescriptionSet);
			
			if(objForm.getConferenceList()!=null){
				List<PhdConferenceTO> conferenceList=objForm.getConferenceList();
				Iterator<PhdConferenceTO> iterator=conferenceList.iterator();
				while(iterator.hasNext()){
					PhdConferenceTO phdConferenceTo=iterator.next();
					PhdConference phdConferenceBo=null;
					if(phdConferenceTo!=null){
						if((phdConferenceTo.getParticipated()!=null && !phdConferenceTo.getParticipated().isEmpty())
								|| (phdConferenceTo.getOrganizedBy()!=null && !phdConferenceTo.getOrganizedBy().isEmpty())
								|| (phdConferenceTo.getNameProgram()!=null && !phdConferenceTo.getNameProgram().isEmpty())
								|| (phdConferenceTo.getDateFrom()!=null && !phdConferenceTo.getDateFrom().isEmpty())
								|| (phdConferenceTo.getDateTo()!=null && !phdConferenceTo.getDateTo().isEmpty()))
						{
							phdConferenceBo=new PhdConference();
													
							if(phdConferenceTo.getId()!=null && phdConferenceTo.getId().intValue()>0){
								phdConferenceBo.setId(phdConferenceTo.getId());
							}
						
							if(phdConferenceTo.getParticipated()!=null && !phdConferenceTo.getParticipated().isEmpty()){
								phdConferenceBo.setParticipated(phdConferenceTo.getParticipated());
							}
							if(phdConferenceTo.getOrganizedBy()!=null && !phdConferenceTo.getOrganizedBy().isEmpty()){
								phdConferenceBo.setOrganizedBy(phdConferenceTo.getOrganizedBy());
							}
							if(phdConferenceTo.getNameProgram()!=null && !phdConferenceTo.getNameProgram().isEmpty()){
								phdConferenceBo.setNameProgram(phdConferenceTo.getNameProgram());
							}
							if(phdConferenceTo.getDateFrom()!=null && !phdConferenceTo.getDateFrom().isEmpty()){
								phdConferenceBo.setDateFrom(CommonUtil.ConvertStringToDate(phdConferenceTo.getDateFrom()));
							}
							if(phdConferenceTo.getDateTo()!=null && !phdConferenceTo.getDateTo().isEmpty()){
								phdConferenceBo.setDateTo(CommonUtil.ConvertStringToDate(phdConferenceTo.getDateTo()));
							}
							if(phdConferenceTo.getLevel()!=null && !phdConferenceTo.getLevel().isEmpty()){
								phdConferenceBo.setLevel(phdConferenceTo.getLevel());
							}
																
							phdConferenceBo.setIsActive(true);
							phdConferenceBo.setCreatedBy(objForm.getUserId());
							phdConferenceBo.setCreatedDate(new Date());
							phdConferenceBo.setModifiedBy(objForm.getUserId());
							phdConferenceBo.setModifiedDate(new Date());
							phdConferenceSet.add(phdConferenceBo);
						}
					}
				}
			}
			
			aggrementBo.setConference(phdConferenceSet);
			
			
			if(objForm.getSynopsisList()!=null && !objForm.getSynopsisList().isEmpty()){
				List<PhdStudentPanelMemberTO> list=objForm.getSynopsisList();
				if(list!=null){
					Iterator<PhdStudentPanelMemberTO> iterator=list.iterator();
					while(iterator.hasNext()){
						PhdStudentPanelMemberTO studentPanelMemberTO=iterator.next();
						PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
						if(studentPanelMemberTO!=null){
							if(studentPanelMemberTO.getSynopsisId()!=null && !studentPanelMemberTO.getSynopsisId().isEmpty()){
								
								if(studentPanelMemberTO.getId()!=null && studentPanelMemberTO.getId().intValue()>0){
									phdEmpExp.setId(studentPanelMemberTO.getId());
								}
								
								if(studentPanelMemberTO.getSynopsisId()!=null && !studentPanelMemberTO.getSynopsisId().isEmpty()){
									if(studentPanelMemberTO.isCheckPanel()){
									Employee phdempp=new Employee();
									phdempp.setId(Integer.parseInt(studentPanelMemberTO.getSynopsisId()));
									phdEmpExp.setSynopsisVivaEmployee(phdempp);
									}else{
										PhdEmployee phdemp=new PhdEmployee();
										phdemp.setId(Integer.parseInt(studentPanelMemberTO.getSynopsisId()));
										phdEmpExp.setSynopsisVivaPanel(phdemp);
									}
								}
								
								if(studentPanelMemberTO.getChecked1()!=null && !studentPanelMemberTO.getChecked1().isEmpty()){
									phdEmpExp.setSelectedPanel(true);
								}else{
									phdEmpExp.setSelectedPanel(false);
								}
																
								phdEmpExp.setSynopsisPanel(true);
								phdEmpExp.setVivaPanel(false);
								phdEmpExp.setIsActive(true);
								phdEmpExp.setCreatedBy(objForm.getUserId());
								phdEmpExp.setCreatedDate(new Date());
								phdEmpExp.setModifiedBy(objForm.getUserId());
								phdEmpExp.setModifiedDate(new Date());
								synopsisPanelMembes.add(phdEmpExp);
							}
						}
					}
				}
				
			} if(objForm.getSynopsis()!=null && !objForm.getSynopsis().isEmpty()){
				PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
				if(objForm.getSynopsisBoId()!=null && !objForm.getSynopsisBoId().isEmpty()){
					phdEmpExp.setId(Integer.parseInt(objForm.getSynopsisBoId()));
				}
				if(objForm.isPanelGuideDisplay()){
					Employee phdempp=new Employee();
					phdempp.setId(Integer.parseInt(objForm.getSynopsis()));
					phdEmpExp.setSynopsisVivaEmployee(phdempp);
					phdEmpExp.setSynopsisVivaPanel(null);
				}else{
					PhdEmployee phdemp=new PhdEmployee();
					phdemp.setId(Integer.parseInt(objForm.getSynopsis()));
					phdEmpExp.setSynopsisVivaPanel(phdemp);
					phdEmpExp.setSynopsisVivaEmployee(null);
				}
				phdEmpExp.setSynopsisPanel(true);
				phdEmpExp.setVivaPanel(false);
				phdEmpExp.setIsActive(true);
				phdEmpExp.setSelectedPanel(false);
				phdEmpExp.setCreatedBy(objForm.getUserId());
				phdEmpExp.setCreatedDate(new Date());
				phdEmpExp.setModifiedBy(objForm.getUserId());
				phdEmpExp.setModifiedDate(new Date());
				synopsisPanelMembes.add(phdEmpExp);
				
			}else if(objForm.getSynopsisBoId()!=null && !objForm.getSynopsisBoId().isEmpty()){
				PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
				if(objForm.getSynopsisBoId()!=null && !objForm.getSynopsisBoId().isEmpty()){
					phdEmpExp.setId(Integer.parseInt(objForm.getSynopsisBoId()));
				}
				phdEmpExp.setSynopsisVivaPanel(null);
				phdEmpExp.setSynopsisVivaEmployee(null);
				phdEmpExp.setSynopsisPanel(true);
				phdEmpExp.setVivaPanel(false);
				phdEmpExp.setIsActive(true);
				phdEmpExp.setSelectedPanel(false);
				phdEmpExp.setCreatedBy(objForm.getUserId());
				phdEmpExp.setCreatedDate(new Date());
				phdEmpExp.setModifiedBy(objForm.getUserId());
				phdEmpExp.setModifiedDate(new Date());
				synopsisPanelMembes.add(phdEmpExp);
				
			}
			
			if(objForm.getFinalVivaList()!=null && !objForm.getFinalVivaList().isEmpty()){
				List<PhdStudentPanelMemberTO> list=objForm.getFinalVivaList();
				if(list!=null){
					Iterator<PhdStudentPanelMemberTO> iterator=list.iterator();
					while(iterator.hasNext()){
						PhdStudentPanelMemberTO studentPanelMemberTO=iterator.next();
						PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
						if(studentPanelMemberTO!=null){
							if(studentPanelMemberTO.getVivaName()!=null && !studentPanelMemberTO.getVivaName().isEmpty()){
								
								if(studentPanelMemberTO.getId()!=null && studentPanelMemberTO.getId().intValue()>0){
									phdEmpExp.setId(studentPanelMemberTO.getId());
								}
								
								if(studentPanelMemberTO.getVivaId()!=null && !studentPanelMemberTO.getVivaId().isEmpty() ){
									if(studentPanelMemberTO.isCheckViva()){
										Employee phdempl=new Employee();
										phdempl.setId(Integer.parseInt(studentPanelMemberTO.getVivaId()));
										phdEmpExp.setSynopsisVivaEmployee(phdempl);
									}else{
									PhdEmployee phdemp=new PhdEmployee();
									phdemp.setId(Integer.parseInt(studentPanelMemberTO.getVivaId()));
									phdEmpExp.setSynopsisVivaPanel(phdemp);
									}
								}
								
								if(studentPanelMemberTO.getChecked2()!=null && !studentPanelMemberTO.getChecked2().isEmpty()){
									phdEmpExp.setSelectedPanel(true);
								}else{
									phdEmpExp.setSelectedPanel(false);
								}
																
								phdEmpExp.setSynopsisPanel(false);
								phdEmpExp.setVivaPanel(true);
								phdEmpExp.setIsActive(true);
								phdEmpExp.setCreatedBy(objForm.getUserId());
								phdEmpExp.setCreatedDate(new Date());
								phdEmpExp.setModifiedBy(objForm.getUserId());
								phdEmpExp.setModifiedDate(new Date());
								synopsisPanelMembes.add(phdEmpExp);
							}
						}
					}
				}
				
			} if(objForm.getFinalViva()!=null && !objForm.getFinalViva().isEmpty()){
				PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
				if(objForm.getVivaBoId()!=null && !objForm.getVivaBoId().isEmpty()){
					phdEmpExp.setId(Integer.parseInt(objForm.getVivaBoId()));
				}
				if(objForm.isFinalGuideDisplay()){
					Employee phdempl=new Employee();
					phdempl.setId(Integer.parseInt(objForm.getFinalViva()));
					phdEmpExp.setSynopsisVivaEmployee(phdempl);
					phdEmpExp.setSynopsisVivaPanel(null);
				}else{
					PhdEmployee phdemp=new PhdEmployee();
					phdemp.setId(Integer.parseInt(objForm.getFinalViva()));
					phdEmpExp.setSynopsisVivaPanel(phdemp);
					phdEmpExp.setSynopsisVivaEmployee(null);
					}
				phdEmpExp.setSynopsisPanel(false);
				phdEmpExp.setVivaPanel(true);
				phdEmpExp.setIsActive(true);
				phdEmpExp.setSelectedPanel(false);
				phdEmpExp.setCreatedBy(objForm.getUserId());
				phdEmpExp.setCreatedDate(new Date());
				phdEmpExp.setModifiedBy(objForm.getUserId());
				phdEmpExp.setModifiedDate(new Date());
				synopsisPanelMembes.add(phdEmpExp);
			}else if(objForm.getVivaBoId()!=null && !objForm.getVivaBoId().isEmpty()){

				PhdStudentPanelMember phdEmpExp=new PhdStudentPanelMember();
				if(objForm.getVivaBoId()!=null && !objForm.getVivaBoId().isEmpty()){
					phdEmpExp.setId(Integer.parseInt(objForm.getVivaBoId()));
				}
					phdEmpExp.setSynopsisVivaPanel(null);
					phdEmpExp.setSynopsisVivaEmployee(null);
				phdEmpExp.setSynopsisPanel(false);
				phdEmpExp.setVivaPanel(true);
				phdEmpExp.setIsActive(true);
				phdEmpExp.setSelectedPanel(false);
				phdEmpExp.setCreatedBy(objForm.getUserId());
				phdEmpExp.setCreatedDate(new Date());
				phdEmpExp.setModifiedBy(objForm.getUserId());
				phdEmpExp.setModifiedDate(new Date());
				synopsisPanelMembes.add(phdEmpExp);
			
			}
			
			aggrementBo.setSynopsisVivaPanel(synopsisPanelMembes);
			
			
			if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty() || objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
			Student student=new Student();
			PhdEmployee guide=new PhdEmployee();
			PhdEmployee coguide=new PhdEmployee();
			DisciplineBo discipline=new DisciplineBo();
			Employee interGuide=new Employee();
			Employee interCoGuide=new Employee();
			
			student.setId(Integer.parseInt(objForm.getStudentId()));
			aggrementBo.setStudentId(student);
			if(objForm.isGuideDisplay()){
				if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()){
					interGuide.setId(Integer.parseInt(objForm.getGuideId()));
					aggrementBo.setInternalGuide(interGuide);
					aggrementBo.setGuideId(null);
				     }
			}else{
				if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()){
					guide.setId(Integer.parseInt(objForm.getGuideId()));
					aggrementBo.setGuideId(guide);
					aggrementBo.setInternalGuide(null);
				     }
		    }
			if(objForm.isCoGuideDisplay()){
				if(objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
					interCoGuide.setId(Integer.parseInt(objForm.getCoGuideId()));
					aggrementBo.setInternalCoGuide(interCoGuide);
					aggrementBo.setCoGuideId(null);
				}
			}else{
				if(objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
					coguide.setId(Integer.parseInt(objForm.getCoGuideId()));
					aggrementBo.setCoGuideId(coguide);
					aggrementBo.setInternalCoGuide(null);
				}
			}
			
			if(objForm.getVivaDiscipline()!=null && !objForm.getVivaDiscipline().isEmpty())
			{
				discipline.setId(Integer.parseInt(objForm.getVivaDiscipline()));
				aggrementBo.setDisciplineId(discipline);
			}
			if(objForm.getVivaDate()!=null && !objForm.getVivaDate().isEmpty()){
			aggrementBo.setVivaVoceDate(CommonUtil.ConvertStringToDate(objForm.getVivaDate()));}
			aggrementBo.setSignedOn(CommonUtil.ConvertStringToDate(objForm.getSignedOn()));
			if(objForm.getVivaTitle()!=null && !objForm.getVivaTitle().isEmpty())
			aggrementBo.setTitle(objForm.getVivaTitle());
			aggrementBo.setCreatedBy(objForm.getUserId());
			aggrementBo.setCreatedDate(new Date());
			aggrementBo.setLastModifiedDate(new Date());
			aggrementBo.setModifiedBy(objForm.getUserId());
			aggrementBo.setIsActive(Boolean.valueOf(true));
			return aggrementBo;
			}
			return null;
			
   }
	/**
	 * @param guideList
	 * @return
	 */
	public List<PhdDocumentSubmissionScheduleTO> copyBotoToguide(List<Object[]> guideList) {
		log.debug("Helper : Entering copyBotoTo");
		List<PhdDocumentSubmissionScheduleTO> guideToList = new ArrayList<PhdDocumentSubmissionScheduleTO>();
		Iterator<Object[]> itr = guideList.iterator();
	while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdDocumentSubmissionScheduleTO PhdTo=new PhdDocumentSubmissionScheduleTO();
		if(object[0]!=null){
			PhdTo.setRegisterNo(object[0].toString());
		}if(object[1]!=null && object[2]!=null && object[3]!=null){
			PhdTo.setStudentName(object[1].toString()+" "+object[2].toString()+""+object[3].toString());
		}if(object[1]!=null &&  object[3]!=null){
			PhdTo.setStudentName(object[1].toString()+""+object[3].toString());
		}if(object[1]!=null &&  object[3]==null){
			PhdTo.setStudentName(object[1].toString());
		}if(object[4]!=null){
			PhdTo.setCourseName(object[4].toString());
		}if(object[5]!=null){
			PhdTo.setDocumentName(object[5].toString());
		}if(object[6]!=null){
			PhdTo.setSubmittedDate(object[6].toString());
		}if(object[7]!=null){
			PhdTo.setGuide(object[7].toString());
		}if(object[8]!=null){
			PhdTo.setCoGuide(object[8].toString());
		}
		if(object[9]!=null){
			PhdTo.setStudentId(Integer.parseInt(object[9].toString()));
		}if(object[10]!=null){
			PhdTo.setCourseId(object[10].toString());
		}if(object[11]!=null){
			PhdTo.setDocumentId(object[11].toString());
		}if(object[12]!=null){
		//	PhdTo.setChecked(null);
		//	PhdTo.setTempChecked("on");
			PhdTo.setPrintornot("Yes");
		}else{
			PhdTo.setPrintornot("No");
		}
		guideToList.add(PhdTo);
	}
		log.debug("Helper : Leaving copyBotoTo");
	   return guideToList;
	}


	public List<PhdDocumentSubmissionScheduleTO> SetDocumentSubmissionByReg(List<PhdDocumentSubmissionSchedule> subStudentBo,PhdDocumentSubmissionForm objForm) {
		List<PhdDocumentSubmissionScheduleTO> documentList= new ArrayList<PhdDocumentSubmissionScheduleTO>();
		Iterator<PhdDocumentSubmissionSchedule> itr=subStudentBo.iterator();
		while (itr.hasNext()) {
			PhdDocumentSubmissionSchedule documentBo = (PhdDocumentSubmissionSchedule) itr.next();
			PhdDocumentSubmissionScheduleTO documentTo=new PhdDocumentSubmissionScheduleTO();
			documentTo.setId(documentBo.getId());
			objForm.setStudentName(documentBo.getStudentId().getAdmAppln().getPersonalData().getFirstName());
			objForm.setCourseName(documentBo.getCourseId().getName());
			documentTo.setStudentId(documentBo.getStudentId().getId());
			documentTo.setCourseId(Integer.toString(documentBo.getCourseId().getId()));
			documentTo.setDocumentName(documentBo.getDocumentId().getDocumentName());
			documentTo.setDocumentId(Integer.toString(documentBo.getDocumentId().getId()));
			if(documentBo.getAssignDate()!=null && !documentBo.getAssignDate().toString().isEmpty()){
	    		documentTo.setAssignDate(CommonUtil.ConvertStringToDateFormat(documentBo.getAssignDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
				documentTo.setDocumentAssiDate(documentBo.getAssignDate());
			}if(documentBo.getIsReminderMail()!=null){
				documentTo.setIsReminderMail(documentBo.getIsReminderMail() ? "Yes":"No");
			}if(documentBo.getGuidesFee()!=null){
			 documentTo.setGuidesFee(documentBo.getGuidesFee() ? "Yes":"No");
			}if(documentBo.getCanSubmitOnline()!=null){
				documentTo.setCanSubmitOnline(documentBo.getCanSubmitOnline() ? "Yes":"No");
			}if(documentBo.getSubmited()!=null){
				documentTo.setTempChecked(documentBo.getSubmited() ? "on": null);
			}if(documentBo.getSubmittedDate()!=null && documentBo.getSubmited()!=null){
	    		documentTo.setSubmittedDate(CommonUtil.ConvertStringToDateFormat(documentBo.getSubmittedDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			}if(documentBo.getGuideFeeGenerated()!=null && documentBo.getGuideFeeGenerated()!=null){
	    		documentTo.setGuideFeeGenerated(CommonUtil.ConvertStringToDateFormat(documentBo.getGuideFeeGenerated().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			}
			documentList.add(documentTo);
		}
		return documentList;
	}


	public List<PhdResearchPublicationTo> convertBOsToResearchPublication(List<PhdResearchPublication> researchBOs) {
		List<PhdResearchPublicationTo> researchTOList=new ArrayList<PhdResearchPublicationTo>();
	    Iterator<PhdResearchPublication> iterator=researchBOs.iterator();
	    while(iterator.hasNext())
	    {
	    	PhdResearchPublication researchBO=(PhdResearchPublication) iterator.next();
	    	PhdResearchPublicationTo researchTO=new PhdResearchPublicationTo();
	    	researchTO.setId(researchBO.getId());
	    	researchTO.setName(researchBO.getName());
	    	researchTOList.add(researchTO);
	    }
		return researchTOList;
     }


	public List<PhdDocumentSubmissionSchedule> documentFormToBO(PhdDocumentSubmissionForm objForm) {
		List<PhdDocumentSubmissionSchedule> documentList=new ArrayList<PhdDocumentSubmissionSchedule>();
		Iterator<PhdDocumentSubmissionScheduleTO> irr=objForm.getStudentDetailsList().iterator();
		while (irr.hasNext()) {
			PhdDocumentSubmissionScheduleTO documentTO = (PhdDocumentSubmissionScheduleTO) irr.next();
			PhdDocumentSubmissionSchedule documentBo=new PhdDocumentSubmissionSchedule();
			documentBo.setId(documentTO.getId());
			Student stu=new Student();
			Course course=new Course();
			DocumentDetailsBO doc=new DocumentDetailsBO();
			stu.setId(documentTO.getStudentId());
			course.setId(Integer.parseInt(documentTO.getCourseId()));
			doc.setId(Integer.parseInt(documentTO.getDocumentId()));
			documentBo.setStudentId(stu);
			documentBo.setCourseId(course);
			documentBo.setDocumentId(doc);
			if(documentTO.getDocumentAssiDate()!=null){
			documentBo.setAssignDate(documentTO.getDocumentAssiDate());
			}
			if(documentTO.getIsReminderMail().equalsIgnoreCase("Yes") && documentTO.getIsReminderMail()!=null && !documentTO.getIsReminderMail().isEmpty()){
				documentBo.setIsReminderMail(true);
			}else{
				documentBo.setIsReminderMail(false);
			}if(documentTO.getGuidesFee().equalsIgnoreCase("Yes") && documentTO.getGuidesFee()!=null){
				documentBo.setGuidesFee(true);
			}else{
				documentBo.setGuidesFee(false);
			}if(documentTO.getCanSubmitOnline().equalsIgnoreCase("Yes") && documentTO.getCanSubmitOnline()!=null && !documentTO.getCanSubmitOnline().isEmpty()){
				documentBo.setCanSubmitOnline(true);
			}else{
				documentBo.setCanSubmitOnline(false);
			}if(documentTO.getChecked()!=null){
			  documentBo.setSubmited(true);
			}else{
				documentBo.setSubmited(false);
			}if(documentTO.getSubmittedDate()!=null && !documentTO.getSubmittedDate().isEmpty()){
				documentBo.setSubmittedDate(CommonUtil.ConvertStringToDate(documentTO.getSubmittedDate()));
			}if(documentTO.getGuideFeeGenerated()!=null && !documentTO.getGuideFeeGenerated().isEmpty()){
				documentBo.setGuideFeeGenerated(CommonUtil.ConvertStringToDate(documentTO.getGuideFeeGenerated()));
			}
			documentBo.setCreatedBy(objForm.getUserId());
			documentBo.setCreatedDate(new Date());
			documentBo.setLastModifiedDate(new Date());
			documentBo.setModifiedBy(objForm.getUserId());
			documentBo.setIsActive(true);
			documentList.add(documentBo);
		}
		return documentList;
}


	public List<PhdEmployeeTo> convertinternalBOsToTO(List<PhdInternalGuideBo> guideBOs) {
		List<PhdEmployeeTo> guideDetailsTOList=new ArrayList<PhdEmployeeTo>();
	    Iterator<PhdInternalGuideBo> iterator=guideBOs.iterator();
	    while(iterator.hasNext())
	    {
	    	PhdInternalGuideBo guideDetailsBO=(PhdInternalGuideBo) iterator.next();
	    	PhdEmployeeTo guideDetailsTO=new PhdEmployeeTo();
	    	guideDetailsTO.setId(guideDetailsBO.getEmployeeId().getId());
	    	guideDetailsTO.setName(guideDetailsBO.getEmployeeId().getFirstName());
	    	guideDetailsTOList.add(guideDetailsTO);
	    }
		return guideDetailsTOList;
     }



}
