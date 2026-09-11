package org.example;

public class Engine {
    public void start(){
        System.out.println("Engine is starting...");
    }
    public void stop(){
        System.out.println("Engine is stopping...");
    }
}

public class Car{
    private final Engine engine;

    public Car(){
        this.engine = new Engine();
    }

    public void startCar(){
        engine.start();
    }
}


