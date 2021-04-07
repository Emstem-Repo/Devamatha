package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.kp.cms.bo.admin.ApplnDoc;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.hostel.ViewRequisitionsForm;
import com.kp.cms.to.hostel.RequisitionsTo;
import com.kp.cms.to.hostel.VRequisitionsTO;
import com.kp.cms.utilities.CommonUtil;

public class RequisitionHelper {

	public static String buildQuery(ViewRequisitionsForm viewRequisitionsForm) {
			String searchCriteria = "";
			if(viewRequisitionsForm.getViewRequisitionsOf().equalsIgnoreCase("student"))
				searchCriteria = "select hl.hlRoomTypeByHlApprovedRoomTypeId.name,student.registerNo,hl.admAppln.personalData.firstName,hl.admAppln.personalData.middleName,hl.admAppln.personalData.lastName,hl.appliedDate,hl.hlStatus.statusType,hl.id from HlApplicationForm hl inner join  hl.admAppln.students student";	
			else
				searchCriteria="select hl.hlRoomTypeByHlApprovedRoomTypeId.name,hl.employee.code,hl.employee.firstName,hl.employee.middleName,hl.employee.lastName,hl.appliedDate,hl.hlStatus.statusType,hl.id from HlApplicationForm hl";
		
			if(viewRequisitionsForm.getHostelId() != null && viewRequisitionsForm.getHostelId().length() > 0 )
			 {
				String id1 = " where hl.isActive=1 and hl.hlHostelByHlApprovedHostelId.id ='"+viewRequisitionsForm.getHostelId()+ "'";
				searchCriteria = searchCriteria + id1;
			}
			if(viewRequisitionsForm.getRoomtype()!=null && viewRequisitionsForm.getRoomtype().length() > 0 )
			{
					String id1 = " and hl.hlRoomTypeByHlApprovedRoomTypeId.id ='"+viewRequisitionsForm.getRoomtype()+ "'";
					searchCriteria = searchCriteria + id1;
			}
			if(viewRequisitionsForm.getStartDate() != null && viewRequisitionsForm.getStartDate().length() > 0 
					&& viewRequisitionsForm.getEndDate() != null && viewRequisitionsForm.getEndDate().length() >0){
							String date = " and hl.appliedDate between '"+ CommonUtil.ConvertStringToSQLDate(viewRequisitionsForm.getStartDate())+"'" + " and '"+CommonUtil.ConvertStringToSQLDate(viewRequisitionsForm.getEndDate())+"'";
							searchCriteria = searchCriteria + date;
			}
			else if (viewRequisitionsForm.getStartDate()!=null && viewRequisitionsForm.getStartDate().trim().length() > 0) {
				String sdate = " and hl.appliedDate >= "+
						"'"+ viewRequisitionsForm.getStartDate()+"'";
				searchCriteria = searchCriteria + sdate;
			}
			else if (viewRequisitionsForm.getEndDate()!=null && viewRequisitionsForm.getEndDate().trim().length() > 0) {
				String enddate = " and hl.appliedDate <= "+
						"'"+ viewRequisitionsForm.getEndDate()+"'";
				searchCriteria = searchCriteria + enddate;
			}
			if(viewRequisitionsForm.getStatus() != null &&  viewRequisitionsForm.getStatus().equals(CMSConstants.HOSTEL_APPROVED))
			{
				String approve=" and  hl.hlStatus.id = 1";
				searchCriteria = searchCriteria +approve;
			}
			else if(viewRequisitionsForm.getStatus() != null &&  viewRequisitionsForm.getStatus().equalsIgnoreCase("Pending"))
			{
				String approve=" and  hl.hlStatus.id = 9";
				searchCriteria = searchCriteria +approve;
			}
		
			else if(viewRequisitionsForm.getStatus() != null &&  viewRequisitionsForm.getStatus().equalsIgnoreCase("Rejected"))
			{
				String approve=" and  hl.hlStatus.id = 10";
				searchCriteria = searchCriteria +approve;
			}else{
				String approve=" and  hl.hlStatus.id in (1,4,9,10,11)";
				searchCriteria = searchCriteria +approve;
			}
				
			return searchCriteria;
		}
		
		
		

