<%@taglib uri="/WEB-INF/struts-tld/struts-html.tld" prefix="html"%>
<%@taglib uri="/WEB-INF/struts-tld/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-tld/struts-nested.tld" prefix="nested"%>
<%@taglib uri="/WEB-INF/cmsTags.tld" prefix="cms" %>
<%@taglib uri="/WEB-INF/struts-tld/c.tld" prefix="c" %>
<%@ page import="java.awt.*" %>
<%@ page import="java.awt.image.BufferedImage" %>
<%@ page import="java.awt.print.*" %>
<%@ page import="com.kp.cms.forms.exam.ExamSecuredMarksVerificationForm" %>
<%@page import="com.kp.cms.utilities.print.SamplePrinting"%>
<%@page import="javax.imageio.ImageIO"%>
<%@page import="java.io.File"%>
<%@page import="java.awt.image.RenderedImage"%><head>
<link href="css/styles.css" rel="stylesheet" type="text/css">
<title><bean:message key="knowledgepro.admission.printingRegisterNumber"/></title>

<script>
  <!--
  function printpage() {
	window.print();
	window.close();
  }
  //-->
</script>

</head>
<body onload="printpage();">
<html:form action="/transferCertificate">

<html:hidden property="formName" value="ExamSecuredMarksVerificationForm" />
<html:hidden property="pageType" value="3"/>
<logic:notEmpty name="ExamSecuredMarksVerificationForm" property="registerNo">
	
		<%
						 ExamSecuredMarksVerificationForm objform = new ExamSecuredMarksVerificationForm();
						 if(request.getAttribute("STUDENTREGVALUES") != null){
							 objform = (ExamSecuredMarksVerificationForm)request.getAttribute("STUDENTREGVALUES");
						 } 
						 String filePath = request.getRealPath("/")+"GenImage/"+objform.getRegisterNo()+".jpg";
						 String imageName = objform.getRegisterNo()+".jpg";
						 BufferedImage img = new BufferedImage( 600, 125, BufferedImage.TYPE_INT_RGB );
					     Graphics g = img.getGraphics();
					     g.setColor(Color.WHITE);  
					     g.fillRect(0, 0, img.getWidth(), img.getHeight());  
					     Font myFont = new Font("Arial", Font.ITALIC | Font.BOLD, 14);
					     Graphics2D g2 = (Graphics2D)g;
					     Double theta = Math.toRadians(180);
					     g2.setColor(Color.black);
					     g2.setFont(myFont);
					     g2.rotate(theta, 80, 80);
					     g2.drawString(objform.getRegisterNo(), -105, 120);
						 g2.drawString(objform.getNameOfStudent(), -105, 140);
						 if(objform.getClassName() !=null ){
						 	g2.drawString(objform.getClassName(), -105, 160);
						 }
						 File mvOutPut = new File(filePath); 
						 ImageIO.write(img, "jpg", mvOutPut);
							
		%>
					 
		<p style=" height: 400px"></p>			 
					
	    <p align="left" style="height: 200px;">
	    	<img src="GenImage/<%=imageName%>" id="imagePrint" width="600" height="125" border="0">
	    </p>
</logic:notEmpty>
<logic:empty name="ExamSecuredMarksVerificationForm" property="registerNo">
	<table width="100%" height="435px">
		<tr>
			<td align="center" valign="middle">
				No Records Found
			</td>
		</tr>
	</table>
</logic:empty>
</html:form>
</body>
<script>
hook=false;
</script>
