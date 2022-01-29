package com.kp.cms.handlers.admin;

import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.SyllabusEntry;
import com.kp.cms.forms.admin.SyllabusUploadForm;
import com.kp.cms.helpers.admin.SyllabusUploadHelper;
import com.kp.cms.to.admin.SyllabusUploadTo;
import com.kp.cms.transactions.admin.ISyllabusUploadTransaction;
import com.kp.cms.transactionsimpl.admin.SyllabusUploadTxnlImpl;

public class SyllabusUploadHandler {
	
	
	ISyllabusUploadTransaction transaction = SyllabusUploadTxnlImpl.getInstance();
    public static volatile SyllabusUploadHandler syllabusUploadHandler = null;
    
    
    
    /**
     * @return
     */
    /**
     * @return
     */
    public static SyllabusUploadHandler getInstance() {
		if (syllabusUploadHandler == null) {
			syllabusUploadHandler = new SyllabusUploadHandler();
			return syllabusUploadHandler;
		}
		return syllabusUploadHandler;
	}


	/**
	 * @param academicYear
	 * @param subjectCode
	 * @return
	 */
	public Integer getSubjectIdByYearAndSubjectCode(String academicYear,String subjectCode) {
		return transaction.getSubejectByYearAndSubjectCode(academicYear,subjectCode);
	}


	/**
	 * @param syllabusEntryMap
	 * @param uploadForm
	 * @return
	 * @throws Exception 
	 */
	public boolean uploadSyllabus(Map<Integer, List<SyllabusUploadTo>> syllabusEntryMap,SyllabusUploadForm uploadForm,List<Integer> subjectIdlist) throws Exception {
		List<SyllabusEntry> syllabusEntriesList=transaction.getSyllabusEntryBySubjectIdList(subjectIdlist);
		List<SyllabusEntry> entriesList=SyllabusUploadHelper.getInstance().convertMapToBo(syllabusEntryMap,uploadForm,syllabusEntriesList);
		return transaction.saveSyllabusEntries(entriesList);
		
       	 	
	}

}
