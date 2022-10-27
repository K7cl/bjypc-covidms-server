package com.k7cl.bjypc.covid.controller;

import com.k7cl.bjypc.covid.bean.Asset;
import com.k7cl.bjypc.covid.bean.Classes;
import com.k7cl.bjypc.covid.entity.AssetEdit;
import com.k7cl.bjypc.covid.entity.ClassEdit;
import com.k7cl.bjypc.covid.entity.Response;
import com.k7cl.bjypc.covid.service.impl.AssetService;
import com.k7cl.bjypc.covid.service.impl.ClassesService;
import com.k7cl.bjypc.covid.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/asset")
public class AssetController {
    @Autowired
    UserService userService;
    @Autowired
    ClassesService classesService;
    @Autowired
    AssetService assetService;

    @GetMapping("/list")
    public Object get(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "10") int limit) {
        try {
            if (limit>0 && limit<50) {
                return new Response(true, null, assetService.findAll(page, limit).get());
            } else {
                return new Response(false, "Limit value invalid!", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request){
        binder.setDisallowedFields("id", "admin", "classes");
    }

    @GetMapping("/search")
    public Object search(@RequestParam(value = "keyword", defaultValue = "") String keyword, @RequestParam(value = "startTime", defaultValue = "0") long startTime, @RequestParam(value = "endTime", defaultValue = "0") long endTime, @RequestParam(value = "classId", defaultValue = "0") int cid) {
        try {
            if (cid != 0 && !classesService.isExists(cid)) {
                return new Response(false, "Class not exist!", null);
            }
            if (cid == 0)
                return new Response(true, null, assetService.searchAnd(new Timestamp(startTime), new Timestamp(endTime), null, keyword));
            return new Response(true, null, assetService.searchAnd(new Timestamp(startTime), new Timestamp(endTime), classesService.findById(cid), keyword));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @GetMapping("/class")
    public Object assetClass(@RequestParam(value = "classId") int cid) {
        try {
            if (!classesService.isExists(cid)) {
                return new Response(false, "Class not exist!", null);
            }
            return new Response(true, null, assetService.findByClass(classesService.findById(cid)));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @PostMapping
    public Object add(@RequestBody AssetEdit reqAsset) {
        try {
            if (!userService.isExists(reqAsset.sid())) {
                return new Response(false, "Admin user not exist!", null);
            }
            if (reqAsset.cid() != 0 && !classesService.isExists(reqAsset.cid())) {
                return new Response(false, "Class not exist!", null);
            }
            buildEntity(reqAsset, new Asset());
            return new Response(true, null, null);
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    @PutMapping
    public Object edit(@RequestBody AssetEdit reqAsset) {
        try {
            if (!assetService.isExists(reqAsset.aid())) {
                return new Response(false, "Asset not exist!", null);
            }
            if (!userService.isExists(reqAsset.sid())) {
                return new Response(false, "Admin user not exist!", null);
            }
            if (reqAsset.cid() != 0 && !classesService.isExists(reqAsset.cid())) {
                return new Response(false, "Class not exist!", null);
            }
            buildEntity(reqAsset, assetService.findById(reqAsset.aid()));
            return new Response(true, null, assetService.findById(reqAsset.aid()));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }

    private void buildEntity(@RequestBody AssetEdit reqAsset, Asset asset) {
        asset.setName(reqAsset.name());
        asset.setCount(reqAsset.count());
        asset.setImportTime(reqAsset.importTime());
        asset.setAdmin(userService.findByStudentId(reqAsset.sid()));
        if (reqAsset.cid() != 0) {
            asset.setClasses(classesService.findById(reqAsset.cid()));
        } else {
            asset.setClasses(null);
        }
        assetService.save(asset);
    }

    @DeleteMapping("/{aid}")
    public Object my(@PathVariable int aid) {
        try {
            if (assetService.delete(aid)) {
                return new Response(true, null, null);
            } else {
                return new Response(false, null, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Response(false, e.getMessage(), null);
        }
    }
}
