package com.poc.linkedinqapoc.scraper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Accomplishment {
    private String category;
    private String title;
    private Institution institution;
}
