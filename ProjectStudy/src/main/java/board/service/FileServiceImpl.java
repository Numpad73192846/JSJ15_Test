package board.service;

import board.dao.FilesDAO;
import board.dto.FileResource;
import board.dto.Files;
import jakarta.servlet.http.HttpServletResponse;

public class FileServiceImpl implements FileService {
	
	private FilesDAO fileDAO = new FilesDAO();
	
    private String uploadRoot;
    private String defaultImagePhysicalPath;

	public FileServiceImpl(String uploadRoot, String defaultImagePhysicalPath) {
		this.uploadRoot = uploadRoot;
		this.defaultImagePhysicalPath = defaultImagePhysicalPath;
	}

	@Override
	public Files getFileOrNull(String id) throws Exception {
		Files file = fileDAO.selectById(id);
		return file;
	}

	@Override
	public FileResource resolveResourceOrDefault(Files file) throws Exception {
		if ( file == null ) {
			return getDefaultResource();
		}
		
		String physicalPath = buildPhysicalPath(file.getPath());
		
		boolean result = exists(physicalPath);
		
		if ( result ) {
			return toResourceFromFile(file);
		}
		
		return getDefaultResource();
	}

	@Override
	public void writeToResponse(FileResource resource, HttpServletResponse response) throws Exception {
		
	}

	private boolean exists(String physicalPath) {
		
		if ( physicalPath == null || physicalPath.isEmpty() ) {
			return false;
		}
		
		return true;
	}
	
	private String buildPhysicalPath(String webPath) {
		String physicalPath = uploadRoot + webPath;
		return physicalPath;
		
	}
	
	private String decideContentType(String contentType, String physicalPath) {
		
		if ( contentType == null || contentType.isEmpty() ) {
			String type[] = physicalPath.split(".");
			String result = type[type.length - 1];
			return result;
		}
		
		return contentType;
		
	}
	
	private Long readContentLength(String physicalPath) {
		
		Long result = 0L;
		
		if ( physicalPath == null ) {
			return result;
		}
		else { 
			result = (long) physicalPath.length();
		}
		
		return result;
		
	}
	
	private FileResource getDefaultResource() {
		String contentType = decideContentType(uploadRoot, defaultImagePhysicalPath);
		Long contentLength = readContentLength(defaultImagePhysicalPath);
		return new FileResource(contentType, defaultImagePhysicalPath, "", contentLength);
	}
	
	private FileResource toResourceFromFile(Files file) {
		String physicalPath = buildPhysicalPath(file.getPath());
		Long contentLength = readContentLength(physicalPath);
		String contentType = decideContentType(file.getContentType(), physicalPath);
		String downloadName = file.getOriginalName();
		
		return new FileResource(contentType, physicalPath, downloadName, contentLength);
		
	}


	
}
