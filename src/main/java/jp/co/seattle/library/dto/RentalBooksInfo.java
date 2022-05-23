package jp.co.seattle.library.dto;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RentalBooksInfo {

	private int bookId;

	private String title;

	private java.sql.Date rentalDate;

	private java.sql.Date returnDate;
	

	public RentalBooksInfo() {

	}

	public RentalBooksInfo(int bookId, String title, java.sql.Date rentalDate, java.sql.Date returnDate) {
		
		this.bookId = bookId;
        this.title = title;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;

	}

}
