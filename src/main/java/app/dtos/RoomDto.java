package app.dtos;

import app.entities.Room;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@NoArgsConstructor
public class RoomDto {

    private Long id;
    @JsonProperty("room_number")
    private int roomNumber;
    @JsonProperty("room_price")
    private int roomPrice;

    public RoomDto(Room room) {
        this.id = room.getId();
        this.roomNumber = room.getRoomNumber();
        this.roomPrice = room.getRoomPrice();
    }

    public RoomDto(int roomNumber, int roomPrice) {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
    }

    public static List<RoomDto> toRoomDTOList(List<Room> rooms) {
        return rooms.stream().map(RoomDto::new).toList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDto roomDto = (RoomDto) o;
        return roomNumber == roomDto.roomNumber &&
                Double.compare(roomDto.roomPrice, roomPrice) == 0 &&
                Objects.equals(id, roomDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, roomPrice);
    }

}
