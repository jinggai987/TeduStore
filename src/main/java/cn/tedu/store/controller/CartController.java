package cn.tedu.store.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.tedu.store.bean.Cart;
import cn.tedu.store.bean.ResponseResult;
import cn.tedu.store.service.CartService;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {
	
	@Resource
	private CartService cartService;

	@RequestMapping("/list.do")
	public String showCartList(
		HttpSession session, ModelMap modelMap) {
		// 获取当前登录的用户ID
		Integer uid = getUidFromSession(session);
		// 根据用户ID获取购物车列表
		List<Cart> carts = cartService.getCartList(uid);
		// 将数据封装到ModelMap中以转发
		modelMap.addAttribute("carts", carts);
		// 执行转发
		return "cart_list";
	}
	
	@RequestMapping(value="add.do", 
			method=RequestMethod.POST)
	@ResponseBody
	public ResponseResult<Void> handleAddCart(
			Cart cart, HttpSession session) {
		Integer uid = getUidFromSession(session);
		cart.setUserId(uid);
		
		cartService.add(cart);
		
		ResponseResult<Void> rr
			= new ResponseResult<Void>();
		rr.setState(ResponseResult.STATE_OK);
		rr.setMessage("成功将商品添加到购物车");
		
		return rr;
	}

	
}
