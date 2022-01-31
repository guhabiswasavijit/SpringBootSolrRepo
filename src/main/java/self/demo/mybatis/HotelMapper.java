package self.demo.mybatis;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import self.demo.dto.Hotels;

import java.util.List;

@Mapper
public interface HotelMapper {
    @Select("SELECT name,title, (3959 * acos(cos(radians(#{lat})) * cos(radians(lat)) * cos(radians(lng) - radians(#{lng})) + sin(radians(#{lat})) * sin(radians(lat )))) AS distance FROM markers" +
            " HAVING distance < #{inAndAroundRadius} ORDER BY distance LIMIT 0, 20")
    List<Hotels> srchHotels(@Param("lat") Double lat, @Param("lng") Double lng, @Param("inAndAroundRadius") Integer inAndAroundRadius);
    @Select("SELECT name,title FROM markers WHERE title like #{startingName}")
    List<Hotels> fetchAllHotels(@Param("startingName") String startingName);
}
