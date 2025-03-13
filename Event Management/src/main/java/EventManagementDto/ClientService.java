package EventManagementDto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ClientService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int clientServiceId;
	private String clientServiceName;
	private double clientServiceCosts;
	private int clientServiceNoOfDays;
	private double clientServiceCostPerPerson;
	public int getClientServiceId() {
		return clientServiceId;
	}
	public void setClientServiceId(int clientServiceId) {
		this.clientServiceId = clientServiceId;
	}
	public String getClientServiceName() {
		return clientServiceName;
	}
	public void setClientServiceName(String clientServiceName) {
		this.clientServiceName = clientServiceName;
	}
	public double getClientServiceCosts() {
		return clientServiceCosts;
	}
	public void setClientServiceCosts(double clientServiceCosts) {
		this.clientServiceCosts = clientServiceCosts;
	}
	public int getClientServiceNoOfDays() {
		return clientServiceNoOfDays;
	}
	public void setClientServiceNoOfDays(int clientServiceNoOfDays) {
		this.clientServiceNoOfDays = clientServiceNoOfDays;
	}
	public double getClientServiceCostPerPerson() {
		return clientServiceCostPerPerson;
	}
	public void setClientServiceCostPerPerson(double clientServiceCostPerPerson) {
		this.clientServiceCostPerPerson = clientServiceCostPerPerson;
	}
	@Override
	public String toString() {
		return "ClientService [clientServiceId=" + clientServiceId + ", clientServiceName=" + clientServiceName
				+ ", clientServiceCosts=" + clientServiceCosts + ", clientServiceNoOfDays=" + clientServiceNoOfDays
				+ ", clientServiceCostPerPerson=" + clientServiceCostPerPerson + "]";
	}
	
	
}
