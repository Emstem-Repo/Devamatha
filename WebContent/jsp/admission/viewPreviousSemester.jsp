<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@page import="com.kp.cms.constants.CMSConstants"%>

<head>
<title><bean:message key="knowledgepro.title"/> </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">


<script language="JavaScript" src="js/calendar_us.js"></script>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<link href="css/styles.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 5px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style></head>


<html:form action="/disciplinaryDetails.do">
<html:hidden property="method" value=""/>

<html:hidden property="formName" value="disciplinaryDetailsForm"/>
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Admission <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admissionForm.PreviousSemester.main.label" />&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"><bean:message key="knowledgepro.admissionForm.PreviousSemester.main.label"/></strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
		<tr><td colspan="3" align="left"><div id="errorMessage"><html:errors/></div>
		</td>
		</tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
              <table width="100%" height="347"  border="0" cellpadding="0" cellspacing="0">
                
              <tr>
                   <td height="35" colspan="6" class="body" >
                   <table width="100%" cellspacing="1" cellpadding="2">
                     <tr bgcolor="#FFFFFF">
                       <td height="10" colspan="22"> </td>
                     </tr>
                                 
                         <tr>
		<td colspan="2" align="left"><span class="heading">&nbsp;Attendance Details </span></td>
						</tr>
						<tr>
							<td colspan="2"><table width="100%" border="0" align="center" cellpadding="0"
								cellspacing="0">
                              <tr>
                                <td><img src="images/01.gif" width="5" height="5"></td>
                                <td width="914" background="images/02.gif"></td>
                                <td><img src="images/03.gif" width="5" height="5"></td>
                              </tr>
                              <tr>
                                <td width="5" background="images/left.gif"></td>
                                <td width="100%" height="91" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                                    <tr>
                                      <td width="20%" height="25" align="center" class="row-odd">Class</td>
                                      <td width="50%" height="25" align="center" class="row-odd"> Subject</td>
                                        <td width="50%" height="25" align="center" class="row-odd"> Absent</td>
                                      <td height="25" class="row-odd" width="15%" align="center">Attendance % </td>
                                      <c:set var = "found" value="0"/>
									<%int examCount = 0; %>
									<logic:iterate id="markHead" name = "DisciplinaryDetailsForm" property="listCourseDetails" indexId="count">
										<c:if test="${found == 0}">
											<logic:notEmpty name = "markHead" property="examMarksEntryDetailsTOList">
											<logic:iterate id="markHead1" name = "markHead" property="examMarksEntryDetailsTOList" >
											<td height="25" class="row-odd" width="10%">
											<table width="100%">
											<tr>
											<td>
												<div align="center"><bean:write name = "markHead1" property="examName"/> </div>
												<c:set var = "found" value="1"/>
												<%examCount = examCount + 1; %>
											</td>
											</tr>
											<tr>
											<td width = "50%" align="left">T</td> <td width = "50%" align="right">P</td>
											</tr>
										
											</table>
											</td>
											</logic:iterate>
											
											</logic:notEmpty>
										</c:if>
									</logic:iterate>
                                    </tr>
                                    
                                     <logic:iterate id="details" name="DisciplinaryDetailsForm"
									type="com.kp.cms.to.admission.CourseDetailsTO"
									property="listCourseDetails" indexId="count">
									<c:choose>
									<c:when test="${count%2==0}">
									<tr class="row-even">
									</c:when>
									<c:otherwise>
									<tr class="row-white">
									</c:otherwise>
									</c:choose>
										<td ><bean:write
													property="className" name="details" /></td>
										<td ><bean:write
													property="subject" name="details" /></td>
										<td> 
										<A HREF="javascript:winOpen('<bean:write name="details" property="subjectID" />',
										'<bean:write name="details" property="studentId" />',
										'<bean:write name="details" property="absent" />');">										
										<bean:write name="details" property="absent" /></A>
										</td>			
										<td >
										<bean:write
													property="attendence" name="details" /></td>
									
									<logic:notEmpty name="details" property="examMarksEntryDetailsTOList">
										<logic:iterate id="mark" name = "details" property="examMarksEntryDetailsTOList">
										
											<td height="25" width="10%">
											<table width="100%">
											<tr>
											<td width="50%" align="left">
											<bean:write 
											name = "mark" property="theoryMarks"/></td>
											<td width="50%" align="right"> <bean:write 
											name = "mark" property="practicalMarks"/>
											</td>
											</tr>
											</table>
											</td>
										</logic:iterate>
									</logic:notEmpty>
										<logic:empty name="details" property="examMarksEntryDetailsTOList">
											<%if(examCount > 0){
												for(int i=1;i<=examCount; i++){%>
												<td width="10%" height="25" align="center">&nbsp;</td>	
											<%} } %>
										</logic:empty>
								</logic:iterate>
								
                                </table></td>
                                <td background="images/right.gif" width="5" height="5"></td>
                              </tr>
                              
                              
                              <!-- Code By Mary for links of previous semesters -->
                              
        
     
      <tr>
                                <td height="5"><img src="images/04.gif" width="5"
										height="5"></td>
                                <td background="images/05.gif"></td>
                                <td><img src="images/06.gif"></td>
                              </tr>
                            </table></td>
						</tr>
                 
                 <tr>
                   <td height="35" colspan="6" class="body" >
		
                <table width="100%"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td width="45%" height="35"><div align="right"> </div></td>
                   <td width="2%"><html:button property="" onclick="javascript:self.close();" styleClass="formbutton" value="Close"></html:button></td>
                    <td width="53%"></td>
                  </tr>
                </table>
                </td>
               </tr>
                 
                
             
           </table>
       
      </tr>
     
    </table></td>
  </tr>
</table>
</td>
</tr></table>
</html:form>


