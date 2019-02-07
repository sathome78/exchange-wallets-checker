package com.example.demo.schedulers.fiatprocessor;

import com.amazonaws.services.simplesystemsmanagement.model.GetParameterRequest;

public interface FiatProcessor {

    void process();

    default GetParameterRequest createParameterRequest(String name) {
        return new GetParameterRequest().withName(name).withWithDecryption(Boolean.TRUE);
    }
}
