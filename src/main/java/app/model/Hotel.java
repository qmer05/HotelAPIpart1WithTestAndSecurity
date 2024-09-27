package app.model;

import app.dto.HotelDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "hotel_id")
    private long hotel_id;

    @Column(name = "hotel_name", nullable = false)
    private String hotelName;

    @Column(name = "hotel_address", nullable = false)
    private String hotelAddress;

    @Column(name = "hotel_rooms", nullable = false)
    @OneToMany
    private List<Room> rooms;

    public Hotel(String hotelName, String hotelAddress, List<Room> rooms) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.rooms = rooms;
    }

    public Hotel(HotelDto hotelDto) {
        this.hotelName = hotelDto.getHotelName();
        this.hotelAddress = hotelDto.getHotelAddress();
        this.rooms = hotelDto.getRooms().stream().map(Room::new).toList();
    }
}
