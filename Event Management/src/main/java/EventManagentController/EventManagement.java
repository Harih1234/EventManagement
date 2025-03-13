package EventManagentController;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import EventManagementDao.AdminDao;
import EventManagementDao.ClientDao;
import EventManagementDao.ClientEventDao;
import EventManagementDao.ClientServiceDao;
import EventManagementDao.ServicesDao;
import EventManagementDto.Admin;
import EventManagementDto.Client;
import EventManagementDto.ClientEvent;
import EventManagementDto.ClientService;
import EventManagementDto.EventType;
import EventManagementDto.Services;

public class EventManagement {
	
	AdminDao adao = new AdminDao();
	ServicesDao sdao = new ServicesDao();
	ClientDao cdao = new ClientDao();
	ClientEventDao cedao = new ClientEventDao();
	ClientServiceDao csdao = new ClientServiceDao();
	

	public static void main(String[] args)
	{	
		EventManagement em = new EventManagement();
		System.out.println(em.saveAdmin());
		System.out.println(em.adminLogin());
		System.out.println(em.saveService());
		System.out.println(em.getAllServices());
		System.out.println(em.updateService());
		System.out.println(em.deleteService());
		System.out.println(em.saveClient());
		System.out.println(em.clientLogin());

		System.out.println(em.createClientEvent());
		System.out.println(em.removeClientService());
//		-------------------------------------------
		
		
	
		
	}
	
	public Admin saveAdmin() {
		Admin admin = new Admin();
		Scanner s = new Scanner(System.in);
		System.out.println("enter admin name : ");
		admin.setAdminName(s.next());
		System.out.println("enter admin mail : ");
		admin.setAdminMail(s.next());
		System.out.println("enter Admin Password : ");
		admin.setAdminPassword(s.next());
		System.out.println("enter Admin contact number : ");
		admin.setContact(s.nextLong());
		
		return adao.saveAdmin(admin);
	}	
//	---------------------------------------------------------------------------
	public Admin adminLogin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Admin Mail : ");
		String adminMail = sc.next();
		System.out.println("Enter Admin Password : ");
		String adminPassword = sc.next();
		Query query =  Persistence.createEntityManagerFactory("uhari").
	createEntityManager()
	.createQuery("select a from Admin a where a.adminMail=?1");
		query.setParameter(1, adminMail);
		Admin exAdmin = (Admin) query.getSingleResult();
		if(exAdmin!=null) {
			if(exAdmin.getAdminPassword().equals(adminPassword)) {
				return exAdmin;
			}
			return null;
		}
		return null;
	}
//	-----------------------------------------------------	
	public Services saveService() {
		Admin exAdmin = adminLogin();
		if(exAdmin!=null) {
			Services service = new Services();
			Scanner s = new Scanner(System.in);
			
			System.out.println("Enter Service Name : ");
			service.setServiceName(s.next());
			
			System.out.println("Enter Service Cost Per Person : ");
			service.setServiceCostPerPerson(s.nextDouble());
			
			System.out.println("Enter Service Cost Per Day : ");
			service.setServiceCostPerDay(s.nextDouble());
			
			Services savedService = sdao.saveService(service);
			exAdmin.getServices().add(savedService);
			adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			
			return service;
		}
		return null;
	}
	
//	-------------------------------------------------------
	public List<Services> getAllServices(){
		System.out.println("Enter Admin Credentials to Proceed ");
		Admin exAdmin = adminLogin();
		if(exAdmin!=null) {
			Query query =  Persistence.createEntityManagerFactory("uhari").
					createEntityManager()
					.createQuery("select s from Services s");
			List<Services> services = query.getResultList();
			return services;
		}
		return null;
	}
	
	public String updateService() {
		Scanner sc = new Scanner(System.in);
		List<Services> service = getAllServices();
		for(Services s : service) {
			System.out.println("serviceId   "+"ServiceName  "+"Cost_Per_Person  "+"Cost_Per_Day");
			System.out.println("  "+s.getServiceId()+"  "+s.getServiceName()+"  "+s.getServiceCostPerPerson()+"  "+s.getServiceCostPerDay());
		}
		System.out.println("************Choose Service Id From Above to Update***********");
		int id = sc.nextInt();
		Services tobeUpdated = sdao.findService(id);
		
		System.out.println("Enter Updated Cost Per Person :");
		double costperPerson = sc.nextDouble();
	
		
		System.out.println("Enter Updated Cost Per Day :");
		double costperday = sc.nextDouble();
		
		tobeUpdated.setServiceCostPerDay(costperday);
		tobeUpdated.setServiceCostPerPerson(costperPerson);
		
		Services updated = sdao.updateService(tobeUpdated, id);
		if(updated!=null) {
			return "service update success";
		}
		return "invalid service id";
	}
//------------------------------------------------------------------------------------------------------------------------
	
	public Services deleteService() {
		Scanner sc = new Scanner(System.in);
		Admin exAdmin = adminLogin();
		
		if(exAdmin!=null) {
			List<Services> service = exAdmin.getServices();
			for(Services s : service) {
				System.out.println("serviceId   "+"ServiceName  "+"Cost_Per_Person  "+"Cost_Per_Day");
				System.out.println("  "+s.getServiceId()+"  "+s.getServiceName()+"  "+s.getServiceCostPerPerson()+"    "+s.getServiceCostPerDay());
			}
			System.out.println("*****Choose Service Id From Above to Delete****");
			int id = sc.nextInt();
			List<Services> newser = new ArrayList<Services>();
			for (Services services : service) {
				if(services.getServiceId()!=id) {
					newser.add(services);
				}
			}
			exAdmin.setServices(newser);
			adao.updateAdmin(exAdmin, exAdmin.getAdminId());
			return sdao.deleteService(id);

		}
		return null;
	}
