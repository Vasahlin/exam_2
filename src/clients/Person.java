package clients;

import java.io.Serializable;
import java.util.HashMap;

public class Person implements Serializable {
    String name;
    Long socialSecurity;
    final static HashMap<Long, Long> fileIndex = new HashMap<>();

    public Person(String name, long socialSecurity) {
        this.name = name;
        this.socialSecurity = socialSecurity;
        fileIndex.put(this.socialSecurity, -1L);
    }

    public void setFileIndex(Long index) {
        fileIndex.put(this.socialSecurity, index);
    }

    public long getFileIndex() {
        return fileIndex.get(this.socialSecurity);
    }

    public String getName() {
        return name;
    }

    public long getSocialSecurity() {
        return socialSecurity;
    }
}
