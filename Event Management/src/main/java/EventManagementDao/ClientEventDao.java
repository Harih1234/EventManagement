package EventManagementDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import EventManagementDto.ClientEvent;



public class ClientEventDao {
	
	EntityManagerFactory emf = Persistence.createEntityManagerFactory("uhari");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	 public ClientEvent saveClientEvent(ClientEvent clientevent) {
		et.begin();
		em.persist(clientevent);
		et.commit();
		return clientevent;
	}
	
	public ClientEvent findClientEvent(int clienteventId) {
		ClientEvent clientevent = em.find(ClientEvent.class, clienteventId);
		if(clientevent!=null) {
			return clientevent;
		}
		return null;
	}
	
	public ClientEvent updateClientEvent(ClientEvent clientevent,int id) {
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent!=null) {
			clientevent.setClientEventId(id);
			et.begin();
			em.merge(clientevent);
			et.commit();
			return clientevent;
			
		}
		return null;
	}
	
	public ClientEvent deleteClient(int id) {
		ClientEvent exClientEvent = em.find(ClientEvent.class, id);
		if(exClientEvent!=null) {
			et.begin();
			em.remove(exClientEvent);
			et.commit();
			return exClientEvent;
		}
		return null;
	}
}
