package com.example.radadownload.model;

import lombok.Builder;
import lombok.Value;

import java.io.InputStream;
import java.net.URL;

@Builder
@Value
public class DeputyCard {

    Integer id;

    String fullName;

    String electedIn;

    String electedInOut;

    String party;

    String post;

    String birthday;

    String about;

    String presentElec;

    String absentElec;

    String present;

    String wasIll;

    String vacation;

    String trip;

    String wasAbsent;

    String image;

    String shortName;
}
