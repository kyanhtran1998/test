package Duan2.Traicagiong.entity;

import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "fish_top")
public class FishTop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
    
    @OneToOne
    @JoinColumn(name = "id_fish") 
	private Fish fish;

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "FishTop [id=" + id + ", fish=" + fish.getName() + fish.getImage() + "]";
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

	public FishTop(Integer id, Fish fish) {
		super();
		this.id = id;
		this.fish = fish;
	}

  
	public FishTop() {
		
	}

 

}