package com.nerdle;

import java.util.*;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


public class Generator {
    private String name;
    private String currentEquation;
    private int equationLength;
    private ArrayList<ArrayList<char[]>> formats = new ArrayList<>(3); // 3 lengths for equations: 7, 8, 9

    ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
    ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
    
    public Generator(String name) {
        this.name = name;

        for (int i = 0; i < 3; i++) {
            formats.add(new ArrayList<char[]>());
        }
            
        initFormats();

    }


    public String generateEquation()  {

        Random rand = new Random();
        String equation = new String();
        boolean equationFound = false;
        int i=0;
        Integer tmpNumber;

        while (!equationFound) {
            String right = new String();
            String left = new String();

            try {
                ArrayList<char[]> row = formats.get(rand.nextInt(3));    //must be rand
                //System.out.println(row);
                
                // we get the format of equation and store it in tmpLeft
                char[] tmpLeft = row.get(rand.nextInt(row.size()));

                while (i < tmpLeft.length && tmpLeft[i] != '=') {
                    if (tmpLeft[i] == '\u0000') {
                        tmpNumber = rand.nextInt(9) + 1;
                        tmpLeft[i] = Character.forDigit(tmpNumber, 10);
                    }
                    i++;
                }

                i = 0;
                StringBuilder sb = new StringBuilder();
                while (tmpLeft[i] != '=') {
                    sb.append(tmpLeft[i]);
                    i++;
                }

                left = sb.toString();
                right = String.valueOf(scriptEngine.eval(left));

                if (right.length() + left.length() + 1 == tmpLeft.length) {
                    equationFound = true;
                    equation = left + "=" + right;

                    setCurrentEquation(equation);
                    setEquationLength(equation.length());
                }
            } 

            catch (ScriptException e) {
                generateEquation();
            }

        }

        return equation;
        
    }


   
    public void initFormats() {

        char[] tmpFormat = new char[7];
        
        //#########################################################
        // Equation Formats for length=7 (8 formats)
        //#########################################################
        tmpFormat[2] = '+';
        tmpFormat[4] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[2] = '-';
        tmpFormat[4] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[2] = '*';
        tmpFormat[4] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[2] = '/';
        tmpFormat[4] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[1] = '+';
        tmpFormat[3] = '+';
        tmpFormat[5] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[1] = '-';
        tmpFormat[3] = '-';
        tmpFormat[5] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[1] = '-';
        tmpFormat[3] = '+';
        tmpFormat[5] = '=';
        formats.get(0).add(tmpFormat);

        tmpFormat = new char[7];
        tmpFormat[1] = '+';
        tmpFormat[3] = '-';
        tmpFormat[5] = '=';
        formats.get(0).add(tmpFormat);

        //#########################################################
        // Equation Formats for length=8 (8 formats)
        //#########################################################
        tmpFormat = new char[8];
        tmpFormat[2] = '+';
        tmpFormat[5] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[2] = '-';
        tmpFormat[5] = '=';
        formats.get(1).add(tmpFormat);
        


        tmpFormat = new char[8];
        tmpFormat[3] = '/';
        tmpFormat[6] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[1] = '*';
        tmpFormat[3] = '+';
        tmpFormat[5] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[2] = '/';
        tmpFormat[4] = '/';
        tmpFormat[6] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[1] = '*';
        tmpFormat[3] = '-';
        tmpFormat[6] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[1] = '*';
        tmpFormat[4] = '-';
        tmpFormat[6] = '=';
        formats.get(1).add(tmpFormat);


        tmpFormat = new char[8];
        tmpFormat[2] = '*';
        tmpFormat[4] = '=';
        formats.get(1).add(tmpFormat);


        //#########################################################
        // Equation Formats for length=9 (8 formats)
        //#########################################################
        tmpFormat = new char[9];
        tmpFormat[2] = '+';
        tmpFormat[5] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[1] = '*';
        tmpFormat[3] = '*';
        tmpFormat[5] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[3] = '/';
        tmpFormat[5] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[3] = '*';
        tmpFormat[5] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[3] = '-';
        tmpFormat[6] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[1] = '*';
        tmpFormat[4] = '+';
        tmpFormat[6] = '=';
        formats.get(2).add(tmpFormat);

        tmpFormat = new char[9];
        tmpFormat[1] = '*';
        tmpFormat[4] = '-';
        tmpFormat[6] = '=';
        formats.get(2).add(tmpFormat);


        tmpFormat = new char[9];
        tmpFormat[3] = '/';
        tmpFormat[5] = '/';
        tmpFormat[7] = '=';
        formats.get(2).add(tmpFormat);



    }


    // Getters and Setters 
    public String getCurrentEquation() {
        return currentEquation;
    }
    public int getEquationLength() {
        return equationLength;
    }
    public ArrayList<ArrayList<char[]>> getFormats() {
        return formats;
    }
    public String getName() {
        return name;
    }
    public void setCurrentEquation(String currentEquation) {
        this.currentEquation = currentEquation;
    }
    public void setEquationLength(int equationLength) {
        this.equationLength = equationLength;
    }
    public void setFormats(ArrayList<ArrayList<char[]>> formats) {
        this.formats = formats;
    }
    public void setName(String name) {
        this.name = name;
    }


}
