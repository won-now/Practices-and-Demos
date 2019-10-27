package demo.com.download.utils;

public class FileUtil {
    private String _id;
    private String name;
    private String url;
    private int fileSize;
    private int finished;

    public FileUtil() {
    }

    public FileUtil(String _id, String name, String url, int fileSize, int finished) {
        this._id = _id;
        this.name = name;
        this.url = url;
        this.fileSize = fileSize;
        this.finished = finished;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "FileUtil{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", fileSize=" + fileSize +
                ", finished=" + finished +
                '}';
    }
}
