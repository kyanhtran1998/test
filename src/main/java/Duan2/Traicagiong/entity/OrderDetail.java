package Duan2.Traicagiong.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "orderdetail")
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
    @OneToOne
    @JoinColumn(name = "id_fish") 
	private Fish fish;

    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_order")
	private Order order;

	@Column(name = "quality")
	private Integer quality;
	

	
	public OrderDetail(Integer id, Fish fish, Integer quality, Order order) {
		this.id = id;
		this.fish = fish;
		this.quality = quality;
		this.order = order;
	
	}

	

	public OrderDetail() {

	}
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}



	public Fish getFish() {
		return fish;
	}

	public void setFish(Fish fish) {
		this.fish = fish;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getQuality() {
		return quality;
	}

	public void setQuality(Integer quality) {
		this.quality = quality;
	}
	@Override
	public String toString() {
		return "OrderDetail [id=" + id + ", id_fish=" + fish + ", order=" + order + ", quality=" + quality + "]";
	}

}