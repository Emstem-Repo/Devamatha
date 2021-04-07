package com.kp.cms.helpers.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.kp.cms.bo.admin.AssignCertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateDetails;
import com.kp.cms.bo.admin.CertificateDetailsTemplate;
import com.kp.cms.bo.admin.CertificateOnlineStudentRequest;
import com.kp.cms.bo.admin.CertificateRequestMarksCardDetails;
import com.kp.cms.bo.admin.CertificateRequestPurpose;
import com.kp.cms.bo.admin.CertificateRequestPurposeDetails;
import com.kp.cms.bo.admin.GroupTemplate;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.employee.EmpIncentives;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.CertificateRequestOnlineForm;
import com.kp.cms.forms.admin.CommonTemplateForm;
import com.kp.cms.handlers.admin.CertificateDetailsHandler;
import com.kp.cms.handlers.admin.OrganizationHandler;
import com.kp.cms.handlers.admin.TemplateHandler;
import com.kp.cms.to.admin.AssignCertificateRequestPurposeTO;
import com.kp.cms.to.admin.CertificateDetailsTemplateTO;
import com.kp.cms.to.admin.CertificateDetailsTo;
import com.kp.cms.to.admin.CertificateRequestMarksCardTO;
import com.kp.cms.to.admin.CertificateRequestOnlineTO;
import com.kp.cms.to.employee.EmpIncentivesTO;
import com.kp.cms.transactions.admin.ICertificateRequestOnlineTransaction;
import com.kp.cms.transactionsimpl.admin.CertificateRequestOnlineImpl;
import com.kp.cms.utilities.CommonUtil;
import com.kp.cms.utilities.CurrentAcademicYear;

public class CertificateRequestOnlineHelper {
	
private static volatile CertificateRequestOnlineHelper instance=null;
	
private CertificateRequestOnlineHelper(){
	
}
/*ICertificateRequestOnlineTransaction txn;

private CertificateRequestOnlineHelper(){
	txn = new CertificateRequestOnlineImpl();}*/
		
	/**
	 * @return
	 */
	public static CertificateRequestOnlineHelper getInstance(){
		if(instance==null){
			instance=new CertificateRequestOnlineHelper();
		}
		return instance;
	}
	// certificate pending Request setting data to form
	
