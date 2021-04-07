<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/admission/admissionform.js"></script>
<script language="JavaScript" src="js/admission/studentView.js"></script>

<script type="text/javascript">
function goToFirstPage(method) {
	document.location.href = "attendanceCondonation.do?method="+method;
}

function condonationRestrict(c){
	
     
	var sem = document.getElementById("sem").value;
	var stdnt = document.getElementById("stdnt"+c).value;
    var args ="method=condonationRestrict&baseStudentId="+stdnt; 
    alert(sem+" "+stdnt);
    var xmlReq = false;

       if(window.XMLHttpRequest){
    	   xmlReq = new XMLHttpRequest();

        } else if (window.ActiveXObject){
            try{
            	xmlReq = new ActiveXObject("Msxml2.XMLHTTP");
            }catch(e){
                try{
                xmlReq = new ActiveXObject("Microsoft.XMLHTTP")
                }catch(e){
                }
            }
        	
        }


       xmlReq.onreadystatechange = function () {
   		                                         if (xmlReq.readyState == 4) {
   			                                         if (xmlReq.status == 200) {
   			                                        	updateCondonationLimit(xmlReq);
   			                                          } else {
   				                                           alert("HTTP error: "+xmlReq.status);
   			                                          }
   		                                          }
   	                                            }
            
       xmlReq.open("POST", "AjaxRequest.do", true);
       xmlReq.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
       xmlReq.send(args);

	
	
}


function updateCondonationLimit(xmlReq){

	var responseObj = xmlReq.responseXML.documentElement;

	var items = responseObj.getElementsByTagName("list");

	if(items.length==1){
		//alert("single entry ");
	 var v = items[0].getElementsByTagName("sem")[0].firstChild.nodeValue;

     var v1 = items[0].getElementsByTagName("class")[0].firstChild.nodeValue;
	}else if(items.length>1){
		
		 alert("already over");
	}

    
     
    

}




function validate(evt) {
	  var theEvent = evt || window.event;
	  var key = theEvent.keyCode || theEvent.which;
	  key = String.fromCharCode( key );
	  var regex = /[0-9]|\./;
	  if( !regex.test(key) ) {
		  if (theEvent.keyCode!=8){
	    theEvent.returnValue = false;
	    if(theEvent.preventDefault) theEvent.preventDefault();
		  }
	  }
	}
</script>


<html:form action="/attendanceCondonation">
	<html:hidden property="method" value="saveAttendanceCondonation" />
	<html:hidden property="studentId" value="" />
	<html:hidden property="formName" value="attendanceCondonationForm" />
	<html:hidden property="scheme"  styleId="sem" />
	<html:hidden property="pageType" value="2" />
