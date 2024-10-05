package app.dtos;

import app.entities.Hotel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({"id", "hotelName", "hotelAddress", "rooms"})
public class HotelDto {

    private Long id;
    @JsonProperty("hotel_name")
    private String hotelName;
    @JsonProperty("hotel_address")
    private String hotelAddress;
    private List<RoomDto> rooms;

    public HotelDto(Hotel hotel) {
        this.id = hotel.getId();
        this.hotelName = hotel.getHotelName();
        this.hotelAddress = hotel.getHotelAddress();
        this.rooms = hotel.getRooms().stream().map(RoomDto::new).toList();
    }

    public HotelDto(String hotelName, String hotelAddress, List<RoomDto> rooms) {
        this.id = 0L;
        this.hotelName = hotelName;
        this.hotelAddress = hotelAddress;
        this.rooms = rooms;
    }

    public static List<HotelDto> toHotelDTOList(List<Hotel> hotels) {
        return hotels.stream().map(HotelDto::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelDto hotelDto = (HotelDto) o;
        return Objects.equals(id, hotelDto.id) &&
                Objects.equals(hotelName, hotelDto.hotelName) &&
                Objects.equals(hotelAddress, hotelDto.hotelAddress) &&
                rooms.containsAll(hotelDto.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, hotelName, hotelAddress, rooms);
    }

}
