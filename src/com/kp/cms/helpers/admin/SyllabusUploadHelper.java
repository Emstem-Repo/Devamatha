package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.Subject;
import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.bo.admin.SyllabusEntryHeadingDesc;
import com.kp.cms.bo.admin.SyllabusEntryUnitsHours;
import com.kp.cms.forms.admin.SyllabusUploadForm;
import com.kp.cms.to.admin.SyllabusUploadTo;
import com.kp.cms.transactions.admin.ISyllabusUploadTransaction;
import com.kp.cms.transactionsimpl.admin.SyllabusUploadTxnlImpl;

public class SyllabusUploadHelper {
	
	ISyllabusUploadTransaction transaction = SyllabusUploadTxnlImpl.getInstance();
    public static volatile SyllabusUploadHelper syllabusUploadHelper = null;
    
    
    /**
     * @return
     */
    /**
     * @return
     */
    public static SyllabusUploadHelper getInstance() {
		if (syllabusUploadHelper == null) {
			syllabusUploadHelper = new SyllabusUploadHelper();
			return syllabusUploadHelper;
		}
		return syllabusUploadHelper;
	}


	/**
	 * @param syllabusEntryMap
	 * @param uploadForm
	 * @return
	 * @throws Exception 
	 */
	public List<SyllabusEntry> convertMapToBo(Map<Integer, List<SyllabusUploadTo>> syllabusEntryMap,SyllabusUploadForm uploadForm,
			List<SyllabusEntry> syllabusEntries) throws Exception {
		
		List<SyllabusEntry> entriesList=new ArrayList<SyllabusEntry>();
      if(!syllabusEntryMap.isEmpty()){
    	  if(syllabusEntries!=null && !syllabusEntries.isEmpty()){
    		  for (SyllabusEntry syllabusEntry : syllabusEntries) {
				if(syllabusEntryMap.containsKey(syllabusEntry.getSubject().getId())){
					List<SyllabusUploadTo> tos=syllabusEntryMap.get(syllabusEntry.getSubject().getId());
					for (SyllabusUploadTo syllabusUploadTo : tos) {
						if(syllabusUploadTo.getTeachingHoursPerSem()!=null && !syllabusUploadTo.getTeachingHoursPerSem().isEmpty()){
							syllabusEntry.setTotTeachingHrsPerSem(Integer.parseInt(syllabusUploadTo.getTeachingHoursPerSem()));
						}
					}
					syllabusEntry.setModifiedBy(uploadForm.getUserId());
					syllabusEntry.setLastModifiedDate(new Date());
					Set<SyllabusEntryUnitsHours> entryUnitsHoursSet=new HashSet<SyllabusEntryUnitsHours>();
					 int count=0;
					for (SyllabusEntryUnitsHours hours : syllabusEntry.getSyllabusEntryUnitsHours()) {
							hours.setModifiedBy(uploadForm.getUserId());
							hours.setLastModifiedDate(new Date());
							count=count+1;
							if(hours.getUnitNo()==null || hours.getUnitNo()==0){
								hours.setUnitNo(count);
							}
							int headingNo=0;
							Set<SyllabusEntryHeadingDesc> headingDescSet=new HashSet<SyllabusEntryHeadingDesc>();
							for (SyllabusEntryHeadingDesc desc : hours.getSyllabusEntryHeadingDescs()) {
									desc.setModifiedBy(uploadForm.getUserId());
									desc.setLastModifiedDate(new Date());
									headingNo=headingNo+1;
									if(desc.getHeadingNo()==null || desc.getHeadingNo()==0){
										desc.setHeadingNo(headingNo);
									}
									headingDescSet.add(desc);
							}
							hours.setSyllabusEntryHeadingDescs(headingDescSet);
							entryUnitsHoursSet.add(hours);
					}
					syllabusEntry.setSyllabusEntryUnitsHours(entryUnitsHoursSet);
					entriesList.add(syllabusEntry);
					syllabusEntryMap.remove(syllabusEntry.getSubject().getId());
				}
			}if(!syllabusEntryMap.isEmpty()){
    			  createSyllabusEntry(entriesList,syllabusEntryMap,uploadForm);
    		  }
    	 }else{
    		     createSyllabusEntry(entriesList,syllabusEntryMap,uploadForm);
             }
         }
	return entriesList;
	}


