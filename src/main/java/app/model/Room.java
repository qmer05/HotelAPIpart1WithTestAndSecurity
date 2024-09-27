package app.model;

import app.dto.RoomDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
