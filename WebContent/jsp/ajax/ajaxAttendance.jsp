<%@ page contentType="text/xml;charset=UTF-8" language="java"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@page import="java.util.ArrayList"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<response>
	<c:if test="${Exam!=null}">
	<c:set var="found" value="0" />
	<fields1>
			<logic:iterate id="markhead" name="Exam" >
			<c:if test="${found == 0}">
					<logic:notEmpty  name="markhead" property="examMarksEntryDetailsTOList">
						<logic:iterate id="markHead1" name="markhead" property="examMarksEntryDetailsTOList" indexId="count">
						<Title>
						     <examName><logic:empty name="markHead1" property="examName">-</logic:empty><logic:notEmpty name="markHead1" property="examName"> <bean:write name="markHead1" property="examName"/></logic:notEmpty></examName>
						<c:set var="found" value="1" />
						</Title>
						</logic:iterate>
					</logic:notEmpty>
					</c:if>
		</logic:iterate>
	</fields1>
		
		</c:if>
	<c:if test="${Attendance!=null}">			
	<logic:iterate id="details" name="Attendance">
		
			<fields>
				<totalMark>
						<className> <bean:write name="details" property="className"/></className> 
						<subject><bean:write name="details" property="subject"/></subject>
						<absent><bean:write name="details" property="absent"/></absent>
						<attendence><bean:write name="details" property="attendence"/></attendence>
							<logic:notEmpty name="details" property="examMarksEntryDetailsTOList" >
							<logic:iterate id="mark" name="details"	property="examMarksEntryDetailsTOList" indexId="count">
								<mark><theoryMarks><logic:empty name="mark" property="theoryMarks">   </logic:empty><logic:notEmpty name="mark" property="theoryMarks"> <bean:write name="mark" property="theoryMarks"/></logic:notEmpty></theoryMarks>
									 <practicalMarks> <logic:empty name="mark" property="practicalMarks">   </logic:empty><logic:notEmpty name="mark" property="practicalMarks"><bean:write name="mark" property="practicalMarks"/></logic:notEmpty></practicalMarks>
								</mark> 
							</logic:iterate>
							</logic:notEmpty>
					</totalMark>
				</fields>
			</logic:iterate>
			</c:if>
</response>