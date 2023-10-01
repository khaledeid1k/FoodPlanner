package com.example.foodplanner.data.models.meal;

public class Instructions {
    String number;
    String Instruction;

    public Instructions(String number, String instruction) {
        this.number = number;
        Instruction = instruction;
    }

    public String getNumber() {
        return number;
    }


    public String getInstruction() {
        return Instruction;
    }

}
