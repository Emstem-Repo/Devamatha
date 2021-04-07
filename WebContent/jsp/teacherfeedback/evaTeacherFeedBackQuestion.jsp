<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>

<head>
<script type="text/javascript">

function addFeedBackQuestion() {
	document.getElementById("method").value="addFeedBackQuestion";
}

function editFeedBackQuestion(id) {
	document.location.href = "TeacherFeedBackQuestion.do?method=editFeedBackQuestion&id="+id;
	}

function updateFeedBackQuestion() {
	document.getElementById("method").value = "updateFeedBackQuestion"; 
	
}

function reActivate() {
	document.location.href="TeacherFeedBackQuestion.do?method=reActivateFeedBackQuestion";
}

function deleteFeedBackQuestion(id) {
	deleteConfirm =confirm("Are you sure to delete this entry?");
	if(deleteConfirm)
	document.location.href = "TeacherFeedBackQuestion.do?method=deleteFeedBackQuestion&id="+id;
	}

</script>
</head>
<html:form action="/TeacherFeedBackQuestion" method="POST">
<html:hidden property="id" styleId="CMID"/>
<c:choose>
	<c:when test="${FeedBackQuestion == 'edit'}">
		<html:hidden property="method" styleId="method" value="editFeedBackQuestion" />
	</c:when>
	<c:otherwise>
		<html:hidden property="method" styleId="method" value="addFeedBackQuestion" />
	</c:otherwise>
</c:choose>
<html:hidden property="formName" value="teacherFeedBackQuestionForm" />
<html:hidden property="pageType" value="1" />
<table width="99%" border="0">
  
  <tr>
    <td><span class="Bredcrumbs"><bean:message key="knowledgepro.teacherFeedBack"/> <span class="Bredcrumbs">&gt;&gt; <bean:message key="knowledgepro.studentFeedBack.question"/> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.teacherFeedBack.question"/> </td>
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
                 <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.studentFeedBack.groupname" /></div></td>
				 <td width="25%" height="25" class="row-even"><span	class="star"> 
				 <html:select property="groupId" styleClass="comboLarge" styleId="groupId"><html:option value=""><bean:message key="knowledgepro.admin.select" />
				 </html:option><html:optionsCollection name="feedBackGroupList" label="name" value="id" /></html:select></span></td>
                <td width="25%" height="25" class="row-odd"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.studentFeedBack.displayorder"/>:</div></td>
                <td width="25%" height="25" class="row-even"><span class="star">
                    <html:text property="order" styleId="order" size="20" maxlength="9" onkeypress="return isNumberKey(event)"/>
                </span></div></td>
               </tr>
              
              <tr>
              <td width="25%" height="25" class="row-odd" colspan="1"><div align="right"><span class="Mandatory">*</span><bean:message key="knowledgepro.studentFeedBack.question"/>:</div></td>
                <td width="75%" height="25" class="row-even" colspan="3" ><label></label><span class="star">
                    <html:text property="question" styleId="question" size="100" maxlength="198"/>
                  </span></td>
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
            <td width="100%" height="20" colspan="4"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="45%" height="35"><div align="right">
                   <c:choose>
            		<c:when test="${FeedBackQuestion == 'edit'}">
              	   		<html:submit property="" styleClass="formbutton" onclick="updateFeedBackQuestion()"><bean:message key="knowledgepro.update"/></html:submit>
              		</c:when>
              		<c:otherwise>
                		<html:submit property="" styleClass="formbutton" onclick="addFeedBackQuestion()"><bean:message key="knowledgepro.submit"/></html:submit>
              		</c:otherwise>
              	</c:choose>
                </div></td>
                <td width="2%"></td>
                <td width="53%">
                 <c:choose>
					<c:when test="${FeedBackQuestion == 'edit'}">
						<html:cancel styleClass="formbutton"><bean:message key="knowledgepro.admin.reset" /></html:cancel>
					</c:when>
					<c:otherwise>
						<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()">
										<bean:message key="knowledgepro.cancel"/></html:button>
					</c:otherwise>
				</c:choose>
				</td>
              </tr>
            </table></td>
          </tr>
          
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
                  <tr >
                    <td height="5%" class="row-odd" ><div align="center"><bean:message key="knowledgepro.slno"/></div></td>
                    <td height="15%" class="row-odd" align="center" ><bean:message key="knowledgepro.studentFeedBack.groupname"/></td>
                    <td height="60%" class="row-odd" align="center"><bean:message key="knowledgepro.studentFeedBack.question"/></td>
                    <td height="10%" class="row-odd" align="center"><bean:message key="knowledgepro.studentFeedBack.displayorder"/></td>
                    <td height="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.edit"/></div></td>
                    <td height="5%" class="row-odd"><div align="center"><bean:message key="knowledgepro.delete"/></div></td>
                  </tr>
                <c:set var="temp" value="0"/>
                <logic:notEmpty name="teacherFeedBackQuestionForm" property="feedBackQuestionList">
                <logic:iterate id="CME" name="teacherFeedBackQuestionForm" property="feedBackQuestionList" indexId="count">
                <c:choose>
                   <c:when test="${temp == 0}">
                   	<tr>
                   		<td width="5%" height="25" class="row-even" ><div align="center"><c:out value="${count + 1}"/></div></td>
                   		<td width="15%" height="25" class="row-even" align="center"><bean:write name="CME" property="groupId"/></td>
                   		<td width="60%" height="25" class="row-even" align="left"><bean:write name="CME" property="question"/></td>
                   		<td width="10%" class="row-even" align="center"><bean:write name="CME" property="order"/></td>
			            <td width="5%" height="25" class="row-even" ><div align="center">
			        		<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editFeedBackQuestion('<bean:write name="CME" property="id"/>')"></div></td>
                   		<td width="5%" height="25" class="row-even" ><div align="center">
                   			<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFeedBackQuestion('<bean:write name="CME" property="id"/>')"></div></td>
					</tr>
                    <c:set var="temp" value="1"/>
                   	</c:when>
                    <c:otherwise>
		            <tr>
               			<td width="5%" height="25" class="row-white"><div align="center"><c:out value="${count + 1}"/></div></td>
               			<td width="15%" height="25" class="row-white" align="center"><bean:write name="CME" property="groupId"/></td>
               			<td width="60%" height="25" class="row-white" align="left"><bean:write name="CME" property="question"/></td>
               			<td width="10%" class="row-white" align="center"><bean:write name="CME" property="order"/></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/edit_icon.gif" width="20" height="16" style="cursor:pointer" onclick="editFeedBackQuestion('<bean:write name="CME" property="id"/>')"></div></td>
               			<td width="5%" height="25" class="row-white" ><div align="center">
               				<img src="images/delete_icon.gif" width="16" height="16" style="cursor:pointer" onclick="deleteFeedBackQuestion('<bean:write name="CME" property="id"/>')"></div></td>
               		</tr>
                    <c:set var="temp" value="0"/>
				  	</c:otherwise>
                  </c:choose>
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
          </tr>
          
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