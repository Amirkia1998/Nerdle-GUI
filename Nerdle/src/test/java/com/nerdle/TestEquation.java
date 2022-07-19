package com.nerdle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.After;  
import org.junit.AfterClass;  
import org.junit.Before;  
import org.junit.BeforeClass;  
import org.junit.Test;  
  
  
public class TestEquation {  

    static Generator tmpGenerator;
    static ScriptEngineManager scriptEngineManager;
    static ScriptEngine scriptEngine;
    static String equation;
    static String left;
    static String right;


  
    @BeforeClass  
    public static void setUpBeforeClass() throws Exception {  
        tmpGenerator = new Generator("tmpGenerator");
        scriptEngineManager = new ScriptEngineManager();
        scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
    }  
    @Before  
    public void setUp() throws Exception {  
        equation = tmpGenerator.generateEquation();
        String[] tmp = equation.split("=");  
        left = tmp[0];
        right = tmp[1];
    }  
  
    
    //####################### TEST CASES ######################################################
    @Test
    public void testEquation(){
        // test if generator returns anything
        assertNotNull(equation);
        System.out.println("Generator Returns Value");
    }
    @Test
    public void testEquationType(){
        // test if generator returns String
        assertTrue(equation instanceof String);
        System.out.println("Returned Equation is String");
    }
    @Test  
    public void testLength(){  
        // test if the length of the equation is 7 or 8 or 9
        int l = tmpGenerator.getEquationLength();
        assertTrue( l >=7 && l <= 9 );
        System.out.println("Equation length is 7 or 8 or 9");
    }  
    @Test  
    public void testOperators(){  
        // test if the equation has operators
         assertTrue(equation.contains("="));
         assertTrue(equation.contains("+") || equation.contains("-") || equation.contains("*") || equation.contains("/"));
         System.out.println("Equation contains {=} and one of the {+, -, *, /}");
    }  
    @Test  
    public void testEquality() throws ScriptException{  
        // test if both sides of the equation are equal to each other
        assertEquals(scriptEngine.eval(right), scriptEngine.eval(left));
        System.out.println("Both sides of the Equation are equal to each other");
    }  
    //##############################################################################################


    @After  
    public void tearDown() throws Exception {  
        System.out.println("-------------------------------");
    }  
    @AfterClass  
    public static void tearDownAfterClass() throws Exception {  
         System.out.println("------------------------------");
         System.out.println("END OF TEST");
    }  
  
} 