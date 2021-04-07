package com.kp.cms.helpers.admin;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.kp.cms.bo.admin.Course;
import com.kp.cms.bo.admin.Preferences;
import com.kp.cms.forms.admin.PreferencesForm;
import com.kp.cms.to.admin.CourseTO;
import com.kp.cms.to.admin.PreferencesTO;
import com.kp.cms.to.admin.ProgramTO;
import com.kp.cms.to.admin.ProgramTypeTO;

public class PreferencesHelper {
	public static volatile PreferencesHelper preferencesHelper = null;

	public static PreferencesHelper getInstance() {
		if (preferencesHelper == null) {
			preferencesHelper = new PreferencesHelper();
			return preferencesHelper;
		}
		return preferencesHelper;
	}
	/**
	 * assigning list from form to TO
	 * @param preferencesForm
	 * @return
	 * @throws Exception
	 */
	public PreferencesTO assignListFromForm(PreferencesForm preferencesForm) throws Exception {
		PreferencesTO preferencesTO = new PreferencesTO();
		CourseTO courseTO = new CourseTO();
		ProgramTO programTO = new ProgramTO();
		preferencesTO.setPrefCourseId(Integer.parseInt(preferencesForm.getPrefProgramId()));
		preferencesTO.setPrefProgramId(Integer.parseInt(preferencesForm.getPrefCourseId()));
		courseTO.setName(preferencesForm.getPrefCourseName());
		programTO.setName(preferencesForm.getPrefProgName());
		courseTO.setProgramTo(programTO);
		preferencesTO.setCourseTO(courseTO);
		preferencesTO.setCourseId(Integer.parseInt(preferencesForm.getCourseId()));
		return preferencesTO;

	}

	/**
	 * 
	 * @param  preferencesForm creates BO from preferencesForm 
	 *            
	 * @return Preferences BO object
	 */

	public Preferences populatePreferencesDataFromForm(PreferencesForm preferencesForm ) throws Exception {
		Preferences preferences = new Preferences();

		Course course = new Course();
		course.setId(Integer.parseInt(preferencesForm.getCourseId()));
		preferences.setCourseByCourseId(course);
		Course precourse = new Course();
		precourse.setId(Integer.parseInt(preferencesForm.getPrefCourseId()));
		preferences.setCourseByPrefCourseId(precourse);
		preferences.setIsActive(true);
		if(preferencesForm.getPrefId() != null && !preferencesForm.getPrefId().equals("")){
			preferences.setId(Integer.parseInt(preferencesForm.getPrefId()));
		}
			
		return preferences;
	}
	
	/**
	 * used for UI display
	 * @param preferencesList
	 * @return
	 */
	public List<PreferencesTO> copyPreferencesBosToTos(List<Preferences> preferencesList) {
		List<PreferencesTO> preferencesTOList = new ArrayList<PreferencesTO>();
		Iterator<Preferences> iterator = preferencesList.iterator();
		Preferences preferences;
		PreferencesTO preferencesTO;
		CourseTO courseTO;
		CourseTO preCourseTO;
		ProgramTO programTO;
		ProgramTO prefProgramTO;
		
		ProgramTypeTO programTypeTO;
		
		
		while (iterator.hasNext()) {
			preferencesTO = new PreferencesTO();
			courseTO = new CourseTO();
			programTO = new ProgramTO();
			programTypeTO = new ProgramTypeTO();
			preferences = (Preferences) iterator.next();
			
			//---------course
			preferencesTO.setCourseId(preferences.getCourseByCourseId().getId());

			programTypeTO.setProgramTypeId(preferences.getCourseByCourseId().getProgram().getProgramType().getId());
			programTypeTO.setProgramTypeName(preferences.getCourseByCourseId().getProgram().getProgramType().getName());
			
			programTO.setId(preferences.getCourseByCourseId().getProgram().getId());
			programTO.setName(preferences.getCourseByCourseId().getProgram().getName());
			programTO.setProgramTypeTo(programTypeTO);

			
			courseTO.setProgramTo(programTO);
			courseTO.setId(preferences.getCourseByCourseId().getId());
			courseTO.setName(preferences.getCourseByCourseId().getName());
			preferencesTO.setCourseTO(courseTO);
			//----------
			
			///------------preferences
			preCourseTO = new CourseTO();
			prefProgramTO = new ProgramTO();
			prefProgramTO.setId(preferences.getCourseByPrefCourseId().getProgram().getId());
			prefProgramTO.setName(preferences.getCourseByPrefCourseId().getProgram().getName());
			preCourseTO.setProgramTo(prefProgramTO);
			preCourseTO.setId(preferences.getCourseByPrefCourseId().getId());
			preCourseTO.setName(preferences.getCourseByPrefCourseId().getName());
			preferencesTO.setPrefCourseTO(preCourseTO);

			
			courseTO.setName(preferences.getCourseByCourseId().getName());
			
			

			preferencesTO.setPrefCourseId(preferences.getCourseByPrefCourseId().getId());
			preferencesTO.setPrefId(preferences.getId());
			
			preferencesTOList.add(preferencesTO);
		}
		return preferencesTOList;
	}
	
	
}
