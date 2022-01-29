package com.kp.cms.helpers.phd;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.upload.FormFile;

import com.kp.cms.bo.admin.Nationality;
import com.kp.cms.bo.admin.QualificationLevelBO;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.admin.State;
import com.kp.cms.bo.phd.DisciplineBo;
import com.kp.cms.bo.phd.PhdEmpEducationalDetails;
import com.kp.cms.bo.phd.PhdEmpImages;
import com.kp.cms.bo.phd.PhdEmployee;
import com.kp.cms.bo.phd.PhdEmployeeExperience;
import com.kp.cms.exceptions.ApplicationException;
import com.kp.cms.forms.phd.PhdEmployeeForms;
import com.kp.cms.to.employee.EmpPreviousOrgTo;
import com.kp.cms.to.employee.EmpQualificationLevelTo;
import com.kp.cms.to.phd.PhdEmpImagesTO;
import com.kp.cms.to.phd.PhdEmployeeTo;
import com.kp.cms.utilities.CommonUtil;


public class PhdEmployeeHelpers {
	private static volatile PhdEmployeeHelpers instance=null;
	/**
	 * 
	 */
	private PhdEmployeeHelpers(){
		
	}
	/**
	 * @return
	 */
	public static PhdEmployeeHelpers getInstance(){
		if(instance==null){
			instance=new PhdEmployeeHelpers();
		}
		return instance;
	}
	/**
	 * @param objform
	 * @param request 
	 * @return
	 */
	public PhdEmployee convertFormToBo(PhdEmployeeForms objform) throws Exception{
		
		PhdEmployee employee=new PhdEmployee();
		Set<PhdEmpImages> empImages = getEmpImagesBoObjects(objform);
		employee.setEmpImages(empImages);
		Set<PhdEmployeeExperience> teachingExperienceSet=new HashSet<PhdEmployeeExperience>();
		Set<PhdEmpEducationalDetails> educationalDeatialSet=new HashSet<PhdEmpEducationalDetails>();
		try{
		
			
			if(objform.getPhdEmployeequalificationFixedTo()!=null){
				List<EmpQualificationLevelTo> qualificationFixedTo=objform.getPhdEmployeequalificationFixedTo();
				Iterator<EmpQualificationLevelTo> iterator=qualificationFixedTo.iterator();
				while(iterator.hasNext()){
					EmpQualificationLevelTo qualificationFixed=iterator.next();
					PhdEmpEducationalDetails educationalDeatails=null;
					if(qualificationFixed!=null){
						if((qualificationFixed.getDegree()!=null && !qualificationFixed.getDegree().isEmpty())
								|| (qualificationFixed.getNameOfUniversity()!=null && !qualificationFixed.getNameOfUniversity().isEmpty())
								|| (qualificationFixed.getQstate()!=null && !qualificationFixed.getQstate().isEmpty())
								|| (qualificationFixed.getPercentage()!=null && !qualificationFixed.getPercentage().isEmpty())
								||  (qualificationFixed.getAttempts()!=null && !qualificationFixed.getAttempts().isEmpty()))
						{
							educationalDeatails=new PhdEmpEducationalDetails();
							
							if(qualificationFixed.getId()>0){
								educationalDeatails.setId(qualificationFixed.getId());
							}
							
							if(qualificationFixed.getEducationId()!=null && !qualificationFixed.getEducationId().isEmpty()){
								QualificationLevelBO level=new QualificationLevelBO();
								level.setId(Integer.parseInt(qualificationFixed.getEducationId()));
								educationalDeatails.setPhdQualificationLevel(level);
							}
							
							if(qualificationFixed.getDegree()!=null && !qualificationFixed.getDegree().isEmpty()){
								educationalDeatails.setDegree(qualificationFixed.getDegree());
							}
							
							if(qualificationFixed.getNameOfUniversity()!=null && !qualificationFixed.getNameOfUniversity().isEmpty()){
								educationalDeatails.setUniversityName(qualificationFixed.getNameOfUniversity());
							}
							
							if(qualificationFixed.getYearOfComp()!=null && !qualificationFixed.getYearOfComp().trim().isEmpty()){
								educationalDeatails.setYearOfpassing(Integer.valueOf(qualificationFixed.getYearOfComp()));
							}
							
							if(qualificationFixed.getPercentage()!=null && !qualificationFixed.getPercentage().trim().isEmpty()){
								educationalDeatails.setPercentage(qualificationFixed.getPercentage());
							}
							
							if(qualificationFixed.getAttempts()!=null && !qualificationFixed.getAttempts().trim().isEmpty()){
								educationalDeatails.setAttempts(qualificationFixed.getAttempts());
							}
							if(qualificationFixed.getQstate()!=null && !qualificationFixed.getQstate().isEmpty()){
								State stat=new State();
								stat.setId(Integer.parseInt(qualificationFixed.getQstate()));
								educationalDeatails.setStateId(stat);
							}
						
							educationalDeatails.setIsActive(true);
							educationalDeatails.setCreatedBy(objform.getUserId());
							educationalDeatails.setCreatedDate(new Date());
							educationalDeatails.setModifiedBy(objform.getUserId());
							educationalDeatails.setModifiedDate(new Date());
							educationalDeatialSet.add(educationalDeatails);
						}
					}
				}
			}
			
			if(objform.getPhdEmployeequalificationTos()!=null){
				Iterator<EmpQualificationLevelTo> iterator=objform.getPhdEmployeequalificationTos().iterator();
				while(iterator.hasNext()){
					EmpQualificationLevelTo levelTo=iterator.next();
					PhdEmpEducationalDetails educationalDetails=null;
					if(levelTo!=null){
						if((levelTo.getDegree()!=null && !levelTo.getDegree().isEmpty())
								|| (levelTo.getNameOfUniversity()!=null && !levelTo.getNameOfUniversity().isEmpty())
								|| (levelTo.getQstate()!=null && !levelTo.getQstate().isEmpty())
								|| (levelTo.getPercentage()!=null && !levelTo.getPercentage().isEmpty())
								||  (levelTo.getAttempts()!=null && !levelTo.getAttempts().isEmpty()))
						{
							educationalDetails=new PhdEmpEducationalDetails();
							
							if(levelTo.getId()>0){
								educationalDetails.setId(levelTo.getId());
							}
							if(levelTo.getEducationId()!=null && !levelTo.getEducationId().isEmpty()){
								QualificationLevelBO level=new QualificationLevelBO();
								level.setId(Integer.parseInt(levelTo.getEducationId()));
								educationalDetails.setPhdQualificationLevel(level);
							}
							
							if(levelTo.getDegree()!=null && !levelTo.getDegree().isEmpty()){
								educationalDetails.setDegree(levelTo.getDegree());
							}
							
							if(levelTo.getNameOfUniversity()!=null && !levelTo.getNameOfUniversity().isEmpty()){
								educationalDetails.setUniversityName(levelTo.getNameOfUniversity());
							}
							
							if(levelTo.getYearOfComp()!=null && !levelTo.getYearOfComp().trim().isEmpty()){
								educationalDetails.setYearOfpassing(Integer.valueOf(levelTo.getYearOfComp()));
							}
							
							if(levelTo.getPercentage()!=null && !levelTo.getPercentage().trim().isEmpty()){
								educationalDetails.setPercentage(levelTo.getPercentage());
							}
							
							if(levelTo.getAttempts()!=null && !levelTo.getAttempts().trim().isEmpty()){
								educationalDetails.setAttempts(levelTo.getAttempts());
							}
							if(levelTo.getQstate()!=null && !levelTo.getQstate().isEmpty()){
								State stat=new State();
								stat.setId(Integer.parseInt(levelTo.getQstate()));
								educationalDetails.setStateId(stat);
							}
							
							educationalDetails.setIsActive(true);
							educationalDetails.setCreatedBy(objform.getUserId());
							educationalDetails.setCreatedDate(new Date());
							educationalDetails.setModifiedBy(objform.getUserId());
							educationalDetails.setModifiedDate(new Date());
							educationalDeatialSet.add(educationalDetails);
						}
					}
				}
			}
			
			employee.setEducationalDetailsSet(educationalDeatialSet);
			
			

			if(objform.getTeachingExperience()!=null){
				List<EmpPreviousOrgTo> list=objform.getTeachingExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						PhdEmployeeExperience phdEmpExp=new PhdEmployeeExperience();
						if(empPreviousOrgTo!=null){
							if((empPreviousOrgTo.gettNameOfInstitution()!=null && !empPreviousOrgTo.gettNameOfInstitution().isEmpty())
									||(empPreviousOrgTo.gettNameOfUniversity()!=null && !empPreviousOrgTo.gettNameOfUniversity().isEmpty())
									|| (empPreviousOrgTo.gettSubject()!=null && !empPreviousOrgTo.gettSubject().isEmpty())
									|| (empPreviousOrgTo.gettYearsOfExpe()!=null && !empPreviousOrgTo.gettYearsOfExpe().isEmpty())){
								
								if(empPreviousOrgTo.getId()>0){
									phdEmpExp.setId(empPreviousOrgTo.getId());
								}
								
								if(empPreviousOrgTo.gettNameOfInstitution()!=null && !empPreviousOrgTo.gettNameOfInstitution().isEmpty()){
									phdEmpExp.settNameOfInstitution(empPreviousOrgTo.gettNameOfInstitution());
								}
								
								if(empPreviousOrgTo.gettNameOfUniversity()!=null && !empPreviousOrgTo.gettNameOfUniversity().isEmpty()){
									phdEmpExp.settNameOfUniversity(empPreviousOrgTo.gettNameOfUniversity());
								}
								
								if(empPreviousOrgTo.gettSubject()!=null && !empPreviousOrgTo.gettSubject().isEmpty()){
									phdEmpExp.settSubject(empPreviousOrgTo.gettSubject());
								}
								
								if(empPreviousOrgTo.gettYearsOfExpe()!=null && !empPreviousOrgTo.gettYearsOfExpe().isEmpty()){
									phdEmpExp.settYearsOfExpe(empPreviousOrgTo.gettYearsOfExpe());
								}
								
								phdEmpExp.setTeachingExperience(true);
								phdEmpExp.setResearchExperience(false);
								phdEmpExp.setResearchpublication(false);
								phdEmpExp.setIsActive(true);
								phdEmpExp.setCreatedBy(objform.getUserId());
								phdEmpExp.setCreatedDate(new Date());
								phdEmpExp.setModifiedBy(objform.getUserId());
								phdEmpExp.setModifiedDate(new Date());
								teachingExperienceSet.add(phdEmpExp);
							}
						}
					}
				}
				
			}
		
