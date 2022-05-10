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
 * 削除コントローラー
 */
@Controller //APIの入り口
public class DeleteBookController {
    final static Logger logger = LoggerFactory.getLogger(DeleteBookController.class);
    
	@Autowired
	private RentalBooksService rentalBooksService;
	
    @Autowired
    private BooksService booksService;
    
    /**
     * 対象書籍を削除する
     *
     * @param locale ロケール情報
     * @param bookId 書籍ID
     * @param model モデル情報
     * @return 遷移先画面名
     */
    @Transactional
    @RequestMapping(value = "/deleteBook", method = RequestMethod.POST)
    public String deleteBook(
            Locale locale,
            @RequestParam("bookId") Integer bookId,
            Model model) {
        logger.info("Welcome delete! The client locale is {}.", locale);
        
        int rentId = rentalBooksService.getBookInfo(bookId);
        
        if (rentId == 0) { //rentalsに消したい書籍(bookId)が登録されていなかったら削除できる
        	booksService.deleteBookList(bookId);
			model.addAttribute("rentMessage","貸出中");
			
		} else { //rentalsに書籍ID(bookId)が登録されていたら削除できないメッセージを表示
			model.addAttribute("rentErrorMessage","貸出中のため削除できません");
			model.addAttribute("bookDetailsInfo", booksService.getBookInfo(bookId));
			return "details";
		}	
        
       
        model.addAttribute("bookList", booksService.getBookList());
        return "home";
        }
}
