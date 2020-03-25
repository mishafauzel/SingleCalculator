package com.example.singlecalculator.utills.equation.utills;

public class Action extends ElementOfEquation {
    TypeOfAction typeOfAction;

    public Action(int position, String actionString) {
        super(position,TypeOfElement.Action);
        this.typeOfAction = stringToTypeOfAction(actionString);
    }

    @Override
    public String toString() {
        return typeOfActionToString(typeOfAction);
    }

    public enum TypeOfAction{
        plus(1),minus(1),multiply(2),divide(2);


        int priority;
        TypeOfAction(int priority) {
            this.priority = priority;
        }
    }
     private TypeOfAction stringToTypeOfAction(String text)
     {
         switch (text)
         {
             case "+":
             {
                 return TypeOfAction.plus;
             }
             case "-":
             {
                 return TypeOfAction.minus;
             }
             case "/":
             {
                 return TypeOfAction.divide;
             }
             case "x":
             {
                 return TypeOfAction.multiply;
             }
             default:
                 return null;

         }
     }

    @Override
    public int getLastPosition() {
        return position+getSizeOfStringRepresentation();
    }

    @Override
    public int getSizeOfStringRepresentation() {
        return 1;
    }

    @Override
    public String toDocumentationString() {
        return new StringBuilder().append("action").append(" ").append(position).append(" ").append(typeOfActionToString(this.typeOfAction)).append("\n").toString();
    }

    private String typeOfActionToString(TypeOfAction action)
     {
         switch (action)
         {
             case plus: return "+";
             case minus: return "-";
             case divide: return"/";
             case multiply: return "x";
             default:return "something went wrong";

         }
     }
}
