<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean"%>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c"%>
<%@page import="java.util.Map,java.util.HashMap"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>

<head>
<title>:: CMS ::</title>
<script type="text/javascript" language="javascript">


</script>
</head>

<html:form action="/ExamOptionalSubjectAssignmentToStudent.do" method="POST"
	enctype="multipart/form-data">
	<html:hidden property="formName" value="ExamOptionalSubjectAssignmentToStudentForm"
		styleId="formName" />
	<html:hidden property="pageType" value="1" styleId="pageType" />

	
			<html:hidden property="method" styleId="method"
				value="continue" />
		
<table width="100%" border="0">
  <tr>
    <td><span class="heading"><a href="Admission_Intro.html" class="Bredcrumbs">Exams</a> <span class="Bredcrumbs">&gt;&gt; Optional Subject Assignment To Student &gt;&gt;</span></span></td>
  </tr>
  <tr>
    <td><table width="98%" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td width="9"><img src="../images/Tright_03_01.gif" width="9" height="29"></td>
        <td background="../images/Tcenter.gif" class="body" ><div align="left"><strong class="boxheader"> Optional Subject Assignment To Student</strong></div></td>
        <td width="10" ><img src="../images/Tright_1_01.gif" width="9" height="29"></td>
      </tr>
      <tr>
        <td height="122" valign="top" background="../images/Tright_03_03.gif"></td>
        <td valign="top" class="news"><table width="100%" cellspacing="1" cellpadding="2">
            <tr bgcolor="#FFFFFF">
              <td width="100%" height="20">&nbsp;</td>
            </tr>
            <tr bgcolor="#FFFFFF">
              <td height="20"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="../images/01.gif" width="5" height="5" /></td>
                  <td width="914" background="../images/02.gif"></td>
                  <td><img src="../images/03.gif" width="5" height="5" /></td>
                </tr>
                <tr>
                  <td width="5"  background="../images/left.gif"></td>
                  <td valign="top"><table width="100%" cellspacing="1" cellpadding="2">
                    <tr >
                       
                   <td width="22%" class="row-odd"><div align="right">Academic Year:</div></td>
             <td width="34%" class="row-even" colspan="3">acadamicYear</td>
                      <td width="27%" height="25" class="row-odd" ><div align="right">Class:</div></td>
                      <td width="20%" height="25" class="row-even" >classes</td>
                     
                    </tr>
                  
                  
                 
                  </table></td>
                  <td width="5" height="30"  background="../images/right.gif"></td>
                </tr>
                <tr>
                  <td height="5"><img src="../images/04.gif" width="5" height="5" /></td>
                  <td background="../images/05.gif"></td>
                  <td><img src="../images/06.gif" /></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="25" class=" heading"></td>
            </tr>
            <tr>
              <td height="25" class=" heading"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                <tr>
                  <td ><img src="../images/01.gif" width="5" height="5" /></td>
                  <td width="914" background="../images/02.gif"></td>
                  <td><img src="../images/03.gif" width="5" height="5" /></td>
                </tr>
                <tr>
                  <td width="5"  background="../images/left.gif"></td>
                  <td valign="top"><table width="100%" height="90" border="0" cellpadding="0" cellspacing="1">
                    <tr class="row-odd">
                      <td width="61" height="25" class="bodytext"><div align="center"><strong>Select</strong>
                        <input type="checkbox" name="checkbox4" id="checkbox4">
                      </div></td>
                      <td width="201" height="25" class="bodytext">Roll No.</td>
                      <td width="165" class="bodytext">Register No.</td>
                      <td width="132" height="25" class="bodytext">Student Name</td>
                      <td width="124" class="bodytext">Specialisation</td>
                      <td width="124" height="25" class="bodytext"><div align="center">Optional subject Group</div></td>
                      </tr>
                    <tr class="row-even">
                      <td height="20" class="bodytext"><div align="center">
                        <input type="checkbox" name="checkbox" id="checkbox">
                      </div></td>
                      <td height="20" class="bodytext"></a>R343</td>
                      <td class="bodytext">AR3254435</td>
                      <td height="20" class="bodytext">George</td>
                      <td align="left" class="bodytext">MBA in HR</td>
                      <td height="20" align="left" class="bodytext"><div align="center">Finance group</div></td>
                      </tr>
                    <tr class="row-white">
                      <td height="20" class="bodytext"><div align="center">
                        <input type="checkbox" name="checkbox2" id="checkbox2">
                      </div></td>
                      <td height="20" class="bodytext">R343</td>
                      <td class="bodytext">BC2343434</td>
                      <td height="20" class="bodytext">Sham Sharma</td>
                      <td align="left" class="bodytext">MBA in HR</td>
                      <td height="20" align="left" class="bodytext"><div align="center">Buisness group</div></td>
                      </tr>
                    <tr class="row-white">
                      <td height="20" class="row-even"><div align="center">
                        <input type="checkbox" name="checkbox3" id="checkbox3">
                      </div></td>
                      <td height="20" class="row-even">R234</td>
                      <td class="row-even">AG2343443</td>
                      <td height="20" class="row-even">Raj Arora</td>
                      <td align="left" class="row-even">MBA in HR</td>
                      <td height="20" align="left" class="row-even"><div align="center">Adminstration Group</div></td>
                      </tr>
                  </table></td>
                  <td width="5" height="30"  background="../images/right.gif"></td>
                </tr>
                <tr>
                  <td height="5"><img src="../images/04.gif" width="5" height="5" /></td>
                  <td background="../images/05.gif"></td>
                  <td><img src="../images/06.gif" /></td>
                </tr>
              </table></td>
            </tr>
            <tr>
              <td height="25" class=" heading"></td>
            </tr>
            
            <tr>
              <td height="25" class=" heading"><table width="100%"  border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="45%" height="35"><div align="right">
                      <input name="button" type="button" class="formbutton" value="Apply" />
                  </div></td>
                  <td width="2%"></td>
                  <td width="53%"><input type="button" class="formbutton" value="Cancel" /></td>
                </tr>
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
        <td width="10" valign="top" background="../images/Tright_3_3.gif" class="news"></td>
      </tr>
      <tr>
        <td><img src="../images/Tright_03_05.gif" width="9" height="29"></td>
        <td width="100%" background="../images/TcenterD.gif"></td>
        <td><img src="../images/Tright_02.gif" width="9" height="29"></td>
      </tr>
    </table></td>
  </tr>
</table>

</html:form>
