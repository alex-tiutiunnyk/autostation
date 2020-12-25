package autostation;

import java.util.Date;

public class Timetable {
    private Integer idWay;
    private String departureWay;
    private String arrivalWay;
    private Date depTimeWay;
    private Date arrTimeWay;
    private Double priceWay;

    public Timetable(Integer idTimetable, String departureTimetable, String arrivalTimetable,
                     Date depTimeTimetable, Date arrTimeTimetable, Double priceTimetable) {
        this.idWay = idTimetable;
        this.departureWay = departureTimetable;
        this.arrivalWay = arrivalTimetable;
        this.depTimeWay = depTimeTimetable;
        this.arrTimeWay = arrTimeTimetable;
        this.priceWay = priceTimetable;
    }

    public Integer getIdTimetable() {
        return idWay;
    }

    public String getDepartureWay() {
        return departureWay;
    }

    public String getArrivalWay() {
        return arrivalWay;
    }

    public Date getDepTimeWay() {
        return depTimeWay;
    }

    public Date getArrTimeWay() {
        return arrTimeWay;
    }

    public Double getPriceWay() {
        return priceWay;
    }
}
