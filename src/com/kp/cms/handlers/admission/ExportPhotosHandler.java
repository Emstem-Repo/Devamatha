package com.kp.cms.handlers.admission;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admission.ExportImagesAction;
import com.kp.cms.helpers.admission.ExportPhotosHelper;
import com.kp.cms.transactionsimpl.admission.ExportPhotosImpl;

public class ExportPhotosHandler {
	ExportPhotosHelper helper = new ExportPhotosHelper();
	ExportPhotosImpl impl = new ExportPhotosImpl();
	private static final Log log = LogFactory.getLog(ExportImagesAction.class);
	public Map<Integer, String> getPrograms(int year) throws Exception {
		return helper.convertBOTOMap(impl.getProgram(year));
	}

//	public void saveInages(int year, int programId, HttpServletRequest request,
//			HttpServletResponse res) throws Exception {
//		int totalImages = 0;
//		if (programId != 0 && year != 0)
//			totalImages = impl.getImages(year, programId); // New in
//		if(totalImages>0){
//			int PAGESIZE = 50; // class
//			int totalPages = (totalImages / PAGESIZE) + 1;
//			int page = 1;
//			String fileName = "D:/Images" + "-" + programId + "-" + year;
//			File tempZipFile = new File(fileName + ".zip");
//			
//			
//			ZipOutputStream out = new ZipOutputStream(new 
//					BufferedOutputStream(new FileOutputStream(tempZipFile)));
//			while (page <= totalPages) {
//				
//				List<ApplnDoc> images = impl.getImages(year, programId, page,
//						PAGESIZE);
//				
//				Iterator<ApplnDoc> itr = images.iterator();
//				InputStream inputStream =null;
//				while (itr.hasNext()) {
//					
//					ApplnDoc applnDoc = (ApplnDoc) itr.next();
//					if (applnDoc.getIsPhoto() != null)
//						if (applnDoc.getIsPhoto() && applnDoc.getDocument() != null) {
//							byte[] myFileBytes = applnDoc.getDocument();
//							String imageName = applnDoc.getAdmAppln().getApplnNo()
//							+ ".jpg";
////							in = new ByteArrayInputStream(myFileBytes);
////							BufferedImage bImageFromConvert = ImageIO.read(in);
////							ImageIO.write(bImageFromConvert, "jpg", new File(
////									imageName));
//							File file = null;
//							try {
//								file = new File("outFile.txt");
//								if(file.exists()){
//									file.delete();
//								}
//								 inputStream = new ByteArrayStreamInfo(
//										 imageName, myFileBytes)
//										.getInputStream();
//								 out = new ZipOutputStream(new 
//											BufferedOutputStream(new FileOutputStream(tempZipFile)));
//								byte buffer[] = new byte[1024];
//								int length = 0;
//								while ((length = inputStream.read(buffer)) > 0) {
//									out.write(buffer, 0, length);
//								}
//								out.close();
//								inputStream.close();
//							} catch (IOException e) {
//								log.info("DownloadImageAction IOException, "
//										+ e);
//							} catch (Exception e) {
//								log.info("DownloadImageAction Exception, " + e);
//							}
//							log.info("Exit DownloadExcelAction");
////							return new FileStreamInfo(contentType, file);
//						}
//							
//						}
//				}
//				page++;
//				res.setContentType("application/zip");
//				res.setHeader("Content-Disposition", "inline; filename=" + tempZipFile);
//				tempZipFile.delete();
//			}
//			
//			
//			
//			
////			outFolder.delete();
//		}

		// helper.saveImage(impl.getImages(year,programId));
		// 0. Create a temp zip file. Get an output filename for
		// content-disposition header
		// 1. For each page (<=50 images)retrieve from hibernate
		// 2. Wrap the outputstream with ZipOutputStream and append it to the
		// temp file
		// 3. Send the temp file to the client with content-type and
		// content-disposition header correctly set
		// 4. Delete temp file

	
//	protected class ByteArrayStreamInfo implements StreamInfo {
//
//		protected String contentType;
//		protected byte[] bytes;
//
//		public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
//			this.contentType = contentType;
//			this.bytes = myXLSBytes;
//		}
//
//		public String getContentType() {
//			return contentType;
//		}
//
//		public InputStream getInputStream() throws IOException {
//			return new ByteArrayInputStream(bytes);
//		}
//	}
}
