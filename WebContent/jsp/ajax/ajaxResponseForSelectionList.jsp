<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${ToList!=null}">
		<logic:iterate id="list" name="ToList">
			<TList>
				<year><bean:write name="list" property="academicYear"/></year>
				<program><bean:write name="list" property="programName"/></program>
				<date><bean:write name="list" property="selectionProcessDate"/></date>
				<id><bean:write name="list" property="id"/> </id>
				<venue><bean:write name="list" property="venueName"/> </venue>
				<cutOffDate><bean:write name="list" property="cutOffDate"/> </cutOffDate>
				<maxSeatsOnline><bean:write name="list" property="maxNoSeatsOnline"/> </maxSeatsOnline>
				<maxSeatsOffline><bean:write name="list" property="maxNoSeatsOffline"/> </maxSeatsOffline>
				<totalAppliedStudents><bean:write name="list" property="totalAppliedStudents"/> </totalAppliedStudents>
			</TList>
		</logic:iterate>
	</c:if>
</response>