	public static List<VRequisitionsTO> copyRequisitionBOToTO(List<Object[]> requisitionBO,	ViewRequisitionsForm viewRequisitionsForm) {
	
		List<VRequisitionsTO> vRequisitionsList = new ArrayList<VRequisitionsTO>();
		VRequisitionsTO vRequisitionsTO; 
		
		if(requisitionBO!=null && !requisitionBO.isEmpty()){	
			Iterator<Object[]> requisitionsIterator = requisitionBO.iterator();
				while (requisitionsIterator.hasNext()) {
					Object[] obj = (Object[]) requisitionsIterator.next();
					vRequisitionsTO = new VRequisitionsTO();
					if(obj[0] != null){
						vRequisitionsTO.setRoomType((obj[0].toString()));
					}
		
					if(obj[1] != null){
						vRequisitionsTO.setApplno(obj[1].toString());
					}
					String name="";
					if(obj[2] != null){
						name=name+obj[2].toString();
					}
					if(obj[3] != null){
						name=name+" "+obj[3].toString();
					}
					if(obj[4] != null){
						name=name+" "+obj[4].toString();
					}
					vRequisitionsTO.setStudentName(name);
					if(obj[6] != null){
						vRequisitionsTO.setRoomStatus(obj[6].toString());
						}
					if(obj[7] != null){
						vRequisitionsTO.setId(Integer.parseInt(obj[7].toString()));
						}
					
					vRequisitionsList.add(vRequisitionsTO);
				
				}
		
		}
		Collections.sort(vRequisitionsList);
		return vRequisitionsList;
	}



// query to show the application form
	public static String getQuery(int hlAppId,ViewRequisitionsForm viewRequisitionsForm) {
	 
		String searchCriteria = "from HlApplicationForm hl " +
				"where hl.isActive=1 and hl.id='"+hlAppId+"'";
				return searchCriteria;
	}
	
