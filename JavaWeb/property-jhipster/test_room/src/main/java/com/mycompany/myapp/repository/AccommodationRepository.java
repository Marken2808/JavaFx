package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Accommodation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Accommodation entity.
 */
@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {
    @Query(
        value = "select distinct accommodation from Accommodation accommodation left join fetch accommodation.rooms",
        countQuery = "select count(distinct accommodation) from Accommodation accommodation"
    )
    Page<Accommodation> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct accommodation from Accommodation accommodation left join fetch accommodation.rooms")
    List<Accommodation> findAllWithEagerRelationships();

    @Query("select accommodation from Accommodation accommodation left join fetch accommodation.rooms where accommodation.id =:id")
    Optional<Accommodation> findOneWithEagerRelationships(@Param("id") Long id);
}
