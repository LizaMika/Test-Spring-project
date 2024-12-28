package com.example.demo.device.mapper;

import com.example.demo.device.domain.Device;
import com.example.demo.device.dto.DeviceRequest;
import com.example.demo.device.dto.DeviceResponse;
import com.example.demo.user.domain.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-12-21T16:00:12+0300",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.13 (Amazon.com Inc.)"
)
@Component
public class DeviceMapperImpl implements DeviceMapper {

    @Override
    public Device toDevice(DeviceRequest deviceRequest, User user) {
        if ( deviceRequest == null && user == null ) {
            return null;
        }

        Device.DeviceBuilder device = Device.builder();

        if ( deviceRequest != null ) {
            device.name( deviceRequest.getDeviceName() );
            device.macAddress( deviceRequest.getMacAddress() );
        }
        device.user( user );

        return device.build();
    }

    @Override
    public DeviceResponse toDeviceResponse(Device device) {
        if ( device == null ) {
            return null;
        }

        DeviceResponse.DeviceResponseBuilder deviceResponse = DeviceResponse.builder();

        deviceResponse.name( device.getName() );
        deviceResponse.macAddress( device.getMacAddress() );
        deviceResponse.user( toUserResponse( device.getUser() ) );

        return deviceResponse.build();
    }
}
