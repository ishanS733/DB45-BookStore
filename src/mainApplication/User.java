package mainApplication;

import controllers.userLoginController;

import java.io.*;

public class User implements Serializable {
    int u_id, age;
    String username, password, email_id, contact_no, sex, card_num;
    User()
    {

    }

    public User(int u_id, String username, String password, String email_id,String contact_no, int age, String sex){
        this.username=username;
        this.age = age;
        this.sex=sex;
        this.password=password;
        this.u_id=u_id;
        this.email_id=email_id;
        this.contact_no=contact_no;
    }

    public User copy(){
        User dup = new User();
        dup.username=this.username;
        dup.age=this.age;
        dup.contact_no=this.contact_no;
        dup.email_id=this.email_id;
        dup.password=this.password;
        dup.sex=this.sex;
        dup.u_id=this.u_id;
        return dup;
    }

    public void serialize() throws IOException {
        FileOutputStream file=null;
        ObjectOutputStream out=null;
        try{
            file = new FileOutputStream("src/mainApplication/currUser");
            out = new ObjectOutputStream(file);
            out.writeObject(this);
        }catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(out!=null){
                out.close();
            }
            if(file!=null){
                file.close();
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", age=" + age +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email_id='" + email_id + '\'' +
                ", contact_no='" + contact_no + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public static User deserialize() throws IOException {
        FileInputStream file=null;
        ObjectInputStream out=null;
        User u=null;
        try{
            file = new FileInputStream("src/mainApplication/currUser");
            out = new ObjectInputStream(file);
            u = (User) out.readObject();
            userLoginController.currUser=u;
            return u;
        }catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally{
            if(out!=null){
                out.close();
            }
            if(file!=null){
                file.close();
            }
        }
        return u;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getContact_no() {
        return contact_no;
    }

    public void setContact_no(String contact_no) {
        this.contact_no = contact_no;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
