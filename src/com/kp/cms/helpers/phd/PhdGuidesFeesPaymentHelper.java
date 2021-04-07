package com.kp.cms.helpers.phd;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.phd.DocumentDetailsBO;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdGuideRemunerationDetails;
import com.kp.cms.bo.phd.PhdGuideRemunerations;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.to.phd.PhdStudentReminderationTO;
import com.kp.cms.utilities.CommonUtil;

public class PhdGuidesFeesPaymentHelper {
	private static final Log log = LogFactory.getLog(PhdGuidesFeesPaymentHelper.class);
	public static volatile PhdGuidesFeesPaymentHelper phdGuidesFeesPaymentHelper = null;

	public static PhdGuidesFeesPaymentHelper getInstance() {
		if (phdGuidesFeesPaymentHelper == null) {
			phdGuidesFeesPaymentHelper = new PhdGuidesFeesPaymentHelper();
		}
		return phdGuidesFeesPaymentHelper;
	}
	/**
	 * @param guideList
	 * @return
	 */
	/**
	 * @param guideList
	 * @return
	 */
	public List<PhdStudentReminderationTO> copyBotoToguide(List<PhdGuideRemunerations> guideList) {
		log.debug("Helper : Entering copyBotoToguide");
		List<PhdStudentReminderationTO> guideToList = new ArrayList<PhdStudentReminderationTO>();
		Iterator<PhdGuideRemunerations> itr = guideList.iterator();
		while (itr.hasNext()) {
			PhdGuideRemunerations phdGuideBo = (PhdGuideRemunerations) itr.next();
			PhdStudentReminderationTO phdGuideTo=new PhdStudentReminderationTO();
			
			phdGuideTo.setId(phdGuideBo.getId());
			
			if(phdGuideBo.getStudentId()!=null){
				phdGuideTo.setVoucherNo(Integer.toString(phdGuideBo.getStudentId().getId()));
			}if(phdGuideBo.getVoucherNo()!=null && !phdGuideBo.getVoucherNo().isEmpty()){
				phdGuideTo.setVoucherNo(phdGuideBo.getVoucherNo());
			}if(phdGuideBo.getGuideId()!=null){
				phdGuideTo.setGuideName(phdGuideBo.getGuideId().getName());
				phdGuideTo.setGuideId(Integer.toString(phdGuideBo.getGuideId().getId()));
			}if(phdGuideBo.getInternalGuide()!=null){
				phdGuideTo.setGuideName(phdGuideBo.getInternalGuide().getFirstName());
				phdGuideTo.setiGuideId(Integer.toString(phdGuideBo.getInternalGuide().getId()));
			}if(phdGuideBo.getCoGuideId()!=null){
				phdGuideTo.setGuideName(phdGuideBo.getCoGuideId().getName());
				phdGuideTo.setCoGuideId(Integer.toString(phdGuideBo.getCoGuideId().getId()));
			}if(phdGuideBo.getInternalCoGuide()!=null){
				phdGuideTo.setGuideName(phdGuideBo.getInternalCoGuide().getFirstName());
				phdGuideTo.setiCoGuideId(Integer.toString(phdGuideBo.getInternalCoGuide().getId()));
			}if(phdGuideBo.getIsPaid()!=null){
				if(phdGuideBo.getIsPaid()){
				phdGuideTo.setTempChecked("on");
				}else{
					phdGuideTo.setChecked(null);
					phdGuideTo.setTempChecked(null);
				}
			}if(phdGuideBo.getPaidDate()!=null){
				phdGuideTo.setPaidDate(CommonUtil.ConvertStringToDateFormat(phdGuideBo.getPaidDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			}if(phdGuideBo.getPaidMode()!=null && !phdGuideBo.getPaidMode().isEmpty()){
				phdGuideTo.setPaidMode(phdGuideBo.getPaidMode());
			}if(phdGuideBo.getOtherRemarks()!=null && !phdGuideBo.getOtherRemarks().isEmpty()){
				phdGuideTo.setOtherRemarks(phdGuideBo.getOtherRemarks());
			}if(phdGuideBo.getGeneratedDate()!=null){
				phdGuideTo.setGeneratedDate(CommonUtil.ConvertStringToDateFormat(phdGuideBo.getGeneratedDate().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
			}
		 guideToList.add(phdGuideTo);
		}
		log.debug("Helper : Leaving copyBotoToguide");
	   return guideToList;
	}

	/**
	 * @param guideList
	 * @param objForm
	 * @return
	 */
	public List<PhdStudentReminderationTO> generateStudentDetails(List<Object[]> guideList, PhdStudentReminderationForm objForm) {
		log.debug("Helper : Entering copyBotoTo");
		List<PhdStudentReminderationTO> guideToList = new ArrayList<PhdStudentReminderationTO>();
		Iterator<Object[]> itr = guideList.iterator();
		double totalAmount =0.0;
	while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdStudentReminderationTO PhdTo=new PhdStudentReminderationTO();
		
		if(object[0]!=null){
			objForm.setId(Integer.parseInt(object[0].toString()));
		}if(object[1]!=null){
			PhdTo.setStudentId(object[1].toString());
			objForm.setStudentId(object[1].toString());
		}if(object[2]!=null){
			PhdTo.setRegisterNo(object[2].toString());
			objForm.setRegisterNo(object[2].toString());
		}if(object[3]!=null && object[4]!=null && object[5]!=null){
			PhdTo.setStudentName(object[3].toString()+" "+object[4].toString()+""+object[5].toString());
			objForm.setStudentName(object[3].toString()+" "+object[4].toString()+""+object[5].toString());
		}if(object[3]!=null &&  object[5]!=null){
			PhdTo.setStudentName(object[3].toString()+""+object[5].toString());
			objForm.setStudentName(object[3].toString()+""+object[5].toString());
		}if(object[3]!=null &&  object[5]==null){
			PhdTo.setStudentName(object[3].toString());
			objForm.setStudentName(object[3].toString());
		}if(object[6]!=null){
			PhdTo.setCourseName(object[6].toString());
			objForm.setCourseName(object[6].toString());
		}if(object[7]!=null){
			PhdTo.setCourseId(object[7].toString());
			objForm.setCourseId(object[7].toString());
		}if(object[8]!=null){
			PhdTo.setDocumentName(object[8].toString());
		}if(object[9]!=null){
			PhdTo.setDocumentId(object[9].toString());
		}if(object[10]!=null){
			PhdTo.setSubmissionDate(object[10].toString());
		}if(object[11]!=null){
			objForm.setGuideName(object[11].toString());
			PhdTo.setGuideName(object[11].toString());
		}if(object[12]!=null){
			PhdTo.setGuideId(object[12].toString());
			objForm.setGuideId(object[12].toString());
		}if(object[13]!=null){
			objForm.setGuideName(object[13].toString());
			PhdTo.setGuideName(object[13].toString());
		}if(object[14]!=null){
			PhdTo.setGuideId(object[14].toString());
			objForm.setiGuideId(object[14].toString());
			PhdTo.setiGuideId(object[14].toString());
		}if(object[15]!=null){
			objForm.setCoGuideName(object[15].toString());
			PhdTo.setCoGuideName(object[15].toString());
		}if(object[16]!=null){
			PhdTo.setCoGuideId(object[16].toString());
			objForm.setCoGuideId(object[16].toString());
		}if(object[17]!=null){
			objForm.setCoGuideName(object[17].toString());
			PhdTo.setCoGuideName(object[17].toString());
		}if(object[18]!=null){
			PhdTo.setCoGuideId(object[18].toString());
			PhdTo.setiCoGuideId(object[18].toString());
			objForm.setiCoGuideId(object[18].toString());
		}if(object[19]!=null){
			objForm.setgAmountConveyance(object[19].toString());
			objForm.setcAmountConveyance(object[19].toString());
		}if(object[20]!=null){
			objForm.setgAmountOther(object[20].toString());
			objForm.setcAmountOther(object[20].toString());
		}if(object[21]!=null){
			objForm.setgAmountdescription(object[21].toString());
			objForm.setcAmountdescription(object[21].toString());
		}if(object[22]!=null){
			objForm.setgAmountTotal(object[22].toString());
			objForm.setcAmountTotal(object[22].toString());
		}if(object[23]!=null){
			PhdTo.setgAmount(object[23].toString());
			PhdTo.setcAmount(object[23].toString());
			totalAmount=totalAmount +Double.parseDouble(object[23].toString());
			objForm.setGgamount(String.valueOf(totalAmount));
			objForm.setCcamount(String.valueOf(totalAmount));
		}if(object[24]!=null){
			PhdTo.setId(Integer.parseInt(object[24].toString()));
		}if(object[25]!=null){
			PhdTo.setVoucherNo(object[25].toString());
			objForm.setgVoucherNo(object[25].toString());
			objForm.setcVoucherNo(object[25].toString());
			objForm.setGeneratedNo(object[25].toString());
		}if(object[26]!=null){
			PhdTo.setGeneratedDate(object[26].toString());
			objForm.setGeneratedDate(object[26].toString());
		}
		if(object[27]!=null){
			PhdTo.setIsPaid(object[27].toString());
			objForm.setIsPaid(object[27].toString().equalsIgnoreCase("true") ? true :false);
		}
		if(object[28]!=null){
			PhdTo.setPaidDate(object[28].toString());
			objForm.setPaidDate(object[28].toString());
		}
		if(object[29]!=null){
			PhdTo.setPaidMode(object[29].toString());
			objForm.setPaidMode(object[29].toString());
		}
		if(object[30]!=null){
			PhdTo.setOtherRemarks(object[30].toString());
			objForm.setOtherRemarks(object[30].toString());
		}guideToList.add(PhdTo);
	}
		log.debug("Helper : Leaving copyBotoTo");
	   return guideToList;
	}
	public List<PhdGuideRemunerations> updateGuideRemenderation(PhdStudentReminderationForm objForm) {
		List<PhdGuideRemunerations> guideList=new ArrayList<PhdGuideRemunerations>();
		Set<PhdGuideRemunerationDetails>  coGetaillist=new HashSet<PhdGuideRemunerationDetails>();
		Set<PhdGuideRemunerationDetails>  detaillist=new HashSet<PhdGuideRemunerationDetails>();
		PhdGuideRemunerations guideBo=new PhdGuideRemunerations();
		PhdGuideRemunerations coguideBo=new PhdGuideRemunerations();
		Student student=new Student();
		PhdEmployee guide=new PhdEmployee();
		Employee iGuide=new Employee();
		
		if((objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()) || (objForm.getiGuideId()!=null && !objForm.getiGuideId().isEmpty())){
			
		if(objForm.getId()>0){
				guideBo.setId(objForm.getId());
			}
		if(objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()){
		guide.setId(Integer.parseInt(objForm.getGuideId()));
		guideBo.setGuideId(guide);
		}if(objForm.getiGuideId()!=null && !objForm.getiGuideId().isEmpty()){
			iGuide.setId(Integer.parseInt(objForm.getiGuideId()));
			guideBo.setInternalGuide(iGuide);
			}
		if(objForm.getStudentId()!=null && !objForm.getStudentId().isEmpty()){
			student.setId(Integer.parseInt(objForm.getStudentId()));
			guideBo.setStudentId(student);
		}
		if(objForm.getgVoucherNo()!=null && !objForm.getgVoucherNo().isEmpty()){
			guideBo.setVoucherNo(objForm.getgVoucherNo());
		}
		if(objForm.getIsPaid()!=null){
			guideBo.setIsPaid(objForm.getIsPaid());
		}if(objForm.getPaidDate()!=null && !objForm.getPaidDate().isEmpty()){
			guideBo.setPaidDate(CommonUtil.ConvertStringToDate(objForm.getPaidDate()));
		}if(objForm.getPaidMode()!=null && !objForm.getPaidMode().isEmpty()){
			guideBo.setPaidMode(objForm.getPaidMode());
		}if(objForm.getOtherRemarks()!=null && !objForm.getOtherRemarks().isEmpty()){
			guideBo.setOtherRemarks(objForm.getOtherRemarks());
		}
		if(objForm.getgAmountConveyance()!=null && !objForm.getgAmountConveyance().isEmpty()){
			guideBo.setConveyanceCharges(new BigDecimal(objForm.getgAmountConveyance()));
		}if(objForm.getgAmountOther()!=null && !objForm.getgAmountOther().isEmpty()){
			guideBo.setOtherCharges(new BigDecimal(objForm.getgAmountOther()));
		}if(objForm.getgAmountTotal()!=null && !objForm.getgAmountOther().isEmpty()){
			guideBo.setTotalCharges(new BigDecimal(objForm.getgAmountTotal()));
		}if(objForm.getgAmountdescription()!=null && !objForm.getgAmountdescription().isEmpty()){
			guideBo.setDescription(objForm.getgAmountdescription());
		}
		guideBo.setLastModifiedDate(new Date());
		guideBo.setModifiedBy(objForm.getUserId());
		guideBo.setIsActive(Boolean.valueOf(true));
		
		Iterator<PhdStudentReminderationTO>  itr= objForm.getGuideDetailList().iterator();
		while (itr.hasNext()) {
			PhdStudentReminderationTO studentReminderationTO = (PhdStudentReminderationTO) itr.next();
			PhdGuideRemunerationDetails guideRemunerationDetails=new PhdGuideRemunerationDetails();
			if(studentReminderationTO.getId()>0){
			guideRemunerationDetails.setId(studentReminderationTO.getId());
			}
			DocumentDetailsBO document=new DocumentDetailsBO();
			document.setId(Integer.parseInt(studentReminderationTO.getDocumentId()));
			guideRemunerationDetails.setDocumentId(document);
			if(studentReminderationTO.getgAmount()!=null && !studentReminderationTO.getgAmount().isEmpty()){
			guideRemunerationDetails.setAmount(new BigDecimal(studentReminderationTO.getgAmount()));
			}
			guideRemunerationDetails.setLastModifiedDate(new Date());
			guideRemunerationDetails.setModifiedBy(objForm.getUserId());
			guideRemunerationDetails.setIsActive(Boolean.valueOf(true));
			detaillist.add(guideRemunerationDetails);
		}
		guideBo.setGuideRemuneration(detaillist);

		guideList.add(guideBo);
		}
		PhdEmployee coguide=new PhdEmployee();
		Employee iCoGuide=new Employee();
		
		if((objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()) || (objForm.getiCoGuideId()!=null && !objForm.getiCoGuideId().isEmpty()))
		{
		if(objForm.getId()>0){
				coguideBo.setId(objForm.getId());
			}
		if(objForm.getCoGuideId()!=null && !objForm.getCoGuideId().isEmpty()){
			coguide.setId(Integer.parseInt(objForm.getCoGuideId()));
			coguideBo.setCoGuideId(coguide);
			}if(objForm.getiCoGuideId()!=null && !objForm.getiCoGuideId().isEmpty()){
				iCoGuide.setId(Integer.parseInt(objForm.getiCoGuideId()));
				coguideBo.setInternalCoGuide(iCoGuide);
				}
			if(objForm.getStudentId()!=null && !objForm.getStudentId().isEmpty()){
				student.setId(Integer.parseInt(objForm.getStudentId()));
				coguideBo.setStudentId(student);
			}
			if(objForm.getcVoucherNo()!=null && !objForm.getcVoucherNo().isEmpty()){
				coguideBo.setVoucherNo(objForm.getcVoucherNo());
			}if(objForm.getIsPaid()!=null){
				coguideBo.setIsPaid(objForm.getIsPaid());
			}if(objForm.getPaidDate()!=null && !objForm.getPaidDate().isEmpty()){
				coguideBo.setPaidDate(CommonUtil.ConvertStringToDate(objForm.getPaidDate()));
			}if(objForm.getPaidMode()!=null && !objForm.getPaidMode().isEmpty()){
				coguideBo.setPaidMode(objForm.getPaidMode());
			}if(objForm.getOtherRemarks()!=null && !objForm.getOtherRemarks().isEmpty()){
				coguideBo.setOtherRemarks(objForm.getOtherRemarks());
			}
			if(objForm.getcAmountConveyance()!=null && !objForm.getcAmountConveyance().isEmpty()){
				coguideBo.setConveyanceCharges(new BigDecimal(objForm.getcAmountConveyance()));
			}if(objForm.getcAmountOther()!=null && !objForm.getcAmountOther().isEmpty()){
				coguideBo.setOtherCharges(new BigDecimal(objForm.getcAmountOther()));
			}if(objForm.getcAmountTotal()!=null && !objForm.getcAmountOther().isEmpty()){
				coguideBo.setTotalCharges(new BigDecimal(objForm.getcAmountTotal()));
			}if(objForm.getcAmountdescription()!=null && !objForm.getcAmountdescription().isEmpty()){
				coguideBo.setDescription(objForm.getcAmountdescription());
			}
			coguideBo.setLastModifiedDate(new Date());
			coguideBo.setModifiedBy(objForm.getUserId());
			coguideBo.setIsActive(Boolean.valueOf(true));
			
			Iterator<PhdStudentReminderationTO>  itrr= objForm.getCoGuideDetailList().iterator();
			while (itrr.hasNext()) {
				PhdStudentReminderationTO studentReminderationTO = (PhdStudentReminderationTO) itrr.next();
				PhdGuideRemunerationDetails guideRemunerationDetails=new PhdGuideRemunerationDetails();
				if(studentReminderationTO.getId()>0){
					guideRemunerationDetails.setId(studentReminderationTO.getId());
					}
				DocumentDetailsBO document=new DocumentDetailsBO();
				document.setId(Integer.parseInt(studentReminderationTO.getDocumentId()));
				guideRemunerationDetails.setDocumentId(document);
				if(studentReminderationTO.getcAmount()!=null && !studentReminderationTO.getcAmount().isEmpty()){
					guideRemunerationDetails.setAmount(new BigDecimal(studentReminderationTO.getcAmount()));
					}
				guideRemunerationDetails.setLastModifiedDate(new Date());
				guideRemunerationDetails.setModifiedBy(objForm.getUserId());
				guideRemunerationDetails.setIsActive(Boolean.valueOf(true));
				coGetaillist.add(guideRemunerationDetails);
			}
			coguideBo.setGuideRemuneration(coGetaillist);
			
		guideList.add(coguideBo);
		}
		return guideList;
	}
	
}
