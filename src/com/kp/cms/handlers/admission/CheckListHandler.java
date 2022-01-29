package com.kp.cms.handlers.admission;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.DocChecklist;
import com.kp.cms.bo.admin.DocType;
import com.kp.cms.exceptions.DuplicateException;
import com.kp.cms.exceptions.ReActivateException;
import com.kp.cms.forms.admission.CheckListForm;
import com.kp.cms.helpers.admission.CheckListHelper;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;
import com.kp.cms.to.admission.CheckListTO;
import com.kp.cms.to.admission.DocTO;
import com.kp.cms.transactions.admission.ICheckListTransaction;
import com.kp.cms.transactionsimpl.admission.CheckListTransactionImpl;

public class CheckListHandler {

	private static volatile CheckListHandler checkListHandler = null;

	public static CheckListHandler getInstance() {
		if (checkListHandler == null) {
			checkListHandler = new CheckListHandler();
		}
		return checkListHandler;
	}

	
	/**
	 * This method will set the checklist Details to Form
	 */
	public void setCheckListTOToForm(CheckListForm checkListForm){
		CheckListTO checkListTO=new CheckListTO();
		ProgramTypeTO programTypeTO=new ProgramTypeTO();
		programTypeTO.setProgramTypeId(Integer.parseInt(checkListForm.getProgramTypeId()));
		programTypeTO.setProgramTypeName(checkListForm.getProgramTypeName());
		
		CourseTO courseTo=new CourseTO();
		courseTo.setId(Integer.parseInt(checkListForm.getCourse()));
		courseTo.setName(checkListForm.getCourseName());
		ProgramTO programTO=new ProgramTO();
		programTO.setId(Integer.parseInt(checkListForm.getProgram()));
		programTO.setName(checkListForm.getProgramName());
		programTO.setProgramTypeTo(programTypeTO);
		
		checkListTO.setProgramTO(programTO);
		checkListTO.setCourseTo(courseTo);
		checkListTO.setYear(checkListForm.getYear());
		
		//HttpSession session=request.getSession(true);
		//session.setAttribute("CheckListTO",checkListTO);
		checkListForm.setCheckListTO(checkListTO);
		
	}
	
	
	/**
	 * 
	 * @param checkListForm
	 * @return boolean
	 * @throws Exception
	 * 		  check for duplicate in database if any duplicate returns true else false.
	 */
	public boolean checkDuplicate(CheckListForm checkListForm) throws Exception, ReActivateException,DuplicateException{
		
		CheckListTO checkListTO = checkListForm.getCheckListTO();
		if(checkListTO != null) {
			String course = Integer.valueOf(checkListTO.getCourseTo().getId()).toString();
			if(checkListForm.getYear().equals(checkListTO.getYear()) && checkListForm.getCourse().equals(course)){
				return false;
			}
		}
		
		setCheckListTOToForm(checkListForm);
					
		int courseId=Integer.parseInt(checkListForm.getCourse());
		int year=Integer.parseInt(checkListForm.getYear());
		
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		
		List<DocChecklist> list=checkListTransaction.getCheckDuplicate(courseId,year);
		Iterator<DocChecklist> itr = list.iterator();
		DocChecklist docChecklist;
		int inActiveCount= 0;
		while(itr.hasNext()){
			docChecklist = itr.next();
			if(docChecklist.getIsActive() == false)
				inActiveCount++;
		}
		if(inActiveCount != 0 &&inActiveCount == list.size()) {
			throw new ReActivateException();
		} else if(!list.isEmpty()) {
			throw new DuplicateException();
		} else 
			return true;
		
	}
	
