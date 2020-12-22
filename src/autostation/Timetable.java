package autostation;

import java.util.Date;

public class Timetable {
    private Integer idTimetable;
    private String departureTimetable;
    private String arrivalTimetable;
    private Date depTimeTimetable;
    private Date arrTimeTimetable;
    private Double priceTimetable;

    public Timetable(Integer idTimetable, String departureTimetable, String arrivalTimetable,
                     Date depTimeTimetable, Date arrTimeTimetable, Double priceTimetable) {
        this.idTimetable = idTimetable;
        this.departureTimetable = departureTimetable;
        this.arrivalTimetable = arrivalTimetable;
        this.depTimeTimetable = depTimeTimetable;
        this.arrTimeTimetable = arrTimeTimetable;
        this.priceTimetable = priceTimetable;
    }

    public Integer getIdTimetable() {
        return idTimetable;
    }

    public String getDepartureTimetable() {
        return departureTimetable;
    }

    public String getArrivalTimetable() {
        return arrivalTimetable;
    }

    public Date getDepTimeTimetable() {
        return depTimeTimetable;
    }

    public Date getArrTimeTimetable() {
        return arrTimeTimetable;
    }

    public Double getPriceTimetable() {
        return priceTimetable;
    }
}
