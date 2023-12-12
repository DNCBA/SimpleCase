package com.mhc.actor;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Message {

    private String data;
    private Long timeStamp;
}
