package entity.user;

public class User {

    private int id;
    private String name;
    private String email;
    private String phone;

    public User() {

    }

    public User(int id, String name, String email, String phone){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public User(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Getters
    public int getId(){
        return this.id;
    }
    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }


    // Setters
    public User setId (int id){
        this.id = id;
        return this;
    }
    public User setName(String name){
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}