package javaweb.ElectronicStore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javaweb.ElectronicStore.models.User;
import javaweb.ElectronicStore.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired private AddressRepository addressRepository;
	
	@Override
	public boolean existAddress(User user, String city, String district, String ward) {
		// TODO Auto-generated method stub
		return addressRepository.existsByUserAndCityAndDistrictAndWard(user, city, district, ward);
	}
	
}
