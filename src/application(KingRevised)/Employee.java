package application;

public class Employee {

	int id;
	int age;
	double salary;
	String name;
	String email;
	String role;
	String gender;
	public Employee() {
		
	}
	
	@Override
	public String toString() {
		return "Employee [id=" + id + ", age=" + age + ", salary=" + salary + ", name=" + name + ", email=" + email
				+ ", role=" + role + ", gender=" + gender + "]";
	}

	public Employee(int id, String name, String email, int age, double salary,  String role, String gender) {
		this.id = id;
		this.age = age;
		this.salary = salary;
		this.name = name;
		this.email = email;
		this.role = role;
		this.gender = gender;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
}
