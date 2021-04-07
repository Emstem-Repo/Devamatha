<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>


<script type="text/javascript" language="javascript">
	function resetCall(){
		document.location.href = "ExamBlockUnblock.do?method=initBlockUnblock";
	}
	function selectAll(obj) {
	    var value = obj.checked;
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxselectedCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox') {
	                  inputObj.checked = value;
	                  inputObj.value="on";
	            }
	    }
	}

	function unCheckSelectAll() {
	    var inputs = document.getElementsByTagName("input");
	    var inputObj;
	    var checkBoxOthersSelectedCount = 0;
	    var checkBoxOthersCount = 0;
	    for(var count1 = 0;count1<inputs.length;count1++) {
	          inputObj = inputs[count1];
	          var type = inputObj.getAttribute("type");
	            if (type == 'checkbox' && inputObj.id != "checkAll") {
	                  checkBoxOthersCount++;
	                  if(inputObj.checked) {
	                        checkBoxOthersSelectedCount++;
	                        inputObj.value="on";
	                  }else{
	                	  inputObj.value="off";	
	                      }   
	            }
	    }
	    if(checkBoxOthersCount != checkBoxOthersSelectedCount) {
	      document.getElementById("checkAll").checked = false;
	    } else {
	      document.getElementById("checkAll").checked = true;
	    }
	}
</script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:form action="/ExamBlockUnblock.do" styleId="myform">
	<html:hidden property="formName" value="ExamBlockUnblockForm" />
	<html:hidden property="method" styleId="method"
		value="updateUnblockAndReason" />
	<html:hidden property="pageType" value="2" />
	<html:hidden property="classId_studentId" styleId="classId_studentId" />
	<html:hidden property="examId"/>
	<html:hidden property="typeId"/>
	<html:hidden property="listCandidatesSize" styleId="listCandidatesSize"/>
<table width="100%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Exam
			<span class="Bredcrumbs">&gt;&gt;  	Block/Unblock-Hall ticket/Marks Card &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Block/Unblock-Hall ticket/Marks Card</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div>
        <div id="errorMessage"><FONT color="red"><html:errors /></FONT>
							<FONT color="green"><html:messages id="msg"
								property="messages" message="true">
								<c:out value="${msg}" escapeXml="false"></c:out>
								<br>
							</html:messages></FONT></div>
        
        </td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
          <tr>
            <td ><img src="images/01.gif" width="5" height="5" /></td>
            <td width="914" background="images/02.gif"></td>
            <td><img src="images/03.gif" width="5" height="5" /></td>
          </tr>
          <tr>
            <td width="5"  background="images/left.gif"></td>
            <td valign="top">
            	<table width="100%" cellspacing="1" cellpadding="2">
								
					<tr>
						<td height="25" align="center" class="row-odd">
							<div align="center"><bean:message key="knowledgepro.slno" /></div>
						</td>
						
						<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'UnBlock'}">
							<td height="25" align="center" class="row-odd">
									<bean:message key="knowledgepro.exam.blockUnblock.select" /> 
									<input type='checkbox' name='selectcheckall'  id="checkAll" onClick="selectAll(this)" />
							</td>
						</c:if>
						<td class="row-odd" align="center"><bean:message
							key="knowledgepro.exam.blockUnblock.class" /></td>
						<td class="row-odd" align="center"><bean:message
							key="knowledgepro.exam.blockUnblock.regNo" /></td>
						
						<td class="row-odd" align="center"><bean:message
							key="knowledgepro.exam.blockUnblock.name" /></td>
						<td class="row-odd" align="center">Status</td>
						<td class="row-odd" align="center">Reason</td>
					</tr>
					<logic:notEmpty property="listCandidatesName" name="ExamBlockUnblockForm">
					<nested:iterate name="ExamBlockUnblockForm" property="listCandidatesName" id="listCandidatesName" indexId="count">
							<logic:notEmpty property="examName" name="listCandidatesName">
								<tr class="row-odd" height="30px">
									<td colspan="7" align="center"><b>Exam Name :&nbsp;&nbsp;<nested:write property="examName"/></b></td>
								</tr>
							</logic:notEmpty>
							
							<c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
								<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
							</c:choose>
							<td width="4%" height="25">
								<div align="center"><c:out value="${count + 1}" /></div>
							</td>
							<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'UnBlock'}">
								<td align="center">
									 <nested:checkbox property="isSelected" onclick="unCheckSelectAll()"/>	
								</td>
							</c:if>
							<td align="center">
								 <nested:write property="className"/>	
							</td>
							<td align="center">
								 <nested:write property="regNumber"/>
							</td>
							<td align="center">
								 <nested:write property="name"/>
							</td>
							<td align="center">
								<nested:write property="status"/>
							</td>
							<td align="center">
								<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'UnBlock'}">
									<nested:write property="reason"/>
								</c:if>
								<c:if	test="${examBlockUnBlockOperation != null && examBlockUnBlockOperation == 'Edit'}">
									<nested:text property="reason"/>
								</c:if>
							</td>
							
					</nested:iterate>
				</logic:notEmpty>

				</table>
            </td>
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
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr><td style="height: 10px" align="center" colspan="3"></td></tr>
						<tr>
							<td width="45%" height="35">
							<div align="right">
							<input name="BlockUnBlock" type="submit" class="formbutton"
										value="Submit" />
							</div>
							</td>
							<td width="2%"></td>
							<td width="53%">
							<div align="left">
									<html:button property="" styleClass="formbutton" value="Cancel"
										onclick="resetCall()"></html:button>
							</div>
							</td>
						</tr>
			</table>
		</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      
	
		<tr>
			<td height="34" valign="top" background="images/Tright_03_03.gif"></td>
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