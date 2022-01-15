package self.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode
public class HotelFacetDTO implements Serializable {
    private String facetName;
    private Integer facetValue;
    private String facetLink;
}
