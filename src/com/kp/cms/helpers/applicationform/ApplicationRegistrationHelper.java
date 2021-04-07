package com.kp.cms.helpers.applicationform;

import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.zefer.html.doc.i;

import com.kp.cms.bo.admin.CandidateMarks;
import com.kp.cms.bo.admin.Caste;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.admin.EdnQualification;
import com.kp.cms.bo.admin.Religion;
import com.kp.cms.bo.applicationform.ProfessorBooksPublificationDetailsBO;
import com.kp.cms.bo.applicationform.ProfessorEducationDetailsBO;
import com.kp.cms.bo.applicationform.ProfessorEducationQualificationDetailsBO;
import com.kp.cms.bo.applicationform.ProfessorPGIDetails;
import com.kp.cms.bo.applicationform.ProfessorPersonalData;
import com.kp.cms.bo.applicationform.ProfessorPostDoctoralExpDetailsBO;
import com.kp.cms.bo.applicationform.ProfessorQualificationBO;
import com.kp.cms.bo.applicationform.ProfessorTeachingExpDetailsBO;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.exceptions.BusinessException;
import com.kp.cms.forms.applicationform.ApplicationRegistrationForm;
import com.kp.cms.handlers.usermanagement.StudentLoginHandler;
import com.kp.cms.to.admin.EdnQualificationTO;
import com.kp.cms.to.admin.StudentTO;
import com.kp.cms.to.applicationform.ProfessorBooksandArticlesTO;
import com.kp.cms.to.applicationform.ProfessorEducationDetailsTO;
import com.kp.cms.to.applicationform.ProfessorPersonalDataTO;
import com.kp.cms.to.applicationform.ProfessorPostDoctoralExperTO;
import com.kp.cms.to.applicationform.ProfessorQualificationTO;
import com.kp.cms.to.applicationform.ProfessorTeachingExperTO;
import com.kp.cms.transactions.applicationform.IApplicationRegistrationTransaction;
import com.kp.cms.transactionsimpl.applicationform.ApplicationRegistrationHandlerTxnImp;
import com.kp.cms.utilities.CommonUtil;

public class ApplicationRegistrationHelper {
	
