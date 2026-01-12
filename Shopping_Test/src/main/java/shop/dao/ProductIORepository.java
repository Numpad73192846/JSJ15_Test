package shop.dao;

import java.sql.SQLException;
import java.sql.Types;

import shop.dto.Product;

public class ProductIORepository extends JDBConnection {

	/**
	 * 상품 입출고 등록
	 * @param product
	 * @param type
	 * @return
	 */
	public int insert(Product product) {
		// TODO: product_io 테이블에 상품 입출고 정보 등록
		int result = 0;
		
		String sql = "INSERT INTO product_io "
				   + "(product_id, order_no, amount, type, user_id) "
				   + "VALUES (?, ?, ?, ?, ?) ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, product.getProductId());
			
			if ( product.getOrderNo() == 0 ) {
				psmt.setNull(2, Types.INTEGER);
			}
			else {
				psmt.setInt(2, product.getOrderNo());
			}
			
			psmt.setInt(3, product.getQuantity());
			psmt.setString(4, product.getType());
			
			if ( product.getUserId() == null || product.getUserId().isEmpty() ) {
				psmt.setNull(5, Types.VARCHAR);
			}
			else {
				psmt.setString(5, product.getUserId());
			}
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	

}