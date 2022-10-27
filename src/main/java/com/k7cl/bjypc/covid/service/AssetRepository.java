package com.k7cl.bjypc.covid.service;

import com.k7cl.bjypc.covid.bean.Asset;
import com.k7cl.bjypc.covid.bean.Classes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AssetRepository extends JpaRepository<Asset, Long> {
    List<Asset> findByClasses(Classes classes);
    List<Asset> findByNameContaining(String keyword);
    List<Asset> findByImportTimeBetween(Timestamp start, Timestamp end);
    List<Asset> findByImportTimeBetweenAndClassesAndNameContaining(Timestamp start, Timestamp end, Classes classes, String name);
    Asset findById(int id);
    boolean existsById(int id);
    @Transactional
    int deleteById(int id);
}
