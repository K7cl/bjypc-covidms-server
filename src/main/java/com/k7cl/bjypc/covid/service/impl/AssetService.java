package com.k7cl.bjypc.covid.service.impl;

import com.k7cl.bjypc.covid.bean.Asset;
import com.k7cl.bjypc.covid.bean.Classes;
import com.k7cl.bjypc.covid.service.AssetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class AssetService {
    @Autowired
    private AssetRepository assetRepository;

    public List<Asset> searchName(String name) {
        return assetRepository.findByNameContaining(name);
    }
    public Asset findById(int id) {
        return assetRepository.findById(id);
    }

    public boolean isExists(int cid) {
        return assetRepository.existsById(cid);
    }

    public void save(Asset u) {
        assetRepository.save(u);
    }

    public void delete(Asset u) {
        assetRepository.delete(u);
    }

    public boolean delete(int id) {
        return assetRepository.deleteById(id) == 1;
    }

    public Page<Asset> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        return assetRepository.findAll(pageable);
    }

    public List<Asset> findByClass(Classes classes) {
        return assetRepository.findByClasses(classes);
    }

    public List<Asset> searchOr(Timestamp start, Timestamp stop, Classes classes, String name) {
        List<Asset> result = new ArrayList<>();
        if (name != null && !name.equals(""))
            result.addAll(assetRepository.findByNameContaining(name));
        if (classes != null)
            result.addAll(assetRepository.findByClasses(classes));
        result.addAll(assetRepository.findByImportTimeBetween(start, stop));
        result.remove(null);
        List<Integer> resultIds = new ArrayList<>();
        List<Asset> resultNew = new ArrayList<>();
        for (Asset asset : result) {
            if (!resultIds.contains(asset.getId())) {
                resultNew.add(asset);
                resultIds.add(asset.getId());
            }
        }
        return resultNew;
    }

    public List<Asset> searchAnd(Timestamp start, Timestamp stop, Classes classes, String name) {
        if (classes == null) {
            if(start == null && stop == null) {
                return assetRepository.findByNameContaining(name);
            }
            return assetRepository.findByImportTimeBetweenAndNameContaining(start, stop, name);
        }
        if(start == null && stop == null) {
            return assetRepository.findByClassesAndNameContaining(classes, name);
        }
        return assetRepository.findByImportTimeBetweenAndClassesAndNameContaining(start, stop, classes, name);
    }
}
