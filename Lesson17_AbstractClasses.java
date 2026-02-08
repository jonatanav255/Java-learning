/*
 * LESSON 17: ABSTRACT CLASSES
 *
 * An abstract class is a class that cannot be instantiated (you can't create objects of it).
 * It serves as a template for other classes.
 *
 * KEY CONCEPTS:
 * - Abstract Class: Declared with 'abstract' keyword, cannot be instantiated
 * - Abstract Method: Method without implementation (body), must be overridden
 * - Concrete Method: Regular method with implementation
 * - Subclass must implement all abstract methods (or be abstract itself)
 *
 * WHEN TO USE ABSTRACT CLASSES:
 * - When you want to share code among closely related classes
 * - When you want to declare non-public members
 * - When you want to define some behavior but force subclasses to provide others
 * - When classes share common state (fields)
 *
 * ABSTRACT CLASS vs INTERFACE:
 * Abstract Class:
 * - Can have both abstract and concrete methods
 * - Can have instance variables (state)
 * - Can have constructors
 * - Single inheritance (extends one class)
 * - Can have any access modifiers
 *
 * Interface (next lesson):
 * - All methods are abstract (before Java 8)
 * - No instance variables (only constants)
 * - No constructors
 * - Multiple inheritance (implements multiple)
 * - All methods are public
 */

public class Lesson17_AbstractClasses {
    public static void main(String[] args) {

        System.out.println("=== ABSTRACT CLASSES ===\n");

        // ============================================================
        // 1. CANNOT INSTANTIATE ABSTRACT CLASS
        // ============================================================

        // Vehicle vehicle = new Vehicle(); // ERROR! Cannot instantiate abstract class

        // But we can create objects of concrete subclasses
        Vehicle car = new Car("Toyota Camry", 2023, 4);
        Vehicle truck = new Truck("Ford F-150", 2023, 2000);

        System.out.println();


        // ============================================================
        // 2. USING ABSTRACT CLASSES
        // ============================================================

        System.out.println("--- Vehicles ---");

        car.start();        // Concrete method from abstract class
        car.drive();        // Abstract method implemented in Car
        car.fuelType();     // Abstract method implemented in Car
        car.stop();         // Concrete method from abstract class

        System.out.println();

        truck.start();
        truck.drive();
        truck.fuelType();
        truck.stop();

        System.out.println();


        // ============================================================
        // 3. POLYMORPHISM WITH ABSTRACT CLASSES
        // ============================================================

        System.out.println("--- Polymorphism ---");

        Vehicle[] vehicles = {
            new Car("Honda Civic", 2024, 4),
            new Truck("Chevy Silverado", 2024, 3000),
            new Motorcycle("Yamaha R1", 2023)
        };

        for (Vehicle v : vehicles) {
            v.displayInfo();
            v.drive();
            System.out.println();
        }


        // ============================================================
        // 4. EMPLOYEES EXAMPLE
        // ============================================================

        System.out.println("--- Employees ---");

        Employee emp1 = new FullTimeEmployee("Alice Johnson", 1001, 75000);
        Employee emp2 = new PartTimeEmployee("Bob Smith", 1002, 25, 160);
        Employee emp3 = new Contractor("Charlie Brown", 1003, 80, 120);

        Employee[] employees = {emp1, emp2, emp3};

        for (Employee emp : employees) {
            emp.displayInfo();
            System.out.println("Salary: $" + emp.calculateSalary());
            emp.work();
            System.out.println();
        }


        // ============================================================
        // 5. SHAPES EXAMPLE
        // ============================================================

        System.out.println("--- Shapes ---");

        Shape circle = new Circle(5, "Red");
        Shape rectangle = new Rectangle(4, 6, "Blue");
        Shape triangle = new Triangle(3, 7, "Green");

        Shape[] shapes = {circle, rectangle, triangle};

        for (Shape shape : shapes) {
            System.out.println("Color: " + shape.getColor());
            shape.draw();
            System.out.println("Area: " + shape.calculateArea());
            System.out.println("Perimeter: " + shape.calculatePerimeter());
            System.out.println();
        }


        // ============================================================
        // 6. ANIMALS EXAMPLE
        // ============================================================

        System.out.println("--- Animals ---");

        Animal dog = new Dog("Buddy", 3);
        Animal cat = new Cat("Whiskers", 2);
        Animal bird = new Bird("Tweety", 1);

        Animal[] animals = {dog, cat, bird};

        for (Animal animal : animals) {
            animal.displayInfo();  // Concrete method
            animal.makeSound();    // Abstract method
            animal.sleep();        // Concrete method
            System.out.println();
        }


        // ============================================================
        // KEY TAKEAWAYS
        // ============================================================

        /*
         * ABSTRACT CLASS RULES:
         * 1. Use 'abstract' keyword to declare
         * 2. Cannot instantiate (no objects)
         * 3. Can have abstract methods (no body)
         * 4. Can have concrete methods (with body)
         * 5. Can have constructors (called by subclasses)
         * 6. Can have instance variables
         * 7. Can have static methods
         * 8. Subclass must implement all abstract methods
         *
         * ABSTRACT METHOD RULES:
         * 1. No method body (no implementation)
         * 2. Ends with semicolon
         * 3. Must be in abstract class
         * 4. Subclass must provide implementation
         * 5. Cannot be private or final
         *
         * WHEN TO USE:
         * - Define a template for a group of related classes
         * - Share common code among subclasses
         * - Force subclasses to implement certain methods
         * - Provide default behavior with option to override
         *
         * BENEFITS:
         * - Code reusability
         * - Enforce method implementation in subclasses
         * - Achieve abstraction (hide complexity)
         * - Support polymorphism
         *
         * ABSTRACT vs CONCRETE:
         * - Abstract: Cannot instantiate, can have abstract methods
         * - Concrete: Can instantiate, all methods implemented
         */
    }
}


