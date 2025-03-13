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
public class Education {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String description;
    private String location;
    private Institution institution;
}
