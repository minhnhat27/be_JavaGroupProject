package javaweb.ElectronicStore.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import javaweb.ElectronicStore.api.payload.request.CartItemRequest;
import javaweb.ElectronicStore.api.payload.response.CartItemResponse;
import javaweb.ElectronicStore.config.CustomUser;
import javaweb.ElectronicStore.models.CartItem;
import javaweb.ElectronicStore.models.Product;
import javaweb.ElectronicStore.models.User;
import javaweb.ElectronicStore.service.ApiUserService;
import javaweb.ElectronicStore.service.CartItemService;
import javaweb.ElectronicStore.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/cart")
public class CartController {
	@Autowired private ProductService productService;
	@Autowired private ApiUserService userService;
	
	@Autowired private CartItemService cartItemService;
	
	@GetMapping("/getCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getCart(@AuthenticationPrincipal CustomUser userDetails){
		User user = userService.getUserById(userDetails.getId());
		List<CartItem> carts = cartItemService.getCartItemsByUser(user);
		List<CartItemResponse> response = new ArrayList<>();
		carts.forEach(e -> {
			CartItemResponse cart = new CartItemResponse();
			cart.setProductId(e.getProduct().getId());
			cart.setName(e.getProduct().getName());
			cart.setPrice(e.getProduct().getPrice());
			cart.setDiscountPercent(e.getProduct().getDiscountPercent());
			cart.setQuantity(e.getQuantity());
			cart.setImg(e.getProduct().getMainImage());
			response.add(cart);
		});
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/addToCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> addToCart(@AuthenticationPrincipal CustomUser userDetails, @Valid @RequestBody CartItemRequest itemRequest ){
		try {
			Product product = productService.getProductById(itemRequest.getProductId());
			User user = userService.getUserById(userDetails.getId());
			CartItem existedCart = cartItemService.getByUserAndProduct(user, product);
			if(existedCart != null) {
				Integer quantity = existedCart.getQuantity() + itemRequest.getQuantity();
				if(quantity < 1) {
					return ResponseEntity.badRequest().body("Cập nhật giỏ hàng thất bại");
				}
				existedCart.setQuantity(quantity);
				cartItemService.AddToCart(existedCart);
				return ResponseEntity.ok().body("Cập nhật giỏ thành công");
			}
			else {
				CartItem items = new CartItem();
				items.setQuantity(itemRequest.getQuantity());
				items.setProduct(product);
				items.setCustomer(user);
				cartItemService.AddToCart(items);
				return ResponseEntity.ok().body("Thêm vào giỏ hàng thành công");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Thêm vào giỏ hàng thất bại");
		}
	}
	
	@PostMapping("/deleteCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteCart(@AuthenticationPrincipal CustomUser userDetails, @Valid @RequestBody CartItemRequest itemRequest){
		try {
			Product product = productService.getProductById(itemRequest.getProductId());
			User user = userService.getUserById(userDetails.getId());
			CartItem existedCart = cartItemService.getByUserAndProduct(user, product);
			if(existedCart != null) {
				cartItemService.removeFromCart((long) existedCart.getId());
				return ResponseEntity.ok().body("Đã xóa khỏi giỏ hàng");
			}
			else {
				return ResponseEntity.badRequest().body("Không tìm thấy sản phẩm");
			}
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Xóa sản phẩm trong giỏ hàng thất bại");
		}
	}
	
	@GetMapping("/deleteAllCart")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> deleteAllCart(@AuthenticationPrincipal CustomUser userDetails){
		try {
			User user = userService.getUserById(userDetails.getId());
			cartItemService.removeAllCart(user);
			return ResponseEntity.ok().body("Đã xóa tất cả sản phẩm khỏi giỏ hàng");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Xóa giỏ hàng thất bại");
		}
	}
}
