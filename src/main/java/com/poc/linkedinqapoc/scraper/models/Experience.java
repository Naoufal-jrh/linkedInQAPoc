package com.poc.linkedinqapoc.scraper.models;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Experience {
    private String fromDate;
    private String toDate;
    private String description;
    private String title;
    private String location;
    private String company;
    private String employmentType;
    private String workMode;
    private String duration;
}
