package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.College;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.bo.admin.University;
import com.kp.cms.forms.admin.UniversityForm;
import com.kp.cms.to.admin.CollegeTO;
import com.kp.cms.to.admin.UniversityTO;
import com.kp.cms.transactions.admin.IUniversityTxn;
import com.kp.cms.transactionsimpl.admin.UniversityTxnImpl;

public class UniversityHelper 
{
	private static volatile UniversityHelper helper=null;
	
	private UniversityHelper()
	{
		
	}
	
	public static UniversityHelper getInstance()
	{
		if(helper==null)
			helper=new UniversityHelper();
		return helper;
	}
	public List<UniversityTO> converttoUniversityTO(List<University> universityBOList) {
		//IUniversityTxn txn = UniversityTxnImpl.getInstance();
		List<UniversityTO> universityList = new ArrayList<UniversityTO>();
			Iterator<University> itr = universityBOList.iterator();
			while (itr.hasNext()) {
				University university = (University) itr.next();
				UniversityTO to = new UniversityTO();
				to.setId(university.getId());
				to.setName(university.getName());
				if(university.getDocType()!= null){
					to.setDocTypeId(university.getDocType().getId());
					to.setDocType(university.getDocType().getName());
				}
				//List<College>collegeList = txn.getCollegeByUniversity(university.getId());
				
				Set<College> collegeBOs=university.getColleges();
				List<CollegeTO> collegeTos=new ArrayList<CollegeTO>();
					
				if(collegeBOs!=null){
					Iterator<College> colItr= collegeBOs.iterator();
					while (colItr.hasNext()) {
						College colBO = (College) colItr.next();
						CollegeTO colTO= new CollegeTO();
						colTO.setId(colBO.getId());
						colTO.setName(colBO.getName());
						collegeTos.add(colTO);
					}
				}
				
				/*if(collegeList!=null && collegeList.size() > 0){
					Iterator<College> colItr= collegeList.iterator();
					while (colItr.hasNext()) {
						College colBO = (College) colItr.next();
						CollegeTO colTO= new CollegeTO();
						colTO.setId(colBO.getId());
						colTO.setName(colBO.getName());
						collegeTos.add(colTO);
					}
				}*/
				to.setCollegeTos(collegeTos);
				universityList.add(to);
			}
		return universityList;
	}

	public University convertFormToBo(UniversityForm form) 
	{
		University university=new University();
		university.setName(form.getUniversity());
		
		DocType docType=new DocType();
		docType.setId(Integer.parseInt(form.getDocTypeId()));
		university.setDocType(docType);
		
		university.setIsActive(true);
		
		university.setCreatedBy(form.getUserId());
		university.setCreatedDate(new Date());
		university.setModifiedBy(form.getUserId());
		university.setLastModifiedDate(new Date());
		return university;
	}

	public void convertBoToForm(University university, UniversityForm uniForm) 
	{
		if(university!=null)
		{
			uniForm.setId(university.getId());
			uniForm.setUniversity(university.getName());
			uniForm.setDummyUniversity(university.getName());
			if(university.getDocType()!=null){
			uniForm.setDocTypeId(Integer.toString(university.getDocType().getId()));
			uniForm.setDummyDocTypeId(uniForm.getDocTypeId());
			}
		}
		
	}

	public University convertFormToBo(UniversityForm uniForm,University oldUniversity) 
	{
		if(oldUniversity!=null)
		{
			oldUniversity.setName(uniForm.getUniversity());
			DocType docType=new DocType();
			docType.setId(Integer.parseInt(uniForm.getDocTypeId()));
			oldUniversity.setDocType(docType);
			oldUniversity.setModifiedBy(uniForm.getUserId());
			oldUniversity.setLastModifiedDate(new Date());
		}
		return oldUniversity;
	}

}
