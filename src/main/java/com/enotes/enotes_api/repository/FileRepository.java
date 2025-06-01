package com.enotes.enotes_api.repository;

import com.enotes.enotes_api.entity.FileDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileDetails, Integer> {
}
