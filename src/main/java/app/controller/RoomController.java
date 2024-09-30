package app.controller;

import app.dao.HotelDao;
import app.dto.RoomDto;
import app.exception.ApiException;
import app.model.Room;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RoomController {

    private final Logger log = LoggerFactory.getLogger(RoomController.class);
    private final HotelDao hotelDao;

    public RoomController(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public void getAllRooms(Context ctx) {
        try {
            // == request ==
            List<Room> rooms = hotelDao.getAllRooms();

            // == querying ==
            List<RoomDto> roomDtos = RoomDto.toRoomDTOList(rooms);

            // == response ==
            ctx.res().setStatus(200);
            ctx.json(roomDtos, RoomDto.class);
        } catch (Exception e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }
}
