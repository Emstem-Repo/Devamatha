<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">

<script type="text/javascript">
	
</script>
<html:form action="HostelAttendance" method="post">
	<html:hidden property="formName" value="attendanceForm" />
	<html:hidden property="pageType" value="1" />
	<html:hidden property="id" styleId="id"/>
	<html:hidden property="hostelName" styleId="hostelName"/>
	<html:hidden property="groupName" styleId="groupName"/>
	<html:hidden property="method" styleId="method"	value="loadStudent" />
	
</html:form>	    