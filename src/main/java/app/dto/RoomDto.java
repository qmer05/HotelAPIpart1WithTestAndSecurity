package app.dto;

import app.model.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomDto {

    @JsonProperty("room_number")
    private int roomNumber;
    @JsonProperty("room_price")
    private int roomPrice;

    public RoomDto(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.roomPrice = room.getRoomPrice();
    }

    public static List<RoomDto> toRoomDTOList(List<Room> rooms) {
        return rooms.stream().map(RoomDto::new).toList();
    }
}
