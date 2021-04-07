package com.kp.cms.bo.exam;

import java.util.Date;

/**
 * Dec 17, 2009 Created By 9Elements Team
 */
public interface IExamGenBO {
	public Integer getId();

	public void setId(Integer id);

	public String getModifiedBy();

	public void setModifiedBy(String modifiedBy);

	public Date getLastModifiedDate();

	public void setLastModifiedDate(Date lastModifiedDate);

	public boolean getIsActive();

	public void setIsActive(boolean isActive);

	public String getName();

	public void setName(String name);

	public Date getCreatedDate();

	public void setCreatedDate(Date createdDate);

	public String getCreatedBy();

	public void setCreatedBy(String createdBy);

}
