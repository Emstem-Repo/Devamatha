package com.kp.cms.transactions.admission;

import java.util.List;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.bo.admission.PrerequisitsYearMonth;

public interface IPrerequisitesYearMonth {
	

    public abstract List<PrerequisitsYearMonth> getPrerequisiteYearMonthDetails()
        throws Exception;

   public abstract boolean addAcademicYear(PrerequisitsYearMonth prereqYearMonth)
        throws Exception;

    public abstract PrerequisitsYearMonth getPrereqYear(int year, int month)
        throws Exception;

    public abstract PrerequisitsYearMonth getPreReqYearDetailsWithId(int i)
        throws Exception;

    public abstract boolean updatePreYearMonth(PrerequisitsYearMonth prereqYearMonth)
        throws Exception;

    public abstract boolean deleteAcademicYearDetails(int i, String s)
        throws Exception;

    public abstract boolean reActivateAcademicYear(int year,int month,String s)
        throws Exception;

}
