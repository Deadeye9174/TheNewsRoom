package com.example.company.thenewsroom.Model;

import java.util.List;

import com.example.company.thenewsroom.Model.Source;

/**
 * Created by jatin on 31/10/17.
 */

public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite(String status) {
    }

    public WebSite(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Source> getSources() {
        return sources;
    }

    public void setSources(List<Source> sources) {
        this.sources = sources;
    }
}
