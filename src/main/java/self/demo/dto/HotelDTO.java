package self.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.solr.core.query.Field;
import self.demo.model.Hotel;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
public class HotelDTO implements Serializable {
    private List<Hotel> hotel;
    private Map<Field,List<HotelFacetDTO>> facetMap;
}
