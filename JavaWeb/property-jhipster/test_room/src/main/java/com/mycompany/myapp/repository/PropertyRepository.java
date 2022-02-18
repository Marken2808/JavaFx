package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Property;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Property entity.
 */
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {
    @Query(
        value = "select distinct property from Property property left join fetch property.rooms",
        countQuery = "select count(distinct property) from Property property"
    )
    Page<Property> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct property from Property property left join fetch property.rooms")
    List<Property> findAllWithEagerRelationships();

    @Query("select property from Property property left join fetch property.rooms where property.id =:id")
    Optional<Property> findOneWithEagerRelationships(@Param("id") Long id);
}
