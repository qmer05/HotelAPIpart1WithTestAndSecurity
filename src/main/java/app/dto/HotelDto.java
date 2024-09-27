package app.dto;

import app.model.Hotel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class HotelDto {

    private String hotelName;
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
