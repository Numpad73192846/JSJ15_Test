package board.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import board.dto.FileResource;
import board.dto.Files;
import board.service.FileService;
import board.service.FileServiceImpl;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/file/view/*")
public class FileViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String uploadRoot = "C:/upload";
	private String defaultImageWebPath = "/static/img/default-image.png";
	private String defaultImagePhysicalPath = uploadRoot + "/img";
	
	private FileService fileService = new FileServiceImpl(uploadRoot, defaultImagePhysicalPath);
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getPathInfo();
		String id = extractIdOrNull(pathInfo);
		try {
				Files file = ( id == null ) ? null : fileService.getFileOrNull(id);
				FileResource resource = fileService.resolveResourceOrDefault(file);
				fileService.writeToResponse(resource, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String extractIdOrNull(String pathInfo) {
		
		if ( pathInfo == null || pathInfo.equals("/") ) {
			return null;
		}
		
		String parts[] = pathInfo.split("/");
		
		if ( parts.length > 2  ) {
			return null;
		}
		
		String extractId = parts[1];
		
		if ( extractId.isEmpty() ) {
			return null;
		}
		
		return extractId;
	}

}
