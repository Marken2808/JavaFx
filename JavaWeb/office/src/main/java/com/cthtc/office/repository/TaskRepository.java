package com.cthtc.office.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cthtc.office.entity.TaskEntity;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long>{

	List<TaskEntity> findAllByTitleContaining(String title);

	List<TaskEntity> findAllByUserId(Long id);

}
