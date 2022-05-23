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
public class ReturnBooksController {
	final static Logger logger = LoggerFactory.getLogger(AddBooksController.class);

	@Autowired
	private RentalBooksService rentalBooksService;

	@Autowired
	private BooksService booksService;

	// 書籍を返却する
	@Transactional
	@RequestMapping(value = "/returnBook", method = RequestMethod.POST)
	public String returnBook(Locale locale, @RequestParam("bookId") Integer bookId, Model model) {
		logger.info("Welcome insertBooks.java! The client locale is {}.", locale);

		java.sql.Date rentDate = rentalBooksService.selectRentBookDate(bookId);

		if (rentDate == null) { // rentalsに返却したい書籍ID(bookId)が登録されていなかったらエラーメッセージを表示
			model.addAttribute("ErrorMessage", "貸出されていません。");

		} else { // rentalsに書籍ID(bookId)が登録されていたら書籍を返却
			rentalBooksService.returnBook(bookId);
		}

		model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
		return "details";

	}

}
