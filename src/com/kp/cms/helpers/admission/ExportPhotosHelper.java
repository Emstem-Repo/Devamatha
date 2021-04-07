package com.kp.cms.helpers.admission;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kp.cms.actions.admission.DownloadExcelAction;
import com.kp.cms.utilities.CommonUtil;

public class ExportPhotosHelper {
	private static final Log log = LogFactory.getLog(DownloadExcelAction.class);

	@SuppressWarnings("unchecked")
	public Map<Integer, String> convertBOTOMap(List program) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		Iterator<Object> iter = program.iterator();
		while (iter.hasNext()) {
			Object[] obj = (Object[]) iter.next();
			int key = 0;
			String value = null;
			if (obj[0] != null) {
				key = Integer.parseInt(obj[0].toString());
			}
			if (obj[1] != null) {
				value = obj[1].toString();
			}
			map.put(key, value);
		}
		map = (HashMap<Integer, String>) CommonUtil.sortMapByValue(map);
		return map;
	}

//	public void saveImage(List<ApplnDoc> images, String zipName) {
//		if (images.size() > 0) {
//
////			File zipFile = new File("Images");
//
//			Iterator<ApplnDoc> itr = images.iterator();
//			try {
//				FileOutputStream fos = new FileOutputStream(zipName);
//				ZipOutputStream zos = new ZipOutputStream(fos);
//				int bytesRead;
//				byte[] buffer = new byte[2048];
//				CRC32 crc = new CRC32();
//				InputStream in = null;
//				while (itr.hasNext()) {
//					ApplnDoc applnDoc = (ApplnDoc) itr.next();
//					if (applnDoc.getIsPhoto() != null)
//						if (applnDoc.getIsPhoto()
//								&& applnDoc.getDocument() != null) {
//							byte[] myFileBytes = applnDoc.getDocument();
//							String fileName = applnDoc.getAdmAppln()
//									.getApplnNo()
//									+ ".jpg";
//							in = new ByteArrayInputStream(myFileBytes);
//							BufferedImage bImageFromConvert = ImageIO.read(in);
//							ImageIO.write(bImageFromConvert, "jpg", new File(
//									fileName));
//
//							// new Added to Zip
////							File file = new File(fileName);
//							BufferedInputStream bis = new BufferedInputStream(
//									new FileInputStream(new File(fileName)));
//							crc.reset();
//
//							crc.update(myFileBytes,0,myFileBytes.length);
//							
//
//							bis.close();
//							bis = new BufferedInputStream(new FileInputStream(file));
//							ZipEntry entry = new ZipEntry(fileName);
//							entry.setMethod(ZipEntry.STORED);
//							entry.setCompressedSize(file.length());
//							entry.setSize(file.length());
//							entry.setCrc(crc.getValue());
//							zos.putNextEntry(entry);
//							while ((bytesRead = bis.read(buffer)) != -1) {
//								zos.write(buffer, 0, bytesRead);
//							}
//							bis.close();
//							log.info("Exit DownloadExcelAction");
//						}
//				}
//				zos.close();
//				in.close();
//			} catch (IOException e) {
//				log.info("DownloadImageAction IOException, " + e);
//			} catch (Exception e) {
//				log.info("DownloadImageAction Exception, " + e);
//			}
//		}
//	}

	

	// protected class ByteArrayStreamInfo implements StreamInfo {
	//
	// protected String contentType;
	// protected byte[] bytes;
	//
	// public ByteArrayStreamInfo(String contentType, byte[] myXLSBytes) {
	// this.contentType = contentType;
	// this.bytes = myXLSBytes;
	// }
	//
	// public String getContentType() {
	// return contentType;
	// }
	//
	// public InputStream getInputStream() throws IOException {
	// return new ByteArrayInputStream(bytes);
	// }
	// }

}
