package com.kp.cms.helpers.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CourseToDepartment;
import com.kp.cms.bo.admin.Department;
import com.kp.cms.bo.exam.ExamValuators;
import com.kp.cms.bo.exam.ExternalEvaluatorsDepartment;
import com.kp.cms.forms.admin.CourseForm;
import com.kp.cms.forms.exam.AssignExternalToDepartmentForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.exam.AssignExternalToDepartmentTO;


public class AssignExternalToDepartmentHelper {
	public static volatile AssignExternalToDepartmentHelper assignExternalToDepartmentHelper = null;
	private static final Log log = LogFactory.getLog(AssignExternalToDepartmentHelper.class);

	public static AssignExternalToDepartmentHelper getInstance() {
		if (assignExternalToDepartmentHelper == null) {
			assignExternalToDepartmentHelper = new AssignExternalToDepartmentHelper();
			return assignExternalToDepartmentHelper;
		}
		return assignExternalToDepartmentHelper;
	}
	
	public List<AssignExternalToDepartmentTO> convertBotoTo(List<Department> departmentList){
		log.info("Start of convertBotoTo of AssignExternalToDepartmentHelper");
		AssignExternalToDepartmentTO to=null;
		List<AssignExternalToDepartmentTO> deptToList = new ArrayList<AssignExternalToDepartmentTO>();
		Iterator<Department> iterator=departmentList.iterator();
		while (iterator.hasNext()) {
			Department dept = iterator.next();
			to = new AssignExternalToDepartmentTO();
			to.setDeptId(dept.getId());
			to.setDeptName(dept.getName());
			deptToList.add(to);
			}
		
		log.info("End of convertBotoTo of AssignExternalToDepartmentHelper");
		return deptToList;		
	}
	public List<AssignExternalToDepartmentTO> convertBotoToForExternalDetails(List<ExamValuators> boList,Map<Integer,Integer> evMap){
		log.info("Start of convertBotoToForExternalDetails of AssignExternalToDepartmentHelper");
		AssignExternalToDepartmentTO to=null;
		List<AssignExternalToDepartmentTO> evlList = new ArrayList<AssignExternalToDepartmentTO>();
		Iterator<ExamValuators> iterator=boList.iterator();
		while (iterator.hasNext()) {
			ExamValuators bo = iterator.next();
			to = new AssignExternalToDepartmentTO();
			if(evMap.containsKey(bo.getId())){
					to.setEvaluatorId(bo.getId());
					to.setEvaluatorName(bo.getName());
					to.setMobile(bo.getMobile());
					to.setPan(bo.getPan());
					to.setDepartmentName(bo.getDepartment());
					to.setTempChecked1(true);
					to.setAssignedDeptId(evMap.get(bo.getId()));
					evlList.add(to);
			}else{
					to.setEvaluatorId(bo.getId());
					to.setEvaluatorName(bo.getName());
					to.setMobile(bo.getMobile());
					to.setPan(bo.getPan());
					to.setDepartmentName(bo.getDepartment());
					evlList.add(to);
			}
		}
		
		log.info("End of convertBotoToForExternalDetails of AssignExternalToDepartmentHelper");
		return evlList;		
	}
	public List<ExternalEvaluatorsDepartment> convertTotoBos(AssignExternalToDepartmentForm assignExternalToDepartmentForm){
		log.info("Start of convertBotoTo of CourseHelper");
		ExternalEvaluatorsDepartment bo=null;
		boolean isSelected=false;
		List<ExternalEvaluatorsDepartment> boList = new ArrayList<ExternalEvaluatorsDepartment>();
		Iterator<AssignExternalToDepartmentTO> iterator=assignExternalToDepartmentForm.getEvlList().iterator();
		while (iterator.hasNext()) {
			AssignExternalToDepartmentTO to = iterator.next();
			bo = new ExternalEvaluatorsDepartment();
			if(to.getChecked()!=null && to.getChecked().equalsIgnoreCase("on")){
				if(to.getAssignedDeptId()>0){
						bo.setId(to.getAssignedDeptId());
					}
					Department dept=new Department();
					ExamValuators ev=new ExamValuators();
					ev.setId(to.getEvaluatorId());
					dept.setId(Integer.parseInt(assignExternalToDepartmentForm.getDepartmentId()));
					bo.setEvaluators(ev);
					bo.setDepartment(dept);
					bo.setCreatedBy(assignExternalToDepartmentForm.getUserId());
					bo.setCreatedDate(new Date());
					bo.setIsActive(true);
					bo.setLastModifiedDate(new Date());
					bo.setModifiedBy(assignExternalToDepartmentForm.getUserId());
					boList.add(bo);
					isSelected=true;
				
			}else if(to.getChecked()==null  && to.isTempChecked1()== true){
				if(to.getAssignedDeptId()>0){
					bo.setId(to.getAssignedDeptId());
				}
				Department dept=new Department();
				ExamValuators ev=new ExamValuators();
				ev.setId(to.getEvaluatorId());
				dept.setId(Integer.parseInt(assignExternalToDepartmentForm.getDepartmentId()));
				bo.setEvaluators(ev);
				bo.setDepartment(dept);
				bo.setIsActive(false);
				bo.setLastModifiedDate(new Date());
				bo.setModifiedBy(assignExternalToDepartmentForm.getUserId());
				boList.add(bo);
				isSelected=true;
		}
			if(isSelected==false){
				assignExternalToDepartmentForm.setSelected(false);
			}
		}
		log.info("End of convertBotoTo of CourseHelper");
		return boList;		
	}

}