	private static volatile ApplicationRegistrationHelper applicationRegistrationHelper = null;
	private static final Log log = LogFactory.getLog(ApplicationRegistrationHelper.class);
	/**
	 * return singleton object of ScoreSheetHandler.
	 * @return
	 */
	public static ApplicationRegistrationHelper getInstance() {
		if (applicationRegistrationHelper == null) {
			applicationRegistrationHelper = new ApplicationRegistrationHelper();
		}
		return applicationRegistrationHelper;
	}
	public List<ProfessorEducationDetailsTO> convertQualDetailsBoToTo(List<ProfessorQualificationBO> quaList) throws Exception{
		
		List<ProfessorEducationDetailsTO> eduList=new LinkedList<ProfessorEducationDetailsTO>();
		
		Iterator<ProfessorQualificationBO> itr=quaList.iterator();
		
		while (itr.hasNext()) {
			ProfessorQualificationBO qua=itr.next();
			
			ProfessorEducationDetailsTO edu=new ProfessorEducationDetailsTO();
			edu.setQualificationId(qua.getId());
			edu.setQualificationName(qua.getName());
			edu.setIsMphil(false);
			edu.setIsPhd(false);
			eduList.add(edu);
			
		}
		return eduList;
	}
	public ProfessorPersonalData convertTOToBO(ApplicationRegistrationForm registrationForm) throws Exception{
		
		ProfessorPersonalData profData=new ProfessorPersonalData();
		
		if(registrationForm.getFirstName()!=null && !registrationForm.getFirstName().isEmpty())
			profData.setFirstName(registrationForm.getFirstName());
		
		if(registrationForm.getDepartmentId()!=null && !registrationForm.getDepartmentId().isEmpty()){
			Department dept=new Department();
			dept.setId(Integer.valueOf(registrationForm.getDepartmentId()));
			profData.setDepartmentId(dept);
		}else {
			profData.setDepartmentId(null);
		}
		
		if(registrationForm.getEmailId()!=null && !registrationForm.getEmailId().isEmpty())
			profData.setEmailId(registrationForm.getEmailId());
			
		if(registrationForm.getMobileNo()!=null && !registrationForm.getMobileNo().isEmpty())
			profData.setMobileNo(registrationForm.getMobileNo());
		
		if(registrationForm.getDateOfBirth()!=null && !registrationForm.getDateOfBirth().isEmpty())
			profData.setDateOfBirth(CommonUtil.ConvertStringToSQLDate(registrationForm.getDateOfBirth()));
		
		if(registrationForm.getAge()!=null && !registrationForm.getAge().isEmpty())
			profData.setAge(Integer.valueOf(registrationForm.getAge()));
		
		if(registrationForm.getMaritalStatus()!=null && !registrationForm.getMaritalStatus().isEmpty())
		    profData.setMaritalStatus(registrationForm.getMaritalStatus());
		
		if(registrationForm.getReligionId()!=null && !registrationForm.getReligionId().isEmpty()){
			Religion rel=new Religion();
			rel.setId(Integer.valueOf(registrationForm.getReligionId()));
			profData.setReligionId(rel);
		}else {
			profData.setReligionId(null);
		}
		
		if(registrationForm.getCasteId()!=null && !registrationForm.getCasteId().isEmpty()){
			Caste cas=new Caste();
			cas.setId(Integer.valueOf(registrationForm.getCasteId()));
			profData.setCasteId(cas);
		}else {
			profData.setCasteId(null);
		}
		
		if(registrationForm.getDiocese()!=null && !registrationForm.getDiocese().isEmpty()){
			profData.setDiocese(registrationForm.getDiocese());
		}else {
			profData.setDiocese(null);
		}
		if(registrationForm.getAddress()!=null && !registrationForm.getAddress().isEmpty()){
			profData.setAddress(registrationForm.getAddress());
		}else {
			profData.setAddress(null);
		}
		if(registrationForm.getCategory()!=null){
			profData.setCategory(registrationForm.getCategory());
		}
		
		if(registrationForm.getEditDocument()!=null && registrationForm.getEditDocument().getFileName()!=null && !StringUtils.isEmpty(registrationForm.getEditDocument().getFileName())){
			
			FormFile editDoc=registrationForm.getEditDocument();
			profData.setDocument(editDoc.getFileData());
			profData.setIsPhoto(true);
			profData.setFileName(editDoc.getFileName());
				
		}

		
		
		profData.setCreatedDate(new Date());
		
		setEducationDetails(profData,registrationForm);
		
		return profData;
	}

