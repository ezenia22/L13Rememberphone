package sg.edu.rp.c346.id20011155.rememberphone;

public class Phone {

    private int id;
    private String name;
    private String numbers;

    public Phone(int id, String name, String numbers) {
        this.id = id;
        this.name = name;
        this.numbers = numbers;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