	/**
	 * 
	 * @param checkListForm
	 * @return boolean
	 * @throws Exception
	 *         return true if success else false while adding the checklist to database.
	 */
	public boolean addCheckList(CheckListForm checkListForm) throws Exception{
		
		boolean isAdded = false;
		
		Iterator<DocTO> iter=checkListForm.getDoclist().iterator();
		List<DocTO> doclist=new ArrayList<DocTO>();
		while (iter.hasNext()) {
			DocTO obj = (DocTO) iter.next();
			if(obj.getSelect().equals("on")){
				obj.setActive(true);
				doclist.add(obj);
			}
		}
		
		//call of helper by passing CheckListTO instance..
		List<DocChecklist> docCheckList= CheckListHelper.getInstance().convertTOstoBOs(doclist,checkListForm.getCheckListTO(),checkListForm);
		Iterator<DocChecklist> itr = docCheckList.iterator();
		while(itr.hasNext()){
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		// after return from helper, passing the BO instance to Impl for saving..
		isAdded = checkListTransaction.addCheckList(itr.next());
		}
		return isAdded;

	}
	
	/**
	 * 
	 * @param checkListForm
	 * @return boolean
	 * @throws Exception
	 * 		   This method will update the particular checklist entry.
	 * 		   return true/false.
	 */
	public boolean updateCheckList(CheckListForm checkListForm) throws Exception{
		
		boolean isAdded = false;
		
		Iterator<DocTO> iter=checkListForm.getDoclist().iterator();
		List<DocTO> doclist=new ArrayList<DocTO>();
		while (iter.hasNext()) {
			DocTO obj = (DocTO) iter.next();
			if(obj.getSelect().equals("on")){
                obj.setActive(true);
				doclist.add(obj);
			} else if(obj.getSelect().equals("off") && obj.getId() != 0){
				obj.setActive(false);
				doclist.add(obj);
			}
		}
		//call of helper by passing CheckListTO instance..
		List<DocChecklist> docCheckList= CheckListHelper.getInstance().convertTOstoBOs(doclist,checkListForm.getCheckListTO(),checkListForm);
		Iterator<DocChecklist> itr = docCheckList.iterator();
		while(itr.hasNext()){
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		// after return from helper, passing the BO instance to Impl for saving..
			isAdded = checkListTransaction.updateCheckList(itr.next());
		
		}
		return isAdded;

	}
	
	/**
	 * 	
	 * @return list of checklistdocs.
	 * @throws Exception
	 * 		   Loads the checklist from database.
	 */
	public List<DocTO> getCheckListDocs() throws Exception{
		
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		//getting the document name, id in Impl from DocType BO..  
		List<DocType> doclist = checkListTransaction.getDocuments();
		//call of helper to set BO to TO by passing doclist of type DocType.
		List<DocTO> list = CheckListHelper.getInstance().getCheckListDocFromDocType(doclist);
		return list;
	}

	/**
	 * 
	 * @return CheckListTO list.
	 * @throws Exception
	 * Loads the checklist from database and return it.
	 */
	public List<CheckListTO> getCheckList(int year) throws Exception{

		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		//getting the details of DocCheckList BO from Impl.. 
		List<DocChecklist> docChecklist = checkListTransaction.getCheckList(year);
		//conversion of BO to TO in helper by passing docChecklist of type DocCheckList..
		List<CheckListTO> checklist = CheckListHelper.getInstance().convertBOstoCheckListTOs(docChecklist);
		List<CheckListTO> newCheckList = new ArrayList<CheckListTO>();
        Set<String> courseYearSet = new HashSet<String>();
        
  
        // checkList data to courseYearSet only if 
        // combination of course_id & year already not exist.
        // This is because combination of  course_id & year will have many check listdata.
        Iterator<CheckListTO> itr = checklist.iterator();
        CheckListTO checkListTO;
        String sourseYearString;
        while(itr.hasNext()) {
        	checkListTO = itr.next();
        	sourseYearString = checkListTO.getCourseTo().getId() + checkListTO.getYear();
        	if(!courseYearSet.contains(sourseYearString)) {
        		newCheckList.add(checkListTO);
        		courseYearSet.add(sourseYearString);
        	}
        }
		return newCheckList;
	}
	
