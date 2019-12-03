package Models;

import javax.xml.crypto.Data;
import java.util.Date;

public class GoddessModel {
    private int id;
    private String user_name;
    private int sex;
    private int age;
    private Date birthday;
    private String email;
    private String mobile;
    private String create_user;//创建人
    private String update_user;//更新人
    private Data create_date;//创建日期
    private Data update_date;//更新日期
    private int isdel;

    public GoddessModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCreate_user() {
        return create_user;
    }

    public void setCreate_user(String create_user) {
        this.create_user = create_user;
    }

    public String getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(String update_user) {
        this.update_user = update_user;
    }

    public Data getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Data create_date) {
        this.create_date = create_date;
    }

    public Data getUpdate_date() {
        return update_date;
    }

    public void setUpdate_date(Data update_date) {
        this.update_date = update_date;
    }

    public int getIsdel() {
        return isdel;
    }

    public void setIsdel(int isdel) {
        this.isdel = isdel;
    }
}
