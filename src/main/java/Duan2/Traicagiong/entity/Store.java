package Duan2.Traicagiong.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "store")
public class Store {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "id")
	private Integer id;
    @NotEmpty
    @Column(name = "name")
	private String name;
    @NotEmpty 
    @Column(name = "image")
	private String image;

    @NotEmpty 
    @Column(name = "phone")
	private String phone;
    @NotEmpty 
    @Column(name = "address")
	private String address;
    @NotEmpty 
    @Column(name = "email")
	private String email;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Store(Integer id,  String name,  String image,  String phone,
			 String address,  String email) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.phone = phone;
		this.address = address;
		this.email = email;
	}
	public Store() {}
}