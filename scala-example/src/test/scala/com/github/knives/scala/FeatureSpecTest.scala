package com.github.knives.scala

import org.scalatest.{Matchers, GivenWhenThen, FeatureSpec}

case class Employee(firstName : String, lastName: String, ssn: String = "000-00-0000") {
  val fullName = firstName + " " + lastName
}

class FeatureSpecTest extends FeatureSpec with GivenWhenThen with Matchers {  
  info("As an employee object consumer")  
  info("I want to be able to create an employee object")  
  info("So I can access the first name and last name")  
  info("And get the employee full name when I need it")
  info("And also get the Social Security Number")
  feature("Employee object") {    
    scenario("Create an employee object with first and last name") {      
      Given("an Employee object is created")      
      val employee = new Employee("Lukasz", "Szwed")      
      Then("the first name and last name should be set")      
      val firstName = employee.firstName      
      firstName should be ("Lukasz")      
      val lastName = employee.lastName      
      lastName should be ("Szwed")      
      Then("the full name should be set")      
      employee.fullName should be (firstName + " " + lastName)      
      Then("the ssn should be set")      
      employee.ssn should be ("000-00-0000")    
    }  
  } 
}