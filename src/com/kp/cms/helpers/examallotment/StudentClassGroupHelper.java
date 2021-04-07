package com.kp.cms.helpers.examallotment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.bo.examallotment.ClassGroup;
import com.kp.cms.bo.examallotment.GroupClasses;
import com.kp.cms.bo.examallotment.StudentClassGroup;
import com.kp.cms.forms.examallotment.StudentClassGroupForm;
import com.kp.cms.to.examallotment.StudentsClassGroupTo;
import com.kp.cms.transactions.examallotment.IStudentClassGroupTransaction;
import com.kp.cms.transactionsimpl.examallotment.StudentClassGroupTxnImpl;

public class StudentClassGroupHelper {
	
	public static volatile StudentClassGroupHelper studentClassGroupHelper=null;
	
	/**
	 * @return
	 */
	public static StudentClassGroupHelper getInstance(){
		if(studentClassGroupHelper==null){
			studentClassGroupHelper=new StudentClassGroupHelper();
		}
		
		return studentClassGroupHelper;
		
	}
	
	public StudentClassGroupHelper() {
	}

	/**
	 * @param studentList
	 * @param studentIdList
	 * @return
	 */
	public List<StudentsClassGroupTo> convertStudentListBoTo(List<Student> studentList,List<Integer> studentIdList) {
		List<StudentsClassGroupTo> classGroupToList=new ArrayList<StudentsClassGroupTo>();
		if(studentList!=null && !studentList.isEmpty()){
			for (Student student : studentList) {
				if(!studentIdList.isEmpty()){
					if(!studentIdList.contains(student.getId())){
						StudentsClassGroupTo classGroupTo=new StudentsClassGroupTo();
						if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty()){
							classGroupTo.setRegisterNo(student.getRegisterNo());
						}if(student.getAdmAppln()!=null){
							if(student.getAdmAppln().getPersonalData()!=null){
								if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
									classGroupTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
								}
							}
						}if(student.getClassSchemewise()!=null){
							if(student.getClassSchemewise().getClasses()!=null){
								if(student.getClassSchemewise().getClasses().getName()!=null && !student.getClassSchemewise().getClasses().getName().isEmpty()){
									classGroupTo.setStudentClass(student.getClassSchemewise().getClasses().getName());
									classGroupTo.setClassId(student.getClassSchemewise().getClasses().getId());
								}
							}
						}
						classGroupTo.setStudentId(student.getId());
						classGroupToList.add(classGroupTo);
					}
				}else{
					StudentsClassGroupTo classGroupTo=new StudentsClassGroupTo();
					if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty()){
						classGroupTo.setRegisterNo(student.getRegisterNo());
					}if(student.getAdmAppln()!=null){
						if(student.getAdmAppln().getPersonalData()!=null){
							if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
								classGroupTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
							}
						}
					}if(student.getClassSchemewise()!=null){
						if(student.getClassSchemewise().getClasses()!=null){
							if(student.getClassSchemewise().getClasses().getName()!=null && !student.getClassSchemewise().getClasses().getName().isEmpty()){
								classGroupTo.setStudentClass(student.getClassSchemewise().getClasses().getName());
								classGroupTo.setClassId(student.getClassSchemewise().getClasses().getId());
							}
						}
					}
					classGroupTo.setStudentId(student.getId());
					classGroupToList.add(classGroupTo);
				}
			}
		}
		return classGroupToList;
	}

	/**
	 * @param classGroupForm
	 * @return
	 * @throws Exception 
	 */
	public List<GroupClasses> convertStudentClassGroupToToBo(StudentClassGroupForm classGroupForm) throws Exception {
		List<GroupClasses> groupClassList=new ArrayList<GroupClasses>();
		String[] strings=classGroupForm.getSelectedClasses().split(",");
		List<String> list=new ArrayList<String>(Arrays.asList(strings));
		for (String classId : list) {
			IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
			GroupClasses groupClasses=transaction.getGroupClassByGroupIdAndClassId(classGroupForm.getClassGroup(),classId);
			if(groupClasses==null){
				 groupClasses=new GroupClasses();
				 Classes classes=new Classes();
				 classes.setId(Integer.parseInt(classId));
				 ClassGroup classGroup=new ClassGroup();
				 classGroup.setId(Integer.parseInt(classGroupForm.getClassGroup()));
				 groupClasses.setClassId(classes);
				 groupClasses.setClassGroup(classGroup);
				 Set<StudentClassGroup> studentClassGroupSet=getStudentClassGroupBo(Integer.parseInt(classId),classGroupForm);
				 groupClasses.setStudentClassGroupSet(studentClassGroupSet);
				 groupClasses.setCreatedBy(classGroupForm.getUserId());
				 groupClasses.setCreatedDate(new Date());
				 groupClasses.setIsActive(true);
				 groupClassList.add(groupClasses);
			}else{
				Set<StudentClassGroup> studentClassGroupSet=getStudentClassGroupBo(Integer.parseInt(classId),classGroupForm);
				groupClasses.setStudentClassGroupSet(studentClassGroupSet);
				groupClassList.add(groupClasses);
			}
		}
		return groupClassList;
	}

	

	/**
	 * @param classGroupList
	 * @return
	 */
	public List<StudentsClassGroupTo> getStudentClassGroupMap(List<GroupClasses> classGroupList,StudentClassGroupForm classGroupForm) {
		List<StudentsClassGroupTo> groupClassesToList=new ArrayList<StudentsClassGroupTo>();
		//Map<Integer, String> classGroupMap=classGroupForm.getClassGroupMap();
		if(classGroupList!=null && !classGroupList.isEmpty()){
			for (GroupClasses groupClasses : classGroupList) {
				StudentsClassGroupTo groupTo=new StudentsClassGroupTo();
                 groupTo.setGroupName(groupClasses.getClassGroup().getName());
                 groupTo.setGroupId(groupClasses.getClassGroup().getId());
                 groupClassesToList.add(groupTo);
               /*  if(classGroupMap.containsKey(groupClasses.getClassGroup().getId())){
                	 classGroupMap.remove(groupClasses.getClassGroup().getId());
                 }*/
			}
		}
		//classGroupForm.setClassGroupMap(classGroupMap);
		return groupClassesToList;
		
	}
	
	/**
	 * @param classId
	 * @param classGroupForm
	 * @return
	 */
	private Set<StudentClassGroup> getStudentClassGroupBo(int classId,
			StudentClassGroupForm classGroupForm) {
		Set<StudentClassGroup> studentClassGroupSet=new HashSet<StudentClassGroup>();
		List<StudentsClassGroupTo> classGroupToList=classGroupForm.getClassGroupToList();
		for (StudentsClassGroupTo studentsClassGroupTo : classGroupToList) {
			if(studentsClassGroupTo.getChecked()!=null && studentsClassGroupTo.getChecked().equalsIgnoreCase("on")){
				if(classId==studentsClassGroupTo.getClassId()){
					StudentClassGroup studentClassGroup=new StudentClassGroup();
					studentClassGroup.setCreatedBy(classGroupForm.getUserId());
					studentClassGroup.setCreatedDate(new Date());
					studentClassGroup.setIsActive(true);
					Student student=new Student();
					student.setId(studentsClassGroupTo.getStudentId());
					studentClassGroup.setStudent(student);
					studentClassGroupSet.add(studentClassGroup);
				}
				
			}
		}
		return studentClassGroupSet;
	}

	/**
	 * @param classGroupList
	 * @return
	 */
	public List<Integer> getStudentIdList(List<StudentClassGroup> classGroupList) {
		List<Integer> studentIdList=new ArrayList<Integer>();
		if(classGroupList!=null && !classGroupList.isEmpty()){
			for (StudentClassGroup studentClassGroup : classGroupList) {
           			studentIdList.add(studentClassGroup.getStudent().getId());
			}
		}
		return studentIdList;
	}

	/**
	 * @param groupList
	 * @param classGroupForm
	 * @return
	 * @throws Exception
	 */
	public List<StudentsClassGroupTo> editStudentsByGroupClass(List<GroupClasses> groupList,StudentClassGroupForm classGroupForm) throws Exception {
		List<StudentsClassGroupTo> classGroupToList=new ArrayList<StudentsClassGroupTo>();
		List<Integer> classIdList=new ArrayList<Integer>();
		List<Integer> studentIdList=new ArrayList<Integer>();
      if(groupList!=null && !groupList.isEmpty()){
    	  int count=0;
    	for (GroupClasses groupClasses : groupList) {
			if(count==0){
				classGroupForm.setClassGroup(String.valueOf(groupClasses.getClassGroup().getId()));
				if(groupClasses.getClassId().getCourse()!=null){
					if(groupClasses.getClassId().getCourse().getWorkLocation()!=null){
						classGroupForm.setCampusName(String.valueOf(groupClasses.getClassId().getCourse().getWorkLocation().getId()));
					}
				}if(groupClasses.getClassId().getClassSchemewises()!=null){
					Set<ClassSchemewise> set=groupClasses.getClassId().getClassSchemewises();
					for (ClassSchemewise classSchemewise : set) {
						if(classSchemewise.getCurriculumSchemeDuration()!=null){
						if(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()!=0){
							classGroupForm.setAcademicYear(String.valueOf(classSchemewise.getCurriculumSchemeDuration().getAcademicYear()));
							break;
						}
						}
						
					}
				}if(groupClasses.getClassId().getTermNumber()!=0){
					classGroupForm.setSchemeNo(String.valueOf(groupClasses.getClassId().getTermNumber()));
				}
				
			}
			classIdList.add(groupClasses.getClassId().getId());
			Set<StudentClassGroup> classGroupSet=groupClasses.getStudentClassGroupSet();
			if(classGroupSet!=null){
				for (StudentClassGroup studentClassGroup : classGroupSet) {
					if(studentClassGroup.getIsActive()){
					studentIdList.add(studentClassGroup.getStudent().getId());
					}
				}
			}
			count++;
		}
      }
      
      if(!classIdList.isEmpty()){
    	  IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
    	 List<Student> studentList= transaction.getStudentDetailsByClasses(classGroupForm, classIdList);
    	 List<Integer> otherGroupStudentList=transaction.getStudentFromOtherGroupClass(classIdList,studentIdList);
    	 
    	 
    	 //setting the classes
    	 Map<Integer, String> classMap=new HashMap<Integer, String>();
 		List<Classes> classList=transaction.getclassesByYearAndLocationId(classGroupForm);
 		if(classList!=null && !classList.isEmpty()){
 			 for (Classes classes : classList) {
 				classMap.put(classes.getId(), classes.getName());
 			}
 		}
 		if(!classMap.isEmpty()){
 			Map<Integer, String> selectedClassMap=new HashMap<Integer, String>();
 			for (Integer classId : classIdList) {
				if(classMap.containsKey(classId)){
					String className=classMap.get(classId);
					selectedClassMap.put(classId, className);
					classMap.remove(classId);
				}
			}
 			classGroupForm.setClassMap(classMap);
 			classGroupForm.setSelectedClassMap(selectedClassMap);
 		}
 		//end
 		
    	 
    	 
    	 if(studentList!=null && !studentList.isEmpty()){
    		 for (Student student : studentList) {
    			 if(!otherGroupStudentList.contains(student.getId())){
    				 StudentsClassGroupTo classGroupTo=new StudentsClassGroupTo();
	    			 if(studentIdList.contains(student.getId())){
	    				 classGroupTo.setTempChecked("on");
	    			 }
	 				if(student.getRegisterNo()!=null && !student.getRegisterNo().isEmpty()){
	 					classGroupTo.setRegisterNo(student.getRegisterNo());
	 				}if(student.getAdmAppln()!=null){
	 					if(student.getAdmAppln().getPersonalData()!=null){
	 						if(student.getAdmAppln().getPersonalData().getFirstName()!=null && !student.getAdmAppln().getPersonalData().getFirstName().isEmpty()){
	 							classGroupTo.setStudentName(student.getAdmAppln().getPersonalData().getFirstName());
	 						}
	 					}
	 				}if(student.getClassSchemewise()!=null){
	 					if(student.getClassSchemewise().getClasses()!=null){
	 						if(student.getClassSchemewise().getClasses().getName()!=null && !student.getClassSchemewise().getClasses().getName().isEmpty()){
	 							classGroupTo.setStudentClass(student.getClassSchemewise().getClasses().getName());
	 							classGroupTo.setClassId(student.getClassSchemewise().getClasses().getId());
	 						}
	 					}
	 				}
	 				classGroupTo.setStudentId(student.getId());
	 				classGroupToList.add(classGroupTo);
    			 }
			}
    		 
    	 }
    	  
      }
	return classGroupToList;
		
	}

	/**
	 * @param classGroupForm
	 * @param groupList
	 * @return
	 * @throws Exception
	 */
	public List<GroupClasses> updateStudentClassGroupToToBo(StudentClassGroupForm classGroupForm,List<StudentClassGroup> groupList) throws Exception {
       List<Integer> studentIdList=new ArrayList<Integer>();
       List<GroupClasses> groupClassList=new ArrayList<GroupClasses>();
		if(groupList!=null && !groupList.isEmpty()){
    	   for (StudentClassGroup classGroup : groupList) {
    		   studentIdList.add(classGroup.getStudent().getId());
		}
       }
		String[] strings=classGroupForm.getSelectedClasses().split(",");
		List<String> list=new ArrayList<String>(Arrays.asList(strings));
		for (String classId : list) {
			IStudentClassGroupTransaction transaction=StudentClassGroupTxnImpl.getInstance();
			GroupClasses groupClasses=transaction.getGroupClassByGroupIdAndClassId(classGroupForm.getClassGroup(),classId);
			if(groupClasses!=null){
				 Set<StudentClassGroup> studentClassGroupSet=getUpdateStudentClassGroupBo(Integer.parseInt(classId),classGroupForm,studentIdList,groupClasses);
				 groupClasses.setStudentClassGroupSet(studentClassGroupSet);
				 groupClasses.setModifiedBy(classGroupForm.getUserId());
				 groupClasses.setLastModifiedDate(new Date());
				 groupClassList.add(groupClasses);
			}
		}
		return groupClassList;
	}
	
	/**
	 * @param classId
	 * @param classGroupForm
	 * @param studentGroupMap
	 * @param groupClasses 
	 * @return
	 */
	private Set<StudentClassGroup> getUpdateStudentClassGroupBo(int classId,
			StudentClassGroupForm classGroupForm,List<Integer> studentIdList, GroupClasses groupClasses) {
		Set<StudentClassGroup> studentClassGroupSet=groupClasses.getStudentClassGroupSet();
		List<StudentsClassGroupTo> classGroupToList=classGroupForm.getClassGroupToList();
		for (StudentsClassGroupTo studentsClassGroupTo : classGroupToList) {
			if(!studentIdList.isEmpty()){
				if(studentIdList.contains(studentsClassGroupTo.getStudentId())){
				for (StudentClassGroup studentClassGroup : studentClassGroupSet) {
					if(studentClassGroup.getStudent().getId()==studentsClassGroupTo.getStudentId()){
						if(studentsClassGroupTo.getChecked()!=null && studentsClassGroupTo.getChecked().equalsIgnoreCase("on")){
							if(classId==studentsClassGroupTo.getClassId()){
								studentClassGroup.setModifiedBy(classGroupForm.getUserId());
								studentClassGroup.setLastModifiedDate(new Date());
								studentClassGroupSet.add(studentClassGroup);
							}
						}else{
							if(classId==studentsClassGroupTo.getClassId()){
								studentClassGroup.setModifiedBy(classGroupForm.getUserId());
								studentClassGroup.setLastModifiedDate(new Date());
								studentClassGroup.setIsActive(false);
								studentClassGroupSet.add(studentClassGroup);
							}
						}
					}
				}
			}else{
				if(studentsClassGroupTo.getChecked()!=null && studentsClassGroupTo.getChecked().equalsIgnoreCase("on")){
					if(classId==studentsClassGroupTo.getClassId()){
						StudentClassGroup studentClassGroup=new StudentClassGroup();
						studentClassGroup.setCreatedBy(classGroupForm.getUserId());
						studentClassGroup.setCreatedDate(new Date());
						studentClassGroup.setIsActive(true);
						Student student=new Student();
						student.setId(studentsClassGroupTo.getStudentId());
						studentClassGroup.setStudent(student);
						studentClassGroupSet.add(studentClassGroup);
					}
					
				}
			}
			}else{
				if(studentsClassGroupTo.getChecked()!=null && studentsClassGroupTo.getChecked().equalsIgnoreCase("on")){
					if(classId==studentsClassGroupTo.getClassId()){
						StudentClassGroup studentClassGroup=new StudentClassGroup();
						studentClassGroup.setCreatedBy(classGroupForm.getUserId());
						studentClassGroup.setCreatedDate(new Date());
						studentClassGroup.setIsActive(true);
						Student student=new Student();
						student.setId(studentsClassGroupTo.getStudentId());
						studentClassGroup.setStudent(student);
						studentClassGroupSet.add(studentClassGroup);
					}
				}
			}
		}
		return studentClassGroupSet;
	}

	/**
	 * @param groupClassList
	 * @param classGroupForm
	 * @return
	 */
	public List<GroupClasses> deleteStudentGroupClasses(List<GroupClasses> groupClassList,StudentClassGroupForm classGroupForm) {
		List<GroupClasses> deleteGroupClassList=new ArrayList<GroupClasses>();
		if(groupClassList!=null && !groupClassList.isEmpty()){
			for (GroupClasses groupClasses : groupClassList) {
				groupClasses.setModifiedBy(classGroupForm.getUserId());
				groupClasses.setLastModifiedDate(new Date());
				groupClasses.setIsActive(false);
				Set<StudentClassGroup> studentClassGroupSet=groupClasses.getStudentClassGroupSet();
				for (StudentClassGroup studentClassGroup : studentClassGroupSet) {
					studentClassGroup.setModifiedBy(classGroupForm.getUserId());
					studentClassGroup.setLastModifiedDate(new Date());
					studentClassGroup.setIsActive(false);
					studentClassGroupSet.add(studentClassGroup);
				}
				groupClasses.setStudentClassGroupSet(studentClassGroupSet);
				deleteGroupClassList.add(groupClasses);
			}
		}
		
		return deleteGroupClassList;
		
	}
}
