package com.lucasmartins.api.service.impl;

import com.lucasmartins.api.service.ExampleService;
import com.lucasmartins.api.service.pattern.AbstractService;
import com.lucasmartins.common.model.entity.Example;
import org.springframework.stereotype.Service;

@Service
public class ExampleServiceImpl extends AbstractService<Example, Integer> implements ExampleService {
}
