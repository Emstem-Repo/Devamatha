package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.kp.cms.bo.admin.CoreComplimentaryPapers;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.forms.admin.CoreComplimentaryPapersForm;
import com.kp.cms.to.admin.CoreComplimentaryPapersTo;
import com.kp.cms.to.admin.CourseTO;

public class CoreComplimentaryPapersHelper
{
	public static List<CoreComplimentaryPapersTo> convertBosToTos(List<CoreComplimentaryPapers> corecompList)
	{
		List<CoreComplimentaryPapersTo> corecomptoList=new ArrayList<CoreComplimentaryPapersTo>();
		CourseTO courseTo;
		if(corecompList!=null&&!corecompList.isEmpty())
		{
			Iterator<CoreComplimentaryPapers> it=corecompList.iterator();
			while(it.hasNext())
			{
				CoreComplimentaryPapers corecompBo=(CoreComplimentaryPapers)it.next();
				courseTo=new CourseTO();
				CoreComplimentaryPapersTo corecompTo=new CoreComplimentaryPapersTo();
				courseTo.setId(corecompBo.getCourse().getId());
				courseTo.setName(corecompBo.getCourse().getName());
				corecompTo.setCourseto(courseTo);
				corecompTo.setCorecompId(corecompBo.getId());
				corecompTo.setCoreSubject(corecompBo.getCoreSubject());
				corecompTo.setCoreSubject2(corecompBo.getCoreSubject2());
				corecompTo.setCoreSubject3(corecompBo.getCoreSubject3());
				corecompTo.setComplementary1Subject(corecompBo.getComplementary1Subject());
				corecompTo.setComplementary2Subject(corecompBo.getComplementary2Subject());
				corecompTo.setComplementary3Subject(corecompBo.getComplementary3Subject());
				corecompTo.setOpenSubject(corecompBo.getOpenSubject());
				corecomptoList.add(corecompTo);
			}
			
		}
		return corecomptoList;
	}
	
	public static CoreComplimentaryPapers convertTotoBo(CoreComplimentaryPapersForm objform,String mode) throws Exception
	{
		CoreComplimentaryPapers corecomppapers=new CoreComplimentaryPapers();
		Course course=new Course();
		course.setId(Integer.parseInt(objform.getCourseId()));
		corecomppapers.setId(objform.getCorecompId());
		corecomppapers.setCoreSubject(objform.getCoreSubject());
		corecomppapers.setCoreSubject2(objform.getCoreSubject2());
		corecomppapers.setCoreSubject3(objform.getCoreSubject3());
		corecomppapers.setComplementary1Subject(objform.getComplementary1Subject());
		corecomppapers.setComplementary2Subject(objform.getComplementary2Subject());
		corecomppapers.setComplementary3Subject(objform.getComplementary3Subject());
		corecomppapers.setOpenSubject(objform.getOpenSubject());
		corecomppapers.setCourse(course);
		if("add".equalsIgnoreCase(mode))
		{
			corecomppapers.setCreatedBy(objform.getUserId());
			corecomppapers.setCreatedDate(new Date());
		}

		corecomppapers.setModifiedBy(objform.getUserId());
		corecomppapers.setLastModifiedDate(new Date());
		corecomppapers.setIsActive(true);
		
		return corecomppapers;
	}

}
