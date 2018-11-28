package kr.or.ddit.web.model2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileListGenerator {
	public File[] getFileList(String parentPath, String fileName) {
		File[] fileList = null;
		
		File parentFolder = new File(parentPath);
		File targetFile = new File(parentFolder, fileName);
		
		if (targetFile.isDirectory()) {
			fileList = targetFile.listFiles();
		} else {
			fileList = new File(parentFolder,"").listFiles();
		}
		return fileList;
	}

	public void deleteFile(String parentPath, String fileName) {
		File targetFile = new File(parentPath, fileName);
		if (targetFile.isDirectory()) {
			File[] fileList = targetFile.listFiles();
			for (File file : fileList) {
				deleteFile(targetFile.getAbsolutePath() + "/", file.getName());
			}
		}
		targetFile.delete();
	}

	public void copyFile(String contextPath, String parentPath, String fileName) {
		File targetFile = new File(parentPath, fileName);
		File copyFile = new File(contextPath, fileName);
		if (targetFile.isDirectory()) {
			copyFile.mkdir();
			File[] fileList = targetFile.listFiles();
			for (File file : fileList) {
				copyFile(copyFile.getAbsolutePath() + "/", targetFile.getAbsolutePath() + "/", file.getName());
			}
		}
		
		byte[] buffer = new byte[1024];
		int pointer = -1;
		if (!copyFile.isDirectory()) {
			try(
					FileInputStream fis = new FileInputStream(targetFile);
					FileOutputStream fos = new FileOutputStream(copyFile);
					
					){
				while ((pointer = fis.read(buffer)) != -1) {
					fos.write(buffer, 0, pointer);
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}

















