package com.example.singlecalculator.utills.equation.utills;

public class Branch extends ElementOfEquation {
    private boolean isClosed=false;
    private boolean isOpening;
    private Branch pairBranch;

    public Branch(int position) {
        super(position,TypeOfElement.Branch);
    }
    public Branch(int position, boolean isOpening) {
        super(position, TypeOfElement.Branch);
        this.isOpening = isOpening;
    }


    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    public Branch getPairBranch() {
        return pairBranch;
    }

    public void setPairBranch(Branch pairBranch) {

        this.pairBranch = pairBranch;
        if(pairBranch!=null)
        {
            pairBranch.setPairBranch(this);
        }
        isClosed=pairBranch!=null;
    }




    public boolean isOpening() {
        return isOpening;
    }

    public void setOpening(boolean opening) {
        this.isOpening = opening;
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
        return new StringBuilder().append(position).append(" ").append("()").append("\n").toString();
    }






}
