package com.ryanalexander.minipro.controller;

import com.ryanalexander.minipro.dao.EDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EController {
    @Autowired
    private EDao eDao;


}