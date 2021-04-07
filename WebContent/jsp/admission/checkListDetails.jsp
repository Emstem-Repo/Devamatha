<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title>checkListDetails</title>
</head>
<table width="99%" border="0">
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.admission"/>
    <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.admission.checklistEntry"/> &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td height="237" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message key="knowledgepro.admission.checklist.checkListDetails"/>  </td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><div align="center"></div></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                <tr>
                  <td width="12%" height="25" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.programtype"/>:</div></td>
                  <td width="14%" class="row-even"><bean:write name="checkListForm" property="programTypeName"/></td>
                  <td width="10%" class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.program"/>:</div></td>
                  <td width="14%" class="row-even"><bean:write name="checkListForm" property="programName"/></td>
                  </tr>
                <tr>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.course"/>:</div></td>
                  <td class="row-even"><bean:write name="checkListForm" property="courseName"/></td>
                  <td class="row-odd"><div align="right"><bean:message key="knowledgepro.admission.year"/>:</div></td>
                  <td class="row-even"><bean:write name="checkListForm" property="year"/></td>
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
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="130" valign="top" background="images/Tright_03_03.gif"></td>
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
                    <td width="16%" height="25" class="row-odd" ><div align="center"><bean:message key="knowledgepro.admission.document"/></div></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce"/></td>
                    <td height="25" class="row-odd" align="center"><bean:message key="knowledgepro.admission.needToProduce.semwise.mc"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admission.markscard"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admission.consolidatedMarks"/></td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admission.semsterwise"/></td>
                    <td width="15%" class="row-odd" align="center">Include Language</td>
                    <td class="row-odd" align="center"><bean:message key="knowledgepro.admission.previousexam"/></td>
					<td class="row-odd" align="center">Exam Required</td>
                  </tr>
          <logic:notEmpty name="checkListForm" property="doclist">
         	 <logic:iterate id="Checklist" name="checkListForm" property="doclist" indexId="count">
			<%
				String dynamicStyle = "";
				if (count % 2 != 0) {
				dynamicStyle = "row-white";
				} else {
				dynamicStyle = "row-even";
				}
			%>	
			<tr>
			    
				<td class="<%=dynamicStyle%>" align="center">
				<bean:write name="Checklist" property="name"/>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.needToProduce == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.needToProduceSemwiseMc == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.isMarksCard == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.isConsolidatedMarks == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.semesterWise == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.isIncludeLanguage == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.previousExam == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				
				</td>
				<td class="<%=dynamicStyle%>" align="center">
				<c:choose>
					<c:when test="${Checklist.isExamRequired == true}">
						Yes
					</c:when>
					<c:otherwise>
						No
					</c:otherwise>
				</c:choose>
				
				</td>
	  		</tr>	
			</logic:iterate>
        </logic:notEmpty>
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
        <td height="35" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
              
			<td colspan="3" align="center"><html:reset styleClass="formbutton" onclick="javascript:self.close();">
			<bean:message key="knowledgepro.close"/></html:reset>
			</td>
            </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
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