	public List<CertificateDetailsTo> convertCertificateTOtoBO(List<CertificateDetails> crlist) throws Exception {
		List<CertificateDetailsTo> crlistTos = new ArrayList<CertificateDetailsTo>();
		String name = "";
		if (crlist != null) {
			Iterator<CertificateDetails> stItr = crlist.iterator();
			while (stItr.hasNext()) {
				name = "";
				CertificateDetails cr = (CertificateDetails) stItr.next();
				CertificateDetailsTo crTo = new CertificateDetailsTo();
				crTo.setChecked(false);
				crTo.setTempChecked(false);
		
				if (cr.getId() > 0) {
					crTo.setId(cr.getId());
				}
				if (cr.getCertificateName() != null) {
					crTo.setCertificateName(cr.getCertificateName());
				}
				if (cr.getFees() != null) {

					crTo.setFees(String.valueOf(cr.getFees()));
					crTo.setOriginalFees(String.valueOf(cr.getFees()));
				}
				if (cr.getMarksCard() != null) {

					String Value = String.valueOf(cr.getMarksCard());
					if (Value.equalsIgnoreCase("true"))
						crTo.setMarksCard("true");
					else
						crTo.setMarksCard("false");
				}
				if (cr.getIsReasonRequired() != null) {

					String Value1 = String.valueOf(cr.getIsReasonRequired());
					if (Value1.equalsIgnoreCase("true")){
						crTo.setIsReasonRequired("true");
						crTo.setPurposeOrRemarksExist("true");
					}
					else
						crTo.setIsReasonRequired("false");
				}
				if (cr.getIsIdCard() != null) {

					String Value1 = String.valueOf(cr.getIsIdCard());
					if (Value1.equalsIgnoreCase("true"))
						crTo.setIsIdCard("true");
					else
						crTo.setIsIdCard("false");
				}
				
				if(cr.getCertTemplate() != null)
				{
					Set<CertificateDetailsTemplate> temp1 = cr.getCertTemplate();
					if (temp1 != null && !temp1.isEmpty()) {
						Iterator<CertificateDetailsTemplate> iterator = temp1.iterator();
						List<CertificateDetailsTemplateTO> tempTO = new ArrayList<CertificateDetailsTemplateTO>();

						while (iterator.hasNext()) {
							CertificateDetailsTemplate temp = iterator.next();
							if (temp != null) {
								CertificateDetailsTemplateTO assignTempTo = new CertificateDetailsTemplateTO();
								if (temp.getId() > 0) {
									assignTempTo.setId(temp.getId());
								}
								if (temp.getCertificateId()!=null && temp.getCertificateId().getId()>0) {
									CertificateDetails cp1=new CertificateDetails();
									cp1.setId(temp.getCertificateId().getId());
									assignTempTo.setCertificateId(cp1);
									assignTempTo.setTemplateDescription(temp.getTemplateDescription());
									assignTempTo.setTemplateName(temp.getTemplateName());
								}
								tempTO.add(assignTempTo);

							}
						}
						crTo.setCertTemplateAssignedTo(tempTO);
					}
				}
				
				if (cr.getAssignedCertPurpose() != null) {
					Set<AssignCertificateRequestPurpose> purpose = cr.getAssignedCertPurpose();
					if (purpose != null && !purpose.isEmpty()) {
						Iterator<AssignCertificateRequestPurpose> iterator = purpose.iterator();
						List<AssignCertificateRequestPurposeTO> assignTO = new ArrayList<AssignCertificateRequestPurposeTO>();

						while (iterator.hasNext()) {
							AssignCertificateRequestPurpose pur = iterator.next();
							if (pur != null) {
								AssignCertificateRequestPurposeTO assignPurposeTo = new AssignCertificateRequestPurposeTO();
								if (pur.getId() > 0) {
									assignPurposeTo.setId(pur.getId());
								}
								if (pur.getCertificatePurposeId()!=null && pur.getCertificatePurposeId().getId()>0) {
									CertificateRequestPurpose cp=new CertificateRequestPurpose();
									cp.setId(pur.getCertificatePurposeId().getId());
									assignPurposeTo.setCertificatePurposeId(cp);
									assignPurposeTo.setPurposeName(pur.getCertificatePurposeId().getPurposeName());
								}
								assignTO.add(assignPurposeTo);

							}
						}
						if(assignTO.isEmpty()){
							crTo.setPurposeExist("false");
						}else{
							crTo.setPurposeExist("true");
							crTo.setPurposeOrRemarksExist("true");
						}
						crTo.setAssignPurposeTo(assignTO);
					}
				}
						
				crlistTos.add(crTo);
			}
		}
		return crlistTos;
	}
	public List<CertificateRequestOnlineTO> convertStudentTOtoBO (List<CertificateOnlineStudentRequest> crlist) throws Exception {
		List<CertificateRequestOnlineTO> crlistTos = new ArrayList<CertificateRequestOnlineTO>();
		String name = "";
		if (crlist != null) {
			Iterator<CertificateOnlineStudentRequest> stItr = crlist.iterator();
			while (stItr.hasNext()) {
				name = "";
				String purposeName="";
				CertificateOnlineStudentRequest cr = (CertificateOnlineStudentRequest) stItr.next();
				
				CertificateRequestOnlineTO crTo = new CertificateRequestOnlineTO();
				if(cr.getId()>0)
				{
					crTo.setId(String.valueOf(cr.getId()));
				}
				if(cr.getCertificateDetailsId()!= null) {
					
					if(cr.getCertificateDetailsId().getCertificateName()!= null && !cr.getCertificateDetailsId().getCertificateName().isEmpty()) {
						if(cr.getCertReqPurpose()!=null && !cr.getCertReqPurpose().isEmpty())
						{
							Iterator<CertificateRequestPurposeDetails> prName= cr.getCertReqPurpose().iterator();
							while (prName.hasNext()) {
								CertificateRequestPurposeDetails acp = (CertificateRequestPurposeDetails) prName.next();
								if(purposeName == null || purposeName.isEmpty())
								{
									purposeName=acp.getPurposeId().getPurposeName();
								}
								else if(!purposeName.isEmpty())
								{
									purposeName=purposeName+", "+acp.getPurposeId().getPurposeName();
								}
							}
						}
						if(purposeName!=null && !purposeName.isEmpty())
							crTo.setCertificateName(cr.getCertificateDetailsId().getCertificateName()+":- "+purposeName);
						else
							crTo.setCertificateName(cr.getCertificateDetailsId().getCertificateName());
						crTo.setCertificateId(String.valueOf(cr.getCertificateDetailsId().getId()));
		    String Value = String.valueOf(cr.getCertificateDetailsId().getMarksCard());
						if (Value.equals("true"))
							crTo.setMarksCard("true");
						else
							crTo.setMarksCard("false");
				
			   String Value1 = String.valueOf(cr.getCertificateDetailsId().getIsReasonRequired());
						if (Value1.equals("true")){
							crTo.setIsReasonRequired("true");
							crTo.setStudentRemarks(cr.getStudentRemarks());
							}
						else
							crTo.setIsReasonRequired("false");
					}
				}
				if(cr.getIsCompleted()!=null && cr.getIsCompleted())
				{
					crTo.setIsCompleted(cr.getIsCompleted());
					crTo.setTempCompletedChecked(true);
						if(cr.getCompletedDate()!=null){
							crTo.setCompletedDate(CommonUtil.ConvertStringToDateFormat(cr.getCompletedDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
						}
					if(cr.getIsIssued()!=null && cr.getIsIssued())
					{
						crTo.setIsIssued(true);
						crTo.setTempIssuedChecked(true);
						if(cr.getIssuedDate()!=null)
						crTo.setIssuedDate(CommonUtil.ConvertStringToDateFormat(cr.getIssuedDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
					}
				}
				
				if(cr.getIsRejected()!=null && cr.getIsRejected())
				{
					crTo.setRejected(cr.getIsRejected());
					if(cr.getRejectDate()!=null)
					crTo.setRejectDate(CommonUtil.ConvertStringToDateFormat(cr.getRejectDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
					if(cr.getRejectReason()!=null && !cr.getRejectReason().isEmpty())
					crTo.setRejectReason(cr.getRejectReason());
				}
				else
				{
					crTo.setRejected(false);
				}
				if(cr.getAdminRemarks()!=null && !cr.getAdminRemarks().isEmpty())
				{
					crTo.setAdminRemarks(cr.getAdminRemarks());
				}
				if(cr.getAppliedDate()!=null)
				{
					crTo.setAppliedDate(CommonUtil.ConvertStringToDateFormat(cr.getAppliedDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				if(cr.getStudentId()!= null) 
				{
					String Name="";
				
					if(cr.getStudentId().getAdmAppln().getPersonalData().getFirstName()!= null && !cr.getStudentId().getAdmAppln().getPersonalData().getFirstName().isEmpty())
						Name=cr.getStudentId().getAdmAppln().getPersonalData().getFirstName();
					if(cr.getStudentId().getAdmAppln().getPersonalData().getMiddleName()!= null && !cr.getStudentId().getAdmAppln().getPersonalData().getMiddleName().isEmpty()) 
						Name=Name + " " + cr.getStudentId().getAdmAppln().getPersonalData().getMiddleName();
				    if(cr.getStudentId().getAdmAppln().getPersonalData().getLastName()!= null && !cr.getStudentId().getAdmAppln().getPersonalData().getLastName().isEmpty()) 	
						Name=Name + " " + cr.getStudentId().getAdmAppln().getPersonalData().getLastName();
					crTo.setStudentName(Name);
					crTo.setStudentId(String.valueOf(cr.getStudentId().getId()));
					
					if(cr.getStudentId().getRegisterNo()!= null && !cr.getStudentId().getRegisterNo().isEmpty())
					crTo.setRegisterNo(cr.getStudentId().getRegisterNo());
					if(cr.getStudentId().getClassSchemewise().getClasses().getName()!=null && !cr.getStudentId().getClassSchemewise().getClasses().getName().isEmpty())	
						crTo.setClassName(cr.getStudentId().getClassSchemewise().getClasses().getName());
				}
				if(cr.getMarksCardReq()!=null && !cr.getMarksCardReq().isEmpty()){	
					List<CertificateRequestMarksCardTO> marksTos = new ArrayList<CertificateRequestMarksCardTO>();
					Iterator<CertificateRequestMarksCardDetails> mkItr = cr.getMarksCardReq().iterator();
					while (mkItr.hasNext()) {
					CertificateRequestMarksCardDetails mk = (CertificateRequestMarksCardDetails) mkItr.next();
					CertificateRequestMarksCardTO mto = new CertificateRequestMarksCardTO();
					if(cr.getCertificateDetailsId()!= null) {
						mto.setCertDetailsId(cr.getCertificateDetailsId().getId());
						if(mk.getId()>0)
						{
							mto.setId(mk.getId());
						}
						mto.setSemester(String.valueOf(mk.getSemester()));
						mto.setYear(String.valueOf(mk.getYear()));
						mto.setType(mk.getType());
						mto.setMonth(mk.getMonth());
				}
				marksTos.add(mto);
			}
			crTo.setMarksCardTo(marksTos);	
		}
		crlistTos.add(crTo);
	}
}
return crlistTos;
}
	
	public List<CertificateOnlineStudentRequest> convertFormToBo(CertificateRequestOnlineForm crForm) throws Exception {
		List<CertificateOnlineStudentRequest> boList=new ArrayList<CertificateOnlineStudentRequest>();
		if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){	
		if(crForm.getIsReject().equalsIgnoreCase("true"))
			{
			Iterator<CertificateRequestOnlineTO> itr=crForm.getStudentToList().iterator();
			while (itr.hasNext()) {
		    CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
			CertificateOnlineStudentRequest cert=new CertificateOnlineStudentRequest();
			if(to.getId()!=null && Integer.parseInt(to.getId())>0){
				if(crForm.getRejectId().equals(to.getId()))
				{
				cert.setId(Integer.parseInt(to.getId()));
				to.setRejected(true);
				if(to.getCertificateId()!=null && Integer.parseInt(to.getCertificateId())>0)
				{
					CertificateDetails cd= new CertificateDetails();
					cd.setId(Integer.parseInt(to.getCertificateId()));
					cert.setCertificateDetailsId(cd);
				}
				if(to.getStudentId()!=null && Integer.parseInt(to.getStudentId())>0)
				{
				Student student = new Student();
				student.setId(Integer.parseInt(to.getStudentId()));
				cert.setStudentId(student);
				}
				if(to.getMarksCard().equalsIgnoreCase("true"))
				{
					
				to.getMarksCardTo();
				Set<CertificateRequestMarksCardDetails> marksCardDetails= getMarksCardObjects(to.getMarksCardTo(),crForm);
				cert.setMarksCardReq(marksCardDetails);
				}
				cert.setRejectReason(crForm.getRejectReason());
				cert.setRejectDate(new Date());
				cert.setIsRejected(to.isRejected());
				cert.setStudentRemarks(to.getStudentRemarks());
				cert.setAdminRemarks(to.getAdminRemarks());
				cert.setIsCompleted(false);
				if(to.getCompletedDate()!=null && !to.getCompletedDate().isEmpty())
					cert.setCompletedDate(CommonUtil.ConvertStringToDate(to.getCompletedDate()));
				cert.setCreatedBy(crForm.getUserId());
				cert.setCreatedDate(new Date());
				cert.setModifiedBy(crForm.getUserId());
				cert.setLastModifiedDate(new Date());
				if(to.getAppliedDate()!=null && !to.getAppliedDate().isEmpty())
					cert.setAppliedDate(CommonUtil.ConvertStringToDate(to.getAppliedDate()));
				cert.setIsIssued(false);
				if(to.getIssuedDate()!=null && !to.getIssuedDate().isEmpty())
					cert.setIssuedDate(CommonUtil.ConvertStringToDate(to.getIssuedDate()));
				cert.setIsActive(true);
				boList.add(cert);
				}
			}
			}
			}
			else
			{
			Iterator<CertificateRequestOnlineTO> itr=crForm.getStudentToList().iterator();
			while (itr.hasNext()) {
			CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
			CertificateOnlineStudentRequest cert=new CertificateOnlineStudentRequest();
			if(to.getIsCompleted()!=null && to.getIsCompleted())
			{
			if(to.getId()!=null && Integer.parseInt(to.getId())>0)
			{
				cert.setId(Integer.parseInt(to.getId()));
			}
			if(to.getCertificateId()!=null && Integer.parseInt(to.getCertificateId())>0)
			{
				CertificateDetails cd= new CertificateDetails();
				cd.setId(Integer.parseInt(to.getCertificateId()));
				cert.setCertificateDetailsId(cd);
			}
			if(to.getStudentId()!=null && Integer.parseInt(to.getStudentId())>0)
			{
			Student student = new Student();
			student.setId(Integer.parseInt(to.getStudentId()));
			cert.setStudentId(student);
			}
			if(to.getMarksCard().equalsIgnoreCase("true"))
			{
			to.getMarksCardTo();
			Set<CertificateRequestMarksCardDetails> marksCardDetails= getMarksCardObjects(to.getMarksCardTo(),crForm);
			cert.setMarksCardReq(marksCardDetails);
			}
			cert.setIsRejected(false);
			cert.setRejectReason(to.getRejectReason());
			if(to.getRejectDate()!=null && !to.getRejectDate().isEmpty())
				cert.setRejectDate(CommonUtil.ConvertStringToDate(to.getRejectDate()));
			cert.setIsCompleted(to.getIsCompleted());
			cert.setCompletedDate(new Date());
			cert.setStudentRemarks(to.getStudentRemarks());
			cert.setAdminRemarks(to.getAdminRemarks());
			cert.setCreatedBy(crForm.getUserId());
			cert.setCreatedDate(new Date());
			cert.setModifiedBy(crForm.getUserId());
			cert.setLastModifiedDate(new Date());
			if(to.getAppliedDate()!=null && !to.getAppliedDate().isEmpty())
				cert.setAppliedDate(CommonUtil.ConvertStringToDate(to.getAppliedDate()));
			cert.setIsActive(true);
			if(to.getIsIssued()!=null && to.getIsIssued())
				cert.setIsIssued(to.getIsIssued());
			else
				cert.setIsIssued(false);
			if(to.getIssuedDate()!=null && !to.getIssuedDate().isEmpty()){
				cert.setIssuedDate(CommonUtil.ConvertStringToDate(to.getIssuedDate()));}
			else{
				if(to.getIsIssued()!=null && to.getIsIssued())
					cert.setIssuedDate(new Date());
			}
			boList.add(cert);
			crForm.setIsReject("false");
			}
		  }
		}
		}
		return boList;
	}
	
	public List<CertificateOnlineStudentRequest> convertFormToBoRemarks(CertificateRequestOnlineForm crForm) throws Exception {
		List<CertificateOnlineStudentRequest> boList=new ArrayList<CertificateOnlineStudentRequest>();
		if(crForm.getStudentToList()!=null && !crForm.getStudentToList().isEmpty()){	
		if((crForm.getRemarkId()!=null && !crForm.getRemarkId().isEmpty())||(crForm.getAdminRemarks()!=null && !crForm.getAdminRemarks().isEmpty()))
			{
			Iterator<CertificateRequestOnlineTO> itr=crForm.getStudentToList().iterator();
			while (itr.hasNext()) {
		    CertificateRequestOnlineTO to = (CertificateRequestOnlineTO) itr.next();
			CertificateOnlineStudentRequest cert=new CertificateOnlineStudentRequest();
			if(to.getId()!=null && Integer.parseInt(to.getId())>0){
				if(crForm.getRemarkId().equals(to.getId()))
				{
				cert.setId(Integer.parseInt(to.getId()));
				if(to.getCertificateId()!=null && Integer.parseInt(to.getCertificateId())>0)
				{
					CertificateDetails cd= new CertificateDetails();
					cd.setId(Integer.parseInt(to.getCertificateId()));
					cert.setCertificateDetailsId(cd);
				}
				if(to.getStudentId()!=null && Integer.parseInt(to.getStudentId())>0)
				{
				Student student = new Student();
				student.setId(Integer.parseInt(to.getStudentId()));
				cert.setStudentId(student);
				}
				if(to.getMarksCard().equalsIgnoreCase("true"))
				{
					
				to.getMarksCardTo();
				Set<CertificateRequestMarksCardDetails> marksCardDetails= getMarksCardObjects(to.getMarksCardTo(),crForm);
				cert.setMarksCardReq(marksCardDetails);
				}
				cert.setRejectReason(crForm.getRejectReason());
				cert.setRejectDate(new Date());
				if(to.isRejected())
					cert.setIsRejected(to.isRejected());
				else
					cert.setIsRejected(false);
				cert.setStudentRemarks(to.getStudentRemarks());
				if(crForm.getAdminRemarks()!=null && !crForm.getAdminRemarks().isEmpty())
				cert.setAdminRemarks(crForm.getAdminRemarks());
				if(to.getIsCompleted()!=null && to.getIsCompleted())
					cert.setIsCompleted(to.getIsCompleted());
				else
					cert.setIsCompleted(false);
				cert.setCompletedDate(CommonUtil.ConvertStringToDate(to.getCompletedDate()));
				cert.setCreatedBy(crForm.getUserId());
				cert.setCreatedDate(new Date());
				cert.setModifiedBy(crForm.getUserId());
				cert.setLastModifiedDate(new Date());
				cert.setAppliedDate(CommonUtil.ConvertStringToDate(to.getAppliedDate()));
				cert.setIsActive(true);
				if(to.getIsIssued()!=null && to.getIsIssued())
					cert.setIsIssued(to.getIsIssued());
				else
					cert.setIsIssued(false);
				cert.setIssuedDate(CommonUtil.ConvertStringToDate(to.getIssuedDate()));
				boList.add(cert);
				crForm.setIsReject("false");
				}
			}
			}
			}
		}
		return boList;
		
	}
	
	private Set<CertificateRequestMarksCardDetails> getMarksCardObjects(List<CertificateRequestMarksCardTO> certTo, CertificateRequestOnlineForm crForm) {
		Set<CertificateRequestMarksCardDetails> mk = new HashSet<CertificateRequestMarksCardDetails>();
		if (certTo != null)
		 {
			Iterator<CertificateRequestMarksCardTO> itr = certTo.iterator();
			while (itr.hasNext()) {
				CertificateRequestMarksCardTO to = (CertificateRequestMarksCardTO) itr.next();
				CertificateRequestMarksCardDetails fin = new CertificateRequestMarksCardDetails();
				if (to.getSemester() != null && !to.getSemester().isEmpty()	|| to.getMonth() != null && !to.getMonth().isEmpty()
						|| to.getType() != null && !to.getType().isEmpty() || to.getYear()!=null && to.getYear().isEmpty()) {
					if (to.getId() > 0) {
						fin.setId(to.getId());
					}
					fin.setCreatedBy(crForm.getUserId());
					fin.setCreatedDate(new Date());
					fin.setModifiedBy(crForm.getUserId());
					fin.setLastModifiedDate(new Date());
					fin.setSemester(Integer.parseInt(to.getSemester()));
					fin.setMonth(to.getMonth());
					fin.setType(to.getType());
					fin.setYear(Integer.parseInt(to.getYear()));
					mk.add(fin);
				}
			}
		}
		return mk;
	}
	
 public List<String> copyBosToList(Student student, List<CertificateDetailsTemplate> tList, HttpServletRequest request, CertificateRequestOnlineForm commonTemplateForm) throws Exception{
		Iterator<CertificateDetailsTemplate> iterator = tList.iterator();
		CertificateDetailsTemplate temp;
		byte[] logo = null;
		byte[] logo1 = null;
		ArrayList<String> messageList = new ArrayList<String>();
		String logoPath = "";
		String logoPath1= "";
		HttpSession session = request.getSession(false);
		Organisation organisation = OrganizationHandler.getInstance().getRequiredFile();
		if (organisation != null) {
			logo = organisation.getLogo();
			logo1 = organisation.getLogo1();
		}
		if (session != null) {
			session.setAttribute("LogoBytes", logo);
			session.setAttribute("LogoBytes1", logo1);
		}
		
		logoPath = request.getContextPath();
		logoPath = "<img src="
				+ logoPath
				+ "/LogoServlet?count=1 alt='Logo not available' width='210' height='100' >";
		
		logoPath1 = request.getContextPath();
		logoPath1 = "<img src="
				+ logoPath1
				+ "/LogoServlet?count=2 alt='Logo not available' width='210' height='100' >";
		
			while (iterator.hasNext()) {
				temp = (CertificateDetailsTemplate) iterator.next();
				if(temp.getTemplateName()!=null && !temp.getTemplateName().isEmpty()){
					commonTemplateForm.setTemplateName(temp.getTemplateName());
				}
				List<CertificateDetailsTemplate> list= CertificateDetailsHandler.getInstance().getDuplicateCheckList(commonTemplateForm.getTemplateName());
				
				if(list != null && !list.isEmpty()) {
					String desc ="";
					if(list.get(0)!=null && list.get(0).getTemplateDescription()!=null){
						desc = list.get(0).getTemplateDescription();
					}
				String message = desc;
				commonTemplateForm.setDate(CommonUtil.getTodayDate());
				Date date = CommonUtil.ConvertStringToDate(commonTemplateForm.getDate());
				SimpleDateFormat formatter= new SimpleDateFormat("MMMMMMMMM dd, yyyy");
					  String dateNow = formatter.format(date);
				message =message.replace(CMSConstants.TEMPLATE_DATE,dateNow);
				message=message.replace(CMSConstants.TEMPLATE_CERTIFICATE_NAME, commonTemplateForm.getTemplateName());
				message = message.replace(CMSConstants.TEMPLATE_LOGO, logoPath);
				message = message.replace(CMSConstants.TEMPLATE_LOGO1, logoPath1);
				if(student!=null)
				{
				if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
					message =message.replace(CMSConstants.TEMPLATE_CANDIDATE_NAME, student.getAdmAppln().getPersonalData().getFirstName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getFatherName() != null){
					message = message.replace(CMSConstants.TEMPLATE_FATHER_NAME, student.getAdmAppln().getPersonalData().getFatherName());
				}
				if(student.getRegisterNo() != null && !student.getRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,student.getRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_DOB_REGISTERNO ,"---");
				}
				if(student.getExamRegisterNo() != null && !student.getExamRegisterNo().isEmpty()){
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,student.getExamRegisterNo());
				}else{
					message = message.replace(CMSConstants.TEMPLATE_EXAM_REG_NO ,"---");
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "S/o");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_SONOF, "D/o");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null && student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream() != null){
					message = message.replace(CMSConstants.TEMPLATE_STREAM, student.getAdmAppln().getCourseBySelectedCourseId().getProgram().getStream());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_CLASS, student.getClassSchemewise().getClasses().getName());
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getCourseBySelectedCourseId() != null && student.getAdmAppln().getCourseBySelectedCourseId().getName() != null){
					message = message.replace(CMSConstants.TEMPLATE_COURSE, student.getAdmAppln().getCourseBySelectedCourseId().getName());
				}
				if(student.getClassSchemewise() != null && student.getClassSchemewise().getClasses() != null && student.getClassSchemewise().getClasses().getTermNumber() != null){
					if( student.getClassSchemewise().getClasses().getTermNumber()== 1){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 2){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"one");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 3){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 4){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"two");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 5){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"three");
					}
					if( student.getClassSchemewise().getClasses().getTermNumber()== 6){
						message = message.replace(CMSConstants.TEMPLATE_SEMYEAR,"three");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "He");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER, "She");
					}
				}
				if(student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getGender() != null){
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("Male")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "his");
					}
					if(student.getAdmAppln().getPersonalData().getGender().equalsIgnoreCase("FeMale")){
						message = message.replace(CMSConstants.TEMPLATE_GENDER2, "her");
					}
				}
				StringBuffer address = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine1().trim().isEmpty()){
						address.append(student.getAdmAppln().getPersonalData().getPermanentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getPermanentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getPermanentAddressLine2().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getPermanentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCityByPermanentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName().trim().isEmpty()){
						address.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getStateByPermanentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers()!=null){
						address.append( student.getAdmAppln().getPersonalData().getPermanentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName().trim().isEmpty()){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCountryByPermanentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers()!=null){
						address.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getPermanentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_PERMANENT_ADDRESS, address.toString());
				}
				StringBuffer currentAddress = new StringBuffer();
				if(student.getAdmAppln()!= null && student.getAdmAppln().getPersonalData()!= null){
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine1()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine1().trim().isEmpty()){
						currentAddress.append(student.getAdmAppln().getPersonalData().getCurrentAddressLine1() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCurrentAddressLine2()!= null && !student.getAdmAppln().getPersonalData().getCurrentAddressLine2().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressLine2() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId()!= null && !student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCityByCurrentAddressCityId() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId()!= null && student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName()!= null && !student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName().trim().isEmpty()){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getStateByCurrentAddressStateId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers()!=null){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressStateOthers() + "<BR>");
					}
					if(student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId()!= null && student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName()!= null && !student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName().trim().isEmpty()){
						currentAddress.append( "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+student.getAdmAppln().getPersonalData().getCountryByCurrentAddressCountryId().getName() + "<BR>");
					}else if(student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers()!=null){
						currentAddress.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+ student.getAdmAppln().getPersonalData().getCurrentAddressCountryOthers() + "<BR>");
					}
					message = message.replace(CMSConstants.TEMPLATE_CURRENT_ADDRESS, currentAddress.toString());
				}
				//if(commonTemplateForm.getAcademicYear() != null && !commonTemplateForm.getAcademicYear().isEmpty()){
					//String acedamicYear = commonTemplateForm.getAcademicYear();
				    int acedamicYear = CurrentAcademicYear.getInstance().getAcademicyear();
					String result="";
					String nextYear=""+String.valueOf(acedamicYear+1);
					result=acedamicYear+"-"+nextYear.substring(0);
					message =message.replace(CMSConstants.TEMPLATE_ACADEMICYEAR, result);
				
				}
				if(student!=null && student.getAdmAppln() != null && student.getAdmAppln().getPersonalData() != null && student.getAdmAppln().getPersonalData().getDateOfBirth() != null){
					Date dateOfBirth= student.getAdmAppln().getPersonalData().getDateOfBirth();
					String dob = new SimpleDateFormat("dd/MM/yyyy").format(dateOfBirth);
					message = message.replace(CMSConstants.TEMPLATE_DOB, dob);
				}
				
				messageList.add(message);
			   }
		   }
		return messageList;
	}
 
 
 public List<CertificateRequestOnlineTO> convertStatusTOtoBO (List<CertificateOnlineStudentRequest> crlist) throws Exception {
		List<CertificateRequestOnlineTO> crlistTos = new ArrayList<CertificateRequestOnlineTO>();
		String name = "";
		if (crlist != null) {
			Iterator<CertificateOnlineStudentRequest> stItr = crlist.iterator();
			while (stItr.hasNext()) {
				name = "";
				String purposeName="";
				CertificateOnlineStudentRequest cr = (CertificateOnlineStudentRequest) stItr.next();
				
				CertificateRequestOnlineTO crTo = new CertificateRequestOnlineTO();
				if(cr.getId()>0)
				{
					crTo.setId(String.valueOf(cr.getId()));
				}
				if(cr.getCertificateDetailsId()!= null) {
					if(cr.getCertificateDetailsId().getId()>0)
						crTo.setCertificateId(String.valueOf(cr.getCertificateDetailsId().getId()));
					
					if(cr.getCertificateDetailsId().getCertificateName()!= null && !cr.getCertificateDetailsId().getCertificateName().isEmpty()) {
						if(cr.getCertReqPurpose()!=null && !cr.getCertReqPurpose().isEmpty())
						{
							Iterator<CertificateRequestPurposeDetails> prName= cr.getCertReqPurpose().iterator();
							while (prName.hasNext()) {
								CertificateRequestPurposeDetails acp = (CertificateRequestPurposeDetails) prName.next();
								if(purposeName == null || purposeName.isEmpty())
								{
									purposeName=acp.getPurposeId().getPurposeName();
								}
								else if(!purposeName.isEmpty())
								{
									purposeName=purposeName+", "+acp.getPurposeId().getPurposeName();
								}
							}
						}
						if(purposeName!=null && !purposeName.isEmpty())
							crTo.setCertificateName(cr.getCertificateDetailsId().getCertificateName()+":- "+purposeName);
						else
							crTo.setCertificateName(cr.getCertificateDetailsId().getCertificateName());
						crTo.setCertificateId(String.valueOf(cr.getCertificateDetailsId().getId()));
					String Value = String.valueOf(cr.getCertificateDetailsId().getMarksCard());
						if (Value.equals("true"))
							crTo.setMarksCard("true");
						else
							crTo.setMarksCard("false");
					}
					if(cr.getCertificateDetailsId().getDescription()!=null)
					
						crTo.setCertificateDescription("true");
					
					else
						crTo.setCertificateDescription("false");
				}
				if((cr.getIsIssued()!=null && cr.getIsIssued()) && (cr.getIsCompleted()!=null && cr.getIsCompleted()) && (cr.getIsRejected()==null || !cr.getIsRejected()))
				{
					crTo.setCertificateStatus("Completed and Issued");
				}
				else if((cr.getIsCompleted()!=null && cr.getIsCompleted()) && (cr.getIsRejected()==null || !cr.getIsRejected()))
				{
					crTo.setCertificateStatus("Completed");
				}
				else if((cr.getIsCompleted()==null || !cr.getIsCompleted()) && (cr.getIsRejected()!=null && cr.getIsRejected()))
				{
					crTo.setCertificateStatus("Rejected" +":-"+ cr.getRejectReason());
				}
				else
				{
					crTo.setCertificateStatus("Pending");
				}
				if(cr.getAppliedDate()!=null)
				{
					crTo.setAppliedDate(CommonUtil.ConvertStringToDateFormat(cr.getAppliedDate().toString(),"yyyy-mm-dd", "dd/mm/yyyy"));
				}
				
				if(cr.getMarksCardReq()!=null && !cr.getMarksCardReq().isEmpty()){	
					List<CertificateRequestMarksCardTO> marksTos = new ArrayList<CertificateRequestMarksCardTO>();
					Iterator<CertificateRequestMarksCardDetails> mkItr = cr.getMarksCardReq().iterator();
					while (mkItr.hasNext()) {
					CertificateRequestMarksCardDetails mk = (CertificateRequestMarksCardDetails) mkItr.next();
					CertificateRequestMarksCardTO mto = new CertificateRequestMarksCardTO();
					if(cr.getCertificateDetailsId()!= null) {
						mto.setCertDetailsId(cr.getCertificateDetailsId().getId());
						if(mk.getId()>0)
						{
							mto.setId(mk.getId());
						}
						mto.setSemester(String.valueOf(mk.getSemester()));
						mto.setYear(String.valueOf(mk.getYear()));
						mto.setType(mk.getType());
						mto.setMonth(mk.getMonth());
				}
				marksTos.add(mto);
				
			}
			crTo.setMarksCardTo(marksTos);	
		}
		crlistTos.add(crTo);
	}
}
return crlistTos;
}
}
