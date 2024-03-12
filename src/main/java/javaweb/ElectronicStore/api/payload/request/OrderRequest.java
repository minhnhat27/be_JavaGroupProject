package javaweb.ElectronicStore.api.payload.request;

import java.util.List;

public class OrderRequest {
	private String paymentType;
	private String city;
	private String district;
	private String ward;
	private String street;
	private List<Long> productId;
	private Float subtotal;
	
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getWard() {
		return ward;
	}
	public void setWard(String ward) {
		this.ward = ward;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public List<Long> getProductId() {
		return productId;
	}
	public void setProductId(List<Long> productId) {
		this.productId = productId;
	}
	public Float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}
	
}
