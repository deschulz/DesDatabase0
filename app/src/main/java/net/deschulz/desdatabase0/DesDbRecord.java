package net.deschulz.desdatabase0;

/**
 * Created by schulz on 2/15/17.
 */

public class DesDbRecord {
    private long id;
    private String name;
    private String password;

    public void DesDbRecord() {}

    public void setId(long id) {
        this.id = id;
    }
    public long getId() {
        return this.id;
    }

    public void setName(String n) {
        this.name = n;
    }
    public String getName() {
        return this.name;
    }

    public void setPassword(String pw) {
        this.password = pw;
    }
    public String getPassword() {
        return this.password;
    }

    public String dump() {
        return String.format("%d, %s, %s", id, name, password);
    }
}
