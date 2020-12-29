package autostation;

import java.util.Date;

public class Drivers {
    private Integer idDriver;
    private String nameDriver;
    private String surnameDriver;
    private Date ageDriver;
    private Integer licenseDriver;

    public Drivers(Integer idDriver, String nameDriver, String surnameDriver, Date ageDriver, Integer licenseDriver) {
        this.idDriver = idDriver;
        this.nameDriver = nameDriver;
        this.surnameDriver = surnameDriver;
        this.ageDriver = ageDriver;
        this.licenseDriver = licenseDriver;
    }

    public Integer getIdDriver() {
        return idDriver;
    }

    public String getNameDriver() {
        return nameDriver;
    }

    public String getSurnameDriver() {
        return surnameDriver;
    }

    public Date getAgeDriver() {
        return ageDriver;
    }

    public Integer getLicenseDriver() {
        return licenseDriver;
    }
}

