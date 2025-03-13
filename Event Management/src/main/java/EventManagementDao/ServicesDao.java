package EventManagementDao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import EventManagementDto.Services;

public class ServicesDao {

	EntityManagerFactory emf = Persistence.createEntityManagerFactory("uhari");
	EntityManager em = emf.createEntityManager();
	EntityTransaction et = em.getTransaction();
	
	 public Services saveService(Services service) {
		et.begin();
		em.persist(service);
		et.commit();
		return service;
	}
	
	public Services findService(int serviceId) {
		Services service = em.find(Services.class, serviceId);
		if(service!=null) {
			return service;
		}
		return null;
	}
	
	public Services updateService(Services service,int id) {
		Services exService = em.find(Services.class, id);
		if(exService!=null) {
			service.setServiceId(id);
			et.begin();
			em.merge(service);
			et.commit();
			return service;
			
		}
		return null;
	}
	
	public Services deleteService(int id) {
		Services exService = em.find(Services.class, id);
		if(exService!=null) {
			et.begin();
			em.remove(exService);
			et.commit();
			return exService;
		}
		return null;
	}

}
