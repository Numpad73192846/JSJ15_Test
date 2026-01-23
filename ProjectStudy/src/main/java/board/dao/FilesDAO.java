package board.dao;

import board.common.JDBConnection;
import board.dto.Files;

public class FilesDAO extends JDBConnection {

	public Files selectById(String id) {
		
		Files file = null;
		
		String sql = "SELECT path, content_type, saved_name, original_name "
				   + "FROM files "
				   + "WHERE id = ?";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			
			if ( rs.next() ) {
				file = new Files();
				file.setPath(rs.getString("path"));
				file.setContentType(rs.getString("content_type"));
				file.setSavedName(rs.getString("saved_name"));
				file.setOriginalName(rs.getString("original_name"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		
		return file;
		
	}
	
}
