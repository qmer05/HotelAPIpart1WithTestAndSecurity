package app.controllers;

import app.daos.HotelDao;
import app.dtos.HotelDto;
import app.entities.Hotel;
import app.exceptions.ApiException;
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

    public void getHotelById(Context ctx) {
        try {
            // == request ==
            long id = Long.parseLong(ctx.pathParam("id"));

            // == querying ==
            Hotel hotel = hotelDao.getById(id);

            // == response ==
            HotelDto hotelDto = new HotelDto(hotel);
            ctx.res().setStatus(200);
            ctx.json(hotelDto, HotelDto.class);
        } catch (Exception e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void getAllHotels(Context ctx) {
        try {
            // == querying ==
            List<Hotel> hotels = hotelDao.getAll();

            // == response ==
            List<HotelDto> hotelDtos = HotelDto.toHotelDTOList(hotels);
            ctx.res().setStatus(200);
            ctx.json(hotelDtos, HotelDto.class);
        } catch (Exception e) {
            log.error("404 {}", e.getMessage());
            throw new ApiException(404, e.getMessage());
        }
    }

    public void createHotel(Context ctx) {
        try {
            // == request ==
            HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);

            // == querying ==
            Hotel newHotel = new Hotel(hotelDto);
            hotelDao.create(newHotel);

            // == response ==
            ctx.json(hotelDto, HotelDto.class);
            ctx.res().setStatus(201);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void updateHotel(Context ctx) {
        try {
            // == request ==
            long id = Long.parseLong(ctx.pathParam("id"));
            HotelDto hotelDto = ctx.bodyAsClass(HotelDto.class);

            // == querying ==
            Hotel hotel = hotelDao.getById(id);
            hotelDao.update(hotel, new Hotel(hotelDto));

            // == response ==
            ctx.json(hotelDto, HotelDto.class);
            ctx.res().setStatus(200);
        } catch (Exception e) {
            log.error("400 {}", e.getMessage());
            throw new ApiException(400, e.getMessage());
        }
    }

    public void deleteHotel(Context ctx) {
        // == request ==
        long id = Long.parseLong(ctx.pathParam("id"));

        // == querying ==
        Hotel hotel = hotelDao.getById(id);

        // == response ==
        hotelDao.delete(id);
        ctx.res().setStatus(204);
    }
}
