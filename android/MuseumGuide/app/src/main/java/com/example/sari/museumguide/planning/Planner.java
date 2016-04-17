package com.example.sari.museumguide.planning;

/**
 * Created by sari on 17/04/16.
 */

import java.io.File;

import javaff.JavaFF;
import javaff.data.Plan;


public class Planner {

    public Planner(){
       File domainFile = new File("domain.pddl");
       File problemFiles = new File("problem.pddl");
       Plan plan = JavaFF.plan(domainFile)
    }
}
