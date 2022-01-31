package self.demo.controller;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.HtmlUtils;
import self.demo.dto.HotelDTO;
import self.demo.model.Hotel;
import self.demo.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.json.Json;
import javax.json.JsonObject;
import java.io.FileReader;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("hotels")
public class HotelController {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private RestTemplate simpleRestTemplate;
    private static final String BASE_URL = "http://cms.local/jsonapi/node/article?filter[title]=";
    private static final ObjectMapper mapper = new ObjectMapper();

    @GetMapping(path="/{name}", produces = "application/json")
    public List<Hotel> getHotels(@PathVariable String name) {
        return findHotelByName(name);
    }
    @PostMapping(path="/{name}", produces = "application/json")
    public HotelDTO populateHotelByFacets(@PathVariable String name) throws Exception {
        return hotelService.findHotelByFacet(name);
    }
    @PostMapping(path="/fetchArticle/{articleTitle}", produces = "application/json")
    public String populateHotelHtml(@PathVariable String articleTitle) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JsonObject request = Json.createObjectBuilder()
                .add("filter", Json.createArrayBuilder().add(Json.createObjectBuilder().add("title",articleTitle)))
                .build();
        HttpEntity<JsonObject> requestEntity = new HttpEntity<JsonObject>(request, headers);
        ResponseEntity<String> responseEntity = simpleRestTemplate.exchange(BASE_URL+articleTitle,HttpMethod.GET,null,String.class);
        Object obj = new JSONParser().parse(responseEntity.getBody());
        JSONObject jo = (JSONObject) obj;
        JSONArray data = (JSONArray) jo.get("data");
        Iterator itr = data.iterator();
        String newHtml = "";
        while (itr.hasNext())
        {
            JSONObject entry = (JSONObject) itr.next();
            JSONObject attributes = (JSONObject)entry.get("attributes");
            JSONObject body = (JSONObject)attributes.get("body");
            String value =  (String)body.get("value");
            newHtml = HtmlUtils.htmlUnescape(value);
            newHtml = newHtml.replace("\\r\\n", "");
            newHtml = newHtml.replace("\\", "");
        }
        return newHtml;
    }

    @PostMapping(path="/qryByFacet", produces = "application/json")
    public List<HotelDTO> getHotelsInFacet(@RequestBody Map<String,String> qryParams) {
        return null;
    }
    private List<Hotel> findHotelByName(String name) {
       return hotelService.findHotelByName(name);
    }
}
