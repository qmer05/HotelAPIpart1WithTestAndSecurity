package app.entities;

import app.dtos.HotelDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Hotel(String hotelName, String hotelAddress, List<Room> rooms) {
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        if (room != null) {
            rooms.add(room);
            room.setHotel(this);
        }
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", hotelName='" + hotelName + '\'' +
                ", hotelAddress='" + hotelAddress + '\'' +
                ", rooms=" + rooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id == hotel.id &&
                hotelName.equals(hotel.hotelName) &&
                hotelAddress.equals(hotel.hotelAddress) &&
                rooms.containsAll(hotel.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotelName, hotelAddress, rooms);
    }
}
