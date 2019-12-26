package com.example.radadownload.model;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DeputyPresence {

    Long id;

    Integer deputyId;

    String name;

    String party;
}