	public static RequisitionsTo copyRequisitionBOToTO(	List<HlApplicationForm> requisitionBO,HttpServletRequest request) {
	RequisitionsTo requisitionsTo=null; 
	
	if(requisitionBO!=null && !requisitionBO.isEmpty()){	
		Iterator<HlApplicationForm> requisitionsIterator = requisitionBO.iterator();
			while (requisitionsIterator.hasNext()) {
				String name="";
				HlApplicationForm hlApplicationForm = (HlApplicationForm) requisitionsIterator.next();
				requisitionsTo = new RequisitionsTo();
				if(hlApplicationForm.getHlHostelByHlApprovedHostelId() != null){
					requisitionsTo.setHostelName(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
					requisitionsTo.setHostelId(hlApplicationForm.getHlHostelByHlApprovedHostelId().getId());
				}
	
				if(hlApplicationForm.getRequisitionNo() != null){
					requisitionsTo.setRequisitionNol(hlApplicationForm.getRequisitionNo().toString());
				}
				if(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId() != null){
					requisitionsTo.setRoomType(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName());
					requisitionsTo.setRoomTypeId(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getId());
				}
				if(hlApplicationForm.getHlStatus()!=null)
				{
					requisitionsTo.setStatus(hlApplicationForm.getHlStatus().getStatusType());
				}

				if(hlApplicationForm.getIsStaff()==null || !hlApplicationForm.getIsStaff()){
					if(hlApplicationForm.getAdmAppln().getPersonalData() != null){
						if(hlApplicationForm.getAdmAppln().getPersonalData().getReligion()!=null){
							requisitionsTo.setReligion(hlApplicationForm.getAdmAppln().getPersonalData().getReligion().getName());
						}else{
							requisitionsTo.setReligion(hlApplicationForm.getAdmAppln().getPersonalData().getReligionOthers());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getDateOfBirth() != null){
							requisitionsTo.setDateofBirth(hlApplicationForm.getAdmAppln().getPersonalData().getDateOfBirth().toString());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getMobileNo2() != null){
							requisitionsTo.setMobileNo((hlApplicationForm.getAdmAppln().getPersonalData().getMobileNo2()));
						}
						if(hlApplicationForm.getAdmAppln().getCourseBySelectedCourseId().getProgram() != null){
							requisitionsTo.setProgram(hlApplicationForm.getAdmAppln().getCourseBySelectedCourseId().getProgram().getName());
						}
						requisitionsTo.setApplNoStaffID(Integer.toString(hlApplicationForm.getAdmAppln().getApplnNo()));
						Set<ApplnDoc> appDocs=hlApplicationForm.getAdmAppln().getApplnDocs();
						if(appDocs!=null && !appDocs.isEmpty()){
							Iterator<ApplnDoc> docItr=appDocs.iterator();
							while (docItr.hasNext()) {
								ApplnDoc applnDoc = (ApplnDoc) docItr.next();
								if(applnDoc.getIsPhoto()){
									if(applnDoc.getDocument()!=null){
										requisitionsTo.setPhotoBytes(applnDoc.getDocument());
									}
								}
							}
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getFirstName()!=null){
							name=name+hlApplicationForm.getAdmAppln().getPersonalData().getFirstName();
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName()!=null){
							name=name+hlApplicationForm.getAdmAppln().getPersonalData().getMiddleName();
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getLastName()!=null){
							name=name+hlApplicationForm.getAdmAppln().getPersonalData().getLastName();
						}
						requisitionsTo.setStudentName(name);
						if(hlApplicationForm.getAdmAppln().getPersonalData().getEmail() != null){
							requisitionsTo.setEmail(hlApplicationForm.getAdmAppln().getPersonalData().getEmail());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine1()!= null){
							requisitionsTo.setPaddressLine1(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine1());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine2() != null){
							requisitionsTo.setPaddressLine2(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine2());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine3() != null){
							requisitionsTo.setPaddressLine3(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressLine3());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressZipCode() != null){
							requisitionsTo.setZip(hlApplicationForm.getAdmAppln().getPersonalData().getParentAddressZipCode());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getParentPh3() != null){
							requisitionsTo.setPhone(hlApplicationForm.getAdmAppln().getPersonalData().getParentPh3());
						}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine1()!= null){
							requisitionsTo.setGaddressLine1(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine1());
							}
						
						if(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine2() != null){
							requisitionsTo.setGaddressLine2(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine2());
							}
						
						if(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine3() != null){
							requisitionsTo.setGaddressLine3(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressLine3());
							}
						
						if(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianPh3()!= null){
							requisitionsTo.setGphone(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianPh3());
							}
						
						if(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressZipCode() != null){
							requisitionsTo.setGzip(hlApplicationForm.getAdmAppln().getPersonalData().getGuardianAddressZipCode());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getCityByParentAddressCityId()!= null){
							requisitionsTo.setCity(hlApplicationForm.getAdmAppln().getPersonalData().getCityByParentAddressCityId());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getStateByParentAddressStateId()!= null){
							requisitionsTo.setState(hlApplicationForm.getAdmAppln().getPersonalData().getStateByParentAddressStateId().getName());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getCountryByParentAddressCountryId() != null){
							requisitionsTo.setCountry(hlApplicationForm.getAdmAppln().getPersonalData().getCountryByParentAddressCountryId().getName());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getCityByGuardianAddressCityId() != null){
							requisitionsTo.setGcity(hlApplicationForm.getAdmAppln().getPersonalData().getCityByGuardianAddressCityId());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getStateByGuardianAddressStateId() != null){
							requisitionsTo.setGstate(hlApplicationForm.getAdmAppln().getPersonalData().getStateByGuardianAddressStateId().getName());
							}
						if(hlApplicationForm.getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId()!= null){
							requisitionsTo.setGcountry(hlApplicationForm.getAdmAppln().getPersonalData().getCountryByGuardianAddressCountryId().getName());
							}
					}
				}else{
					if(hlApplicationForm.getEmployee()!=null){
						requisitionsTo.setApplNoStaffID(hlApplicationForm.getEmployee().getCode());
						if(hlApplicationForm.getEmployee().getDob()!= null){
							requisitionsTo.setDateofBirth(hlApplicationForm.getEmployee().getDob().toString());
						}
						
						if(hlApplicationForm.getEmployee().getFirstName()!=null){
							name=name+hlApplicationForm.getEmployee().getFirstName();
						}
					/*	if(hlApplicationForm.getEmployee().getMiddleName()!=null){
							name=name+hlApplicationForm.getEmployee().getMiddleName();
						}
						if(hlApplicationForm.getEmployee().getLastName()!=null){
							name=name+hlApplicationForm.getEmployee().getLastName();
						}*/
						requisitionsTo.setStudentName(name);
						if(hlApplicationForm.getEmployee().getEmail() != null){
							requisitionsTo.setEmail(hlApplicationForm.getEmployee().getEmail());
						}
						if(hlApplicationForm.getEmployee().getPermanentAddressLine1()!= null){
							requisitionsTo.setPaddressLine1(hlApplicationForm.getEmployee().getPermanentAddressLine1());
						}
						if(hlApplicationForm.getEmployee().getPermanentAddressLine2() != null){
							requisitionsTo.setPaddressLine2(hlApplicationForm.getEmployee().getPermanentAddressLine2());
						}
						if(hlApplicationForm.getEmployee().getPermanentAddressZip() != null){
							requisitionsTo.setZip(hlApplicationForm.getEmployee().getPermanentAddressZip());
						}
					/*	if(hlApplicationForm.getEmployee().getPermanentAddressHomeTelephone3()!= null){
							requisitionsTo.setPhone(hlApplicationForm.getEmployee().getPermanentAddressHomeTelephone3());
						}*/
						if(hlApplicationForm.getEmployee().getCommunicationAddressLine1() != null){
							requisitionsTo.setGaddressLine1(hlApplicationForm.getEmployee().getCommunicationAddressLine1());
							}
						
						if(hlApplicationForm.getEmployee().getCommunicationAddressLine2()!= null){
							requisitionsTo.setGaddressLine2(hlApplicationForm.getEmployee().getCommunicationAddressLine2());
							}
						
						/*if(hlApplicationForm.getEmployee().getPhone2()!= null){
							requisitionsTo.setGphone(hlApplicationForm.getEmployee().getPhone2());
							}*/
						
						if(hlApplicationForm.getEmployee().getCommunicationAddressZip()!= null){
							requisitionsTo.setGzip(hlApplicationForm.getEmployee().getCommunicationAddressZip());
							}
						if(hlApplicationForm.getEmployee().getPermanentAddressCity()!= null){
							requisitionsTo.setCity(hlApplicationForm.getEmployee().getPermanentAddressCity());
							}
						if(hlApplicationForm.getEmployee().getStateByPermanentAddressStateId()!= null){
							requisitionsTo.setState(hlApplicationForm.getEmployee().getStateByPermanentAddressStateId().getName());
							}
						if(hlApplicationForm.getEmployee().getCountryByPermanentAddressCountryId() != null){
							requisitionsTo.setCountry(hlApplicationForm.getEmployee().getCountryByPermanentAddressCountryId().getName());
							}
						if(hlApplicationForm.getEmployee().getCommunicationAddressCity() != null){
							requisitionsTo.setGcity(hlApplicationForm.getEmployee().getCommunicationAddressCity());
							}
						if(hlApplicationForm.getEmployee().getStateByCommunicationAddressStateId() != null){
							requisitionsTo.setGstate(hlApplicationForm.getEmployee().getStateByCommunicationAddressStateId().getName());
							}
						if(hlApplicationForm.getEmployee().getCountryByCommunicationAddressCountryId()!= null){
							requisitionsTo.setGcountry(hlApplicationForm.getEmployee().getCountryByCommunicationAddressCountryId().getName());
							}
						
					}
				}
				if(hlApplicationForm.getHlHostelByHlApprovedHostelId() != null){
					requisitionsTo.setPreferredHostel(hlApplicationForm.getHlHostelByHlApprovedHostelId().getName());
					}
				if(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId()!= null){
					requisitionsTo.setPreferredRoomType(hlApplicationForm.getHlRoomTypeByHlApprovedRoomTypeId().getName());
				}
			}
			request.getSession().setAttribute("image", requisitionsTo);
	}
	return requisitionsTo;
		
		
	
	}




