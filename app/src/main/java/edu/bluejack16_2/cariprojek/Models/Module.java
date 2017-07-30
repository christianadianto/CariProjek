package edu.bluejack16_2.cariprojek.Models;

import java.util.ArrayList;

/**
 * Created by chris on 07/29/2017.
 */

public class Module {
    public ArrayList<String>urls;
    public ArrayList<String>titles;

    public Module(String category){
        urls = new ArrayList<String>();
        titles = new ArrayList<String>();

        if(category.equals("C++")){
            setModuleCPlus();
        }
        else if(category.equals("C#")){
            setModuleCSharp();
        }

        else if(category.equals("Ruby")){
            setModuleRuby();
        }

        else if(category.equals("Python")){
            setModulePython();
        }

        else if(category.equals("Web Design")){
            setModuleWebDesign();
        }
        else if(category.equals("Web Programming")){
            setModuleWebProg();
        }
    }

    public void setModuleCPlus(){
        titles.add("Introduction to C++");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");

        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }

    public void setModuleCSharp(){
        titles.add("Introduction to C#");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");

        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }

    public void setModuleRuby(){
        titles.add("Introduction to Ruby");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");

        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }

    public void setModulePython(){
        titles.add("Introduction to Python");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");

        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }

    public void setModuleWebDesign(){
        titles.add("Introduction to Web Design");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");


        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }
    public void setModuleWebProg(){
        titles.add("Introduction to Web Programming");
        titles.add("Basic Syntax");
        titles.add("Variable types");
        titles.add("Function");
        titles.add("Array&pointers");


        urls.add("https://www.tutorialspoint.com/cplusplus/index.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_basic_syntax.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_variable_types.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_functions.htm");
        urls.add("https://www.tutorialspoint.com/cplusplus/cpp_arrays.htm");
    }


}
