package com.mhc.java8;

public class Ticket {
    private Integer id;
    private Boolean active;
    private String info;

    public Ticket() {

    }

    public Ticket(Integer id, Boolean active, String info) {
        this.id = id;
        this.active = active;
        this.info = info;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", active=" + active +
                ", info='" + info + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

}
