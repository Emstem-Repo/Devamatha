package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.bo.admin.ClassSchemewise;
import com.kp.cms.bo.admin.Classes;
import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.CurriculumSchemeDuration;
import com.kp.cms.forms.admission.CopyClassesForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admission.CopyClassesTO;
import com.kp.cms.transactionsimpl.admission.CopyClassesTransactionImpl;

	public class CopyClassesHelper {
		private static final Log log = LogFactory.getLog(CopyClassesHelper.class);
		private static volatile CopyClassesHelper copyClassesHelper = null;
	
		public static CopyClassesHelper getInstance() {
			if (copyClassesHelper == null) {
				copyClassesHelper = new CopyClassesHelper();
			}
			return copyClassesHelper;
		}

		public List<CopyClassesTO> convertBOToTO(List<Classes> classesListBO)  throws Exception{
			List<CopyClassesTO> classesList = new ArrayList<CopyClassesTO>();
			Iterator<Classes> iterator = classesListBO.iterator();
			CopyClassesTO classesTO;
			while (iterator.hasNext()) {
				Classes classes =iterator.next();
				classesTO = new CopyClassesTO();
				classesTO.setClassName(classes.getName());
				CourseTO courseTo = new CourseTO();
				courseTo.setId(classes.getCourse().getId());
				courseTo.setName(classes.getCourse().getName());
				classesTO.setCourseTo(courseTo);
				classesTO.setSectionName(classes.getSectionName());
				classesTO.setTermNo(classes.getTermNumber());
				classesList.add(classesTO);
			}
			return classesList;
		}

		public List<Classes> convertTOToBO(List<CopyClassesTO> classesList,int toYear,Map<String,Integer> schemeMap,CopyClassesForm copyClassesForm) throws Exception {
			List<Classes> classesBOList = new ArrayList<Classes>();
			ClassSchemewise classSchemewises ;
			Iterator<CopyClassesTO> clasIterator = classesList.iterator();
			while (clasIterator.hasNext()) {
				CopyClassesTO copyClassesTO = (CopyClassesTO) clasIterator.next();
				
				if(copyClassesTO.getClassesSelId()){
					String name=copyClassesTO.getClassName();
					String courseName = copyClassesTO.getCourseTo().getName();
					int semesterNo = copyClassesTO.getTermNo();
					String course_semeter = courseName+"_"+semesterNo;
					if(schemeMap.containsKey(course_semeter))
					{
						String query=" from ClassSchemewise c " +
						"where c.classes.name ='"+name+"' and c.classes.course.id ="+copyClassesTO.getCourseTo().getId()+
								" and c.classes.termNumber ="+semesterNo+" and c.curriculumSchemeDuration.academicYear = '"+copyClassesForm.getToYear()+"'";
						if(copyClassesTO.getSectionName()!=null && !copyClassesTO.getSectionName().isEmpty()){
							query=query+" and c.classes.sectionName='"+copyClassesTO.getSectionName()+"'";
						}else{
							query=query+" and c.classes.sectionName = ''";
						}
						boolean isDup;
						isDup = CopyClassesTransactionImpl.getInstance().checkDuplicate(query);
						if(!isDup){
						CurriculumSchemeDuration curriculumSchemeDuration = new CurriculumSchemeDuration();
						curriculumSchemeDuration.setId(schemeMap.get(course_semeter));	
						
						classSchemewises = new ClassSchemewise();
						classSchemewises.setCurriculumSchemeDuration(curriculumSchemeDuration);
						Set<ClassSchemewise> classSchemewiseSet = new HashSet<ClassSchemewise>();
						classSchemewiseSet.add(classSchemewises);
						Classes classes=new Classes();
						classes.setClassSchemewises(classSchemewiseSet);
						
						classes.setName(copyClassesTO.getClassName());
						classes.setSectionName(copyClassesTO.getSectionName());
						
						Course course = new Course();
						course.setId(copyClassesTO.getCourseTo().getId());
						classes.setCourse(course);
						classes.setTermNumber(copyClassesTO.getTermNo());
						classes.setCreatedBy(copyClassesForm.getUserId());
						classes.setCreatedDate(new Date());
						classes.setModifiedBy(copyClassesForm.getUserId());
						classes.setLastModifiedDate(new Date());
						classes.setIsActive(true);
						classesBOList.add(classes);
						}
					}
				}
			}
			return classesBOList;
		}
	}
