package edu.bluejack16_2.cariprojek.Models;

import java.util.ArrayList;

/**
 * Created by chris on 07/29/2017.
 */

public class Module {
    private ArrayList<String>urls;
    private ArrayList<String>titles;

    public Module(String category){
        if(category.equals("C++")){
            setModuleCPlus();
        }
    }

    public void setModuleCPlus(){
        titles.add("Introduction to C++");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");
    }

    public void setModuleCSharp(){
        titles.add("Introduction to C#");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");
    }

    public void setModuleRuby(){
        titles.add("Introduction to C++");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");
    }
}
