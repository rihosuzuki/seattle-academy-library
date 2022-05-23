package jp.co.seattle.library.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jp.co.seattle.library.service.RentalBooksService;

@Controller // APIの入り口
public class rentHistoryController {

	final static Logger logger = LoggerFactory.getLogger(rentHistoryController.class);

	@Autowired
	private RentalBooksService rentalbooksService;

	@RequestMapping(value = "/rentHistory", method = RequestMethod.GET) 
	// RequestParamでname属性を取得
	public String rentHistory(Model model) {
		model.addAttribute("rentalBooksList", rentalbooksService.RentBookList());
		return "rentHistory";
	}
	
	
}
