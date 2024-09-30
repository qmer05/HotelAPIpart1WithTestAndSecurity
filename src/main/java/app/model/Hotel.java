package app.model;

import app.dto.HotelDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "hotels")
@NamedQuery(name = "hotel.deleteAllRows", query = "DELETE from Hotel")
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "hotel_address", nullable = false)
    private String hotelAddress;

    @Column(name = "hotel_rooms", nullable = false)
    @OneToMany(mappedBy = "hotel", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Room> rooms = new ArrayList<>();

    public Hotel(HotelDto hotelDto) {
        this.hotelName = hotelDto.getHotelName();
        this.hotelAddress = hotelDto.getHotelAddress();
        this.rooms = hotelDto.getRooms().stream().map(Room::new).toList();
    }

    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
            room.setHotel(this);
        }
    }
}