	/**
	 * @param entriesList
	 * @param syllabusEntryMap
	 * @param uploadForm
	 */
	private void createSyllabusEntry(List<SyllabusEntry> entriesList,
			Map<Integer, List<SyllabusUploadTo>> syllabusEntryMap,
			SyllabusUploadForm uploadForm)throws Exception {
		
		try{
		int count=0;
		for (Map.Entry<Integer, List<SyllabusUploadTo>> entrySyllabusEntry : syllabusEntryMap.entrySet()) {
			SyllabusEntry entry=new SyllabusEntry();
			Set<SyllabusEntryUnitsHours> unitsHoursSet=new HashSet<SyllabusEntryUnitsHours>();
			Subject subject=new Subject();
			subject.setId(entrySyllabusEntry.getKey());
			entry.setSubject(subject);
			entry.setApproved(false);
			entry.setSendForApproval(false);
			entry.setIsActive(true);
			entry.setCreatedBy(uploadForm.getUserId());
			entry.setCreatedDate(new Date());
			Set<SyllabusEntryHeadingDesc> headingDescSet=null;
			SyllabusEntryUnitsHours entryUnitsHours=null;
			for (SyllabusUploadTo to : entrySyllabusEntry.getValue()) {
				if(entry.getBatchYear()==null){
					if(to.getAcademicYear()!=null && !to.getAcademicYear().isEmpty()){
					entry.setBatchYear(Integer.parseInt(to.getAcademicYear()));
					}
				}if(entry.getCourseObjective()==null){
					if(to.getCourseDescription()!=null && !to.getCourseDescription().isEmpty()){
					entry.setCourseObjective(to.getCourseDescription().trim());
					}
				}if(entry.getLearningOutcome()==null){
					if(to.getLearningOutcome()!=null && !to.getLearningOutcome().isEmpty()){
						entry.setLearningOutcome(to.getLearningOutcome().trim());
					}
				}if(entry.getTextBooksAndRefBooks()==null){
					if(to.getTextBook()!=null && !to.getTextBook().isEmpty()){
						entry.setTextBooksAndRefBooks(to.getTextBook().trim());
					}
				}if(entry.getTotTeachingHrsPerSem()==null){
					if(to.getTeachingHoursPerSem()!=null && !to.getTeachingHoursPerSem().isEmpty()){
						entry.setTotTeachingHrsPerSem(Integer.parseInt(to.getTeachingHoursPerSem()));
					}
				}
				if(to.getUnitNo()!=null && !to.getUnitNo().isEmpty()){
				entryUnitsHours=new SyllabusEntryUnitsHours();
				 headingDescSet=new HashSet<SyllabusEntryHeadingDesc>();
				entryUnitsHours.setUnits(to.getUnitNo());
				entryUnitsHours.setUnitNo(to.getSyllabusEntryNo());
				if(to.getTeachingHours()!=null && !to.getTeachingHours().isEmpty()){
				entryUnitsHours.setTeachingHours(Integer.parseInt(to.getTeachingHours()));
				}
				entryUnitsHours.setCreatedBy(uploadForm.getUserId());
				entryUnitsHours.setCreatedDate(new Date());
				entryUnitsHours.setIsActive(true);
				SyllabusEntryHeadingDesc headingDesc=new SyllabusEntryHeadingDesc();
				if(to.getHeadings()!=null && !to.getHeadings().isEmpty()){
				headingDesc.setHeading(to.getHeadings().trim());
				}if(to.getContents()!=null && !to.getContents().isEmpty()){
					headingDesc.setDescription(to.getContents().trim());
				}
				headingDesc.setCreatedBy(uploadForm.getUserId());
				headingDesc.setCreatedDate(new Date());
				headingDesc.setIsActive(true);
				count=0;
				headingDesc.setHeadingNo(count+1);
				count=count+1;
				headingDescSet.add(headingDesc);
				}else{
					if(headingDescSet==null){
						headingDescSet=new HashSet<SyllabusEntryHeadingDesc>();
					}
					if(entryUnitsHours==null){
						entryUnitsHours=new SyllabusEntryUnitsHours();
					}
					count=count+1;;
					SyllabusEntryHeadingDesc headingDesc=new SyllabusEntryHeadingDesc();
					if(to.getHeadings()!=null && !to.getHeadings().isEmpty()){
					headingDesc.setHeading(to.getHeadings().trim());
					}if(to.getContents()!=null && !to.getContents().isEmpty()){
						headingDesc.setDescription(to.getContents().trim());
					}
					headingDesc.setCreatedBy(uploadForm.getUserId());
					headingDesc.setCreatedDate(new Date());
					headingDesc.setIsActive(true);
					headingDesc.setHeadingNo(count);
					headingDescSet.add(headingDesc);
				}
				if(headingDescSet!=null && !headingDescSet.isEmpty()){
				entryUnitsHours.setSyllabusEntryHeadingDescs(headingDescSet);
				}
				unitsHoursSet.add(entryUnitsHours);
			}
			entry.setSyllabusEntryUnitsHours(unitsHoursSet);
    		entriesList.add(entry);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
