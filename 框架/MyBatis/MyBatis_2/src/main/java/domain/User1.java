package domain;

public class User1 {
    private String professional;

    @Override
    public String toString() {
        return "User1{" +
                "professional='" + professional + '\'' +
                '}';
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }
}
