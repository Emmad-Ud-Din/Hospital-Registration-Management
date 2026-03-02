public abstract class Person {
    protected String name;
    protected int age;
    private static int personCounter = 0;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        personCounter++;
    }

    public static int getPersonCount() {
        return personCounter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return "Name: " + name + "\n" +
               "Age: " + age + "\n" +
               "Role: " + getRole();
    }
}
