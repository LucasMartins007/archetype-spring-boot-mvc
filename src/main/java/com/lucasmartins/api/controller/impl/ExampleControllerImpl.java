package com.lucasmartins.api.controller.impl;

import com.lucasmartins.api.controller.ExampleController;
import com.lucasmartins.api.controller.pattern.AbstractController;
import com.lucasmartins.api.service.ExampleService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleControllerImpl extends AbstractController<ExampleService> implements ExampleController {

    @Override
    public String example() {
        return "Example";
    }
}
