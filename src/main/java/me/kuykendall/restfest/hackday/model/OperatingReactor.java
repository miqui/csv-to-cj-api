package me.kuykendall.restfest.hackday.model;

public class OperatingReactor {
    private String plantName;
    private Hyperlink webPage;
    private String docketNumber;

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Hyperlink getWebPage() {
        return webPage;
    }

    public void setWebPage(Hyperlink webPage) {
        this.webPage = webPage;
    }

    public String getDocketNumber() {
        return docketNumber;
    }

    public void setDocketNumber(String docketNumber) {
        this.docketNumber = docketNumber;
    }
}
