package EventManagementDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import EventManagementDto.ClientService;

public class ClientServiceDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("uhari");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	 public ClientService saveClientService(ClientService clientservice) {
		et.begin();
		em.persist(clientservice);
		et.commit();
		return clientservice;
	}
	
	public ClientService findClientService(int clienteventId) {
		ClientService clientservice = em.find(ClientService.class, clienteventId);
		if(clientservice!=null) {
			return clientservice;
		}
		return null;
	}
	
	public ClientService updateClientService(ClientService clientservice,int id) {
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService!=null) {
			clientservice.setClientServiceId(id);
			et.begin();
			em.merge(clientservice);
			et.commit();
			return clientservice;
			
		}
		return null;
	}
	
	public ClientService deleteClientService(int id) {
		ClientService exClientService = em.find(ClientService.class, id);
		if(exClientService!=null) {
			et.begin();
			em.remove(exClientService);
			et.commit();
			return exClientService;
		}
		return null;
	}
}
