package self.demo.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode
@Getter
@Setter
public class HotelAutofillParams {
    private Double lat;
    private Double lng;
    private Integer inAndAroundRadius;
}
