package self.demo.repository;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.UriTemplate;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Repository;
import self.demo.controller.HotelController;
import self.demo.dto.HotelDTO;
import self.demo.dto.HotelFacetDTO;
import self.demo.model.Hotel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.apache.http.client.utils.URIBuilder;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
@Repository("hotelFacetedRepository")
public class HotelFacetedRepository {
    @Autowired
    private SolrTemplate solrTemplate;
    public List<HotelDTO> findHotelsByFacet(String facetField,Map<String,String> queryParam){
        FacetOptions options = new FacetOptions();
        options.addFacetOnField(new FacetOptions.FieldWithFacetParameters(facetField));
        return null;
    }
    public HotelDTO findHotelsByParam(String hotelName) throws Exception{
        FacetOptions options = new FacetOptions();
        options.addFacetOnField(new FacetOptions.FieldWithFacetParameters("country").setLimit(5));
        options.addFacetOnField(new FacetOptions.FieldWithFacetParameters("market_segment").setLimit(5));
        options.setFacetMinCount(1);
        String queryRegex = "hotel:#1&wt=json";
        queryRegex = queryRegex.replace("#1",hotelName);
        Criteria criteria = new SimpleStringCriteria(queryRegex);
        SimpleFacetQuery facetQuery = new SimpleFacetQuery(criteria).setFacetOptions(options);
        FacetPage<Hotel> results = solrTemplate.queryForFacetPage("hotel_core", facetQuery, Hotel.class);
        List<Hotel> hotels = results.get().collect(Collectors.toList());
        Map<Field,List<HotelFacetDTO>> facetDtls = new HashMap<Field,List<HotelFacetDTO>>();
        Collection<Field> fields = results.getFacetFields();
        final List<String> facetFldLst = new ArrayList<String>();
        fields.stream().forEach(fld -> {
            facetFldLst.add(fld.getName());
        });
        String facetJsonStr = new Gson().toJson(facetFldLst);
        for (Field field : fields) {
            List<HotelFacetDTO> facets = new ArrayList<HotelFacetDTO>();
            for (FacetEntry facetEntry : results.getFacetResultPage(field)) {
                HotelFacetDTO facet = new HotelFacetDTO();
                facets.add(facet);
                facet.setFacetName(facetEntry.getValue());
                facet.setFacetValue((int) facetEntry.getValueCount());
                Method method = HotelController.class.getMethod("getHotelsInFacet", Map.class);
                WebMvcLinkBuilder link = linkTo(HotelController.class,method);
                Link fcLink = link.withRel("qryByFacet");
                URIBuilder uriBuilder = new URIBuilder(fcLink.toUri().toString());
                uriBuilder.addParameter("facet",facetJsonStr);
                uriBuilder.addParameter("srchField", "hotel");
                uriBuilder.addParameter("srchFieldValue", hotelName);
                uriBuilder.addParameter("srchFacetFieldValue", facetEntry.getValue());
                facet.setFacetLink(uriBuilder.build().toURL().toString());
            }
            facetDtls.put(field,facets);
         }
        HotelDTO dto =  new HotelDTO();
        dto.setHotel(hotels);
        dto.setFacetMap(facetDtls);
        return dto;
    }

}
