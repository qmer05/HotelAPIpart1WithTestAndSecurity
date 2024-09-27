package app.dto;

import app.model.Hotel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@JsonPropertyOrder({ "hotelName", "hotelAddress", "rooms" })
public class HotelDto {

    @JsonProperty("hotel_name")
    private String hotelName;
    @JsonProperty("hotel_address")
    private String hotelAddress;
    private List<RoomDto> rooms;

    public HotelDto(Hotel hotel) {
        this.hotelName = hotel.getHotelName();
        this.hotelAddress = hotel.getHotelAddress();
        this.rooms = hotel.getRooms().stream().map(RoomDto::new).toList();
    }

    public static List<HotelDto> toHotelDTOList(List<Hotel> hotels) {
        return hotels.stream().map(HotelDto::new).toList();
    }
}
