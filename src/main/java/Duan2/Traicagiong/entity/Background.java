package Duan2.Traicagiong.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="background") 
public class Background {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String image;
    
    public Background() {
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Background(Integer id, String image) {
		this.id = id;
		this.image = image;
	}

   
}