package com.poc.linkedinqapoc.scraper.models;

import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Education {
    private String degree;
    private String major;
    private String fromDate;
    private String toDate;
    private String description;
    private String location;
    private String institution;

    private List<String> acquiredSkills;
}
