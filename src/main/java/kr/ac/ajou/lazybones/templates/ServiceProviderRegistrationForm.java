package kr.ac.ajou.lazybones.templates;

import java.io.Serializable;


/**
 *
 * @author Han
 */
public class ServiceProviderRegistrationForm implements Serializable{

    public String getSp() {
        return sp;
    }

    public void setSp(String sp) {
        this.sp = sp;
    }

    public Service[] getServices() {
        return services;
    }

    public void setServices(Service[] services) {
        this.services = services;
    }

    private String sp;
    private Service[] services;
    private ReservationService[] reservationServices;

    public ReservationService[] getReservationServices() {
        return reservationServices;
    }

    public void setReservationServices(ReservationService[] reservationServices) {
        this.reservationServices = reservationServices;
    }
    
    
    
    
}
