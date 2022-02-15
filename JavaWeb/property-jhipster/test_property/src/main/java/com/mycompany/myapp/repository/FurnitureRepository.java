package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Furniture;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Furniture entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FurnitureRepository extends JpaRepository<Furniture, Long> {}
