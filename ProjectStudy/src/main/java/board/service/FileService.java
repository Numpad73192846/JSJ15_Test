package board.service;

import board.dto.FileResource;
import board.dto.Files;
import jakarta.servlet.http.HttpServletResponse;

public interface FileService {

	public Files getFileOrNull(String id) throws Exception;
	
	public FileResource resolveResourceOrDefault(Files file) throws Exception;
	
	public void writeToResponse(FileResource resource, HttpServletResponse response) throws Exception;
	
}