			if(objform.getResearchExperience()!=null){
				List<EmpPreviousOrgTo> list=objform.getResearchExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						PhdEmployeeExperience phdEmpExp=new PhdEmployeeExperience();
						if(empPreviousOrgTo!=null){
							if((empPreviousOrgTo.getrNameOfInstitution()!=null && !empPreviousOrgTo.getrNameOfInstitution().isEmpty())
									||(empPreviousOrgTo.getrNameOfTheUniversity()!=null && !empPreviousOrgTo.getrNameOfTheUniversity().isEmpty())
									|| (empPreviousOrgTo.getrSubject()!=null && !empPreviousOrgTo.getrSubject().isEmpty())
									|| (empPreviousOrgTo.getrYearOfExper()!=null && !empPreviousOrgTo.getrYearOfExper().isEmpty())){
								
								if(empPreviousOrgTo.getId()>0){
									phdEmpExp.setId(empPreviousOrgTo.getId());
								}
								
								if(empPreviousOrgTo.getrNameOfInstitution()!=null && !empPreviousOrgTo.getrNameOfInstitution().isEmpty()){
									phdEmpExp.setrNameOfInstitution(empPreviousOrgTo.getrNameOfInstitution());
								}
								
								if(empPreviousOrgTo.getrNameOfTheUniversity()!=null && !empPreviousOrgTo.getrNameOfTheUniversity().isEmpty()){
									phdEmpExp.setrNameOfTheUniversity(empPreviousOrgTo.getrNameOfTheUniversity());
								}
								
								if(empPreviousOrgTo.getrSubject()!=null && !empPreviousOrgTo.getrSubject().isEmpty()){
									phdEmpExp.setrSubject(empPreviousOrgTo.getrSubject());
								}
								
								if(empPreviousOrgTo.getrYearOfExper()!=null && !empPreviousOrgTo.getrYearOfExper().isEmpty()){
									phdEmpExp.setrYearOfExper(empPreviousOrgTo.getrYearOfExper());
								}
								
								phdEmpExp.setTeachingExperience(false);
								phdEmpExp.setResearchExperience(true);
								phdEmpExp.setResearchpublication(false);
								phdEmpExp.setIsActive(true);
								phdEmpExp.setCreatedBy(objform.getUserId());
								phdEmpExp.setCreatedDate(new Date());
								phdEmpExp.setModifiedBy(objform.getUserId());
								phdEmpExp.setModifiedDate(new Date());
								teachingExperienceSet.add(phdEmpExp);
							}
						}
					}
				}
				
			}
			
			
			if(objform.getPublicationExperience()!=null){
				List<EmpPreviousOrgTo> list=objform.getPublicationExperience();
				if(list!=null){
					Iterator<EmpPreviousOrgTo> iterator=list.iterator();
					while(iterator.hasNext()){
						EmpPreviousOrgTo empPreviousOrgTo=iterator.next();
						PhdEmployeeExperience phdEmpExp=new PhdEmployeeExperience();
						if(empPreviousOrgTo!=null){
							if((empPreviousOrgTo.getpNameOfTitles()!=null && !empPreviousOrgTo.getpNameOfTitles().isEmpty())
									||(empPreviousOrgTo.getpJournalPubli()!=null && !empPreviousOrgTo.getpJournalPubli().isEmpty())
									|| (empPreviousOrgTo.getPyear()!=null && !empPreviousOrgTo.getPyear().isEmpty())){
								
								if(empPreviousOrgTo.getId()>0){
									phdEmpExp.setId(empPreviousOrgTo.getId());
								}
								
								if(empPreviousOrgTo.getpNameOfTitles()!=null && !empPreviousOrgTo.getpNameOfTitles().isEmpty()){
									phdEmpExp.setpNameOfTitles(empPreviousOrgTo.getpNameOfTitles());
								}
								
								if(empPreviousOrgTo.getpJournalPubli()!=null && !empPreviousOrgTo.getpJournalPubli().isEmpty()){
									phdEmpExp.setpJournalPubli(empPreviousOrgTo.getpJournalPubli());
								}
								
								if(empPreviousOrgTo.getPyear()!=null && !empPreviousOrgTo.getPyear().isEmpty()){
									phdEmpExp.setPyear(empPreviousOrgTo.getPyear());
								}
													
								phdEmpExp.setTeachingExperience(false);
								phdEmpExp.setResearchExperience(false);
								phdEmpExp.setResearchpublication(true);
								phdEmpExp.setIsActive(true);
								phdEmpExp.setCreatedBy(objform.getUserId());
								phdEmpExp.setCreatedDate(new Date());
								phdEmpExp.setModifiedBy(objform.getUserId());
								phdEmpExp.setModifiedDate(new Date());
								teachingExperienceSet.add(phdEmpExp);
							}
						}
					}
				}
				
			}
			employee.setTeachingExperience(teachingExperienceSet);
		
	    if(objform.getId()>0){
	    	employee.setId(objform.getId());
	    }
		if(objform.getName()!=null && !objform.getName().isEmpty()){
			employee.setName(objform.getName().toUpperCase());
		}
		if(objform.getGender()!=null && !objform.getGender().isEmpty()){
			employee.setGender(objform.getGender());
		}
		if(objform.getEmpanelmentNo()!=null && !objform.getEmpanelmentNo().isEmpty()){
			employee.setEmpanelmentNo(objform.getEmpanelmentNo());
		}
		if(objform.getDateOfBirth()!=null && !objform.getDateOfBirth().isEmpty()){
			employee.setDateOfBirth(CommonUtil.ConvertStringToDate(objform.getDateOfBirth()));
		}
		if(objform.getPlaceOfBirth()!=null && !objform.getPlaceOfBirth().isEmpty()){
			employee.setPlaceOfBirth(objform.getPlaceOfBirth());
		}
		if(objform.getNationalityId()!=null && !objform.getNationalityId().isEmpty()){
			Nationality nationality=new Nationality();
			nationality.setId(Integer.parseInt(objform.getNationalityId()));
			employee.setNationality(nationality);
		}
		if(objform.getReligionId()!=null && !objform.getReligionId().isEmpty()){
			Religion empReligion=new Religion();
			empReligion.setId(Integer.parseInt(objform.getReligionId()));
			employee.setReligion(empReligion);
		}
		if(objform.getBloodGroup()!=null && !objform.getBloodGroup().isEmpty()){
			employee.setBloodGroup(objform.getBloodGroup());
		}
		if(objform.getDomicialStatus()!=null && !objform.getDomicialStatus().isEmpty()){
			employee.setDomicialStatus(objform.getDomicialStatus());
		}
		if(objform.getPassPortNo()!=null && !objform.getPassPortNo().isEmpty()){
			employee.setPassPortNo(objform.getPassPortNo());
		}
		if(objform.getPanNo()!=null && !objform.getPanNo().isEmpty()){
			employee.setPanNo(objform.getPanNo());
		}
		if(objform.getEmail()!=null && !objform.getEmail().isEmpty()){
				employee.setEmail(objform.getEmail());
		}
		if(objform.getDateOfAward()!=null && !objform.getDateOfAward().isEmpty()){
			employee.setDateOfAward(CommonUtil.ConvertStringToDate(objform.getDateOfAward()));
		}
		if(objform.getSubjectGuideShip()!=null && !objform.getSubjectGuideShip().isEmpty()){
			DisciplineBo disciplineBo=new DisciplineBo();
			disciplineBo.setId(Integer.parseInt(objform.getSubjectGuideShip()));
			employee.setSubjectGuideship(disciplineBo);
		}
		if(objform.getNoOfresearch()!=null && !objform.getNoOfresearch().isEmpty()){
			employee.setNoOfresearch(objform.getNoOfresearch());
	    }
		if(objform.getNoOfBookAuthored()!=null && !objform.getNoOfBookAuthored().isEmpty()){
		employee.setNoOfBookAuthored(objform.getNoOfBookAuthored());
	    }
		if(objform.getNameAddress()!=null && !objform.getNameAddress().isEmpty()){
			employee.setNameAddress(objform.getNameAddress());
	    }
		if(objform.getDepartmentId()!=null && !objform.getDepartmentId().isEmpty()){
			employee.setDepartment(objform.getDepartmentId());
		}
		if(objform.getDesiginitionId()!=null && !objform.getDesiginitionId().isEmpty()){
			employee.setDesignation(objform.getDesiginitionId());
		}
		if(objform.getYearOfExp()!=null && !objform.getYearOfExp().isEmpty()){
			employee.setYearOfExp(objform.getYearOfExp());
	    }
		if(objform.getPermanentAddress()!=null && !objform.getPermanentAddress().isEmpty()){
			employee.setPermanentAddress(objform.getPermanentAddress());
	    }
		
		if(objform.getpAddressPhonNo()!=null && !objform.getpAddressPhonNo().isEmpty()){
			employee.setpAddressPhonNo(objform.getpAddressPhonNo());
		}
		
		if(objform.getContactAddress()!=null && !objform.getContactAddress().isEmpty()){
			employee.setContactAddress(objform.getContactAddress());
		}
		
		if(objform.getcAddressPhonNo()!=null && !objform.getcAddressPhonNo().isEmpty()){
			employee.setcAddressPhonNo(objform.getcAddressPhonNo());
		}
		if(objform.getBankName()!=null && !objform.getBankName().isEmpty()){
			employee.setBankName(objform.getBankName());
		}
		if(objform.getBankAccNo()!=null && !objform.getBankAccNo().isEmpty()){
			employee.setBankAccNo(objform.getBankAccNo());
		}
		if(objform.getBankBranch()!=null && !objform.getBankBranch().isEmpty()){
			employee.setBankBranch(objform.getBankBranch());
		}
			employee.setNoMphilScolars(objform.getNoMphilScolars());
			employee.setNoPhdScolars(objform.getNoPhdScolars());
			employee.setNoPhdScolarOutside(objform.getNoPhdScolarOutside());
			
		}catch (Exception e) {
			throw new ApplicationException(e);
		}
		employee.setIsActive(true);
		employee.setCreatedBy(objform.getUserId());
		employee.setCreatedDate(new Date());
		
		return employee;
	}
	/**
	 * @param objform
	 * @param request 
	 * @return
	 * @throws Exception
	 */
	private Set<PhdEmpImages> getEmpImagesBoObjects(PhdEmployeeForms objform) throws Exception{
		
		Set<PhdEmpImages> images = new HashSet<PhdEmpImages>();
		PhdEmpImages img = new PhdEmpImages();
		if (objform.getEmpPhoto() != null && objform.getEmpPhoto().getFileSize()>0) {
			if(objform.getEmpImageId()!=null && Integer.parseInt(objform.getEmpImageId())>0){
				img.setId(Integer.parseInt(objform.getEmpImageId()));
			}
				FormFile file = objform.getEmpPhoto();
				byte[] data = file.getFileData();
				if (data.length > 0) {
					img.setPhdEmpPhoto(data);
				}
			
			img.setCreatedBy(objform.getUserId());
			img.setCreatedDate(new Date());
			img.setModifiedBy(objform.getUserId());
			img.setLastModifiedDate(new Date());
			
			images.add(img);
		}else if(objform.getEmpPhoto()==null || objform.getEmpPhoto().getFileSize()<=0){
			 if(objform.getPhotoBytes()!=null){
				if(objform.getPhotoBytes().length>0){
				img.setPhdEmpPhoto(objform.getPhotoBytes());
				if(objform.getEmpImageId()!=null && Integer.parseInt(objform.getEmpImageId())>0){
					img.setId(Integer.parseInt(objform.getEmpImageId()));
				}
				img.setCreatedBy(objform.getUserId());
				img.setCreatedDate(new Date());
				img.setModifiedBy(objform.getUserId());
				img.setLastModifiedDate(new Date());
				images.add(img);
			  }
			}
		}
		
	
    return images;
}
	/**
	 * @param phdBoList
	 * @return
	 * @throws Exception
	 */
	public List<PhdEmployeeTo> convertBosToTOs(List<PhdEmployee> phdBoList) throws Exception{
		ArrayList<PhdEmployeeTo> phdTolist=new ArrayList<PhdEmployeeTo>();
		Iterator<PhdEmployee> itr=phdBoList.iterator();
		while (itr.hasNext()) {
			PhdEmployee phdBo = (PhdEmployee) itr.next();
			PhdEmployeeTo phdTo=new PhdEmployeeTo();
			
			if(phdBo.getId()>0 && !phdBo.getName().isEmpty()){
				phdTo.setId(phdBo.getId());				
			}
			
			if(phdBo.getName()!=null && !phdBo.getName().isEmpty()){
				phdTo.setName(phdBo.getName());				
			}
			if(phdBo.getGender()!=null && !phdBo.getGender().isEmpty()){
				phdTo.setGender(phdBo.getGender());				
			}
			if(phdBo.getEmpanelmentNo()!=null && !phdBo.getEmpanelmentNo().isEmpty()){
				phdTo.setEmpanelmentNo(phdBo.getEmpanelmentNo());				
			}
			if(phdBo.getDateOfBirth()!=null){
				String date = formatDate(phdBo.getDateOfBirth());
				phdTo.setDateOfBirth(CommonUtil.formatSqlDate1(date));				
			}
			if(phdBo.getPlaceOfBirth()!=null && !phdBo.getPlaceOfBirth().isEmpty()){
				phdTo.setPlaceOfBirth(phdBo.getPlaceOfBirth());				
			}
			if(phdBo.getBloodGroup()!=null && !phdBo.getBloodGroup().isEmpty()){
				phdTo.setBloodGroup(phdBo.getBloodGroup());				
			}
			if(phdBo.getDomicialStatus()!=null && !phdBo.getDomicialStatus().isEmpty()){
				phdTo.setDomicialStatus(phdBo.getDomicialStatus());				
			}
			if(phdBo.getEmail()!=null && !phdBo.getEmail().isEmpty()){
				phdTo.setEmail(phdBo.getEmail());				
			}
			if(phdBo.getPanNo()!=null && !phdBo.getPanNo().isEmpty()){
				phdTo.setPanNo(phdBo.getPanNo());				
			}
			if(phdBo.getDateOfAward()!=null){
				String date = formatDate(phdBo.getDateOfAward());
				phdTo.setDateOfAward(CommonUtil.formatSqlDate1(date));		
			}
			if(phdBo.getContactAddress()!=null && !phdBo.getContactAddress().isEmpty()){
				phdTo.setContactAddress(phdBo.getContactAddress());				
			}
			if(phdBo.getcAddressPhonNo()!=null && !phdBo.getcAddressPhonNo().isEmpty()){
				phdTo.setcAddressPhonNo(phdBo.getcAddressPhonNo());				
			}
			phdTolist.add(phdTo);
			
		}
		return phdTolist;
	}
	/**
	 * @param objform
	 * @param phdBO
	 * @throws Exception
	 */
	public void setDataBoToForm(PhdEmployeeForms objform, PhdEmployee phdBO) throws Exception{
		
		if(phdBO.getId()>0){
			objform.setId(phdBO.getId());
		}
		if(phdBO.getName()!=null && !phdBO.getName().isEmpty()){
			objform.setName(phdBO.getName());
		}
		if(phdBO.getGender()!=null && !phdBO.getGender().isEmpty()){
			objform.setGender(phdBO.getGender());
		}
		if(phdBO.getEmpanelmentNo()!=null && !phdBO.getEmpanelmentNo().isEmpty()){
			objform.setEmpanelmentNo(phdBO.getEmpanelmentNo());
		}
		if(phdBO.getDateOfBirth()!=null){
			objform.setDateOfBirth(CommonUtil.ConvertStringToDateFormat(phdBO.getDateOfBirth().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if(phdBO.getPlaceOfBirth()!=null && !phdBO.getPlaceOfBirth().isEmpty()){
			objform.setPlaceOfBirth(phdBO.getPlaceOfBirth());
		}
		if(phdBO.getNationality()!=null){
			objform.setNationalityId(Integer.toString(phdBO.getNationality().getId()));
		}
		if(phdBO.getReligion()!=null ){
			objform.setReligionId(Integer.toString(phdBO.getReligion().getId()));
		}
		if(phdBO.getBloodGroup()!=null && !phdBO.getBloodGroup().isEmpty()){
			objform.setBloodGroup(phdBO.getBloodGroup());
		}
		if(phdBO.getDomicialStatus()!=null && !phdBO.getDomicialStatus().isEmpty()){
			objform.setDomicialStatus(phdBO.getDomicialStatus());
		}
		if(phdBO.getPassPortNo()!=null && !phdBO.getPassPortNo().isEmpty()){
			objform.setPassPortNo(phdBO.getPassPortNo());
		}
		if(phdBO.getPanNo()!=null && !phdBO.getPanNo().isEmpty()){
			objform.setPanNo(phdBO.getPanNo());
		}
		if(phdBO.getEmail()!=null && !phdBO.getEmail().isEmpty()){
			objform.setEmail(phdBO.getEmail());
		}
		if(phdBO.getDateOfAward()!=null){
			objform.setDateOfAward(CommonUtil.ConvertStringToDateFormat(phdBO.getDateOfAward().toString(), "yyyy-mm-dd","dd/mm/yyyy"));
		}
		if(phdBO.getNoOfresearch()!=null && !phdBO.getNoOfresearch().isEmpty()){
			objform.setNoOfresearch(phdBO.getNoOfresearch());
		}
		if(phdBO.getNoOfBookAuthored()!=null && !phdBO.getNoOfBookAuthored().isEmpty()){
			objform.setNoOfBookAuthored(phdBO.getNoOfBookAuthored());
		}
		if(phdBO.getBankName()!=null && !phdBO.getBankName().isEmpty()){
			objform.setBankName(phdBO.getBankName());
		}
		if(phdBO.getBankAccNo()!=null && !phdBO.getBankAccNo().isEmpty()){
			objform.setBankAccNo(phdBO.getBankAccNo());
		}
		if(phdBO.getBankBranch()!=null && !phdBO.getBankBranch().isEmpty()){
			objform.setBankBranch(phdBO.getBankBranch());
		}
		if(phdBO.getSubjectGuideship()!=null){
			objform.setSubjectGuideShip(Integer.toString(phdBO.getSubjectGuideship().getId()));
		}
		if(phdBO.getNameAddress()!=null && !phdBO.getNameAddress().isEmpty()){
			objform.setNameAddress(phdBO.getNameAddress());
		}
		objform.setNoMphilScolars(phdBO.getNoMphilScolars());
		objform.setNoPhdScolars(phdBO.getNoPhdScolars());
		objform.setNoPhdScolarOutside(phdBO.getNoPhdScolarOutside());
		if(phdBO.getDepartment()!=null && !phdBO.getDepartment().isEmpty()){
			objform.setDepartmentId(phdBO.getDepartment());
		}
		if(phdBO.getDesignation()!=null && !phdBO.getDesignation().isEmpty()){
			objform.setDesiginitionId(phdBO.getDesignation());
		}
		if(phdBO.getYearOfExp()!=null && !phdBO.getYearOfExp().isEmpty()){
			objform.setYearOfExp(phdBO.getYearOfExp());
		}
		if(phdBO.getPermanentAddress()!=null && !phdBO.getPermanentAddress().isEmpty()){
			objform.setPermanentAddress(phdBO.getPermanentAddress());
		}
		if(phdBO.getpAddressPhonNo()!=null && !phdBO.getpAddressPhonNo().isEmpty()){
			objform.setpAddressPhonNo(phdBO.getpAddressPhonNo());
		}
		if(phdBO.getContactAddress()!=null && !phdBO.getContactAddress().isEmpty()){
			objform.setContactAddress(phdBO.getContactAddress());
		}
		if(phdBO.getcAddressPhonNo()!=null && !phdBO.getcAddressPhonNo().isEmpty()){
			objform.setcAddressPhonNo(phdBO.getcAddressPhonNo());
		}
		if(phdBO.getEducationalDetailsSet()!=null && !phdBO.getEducationalDetailsSet().isEmpty() ){
			List<EmpQualificationLevelTo> fixed=null;
			if(objform.getPhdEmployeequalificationFixedTo()!=null){
				if(objform.getPhdEmployeequalificationFixedTo()!=null){
					fixed=objform.getPhdEmployeequalificationFixedTo();
				}
				List<EmpQualificationLevelTo> level=new ArrayList<EmpQualificationLevelTo>();
				Set<PhdEmpEducationalDetails> educationalSet=phdBO.getEducationalDetailsSet();
				Iterator<PhdEmpEducationalDetails> iterator=educationalSet.iterator();
				while(iterator.hasNext()){
					PhdEmpEducationalDetails phdEmpEducationalDetails = (PhdEmpEducationalDetails) iterator.next();
					if(phdEmpEducationalDetails!=null){
						boolean flag=false;
						if(phdEmpEducationalDetails.getPhdQualificationLevel()!=null 
								&& phdEmpEducationalDetails.getPhdQualificationLevel().isFixedDisplay()!=null){
							flag=phdEmpEducationalDetails.getPhdQualificationLevel().isFixedDisplay();
							if(flag && fixed!=null){
								Iterator<EmpQualificationLevelTo> iterator2=fixed.iterator();
								while(iterator2.hasNext()){
									EmpQualificationLevelTo empQualificationLevelTo=iterator2.next();
									if(empQualificationLevelTo!=null && StringUtils.isNotEmpty(empQualificationLevelTo.getEducationId())){
										if(phdEmpEducationalDetails.getPhdQualificationLevel().getId()>0)
											if(empQualificationLevelTo.getEducationId().equalsIgnoreCase(String.valueOf(phdEmpEducationalDetails.getPhdQualificationLevel().getId()))){
												
												if (phdEmpEducationalDetails.getId()> 0) {
													empQualificationLevelTo.setId(phdEmpEducationalDetails.getId());
												}
												if (phdEmpEducationalDetails.getPhdQualificationLevel().getId()> 0) {
													empQualificationLevelTo.setEducationId(Integer.toString(phdEmpEducationalDetails.getPhdQualificationLevel().getId()));
												}
												if (phdEmpEducationalDetails.getDegree()!=null && !phdEmpEducationalDetails.getDegree().isEmpty()) {
													empQualificationLevelTo.setDegree(phdEmpEducationalDetails.getDegree());
												}
												if (phdEmpEducationalDetails.getUniversityName()!=null && !phdEmpEducationalDetails.getUniversityName().isEmpty()) {
													empQualificationLevelTo.setNameOfUniversity(phdEmpEducationalDetails.getUniversityName());
												}
												if (phdEmpEducationalDetails.getStateId()!=null && phdEmpEducationalDetails.getStateId().getId()>0) {
													empQualificationLevelTo.setQstate(Integer.toString(phdEmpEducationalDetails.getStateId().getId()));
												}
												if (phdEmpEducationalDetails.getPercentage()!=null && !phdEmpEducationalDetails.getPercentage().isEmpty()) {
													empQualificationLevelTo.setPercentage(phdEmpEducationalDetails.getPercentage());
												}
												if (phdEmpEducationalDetails.getYearOfpassing() > 0) {
													empQualificationLevelTo.setYearOfComp(String.valueOf(phdEmpEducationalDetails.getYearOfpassing()));
												}
												if (phdEmpEducationalDetails.getAttempts()!=null && !phdEmpEducationalDetails.getAttempts().isEmpty()) {
													empQualificationLevelTo.setAttempts(phdEmpEducationalDetails.getAttempts());
												}
												if(phdEmpEducationalDetails.getPhdQualificationLevel().getName()!=null && !phdEmpEducationalDetails.getPhdQualificationLevel().getName().isEmpty()){
													empQualificationLevelTo.setQualification(phdEmpEducationalDetails.getPhdQualificationLevel().getName());
												}
												if(phdEmpEducationalDetails.getPhdQualificationLevel().getDisplayOrder()>0){
													empQualificationLevelTo.setFixedDisplay(String.valueOf(phdEmpEducationalDetails.getPhdQualificationLevel().getDisplayOrder()));
												}
											}
									  }
								}
							}else{
								EmpQualificationLevelTo empQualificationLevelTo=new EmpQualificationLevelTo();
								   if (phdEmpEducationalDetails.getId()> 0) {
									empQualificationLevelTo.setId(phdEmpEducationalDetails.getId());
								    }
								    if (phdEmpEducationalDetails.getPhdQualificationLevel().getId()> 0) {
									empQualificationLevelTo.setEducationId(Integer.toString(phdEmpEducationalDetails.getPhdQualificationLevel().getId()));
								    }
							     	if (phdEmpEducationalDetails.getDegree()!=null && !phdEmpEducationalDetails.getDegree().isEmpty()) {
									empQualificationLevelTo.setDegree(phdEmpEducationalDetails.getDegree());
								    }
							     	if (phdEmpEducationalDetails.getUniversityName()!=null && !phdEmpEducationalDetails.getUniversityName().isEmpty()) {
										empQualificationLevelTo.setNameOfUniversity(phdEmpEducationalDetails.getUniversityName());
									}
									if (phdEmpEducationalDetails.getStateId()!=null && phdEmpEducationalDetails.getStateId().getId()>0) {
										empQualificationLevelTo.setQstate(Integer.toString(phdEmpEducationalDetails.getStateId().getId()));
									}
									if (phdEmpEducationalDetails.getPercentage()!=null && !phdEmpEducationalDetails.getPercentage().isEmpty()) {
										empQualificationLevelTo.setPercentage(phdEmpEducationalDetails.getPercentage());
									}
									if (phdEmpEducationalDetails.getYearOfpassing() > 0) {
										empQualificationLevelTo.setYearOfComp(String.valueOf(phdEmpEducationalDetails.getYearOfpassing()));
									}
									if (phdEmpEducationalDetails.getAttempts()!=null && !phdEmpEducationalDetails.getAttempts().isEmpty()) {
										empQualificationLevelTo.setAttempts(phdEmpEducationalDetails.getAttempts());
									}
									if(phdEmpEducationalDetails.getPhdQualificationLevel().getName()!=null && !phdEmpEducationalDetails.getPhdQualificationLevel().getName().isEmpty()){
										empQualificationLevelTo.setQualification(phdEmpEducationalDetails.getPhdQualificationLevel().getName());
									}
									if(phdEmpEducationalDetails.getPhdQualificationLevel().getDisplayOrder()>0){
										empQualificationLevelTo.setFixedDisplay(String.valueOf(phdEmpEducationalDetails.getPhdQualificationLevel().getDisplayOrder()));
									}
								level.add(empQualificationLevelTo);
							}
								
							}
						}
				}
				objform.setPhdEmployeequalificationTos(level);
			}
		}else
		{
			if(objform.getPhdEmployeequalificationFixedTo()!=null){
				Iterator<EmpQualificationLevelTo> iterator=objform.getPhdEmployeequalificationFixedTo().iterator();
				while(iterator.hasNext()){
					EmpQualificationLevelTo empQualificationLevelTo=iterator.next();
					if(empQualificationLevelTo!=null){
						empQualificationLevelTo.setDegree("");
						empQualificationLevelTo.setNameOfUniversity("");
						empQualificationLevelTo.setQstate("");
						empQualificationLevelTo.setPercentage("");
						empQualificationLevelTo.setYearOfComp("");
						empQualificationLevelTo.setAttempts("");
					}
				}
				
			}
		}
		
		if (phdBO.getEmpImages() != null && !phdBO.getEmpImages().isEmpty()) {
			Iterator<PhdEmpImages> itr=phdBO.getEmpImages().iterator();
			while (itr.hasNext()) {
				PhdEmpImages bo =itr.next();
				
				if(bo.getPhdEmpPhoto()!=null && bo.getPhdEmpPhoto().length >0){
					objform.setPhotoBytes(bo.getPhdEmpPhoto());
					objform.setEmpImageId(String.valueOf(bo.getId()));
					break;
				}
			}
			
		}
		
		if (phdBO.getEmpImages() != null && !phdBO.getEmpImages().isEmpty()) {
			Set<PhdEmpImages> empImages = phdBO.getEmpImages();
			if (empImages != null && !empImages.isEmpty()) {
				Iterator<PhdEmpImages> iterator = empImages.iterator();
				List<PhdEmpImagesTO> empImagesTOs = new ArrayList<PhdEmpImagesTO>();

				while (iterator.hasNext()) {
					PhdEmpImages eImages = iterator.next();
					if (eImages != null) {
						PhdEmpImagesTO eImagesTO = new PhdEmpImagesTO();
						if (eImages.getId() > 0) {
							eImagesTO.setId(eImages.getId());
						}
						if (eImages.getPhdEmpPhoto() != null && eImages.getPhdEmpPhoto().length > 0)
						 {							
							byte[] myFileBytes = eImages.getPhdEmpPhoto();
							objform.setPhotoBytes(myFileBytes);
						}
						empImagesTOs.add(eImagesTO);

					}
				}			
				objform.setEmpImages(empImagesTOs);
			}
		}else{
			objform.setEmpImageId(null);
			objform.setPhotoBytes(null);
			objform.setEmpImages(null);
		}
		
		if (phdBO.getTeachingExperience() != null) {
			Set<PhdEmployeeExperience> experience = phdBO.getTeachingExperience();
			if (experience != null && !experience.isEmpty()) {
				Iterator<PhdEmployeeExperience> iterator = experience.iterator();
				List<EmpPreviousOrgTo> teachingExperience = new ArrayList<EmpPreviousOrgTo>();
				List<EmpPreviousOrgTo> researchExperience = new ArrayList<EmpPreviousOrgTo>();
				List<EmpPreviousOrgTo> researchpublication = new ArrayList<EmpPreviousOrgTo>();

				while (iterator.hasNext()) {
					PhdEmployeeExperience totalExperience = iterator.next();
					if (totalExperience != null) {
						EmpPreviousOrgTo teachingTo = new EmpPreviousOrgTo();
						EmpPreviousOrgTo researchTo = new EmpPreviousOrgTo();
						EmpPreviousOrgTo publicationTo = new EmpPreviousOrgTo();
						
						if(totalExperience.getTeachingExperience()){
						if (totalExperience.getId() > 0) {
							teachingTo.setId(totalExperience.getId());
						}
						if (StringUtils.isNotEmpty(totalExperience.gettNameOfUniversity())) {
							teachingTo.settNameOfUniversity(totalExperience.gettNameOfUniversity());
						}
						if (StringUtils.isNotEmpty(totalExperience.gettNameOfInstitution())) {
							teachingTo.settNameOfInstitution(totalExperience.gettNameOfInstitution());
						}
						if (StringUtils.isNotEmpty(totalExperience.gettSubject())) {
							teachingTo.settSubject(totalExperience.gettSubject());
						}
						if (StringUtils.isNotEmpty(totalExperience.gettYearsOfExpe())) {
							teachingTo.settYearsOfExpe(totalExperience.gettYearsOfExpe());
						}
						teachingExperience.add(teachingTo);
						}
						
						if(totalExperience.getResearchExperience()){
							if (totalExperience.getId() > 0) {
								researchTo.setId(totalExperience.getId());
							}
							if (StringUtils.isNotEmpty(totalExperience.getrNameOfTheUniversity())) {
								researchTo.setrNameOfTheUniversity(totalExperience.getrNameOfTheUniversity());
							}
							if (StringUtils.isNotEmpty(totalExperience.getrNameOfInstitution())) {
								researchTo.setrNameOfInstitution(totalExperience.getrNameOfInstitution());
							}
							if (StringUtils.isNotEmpty(totalExperience.getrSubject())) {
								researchTo.setrSubject(totalExperience.getrSubject());
							}
							if (StringUtils.isNotEmpty(totalExperience.getrYearOfExper())) {
								researchTo.setrYearOfExper(totalExperience.getrYearOfExper());
							}
							researchExperience.add(researchTo);
							}
						
						if(totalExperience.getResearchpublication()){
							if (totalExperience.getId() > 0) {
								publicationTo.setId(totalExperience.getId());
							}
							if (StringUtils.isNotEmpty(totalExperience.getpNameOfTitles())) {
								publicationTo.setpNameOfTitles(totalExperience.getpNameOfTitles());
							}
							if (StringUtils.isNotEmpty(totalExperience.getpJournalPubli())) {
								publicationTo.setpJournalPubli(totalExperience.getpJournalPubli());
							}
							if (StringUtils.isNotEmpty(totalExperience.getPyear())) {
								publicationTo.setPyear(totalExperience.getPyear());
							}
							researchpublication.add(publicationTo);
							}
					}
				}
				
				objform.setTeachingExperience(teachingExperience);
				objform.setResearchExperience(researchExperience);
				objform.setPublicationExperience(researchpublication);
				
			} else {
				EmpPreviousOrgTo empPreviousOrgTo=new EmpPreviousOrgTo();
				
				List<EmpPreviousOrgTo> teachingList=new ArrayList<EmpPreviousOrgTo>();
				empPreviousOrgTo.settNameOfUniversity("");
				empPreviousOrgTo.settNameOfInstitution("");
				empPreviousOrgTo.settSubject("");
				empPreviousOrgTo.settYearsOfExpe("");
				objform.setTeachingExpLength(String.valueOf(teachingList.size()));
				teachingList.add(empPreviousOrgTo);
				objform.setTeachingExperience(teachingList);
				
				List<EmpPreviousOrgTo> researchList=new ArrayList<EmpPreviousOrgTo>();
				empPreviousOrgTo.setrNameOfTheUniversity("");
				empPreviousOrgTo.setrNameOfInstitution("");
				empPreviousOrgTo.setrSubject("");
				empPreviousOrgTo.setrYearOfExper("");
				objform.setResearchlength(String.valueOf(researchList.size()));
				researchList.add(empPreviousOrgTo);
				objform.setResearchExperience(researchList);
				
				List<EmpPreviousOrgTo> publicationList=new ArrayList<EmpPreviousOrgTo>();
				empPreviousOrgTo.setpNameOfTitles("");
				empPreviousOrgTo.setpJournalPubli("");
				empPreviousOrgTo.setPyear("");
				objform.setPublicationLength(String.valueOf(publicationList.size()));
				publicationList.add(empPreviousOrgTo);
				objform.setPublicationExperience(publicationList);
			}
		}
		
	}
	public String formatDate(Date date){
		DateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		String newDate=formatter.format(date);
		return newDate;
	}
}
