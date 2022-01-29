package com.kp.cms.helpers.admin;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.ExtracurricularActivity;
import com.kp.cms.bo.admin.Organisation;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.admin.OrganizationForm;
import com.kp.cms.to.admin.ExtracurricularActivityTO;
import com.kp.cms.to.admin.OrganizationTO;

public class OrganizationHelper {

private static final Log log = LogFactory.getLog(OrganizationHelper.class);
	private static volatile OrganizationHelper organizationHelper = null;

	private OrganizationHelper() {
	}
	/**
	 * 
	 * @returns a single instance when called
	 */
	public static OrganizationHelper getInstance() {
		if (organizationHelper == null) {
			organizationHelper = new OrganizationHelper();
		}
		return organizationHelper;
	}
	
	/**
	 * 
	 * @param organizationTO
	 * @param request 
	 * @return
	 * Used while saving
	 * Populates BO object from the captured TO object
	 */
	public Organisation polupateTOtoBOSave(OrganizationTO organizationTO, HttpServletRequest request) throws Exception
	{
		Organisation organisation = null;
		try {
			if (organizationTO != null) {
				/* added by sudhir*/
				String imageURL = request.getRealPath("/")+"images/Logos/";
				File f = new File(imageURL);
				/* -----------------*/
				organisation = new Organisation();
				organisation.setName(organizationTO.getOrganizationName().toUpperCase());
				organisation.setNeedFinalApprival(organizationTO.isNeedApproval());
				organisation.setChangePassword(organizationTO.getChangePassword());
				organisation.setSameUseridPassword(organizationTO.getSameUseridPassword());
				organisation.setAddressLine1(organizationTO.getAddressLine1());
				organisation.setCreatedDate(new Date());
				organisation.setLastModifiedDate(new Date());
				organisation.setCreatedBy(organizationTO.getCreatedBy());
				organisation.setModifiedBy(organizationTO.getModifiedBy());
				organisation.setIsActive(true);
				organisation.setFinalMeritlistApproval(organizationTO.isFinalMeritListApproval());
				if (organizationTO.getAddressLine2() != null) {
					organisation.setAddressLine2(organizationTO.getAddressLine2());
				}
				if (organizationTO.getAddressLine3() != null) {
					organisation.setAddressLine3(organizationTO.getAddressLine3());
				}
				if (organizationTO.getOrganizationLogo() != null) {
					organisation.setLogoName(organizationTO.getOrganizationLogo().getFileName());
					organisation.setLogoContentType(organizationTO.getOrganizationLogo().getContentType());
					organisation.setLogo(organizationTO.getOrganizationLogo().getFileData());
					/* added by sudhir*/
					if(organisation.getLogo()!=null && organisation.getLogo().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(organisation.getLogo()));
					//ImageIO.write(imag, "jpg",new File(f,organisation.getLogoName()));
					}
				}
				if(organizationTO.getOrganizationtopBar()!=null){
					organisation.setTopbarName(organizationTO.getOrganizationtopBar().getFileName());
					organisation.setTopbarContentType(organizationTO.getOrganizationtopBar().getContentType());
					organisation.setTopbar(organizationTO.getOrganizationtopBar().getFileData());
					/* added by sudhir*/
					if(organisation.getTopbar()!=null && organisation.getTopbar().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(organisation.getTopbar()));
					//ImageIO.write(imag, "jpg",new File(f,organisation.getTopbarName()));
					}
				}
				if (organizationTO.getOrganizationLogo1() != null) {
					organisation.setLogoName1(organizationTO.getOrganizationLogo1().getFileName());
					organisation.setLogoContentType1(organizationTO.getOrganizationLogo1().getContentType());
					organisation.setLogo1(organizationTO.getOrganizationLogo1().getFileData());
					/* added by sudhir*/
					if(organisation.getLogo1()!=null && organisation.getLogo1().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(organisation.getLogo1()));
					//ImageIO.write(imag, "jpg",new File(f,organisation.getLogoName1()));
					}
				}
				if(organizationTO.getOrganizationtopBar1()!=null){
					organisation.setTopbarName1(organizationTO.getOrganizationtopBar1().getFileName());
					organisation.setTopbarContentType1(organizationTO.getOrganizationtopBar1().getContentType());
					organisation.setTopbar1(organizationTO.getOrganizationtopBar1().getFileData());
					/* added by sudhir*/
					if(organisation.getTopbar1()!=null && organisation.getTopbar1().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(organisation.getTopbar1()));
					//ImageIO.write(imag, "jpg",new File(f,organisation.getTopbarName1()));
					}
				}
				organisation.setTimeLimit(organizationTO.getTimeLimit());
				organisation.setOpenHonoursCourseLink(organizationTO.isOpenHonoursCourseLink());
				organisation.setConvocationRegistration(organizationTO.isConvocationRegistration());
				organisation.setStudentPhotoUpload(organizationTO.isStudentPhotoUpload());
				}
		} 
		catch (RuntimeException runtime) {
			throw new ApplicationException (runtime);
		}catch (Exception e) {
			throw new ApplicationException (e);
		}
		log.info("End of Organization helper of polupateTOtoBOSave");
		return organisation;
	}
	
	/**
	 * Used while getting all organization details from DB
	 * Construct To object from the BO object
	 */
	
	public List<OrganizationTO> populateBOtoTO(List<Organisation> organisation)throws Exception{
		List<OrganizationTO>orgList=new ArrayList<OrganizationTO>();
		if(organisation!=null && !organisation.isEmpty()){
			
			Iterator<Organisation> iterator=organisation.iterator();
			while (iterator.hasNext()) {
				Organisation org = iterator.next();
				OrganizationTO organizationTO=new OrganizationTO();
				organizationTO.setId(org.getId());
				organizationTO.setAddressLine1(splitString(org.getAddressLine1()!=null ? org.getAddressLine1():null));
				if(org.getAddressLine2()!=null && !org.getAddressLine2().isEmpty()){
				organizationTO.setAddressLine2("," + splitString(org.getAddressLine2()));
				}
				if(org.getAddressLine3()!=null && !org.getAddressLine3().isEmpty()){
				organizationTO.setAddressLine3("," + splitString(org.getAddressLine3()));
				}
				//organizationTO.setOrganizationName(splitString(org.getName().trim()!=null ? org.getName():null));
				if(org.getName()!=null)
				organizationTO.setOrganizationName(org.getName().toUpperCase());
				organizationTO.setOrganizationLogoName(org.getLogoName()!=null ? org.getLogoName():null);
				organizationTO.setOrganizationTopBarName(org.getTopbarName()!=null ? org.getTopbarName():null);
				organizationTO.setOrganizationLogoName1(org.getLogoName1()!=null ? org.getLogoName1():null);
				organizationTO.setOrganizationTopBarName1(org.getTopbarName1()!=null ? org.getTopbarName1():null);
				organizationTO.setNeedApproval(org.getNeedFinalApprival()!=null ? org.getNeedFinalApprival():false);
				organizationTO.setFinalMeritListApproval(org.getFinalMeritlistApproval()!=null ? org.getFinalMeritlistApproval():false);
				organizationTO.setConvocationRegistration(org.getConvocationRegistration()!=null ? org.getConvocationRegistration():false);
				organizationTO.setStudentPhotoUpload(org.getStudentPhotoUpload()!=null ? org.getStudentPhotoUpload():false);
				organizationTO.setChangePassword(org.getChangePassword()!=null ? org.getChangePassword():false);
				organizationTO.setSameUseridPassword(org.getSameUseridPassword()!=null ? org.getSameUseridPassword():false);
				if(org.getNeedFinalApprival()!=null){
					if(org.getNeedFinalApprival()){
						organizationTO.setNeedFinalApprival(CMSConstants.YES);
					}
					else{
						organizationTO.setNeedFinalApprival(CMSConstants.NO);
					}
				}
				if(org.getSameUseridPassword()!=null){
					if(org.getSameUseridPassword()){
						organizationTO.setUseridPassword(CMSConstants.YES);
					}
					else{
						organizationTO.setUseridPassword(CMSConstants.NO);
					}
				}
				if(org.getChangePassword()!=null){
					if(org.getChangePassword()){
						organizationTO.setEnableChangePassword(CMSConstants.YES);
					}
					else{
						organizationTO.setEnableChangePassword(CMSConstants.NO);
					}
				}	
				if(org.getFinalMeritlistApproval()!=null){
					if(org.getFinalMeritlistApproval()){
						organizationTO.setDispFinalMeritApproval(CMSConstants.YES);
					}
					else
					{
						organizationTO.setDispFinalMeritApproval(CMSConstants.NO);
					}
				}
				Set<ExtracurricularActivity> extracurriculars=org.getExtracurricularActivities();
				if(extracurriculars!=null){
					List<ExtracurricularActivityTO> extraTos=new ArrayList<ExtracurricularActivityTO>();
					Iterator< ExtracurricularActivity> extrItr=extracurriculars.iterator();
					while (extrItr.hasNext()) {
						ExtracurricularActivity extraAct = (ExtracurricularActivity) extrItr.next();
						if(extraAct.getIsActive()==true){
						ExtracurricularActivityTO to= new ExtracurricularActivityTO();
						to.setId(extraAct.getId());
						to.setName(extraAct.getName());
						extraTos.add(to);
						}
					}
					organizationTO.setExtracurriculars(extraTos);
				}
				orgList.add(organizationTO);
				if(org.getLogoName()!=null && !org.getLogoName().isEmpty()){
					organizationTO.setLogoPresent(true);
					organizationTO.setLogo(org.getLogo());
				}else{
					organizationTO.setLogoPresent(false);
				}
				if(org.getTopbarName() != null && !org.getTopbarName().isEmpty()){
					organizationTO.setTopbarPresent(true);
				}else{
					organizationTO.setTopbarPresent(false);
				}
				if(org.getLogoName1()!=null && !org.getLogoName1().isEmpty()){
					organizationTO.setLogoPresent1(true);
				}else{
					organizationTO.setLogoPresent1(false);
				}
				if(org.getTopbarName1() != null && !org.getTopbarName1().isEmpty()){
					organizationTO.setTopbarPresent1(true);
				}else{
					organizationTO.setTopbarPresent1(false);
				}
			}
			if(!orgList.isEmpty()){
				return orgList;				
			}
		}
		log.info("Leaving into helper of populateBOtoTO");
		return null;
	}
	
	
	/**
	 * 
	 * @param This method is used to split the string if it is long (For keeping 10 aplhabets)
	 * @return
	 */
		public static String splitString(String value)throws Exception
		{
			StringBuffer appendedvalue = new StringBuffer();
			if(value!=null && !value.isEmpty())
			{
			int length=value.length();
			String[] temp=new String[length];
			int begindex=0,endindex=20;int count=0;
			
			while(true)
			{
				if(endindex<length)
				{
					temp[count]=value.substring(begindex,endindex);
					begindex=begindex+20;
					endindex=endindex+20;
					appendedvalue.append(temp[count]).append("\n");
				}else
				{
					if(count==0)
					temp[count]=value.substring(0,length);
					temp[count]=value.substring(begindex,length);
					appendedvalue.append(temp[count]);
					break;
				}
				count++;
			}
		}
			return appendedvalue.toString();
				
		}
		/**
		 * Used while editing organization details
		 * Set the datas to formbean from BO
		 */
		public void setFromBOToFormWhileEdit(Organisation organisation, OrganizationForm organizationForm)throws Exception{
			if(organisation != null){
				organizationForm.setOrganisation(organisation);
				organizationForm.setId(organisation.getId());
				organizationForm.setOrganizationName(organisation.getName()!=null ? organisation.getName():null);
				if(organisation.getNeedFinalApprival()!=null){
				organizationForm.setNeedApproval(organisation.getNeedFinalApprival());
				}
				if(organisation.getFinalMeritlistApproval()!=null){
					organizationForm.setFinalMeritListApproval(organisation.getFinalMeritlistApproval());
				}
				organizationForm.setAddressLine1(organisation.getAddressLine1()!=null ? organisation.getAddressLine1():null);
				organizationForm.setAddressLine2(organisation.getAddressLine2()!=null ? organisation.getAddressLine2():null);
				organizationForm.setAddressLine3(organisation.getAddressLine3()!=null ? organisation.getAddressLine3():null);
				if(organisation.getSameUseridPassword()!=null){
				organizationForm.setSameUseridPassword(organisation.getSameUseridPassword());
				}
				if(organisation.getChangePassword()!=null ){
				organizationForm.setChangePassword(organisation.getChangePassword());
				}
				if(organisation.getTimeLimit() != null && organisation.getTimeLimit() !=0){
					organizationForm.setTimeLimit(String.valueOf(organisation.getTimeLimit()));
				}
				organizationForm.setOpenHonoursCourseLink(organisation.getOpenHonoursCourseLink());
				if(organisation.getConvocationRegistration()!=null)
				    organizationForm.setConvocationRegistration(organisation.getConvocationRegistration());
				if(organisation.getStudentPhotoUpload()!=null)
					organizationForm.setStudentPhotoUpload(organisation.getStudentPhotoUpload());
			}
		}
		
		/**
		 * Used to update Organization Details
		 * Constructs a BO object for update 
		 * @param request 
		 */
		public Organisation populateTOToBOWhileUpdate(OrganizationTO newOrganizationTO, Organisation oldBO, HttpServletRequest request)throws Exception{
			Organisation newOrganisationBO = null;
			if(newOrganizationTO!=null && oldBO != null){
				/* added by sudhir*/
				String imageURL = request.getRealPath("/")+"images/Logos/";
				File f = new File(imageURL);
				/* -----------------*/
				newOrganisationBO = new Organisation();
				newOrganisationBO.setId(newOrganizationTO.getId());
				newOrganisationBO.setName(newOrganizationTO.getOrganizationName()!=null ? newOrganizationTO.getOrganizationName():null);
				newOrganisationBO.setAddressLine1(newOrganizationTO.getAddressLine1()!=null ? newOrganizationTO.getAddressLine1():null);
				newOrganisationBO.setAddressLine2(newOrganizationTO.getAddressLine2()!=null ? newOrganizationTO.getAddressLine2():null);
				newOrganisationBO.setAddressLine3(newOrganizationTO.getAddressLine3()!=null ? newOrganizationTO.getAddressLine3():null);
				newOrganisationBO.setNeedFinalApprival(newOrganizationTO.isNeedApproval());
				newOrganisationBO.setFinalMeritlistApproval(newOrganizationTO.isFinalMeritListApproval());
				newOrganisationBO.setSameUseridPassword(newOrganizationTO.getSameUseridPassword());
				newOrganisationBO.setChangePassword(newOrganizationTO.getChangePassword());
				newOrganisationBO.setIsActive(true);
				newOrganisationBO.setModifiedBy(newOrganizationTO.getModifiedBy());
				newOrganisationBO.setLastModifiedDate(new Date());
				newOrganisationBO.setOpenHonoursCourseLink(newOrganizationTO.isOpenHonoursCourseLink());
				//Sets new logo to BO if user selected
				if(newOrganizationTO.getOrganizationLogo()!=null){
					newOrganisationBO.setLogo(newOrganizationTO.getOrganizationLogo().getFileData());
					newOrganisationBO.setLogoName(newOrganizationTO.getOrganizationLogo().getFileName());
					newOrganisationBO.setLogoContentType(newOrganizationTO.getOrganizationLogo().getContentType());
					if(newOrganisationBO.getLogo()!=null && newOrganisationBO.getLogo().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getLogo()));
					ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getLogoName()));
					}
				}
				//Otherwise keeps the old logo as it was earlier
				else{
					newOrganisationBO.setLogo(oldBO.getLogo()!=null ? oldBO.getLogo():null);
					newOrganisationBO.setLogoName(oldBO.getLogoName()!=null ? oldBO.getLogoName():null);
					newOrganisationBO.setLogoContentType(oldBO.getLogoContentType()!=null ? oldBO.getLogoContentType():null);
					
					if(newOrganisationBO.getLogo()!=null && newOrganisationBO.getLogo().length!=0){
						BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getLogo()));
						//ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getLogoName()));
					}
				}
				//Sets new TopBar to BO if user selected
				if(newOrganizationTO.getOrganizationtopBar()!=null){
					newOrganisationBO.setTopbar(newOrganizationTO.getOrganizationtopBar().getFileData());
					newOrganisationBO.setTopbarName(newOrganizationTO.getOrganizationtopBar().getFileName());
					newOrganisationBO.setTopbarContentType(newOrganizationTO.getOrganizationtopBar().getContentType());
					if(newOrganisationBO.getTopbar()!=null && newOrganisationBO.getTopbar().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getTopbar()));
					ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getTopbarName()));
					}
				}
				//Otherwise keeps the old topbar as it was earlier
				else{
					newOrganisationBO.setTopbar(oldBO.getTopbar()!=null ? oldBO.getTopbar():null);
					newOrganisationBO.setTopbarName(oldBO.getTopbarName()!=null ? oldBO.getTopbarName():null);
					newOrganisationBO.setTopbarContentType(oldBO.getTopbarContentType()!=null ? oldBO.getTopbarContentType():null);
					
					if(newOrganisationBO.getTopbar()!=null && newOrganisationBO.getTopbar().length!=0){
						BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getTopbar()));
						//ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getTopbarName()));
					}
				}
				//Sets new logo to BO if user selected
				if(newOrganizationTO.getOrganizationLogo1()!=null ){
					newOrganisationBO.setLogo1(newOrganizationTO.getOrganizationLogo1().getFileData());
					newOrganisationBO.setLogoName1(newOrganizationTO.getOrganizationLogo1().getFileName());
					newOrganisationBO.setLogoContentType1(newOrganizationTO.getOrganizationLogo1().getContentType());
					if(newOrganisationBO.getLogo1()!=null && newOrganisationBO.getLogo1().length!=0){
					BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getLogo1()));
					ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getLogoName1()));
					}
				}
				//Otherwise keeps the old logo as it was earlier
				else{
					newOrganisationBO.setLogo1(oldBO.getLogo1()!=null ? oldBO.getLogo1():null);
					newOrganisationBO.setLogoName1(oldBO.getLogoName1()!=null ? oldBO.getLogoName1():null);
					newOrganisationBO.setLogoContentType1(oldBO.getLogoContentType1()!=null ? oldBO.getLogoContentType1():null);
					
					if(newOrganisationBO.getLogo1()!=null && newOrganisationBO.getLogo1().length!=0){
						BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getLogo1()));
						//ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getLogoName1()));
					}
				}
				//Sets new TopBar to BO if user selected
				if(newOrganizationTO.getOrganizationtopBar1()!=null){
					newOrganisationBO.setTopbar1(newOrganizationTO.getOrganizationtopBar1().getFileData());
					newOrganisationBO.setTopbarName1(newOrganizationTO.getOrganizationtopBar1().getFileName());
					newOrganisationBO.setTopbarContentType1(newOrganizationTO.getOrganizationtopBar1().getContentType());
					if(newOrganisationBO.getTopbar1()!=null && newOrganisationBO.getTopbar1().length!=0){
						BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getTopbar1()));
						ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getTopbarName1()));
					}
				}
				//Otherwise keeps the old topbar as it was earlier
				else{
					newOrganisationBO.setTopbar1(oldBO.getTopbar1()!=null ? oldBO.getTopbar1():null);
					newOrganisationBO.setTopbarName1(oldBO.getTopbarName1()!=null ? oldBO.getTopbarName1():null);
					newOrganisationBO.setTopbarContentType1(oldBO.getTopbarContentType1()!=null ? oldBO.getTopbarContentType1():null);
					
					if(newOrganisationBO.getTopbar1()!=null && newOrganisationBO.getTopbar1().length!=0){
						BufferedImage imag=ImageIO.read(new ByteArrayInputStream(newOrganisationBO.getTopbar1()));
						ImageIO.write(imag, "jpg",new File(f,newOrganisationBO.getTopbarName1()));
					}
				}
				newOrganisationBO.setStudentPhotoUpload(newOrganizationTO.isStudentPhotoUpload());
				newOrganisationBO.setConvocationRegistration(newOrganizationTO.isConvocationRegistration());
			}
			return newOrganisationBO;
		}
}

