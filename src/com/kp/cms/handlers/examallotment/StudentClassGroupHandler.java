package com.kp.cms.handlers.examallotment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.EmployeeWorkLocationBO;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.GroupClasses;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.forms.examallotment.StudentClassGroupForm;
import com.kp.cms.helpers.examallotment.StudentClassGroupHelper;
import com.kp.cms.to.examallotment.StudentsClassGroupTo;
import com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction;
import com.kp.cms.transactionsimpl.examallotment.StudentClassGroupTxnImpl;

public class StudentClassGroupHandler {
	
	private static volatile StudentClassGroupHandler studentClassGroupHandler=null;
	 
	/**
	 * @return
	 */
	/**
	 * @return
	 */
	public static StudentClassGroupHandler getInstance(){
		if(studentClassGroupHandler == null){
			studentClassGroupHandler=new StudentClassGroupHandler();
		}
		return studentClassGroupHandler;
	}
	
	public StudentClassGroupHandler() {

	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getWorkLocation() throws Exception {
        IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
        Map<Integer, String> locationMap=new HashMap<Integer, String>();
        List<EmployeeWorkLocationBO> locationBOList=transaction.getWorkLocations();
        if(locationBOList!=null && !locationBOList.isEmpty()){
        	for (EmployeeWorkLocationBO locationBO : locationBOList) {
				locationMap.put(locationBO.getId(), locationBO.getName());
			}
        }
		return locationMap;
		
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassGroups() throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
        Map<Integer, String> classGroupMap=new HashMap<Integer, String>();
        List<ClassGroup> classGroupList=transaction.getClassGroupList();
        if(classGroupList!=null && !classGroupList.isEmpty()){
        	for (ClassGroup classGroup : classGroupList) {
				classGroupMap.put(classGroup.getId(), classGroup.getName());
			}
        }
		return classGroupMap;
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public Map<Integer, String> getClassesByYearAndLocation(StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		Map<Integer, String> classMap=new HashMap<Integer, String>();
		List<Classes> classList=transaction.getclassesByYearAndLocationId(classGroupForm);
		if(classList!=null && !classList.isEmpty()){
			 for (Classes classes : classList) {
				classMap.put(classes.getId(), classes.getName());
			}
		}
		return classMap;
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentsClassGroupTo> getStudentDetailsByClasses(StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		Map<Integer, String> selectedClassMap=new HashMap<Integer, String>();
		String[] selectedClasses=classGroupForm.getSelectedClasses().split(",");
		List<Integer> selectedClassList=new ArrayList<Integer>();
		 for (int i = 0; i < selectedClasses.length; i++) {
			 selectedClassList.add(Integer.parseInt(selectedClasses[i]));
			 if(classGroupForm.getClassMap().containsKey(Integer.parseInt(selectedClasses[i]))){
				 String className=classGroupForm.getClassMap().get(Integer.parseInt(selectedClasses[i]));
				 selectedClassMap.put(Integer.parseInt(selectedClasses[i]), className);
				 classGroupForm.getClassMap().remove(Integer.parseInt(selectedClasses[i]));
			 }
		}
		 //start getting student from group classes
		 List<StudentClassGroup> classGroupList=transaction.getStudentFromGroupClassByClasses(selectedClassList);
		 List<Integer> studentIdList=StudentClassGroupHelper.getInstance().getStudentIdList(classGroupList);
		 //end
		List<Student> studentList=transaction.getStudentDetailsByClasses(classGroupForm,selectedClassList);
		List<StudentsClassGroupTo> classGroupToList=StudentClassGroupHelper.getInstance().convertStudentListBoTo(studentList,studentIdList);
			int halfLength = 0;
			int totLength = classGroupToList.size();
			if (totLength % 2 == 0) {
				halfLength = totLength / 2;
			} else {
				halfLength = (totLength / 2) + 1;
			}
			classGroupForm.setHalfLength(halfLength);
			if(!selectedClassMap.isEmpty()){
			classGroupForm.setSelectedClassMap(selectedClassMap);
			}
			return classGroupToList;
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public boolean addClassGroup(StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		List<GroupClasses> groupClassList=StudentClassGroupHelper.getInstance().convertStudentClassGroupToToBo(classGroupForm);
		return transaction.addStudentToClassGroup(groupClassList);
	}

	/**
	 * @return
	 * @throws Exception
	 */
	public List<StudentsClassGroupTo> getStudentClassGroup(int year,StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		List<GroupClasses> classGroupList=transaction.getStudentClassGroup(year);
		return StudentClassGroupHelper.getInstance().getStudentClassGroupMap(classGroupList,classGroupForm);
	}

	/**
	 * @param classGroupForm
	 * @throws Exception 
	 * @throws NumberFormatException 
	 */
	public List<StudentsClassGroupTo> editStudentGroupClassByGroupId(StudentClassGroupForm classGroupForm) throws NumberFormatException, Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		List<GroupClasses> groupList=transaction.getGroupClassesByGroupId(Integer.parseInt(classGroupForm.getGroupId()));
		List<StudentsClassGroupTo> groupToList= StudentClassGroupHelper.getInstance().editStudentsByGroupClass(groupList,classGroupForm);
		int halfLength = 0;
		int totLength = groupToList.size();
		if (totLength % 2 == 0) {
			halfLength = totLength / 2;
		} else {
			halfLength = (totLength / 2) + 1;
		}
		classGroupForm.setHalfLength(halfLength);
		return groupToList;
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public boolean updateGroupClassWithStudents(StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		String[] selectedClasses=classGroupForm.getSelectedClasses().split(",");
        List<Integer> selectedClassList=new ArrayList<Integer>();
        for (int i = 0; i < selectedClasses.length; i++) {
			selectedClassList.add(Integer.parseInt(selectedClasses[i]));
		}
        List<StudentClassGroup> groupList=transaction.getStudentFromGroupClassByClassesAndGroupId(selectedClassList,classGroupForm.getClassGroup());
		List<GroupClasses> groupClassList=StudentClassGroupHelper.getInstance().updateStudentClassGroupToToBo(classGroupForm,groupList);
		return transaction.updateStudentToClassGroup(groupClassList);
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public boolean deleteGroupClassWithStudents(StudentClassGroupForm classGroupForm) throws Exception {
		IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
		List<GroupClasses> groupClassList=transaction.getGroupClassesByGroupId(Integer.parseInt(classGroupForm.getGroupId()));
		List<GroupClasses> groupClassList2=StudentClassGroupHelper.getInstance().deleteStudentGroupClasses(groupClassList,classGroupForm);
		return transaction.deleteGroupClassStudent(groupClassList2);
	}
}
