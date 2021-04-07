package com.kp.cms.to.admission;

import java.io.Serializable;
import java.util.List;

public class SessionAttendnceToPm
  implements Serializable
{
  private String ampm;
  private boolean coleave;
  private String attdate;
  private boolean onleave;
  private boolean present;
  private String periodName;
  private String periodId;
  private Integer attendanceId;
  private List<String> pnames;
  private List<String> coLeaves;
  private String periods;
  private String leaves;
  
  public String getPeriods()
  {
    return this.periods;
  }
  
  public void setPeriods(String periods)
  {
    this.periods = periods;
  }
  
  public String getLeaves()
  {
    return this.leaves;
  }
  
  public void setLeaves(String leaves)
  {
    this.leaves = leaves;
  }
  
  public String getAmpm()
  {
    return this.ampm;
  }
  
  public void setAmpm(String ampm)
  {
    this.ampm = ampm;
  }
  
  public boolean isColeave()
  {
    return this.coleave;
  }
  
  public void setColeave(boolean coleave)
  {
    this.coleave = coleave;
  }
  
  public boolean isOnleave()
  {
    return this.onleave;
  }
  
  public void setOnleave(boolean onleave)
  {
    this.onleave = onleave;
  }
  
  public String getAttdate()
  {
    return this.attdate;
  }
  
  public void setAttdate(String attdate)
  {
    this.attdate = attdate;
  }
  
  public boolean isPresent()
  {
    return this.present;
  }
  
  public void setPresent(boolean present)
  {
    this.present = present;
  }
  
  public String getPeriodName()
  {
    return this.periodName;
  }
  
  public void setPeriodName(String periodName)
  {
    this.periodName = periodName;
  }
  
  public String getPeriodId()
  {
    return this.periodId;
  }
  
  public void setPeriodId(String periodId)
  {
    this.periodId = periodId;
  }
  
  public Integer getAttendanceId()
  {
    return this.attendanceId;
  }
  
  public void setAttendanceId(Integer attendanceId)
  {
    this.attendanceId = attendanceId;
  }
  
  public List<String> getPnames()
  {
    return this.pnames;
  }
  
  public void setPnames(List<String> pnames)
  {
    this.pnames = pnames;
  }
  
  public List<String> getCoLeaves()
  {
    return this.coLeaves;
  }
  
  public void setCoLeaves(List<String> coLeaves)
  {
    this.coLeaves = coLeaves;
  }
}
