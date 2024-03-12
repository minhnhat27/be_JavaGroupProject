package javaweb.ElectronicStore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;
import javaweb.ElectronicStore.models.CartItem;
import javaweb.ElectronicStore.models.Product;
import javaweb.ElectronicStore.models.User;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
	List<CartItem> findByUser(User user);
	
	CartItem findByUserAndProduct(User user, Product product);
	
	@Modifying
	@Transactional
	void deleteAllByUser(User user);
}