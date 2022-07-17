package com.lucasmartins.api.controller;

import io.swagger.annotations.Api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@Api(tags = ExampleController.TAG)
@Tag(name = ExampleController.TAG, description = ExampleController.DESCRIPTION)
public interface ExampleController {

    String TAG = "Example";

    String DESCRIPTION = "Description";

    @GetMapping
    String example();
}
