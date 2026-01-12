package shop.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shop.dto.Order;
import shop.dto.Product;

public class OrderRepository extends JDBConnection {
	
	/**
	 * 주문 등록
	 * @param user
	 * @return
	 */
	public int insert(Order order) {
		// TODO: order 테이블에 주문정보 등록
		int result = 0;
		
		String sql = "INSERT INTO `order` "
				   + "(ship_name, zip_code, country, address, date, order_pw, user_id, total_price, phone) "
				   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, order.getShipName());
			psmt.setString(2, order.getZipCode());
			psmt.setString(3, order.getCountry());
			psmt.setString(4, order.getAddress());
			psmt.setString(5, order.getDate());
			psmt.setString(6, order.getOrderPw());
			psmt.setString(7, order.getUserId());
			psmt.setInt(8, order.getTotalPrice());
			psmt.setString(9, order.getPhone());
			
			result = psmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 최근 등록한 orderNo 
	 * @return
	 */
	public int lastOrderNo() {
		// TODO: 가장 최근에 등록한 주문번호 조회
		int lastNo = 0;
		
		String sql = "SELECT MAX(order_no) AS last_no "
				   + "FROM `order` ";
		
		try {
			psmt = con.prepareStatement(sql);
			rs = psmt.executeQuery();
			
			if ( rs.next() ) {
				lastNo = rs.getInt("last_no");
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastNo;
	}

	
	/**
	 * 주문 내역 조회 - 회원
	 * @param userId
	 * @return
	 */
	public List<Product> list(String userId) {
		// TODO: 회원의 주문내역 조회
		List<Product> list = new ArrayList<>();
		
		String sql = "SELECT "
				   + "p.product_id, p.name, p.unit_price, p.description, "
				   + "p.manufacturer, p.category, p.units_in_stock, "
				   + "p.`condition`, p.file, "
				   + "io.amount, io.type, io.order_no, io.user_id "
				   + "FROM `order` o "
				   + "JOIN product_io io ON o.order_no = io.order_no "
				   + "JOIN product p ON io.product_id = p.product_id "
				   + "WHERE o.user_id = ? "
				   + "ORDER BY o.order_no DESC ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, userId);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setName(rs.getString("name"));
				product.setUnitPrice(rs.getInt("unit_price"));
				product.setDescription(rs.getString("description"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setCategory(rs.getString("category"));
				product.setUnitsInStock(rs.getLong("units_in_stock"));
				product.setCondition(rs.getString("condition"));
				product.setFile(rs.getString("file"));
				product.setQuantity(rs.getInt("amount"));
				product.setType(rs.getString("type"));
				product.setOrderNo(rs.getInt("order_no"));
				product.setUserId(rs.getString("user_id"));
				
				list.add(product);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 주문 내역 조회 - 비회원
	 * @param phone
	 * @param orderPw
	 * @return
	 */
	public List<Product> list(String phone, String orderPw) {
		// TODO: 비회원의 주문내역 조회
		List<Product> list = new ArrayList<>();
		
		String sql = "SELECT "
				   + "p.product_id, p.name, p.unit_price, p.description, "
				   + "p.manufacturer, p.category, p.units_in_stock, "
				   + "p.`condition`, p.file, "
				   + "io.amount, io.type, io.order_no, io.user_id "
				   + "FROM `order` o "
				   + "JOIN product_io io ON o.order_no = io.order_no "
				   + "JOIN product p ON io.product_id = p.product_id "
				   + "WHERE o.phone = ? AND o.order_pw = ? "
				   + "ORDER BY o.order_no DESC ";
		
		try {
			psmt = con.prepareStatement(sql);
			psmt.setString(1, phone);
			psmt.setString(2, orderPw);
			rs = psmt.executeQuery();
			
			while (rs.next()) {
				Product product = new Product();
				product.setProductId(rs.getString("product_id"));
				product.setName(rs.getString("name"));
				product.setUnitPrice(rs.getInt("unit_price"));
				product.setDescription(rs.getString("description"));
				product.setManufacturer(rs.getString("manufacturer"));
				product.setCategory(rs.getString("category"));
				product.setUnitsInStock(rs.getLong("units_in_stock"));
				product.setCondition(rs.getString("condition"));
				product.setFile(rs.getString("file"));
				product.setQuantity(rs.getInt("amount"));
				product.setType(rs.getString("type"));
				product.setOrderNo(rs.getInt("order_no"));
				product.setUserId(rs.getString("user_id"));
				
				list.add(product);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}






























