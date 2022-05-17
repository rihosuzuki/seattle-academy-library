package jp.co.seattle.library.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.seattle.library.service.BooksService;

/**
 * 検索コントローラー
 */
@Controller // APIの入り口
public class SearchBooksController {

	@Autowired
	private BooksService booksService;

	/**
	 * 対象書籍を検索する
	 *
	 * @param locale ロケール情報
	 * @param bookId 書籍ID
	 * @param model  モデル情報
	 */
	@Transactional
	@RequestMapping(value = "searchBook", method = RequestMethod.POST)
	public String searchBook(Locale locale, @RequestParam("search") String search, Model model) {

		model.addAttribute("bookList", booksService.getSearchBookList(search));

		return "home";

	}
}
