package app.entities;

import app.dtos.RoomDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "rooms")
@NamedQuery(name = "room.deleteAllRows", query = "DELETE from Room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "room_number", nullable = false)
    private int roomNumber;

    @Column(name = "room_price", nullable = false)
    private int roomPrice;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    public Room(RoomDto roomDto) {
        this.roomNumber = roomDto.getRoomNumber();
        this.roomPrice = roomDto.getRoomPrice();
    }

    public Room(int roomNumber, int roomPrice) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", roomPrice=" + roomPrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id &&
                roomNumber == room.roomNumber &&
                roomPrice == room.roomPrice;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, roomPrice);
    }
}
