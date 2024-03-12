package javaweb.ElectronicStore;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javaweb.ElectronicStore.models.Brand;
import javaweb.ElectronicStore.models.Category;
import javaweb.ElectronicStore.models.Product;
import javaweb.ElectronicStore.models.Role;
import javaweb.ElectronicStore.models.User;
import javaweb.ElectronicStore.repository.BrandRepository;
import javaweb.ElectronicStore.repository.CategoryRepository;
import javaweb.ElectronicStore.repository.ProductRepository;
import javaweb.ElectronicStore.repository.RoleRepository;
import javaweb.ElectronicStore.repository.UserRepository;


@SpringBootApplication
public class ElectronicStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}
	
	@Bean
	CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository,
							BrandRepository brandRepository, CategoryRepository categoryRepository,
							ProductRepository productRepository,PasswordEncoder passwordEncode){
		return args ->{
			if(roleRepository.findByName("ADMIN") != null) return;
			Role adminRole = roleRepository.save(new Role("ADMIN"));
			Role userrole = roleRepository.save(new Role("USER"));

			Set<Role> adminroles = new HashSet<>();
			adminroles.add(adminRole);
			
			Set<Role> userroles = new HashSet<>();
			userroles.add(userrole);

			User admin = new User((long) 1, "1", passwordEncode.encode("admin"), "admin@gmail.com", "admin", "admin", adminroles, true, new Date());
			User user = new User((long) 2, "2", passwordEncode.encode("user"), "user@gmail.com", "user", "user", userroles, true, new Date());
			userRepository.save(admin);
			userRepository.save(user);
			
			List<Brand> brands = new ArrayList<>();
			Brand brand1 = new Brand(1, "NOKIA", "brand1.png");
	        Brand brand2 = new Brand(2, "SAMSUNG", "brand2.png");
			
	        brands.add(brand1);
	        brands.add(brand2);
	        
	        brandRepository.saveAll(brands);
	        
	        List<Category> categories = new ArrayList<>();
	        Category category1 = new Category(1, "Điện Thoại", "ĐT", "dt.png", true);
	        Category category2 = new Category(2, "Máy Tính", "MT", "mt.png", true);
			
	        categories.add(category1);
	        categories.add(category2);
	        
	        categoryRepository.saveAll(categories);

	        List<Product> products = new ArrayList<>();
	        Product product1 = new Product(1, "tai nghe", "TN", "sản phẩm 1", "sản phẩm 1 khá ok", new Date(), new Date(), true, 1, 100000, 1, "product-1.jpg", category1, brand1);
	        Product product2 = new Product(2, "bàn phím", "BP", "sản phẩm 2", "sản phẩm 2 khá ok", new Date(), new Date(), true, 2, 200000, 2, "product-2.jpg", category2, brand2);
	        Product product3 = new Product(3, "bàn phím không dây", "BPKD", "sản phẩm 3", "sản phẩm 3 khá ok", new Date(), new Date(), true, 3, 300000, 3, "product-3.jpg", category1, brand1);
	        Product product4 = new Product(4, "con chuột", "cc", "sản phẩm 4", "sản phẩm 4 khá ok", new Date(), new Date(), true, 4, 400000, 4, "product-4.jpg", category2, brand2);
	        Product product5 = new Product(5, "cục sạc máy tính", "csmt", "sản phẩm 5", "sản phẩm 5 khá ok", new Date(), new Date(), true, 5, 500000, 5, "product-5.jpg", category1, brand1);
	        Product product6 = new Product(6, "cục sạc điện thoại", "csdt", "sản phẩm 6", "sản phẩm 6 khá ok", new Date(), new Date(), true, 6, 600000, 6, "product-1.jpg", category2, brand2);
	        Product product7 = new Product(7, "dây cắm", "dc", "sản phẩm 7", "sản phẩm 7 khá ok", new Date(), new Date(), true, 7, 700000, 7, "product-2.jpg", category1, brand1);
	        Product product8 = new Product(8, "con chuột2", "cc2", "sản phẩm 8", "sản phẩm 8 khá ok", new Date(), new Date(), true, 8, 800000, 8, "product-3.jpg", category2, brand2);
	        Product product9 = new Product(9, "con chuột3", "cc3", "sản phẩm 9", "sản phẩm 9 khá ok", new Date(), new Date(), true, 9, 900000, 9, "product-4.jpg", category1, brand1);
	        Product product10 = new Product(10, "con chuột4", "cc4", "sản phẩm 10", "sản phẩm 10 khá ok", new Date(), new Date(), true, 10, 10000, 10, "product-5.jpg", category2, brand2);

	        Product product11 = new Product(11, "Laptop Asus i5", "asi5", "sản phẩm 11", "sản phẩm 11 khá ok", new Date(), new Date(), true, 5, 100000, 5, "product-5.jpg", category1, brand1);
	        Product product12 = new Product(12, "Điện thoại Nokia", "dtnka", "sản phẩm 12", "sản phẩm 12 khá ok", new Date(), new Date(), true, 6, 2900000, 3, "product-1.jpg", category2, brand2);
	        Product product13 = new Product(13, "Laptop MSI i7", "msii7", "sản phẩm 13", "sản phẩm 13 khá ok", new Date(), new Date(), true, 7, 1200000, 1, "product-2.jpg", category2, brand1);
	        Product product14 = new Product(14, "Laptop MSI Ryzen 7", "msir7", "sản phẩm 14", "sản phẩm 14 khá ok", new Date(), new Date(), true, 8, 5000000, 6, "product-3.jpg", category2, brand2);
	        Product product15 = new Product(15, "Điện thoại Nokia 1280", "nka1280", "sản phẩm 15", "sản phẩm 15 khá ok", new Date(), new Date(), true, 9, 1300000, 4, "product-4.jpg", category2, brand1);
	        Product product16 = new Product(16, "Iphone 14 Pro Max", "ip14px", "sản phẩm 16", "sản phẩm 16 khá ok", new Date(), new Date(), true, 10, 200000, 5, "product-5.jpg", category1, brand2);		
	        Product product17 = new Product(17, "Asus i7 2022", "si5", "sản phẩm 17", "sản phẩm 17 khá ok", new Date(), new Date(), true, 5, 1000000, 5, "product-5.jpg", category1, brand1);
	        Product product18 = new Product(18, "Nokia Lumia 222", "nka222", "sản phẩm 18", "sản phẩm 18 khá ok", new Date(), new Date(), true, 6, 15000000, 3, "product-1.jpg", category1, brand2);
	        Product product19 = new Product(19, "MSI i7 2021", "msi21", "sản phẩm 19", "sản phẩm 19 khá ok", new Date(), new Date(), true, 7, 1760000, 1, "product-2.jpg", category1, brand1);
	        Product product20 = new Product(20, "MSI Ryzen 7", "msi7", "sản phẩm 20", "sản phẩm 20 khá ok", new Date(), new Date(), true, 8, 120000, 6, "product-3.jpg", category2, brand2);
	        Product product21 = new Product(21, "Samsung Galaxy 1280", "ss1280", "sản phẩm 21", "sản phẩm 21 khá ok", new Date(), new Date(), true, 9, 1990000, 4, "product-4.jpg", category1, brand1);
	        Product product22 = new Product(22, "Iphone 11 Pro Max", "ip11px", "sản phẩm 22", "sản phẩm 22 khá ok", new Date(), new Date(), true, 10, 210000, 5, "product-5.jpg", category2, brand2);		
	        
	        products.add(product1);
	        products.add(product2);
	        products.add(product3);
	        products.add(product4);
	        products.add(product5);
	        products.add(product6);
	        products.add(product7);
	        products.add(product8);
	        products.add(product9);
	        products.add(product10);

	        products.add(product11);
	        products.add(product12);
	        products.add(product13);
	        products.add(product14);
	        products.add(product15);
	        products.add(product16);
	        products.add(product17);
	        products.add(product18);
	        products.add(product19);
	        products.add(product20);
	        products.add(product21);
	        products.add(product22);
	        
	        productRepository.saveAll(products);
	        
		};
	}

}
