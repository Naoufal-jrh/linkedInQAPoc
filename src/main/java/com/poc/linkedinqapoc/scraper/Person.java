package com.poc.linkedinqapoc.scraper;

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
    private String headLine;
    private String about;
    private String location;
    private List<Experience> experiences;
    private List<Education> education;
    private List<Interest> interests;
    private List<Accomplishment> accomplishments;
    private String company;
    private String jobTitle;
    private List<Contact> contactList;
}
