package com.poc.linkedinqapoc.scraper.models;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person implements Serializable {
    private String linkedInUrl;
    private String firstName;
    private String lastName;
    private String about;
    private String headLine;
    private String currentCompany;
    private String currentSchool;
    private String currentLocation;
    private String email;
    private String joinedDate;

    private List<Experience> experiences;
    private List<Education> educations;
    private List<String> selfAcquiredSkills;
}
