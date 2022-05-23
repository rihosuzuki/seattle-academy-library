package jp.co.seattle.library.controller;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;
import jp.co.seattle.library.service.RentalBooksService;

/**
 * Handles requests for the application home page.
 */
@Controller // APIの入り口
public class RentalBooksController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

	@Autowired
	private RentalBooksService rentalBooksService;
	
	@Autowired
	private BooksService booksService;

	@Transactional
	@RequestMapping(value = "/rentBook", method = RequestMethod.POST)
	public String rentalBook(Locale locale, @RequestParam("bookId") int bookId, Model model) {
		logger.info("Welcome insertBooks.java! The client locale is {}.", locale);
		
		int rentId = rentalBooksService.getBookInfo(bookId);
		java.sql.Date rentDate = rentalBooksService.selectRentBookDate(bookId);
		
		if (rentId == 0) { //rentalsに借りたい書籍ID(bookId)が登録されていなかったら貸出できる
			rentalBooksService.rentBook(bookId);
			
		} else if (rentDate == null) {
			rentalBooksService.rentedBook(bookId);
			
		} else { //rentalsに書籍ID(bookId)が登録されていたら貸出できないメッセージを表示
			model.addAttribute("ErrorMessage","貸出済みです。");
			
		}
		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		return "details";

	}
}
