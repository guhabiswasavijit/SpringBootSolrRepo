package self.demo.controller;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.demo.dto.HotelAutofillParams;
import self.demo.dto.Hotels;
import self.demo.model.Hotel;
import self.demo.mybatis.HotelMapper;

import java.util.List;

@RestController
@RequestMapping("hotelsAutoFill")
public class HotelAutoFillController {

    @Autowired
    private HotelMapper hotelMapper;

    @PostMapping(path="/fetchHotels", produces = "application/json")
    public List<Hotels> getHotels(@RequestBody HotelAutofillParams params) {

        return hotelMapper.srchHotels(params.getLat(),params.getLng(), params.getInAndAroundRadius());
    }
    @GetMapping(path="/fetchAllHotelsWithStartingName/{startingName}", produces = "application/json")
    public List<Hotels> fetchAllHotels(@PathVariable String startingName) {

        return hotelMapper.fetchAllHotels(startingName+"%");
    }
}
