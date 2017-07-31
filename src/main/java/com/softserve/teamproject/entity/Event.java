package com.softserve.teamproject.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.softserve.teamproject.entity.deserializer.EventTypeDeserializer;
import com.softserve.teamproject.entity.deserializer.GroupDeserializer;
import com.softserve.teamproject.entity.deserializer.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "datetime")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTime;

    @Column(name = "duration")
    private int duration;

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    @JsonDeserialize(using = GroupDeserializer.class)
    private Group group;

    @ManyToOne
    @JoinColumn(name = "event_type_id", referencedColumnName = "id", nullable = false)
    @JsonDeserialize(using = EventTypeDeserializer.class)
    private EventType eventType;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id")
    private Room Room;

    public Event() {};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public Room getRoom() {
        return Room;
    }

    public void setRoom(Room room) {
        Room = room;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + id +
            ", dateTime=" + dateTime +
            ", duration=" + duration +
            ", group=" + group.getName() +
            ", eventType=" + eventType.getName() +
            '}';
    }
}
