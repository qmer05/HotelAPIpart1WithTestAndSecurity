package app.controller;

import app.dao.HotelDao;
import app.dto.HotelDto;
import app.model.Hotel;
import app.exception.ApiException;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class HotelController {

    private final Logger log = LoggerFactory.getLogger(HotelController.class);
    private final HotelDao hotelDao;

    public HotelController(HotelDao hotelDao) {
        this.hotelDao = hotelDao;
    }

    public void getHotelById(Context ctx)  {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));

        // == querying ==
        Hotel hotel = hotelDao.getHotelById(id);

        // == response ==
        HotelDto hotelDto = new HotelDto(hotel);
        ctx.res().setStatus(200);
        ctx.json(hotelDto, HotelDto.class);
    }

    public void getAllHotels(Context ctx) {
        // == querying ==
        List<Hotel> hotels = hotelDao.getAllHotels();

        // == response ==
        List<HotelDto> hotelDtos = HotelDto.toHotelDTOList(hotels);
        ctx.res().setStatus(200);
        ctx.json(hotelDtos, HotelDto.class);
    }

    public void createHotel(Context ctx) {
        try {
            // == request ==
            HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);

            // == querying ==
            Hotel newHotel = new Hotel(hotelDto);
            hotelDao.createHotel(newHotel);

            // == response ==
            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updateHotel(Context ctx) {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));
        HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);

        // == querying ==
        Hotel hotel = hotelDao.getHotelById(id);
        hotelDao.updateHotel(hotel, new Hotel(hotelDto));

        // == response ==
        ctx.res().setStatus(200);
    }

    public void deleteHotel(Context ctx) {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));

        // == querying ==
        Hotel hotel = hotelDao.getHotelById(id);

        // == response ==
        hotelDao.deleteHotel(id);
        ctx.res().setStatus(204);
    }
}
