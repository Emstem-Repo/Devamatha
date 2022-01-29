package com.kp.cms.actions.admission;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.kp.cms.actions.BaseDispatchAction;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admission.FinalMeritListForm;
import com.kp.cms.handlers.admin.CasteHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.ProgramTypeHandler;
import com.kp.cms.handlers.admin.ReligionHandler;
import com.kp.cms.handlers.admin.ResidentCategoryHandler;
import com.kp.cms.handlers.admin.UniversityHandler;
import com.kp.cms.handlers.admission.FinalMeritListSearchHandlar;
import com.kp.cms.helpers.admission.FinalMeritListSearchHelper;
import com.kp.cms.to.admin.CasteTO;
import com.kp.cms.to.admin.NationalityTO;
import com.kp.cms.to.admin.OrganizationTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.to.admission.FinalMeritListMailTO;
import com.kp.cms.to.admission.FinalMeritListSearchTo;
import com.kp.cms.utilities.CommonUtil;

public class FinalMeritApproveAction extends BaseDispatchAction {
	
	private static final Log log = LogFactory.getLog(FinalMeritApproveAction.class);
	/**
	 * 
	 * Performs the initialize final merit list .
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward initFinalMeritList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initFinalMeritList of FinalMeritListAction class");
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		//It use for Help,Don't Remove
		HttpSession session=request.getSession();
		session.setAttribute("field","approve_final_mertlist");
		
		finalMeritListForm.resetFields(finalMeritListForm);
		List<OrganizationTO> tos=OrganizationHandler.getInstance().getOrganizationDetails();
			if(tos!=null && !tos.isEmpty())
			{
				OrganizationTO orgTO=tos.get(0);
				finalMeritListForm.setNeedApproval(orgTO.isFinalMeritListApproval());
			}
		setRequiredDatatoForm(finalMeritListForm, request);
		log.info("exit into initFinalMeritList of FinalMeritListAction class");
		return mapping.findForward(CMSConstants.INIT_MERIT_LIST_APPROVE);
	}
	
	
	/**
	 * 
	 * gets the  final merit selected list
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward getSelectedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into initSelectedList of FinalMeritListSearchAction class.");
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		 ActionErrors errors = finalMeritListForm.validate(mapping, request);
		try {
				if(!errors.isEmpty()){
				addErrors(request, errors);
				setRequiredDatatoForm(finalMeritListForm, request);
				return mapping.findForward(CMSConstants.INIT_MERIT_LIST_APPROVE);
				}
				
				if(!finalMeritListForm.isNeedApproval()){
					message = new ActionMessage("knowledgepro.admission.approvedmeritlist.notneeded");
					messages.add(CMSConstants.MESSAGES, message);
					saveMessages(request, messages);
					setRequiredDatatoForm(finalMeritListForm, request);
					return mapping.findForward(CMSConstants.INIT_MERIT_LIST_APPROVE);
				}
				
				
			finalMeritListForm.setFinalMeritList(FinalMeritListSearchHandlar
					.getInstanse().getFinalMeritListApproveSearchResult(
							finalMeritListForm, true, null));
			
			Iterator<FinalMeritListSearchTo> finalMeritListIterator = finalMeritListForm.getFinalMeritList()
			.iterator();
			FinalMeritListMailTO mailTO;
			List<FinalMeritListMailTO> mailList = new ArrayList<FinalMeritListMailTO>();
			while (finalMeritListIterator.hasNext()) {
				FinalMeritListSearchTo finalMeritListSearchTo = (FinalMeritListSearchTo) finalMeritListIterator
						.next();
				
				mailTO = new FinalMeritListMailTO();
				if(finalMeritListSearchTo.getFinalMeritSetId()!= null){
					mailTO.setMeritListId(finalMeritListSearchTo.getFinalMeritSetId());
				}				
				AdmAppln admAppln = finalMeritListSearchTo.getAdmAppln();
				mailTO.setApplicationProcessSms(admAppln.getCourseBySelectedCourseId().getIsApplicationProcessSms());
				if (admAppln != null && admAppln.getPersonalData() != null) {
					if (admAppln != null && admAppln.getPersonalData() != null) {
						mailTO.setMobileNo1(admAppln.getPersonalData().getMobileNo1());
						mailTO.setMobileNo2(admAppln.getPersonalData().getMobileNo2());
						if (admAppln.getPersonalData().getDateOfBirth() != null) {
							mailTO.setDateOfBirth(CommonUtil.getStringDate(admAppln
									.getPersonalData().getDateOfBirth()));
						}
						if (admAppln.getPersonalData().getBirthPlace() != null) {
							mailTO.setPlaceOfBirth(admAppln.getPersonalData().getBirthPlace());
						}
						if (admAppln.getPersonalData().getFirstName() != null
								&& !admAppln.getPersonalData().getFirstName().trim()
										.isEmpty()) {
							mailTO.setApplicantName(admAppln.getPersonalData().getFirstName());
						}
						if (admAppln.getPersonalData().getMiddleName() != null
								&& !admAppln.getPersonalData().getMiddleName().trim()
										.isEmpty()) {
							mailTO.setApplicantName(mailTO.getApplicantName() + " "
									+ admAppln.getPersonalData().getMiddleName());
						}
						if (admAppln.getPersonalData().getLastName() != null
								&& !admAppln.getPersonalData().getLastName().trim()
										.isEmpty()) {
							mailTO.setApplicantName(mailTO.getApplicantName() + " "
									+ admAppln.getPersonalData().getLastName());
						}
						if (admAppln.getPersonalData().getNationalityOthers() != null) {
							mailTO.setNationality(admAppln.getPersonalData()
									.getNationalityOthers());
						} else if (admAppln.getPersonalData().getNationality() != null) {
							mailTO.setNationality(admAppln.getPersonalData().getNationality()
									.getName());
						}
						if (admAppln.getPersonalData().getReligionOthers() != null) {
							mailTO.setReligion(admAppln.getPersonalData().getReligionOthers());
						} else if (admAppln.getPersonalData().getReligion() != null) {
							mailTO.setReligion(admAppln.getPersonalData().getReligion()
									.getName());
						}

						if (admAppln.getPersonalData().getReligionSectionOthers() != null) {
							mailTO.setSubreligion(admAppln.getPersonalData()
									.getReligionSectionOthers());
						} else if (admAppln.getPersonalData().getReligionSection() != null) {
							mailTO.setSubreligion(admAppln.getPersonalData()
									.getReligionSection().getName());
						}

						if (admAppln.getPersonalData().getGender() != null) {
							mailTO.setGender(admAppln.getPersonalData().getGender());
						}
						if (admAppln.getPersonalData().getEmail() != null) {
							mailTO.setEmail(admAppln.getPersonalData().getEmail());
						}
						if (admAppln.getPersonalData().getCasteOthers() != null) {
							mailTO.setCategory(admAppln.getPersonalData().getCasteOthers());
						} else if (admAppln.getPersonalData().getCaste() != null) {
							mailTO.setCategory(admAppln.getPersonalData().getCaste().getName());
						}
						if (admAppln.getCourse() != null) {
							mailTO.setCourse(admAppln.getCourse().getName());
						}
						if (admAppln.getCourseBySelectedCourseId() != null) {
							mailTO.setSelectedCourse(admAppln.getCourseBySelectedCourseId().getName());
						}
						if (admAppln.getCourse() != null
								&& admAppln.getCourse().getProgram() != null) {
							mailTO.setProgram(admAppln.getCourse().getProgram().getName());
						}
						mailTO.setApplicationNo(String.valueOf(admAppln.getApplnNo()));
						mailTO.setAcademicYear(String.valueOf(admAppln.getAppliedYear()));

						if (admAppln.getApplnDocs() != null) {
							Iterator<ApplnDoc> applnDocItr = admAppln.getApplnDocs()
									.iterator();

							while (applnDocItr.hasNext()) {
								ApplnDoc applnDoc = (ApplnDoc) applnDocItr.next();
								if (applnDoc.getIsPhoto() != null
										&& applnDoc.getIsPhoto()) {
									mailTO.setPhoto(applnDoc.getDocument());
								}
							}
						}

						Organisation organisation = OrganizationHandler.getInstance()
								.getRequiredFile();
						if (organisation != null) {
							mailTO.setLogo(organisation.getLogo());
						}
					}
					
					StringBuffer currentAddress = new StringBuffer();
					
					if (admAppln.getPersonalData().getCurrentAddressLine1() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCurrentAddressLine1());
						currentAddress.append(' ');
					}

					if (admAppln.getPersonalData().getCurrentAddressLine2() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCurrentAddressLine2());
						currentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getCityByCurrentAddressCityId() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCityByCurrentAddressCityId());
						currentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getStateByCurrentAddressStateId() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getStateByCurrentAddressStateId().getName());
						currentAddress.append(' ');
					} else if (admAppln.getPersonalData()
							.getCurrentAddressStateOthers() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCurrentAddressStateOthers());
						currentAddress.append(' ');
					}

					if (admAppln.getPersonalData()
							.getCountryByCurrentAddressCountryId() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCountryByCurrentAddressCountryId().getName());
						currentAddress.append(' ');
					} else if (admAppln.getPersonalData()
							.getCurrentAddressCountryOthers() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCurrentAddressCountryOthers());
						currentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getCurrentAddressZipCode() != null) {
						currentAddress.append(admAppln.getPersonalData()
								.getCurrentAddressZipCode());
						currentAddress.append(' ');
					}
					mailTO.setCurrentAddress(currentAddress.toString());
					
					StringBuffer permanentAddress = new StringBuffer();
					
					if (admAppln.getPersonalData().getParentAddressLine1() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getParentAddressLine1());
						permanentAddress.append(' ');
					}

					if (admAppln.getPersonalData().getParentAddressLine2() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getParentAddressLine2());
						permanentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getCityByPermanentAddressCityId() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getCityByPermanentAddressCityId());
						permanentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getStateByParentAddressStateId() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getStateByParentAddressStateId().getName());
						permanentAddress.append(' ');
					} else if (admAppln.getPersonalData()
							.getParentAddressStateOthers() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getParentAddressStateOthers());
						permanentAddress.append(' ');
					}

					if (admAppln.getPersonalData()
							.getCountryByPermanentAddressCountryId() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getCountryByPermanentAddressCountryId().getName());
						permanentAddress.append(' ');
					} else if (admAppln.getPersonalData()
							.getPermanentAddressCountryOthers() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getPermanentAddressCountryOthers());
						permanentAddress.append(' ');
					}
					if (admAppln.getPersonalData().getPermanentAddressZipCode() != null) {
						permanentAddress.append(admAppln.getPersonalData()
								.getPermanentAddressZipCode());
						permanentAddress.append(' ');
					}
					mailTO.setPermanentAddress(permanentAddress.toString());
	
					mailList.add(mailTO);
				}	
				
			}
			finalMeritListForm.setMailList(mailList);
			
		} catch (Exception e) {
			log.error("Error while initializing selected list"+e.getMessage() );
			String msg = super.handleApplicationException(e);
			finalMeritListForm.setErrorMessage(msg);
			finalMeritListForm.setErrorStack(e.getMessage());
			return mapping.findForward(CMSConstants.ERROR_PAGE);

		}
		log.info("exit of initSelectedList of FinalMeritListSearchAction class.");
		return mapping.findForward(CMSConstants.FINAL_MERIT_LIST_APPROVE);

	}
	
	
	/**
	 * 
	 * approves selected candidates from the list.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param form
	 *            The optional ActionForm bean for this request (if any)
	 * @param request
	 *            The HTTP request we are processing
	 * @param response
	 *            The HTTP response we are creating
	 * @return The forward to which control should be transferred,
	 * @throws Exception
	 *             if an exception occurs
	 */
	public ActionForward approveSelectedList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.info("entering into approveSelectedList of FinalMeritListSearchAction class.");
		ActionMessages messages = new ActionMessages();
		ActionMessage message = null;
		FinalMeritListForm finalMeritListForm = (FinalMeritListForm) form;
		setUserId(request, finalMeritListForm);
		if (finalMeritListForm.getSelectedCandidates() != null) {
			try {
				FinalMeritListSearchHandlar.getInstanse()
						.approveSelectedCandidates(
								finalMeritListForm.getSelectedCandidates(),
								true, finalMeritListForm.getUserId());
					FinalMeritListSearchHandlar.getInstanse().sendMailToApprovedStudent(finalMeritListForm,request);
					FinalMeritListSearchHandlar.getInstanse().sendSMSToApprovedStudent(finalMeritListForm, request);

				message = new ActionMessage(
						"knowledgepro.admission.approvedmeritlist");
				messages.add(CMSConstants.MESSAGES, message);
				saveMessages(request, messages);
				
			} catch (Exception e) {
				log.error("Error while approving selected list"+e.getMessage() );
				String msg = super.handleApplicationException(e);
				finalMeritListForm.setErrorMessage(msg);
				finalMeritListForm.setErrorStack(e.getMessage());
				setRequiredDatatoForm(finalMeritListForm, request);
				return mapping.findForward(CMSConstants.ERROR_PAGE);
			}

		}
//		try {
//			finalMeritListForm.setFinalMeritList(FinalMeritListSearchHandlar
//					.getInstanse().getFinalMeritListSearchResult(
//							finalMeritListForm, true, null));
//		} catch (Exception e) {
//			log.error("Error while setting the data"+e.getMessage() );
//			String msg = super.handleApplicationException(e);
//			finalMeritListForm.setErrorMessage(msg);
//			finalMeritListForm.setErrorStack(e.getMessage());
//			return mapping.findForward(CMSConstants.ERROR_PAGE);
//		}

//		finalMeritListForm.setSelectedCandidates(null);
//		setRequiredDatatoForm(finalMeritListForm, request);
		log.info("exit of removeFromSelectedList of FinalMeritListSearchAction class.");
		return mapping.findForward("approveList");

	}
	
	
	/**
	 * Set the required data to form.
	 * 
	 * @param finalMeritListForm
	 * @param request
	 * @throws Exception
	 */
	private void setRequiredDatatoForm(FinalMeritListForm finalMeritListForm,
			HttpServletRequest request) throws Exception {
		log.info("entering into setRequiredDatatoForm of FinalMeritListAction class");
		// Set Religion
		ReligionHandler religiHandler = ReligionHandler.getInstance();
		request.setAttribute("religionList", religiHandler.getReligion());

		// Set resident category
		ResidentCategoryHandler residentCategoryHandler = ResidentCategoryHandler
				.getInstance();
		request.setAttribute("residentCategoryList", residentCategoryHandler
				.getResidentCategory());

		List<UniversityTO> universityList = UniversityHandler.getInstance()
				.getUniversity();

		request.setAttribute("univercityList", universityList);

		// setting programList to Request
		List<ProgramTypeTO> programTypeList = ProgramTypeHandler.getInstance()
				.getProgramType();
		request.setAttribute("programTypeList", programTypeList);

		List<CasteTO> casteCategoryList = CasteHandler.getInstance()
				.getCastes();
		request.setAttribute("casteCategoryList", casteCategoryList);

		List<NationalityTO> countryList;
		try {
			countryList = FinalMeritListSearchHelper.getNationalities();
			request.setAttribute("countryList", countryList);
		} catch (Exception e) {
			log.error("error in setRequiredDatatoForm",e);
			throw new ApplicationException(e);

		}
		log.info("exit into setRequiredDatatoForm of FinalMeritListAction class");
	}
}
