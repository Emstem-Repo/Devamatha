package com.kp.cms.helpers.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.apache.struts.action.ActionForm;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CoursePrerequisite;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.Prerequisite;
import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.admin.PreRequisiteDefinitionForm;
import com.kp.cms.to.admin.CoursePrerequisiteTO;

public class PrerequisiteDefHelper {
	
	public static volatile PrerequisiteDefHelper prerequisiteDefHelper = null;

	public static PrerequisiteDefHelper getInstance() {
		if (prerequisiteDefHelper == null) {
			prerequisiteDefHelper = new PrerequisiteDefHelper();
			return prerequisiteDefHelper;
		}
		return prerequisiteDefHelper;
	}
	
	/**
	 * creating BO object from form 
	 * @param form
	 * @param mode
	 * @param order
	 * @return
	 */
	public CoursePrerequisite createBoObject(PreRequisiteDefinitionForm prereqForm, String mode,String order)
	{
		CoursePrerequisite prerequisite=new CoursePrerequisite();
		Prerequisite prereqexam=new Prerequisite();
		Course course=new Course();
		course.setId(Integer.parseInt(prereqForm.getCourseId()));
		prerequisite.setIsActive(!mode.equalsIgnoreCase("Delete"));
		prerequisite.setCourse(course);
		if(order.equalsIgnoreCase("first"))
		{
			if(prereqForm.getPrereqid1()!=null){
				prereqexam.setId(Integer.parseInt(prereqForm.getPrereqid1()));
			}if(prereqForm.getPercentage1()!=null){
				prerequisite.setPercentage(BigDecimal.valueOf(Float.parseFloat(prereqForm.getPercentage1())));
			}if(prereqForm.getTotalMark1()!= null){
				prerequisite.setTotalMark(BigDecimal.valueOf(Float.parseFloat(prereqForm.getTotalMark1())));
			}
			prerequisite.setPrerequisite(prereqexam);
		}
		else{
			if(prereqForm.getPrereqid2()!=null){
				prereqexam.setId(Integer.parseInt(prereqForm.getPrereqid2()));
			}if((prereqForm.getPercentage2()!=null) && (!prereqForm.getPercentage2().equals(""))){
				prerequisite.setPercentage(BigDecimal.valueOf(Float.parseFloat(prereqForm.getPercentage2())));
			}
			if(prereqForm.getTotalMark2() != null && !prereqForm.getTotalMark2().equals("")){
				prerequisite.setTotalMark(BigDecimal.valueOf(Float.parseFloat(prereqForm.getTotalMark2())));
			}
			prerequisite.setPrerequisite(prereqexam);
		}
		if(mode.equalsIgnoreCase("Add"))
		{
			prerequisite.setCreatedDate(new Date());
			prerequisite.setModifiedBy(prereqForm.getUserId());
			prerequisite.setCreatedBy(prereqForm.getUserId());
			prerequisite.setLastModifiedDate(new Date());
		}else
		{
			prerequisite.setModifiedBy(prereqForm.getUserId());
			prerequisite.setLastModifiedDate(new Date());
		}
		return prerequisite;
	}
	
