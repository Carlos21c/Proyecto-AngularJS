package es.unex.pi.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Restaurant {

	private long id;
	private String name;
	private String image;
	private String description;
	private String address;
	private String telephone;
	private String city;
	private float minPrice;
	private float maxPrice;
	private String contactEmail;
	private float gradesAverage;
	private int bikeFriendly;
	private int available;
	private long idu;
	private int deliveryTime;
	private float deliveryPrice;
	private List<Integer> list;
	
	
	public Restaurant() {
		super();
		this.image="";
		list = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public float getMinPrice() {
		return minPrice;
	}
	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}
	public float getMaxPrice() {
		return maxPrice;
	}
	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}
	public float getGradesAverage() {
		return gradesAverage;
	}
	public void setGradesAverage(float gradesAverage) {
		this.gradesAverage = gradesAverage;
	}
	public int getBikeFriendly() {
		return bikeFriendly;
	}
	public void setBikeFriendly(int bikeFriendly) {
		this.bikeFriendly = bikeFriendly;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public int getAvailable() {
		return available;
	}
	public void setAvailable(int available) {
		this.available = available;
	}
	public long getIdu() {
		return idu;
	}
	public void setIdu(long idu) {
		this.idu = idu;
	}
	public int getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(int deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	public float getDeliveryPrice() {
		return deliveryPrice;
	}
	public void setDeliveryPrice(float deliveryPrice) {
		this.deliveryPrice = deliveryPrice;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}
	
}
