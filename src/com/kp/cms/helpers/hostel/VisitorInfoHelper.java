package com.kp.cms.helpers.hostel;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.kp.cms.bo.admin.AdmAppln;
import com.kp.cms.bo.admin.Employee;
import com.kp.cms.bo.admin.HlApplicationForm;
import com.kp.cms.bo.admin.HlHostel;
import com.kp.cms.bo.admin.HlRoom;
import com.kp.cms.bo.admin.HlRoomTransaction;
import com.kp.cms.bo.admin.HlVisitorInfo;
import com.kp.cms.bo.admin.PersonalData;
import com.kp.cms.bo.admin.Student;
import com.kp.cms.forms.hostel.VisitorInfoForm;
import com.kp.cms.to.hostel.VisitorInfoTO;
import com.kp.cms.utilities.CommonUtil;

public class VisitorInfoHelper
{

    private static volatile VisitorInfoHelper visitorInfoHelper = null;
    private static final Log log = LogFactory.getLog(VisitorInfoHelper.class);

    private VisitorInfoHelper()
    {
    }

    public static VisitorInfoHelper getInstance()
    {
        if(visitorInfoHelper == null)
        {
            visitorInfoHelper = new VisitorInfoHelper();
        }
        return visitorInfoHelper;
    }

    public String getSearchQuery(VisitorInfoForm visitorInfoForm, String roomStatusId)
        throws Exception
    {
        String searchQuery = commonSearch(roomStatusId,visitorInfoForm.getHostelId());
        if(visitorInfoForm.getRegisterNo().trim() != null && !visitorInfoForm.getRegisterNo().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and s.registerNo='").append(visitorInfoForm.getRegisterNo()).append("'").toString();
        }
        if(visitorInfoForm.getStaffId().trim() != null && !visitorInfoForm.getStaffId().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and h.employee.id= '").append(visitorInfoForm.getStaffId()).append("'").toString();
        }
        if(visitorInfoForm.getName().trim() != null && !visitorInfoForm.getName().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and h.admAppln.personalData.firstName like'").append(visitorInfoForm.getName()).append("%'").toString();
        }
        searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" group by h.hlApplicationForm.id").toString();
        return searchQuery;
    }

    public String getSearchQueryForEmployee(VisitorInfoForm visitorInfoForm, String roomStatusId)
        throws Exception
    {
        String searchQuery = "select h from HlRoomTransaction h where h.hlApplicationForm.isActive=1 and h.isActive=1 and h.hlApplicationForm.hlStatus.id="+roomStatusId+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId="+visitorInfoForm.getHostelId();
        if(visitorInfoForm.getStaffId().trim() != null && !visitorInfoForm.getStaffId().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and h.employee.code= '").append(visitorInfoForm.getStaffId()).append("'").toString();
        }
        if(visitorInfoForm.getName().trim() != null && !visitorInfoForm.getName().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and h.employee.firstName like'").append(visitorInfoForm.getName()).append("%'").toString();
        }
        if(visitorInfoForm.getRegisterNo().trim() != null && !visitorInfoForm.getRegisterNo().trim().isEmpty())
        {
            searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" and s.registerNo='").append(visitorInfoForm.getRegisterNo()).append("'").toString();
        }
        searchQuery = (new StringBuilder(String.valueOf(searchQuery))).append(" group by h.hlApplicationForm.id").toString();
        return searchQuery;
    }

    private String commonSearch(String roomStatusId,String hostelId)
        throws Exception
    {
        String query = "select h from HlRoomTransaction h join  h.admAppln.students s where h.hlApplicationForm.isActive=1 and h.hlApplicationForm.hlStatus.id=2 and h.isActive=1 and h.hlApplicationForm.hlStatus.id="+roomStatusId+" and h.hlApplicationForm.hlHostelByHlApprovedHostelId="+hostelId;
        return query;
    }

    public List<VisitorInfoTO> convertListOfBOtoTO(List<HlRoomTransaction> appList)
        throws Exception
    {
        List<VisitorInfoTO> list = new ArrayList<VisitorInfoTO>();
        if(appList != null)
        {
            for(Iterator<HlRoomTransaction> Itr = appList.iterator(); Itr.hasNext();)
            {
                HlRoomTransaction hlRoomTransaction = (HlRoomTransaction)Itr.next();
                VisitorInfoTO residentTo = new VisitorInfoTO();
                AdmAppln admAppln = hlRoomTransaction.getAdmAppln();
                if(admAppln != null)
                {
                    if(hlRoomTransaction.getAdmAppln().getApplnNo() != 0 && Integer.toString(hlRoomTransaction.getAdmAppln().getApplnNo()) != null)
                    {
                        residentTo.setApplNo(Integer.toString(hlRoomTransaction.getAdmAppln().getApplnNo()));
                    }
                    if(admAppln.getAppliedYear() != null && admAppln.getAppliedYear().intValue() > 0)
                    {
                        residentTo.setYear(admAppln.getAppliedYear().intValue());
                    }
                    if(admAppln.getCourseBySelectedCourseId() != null && admAppln.getCourseBySelectedCourseId().getId() > 0)
                    {
                        residentTo.setCourseId(admAppln.getCourseBySelectedCourseId().getId());
                    }
                    PersonalData personalData = admAppln.getPersonalData();
                    Set<Student> studentSet = admAppln.getStudents();
                    Iterator<Student> itr = studentSet.iterator();
                    if(itr.hasNext())
                    {
                        Student student = (Student)itr.next();
                        residentTo.setStudentId(student.getId());
                    }
                    if(personalData.getMiddleName() == null && personalData.getLastName() == null)
                    {
                        residentTo.setStudentName(personalData.getFirstName());
                    } else
                    if(personalData.getLastName() == null)
                    {
                        residentTo.setStudentName((new StringBuilder(String.valueOf(personalData.getFirstName()))).append(" ").append(personalData.getMiddleName()).toString());
                    } else
                    if(personalData.getMiddleName() == null)
                    {
                        residentTo.setStudentName((new StringBuilder(String.valueOf(personalData.getFirstName()))).append(" ").append(personalData.getLastName()).toString());
                    } else
                    {
                        residentTo.setStudentName((new StringBuilder(String.valueOf(personalData.getFirstName()))).append(" ").append(personalData.getMiddleName()).append(" ").append(personalData.getLastName()).toString());
                    }
                }
                HlRoom hlRoom = hlRoomTransaction.getHlRoom();
                if(hlRoom != null)
                {
                    if(hlRoom.getId() > 0)
                    {
                        residentTo.setRoomId(hlRoom.getId());
                    }
                    if(hlRoom.getFloorNo() != null)
                    {
                        residentTo.setFloorNo(hlRoom.getFloorNo());
                    }
                    if(hlRoom.getName() != null)
                    {
                        residentTo.setRoomNo(hlRoom.getName());
                    }
                    if(hlRoom.getHlHostel() != null)
                    {
                        residentTo.setHostelId(hlRoom.getHlHostel().getId());
                    }
                }
                HlApplicationForm hlApplicationForm = hlRoomTransaction.getHlApplicationForm();
                if(hlApplicationForm != null)
                {
                    if(hlApplicationForm.getId() > 0)
                    {
                        residentTo.setHostelApplnId(hlApplicationForm.getId());
                    }
                    if(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName() != null)
                    {
                        residentTo.setHostelType(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName());
                    }
                    if(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName() != null)
                    {
                        residentTo.setRoomType(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
                    }
                    list.add(residentTo);
                }
            }

        }
        return list;
    }

    public List<VisitorInfoTO> convertListOfBOtoTOForEmployee(List<HlRoomTransaction> appList)
        throws Exception
    {
        List<VisitorInfoTO> list = new ArrayList<VisitorInfoTO>();
        if(!appList.isEmpty() || appList != null)
        {
            for(Iterator<HlRoomTransaction> Itr = appList.iterator(); Itr.hasNext();)
            {
                HlRoomTransaction hlRoomTransaction = (HlRoomTransaction)Itr.next();
                VisitorInfoTO residentTo = new VisitorInfoTO();
                Employee emp = hlRoomTransaction.getEmployee();
                if(emp != null)
                {
                    if(emp.getId() > 0)
                    {
                        residentTo.setEmpId(emp.getId());
                    }
                 /*   if(emp.getMiddleName() == null && emp.getLastName() == null)
                    {
                        residentTo.setStudentName(emp.getFirstName());
                    } else
                    if(emp.getLastName() == null)
                    {
                        residentTo.setStudentName((new StringBuilder(String.valueOf(emp.getFirstName()))).append(" ").append(emp.getMiddleName()).toString());
                    } else
                    if(emp.getMiddleName() == null)
                    {
                        residentTo.setStudentName((new StringBuilder(String.valueOf(emp.getFirstName()))).append(" ").append(emp.getLastName()).toString());
                    } else
                    {*/
                        residentTo.setStudentName(emp.getFirstName());
                   // }
                }
                HlRoom hlRoom = hlRoomTransaction.getHlRoom();
                if(hlRoom != null)
                {
                    if(hlRoom.getId() > 0)
                    {
                        residentTo.setRoomId(hlRoom.getId());
                    }
                    if(hlRoom.getFloorNo() != null)
                    {
                        residentTo.setFloorNo(hlRoom.getFloorNo());
                    }
                    if(hlRoom.getName() != null)
                    {
                        residentTo.setRoomNo(hlRoom.getName());
                    }
                    if(hlRoom.getHlHostel() != null)
                    {
                        residentTo.setHostelId(hlRoom.getHlHostel().getId());
                    }
                }
                HlApplicationForm hlApplicationForm = hlRoomTransaction.getHlApplicationForm();
                if(hlApplicationForm != null)
                {
                    if(hlApplicationForm.getId() > 0)
                    {
                        residentTo.setHostelApplnId(hlApplicationForm.getId());
                    }
                    if(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName() != null)
                    {
                        residentTo.setHostelType(hlApplicationForm.getHlHostelByHlAppliedHostelId().getName());
                    }
                    if(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName() != null)
                    {
                        residentTo.setRoomType(hlApplicationForm.getHlRoomTypeByHlAppliedRoomTypeId().getName());
                    }
                    list.add(residentTo);
                }
            }

        }
        return list;
    }

    public HlVisitorInfo getBOFromForm(VisitorInfoForm visitorInfoForm)
        throws Exception
    {
        HlVisitorInfo hlVisitorInfo = null;
        if(visitorInfoForm.getVto() != null)
        {
            hlVisitorInfo = new HlVisitorInfo();
            VisitorInfoTO vto = visitorInfoForm.getVto();
            if(vto.getHostelApplnId() > 0)
            {
                HlApplicationForm hlapp = new HlApplicationForm();
                hlapp.setId(vto.getHostelApplnId());
                hlVisitorInfo.setHlApplicationForm(hlapp);
            }
            if(vto.getHostelId() > 0)
            {
                HlHostel hlHostel = new HlHostel();
                hlHostel.setId(vto.getHostelId());
                hlVisitorInfo.setHlHostel(hlHostel);
            }
            if(vto.getEmpId() > 0)
            {
                Employee emp = new Employee();
                emp.setId(vto.getEmpId());
                hlVisitorInfo.setEmployee(emp);
            }
            hlVisitorInfo.setFirstName(visitorInfoForm.getFirstName());
            hlVisitorInfo.setLastName(visitorInfoForm.getLastName());
            hlVisitorInfo.setPhoto(visitorInfoForm.getPhotoFile().getFileData());
            hlVisitorInfo.setRelationship(visitorInfoForm.getRelationShip());
            hlVisitorInfo.setVisitorId(visitorInfoForm.getVisitorId());
            hlVisitorInfo.setInDatetime(CommonUtil.ConvertStringToSQLDate(visitorInfoForm.getDateIn()));
            hlVisitorInfo.setOutDatetime(CommonUtil.ConvertStringToSQLDate(visitorInfoForm.getDateOut()));
            hlVisitorInfo.setCreatedBy(visitorInfoForm.getUserId());
            hlVisitorInfo.setCreatedDate(new Date());
            hlVisitorInfo.setModifiedBy(visitorInfoForm.getUserId());
            hlVisitorInfo.setLastModifiedDate(new Date());
            hlVisitorInfo.setIsActive(Boolean.valueOf(true));
        }
        return hlVisitorInfo;
    }

}