	/**
	 * 
	 * @param listForm
	 * @return List document types to display at user side.
	 * @throws Exception
	 * 
	 */
	public List<DocTO> viewCheckList(CheckListForm checkListForm)throws Exception{
		
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		List<DocChecklist> doclist=checkListTransaction.getChecklist(checkListForm.getCourse(), checkListForm.getYear());
		List<DocTO> list= CheckListHelper.getInstance().convertBOstoTOs(doclist);
		DocChecklist docChecklist;
		if(doclist != null) {
			docChecklist = doclist.get(0);
			checkListForm.setProgramTypeName(docChecklist.getProgram().getProgramType().getName());
			checkListForm.setProgramName(docChecklist.getProgram().getName());
			checkListForm.setCourseName(docChecklist.getCourse().getName());
			String year1 = String.valueOf(docChecklist.getYear() + 1);
			checkListForm.setYear(docChecklist.getYear()+"-"+year1);
		}
		
		
		return list;
	}
	
	/**
	 * 
	 * @param checkListForm
	 * @return will load the Checklist details in Edit operation
	 * @throws Exception
	 */
	public List<DocTO> editCheckList(CheckListForm checkListForm)throws Exception{
		
		ICheckListTransaction checkListTransaction = new CheckListTransactionImpl();
		
		// Here creating Default DocTo's only setting docTypeName & docTypeId
		List<DocType> docTypelist = checkListTransaction.getDocuments();
		List<DocTO> docTolist = CheckListHelper.getInstance().getCheckListDocFromDocType(docTypelist);
		
		// Here loading DOCTO from DB.
		List<DocChecklist> doclist=checkListTransaction.getChecklist(checkListForm.getCourse(), checkListForm.getYear());
		List<DocTO> chelist= CheckListHelper.getInstance().convertBOstoTOs(doclist);
		
		if(docTolist.size() == chelist.size()) {
			return chelist;
		}
		
		// Merging 1 & 2 -> result in datapresent in DB + new DOCTO's were not selected before.
		Set<Integer> docTypeIdsSet = new HashSet<Integer>();
		Iterator<DocTO> itr = chelist.iterator();
		while(itr.hasNext()) {
			docTypeIdsSet.add(itr.next().getDocTypeId());
		}
		
		itr = docTolist.iterator();
		DocTO newDocTO;
		while(itr.hasNext()){
			new DocTO();
			newDocTO = itr.next();
			if(!docTypeIdsSet.contains(newDocTO.getDocTypeId())){
				chelist.add(newDocTO);
			}
		}
		
		return chelist;
	}
	
	/**
	 * 
	 * @param listForm
	 * @return
	 * @throws Exception
	 * 	       Delete the checklist entry. 
	 */
	public boolean deleteCheckList(CheckListForm listForm) throws Exception {
		CheckListTransactionImpl transactionImpl = new CheckListTransactionImpl();
		boolean isdocumentDeleted = false;
		if (transactionImpl != null) 
			//call of deleteCheckList method in Impl by passing docCheckListId..
			isdocumentDeleted = transactionImpl.deleteCheckList(listForm.getCourse(),listForm.getYear());

		return isdocumentDeleted;
	}
	
	/**
	 * 
	 * @param listForm
	 * @return
	 * @throws Exception
	 *         reactivate the particular checklist entry.
	 */
	public boolean reActivateCheckList(CheckListForm listForm) throws Exception {
		CheckListTransactionImpl transactionImpl = new CheckListTransactionImpl();
		boolean isdocumentDeleted = false;
		if (transactionImpl != null) 
			//call of deleteCheckList method in Impl by passing docCheckListId..
			isdocumentDeleted = transactionImpl.reActivateCheckList(listForm.getCourse(),listForm.getYear());

		return isdocumentDeleted;
	}
}