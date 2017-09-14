package me.kuykendall.restfest.hackday.model;

public class Hyperlink {
    private String url;
    private String label;

    public  Hyperlink() {
    }

    public Hyperlink(String label, String address) {
        this.label = label;
        this.url = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