	public static List<VRequisitionsTO> getapprvieIds(ViewRequisitionsForm viewRequisitionsForm) {
		List<VRequisitionsTO> list = viewRequisitionsForm.getRequisitionsList();
		List<VRequisitionsTO> list1=new ArrayList<VRequisitionsTO>();
		Iterator<VRequisitionsTO> itr = list.iterator();	
		//Set<Integer> ids = new HashSet<Integer>();
		while(itr.hasNext()) {
			VRequisitionsTO vRequisitionsTO=itr.next();
			VRequisitionsTO vRequisitionsTO1=new VRequisitionsTO();
			if(vRequisitionsTO.getStatus()!=null && vRequisitionsTO.getStatus().length() >0 && vRequisitionsTO.getStatus() != null &&  !vRequisitionsTO.getStatus().equalsIgnoreCase("select")) {
				vRequisitionsTO1.setId(vRequisitionsTO.getId());
				vRequisitionsTO1.setApplno(vRequisitionsTO.getApplno());
				vRequisitionsTO1.setStatus(vRequisitionsTO.getStatus());
				list1.add(vRequisitionsTO1);
			}
		}
	return list1;
	}
	// single status update
	public static List<VRequisitionsTO> getapprvieIdforSingle(ViewRequisitionsForm viewRequisitionsForm) {
	
		List<VRequisitionsTO> list = viewRequisitionsForm.getRequisitionsList();
		List<VRequisitionsTO> list1=new ArrayList<VRequisitionsTO>();
		Iterator<VRequisitionsTO> itr = list.iterator();	
		//Set<Integer> ids = new HashSet<Integer>();
		while(itr.hasNext()) {
			VRequisitionsTO vRequisitionsTO=itr.next();
			VRequisitionsTO vRequisitionsTO1=new VRequisitionsTO();
			if(vRequisitionsTO.getRoomStatus().length() >0 && vRequisitionsTO.getRoomStatus() != null &&  !vRequisitionsTO.getRoomStatus().equalsIgnoreCase("select")) {
				vRequisitionsTO1.setId(vRequisitionsTO.getId());
				vRequisitionsTO1.setApplno(vRequisitionsTO.getApplno());
				vRequisitionsTO1.setStatus(vRequisitionsTO.getRoomStatus());
				list1.add(vRequisitionsTO1);
				
			}
		}
	return list1;
	}
}

