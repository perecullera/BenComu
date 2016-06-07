package pcedev.bencomu.Models;

/**
 * Created by perecullera on 7/6/16.
 */
public class RssItem {

    private String title;

    private String descripcion;

    private String link;

    private String content;



    public RssItem() {
    }
    public RssItem(String title, String descripcion, String link, String content) {
        this.title = title;
        this.descripcion = descripcion;
        this.link = link;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getLink() {
        return link;
    }

    public String getContent() {
        return content;
    }
}
