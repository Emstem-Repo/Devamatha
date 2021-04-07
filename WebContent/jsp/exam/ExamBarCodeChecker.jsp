<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>



<SCRIPT>
function resetErrMsgs()
{
	
	document.location.href="ExamBarCodeChecker.do?method=initExamBarCodeChecker";
}


</SCRIPT>



<html:form action="/ExamBarCodeChecker.do" >

	
	<html:hidden property="formName" value="ExamBarCodeCheckerForm"
		styleId="formName" />
		<html:hidden property="method" styleId="method" value="onCheck"/>
		
	<html:hidden property="pageType" value="1" styleId="pageType" />

	<table width="100%" border="0">
  <tr>
    <td><span class="heading"><span class="Bredcrumbs">Exams</a> <span class="Bredcrumbs">&gt;&gt;Bar Code Checker  &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> Bar Code checker</strong></div></td>
        <td width="10" ><img src="images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr bgcolor="#FFFFFF">
              <td width="100%" height="20">&nbsp;</td>
            </tr>
            <tr bgcolor="#FFFFFF">
              <td height="20"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="images/01.gif" width="5" height="5" /></td>
                  <td width="914" background="images/02.gif"></td>
                  <td><img src="images/03.gif" width="5" height="5" /></td>
                </tr>
                <tr>
                  <td width="5"  background="images/left.gif"></td>
                  <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    <tr >
                      <td width="10%" height="25" class="row-odd" ><div align="right">Register No:</div></td>
                      <td width="10%" height="25" class="row-even" ><html:password property="registrNo" styleId="registrNo1" size="15"/><STRONG><bean:write name="ExamBarCodeCheckerForm" property="registrNo"/></STRONG></td>
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
            </tr>
            <tr>
              <td height="25" class=" heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr><td style="height: 10px" align="center" colspan="3"></td></tr>
                <tr>
                  <td width="45%" height="35"><div align="right">
                      <input name="button" type="submit" class="formbutton" value="Check" />
                  </div></td>
                  <td width="2%"></td>
                  <td width="53%"><input type="button" class="formbutton" value="Reset" onclick="resetErrMsgs()" /></td>
                </tr>                
                <tr><td style="height: 10px" align="center" colspan="3"></td></tr>                
              </table></td>
            </tr>
          </table>
            <div align="center">
              <table width="100%" height="10  border="0" cellpadding="0" cellspacing="0">
                
                <tr>

                  <td></td>
                </tr>
              </table>
            </div></td>
        <td width="10" valign="top" background="images/Tright_3_3.gif" class="news"></td>
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
