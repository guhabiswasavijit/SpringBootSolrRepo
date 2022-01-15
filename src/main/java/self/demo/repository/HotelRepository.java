package self.demo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import self.demo.model.Hotel;
import java.util.List;
import java.util.Optional;

@Repository("hotelRepository")
public interface HotelRepository extends SolrCrudRepository<Hotel, String> {

    public Optional<Hotel> findById(String id);
    @Query("hotel:*?0*&wt=json")
    public List<Hotel> findByName(String name);

    @Query("id:*?0* OR name:*?0*")
    public Page<Hotel> findByCustomQuery(String searchTerm, Pageable pageable);

    @Query(name = "Product.findByNamedQuery")
    public Page<Hotel> findByNamedQuery(String searchTerm, Pageable pageable);

}
