package jp.co.seattle.library.dto;

import java.util.Date;

import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class RentalBooksInfo {

	private int bookId;

	private String title;

	private Date rentalDate;

	private Date returnDate;
	

	public RentalBooksInfo() {

	}

	public RentalBooksInfo(int bookId, String title, Date rentalDate, Date returnDate) {
		
		this.bookId = bookId;
        this.title = title;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;

	}

}
