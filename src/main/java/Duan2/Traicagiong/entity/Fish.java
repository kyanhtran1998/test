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
@Table(name = "fish")
public class Fish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
    @NotEmpty
    @Column(name = "name")
	private String name;
    @NotEmpty 
    @Column(name = "description")
	private String description;
    @NotEmpty 
    @Column(name = "image")
	private String image;
    
    @Column(name = "price")
	private Integer price;
    
    @Column(name = "quality")
  	private Integer quality;
    
    @Column(name = "date")
  	private Date date;
    
	public Fish(Integer id, String name, String description, String image,Integer price, Integer quality,Date date) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.quality = quality;
		this.date = date;
	}
	

	public Fish(Integer id) {
		this.id = id;
	}
	
	public Fish(Integer id,Integer quality) {
		this.id = id;
		this.quality = quality;
	}
	
	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public Fish() {

	}

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
	
	
	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}
	
	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}

}