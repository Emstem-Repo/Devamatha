package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.forms.admission.CopyCheckListAssignmentForm;
import com.kp.cms.helpers.admission.CopyCheckListAssignmentHelper;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.transactions.admission.ICopyCheckListAssignmentTransaction;
import com.kp.cms.transactionsimpl.admission.CopyCheckListAssignmentTransImpl;

	public class CopyCheckListAssignmentHandler {
		private static final Log log = LogFactory.getLog(CopyCheckListAssignmentHandler.class);
		private static volatile CopyCheckListAssignmentHandler copyCheckListHandler = null;
		ICopyCheckListAssignmentTransaction checkListTransaction = new CopyCheckListAssignmentTransImpl();
		
		public static CopyCheckListAssignmentHandler getInstance() {
			if (copyCheckListHandler == null) {
				copyCheckListHandler = new CopyCheckListAssignmentHandler();
			}
			return copyCheckListHandler;
		}

		public List<CheckListTO> getCheckListByYear(int fromYear,CopyCheckListAssignmentForm copyCheckListAssignmentForm)throws Exception{
			
			//getting the details of DocCheckList BO from Impl.. 
			List<DocChecklist> docChecklist = checkListTransaction.getCheckListByYear(fromYear);
			//conversion of BO to TO in helper by passing docChecklist of type DocCheckList..
			List<CheckListTO> checklist = CopyCheckListAssignmentHelper.getInstance().convertBOstoCheckListTOs(docChecklist);
			List<CheckListTO> newCheckList = new ArrayList<CheckListTO>();
	        Set<String> courseYearSet = new HashSet<String>();
	        
	        // checkList data to courseYearSet only if 
	        // combination of course_id & year already not exist.
	        // This is because combination of  course_id & year will have many check listdata.
	        Iterator<CheckListTO> itr = checklist.iterator();
	        CheckListTO checkListTO;
	        String courseYearString;
	        while(itr.hasNext()) {
	        	checkListTO = itr.next();
	        	courseYearString = checkListTO.getCourseTo().getId() + checkListTO.getYear();
	        	if(!courseYearSet.contains(courseYearString)) {
	        		newCheckList.add(checkListTO);
	        		courseYearSet.add(courseYearString);
	        	}
	        }
	        copyCheckListAssignmentForm.setDisplayCheckList(newCheckList);
			return checklist;
		}
		
		public Boolean copyCheckList(CopyCheckListAssignmentForm copyCheckListAssignmentForm) throws Exception{
			boolean isCopied =false;
			List<DocChecklist> docList = CopyCheckListAssignmentHelper.getInstance().convertTOToBO(copyCheckListAssignmentForm);
			if(docList != null){
				isCopied = checkListTransaction.copyCheckList(docList);
			}
			return isCopied;
		}
}
