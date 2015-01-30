package wairoadc.godiscover.view.models;

/**
 * Created by Lucas on 30/01/2015.
 */
public class ProgressModel {
    private String name;
    private int max;
    private int status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
