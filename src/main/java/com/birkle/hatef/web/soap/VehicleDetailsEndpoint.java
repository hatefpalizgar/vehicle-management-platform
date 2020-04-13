package com.birkle.hatef.web.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;


@Endpoint
public class VehicleDetailsEndpoint {
    private static final String NAMESPACE_URI = "http://soap.web.hatef.birkle.com";

    @Autowired
    SoapService soapService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "postVehiclesRequest")
    @ResponsePayload
    public PostVehiclesResponse processVehicleDetailsRequest(@RequestPayload PostVehiclesRequest request) {
        PostVehiclesResponse response = new PostVehiclesResponse();
        response.vehicleDetails = request.getRecord();
        soapService.persist(response.getVehicleDetails());
        return response;
    }
}
