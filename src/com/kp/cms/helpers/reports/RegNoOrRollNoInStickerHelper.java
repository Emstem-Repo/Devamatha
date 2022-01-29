package com.kp.cms.helpers.reports;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Student;
import com.kp.cms.to.admin.StudentTO;

public class RegNoOrRollNoInStickerHelper {

	public static volatile RegNoOrRollNoInStickerHelper regNoOrRollNoInStickerHelper = null;

	public static RegNoOrRollNoInStickerHelper getInstance() {
		if (regNoOrRollNoInStickerHelper == null) {
			regNoOrRollNoInStickerHelper = new RegNoOrRollNoInStickerHelper();
			return regNoOrRollNoInStickerHelper;
		}
		return regNoOrRollNoInStickerHelper;
	}

	/**
	 * 
	 * @param studList
	 * @return
	 * @throws Exception
	 */
	public List<StudentTO> copyBosToList(List<Student> studList, boolean isRollNo) throws Exception {
		Iterator<Student> iterator = studList.iterator();
		Student student;
		List<StudentTO> studentToList = new ArrayList<StudentTO>();
		StudentTO studentTO;
		
		while (iterator.hasNext()) {
			studentTO = new StudentTO();
			student = (Student) iterator.next();
			if(isRollNo){
				studentTO.setRegisterNo(student.getRollNo());
			}
			else
			{
				studentTO.setRegisterNo(student.getRegisterNo());	
			}
			studentToList.add(studentTO);	
		}
		return studentToList;
	}
	
	
}
