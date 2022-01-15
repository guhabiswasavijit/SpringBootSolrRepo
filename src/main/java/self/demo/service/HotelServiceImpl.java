package self.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import self.demo.dto.HotelDTO;
import self.demo.model.Hotel;
import self.demo.repository.HotelFacetedRepository;
import self.demo.repository.HotelRepository;

import java.util.List;
@Service("hotelService")
public class HotelServiceImpl implements HotelService{
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelFacetedRepository hotelFacetedRepository;

    @Override
    public List<Hotel> findHotelByName(String name) {
        return hotelRepository.findByName(name);
    }
    @Override
    public HotelDTO findHotelByFacet(String name) throws Exception{
        return hotelFacetedRepository.findHotelsByParam(name);
    }
}
