package profhunt.pojo;

public class FileToAttach {
  private String name;
  private String renameTo;

    @Override
    public String toString() {
        return "FileToAttach{" +
                "name='" + name + '\'' +
                ", renameTo='" + renameTo + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRenameTo() {
        return renameTo;
    }

    public void setRenameTo(String renameTo) {
        this.renameTo = renameTo;
    }
}
