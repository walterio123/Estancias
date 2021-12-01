package Login.web.Entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Rent {

		@Id
		@GeneratedValue(generator = "uuid")
		@GenericGenerator(name = "uuid", strategy = "uuid2")
	    private String id;
		
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date dateFrom;
	    @Temporal(TemporalType.DATE)
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private Date dateTo;
	    private boolean alta;
	    
	    @OneToOne
	    private House house;
	    
	    @OneToOne
	    private Customer customer;
	    
	    
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public Date getDateFrom() {
			return dateFrom;
		}
		public void setDateFrom(Date dateFrom) {
			this.dateFrom = dateFrom;
		}
		public Date getDateTo() {
			return dateTo;
		}
		public void setDateTo(Date dateTo) {
			this.dateTo = dateTo;
		}
		public boolean isAlta() {
			return alta;
		}
		public void setAlta(boolean alta) {
			this.alta = alta;
		}
		public House getHouse() {
			return house;
		}
		public void setHouse(House house) {
			this.house = house;
		}
		public Customer getCustomer() {
			return customer;
		}
		public void setCustomer(Customer customer) {
			this.customer = customer;
		}
	    
}
