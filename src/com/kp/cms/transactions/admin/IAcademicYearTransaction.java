package com.kp.cms.transactions.admin;

import com.kp.cms.bo.admin.AcademicYear;
import java.util.List;

public interface IAcademicYearTransaction
{

    public abstract List<AcademicYear> getAcademicYearDetails()
        throws Exception;

    public abstract boolean addAcademicYear(AcademicYear academicyear)
        throws Exception;

    public abstract AcademicYear getAcademicYear(int i)
        throws Exception;

    public abstract AcademicYear getAcademicYearDetailsWithId(int i)
        throws Exception;

    public abstract boolean updateAcademicYear(AcademicYear academicyear)
        throws Exception;

    public abstract boolean deleteAcademicYearDetails(int i, String s)
        throws Exception;

    public abstract boolean reActivateAcademicYear(int i, String s)
        throws Exception;
}
