package com.kp.cms.helpers.admission;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.kp.cms.bo.admission.PrerequisitsYearMonth;
import com.kp.cms.handlers.admission.PrerequisiteYearMonthHandler;
import com.kp.cms.to.admission.PrerequisiteYearMonthTO;

	public class PrerequisiteYearMonthHelper
	{

	    private static final Logger log = Logger.getLogger(PrerequisiteYearMonthHelper.class);
	    private static volatile PrerequisiteYearMonthHelper prerequisiteYearMonthHelper = null;

	    private PrerequisiteYearMonthHelper()
	    {
	    }

	    public static PrerequisiteYearMonthHelper getInstance()
	    {
	        if(prerequisiteYearMonthHelper == null)
	        {
	        	prerequisiteYearMonthHelper = new PrerequisiteYearMonthHelper();
	        }
	        return prerequisiteYearMonthHelper;
	    }

	    public List<PrerequisiteYearMonthTO> poupulateBOtoTO(List<PrerequisitsYearMonth> yearList)
	    {
	        List<PrerequisiteYearMonthTO> listOfPrerequisitsYearMonth = new ArrayList<PrerequisiteYearMonthTO>();
	        if(yearList != null && !yearList.isEmpty())
	        {
	        	PrerequisiteYearMonthTO prYearMonthTO = null;
	            PrerequisitsYearMonth prYearMonth = null;
	            for(Iterator<PrerequisitsYearMonth> iterator = yearList.iterator(); iterator.hasNext();)
	            {
	            	prYearMonth = (PrerequisitsYearMonth)iterator.next();
	                prYearMonthTO = new PrerequisiteYearMonthTO();
	                prYearMonthTO.setId(prYearMonth.getId());
	               
	                if(prYearMonth.getYear() != null && prYearMonth.getYear()>0)
	                {	                    
	                 prYearMonthTO.setYear(String.valueOf(prYearMonth.getYear()));
	                }
	                if(prYearMonth.getMonth()!=null && prYearMonth.getMonth()>0){
	                	if(prYearMonth.getMonth()==1)
	                		prYearMonthTO.setMonth("JAN");
	    				if(prYearMonth.getMonth()==2)
	    					prYearMonthTO.setMonth("FEB");
	    				if(prYearMonth.getMonth()==3)
	    					prYearMonthTO.setMonth("MAR");
	    				if(prYearMonth.getMonth()==4)
	    					prYearMonthTO.setMonth("APR");
	    				if(prYearMonth.getMonth()==5)
	    					prYearMonthTO.setMonth("MAY");
	    				if(prYearMonth.getMonth()==6)
	    					prYearMonthTO.setMonth("JUN");
	    				if(prYearMonth.getMonth()==7)
	    					prYearMonthTO.setMonth("JUL");
	    				if(prYearMonth.getMonth()==8)
	    					prYearMonthTO.setMonth("AUG");
	    				if(prYearMonth.getMonth()==9)
	    					prYearMonthTO.setMonth("SEP");
	    				if(prYearMonth.getMonth()==10)
	    					prYearMonthTO.setMonth("OCT");
	    				if(prYearMonth.getMonth()==11)
	    					prYearMonthTO.setMonth("NOV");
	    				if(prYearMonth.getMonth()==12)
	    					prYearMonthTO.setMonth("DEC");
	                }
	                listOfPrerequisitsYearMonth.add(prYearMonthTO);
	            }
	        }
	        log.info("End of poupulateBOtoTO of PrerequisiteYearMonthHelper");
	        return listOfPrerequisitsYearMonth;
	    }

	   public PrerequisitsYearMonth populateTOtoBO(PrerequisiteYearMonthTO prereqYrTO)
	    {
	        PrerequisitsYearMonth prereqYr = new PrerequisitsYearMonth();
	        if(prereqYrTO != null)
	        {
	          
	            if(prereqYrTO.getYear()!=null && !prereqYrTO.getYear().isEmpty())
	            {
	            	prereqYr.setYear(Integer.parseInt(prereqYrTO.getYear()));
	            }
	            if(prereqYrTO.getMonth()!=null && !prereqYrTO.getMonth().isEmpty())
	            {
	            	prereqYr.setMonth(Integer.parseInt(prereqYrTO.getMonth()));
	            }
	            prereqYr.setCreatedBy(prereqYrTO.getCreatedBy());
	        	prereqYr.setCreatedDate(new Date());
	        	prereqYr.setModifiedBy(prereqYrTO.getModifiedBy());
	        	prereqYr.setLastModifiedDate(new Date());
	        	prereqYr.setIsActive(Boolean.valueOf(true));
	          
	        }
	        log.info("End of populateTOtoBO of PrerequisiteYearMonthHelper");
	        return prereqYr;
	    }

	   public PrerequisiteYearMonthTO populateBOtoTOEdit(PrerequisitsYearMonth prereqYearMonth)
	    {
	        PrerequisiteYearMonthTO prereqYearMonthTO = new PrerequisiteYearMonthTO();
	        if(prereqYearMonth != null)
	        {
	        	prereqYearMonthTO.setId(prereqYearMonth.getId());
	        	 if(prereqYearMonth.getYear()!= null && prereqYearMonth.getYear()>0)
	        	 {
	        		 prereqYearMonthTO.setYear(String.valueOf(prereqYearMonth.getYear())) ;
	        	 }
	        	 if(prereqYearMonth.getMonth()!= null && prereqYearMonth.getMonth()>0)
	        	 {
	        		 prereqYearMonthTO.setMonth(String.valueOf(prereqYearMonth.getMonth())) ;
	        	 }
	        	// prereqYearMonthTO.setIsActive()
	        
	           
	        }
	        log.info("End of populateBOtoTOEdit of PrerequisiteYearMonthHelper");
	        return prereqYearMonthTO;
	    }

	    @SuppressWarnings("static-access")
		public PrerequisitsYearMonth populateTOtoBOUpdate(PrerequisiteYearMonthTO yearTO)
	    {
	        PrerequisitsYearMonth prereqYrMonth = null;
	        prereqYrMonth = new PrerequisitsYearMonth();
	        prereqYrMonth.setId(yearTO.getId());
	        prereqYrMonth.setYear(Integer.parseInt(yearTO.getYear()));
	        prereqYrMonth.setMonth(Integer.parseInt(yearTO.getMonth()));
	        prereqYrMonth.setIsActive(Boolean.valueOf(true));
	        prereqYrMonth.setModifiedBy(yearTO.getModifiedBy());
	        prereqYrMonth.setLastModifiedDate(new Date());
	        prereqYrMonth.setCreatedBy(yearTO.getCreatedBy());
	        prereqYrMonth.setCreatedDate(new Date());
	       
	        log.info("End of populateTOtoBOUpdate of PrerequisiteYearMonthHelper");
	        return prereqYrMonth;
	    }

	}



