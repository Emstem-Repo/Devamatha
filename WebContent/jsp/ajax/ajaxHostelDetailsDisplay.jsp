<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>

	<c:if test="${HDetails!=null}">			
			<HostelDetails>
						<hostelName> <bean:write name="HDetails" property="hostelName"/></hostelName> 
						<block><bean:write name="HDetails" property="block"/></block>
						<unit><bean:write name="HDetails" property="unit"/></unit>
						<roomNo><bean:write name="HDetails" property="roomNo"/></roomNo>
						<bedNo><bean:write name="HDetails" property="bedNo"/></bedNo>
							
			</HostelDetails>
	</c:if>
	<c:if test="${HLeaveDetails!=null}">			
		<logic:iterate id="details" name="HLeaveDetails">
			<LeaveDetails>
						<SNo> <bean:write name="details" property="id"/></SNo> 
						<leaveFrom><bean:write name="details" property="leaveFrom"/></leaveFrom>
						<leaveTo><bean:write name="details" property="leaveTo"/></leaveTo>
						<status><bean:write name="details" property="status"/></status>
			</LeaveDetails>
		</logic:iterate>
	</c:if>
	<c:if test="${HStudentAttendance!=null}">			
		<logic:iterate id="details" name="HStudentAttendance">
			<HStudAttendance>
						<date><bean:write name="details" property="date"/></date>
						<session><bean:write name="details" property="session"/></session>
			</HStudAttendance>
		</logic:iterate>
	</c:if>
	<c:if test="${HDispDetails!=null}">			
		<logic:iterate id="details" name="HDispDetails">
			<HDisciplinary>
						<disciplineTypeName><bean:write name="details" property="disciplineTypeName"/></disciplineTypeName>
						<date><bean:write name="details" property="date"/></date>
						<description><bean:write name="details" property="description"/></description>
			</HDisciplinary>
		</logic:iterate>
	</c:if>
	<c:if test="${HFineDetails!=null}">			
		<logic:iterate id="details" name="HFineDetails">
			<HFine>
						<category><bean:write name="details" property="category"/></category>
						<date><bean:write name="details" property="date"/></date>
						<amount><bean:write name="details" property="amount"/></amount>
						<pay><bean:write name="details" property="pay"/></pay>
						<remarks><bean:write name="details" property="remarks"/></remarks>
			</HFine>
		</logic:iterate>
	</c:if>
</response>