package com.pet.home;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.pet.home.board.impl.BoardDTO;
import com.pet.home.board.sharing.SharingService;
import com.pet.home.sell.SellItemDTO;
import com.pet.home.sell.SellItemService;
import com.pet.home.sell.purchase.PurchaseDTO;

import retrofit2.http.GET;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private SellItemService itemService;
	@Autowired
	private SharingService sharingService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) throws Exception {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		List<BoardDTO> sharingList = sharingService.getListByHit();
		model.addAttribute("sharingList",sharingList);
		
		List<SellItemDTO> purchaseList = itemService.getPurchaseListtoMain();
		model.addAttribute("purchaseList", purchaseList );
		return "main";
	}
	
}
