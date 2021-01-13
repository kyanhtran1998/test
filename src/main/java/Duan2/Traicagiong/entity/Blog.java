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
@Table(name = "blogs")
public class Blog {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
    @NotEmpty
    @Column(name = "title")
	private String name;
    @NotEmpty 
    @Column(name = "content")
	private String content;
    @NotEmpty 
    @Column(name = "summary")
	private String summary;
    
    @Column(name = "image")
	private String image;
    
    @Column(name = "date")
   	private Date date;
   
    public Blog() {}
    public Blog(Integer id,  String name,  String content,  String image,Date date,String summary) {
		super();
		this.id = id;
		this.name = name;
		this.content = content;
		this.image = image;
		this.date = date;
		this.summary = summary;
	}
    public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
}