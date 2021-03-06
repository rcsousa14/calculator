package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
   private var lastNumeric : Boolean = false
    private var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

 // for each digit it get the text onclick
    fun onDigit(view: View) {
        val input = findViewById<TextView>(R.id.tvInput)
        input.append((view as Button).text)
        lastNumeric = true

    }
 // clears the text
    fun onClear(view: View){
        val input = findViewById<TextView>(R.id.tvInput)
        input.text = ""
        lastNumeric = false
        lastDot = false

    }

    //to add decimal
    fun onDecimalPoint(view: View){

        if(lastNumeric && !lastDot){
            val input = findViewById<TextView>(R.id.tvInput)
            input.append((view as Button).text)
            lastNumeric = false
            lastDot = true

        }
    }

    //what to do onclick on equals
    fun onEqual(view: View){
        if(lastNumeric){
            val input = findViewById<TextView>(R.id.tvInput)
            var tvValue = input.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")){
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                else if(tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    input.text = removeZero((one.toDouble() - two.toDouble()).toString())
                }
                else if(tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    input.text = removeZero((one.toDouble() + two.toDouble()).toString())
                }
                else if(tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    input.text = removeZero((one.toDouble() * two.toDouble()).toString())
                }
                else if(tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]
                    if(prefix.isNotEmpty()){
                        one = prefix + one
                    }
                    input.text = removeZero((one.toDouble() / two.toDouble()).toString())
                }
            }catch (e: ArithmeticException){
                e.printStackTrace()
            }
        }
    }

 // onclick of each operator
    fun onOperator(view: View){
        val input = findViewById<TextView>(R.id.tvInput)
        if(lastNumeric && !isOperatorAdded(input.text.toString())){
            input.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }
    //function to remove the zeros in the result
    private fun removeZero(result: String): String {
        var value = result
        if(result.contains(".0")){
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    //function to see if number is negative or if the operator already been used
    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")){
            false
        } else {
            value.contains("/") || value.contains("+") || value.contains("-") || value.contains("*")
        }
    }

}