package com.ll.exam.fileUpload1205.app.fileUpload.repository;

import com.ll.exam.fileUpload1205.app.fileUpload.entity.GenFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenFileRepository extends JpaRepository<GenFile, Long> {
    List<GenFile> findByRelTypeCodeAndRelId(String relTypeCode, Long id);
}
