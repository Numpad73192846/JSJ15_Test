package board.dto;

import java.sql.Timestamp;
import java.util.UUID;

import lombok.Data;

@Data
public class Files {

	private String id;	// UUID
	private String parentTable;
	private int parentNo;
	private String originalName;
	private String savedName;
	private String path;
	private String contentType;
	private Long fileSize;
	private boolean isMain;
	private int sortOrder;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	public Files() {
		this.id = UUID.randomUUID().toString();
	}
	
}
