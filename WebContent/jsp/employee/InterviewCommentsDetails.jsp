<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>

<head>

</head>
<script type="text/javascript">
 function comments(id){
	 document.location.href = "InterviewComments.do?method=getComments&id="+ id;
 }
 function details(id){
	 document.location.href = "InterviewComments.do?method=getInterviedetails&id="+ id;
 }
 function cancelAction(){
	 document.location.href = "InterviewComments.do?method=initInterviewComments";
 }

 function updateStatus(){
		document.getElementById("method").value="updateStatus";
 }	 
</script>
<html:form action="/InterviewComments"
	enctype="multipart/form-data">
	<html:hidden property="pageType" value="2" />
	<html:hidden property="method" value="" styleId="method" />
	<html:hidden property="formName" value="InterviewCommentsForm" />


<table width="99%" border="0">
  
  <tr>
    <td>Interview Comments</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="heading_white" ><strong class="boxheader">Interview Comments</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
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
              <tr >
                <td align="center" class="row-odd" >Sl.No</td>
                <td align="center" height="25" class="row-odd" >Name</td>
                <td align="center" class="row-odd" >Email ID</td>
                <td align="center" class="row-odd" >Status</td>
                <td align="center" class="row-odd" >Comments</td>
                <td align="center" class="row-odd" >Details</td>
                </tr>
              <logic:notEmpty property="listOfDetails" name="InterviewCommentsForm" > 
              <nested:iterate property="listOfDetails"  indexId="count">
				<c:choose>
					<c:when test="${count%2 == 0}">
						<tr class="row-even">
					</c:when>
					<c:otherwise>
						<tr class="row-white">
					</c:otherwise>
				</c:choose>
					<td class="bodytext"><div align="center"><c:out value="${count + 1}" /></div></td>
					<td align="center" class="bodytext"><nested:write  property="name" /></td>
					<td align="center" class="bodytext"><nested:write  property="email" /></td>
					<%
						String value1 = "aa_"+ count;
						%>
					<td align="center" class="bodytext">
						<nested:select styleId='<%=value1 %>' property="status" >
	                    <html:option value="">Select</html:option>
	                    <html:option value="Selected">Selected</html:option>
	                    <html:option value="Rejected">Rejected</html:option>
	                    <html:option value="On hold">On hold</html:option>
	                  </nested:select></td>
					<td align="center"><div align="center" style="cursor: pointer" onclick="comments(<nested:write property='id'/>)"> <u>Comments</u></div> </td>
					<td align="center"><div align="center" style="cursor: pointer" onclick="details(<nested:write property="id" />)"><u> Details </u></div> </td>
				
									
				 </tr>
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
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><input name="Submit" type="Submit" class="formbutton" value="Submit" onclick="updateStatus()" /></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><input name="Submit" type="reset" class="formbutton" value="Reset" /> 
            &nbsp;<input name="Submit" type="reset" class="formbutton" value="Cancel" onclick="cancelAction()" />
            </td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td class="heading">&nbsp;</td>
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