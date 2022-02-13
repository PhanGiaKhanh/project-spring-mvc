package DiamonShop.Controller.User;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import DiamonShop.Dto.CartDto;
import DiamonShop.Service.User.CartService;

@Controller
public class CartController extends BaseController {
	@Autowired
	private CartService cartService;
	
	
	// Add item Cart
	@SuppressWarnings("unchecked")
	@GetMapping(value = "addCart/{id}")
	public String addCart(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		Map<Integer, CartDto> mapCart = (Map<Integer, CartDto>) session.getAttribute("cart");
		if (mapCart == null) {
			mapCart = new HashMap<>();
		}
		cartService.addItemCart(id, mapCart);
		session.setAttribute("cart", mapCart);
		session.setAttribute("totalQuantityCart", cartService.totalQuantity(mapCart));
		session.setAttribute("totalPriceCart", cartService.totalPrice(mapCart));
		return "redirect:" + request.getHeader("Referer");
	}
	
	// Show Cart
	@GetMapping(value = "showCart")
	public ModelAndView showCart(HttpSession session) {
		baseModelAndView.addObject("slides", baseHomeService.getDataSlides());
		baseModelAndView.addObject("categories", baseHomeService.getDataCategories());
		baseModelAndView.addObject("products", baseHomeService.getDataProductsDto());
		baseModelAndView.setViewName("user/cart/list_cart"); 
		return baseModelAndView;
	}
	
	// Edit Cart
	@GetMapping(value = "editCart/{id}")
	public String editCart(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		Map<Integer, CartDto> mapCart = (Map<Integer, CartDto>) session.getAttribute("cart");
		if (mapCart == null) {
			mapCart = new HashMap<>();
		}
		mapCart = cartService.editCart(id, id, mapCart);
		return "redirect:" + request.getHeader("Referer");
	}
	
	// Delete Cart
	@GetMapping(value = "deleteCart/{id}")
	public String deleteCart(@PathVariable int id, HttpSession session, HttpServletRequest request) {
		Map<Integer, CartDto> mapCart = (Map<Integer, CartDto>) session.getAttribute("cart");
		if (mapCart == null) {
			mapCart = new HashMap<>();
		}
		mapCart = cartService.removeCart(id, mapCart);
		session.setAttribute("cart", mapCart);
		session.setAttribute("totalQuantityCart", cartService.totalQuantity(mapCart));
		session.setAttribute("totalPriceCart", cartService.totalPrice(mapCart));
		return "redirect:" + request.getHeader("Referer");
	}
}
