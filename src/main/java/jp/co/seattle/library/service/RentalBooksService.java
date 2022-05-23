package jp.co.seattle.library.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.seattle.library.dto.RentalBooksInfo;
import jp.co.seattle.library.rowMapper.RentalBooksRowMapper;

/**
 * 書籍貸出サービス
 * 
 * rentalsテーブルに関する処理を実装する
 */
@Service
public class RentalBooksService {
	final static Logger logger = LoggerFactory.getLogger(BooksService.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 書籍IDに紐づく書籍詳細情報を取得する
	 *
	 * @param bookId 書籍ID
	 * @return 貸出書籍ID
	 */
	public int getBookInfo(int bookId) {

		// JSPに渡すデータを設定する
		String sql = "SELECT rent_id FROM rentals where rent_id =" + bookId;
		
		try {
			int rentId = jdbcTemplate.queryForObject(sql, Integer.class);
			return  rentId;
		} catch (Exception e) {
			return 0;
		}

	}
	
	
	/**
	 * 書籍情報と貸出日をrentalsテーブルに登録する
	 *
	 * @param bookId 書籍情報
	 */
	public void rentBook(int bookId) {

		String sql = "INSERT INTO rentals (rent_id, rent_date) VALUES (" + bookId + "," + "now());";

		jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 書籍IDに紐づく書籍貸出情報を更新する
	 *
	 * @param bookId 書籍ID
	 */
	public void rentedBook(int bookId) {
		
		String sql = " UPDATE rentals set return_date = null, rent_date = now() where rent_id = " + bookId;
		
		jdbcTemplate.update(sql);
	}
	
	
	/**
	 * 書籍IDに紐づく書籍返却情報を更新する
	 *
	 * @param bookId 書籍ID
	 */
	public void returnBook(int bookId) {
		
		String sql = " UPDATE rentals set return_date = now(), rent_date = null where rent_id = " + bookId;
		
		jdbcTemplate.update(sql);
	}


	/**
	 * 書籍IDに紐づく書籍の貸出日／返却日を取得する
	 *
	 * @param bookId 書籍ID
	 */
	public java.sql.Date selectRentBookDate(int bookId) {
		
		String sql = " SELECT rent_date FROM rentals where rent_id = " + bookId;
		
		try {
			java.sql.Date rentBookId = jdbcTemplate.queryForObject(sql, java.sql.Date.class);
			return  rentBookId;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	/**
	 * 貸出したことのある書籍履歴のリストを取得する
	 *
	 * @return 書籍リスト
	 */
	public List<RentalBooksInfo> RentBookList() {
		List<RentalBooksInfo> rentBookList = jdbcTemplate.query(
				
				"SELECT rent_id, title, rent_date, return_date FROM books LEFT OUTER JOIN rentals ON books.id = rentals.rent_id where rent_id is not null order by title",
				new RentalBooksRowMapper());

		return rentBookList;
	}



}
