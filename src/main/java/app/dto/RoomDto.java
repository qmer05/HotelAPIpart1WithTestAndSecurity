package app.dto;

import app.model.Room;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class RoomDto {

    private int roomNumber;
    private int roomPrice;

    public RoomDto(Room room) {
        this.roomNumber = room.getRoomNumber();
        this.roomPrice = room.getRoomPrice();
    }

    public static List<RoomDto> toRoomDTOList(List<Room> rooms) {
        return rooms.stream().map(RoomDto::new).toList();
    }
}
