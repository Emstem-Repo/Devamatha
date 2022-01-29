<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<html>
<head>

</head>
<script type="text/javascript">
 function cancelAction(){
	 document.location.href = "newInterviewComments.do?method=initInterviewComments";
 }
 
 function edit(id){
	 document.location.href = "newInterviewComments.do?method=editInterviewCommentDetails&id="+id;
 }	 
 function view(id){
	 document.location.href = "newInterviewComments.do?method=viewInterviewComments&id="+id;
 }	 
</script>

<body>
	<html:form action="/newInterviewComments">
	<html:hidden property="formName" value="newInterviewCommentsForm"/>
	<html:hidden property="method" styleId="method" value="initInterviewComments"/>
	<html:hidden property="pageType" value="3"/>
<table width="99%" border="0">
  
  <tr>
    <td><span class="heading">
				Employee
			<span class="Bredcrumbs">&gt;&gt; Interview Comments&gt;&gt;</span></span></td>
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
                <td align="center"  class="row-odd" >Name</td>
                <td align="center" class="row-odd" >Email ID</td>
                 <td align="center" class="row-odd" >Interview Comments</td>
                <td align="center" class="row-odd" > View Interview Comments</td>
                </tr>
                <logic:notEmpty name="newInterviewCommentsForm" property="newInterviewCommentsList">
				<nested:iterate id="interviewComments" name="newInterviewCommentsForm" property="newInterviewCommentsList" indexId="count">
                <tr>
                
                	 <td align="center" class="row-even" ><c:out value="${count + 1}"/></td>
                	<td align="center"  class="row-even" ><nested:write property="name"/></td>
                	<td align="center" class="row-even" ><nested:write property="email"/></td>
                	<td align="center" class="row-even" ><div align="center">&nbsp;&nbsp;<img src="images/edit_icon.gif" border="0" onclick="edit('<nested:write property="id" name="interviewComments"/>')"/></div></td>
                	<td align="center" class="row-even" ><div align="center">&nbsp;&nbsp;<img src="images/view_o.gif" border="0" onclick="view('<nested:write property="id" name="interviewComments"/>')"/></div></td>
              
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
            <td width="49%" height="35" align="center"><input name=" " type="button" class="formbutton" value="Cancel" onclick="cancelAction()"/></td>
           
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
</table></html:form></body>
</html>