	private void setEducationDetails(ProfessorPersonalData profData,
			ApplicationRegistrationForm registrationForm) throws Exception {
		Set<ProfessorEducationDetailsBO> educationdDetails=new HashSet<ProfessorEducationDetailsBO>();
		
		if(profData!=null){
			ProfessorEducationDetailsBO bo=new ProfessorEducationDetailsBO();
			if(registrationForm.getIsNetQualification()!=null && registrationForm.getIsNetQualification()){
				bo.setIsNet(true);
				bo.setNetDetails(registrationForm.getNetDeatils());
			}else{
				bo.setIsNet(false);
				bo.setNetDetails(null);
			}
			
			if(registrationForm.getIsJrfQualification()!=null && registrationForm.getIsJrfQualification()){
				bo.setIsJrf(true);
				bo.setJrfDetails(registrationForm.getJrfDeatils());
			}else{
				bo.setIsJrf(false);
				bo.setJrfDetails(null);
			}
			
			if(registrationForm.getAdditionalInformation()!=null && !registrationForm.getAdditionalInformation().isEmpty()){
				bo.setAdditionalInformation(registrationForm.getAdditionalInformation());
			}
			if(registrationForm.getUgcApprovedInformation()!=null && !registrationForm.getUgcApprovedInformation().isEmpty()){
				bo.setUgcApprovedInformation(registrationForm.getUgcApprovedInformation());
			}
			
			Set<ProfessorEducationQualificationDetailsBO> educationQualificationDetails;
			
			if(registrationForm.getEducationalDetails()!=null && !registrationForm.getEducationalDetails().isEmpty()){
				educationQualificationDetails=new HashSet<ProfessorEducationQualificationDetailsBO>();
				List<ProfessorEducationDetailsTO> educationQualifications=registrationForm.getEducationalDetails();
				Iterator<ProfessorEducationDetailsTO> itr= educationQualifications.iterator();
				while (itr.hasNext()) {
					ProfessorEducationDetailsTO details=(ProfessorEducationDetailsTO) itr.next();
					ProfessorEducationQualificationDetailsBO qualificationBo=new ProfessorEducationQualificationDetailsBO();
					
					if(details.getQualificationId()!=0){
						ProfessorQualificationBO qualBo=new ProfessorQualificationBO();
						qualBo.setId(details.getQualificationId());
						qualificationBo.setProfessorQualificationId(qualBo);
					}
					if(details.getSubjectName()!=null && !details.getSubjectName().isEmpty()){
						qualificationBo.setSubjectName(details.getSubjectName());
					}
					if(details.getCollegeName()!=null && !details.getCollegeName().isEmpty()){
						qualificationBo.setCollegeName(details.getCollegeName());
					}
					if(details.getUniversityName()!=null && !details.getUniversityName().isEmpty()){
						qualificationBo.setUniversityName(details.getUniversityName());
					}
					if(details.getIsMphil()!=null){
						qualificationBo.setIsMphil(details.getIsMphil());
					}else{
						qualificationBo.setIsMphil(false);
					}
					if(details.getIsPhd()!=null){
						qualificationBo.setIsPhd(details.getIsPhd());
					}else{
						qualificationBo.setIsPhd(false);
					}
					
					if(details.getPercentage()!=null && !details.getPercentage().isEmpty()){
						qualificationBo.setPercentage(details.getPercentage());
					}
					
					if(details.getRankPosition()!=null && !details.getRankPosition().isEmpty()){
						bo.setRankPosition(details.getRankPosition());
					}
					
					educationQualificationDetails.add(qualificationBo);
				}
				bo.setProfessorEducationQualificationDetails(educationQualificationDetails);
			}
			
			
			Set<ProfessorBooksPublificationDetailsBO> booksandArticles;
			
			if(registrationForm.getBooksAndArticles()!=null && !registrationForm.getBooksAndArticles().isEmpty()){
				booksandArticles=new HashSet<ProfessorBooksPublificationDetailsBO>();
				List<ProfessorBooksandArticlesTO> booksDetails=registrationForm.getBooksAndArticles();
				Iterator<ProfessorBooksandArticlesTO> itr=booksDetails.iterator();
				
				while (itr.hasNext()) {
					ProfessorBooksandArticlesTO book=(ProfessorBooksandArticlesTO)itr.next();
					ProfessorBooksPublificationDetailsBO booksBo=new ProfessorBooksPublificationDetailsBO();
					if(booksDetails.size()>1){
					if(book.getBooks()!=null && !book.getBooks().isEmpty()){
						booksBo.setBookName(book.getBooks());
					}
					if(book.getArticles()!=null && !book.getArticles().isEmpty()){
						booksBo.setArticles(book.getArticles());
					}
					booksandArticles.add(booksBo);
				}else if(booksDetails.size()==1){
					boolean check=false;
					if(book.getBooks()!=null && !book.getBooks().isEmpty()){
						booksBo.setBookName(book.getBooks());
						check=true;
					}
					if(book.getArticles()!=null && !book.getArticles().isEmpty()){
						booksBo.setArticles(book.getArticles());
						check=true;
					}
					if(check){
						booksandArticles.add(booksBo);
					}
					
				}
				}
				bo.setProfesserBooksPublificationDetails(booksandArticles);
			}
			
			
			Set<ProfessorPostDoctoralExpDetailsBO> postDoctoralSet;
			if (registrationForm.getPostDocExp()!=null && !registrationForm.getPostDocExp().isEmpty()) {
				
				postDoctoralSet=new HashSet<ProfessorPostDoctoralExpDetailsBO>();
				List<ProfessorPostDoctoralExperTO> postDoc=registrationForm.getPostDocExp();
				Iterator<ProfessorPostDoctoralExperTO> itr=postDoc.iterator();
				
				while (itr.hasNext()) {
					ProfessorPostDoctoralExperTO postDetails=(ProfessorPostDoctoralExperTO) itr.next();
					ProfessorPostDoctoralExpDetailsBO postBo=new ProfessorPostDoctoralExpDetailsBO();
					
					if(postDoc.size()>1){
					if(postDetails.getFromDate()!=null && !postDetails.getFromDate().isEmpty()){
						postBo.setFromDate(CommonUtil.ConvertStringToSQLDate(postDetails.getFromDate()));
					}
					
					if(postDetails.getToDate()!=null && !postDetails.getToDate().isEmpty()){
						postBo.setToDate(CommonUtil.ConvertStringToSQLDate(postDetails.getToDate()));
					}
					if(postDetails.getCollageName()!=null && !postDetails.getCollageName().isEmpty()){
						postBo.setCollegeName(postDetails.getCollageName());
					}
					postDoctoralSet.add(postBo);
				}else if(postDoc.size()==1){
					boolean check=false;
					if(postDetails.getFromDate()!=null && !postDetails.getFromDate().isEmpty()){
						postBo.setFromDate(CommonUtil.ConvertStringToSQLDate(postDetails.getFromDate()));
						check=true;
					}
					
					if(postDetails.getToDate()!=null && !postDetails.getToDate().isEmpty()){
						postBo.setToDate(CommonUtil.ConvertStringToSQLDate(postDetails.getToDate()));
						check=true;
					}
					if(postDetails.getCollageName()!=null && !postDetails.getCollageName().isEmpty()){
						postBo.setCollegeName(postDetails.getCollageName());
						check=true;
					}
					if(check){
						postDoctoralSet.add(postBo);
					}
					
				}
				}
				
				bo.setProfessorPostDoctoralExpDetails(postDoctoralSet);
			}
			
			Set<ProfessorTeachingExpDetailsBO> teachSet;
			if (registrationForm.getPostDocExp()!=null && !registrationForm.getPostDocExp().isEmpty()) {
				
				teachSet=new HashSet<ProfessorTeachingExpDetailsBO>();
				List<ProfessorTeachingExperTO> teachDoc=registrationForm.getTeachingExp();
				Iterator<ProfessorTeachingExperTO> itr=teachDoc.iterator();
				
				while (itr.hasNext()) {
					ProfessorTeachingExperTO teachDetails=(ProfessorTeachingExperTO) itr.next();
					ProfessorTeachingExpDetailsBO teachBo=new ProfessorTeachingExpDetailsBO();
					if(teachDoc.size()>1){
					if(teachDetails.getFromDate()!=null && !teachDetails.getFromDate().isEmpty()){
						teachBo.setFromDate(CommonUtil.ConvertStringToSQLDate(teachDetails.getFromDate()));
					}
					
					if(teachDetails.getToDate()!=null && !teachDetails.getToDate().isEmpty()){
						teachBo.setToDate(CommonUtil.ConvertStringToSQLDate(teachDetails.getToDate()));
					}
					if(teachDetails.getCollageName()!=null && !teachDetails.getCollageName().isEmpty()){
						teachBo.setCollegeName(teachDetails.getCollageName());
					}
					teachSet.add(teachBo);
				}else if(teachDoc.size()==1){
					boolean check=false;
					if(teachDetails.getFromDate()!=null && !teachDetails.getFromDate().isEmpty()){
						teachBo.setFromDate(CommonUtil.ConvertStringToSQLDate(teachDetails.getFromDate()));
						check=true;
					}
					
					if(teachDetails.getToDate()!=null && !teachDetails.getToDate().isEmpty()){
						teachBo.setToDate(CommonUtil.ConvertStringToSQLDate(teachDetails.getToDate()));
						check=true;
					}
					if(teachDetails.getCollageName()!=null && !teachDetails.getCollageName().isEmpty()){
						teachBo.setCollegeName(teachDetails.getCollageName());
						check=true;
					}
					if(check){
						teachSet.add(teachBo);
					}
				}
				}
				
				bo.setProfessorTeachingExpDetails(teachSet);
			}
			
				educationdDetails.add(bo);
			
		}
		
		profData.setProfesserEducationDetails(educationdDetails);
			
		

	}
	public ProfessorPGIDetails convertPGITOtoBO(ApplicationRegistrationForm registrationForm) throws Exception{
		
		ProfessorPGIDetails bo=new ProfessorPGIDetails();
		StringBuilder temp=new StringBuilder();
		
		temp.append(CMSConstants.PGI_MERCHANT_ID_FEDERAL).append("|").append(registrationForm.getTxnid()).append("|").append(registrationForm.getAmount()).append("|").append(registrationForm.getStatus()).append("|").append(CMSConstants.PGI_CHECKSUM_KEY_FEDERAL);
		
		System.out.println("+++++++++++++++++++++++++++++++++++  this is data before hash alogoritham ++++++++++++++++++++++++++++++"+temp.toString());
		String hash=StudentLoginHandler.getInstance().sha1("SHA-1",temp.toString());
		
      System.out.println("+++++++++++++++++++++++++++++++++++  this is data of after  hash  generation ++++++++++++++++++++++++++++++"+hash);
		
		System.out.println("+++++++++++++++++++++++++++++++++++  this is data of pay u hash ++++++++++++++++++++++++++++++"+registrationForm.getHash());
		
		if(registrationForm.getHash()!=null && hash!=null && !registrationForm.getHash().equals(hash)){
			
			throw  new BusinessException();
		}else{
			bo.setTxnRefNo(registrationForm.getTxnid());
			bo.setTxnAmount(new BigDecimal(Integer.parseInt(registrationForm.getAmount())/100));
			bo.setMode("Online");
		   //bo.setAdditionalCharges(new BigDecimal(admForm.getAdditionalCharges()));
			//bo.setBankRefNo(admForm.getBank_ref_num());
			if(registrationForm.getStatus().equalsIgnoreCase("S")){
			bo.setTxnStatus("Success");
			}else {
				bo.setTxnStatus("Pending");
			}

			bo.setTxnDate(CommonUtil.ConvertStringToSQLDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy")));

			registrationForm.setTxnRefNo(registrationForm.getTxnid());
			registrationForm.setPgiStatus("Payment Successful");
			registrationForm.setTxnAmt(registrationForm.getAmount());
			if(registrationForm.getStatus().equalsIgnoreCase("S")){
				registrationForm.setIsTnxStatusSuccess(true);
			}
			else{
				registrationForm.setIsTnxStatusSuccess(false);
			}
			//admForm.setTxnRefNo(admForm.getPayuMoneyId());
			registrationForm.setTxnDate(CommonUtil.ConvertStringToDateFormat(CommonUtil.getDateTime(), "MM/dd/yyyy hh:mm:ss", "dd/MM/yyyy"));
			
		}
		return bo;
	}
	public void convertPrintBOtoTO(ApplicationRegistrationForm registrationForm,ProfessorPersonalData printData) throws Exception{
		IApplicationRegistrationTransaction txn=ApplicationRegistrationHandlerTxnImp.getInstance();
		if(printData!=null){
			if(printData.getFirstName()!=null && !printData.getFirstName().isEmpty()){
				registrationForm.setFirstName(printData.getFirstName());
			}
			if(printData.getDepartmentId()!=null &&  printData.getDepartmentId().getName()!=null){
				registrationForm.setDepartmentName(printData.getDepartmentId().getName());
			}else{
				if(printData.getDepartmentId()!=null && printData.getDepartmentId().getName()==null){
				Department department=	txn.getDepartmentNameThroughId(printData.getDepartmentId().getId());
					if(department!=null){
						registrationForm.setDepartmentName(department.getName());
					}
				}
				
			}
			if(printData.getEmailId()!=null && !printData.getEmailId().isEmpty()){
				registrationForm.setEmailId(printData.getEmailId());
			}
			if(printData.getMobileNo()!=null && !printData.getMobileNo().isEmpty()){
				registrationForm.setMobileNo(printData.getMobileNo());
			}
			if(printData.getDateOfBirth()!=null){
				registrationForm.setDateOfBirth(CommonUtil.formatDate(printData.getDateOfBirth(), "dd-MM-yyyy"));
			}
			if(printData.getAge()!=null){
				registrationForm.setAge(String.valueOf(printData.getAge()));
			}
			if(printData.getMaritalStatus()!=null){
				registrationForm.setMaritalStatus(printData.getMaritalStatus());
			}
			if(printData.getReligionId()!=null && printData.getReligionId().getName()!=null){
				registrationForm.setReligionName(printData.getReligionId().getName());
			}else{
				if(printData.getReligionId()!=null && printData.getReligionId().getName()==null){
					Religion religion=	txn.getReligionNameThroughId(printData.getReligionId().getId());
						if(religion!=null){
							registrationForm.setReligionName(religion.getName());
						}
					}
					
				}
			if(printData.getCasteId()!=null && printData.getCasteId().getName()!=null){
				registrationForm.setCasteName(printData.getCasteId().getName());
			}else{
				if(printData.getCasteId()!=null && printData.getCasteId().getName()==null){
					Caste caste=txn.getCasteNameThroughId(printData.getCasteId().getId());
						if(caste!=null){
							registrationForm.setCasteName(caste.getName());
						}
					}
					
				}
			if(printData.getDiocese()!=null){
				if(printData.getReligionId().getId()== 3 && printData.getCasteId().getId()==160){
				registrationForm.setDiocese(printData.getDiocese());
				}
			}
			if(printData.getAddress()!=null){
				registrationForm.setAddress(printData.getAddress());
			}
			if(printData.getCategory()!=null){
				registrationForm.setCategory(printData.getCategory());
			}
			
			Set<ProfessorEducationDetailsBO> professorEducation= printData.getProfesserEducationDetails();
			
			if(professorEducation!=null){
			Iterator<ProfessorEducationDetailsBO> itr=professorEducation.iterator();
			
			while(itr.hasNext()){
				ProfessorEducationDetailsBO bo=itr.next();
				
				if(bo.getIsJrf()!=null)
				registrationForm.setIsJrfQualification(bo.getIsJrf());
				
				if(bo.getJrfDetails()!=null){
					registrationForm.setJrfDeatils(bo.getJrfDetails());
				}
				
				if(bo.getIsNet()!=null)
					registrationForm.setIsNetQualification(bo.getIsNet());
					
					if(bo.getNetDetails()!=null){
						registrationForm.setNetDeatils(bo.getNetDetails());
					}
					
				if(bo.getAdditionalInformation()!=null){
					registrationForm.setAdditionalInformation(bo.getAdditionalInformation());
				}
				
				if(bo.getUgcApprovedInformation()!=null){
					registrationForm.setUgcApprovedInformation(bo.getUgcApprovedInformation());
				}
				
				
				
			   Set<ProfessorEducationQualificationDetailsBO> educationQuaBo=bo.getProfessorEducationQualificationDetails();
			   
			   if(educationQuaBo!=null && !educationQuaBo.isEmpty()){
				   
				   Iterator<ProfessorEducationQualificationDetailsBO> qualItr=educationQuaBo.iterator();
				   List<ProfessorEducationDetailsTO> educationList=new LinkedList<ProfessorEducationDetailsTO>();
				  while(qualItr.hasNext()){
					  ProfessorEducationQualificationDetailsBO qualBo=qualItr.next();
					  ProfessorEducationDetailsTO qualTo=new ProfessorEducationDetailsTO();
					  if(qualBo.getProfessorQualificationId()!=null && qualBo.getProfessorQualificationId().getId()!=0){
					  qualTo.setQualificationId(qualBo.getProfessorQualificationId().getId());
					  qualTo.setQualificationName(qualBo.getProfessorQualificationId().getName());
					  }
					  
					  if(qualBo.getSubjectName()!=null){
						  qualTo.setSubjectName(qualBo.getSubjectName());
					  }
					  
					  if(qualBo.getCollegeName()!=null){
						  qualTo.setCollegeName(qualBo.getCollegeName());
					  }
					  
					  if(qualBo.getUniversityName()!=null){
						  qualTo.setUniversityName(qualBo.getUniversityName());
					  }
					  
					  if(qualBo.getIsMphil()!=null && qualBo.getIsMphil()){
						  qualTo.setIsMphil(qualBo.getIsMphil());
					  }
					  if(qualBo.getIsPhd()!=null && qualBo.getIsPhd()){
						  qualTo.setIsPhd(qualBo.getIsPhd());
					  }
					  if(qualBo.getPercentage()!=null){
						  qualTo.setPercentage(qualBo.getPercentage());
					  }
					  if(bo.getRankPosition()!=null){
						  if(bo.getRankPosition().equalsIgnoreCase("1")){
						  qualTo.setRankPosition("I");
						  }
						  if(bo.getRankPosition().equalsIgnoreCase("2")){
							  qualTo.setRankPosition("II");
							  }
						  if(bo.getRankPosition().equalsIgnoreCase("3")){
							  qualTo.setRankPosition("III");
							  }
					  }
					  
					  educationList.add(qualTo);  
				  }
				  
				  registrationForm.setEducationalDetails(educationList);
				  
				  Collections.sort(educationList,new Comparator<ProfessorEducationDetailsTO>() {
						@Override
						public int compare(ProfessorEducationDetailsTO arg0, ProfessorEducationDetailsTO arg1) {
							int string = arg0.getQualificationId();
							int string2=arg1.getQualificationId();
							return string - string2;
						}
					})
					;
				   
			   }
			   
			   Set<ProfessorBooksPublificationDetailsBO> booksArticles=bo.getProfesserBooksPublificationDetails();
			   List<ProfessorBooksandArticlesTO> bookList=new LinkedList<ProfessorBooksandArticlesTO>();
			   if(booksArticles!=null && !booksArticles.isEmpty()){
				   Iterator<ProfessorBooksPublificationDetailsBO> bookItr=booksArticles.iterator();
				  
				   while(bookItr.hasNext()){
					   ProfessorBooksPublificationDetailsBO booksBo=bookItr.next();
					   
					   ProfessorBooksandArticlesTO booksTo=new ProfessorBooksandArticlesTO();
					   
					   booksTo.setId(booksBo.getId());
					   if(booksBo.getBookName()!=null){
						   booksTo.setBooks(booksBo.getBookName());   
					   }
					   if(booksBo.getArticles()!=null){
						   booksTo.setArticles(booksBo.getArticles());
					   }
					   bookList.add(booksTo);
					   
				   }
				   
				   registrationForm.setBooksAndArticles(bookList);
				   
			   }
			   registrationForm.setBookSize(String.valueOf(bookList.size()));
			   
			   Set<ProfessorPostDoctoralExpDetailsBO> postDoctoral=bo.getProfessorPostDoctoralExpDetails();
			   List<ProfessorPostDoctoralExperTO> postList=new LinkedList<ProfessorPostDoctoralExperTO>();
			   if(postDoctoral!=null && !postDoctoral.isEmpty()){
				   
				   Iterator<ProfessorPostDoctoralExpDetailsBO> postItr=postDoctoral.iterator();
				  
				   while(postItr.hasNext()){
					   
					   ProfessorPostDoctoralExpDetailsBO postBo=postItr.next();
					   
					   ProfessorPostDoctoralExperTO postTo=new ProfessorPostDoctoralExperTO();
					   
					  postTo.setId(postBo.getId());
					  if(postBo.getFromDate()!=null){
						  postTo.setFromDate(CommonUtil.formatDate(postBo.getFromDate(), "dd-MM-yyyy"));
					  }
					  if(postBo.getToDate()!=null){
						  postTo.setToDate(CommonUtil.formatDate(postBo.getToDate(), "dd-MM-yyyy"));
					  }
					  if(postBo.getCollegeName()!=null){
						  postTo.setCollageName(postBo.getCollegeName());
					  }
					   
					  postList.add(postTo);
					   
				   }
				   
				   registrationForm.setPostDocExp(postList);
				 
			   }
			   registrationForm.setPostDocSize(String.valueOf(postList.size()));
			   
			   
           Set<ProfessorTeachingExpDetailsBO> teachExp=bo.getProfessorTeachingExpDetails();
           List<ProfessorTeachingExperTO> teachList=new LinkedList<ProfessorTeachingExperTO>();
			   if(teachExp!=null && !teachExp.isEmpty()){
				   
				   Iterator<ProfessorTeachingExpDetailsBO> teachItr=teachExp.iterator();
				   
				   while(teachItr.hasNext()){
					   
					   ProfessorTeachingExpDetailsBO teachBo=teachItr.next();
					   
					   ProfessorTeachingExperTO teachTo=new ProfessorTeachingExperTO();
					   
					   teachTo.setId(teachBo.getId());
					  if(teachBo.getFromDate()!=null){
						  teachTo.setFromDate(CommonUtil.formatDate(teachBo.getFromDate(), "dd-MM-yyyy"));
					  }
					  if(teachBo.getToDate()!=null){
						  teachTo.setToDate(CommonUtil.formatDate(teachBo.getToDate(), "dd-MM-yyyy"));
					  }
					  if(teachBo.getCollegeName()!=null){
						  teachTo.setCollageName(teachBo.getCollegeName());
					  }
					   
					  teachList.add(teachTo);
					   
				   }
				   
				   registrationForm.setTeachingExp(teachList);
				  
			   }
				
			   registrationForm.setTeachingExpSize(String.valueOf(teachList.size()));
				
			}
			}
			
		}
	}

}
