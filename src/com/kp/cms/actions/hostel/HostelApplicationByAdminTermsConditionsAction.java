    package com.kp.cms.actions.hostel;
	import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DownloadAction;

import com.kp.cms.forms.hostel.HostelApplicationByAdminForm;
import com.kp.cms.handlers.hostel.HostelApplicationByAdminHandler;
import com.kp.cms.to.hostel.HostelTO;

	public class HostelApplicationByAdminTermsConditionsAction  extends DownloadAction{
			
				private static final Log log = LogFactory.getLog(HostelApplicationByAdminTermsConditionsAction.class);
				
				protected StreamInfo getStreamInfo(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					log.info("Entering into getStreamInfo of GuideLineDownloadActionId");
					HostelApplicationByAdminForm hostelApplicationByAdminForm = (HostelApplicationByAdminForm) form;
					/*final int id=hForm.getId();
					final List<HostelTO> hostelList = HostelEntryHandler.getInstance().getHostelDetailsById(id);
					Iterator<HostelTO> hIt = hostelList.iterator();
					HostelTO hostelTO = new HostelTO();*/
					HostelTO hostelTO= HostelApplicationByAdminHandler.getInstance().viewTermsCondition(hostelApplicationByAdminForm);	
					byte [] myFileBytes = null; 
					String fileName = "";
					String contentType = "";
					/*while (hIt.hasNext()){
						hostelTO = hIt.next();*/
						myFileBytes = hostelTO.getTermsConditions();
						fileName = hostelTO.getFileName();
						contentType = hostelTO.getContentType();
					//}
					response.setHeader("Content-disposition", "attachment; filename ="+fileName);
					
					File file = null;
					try {
						file = new File(request.getRealPath("")+ "//TempFiles//"+"outFile.txt");
						InputStream inputStream = new ByteArrayStreamInfo(contentType,
								myFileBytes).getInputStream();
						OutputStream out = new FileOutputStream(file);
						byte buffer[] = new byte[1024];
						int len = 0;
						while ((len = inputStream.read(buffer)) > 0){
							out.write(buffer, 0, len);
						}
						out.close();
						inputStream.close();
					} 
					catch (Exception e){
						log.error("Error occured at getStreamInfo of GuideLineDownloadActionId" + e);
					}
					log.info("Leaving into getStreamInfo of HostelApplicationByAdminTermsConditionsAction");
					return new FileStreamInfo(contentType, file);
				}

				protected class ByteArrayStreamInfo implements StreamInfo {
					transient protected String contentType;
					transient protected byte[] bytes;

					public ByteArrayStreamInfo(final String contentType, byte[] myPdfBytes) {
						this.contentType = contentType;
						this.bytes = myPdfBytes;
					}

					public String getContentType() {
						return contentType;
					}

					public InputStream getInputStream() throws IOException {
						return new ByteArrayInputStream(bytes);
					}
				}
		       
		}


