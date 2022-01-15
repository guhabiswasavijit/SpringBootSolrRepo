package self.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@SolrDocument(solrCoreName = "hotel_core")
@Getter
@Setter
@EqualsAndHashCode
public class Hotel {

    @Id
    @Indexed(name = "id", type = "string")
    private String id;
    private String hotel;
    private String description;
    private String image;
    private String arrival_date_year;
    private String arrival_date_month;
    private String meal;
    private String country;
    private String market_segment;
    private String distribution_channel;
    private Double adr;
    private String reservation_status;
    private Date reservation_status_date;

}