	/**
	 * creating TO object
	 * @param deflist
	 * @return
	 */
	public List<CoursePrerequisiteTO> createTOObjcet(List<CoursePrerequisite> deflist)
	{
		List<CoursePrerequisiteTO> detTOList=new ArrayList<CoursePrerequisiteTO>();;
		CoursePrerequisiteTO detTO=null;
		CoursePrerequisite preReqDef=null;
		HashMap amap=new HashMap();
		HashMap percentagemap=new HashMap();
		HashMap totalMarkMap = new HashMap();
		Iterator itr1=deflist.iterator();
		Iterator itr2=null;
		while(itr1.hasNext())
		{
			
			detTO=new CoursePrerequisiteTO();
			preReqDef=(CoursePrerequisite) itr1.next();
			detTO.setId(preReqDef.getId());
			if(amap.containsKey(preReqDef.getCourse().getId()))
			{
				StringBuffer coursename= new StringBuffer(amap.get(preReqDef.getCourse().getId()).toString());
				StringBuffer precentage= new StringBuffer(percentagemap.get(preReqDef.getCourse().getId()).toString());
				StringBuffer totalMark = new StringBuffer(totalMarkMap.get(preReqDef.getCourse().getId()).toString());
				coursename=coursename.append(", "+preReqDef.getPrerequisite().getName());
				precentage=precentage.append(", "+preReqDef.getPercentage().toString());
				totalMark = totalMark.append(", "+preReqDef.getTotalMark().toString());
				itr2=detTOList.iterator();
				while(itr2.hasNext())
				{
					detTO=(CoursePrerequisiteTO) itr2.next();
					if(detTO.getCourseid().equalsIgnoreCase(Integer.toString(preReqDef.getCourse().getId())))
					{
						detTO.setPrereqexamName1(coursename.toString());
						detTO.setPercentage1(precentage.toString());
						detTO.setTotalMark1(totalMark.toString());
						amap.remove(preReqDef.getCourse().getId());
						amap.put(preReqDef.getCourse().getId(), coursename);
						percentagemap.remove(preReqDef.getCourse().getId());
						percentagemap.put(preReqDef.getCourse().getId(), precentage);
						totalMarkMap.put(preReqDef.getCourse().getId(), totalMark);
					}
					
				}
			
			}else
			{
					
			detTO.setCourseid(Integer.toString(preReqDef.getCourse().getId()));
			detTO.setCourseName(preReqDef.getCourse().getName());
			detTO.setPrereqexamName1(preReqDef.getPrerequisite().getName());
			detTO.setPercentage1(preReqDef.getPercentage().toString());
			detTO.setTotalMark1(preReqDef.getTotalMark().toString());
			
			detTO.setProgramid(Integer.toString(preReqDef.getCourse().getProgram().getId()));
			detTO.setProgramName(preReqDef.getCourse().getProgram().getName());
			
			detTO.setProgramTypeid(Integer.toString(preReqDef.getCourse().getProgram().getProgramType().getId()));
			detTO.setProgramTypeName(preReqDef.getCourse().getProgram().getProgramType().getName());
			detTOList.add(detTO);
			amap.put(preReqDef.getCourse().getId(),preReqDef.getPrerequisite().getName());
			percentagemap.put(preReqDef.getCourse().getId(), preReqDef.getPercentage().toString());
			totalMarkMap.put(preReqDef.getCourse().getId(), preReqDef.getTotalMark().toString());
			
			}
		}
		return detTOList;
		
	}
	/**
	 * 
	 * @param predefobj
	 * @param mode
	 * @param preDefinitionForm
	 * @return
	 */
	public CoursePrerequisite createnewBoObject(CoursePrerequisite predefobj, String mode, PreRequisiteDefinitionForm preDefinitionForm)
	{
		Employee employee=new Employee();
		employee.setId(CMSConstants.empid);
		CoursePrerequisite prerequisite=new CoursePrerequisite();
		Prerequisite prereqexam=new Prerequisite();
		Course course=new Course();
		course.setId(predefobj.getCourse().getId());
		prerequisite.setIsActive(!mode.equalsIgnoreCase("Delete"));
		prerequisite.setCourse(course);
		prereqexam.setId(predefobj.getPrerequisite().getId());
		prerequisite.setPercentage(predefobj.getPercentage());
		prerequisite.setTotalMark(predefobj.getTotalMark());
		prerequisite.setPrerequisite(prereqexam);
		prerequisite.setId(predefobj.getId());
		
		if(mode.equalsIgnoreCase("Delete")){
			prerequisite.setLastModifiedDate(new Date());
			prerequisite.setModifiedBy(preDefinitionForm.getUserId());
		}
		else {
			prerequisite.setCreatedBy(preDefinitionForm.getUserId());
			prerequisite.setCreatedDate(new Date());
			prerequisite.setModifiedBy(preDefinitionForm.getUserId());
			prerequisite.setLastModifiedDate(new Date());
		}
		
		return prerequisite;
	}
	

}
