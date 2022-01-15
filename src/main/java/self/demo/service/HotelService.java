package self.demo.service;

import self.demo.dto.HotelDTO;
import self.demo.model.Hotel;
import java.util.List;

public interface HotelService {
    List<Hotel> findHotelByName(String name);
    HotelDTO findHotelByFacet(String name) throws Exception;
}
