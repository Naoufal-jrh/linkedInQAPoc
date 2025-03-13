package com.poc.linkedinqapoc.scraper;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Person implements Serializable {
    private String linkedInUrl;
    private String name;
    private String about;
    private List<Experience> experiences;
    private List<Education> education;
    private List<Interest> interests;
    private List<Accomplishment> accomplishments;
    private Company company;
    private String jobTitle;
    private List<Contact> contactList;
}
