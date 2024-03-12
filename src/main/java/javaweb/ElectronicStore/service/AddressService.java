package javaweb.ElectronicStore.service;

import javaweb.ElectronicStore.models.User;

public interface AddressService {
	boolean existAddress(User user, String city, String district, String ward);
}
