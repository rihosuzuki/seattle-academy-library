package jp.co.seattle.library.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

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
	 * 書籍をrentalsに登録する
	 *
	 * @param bookId 書籍情報
	 */
	public void rentBook(int bookId) {

		String sql = "INSERT INTO rentals (rent_id) VALUES (" + bookId + ");";

		jdbcTemplate.update(sql);
	}
	

	
	/**
	 * 書籍の返却
	 * 
	 * 書籍貸出IDを取得する
	 *
	 * @param bookId 貸出書籍ID
	 * @return 貸出書籍ID
	 */
	public int getRentBookInfo(int bookId) {

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
	 * 書籍を返却する = rentalsにある書籍情報を削除する
	 * 
	 * @param bookId 書籍ID
	 * 
	 */
	public void returnBook(int bookId) {
		String sql = "delete from rentals where rent_id = " + bookId + ";";

		jdbcTemplate.update(sql);
	}

}
