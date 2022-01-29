package com.kp.cms.helpers.admin;

import com.kp.cms.bo.admin.AcademicYear;
import com.kp.cms.handlers.admin.AcademicYearHandler;
import com.kp.cms.to.admin.AcademicYearTO;
import java.util.*;
import org.apache.log4j.Logger;

public class AcademicYearHelper
{

    private static final Logger log = Logger.getLogger(AcademicYearHelper.class);
    private static volatile AcademicYearHelper academicYearHelper = null;

    private AcademicYearHelper()
    {
    }

    public static AcademicYearHelper getInstance()
    {
        if(academicYearHelper == null)
        {
            academicYearHelper = new AcademicYearHelper();
        }
        return academicYearHelper;
    }

    public List<AcademicYearTO> poupulateBOtoTO(List<AcademicYear> academicYearList)
    {
        List<AcademicYearTO> listOfAcademicYear = new ArrayList<AcademicYearTO>();
        if(academicYearList != null && !academicYearList.isEmpty())
        {
            AcademicYearTO academicYearTO = null;
            AcademicYear academicYear = null;
            for(Iterator<AcademicYear> iterator = academicYearList.iterator(); iterator.hasNext();)
            {
                academicYear = (AcademicYear)iterator.next();
                academicYearTO = new AcademicYearTO();
                academicYearTO.setId(academicYear.getId());
                academicYearTO.setYear(academicYear.getYear() == null ? null : academicYear.getYear().toString());
                if(academicYear.getIsCurrent() != null)
                {
                    if(String.valueOf(academicYear.getIsCurrent()).equals("true"))
                    {
                        academicYearTO.setIsCurrent("Yes");
                    } else
                    if(String.valueOf(academicYear.getIsCurrent()).equals("false"))
                    {
                        academicYearTO.setIsCurrent("No");
                    }
                }
                if(academicYear.getIsCurrentForAdmission()!=null){
                	if(String.valueOf(academicYear.getIsCurrentForAdmission()).equals("true")){
                		academicYearTO.setIsCurrentForAdmission("Yes");
                	}
                	else if(String.valueOf(academicYear.getIsCurrentForAdmission()).equals("false")){
                		academicYearTO.setIsCurrentForAdmission("No");
                	}
                }else{
                	academicYearTO.setIsCurrentForAdmission(" ");
                }
                listOfAcademicYear.add(academicYearTO);
                
            }

        }
        log.info("End of poupulateBOtoTO of AcademicYearHelper");
        return listOfAcademicYear;
    }

    public AcademicYear populateTOtoBO(AcademicYearTO academicYrTO)
    {
        AcademicYear academicYear = new AcademicYear();
        if(academicYrTO != null)
        {
            academicYear.setCreatedBy(academicYrTO.getCreatedBy());
            academicYear.setCreatedDate(new Date());
            academicYear.setModifiedBy(academicYrTO.getModifiedBy());
            academicYear.setLastModifiedDate(new Date());
            academicYear.setYear(AcademicYearHandler.splitYear(academicYrTO.getYear()));
            academicYear.setIsCurrent(Boolean.valueOf(academicYrTO.getIsCurrent()));
            academicYear.setIsActive(Boolean.valueOf(true));
            //code added by sudhir
            academicYear.setIsCurrentForAdmission(Boolean.valueOf(academicYrTO.getIsCurrentForAdmission()));
            //
        }
        log.info("End of populateTOtoBO of AcademicYearHelper");
        return academicYear;
    }

    public AcademicYearTO populateBOtoTOEdit(AcademicYear academicYr)
    {
        AcademicYearTO academicYrTO = new AcademicYearTO();
        if(academicYr != null)
        {
            academicYrTO.setId(academicYr.getId());
            academicYrTO.setIsCurrent(String.valueOf(academicYr.getIsCurrent()));
            academicYrTO.setYear(academicYr.getYear() == null ? null : academicYr.getYear().toString());
            //code added by sudhir
            
            academicYrTO.setIsCurrentForAdmission(String.valueOf(academicYr.getIsCurrentForAdmission()));
            //
        }
        log.info("End of populateBOtoTOEdit of AcademicYearHelper");
        return academicYrTO;
    }

    @SuppressWarnings("static-access")
	public AcademicYear populateTOtoBOUpdate(AcademicYearTO yearTO)
    {
        AcademicYear academicYear = null;
        academicYear = new AcademicYear();
        academicYear.setId(yearTO.getId());
        academicYear.setIsCurrent(Boolean.valueOf(yearTO.getIsCurrent()));
        academicYear.setYear(AcademicYearHandler.getInstance().splitYear(yearTO.getYear()));
        academicYear.setIsActive(Boolean.valueOf(true));
        academicYear.setModifiedBy(yearTO.getModifiedBy());
        academicYear.setLastModifiedDate(new Date());
        academicYear.setCreatedBy(yearTO.getCreatedBy());
        academicYear.setCreatedDate(new Date());
        //code added by sudhir
        academicYear.setIsCurrentForAdmission(Boolean.valueOf(yearTO.getIsCurrentForAdmission()));
        //
        log.info("End of populateTOtoBOUpdate of AcademicYearHelper");
        return academicYear;
    }

}
