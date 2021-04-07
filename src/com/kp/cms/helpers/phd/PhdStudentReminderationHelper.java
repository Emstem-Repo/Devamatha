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
import com.kp.cms.bo.phd.PhdVoucherNumber;
import com.kp.cms.forms.phd.PhdStudentReminderationForm;
import com.kp.cms.to.phd.PhdStudentReminderationTO;

public class PhdStudentReminderationHelper {
	private static final Log log = LogFactory.getLog(PhdStudentReminderationHelper.class);
	public static volatile PhdStudentReminderationHelper examCceFactorHelper = null;

	public static PhdStudentReminderationHelper getInstance() {
		if (examCceFactorHelper == null) {
			examCceFactorHelper = new PhdStudentReminderationHelper();
		}
		return examCceFactorHelper;
	}
	/**
	 * @param guideList
	 * @return
	 */
	public List<PhdStudentReminderationTO> copyBotoToguide(List<Object[]> guideList) {
		log.debug("Helper : Entering copyBotoTo");
		List<PhdStudentReminderationTO> guideToList = new ArrayList<PhdStudentReminderationTO>();
		Iterator<Object[]> itr = guideList.iterator();
	while (itr.hasNext()) {
		Object[] object = (Object[]) itr.next();
		PhdStudentReminderationTO PhdTo=new PhdStudentReminderationTO();
		
		if(object[0]!=null){
			PhdTo.setStudentId(object[0].toString());
		}
		if(object[1]!=null){
			PhdTo.setRegisterNo(object[1].toString());
		}if(object[2]!=null && object[3]!=null && object[4]!=null){
			PhdTo.setStudentName(object[2].toString()+" "+object[3].toString()+""+object[4].toString());
		}if(object[2]!=null &&  object[4]!=null){
			PhdTo.setStudentName(object[2].toString()+""+object[4].toString());
		}if(object[2]!=null &&  object[4]==null){
			PhdTo.setStudentName(object[2].toString());
		}if(object[5]!=null){
			PhdTo.setCourseName(object[5].toString());
		}if(object[6]!=null){
			PhdTo.setCourseId(object[6].toString());
		}if(object[7]!=null){
			PhdTo.setGuide(object[7].toString());
		}if(object[8]!=null){
			PhdTo.setGuideId(object[8].toString());
		}if(object[9]!=null){
			PhdTo.setGuide(object[9].toString());
		}if(object[10]!=null){
			PhdTo.setGuideId(object[10].toString());
		}if(object[11]!=null){
			PhdTo.setCoGuide(object[11].toString());
		}if(object[12]!=null){
			PhdTo.setCoGuideId(object[12].toString());
		}if(object[13]!=null){
			PhdTo.setCoGuide(object[13].toString());
		}if(object[14]!=null){
			PhdTo.setCoGuideId(object[14].toString());
		}if(object[15]!=null){
			PhdTo.setPrintornot("Yes");
		}else{
			PhdTo.setPrintornot("No");
		}
		guideToList.add(PhdTo);
	}
		log.debug("Helper : Leaving copyBotoTo");
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
			PhdTo.setDocumentName(object[0].toString());
		}if(object[1]!=null){
			PhdTo.setDocumentId(object[1].toString());
		}if(object[2]!=null){
				totalAmount=totalAmount +Double.parseDouble(object[2].toString());
				objForm.setgAmountTotal(String.valueOf(totalAmount));
				objForm.setGgamount(String.valueOf(totalAmount));
				objForm.setcAmountTotal(String.valueOf(totalAmount));
				objForm.setCcamount(String.valueOf(totalAmount));
				PhdTo.setcAmount(object[2].toString());
				PhdTo.setgAmount(object[2].toString());
		}if(object[3]!=null){
			objForm.setGuideName(object[3].toString());
			PhdTo.setGuideName(object[3].toString());
		}if(object[4]!=null){
			PhdTo.setGuideId(object[4].toString());
			objForm.setGuideId(object[4].toString());
		}if(object[5]!=null){
			objForm.setGuideName(object[5].toString());
			PhdTo.setGuideName(object[5].toString());
		}if(object[6]!=null){
			PhdTo.setGuideId(object[6].toString());
			objForm.setiGuideId(object[6].toString());
			PhdTo.setiGuideId(object[6].toString());
		}if(object[7]!=null){
			objForm.setCoGuideName(object[7].toString());
			PhdTo.setCoGuideName(object[7].toString());
		}if(object[8]!=null){
			PhdTo.setCoGuideId(object[8].toString());
			objForm.setCoGuideId(object[8].toString());
		}if(object[9]!=null){
			objForm.setCoGuideName(object[9].toString());
			PhdTo.setCoGuideName(object[9].toString());
		}if(object[10]!=null){
			PhdTo.setCoGuideId(object[10].toString());
			PhdTo.setiCoGuideId(object[10].toString());
			objForm.setiCoGuideId(object[10].toString());
		}if(object[11]!=null){
			PhdTo.setSubmissionDate(object[11].toString());
		}if(object[12]!=null){
			PhdTo.setStudentId(object[12].toString());
			objForm.setStudentId(object[12].toString());
		}if(object[13]!=null){
			PhdTo.setRegisterNo(object[13].toString());
			objForm.setRegisterNo(object[13].toString());
		}if(object[14]!=null && object[15]!=null && object[16]!=null){
			PhdTo.setStudentName(object[14].toString()+" "+object[15].toString()+""+object[16].toString());
			objForm.setStudentName(object[14].toString()+" "+object[15].toString()+""+object[16].toString());
		}if(object[14]!=null &&  object[16]!=null){
			PhdTo.setStudentName(object[14].toString()+""+object[16].toString());
			objForm.setStudentName(object[14].toString()+""+object[16].toString());
		}if(object[14]!=null &&  object[16]==null){
			PhdTo.setStudentName(object[14].toString());
			objForm.setStudentName(object[14].toString());
		}if(object[17]!=null){
			PhdTo.setCourseName(object[17].toString());
			objForm.setCourseName(object[17].toString());
		}if(object[18]!=null){
			PhdTo.setCourseId(object[18].toString());
		}if(object[19]!=null){
			PhdTo.setAssignDate(object[19].toString());
		}
		guideToList.add(PhdTo);
	}
		log.debug("Helper : Leaving copyBotoTo");
	   return guideToList;
	}
	public List<PhdGuideRemunerations> guidesFormToBO(PhdStudentReminderationForm objForm) {
		List<PhdGuideRemunerations> guideList=new ArrayList<PhdGuideRemunerations>();
		Set<PhdGuideRemunerationDetails>  coGetaillist=new HashSet<PhdGuideRemunerationDetails>();
		Set<PhdGuideRemunerationDetails>  detaillist=new HashSet<PhdGuideRemunerationDetails>();
		PhdGuideRemunerations guideBo=new PhdGuideRemunerations();
		PhdGuideRemunerations coguideBo=new PhdGuideRemunerations();
		Student student=new Student();
		
		PhdEmployee guide=new PhdEmployee();
		Employee iGuide=new Employee();
		if((objForm.getGuideId()!=null && !objForm.getGuideId().isEmpty()) || (objForm.getiGuideId()!=null && !objForm.getiGuideId().isEmpty())){
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
		if(objForm.getgAmountConveyance()!=null && !objForm.getgAmountConveyance().isEmpty()){
			guideBo.setConveyanceCharges(new BigDecimal(objForm.getgAmountConveyance()));
		}if(objForm.getgAmountOther()!=null && !objForm.getgAmountOther().isEmpty()){
			guideBo.setOtherCharges(new BigDecimal(objForm.getgAmountOther()));
		}if(objForm.getgAmountTotal()!=null && !objForm.getgAmountTotal().isEmpty()){
			guideBo.setTotalCharges(new BigDecimal(objForm.getgAmountTotal()));
		}if(objForm.getgAmountdescription()!=null && !objForm.getgAmountdescription().isEmpty()){
			guideBo.setDescription(objForm.getgAmountdescription());
		}guideBo.setGeneratedDate(new Date());
		guideBo.setCreatedBy(objForm.getUserId());
		guideBo.setCreatedDate(new Date());
		guideBo.setLastModifiedDate(new Date());
		guideBo.setModifiedBy(objForm.getUserId());
		guideBo.setIsActive(Boolean.valueOf(true));
		
		Iterator<PhdStudentReminderationTO>  itr= objForm.getGuideDetailList().iterator();
		while (itr.hasNext()) {
			PhdStudentReminderationTO studentReminderationTO = (PhdStudentReminderationTO) itr.next();
			PhdGuideRemunerationDetails guideRemunerationDetails=new PhdGuideRemunerationDetails();
			DocumentDetailsBO document=new DocumentDetailsBO();
			document.setId(Integer.parseInt(studentReminderationTO.getDocumentId()));
			guideRemunerationDetails.setDocumentId(document);
			if(studentReminderationTO.getgAmount()!=null && !studentReminderationTO.getgAmount().isEmpty()){
			guideRemunerationDetails.setAmount(new BigDecimal(studentReminderationTO.getgAmount()));
			}
			guideRemunerationDetails.setCreatedBy(objForm.getUserId());
			guideRemunerationDetails.setCreatedDate(new Date());
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
			}
			if(objForm.getcAmountConveyance()!=null && !objForm.getcAmountConveyance().isEmpty()){
				coguideBo.setConveyanceCharges(new BigDecimal(objForm.getcAmountConveyance()));
			}if(objForm.getcAmountOther()!=null && !objForm.getcAmountOther().isEmpty()){
				coguideBo.setOtherCharges(new BigDecimal(objForm.getcAmountOther()));
			}if(objForm.getcAmountTotal()!=null && !objForm.getcAmountTotal().isEmpty()){
				coguideBo.setTotalCharges(new BigDecimal(objForm.getcAmountTotal()));
			}if(objForm.getcAmountdescription()!=null && !objForm.getcAmountdescription().isEmpty()){
				coguideBo.setDescription(objForm.getcAmountdescription());
			}coguideBo.setGeneratedDate(new Date());
			coguideBo.setCreatedBy(objForm.getUserId());
			coguideBo.setCreatedDate(new Date());
			coguideBo.setLastModifiedDate(new Date());
			coguideBo.setModifiedBy(objForm.getUserId());
			coguideBo.setIsActive(Boolean.valueOf(true));
			
			Iterator<PhdStudentReminderationTO>  itrr= objForm.getCoGuideDetailList().iterator();
			while (itrr.hasNext()) {
				PhdStudentReminderationTO studentReminderationTO = (PhdStudentReminderationTO) itrr.next();
				PhdGuideRemunerationDetails guideRemunerationDetails=new PhdGuideRemunerationDetails();
				DocumentDetailsBO document=new DocumentDetailsBO();
				document.setId(Integer.parseInt(studentReminderationTO.getDocumentId()));
				guideRemunerationDetails.setDocumentId(document);
				if(studentReminderationTO.getcAmount()!=null && !studentReminderationTO.getcAmount().isEmpty()){
					guideRemunerationDetails.setAmount(new BigDecimal(studentReminderationTO.getcAmount()));
					}
				guideRemunerationDetails.setCreatedBy(objForm.getUserId());
				guideRemunerationDetails.setCreatedDate(new Date());
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
	public String getGuidetotalAmount(PhdStudentReminderationForm objForm) {
		Iterator<PhdStudentReminderationTO> itr=objForm.getGuideDetailList().iterator();
		double gTotalAmount =0.0;
		double cTotalAmount =0.0;
		while (itr.hasNext()) {
			PhdStudentReminderationTO phdTO = (PhdStudentReminderationTO) itr.next();
			if(phdTO.getgAmount()!=null && !phdTO.getgAmount().isEmpty()){
				gTotalAmount=gTotalAmount+Double.parseDouble(phdTO.getgAmount());
			}if(phdTO.getcAmount()!=null && !phdTO.getcAmount().isEmpty()){
				cTotalAmount=cTotalAmount+Double.parseDouble(phdTO.getcAmount());
			}
			objForm.setGgamount(String.valueOf(gTotalAmount));
			objForm.setCcamount(String.valueOf(cTotalAmount));
		}if(objForm.getgAmountConveyance()!=null && !objForm.getgAmountConveyance().isEmpty()){
			gTotalAmount=gTotalAmount+Double.parseDouble(objForm.getgAmountConveyance());
		}if(objForm.getgAmountOther()!=null && !objForm.getgAmountOther().isEmpty()){
			gTotalAmount=gTotalAmount+Double.parseDouble(objForm.getgAmountOther());
		}if(objForm.getcAmountConveyance()!=null && !objForm.getcAmountConveyance().isEmpty()){
			cTotalAmount=cTotalAmount+Double.parseDouble(objForm.getcAmountConveyance());
		}if(objForm.getcAmountOther()!=null && !objForm.getcAmountOther().isEmpty()){
			cTotalAmount=cTotalAmount+Double.parseDouble(objForm.getcAmountOther());
		}
		objForm.setgAmountTotal(String.valueOf(gTotalAmount));
		objForm.setcAmountTotal(String.valueOf(cTotalAmount));
		return String.valueOf(gTotalAmount);
	}
	public void setVoucherNo(PhdVoucherNumber guideBOs,	PhdStudentReminderationForm objForm) {
		
		if(guideBOs.getCurrentNo()!=null && !guideBOs.getCurrentNo().isEmpty())	{
			objForm.setGeneratedNo(guideBOs.getCurrentNo());
		}else{
			objForm.setGeneratedNo(guideBOs.getStartNo());
		}
		if(Integer.parseInt(objForm.getGeneratedNo())==0){
			objForm.setGeneratedNo(Integer.toString(Integer.parseInt(objForm.getGeneratedNo())+1));
		}
	}
}
