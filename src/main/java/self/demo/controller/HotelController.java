package self.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import self.demo.dto.HotelDTO;
import self.demo.model.Hotel;
import self.demo.service.HotelService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping(path="/{name}", produces = "application/json")
    public List<Hotel> getHotels(@PathVariable String name) {
        return findHotelByName(name);
    }
    @PostMapping(path="/{name}", produces = "application/json")
    public HotelDTO populateHotelByFacets(@PathVariable String name) throws Exception {
        return hotelService.findHotelByFacet(name);
    }
    @PostMapping(path="/qryByFacet", produces = "application/json")
    public List<HotelDTO> getHotelsInFacet(@RequestBody Map<String,String> qryParams) {
        return null;
    }
    private List<Hotel> findHotelByName(String name) {
       return hotelService.findHotelByName(name);
    }
}
