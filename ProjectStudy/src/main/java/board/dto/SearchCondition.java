package board.dto;

import lombok.Data;

@Data
public class SearchCondition {

	private Integer cate;
	private String keyword;
	private int page = 1;
	private int size = 10;
	
}
