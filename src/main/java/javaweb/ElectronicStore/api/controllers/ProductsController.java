package javaweb.ElectronicStore.api.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import javaweb.ElectronicStore.api.payload.response.ProductResponse;
import javaweb.ElectronicStore.models.Product;
import javaweb.ElectronicStore.service.ProductService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/products")
public class ProductsController {
	@Autowired private ProductService productService;
	
	@GetMapping("/getProducts")
	public ResponseEntity<?> getProducts(){
		List<Product> products = productService.getAllProduct();
		List<ProductResponse> response = new ArrayList<>();
		products.forEach(e -> {
			ProductResponse product = new ProductResponse();
			product.setId(e.getId());
			product.setName(e.getName());
			product.setAlias(e.getAlias());
			product.setPrice(e.getPrice());
			product.setDiscountPercent(e.getDiscountPercent());
			product.setMainImage(e.getMainImage());
			product.setBrand(e.getBrand().getName());
			product.setCategory(e.getCategory().getName());
			response.add(product);
		});
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/getTopProducts")
	public ResponseEntity<?> getTopProducts(){
		List<Product> products = productService.getTopProducts();
		List<ProductResponse> response = new ArrayList<>();
		products.forEach(e -> {
			ProductResponse product = new ProductResponse();
			product.setId(e.getId());
			product.setName(e.getName());
			product.setAlias(e.getAlias());
			product.setPrice(e.getPrice());
			product.setDiscountPercent(e.getDiscountPercent());
			product.setMainImage(e.getMainImage());
			product.setBrand(e.getBrand().getName());
			product.setCategory(e.getCategory().getName());
			response.add(product);
		});
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/product/{id}")
	public ResponseEntity<?> getTopProducts(@Valid @PathVariable Long id){
		Product products = productService.getProductById(id);
		ProductResponse product = new ProductResponse();
		product.setId(products.getId());
		product.setName(products.getName());
		product.setAlias(products.getAlias());
		product.setPrice(products.getPrice());
		product.setDiscountPercent(products.getDiscountPercent());
		product.setMainImage(products.getMainImage());
		product.setBrand(products.getBrand().getName());
		product.setCategory(products.getCategory().getName());
		return ResponseEntity.ok(product);
	}
}
