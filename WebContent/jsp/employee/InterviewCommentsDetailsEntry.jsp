<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<script language="JavaScript" src="js/calendar_us.js"></script>
<link rel="stylesheet" href="css/calendar.css">
<script language="JavaScript" src="calendar_us.js"></script>
<head>

</head>
<script type="text/javascript">
 function comments(){
	 var type=document.getElementById(id).value;
	 document.location.href = "InterviewComments.do?method=getComments&id="+ id+"&type="+type;
 }
 function details(id){
	 var type=document.getElementById(id).value; 
	 document.location.href = "InterviewComments.do?method=getInterviedetails&id="+id+"&type="+type;
 }
</script>
<html:form action="/InterviewComments"
	enctype="multipart/form-data">
	<html:hidden property="pageType" value="3" />
	<html:hidden property="method" value="saveInterviewComments" styleId="method" />
	<html:hidden property="formName" value="InterviewCommentsForm" />


<table width="100%" border="0">
  <tr>
    <td>Interview Comments</td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><strong class="boxheader">Interview Comments</strong></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="19" valign="top" background="images/Tright_03_03.gif"></td>
        <td height="20" class="news"><div align="right" class="mandatoryfield">*Mandatory fields</div></td>
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
            <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
              <tr >
                <td width="50%" height="25" class="row-odd"><div align="right" >Name of the Candidate:&nbsp; </div></td>
                <td width="50%" class="row-even"><bean:write property="name" name="InterviewCommentsForm"/></td>
              </tr>
              <tr >
                <td width="50%" height="25" class="row-odd"><div align="right" >Department Applied For: </div></td>
                <td width="50%" class="row-even"><bean:write property="departmentId" name="InterviewCommentsForm"/></td>
              </tr>
              <tr>
               <td width="50%" height="25" class="row-odd"><div align="right" >Date of Interview: </div></td>
               <td width="23%" height="25" class="row-even" ><table width="82" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td width="60"><html:text property="dateOfInterview" size="15"/></td>
                      <td width="40"><script language="JavaScript">
						new tcal ({
							// form name
							'formname': 'InterviewCommentsForm',
							// input name
							'controlname': 'dateOfInterview'
						});</script></td>
                    </tr>
                    
                  </table></td>
               </tr>
               
              
              
              <tr>
                <td width="23%"  class="row-odd" ><div align="right"> Comments:</div></td>
                <td class="row-even" colspan="5"><span class="row-white">
                  <html:textarea property="comments" cols="75" rows="8"/>
                </span></td>
              </tr>
              
              <tr>
                    <td width="50%" height="25" class="row-odd"><div align="right" >Evaluated By: </div></td>
                    <td class="row-even"><html:text property="evaluatedBy"/></td>
                    </tr>
                    <tr>
                    <td width="50%" height="25" class="row-odd"><div align="right" >Verification of Certificates: </div></td>
                    <td class="row-even">
                    <html:checkbox property="marksCards"/>
                  
                      Marks Cards 
                      <html:checkbox property="experienceCertificate"/>
                      Experience Certificate</td>
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
        <td class="heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="49%" height="35" align="right"><input  type="submit" class="formbutton" value="Add Comments" /></td>
            <td width="2%" height="35" align="center">&nbsp;</td>
            <td width="49%" height="35" align="left"><input  type="reset" class="formbutton" value="Reset" /></td>
          </tr>
        </table></td>
        <td valign="top" background="images/Tright_3_3.gif" ></td>
      </tr>
      
      <tr>
        <td height="10" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news">&nbsp;</td>
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