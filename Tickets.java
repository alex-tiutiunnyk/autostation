package autostation;

import java.util.Date;

public class Tickets {
    private Integer idTicket;
    private String nameTicket;
    private String surnameTicket;
    private Date ageTicket;
    private String departureTicket;
    private String arrivalTicket;
    private Date depTimeTicket;
    private Date arrTimeTicket;
    private Double priceTicket;

    public Tickets(Integer idTicket, String nameTicket, String surnameTicket,
                   Date ageTicket, String departureTicket, String arrivalTicket,
                   Date depTimeTicket, Date arrTimeTicket, Double priceTicket) {
        this.idTicket = idTicket;
        this.nameTicket = nameTicket;
        this.surnameTicket = surnameTicket;
        this.ageTicket = ageTicket;
        this.departureTicket = departureTicket;
        this.arrivalTicket = arrivalTicket;
        this.depTimeTicket = depTimeTicket;
        this.arrTimeTicket = arrTimeTicket;
        this.priceTicket = priceTicket;
    }


    public Integer getIdTicket() {
        return idTicket;
    }

    public String getNameTicket() {
        return nameTicket;
    }

    public String getSurnameTicket() {
        return surnameTicket;
    }

    public Date getAgeTicket() {
        return ageTicket;
    }

    public String getDepartureTicket() {
        return departureTicket;
    }

    public String getArrivalTicket() {
        return arrivalTicket;
    }

    public Date getDepTimeTicket() {
        return depTimeTicket;
    }

    public Date getArrTimeTicket() {
        return arrTimeTicket;
    }

    public Double getPriceTicket() {
        return priceTicket;
    }
}
