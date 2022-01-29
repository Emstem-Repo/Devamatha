<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function saveCertificateDetails(){
	document.getElementById("method").value = "saveCertificateDetails";
	document.certificateDetailsForm.submit();
	
}
function cancelCertificateDetails(){
	document.location.href = "CertificateDetails.do?method=initCertificateDetails";
	}
function validateCheckBox() {
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Roles Assigned :"+checkBoxselectedCount;
        document.getElementById("checkedid").value=checkBoxselectedCount;
            
}
</script>

<html:form action="/CertificateDetails" method="post">

<html:hidden property="formName" value="certificateDetailsForm" />
<html:hidden property="method" styleId="method" value="saveCertificateDetails"/>
<html:hidden property="countt" styleId="checkedid"/>
<html:hidden property="pageType" value="1"/>

<table width="99%" border="0">
  
  <tr>
    <td><span class="heading"><bean:message key="knowledgepro.certificateDetails"/></span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><bean:message key="knowledgepro.certificateDetails"/></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="5" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="15" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div id="err"></div>
			<div id="errorMessage"><FONT color="red"><html:errors /></FONT>
				<FONT color="green"><html:messages id="msg" property="messages" message="true">
					<c:out value="${msg}" escapeXml="false"></c:out><br>
					</html:messages> </FONT></div></td>
       <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
         </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td class="heading"></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="5" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
                  
                  <table width="100%" cellspacing="1" cellpadding="2">
                        <tr >
                        <td width="20%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.select"/></td>
                        <td width="80%" class="row-odd" align="center"><bean:message key="knowledgepro.assign.to.roles"/></td>
                        </tr>
                      <nested:iterate id="name" property="assignToRolesList" name="certificateDetailsForm" indexId="count">
                      
                      <c:if test="${count < certificateDetailsForm.halfLength}">
					   <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					 <td height="25" align="center" >
                        
                        <input
							type="hidden"
							name="assignToRolesList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='name' property='tempChecked'/>" />
                       
                        <input
							type="checkbox"
							name="assignToRolesList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
                       
                       
						<script type="text/javascript">
							var id = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(id == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                       <td width="212" align="center"><nested:write name="name" property="name"/></td>
                        
                      </tr>
                      </c:if>
                      </nested:iterate>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
            <td><table width="99%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5"></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5"></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td height="54" valign="top">
              
                  <table width="100%" cellspacing="1" cellpadding="2">
                   <tr >
                        <td width="20%" height="25" class="row-odd" align="center"><bean:message key="knowledgepro.select"/></td>
                         <td width="80%" class="row-odd" align="center"><bean:message key="knowledgepro.assign.to.roles"/></td>
                      </tr>
                      <c:set var="c" value="0"/>
                      <nested:iterate id="name" property="assignToRolesList" name="certificateDetailsForm" indexId="count">
                      <c:set var="c" value="${c + 1}"/>
                      <c:if test="${count >= certificateDetailsForm.halfLength}">
                      <c:choose>
								<c:when test="${count%2 == 0}">
									<tr class="row-even">
								</c:when>
									<c:otherwise>
									<tr class="row-white">
								</c:otherwise>
					 </c:choose>
					  <td height="25" align="center">
                        
                        <input
							type="hidden"
							name="assignToRolesList[<c:out value='${count}'/>].tempChecked"
							id="hidden_<c:out value='${count}'/>"
							value="<nested:write name='name' property='tempChecked'/>" />
                       <input
							type="checkbox"
							name="assignToRolesList[<c:out value='${count}'/>].checked"
							id="<c:out value='${count}'/>" onclick="validateCheckBox()"/>
                        
                        
						<script type="text/javascript">
							var id1 = document.getElementById("hidden_<c:out value='${count}'/>").value;
							if(id1 == "true") {
								document.getElementById("<c:out value='${count}'/>").checked = true;
							}		
						</script>
					   </td>
                      <td width="212" align="center"><nested:write name="name" property="name"/></td>
                        
                      </tr>
                      </c:if>
                      </nested:iterate>
                      <c:if test="${(c % 2) != 0}" >
                      <tr  class="row-white">
                        <td width="193"  >&nbsp;</td>
                        <td width="212" >&nbsp;</td>
                        <td height="25" align="center" >&nbsp;
                      </td>
                      </tr>
                      </c:if>
                  </table>
                  
                  </td>
                  <td  background="images/right.gif" width="5" height="54"></td>
                </tr>
                <tr>
                  <td height="5"><img src="images/04.gif" width="5" height="5"></td>
                  <td background="images/05.gif"></td>
                  <td><img src="images/06.gif" ></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
            <tr>
            <td height="35" align="center">
            <html:button property="" styleClass="formbutton" value="Submit" onclick="saveCertificateDetails()"></html:button>
            &nbsp;&nbsp;&nbsp;
           	<html:button property="" styleClass="formbutton" value="Reset" onclick="resetFieldAndErrMsgs()"></html:button>
           	&nbsp;&nbsp;&nbsp;
           <html:button property="" styleClass="formbutton" value="Cancel" onclick="cancelCertificateDetails()"></html:button>
          </td>
			
          </tr>
        </table>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
<script language="JavaScript" >
	var inputs = document.getElementsByTagName("input");
    var inputObj;
    var checkBoxselectedCount = 0;
    for(var count1 = 0;count1<inputs.length;count1++) {
	    inputObj = inputs[count1];
	    var type = inputObj.getAttribute("type");
	   	if (type == 'checkbox') {
	   		if(inputObj.checked){
	   			checkBoxselectedCount++;
		   	}
		}
    }
        document.getElementById("err").innerHTML = "Roles Assigned :"+checkBoxselectedCount;
        document.getElementById("checkedid").value=checkBoxselectedCount;
</script>