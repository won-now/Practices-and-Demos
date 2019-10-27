package imooc.com.downloaddemo.entities;

import java.io.Serializable;

public class FileInofo implements Serializable {
    private int id;
    private String url;
    private String FileName;
    private long length;
    private long finished;

    public FileInofo() {
    }

    public FileInofo(int id, String url, String fileName, long length, long finished) {
        this.id = id;
        this.url = url;
        FileName = fileName;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getFinished() {
        return finished;
    }

    public void setFinished(long finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileInofo{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", FileName='" + FileName + '\'' +
                ", length=" + length +
                ", finished=" + finished +
                '}';
    }
}
