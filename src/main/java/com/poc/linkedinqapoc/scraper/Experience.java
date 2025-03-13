package com.poc.linkedinqapoc.scraper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Experience {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String description;
    private String title;
    private String duration;
    private String location;
    private Institution institution;
}
