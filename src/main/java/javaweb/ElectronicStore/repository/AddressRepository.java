package javaweb.ElectronicStore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import javaweb.ElectronicStore.models.Address;
import javaweb.ElectronicStore.models.User;

public interface AddressRepository extends JpaRepository<Address, Integer> {
	boolean existsByUserAndCityAndDistrictAndWard(User user, String city, String district, String ward);
}
