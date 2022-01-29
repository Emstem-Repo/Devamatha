<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<script type="text/javascript">
function backToInput(){
	document.location.href = "HostelerDatabase.do?method=initHostelerDatabase";
}
// author Hari Kumar .L
function getDetails(applicationNumber) {
	var url  = "HostelerDatabase.do?method=gettHostelerDatabase&applicationNumber="+applicationNumber;
	myRef = window.open(url,"ViewStudentDetails","left=20,top=20,width=800,height=600,toolbar=1,resizable=0,scrollbars=1");
}
</script>
<html:form action="/HostelerDatabase" method="post">
<html:hidden property="method" styleId="method" />
<html:hidden property="formName" value="hostelerDatabaseForm" />
<table width="99%" border="0"> 
  <tr>
    <td><span class="heading"><bean:message
				key="knowledgepro.hostel" /><span class="Bredcrumbs">&gt;&gt;<bean:message
				key="knowledgepro.hostel.hosteler.database" /> &gt;&gt;</span> </span></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" > <bean:message
				key="knowledgepro.hostel.hosteler.database" /></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td height="99" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
          <tr>
            <td width="400%" height="45" colspan="4" ><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5" /></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5" /></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td width="100%" valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    <tr >
                      <td height="25" class="row-odd" ><div align="center"><bean:message key="admissionForm.detailmark.slno.label"/> </div></td>
                      <td class="row-odd" ><bean:message key="knowledgepro.hostel.hostel.entry.name"/> </td>
                      <td height="25" class="row-odd" ><bean:message key="knowledgepro.hostel.hostelerdatabase.regno/staffId"/> </td>
                      <td class="row-odd" ><bean:message key="knowledgepro.hostel.hostelerdatabase.name"/> </td>
                      <td height="25" class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.hostelerdatabase.roomno"/> </div></td>
                      <td class="row-odd"><div align="left"><bean:message key="knowledgepro.hoste.roomtype.col"/> </div></td>
                       <td class="row-odd"><div align="left"><bean:message key="knowledgepro.hostel.floorno"/> </div></td>
                    </tr>
                    <c:set var="temp" value="0" />
                    <logic:notEmpty name="hostelerDatabaseForm"	property="studentList">
                    <nested:iterate id="stud" name="hostelerDatabaseForm" property="studentList"
					indexId="count">
					<c:choose>
					<c:when test="${temp == 0}">
                    <tr >
                      <td  height="25" class="row-even"><div align="center"><c:out value="${count + 1}" /></div></td>
                      <td class="row-even" ><nested:write name="stud" property="hostelName"/> </td>
                      <td height="25" class="row-even" ><nested:write name="stud" property="regNo"/></td>
                      <td class="row-even" ><a href="#" onclick="getDetails('<nested:write name="stud" property="hlformId"/>')"><nested:write name="stud" property="studentName"/></a></td>
                      <td height="25" class="row-even" ><nested:write name="stud" property="roomNo"/></td>
                      <td class="row-even" ><nested:write name="stud" property="roomTypeName"/></td>
                      <td class="row-even"><nested:write name="stud" property="floorNo"/></td>
                      
                    </tr>
                    <c:set var="temp" value="1" />
				</c:when>
				<c:otherwise>
	               <tr >
                      <td height="25" class="row-white"><div align="center"><c:out value="${count + 1}" /></div></td>
                      <td class="row-white" ><nested:write name="stud" property="hostelName"/></td>
                      <td height="25" class="row-white" ><nested:write name="stud" property="regNo"/></td>
                      <td class="row-white" ><a href="#" onclick="getDetails('<nested:write name="stud" property="hlformId"/>')"><nested:write name="stud" property="studentName"/></a><a href="#"></a></td>
                      <td height="25" class="row-white" ><p><nested:write name="stud" property="roomNo"/></p></td>
                      <td class="row-white" ><nested:write name="stud" property="roomTypeName"/></td>
                      <td class="row-white"><nested:write name="stud" property="floorNo"/></td>
                    </tr>
                    <c:set var="temp" value="0" />
					</c:otherwise>
					</c:choose>
					</nested:iterate>
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
        <td valign="top" class="news"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="48%" height="35">&nbsp;</td>
            <td width="52%">
            <html:button property="" styleClass="formbutton" value="Back"
			onclick="backToInput()"></html:button>
			</td>
          </tr>
        </table></td>
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