//	-----------------------------------------------------------------------------------------------------
	public Client saveClient() {
		Admin exAdmin = adminLogin();
		Client client = new Client();
		Scanner s = new Scanner(System.in);
		System.out.println("enter Client name : ");
		client.setClientName(s.next());
		System.out.println("enter Client mail : ");
		client.setClientMail(s.next());
		System.out.println("enter Client Contact : ");
		client.setClientContact(s.nextLong());
		System.out.println("enter Client Password : ");
		client.setClientPassword(s.next());
		
		
		return cdao.saveClient(client);
	}
//	-----------------------------------------------
	public Client clientLogin() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter Client Mail : ");
		String clientMail = sc.next();
		System.out.println("Enter Client Password : ");
		String clientPassword = sc.next();
		Query query =  Persistence.createEntityManagerFactory("uhari").
	createEntityManager()
	.createQuery("select c from Client c where c.clientMail=?1");
		query.setParameter(1, clientMail);
		Client exClient = (Client) query.getSingleResult();
		if(exClient!=null) {
			if(exClient.getClientPassword().equals(clientPassword)) {
				return exClient;
			}
			return null;
		}
		return null;
	}
	
//	----------------------------------------------------------------------------------------------------------------------------
//	Create Client Event
//	Add  Event Service
//  Remove Event Service
	public ClientEvent createClientEvent() {
		ClientEvent clientevent = new ClientEvent();
		Scanner s = new Scanner(System.in);
	
		Client client = clientLogin();
		
		if(client !=null) {
		
		
		System.out.println("enter noOfpeople : ");
		clientevent.setClientEventNoOfpeople(s.next());


		System.out.println("enter StartDate : ");
		String date[] = s.next().split("/");
		clientevent.setStartDate(LocalDate.of(Integer.parseInt(date[2]),Integer.parseInt(date[1]),Integer.parseInt(date[0])));

		System.out.println("enter NoOfDays : ");
		clientevent.setClientEventNoOfDays(s.nextInt());

		System.out.println("enter EventLocation : ");
		clientevent.setClientEventLocation(s.next());

		System.out.println("enter EventCost : ");
		clientevent.setClientEventCost(s.nextDouble());
		
		ClientService clientservice = new ClientService();
		
		System.out.println("Enter Client Service Name : ");
		clientservice.setClientServiceName(s.next());
		
		System.out.println("Enter Client Service Cost : ");
		clientservice.setClientServiceCosts(s.nextDouble());
		
		System.out.println("Enter ClientService NoOfDays : ");
		clientservice.setClientServiceNoOfDays(s.nextInt());
		
		System.out.println("Enter ClientService Cost Per Person : ");
		clientservice.setClientServiceCostPerPerson(s.nextDouble());
		
		List<ClientService> cs = new ArrayList<ClientService>();
		cs.add(clientservice);
		clientevent.setClientServices(cs);
		
		System.out.println("Enter Event type:");
		EventType arr[] = {EventType.Anniversary,EventType.BachelorParty,EventType.babyShower,EventType.BirthDay,EventType.Engagement,EventType.Marriage,EventType.NamingCeremony,EventType.Reunion};
		
		for (int i = 0; i < arr.length; i++) {
			System.out.println("Press "+i+" "+ arr[i]);
		}
		
		clientevent.setEventType(arr[s.nextInt()]);
		
		clientevent = cedao.saveClientEvent(clientevent);
        client.getEvents().add(clientevent);
		clientevent.setClient(client);
		cdao.updateClient(client, client.getClientId());
		return cedao.updateClientEvent(clientevent, clientevent.getClientEventId());
		

		}
		return null;

	}
//	--------------------------------------------------------------------------------------------
	
    public ClientService removeClientService() {
		Client client = clientLogin();
		List<ClientEvent>ce =  client.getEvents();
		List<ClientService>cs= new ArrayList<ClientService>();
		for (ClientEvent cev : ce) 
		{
			cs=cev.getClientServices();
			System.out.println("Event id : "+cev.getClientEventId()+", Event : "+cev.getEventType()+", Client Services : {");
			for (ClientService cse : cs) 
			{
				System.err.println("Service id : "+cse.getClientServiceId()+" Service Name: "+cse.getClientServiceName());
			}
			
		}
		System.out.println("Enter event id :");
		Scanner s = new Scanner(System.in);
		ClientEvent cev = cedao.findClientEvent(s.nextInt());
		System.out.println("Enter Client  Service id :");
		ClientService csv= csdao.findClientService(s.nextInt());
		cev.getClientServices().remove(csv);
		System.out.println("*****Choose ClientService Id From Above to Remove****");
		int id = s.nextInt();
		List<ClientService> newser = new ArrayList<ClientService>();
		for (ClientService clientservices : cs) {
		if(clientservices.getClientServiceId()!=id) {
			newser.add(clientservices);
			}
		}
		cev.setClientServices(newser);
		cedao.updateClientEvent(cev, id);
		return csdao.deleteClientService(id);
	}

}


		
		

