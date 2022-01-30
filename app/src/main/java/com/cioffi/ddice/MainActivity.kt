package com.cioffi.ddice

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import java.util.*
import kotlin.collections.ArrayList
import kotlin.String as String1

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Custom logic
        var rollButton: Button = findViewById(R.id.rollButton)

        var showHist: Button = findViewById(R.id.viewHist)

        //Set Default
        //Attempt
        val textInputAttemp: TextInputEditText = findViewById(R.id.textEdit1)
        textInputAttemp.text = "1".toEditable()

        //Number of Faces
        val textInputNum: TextInputEditText = findViewById(R.id.textEdit2)
        textInputNum.text = "20".toEditable()

        //Modifier
        val textInputMod: TextInputEditText = findViewById(R.id.textEdit3)
        textInputMod.text = "0".toEditable()

        //Array of Dice rolled
        var arrayDice = arrayOf<String1>()

        //List of History
        var mListView = findViewById<ListView>(R.id.listHist)


        //Add onclick Event Listener
        rollButton.setOnClickListener {

            //Close keyboard
            closeKeyBoard()
            //Attempt
            val att: Int = textInputAttemp.text.toString().toInt()

            //Number of Faces
            val faces: Int = textInputNum.text.toString().toInt()

            //Modifier
            val mod: Int = textInputMod.text.toString().toInt()

            var myAttemp = DiceD20(att.toString().toInt(), faces.toString().toInt());

            val resultTextView: TextView = findViewById(R.id.rollResult)
            var attemp = myAttemp.roll()
            var diceRes = attemp + mod
            resultTextView.text = diceRes.toString()
//            Logic for D20
            if (faces == 20) {
                var msg = ""
                //Natural 20
                if (attemp == 20) {
                    msg = getString(R.string.nsucc)
                }
                //Critical 1
                if (attemp == 1) {
                    msg = getString(R.string.nfail)
                }
                val toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
                if (msg.isNotBlank())
                    toast.show()
            }

            arrayDice += "D"+faces+": (" + attemp + ")" + "+" + mod + "=" + diceRes


            val arrayAdapter: ArrayAdapter<*>

            // access the listView from xml file

            arrayAdapter = ArrayAdapter(
                this,
                android.R.layout.simple_list_item_1, arrayDice.reversedArray()
            )

            mListView.adapter = arrayAdapter

//            val adapter = ArrayAdapter(this,
//                R.layout.listview_item, arrayDice)
//
//            val listView: ListView = findViewById(R.id.listHyst)
//            listView.adapter = adapter

        }

        //Show / Hide History
        showHist.setOnClickListener {
            var vis = mListView.getVisibility()
            if (vis == View.VISIBLE) {
                showHist.setText(getString(R.string.vhist))
                mListView.setVisibility(View.INVISIBLE)
            } else {
                showHist.setText(getString(R.string.chist))
                mListView.setVisibility(View.VISIBLE)
            }
        }


    }


    fun String1.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

    private fun closeKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}

class DiceD20(val numAttemps: Int, val numSides: Int) {
    fun roll(): Int {
        var finlRoll: Int = 0
        repeat(numAttemps) {
            var tmp = (1..numSides).random()
            finlRoll = finlRoll + tmp
        }
        return finlRoll
    }

}

