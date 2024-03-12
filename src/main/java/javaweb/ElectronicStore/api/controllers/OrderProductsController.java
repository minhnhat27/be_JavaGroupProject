package javaweb.ElectronicStore.api.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import javaweb.ElectronicStore.api.payload.request.OrderRequest;
import javaweb.ElectronicStore.api.payload.request.UpdateOrderRequest;
import javaweb.ElectronicStore.api.payload.response.OrderResponse;
import javaweb.ElectronicStore.config.CustomUser;
import javaweb.ElectronicStore.models.Address;
import javaweb.ElectronicStore.models.CartItem;
import javaweb.ElectronicStore.models.Product;
import javaweb.ElectronicStore.models.User;
import javaweb.ElectronicStore.models.order.Order;
import javaweb.ElectronicStore.models.order.OrderDetail;
import javaweb.ElectronicStore.models.order.OrderStatus;
import javaweb.ElectronicStore.models.order.PaymentMethod;
import javaweb.ElectronicStore.service.AddressService;
import javaweb.ElectronicStore.service.ApiUserService;
import javaweb.ElectronicStore.service.CartItemService;
import javaweb.ElectronicStore.service.OrderService;
import javaweb.ElectronicStore.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderProductsController {
	@Autowired private ProductService productService;
	@Autowired private ApiUserService userService;
	
	@Autowired private CartItemService cartItemService;
	@Autowired private OrderService orderService;
	
	@Autowired private AddressService addressService;
	
	@GetMapping("/getOrders")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> orderProducts(@AuthenticationPrincipal CustomUser userDetails){
		User user = userService.getUserById(userDetails.getId());
		List<Order> orders = orderService.getOrdersByUser(user);
		List<OrderResponse> response = new ArrayList<>();
		orders.forEach(e -> {
			OrderResponse order  = new OrderResponse();
			order.setId(e.getId());
			order.setOrderTime(e.getOrderTime().toString());
			order.setTotal(e.getTotal());
			order.setStatus(e.getStatus().name());
			response.add(order);
		});
		return ResponseEntity.ok().body(response);
	}
	
	@PostMapping("/orderProducts")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> orderProducts(@AuthenticationPrincipal CustomUser userDetails, @Valid @RequestBody OrderRequest orderRequest ){
		try {
			User user = userService.getUserById(userDetails.getId());
			
			boolean existedAddress = addressService.existAddress(user, orderRequest.getCity(), orderRequest.getDistrict(), orderRequest.getWard());
			if(!existedAddress) {
				Address address = new Address();
				address.setCity(orderRequest.getCity());
			    address.setDistrict(orderRequest.getDistrict());
			    address.setWard(orderRequest.getWard());
			    address.setStreet(orderRequest.getStreet());
			    address.setUser(user);
			    user.getAddresses().add(address);
			}
			
			Order order = new Order();
			order.setOrderTime(new Date());
			order.setShippingCost(0);
			order.setTax(0);
			order.setTotal(orderRequest.getSubtotal() + order.getShippingCost() + order.getTax());
			order.setDeliverDays(3);
			order.setPaymentMethod(PaymentMethod.COD);
			order.setStatus(OrderStatus.PROCESSING);
			order.setCustomer(user);
			
			Set<OrderDetail> orderDetails = new HashSet<>();
			orderRequest.getProductId().forEach(id -> {
				Product product = productService.getProductById(id);
				CartItem ProductInCart = cartItemService.getByUserAndProduct(user, product);
				cartItemService.removeFromCart((long) ProductInCart.getId()); //remove Cart
				
				OrderDetail orderDetail = new OrderDetail();	
				orderDetail.setProduct(product);
				orderDetail.setQuantity(ProductInCart.getQuantity());
				orderDetail.setUnitPrice(orderRequest.getSubtotal());
				orderDetail.setOrder(order);
				orderDetails.add(orderDetail);
			});
			order.setOrderDetails(orderDetails);
			orderService.saveOrder(order);
			return ResponseEntity.ok().body("Đặt hành thành công");
		} catch (Exception e) {
			return ResponseEntity.ok().body("Đặt hàng thất bại");
		}
//		OrderDto.paymentType = PaymentMethod.VNPAY;
//		OrderDto.userId = userDetails.getId();
//		OrderDto.subtotal = orderRequest.getSubtotal();
//		OrderDto.productIds = orderRequest.getProductId();
//		OrderDto.city = orderRequest.getCity();
//		OrderDto.district = orderRequest.getDistrict();
//		OrderDto.ward = orderRequest.getWard();
//		OrderDto.street = orderRequest.getStreet();
//		HttpHeaders headers = new HttpHeaders();
//	    headers.add("Location", "/api/payment/create_payment?amount=" + (float)orderRequest.getSubtotal());
//	    return new ResponseEntity<>(headers, HttpStatus.FOUND);
	}
	
	@PostMapping("/updateOrder")
	public ResponseEntity<?> updateOrder(@Valid @RequestBody UpdateOrderRequest updateOrderRequest){
		Order order = orderService.getOrderById(updateOrderRequest.getId());
		if ("cancel".equals(updateOrderRequest.getAction()) && !order.getStatus().equals(OrderStatus.PROCESSING)) {
			return ResponseEntity.badRequest().build();
		}
		if ("cancel".equals(updateOrderRequest.getAction())) {
	        order.setStatus(OrderStatus.CANCELLED);
	    } else if ("receive".equals(updateOrderRequest.getAction())) {
	        order.setStatus(OrderStatus.DELIVERED);
	    }
	    orderService.saveOrder(order);
		return ResponseEntity.ok().build();
	}
}
