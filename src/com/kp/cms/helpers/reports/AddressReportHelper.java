package com.kp.cms.helpers.reports;

import com.kp.cms.constants.CMSConstants;
import com.kp.cms.forms.reports.AddressReportForm;
import com.kp.cms.to.reports.StudentAddressTo;
import com.kp.cms.utilities.CommonUtil;
import java.io.*;
import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AddressReportHelper
{

	private static final Log log = LogFactory.getLog(AddressReportHelper.class);

   
    public static String commonSearch(AddressReportForm addressForm)
    {
        log.info("Entering commonSearch of AddressReportHelper");
        String searchCriteria = " ";
        searchCriteria = " students.registerNo != null and students.classSchemewise != null and students.isAdmitted = 1 and admAppln.isCancelled=0 and (students.isHide = 0 or students.isHide is null) and " +
                         " students.id not in (select student from ExamStudentDetentionRejoinDetails s " + 
                         		          " where ((s.detain=1 or s.discontinued=1) and (s.rejoin = 0 or s.rejoin is null))) and "; // Added by Cimi on 02/02/2013
        
        if(addressForm.getProgramId() != null && addressForm.getProgramId().length() > 0)
        {
            String classType = (new StringBuilder("admAppln.courseBySelectedCourseId.program.id = ")).append(addressForm.getProgramId()).toString();
            searchCriteria = (new StringBuilder(String.valueOf(searchCriteria))).append(classType).toString();
        }
        if(addressForm.getAcademicYear() != null && addressForm.getAcademicYear().trim().length() > 0)
        {
            String appliedYear = (new StringBuilder(" and admAppln.appliedYear = ")).append(addressForm.getAcademicYear()).toString();
            searchCriteria = (new StringBuilder(String.valueOf(searchCriteria))).append(appliedYear).toString();
        }
        if(addressForm.getStartRegisterNo() != null && addressForm.getStartRegisterNo().length() > 0 && addressForm.getEndRegisterNo() != null && addressForm.getEndRegisterNo().length() > 0)
        {
            searchCriteria = (new StringBuilder(String.valueOf(searchCriteria))).append(" and students.registerNo between '").append(addressForm.getStartRegisterNo()).append("'").append(" and ").append("'").append(addressForm.getEndRegisterNo()).append("'").toString();
        } 
        else if(addressForm.getStartRegisterNo() != null && addressForm.getStartRegisterNo().trim().length() > 0)
        {
            String fromRegisterNo = (new StringBuilder(" and students.registerNo >= '")).append(addressForm.getStartRegisterNo()).append("'").toString();
            searchCriteria = (new StringBuilder(String.valueOf(searchCriteria))).append(fromRegisterNo).toString();
        } 
        else if(addressForm.getEndRegisterNo() != null && addressForm.getEndRegisterNo().trim().length() > 0)
        {
            String endRegisterNo = (new StringBuilder(" and students.registerNo <= '")).append(addressForm.getEndRegisterNo()).append("'").toString();
            searchCriteria = (new StringBuilder(String.valueOf(searchCriteria))).append(endRegisterNo).toString();
        }
        
        log.info("Leaving commonSearch of AddressReportHelper");
        return searchCriteria;
    }

    public static List<StudentAddressTo> copyStudentAddressBOToTO(List<Object[]> studentaddressBOList, AddressReportForm addressForm)
        throws Exception
    {
        log.info("Entering copyStudentAddressBOToTO of AddressReportHelper");
        List<StudentAddressTo> studentList = new ArrayList<StudentAddressTo>();
        if(studentaddressBOList != null && !studentaddressBOList.isEmpty())
        {
        	Iterator<Object[]> addressIterator = studentaddressBOList.iterator();
            int count = 1;
            int count1 = 0;
            StudentAddressTo addressTo;
            for(; addressIterator.hasNext(); studentList.add(addressTo))
            {
                addressTo = new StudentAddressTo();
                Object obj[] = (Object[])null;
                String totalObject1 = " ";
                String totalObject2 = " ";
                String totalObject3 = " ";
                if(count == 1 && addressIterator.hasNext())
                {
                    obj = (Object[])addressIterator.next();
                    if(obj[0] != null)
                    {
                        String str = (String)obj[0];
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append(str.toUpperCase()).append("\n").toString();
                    }
                    if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    }
                    else
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    }
                    count1++;
                    addressTo.setTotalContent1(totalObject1.toUpperCase());
                    count++;
                }
                if(count == 2 && addressIterator.hasNext())
                {
                    obj = (Object[])addressIterator.next();
                    if(obj[0] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[0]).append("\n").toString();
                    }
                    if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    } else
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    }
                    count1++;
                    addressTo.setTotalContent2(totalObject2.toUpperCase());
                    count++;
                }
                if(count == 3 && addressIterator.hasNext())
                {
                    obj = (Object[])addressIterator.next();
                    if(obj[0] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[0]).append("\n").toString();
                    }
                    if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    } else
                    {
                        for(int i = 1; i < obj.length; i++)
                        {
                            if(obj[i] != null)
                            {
                                totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[i]).append("\n").toString();
                            }
                        }

                    }
                    count1++;
                    addressTo.setTotalContent3(totalObject3.toUpperCase());
                }
                if(count == 3)
                {
                    count = 1;
                }
            }

            addressForm.setCount(count1);
        }
        return studentList;
    }

    public static List<StudentAddressTo> copyStudentAddressBOToTOtoPrint(List<Object[]> studentaddressBOList, AddressReportForm addressForm)
    throws Exception
{
    log.info("Entering copyStudentAddressBOToTO of AddressReportHelper");
    List<StudentAddressTo> studentList = new ArrayList<StudentAddressTo>();
    if(studentaddressBOList != null && !studentaddressBOList.isEmpty())
    {
        Iterator<Object[]> addressIterator = studentaddressBOList.iterator();
        int count = 1;
        StudentAddressTo addressTo;
        for(; addressIterator.hasNext(); studentList.add(addressTo))
        {
            addressTo = new StudentAddressTo();
            Object obj[] = (Object[])null;
            String totalObject1 = " ";
            String totalObject2 = " ";
            String totalObject3 = " ";
            if(count == 1 && addressIterator.hasNext())
            {
                obj = (Object[])addressIterator.next();
                if(obj[0] != null)
                {
                    totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[0]).append("\n").toString();
                }
                if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                {
                    if(obj[1] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[1]).append("\n").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[2]).append(", ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[3]).append("\n").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[4]).append("\n").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[5]).append("\n").toString();
                    }
                } else
                {
                    if(obj[1] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[1]).append(" ").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[2]).append(" ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[3]).append("\n").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[4]).append(", ").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[5]).append("\n").toString();
                    }
                    if(obj[6] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[6]).append("\n").toString();
                    }
                    if(obj[7] != null)
                    {
                        totalObject1 = (new StringBuilder(String.valueOf(totalObject1))).append((String)obj[7]).toString();
                    }
                }
                addressTo.setTotalContent1(totalObject1);
                count++;
            }
            if(count == 2 && addressIterator.hasNext())
            {
                obj = (Object[])addressIterator.next();
                if(obj[0] != null)
                {
                    totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[0]).append("\n").toString();
                }
                if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                {
                    if(obj[1] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[1]).append("\n").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[2]).append(", ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[3]).append("\n").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[4]).append("\n").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[5]).append("\n").toString();
                    }
                } else
                {
                    if(obj[1] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[1]).append(" ").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[2]).append(" ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[3]).append("\n").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[4]).append(", ").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[5]).append("\n").toString();
                    }
                    if(obj[6] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[6]).append("\n").toString();
                    }
                    if(obj[7] != null)
                    {
                        totalObject2 = (new StringBuilder(String.valueOf(totalObject2))).append((String)obj[7]).append("\n").toString();
                    }
                }
                addressTo.setTotalContent2(totalObject2);
                count++;
            }
            if(count == 3 && addressIterator.hasNext())
            {
                obj = (Object[])addressIterator.next();
                if(obj[0] != null)
                {
                    totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[0]).append("\n").toString();
                }
                if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
                {
                    if(obj[1] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[1]).append("\n").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[2]).append(", ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[3]).append("\n").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[4]).append("\n").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[5]).append("\n").toString();
                    }
                } else
                {
                    if(obj[1] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[1]).append("\n").toString();
                    }
                    if(obj[2] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[2]).append(" ").toString();
                    }
                    if(obj[3] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[3]).append(" ").toString();
                    }
                    if(obj[4] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[4]).append("\n").toString();
                    }
                    if(obj[5] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[5]).append(", ").toString();
                    }
                    if(obj[6] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[6]).append("\n").toString();
                    }
                    if(obj[7] != null)
                    {
                        totalObject3 = (new StringBuilder(String.valueOf(totalObject3))).append((String)obj[7]).append("\n").toString();
                    }
                }
                addressTo.setTotalContent3(totalObject3);
            }
            if(count == 3)
            {
                count = 1;
            }
        }

    }
    return studentList;
}

       
   

    public static String buildQuery(AddressReportForm addressForm)
    {
        log.info("entered buildQuery..");
        String statusCriteria = commonSearch(addressForm);
        String selectQuery = queryBuildForSelect(addressForm);
        String searchCriteria = "";
        searchCriteria = (new StringBuilder("select students.registerNo, ")).append(selectQuery).append(" from AdmAppln admAppln inner join admAppln.students students left join admAppln.personalData.countryByParentAddressCountryId parentCountry" +
        		" left join admAppln.personalData.countryByCurrentAddressCountryId currentCountry" +
        		" left join admAppln.personalData.countryByPermanentAddressCountryId permanentCountry" +
        		" left join admAppln.personalData.stateByParentAddressStateId parentState" +
        		" left join admAppln.personalData.stateByCurrentAddressStateId currentState" +
        		" left join admAppln.personalData.stateByPermanentAddressStateId permanentState ").append("where").append(statusCriteria).toString();
        log.info("exit buildQuery..");
        return searchCriteria;
    }

    private static String queryBuildForSelect(AddressReportForm addressForm)
    {
        String query = "";
        if(addressForm.getWithName() != null && addressForm.getWithName().equalsIgnoreCase("true"))
        {
            query = (new StringBuilder(String.valueOf(query))).append("admAppln.personalData.fatherName, ").toString();
        } else
        {
            query = (new StringBuilder(String.valueOf(query))).append("admAppln.personalData.firstName, admAppln.personalData.middleName,").append(" admAppln.personalData.lastName, ").toString();
        }
        if(addressForm.getAddressType() != null && addressForm.getAddressType().equalsIgnoreCase("Current Address"))
        {
            query = (new StringBuilder(String.valueOf(query))).append("admAppln.personalData.currentAddressLine1, ").append("admAppln.personalData.currentAddressLine2,admAppln.personalData.cityByCurrentAddressCityId,currentState.name,currentCountry.name, ").append("admAppln.personalData.currentAddressZipCode ").toString();
        } else
        if(addressForm.getAddressType() != null && addressForm.getAddressType().equalsIgnoreCase("Permanent Address"))
        {
            query = (new StringBuilder(String.valueOf(query))).append("admAppln.personalData.permanentAddressLine1, ").append("admAppln.personalData.permanentAddressLine2,admAppln.personalData.cityByPermanentAddressCityId,permanentState.name,permanentCountry.name,").append("admAppln.personalData.permanentAddressZipCode ").toString();
        } else
        {
            query = (new StringBuilder(String.valueOf(query))).append("admAppln.personalData.parentAddressLine1, ").append("admAppln.personalData.parentAddressLine2, ").append("admAppln.personalData.parentAddressLine3,admAppln.personalData.cityByParentAddressCityId,parentState.name,parentCountry.name,").append("admAppln.personalData.parentAddressZipCode ").toString();
        }
        return query;
    }

    public static  void  printAddressReport(List<StudentAddressTo> list) throws IOException{
		
    	
    		log.info("Inside of createReport of Address ReportHelper");
    		JasperReport jasperReport;
    		JasperPrint jasperPrint;
    		Map parameters = new HashMap();
    		parameters.put("Title", "Address Report");
    		    try {
    				Properties prop = new Properties();
    				try {
    								InputStream in = CommonUtil.class.getClassLoader()
    							.getResourceAsStream(CMSConstants.APPLICATION_PROPERTIES);
    					prop.load(in);
    				} catch (FileNotFoundException e) {
    					   				
    					log.error("error occured in printAddressReport method of AddressReportHelper class..");
    				} catch (IOException e) {
    					log.error("error occured in printAddressReport method of AddressReportHelper class..");
    				}
    					String source=prop.getProperty("knowledgepro.reports.student.printSource");
    					jasperReport = (JasperReport) JRLoader.loadObject(CommonUtil.class.getClassLoader()
    						.getResourceAsStream(source));
    		      JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(list);

    		      jasperPrint = JasperFillManager.fillReport(
    			          jasperReport, parameters, ds);
    				OutputStream ouputStream
    			    = new FileOutputStream(new File("c://temp/temppdf.pdf"));

    			

    				
    JasperExportManager.exportReportToPdfStream(jasperPrint, ouputStream);
      JasperPrintManager.printReport(jasperPrint, true);

    			}
    	catch (Exception e) {
    		log.error("error occured in printAddressReport method of AddressReportHelper class..");
    	}
    	}

}