<table width="98%" border="0">
  <tr>
    <td><span class="Bredcrumbs">Attendance<span class="Bredcrumbs">&gt;&gt; Condonation&gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="10"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader">Condonation</strong></div></td>
        <td width="13" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
	
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><div align="center">
            <table width="100%" height="90"  border="0" cellpadding="0" cellspacing="0">
              <tr>
                <td height="35" valign="top" class="body" ><tr bgcolor="#FFFFFF">
		<td height="20" colspan="2">
		<div align="right"><FONT color="red"> </FONT></div>
		<div id="errorMessage" align="left"><FONT color="red"><html:errors /></FONT>
		<FONT color="green"> <html:messages id="msg"
			property="messages" message="true">
			<c:out value="${msg}" escapeXml="false"></c:out>
			<br>
		</html:messages> </FONT></div>
		</td>
	</tr></td>
              </tr>
              <tr>
                <td height="35" valign="top" class="body" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                    <tr>
                      <td ><img src="images/01.gif" width="5" height="5" /></td>
                      <td width="914" background="images/02.gif"></td>
                      <td><img src="images/03.gif" width="5" height="5" /></td>
                    </tr>
                    <tr>
                      <td width="5"  background="images/left.gif"></td>
                      <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                          <tr >
                            <td height="25" class="row-odd"><div align="center"><bean:message key="knowledgepro.slno" /></div></td>
                            <td class="row-odd"><bean:message key="knowledgepro.admin.name"/></td>
                             <td class="row-odd">Class No</td>
                            <td class="row-odd"><bean:message key="knowledgepro.attendance.regno"/></td>
                            <td class="row-odd">Current Percentage</td>
                            <td class="row-odd"><bean:message key="knowledgepro.att.condon.condonationpercentage"/></td>
                            
   
                          </tr>
                          <c:set var="temp" value="0" />
							<nested:iterate id="list" name="attendanceCondonationForm" property="studentList" indexId="count">
							<%
							int c = count;
							String stdnt = "stdnt"+count;
							String sem = "sem"+count;
							%>
							<nested:hidden property="studentId"  styleId='<%= stdnt %>'/>
								<c:choose>
								<c:when test="${temp == 0}">
                          <tr>
                            <td height="39" class="row-even" style="background-color:#CCE0FF"><div align="center">
                            <c:out value="${count+1}" />
                            </div></td>
                            <td  class="row-even" style="background-color:#CCE0FF">&nbsp;<bean:write name="list" property="studentName"/></td>
                            <td  class="row-even" style="background-color:#CCE0FF">&nbsp;<bean:write name="list"  property="rollNo"/></td>
                            <td  class="row-even" style="background-color:#CCE0FF">&nbsp;<bean:write name="list" property="registerNo"/></td>
                            <td  class="row-even" style="background-color:#CCE0FF">&nbsp;<bean:write name="list" property="previousPercentage"/></td>
                            <logic:equal value="false" name="list" property="isOver">
                            <td  class="row-even" style="background-color:#CCE0FF">&nbsp;<nested:text size="5" property="addedPercentage" onkeypress='validate(event)'></nested:text></td>
                            </logic:equal>
                            <logic:equal value="true" name="list" property="isOver">
                             <td  class="row-even" style="background-color:#CCE0FF"> &nbsp;<FONT color="red"><bean:write name="list" property="displayMsg"/></FONT></td>
                            </logic:equal>
                          </tr>
                          <c:set var="temp" value="1" />
							</c:when>
							<c:otherwise>
                          <tr>
                            <td width="5%" height="39" class="row-even" style="background-color:#B2D1FF"><div align="center">
                            <c:out value="${count+1}" />
                            </div></td>
                            <td  class="row-even" style="background-color:#B2D1FF" >&nbsp;<bean:write name="list" property="studentName"/></td>
                            <td  class="row-even" style="background-color:#B2D1FF" >&nbsp;<bean:write name="list" property="rollNo"/></td>
                            <td  class="row-even" style="background-color:#B2D1FF">&nbsp;<bean:write name="list" property="registerNo"/></td>
                            <td  class="row-even" style="background-color:#B2D1FF">&nbsp;<bean:write name="list" property="previousPercentage"/></td>
                             <logic:equal value="false" name="list" property="isOver">
                            <td  class="row-even" style="background-color:#B2D1FF">&nbsp;<nested:text size="5" property="addedPercentage" onkeypress='validate(event)'></nested:text></td>
                            </logic:equal>
                            <logic:equal value="true" name="list" property="isOver">
                             <td  class="row-even" style="background-color:#B2D1FF"> &nbsp;<FONT color="red"><bean:write name="list" property="displayMsg"/></FONT></td>
                            </logic:equal>
                          </tr>    
                          <c:set var="temp" value="0" />
						</c:otherwise>
						</c:choose>
						</nested:iterate>
                      </table></td>
                      <td width="5" height="30"  background="images/right.gif"></td>
                    </tr>
                    
                    <tr>
					<td height="19" valign="top" background="images/Tright_03_03.gif"></td>

					<td class="heading">
					<table width="100%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="45%" height="35">
							<div align="right"><input name="button2" type="submit"
								class="formbutton" value="Submit" /></div>
							</td>
							<td width="2%"></td>
							<td width="6%">
							<input type="button" class="formbutton" value="Cancel" onclick="goToFirstPage('initAttendanceCondonation')" />
								</td>
							<td width="2%"></td>
							<td width="46%">&nbsp;</td>

						</tr>
					</table>
					</td>
					<td valign="top" background="images/Tright_3_3.gif"></td>
				</tr>
                    
                    
                    <tr>
                      <td height="5"><img src="images/04.gif" width="5" height="5" /></td>
                      <td background="images/05.gif"></td>
                      <td><img src="images/06.gif" /></td>
                    </tr>
                </table></td>
              </tr>
              <tr>
                <td height="10" class="body" ></td>
              </tr>
            </table>
        </div></td>
        <td width="13" valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="images/TcenterD.gif"></td>
        <td><img src="images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>
</html:form>