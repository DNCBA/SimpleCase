package com.mhc.actor;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class Message implements Serializable {

    private String data;
    private Long timeStamp;
}
