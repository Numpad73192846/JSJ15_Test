package board.dto;

import lombok.Data;

@Data
public class FileResource {

	private String contentType;
	private String physicalPath;
	private String downloadName;
	private Long contentLength;
	
	public FileResource() {
	}
	
	public FileResource(String contentType, String physicalPath, String downloadName, Long contentLength) {
		this.contentType = contentType;
		this.physicalPath = physicalPath;
		this.downloadName = downloadName;
		this.contentLength = contentLength;
	}
	
	public static FileResource ofPhysicalFile(String contentType, String physicalPath, String downloadName, Long contentLength) {
		return null;
	}
	
	public static FileResource ofDefaultImage(String contentType, String physicalPath, Long contentLength) {
		return null;
	}

}
