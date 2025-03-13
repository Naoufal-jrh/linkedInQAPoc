package com.poc.linkedinqapoc.scraper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Institution {
    private String institutionName;
    private String institutionUrl;
    private String website;
    private String industry;
    private String type;
    private String headquarters;
    private String companySize;
    public Integer founded;
}
