package com.softserve.teamproject.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "id_group", referencedColumnName = "id", nullable = false)
    private int idGroup;

    @ManyToOne
    @JoinColumn(name = "id_event_type", referencedColumnName = "id", nullable = false)
    private int idEventType;

    @ManyToOne
    @JoinColumn(name = "id_room", referencedColumnName = "id", nullable = false)
    private int idRoom;

    public Event() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdEventType() {
        return idEventType;
    }

    public void setIdEventType(int idEventType) {
        this.idEventType = idEventType;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }
}
