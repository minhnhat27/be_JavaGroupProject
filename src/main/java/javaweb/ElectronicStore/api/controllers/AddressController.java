package javaweb.ElectronicStore.api.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javaweb.ElectronicStore.config.CustomUser;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/address")
public class AddressController {	
	@GetMapping("/getAddress")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAddress(@AuthenticationPrincipal CustomUser userDetails){
		
		return null;
	}
}
