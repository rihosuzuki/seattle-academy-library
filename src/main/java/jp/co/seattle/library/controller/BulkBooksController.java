package jp.co.seattle.library.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jp.co.seattle.library.dto.BookDetailsInfo;
import jp.co.seattle.library.service.BooksService;

@Controller // APIの入り口
public class BulkBooksController {

	@Autowired
	private BooksService booksService;

	@RequestMapping(value = "/bulkBook", method = RequestMethod.GET) // value＝actionで指定したパラメータ
	// RequestParamでname属性を取得
	public String login(Model model) {
		return "bulkBook";

	}

	@Transactional
	@RequestMapping(value = "/bulkRegistBook", method = RequestMethod.POST)
	public String bulkRegistBook(Locale locale, @RequestParam("file") MultipartFile file, Model model) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

			List<String[]> booksList = new ArrayList<String[]>();
			List<Integer> errorList = new ArrayList<Integer>();
			
			String line;
			int count = 0;


			if (!br.ready()) {
				model.addAttribute("bulkErrorMessage", "CSVに情報がありません。");
				return "bulkBook";
			}

			while ((line = br.readLine()) != null) { // CSVファイルから１行読み込む
				count = count + 1;
				final String[] split = line.split(",", -1);

				if (StringUtils.isEmpty(split[0]) || StringUtils.isEmpty(split[1]) || StringUtils.isEmpty(split[2])
						|| StringUtils.isEmpty(split[3]) || !(split[3].matches("^[0-9]{8}"))
						|| split[4].length() != 0 && !(split[4].matches("^[0-9]{10}|[0-9]{13}"))) {
					errorList.add(count);
					
				} else {
					booksList.add(split);
				}

			}

			if (errorList.size() > 0) {
				List<String> bulkErrorMessage = new ArrayList<String>();
				for (int i = 0; i < errorList.size(); i++) {
					bulkErrorMessage.add(errorList.get(i) + "行目でバリデーションエラーが起きました。");
				}
				model.addAttribute("bulkErrorMessage", bulkErrorMessage);
				return "bulkBook";
			}

			
			for (int i = 0; i < booksList.size(); i++) {
				String[] bookList = booksList.get(i);

				BookDetailsInfo bookInfo = new BookDetailsInfo();
				bookInfo.setTitle(bookList[0]);
				bookInfo.setAuthor(bookList[1]);
				bookInfo.setPublisher(bookList[2]);
				bookInfo.setPublishDate(bookList[3]);
				bookInfo.setISBN(bookList[4]);

				booksService.registBook(bookInfo);

			}

		} catch (

		IOException e) {
			throw new RuntimeException("ファイルが読み込めません", e);
		}
		
		
		return "redirect:home";

	}
}
