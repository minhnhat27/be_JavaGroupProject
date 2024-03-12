package javaweb.ElectronicStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javaweb.ElectronicStore.models.order.OrderDetail;

public interface OrderDetailsRespository extends JpaRepository<OrderDetail, Integer> {
	
}
