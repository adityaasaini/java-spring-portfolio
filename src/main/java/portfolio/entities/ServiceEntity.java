package portfolio.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="services")
public class ServiceEntity {
	
	public ServiceEntity(){
		
	}

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
    private int id;
	
	@Column(length = 30)
	private String title;
	
	@Column(length = 80)
	private String description;
	
	@Column(length = 300)
	private String filename;
	
	@Column(length = 50)
	private String datetime;

	public ServiceEntity(int id, String title, String description, String filename, String datetime) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.filename = filename;
		this.datetime = datetime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	
	
}
