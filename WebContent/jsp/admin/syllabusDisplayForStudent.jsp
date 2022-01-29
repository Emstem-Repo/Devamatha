<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function cancelAction() {
	document.location.href = "SyllabusDisplay.do?method=initSyllabusDisplayForStudent";
}

function getDownloadFile(paperCode,admitedYear){
	var url="SyllabusDisplayDownloads.do?method=getStreamInfo&paperCode="+paperCode+"&admitedYear="+admitedYear;
			
		win2 = window.open(url, "Download File", "left=20,top=20,width=900,height=900,toolbar=1,resizable=0,scrollbars=1");
}

</script>
</head>
<html:form action="/SyllabusDisplay" method="POST">
<html:hidden property="formName" value="syllabusDisplayForStudentForm" />
<html:hidden property="method" value="getStudentBacklocks" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admin"/> <span class="Bredcrumbs">&gt;&gt; Syllabus Display For Student &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" >Syllabus Display For Student </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="right"><span class='MandatoryMark'><bean:message
					key="knowledgepro.mandatoryfields" /></span></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="44" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              
              <tr >
 				<td width="10%" height="25" class="row-odd" ><div align="right"><bean:message key="knowledgepro.hostel.reservation.registerNo"/></div></td>
 				<td width="10%" height="25" class="row-even" ><html:text property="registrNo" styleId="registrNo" size="15"/></td>
               </tr>

            </table></td>
            <td width="5" height="30"  background="images/right.gif"></td>
          </tr>
          <tr>
            <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
            <td background="images/05.gif"></td>
            <td><img src="images/06.gif" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      
      
      
      
      
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr bgcolor="#FFFFFF">
            <td width="100%" height="20" colspan="4">
            <table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr><td style="height: 10px" align="center" colspan="1"></td></tr>
              <tr>
				<td align="center">
				<html:submit styleClass="formbutton" ><bean:message key="knowledgepro.submit" /></html:submit>&nbsp;&nbsp;
				<html:button  property="" styleClass="formbutton" value="Cancel" onclick="cancelAction()" />						
				</td>
			</tr>              
			<tr><td style="height: 10px" align="center" colspan="1"></td></tr>			
            </table>
            </td>
          </tr>
          
          
          <logic:notEmpty name="syllabusDisplayForStudentForm" property="syllabusDisplayForStudentToList">	
          <tr>
            <td height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
              <tr>
                <td ><img src="images/01.gif" width="5" height="5" /></td>
                <td width="914" background="images/02.gif"></td>
                <td><img src="images/03.gif" width="5" height="5" /></td>
              </tr>
              <tr>
                <td width="5"  background="images/left.gif"></td>
                <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                  <tr>
					<td height="25" width="7%" class="row-odd">
					<div align="center"><bean:message key="knowledgepro.slno"/></div>
					</td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.supplimentary.display.dateofjoining.year"/></div> </td>
					<td height="25" width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.supplimentary.display.exam.attempted.year"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.display.semester"/></div> </td>
					<td height="25" width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.paper.code"/></div> </td>
					<td height="25" width="22%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.paper.name"/></div> </td>
					<td height="25" width="8%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.num.chances.left"/></div> </td>
					<td height="25" width="10%" class="row-odd"><div align="center"><bean:message key="knowledgepro.syllabus.display.syllabusyear"/></div> </td>
					
					<td height="25" width="19%" class="row-odd"><div align="center"><bean:message key="knowledgepro.student.syllabus.display.syllabus.link"/></div> </td>
				</tr>
				
				
				
				
                <logic:iterate name="syllabusDisplayForStudentForm" property="syllabusDisplayForStudentToList"
									id="id" indexId="count">
					<tr>
					<c:choose>
						<c:when test="${count%2 == 0}">
							<tr class="row-even">
						</c:when>
						<c:otherwise>
							<tr class="row-odd">
						</c:otherwise>
					</c:choose>
					
						<td width="7%" height="25">
						<div align="center"><c:out value="${count+1}" /></div>
						</td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="joiningYear" /></div></td>
						<td width="10%" height="20"><div align="center"><bean:write name="id" property="examFirstAttemptedYear" /></div></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="semNo" /></div></td>
						<td width="10%" height="20"><bean:write name="id" property="paperCode" /></td>
						<td width="22%" height="25"><bean:write name="id" property="paperName" /></td>
						<td width="8%" height="20"><div align="center"><bean:write name="id" property="numOfChancesLeft" /></div></td>
						<td width="10%" height="20"><div align="center"><bean:write name="id" property="admitedYear" /></div></td>
						<td>
						
						<logic:equal value="true" name="id" property="isChancesAvailable">
						<div align="center"><a href="#" onclick="getDownloadFile('<bean:write name="id" property="paperCode" />','<bean:write name="id" property="admitedYear" />')">
							<bean:message key="knowledgepro.student.syllabus.display.syllabus.link.message"/></a></div>
						</logic:equal>	
						<logic:equal value="false" name="id" property="isChancesAvailable">
						<bean:message key="knowledgepro.student.syllabus.display.nochances.message"/>
						</logic:equal>			
						</td>
						</tr>
				<c:set var="temp" value="1"/>
				</logic:iterate> 
                
                
                </table></td>
                <td width="5" height="30"  background="images/right.gif"></td>
              </tr>
              <tr>
                <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                <td background="images/05.gif"></td>
                <td><img src="images/06.gif" /></td>
              </tr>
            </table></td>
          </tr>
          </logic:notEmpty>
        </table></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
      
      
      
      
      <tr>
        <td height="26" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="0" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>