// ============================================================
// VEHICLE EXAMPLE
// ============================================================

// Abstract class - cannot create objects of Vehicle
abstract class Vehicle {
    protected String model;
    protected int year;

    // Constructor (called by subclasses)
    public Vehicle(String model, int year) {
        this.model = model;
        this.year = year;
    }

    // Concrete method (with implementation)
    public void start() {
        System.out.println(model + " is starting...");
    }

    public void stop() {
        System.out.println(model + " is stopping...");
    }

    public void displayInfo() {
        System.out.println("Model: " + model + ", Year: " + year);
    }

    // Abstract methods (no implementation) - subclasses MUST implement
    public abstract void drive();

    public abstract void fuelType();
}


class Car extends Vehicle {
    private int numberOfDoors;

    public Car(String model, int year, int numberOfDoors) {
        super(model, year);
        this.numberOfDoors = numberOfDoors;
    }

    @Override
    public void drive() {
        System.out.println("Car is driving smoothly on the road");
    }

    @Override
    public void fuelType() {
        System.out.println("This car uses gasoline");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Doors: " + numberOfDoors);
    }
}


class Truck extends Vehicle {
    private int loadCapacity;

    public Truck(String model, int year, int loadCapacity) {
        super(model, year);
        this.loadCapacity = loadCapacity;
    }

    @Override
    public void drive() {
        System.out.println("Truck is hauling cargo");
    }

    @Override
    public void fuelType() {
        System.out.println("This truck uses diesel");
    }

    @Override
    public void displayInfo() {
        super.displayInfo();
        System.out.println("Load Capacity: " + loadCapacity + " kg");
    }
}


class Motorcycle extends Vehicle {
    public Motorcycle(String model, int year) {
        super(model, year);
    }

    @Override
    public void drive() {
        System.out.println("Motorcycle is speeding on two wheels");
    }

    @Override
    public void fuelType() {
        System.out.println("This motorcycle uses gasoline");
    }
}


// ============================================================
// EMPLOYEE EXAMPLE
// ============================================================

abstract class Employee {
    protected String name;
    protected int employeeId;

    public Employee(String name, int employeeId) {
        this.name = name;
        this.employeeId = employeeId;
    }

    // Concrete method
    public void displayInfo() {
        System.out.println("Name: " + name + ", ID: " + employeeId);
    }

    // Abstract methods - each employee type calculates salary differently
    public abstract double calculateSalary();

    public abstract void work();
}


class FullTimeEmployee extends Employee {
    private double annualSalary;

    public FullTimeEmployee(String name, int employeeId, double annualSalary) {
        super(name, employeeId);
        this.annualSalary = annualSalary;
    }

    @Override
    public double calculateSalary() {
        return annualSalary / 12; // Monthly salary
    }

    @Override
    public void work() {
        System.out.println(name + " is working full-time (40 hours/week)");
    }
}


class PartTimeEmployee extends Employee {
    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String name, int employeeId, double hourlyRate, int hoursWorked) {
        super(name, employeeId);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }

    @Override
    public void work() {
        System.out.println(name + " is working part-time (" + hoursWorked + " hours)");
    }
}


class Contractor extends Employee {
    private double contractRate;
    private int hoursWorked;

    public Contractor(String name, int employeeId, double contractRate, int hoursWorked) {
        super(name, employeeId);
        this.contractRate = contractRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return contractRate * hoursWorked;
    }

    @Override
    public void work() {
        System.out.println(name + " is working on contract (" + hoursWorked + " hours)");
    }
}


// ============================================================
// SHAPE EXAMPLE
// ============================================================

abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    // Concrete method
    public void draw() {
        System.out.println("Drawing a " + color + " shape");
    }

    // Abstract methods
    public abstract double calculateArea();

    public abstract double calculatePerimeter();
}


class Circle extends Shape {
    private double radius;

    public Circle(double radius, String color) {
        super(color);
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " circle with radius " + radius);
    }
}


class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height, String color) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return width * height;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " rectangle: " + width + "x" + height);
    }
}


class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height, String color) {
        super(color);
        this.base = base;
        this.height = height;
    }

    @Override
    public double calculateArea() {
        return 0.5 * base * height;
    }

    @Override
    public double calculatePerimeter() {
        // Simplified - assuming equilateral
        return 3 * base;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + color + " triangle");
    }
}


// ============================================================
// ANIMAL EXAMPLE
// ============================================================

abstract class Animal {
    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Concrete methods
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    public void sleep() {
        System.out.println(name + " is sleeping...");
    }

    // Abstract method - each animal makes different sound
    public abstract void makeSound();
}


class Dog extends Animal {
    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Woof! Woof!");
    }
}


class Cat extends Animal {
    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Meow!");
    }
}


class Bird extends Animal {
    public Bird(String name, int age) {
        super(name, age);
    }

    @Override
    public void makeSound() {
        System.out.println(name + " says: Tweet! Tweet!");
    }
}
