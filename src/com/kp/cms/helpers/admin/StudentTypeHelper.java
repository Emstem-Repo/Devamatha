package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.StudentType;
import com.kp.cms.to.admin.StudentTypeTO;

public class StudentTypeHelper {
	
	public static List<StudentTypeTO> convertBOstoTos(
			List<StudentType> studenttypebolist) {
		List<StudentTypeTO> studentTypeList = null;
		if (studenttypebolist != null) {
			studentTypeList=new ArrayList<StudentTypeTO>();
			Iterator<StudentType> itr = studenttypebolist.iterator();
			while (itr.hasNext()) {
				StudentType studenttypebo = (StudentType) itr.next();
				StudentTypeTO studentTypeTo = new StudentTypeTO();
				studentTypeTo.setStudentTypeId(Integer.toString(studenttypebo.getId()));
				studentTypeTo.setTypeName(studenttypebo.getName());
				studentTypeTo.setTypeDesc(studenttypebo.getDescription());
				studentTypeList.add(studentTypeTo);
			}
		}
		return studentTypeList;
	}

}
