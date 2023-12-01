/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domaines;

import entities.Employe;
import entities.Service;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.RowEditEvent;
import service.EmployeService;
import service.ServiceService;

/**
 *
 * @author moukhtar
 */
@ManagedBean
@RequestScoped
public class EmployeBean {
    private Employe employe;
    private Service service;
    private List<Employe> employes;
    private EmployeService es;
    private ServiceService ss;
    private Employe chefDe;
    

    public EmployeBean() {

        employe = new Employe();
        chefDe = new Employe();
        es = new EmployeService();
        ss = new ServiceService();

    }

    public Employe getEmploye() {
        return employe;
    }

    public void setEmploye(Employe employe) {
        this.employe = employe;
    }

    public Employe getChef() {
        return chefDe;
    }

    public void setChef(Employe chefDe) {
        this.chefDe = chefDe;
    }

    public List<Employe> getEmployes() {
        if (employes == null) {
            employes = es.getAll();
        }
        return employes;
    }

    public EmployeService getEmployeService() {
        return es;
    }

    public void setEmployeService(EmployeService employeService) {
        this.es= es;
    }

    public ServiceService getServiceService() {
        return ss;
    }

    public void setServiceService(ServiceService serviceService) {
        this.ss = ss;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public void setEmployes(List<Employe> employes) {
        this.employes = employes;
    }
    
    
    

    public void onCreateAction() {
        if (chefDe != null) {
            chefDe = es.getById(chefDe.getId());
            employe.setChefDe(chefDe);
        }
        es.create(employe);
        employe = new Employe();
        chefDe = new Employe();
    }

    public void onEditAction(RowEditEvent event) {
        employe = (Employe) event.getObject();
        if (chefDe != null) {
            chefDe = es.getById(chefDe.getId());
            employe.setChefDe(chefDe);
        }
        service = ss.getById(employe.getService().getId());
        employe.setService(service);
        es.update(employe);
        chefDe = new Employe();
    }

    public void onCancelEdit() {
        //nothing
    }
    
   

    public void onDeleteAction() {
        List<Employe> employeesToUpdate = es.getByChef(employe);
        for (Employe employee : employeesToUpdate) {
                employee.setChefDe(null);
                es.update(employee);
            }
        employe.setChefDe(null);
        employe.setService(null);
        es.delete(es.getById(employe.getId()));
    }

}